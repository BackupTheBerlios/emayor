package org.emayor.eMayorWebTier.municipalities;



import java.util.HashMap;

import org.emayor.eMayorWebTier.Utilities.*;



public class MunicipalityServiceParameter 
{

  private String serviceIdentifier;
  private String municipalityNameKey;
  private String language;
  private LanguageProperties languageProperties;  

  
  
  public MunicipalityServiceParameter( final String _serviceIdentifier,
  		                               final String _municipalityNameKey,
									   final String _language )
  {
  	this.serviceIdentifier= _serviceIdentifier;
  	this.municipalityNameKey = _municipalityNameKey;
  	this.language = _language;
  	this.languageProperties = LanguageProperties.getInstance();
  }
									  


  public String getServiceIdentifier()
  {
    return this.serviceIdentifier;
  }

  
  /**
   *  Returns the name of the service in the current language.
   */ 
  public String getNameOfService()
  {
    MunicipalitiesManager mm = MunicipalitiesManager.GetInstance();
    Municipality municipality = mm.getMunicipalityByKey(this.municipalityNameKey);
    MunicipalityService service = municipality.getServiceByIdentifier( this.serviceIdentifier );
    return service.getServiceNameForLanguage( this.language );
  }
  

	
  public HashMap getServiceParameters()
  {
	HashMap serviceParameters = new HashMap();
	serviceParameters.put( TextResourceKeys.ServiceNameKeyTag,this.serviceIdentifier );
	serviceParameters.put( TextResourceKeys.MunicipalityNameKeyTag,this.municipalityNameKey);
	serviceParameters.put("language",this.language);
	return serviceParameters;
  }
	
	
} // MunicipalityServiceParameter


