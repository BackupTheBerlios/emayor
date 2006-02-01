package org.emayor.RepresentationLayer.ClientTier.Logic;

/**
 *
 *   This class is used for letting the client user save or load
 *   eMayorForms content to/from disk.
 * 
 *   NOTE
 *   ----
 *   The used java methods do NOT BREAK the java sandbox.
 *   NO DISK ACCESS rights are required for this. 
 *   The disk access is legitimate - it is under the control
 *   of the user and the JRE, but NOT by this application.
 *   ( See the new sandbox compliant JNLP disk access methods -> SUN documents...)
 * 
 *   - 4. August 2005, created, J.Plaz
 * 
 */

import java.io.*;

import javax.swing.*;
import javax.jnlp.*;
import com.sun.jnlp.FileOpenServiceImpl;
import com.sun.jnlp.FileSaveServiceImpl;


public class SandboxCompliantDiskManger
{
  
  private JApplet parentApplet;
  

  public SandboxCompliantDiskManger( final JApplet _parentApplet )
  {
     this.parentApplet = _parentApplet;
  }

  
  
  public void saveData( final String stringData, String fileExtension )
  {
    try 
    {
      // Note: The recommended method, using this call:
      // FileSaveService fs = (FileSaveService)ServiceManager.lookup("javax.jnlp.FileSaveService");
      // does not work for JApplets. It only works for JNLP clients. So get it directly:
      FileSaveService fs = FileSaveServiceImpl.getInstance();      
      FileContents fileContents = 
         fs.saveFileDialog( "/eMayorForms",new String[]{fileExtension},
                            new ByteArrayInputStream( stringData.getBytes("UTF-8") ),
                            "ServiceRequest.eMayorForm" );
      if( fileContents != null )
      {
        System.out.println("file was saved to: " + fileContents.getName() );
        System.out.println("saved data was:");
        System.out.println(stringData);
      }
      else
      {
        System.out.println("file was NOT saved.");   
      }
    } 
    catch( Exception e ) 
    {
      e.printStackTrace();
    }  
  } // saveData

  
  
  
  public String loadData()
  {
    String result = null; // The return value
    try 
    {
      // Note: The recommended method, using this call:
      // FileOpenService fs = (FileOpenService)ServiceManager.lookup("javax.jnlp.FileOpenService");
      // does not work for JApplets. It only works for JNLP clients. So get it directly:
      FileOpenService fs = FileOpenServiceImpl.getInstance();      
      FileContents fileContents = fs.openFileDialog("/eMayorForms",new String[]{"eMayorForm"});
      if(fileContents != null)
      {
        InputStream inputStream = fileContents.getInputStream();
        BufferedInputStream bufIn = new BufferedInputStream( inputStream );
        ByteArrayOutputStream byteArrayBuffer = new ByteArrayOutputStream(0);
        final byte[] byteBuffer = new byte[3200];
        int bytesRead;
        while(  (bytesRead = bufIn.read(byteBuffer)) != -1 )
         {
           byteArrayBuffer.write( byteBuffer,0,bytesRead );
         }
        bufIn.close();
        result = byteArrayBuffer.toString("UTF-8");
        System.out.println("file has been read. returning:");
        System.out.println(result);
      }
      else
      {
        System.out.println("file has NOT been read.");
     }
    } 
    catch( Exception e ) 
    {
      e.printStackTrace();
    }
    return result;
  } // loadData




} // SandboxCompliantDiskManger
