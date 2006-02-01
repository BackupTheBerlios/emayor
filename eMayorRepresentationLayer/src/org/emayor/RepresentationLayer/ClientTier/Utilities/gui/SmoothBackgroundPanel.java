package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;


/**         
 *   A panel used as background. It has a small deviation
 *   from the usual JPanel background color in its inside.
 * 
 *   5.1.06   jpl
 */

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.beans.*;



public class SmoothBackgroundPanel extends JPanel

{
  // Some Color Indices :
  public static final String ActiveTitleColorbackgroundColorIdentifier = "InternalFrame.activeTitleBackground";
  public static final String BackgroundColorIdentifier = "Menu.background";
  public static final String PanelBackGroundColorIdentifier = "Panel.background";

  // Fill Modes :
  public static final int SmoothCenterMode = 0;

                          

  private Color basisColor = null;


  private static final int numberOfColors = 16;

  private Color[] gradientColors = new Color[numberOfColors];

  private String colorIdentifier;


  private boolean allIsReadyForSpecialUpdates = false;
  // This flag is needed, because the updateUI() method sometimes
  // is initially called, when some referenced UIManager
  // functionality is not available yet. DO NOT REMOVE THIS.



 /**
  *  A Panel with a color gradient from the topleft corner.
  *  The color identifier has to be a valid LF color key,
  *  like for example one of the static strings in this class.
  *  fillMode also should be one of the final static ints of this class.
  */      
  public SmoothBackgroundPanel( LayoutManager layout,
                                String theColorIdentifier,
                                int fillMode )
  {
    super(layout);
    this.colorIdentifier = theColorIdentifier;
    this.updateSpecialUI(); // sets basisColor and insideColor and the gradients.
    EventQueue.invokeLater(new Runnable()
     {
       public void run()
       {
         allIsReadyForSpecialUpdates = true;
       }
     });
  } // Constructor



 /**
  *  Process additional update-work, after having called
  *  the parent method.
  */
  public void updateUI()
  {
    super.updateUI();
    // Additional update work :
    if( this.allIsReadyForSpecialUpdates )
     {
       updateSpecialUI();
     }
  }




 /**
  *  Must be called, when the lf theme changes.
  *  Called by the propertychange listener above.
  */
  public void updateSpecialUI()
  {
    // Derive the basisColor :
    this.basisColor = UIManager.getColor( this.colorIdentifier );
    int average = ( basisColor.getRed() + basisColor.getGreen() + basisColor.getBlue() )/3;
    double cs = 1.0 + 22.0/(double)this.numberOfColors;
    int colorStep = (int)cs;
    // Gradient direction: Away from foreground, so that the contrast gets bigger :
    Color foreGroundColor = UIManager.getColor("Panel.foreground");
    int foregroundAverage = ( foreGroundColor.getRed() + foreGroundColor.getGreen() + foreGroundColor.getBlue() )/3;
    int sign = ( foregroundAverage < average ) ? +1 : -1;
    // Define the gradientColors :
    int r,g,b;
    for( int i=0; i < this.numberOfColors; i++ )
     {
       r = basisColor.getRed()   + sign * i * colorStep; if( r > 255 ) r = 255; if( r < 0 ) r = 0;
       g = basisColor.getGreen() + sign * i * colorStep; if( g > 255 ) g = 255; if( g < 0 ) g = 0;
       b = basisColor.getBlue()  + sign * i * colorStep; if( b > 255 ) b = 255; if( b < 0 ) b = 0;
       gradientColors[i] = new Color(r,g,b);
     }
    // and redefine the basiscolor, so that it actually lies one step before
    // the index zero color :
    r = basisColor.getRed()   - sign * colorStep; if( r > 255 ) r = 255; if( r < 0 ) r = 0;
    g = basisColor.getGreen() - sign * colorStep; if( g > 255 ) g = 255; if( g < 0 ) g = 0;
    b = basisColor.getBlue()  - sign * colorStep; if( b > 255 ) b = 255; if( b < 0 ) b = 0;
    this.basisColor = new Color(r,g,b);
  }

   
   
   

 /**
  *  Overwritten paint method to have a slight color gradient.
  */
  public void paint( Graphics g )
  {
    Graphics2D graphics2D = (Graphics2D)g;
    final Paint savePaint = graphics2D.getPaint();
    // Then apply the gradient :
    graphics2D.setPaint( this.basisColor );
    graphics2D.fill( graphics2D.getClip() );

    final int panelWidth = this.getWidth();
    final int panelHeight = this.getHeight();
    int w = panelWidth;
    int h = panelHeight;
    // Make the ratio a bit progressive, but not constant :
    int horizontalStep = 1;
    int verticalStep   = 1;
    if( w > h )
     {
       horizontalStep = (int)( 0.60 * w / this.numberOfColors );
       verticalStep   = (int)( 0.99 * h / this.numberOfColors );
     }
    else
     {
       horizontalStep = (int)( 0.99 * w / this.numberOfColors );
       verticalStep   = (int)( 0.60 * h / this.numberOfColors );
     }
    if( horizontalStep < 1 ) horizontalStep = 1;
    if( verticalStep   < 1 ) verticalStep   = 1;
    for( int i=0; i < this.numberOfColors; i++ )
     {
        graphics2D.setColor( this.gradientColors[i] );
        graphics2D.fillOval( (panelWidth-w)/2,(panelHeight-h)/2,w,h);
        w -= horizontalStep; if( w < 2 ) break;
        h -= verticalStep;   if( h < 2 ) break;
     }
    graphics2D.setPaint( savePaint );
    super.paintChildren(graphics2D);
  } // paint
  



 /**
  *  Overwitten, so it doesnt clear all, but
  *  one has to call super, so children are properly rendered.
  */
  public void update( Graphics g )
  {
    paint(g);
  }

}
