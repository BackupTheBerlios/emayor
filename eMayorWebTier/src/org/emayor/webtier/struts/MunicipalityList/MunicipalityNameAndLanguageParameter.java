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
	
	public MunicipalityNameAndLanguageParameter( final String _name,
			                                     final String _nameKey,
                                                 final String _languageParameter )
	{
	  this.name = _name;
	  this.nameKey = _nameKey;
	  this.languageParameter = _languageParameter;
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
	
}



