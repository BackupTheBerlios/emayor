package org.emayor.RepresentationLayer.ClientTier.Controlers.printing;

/**
 * 
 * A printable JEditorPane
 * 
 * Created 8.Nov.2005   jpl
 *
 */


import java.awt.*;
import javax.swing.*;

import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

import org.emayor.RepresentationLayer.ClientTier.LanguageProperties;
import org.emayor.RepresentationLayer.ClientTier.ResourceLoader;
import org.emayor.RepresentationLayer.ClientTier.Utilities.ThreadEngine.*;


public class PrintableEditorPane extends JEditorPane implements Printable
{

  private Frame ownerFrame;
  private ResourceLoader resourceLoader;
  private JDialog parentDialog;
  private LanguageProperties languageProperties;

  
  public PrintableEditorPane( final String type, 
                              final String text, 
                              final Frame _ownerFrame,
                              final JDialog theParentDialog,
                              final ResourceLoader _resourceLoader,
                              final LanguageProperties _languageProperties )
  {
    super(type,text);
    this.ownerFrame = _ownerFrame;
    this.parentDialog = theParentDialog;
    this.resourceLoader = _resourceLoader;
    this.languageProperties = _languageProperties;
    
  
  } // Constructor




 /**
  *  Prints the whole component.
  */
  public void printEditorPane( final Point globalScreenPoint )
  {
    Runnable printRunnable = new Runnable()
     {      
       public void run()
       {
         printEditorPane_InThread();
       }
     };
    ThreadEngine.getInstance().addRunnable( printRunnable,"Print_XMLDocument" );
  } // printEditorPane
                                                   



  private void printEditorPane_InThread()
  {
    // Set some required attributes:
    PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
    attributes.add( OrientationRequested.PORTRAIT );
    attributes.add( new Copies(1) );
    attributes.add( new JobName("eMayor_PrintDocument", null));
    // and print:
    Print2DGraphicsDocument print2DGraphicsDoc =
             new Print2DGraphicsDocument( this,attributes,this.parentDialog,
                                     this.resourceLoader,this.languageProperties );
    print2DGraphicsDoc.printTheDocument();
  }



 /**
  *  Implementation of the Printable interface
  *  Von einer Java Question of the week Seite
  */
  public int print( Graphics g, PageFormat pageFormat,int pageIndex ) throws PrinterException
  {
    JEditorPane editorView = this;

    Graphics2D  g2 = (Graphics2D) g;
    g2.setColor(Color.black);

    Font defaultFont = g2.getFont();
    Font editorPaneFont = new Font(defaultFont.getName(), defaultFont.getStyle(), 10 );
    g2.setFont( editorPaneFont );

    int fontHeight=g2.getFontMetrics().getHeight();
    int fontDesent=g2.getFontMetrics().getDescent();

    // leave room for page number and the page comment line:
    double pageHeight = pageFormat.getImageableHeight() - fontHeight;

    double pageWidth = pageFormat.getImageableWidth();
    double editorWidth = (double) editorView.getWidth();
    double scale = 1;
    if( editorWidth >= pageWidth )
    {
      scale =  pageWidth / editorWidth;
    }

    double headerHeightOnPage= 0*scale; // zero pixels for the header
    double tableWidthOnPage = editorWidth*scale;

    //double oneRowHeight=(tableView.getRowHeight()+tableView.getRowMargin())*scale;
    //int numRowsOnAPage = (int)((pageHeight-headerHeightOnPage)/oneRowHeight);
    
    //double pageHeightForTable=oneRowHeight*numRowsOnAPage;
    //int totalNumPages= (int)Math.ceil(((double)tableView.getRowCount())/numRowsOnAPage);

    FontMetrics metrics = this.getToolkit().getFontMetrics(editorPaneFont);
    double lineHeight = metrics.getHeight();
    double pageToLineRatio = pageHeight / lineHeight;
    int totalNumberOfLines = (int)pageToLineRatio;
    
    double oneRowHeight=(lineHeight)*scale;
    int numRowsOnAPage = (int)((pageHeight)/oneRowHeight);
        
    double pageHeightForTable=oneRowHeight*numRowsOnAPage;
    int totalNumPages= (int)Math.ceil(((double)totalNumberOfLines)/numRowsOnAPage);
    
    if( pageIndex >= totalNumPages )
    {
      return NO_SUCH_PAGE;
    }

    // go to upperleft corner of the page:
    g2.translate( pageFormat.getImageableX(),pageFormat.getImageableY() );
    
    /* ----
    g2.drawString( this.pageCommentLine,
                   (int)fontHeight,
                   (int)(pageHeight+fontHeight-fontDesent) ); // bottom center, 1st line
    g2.drawString( this.tableTitle + "   Seite: "+(pageIndex+1),
                   (int)fontHeight,
                   (int)(pageHeight+2*fontHeight-fontDesent) ); // bottom center, 2nd line
    --- */

    g2.translate( 0f, headerHeightOnPage );
    g2.translate( 0f, -pageIndex*pageHeightForTable );
    
    // TODO this next line treats the last page as a full page
    g2.setClip( 0,
                (int)(pageHeightForTable*pageIndex),
                (int)Math.ceil(tableWidthOnPage),
                (int)Math.ceil(pageHeightForTable));

    g2.scale(scale,scale);

    editorView.paint(g2); // paint the table

    // next page:
    g2.scale(1/scale,1/scale);
    g2.translate(0f,pageIndex*pageHeightForTable);
    g2.translate(0f, -headerHeightOnPage);
    g2.setClip(0, 0,(int) Math.ceil(tableWidthOnPage),(int)Math.ceil(headerHeightOnPage));
    g2.scale(scale,scale);


    return Printable.PAGE_EXISTS;
  }

  
} // PrintableEditorPane
