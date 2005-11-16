package org.emayor.webtier.struts.login;

import javax.servlet.http.HttpSession;

import org.emayor.webtier.municipalities.*;
import org.emayor.webtier.shared.*;


public class LoginForm extends ExtendedActionForm 
{
  private String municipalityNameKey = "undefined_key";

  
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
       name = "undefined";
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
  

   
} // LoginForm

