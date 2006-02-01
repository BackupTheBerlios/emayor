package org.emayor.eMayorWebTier.struts.ClientSoftwareCheck;


  /**
   *  A singleton class.
   *  It calculates and holds the MD5 checksum for
   *  the client software files to be installed or
   *  updated on the client.
   * 
   *  It also reads all clientfiles and holds them
   *  in the memory for passing them to client
   *  software updaters on demand.
   * 
   *  All file pathnames are relative to the ClientInstallationFiles.zip
   *  which holds all files to be updated inside
   *  logical directories (f.ex. java.home to be
   *  replaced by the value of java.home on the
   *  client)
   * 
   *  This is used for the automatic client software
   *  installation or update.
   * 
   *  4.1.06  jpl   for the finetuning version
   */


import java.util.*;
import java.awt.EventQueue;
import java.io.*;
import java.util.jar.*;
import java.net.URL;

import javax.swing.JOptionPane;

import org.emayor.RepresentationLayer.Shared.Utilities.DataUtilities;




public class ClientSoftwareAdministration
{
  // The directory on the server, where the ClientInstallationFiles.zip
  // library file will be stored:
  private String clientSoftwareLibraryDirectoryPath;
  // and the name:
  private String clientSoftwareLibraryName = "ClientInstallationFiles.zip";
  // and the JarFile object:
  private JarFile clientSoftwareLibrary = null;
  
  private static ClientSoftwareAdministration Instance = null; 
  
  // This vector holds ClientSoftwareFileMD5ChecksumEntry elements for
  // all files of the ClientInstallationFiles.zip. Each entry holds
  // the zip relative filepath and the md5 checksum.
  private Vector md5ChecksumList = new Vector(); // of ClientSoftwareFileMD5ChecksumEntry elements
  
  // This hashtable holds all clientfiles as byte arrays
  // with the symbolic (zip relative) pathname as keys.
  // It is set by the method readAndStoreClientFiles.
  private Hashtable clientFiles = new Hashtable();
  
  
  
  
  private ClientSoftwareAdministration( final int httpPortNumber )
  {
    // Define the directory on the server, where the ClientInstallationFiles.zip
    // library file will be stored:
    StringBuffer clientSoftwareLibraryDirectoryBuffer = new StringBuffer();  
    clientSoftwareLibraryDirectoryBuffer.append( System.getProperty("jboss.server.home.dir") );
    clientSoftwareLibraryDirectoryBuffer.append( File.separator );
    clientSoftwareLibraryDirectoryBuffer.append( "conf" );
    clientSoftwareLibraryDirectoryBuffer.append( File.separator );
    clientSoftwareLibraryDirectoryBuffer.append( "ClientInstallationTemp" );
    clientSoftwareLibraryDirectoryBuffer.append( File.separator );
    this.clientSoftwareLibraryDirectoryPath = clientSoftwareLibraryDirectoryBuffer.toString();
  
    // Get the ClientInstallationFiles.zip over localhost tcp/ip from this server's ear
    // and store it and return it as JarFile:
    this.clientSoftwareLibrary = this.retrieveClientSoftwareLibrary( httpPortNumber );
    if( this.clientSoftwareLibrary != null )
    {
      // Read all jar entries in the clientSoftwareLibrary == client software files:
      // calculate the md5 checksums and add them to the md5ChecksumList:
      this.addAllMD5ChecksumsTo( this.md5ChecksumList, this.clientSoftwareLibrary );
      // Read all files from that jar file and hold them
      // in the memory ready for passing them to client
      // software updaters on demand:
      this.readAndStoreClientFiles();
    }
    else
    {
      System.out.println("***");
      System.out.println("*** ClientSoftwareAdministration: Unable to load the software library as jar file.");
      System.out.println("***");
    }
  } // Constructor


  
 /**
  *  Read all files from that jar file and hold them
  * in the memory ready for passing them to client
  * software updaters on demand:
  */
  private void readAndStoreClientFiles()
  {
    System.out.println(">>ClientSoftwareAdministration: Reading all client software files.");
    try
    {
      Enumeration jarEntries = this.clientSoftwareLibrary.entries();
      String virtualTargetPath = null;
      String javaHome = System.getProperty("java.home");
      String userHome = System.getProperty("user.home");
      while( jarEntries.hasMoreElements() )
      {
        final JarEntry entry = (JarEntry)jarEntries.nextElement();
        virtualTargetPath = entry.getName();        
        // skip META-INF entries:
        if( ( virtualTargetPath.indexOf("META-INF") < 0 ) &&
            ( virtualTargetPath.indexOf("meta-inf") < 0 ) &&
            ( !entry.isDirectory()) /* do process only files */ )
        {
          System.out.println(">>Read and store file with virtual path: " + virtualTargetPath );
          InputStream jarInputStream = this.clientSoftwareLibrary.getInputStream(entry);
          byte[] fileBytes = this.readByteFileFromInputStream(jarInputStream);
          if( fileBytes != null )
          {
            this.clientFiles.put( virtualTargetPath,fileBytes ); // stored
            System.out.println(">>ClientSoftwareAdministration: Stored: " + virtualTargetPath );            
          }
          else
          {
            System.out.println(">>***ClientSoftwareAdministration: Unable to read and store " +
                                virtualTargetPath );
          }
        } // if its not meta-inf in it
      } // while
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }
    catch( Error err )
    {
      err.printStackTrace();
    }  
  } // readAndStoreClientFiles
  
  
  
  
  
  
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

  

  
  
 /** 
  *   Get the ClientInstallationFiles.zip over localhost tcp/ip from this server's ear
  *   and store it:
  */
  private JarFile retrieveClientSoftwareLibrary( final int httpPortNumber )
  {
    JarFile resultFile = null;
    System.out.println("ClientSoftwareAdministration: Retrieving local ClientInstallationFiles.zip");
    // Load the jar file on this server into a byte array:
    String localHostURL = "http://localhost:" + String.valueOf(httpPortNumber) +
                          "/eMayor2.0/ClientInstallationFiles.zip";
    try
    {
      URL documentURL = new URL(localHostURL);
      InputStream inputStream = documentURL.openStream();
      ByteArrayOutputStream buffer = DataUtilities.ReadByteFileFromInputStream(inputStream);
      byte[] fileBytes = buffer.toByteArray();
      // and save it:
      String clientSoftwareLibraryPath = clientSoftwareLibraryDirectoryPath + clientSoftwareLibraryName;
      DataUtilities.WriteBinaryFile(clientSoftwareLibraryPath,fileBytes);
      fileBytes = null; // helps GC on some systems
      resultFile = new JarFile( clientSoftwareLibraryPath );
    }
    catch( Exception ee )
    {
      System.out.println("*** ClientSoftwareAdministration: Unable to store ClientInstallationFiles.zip.");
      System.out.println("*** ClientSoftwareAdministration: Automatic client software installation will NOT work.");    
      ee.printStackTrace();
    }  
    return resultFile;
  } // retrieveClientSoftwareLibrary
  

  
  
  
 /** 
  *   Read all jar entries in the source jarfile ( == client software files )
  *   calculate the md5 checksums and filepath and md5 checksums to the checksumList.
  */
  private void addAllMD5ChecksumsTo( final Vector checksumList,
                                     final JarFile sourceJarFile )
  {
    System.out.println(">>ClientSoftwareAdministration: Creating MD5 informations for client software files");
    try
    {
      Enumeration jarEntries = sourceJarFile.entries();
      int errorCounter = 0;
      String virtualTargetPath = null;
      String javaHome = System.getProperty("java.home");
      String userHome = System.getProperty("user.home");
      while( jarEntries.hasMoreElements() )
      {
        final JarEntry entry = (JarEntry)jarEntries.nextElement();
        virtualTargetPath = entry.getName();        
        // skip META-INF entries:
        if( ( virtualTargetPath.indexOf("META-INF") < 0 ) &&
            ( virtualTargetPath.indexOf("meta-inf") < 0 ) &&
            ( !entry.isDirectory()) /* do process only files */ )
        {
          System.out.println(">>Preparing file with virtual path: " + virtualTargetPath );
          String md5Checksum = this.calculateMD5ChecksumFor(entry,sourceJarFile);
          // add it:
          ClientSoftwareFileMD5ChecksumEntry md5Entry = 
            new ClientSoftwareFileMD5ChecksumEntry(virtualTargetPath,md5Checksum);
          this.md5ChecksumList.addElement(md5Entry);
        } // if its not meta-inf in it
      } // while
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }
    catch( Error err )
    {
      err.printStackTrace();
    }  
  } // addAllMD5ChecksumsTo
  
  
  
  
 /** 
  *   Calculates the MD5 checksum for the file defined by the
  *   passed entry in the passed jarFile.
  */
  private String calculateMD5ChecksumFor( final JarEntry entry,
                                          final JarFile jarFile )
  {
    String checksum = "";
    try
    {
      InputStream jarInputStream = jarFile.getInputStream(entry);
      ByteArrayOutputStream outStream = DataUtilities.ReadByteFileFromInputStream(jarInputStream);
      byte[] fileBytes = outStream.toByteArray();
      if( fileBytes != null )
      {
        checksum = DataUtilities.GetMessageDigestForBytesAsString(fileBytes);
      }
      else
      {
        System.out.println("*** calculateMD5ChecksumFor() failed: null returned.");
      }
      jarInputStream.close();
      jarInputStream = null;
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }   
    return checksum;
  }
  
  
    
  
 /**
  *  Called from the softwareCheckForm
  *  @return the list with zip relative filepathes and md5 checksums of all client software files
  */ 
  public String getClientLibraryMD5CheckSumList()
  {
    StringBuffer text = new StringBuffer();
    for( int i=0; i < this.md5ChecksumList.size(); i++ )
    {
      ClientSoftwareFileMD5ChecksumEntry  entry =
        (ClientSoftwareFileMD5ChecksumEntry)this.md5ChecksumList.elementAt(i);
      String filePath = entry.getFilePath();
      String md5Checksum = entry.getMd5Checksum();
      // Convention: filepath=<filepath> md5Checksum=<md5Checksum>
      // and lines separated by newline characters:
      text.append("filepath=");
      text.append(filePath);
      text.append(" md5Checksum=");
      text.append(md5Checksum);
      text.append("\n");      
    } // for
    return text.toString();
  } // getClientLibraryMD5CheckSumList

  

  
  /**
   *  Called from the softwareCheckForm
   *  @return the file with the passed virtual path as byte array
   */ 
   public byte[] getFileWithVirtualPath( final String virtualPath )
   {
     byte[] fileBytes = (byte[])this.clientFiles.get(virtualPath);
     if( fileBytes != null )
     {
       System.out.println("ClientSoftwareAdministration.getFileWithVirtualPath() returns file for " +
                          virtualPath );
     }
     else
     {
       System.out.println("*** ClientSoftwareAdministration.getFileWithVirtualPath(): No file found for " +
                          virtualPath );
     }
     return fileBytes;
   }

   
   
  
 /**
  *  Singleton instance getter.
  */ 
  public static ClientSoftwareAdministration getInstance( final int httpPortNumber )
  {
    if( Instance == null ) Instance = new ClientSoftwareAdministration(httpPortNumber);
    return Instance;
  }


}



