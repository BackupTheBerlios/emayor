package org.emayor.RepresentationLayer.ClientTier.Controlers.printing;

/**
 * 
 * A printable JTextArea
 * 
 * Created 8.Nov.2005   jpl
 *
 */


import java.awt.*;
import javax.swing.*;

import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

import org.emayor.RepresentationLayer.ClientTier.LanguageProperties;
import org.emayor.RepresentationLayer.ClientTier.ResourceLoader;
import org.emayor.RepresentationLayer.ClientTier.Utilities.ThreadEngine.*;


public class PrintableTextArea extends JTextArea implements Printable
{

  private Frame ownerFrame;
  private ResourceLoader resourceLoader;
  private JDialog parentDialog;
  private LanguageProperties languageProperties;

  
  public PrintableTextArea( final String text,
                            final int numberOfColumns,
                            final Frame _ownerFrame,
                            final JDialog theParentDialog,
                            final ResourceLoader _resourceLoader,
                            final LanguageProperties _languageProperties )
  {
    super();
    this.ownerFrame = _ownerFrame;
    this.parentDialog = theParentDialog;
    this.resourceLoader = _resourceLoader;
    this.languageProperties = _languageProperties;
    
    this.setColumns( numberOfColumns );
    
    // The test must be processed manually here:
    // insert newlines until no line exceeds the passed numberOfColumns:
    StringBuffer textBuffer = new StringBuffer(text);
    String textAreaText = doLimitNumberOfColumnsIn(textBuffer,numberOfColumns);
    
    this.setText(textAreaText);
    
    this.setLineWrap(true);
    
  } // Constructor




  
  
  private String doLimitNumberOfColumnsIn( StringBuffer text, int numberOfColumns )
  {
    int charCount = 0;
    int columnCount = 0; // counts columns in the current line
    int lastGoodLineBreakPosition = -1;
    char character = ' ';
    while( charCount < text.length() )
    {
      character = text.charAt(charCount);
      if( ( character == '>' ) ||
          ( character == ' ' ) )
      {
        lastGoodLineBreakPosition = charCount;
      }
      columnCount++;
      if( columnCount > numberOfColumns )
      {
        if( lastGoodLineBreakPosition > 0 )
        {
          charCount = lastGoodLineBreakPosition+1;
        }
        text.insert(charCount,"\n");
        lastGoodLineBreakPosition = -1;
        columnCount = 0;
        charCount++;
      }
      else
      if( ( character == '\n' ) ||
          ( character == '\r' ) )
      {
        lastGoodLineBreakPosition = -1;
        columnCount = 0;
      }
      charCount++;
    }  
    return text.toString();  
  } // doLimitNumberOfColumnsIn
  

   
  

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

  int left_margin = inchesToPage(0.1);
  int right_margin = inchesToPage(0.1);
  int top_margin = inchesToPage(0.5);
  int bottom_margin = inchesToPage(0.5);

  
  int inchesToPage(double inches) 
  {
    return (int)(inches*72.0);
  }

  
  public int print( java.awt.Graphics graphics, 
                    java.awt.print.PageFormat pageFormat, 
                    int pageIndex) throws PrinterException 
  {
  Graphics2D g2 = (Graphics2D) graphics;

  // Use a better font for printing: 
  g2.setFont( super.getFont().deriveFont(8.0f));

  int bodyheight = (int)pageFormat.getImageableHeight();
  int bodywidth = (int)pageFormat.getImageableWidth();
  int lineheight = g2.getFontMetrics().getHeight()-(g2.getFontMetrics().getLeading()/2);

  int lines_per_page = (bodyheight-top_margin-bottom_margin)/lineheight;

  int start_line = lines_per_page*pageIndex;

  if (start_line > getLineCount()) 
  {
    return NO_SUCH_PAGE;
  }

  int page_count = (getLineCount()/lines_per_page)+1;

  int end_line = start_line + lines_per_page;

  int linepos = (int)top_margin;

  int lines = getLineCount();

  g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

  for (int line = start_line; line < end_line; line++) 
  {
    try 
    {
      String linetext = getText( getLineStartOffset(line),
                                 getLineEndOffset(line)-getLineStartOffset(line) );
      // Remove newlines, and returns
      linetext = linetext.replace('\n',' ');
      linetext = linetext.replace('\r',' ');
      linetext = linetext.replace('\t',' ');
      g2.drawString(linetext, left_margin, linepos);
    } catch (Exception e) 
    {
      //  never a bad location
    }
    linepos += lineheight;
    if (linepos > bodyheight-bottom_margin) 
    {
      break;
    }
  }
  return Printable.PAGE_EXISTS;
  }
  
  
  
  
} // PrintableEditorPane
