package org.emayor.webtier.struts.Authentication;

import javax.servlet.http.HttpSession;

import org.emayor.webtier.municipalities.*;
import org.emayor.webtier.shared.*;




public class AuthenticationForm extends ExtendedActionForm 
{
  private String municipalityNameKey = "undefined_key";

  
  public AuthenticationForm()
  {
    super();
    System.out.println("%%");
    System.out.println("%% AuthenticationForm INSTANCE CREATED.");
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
       System.out.println("*** AuthenticationForm.getNameOfMunicipality(): name of municipality is not defined.");
       name = "undefined";
     }
     return name;
     
   }
   
   

   public String getHelpTopicKey()
   {
   return TextResourceKeys.HelpTopic_MunicipalityKey;
   }
  
  
   public String getMunicipalityURL()
   {
     StringBuffer municipalityURL = new StringBuffer();
     municipalityURL.append("municipality.do?do=selectMunicipality");
     municipalityURL.append("&municipalityNameKey=");
     municipalityURL.append(this.municipalityNameKey);
     municipalityURL.append("&language=");
     municipalityURL.append(super.getLanguage());     
     return municipalityURL.toString();
   }
   
  
  
} // AuthenticationForm

