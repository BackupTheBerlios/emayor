package org.emayor.webtier.struts.FormTest;

/**
 *  May 20, 2005 created, Joerg Plaz
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.emayor.client.parser.XMLParser;
import org.emayor.client.parser.xml.XML_Node;
import org.emayor.webtier.shared.TextResourceKeys;
import org.emayor.webtier.shared.Utilities;



public class FormTestAction extends DispatchAction
{



   // Use UTF-8 for file read operations
   private static final Charset charset = Charset.forName("UTF-8");


  public ActionForward executeService( ActionMapping mapping,
                                       ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response )
  {
    response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

    System.out.println(" ");
    System.out.println("-------------------------------FormTestAction.executeService()");
    Enumeration namesEnumeration = request.getParameterNames();
    int count = 0;
    String languageParameterValue = null;
    while( namesEnumeration.hasMoreElements() )
    {
      String parameterName = namesEnumeration.nextElement().toString();
      String parameterValue = request.getParameter(parameterName);
      System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
      count++;  
    }
    System.out.println(">" + count + " parameters.");
    
    FormTestForm formTestForm = (FormTestForm)form;    
    // Initialize ( load the eMayorForm from disk, if required: )
    if( formTestForm.getEmayorform() == null )
    {
      this.initializeFormTestService(formTestForm);
    }    

    // Show the eMayorForms client applet and process the template,
    // if we have a templateName parameter -
    // show the index page, if there is no templateName parameter
    String templateName = request.getParameter("templateName");
    String forwardname = "testformtestindex";
    if( templateName == null ) // -> index page
    {
      System.out.println("No templateName parameter found -> Show the index page.");
      // Synchronize the form:
      formTestForm.setTemplateName(null);
    }
    else
    {
      // Synchronize the form:
      formTestForm.setTemplateName(templateName);
      System.out.println("templateName parameter is: " + templateName );
      // Create the eMayorForm for this template and set it in the
      // formTestForm, so that is available for a subsequent
      // getEMayorForm() call from the applet, all done in
      // createEMayorFormFromTemplate():
      FormTestRepository repository = FormTestRepository.GetInstance(false); // false: keep existing instance
      // Split the eMayorFormNameSuffix from the templateName:
      String eMayorFormNameSuffix = "";
      String templateNameWithoutSuffix = templateName;
      if( templateName.endsWith("ReadOnly") ) 
      {
        eMayorFormNameSuffix = "ReadOnly";
        templateNameWithoutSuffix = templateName.substring(0,templateName.length()-8 );
      }
      if( templateName.endsWith("CivilServant") ) 
      {
        eMayorFormNameSuffix = "CivilServant";
        templateNameWithoutSuffix = templateName.substring(0,templateName.length()-12 );
      }      
      String eDocument = repository.getEDocumentForTemplate(templateNameWithoutSuffix);
      if( eDocument != null ) // found
      {
        if( templateName.endsWith("ReadOnly")     ) eMayorFormNameSuffix = "ReadOnly";
        if( templateName.endsWith("CivilServant") ) eMayorFormNameSuffix = "CivilServant";
        this.createEMayorFormFromTemplate( eDocument,
                                           eMayorFormNameSuffix,
                                           formTestForm );      

      
        // Debug file write:
        // Utilities.DebugFileWriteTo(eDocument,"webtier_testservice_got_from_FileSystem.xml");
      
      }
      else
      {
        // Create an errorForm, which informs the user:
        String title = "The web tier was not able to produce the eMayorForm.";
        String message = "For template name (w.o. suffix)= " + templateNameWithoutSuffix;
        String errorForm = this.createErrorForm(title,message);
        // And set this in the form:
        formTestForm.setEmayorform( errorForm );
      }
      forwardname = "testemayorform";
    }    
    System.out.println("FormTestAction.executeService(): forwarding to testformtestindex.");
    return mapping.findForward(forwardname);
  } // execute


  
  

  
  
  /**
   *  Collect xml e-documents and eMayorForm templates
   *  in the formtest directory and set all required data 
   *  in the FormTestForm.
   */
   private void initializeFormTestService( FormTestForm formTestForm )
   {
     FormTestRepository rep = FormTestRepository.GetInstance(true); // force creation of a new instance
   } // initializeEMayorForm
   
   
   
  
  
  
  

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
                                                 FormTestForm formTestForm )
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
       String municipalityNameKey = formTestForm.getMunicipalityNameKey();
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
           String title = formTestForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation1);
           String message = formTestForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation2) +
                            "\n\nThe eMayorForm template file is empty.";
           String errorForm = this.createErrorForm(title,message);
           completeForm.append(errorForm);
         }
       }
       catch( Exception e )
       {
         // The template file does not exist. Use an error form for informing the user:
         String title = formTestForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation1);
         String message = formTestForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation2) +
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
       formTestForm.setEmayorform( completeForm.toString() );
       System.out.println("The complete eMayorForm has been set in the serviceForm.");      
     }
     catch( Exception e )
     {
       	 e.printStackTrace();
       	 success = false;
       	 String title = formTestForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation1);
       	 String message = formTestForm.getTextFromResource(TextResourceKeys.ServiceFailureInformation2) +
		 "\n\n" + e.getMessage();
       	 String errorForm = this.createErrorForm(title,message);
       	 // And set this in the form:
       	 formTestForm.setEmayorform( errorForm );
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
      buf.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
      buf.append("<eMayorForm1 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.emayor.org/eMayorFormsSchema.xsd eMayorFormsSchema.xsd \">");
      buf.append("<Model>");
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
       // Note: the called sendEMayorForm.jsp will get the complete
       //       eMayorForm from the serviceForm. This has been set
       //       earlier, before the applet page has been sent to the client.
       //       See: setEmayorFormFromTemplate()
       return mapping.findForward("testsendemayorform");
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
        System.out.println(" ");
        System.out.println(" FormTestAction: processPostedDocument() called.");
        System.out.println(" Returning acknowledgment message.");
        System.out.println(" ");
      
        return mapping.findForward("testpostAcknowledgement");      
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
        return mapping.findForward( "testgetappletproperties" );
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
         return mapping.findForward( "testgetEnumerationProperties" );
      } // getEnumerationProperties
    
  
} // FormTestAction

