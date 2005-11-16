package org.emayor.client.controlers.printing;




import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JPanel;
import javax.swing.RepaintManager;


public class PrintableJPanel extends JPanel implements Printable
{

    public PrintableJPanel()
    {
      super();
    }

    public PrintableJPanel( LayoutManager layout )
    {
      super(layout);
    }

    public int print( Graphics graphics, 
                      PageFormat pageFormat, 
                      int pageIndex ) throws PrinterException
    {
      if (pageIndex > 0) 
      {
        return(NO_SUCH_PAGE);
      } 
      else 
      {
        Graphics2D g2d = (Graphics2D)graphics;
        // Move to paper origin:
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        // Scale it to fit into one paper sheet, if required:
        double xScale = pageFormat.getImageableWidth() / this.getWidth();
        double yScale = pageFormat.getImageableHeight() / this.getHeight();
        double minScale = (xScale < yScale ) ? xScale : yScale;
        if( minScale < 1.0 )
        {
          g2d.scale(minScale,minScale);
        }    
        System.out.println("ScaleFactor= " + minScale + " (only scaled, when < 1)");
        this.disableDoubleBuffering(this);
        this.paint(g2d);
        this.enableDoubleBuffering(this);
        return(PAGE_EXISTS);
      }
    }

    
    public void disableDoubleBuffering(Component c) 
    {
      RepaintManager currentManager = RepaintManager.currentManager(c);
      currentManager.setDoubleBufferingEnabled(false);
    }

    
    public void enableDoubleBuffering(Component c) 
    {
      RepaintManager currentManager = RepaintManager.currentManager(c);
      currentManager.setDoubleBufferingEnabled(true);
    }
    
    
}
