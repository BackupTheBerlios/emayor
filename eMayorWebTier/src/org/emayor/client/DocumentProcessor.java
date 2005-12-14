package org.emayor.client;


  /**
   *  DocumentProcessor parses the xml content of the passed
   *  eMayorFormsDocument ( in processDocument ) and
   *  adds all view components described in this document
   *  to the appletContentPane. Input controlling objects are created
   *  and launched too.
   * 
   *  August 2005, created jpl
   * 
   */


import java.awt.*;
import javax.swing.*;

import org.emayor.client.parser.EMayorDocumentParser;
import org.emayor.client.parser.XMLPath;
import org.emayor.client.parser.xml.*;
 
import org.emayor.client.Utilities.BooleanString;



public class DocumentProcessor
{

  private JPanel formsInteractionPanel;     // holds the user interaction
  private JTextArea formsModelTextArea;     // xml forms model

  private EMayorDocumentParser eMayorDocumentParser = null;

  private XML_Node modelNode = null;
  private XML_Node viewNode = null;

  private EMayorFormsClientApplet mainApplet;

  private LanguageProperties languageProperties;
  private EnumerationProperties enumerationProperties;

  private GUIBuilder guiBuilder = null;
 
  private String language = "en"; // the language for the UI
  
  
  // Must use java.home, because this is most problably
  // a path without special non ascii characters, for which
  // the parsers cannot find schema location paths.
  private String javaHomeDirectory = System.getProperty("java.home");

  private String userHomeDirectory = System.getProperty("user.home");
  
  // Purpose of localSchemaLocationPath and originalSchemaLocationPath:
  // The e-documents have any schemalocation path, when they are received
  // from the webtier.
  // But on the client, the schema locations are present on a known
  // location in java.home.
  // Therefore, the original schema location path is overwritten by the
  // local schema location path as long as the client user interaction is
  // running and validations are made in the validator thread.
  // BUT once the user tells the client to sign and post the document,
  // the original schema location is set, before signing and posting are
  // processed, so the webtier will again receive that e-document with
  // unchanged schema location. This way, setting the schema location
  // is left free for the business logic, which does the hard validation
  // for the e-document.
  // The process of calculating and inserting the local or 
  // reinserting the original schema location into
  // the xml tree is done by the method setLocalSchemaLocation( boolean )
  // in this class.
  private String localSchemaLocationPath = "";
  private String originalSchemaLocationPath = "";
  
  
  private boolean produceVersionForPrinting;  
  
  
  public DocumentProcessor( final JPanel _formsInteractionPanel,
                            final JTextArea _formsModelTextArea,
							final LanguageProperties _languageProperties,
                            final EnumerationProperties _enumerationProperties,
                            final String _language,
                            final EMayorFormsClientApplet _mainApplet,
                            final boolean _produceVersionForPrinting )
  {
    this.formsInteractionPanel = _formsInteractionPanel;
    this.formsModelTextArea = _formsModelTextArea;
    this.languageProperties = _languageProperties;
    this.enumerationProperties = _enumerationProperties;
    this.language = _language;
    this.mainApplet = _mainApplet;
    this.produceVersionForPrinting = _produceVersionForPrinting;
    this.eMayorDocumentParser = new EMayorDocumentParser(_languageProperties);
    System.out.println("DocumentProcessor: java home is: " + this.javaHomeDirectory );
  } // Constructor




  public void processDocument( final StringBuffer raw_eMayorFormsDocument ) throws Exception
  {
    String doc = raw_eMayorFormsDocument.toString();
  	
    System.out.println("DocumentProcessor.processDocument() starts.");
    // Debug file write:
    // DataUtilities.DebugFileWriteTo(doc,"applet_DocumentProcessor_Constructor.xml");
        
    // Get the Model and the View xml subtrees from this document:
    XML_Node[] nodes = this.eMayorDocumentParser.translate_eMayorFormsDocument( new StringBuffer(doc) );
    this.modelNode = nodes[0]; // contains the xml model
    this.viewNode  = nodes[1]; // contains the xml view, with controler directives
                              
    // Remove existing signatures not made by eMayor (develop test signatures):
    // The signature is calculated and inserted when the e-document
    // is submitted to the webtier.
    this.removeExistingForeignSignaturesIn(this.modelNode);

    // and set the local schema location path for the client environment,
    // so the validating parser finds the schema files stored in the
    // local filesystem in the used JRE java.home directory.
    this.setLocalSchemaLocation(true);
        
    // Build the interaction GUI - this also starts the Validator thread.
    this.guiBuilder = new GUIBuilder( this.modelNode, this.viewNode, 
                                      this.mainApplet, this.languageProperties, this.enumerationProperties,
                                      this.language,this.eMayorDocumentParser );
    this.guiBuilder.buildGraphicalUserInterfaceOn( this.formsInteractionPanel,this.produceVersionForPrinting );
    // Switch to EDT for Swing:
    EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {
        formsInteractionPanel.updateUI();
      }
    });
    System.out.println("DocumentProcessor.processDocument() has ended.");
  } // processDocument



  
  
  public void updateTheModelTabContent()
  {
    final StringBuffer reverse_Model_Document = this.eMayorDocumentParser.translateXMLTree( modelNode,false );
    EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {   
        formsModelTextArea.setText(""); // clear
        formsModelTextArea.append( "\nReversed generated model document:\n\n" );
        formsModelTextArea.append( reverse_Model_Document.toString() + "\n\n" );
      }
    });      
  } // updateTheModel
  

  
  
  
 /**
  *   Used by the SandboxCompliantDiskmanager for saving the
  *   current eMayorForm model on disk.
  *   Lock this documentProcessor instance while this is carried out.
  */ 
  public synchronized String get_EMayorFormModelAsString()
  {
    // Get rid of the model tag too for this:
    XML_Node eDocumentNode = modelNode.getChildAt(0);  
    final StringBuffer reverse_Model_Document = this.eMayorDocumentParser.translateXMLTree( eDocumentNode,true );
    return reverse_Model_Document.toString();
  } // getEMayorFormModelAsString
  
  
  
  

  
 /**
  *   Used by the SandboxCompliantDiskmanager for loading 
  *   eMayorForm model data from disk and inserting the
  *   loaded model contents into the current eMayor form.
  *   Only tag contents, which the current and the loaded
  *   model tree's have in common take part in this.
  */ 
  public void setEMayorFormModelContentFrom( String inputStringModel )
  {
    // One cannot just insert the passed model into this documents model
    // (...)
    // The method is:
    // 1) Translate the passed inputStringModel into a tree =: inputModelTree
    // 2) Check, if the root tag names are equal.If they are not, one must
    //    abort this process, because the e-documents are not the same.
    // 3) Traverse the inputModelTree, search the element with the same
    //    xpath address in the current tree and, if found, move the content
    //    from the inputModelTree element to the current tree element.
    //
    //    This method shows a good behaviour, when e-document tree structures
    //    are changed: It still works, but of course simply does skip new or
    //    changed entries, until the form is saved with the new structure.
    //    So: No format problems disk files <-> e-documents.
 
    System.out.println("DocumentProcessor.setEMayorFormModelContentFrom()");
    /*
    System.out.println("");
    System.out.println("got the following input string model: ");
    System.out.println("");
    System.out.println(inputStringModel);
    System.out.println("");
    System.out.println("");
    */
    
    try
    {
      XML_Node inputModelNode = this.eMayorDocumentParser.translateDocument( new StringBuffer(inputStringModel) );
      // The eMayorDocumentParser always puts a RootNode as root, so
      // take the [only] child, which is the model node:
      inputModelNode = inputModelNode.getChildAt(0);
      
      String inputModelDocumentName = inputModelNode.getChildAt(0).getTagName();
      String currentModelDocumentName = this.modelNode.getChildAt(0).getTagName();
      
      System.out.println("inputModelDocumentName is " + inputModelDocumentName );
      System.out.println("currentModelDocumentName is " + currentModelDocumentName );
 
      if( inputModelDocumentName.equals(currentModelDocumentName) )
      {
        // Recursive worker:
        this.traverseAndUpdateValues( this.modelNode, inputModelNode );
        // Remove existing signatures from others:
        // The signature is calculated and inserted when the e-document
        // is submitted to the webtier.
        this.removeExistingForeignSignaturesIn(this.modelNode);
        
        // Tell the GuiBuilder it should update the whole view:
        this.guiBuilder.fireModelChanged( this.formsInteractionPanel );
        
        /*
        System.out.println("-------- Model Tree after loading from disk: ---------- ");
        System.out.println("");
        System.out.println( this.getEMayorFormModelAsString() );
        System.out.println("");
        */
        
      }
      else
      {
        // Multilingual translation missing for the following...
        String title = "Different input forms";
        String message = "You cannot load a " + inputModelDocumentName + 
                         " input form \ninto a " + currentModelDocumentName + " input form.";
        JOptionPane.showMessageDialog( this.mainApplet, message, title, JOptionPane.WARNING_MESSAGE );
      }
    }
    catch( Exception e )
    {
      // Multilingual translation missing for the following...
      String title = "Invalid input form data";
      String message = "The loaded input form data couldn't be inserted to the form.";
      JOptionPane.showMessageDialog( this.mainApplet, message, title, JOptionPane.WARNING_MESSAGE );
      e.printStackTrace();
    }
  } // setEMayorFormModelContentFrom
  
  

  
  
 /**
  *   Recursive worker, used by setEMayorFormModelContentFrom() above.
  */ 
  private void traverseAndUpdateValues( final XML_Node targetNode,
                                        final XML_Node sourceNode )
  {
    for( int i=0; i < targetNode.getNumberOfChildren(); i++ )
    {
      XML_Node targetNodeChild = targetNode.getChildAt(i);
      // See, if the sourceNode has a child with the same tagName:
      XML_Node sourceNodeChild = this.getChildWithTagName( sourceNode,targetNodeChild.getTagName() );
      if( sourceNodeChild != null )
      {
        if( sourceNodeChild.getTagValue() != null )
        {
          targetNodeChild.setTagValue( sourceNodeChild.getTagValue() );
        }
        // and recur:
        if( targetNodeChild.getNumberOfChildren() > 0 )
        {
          this.traverseAndUpdateValues( targetNodeChild, sourceNodeChild );
        }
      } // else nothing to do there  
    } // for

  } // traverseModelAndUpdateWithInputModel
  

  
  

  
  
 /**
  *  e-documents received from the webtier shouldn't contain signatures.
  *  Signatures are calculated and inserted only before the e-document
  *  is sent back to the server.
  *  However some test e-documents can carry dummy signatures.
  *  This method is used for removing such signatures during the load
  *  process of an e-document.
  * 
  *  Recursive method
  */ 
  private void removeExistingForeignSignaturesIn( final XML_Node modelRootNode )
  {
    //System.out.println(" ");
    //System.out.println("DocumentProcessor.removeExistingForeignSignaturesIn(): node: " + modelRootNode.getTagName() );
    if( modelRootNode.getNumberOfChildren() > 0 )
    {
      try
      {
        XML_Node eDocumentNode = modelRootNode.getChildAt(0);
        XML_Node childNode;
        int numberOfChildren = eDocumentNode.getNumberOfChildren();
        int i=0;
        while( i < numberOfChildren )
        {
          childNode = eDocumentNode.getChildAt(i);
          // Remove existing signature subtree's:
          if( childNode.getTagName().equals("ds:Signature") )
          {
            //System.out.println("DocumentProcessor: Examine signature");
            boolean signatureIsForeign = true;
            // Search the <ds:CanonicalizationMethod> tag:
            XML_Node methodNode = null;
            XML_Node signedInfoNode = null;
            signedInfoNode = childNode.getChildByName("ds:SignedInfo");
            if( signedInfoNode != null )
            {
              methodNode = signedInfoNode.getChildByName("ds:CanonicalizationMethod");
            }
            if( methodNode != null ) // must exist for eMayor signatures
            {
              String method = methodNode.getAttributeValueForName("Algorithm");
              if( (method != null) && (method.startsWith("http://www.w3.org/" )) )
              {
                signatureIsForeign = false; // its from us, don't remove that
                System.out.println(" ");
                System.out.println("DocumentProcessor: signature method tag value: " + method);
                System.out.println("DocumentProcessor: Found an initially present signature in the e-document.");
                System.out.println("DocumentProcessor: However, this one was made by the eMayor clienttier,");
                System.out.println("DocumentProcessor: and therefore will NOT be removed.");
                System.out.println(" ");
              }
              else
              {
                System.out.println(" ");
                System.out.println("DocumentProcessor: signature method tag value: " + method);
                System.out.println("DocumentProcessor: signature is foreign.");          
              }
            }
            else
            {
              System.out.println(">> Signature has no method node -> It will be removed.");          
            }
            if( signatureIsForeign )
            {
              eDocumentNode.removeChildNodeAt(i);
              numberOfChildren--;
              System.out.println("DocumentProcessor: Found and removed an initially present foreign signature in the e-document.");
              System.out.println(" ");
            }
            else
            {
              i++;
            }
          }
          else
          {
            i++;
          }
        } // for    
      }
      catch( Exception e )
      {
        System.out.println("*** DocumentProcessor: Unable to check and remove existing signatures.");
        e.printStackTrace();
      }      
      // recur:
      int i=0;
      while( i < modelRootNode.getNumberOfChildren() )
      {
        XML_Node child = modelRootNode.getChildAt(i);
        this.removeExistingForeignSignaturesIn(child);
        i++;
      }
    } // if has children
    //System.out.println(" ");
  } // removeExistingSignatures

  

  

  
  
  /**
   *  The e-documents have any schemalocation path, when they are received
   *  from the webtier.
   *  But on the client, the schema locations are present on a known
   *  location in java.home.
   *  Therefore, the original schema location path is overwritten by the
   *  local schema location path as long as the client user interaction is
   *  running and validations are made in the validator thread.
   *  BUT once the user tells the client to sign and post the document,
   *  the original schema location is set, before signing and posting are
   *  processed, so the webtier will again receive that e-document with
   *  unchanged schema location. This way, setting the schema location
   *  is left free for the business logic, which does the hard validation
   *  for the e-document.
   *  The process of calculating and inserting the local or 
   *  reinserting the original schema location into
   *  the xml tree is done by the method setLocalSchemaLocation( boolean )
   *
   *  setLocal = true ->  calculate and set the local schema location into the 
   *                      xml e-document tree. The calculation is done based on
   *                      the present [original] schema location, which is saved
   *                      before, so it can be restored by a call with setLocal = false.
   * 
   *  setLocal = false -> set the [previously stored] original schema location
   *                      into the xml e-document tree
   */ 
   public void setLocalSchemaLocation( final boolean setLocal )
   {
    System.out.println( "DocumentProcessor.setLocalSchemaLocation called with setLocal= " + setLocal );
    if( setLocal )
    {
      String currentSchemaLocationPath = "";
      BooleanString bs = XMLPath.GetSchemaLocationPathForModel(this.modelNode);
      if( bs.getIsTrue() )
      {
        currentSchemaLocationPath = bs.getValue();
      }
      else
      {
        String errorDescription = bs.getValue();
        this.mainApplet.getErrormanager().addErrorMessage( errorDescription, true );        
      }
      // store this one for a later call with setLocal = false:
      this.originalSchemaLocationPath = currentSchemaLocationPath;
      // If no schema is specified, an empty string will be returned. 
      // This is allowed, because schemas are optional.
      System.out.println("raw SchemaLocationPath= " + currentSchemaLocationPath );
      // replace backslashes by slashes:
      currentSchemaLocationPath = currentSchemaLocationPath.replace('\\','/');
      // and important: replace any white spaces by real ' ' spaces:
      // Note that  newline,cr and lf's are white spaces too.
      StringBuffer currentSchemaLocationPathBuffer = new StringBuffer(currentSchemaLocationPath);
      for( int i=0; i < currentSchemaLocationPathBuffer.length(); i++ )
      {
        if( Character.isWhitespace( currentSchemaLocationPathBuffer.charAt(i) ) )
        {
          currentSchemaLocationPathBuffer.setCharAt(i,' '); 
        }
      }
      // continue with the string:      
      currentSchemaLocationPath = currentSchemaLocationPathBuffer.toString();
            
      // Get the URI, if available: Delimiter is a white space:
      int firstWhiteSpaceLocation = -1;
      for(int i=0; i <  currentSchemaLocationPath.length(); i++ )
      {
        if( Character.isWhitespace(currentSchemaLocationPath.charAt(i)) )
        {
          firstWhiteSpaceLocation = i;
          break;
        }
      }      
      String uri = "";
      if( firstWhiteSpaceLocation > 0 )
      {
        uri = currentSchemaLocationPath.substring(0,firstWhiteSpaceLocation);
        System.out.println("DocumentProcessor: Using URI: " + uri );
      }
      else
      {
        uri = "http://www.emayor.org/BusinessDocument.xsd";
        System.out.println("*** ");
        System.out.println("*** ");
        System.out.println("*** ");
        System.out.println("*** CAUTION:");
        System.out.println("*** ");
        String errorLine1 = "No uri found in original schema location attribute";
        System.out.println(errorLine1);
        System.out.println( this.originalSchemaLocationPath );
        System.out.println("*** ");
        System.out.println("*** Using:");
        System.out.println("*** http://www.emayor.org/BusinessDocument.xsd");
        System.out.println("*** ");
        System.out.println("*** The e-document might have a wrong schemalocation");
        System.out.println("*** attribute without space delimiter between");
        System.out.println("*** uri and schemalocation url path");
        System.out.println("*** ");
        System.out.println("*** ");
        System.out.println("*** ");
        // also publish this error in the applet error display:
        this.mainApplet.getErrormanager().addErrorMessage( errorLine1, true );
        this.mainApplet.getErrormanager().addErrorMessage( this.originalSchemaLocationPath, false );
      }
      
      // search "/xsd/"  or "emayor/" and replace it by abolute local address:
      int xsdPosition = currentSchemaLocationPath.indexOf("/xsd/");
      if( xsdPosition >= 0 ) // first possibility found
      {
        System.out.println("DocumentProcessor.setLocalSchemaLocation: /xsd/ found.");
        currentSchemaLocationPath = currentSchemaLocationPath.substring(xsdPosition);
        // and the local one (to be used for the applet's parser:
                
        String urlPart = "file:///" + this.javaHomeDirectory + "/eMayor" + currentSchemaLocationPath;
        urlPart = urlPart.replaceAll(" ", "%20"); // required parser workaround: replace spaces by %20
        this.localSchemaLocationPath = uri + " " + urlPart; // uri + space + url
        // Take out backslashes possibly present in the userHomeDirectory too:
        this.localSchemaLocationPath = this.localSchemaLocationPath.replace('\\','/');
                        
        // and set this one to the xml tree:
        XMLPath.SetSchemaLocationPathForModel(this.modelNode,this.localSchemaLocationPath);
        System.out.println("DocumentProcessor.setLocalSchemaLocation: local schema location path set to: " +
                            this.localSchemaLocationPath );
      }
      else
      {
        xsdPosition = currentSchemaLocationPath.indexOf("emayor/");
        if( xsdPosition >= 0 ) // second possibility found
        {
          System.out.println("DocumentProcessor.setLocalSchemaLocation: emayor/ found.");
          currentSchemaLocationPath = currentSchemaLocationPath.substring(xsdPosition);
          // and the local one (to be used for the applet's parser:
                    
          String urlPart = "file:///" + this.javaHomeDirectory + "/eMayor/xsd/" + currentSchemaLocationPath;
          urlPart = urlPart.replaceAll(" ", "%20"); // required parser workaround: replace spaces by %20
          this.localSchemaLocationPath = uri + " " + urlPart; // uri + space + url
          // Take out backslashes possibly present in the userHomeDirectory too:
          this.localSchemaLocationPath = this.localSchemaLocationPath.replace('\\','/');
                            
          // and set this one to the xml tree:
          XMLPath.SetSchemaLocationPathForModel(this.modelNode,this.localSchemaLocationPath);
          System.out.println("DocumentProcessor.setLocalSchemaLocation: local schema location path set to: " +
                              this.localSchemaLocationPath );
        }
        else
        {
          System.out.println("*** ");
          System.out.println("*** DocumentProcessor.setLocalSchemaLocation():");
          System.out.println("*** Unable to do this.");
          String errorLine1 = "The schema location must contain the sequence /xsd/ or \\xsd\\ or emayor/ or emayor\\ .";
          System.out.println("*** " + errorLine1);
          System.out.println("*** ");
          // also publish this error in the applet error display:
          this.mainApplet.getErrormanager().addErrorMessage( "Invalid e-document:\n" + errorLine1, true );
        }  
      }      
    }
    else
    {
      System.out.println( "DocumentProcessor.setLocalSchemaLocation called with setLocal= " + setLocal );
      System.out.println("localSchemaLocationPath= " + this.localSchemaLocationPath );
      System.out.println("originalSchemaLocationPath= " + this.originalSchemaLocationPath );
      System.out.println("-> setting the originalSchemaLocationPath again.");
      XMLPath.SetSchemaLocationPathForModel(this.modelNode,this.originalSchemaLocationPath );    
    }
    
   } // setLocalSchemaLocation

   
   
  
  
  
  
  
  
 /**
  *  Search the parentNode for a child with the passed searchedChildName
  *  and return that child, if it exists.
  */ 
  private XML_Node getChildWithTagName( XML_Node parentNode, String searchedChildName )
  {
    XML_Node foundChild = null; 
    for( int i=0; i < parentNode.getNumberOfChildren(); i++ )
    {
      XML_Node nodeChild = parentNode.getChildAt(i);
      if( nodeChild.getTagName().equals(searchedChildName) )
      {
        foundChild = nodeChild;
        break;
      }
    }
    return foundChild;
  }
  
  
 /**
  *  Called when the applet terminates.
  */
  public void stop()
  {
    this.guiBuilder.stop();
  }
  
  
  
  public static String ReplaceAllSequencesInStringCaseSensitive( 
                        String inputString,
                        String oldSequence,
                        String newSequence,
                        final boolean doSearchWholeWords )
  {
    final int inputStringLength = inputString.length();
    final StringBuffer buffer = new StringBuffer(512);
    int searchStartIndex = 0;
    int oldSequenceLength = oldSequence.length();
    int numberOfReplacements = 0;
    boolean leftBorderIsWordDelimiter, rightBorderIsWordDelimiter;
    char testCharacter;
    while( true )
    {
      final int index = inputString.indexOf( oldSequence,searchStartIndex );
      if( index >= 0 )
      {
        if( doSearchWholeWords )
        {
          // Check the borders
          leftBorderIsWordDelimiter = true;
          rightBorderIsWordDelimiter = true;
          if( index > 1 )
          {
            testCharacter = inputString.charAt(index-1);
            leftBorderIsWordDelimiter = !Character.isJavaIdentifierPart(testCharacter);
          }
          if( index + oldSequenceLength < inputStringLength-1 )
          {
            testCharacter = inputString.charAt( index + oldSequenceLength );
            rightBorderIsWordDelimiter = !Character.isJavaIdentifierPart(testCharacter);
          }
          if( leftBorderIsWordDelimiter && rightBorderIsWordDelimiter )
          {
            buffer.append( inputString.substring(searchStartIndex,index) );
            buffer.append( newSequence );
            numberOfReplacements++;
            searchStartIndex = index + oldSequenceLength;
          }
          else
          {
            // It's not a whole word, so skip and continue after that:
            buffer.append( inputString.substring(searchStartIndex,index) );
            buffer.append( oldSequence );
            searchStartIndex = index + oldSequenceLength;
          }
        }
        else
        {
          // No additional conditions, so replace and add:
          buffer.append( inputString.substring(searchStartIndex,index) );
          buffer.append( newSequence );
          numberOfReplacements++;
          searchStartIndex = index + oldSequenceLength;
        }
      }
      else
      {
        // append the rest :
        if( searchStartIndex < inputString.length() )
        {
          buffer.append( inputString.substring(searchStartIndex,inputString.length()) );
        }
        // and quit :
        break;
      }
    } // while
    return buffer.toString();
  }
   


} // eMayorFormsProcessor
                






   
