package org.emayor.webtier.municipalities;

/**
 * 
 * Holds the information about the municipalitie(s)
 * which it retrieves on startup from the xml files
 * inside the MunicipalityInformation directory.
 * 
 * [Singleton]
 * 
 *  May 12, 2005 created, Joerg Plaz
 */

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.emayor.webtier.shared.*;


public class MunicipalitiesManager implements Serializable
{


  // Singleton -> Reference is a private class attribute
  private static MunicipalitiesManager ClassReference = null;
  
  // No real persistence supported, so matk it quick:
  private volatile Vector municipalities = new Vector(); // of Municipality elements
  
  
  // Use UTF-8 for file read operations
  private static final Charset charset = Charset.forName("UTF-8");

  
 /**
  *  Private constructor.
  */ 
  private MunicipalitiesManager()
  {
  	System.out.println(" ");
  	System.out.println("-------------- MunicipalitiesManager.constructor starts.");
  	System.out.println("-------------- Collecting the municipalities inside the");
  	System.out.println("-------------- JBoss/conf/MunicipalityInformation directory...");
  	
  	// Read the multilingual description of all available 
  	// municipalities and their services from the JBoss conf folder:  	
    try 
    {
      StringBuffer municipalitiesInformationDirPath = new StringBuffer();  
      municipalitiesInformationDirPath.append( System.getProperty("jboss.server.home.dir") );
      municipalitiesInformationDirPath.append( File.separator );
      municipalitiesInformationDirPath.append( "conf" );
      municipalitiesInformationDirPath.append( File.separator );
      municipalitiesInformationDirPath.append( "MunicipalityInformation" );
      municipalitiesInformationDirPath.append( File.separator );
      File municipalitiesInformationDirectory = new File( municipalitiesInformationDirPath.toString() );
      if( municipalitiesInformationDirectory.exists() )
      {
      	// This directory must contain subdirectories, which
      	// define the identification-name of a single municipality
      	// and contain all required multilingual information for that municipality.
        // Not all directories must contain municipality information though.
      	File[] municipalitiesDirectory = municipalitiesInformationDirectory.listFiles();
      	for( int i=0; i < municipalitiesDirectory.length; i++ )
      	{
          // This directory also contains language property files, readme.txt etc.
          // so skip them and only look at subdirectories:
          if( municipalitiesDirectory[i].isDirectory() )
          {
            Municipality municipality = this.loadMunicipalityDescriptionsForDirectory( municipalitiesDirectory[i] );     		
            if( municipality != null )
            {
          	  this.municipalities.addElement( municipality );
            }
          }  
      	} // for
      	System.out.println("-------------- Number of loaded municipality information: " +
                           this.municipalities.size() );
        if( this.municipalities.size() == 0 )
        {
            String message = "The MunicipalityInformation folder  " + municipalitiesInformationDirectory.getName() + 
            " does not contain at least one valid municipality information subfolder.";
            String title = "The WebTier configuration is not complete";
            this.errorFeedBack( message,title );
            System.out.println( "***Error: " + message );        
        }
      }
      else
      {
      	String msg = "The MunicipalityInformation folder is missing in the JBoss conf directory.";
      	String title = "The WebTier configuration is not complete";
      	this.errorFeedBack(msg,title);
        System.out.println( "***Error: " + msg );
      }
    }
    catch( Exception e )
	{
      e.printStackTrace();
	}         
  } // private constructor



  
  

  private Municipality loadMunicipalityDescriptionsForDirectory( final File municipalityDirectory )
  {
    Municipality municipality = null; // The return value
  	// The name of the municipalityDirectory is used as municipality identifier
  	// municipality identifier is used by the logic undependent from the
  	// used language on the UI's.
    // The municipalityNameIdentifier by convention is
    // "Name." + the municipalityDirectory name.
    String municipalityDirectoryName = municipalityDirectory.getName();
  	String municipalityNameIdentifier = "Name." + municipalityDirectoryName.trim();
  	System.out.println("Processing municipality in directory " + municipalityDirectoryName );
    File[] childrenFiles = municipalityDirectory.listFiles();
    // One of the children files must be a propterty file called municipality.properties.
    // It contains translations of the municipality name.
    // Search and read this one:
    File municipalityPropertyFile = this.searchFile(childrenFiles,"municipality.properties");
    if( municipalityPropertyFile != null )
    {
      NamedProperties municipalityProperties = this.loadPropertiesFromFile( municipalityPropertyFile );
      // Now read the "services" directory:
      File servicesDirectory = this.searchDirectory(childrenFiles,"services");
      if( servicesDirectory != null )
      {
        MunicipalityService[] services = this.readAvailableServicesDescriptionFromDirectory( servicesDirectory,
                                                                                             municipalityNameIdentifier );  
        if( ( services != null ) && (services.length > 0) )
        {
          // Finally load all property files required for the eMayorForms applets:
          // They are located in the AppletProperties subdirectory and have the form
          // eMayorForms_xx.properties where xx = en,ger,it,sp denotes the language.
          // properties structure is <key>   == language
          //                         <value> == the file as string
          File appletPropertiesDirectory = this.searchDirectory(childrenFiles,"AppletProperties");
          if( (appletPropertiesDirectory != null) && (appletPropertiesDirectory.isDirectory()) )
          {
            Properties appletProperties = this.readAvailableAppletPropertyFiles( appletPropertiesDirectory );
            if( appletProperties != null )
            {
              // Read the optional enumeration properties as a String:
              String enumerationPropertiesString = this.readEnumerationProperties( appletPropertiesDirectory );            
              // All needed stuff is present, so one can create the municipality instance:
              municipality = new Municipality( municipalityNameIdentifier,municipalityProperties,services,
                                               appletProperties,enumerationPropertiesString );        
            }
            else
            {
              String message = "The AppletProperties folder in " + municipalityNameIdentifier + 
              " does not contain any applet properties files of the form eMayorForms_xxx.properties";
              String title = "The WebTier configuration is not complete";
              this.errorFeedBack( message,title );
              System.out.println( "***Error: " + message );
            }  
          }
          else
          {
            String message = "The MunicipalityInformation folder  " + municipalityNameIdentifier + 
            " does not contain any applet properties files in the AppletProperties subdirectory";
            String title = "The WebTier configuration is not complete";
            this.errorFeedBack( message,title );
            System.out.println( "***Error: " + message );
          }          
        }
        else // skip it - it contains other stuff
        {
          String message = "The folder  " + municipalityNameIdentifier + 
                           " does not contain municipality information.";
          System.out.println(message);
        }
      }
      else
      {
        String message = "The MunicipalityInformation folder  " + municipalityDirectoryName + 
                         " does not contain the required services subdirectory.";
        String title = "The WebTier configuration is not complete";
        this.errorFeedBack( message,title );
        System.out.println( "***Error: " + message );
      }
    }
    // else it doesn't contain valid municipality information - contains other stuff
  	return municipality;
  } // municipalityHasBeenLoaded




  
 /**
  *   Read the Enumeration.properties file, returns it as String.
  *   It is optional.
  *   It holds mappings for enumerations, which occur in referenced xsd schema files.
  * 
  *   The returned Properties object can be empty.
  */
  private String readEnumerationProperties( final File appletPropertiesDirectory )
  {
    String enumerationPropertiesString = ""; // empty String if nothing could be read
    String enumerationFilePath = appletPropertiesDirectory.getPath() + File.separator + "Enumeration.properties";
    File file = new File(enumerationFilePath);
    if( file.exists() )
    {
      try
      {
        enumerationPropertiesString = this.readUTF8TextFileAsString( file );     
        ByteArrayInputStream bis = new ByteArrayInputStream( enumerationPropertiesString.getBytes("UTF-8") );
        Properties props = new Properties(); // Only used for checking
        props.load( bis );        
        if( !this.isPropertyFileUpToDate(props,enumerationFilePath) )
        {     
          // This is a serious configuration error - the webtier may produce wrong / not readable
          // textual output, if property files were outdated, so tell this the
          // used through text and dialog output:
          String filePath = file.getPath();
          System.out.println("*** ");
          System.out.println("*** ");
          System.out.println("*** WebTier: Fatal Error: Municipalitiesmanager");
          System.out.println("*** ");
          System.out.println("*** Outdated Property file detected.");
          System.out.println("*** path: " + filePath );
          System.out.println("*** ");
          System.out.println("*** The web tier cannot work properly, if it cannot");
          System.out.println("*** access up to date property files.");
          System.out.println("*** ");
          System.out.println("*** Your webtier installation is NOT correct.");
          System.out.println("*** Please update the MunicipalityInformation directory");
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
                                  "\nPlease update the MunicipalityInformation directory" +
                                  "\nin JBoss/server/default/conf and restart JBoss." +
                                  "\nContact the eMayor developpers if the problem remains.";
          fileInformation.signalizeFilesAreNotUptodate(failureMessage);            

          System.out.println("Available keys in this property file are: ");
          Enumeration enumeration = props.keys();
          while( enumeration.hasMoreElements() )
          {
            System.out.println("key: " + enumeration.nextElement().toString() );
          }                
        } // if      
      }
      catch( Exception ee )
      {
        System.out.println("MunicipalitiesManager.readEnumerationProperties(): Optional file " +
                           enumerationFilePath + " not found.");
      }
    } // if
    return enumerationPropertiesString;
  } // readEnumerationProperties
  
  
  
  
  
 /** 
  *  Finally load all property files required for the eMayorForms applets:
  *  They are located in the AppletProperties subdirectory and have the form
  *  eMayorForms_xx.properties where xx = en,ger,it,sp denotes the language.
  *  properties structure is <key>   == language
  *                          <value> == the file as string
  */
  private Properties readAvailableAppletPropertyFiles( final File appletPropertiesDirectory )
  {
    Properties appletProperties = new Properties();
    // Collect them:
    File[] files = appletPropertiesDirectory.listFiles();
    for( int i=0; i < files.length; i++ )
    {
      String fileName = files[i].getName();
      if( ( files[i].isFile() ) && 
            ( fileName.startsWith("eMayorForms_") ) && 
            ( fileName.endsWith(".properties") )     )
      {
        String fileContent = this.readUTF8TextFileAsString( files[i] );     
        // filter out the language identifier:
        int lastPointIndex = fileName.lastIndexOf(".");
        String languageID = fileName.substring(12,lastPointIndex);
        System.out.println("readAvailableAppletPropertyFiles: applet property file read for language: " + languageID );
        appletProperties.put(languageID,fileContent);
        // Test, if they are up to date. display a message by dialog,
        // if it's not - they MUST be uptodate, otherwise proper processing
        // is not guaranteed and malfunctions may create complex error pictures,
        // so intercept this:
        try
        {
          Properties singleProperties = new Properties();
          ByteArrayInputStream bis = new ByteArrayInputStream( fileContent.getBytes("UTF-8") );
          singleProperties.load( bis );        
          if( !this.isPropertyFileUpToDate(singleProperties,fileName) )
          {     
            // This is a serious configuration error - the webtier may produce wrong / not readable
            // textual output, if property files were outdated, so tell this the
            // used through text and dialog output:
            String filePath = files[i].getPath();
            System.out.println("*** ");
            System.out.println("*** ");
            System.out.println("*** WebTier: Fatal Error: Municipalitiesmanager");
            System.out.println("*** ");
            System.out.println("*** Outdated Property file detected.");
            System.out.println("*** path: " + filePath );
            System.out.println("*** ");
            System.out.println("*** The web tier cannot work properly, if it cannot");
            System.out.println("*** access up to date property files.");
            System.out.println("*** ");
            System.out.println("*** Your webtier installation is NOT correct.");
            System.out.println("*** Please update the MunicipalityInformation directory");
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
                                    "\nPlease update the MunicipalityInformation directory" +
                                    "\nin JBoss/server/default/conf and restart JBoss." +
                                    "\nContact the eMayor developpers if the problem remains.";
            fileInformation.signalizeFilesAreNotUptodate(failureMessage);            

            System.out.println("Available keys in this property file are: ");
            Enumeration enumeration = singleProperties.keys();
            while( enumeration.hasMoreElements() )
            {
              System.out.println("key: " + enumeration.nextElement().toString() );
            }                
          } // if
        }
        catch( Exception eee )
        {
          eee.printStackTrace();
        }
      }  
    } // for  
    return appletProperties;
  } // readAvailableAppletPropertyFiles
  
  
  
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

         System.out.println("MunicipalitiesManager.isPropertyFileUpToDate()");
         System.out.println("File.VersionNumber is $" + propertiesVersion + "$ for file " + fileName );
         System.out.println("File.VersionNumber required for webtier is: $" + ProjectFileVersionInformation.WebTierFilesVersion + "$");

       }
       else
       {
         System.out.println("MunicipalitiesManager.isPropertyFileUpToDate()");
         System.out.println("File.VersionNumber key does not exist for file " + fileName );
       }
     }
     catch( Exception e )
     {
       System.out.println("*** WebTier: MunicipalitiesManager: Outdated property file detected. Message= " + e.getMessage() );
     }
     return isUpToDate;  
   } // isPropertyFileUpToDate
  

   
   
   
   
  
 /**
  *  Read the files with ending *.service inside the passed servicesDirectory,
  *  and create and return the MunicipalityService Array with this information.
  * 
  *  This method always adds the "Browse Repository" service to the ones found in the files,
  *  because this one ALWAYS is present.  
  */ 
  private MunicipalityService[] readAvailableServicesDescriptionFromDirectory( final File servicesDirectory,
                                                                               final String municipalityNameIdentifier )
  {
    Vector serviceVector = new Vector(); // found services are added here
    // This directory must contain property files with ending *.service,
    // which contain multilingual information about the services.
    // The filename without ending .service serves as serviceID, used for
    // the ServiceHandling.
    File[] files = servicesDirectory.listFiles();
    for( int i=0; i < files.length; i++ )
    {
      if( ( files[i].isFile() ) && ( files[i].getName().endsWith(".service") ) )
      {
        MunicipalityService service = this.loadServiceDescriptionFromFile( files[i],municipalityNameIdentifier );     
        if( service != null )
        {
          serviceVector.addElement( service );
        }
      }  
    } // for
    System.out.println("-------------- Number of loaded services: " + serviceVector.size() );

    // Add the browse repository service, which exists always, and isn't loaded from a file therefore.
    // for this one, create the properties with the multilingual servicename right here,
    // using the properties from the global language file:
    String browseRepositoryKey = TextResourceKeys.BrowseAvailableDocumentsServiceKey;
    Properties browseRepositoryProperties = new Properties();
    // Fill the names in the different languages:
    LanguageProperties languageProperties = LanguageProperties.getInstance();
    browseRepositoryProperties.setProperty( 
            "service.name.en", 
            languageProperties.getTextFromLanguageResource(browseRepositoryKey,LanguageProperties.English) );
    browseRepositoryProperties.setProperty( 
            "service.name.ger", 
            languageProperties.getTextFromLanguageResource(browseRepositoryKey,LanguageProperties.German) );
    browseRepositoryProperties.setProperty( 
            "service.name.it", 
            languageProperties.getTextFromLanguageResource(browseRepositoryKey,LanguageProperties.Italian) );
    browseRepositoryProperties.setProperty( 
            "service.name.sp", 
            languageProperties.getTextFromLanguageResource(browseRepositoryKey,LanguageProperties.Spanish) );    
    NamedProperties browseRepositoryServiceNameProperties = 
                    new NamedProperties( browseRepositoryKey,
                                         browseRepositoryProperties );       
    MunicipalityService browseRepositoryService = 
            new MunicipalityService( browseRepositoryKey,
                                     browseRepositoryServiceNameProperties,
                                     municipalityNameIdentifier );
    serviceVector.addElement( browseRepositoryService );
        
    MunicipalityService[] services = new MunicipalityService[serviceVector.size()];
    serviceVector.copyInto( services );
    return services;
  } // readAvailableServicesDescriptionFromDirectory
  
  
  

  
  
  
  
 /**
  *  Load properties from a .service file into the associated MunicipalityService object,
  *  and return that one.
  */ 
  private MunicipalityService loadServiceDescriptionFromFile( final File servicePropertyFile,
                                                              final String municipalityNameIdentifier )
  {
    MunicipalityService service = null;
    NamedProperties namedServiceNameProperties = this.loadPropertiesFromFile(servicePropertyFile);
    if( namedServiceNameProperties != null )
    {
      // The service ID used for the ServiceHandling simply is the filename without
      // the ending ".service":
      String fileName = servicePropertyFile.getName();
      String serviceID = fileName.substring( 0,fileName.length()-8 );
      service = new MunicipalityService( serviceID,namedServiceNameProperties,municipalityNameIdentifier );
    }
    return service;
  } // loadServiceDescriptionFromFile
  
  
  

  
  private String readUTF8TextFileAsString( final File file )
  {    
    String fileString = null;
    try
    {
      FileInputStream fin = new FileInputStream(file);
      ByteArrayOutputStream bOut = Utilities.ReadByteFileFromInputStream(fin);
      fileString = bOut.toString("UTF-8");
      bOut = null;
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }
    return fileString;    
  } // readUTF8TextFileAsString
  

  
  
  
  
 /**
  *  The method assumes that the passed propertyFile consists
  *  of name=value pairs. It reads the file and passes
  *  the information into a Properties object which it returns.
  *  If an error occurs, the Properties object will be null.
  */ 
  private NamedProperties loadPropertiesFromFile( final File propertyFile )
  {
  	NamedProperties namedProperties = null;
    try
	{
  	  FileInputStream fin = new FileInputStream(propertyFile);
  	  BufferedInputStream bin = new BufferedInputStream(fin);
  	  Properties prop = new Properties();
  	  prop.load(bin);
 	  bin.close();
  	  bin = null; fin = null;
  	  String fileName = propertyFile.getName();
  	  StringTokenizer tok = new StringTokenizer(fileName,".");
  	  String nameWithoutEnding = ( tok.hasMoreTokens() ) ?  tok.nextToken() : "undefined";
      namedProperties = new NamedProperties(nameWithoutEnding,prop);
      LanguageProperties.TranslateUnicodeShortcutsIn( namedProperties );   	  
  	  System.out.println("loadPropertiesFromFile() loaded: " + propertyFile.getAbsolutePath() );
	}
    catch( Exception e )
	{
      e.printStackTrace();	
	}  	
    return namedProperties;
  } // loadPropertiesFromFile
  

  
  

 /**
  *  Search the file with the passed name in the passed filesArray
  *  and return it, or return null if it is not part of the passed filesArray.
  */ 
  private File searchFile( File[] filesArray , String fileName )
  {
  	File foundFile = null;
  	if( filesArray != null )
  	{
      for( int i=0; i < filesArray.length; i++ )
      {
      	if( filesArray[i].exists() )
      	{
      	  if( filesArray[i].isFile() )
      	  {
      	  	if( filesArray[i].getName().trim().equals( fileName.trim() ) )
      	  	{
      	  	  foundFile = filesArray[i];
      	  	  break;
      	  	}
      	  }
      	}
      }
  	}
  	return foundFile;
  } // searchFile

  
  
  /**
   *  Search the directory with the passed name in the passed filesArray
   *  and return it, or return null if it is not part of the passed filesArray.
   */ 
   private File searchDirectory( File[] filesArray , String directoryName )
   {
   File foundDirectory = null;
   if( filesArray != null )
   {
       for( int i=0; i < filesArray.length; i++ )
       {
       if( filesArray[i].exists() )
       {
         if( filesArray[i].isDirectory() )
         {
         if( filesArray[i].getName().trim().equals( directoryName.trim() ) )
         {
           foundDirectory = filesArray[i];
           break;
         }
         }
       }
       }
   }
   return foundDirectory;
   } // searchDirectory
  
  
  
    
  public Municipality[] getMunicipalities()
  {
  	Municipality[] array = new Municipality[this.municipalities.size()];
  	this.municipalities.copyInto( array );
    return array;
  }

  
  
  public Municipality getMunicipalityByKey( final String theKey )
  {
    Municipality foundMunicipality = null;
    Municipality workMunicipality = null;
    for( int i=0; i < this.municipalities.size(); i++ )
    {
      workMunicipality = (Municipality)this.municipalities.elementAt(i);
      if( workMunicipality.getMunicipalityNameKey().equals(theKey) )
      {
      	foundMunicipality = workMunicipality;
        break;
      }
    }
    if( foundMunicipality == null)
    {
      System.out.println("***");
      System.out.println("*** MunicipalityWebAttributes.getMunicipalityByKey(): Municipalitykey " +
                              theKey + " NOT FOUND.");
      System.out.println("***");
    }    
    return foundMunicipality;
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
  * The only public method here: 
  * @return MunicipalityWebAttributes (singleton)
  */ 
  public static MunicipalitiesManager GetInstance()
  {
    if( ClassReference == null )
        ClassReference = new MunicipalitiesManager();
    return ClassReference;
  }
  
  

} // MunicipalityWebAttributes

