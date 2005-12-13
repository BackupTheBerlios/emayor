package org.emayor.webtier.municipalities;

/**
 * 
 *  Holds all published UI elements for a municipality.
 *  Typically this is the name of the municipality,
 *  the currently available services and property
 *  files, which are requested by the applet possibly.
 * 
 *  May 12, 2005 created, Joerg Plaz
 * 
 */


import java.io.Serializable;
import java.util.Properties;

import org.emayor.webtier.shared.NamedProperties;


public class Municipality implements Serializable
{

	    
  // The key for the name of this municipality.
  private String municipalityNameKey = "undefinedKey";
    
  // Keys for the municipality.properties file properties:
  public static final String MunicipalitySessionTimeoutForCitizen="municipality.SessionTimeoutInSecondsForCitizen";
  public static final String MunicipalitySessionTimeoutForCivilServant="municipality.SessionTimeoutInSecondsForCivilServant";
  
  
  // serviceNames are written out on the client 
  private MunicipalityService[] services;
  
  private NamedProperties municipalityProperties;
 
  // appletProperties contains the content of the eMayorForms_XX.properties
  // file, where XX is the actual language set in the current session.
  // key is the language and value is the content of the associated property file as String.
  private Properties appletProperties;

  // enumerationProperties contains the content of the Enumeration.properties
  // file,which is used for replacing english schema enumeration values
  // by language specific ones for the input process.
  private String enumerationPropertiesString;

  
  // municipalityURL is a property read from MunicipalityURLMapping.properties
  private String municipalityURL; // empty String, if not defined
  
  
  public Municipality( final String _municipalityNameKey,
                       final NamedProperties _municipalityProperties,
                       final MunicipalityService[] _services,
                       final Properties _appletProperties,
                       final String _enumerationPropertiesString,
                       final String _municipalityURL )
  {
    this.municipalityNameKey = _municipalityNameKey;
    this.municipalityProperties = _municipalityProperties;
    this.services = _services;
    this.appletProperties = _appletProperties;
    this.enumerationPropertiesString = _enumerationPropertiesString;
    this.municipalityURL = _municipalityURL; 
  } // constructor

  
  
  public String getEnumerationProperties()
  {
    return this.enumerationPropertiesString;
  }
  
  
  
  public String getAppletPropertyFileForLanguage( String language )
  {
    String propertyFile = this.appletProperties.getProperty(language);
    if( propertyFile == null ) // try english:
    {
      propertyFile = this.appletProperties.getProperty("en");
    }
    if( propertyFile == null )
    {
      System.out.println("*** ");
      System.out.println("*** Municipality.getAppletPropertyFileForLanguage():");
      System.out.println("*** Failed to return a propertyfile for the calling eMayorForms applet.");
    }       
    return propertyFile;
  }

  

  public String getMunicipalityURL()
  {
    return this.municipalityURL; // empty String, if not set
  }
  
  
  
 /**
  *  Return the name of this municipality in the passed language.
  */ 
  public String getMunicipalityNameForLanguage( String language )
  {
    String nameKey = "municipality.name." + language;  
    String municipalityName = this.municipalityProperties.getProperties().getProperty(nameKey);
    //System.out.println("getMunicipalityNameForLanguage() for municipality with municipalityNameKey= " +
    //                   this.municipalityNameKey +
    //                   " for language= " + language +
    //                   " returns servicename= " + municipalityName );    
    return municipalityName;  
  }

  

  
 /**
  *  Returns a property read from the municipality.properties file
  *  with the specified property key.
  *  The passed key should be one of the public static strings defined
  *  in this class.
  */ 
  public String getMunicipalityPropertyValue( String key )
  {
    return this.municipalityProperties.getProperties().getProperty(key);
  }
  
  
  
  
  
  public String getMunicipalityNameKey()
  {
  	return this.municipalityNameKey;  	
  }
  
  
    
  public MunicipalityService[] getServices()
  {
    return this.services;
  }

  
  
  public MunicipalityService getServiceByIdentifier( String serviceIdentifier )
  {
  	MunicipalityService s = null;
  	for( int i=0; i < this.services.length; i++ )
  	{
      if( this.services[i].getServiceIdentifier().equals(serviceIdentifier) )
      {
      	s = this.services[i];
      	break;
      }
  	}
  	return s;
  }
  
  
  
} // Municipality

