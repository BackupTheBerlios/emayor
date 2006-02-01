package org.emayor.eMayorWebTier.struts.MunicipalityList;

/**
 *  Holds the municipalities used on this server.
 *  Typically only one municipality will be present,
 *  however for test runs of eMayor, one can run
 *  several municipality services on one server.
 *  
 *
 *  May 12, 2005 created, Joerg Plaz
 * 
 */


import javax.servlet.http.HttpSession;

import org.emayor.eMayorWebTier.Utilities.*;
import org.emayor.eMayorWebTier.municipalities.MunicipalitiesManager;
import org.emayor.eMayorWebTier.municipalities.Municipality;


public class MunicipalityListForm extends ExtendedActionForm
{

  private Municipality[] municipalities;
  
  private boolean useLocalLinks = false;
  private String requestURL = null;
    
  
  
  public MunicipalityListForm()
  {
  	super(); // important
    this.municipalities = MunicipalitiesManager.GetInstance().getMunicipalities();
  	System.out.println("%%");
  	System.out.println("%% MunicipalityListForm INSTANCE CREATED.");
  	System.out.println("%%");
  } // constructor
  
  

  
  
 /**
  *  Set the language from the passed languageParameterValue and store it in the session object
  *  or read it from the session object, if languageParameterValue is null.
  */
  public void initialize( final HttpSession session, 
                          final String languageParameterValue,
                          boolean _useLocalLinks,
                          String _requestURL )
  {
  	super.initialize(session,languageParameterValue);
    this.useLocalLinks = _useLocalLinks;
    this.requestURL = _requestURL;
  } // initialize

  
  
  
  
  
  public Municipality[] getMunicipalities()
  {
    return this.municipalities;
  }
  
  
 /**
  *  Called by the municipality jsp for creating html links.
  */ 
  public MunicipalityNameAndLanguageParameter[] getLinkParameters()
  {
  	MunicipalityNameAndLanguageParameter[] parms = new MunicipalityNameAndLanguageParameter[this.municipalities.length];
  	for( int i=0; i < parms.length; i++ )
  	{
  	  String municipalityNameKey=this.municipalities[i].getMunicipalityNameKey();
  	  String municipalityName = this.municipalities[i].getMunicipalityNameForLanguage( super.getLanguage() );
  	  parms[i] = new MunicipalityNameAndLanguageParameter(
                                 municipalityName,
                                 this.municipalities[i].getMunicipalityNameKey(),
                                 super.getLanguage(),
                                 this.useLocalLinks,
                                 this.requestURL );
    }
  	return parms;
  }
  

  
  public String getHelpTopicKey()
  {
  	return TextResourceKeys.HelpTopic_MunicipalityListKey;
  }
  
  
  
  

} // MunicipalityListForm

