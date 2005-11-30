package org.emayor.client.Utilities;


  /**                              
   *  Contains general utility classmethods
   *  for data access, transformations etc.
   */

import java.io.*;
import java.security.MessageDigest;

public class DataUtilities
{


                                  
  public static synchronized ByteArrayOutputStream ReadByteFileFromInputStream( final InputStream inputStream ) throws IOException
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
       byteArrayBuffer.write( byteBuffer,0,bytesRead );
     }
    bufIn.close();
    return byteArrayBuffer;
  }

  
  
  public static final String TranslateUnicodeShortcutsInLine( String line )
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
    	//System.out.println("Translated -----------------------> " + text );
    }
    return line;
  }

  
  
  public static byte[] GetMessageDigestForFile( final String filePath ) throws Exception
  {
    byte[] digest = new byte[0];
    File file  = new File( filePath );
    if( file.exists() )
     {
       BufferedInputStream fileInputStream = null;
       try
        {
          FileInputStream fileIn = new FileInputStream(file);
          fileInputStream = new BufferedInputStream(fileIn);
          ByteArrayOutputStream byteOutputStream = ReadByteFileFromInputStream( fileInputStream );
          byte[] fileBytes = byteOutputStream.toByteArray();
          MessageDigest algorithm = MessageDigest.getInstance("MD5");
          algorithm.reset();
          algorithm.update( fileBytes );
          digest = algorithm.digest();
        }
       catch( Exception e )
        {
          throw new Exception( e );
        }                         
       finally
        {       
          try{ fileInputStream.close(); } catch( Exception ee ){ /*give up*/ }
        }
     }
    else
     {
       throw new Exception("GetMessageDigestForFile: " + filePath + " doesn't exist.");
     }
    return digest;
  } // GetMessageDigestForFile



  
  public static byte[] GetMessageDigestForBytes( final byte[] sourceBytes )
  {
    byte[] digest = new byte[0];
    try
    {
          MessageDigest algorithm = MessageDigest.getInstance("MD5");
          algorithm.reset();
          algorithm.update( sourceBytes );
          digest = algorithm.digest();
    }
    catch( Exception e )
    {
       e.printStackTrace();
    }                         
    return digest;
  } // GetMessageDigestForBytes
  
  
  
  public static String GetMessageDigestForBytesAsString( final byte[] sourceBytes ) throws Exception
  {
    byte[] bytesResult = GetMessageDigestForBytes(sourceBytes);
    // Show them as numbers:
    StringBuffer buffer = new StringBuffer();
    for( int i=0; i < bytesResult.length; i++ )
    {
      buffer.append( String.valueOf(bytesResult[i]) );
    }
    return buffer.toString();
  }
  

  public static boolean GetAreMessageDigestsEqual( byte[] digest1, byte[] digest2 )
  {
    boolean equal = false;
    if( ( digest1 == null ) && ( digest2 == null ) )
     {
       equal = true;
     }
    else
     {
       if( ( digest1 != null ) && ( digest2 != null ) )
        {
          if( digest1.length == digest2.length )
           {
             equal = true;
             for( int i=0; i < digest1.length; i++ )
              {
                if( digest1[i] != digest2[i] )
                 {
                   equal = false;
                   break;
                 }
              }
           }
        }
     }
    return equal;
  }
  

  
  public static void DebugFileWriteTo( final String document,
                                       final String fileName )
  {
    String documentFilePath = System.getProperty("user.home") + "/" + fileName;
    System.out.println("Write document: " + documentFilePath );
    try
    {
      File docFile = new File(documentFilePath);
      FileOutputStream fOut = new FileOutputStream(docFile);
      fOut.write( document.getBytes("UTF-8") );
      fOut.close();
      docFile = null;
      System.out.println("document has been written successfully.");
    }
    catch( Exception e )
    {
      System.out.println("*** document couldn't be written. Exception:");
      e.printStackTrace();
    }
    
    try
    {
      byte[] md5 = DataUtilities.GetMessageDigestForFile(documentFilePath);
      System.out.println("MD5 hash for file is: ");
      for( int i=0; i < md5.length; i++ )
      {
        System.out.print( md5[i] );
      }
    }
    catch( Exception e )
    {
      System.out.println("*** Unable to write md5 hash for the cod file.");
      e.printStackTrace();
    }     
  }
  
  

  
  
  
  private void test()
  {
     StringWriter w;  
     ByteArrayOutputStream b;
  }

} // FileUtilities
