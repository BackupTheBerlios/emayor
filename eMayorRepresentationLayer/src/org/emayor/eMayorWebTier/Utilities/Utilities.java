package org.emayor.eMayorWebTier.Utilities;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;


  /**
   *  Some static utility methods.
   * 
   *  27.Mai.2005    Joerg Plaz
   *  
   */


public class Utilities 
{

  public static final boolean IsDebugMode = true;	
	
	
  public final static void PrintLn( final String s )
  {
  	if( IsDebugMode ) System.out.println( s );  
  }

 
  
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

  
  
  
  
  
  /**
   *  Replaces  a String where characters, which are not allowed for
   *  the Windows filesystem are replaced by underscores.
   *  Should be ok for other OS's too...?
   *  Windows: Invalid characters for the filesystem are:   \  /  :  *  ?  "  <  >  |
   */
   public static String MakeNameValidForFileSystem( final String rawName )
   {
     StringBuffer name = new StringBuffer(rawName); 
     char ch;
     for( int i=0; i < name.length(); i++ )
     {
       ch = name.charAt(i);
       if( ( ch == '\\') ||
           ( ch == '/' ) ||
           ( ch == ':' ) ||
           ( ch == '*' ) ||
           ( ch == '?' ) ||
           ( ch == '\"') ||
           ( ch == '<' ) ||
           ( ch == '>' ) ||
           ( ch == '|' )    )
       {
         name.setCharAt(i,'_');
       }
     } // for
     return name.toString();
   } // makeNameValidForFileSystem
  
  

   
   
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
      byte[] md5 = GetMessageDigestForFile(documentFilePath);
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
  } // debugFileWriteTo

  

  
  
  
  
  
  
  public static void RemovePrologFrom( final StringBuffer eDocument )
  {
    boolean isPrologRemoved = false;
    // Go to the first occurence of a "<" = Bra character :
    int firstBraPosition = eDocument.indexOf("<");
    // Leading spaces anyway are not allowed for valid xml,
    // so remove them:
    if( firstBraPosition > 0 ) eDocument.delete(0,firstBraPosition);
    // Now the first bra is at index zero.
    // If it's a prolog, the character "?" must be at 1:
    if( eDocument.length() > 3 )
    {
      char ch = eDocument.charAt(1);
      if( ch == '?' ) // prolog found - remove it:
      {
        int prologEndControlIndex = eDocument.indexOf("?>");
        if( prologEndControlIndex > 0 )
        {
          eDocument.delete(0,prologEndControlIndex+2);
          // Don't create leading CR's or spaces - the xml
          // document must start with a bra too:
          while( (eDocument.length() > 0 ) && (eDocument.charAt(0) != '<') )
          {
            eDocument.delete(0,1);
          }
          isPrologRemoved = true;
        }     
      }
    }
    // Feedback:
    System.out.println(">>");
    System.out.println(">>removePrologFrom() result:");
    String txt = (isPrologRemoved) ? "Prolog found and removed." : "No prolog found.";
    System.out.println(">>" + txt);
    System.out.println(">>");
  } // removePrologFrom
  
  
  
  
  
 /** 
  *  Scan for spaces after weakLimit characters on a line,
  *  and if found one, insert a newline character.
  *  If hardLimit characters are reached on a line,
  *  a newline character is inserted in any case.
  *  This way, very long lines are prevented.
  */
  public static String InsertNewLinesInText( final String inputText,
                                             final int weakLimit,
                                             final int hardLimit )
  {
    StringBuffer outText = new StringBuffer();
    int charCounter = 0;
    for( int i=0; i < inputText.length(); i++ )
    {
      char c = inputText.charAt(i);
      outText.append(c);
      charCounter++;
      if( c == '\n' )
      {
        charCounter = 0;
      }
      else
      {
        if( charCounter > weakLimit )
        {
          if( c == ' ' )
          {
            outText.append('\n');
            charCounter = 0;
          }
        }
        if( charCounter > hardLimit )
        {
          outText.append('\n');
          charCounter = 0;      
        }
      }  
    }    
    return outText.toString();
  } // InsertNewLinesInText
  

  
  
  
} // Utilies

