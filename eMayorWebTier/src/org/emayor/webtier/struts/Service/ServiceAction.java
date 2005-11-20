package org.emayor.webtier.struts.Service;


/**
 *  May 15, 2005 created, Joerg Plaz
 */



import java.io.*;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.emayor.webtier.shared.*;

import org.emayor.webtier.municipalities.MunicipalitiesManager;
import org.emayor.webtier.municipalities.Municipality;
import org.emayor.webtier.municipalities.MunicipalityService;

import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.utclient.CVDocumentTypes;
import org.emayor.servicehandling.utclient.CivilServantTaskServiceClient;
import org.emayor.servicehandling.utclient.InputDataCollector;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.utclient.UserTaskServiceClient;
import org.emayor.servicehandling.test.utils.Utils;

import org.emayor.client.Utilities.DataUtilities;
import org.emayor.client.parser.XMLParser;
import org.emayor.client.parser.xml.*;


import org.emayor.webtier.shared.TextFrame;
import org.emayor.webtier.shared.Utilities;


public class ServiceAction extends DispatchAction
{


  private static final String WaitingForResponseFromSH = "WaitingForResponseFromServiceHandling";	
	
  // Backup during the waiting phase. It's used as soon as the request is available.
  private String initialServiceNameKey = "not defined";
  
  
  
  // Use UTF-8 for file read operations
  private static final Charset charset = Charset.forName("UTF-8");
  
  

 /**
  *  First execution method of this DispatchAction 
  */ 
  public ActionForward executeService( ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response )
  {
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 1);

    ServiceForm serviceForm = (ServiceForm)form;
 
    System.out.println(" ");
    System.out.println("-------------------------------ServiceAction.executeService(). Passed parameter names:");
    Enumeration namesEnumeration = request.getParameterNames();
    int count = 0;
    String languageParameterValue = null;
    while( namesEnumeration.hasMoreElements() )
    {
      String parameterName = namesEnumeration.nextElement().toString();
      String parameterValue = request.getParameter(parameterName);
      if( parameterName.equals("language") ) // Take over the language, if its present:
      {
      	languageParameterValue = parameterValue; // Take over the new language in the initialize() call below.
      }
      System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
      count++;  
    }
    System.out.println(">" + count + " parameters.");


    //Get the requested service parameter name from the request:
    String serviceNameKey = request.getParameter(TextResourceKeys.ServiceNameKeyTag);
    
    System.out.println("ServiceAction serviceNameKey is: " + serviceNameKey );
           	
    // Get the requested nameOfMunicipality from the request:
    String municipalityNameKey = request.getParameter(TextResourceKeys.MunicipalityNameKeyTag);
    
    System.out.println("ServiceAction municipalityNameKey is: " + municipalityNameKey );
    
    // Get the associated municipality object:
    Municipality municipality = MunicipalitiesManager.GetInstance().getMunicipalityByKey(municipalityNameKey);
    // and the associated service object:
    MunicipalityService service = municipality.getServiceByIdentifier(serviceNameKey);    

    // Initialize the form, unless the service is already initialized and waiting
    // for the bpel / sh answer:
    if( !serviceNameKey.equals(TextResourceKeys.WaitForServiceRequestResponseKey) )
    {
      String serviceID = ( service != null ) ? service.getServiceIdentifier() : "Service_Undefined";    
      // Initialize the form with these:
      serviceForm.initialize( request.getSession(),
                              languageParameterValue,
                              serviceID,
                              municipalityNameKey );
    } // if  
    
    // User login and authentication must already have been done here:
    // Just check, if this user has an ASID or not, and get it if there is no ASID.
    // This also set the user ROLE.    
    String actionForwardName = "showMunicipalityList"; // case not authenticated yet
    if( this.getIsUserAuthenticated( request, request.getSession() ) )
    {
      if( serviceNameKey.equals(TextResourceKeys.BrowseAvailableDocumentsServiceKey) )
      {
        // Switch to repository:
        // Pass data to the repository:
        Repository repository = new Repository();
        this.requestRepositoryDocumentsFromServiceHandling( repository,serviceForm,request.getSession() );
        // Pass the repository to the serviceForm, so it is
        // accessible from the repository.jsp:
        serviceForm.setRepository( repository );
        // Distinguish between citizen and civil servant repository:
        String role = (String)request.getSession().getAttribute("ROLE");
        actionForwardName = ( role.equalsIgnoreCase("citizen") ) ?
                              "showCitizenRepository" : "showCivilServantRepository";
      }
      else
      {
        // One of the form services.
      	// Check if the service should be started in the ServiceHandling, or
      	// if it already has been started and we are waiting for a response:
      	if( serviceNameKey.equals( TextResourceKeys.WaitForServiceRequestResponseKey ))
      	{
          // Wait until a response has arrived:
          System.out.println("-->Signal: WaitForServiceRequestResponse");
          if( this.getIsRequestResponseAvailable( request.getSession(),serviceForm ) )
          {
            System.out.println("-->Signal: Request available -> processService with initial service namekey= " +
            		           this.initialServiceNameKey  );
            // Reinsert the initial service name:
            System.out.println("+++++++++ Set service identifier to initial service = " + this.initialServiceNameKey );
            serviceForm.setServiceIdentifier( this.initialServiceNameKey );			
          	// Note: getIsRequestResponseAvailable() has returned true,
          	//       so the task information is available in the serviceForm. 
            //       get its data:
            String taskInformation   = serviceForm.getTaskInformation();           
            String taskInformationId = serviceForm.getTaskInformationId();           
            String taskInformationStatus = serviceForm.getTaskInformationStatus();           
            String xmlDocument = serviceForm.getTaskXMLDocument();           
            Task task = serviceForm.getTask();           
            
            /* debug: write out the xml document:
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("The xml document for the client applet (to be packed into the eMayorForm template) is:");
            System.out.println(" ");
            System.out.println(xmlDocument);
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            */
                        
          	// Show the request response - this returns the requestdocument.jsp,
            // which will start the eMayor client applet.
            
            // The emayor applet will call getEMayorForm() after this
            // for getting the complete eMayorForm.
            // Therefore one must pack the e-document into the associated
            // eMayorForm template now and store it in the serviceForm,
            // which is done here:
            // Second parameter = namesuffix (empty or ReadOnly) here: empty.
            if( this.createEMayorFormFromTemplate(xmlDocument,"",serviceForm) )
            {
            	System.out.println("The e-document has been packed into the associated eMayorForm template.");
                actionForwardName = "processService"; // go on, return the page with the client applet now.          	
            }
            else
            {
            	System.out.println("*** The e-document COULD NOT BE packed into the associated eMayorForm template.");
            	actionForwardName = "processService";          	
            }
          }
          else
          {
            System.out.println("-->Signal: Request not yet available. ->Wait");
          	// continue wait:
            String waitRedirectURL = "service.do?do=executeService&caller=executeService1" + 
            "&serviceNameKey=" + TextResourceKeys.WaitForServiceRequestResponseKey +
            "&" + TextResourceKeys.MunicipalityNameKeyTag + "=" + municipalityNameKey +
            "&" + TextResourceKeys.ServiceNameKeyTag + "=" + serviceNameKey ;
            serviceForm.setWaitPageRedirectURL(waitRedirectURL);
            actionForwardName = "WaitForServiceRequestResponse";
          }

      	}
      	else
      	{
      	  // Backup during the waiting phase. It's used as soon as the request is available.
      	  this.initialServiceNameKey = serviceNameKey;      	  
          // Start it:        	
          System.out.println("-->Signal:  -> Start the service.");
          if( this.sendServiceRequestToServiceHandling( municipality,service,request.getSession() ) )
          {
            // Wait for response:
            // Set the required waitPageRedirectURL:
            String waitRedirectURL = "service.do?do=executeService&caller=executeService2" + 
		                             "&serviceNameKey=" + TextResourceKeys.WaitForServiceRequestResponseKey +
		                             "&" + TextResourceKeys.MunicipalityNameKeyTag + "=" + municipalityNameKey +
		                             "&" + TextResourceKeys.ServiceNameKeyTag + "=" + serviceNameKey ;
            serviceForm.setWaitPageRedirectURL(waitRedirectURL);
            actionForwardName = "WaitForServiceRequestResponse";
          }
          else
          {
            // Show the page, which tells the user that the chosen service is
            // currently not available:
            actionForwardName = "showServiceError";
          }
      	}
      }
    }
    else
    {
      // Show the page, which tells the user that the session has expired, and allows
      // the user to login again:
      actionForwardName = "showSessionExpired";    
    }
    System.out.println("ServiceAction.executeService() ends. Redirecting to: " + actionForwardName );    
    return mapping.findForward(actionForwardName);
  } // execute

  

  

  
  
 /**
  *  Packs the e-document in the associated eMayorForm template and sets
  *  the complete eMayorForm (member attribute) in the serviceForm.
  *  When the applet requests it, the sendEmayorForm.jsp will retrieve it
  *  from the serviceForm later.
  * 
  *  eMayorFormNameSuffix= empty or "ReadOnly" for eMayorForms, which additionally
  *  exist in readonly form.
  */ 
  private boolean createEMayorFormFromTemplate( String raw_eDocument,
  		                                        String eMayorFormNameSuffix,
  		                                        ServiceForm serviceForm )
  {
  	boolean success = true;
  	try
	{
      StringBuffer eDocument = new StringBuffer(raw_eDocument);       
      // Step one: Find out the name of the associated eMayorForms template:
  	  // By convention, this must be the name of the eDocument root node
  	  // with ending eMayorForm, and it must reside in the directory
  	  // jboss/conf/MunicipalityInformation/nameOfMunicipality
  	  // along with the municipality.properties file and the *.service files
  	  XMLParser xmlParser = new XMLParser();
  	  XML_Node node = xmlParser.transformDocumentToTree( eDocument );
  	  // Now node is the root, with tagname RootNode. Get its first child,
  	  // which is the edocument node:
  	  node = node.getChildAt(0); //
      String eDocumentName = node.getTagName();
      
      // Get rid of the <?xml ?> prolog and the root node, because we
      // cannot insert the prolog to the eMayorForm's model node,
      // because this wouldn't be valid xml:
      Utilities.RemovePrologFrom(eDocument);       
      // Now eDocument has no <?xml ?> tag (which is not closed) -> ok for the following:
      
      System.out.println("createEMayorFormFromTemplate() eDocument name= root tag= " + eDocumentName );

      // eMayorFormTemplateName is used as filename. This requires, that we search for
      // characters in eDocumentName, which are not valid for the filesystem.
      // Windows: Invalid characters for the filesystem are:   \  /  :  *  ?  "  <  >  |
      // Replace these by underscores:
      String eDocumentNameForFileSystem = Utilities.MakeNameValidForFileSystem(eDocumentName);
      
      String eMayorFormTemplateName = eDocumentNameForFileSystem + eMayorFormNameSuffix + ".eMayorForm";  
      
      System.out.println("createEMayorFormFromTemplate() required eMayorFormTemplateName= " + eMayorFormTemplateName );

      // Examples:      - ResidenceCertificationRequestDocument.eMayorForm
      //                - ResidenceCertificationDocument.eMayorForm
      //                - FamilyResidenceCertificationRequestDocument.eMayorForm
      //                and various others - one has to create them for each service.
           
      // Load it:
      String municipalityNameKey = serviceForm.getMunicipalityNameKey();
      String templatePath = this.calculatePathNameForEMayorFormTemplate( eMayorFormTemplateName,municipalityNameKey );
      StringBuffer eMayorFormsTemplate = null;
      StringBuffer completeForm = new StringBuffer();
      try
      {
        eMayorFormsTemplate = this.loadEMayorFormsTemplate( templatePath );
        if( eMayorFormsTemplate.length() > 0 )
        {
          int modelPosition = eMayorFormsTemplate.indexOf("<Model>");
          completeForm.append( eMayorFormsTemplate.toString().substring(0,modelPosition+7) );
          completeForm.append( eDocument );
          int endPosition = eMayorFormsTemplate.toString().length();
          completeForm.append( eMayorFormsTemplate.toString().substring(modelPosition+7,endPosition ));
        }
        else
        {
          // If loadEMayorFormsTemplate() has worked w.o. exception, but the template is empty,
          // use an error form for informing the user:
          String title = serviceForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation1);
          String message = serviceForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation2) +
                           "\n\nThe eMayorForm template file is empty.";
          String errorForm = this.createErrorForm(title,message);
          completeForm.append(errorForm);
        }
      }
      catch( Exception e )
      {
        // The template file does not exist. Use an error form for informing the user:
        String title = serviceForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation1);
        String message = serviceForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation2) +
                         "\n\nFor the e-document with the name\n" +
                         eDocumentName +
                         "\nthe following eMayorForm template file must exist, but is missing:\n" +
                         templatePath;
        String errorForm = this.createErrorForm(title,message);
        completeForm.append(errorForm);      
      }
       
      //System.out.println("The associated eMayorForms template is:");
      //System.out.println(eMayorFormsTemplate);
      //System.out.println(" ");

      //System.out.println("The complete eMayorForm is:");
      //System.out.println( completeForm.toString("UTF-8") );
      //System.out.println(" ");

      // And set this in the form:
      serviceForm.setEmayorform( completeForm.toString() );
      System.out.println("The complete eMayorForm has been set in the serviceForm.");      
	}
  	catch( Exception e )
	{
  	  e.printStackTrace();
  	  success = false;
      String title = serviceForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation1);
      String message = serviceForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation2) +
                       "\n\n" + e.getMessage();
      String errorForm = this.createErrorForm(title,message);
      // And set this in the form:
      serviceForm.setEmayorform( errorForm );
	}  	
  	return success;
  } // createEMayorFormFromTemplate
  

  
  
  
  

  
 /**
  *  Creates a complete small eMayorForm, which is used above
  *  for propagating error messages to the client.
  *  text can be multiline text.
  */ 
  private String createErrorForm( final String title, final String text )
  {
    StringBuffer buf = new StringBuffer();
    buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
    buf.append("<eMayorForm1 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.emayor.org/eMayorFormsSchema.xsd eMayorFormsSchema.xsd \">");
    buf.append("<Model>");
    buf.append("    <!-- eMayorErrorForm: has no model data, only display -->");
    buf.append("</Model>");
    buf.append("<View>  <!-- The view always has a BorderLayout -->");
    buf.append("  <JPanel LayoutClass=\"BoxLayout\"");
    buf.append("          LayoutParameter=\"BoxLayout.Y_AXIS\"");
    buf.append("          AddParameter=\"BorderLayout.CENTER\"");
    buf.append("          Background=\"252,236,224\">");
    buf.append("    <JLabel Background=\"242,236,224\">" + title + "</JLabel>");
    buf.append("    <Box BoxType=\"createVerticalStrut\" BoxSize=\"40\"/>");
    buf.append("    <JTextArea Background=\"242,236,224\" Rows=\"8\" Columns=\"46\">" + text + "</JTextArea>");
    buf.append("  </JPanel>");
    buf.append("</View>");
    buf.append("</eMayorForm1>");
    return buf.toString();
  }
  
  

  
  
  
  

  private String calculatePathNameForEMayorFormTemplate( final String eMayorFormTemplateName,
                                                         final String municipalityNameKey )
  {
    // The name key is point separated: f.ex. Name.Aachen, take the part after the point:
    StringTokenizer tok = new StringTokenizer(municipalityNameKey,".");
    tok.nextToken();
    String municipalityFolderName = tok.nextToken();
  
    StringBuffer templatePath = new StringBuffer();  
    templatePath.append( System.getProperty("jboss.server.home.dir") );
    templatePath.append( File.separator );
    templatePath.append( "conf" );
    templatePath.append( File.separator );
    templatePath.append( "MunicipalityInformation" );
    templatePath.append( File.separator );
    templatePath.append( municipalityFolderName );
    templatePath.append( File.separator );
    templatePath.append("eMayorFormTemplates"); 
    templatePath.append( File.separator );
    templatePath.append( eMayorFormTemplateName );
    return templatePath.toString();
  }
  
  
  
  
  private StringBuffer loadEMayorFormsTemplate( final String templatePath ) throws Exception
  {
  	  StringBuffer templateBuffer = new StringBuffer();
    	    	
      System.out.println("Loading template with path: " + templatePath );
	  
      File templateFile = new File( templatePath );
      
      FileInputStream inputStream = new FileInputStream(templateFile);
      BufferedReader in = new BufferedReader( new InputStreamReader(inputStream,charset) );
      final char[] characterbuffer = new char[8192];
      int charactersRead;             
      while(  (charactersRead = in.read(characterbuffer)) != -1 )
      {
      	templateBuffer.append( characterbuffer, 0, charactersRead );
      }
      in.close();
      templateFile = null;
    return templateBuffer;
  } // readlanguagePropertiesFromFiles
  

  

  
  
  /**
   *  Execution method of this DispatchAction
   *  Called when either a citizen or a civil servant want to
   *  see a document. The civil servant additionally can decide,
   *  if the document is accepted or not. 
   */
   public ActionForward getEMayorForm( ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response )
   {
    ServiceForm serviceForm = (ServiceForm)form;
    String actionForwardName = "showServiceError";
    if( this.getIsUserAuthenticated( request, request.getSession() ) )
    {
        /* Debug info:
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" eMayorForm sent to client:");
        System.out.println(" ");
        System.out.println( serviceForm.getEmayorform() );
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        */
        
       
         // Note: This is the first call of an eMayorForms applet to the webtier.
         //       With this call, the applet wants to receive the eMayorForm.
         //       So for checking, under which ASID the webtier communicates with
         //       the applet, write out the ASID,SSID and user ROLE, if defined:
         String asid = (String)request.getSession().getAttribute("ASID");
         String ssid = (String)request.getSession().getAttribute("SSID");
         String role = (String)request.getSession().getAttribute("ROLE");
         System.out.println(">> ");
         System.out.println(">> ");
         System.out.println(">> ServiceAction.getEMayorForm():");
         System.out.println(">> ");
         System.out.println(">> This is the first contact of an eMayorForms applet,");
         System.out.println(">> which requests the eMayorForm from the webtier.");
         System.out.println(">> ");
         System.out.println(">> This is performed with the following session id's:");
         System.out.println(">> ");
         System.out.println(">> ASID= " + asid);
         System.out.println(">> SSID= " + ssid);
         System.out.println(">> ROLE= " + role);
         System.out.println(">> ");
         System.out.println(">> ");
         System.out.println(">> ");
             
         // Note: the called sendEMayorForm.jsp will get the complete
         //       eMayorForm from the serviceForm. This has been set
         //       earlier, before the applet page has been sent to the client.
         //       See: setEmayorFormFromTemplate()
         actionForwardName = "sendEMayorForm";    
    }
    else
    {
        System.out.println("ServiceAction: A session has expired.");
        // Show the page, which tells the user that the session has expired, and allows
        // the user to login again:
        actionForwardName = "showSessionExpired";    
    }
    return mapping.findForward(actionForwardName);    
   } // showDocument 

   
   
   
   
   
  
  
 /**
  *  Execution method of this DispatchAction
  *  Called when either a citizen or a civil servant want to
  *  see a document. The civil servant additionally can decide,
  *  if the document is accepted or not - these actions are
  *  defined outside the source in the eMayorForms. 
  */
  public ActionForward showDocument( ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response )
  {
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 1);

    ServiceForm serviceForm = (ServiceForm)form;
    
    System.out.println(" ");
    System.out.println("-----------------------------ServiceAction.showDocument(). Passed parameter names:");
    Enumeration namesEnumeration = request.getParameterNames();
    int count = 0;
    String languageParameterValue = null;
    while( namesEnumeration.hasMoreElements() )
    {
      String parameterName = namesEnumeration.nextElement().toString();
      String parameterValue = request.getParameter(parameterName);
      if( parameterName.equals("language") ) // Take over the language, if its present:
      {
        languageParameterValue = parameterValue; // Take over the new language in the initialize() call below.
      }
      System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
      count++;  
    }
    System.out.println(">" + count + " parameters.");
    
    String actionForwardName = "showServiceError";
    if( this.getIsUserAuthenticated( request, request.getSession() ) )
    {
        //Get the requested service parameter name from the request:
        String serviceNameKey = request.getParameter(TextResourceKeys.ServiceNameKeyTag);
        
        System.out.println("ServiceAction serviceNameKey is: " + serviceNameKey );
               
        // Get the requested nameOfMunicipality from the request:
        String municipalityNameKey = request.getParameter(TextResourceKeys.MunicipalityNameKeyTag);
        
        System.out.println("ServiceAction municipalityNameKey is: " + municipalityNameKey );
        
        // Get the associated municipality object:
        Municipality municipality = MunicipalitiesManager.GetInstance().getMunicipalityByKey(municipalityNameKey);
        // and the associated service object:
        MunicipalityService service = municipality.getServiceByIdentifier(serviceNameKey);

        // and the index of the selected document, which the user wants to see now:
        String documentIndexString = request.getParameter(TextResourceKeys.DocumentIndexKeyTag); 
        
        System.out.println("ServiceAction selected index is: " + documentIndexString );

        // Switch dependent of the user role:
        String role = (String)request.getSession().getAttribute("ROLE");        
        actionForwardName = ( role.equalsIgnoreCase("citizen") ) ?
                              "showDocumentForCitizen" : "showDocumentForCivilServant";      
        try
        {
          // Get the selected document from the list:
          int documentIndex = Integer.parseInt(documentIndexString);
          RepositoryDocument document = serviceForm.getRepositoryDocumentAt(documentIndex);
          // Set the current task from this document - it is used later:
          Task task = document.getTask();
          ServiceTaskInformation serviceTaskInformation = new ServiceTaskInformation( task );
          serviceForm.setServiceTaskInformation( serviceTaskInformation );
          String xmlDocument = document.getXmlDocument();
          // Set it in the serviceForm too:
          serviceForm.setXMLDocument( xmlDocument );
                
          // Debug file write:
          // Utilities.DebugFileWriteTo(xmlDocument,"webtier_got_from_ServiceHandling.xml");
          
          //TextFrame f = new TextFrame("XML received from the webtier repository in showDocument()",
          //                            xmlDocument );
          
          // Now all one has to do is:
          // Create the associated eMayorForm and store it in the serviceForm.
          // The applet will fetch it from there, when it has started.
          // The emayor applet will call getEMayorForm() after this
          // for getting the complete eMayorForm.
          // Therefore one must pack the e-document into the associated
          // eMayorForm template now and store it in the serviceForm,
          // which is done here:
          // Second parameter = namesuffix (empty or ReadOnly) here: ReadOnly.
          // -> loads template with name <eDocument root tag>ReadOnly.eMayorForm
          
          String nameSuffix = ( role.equalsIgnoreCase("citizen") ) ? "ReadOnly" : "CivilServant";
          
          // -----------------------------------------------------------------------------------
          // !!!! TEMPORARY !!!!
          // Here comes the temporary hack for a civil servant acting as local delegate
          // for sergiu, Aachen:
          // If the user role is CivilServant, we decide here, if it is acting as
          // local delegate for a citizen:
          // CS delegate <-> if the document is not the request document and if
          //                 it contains a valid signature
          // Falls CS als delegate operiert, setze ich hier einfach "ReadOnly" postfix
          // fuer die eMayorForm templates.
          // Also:
          if( !role.equalsIgnoreCase("citizen") ) // is a civil servant
          {
            // Make sure, the document is not a request document:
            if( xmlDocument.indexOf("<ResidenceCertificationRequestDocument ") < 0 )
            {
              // It is not a ResidenceCertificationRequestDocument document, so continue
              // Check if it already contains a valid signature:
              if( xmlDocument.indexOf("<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"/>") > 0 )
              {
                // It already contains a signature made by the AET xmlsigner.
                // so only treat this, if the CS was a citizen - set citizen role here:
                nameSuffix = "ReadOnly";
              } // else nothing        
            } // else nothing     
          } // delegate hack      
          // end of temporary hack
          // -----------------------------------------------------------------------------------
          
          
          // Create an store the eMayorForm template, depending from the nameSuffix set above:
          if( this.createEMayorFormFromTemplate( xmlDocument,nameSuffix,serviceForm ) )
          {
          System.out.println("The e-document has been packed into the associated eMayorForm template.");
          }
          else
          {
          System.out.println("*** The e-document COULD NOT BE packed into the associated eMayorForm template.");
          }
                
        }
        catch( Exception e)
        {
          e.printStackTrace();
          actionForwardName = "showErrorGlobal";
        }    
    }
    else
    {
        System.out.println("ServiceAction: A session has expired.");
        // Show the page, which tells the user that the session has expired, and allows
        // the user to login again:
        actionForwardName = "showSessionExpired";    
    }
    return mapping.findForward(actionForwardName);        
  } // showDocument 
  

  

  
  
  /**
   *  An execution method of this DispatchAction
   *  It is called by the eMayorForms applet, when it posts an e-document. 
   */ 
   public ActionForward processPostedDocument( ActionMapping mapping,
                                               ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response )
   {
     response.setHeader("Pragma", "No-cache");
     response.setHeader("Cache-Control", "no-cache");
     response.setDateHeader("Expires", 1);

     ServiceForm serviceForm = (ServiceForm)form;
     
     System.out.println(" ");
     System.out.println("--------------------------------ServiceAction.processPostedDocument(). Passed parameter names:");
     
     String actionForwardName = "showServiceError";
     if( this.getIsUserAuthenticated( request, request.getSession() ) )
     {
        actionForwardName = "requestdocumentFailure";
        try
        {
        
          Enumeration namesEnumeration = request.getParameterNames();
          int count = 0;
          String languageParameterValue = null;
          String postedRequestDocument = null;
          while( namesEnumeration.hasMoreElements() )
          {
            String parameterName = namesEnumeration.nextElement().toString();
            String parameterValue = request.getParameter(parameterName);
            if( parameterName.equals("language") ) // Take over the language, if its present:
            {
              languageParameterValue = parameterValue; // Take over the new language in the initialize() call below.
              System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
            }
            else
            if( parameterName.equals("RequestDocument") )
            {
              // Note: the RequestDocument parameter already has been
              //       UTF8 urldecoded by the Struts components, so take it as it is:
              postedRequestDocument = parameterValue;
              
              System.out.println("> name= $" + parameterName );
              System.out.println("> with [Struts-] url decoded document. <content skipped here> ");
              //System.out.println(postedRequestDocument);
            }
            else
            {
              System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
            }
            count++;  
          }
          System.out.println(">" + count + " parameters.");

          // postedRequestDocument must exist, otherwise its a failure.
          if( postedRequestDocument != null )
          {

            /* Debug info:
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("The received e-document from the client is: ");
            System.out.println(postedRequestDocument);
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            */
          
            //TextFrame f = new TextFrame("XML received from client applet in processPostedDocument()",
            //                            postedRequestDocument );
          
          
            // The user has filled out and posted a request document.
            // Call the servicehandling for processing it.
            // Get a boolean, which tells if it has succeeded or not,
            // that is, if the bpel task now is underway or not.
         HttpSession session = request.getSession();
         actionForwardName = this.postDocument(session,serviceForm,postedRequestDocument);
          }
          else
          {
            actionForwardName = "requestdocumentFailure";
            System.out.println("*** ServiceAction.processPostedDocument(): Null Document received. Forwarding to: " +
                               actionForwardName );
          }
        }
        catch( Exception e )
        {
          actionForwardName = "requestdocumentFailure";
          System.out.println("*** ServiceAction.processPostedDocument(): Exception thrown. Forwarding to: " +
                             actionForwardName );
          e.printStackTrace();
        }            
     }
     else
     {
         System.out.println("ServiceAction: A session has expired.");
         // Show the page, which tells the user that the session has expired, and allows
         // the user to login again:
         actionForwardName = "showSessionExpired";    
     }          
     return mapping.findForward( actionForwardName );
   } // processPostedDocument 
  
  
  

   
   
   
   

    
    

  /** 
   *  Call the SH.
   */ 
   private boolean getIsRequestResponseAvailable( final HttpSession session,
   		                                          final ServiceForm serviceForm )
   {
     System.out.println("ServiceAction.getIsRequestResponseAvailable() called.");
   	 boolean gotResponse = false;
   	 try
	 {
   	   // The code here actually comes from the sh test GetInputDataProcessor
       String asid = (String) session.getAttribute("ASID");
       String ssid = (String) session.getAttribute("SSID");
       String role = (String) session.getAttribute("ROLE");
       UserTaskServiceClient userTaskServiceClient = new UserTaskServiceClient();
       Task task = userTaskServiceClient.lookupTask(asid, ssid);
       if( task != null ) // otherwise return false 
       {
         System.out.println("getIsRequestResponseAvailable(): SUCCESS - got the task");
         // Put the task and related data into the ServiceForm,
         // so it is available for later method calls:
         ServiceTaskInformation taskInfo = new ServiceTaskInformation( task );
         serviceForm.setServiceTaskInformation( taskInfo );
         
         // Debug file write:
         // Utilities.DebugFileWriteTo(task.getXMLDocument(),"webtier_got_from_SH_Task.xml");

         //TextFrame f = new TextFrame("XML received from the ServiceHandling in getIsRequestResponseAvailable()",
         //                            task.getXMLDocument() );
         
         /* Debug info:
         System.out.println(" ");
         System.out.println(" ");
         System.out.println(" ");
         System.out.println("************** Content of the available task *************");
         System.out.println("task.getXMLDocument():");
         System.out.println(task.getXMLDocument());
         System.out.println(" ");                           
         System.out.println(" ");
         System.out.println(" ");
         System.out.println(" ");
         */
         
         gotResponse = true; // return positive result
       }
       else
       {
        System.out.println("getIsRequestResponseAvailable(): The task isn't available yet.");       
       }
	 }
   	 catch( Exception e )
	 {
   	 	e.printStackTrace();
	 }
     return gotResponse;    	   	
   } // getIsRequestResponseAvailable
  

   
   
   
   
   
   
 /** 
  *  Call the SH.
  */ 
  private boolean sendServiceRequestToServiceHandling( final Municipality municipality,
                                                       final MunicipalityService service,
					   								   final HttpSession session )
  {
    boolean success = true;
  	System.out.println("---------------ServiceAction: sendServiceRequestToServiceHandling() starts.");
  	
  	String serviceID = service.getServiceIdentifier(); // The ID required for the ServiceHandling
    String asid = (String)session.getAttribute("ASID");
    String role = (String)session.getAttribute("ROLE");
    
    System.out.println( "ASID= " + asid );
    System.out.println( "ROLE= " + role );
    
    System.out.println( "Starting service with service ID= " + serviceID );
    
    try 
	{
      ServiceLocator serviceLocator = ServiceLocator.getInstance();
      AccessManagerLocal access = serviceLocator.getAccessManager();
      String ssid = access.startService(asid, serviceID);
      
      System.out.println("Got SSID= " + ssid );

      session.setAttribute("SSID", ssid);
      access.remove();	
	} 
    catch( Exception ex ) 
	{
      // Handled by the calling method. False is returned by this method.
      //ex.printStackTrace();
      System.out.println("*** The ServiceHandling was not able to start that service.");
      success = false;
    }    
    System.out.println("ServiceAction: sendServiceRequestToServiceHandling() has ended.");
    return success;
  } // sendServiceRequestToServiceHandling
  
  

   
  
  
  /**
   *  Contains the code from the ServiceHandling's ListMyTasksProcessor.
   *  
   *  Call the SH.
   * 
   */ 
   private void requestRepositoryDocumentsFromServiceHandling( final Repository repository,
   		                                                       final ServiceForm serviceForm,
															   final HttpSession session)
   {
     System.out.println("----------------ServiceAction: requestRepositoryDocumentsFromServiceHandling() starts.");
     try 
	 {
       String asid = (String) session.getAttribute("ASID");
       String role = (String) session.getAttribute("ROLE");
       Task[] tasks = null;
       if( role.equalsIgnoreCase("citizen") ) 
       {
         System.out.println("Repository: for a citizen");
         UserTaskServiceClient client = new UserTaskServiceClient();
         tasks = client.getMyTasks(asid);
       } 
       else
       {
         System.out.println("Repository: for a civil servant");
         CivilServantTaskServiceClient client = new CivilServantTaskServiceClient();
         tasks = client.getMyTasks(asid);
       }
       
       // Not really good idea, but needed from the SH, it
       // changes the content of a kernel object, and couples to the
       // speficic documents:
       tasks = Utils.setTypeToTasks(tasks);
       
       
       System.out.println("ServiceAction: Collecting document/task types:");
              
       // Add the tasks to the repository.
       for (int i = 0; i < tasks.length; i++) 
       {
         repository.addTask( tasks[i],serviceForm );
       }
       // That's it.
     } 
     catch( Exception ex ) 
	 {
       ex.printStackTrace();
     }
     System.out.println("ServiceAction: requestRepositoryDocumentsFromServiceHandling() has ended.");
   } // requestRepositoryDocumentsFromServiceHandling
   
   

   
   
   
   /**
    *  Check if user credentials are available
    */
    private boolean getIsUserAuthenticated( final HttpServletRequest request,
    		                                final HttpSession session )
    {
      // Check if an ID for an AccessSession exists in this session:
      boolean isAuthenticated = ( session.getAttribute("ASID") != null );
      return isAuthenticated;
    }   

    
    
  /**
   *  User Authentication and Login.
   *  Uses code from the ServiceHandling's LoginProcessor
   *  Gets ASID and user ROLE if successfull.
   */
   private void doAuthenticateUser( final HttpServletRequest request,
   		                            final HttpSession session )
   {
    // Check if an ID for an AccessSession exists in this session:
    boolean isAuthenticated = ( session.getAttribute("ASID") != null );
    if( !isAuthenticated )
    {
      try 
      {
        System.out.println("ServiceAction.doAuthenticateUser(): Initializing the SH.");
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        AccessManagerLocal access = serviceLocator.getAccessManager();
        String asid = access.createAccessSession();
        System.out.println("got asid= " + asid);
        // This code is taken from the Login processor:
        System.out.println("ServiceAction.doAuthenticateUser(): Trying to authenticate the user.");
        X509Certificate[] certificates = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
        if( certificates != null )
        {
          System.out.println("ServiceAction.doAuthenticateUser(): Certificates.length = " + certificates.length);
          for( int i = 0; i < certificates.length; i++ )
            System.out.println("ServiceAction.doAuthenticateUser(): >>certificates[" + i + "] = " + certificates[i].toString());
//          Caution: The above call depends from the Default Platform Character Encoding 
        }
        else
        {
          System.out.println("ServiceAction.doAuthenticateUser(): *** Certificates are NULL. Login is not accepted.");
        }
        boolean b = access.startAccessSession( asid, certificates );
        System.out.println("ServiceAction.doAuthenticateUser(): User authenticated: " + b);

        System.out.println("Session: Set the max inactive interval to 900 seconds (15 min)");
        session.setMaxInactiveInterval(900);
        
        // Set the ASID in the session object:
        session.setAttribute("ASID", asid);

        // Debug output:
        System.out.println(">> ");
        System.out.println(">> ");
        System.out.println(">> AUTHENTICATION successful.");
        System.out.println(">> ");
        System.out.println("ASID= " + asid );
        
        if( access.getUserProfile(asid).getPEUserProfile().getUserRole().equalsIgnoreCase("Citizen") )
        {
          session.setAttribute("ROLE", "citizen");
          System.out.println(">> ROLE= citizen");
        } 
        else
        {
          session.setAttribute("ROLE", "civilservant");
          System.out.println(">> ROLE= civilservant");
        }
        System.out.println(">> ");
        System.out.println(">> ");
        access.remove(); // AccessmanagerLocal extends EJBLocalObject, which contains remove()
      } 
      catch( Exception ex)
      {
        ex.printStackTrace();
      }
    } // if not authenticated
    else
    {
      System.out.println("ServiceAction.doAuthenticateUser(): User already has been authenticated.");
    }
   } // doAuthenticateUser
  


 
   
   
   /**
    * 
    *  Called when the eMayorForms applet has posted a document to the webtier.
    *  -> forward this to the SH. 
    *     returns true, if the SH could process it.
    * 
    *   This method performs the work of the following ServiceHandling processors:
    *   - PostTaskAndWaitProcessor
    *   - RCSDataCompleteProcessor
    *   - RCSPostSignRequestProcessor
    * 
    */ 
    private String postDocument( final HttpSession session,
                                 final ServiceForm serviceForm,
                                 final String xmlDocumentPostedByClient )
    {
      System.out.println("postDocument() called.");
      // Dieser Code stammt im wesentlichen vom ServiceHandling 
      // PostTaskAndWaitProcessor und RCSPostSignRequestProcessor:     
      String actionForwardName = "requestdocumentFailure";
      try 
      {
        String asid = (String) session.getAttribute("ASID");
        String ssid = (String) session.getAttribute("SSID");
        String role = (String) session.getAttribute("ROLE");
        
        System.out.println("ServiceAction.postDocument(): got asid: " + asid);
        System.out.println("ServiceAction.postDocument(): got ssid: " + ssid);
        System.out.println("ServiceAction.postDocument(): got role: " + role);

        // Debug file write:
        // Utilities.DebugFileWriteTo(xmlDocumentPostedByClient,"webtier_passed_to_ServiceHandling.xml");

        
        // Store the xml document received from the client on the serviceform:
        serviceForm.setXMLDocument( xmlDocumentPostedByClient );

        // The associated SH task has been stored in the serviceForm 
        // in the method getIsRequestResponseAvailable(). Retrieve it from there:
        Task associatedTask = serviceForm.getTask();        
        if( associatedTask != null )
        {
          associatedTask.setXMLDocument( xmlDocumentPostedByClient );
          associatedTask.setStatus("YES");
        
          InputDataCollector collector = new InputDataCollector();
          collector.postInputData(associatedTask, asid, ssid);

          session.removeAttribute("SSID");
                     
          // Signalize the serviceForm, that the post action has succeeded.
          // When the applet redirects the program control to
          // the method completeDocumentPostAction() below, this flag
          // controls the next pages the user will see:
          serviceForm.getServiceTaskInformation().setIsRunningSuccessfully(true);
          serviceForm.getServiceTaskInformation().setTask(null); // clear that
          
          // This response is not processed by the applet. It just will display
          // "Please wait", but call the redirection page after this, which has
          // been passed to it through the applet parameter tags and will call
          // the method completeDocumentPostAction() below.
          String waitRedirectURL = "service.do?do=completeDocumentPostAction&caller=postDocument";
          serviceForm.setWaitPageRedirectURL(waitRedirectURL);
          actionForwardName = "postAcknowledgement";
        
        }
        else
        {
         String errorMessage = "Unable to post the document.";
         serviceForm.getServiceTaskInformation().setIsRunningSuccessfully(false);
         serviceForm.getServiceTaskInformation().setErrorInformation(errorMessage);
         // This is an internal error (fatal), shoudln't occure:
         actionForwardName = "requestdocumentFailure";
         System.out.println("ServiceAction.postDocument(): Internal error: associatedTask from serviceForm was null. Forwarding to: " +
                            actionForwardName );        
        }
      } 
      catch (Exception utex)
      {
        String errorMessage = "Unable to post the document.";
        serviceForm.getServiceTaskInformation().setIsRunningSuccessfully(false);
        serviceForm.getServiceTaskInformation().setErrorInformation(errorMessage);
        utex.printStackTrace();
        actionForwardName = "requestdocumentFailure";
        System.out.println("ServiceAction.postDocument(): postDocument failure. Forwarding to: " +
                          actionForwardName );
      }     
      System.out.println("ServiceAction.postDocument() has ended. Forwarding to: " + actionForwardName );
      return actionForwardName;
    } // postDocument
   
   

    
    
    public ActionForward completeDocumentPostAction( ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response )
    {
      response.setHeader("Pragma", "No-cache");
      response.setHeader("Cache-Control", "no-cache");
      response.setDateHeader("Expires", 1);

      ServiceForm serviceForm = (ServiceForm)form;
      HttpSession session = request.getSession();
         
      String actionForwardName = "showServiceError";
      if( this.getIsUserAuthenticated( request, request.getSession() ) )
      {
        // Switch depending on the success of the prior post action:
        boolean success = serviceForm.getServiceTaskInformation().getIsRunningSuccessfully();
        actionForwardName = (success) ? "requestdocumentAcknowledgement" : "requestdocumentFailure";
        
        System.out.println("ServiceAction.completeDocumentPostAction(): Forwarding to: " + actionForwardName );      
      }
      else
      {
          System.out.println("ServiceAction: A session has expired.");
          // Show the page, which tells the user that the session has expired, and allows
          // the user to login again:
          actionForwardName = "showSessionExpired";    
      }
      return mapping.findForward( actionForwardName );
    } // completeDocumentPostAction

    
    
    
    /**
     *  Logout the user (CS or CT).
     * 
     *  Execution method of this DispatchAction
     *  Completes the processing of an e-document by the civil servant.
     *  Called by an eMayorapplet. 
     */    
     public ActionForward logout( ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response )
     {     
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 1);

        ServiceForm serviceForm = (ServiceForm)form;

        System.out.println(" ");
        System.out.println("--------------------------------ServiceAction.logout(). Passed parameter names:");

        String actionForwardName = "showServiceError";
        if( this.getIsUserAuthenticated( request, request.getSession() ) )
        {
            HttpSession session = request.getSession(false);
            String asid = (String)session.getAttribute("ASID");
            String ssid = (String)session.getAttribute("SSID");

            System.out.println("LOGOUT: got ASID= " + asid);
            try
            {        
              ServiceLocator serviceLocator = ServiceLocator.getInstance();
              AccessManagerLocal access = serviceLocator.getAccessManager();
              boolean sessionStopped = access.stopAccessSession(asid);
              if( sessionStopped )
              {
                System.out.println("LOGOUT: Access session stopped successfully.");
              }
              else
              {
                System.out.println("*** LOGOUT: Access session could not be stopped...");
              }
              System.out.println("LOGOUT: Trying to remove access references...");
              // Remove all access references:
              access.remove();
              System.out.println("LOGOUT: done...");
            }
            catch( Exception e )
            {
              System.out.println("*** LOGOUT: Failure:");
              e.printStackTrace();
            }
            finally
            {
              // Invalidate the session in any case:
              session.invalidate();
              System.out.println("LOGOUT: The current session has ben invalidated.");                  
            }
            actionForwardName = "municipalityList";               
        }
        else
        {
            System.out.println("Logout: Session already had expired...");
            // Invalidate the session in any case:
            HttpSession session = request.getSession(false);
            if( session != null )
            {
              session.invalidate();
              System.out.println("LOGOUT: The current session has ben invalidated.");                          
            }
            actionForwardName = "municipalityList";    
        }        
        return mapping.findForward( actionForwardName );
     } // logout    
    

     
     
       
    /**
     *  Completes the running task.
     *  Called by the eMayor client applet from the citizen, after
     *  the citizen has viewed it and clicked the "Done" button.
     * 
     *  Execution method of this DispatchAction
     *  Completes the processing of an e-document by the civil servant.
     *  Called by an eMayorapplet. 
     */    
     public ActionForward processDocumentViewedByCitizen( ActionMapping mapping,
                                                          ActionForm form,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response )
     {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 1);

        ServiceForm serviceForm = (ServiceForm)form;
        HttpSession session = request.getSession();

        System.out.println(" ");
        System.out.println("--------------------------------ServiceAction.processDocumentViewedByCitizen(). Passed parameter names:");

        String actionForwardName = "showServiceError";
        if( this.getIsUserAuthenticated( request, request.getSession() ) )
        {
            Enumeration namesEnumeration = request.getParameterNames();
            int count = 0;
            String languageParameterValue = null;
            String postedRequestDocument = null;
            while( namesEnumeration.hasMoreElements() )
            {
              String parameterName = namesEnumeration.nextElement().toString();
              String parameterValue = request.getParameter(parameterName);
              if( parameterName.equals("language") ) // Take over the language, if its present:
              {
                languageParameterValue = parameterValue; // Take over the new language in the initialize() call below.
                System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
              }
              else
              if( parameterName.equals("RequestDocument") )
              {
                   // Note: the RequestDocument parameter already has been
                   //       UTF8 urldecoded by the Struts components, so take it as it is:
                   postedRequestDocument = parameterValue;
                   
                   System.out.println("> name= $" + parameterName );
                   System.out.println("> with [Struts-] url decoded document: ");
                   System.out.println(postedRequestDocument);
              }
              else
              {
                   System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
              }         
              System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
              count++;  
            }
            System.out.println(">" + count + " parameters.");

            String asid = (String) session.getAttribute("ASID");
            String ssid = (String) session.getAttribute("SSID");

            System.out.println("processDocumentViewedByCitizen: Trying to complete task");
            try
            {        
               Task associatedTask = serviceForm.getTask();          
               CivilServantTaskServiceClient utm = new CivilServantTaskServiceClient();
               utm.completeTask(asid, associatedTask);
               System.out.println("processDocumentViewedByCitizen: Task completed.");          
            }
            catch( Exception e )
            {
              System.out.println("processDocumentViewedByCitizen: Failure:");
              e.printStackTrace();
            }
            actionForwardName = "municipalityList";       
        }
        else
        {
            System.out.println("ServiceAction: A session has expired.");
            // Show the page, which tells the user that the session has expired, and allows
            // the user to login again:
            actionForwardName = "showSessionExpired";    
        }
        return mapping.findForward( actionForwardName );        
     } // processDocumentViewedByCitizen



     
     


   /**
    *  Contains code of the ServiceHandling's CVHandleTaskProcessor.
    * 
    *  Execution method of this DispatchAction
    *  Completes the processing of an e-document by the civil servant.
    *  Called by an eMayorapplet. 
    */    
    public ActionForward processDocumentProcessedByCivilServant( ActionMapping mapping,
                                                                 ActionForm form,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse response )
    {
       response.setHeader("Pragma", "No-cache");
       response.setHeader("Cache-Control", "no-cache");
       response.setDateHeader("Expires", 1);

       ServiceForm serviceForm = (ServiceForm)form;
       HttpSession session = request.getSession();
       
       System.out.println(" ");
       System.out.println("--------------------------------ServiceAction.processDocumentProcessedByCivilServant(). Passed parameter names:");
       
       String actionForwardName = "showServiceError";
       if( this.getIsUserAuthenticated( request, request.getSession() ) )
       {
        Enumeration namesEnumeration = request.getParameterNames();
        int count = 0;
        String languageParameterValue = null;
        String postedRequestDocument = null;
        while( namesEnumeration.hasMoreElements() )
        {
          String parameterName = namesEnumeration.nextElement().toString();
          String parameterValue = request.getParameter(parameterName);
          if( parameterName.equals("language") ) // Take over the language, if its present:
          {
            languageParameterValue = parameterValue; // Take over the new language in the initialize() call below.
            System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
          }
          else
          if( parameterName.equals("RequestDocument") )
          {
             // Note: the RequestDocument parameter already has been
             //       UTF8 urldecoded by the Struts components, so take it as it is:
             postedRequestDocument = parameterValue;
               
             System.out.println("> name= $" + parameterName + " (value skipped here)" );
             //System.out.println("> with [Struts-] url decoded document: ");
             //System.out.println(postedRequestDocument);
          }
          else
          {
            System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
          }         
          count++;  
        }
        System.out.println(">" + count + " parameters.");

        /* debug: write out the xml document:
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("The xml document posted by the civil servant is:");
        System.out.println(" ");
        System.out.println(postedRequestDocument);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        */
        
        //TextFrame f = new TextFrame("XML received from client applet in processDocumentProcessedByCivilServant()",
        //                            postedRequestDocument );

        
        // Has the civil servant accepted or denied it ?
        String civilServantDecisionParameter = request.getParameter("RequestDocumentParameter");
        // This parameter actually should exist in any case. If it does not,
        // this would means, that the eMayorForm for the CivilServant was made
        // with a submit button instead of accept and deny buttons.
        // In this case, treat it as accept, but write it out:
        boolean civilServantHasAccepted = true;
        if( civilServantDecisionParameter != null )
        {
          civilServantHasAccepted = ( civilServantDecisionParameter.equals("Accept") );
        }
        else
        {
          System.out.println("");
          System.out.println("*** ServiceAction: Uncorrect eMayorForm for CivilServant detected.");
          System.out.println("*** ServiceAction: No accept or deny button parameter received.   ");
          System.out.println("*** ServiceAction: Accepting the post. ");
          System.out.println("*** ServiceAction: Please make sure, the eMayorForm template for");
          System.out.println("*** ServiceAction: Civil servant does have accept and deny buttons.");
          System.out.println("");
        }
        String asid = (String) session.getAttribute("ASID");
        String ssid = (String) session.getAttribute("SSID");

        actionForwardName = "postAcknowledgement";      
        System.out.println("processDocumentProcessedByCivilServant: Trying to complete task");
        try
        {        
           Task associatedTask = serviceForm.getTask();          
           associatedTask.setXMLDocument( postedRequestDocument );          
           if( civilServantHasAccepted )
           {
             // set it to open, unless it was already closed:
             if( !associatedTask.getStatus().equals("closed") )
             {
               associatedTask.setStatus("open");
             }        
             System.out.println("processDocumentProcessedByCivilServant: Status= " +
                                associatedTask.getStatus() );
           }
           else
           {
             associatedTask.setStatus("closed");
             System.out.println("processDocumentProcessedByCivilServant: denied. Status=closed");
           }          
           CivilServantTaskServiceClient utm = new CivilServantTaskServiceClient();
           utm.completeTask(asid, associatedTask);
           System.out.println("processDocumentProcessedByCivilServant: Task completed.");          
        }
        catch( Exception e )
        {
        actionForwardName = "requestdocumentFailure";
          System.out.println("processDocumentProcessedByCivilServant: Failure:");
          e.printStackTrace();
        }       
       }
       else
       {
           System.out.println("ServiceAction: A session has expired.");
           // Show the page, which tells the user that the session has expired, and allows
           // the user to login again:
           actionForwardName = "showSessionExpired";    
       }
       return mapping.findForward( actionForwardName );       
    }   
 
    
    
    
    /**
     *  Execution method of this DispatchAction
     *  Called by an eMayorapplet for getting the eMayorForms properties
     *  file in the current language. 
     */    
     public ActionForward getAppletProperties( ActionMapping mapping,
                                               ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response )
     {
        // No parameters are required here - just return the applet property file
        // for the current language. The ServiceForm can perform this task
        // without further information - it already knows the current language,
        // and it has session scope anyway.
        return mapping.findForward( "getAppletProperties" );
     } // getAppletProperties
    

     
     /**
      *  Execution method of this DispatchAction
      *  Called by an eMayorapplet for getting the enumeration properties
      *  file in the current language. 
      */    
      public ActionForward getEnumerationProperties( ActionMapping mapping,
                                                     ActionForm form,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response )
      {
         // No parameters are required here - just return the applet property file
         // for the current language. The ServiceForm can perform this task
         // without further information - it already knows the current language,
         // and it has session scope anyway.
         return mapping.findForward( "getEnumerationProperties" );
      } // getEnumerationProperties

     
 
      
      
} // ServiceAction

