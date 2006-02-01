package org.emayor.RepresentationLayer.ClientTier;

  /**
   *  This class is used calculating and displaying
   *  informations about the signatures in the
   *  incoming (initial) document.
   * 
   *  It is called from a menu in the applet.
   * 
   *  10.1.06  jpl
   */

import java.awt.*;

import javax.swing.*;

import org.emayor.RepresentationLayer.ClientTier.Logic.EMayorDocumentParser;
import org.emayor.RepresentationLayer.ClientTier.Logic.XMLSignature.SignerInfo;
import org.emayor.RepresentationLayer.Shared.parser.xml.XML_Node;
import org.emayor.RepresentationLayer.ClientTier.Utilities.gui.ProgressWindow;
import org.emayor.RepresentationLayer.ClientTier.Utilities.gui.TextOutputDialog;

public class SignatureInfoProcessor
{

  private LanguageProperties languageProperties;

  
  
  public SignatureInfoProcessor( final LanguageProperties _languageProperties )
  {
    this.languageProperties = _languageProperties;
  }
  

  
  
 /**
  *  Calculates and displays information about present signatures in the
  *  passed eMayorForm.
  *  Called outside the EDT. Uses a modal progress window during calculation
  *  and displays the results in a frame.
  * 
  * @param eMayorForm           the eMayorForm
  * @param appletParentFrame    the target for the modal progress dialog
  */ 
  public void showSignatureInformation( final StringBuffer eMayorForm,
                                        final Frame appletParentFrame,
                                        final ImageIcon progressIcon ) throws Exception
  {
    String userInformation = languageProperties.getTextFromLanguageResource("SignatureInfo.PleaseWaitCheckingSignatures");
    String cancelText = languageProperties.getTextFromLanguageResource("PrinterDialog.CancelButtonText");

    final ProgressWindow progressWindow = 
        new ProgressWindow( userInformation,
                            progressIcon,
                            appletParentFrame,
                            true,
                            cancelText );
    EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {
        progressWindow.showCentered();
      }
    });
    try{ Thread.sleep(299); } catch( Exception ee1 ){} // Time for Swing queue

    
    // Parse the eMayorForm and extract the e-document from the model:
    EMayorDocumentParser docParser = new EMayorDocumentParser(this.languageProperties);
    // Get the Model and the View xml subtrees from this document:
    XML_Node[] nodes = docParser.translate_eMayorFormsDocument( eMayorForm );
    XML_Node modelNode = nodes[0]; // contains the xml model
    // Get rid of the model tag too for this:
    XML_Node eDocumentNode = modelNode.getChildAt(0); // contains the e-document  
    final StringBuffer reverse_Model_Document = docParser.translateXMLTree( eDocumentNode,true );
    String eDocument = reverse_Model_Document.toString();
    // Create the signature information text:  
    final String signatureInfoText = this.createSignatureInformationText(eDocument);

    EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {
        progressWindow.setVisible(false);
        progressWindow.dispose();
      }
    });
    try{ Thread.sleep(299); } catch( Exception ee1 ){} // Time for Swing queue
    
    // and display it:
    EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {
        displayText( signatureInfoText,appletParentFrame );
      }
    });
    
  } // showSignatureInformation
  
  
  
  
  
  
  private String createSignatureInformationText( final String eDocument )
  {
    StringBuffer textBuffer = new StringBuffer();
    try
    {
      SignerInfo signerInfo = new SignerInfo( eDocument );
      int numberOfSignatures = signerInfo.getSignaturesNumber();
            
      // Number of signatures:
      String s = languageProperties.getTextFromLanguageResource("SignatureInfo.NumberOfSignatures") + ":";
      textBuffer.append("\n");
      textBuffer.append(s);
      textBuffer.append(numberOfSignatures);
      textBuffer.append("\n\n");
  
      if( numberOfSignatures > 0 )
      {
        // signature validation result:
        boolean areAllSignaturesValid = signerInfo.getValidationResult();
        s = languageProperties.getTextFromLanguageResource("SignatureInfo.SignaturesAreValid") + ":";
        textBuffer.append(s);
        s = (areAllSignaturesValid) ? languageProperties.getTextFromLanguageResource("Message.Yes") : 
                                      languageProperties.getTextFromLanguageResource("Message.No");
        textBuffer.append(s);
        textBuffer.append("\n\n");
                
        String signatureText = languageProperties.getTextFromLanguageResource("SignatureInfo.Signature");  
        for( int i=1; i <= numberOfSignatures; i++ )
        {
          // Numbered title:
          s = String.valueOf(i) + ".  " + signatureText;
          textBuffer.append(s);
          textBuffer.append("\n");
          // signer role:
          String signerRole = signerInfo.getSignerRole(i);  
          s = languageProperties.getTextFromLanguageResource("SignatureInfo.RoleOfTheSigner") + ": ";  
          textBuffer.append(s);
          textBuffer.append(signerRole);
          textBuffer.append("\n");
          // signer name:
          String signerName = signerInfo.getCommonName(i);  
          s = languageProperties.getTextFromLanguageResource("SignatureInfo.NameOfTheSigner") + ": ";  
          textBuffer.append(s);
          textBuffer.append(signerName);
          textBuffer.append("\n");
          // validity of the signature:
          boolean isValidSignature = signerInfo.getSignatureStatus(i);
          s = languageProperties.getTextFromLanguageResource("SignatureInfo.SignatureIsValid") + ": ";  
          textBuffer.append(s);
          s = (isValidSignature) ? languageProperties.getTextFromLanguageResource("Message.Yes") : 
                                   languageProperties.getTextFromLanguageResource("Message.No");
          textBuffer.append(s);
          textBuffer.append("\n\n");  
        } // for i
      } // if at least one signature exists in the document
    }
    catch( Exception eee )
    {
      textBuffer.append( "\nException:\n" );
      textBuffer.append( eee.getMessage() );
      eee.printStackTrace();
    }
    catch( Error err )
    {
      textBuffer.append( "\nError:\n" );
      textBuffer.append( err.getMessage() );
      err.printStackTrace();
    }
    return textBuffer.toString();
  }
  

  
  
  
  
  private void displayText( final String signatureInfoText,
                            final Frame appletParentFrame )
  {
    String frameTitle = languageProperties.getTextFromLanguageResource("SignatureInfo.Signature");
    String closeButtonText = languageProperties.getTextFromLanguageResource("Message.Proceed");
    TextOutputDialog dia = new TextOutputDialog( appletParentFrame,
                                                 frameTitle,
                                                 signatureInfoText,
                                                 closeButtonText );
    dia.showCentered();
  } // displayText


  
  
} // SignatureInfoProcessor


