package org.emayor.client.controlers.printing;

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
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.swing.plaf.metal.*;
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.beans.*;

import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

import org.emayor.client.controlers.printing.Print2DGraphicsPanel;

import org.emayor.client.Utilities.ThreadEngine.ThreadEngine;

import org.emayor.client.EMayorFormsClientApplet;
import org.emayor.client.LanguageProperties;
import org.emayor.client.Utilities.gui.JSenseButton;
import org.emayor.client.Utilities.gui.GradientPanel;



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
    int pHeight = (3*usableRect.height)/4;
    int pWidth  = pHeight;
    int hOffset = (usableRect.width-pWidth)/2;
    int vOffset = (usableRect.height-pHeight)/2;
    
    // check sizes:
    if( pHeight > 1024 ) pHeight = 1024;
    if( pHeight >  800 ) pHeight =  800;
        
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

