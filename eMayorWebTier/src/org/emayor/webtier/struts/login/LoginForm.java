package org.emayor.webtier.struts.login;

import javax.servlet.http.HttpSession;

import org.emayor.webtier.municipalities.*;
import org.emayor.webtier.shared.*;


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
   *  Returns the URL string for the next page, which is
   *  the authentication page.
   */ 
   public String getAuthenticationURL()
   {
     return "/eMayor/authenticateUser.do?municipalityNameKey=" +
            this.getMunicipalityNameKey() +
            "&language=" + super.getLanguage();
   }
   
   
   
} // LoginForm

