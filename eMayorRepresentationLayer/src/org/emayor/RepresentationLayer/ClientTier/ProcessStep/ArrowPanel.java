package org.emayor.RepresentationLayer.ClientTier.ProcessStep;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;


public class ArrowPanel extends JPanel
{

  private String text;
  private Color textColor;
  private Color foregroundColor;
  private Color backgroundColor;

  private Font font = null;
  
  
  public ArrowPanel( final String _text,
                     final Color  _textColor,
                     final Color  _foregroundColor,
                     final Color  _backgroundColor )
  {
    super();
    this.text = _text;
    this.textColor = _textColor;
    this.foregroundColor = _foregroundColor;
    this.backgroundColor = _backgroundColor;
    
    //int textSize = 16;
    //int textStyle = Font.BOLD | Font.ITALIC;    
    //this.font = new Font( "SansSerif",textStyle,textSize );
    this.font = UIManager.getFont("Label.font");

    int width = 2*this.font.getSize() + ( 3 * this.font.getSize() * this.text.length() )/4;
    int height = 5*this.font.getSize()/3;
    
    this.setSize( new Dimension(width,height) );
    this.setPreferredSize( new Dimension(width,height) );
  }


  
  public void paint( Graphics g )
  {
    Graphics2D graphics2D = (Graphics2D)g;
    
    // Backup:
    Paint currentPaint = graphics2D.getPaint();
    Font currentFont = graphics2D.getFont();
   
    int width = getWidth();
    int height = getHeight();
    // Fill the background:
    Rectangle viewRectangle = new Rectangle( 0,0,width,height );
    graphics2D.setPaint( this.backgroundColor );
    graphics2D.fill( viewRectangle );
    // Paint the arrow region:
    float w = width;
    float h = height;
    float offset = h/2.0f;
    GeneralPath p = new GeneralPath();
    p.moveTo(0f,0f);
    p.lineTo(w-offset,0f);
    p.lineTo(w,h/2f);
    p.lineTo(w-offset,h);
    p.lineTo(0f,h);
    p.lineTo(offset,h/2.0f);
    p.closePath();
    graphics2D.setPaint( this.foregroundColor );
    graphics2D.fill( p );
    // Draw the text:
    graphics2D.setPaint( this.textColor );
    graphics2D.setFont( this.font );
    graphics2D.drawString(this.text,(height/2) + this.font.getSize(),(height/2) + this.font.getSize()/2 );
    // Restore:
    graphics2D.setPaint( currentPaint );
    graphics2D.setFont(currentFont);
    
    super.paintChildren(g);    
  } // paint
  

  
  

}
