package org.emayor.eMayorWebTier.struts.FormTest;


import java.util.*;

import org.emayor.eMayorWebTier.Utilities.*;


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
          
         String nameForRole1 = eDocumentNameInFileSystem;
         String nameForRole2 = eDocumentNameInFileSystem + "CivilServant";
         String nameForRole3 = eDocumentNameInFileSystem + "ReadOnly";
 
         if( templateName.equals(nameForRole1) )
         {
           citizenTemplateName = templateName;
         } else
         if( templateName.equals(nameForRole2) )
         {
           civilServantTemplateName = templateName;
         } else
         if( templateName.equals(nameForRole3) )
         {
           readOnlyTemplateName = templateName;
         }         
       } // while

       /* Debug test output to see the mapping:
       System.out.println("*********************** citizenTemplateName=" + citizenTemplateName );
       System.out.println("*********************** civilServantTemplateName=" + civilServantTemplateName );
       System.out.println("*********************** readOnlyTemplateName=" + readOnlyTemplateName );
       System.out.println(" ");
       */
       
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
   
 
   
   
   
   /**
    *  For the ProcessStep Applet, three methods (called by JSP's) are required here:
    *  getProcessStepTextList(), processStepDescriptionList() and getProcessStepIndex()
    * 
    *  This method returns a ";" separated list of texts for the
    *  process step list view.
    * 
    *  The texts are read from the textresource and returned in the
    *  selected natural language of the client.
    * 
    *  @return comma separated list of texts for each step.
    */ 
    public String getProcessStepTextList()
    {
      StringBuffer textList = new StringBuffer();
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_MunicipalityIndex) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_FillOutForm) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_SignAndSubmit) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_Acknowledgment) );
      return textList.toString();
    }
    

   /**
    *  For the ProcessStep Applet, three methods (called by JSP's) are required here:
    *  getProcessStepTextList(), processStepDescriptionList() and getProcessStepIndex()
    * 
    *  This method returns a ";" separated list of descriptions for the
    *  process step list view.
    * 
    *  The texts are read from the textresource and returned in the
    *  selected natural language of the client.
    * 
    *  @return comma separated list of texts for each step.
    */ 
    public String getProcessStepDescriptionList()
    {
      StringBuffer textList = new StringBuffer();
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_MunicipalityIndex_Description) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_FillOutForm_Description) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_SignAndSubmit_Description) );
      textList.append( ";" );
      textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_Acknowledgment_Description) );
      return textList.toString();
    }
    
    
   /**
    *  For the ProcessStep Applet, three methods (called by JSP's) are required here:
    *  getProcessStepTextList(), processStepDescriptionList() and getProcessStepIndex()
    * 
    *  This method returns the index for the
    *  current process step. The associated entry shall be highlighted
    *  and means the current step in the list. Steps on the left are past
    *  steps, and steps on the right are to be carried out in the future. 
    *  @return index of current step (zero based)
    */ 
    public String getProcessStepIndex()
    {
      return "1"; // The first entry = the login step
    }
   
   
   
   
} // DocumentRequestForm

