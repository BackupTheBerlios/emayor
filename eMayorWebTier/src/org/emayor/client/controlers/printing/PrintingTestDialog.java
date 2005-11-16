package org.emayor.client.controlers.printing;



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

import org.emayor.client.EMayorFormsClientApplet;
import org.emayor.client.LanguageProperties;
import org.emayor.client.ResourceLoader;
import org.emayor.client.Utilities.gui.GradientPanel;
import org.emayor.client.Utilities.gui.JSenseButton;
import org.emayor.webtier.shared.Utilities;



public class PrintingTestDialog extends JDialog implements ResourceLoader
{


  private LanguageProperties languageProperties;
  private String xmlDocument;

  
  private JScrollPane documentScrollPane;
  private PrintableTextArea documentTextArea;
  private JSenseButton printButton;



  public PrintingTestDialog( final Frame theOwnerFrame,
                             final String _xmlDocument )
  {
    super(theOwnerFrame,"test dialog ",true);    
    this.languageProperties = new LanguageProperties();
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
         new PrintableTextArea(   this.xmlDocument,numberOfColumns,
                                  theOwnerFrame,this,this,
                                  this.languageProperties );
    this.documentTextArea.setEditable(false);    
    this.documentScrollPane = new JScrollPane(this.documentTextArea);
    // add it:
    mainPanel.add( this.documentScrollPane, BorderLayout.CENTER );

    // The buttons:
    String printButtonText = this.languageProperties.getTextFromLanguageResource("PrinterDialog.PrintDocumentButtonText");
    this.printButton = new JSenseButton( "   " + printButtonText + "   ", true );
    this.printButton.setIcon( this.loadImageIcon("/pictures/applet/selection.gif"));
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
    cancelButton.setIcon( this.loadImageIcon("/pictures/applet/failure.gif"));
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

    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int pHeight = (3*screen.height)/4;
    int pWidth  = (3*screen.width)/4;
    int hOffset = (screen.width-pWidth)/2;
    int vOffset = (screen.height-pHeight)/2;
    this.setLocation(hOffset,vOffset);
    this.setSize(pWidth,pHeight);
  }
  
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
  
   
   public ImageIcon loadImageIcon(String name)
   {
     return null;
   }

 
   public static void main( String[] arguments )
   {
     JFrame parentFrame = new JFrame();
     String testFileName = "testDocument.xml";
     String documentFilePath = System.getProperty("user.home") + "/" + testFileName;
     File xmlDocFile = new File(documentFilePath);
     String xmlDocument = readUTF8TextFileAsString(xmlDocFile);
     PrintingTestDialog dia = new PrintingTestDialog(parentFrame,xmlDocument);
     dia.setVisible(true);   
   }
   
   private static String readUTF8TextFileAsString( final File file )
   {    
     String fileString = null;
     try
     {
       FileInputStream fin = new FileInputStream(file);
       ByteArrayOutputStream bOut = Utilities.ReadByteFileFromInputStream(fin);
       fileString = bOut.toString("UTF-8");
       bOut = null;
     }
     catch( Exception e )
     {
       e.printStackTrace();
     }
     return fileString;    
   } // readUTF8TextFileAsString
   

}
