package org.emayor.eMayorWebTier.municipalities;


 /**
  *  Defines a service by a key for the service and 
  *  a key for the municipality. 
  */
import java.io.Serializable;

import org.emayor.eMayorWebTier.Utilities.NamedProperties;


public class MunicipalityService implements Serializable
{
  
	
  // The keys:	
  private String serviceIdentifier; // The ID used to identify the service on calls to the SH.
  private NamedProperties serviceNameProperties; // contain the service name translated in the different languages
  private String municipalityNameKey;  // The key for getting the municipality name in any language
  
  
  
  public MunicipalityService( final String _serviceIdentifier,
                              final NamedProperties _serviceNameProperties,  		                      
                              final String _municipalityNameKey )
  {
    this.serviceIdentifier = _serviceIdentifier;
    this.serviceNameProperties = _serviceNameProperties;
    this.municipalityNameKey = _municipalityNameKey;    
  }

  
 
  
 /**
  *  Return the name of this service in the passed language.
  */ 
  public String getServiceNameForLanguage( String language )
  {
    String nameKey = "service.name." + language;  
    String serviceName = this.serviceNameProperties.getProperties().getProperty(nameKey);
    System.out.println("getServiceNameForLanguage() for service with id= " +
                       this.serviceIdentifier +
                       " for language= " + language +
                       " returns servicename= " + serviceName );    
    return serviceName;
  }
  
 
  
 /**
  *  Returns the ID to identify this service on calls to the SH.
  */ 
  public String getServiceIdentifier()
  {
  	return this.serviceIdentifier;
  }

  
 /**
  *  Returns the key for getting the municipality name in any language.
  */ 
  public String getMunicipalityNameKey()
  {
    return this.municipalityNameKey;
  }
  
  
 /**
  *  Returns the service name in the chosen laguage 
  */
  
  
  
} // MunicipalityService


