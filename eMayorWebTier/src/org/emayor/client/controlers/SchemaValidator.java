package org.emayor.client.controlers;


/*
 *  Validates the model from time to time, when the user is not
 *  typing, or when it is forced to validate.
 *  It's a user thread, which runs on the background and consumes
 *  small CPU power, except when it is validating.
 * 
 * 
 *  Created on 11.08.2005,           jpl
 *  Changed September,October 2005   jpl
 *
 */

import java.awt.EventQueue;
import javax.swing.*;
import java.util.*;

import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory; 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser; 

import org.emayor.client.EMayorFormsClientApplet;
import org.emayor.client.LanguageProperties;
import org.emayor.client.parser.EMayorDocumentParser;
import org.emayor.client.parser.xml.*;


public class SchemaValidator extends Thread
{


  private final static long MinimumWaitTime = 1888;
  
  private boolean doTerminate = false; // the thread ends, when this is set
  
  // This is set in fireContentHasChanged() and cleared after a validation
  // has been processed.
  private boolean contentHasChanged = false;

  private Vector entries = new Vector(); // of SchemaValidatorEntry elements
  
  private EMayorDocumentParser eMayorDocumentParser = null;
  private XML_Node modelNode;
  private EMayorFormsClientApplet mainApplet;
  
  private SchemaValidatorOutputHandler outputHandler;
  
  private long lastValidationTime = 0;
  
  private LanguageProperties languageProperties;
 
  
  public SchemaValidator( final XML_Node _modelNode,
                          final EMayorDocumentParser _eMayorDocumentParser,
                          final EMayorFormsClientApplet _mainApplet,
                          final LanguageProperties _languageProperties )
  {
    this.modelNode = _modelNode;
    this.eMayorDocumentParser = _eMayorDocumentParser;
    this.mainApplet = _mainApplet;
    this.languageProperties = _languageProperties;
    this.outputHandler = new SchemaValidatorOutputHandler(this); 
  }

  
  
  public void run()
  {
    System.out.println("SchemaValidator: Thread has started.");        
    while( !this.doTerminate )
    {
      try
      {
        Thread.sleep(666);
        if( this.contentHasChanged )
        {
          // Wait always longer than MinimumWaitTime:
          if( System.currentTimeMillis() - this.lastValidationTime > MinimumWaitTime )
          {
            // First clear it, then validate:
            this.contentHasChanged = false; 
            this.doValidate(); // Validate the data and write validation information to the GUI
            this.lastValidationTime = System.currentTimeMillis();        
          }
        }  
      }
      catch( Exception ex )
      {
        System.out.println("SchemaValidator: Catched exception: " + ex.getMessage() );
      }
      catch( Error er )
      {
        System.out.println("SchemaValidator: Catched error: " + er.getMessage() );
      }   
    } // while loop
    System.out.println("SchemaValidator: Thread has ended.");
  } // run
 
  
  
  /**
   *  Called when the applet terminates.
   */
   public void terminateThread()
   {
     this.doTerminate = true;
   }
  
  
 /**
  *  This is called by the GUI, when the user has entered or changed some input.
  *  This validator then should start a validation somewhen later, when
  *  the user isn't typing frequently, or when it is forced to, because the
  *  user wants to post the input data.
  */ 
  public void fireContentHasChanged()
  {
    this.contentHasChanged = true;  
  } // fireContentHasChanged
  

  
  
  
  
 /**
  *  This is the main task of this thread.
  *  It validates the data and writes validation information to the GUI.
  */
  private void doValidate()
  {
    System.out.println("SchemaValidator.doValidate() starts.");
    // Get the model as string document [Note that this method is synchronized - no probs with EDT]
    XML_Node eDocumentNode = this.modelNode.getChildAt(0);
    final StringBuffer eDocument = this.eMayorDocumentParser.translateXMLTree( eDocumentNode,true );

    //System.out.println(" ");
    //System.out.println("input e-document is:");
    //System.out.println( eDocument.toString() );
    //System.out.println(" ");
    //System.out.println(" ");

    try 
    {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setValidating(true);
      factory.setNamespaceAware(true);
      // Parse the input, convert the string to an input stream, which 
      // is passed to the parser then:
      InputStream inputStream = new ByteArrayInputStream(eDocument.toString().getBytes("UTF-8"));
      SAXParser saxParser = factory.newSAXParser();
      XMLReader xmlReader = saxParser.getXMLReader();
      xmlReader.setFeature("http://apache.org/xml/features/validation/schema",  true);
      saxParser.parse( inputStream, this.outputHandler );
      //System.out.println("SchemaValidator.doValidate() completed.");
    } 
    catch( SAXParseException spe ) // Shouldn't ocur.
    {
      System.out.println(" ");     
      System.out.println(">SchemaValidator.doValidate(): Catched an exception of type:");
      System.out.println(">" + spe.getClass().getName() );
      System.out.println(">and with the message:");
      System.out.println(">" + spe.getMessage() );
      //System.out.println(">and the following stacktrace:");
      //ex.printStackTrace();
      System.out.println(" ");     
    }
    catch( Exception ex ) // should not ocur                                                                                         
    { 
      System.out.println(" ");     
      System.out.println(">SchemaValidator.doValidate(): Catched an exception of type:");
      System.out.println(">" + ex.getClass().getName() );
      System.out.println(">and with the message:");
      System.out.println(">" + ex.getMessage() );
      //System.out.println(">and the following stacktrace:");
      //ex.printStackTrace();
      System.out.println(" ");     
    }
    catch( Error err ) // should not ocur 
    { 
      System.out.println(" ");     
      System.out.println(">SchemaValidator.doValidate(): Catched an error of type:");
      System.out.println(">" + err.getClass().getName() );
      System.out.println(">and with the message:");
      System.out.println(">" + err.getMessage() );
      //System.out.println(">and the following stacktrace:");
      //err.printStackTrace();
      System.out.println(" ");     
    }
   
    System.out.println("SchemaValidator.doValidate() ends.");
  } // doValidate
  

  
  
  
  
  
  
  
  
 /**
  *  Shows user feedback for the validation result of the entry
  *  defined by the passed xpath.
  *
  *  It is called for initialization from this class, and after
  *  that from the SchemaOutputHandler of the SAXParser during
  *  the schema validation.
  *  
  *  If violationMessage is empty, the validation is ok, and
  *  just an ok icon is displayed and the label text is removed.
  * 
  *  Otherwise a red violation icon is displayed and the
  *  violationMessage is set as label text.
  * 
  *  Note: This method must be processed FAST, so it must
  *        be effectively implemented, otherwise the validating
  *        parser process is slowed down by this.
  *  Note: This method is processed in a user thread context.
  *        Swing calls therefore must be set into the
  *        EDT (Event Dispatch Queue)
  */ 
  public void processValidationResultFor( final String xPath,
                                          final String parserViolationMessage )
  {

    System.out.println(">parser schema violation: " + parserViolationMessage );
  
    // Search the associated entry:
    SchemaValidatorEntry entry;
    boolean isCorrect = ( parserViolationMessage.length() == 0 );
    String validationStateText = parserViolationMessage; // is empty, if no violation has detected
    String tooltipText = "";
    synchronized( this.entries )
    {
      for( int i=0; i < this.entries.size(); i++ )
      {
        entry = (SchemaValidatorEntry)this.entries.elementAt(i);
        if( entry.getXPath().equals(xPath))
        {
          // found - process validation for this one:
        
          //System.out.println("->");
          //System.out.println("-> SchemaValidator.processValidationResultFor: " + xPath );
          //System.out.println("-> with isCorrect= " + isCorrect );
          //System.out.println("-> with violationMessage= " + parserViolationMessage );
          //System.out.println("->");
          
          if( !isCorrect )
          {
            validationStateText = this.languageProperties.getTextFromLanguageResource("ParserResult.NotValid");
            tooltipText = this.languageProperties.getTextFromLanguageResource("ParserResult.FieldContainsNotAllowedCharacters");
          }
        
          entry.setValidationResult(isCorrect,validationStateText,tooltipText);
          break;
        }
      } // for
    } // sync    
  } // processValidationResultFor
  
  
   
  
  
  
  
  
  public void addEntry( final JComponent inputComponent,
                        final JLabel schemaValidationMessageLabel,
                        final XML_Node modelValueNode )
  {
    // Load the validation information icons from the resource:
    // Note: Do not share icon references, Swing might cause problems, so create
    // new instances:
    final ImageIcon notValidatedIcon = this.mainApplet.loadImageIcon("/pictures/applet/notvalidated.gif");
    final ImageIcon successIcon = this.mainApplet.loadImageIcon("/pictures/applet/success.gif");
    final ImageIcon failureIcon = this.mainApplet.loadImageIcon("/pictures/applet/failure.gif");

   SchemaValidatorEntry entry = 
        new SchemaValidatorEntry( inputComponent,schemaValidationMessageLabel,modelValueNode,
                                  successIcon,failureIcon);
    entries.addElement( entry );
    // Swing requires to put calls into the EDT (Event Dispatch Queue)
    if( EventQueue.isDispatchThread() ) // direct call is ok
    {
      // Set the schemaValidationMessageLabel icon as notValidatedIcon initially:
      schemaValidationMessageLabel.setIcon( notValidatedIcon );
    }
    else
    {
      EventQueue.invokeLater( new Runnable()
      {
        public void run()
        {
          // Set the schemaValidationMessageLabel icon as notValidatedIcon initially:
          schemaValidationMessageLabel.setIcon( notValidatedIcon );
        }
      });
    }
  }
  


  
  
  
  
  
  
} // SchemaValidator
