package org.emayor.servicehandling.model;

import com.oracle.bpel.client.auth.DomainAuth;
import com.oracle.bpel.client.auth.DomainAuthFactory;
import com.oracle.services.bpel.task.IWorklistService;
import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import com.oracle.services.bpel.task.ITask;
import com.oracle.bpel.client.ServerException;
import com.oracle.bpel.client.util.WhereCondition;
//import com.oracle.bpel.client.Locator;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.Tasks;
import org.emayor.servicehandling.kernel.BPELDomainCredentials;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class UTWrapperEJBBean implements SessionBean 
{
  private static final Logger log = Logger.getLogger(UTWrapperEJBBean.class);
  
  private static final int ALREADY_COMPLETED = 99999;
  
  //private IWorklistService worklistService;
  private SessionContext ctx;
  private Locator locator;

  public void ejbCreate() throws CreateException
  {
    log.debug("ejbCreate -> start processing ...");
    this.locator = Locator.getInstance();
    log.debug("ejbCreate -> ... processing DONE!");
  }

  public void ejbActivate()
  {
    log.debug("ejbActivate -> start processing ...");
  }

  public void ejbPassivate()
  {
    log.debug("ejbPassivate -> start processing ...");
  }

  public void ejbRemove()
  {
    log.debug("ejbRemove -> start processing ...");
  }

  public void setSessionContext(SessionContext ctx)
  {
    log.debug("setSessionContext -> start processing ...");
    this.ctx = ctx;
  }

  public org.emayor.servicehandling.kernel.ITask lookupTask(String taskId, BPELDomainCredentials credentials) throws UTWrapperException
  {
    log.debug("lookupTask -> start processing ...");
    Task ret = null;
    try {
      IWorklistService worklistService = this.locator.getWorklistService(credentials);
      ITask _task =  worklistService.lookupTask(taskId);
      if (_task != null) 
      {
        log.debug("lookupTask -> found the task -> set up the resonse");
        if (log.isDebugEnabled())
          log.debug("lookupTask -> the taskId = " + _task.getTaskId());
        ret = this.fromBPELTask(_task);
      }
    } catch(ServerException sex) 
    {
      log.error("lookupTask -> caught ex: " + sex.toString());
      throw new UTWrapperException("operation failed: " + sex.toString());
    }
    return ret; 
  }


  public Tasks listTasksByAssignee(String assignee, BPELDomainCredentials credentials) throws UTWrapperException
  {
    log.debug("listTasksByAssignee -> start processing ...");
    Tasks ret = new Tasks();
    try {
      IWorklistService worklistService = this.locator.getWorklistService(credentials);
      ret = this.fromBPELTaskArray(worklistService.listTasksByAssignee(assignee));
    } catch(ServerException sex) 
    {
      log.error("listTasksByAssignee -> caught ex: " + sex.toString());
      throw new UTWrapperException("operation failed: " + sex.toString());
    }
    return ret;
  }

  public Tasks lookupTasksByAssigneeAndTitle(String assignee, String title, BPELDomainCredentials credentials) throws UTWrapperException
  {
    log.debug("lookupTasksByAssigneeAndTitle -> start processing ...");
    Tasks ret = null;
    try {
      if (log.isDebugEnabled()) 
      {
        log.debug("lookupTasksByAssigneeAndTitle -> assignee = " + assignee);
        log.debug("lookupTasksByAssigneeAndTitle -> title    = " + title);
      }
      log.debug("lookupTasksByAssigneeAndTitle -> creating where condition");
      WhereCondition where = new WhereCondition();        					
      WhereCondition whereAssignee = new WhereCondition( "assignee = ?");
      whereAssignee.setString(1, assignee);
      where.append( whereAssignee);
      WhereCondition whereTitle = new WhereCondition( "title = ?" );
      whereTitle.setString(1, title);
      where.append("and").append( whereTitle );
      WhereCondition wherePriority = new WhereCondition(" priority != ?");
      wherePriority.setInt(1, ALREADY_COMPLETED);
      where.append("and").append( wherePriority );
      if (log.isDebugEnabled()) 
        log.debug("lookupTasksByAssigneeAndTitle -> created following where condition: " + where.toString());
      IWorklistService worklistService = this.locator.getWorklistService(credentials);
      if (worklistService == null)
        log.debug("lookupTasksByAssigneeAndTitle -> worklistService ref is NULL !!!");
      log.debug("lookupTasksByAssigneeAndTitle -> call listTasks");
      ITask[] tasks = worklistService.listTasks(where);
      if (tasks != null && tasks.length !=0) 
      {
        log.debug("lookupTasksByAssigneeAndTitle -> got tasks number = " + tasks.length);       
        ITask _task = tasks[tasks.length - 1];
        tasks = new ITask[1];
        tasks[0] = _task;
        ret = this.fromBPELTaskArray(tasks);
      }
      else 
      {
        log.debug("FOUND NO NEW TASKS !!!!!");
      }
    } catch(ServerException sex) 
    {
      log.error("lookupTasksByAssigneeAndTitle -> caught ex: " + sex.toString());
      throw new UTWrapperException("operation failed: " + sex.toString());
    } catch (Exception ex) 
    {
      log.error("lookupTasksByAssigneeAndTitle -> caught ex: " + ex.toString());
    }
    log.debug("lookupTasksByAssigneeAndTitle -> start processing ...");
    return ret;
  }

  public void completeTask(org.emayor.servicehandling.kernel.ITask task, BPELDomainCredentials credentials, String userID) throws UTWrapperException
  {
    log.debug("completeTask -> start processing ...");
    try {
      IWorklistService worklistService = this.locator.getWorklistService(credentials);
      if (log.isDebugEnabled())
        log.debug("completeTask -> current taskID = " + task.getTaskId());
      log.debug("completeTask -> try to lookup the specified task");
      ITask _task = worklistService.lookupTask(task.getTaskId());
      if (log.isDebugEnabled()) 
      {
        log.debug("completeTask -> lookuped task with id = " + _task.getTaskId()); 
      }
      log.debug("completeTask -> set the xml document as attachment");
      if (log.isDebugEnabled())
        log.debug("completeTask -> set the xml document:\n" + task.getXMLDocument());
      
      log.info("merge attachment BEGIN ....");
      _task = mergeAttachment(_task,task);
      log.info("merge attachment END ....");
      
      log.debug("completeTask -> set the userID: "+userID);
      _task.setModifier(userID);
      log.debug("completeTask -> set the signature (xml document 3) as custom key");
      _task.setCustomKey(task.getDocDigSig());
      log.debug("completeTask -> set the status");
      _task.setConclusion(task.getStatus());
      log.debug("completeTask -> try to complete task");
      log.debug("completeTask -> set the priority to ALREADY_COMPLETED");
      _task.setPriority(ALREADY_COMPLETED);
      worklistService = this.locator.getWorklistService(credentials);
      worklistService.completeTask(_task);
      log.debug("completeTask -> the task has been completed!");
    } catch(ServerException sex) 
    {
      log.error("completeTask -> caught ex: " + sex.toString());
      throw new UTWrapperException("operation failed: " + sex.toString());
    } 
    log.debug("completeTask -> ... processing DONE!");
  }
  
  private Tasks fromBPELTaskArray(ITask[] input) throws ServerException
  {
    log.debug("fromBPELTaskArray -> start processing ...");
    Tasks ret = new Tasks();
    if (input != null) {
      Task[] tasks = new Task[input.length];
      for (int i = 0; i < tasks.length; i++) 
        tasks[i] = this.fromBPELTask(input[i]);
      ret.setTasks(tasks);
    }
    return ret;
  }
  
  private Task fromBPELTask(ITask input) throws ServerException
  {
    log.debug("fromBPELTask -> start processing ...");
    Task ret = null;
    if (input != null) 
    {
      ret = new Task();
      if (log.isDebugEnabled())
        log.debug("fromBPELTask -> the taskId = " + input.getTaskId());
      ret = new Task();
      ret.setTaskId(input.getTaskId());
      ret.setStatus(input.getConclusion());
      ret.setTaskType(input.getPriority());
      ret.setIncoming(input.getCreationDate());
      ret.setDeadline(input.getExpirationDate());
      ret.setSsid(input.getTitle());
     
      log.info("split attachment BEGIN....");
      ret = splitAttachment(ret,input);
      log.info("split attachment END ....");
      
      /*
      String sig = input.getCustomKey();
      if (sig != null && sig.length() != 0 && !sig.equalsIgnoreCase("</empty>")) 
      {
        log.debug("fromBPELTask -> got valid digital signature part!");
        if (log.isDebugEnabled())
          log.debug("fromBPELTask -> digsig: \n" + sig);
        sig = this.unmarshall(sig);
      }
      else 
      {
        sig = "</empty>";
      }
      ret.setDocDigSig(sig);
      */
      
    }
    return ret;

  }
  
  private final String unmarshall(String input) 
  {
    String ret = input.replaceAll("&gt;",">");
    ret = ret.replaceAll("&lt;"         ,"<");
    ret = ret.replaceAll("&amp;"         ,"&");
    return ret;
  }
  
  private final String unmarshallSecondStage(String input) 
  {
    String ret = input.replaceAll(		"_EMAY_LT_"    ,"<");
    ret = ret.replaceAll(				"_EMAY_GT_"    ,">");
    return ret;
  }
  
  private final String marshallSecondStage(String input) 
  {
    String ret = input.replaceAll(	"<",	"_EMAY_LT_");
    ret = ret.replaceAll(			">",	"_EMAY_GT_");
    return ret;
  }

  private final Task splitAttachment(Task output, ITask input) {
  	String attachment;
	try {
		
		Object refObj = input.getAttachment();
		
		Element elem = (Element)refObj;
		/*
		log.debug("get node value = " + elem.getNodeValue());
		javax.swing.JFrame frame = new javax.swing.JFrame();
    	javax.swing.JScrollPane pane = new javax.swing.JScrollPane();
    	javax.swing.JTextArea ta = new javax.swing.JTextArea();
    	pane.setViewportView(ta);
    	frame.getContentPane().add(pane);
    	frame.setSize(400,400);
    	System.out.println(".... after GUI ....");
    	ta.append(elem.getNodeValue());
    	ta.append("\n------------------------------\n");
    	ta.append(input.getAttachment().toString());
    	frame.setVisible(true);
    	System.out.println("########### #.... after GUI .... ################# #");
    	*/
		/*
			from log: MICallHandler-56] - refObject.getClass().getName(): com.collaxa.cube.xml.dom.CubeDOMElement
		*/
		
		//log.info("refObject.getClass().getName(): " + refObj.getClass().getName());
		
		//attachment = input.getAttachment().toString();
		attachment = elem.getNodeValue();
		log.debug("got att:\n" + attachment);
		/*
		if (attachment != null) {
		      int start = attachment.indexOf(">") + 1;
		      int stop = attachment.lastIndexOf("</");
		      attachment = attachment.substring(start, stop);
		      attachment = this.unmarshall(attachment);
		}*/
		
		//log.info("attachment raw IN value is: "+attachment);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document root = builder.parse(new InputSource(new StringReader(attachment)));
		
		String doc0 = getDocNodeFromAttachment("/taskRequest/doc0/text()",root);
		String doc1 = getDocNodeFromAttachment("/taskRequest/doc1/text()",root);
		String doc2 = getDocNodeFromAttachment("/taskRequest/doc2/text()",root);
		String doc3 = getDocNodeFromAttachment("/taskRequest/doc3/text()",root);
		String doc4 = getDocNodeFromAttachment("/taskRequest/doc4/text()",root);
		
			  	
	    if (doc0 != null) {
	    	log.info("fromBPELTask -> got from doc0 after transformation: \n" + doc0);
	      	output.setXMLDocument(doc0);
	    } else {
	    	output.setXMLDocument(attachment);
	    }
	    
	    if (doc1 != null) {
	        log.info("fromBPELTask -> got from doc1 after transformation: \n" + doc1);
	        output.setDocumentResponse(doc1);
	      } else {
	      	output.setDocumentResponse("<empty/>");
	    }
	    
	    if (doc2 != null) {
	        log.info("fromBPELTask -> got from doc2 after transformation: \n" + doc2);
	        output.setDocumentResponseDigSig(doc2);
	      } else {
	      	output.setDocumentResponseDigSig("<empty/>");
	    }
	    
	    if (doc3 != null) {
	        log.info("fromBPELTask -> got from doc3 after transformation: \n" + doc3);
	        output.setDocDigSig(doc3);
	    } else {
	    	output.setDocDigSig("<empty/>");
	    }
	    
	    if (doc4 != null) {
	        log.info("fromBPELTask -> got from doc4 after transformation: \n" + doc4);
	    }
	    
	} catch (ServerException e) {
        log.info("Failed to get input ...");
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	log.info("doc1 : "+output.getDocumentResponse());
	log.info("doc2 : "+output.getDocumentResponseDigSig());
	log.info("doc3 : "+output.getDocDigSig());
	
    return output;
  }
  
  private final ITask mergeAttachment(ITask output, org.emayor.servicehandling.kernel.ITask input) {

  	String doc0 = input.getXMLDocument(); 
  	String doc1 = input.getDocumentResponse();
  	String doc2 = input.getDocumentResponseDigSig();
  	String doc3 = input.getDocDigSig();
  	String doc4 = null;
  	
  	String result = "<taskRequest>";
  	
  	if ( doc0 == null || doc0.equals("") || doc0.equals("<empty/>"))
  		result+= "<doc0><empty/></doc0>";
  	else 
  		result+= "<doc0>"+marshallSecondStage(doc0)+"</doc0>";
  	
  	if ( doc1 == null || doc1.equals("") || doc1.equals("<empty/>")) {
  		result+= "<doc1><empty/></doc1>";
  		doc1 = null;
  	} else 
  		result+= "<doc1>"+marshallSecondStage(doc1)+"</doc1>";
  	
  	if ( doc2 == null || doc2.equals("") || doc2.equals("<empty/>")) {
  		result+= "<doc2><empty/></doc2>";
  		doc2 = null;
  	} else 
  		result+= "<doc2>"+marshallSecondStage(doc2)+"</doc2>";
  	
  	if ( doc3 == null || doc3.equals("") || doc3.equals("<empty/>")) {
  		result+= "<doc3><empty/></doc3>";
  		doc3 = null;
  	} else 
  		result+= "<doc3>"+marshallSecondStage(doc3)+"</doc3>";
  	
  	result+= "<doc4><empty/></doc4>";
  	
  	result += "</taskRequest>";

  	if (doc1 == null && doc2 == null && doc3 == null && doc4 == null) {
  		result = doc0;
  	}
  	
  	// fallback if everything else fails
  	output.setAttachment(result);
  	
  	//log.info("attachment raw OUT value is: "+result);
  	
  	return output;
  }
  
  private final String getDocNodeFromAttachment(String node, Document root) {
  	
  	String result = null;
  	
  	try {
  		result = XPathAPI.selectSingleNode(root,node).getNodeValue();
  	} catch (Exception e) {
  		//e.printStackTrace();
  	}
  	
  	if (result != null) result = unmarshallSecondStage(result);
  	
  	return result;
  }
  
}