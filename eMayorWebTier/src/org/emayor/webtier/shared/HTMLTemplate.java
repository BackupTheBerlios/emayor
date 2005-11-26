package org.emayor.webtier.shared;



/*
 *  Singleton
 *  Holds the html template information used by all JSP's. 
 * 
 *  Designed as Singleton, so that the html template file
 *  only is read and processed once, and not on every page call.
 *
 *  An instance of this is hold in ExtendedActionForm, from
 *  where it is visible for all JSP's. (->Basic Struts architecture )
 *  
 *  Created 9. August 2005, jpl
 * 
 */

import java.io.*;
import java.nio.charset.Charset;

public class HTMLTemplate implements Serializable
{


  private static HTMLTemplate instance = null;
 
  // The html template is partitioned at these identifiers, which are removed
  // and do not occure in the partitions:
  private final String[]  insertionIdentifiers = 
            new String[]{ "Logic.LanguageSelection",
                          "Logic.NavigationBar",
                          "Logic.PageContent",
                          "Logic.AboutLinkText" };
  
  // Created by processHTMLTemplate, this will have 1 element more, than insertionIdentifiers
  // == the partitions BETWEEN these insertionIdentifiers,
  // and is accessed by the JSP's using one method for one part. So there are the
  // following access methods for this:
  // getPartition0() upto getPartition4()  == 5 partitions.
  private String[] templatePartition = null;
  

  // Use UTF-8 for file read operations
  private static final Charset charset = Charset.forName("UTF-8");

  
  
  
  private HTMLTemplate()
  {
    this.loadTemplateFromFileSystem();
  }

  
  
  
  private void loadTemplateFromFileSystem()
  {
    StringBuffer municipalitiesInformationDirPath = new StringBuffer();  
    municipalitiesInformationDirPath.append( System.getProperty("jboss.server.home.dir") );
    municipalitiesInformationDirPath.append( File.separator );
    municipalitiesInformationDirPath.append( "conf" );
    municipalitiesInformationDirPath.append( File.separator );
    municipalitiesInformationDirPath.append( "MunicipalityInformation" );
    municipalitiesInformationDirPath.append( File.separator );
    File municipalitiesInformationDirectory = new File( municipalitiesInformationDirPath.toString() );
    StringBuffer htmlTemplateText = new StringBuffer();
    if( municipalitiesInformationDirectory.exists() )
    {
      // Read the html template file from there:
      System.out.println("ExtendedActionForm: MunicipalityInformation directory found at: " +
                         municipalitiesInformationDirectory.getPath() );
      String htmlTemplateFileName = municipalitiesInformationDirectory.getPath() +
                                    File.separator +
                                    "template.htm";  
      File htmlTemplateFile = new File(htmlTemplateFileName);
      if( htmlTemplateFile.exists() )
      {
        htmlTemplateText = new StringBuffer( this.readUTF8TextFileAsString( htmlTemplateFile ) );
        if( htmlTemplateText.length() == 0 )
        {
          System.out.println("ExtendedActionForm: HTML template file could not be read correctly.");        
        }
      }
      else
      {
        System.out.println("ExtendedActionForm: HTML template file does not exist.");              
      }
    }
    else
    {
      // The template does not exist in the MunicipalityInformation directory,
      // so it must be loaded from the resource of this war file:
      System.out.println("ExtendedActionForm: HTML template file is MISSING in the MunicipalityInformation directory.");
      System.out.println(" -> Loading it from the WAR file");
    } // else
    
    // Could it be read ?
    if( htmlTemplateText.length() > 0 )
    {
      if( !this.processHTMLTemplate(htmlTemplateText) )
      {
        System.out.println("ExtendedActionForm: The HTML template file had syntactical errors. Loading it from the WAR.");
        htmlTemplateText = this.loadTHMLTemplateFromWAR();
        this.processHTMLTemplate( htmlTemplateText );  
        // This time we give up on errors.
      }
    }
    else
    {
      htmlTemplateText = this.loadTHMLTemplateFromWAR();
      this.processHTMLTemplate( htmlTemplateText );  
    }    
  } // private constructor
  
  

  
 /**
  *   Fallback method. Used, if loading the html template from the
  *   jboss conf directory has failed or if it contained syntactical errors.
  *   In this case the default html template from the webtier war is used. 
  */ 
  private StringBuffer loadTHMLTemplateFromWAR()
  {
    System.out.println("ExtendedActionForm: Try to load default HTML template file from WAR.");        
    StringBuffer htmlTemplateText = new StringBuffer();
    String htmlTemplateFileName = "template.htm";      
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(htmlTemplateFileName);   
    htmlTemplateText = new StringBuffer( this.readUTF8TextFileAsStringFromInputStream( inputStream ) );
      
    System.out.println(" ");
    System.out.println(" ----- ");
    System.out.println("ExtendedActionForm: Got the following html template from the WAR:");
    System.out.println( htmlTemplateText.toString() );
    System.out.println(" ----- ");
    System.out.println(" ");
      
    if( htmlTemplateText.length() == 0 ) // Give up - webtier won't work
    {
      System.out.println("***");
      System.out.println("*** Fatal error:");
      System.out.println("*** ExtendedActionForm: HTML template file could not be read correctly from WAR.");        
      System.out.println("***");
    }      
    return htmlTemplateText;
  } // loadTHMLTemplateFromWAR
  

  
  

  
  
  private boolean processHTMLTemplate( final StringBuffer templateBuffer )
  {
    boolean success = false;
    System.out.println("HTMLTemplate.processHTMLTemplate() starts.");
    // First, all comments are removed. Reason for this: In some explanations
    // the insertionIdentifier also occur inside comments. These occurences
    // of course must be skipped, so the easy way is just to get rid of all html comments:
    this.removeAllCommentsFrom( templateBuffer );    
    // Create the partitions:
    int numberOfInsertionIdentifiers = this.insertionIdentifiers.length;
    // partitions between the identifiers == have 1 more element than the id's:
    this.templatePartition = new String[ numberOfInsertionIdentifiers+1 ];
    // Just use index calculations, don't produce temporary Strings:
    int currentIdentifierPosition = 0;
    int previousIdentifierPosition = 0;
    try
    {
      for( int i=0; i < numberOfInsertionIdentifiers; i++ )
      {
        currentIdentifierPosition = templateBuffer.indexOf( this.insertionIdentifiers[i] );
        templatePartition[i] = templateBuffer.substring( previousIdentifierPosition,currentIdentifierPosition );      
        
        System.out.println(" ");
        System.out.println("Set part " + i + " to: ");
        System.out.println( templatePartition[i] );
        System.out.println(" ");
        
        // update for next run:
        previousIdentifierPosition = currentIdentifierPosition + this.insertionIdentifiers[i].length();      
      } // for
      // remains the partition from the last identifier to the end of the template:
      templatePartition[numberOfInsertionIdentifiers] =
                        templateBuffer.substring( previousIdentifierPosition,templateBuffer.length() );

      System.out.println(" ");
      System.out.println("Set last part to: ");
      System.out.println( templatePartition[numberOfInsertionIdentifiers] );
      System.out.println(" ");

      success = true; // as no exception has occured.
    }
    catch( Exception e )
    {
      System.out.println("processHTMLTemplate: Invalid template structure.");
    }
    System.out.println("HTMLTemplate.processHTMLTemplate() ends with success= " + success );
    return success;
  } // processHTMLTemplate
  

  
  
 /**
  *  The getPartitionX() methods are called by JSP's 
  *  (through an instance of ExtendedActionForm)
  *  and are valid, after processHTMLTemplate() has 
  *  created the partitions.
  */ 
  public String getPartition0()
  {  
    // TEST: Load template.htm from disk on every call, by outcommenting this call:
    // this.loadTemplateFromFileSystem();  
    return this.templatePartition[0];
  }
  
  
  public String getPartition1()
  {
    return this.templatePartition[1];
  }
  
  
  public String getPartition2()
  {
    return this.templatePartition[2];
  }
  
  
  public String getPartition3()
  {
    return this.templatePartition[3];
  }
  
  
  public String getPartition4()
  {
    return this.templatePartition[4];
  }


  
  
  private void removeAllCommentsFrom( final StringBuffer htmlBuffer )
  {
    int commentStartIndex = -1;
    int commentEndIndex = -1;
    while( (commentStartIndex = htmlBuffer.indexOf("<!--")) >= 0 )
    {
      commentEndIndex = htmlBuffer.indexOf("-->");
      if( commentEndIndex > commentStartIndex )
      {
        htmlBuffer.replace(commentStartIndex,commentEndIndex+3,"");
      }
      else
      {
        System.out.println("removeAllCommentsFrom: Syntactical error in the html template. Breaking...");
        break;
      }
    } // while  
  } // removeAllCommentsFrom
  

  
  
  private String readUTF8TextFileAsStringFromInputStream( final InputStream inputStream )
  {    
    String fileString = null;
    try
    {
      ByteArrayOutputStream bOut = Utilities.ReadByteFileFromInputStream(inputStream);
      fileString = bOut.toString("UTF-8");
      bOut = null;
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }
    return fileString;    
  } // readUTF8TextFileAsString
  
  
  
  
  
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

  
  

  
  public static HTMLTemplate getInstance()
  {
    if( instance == null ) instance = new HTMLTemplate();
    return instance;
  }

  
  
} // HTMLTemplate


