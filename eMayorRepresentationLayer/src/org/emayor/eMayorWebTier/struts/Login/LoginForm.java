package org.emayor.eMayorWebTier.struts.Login;

import javax.servlet.http.HttpSession;

import org.emayor.eMayorWebTier.Utilities.*;
import org.emayor.eMayorWebTier.municipalities.*;


public class LoginForm extends ExtendedActionForm 
{

  // This is used by the LoginAction for detecting calls with invalid
  // municipality name parameters. Null is not used here, because
  // JSP's request the municipality key name too and would go byebye
  // with a nullpointer exception that way.
  public static final String MunicipalityNameIsUndefined="undefined";

  
  private String municipalityNameKey = null;

  
  public LoginForm()
  {
  	super();
  	System.out.println("%%");
  	System.out.println("%% LoginForm INSTANCE CREATED.");
  	System.out.println("%%");
  }
 
  
  
  
  /**  
   * Called by the associated action, which passes the
   * municipality which it has resolved from the http request.
   */ 
   public void initializeAttibutes( final HttpSession session,
                                    final String languageParameterValue,
                                    final String _municipalityNameKey )
   {
     super.initialize(session,languageParameterValue); // language initialization
     this.municipalityNameKey = _municipalityNameKey;
   } // initializeAttibutes

   
   
   public String getMunicipalityNameKey()
   {
     return this.municipalityNameKey;
   }
   
   
   public String getNameOfMunicipality()
   {	
     MunicipalitiesManager mm = MunicipalitiesManager.GetInstance();
     Municipality municipality = mm.getMunicipalityByKey(this.municipalityNameKey);     
     String name = null;
     if( municipality != null )
     {
       name = municipality.getMunicipalityNameForLanguage( super.getLanguage() );
     }
     // don't return null, otherwise calling JSP's run into exceptions:
     if( name == null ) 
     {
       System.out.println("*** LoginForm.getNameOfMunicipality(): name of municipality is not defined.");
       name = MunicipalityNameIsUndefined;
     }
     return name;
     
   }
   
   

   public String getHelpTopicKey()
   {
   	return TextResourceKeys.HelpTopic_MunicipalityKey;
   }
  
  
  
   public String getInformationAboutFilesWithIncorrectVersions()
   {
    ProjectFileVersionInformation fileInformation = ProjectFileVersionInformation.GetInstance();
    return fileInformation.getFailureDescriptionText();   
   }
  

   
   
  /** 
   *  Called by the LoginButtonApplet.
   *  Returns the information in correct language, that
   *  the client computer has not a required version of the Java Plugin.
   */
   public String getOutDatedPluginMessage()
   {
     return super.getTextFromResource(TextResourceKeys.JavaPluginUpdate);
   }
   
   
   public String getJavaPluginIsUpToDateMessage()
   {
    return super.getTextFromResource(TextResourceKeys.JavaPluginIsUpToDate);   
   }

   
  /**
   *  Called by the LoginButtonApplet.
   *  Returns the URL string for the next page.
   */    
   public String getAuthenticationURL()
   {
     return "/eMayor2.0/authenticateUser.do?municipalityNameKey=" +
            this.getMunicipalityNameKey() +
            "&language=" + super.getLanguage();
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
     textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_Login) );
     textList.append( ";" );
     textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_Authentication) );
     textList.append( ";" );
     textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_SoftwareCheck) );
     textList.append( ";" );
     textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_MunicipalityIndex) );
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
     textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_Login_Description) );
     textList.append( ";" );
     textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_Authentication_Description) );
     textList.append( ";" );
     textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_SoftwareCheck_Description) );
     textList.append( ";" );
     textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_MunicipalityIndex_Description) );
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
     return "0"; // The first entry = the login step
   }

   
   
   
} // LoginForm

