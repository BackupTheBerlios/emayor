package org.emayor.webtier.struts.FormTest;


import java.util.*;

import javax.servlet.http.HttpSession;

import org.emayor.webtier.municipalities.MunicipalitiesManager;
import org.emayor.webtier.municipalities.Municipality;
import org.emayor.webtier.shared.*;


public class FormTestForm extends ExtendedActionForm
{

  private String testEMayorForm = ""; // default - important for expectedAppletHeight()
 
  private String templateName = null;
  
  
  public FormTestForm()
  {
    super();    
  }
  
  
  public void setEmayorform( String eMayorForm )
  {
    this.testEMayorForm = eMayorForm;
  }
  
  
  public String getEmayorform()
  {
    return this.testEMayorForm;    
  }
  
  
  public String getMunicipalityNameKey()
  {
    return FormTestRepository.MunicipalityNameKey;   
  }


  
  /**
   *  Returns the eMayorForms applet property file in the current language.
   */ 
   public String getAppletPropertyFile()
   {
     FormTestRepository formTestRepository = FormTestRepository.GetInstance();
     return formTestRepository.getAppletPropertyFileForLanguage( super.getLanguage() );
   }

   
   /**
    *  Returns the enumeration property file.
    *  This is called by a jsp, envoked by the eMayor client applet over the net.
    */ 
    public String getEnumerationProperties()
    {
      FormTestRepository formTestRepository = FormTestRepository.GetInstance();
      return formTestRepository.getEnumerationPropertiesFile();
    }
    

    
    
  /**
   *   Called by the testformtesinindex.jsp
   */
   public EDocumentParameters[] getEdocumentparameters()
   {
     FormTestRepository formTestRepository = FormTestRepository.GetInstance();
       
     Properties eDocumentFileNames = formTestRepository.getEDocumentFileNames();
     Properties eDocuments = formTestRepository.getEDocuments();
     Properties eMayorFormTemplates = formTestRepository.getEMayorFormTemplates();
     
     Vector eDocumentParameters = new Vector();
     Enumeration eDocumentKeys = eDocuments.keys();
     while( eDocumentKeys.hasMoreElements() )
     {
       String eDocumentName = eDocumentKeys.nextElement().toString();
       // Get the associated file name:
       String eDocFileName = eDocumentFileNames.getProperty(eDocumentName);
       // Now collect available associated eMayorForm templates:
       String citizenTemplateName = null;
       String civilServantTemplateName = null;
       String readOnlyTemplateName = null;
       Enumeration templateKeys = eMayorFormTemplates.keys();
       while( templateKeys.hasMoreElements() )
       {
         String templateName = templateKeys.nextElement().toString();
         // The templateName without ending can differ from the
         // eDocumentName by characters not allowed by the filesystem.
         // The templateNames have been modified to be valid filesystem
         // names using the method Utilities.MakeNameValidForFileSystem(),
         // so apply this method to eDocumentName before comparing:
         String eDocumentNameInFileSystem = Utilities.MakeNameValidForFileSystem(eDocumentName);         
         if( templateName.startsWith(eDocumentNameInFileSystem) )
         {
            // It is a eMayorForms template associated to one of the 3 roles:
            // either ReadOnly (Role 3) or CivilServant (Role 2) or
            // without suffix, its a request document (Role 1)
            //
            if( templateName.endsWith("ReadOnly") ) // Role 3
            {
              readOnlyTemplateName = templateName;
            }
            else
            if( templateName.endsWith("CivilServant") ) // Role 2
            {
              civilServantTemplateName = templateName;
            }
            else // Role 1
            {
              citizenTemplateName = templateName;
            }          
         }
       }
       EDocumentParameters parms = new EDocumentParameters( eDocFileName,
                                                            eDocumentName,
                                                            citizenTemplateName,
                                                            civilServantTemplateName,
                                                            readOnlyTemplateName );
       eDocumentParameters.addElement( parms );
     } // while
     EDocumentParameters[] parmArray = new EDocumentParameters[ eDocumentParameters.size() ];
     eDocumentParameters.copyInto(parmArray);
     return parmArray;
   }
   

   
   
   public void setTemplateName( String _templateName )
   {
     this.templateName = _templateName;
   }

   
      
  /**
   *  Called by the testemayorform.jsp for restarting the testEmayor site
   *  after the user has changed the language.
   */ 
   public HashMap getServiceParametersWithoutLanguage()
   {
     HashMap serviceParameters = new HashMap();
     serviceParameters.put( "templateName",this.templateName );
     return serviceParameters;
   }
   

   
   
   
  /**
   *  Returns the expected applet height based on the
   *  member attribute testEMayorForm.
   *  It is called by JSP's carrying an applet directive.
   */ 
   public String getExpectedAppletHeight()
   { 
     int expectedAppletHeight = 1400; // default return value
     
     return String.valueOf(expectedAppletHeight);
   }
   
   
   
} // DocumentRequestForm

