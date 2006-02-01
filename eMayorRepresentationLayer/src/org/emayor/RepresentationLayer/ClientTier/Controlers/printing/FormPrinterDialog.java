package org.emayor.RepresentationLayer.ClientTier.Controlers.printing;

  /**
   * 
   *   A modal dialog, which displays the passed JPanel
   *   and contains functionality for printing it out.
   * 
   *   Created 8.Nov 2005  jpl
   */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

import org.emayor.RepresentationLayer.ClientTier.EMayorFormsClientApplet;
import org.emayor.RepresentationLayer.ClientTier.LanguageProperties;
import org.emayor.RepresentationLayer.ClientTier.Controlers.printing.Print2DGraphicsPanel;
import org.emayor.RepresentationLayer.ClientTier.Utilities.ThreadEngine.ThreadEngine;
import org.emayor.RepresentationLayer.ClientTier.Utilities.gui.GradientPanel;
import org.emayor.RepresentationLayer.ClientTier.Utilities.gui.JSenseButton;





public class FormPrinterDialog extends JDialog
{

  private EMayorFormsClientApplet parentApplet;
  private LanguageProperties languageProperties;
  private PrintableJPanel printablePanel;

  
  private JScrollPane documentScrollPane;
  private PrintableTextArea documentTextArea;
  private JSenseButton printButton;
  
  private Frame ownerFrame;

  
  
  public FormPrinterDialog( final Frame theOwnerFrame,
                            final EMayorFormsClientApplet _parentApplet,
                            final LanguageProperties _languageProperties,
                            final String dialogTitle,
                            final PrintableJPanel _printablePanel )
  {
    super( theOwnerFrame,dialogTitle,true ); // modal on theOwnerFrame    
    this.ownerFrame = theOwnerFrame;
    this.parentApplet = _parentApplet;
    this.languageProperties = _languageProperties;
    this.printablePanel = _printablePanel;
        
    final int fontSize = UIManager.getFont("Label.font").getSize();
    this.getContentPane().setLayout( new BorderLayout(fontSize/8,fontSize/8) );
    // message parts :
    JPanel mainPanel = new JPanel( new BorderLayout(fontSize/2,fontSize/2) );
    mainPanel.setBorder( BorderFactory.createEmptyBorder(fontSize/2,fontSize/2,fontSize/2,fontSize/2) );
    
    // Create the printable panel inside a scrollpane:
    this.documentScrollPane = new JScrollPane(this.printablePanel);
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

    this.getContentPane().add( mainPanel, BorderLayout.CENTER  );
    this.getContentPane().add( southPanel, BorderLayout.SOUTH );

    GraphicsEnvironment localEnvirnonment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Rectangle usableRect = localEnvirnonment.getMaximumWindowBounds();        

    /*
    int pHeight = (3*usableRect.height)/4;
    int pWidth  = pHeight;
    int hOffset = (usableRect.width-pWidth)/2;
    int vOffset = (usableRect.height-pHeight)/2;
    */
    
    // Size it so, that its width is the same as the applet's width:
    int pWidth = this.parentApplet.getWidth();
    int pHeight = (int)(((double)pWidth) * 1.4142d); // Din format
    // cut the height, if its bigger than the screen height:
    if( pHeight > (8*usableRect.height)/10 )
    {
       pHeight = (8*usableRect.height)/10;
    }
   
    // check sizes:
    if( pHeight > 1024 ) pHeight = 1024;
    if( pHeight >  800 ) pHeight =  800;
        
    int hOffset = (usableRect.width-pWidth)/2;
    int vOffset = (usableRect.height-pHeight)/2;

    this.setLocation(hOffset,vOffset);
    this.setSize(pWidth,pHeight);
        
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
     // and print in user thread:
     Runnable printRunnable = new Runnable()
     {      
       public void run()
       {
         print_InThread();
       }
     };
    ThreadEngine.getInstance().addRunnable( printRunnable,"Print_Form" );     
    this.setVisible(false);
   } // okButtonPressed
  

   private void print_InThread()
   {
    // Set some required attributes:
    PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
    attributes.add( OrientationRequested.PORTRAIT );
    attributes.add( new Copies(1) );
    attributes.add( new JobName("eMayor_PrintForm", null));
    Print2DGraphicsPanel print2DGraphicsDoc =
        new Print2DGraphicsPanel( this.printablePanel,
                                  attributes,this.ownerFrame,
                                  this.parentApplet,this.languageProperties );
                                  print2DGraphicsDoc.printTheDocument();   
   }
   

} // XMLDocumentPrinter

