package org.emayor.RepresentationLayer.ClientTier.SoftwareCheck;

import java.awt.EventQueue;
import java.awt.Frame;
import java.util.*;
import java.io.*;
import java.net.URL;
import java.awt.EventQueue;
import javax.swing.JOptionPane;


  /**
   *  Contains the methods for installing the software.
   * 
   *  For this, the library ClientInstallationLibraryFileName
   *  must exist on the client in the java.home directory
   *  already.
   * 
   *  The installFiles() method of this class then
   *  reads the ClientInstallationFiles.zip library
   *  and copies all files to the required locations.
   * 
   *  The subdirectories in ClientInstallationFiles.zip can
   *  have logical names. Example: java.home is replaced
   *  by the value of java.home of the client JRE.
   * 
   */


public class SoftwareInstaller
{

  // The list of symbolic pathnames and MD5 checksums from the
  // server. The associated files can be requested using
  // the symbolic pathnames as argument.
  private FileChecksumEntry[] fileChecksumEntries;

  // The parent applet:
  private SoftwareCheckApplet softwareCheckApplet;
  
  // Defined locations on the client:
  private final String javaHomeDirectory   = System.getProperty("java.home");
  private final String userHomeDirectory   = System.getProperty("user.home");
  private final String fileSystemSeparator = System.getProperty("file.separator");
  private final String OSDelimiter = System.getProperty("file.separator");

  private ProgressWindow progressWindow; 

  
  
  
  public SoftwareInstaller( final FileChecksumEntry[] _fileChecksumEntries,
                            final SoftwareCheckApplet _softwareCheckApplet )
  {
    this.fileChecksumEntries = _fileChecksumEntries;
    this.softwareCheckApplet = _softwareCheckApplet;
    Frame appletParentFrame = JOptionPane.getFrameForComponent(this.softwareCheckApplet);    
    this.progressWindow = 
        new ProgressWindow( "Installing...",
                            this.softwareCheckApplet.getWorkingIcon(),
                            appletParentFrame,
                            true,
                            this.softwareCheckApplet.getCancelMessage() );
  } // Constuctor
  


  
  
  public boolean installFiles( final String userInformation )
  {
    boolean success = false;
    EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {
        progressWindow.showCentered();
      }
    });
    try{ Thread.sleep(299); } catch( Exception ee1 ){} // Time for Swing queue

    success = this.doInstallFiles();
    
    EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {
        progressWindow.setVisible(false);
        progressWindow.dispose();
      }
    });
    try{ Thread.sleep(299); } catch( Exception ee1 ){} // Time for Swing queue
    
    return success;
  } // installFiles
  
  
  
  
 /**
  * Use the fileChecksumEntries for testing for each entry, if the
  * file declared in this entry does exist, and if its up to date.
  * If required, download it from the server and store it on the
  * client.
  * @return success
  */ 
  private boolean doInstallFiles()
  {
    boolean success = true;
    int numberOfErrors = 0;
    try
    {
      this.progressWindow.setProgressMaxValue(this.fileChecksumEntries.length);
      for( int index=0; index < this.fileChecksumEntries.length; index++ )
      {
        FileChecksumEntry entry = this.fileChecksumEntries[index];
        // see if it exists on the client:
        File clientFile = this.searchFileOnClient( entry.getFilePath() );
        if( clientFile != null )
        {
          // Test, if it's uptodate:
          String clientFileMD5Checksum = DataUtilities.GetMessageDigestForFileAsString(clientFile.getPath());
          if( clientFileMD5Checksum.equals(entry.getMd5Checksum() ) )
          {
            String message = entry.getFilePath() + " is up to date. (MD5 checksums match)";
            this.progressWindow_SetText( message );
            this.progressWindow.setProgressValue(index);
            System.out.println( message );
          }
          else
          {
            String message = "Updating " + entry.getFilePath();
            this.progressWindow_SetText( message );
            this.progressWindow.setProgressValue(index);
            System.out.println( message );
            boolean downloaded = this.downloadClientFile( entry.getFilePath() );
            if( !downloaded ) numberOfErrors++;
          }
        }
        else // The file doesn't exist yet on the client. Install it:
        {
          String message = "Installing " + entry.getFilePath();
          this.progressWindow_SetText( message );
          this.progressWindow.setProgressValue(index);
          boolean downloaded = this.downloadClientFile( entry.getFilePath() );      
          if( !downloaded ) numberOfErrors++;
        }
        try{ Thread.sleep(29); } catch( Exception ee1 ){} // Time for Swing queue
      } // for
    }
    catch( Exception eee )
    {
      success = false;
      eee.printStackTrace();
    }
    // If we have no exception, check numberOfErrors additionally:
    if( success )
    {
      success = (numberOfErrors == 0);
      if( numberOfErrors > 0 )
      {
        System.out.println("*** ");
        System.out.println("*** SoftwareInstaller.doInstallFiles: Number of errors: " + numberOfErrors );
        System.out.println("*** ");
      }
    }
    return success;  
  } // doInstallFiles



  
  
  /**
   *   Download the file defined by the passed virtualFilePath,
   *   and copy it to the correct location on the client.
   */ 
   private boolean downloadClientFile( final String virtualFilePath )      
   {   
     boolean success = false;
     String relativeURL = this.softwareCheckApplet.getClientFileRequestURL() +
                          "&VirtualFilePath=" + virtualFilePath;
     BufferedOutputStream outBuffer = null;
     try
     {
       URL absoluteURL = new URL( this.softwareCheckApplet.getDocumentBase() ,relativeURL );
       InputStream inputStream = absoluteURL.openStream();
       ByteArrayOutputStream buffer = DataUtilities.ReadByteFileFromInputStream(inputStream);
       byte[] targetFileBytes = buffer.toByteArray();
       // Make the virtualFilePath real:
       String realFilePath = this.getRealPathFor( virtualFilePath );
       // Write it:
       success = DataUtilities.WriteBinaryFile(realFilePath,targetFileBytes);
       if( success )
       {
         System.out.println("SoftwareInstaller: The file " + virtualFilePath +
                            " has been downloaded and installed. Real file path= " + realFilePath );
       }
       else
       {
          System.out.println("*** SoftwareInstaller: Unable to write to the file " + virtualFilePath +
                "  Real file path= " + realFilePath );
       }
     }
     catch( Exception eee )
     {
       System.out.println("SoftwareInstaller: Error for file " + virtualFilePath );
       eee.printStackTrace();
     }
     finally        
     {
       // try to close open buffers in each case :
       if( outBuffer != null )
       {
         try{ outBuffer.close(); } catch( Exception e22 ){}
       }
     }     
     return success;
   }
  

   
   
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
   

   
   
   
  /**
   *  Searches the file using the virtualZipFilePath, which can
   *  contain symbolic parts like java.home, which automatically
   *  are replaced by the System values on this client.
   *  Returns null, if the file has not been found.
   */ 
   private File searchFileOnClient( final String virtualZipFilePath )
   {
     System.out.println("SoftwareInstaller.searchFileOnClient() with virtual path: " + virtualZipFilePath );
     // split:
     StringTokenizer tok = new StringTokenizer(virtualZipFilePath,"/");
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
     System.out.println("SoftwareInstaller.searchFileOnClient() with real path: " + targetPath );
     File resultFile = new File(targetPath.toString());
     if( !resultFile.exists() ) resultFile = null;  // return null, if it doesn't exist
     return resultFile;  
   } // searchFileOnClient

   
   

  


  
  private byte[] readByteFileFromInputStream( final InputStream inputStream ) throws IOException
  {
    // Caution: The following values give the fastest operation :
    // Initial buffersize = zero (!)
    // byteBuffer size = 3200
    ByteArrayOutputStream byteArrayBuffer = new ByteArrayOutputStream(0);
    BufferedInputStream bufIn = new BufferedInputStream( inputStream );
    final byte[] byteBuffer = new byte[3200];
    int bytesRead;
    while(  (bytesRead = bufIn.read(byteBuffer)) != -1 )
     {
       byteArrayBuffer.write(byteBuffer,0,bytesRead);
     }
    bufIn.close();
    return byteArrayBuffer.toByteArray();
  }
  
  
  
  private void progressWindow_SetText( final String text )
  {
    EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {
        progressWindow.setText(text);
      }
    });
    try{ Thread.sleep(99); } catch( Exception ee1 ){} // Time for Swing queue    
  }
  

  
} // SoftwareInstaller

