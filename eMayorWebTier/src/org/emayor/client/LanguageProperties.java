package org.emayor.client;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;


import org.emayor.client.Utilities.DataUtilities;


/**
 * 
 *  Holds all information about the available languages.
 *
 *  May 12, 2005 created (Joerg Plaz)
 * 
 */
public class LanguageProperties implements Serializable
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
  public LanguageProperties( final String languageParameter, 
                             final URL documentBase,
                             final String callParameter )
  { 
    System.out.println("LanguageProperties: downloading language property file...");
  	// Read and set the member properties attribute:
    try
	{
  	  this.readLanguagePropertiesFromHost( documentBase,callParameter );
	}
    catch( Exception e )
	{
      // Nevermind .. although this will force the engine to just output the
      // keys instead of their values.
	  System.out.println("*** LanguageProperties: No property file has been found at all.");
	  System.out.println("*** LanguageProperties: You just will see the text keys in place of their values therefore.");
	  System.out.println("*** LanguageProperties: At least the default properties eMayorForms.properties file IS REQUIRED.");
	}
  
  } // Constructor


 /**
  *  Only for test purpose.
  *  Will create an empty properties object
  */ 
  public LanguageProperties()
  {  
  }
  
  
  
 /**
  *  Downloads the appletProperties from the server.
  *  callParameter usually is like "service.do?do=getAppletProperties"
  *  or for the test service like formtest.do?do=getAppletProperties.
  */ 
  private void readLanguagePropertiesFromHost( final URL documentBase,
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
    System.out.println(">>Download has been successful. Language properties are set.");
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
  
  
  

  
  
  public int getLanguageFromParameterString( final String parameterValue )
  {
  	int theLanguage = LanguageProperties.English; // default / fallback
  	if( parameterValue.startsWith("ger") ){ theLanguage=German;   }
  	if( parameterValue.startsWith("de")  ){ theLanguage=German;   }
  	if( parameterValue.startsWith("te")  ){ theLanguage=German;   }
  	if( parameterValue.startsWith("it")  ){ theLanguage=Italian;  }
  	if( parameterValue.startsWith("sp")  ){ theLanguage=Spanish;  }
  	if( parameterValue.startsWith("esp") ){ theLanguage=Spanish;  }
  	return theLanguage;  	
  }
  
  
  public String getLanguageParameterStringForLanguage( final int theLanguage )
  {
  	String s = null;
  	switch( theLanguage )
	{
	  case German:  s = GermanIdentifier; break;
  	  case Italian: s = ItalianIdentifier;  break;
  	  case Spanish: s = SpanishIdentifier;  break;
  	  default: s = EnglishIdentifier;  	
	}
    return s;	
  }

  
  
  private void errorFeedBack( String message, String title )
  {  	  
  	  JFrame errorFrame = new JFrame();
  	  errorFrame.setSize(600,300);
  	  errorFrame.setLocation(200,140);
  	  errorFrame.setTitle("eMayor Developer Feedback");
  	  errorFrame.setVisible(true);
  	  errorFrame.toFront();
  	  JOptionPane.showMessageDialog(errorFrame,message,title,JOptionPane.ERROR_MESSAGE );
  	  errorFrame.setVisible(false);
  	  errorFrame.dispose();
  }

  
  
  
 /**
  *  Returns the text associated with the passed key.
  *  If the properties do not exist (because no property file could be loaded at all)
  *  the key itself is returned. (This should be seen by municipality providers
  *  and be corrected - it is an error ) 
  */
  public String getTextFromLanguageResource( final String key )
  { 
    String text = ( this.properties != null ) ? this.properties.getProperty(key) : key;
    if( text == null ) text = "n/a";
  	return text;    	
  }
  
  
    
  
  
} // Constants

