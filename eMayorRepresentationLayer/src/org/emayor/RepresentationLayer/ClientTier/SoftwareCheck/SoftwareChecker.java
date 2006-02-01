package org.emayor.RepresentationLayer.ClientTier.SoftwareCheck;

import java.awt.Frame;
import java.io.*;
import java.net.URL;
import java.awt.EventQueue;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



  /**
   *  Performs the automatic client software check and update
   * 
   *  4.1.06  jpl
   */


public class SoftwareChecker extends Thread
{
  
  private final String ClientInstallationLibraryFileName = "ClientInstallationFiles.zip";

  private boolean doRun = true;

  private SoftwareCheckApplet softwareCheckApplet;

  // The client path of the ClientInstallationFiles.zip file. 
  // It's set in the constructor.
  private String libraryFilePath;
  
  // Defined locations on the client:
  private String javaHomeDirectory   = System.getProperty("java.home");
  private String userHomeDirectory   = System.getProperty("user.home");
  private String fileSystemSeparator = System.getProperty("file.separator");

 
  
  
  public SoftwareChecker( final SoftwareCheckApplet _softwareCheckApplet )
  {
    this.softwareCheckApplet = _softwareCheckApplet;
    // The ClientInstallationFiles.zip is located directly
    // in java.home:
    this.libraryFilePath = this.javaHomeDirectory + fileSystemSeparator + 
                           ClientInstallationLibraryFileName;
  }

  
  
  
  public void run()
  {
    try{  Thread.sleep(266); } catch( Exception e1 ){} // Time for Swing
    // Step 1: Get the md5 checksums of actual the client software files
    //         from the server:
    FileChecksumEntry[] fileChecksumEntries = this.getMD5ChecksumListFromServer();
    System.out.println("SoftwareChecker: Number of filepath/md5checksums got from server: " + 
                        fileChecksumEntries.length );
    
    // Step 2: Try to locate the client software files using the filepathes from
    //         the fileChecksumEntries and decide, if an installation or an update
    //         is required:
    boolean isInstallationRequired = true; // This will be set to false as soon as one
                                           // already existing file is found.
    boolean isUpdateRequired = false;
    for( int index=0; index < fileChecksumEntries.length; index++ )
    {
      FileChecksumEntry entry = (FileChecksumEntry)fileChecksumEntries[index];
      // see if it exists on the client:
      File clientFile = this.searchFileOnClient( entry.getFilePath() );
      if( clientFile != null )
      {
        isInstallationRequired = false; // at least one file found
        // compare md5 checksums:
        try
        {
          String clientFileMD5Checksum = DataUtilities.GetMessageDigestForFileAsString(clientFile.getPath());
          if( !clientFileMD5Checksum.equals(entry.getMd5Checksum() ) )
          {
            System.out.println("checksum mismatch:");
            System.out.println(clientFileMD5Checksum + "   <>   " + entry.getMd5Checksum() );
            isUpdateRequired = true;
          }
          else
          {
            System.out.println("Checksum matched.");
          }
        }
        catch( Exception ee )
        {
          ee.printStackTrace();
          isUpdateRequired = true;
        }
      }
      else
      {
        isUpdateRequired = true;
      }      
    } // for
             
    // Wait some time:
    try{  Thread.sleep(1077); } catch( Exception e2 ){} // debug delay
        
    // Decide and act:
    boolean isAllowedToProceed = false;
    boolean softwareHasBeenInstalled = false;
    boolean softwareHasBeenUpdated = false;
    if( isInstallationRequired )
    {
      this.softwareCheckApplet.setMustBeInstalled();
      try{ Thread.sleep(199); } catch( Exception ee1 ){} // Time for Swing queue
      softwareHasBeenInstalled = this.doInstallClientSoftware(fileChecksumEntries);
      if( !softwareHasBeenInstalled )
      {
        JOptionPane.showMessageDialog(this.softwareCheckApplet,"Installation had errors","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
    else
    if( isUpdateRequired )
    {
      this.softwareCheckApplet.setMustBeUpdated();
      try{ Thread.sleep(199); } catch( Exception ee1 ){} // Time for Swing queue
      softwareHasBeenUpdated = this.doUpdateClientSoftware(fileChecksumEntries);
      if( !softwareHasBeenUpdated )
      {
        JOptionPane.showMessageDialog(this.softwareCheckApplet,"Update had errors","Error",JOptionPane.ERROR_MESSAGE);
      }
    }
    else // client library is up to date.
    {
      isAllowedToProceed = true;
    }
            
    // Wait some time:
    try{  Thread.sleep(1077); } catch( Exception e3 ){} // debug delay
    
    if( softwareHasBeenInstalled )
    {
      this.softwareCheckApplet.setIsInstalledSuccessfully();
    }
    else
    if( softwareHasBeenUpdated )
    {
      this.softwareCheckApplet.setIsUpdatedSuccessfully();
    }
    else
    if( isAllowedToProceed ) // only in this case, we can continue the login sequence
    {
      this.softwareCheckApplet.setIsUpToDate();
      try{ Thread.sleep(499); } catch( Exception ee1 ){} // Time for Swing queue
      this.softwareCheckApplet.redirectBrowserToNextPage();
    }
    else
    {
      this.softwareCheckApplet.redirectBrowserToLoginPage();
    }
  } // run
   

  
  
  
 /**
  *  Searches the file using the virtualZipFilePath, which can
  *  contain symbolic parts like java.home, which automatically
  *  are replaced by the System values on this client.
  *  Returns null, if the file has not been found.
  */ 
  private File searchFileOnClient( final String virtualZipFilePath )
  {
    System.out.println("SoftwareChecker.searchFileOnClient() with virtual path: " + virtualZipFilePath );
    String realPath = this.getRealPathFor(virtualZipFilePath);
    System.out.println("SoftwareChecker.searchFileOnClient() with real path: " + realPath );
    File resultFile = new File(realPath);
    if( !resultFile.exists() ) resultFile = null;  // return null, if it doesn't exist
    return resultFile;  
  } // searchFileOnClient
  
  
  
  
  
  private String getRealPathFor( final String virtualPath )
  {
    StringTokenizer tok = new StringTokenizer(virtualPath,"/");
    // Now we have logical names, where we do replace property strings
    // like java.home and user.home by their property values:
    int numberOfTokens = tok.countTokens(); // bug: call this only once.
    String[] paths = new String[numberOfTokens];
    for( int i=0; i < numberOfTokens; i++ ) paths[i] = tok.nextToken();
    for( int i=0; i < numberOfTokens; i++ )
    {
      if( paths[i].equals("java.home") ) paths[i] = javaHomeDirectory;
      if( paths[i].equals("user.home") ) paths[i] = userHomeDirectory;
    }
    StringBuffer targetPath = new StringBuffer();
    for( int i=0; i < numberOfTokens; i++ )
    {
      targetPath.append( paths[i] );
      if( i < numberOfTokens-1) targetPath.append( fileSystemSeparator );
    }
    return targetPath.toString();
  } // getRealPathFor
  
  
  
  
  
  private boolean doInstallClientSoftware( final FileChecksumEntry[] fileChecksumEntries )
  {
    boolean success = false;
    // Ask the user:
    String title = "eMayor";
    String message = this.softwareCheckApplet.getInstallationMessage();
    if( JOptionPane.showConfirmDialog( this.softwareCheckApplet,
                                       message, title,
                                       JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION )
    {
      SoftwareInstaller installer = new SoftwareInstaller( fileChecksumEntries,this.softwareCheckApplet );
      success = installer.installFiles("Installing...");
    } // if
    return success;
  }

  

  
  private boolean doUpdateClientSoftware( final FileChecksumEntry[] fileChecksumEntries )
  {
    boolean success = false;
    // Ask the user:
    String title = "eMayor";
    String message = this.softwareCheckApplet.getUpdateMessage();
    if( JOptionPane.showConfirmDialog( this.softwareCheckApplet,
                                       message, title,
                                       JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION )
    {
      SoftwareInstaller installer = new SoftwareInstaller( fileChecksumEntries,this.softwareCheckApplet );
      success = installer.installFiles("Updating...");
    } // if
    return success;
  }

 
  

  
  
 /**
  *   Requests and returns the MD5 checksum list from the server.
  */ 
  private FileChecksumEntry[] getMD5ChecksumListFromServer()
  {
    Vector fileChecksumVector = new Vector();
    String md5CheckSumStringFromServer = ""; // empty String means: failed.
    String relativeURL = this.softwareCheckApplet.getMD5RequestURL();
    try
    {
      URL documentURL = new URL( this.softwareCheckApplet.getDocumentBase() ,relativeURL );
      InputStream documentInputStream = documentURL.openStream();
      ByteArrayOutputStream buffer = DataUtilities.ReadByteFileFromInputStream(documentInputStream);
      md5CheckSumStringFromServer = buffer.toString("UTF-8"); 
      // Parse this String and create FileChecksumEntry elements.
      // Convention (cf server class ClientSoftwareAdministration, getClientLibraryMD5CheckSumList() ):
      // Convention: filepath=<filepath> md5Checksum=<md5Checksum>
      // and lines separated by newline characters:
      StringTokenizer tok = new StringTokenizer(md5CheckSumStringFromServer,"\n");
      while( tok.hasMoreTokens() )
      {
        String nextLine = tok.nextToken();
        int filePathIndex = nextLine.indexOf("filepath=");
        int md5ChecksumIndex = nextLine.indexOf(" md5Checksum=");
        int lineLength = nextLine.length();
        if( ( filePathIndex >= 0 ) && (md5ChecksumIndex >= 0 ) )
        {
          String relativeFilePathValue = nextLine.substring(filePathIndex+9,md5ChecksumIndex );
          String md5ChecksumValue = nextLine.substring(md5ChecksumIndex+13);
          FileChecksumEntry entry = new FileChecksumEntry(relativeFilePathValue,md5ChecksumValue);
          fileChecksumVector.addElement(entry);
          System.out.println("added entry with filepath="+relativeFilePathValue +
                             "      md5 checksum=" + md5ChecksumValue );
        } else
        {
          System.out.println("*** getMD5ChecksumListFromServer() detected incorrect line: ");
          System.out.println(nextLine);
        }
      } // while
    }
    catch( Exception eee )
    {
      System.out.println("*** SoftwareChecker: Unable to get MD5 checksum from server:");
      eee.printStackTrace();
    }
    // Copy the vector elements into the returned array:
    FileChecksumEntry[] fileChecksums = new FileChecksumEntry[fileChecksumVector.size()];
    fileChecksumVector.copyInto(fileChecksums);
    return fileChecksums;
  } // getMD5FromFileOnServer
  
  
  
  
  
  
  
  
 /**
  *  Terminates the execution of this thread
  */ 
  public void terminateThread()
  {
    this.doRun = false;
  }
  
  
} // SoftwareChecker



