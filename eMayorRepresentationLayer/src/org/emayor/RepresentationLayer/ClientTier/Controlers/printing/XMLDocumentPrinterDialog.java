package org.emayor.RepresentationLayer.ClientTier.Controlers.printing;

  /**
   * 
   *   A modal dialog, which displays the passed xml document
   *   and contains functionality for printing it out.
   * 
   *   Created 8.Nov 2005  jpl
   */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import org.emayor.RepresentationLayer.ClientTier.EMayorFormsClientApplet;
import org.emayor.RepresentationLayer.ClientTier.LanguageProperties;
import org.emayor.RepresentationLayer.ClientTier.Utilities.gui.GradientPanel;
import org.emayor.RepresentationLayer.ClientTier.Utilities.gui.JSenseButton;



public class XMLDocumentPrinterDialog extends JDialog
{

  private EMayorFormsClientApplet parentApplet;
  private LanguageProperties languageProperties;
  private String xmlDocument;

  
  private JScrollPane documentScrollPane;
  private PrintableTextArea documentTextArea;
  private JSenseButton printButton;
  
  
  public XMLDocumentPrinterDialog( final Frame theOwnerFrame,
                                   final EMayorFormsClientApplet _parentApplet,
                                   final LanguageProperties _languageProperties,
                                   final String dialogTitle,
                                   final String _xmlDocument )
  {
    super( theOwnerFrame,dialogTitle,true ); // modal on theOwnerFrame    
    this.parentApplet = _parentApplet;
    this.languageProperties = _languageProperties;
    this.xmlDocument = _xmlDocument;
    
    // better for printing: set tab length to 1 == replace tabs by spaces:
    this.xmlDocument = this.xmlDocument.replaceAll("\t"," ");
    
    final int fontSize = UIManager.getFont("Label.font").getSize();
    this.getContentPane().setLayout( new BorderLayout(fontSize/8,fontSize/8) );
    // message parts :
    JPanel mainPanel = new JPanel( new BorderLayout(fontSize/2,fontSize/2) );
    mainPanel.setBorder( BorderFactory.createEmptyBorder(fontSize/2,fontSize/2,fontSize/2,fontSize/2) );
    
    // Create a readonly editorpane and put it inside a scrollpane:
    int numberOfColumns = 80; // 80 columns max per line
    this.documentTextArea = 
         new PrintableTextArea(   this.xmlDocument,
                                  numberOfColumns,
                                  theOwnerFrame,this,this.parentApplet,
                                  this.languageProperties );
    this.documentTextArea.setEditable(false);    
    this.documentScrollPane = new JScrollPane(this.documentTextArea);
    // add it:
    mainPanel.add( this.documentScrollPane, BorderLayout.CENTER );

    // The buttons:
    String printButtonText = this.languageProperties.getTextFromLanguageResource("PrinterDialog.PrintDocumentButtonText");
    this.printButton = new JSenseButton( "   " + printButtonText + "   ", true );
    this.printButton.setIcon( parentApplet.loadImageIcon("/pictures/applet/selection.gif"));
    this.printButton.setFocusPainted(false);
    this.printButton.addActionListener(new java.awt.event.ActionListener()
     {
       public void actionPerformed(ActionEvent e)
       {
         printButtonPressed();
       }
     });

    String cancelButtonText = this.languageProperties.getTextFromLanguageResource("PrinterDialog.CancelButtonText");
    JButton cancelButton = new JSenseButton("   " + cancelButtonText + "   ",true );
    cancelButton.setIcon( parentApplet.loadImageIcon("/pictures/applet/failure.gif"));
    cancelButton.setFocusPainted(false);
    cancelButton.addActionListener(new java.awt.event.ActionListener()
     {
       public void actionPerformed(ActionEvent e)
       {
         setVisible(false);
       }
     });

    JPanel buttonPanel = new GradientPanel( new FlowLayout(),
                                            GradientPanel.ApplyVerticalHighLight,
                                            GradientPanel.StrongGradientStrength,
                                            GradientPanel.PanelBackground );
    buttonPanel.add( printButton );
    buttonPanel.add( cancelButton );
                    
    JPanel southPanel = new JPanel( new BorderLayout() );
    southPanel.add( buttonPanel, BorderLayout.CENTER );

    // Use a wrap panel on the north, so that content
    // is not stretched over the whole frame, is its
    // smaller than the frame:
    JPanel wrapPanel = new JPanel( new BorderLayout(0,0) );
    wrapPanel.add( mainPanel, BorderLayout.CENTER  );
    wrapPanel.add( southPanel, BorderLayout.SOUTH );    
    this.getContentPane().add( wrapPanel, BorderLayout.CENTER  );

    GraphicsEnvironment localEnvirnonment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Rectangle usableRect = localEnvirnonment.getMaximumWindowBounds();        
    int pHeight = (3*usableRect.height)/4;
    int pWidth  = pHeight;
    int hOffset = (usableRect.width-pWidth)/2;
    int vOffset = (usableRect.height-pHeight)/2;
    
    // check sizes:
    if( pHeight > 1024 ) pHeight = 1024;
    if( pHeight >  800 ) pHeight =  800;
      
    // don't let the scrollpane get too big for the screen:
    int sw = pWidth- 2*fontSize;
    int sh = pHeight- 4*fontSize;
    this.documentScrollPane.setPreferredSize( new Dimension(sw,sh) );
    
    this.setLocation(hOffset,vOffset);
    this.setSize(pWidth,pHeight);
        
    this.pack();
    
  } // constructor


  
  
  
  /**
   *  Print the table and close this dialog
   */
   private void printButtonPressed()
   {
     int fontSize = UIManager.getFont("TextField.font").getSize();
     Point point = new Point( fontSize*2 , fontSize );
     SwingUtilities.convertPointToScreen( point, this );     
     // print it:
     this.documentTextArea.printEditorPane(point);     
     this.setVisible(false);
   } // okButtonPressed
  


} // XMLDocumentPrinter

