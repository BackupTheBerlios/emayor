package org.emayor.eMayorWebTier.Utilities;

import java.io.*;
import java.util.*;
import javax.swing.*;



/**
 * 
 *  Singleton.
 *  Holds all information about the available languages.
 *  Used mainly by the ExtendedActionForm as backend.
 *
 *  Note: Singletons have application scope automatically,
 *        therefore no session specific attributes must be kept here.
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
  
  

  private volatile Vector namedProperties = new Vector(); // of namedProperties elements
  
  public final int numberOfLanguages = 4;
  
  private volatile static LanguageProperties Instance = null;
  
  
  
  private LanguageProperties()
  { 
  	this.readLanguagePropertiesFromFiles();
  	System.out.println("LanguageProperties loaded. Number of language property files: " +
  			           this.namedProperties.size() );
  	// Developer error feedback:
  	if( this.namedProperties.size() == 0 ) // no properties
  	{
  	  String msg = "No language properties file found in the MunicipalityInformation2.0 folder in JBoss conf.";
  	  String title = "WebTier Incomplete Configuration";
  	  this.errorFeedBack(msg,title);	
  	}
  } // Constructor


  
  
  
  
  
  private void readLanguagePropertiesFromFiles()
  {
    try 
    {
      StringBuffer propertyDirPath = new StringBuffer();  
      propertyDirPath.append( System.getProperty("jboss.server.home.dir") );
      propertyDirPath.append( File.separator );
      propertyDirPath.append( "conf" );
      propertyDirPath.append( File.separator );
      propertyDirPath.append( "MunicipalityInformation2.0" );
      propertyDirPath.append( File.separator );
      File parentDirectory = new File( propertyDirPath.toString() );
      if( parentDirectory.exists() )
      {
      	File[] propertyFile = parentDirectory.listFiles();
      	for( int i=0; i < propertyFile.length; i++ )
      	{
      	  // Load all *.properties (language) files:
      	  if(propertyFile[i].getAbsolutePath().endsWith(".properties") )
      	  {
      	  	this.loadPropertyFile( propertyFile[i] );
      	  	propertyFile[i] = null; // has effect on linux at least & helps GC
      	  } // if      		
      	} // for      	
      }
      else
      {
      	String msg = "The MunicipalityInformation2.0 folder is missing in the JBoss conf directory.";
      	String title = "WebTier Configuration Incomplete";
      	this.errorFeedBack(msg,title);
      }
    }
    catch( Exception e )
	{
      e.printStackTrace();
	}
  } // readlanguagePropertiesFromFiles
  
  

  
  
  private void loadPropertyFile( final File propertyFile )
  {
    try
	{
  	  FileInputStream fin = new FileInputStream(propertyFile);
  	  BufferedInputStream bin = new BufferedInputStream(fin);
  	  Properties prop = new Properties();
  	  prop.load(bin);
 	  bin.close();
  	  bin = null; fin = null;
      // Test, if they are up to date. display a message by dialog,
      // if it's not - they MUST be uptodate, otherwise proper processing
      // is not guaranteed:
      if( this.isPropertyFileUpToDate(prop,propertyFile.getName()) )
      {    
  	    // add to namedProperties vector. Name is the filename without ending:
  	    String fileName = propertyFile.getName();
  	    StringTokenizer tok = new StringTokenizer(fileName,".");
  	    String nameWithoutEnding = ( tok.hasMoreTokens() ) ?  tok.nextToken() : "undefined";
   	    NamedProperties np = new NamedProperties(nameWithoutEnding,prop);
        TranslateUnicodeShortcutsIn(np);   	  
   	    this.namedProperties.addElement(np);
  	    System.out.println("LanguageProperties loaded: " + propertyFile.getAbsolutePath() );
      }
      else
      {
        // This is a serious configuration error - the webtier may produce wrong / not readable
        // textual output, if property files were outdated, so tell this the
        // used through text and dialog output:
        String filePath = propertyFile.getName();
        System.out.println("*** ");
        System.out.println("*** ");
        System.out.println("*** WebTier: Fatal Error: LanguageProperties");
        System.out.println("*** ");
        System.out.println("*** Outdated Property file detected.");
        System.out.println("*** path: " + filePath );
        System.out.println("*** ");
        System.out.println("*** The web tier cannot work properly, if it cannot");
        System.out.println("*** access up to date property files.");
        System.out.println("*** ");
        System.out.println("*** Your webtier installation is NOT correct.");
        System.out.println("*** Please update the MunicipalityInformation2.0 directory");
        System.out.println("*** in JBoss/server/default/conf and restart JBoss.");
        System.out.println("*** ");
        System.out.println("*** ");
        System.out.println("*** ");
        System.out.println("*** ");
        
        // Add this information to the fileInformation object.
        // This will force the webtier not to call jsp's, because correct
        // operation cannot be guaranteed, but show an error information
        // page instead. 
        ProjectFileVersionInformation fileInformation = ProjectFileVersionInformation.GetInstance();
        String failureMessage = "The property file " + filePath + " has not the actual version." +
        "\nThe webtier cannot work properly with old files." +
        "\nPlease update the MunicipalityInformation2.0 directory" +
        "\nin JBoss/server/default/conf and restart JBoss." +
        "\nContact the eMayor developpers if the problem remains.";
        fileInformation.signalizeFilesAreNotUptodate(failureMessage);
        
        System.out.println("Available keys in this property file are: ");
        Enumeration enumeration = prop.keys();
        while( enumeration.hasMoreElements() )
        {
          System.out.println("key: " + enumeration.nextElement().toString() );
        }
        
      }
	}
    catch( Exception e )
	{
      e.printStackTrace();	
	}  	
  } // loadPropertyFile


  

  
  
 /**
  *   Compares the version number of the properties object
  *   with the version number stored here in this class.
  *   It is ***important***, that the version is uptodate,
  *   otherwise the proper functionality of the web tier is not guaranteed.
  */ 
  private boolean isPropertyFileUpToDate( final Properties properties, String fileName )
  {  
    boolean isUpToDate = false;  
    try  // catch any exception, like invalid casts
    {
      String propertiesVersion = (String)properties.get("File.VersionNumber");
      if( propertiesVersion != null )
      {
        propertiesVersion = propertiesVersion.trim();
        isUpToDate = ( propertiesVersion.equals(ProjectFileVersionInformation.WebTierFilesVersion) );

        System.out.println("LanguageProperties.isPropertyFileUpToDate()");
        System.out.println("File.VersionNumber is $" + propertiesVersion + "$ for file " + fileName );
        System.out.println("File.VersionNumber required for webtier is: $" + ProjectFileVersionInformation.WebTierFilesVersion + "$");
      
      }
      else
      {
        System.out.println("LanguageProperties.isPropertyFileUpToDate()");
        System.out.println("File.VersionNumber key does not exist for file " + fileName );
      }
    }
    catch( Exception e )
    {
      System.out.println("*** WebTier: LanguageProperties: Outdated property file detected. Message= " + e.getMessage() );
    }
    return isUpToDate;  
  } // isPropertyFileUpToDate


  
  
  
  
 /**
  *  Unicode shortcuts like &oacute; are allowed in the resource files.
  *  For being compatible with the struts bean.write tags, the shortcuts
  *  must be translated into real unicode characters, which is done here. 
  */ 
  public static void TranslateUnicodeShortcutsIn( final NamedProperties np )
  {
  	Properties prop = np.getProperties();
  	Enumeration e = prop.keys();
  	while( e.hasMoreElements() )
  	{
      String key = (String)e.nextElement();
      String text = prop.getProperty(key);
      if( text.indexOf("&") >= 0 )
      {
        text = TranslateUnicodeShortcutsInLine(text);
      	prop.setProperty(key,text);
      }
  	}
  } // translateUnicodeShortcutsIn
  


  
  
  private static String TranslateUnicodeShortcutsInLine( String line )
  {
    if( (line != null) &&  (line.indexOf("&") >= 0) )
    {
    	line = line.replaceAll("&aacute;","" + '\u00E1');
    	line = line.replaceAll("&agrave;","" + '\u00E0');

    	line = line.replaceAll("&oacute;","" + '\u00F3');
    	line = line.replaceAll("&ograve;","" + '\u00F2');

    	line = line.replaceAll("&eacute;","" + '\u00E9');
    	line = line.replaceAll("&egrave;","" + '\u00E8');
    	
    	line = line.replaceAll("&iacute;","" + '\u00ED');
    	line = line.replaceAll("&igrave;","" + '\u00EC');

    	line = line.replaceAll("&uacute;","" + '\u00FA');
    	line = line.replaceAll("&ugrave;","" + '\u00F9');

    	line = line.replaceAll("&ccedil;","" + '\u00E7');
        line = line.replaceAll("&ntilde;","" + '\u00F1');

    	line = line.replaceAll("&auml;","" + '\u00E4');
    	line = line.replaceAll("&ouml;","" + '\u00F6');
    	line = line.replaceAll("&uuml;","" + '\u00FC');
    
        // Replace newline sequences too:
        line = line.replaceAll("\\n","" + '\n');
    
    	//System.out.println("Translated -----------------------> " + text );
    }
    return line;
  }

 
  
  
  public int getNumberOfLanguages()
  {
  	return this.numberOfLanguages;
  }
 
  
  
  public int getLanguageFromParameterString( final String parameterValue )
  {
  	int theLanguage = LanguageProperties.English;
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
  *  This method is the gateway for all text requests from the JSP's,
  *  which call this through the ExtendedActionForm objects, which
  *  are the parents of all used ActionForms, which are accessible in the JSP's. 
  */
  public String getTextFromLanguageResource( final String key,
  		                                     final int language )
  { 
  	String languageString = this.getLanguageParameterStringForLanguage(language);
    // First: get the properties for the passed language:
  	Properties languageProperties = null;
	for( int i=0; i < this.namedProperties.size(); i++ )
	{
	  NamedProperties np = (NamedProperties)this.namedProperties.elementAt(i);
	  if( np.getName().equals(languageString) )
	  {
	  	languageProperties = np.getProperties();
	  	break;
	  }
	} // for	
    String text = ( languageProperties != null ) ? 
           languageProperties.getProperty(key) :
           "LanguageProperties.java: Properties object not found";
  	if( text == null )
  	 {
  		System.out.println("***getTextFromLanguageResource: Value not found for key " + 
  				           key + "  ->Returning the key as value." );
  		text = key; // Return the key, if the current properties object is null
  		
  		// Check the origin:
  		new Throwable().printStackTrace();
  		
  	 }
  	return text;    	
  }
  
  
  
 /**
  *  Singleton -> Returns the instance of it, creates it on the first call.
  *  The (private) constructor only is accessible through this static method.
  */  
  public static LanguageProperties getInstance()
  {
  	if( Instance == null ) Instance = new LanguageProperties();
  	return Instance;
  } // getInstance
  
  
  
} // Constants

