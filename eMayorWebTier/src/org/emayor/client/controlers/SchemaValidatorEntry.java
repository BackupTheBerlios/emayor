package org.emayor.client.controlers;


/*
 *  A container for the SchemaValidator entries. 
 * 
 *  Created on 11.08.2005,  jpl
 *
 */



import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import org.emayor.client.parser.xml.XML_Node;


 public class SchemaValidatorEntry
 {
 
   // The three possible validation states:
    public static final int ValidationStateUndefined = 0;
    public static final int ValidationStateOk        = 1;
    public static final int ValidationStateViolation = 2;
 
   private final Color correctValueColor = new Color(255,255,255);
   private final Color wrongValueColor   = new Color(255,200,200);
 
   private JComponent inputComponent;
   private JLabel schemaValidationMessageLabel;
   private XML_Node modelValueNode;
   private String xPathOfNode;

   
   private int validationState = 0; // See the three validation date values above
   private ImageIcon successIcon;
   private ImageIcon failureIcon;
   
   
   public SchemaValidatorEntry( JComponent _inputComponent,
                                JLabel _schemaValidationMessageLabel,
                                XML_Node _modelValueNode,
                                final ImageIcon _successIcon,
                                final ImageIcon _failureIcon  )
   {
     this.inputComponent = _inputComponent;
     this.schemaValidationMessageLabel = _schemaValidationMessageLabel;
     this.modelValueNode = _modelValueNode;
     this.successIcon = _successIcon;
     this.failureIcon = _failureIcon;
     
     // Create the xpath - this way, during validation it can be compared
     // fast without having to create it always from the modelValueNode:
     this.xPathOfNode = this.modelValueNode.getXPath_From_eDocument(); 

     this.validationState = ValidationStateUndefined;
     
     //System.out.println("Created SchemaValidatorEntry with xPath:");
     //System.out.println(this.xPathOfNode);
     
   } // Constructor
   
   

   
   public String getXPath()
   {
     return this.xPathOfNode;
   }

   
   public XML_Node getModelValueNode()
   {
     return this.modelValueNode;
   }
   

   
  /**
   *  This method must be fast, it affects the validating
   *  parser's speed.
   */ 
   public void setValidationResult( final boolean isValueCorrect,
                                    final String validationStateText,
                                    final String tooltipText )
   {

    //System.out.println("?-> setValidationResult(): called for isValueCorrect= " + 
    //                   isValueCorrect + "  and validationResult= " + validationResult);     
   
   // Only trigger on validation state changes for speed reasons:
     if(  ( ( isValueCorrect) && (this.validationState != ValidationStateOk) )  ||
          ( (!isValueCorrect) && (this.validationState != ValidationStateViolation) )  )
     {
       // sync the cache attribute:
       this.validationState = ( isValueCorrect ) ? ValidationStateOk : ValidationStateViolation;

       //System.out.println("setValidationResult(): updating GUI for result: " + validationResult);     

       // and update the GUI:
       // This directly affects the UI, so add it to the EDT queue:
       EventQueue.invokeLater( new Runnable()
       {
         public void run()
         {
           // Set the background color of the inputComponent:
           Color bgColor = (isValueCorrect) ? correctValueColor : wrongValueColor;
           inputComponent.setBackground( bgColor );
           // and set the validation result into the schemaValidationMessageLabel.
           // This should be an empty String when no violation has detected,
           // which automatically clears the violation message in that case:
           
           // Only upto 24 chars in the label, complete text as tooltip:
           String labelText = validationStateText;
           if( labelText.length() > 36 ) labelText = labelText.substring(0,32) + "...";
           
           schemaValidationMessageLabel.setText( labelText );
           schemaValidationMessageLabel.setToolTipText(tooltipText);
           ImageIcon icon = (isValueCorrect) ? successIcon : failureIcon;
           schemaValidationMessageLabel.setIcon( icon );
         }
       });    
     }
     else
     {
       //System.out.println("setValidationResult(): No transition -> no action.");     
     }
   } // setValidationResult
 
   
   
 } // ValidatorEntry
