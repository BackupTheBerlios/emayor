package org.emayor.servicehandling.model;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.emayor.servicehandling.kernel.ITask;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import org.emayor.servicehandling.model.UTWrapperEJB;
import org.emayor.servicehandling.model.UTWrapperEJBHome;
import org.emayor.servicehandling.kernel.BPELDomainCredentials;
import javax.naming.NamingException;
import java.util.Hashtable;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class UTWrapperEJBClient 
{
  public static void main(String [] args)
  {
    UTWrapperEJBClient uTWrapperEJBClient = new UTWrapperEJBClient();
    try
    {
      Context context = getInitialContext();
      UTWrapperEJBHome uTWrapperEJBHome = (UTWrapperEJBHome)PortableRemoteObject.narrow(context.lookup("UTWrapperEJB"), UTWrapperEJBHome.class);
      UTWrapperEJB uTWrapperEJB;

      // Use one of the create() methods below to create a new instance
      uTWrapperEJB = uTWrapperEJBHome.create();

      // Call any of the Remote methods below to access the EJB
      // uTWrapperEJB.lookupTasksByAssigneeAndTitle( java.lang.String assignee, java.lang.String title );
      String taskId = "bpel://localhost/default/InputDataCollectionIteration~1.0/215-BpInv0-BpSeq2.9-2";
      BPELDomainCredentials credentials = new BPELDomainCredentials();
      credentials.setDomainName("default");
      credentials.setDomainPassword("bpel");
      ITask task = uTWrapperEJB.lookupTask( taskId, credentials );
      String att = task.getXMLDocument();
      System.out.println("got att: " + att);
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      StringReader reader = new StringReader(att);
			InputSource inputSource = new InputSource(reader);
			Document root = builder.parse(inputSource);
      if (root != null)
        System.out.println("ROOT IS NOT NULL ");
      // uTWrapperEJB.listTasksByAssignee( java.lang.String assignee );
      // uTWrapperEJB.listTasks( com.oracle.bpel.client.util.WhereCondition condition );
      // uTWrapperEJB.completeTask( org.emayor.servicehandling.kernel.ITask task );

    }
    catch(Throwable ex)
    {
      ex.printStackTrace();
    }

  }

  private static Context getInitialContext() throws NamingException
  {
    Hashtable env = new Hashtable();
    // Standalone OC4J connection details
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.evermind.server.rmi.RMIInitialContextFactory");
    env.put(Context.SECURITY_PRINCIPAL, "admin");
    env.put(Context.SECURITY_CREDENTIALS, "welcome");
    env.put(Context.PROVIDER_URL, "ormi://localhost/UTWrapperApp");

    return new InitialContext(env);
  }
}