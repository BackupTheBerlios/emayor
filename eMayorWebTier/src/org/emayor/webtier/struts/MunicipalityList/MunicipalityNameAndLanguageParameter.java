package org.emayor.webtier.struts.MunicipalityList;


import java.util.*;

import org.emayor.webtier.municipalities.MunicipalitiesManager;
import org.emayor.webtier.municipalities.Municipality;
import org.emayor.webtier.shared.TextResourceKeys;


public class MunicipalityNameAndLanguageParameter 
{
	private String name;
	private String nameKey;
	private String languageParameter;
	private boolean useLocalLinks = false;
    private String requestURL = null;


	public MunicipalityNameAndLanguageParameter( final String _name,
			                                     final String _nameKey,
                                                 final String _languageParameter,
                                                 final boolean _useLocalLinks,
                                                 final String _requestURL )
	{
	  this.name = _name;
	  this.nameKey = _nameKey;
	  this.languageParameter = _languageParameter;
      this.useLocalLinks = _useLocalLinks;
      this.requestURL = _requestURL;
	}



	public String getNameKeyOfMunicipality()
	{
		return this.nameKey;
    }



	public String getNameOfMunicipality()
	{
     MunicipalitiesManager mm = MunicipalitiesManager.GetInstance();
     Municipality municipality = mm.getMunicipalityByKey(this.nameKey);
     String name = null;
     if( municipality != null )
     {
       name = municipality.getMunicipalityNameForLanguage( this.languageParameter );
     }
     // don't return null, otherwise calling JSP's run into exceptions:
     if( name == null ) 
     {
       System.out.println("*** MunicipalityNameAndLanguageParameter.getNameOfMunicipality(): name of municipality is not defined.");
       name = "undefined";
     }
     return name;
	}



	public HashMap getParameterMap()
	{
	  HashMap map = new HashMap();
	  map.put(TextResourceKeys.MunicipalityNameKeyTag,this.nameKey );
	  map.put("language",this.languageParameter );	  
	  return map;
	}



    public String getAbsoluteURL()
    {
      String absoluteURL = null;
      if( this.useLocalLinks )
      {      
        absoluteURL = this.requestURL + 
                      "?municipalityNameKey=" + 
                      this.nameKey +
                      "&language=" + 
                      this.languageParameter;
        // Example: "http://emayor.aachen.de/eMayor/login.do?municipalityNameKey=Name.Aachen&language=de";
      }
      else // use global links
      {
        MunicipalitiesManager mm = MunicipalitiesManager.GetInstance();
        Municipality municipality = mm.getMunicipalityByKey(this.nameKey);
        if( municipality != null )
        {
          absoluteURL = municipality.getMunicipalityURL();
        }
        // don't return null, otherwise calling JSP's run into exceptions:
        if( absoluteURL == null ) 
        {
          System.out.println("*** MunicipalityNameAndLanguageParameter.getAbsoluteURL(): name of municipality is not defined.");
          absoluteURL = "undefined link";
        }      
      }
      
      System.out.println("MunicipalityNameAndLanguageParameter returns absolute URL= " +
                         absoluteURL );
      
      return absoluteURL;
    }
    

} // MunicipalityNameAndLanguageParameter



