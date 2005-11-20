package org.emayor.client;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;


import org.emayor.client.Utilities.DataUtilities;


/**
 * 
 *  Holds all information about available maps for
 *  translating the english enumerations from the xsd schemas
 *  into language specific enumerations.
 *
 *  Nov 20, 2005 created (Joerg Plaz)
 * 
 */


public class EnumerationProperties implements Serializable
{

  // Start language dependent:
	
  // Available languages:	
  public final static int English = 0;
  public final static int German  = 1;
  public final static int Italian = 2;
  public final static int Spanish = 3;
  
  // shortcuts == file names [*.properties]
  public final static String EnglishIdentifier = "en";
  public final static String GermanIdentifier  = "ger";
  public final static String ItalianIdentifier = "it";
  public final static String SpanishIdentifier = "sp";
  

  // End language dependent

  // The properties read from the host:
  private Properties properties = new Properties();
  
  
  
  /**
   *  Takes care for downloads of the appletProperties from the server.
   *  callParameter usually is like "service.do?do=getAppletProperties"
   *  or for the test service like formtest.do?do=getAppletProperties.
   */ 
  public EnumerationProperties( final URL documentBase,
                                final String callParameter )
  { 
    System.out.println("EnumerationProperties: downloading property file...");
  	// Read and set the member properties attribute:
    try
	{
  	  this.readEnumerationPropertiesFromHost( documentBase,callParameter );
	}
    catch( Exception e )
	{
      // Nevermind .. it's optional.
	  System.out.println("*** EnumerationProperties: The optional enumeration property file could not be downloaded.");
	} 
  } // Constructor


 /**
  *  Only for test purpose.
  *  Will create an empty properties object
  */ 
  public EnumerationProperties()
  {  
  }
  
  
  
 /**
  *  Downloads the appletProperties from the server.
  *  callParameter usually is like "service.do?do=getAppletProperties"
  *  or for the test service like formtest.do?do=getAppletProperties.
  */ 
  private void readEnumerationPropertiesFromHost( final URL documentBase,
                                                  final String callParameter ) throws Exception
  {
  	// Possible exceptions raised by this method are catched and processed by the caller.
    // The server is aware of the current language and will return
    // the correct property file:
    URL documentURL = new URL( documentBase ,callParameter );
    
    System.out.println("Calling documentURL: " + documentURL.toExternalForm() );
    
    InputStream documentInputStream = documentURL.openStream();    
    this.properties.load(documentInputStream);
        
    // Debug: List all received property pairs:
    // System.out.println("LanguageProperties: Received property pairs:");
    // this.properties.list( System.out );
    
    translateUnicodeShortcutsIn(this.properties);
    System.out.println(">>Download has been successful. Enumeration properties are set.");
  } // readLanguagePropertiesFromHost
  
  

   

  
 /**
  *  Unicode shortcuts like &oacute; are allowed in the resource files.
  *  For being compatible with the struts bean.write tags, the shortcuts
  *  must be translated into real unicode characters, which is done here. 
  */ 
  private void translateUnicodeShortcutsIn( final Properties prop )
  {
  	Enumeration e = prop.keys();
  	while( e.hasMoreElements() )
  	{
      String key = (String)e.nextElement();
      String text = prop.getProperty(key);
      if( text.indexOf("&") >= 0 )
      {
        text = DataUtilities.TranslateUnicodeShortcutsInLine(text);
      	prop.setProperty(key,text);
      }      
  	}
  } // translateUnicodeShortcutsIn
  
  
  
  
  
  // Use ONE member attribute for speed up and lowering memory traffic for the GC:
  private StringBuffer enumerationCadidateValues = new StringBuffer();

  
  
  
  public synchronized String[] getTranslatedEnumerationValuesFor( final String basisLanguage,
                                                                  final String[] basisEnumerationValues )
                                                                  throws Exception
  {
    //System.out.println("EnumerationProperties.getTranslatedEnumerationValuesFor()");
    //System.out.println("called with basisLanguage= " + basisLanguage);
    
    String[] translatedEnumerationValues = new String[0]; // The return value
    // The values in this properties object are in one String, separated by commas
    // so create such a String with the basisEnumerationValues for comparisons:
    // Work with the member temp attribute - therefore this method was synchronized:
    this.enumerationCadidateValues.setLength(0);
    for( int i=0; i < basisEnumerationValues.length; i++ )
    {
      this.enumerationCadidateValues.append( basisEnumerationValues[i] );
      if( i < basisEnumerationValues.length-1 )
      {
        this.enumerationCadidateValues.append(",");
      }
    }    
    
    //System.out.println("EnumerationProperties: translatedEnumerationValues are:"); 
    //System.out.println("> " + enumerationCadidateValues.toString() ); 
    
    // Linear search....:
    String foundCandidateKey = null;
    Enumeration keys = this.properties.keys();
    while( keys.hasMoreElements() )
    {
      String key = (String)keys.nextElement();
      String value = (String)this.properties.get(key);
      
      //System.out.println("EnumerationProperties: Compare " + value + "   against   " +
      //                    enumerationCadidateValues.toString() );
      
      if( value.equals( this.enumerationCadidateValues.toString() ) )
      {
        foundCandidateKey = key;
        //System.out.println("EnumerationProperties: Match found for " + key );
        break;
      }
    }
    // Now if foundCandidateKey is not null, a enumeration translation group
    // was found for some languages.
    // Example for a enumeration list entry:
    //
    // Enumeration.list3.en=Male,Female
    // Enumeration.list3.de=Männlich,Weiblich
    // Enumeration.list3.it=Maschio,Femmina
    // Enumeration.list3.sp=Hombre,Mujer
    //
    // Now suppose, enumerationCadidateValues would be Male,Female from an english
    // xsd schema, and we have a hit ( == enumerationCadidateValues equals Male,Female too )
    //
    // and suppose basisLanguage = it
    //
    // then this method must return Maschio,Femmina
    //
    // do that:
    //
    // first get the target language from the key:
    //
    // This here is allowed to throw ANY exception - caller will intercept them.
    //
    if( foundCandidateKey != null )
    {
      StringTokenizer tok = new StringTokenizer(foundCandidateKey,".");
      // Get the map group == all with equal first 2 identifiers:
      String sourceGroupIdentifier = "";
      sourceGroupIdentifier += tok.nextToken();
      sourceGroupIdentifier += ".";
      sourceGroupIdentifier += tok.nextToken();
      // No find the target group entry:
      String targetKey = sourceGroupIdentifier + "." + basisLanguage;
      
      //System.out.println("EnumerationProperties: targetKey= " + targetKey );
      
      Enumeration keys2 = this.properties.keys();
      while( keys2.hasMoreElements() )
      {
        String key = (String)keys2.nextElement();
        String value = (String)this.properties.get(key);
        
        //System.out.println("EnumerationProperties: Compare " + targetKey + "   against   " +
        //                    key );
        
        if( key.equals( targetKey ) )
        {
        
          //System.out.println("EnumerationProperties: Match");
        
          // decompose the comma separated target list:
          StringTokenizer tok2 = new StringTokenizer(value,",");
          int numberOfEntries = tok2.countTokens(); // call this only once (bug)
          
          System.out.println("EnumerationProperties: creating array with size " + numberOfEntries);
          
          translatedEnumerationValues = new String[numberOfEntries];
          for( int i=0; i < numberOfEntries; i++ )
          {
            translatedEnumerationValues[i] = tok2.nextToken();
            
            //System.out.println("EnumerationProperties: adding " + translatedEnumerationValues[i]);
          }
          break;
        }
      }    
    } // if  
    return translatedEnumerationValues;
  } // getTranslatedEnumerationValuesFor
  

  
  
 /**
  *   Looks, if translatedText occurs in translatedEnumerationValues,
  *   and if so returns the array element at the same index
  *   in enumerationValues instead.
  */ 
  public String backTransform( String   translatedText,
                               String[] translatedEnumerationValues,
                               String[] enumerationValues )
  {
    String result = translatedText;
    try
    {
      for( int i=0; i < translatedEnumerationValues.length; i++ )
      {
        if( translatedText.equals(translatedEnumerationValues[i]) )
        {
          result = enumerationValues[i];
          break;
        }
      }
    }
    catch( Exception eee )
    {
      // Nothing - just don't transform. This is no error.
    }
    return result;
  }

  
  
  /**
   *   Looks, if originalText occurs in enumerationValues,
   *   and if so returns the array element at the same index
   *   in translatedEnumerationValues instead.
   */ 
   public String forwardTransform( String   originalText,
                                   String[] translatedEnumerationValues,
                                   String[] enumerationValues )
   {
     String result = originalText;
     try
     {
       for( int i=0; i < enumerationValues.length; i++ )
       {
         if( originalText.equals(enumerationValues[i]) )
         {
           result = translatedEnumerationValues[i];
           break;
         }
       }
     }
     catch( Exception eee )
     {
       // Nothing - just don't transform. This is no error.
     }
     return result;
   }
  
  
} // Constants

