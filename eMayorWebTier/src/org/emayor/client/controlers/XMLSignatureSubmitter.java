package org.emayor.client.controlers;


/**
 *  Specialized action controler for buttons.
 *  It calls the XMLSigner_eMayorVersion for getting
 *  an xml signature of the xml model, which is added
 *  to the xml model root node. 
 *  It then submits the xml model with signature to the host, from where
 *  the applet has been loaded.
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

import org.emayor.client.EMayorFormsClientApplet;
import org.emayor.client.parser.XMLParser;
import org.emayor.client.parser.xml.*;
import org.emayor.client.parser.XMLPath;

import org.emayor.client.Utilities.DataUtilities;
import org.emayor.client.controlers.xmlsigner.XMLSigner_eMayorVersion;
import org.emayor.client.controlers.xmlsigner.SignerException;



public class XMLSignatureSubmitter implements ActionListener 
{

	  private XML_Node modelNode = null;
	  private EMayorFormsClientApplet mainApplet;
	  private String buttonParameter = null;
	
	
  public XMLSignatureSubmitter( final XML_Node _modelNode,
                                final EMayorFormsClientApplet _mainApplet,
								final String _buttonParameter )
  {
    this.modelNode = _modelNode;
    this.mainApplet = _mainApplet;
    this.buttonParameter = _buttonParameter;
    // Note: The modelNode and the contained tree will be changed
    //       as long as the user is entering or modifying data.
    //       Therefore do not do anything here. Any action only
    //       start in the actionPerformed() method.     
  } // Constructor

	  
	

  /**
   *  The only method from the ActionListener interface.
   *  It is called as soon as a user clicks a button,
   *  which this object is listening to.
   * 
   *  Because this method takes some time ( >> 0.1sek ) we
   *  change to a user thread and give the EDT free.
   */
   public void actionPerformed(ActionEvent e)
   {
     // After this button has been pressed, disable the applet GUI,
     // otherwise the user can click on it again, which would
     // cause troubles. This task will change to another site
     // anyway, so the applet isn't used anymore after this.
     this.mainApplet.setEnabled(false);
     this.mainApplet.getContentPane().setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
     // Carry out the task outside the EDT:
     Thread thread = new Thread()
     {
       public void run()
       {
         try{ Thread.sleep(99); } catch( Exception ee ){} // Time for Swing
         performTaskInUserThread();
       }
     };
     thread.start();
   }
   
   
   
   
  /**
   *  Called by actionPerformed() in
   *  a user thread context. Outside the EDT.
   */ 
   private void performTaskInUserThread()
   {   
     // Select the child of the model node, because the <model> tag
     // itself is not part of the e-document. So the first (and only)
     // childnode of the model node is the e-document, and this one
     // must exist in any case:
     if( this.modelNode.getNumberOfChildren() > 0 ) // actually it must be exactly 1
     {
       System.out.println("-----------Replacing the local schema location with the original one--------");
       // reinsert the original schema location to the e-document:
       this.mainApplet.getDocumentProcessor().setLocalSchemaLocation(false);

       XML_Node modelContentNode = this.modelNode.getChildAt(0);
       XMLParser xmlParser = new XMLParser();
       StringBuffer xmlModelDocument = xmlParser.transformTreeToDocument( modelContentNode,true );       
       this.signAndSubmit( xmlModelDocument.toString() );
     }
     else
     {
       EventQueue.invokeLater( new Runnable()
       {
         public void run()
         {
           String message = "Unable to post the document to the server. (Empty document)";
           JOptionPane.showMessageDialog(mainApplet,message);      	 		
         }
       });
     }
   } // actionPerformed     
       
   
   
   private void signAndSubmit( final String eDocument )
   {   
     System.out.println("XMLSignatureSubmitter.signAndSubmit() starts...");
     
     // Debug file write:
     // DataUtilities.DebugFileWriteTo(eDocument,"applet_document_before_signing.xml");
     
     try
	 {
       // Calculate the digital signature for the xmlDocument:
       XMLSigner_eMayorVersion signer = new XMLSigner_eMayorVersion(this.mainApplet);
       String signedDocument = signer.signTheDocument( eDocument );       
       boolean succeeded = false;
       if( signedDocument != null )
       {
         // If its empty, do nothing == the user has cancelled this:
         if( signedDocument.length() > 0 )
         {
            // Debug file write:
            // DataUtilities.DebugFileWriteTo(signedDocument,"applet_document_after_signing.xml");
            // post this to the server:
           succeeded = this.mainApplet.postDocument( signedDocument,this.buttonParameter );
         }
       }
       if( !succeeded )
       {
       	 EventQueue.invokeLater( new Runnable()
         {
       	 	public void run()
       	 	{
              String message = "Unable to post the document to the server. (null document)";
              JOptionPane.showMessageDialog(mainApplet,message);      	 		
       	 	}
         });
       }
	 }
     catch( SignerException se )
	 {
 	    final String message = "Problem with the xml signer: " + se.getMessage() +
                               "\nDo you want to send it without signature ?";
  	    System.out.println("*** " + message);
     	se.printStackTrace();
      	EventQueue.invokeLater( new Runnable()
      	{
      	   public void run()
      	   {
             if( JOptionPane.showConfirmDialog( mainApplet,
                 message, "Develop Info",
                 JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION )
             {
                mainApplet.postDocument( eDocument,buttonParameter );
             }
      	   }
      	});     	
	 }
     catch( java.security.AccessControlException aex )
	 {
     	// Security priviledge violation - the applet has not the required rights
     	// Should never occure, except in development
 	    final String message = "The applet has not the required rights for connecting with a smartcard reader." +
                               "\nDo you want to send it without signature ?";;
  	    System.out.println("*** " + message);
     	aex.printStackTrace();
      	EventQueue.invokeLater( new Runnable()
      	{
      	   public void run()
      	   {
                if( JOptionPane.showConfirmDialog( mainApplet,
                    message, "Develop Info",
                    JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION )
                {
                   mainApplet.postDocument( eDocument,buttonParameter );
                }
      	   }
      	});
	 }
     catch( java.lang.UnsatisfiedLinkError linkErr )
	 {
        // no pkcs11wrapper in java.library.path - cardreader software not installed.
 	    final String message = "The applet could not connect to a smartcard reader software."+ 
                               "\nPlease install the smart card reader first." + 
                               "\nThis is required for posting data to the server." +
                               "\n(pkcs11wrapper.dll not found)" +
                               "\nDo you want to send it without signature ?";;
  	    System.out.println("*** " + message);
  	  linkErr.printStackTrace();
      	EventQueue.invokeLater( new Runnable()
      	{
      	   public void run()
      	   {
                if( JOptionPane.showConfirmDialog( mainApplet,
                    message, "Develop Info",
                    JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION )
                {
                   mainApplet.postDocument( eDocument,buttonParameter );
                }
      	   }
      	});
	 }
     catch( Exception ex )
     {
     	// shouldn't occure
	    final String message = "An exception has occured. The data coudln't be sent to the server.";
  	    System.out.println("*** " + message);
     	ex.printStackTrace();
      	EventQueue.invokeLater( new Runnable()
     	{
     	   public void run()
     	   {
     	     JOptionPane.showMessageDialog( mainApplet,message);
     	   }
     	});
	 }
     catch( Error err )
     {
     	// shouldn't occure
 	    final String message = "An error has occured. The data coudln't be sent to the server.";
        System.out.println("*** " + message);
     	err.printStackTrace();
     	EventQueue.invokeLater( new Runnable()
     	{
     	  public void run()
     	  {
     	    JOptionPane.showMessageDialog( mainApplet,message);
     	  }
     	});
	 }
   } // signAndSubmit

  
 
   
   
	  
} // XMLSignatureSubmitter


