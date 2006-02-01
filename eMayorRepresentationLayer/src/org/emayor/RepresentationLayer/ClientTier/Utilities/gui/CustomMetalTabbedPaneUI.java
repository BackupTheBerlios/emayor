package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;


import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;


                     
public class CustomMetalTabbedPaneUI extends MetalTabbedPaneUI
{                     


   // Attributes for caching colors so they can be reused for subsequent calls
   // with the same basiscolor and array size:
   private int numberOfGradientColors = 10;
   private Color backgroundGradientBasisColor = null;
   private Color[] backgroundGradientColors = new Color[numberOfGradientColors];
   
   private Color customTabPaneBackgroundColor = null;
                            
                                    
  public static ComponentUI createUI( JComponent x )
  {
    return new CustomMetalTabbedPaneUI();
  }  




  public void update( Graphics g, JComponent c )
  {
    // We fill the background with a slightly changed color:
    if( this.customTabPaneBackgroundColor == null ) // create it the first time
     {
       updateCustomTabPaneBackgroundColor();
     }
    g.setColor( this.customTabPaneBackgroundColor );
    g.fillRect( 0, 0, c.getWidth(),c.getHeight() );

    paint( g,c );
  }
  



  protected void paintTabBackground( Graphics g, int tabPlacement,
                                     int tabIndex,
                                     int x, int y, int w, int h,
                                     boolean isSelected )
  { 
    if( isSelected )
     {
       super.paintTabBackground(g,tabPlacement,tabIndex,x,y,w,h,isSelected);
     }
    else 
     {
       this.paintBackgroundGradient( g,x,y,w,h,isSelected );
     }  
  }
          
  
  


  private void paintBackgroundGradient( Graphics g,
                                        int x, int y, int w, int h, 
                                        boolean isSelected )      
  {
    // Check, if we have to (re)create the background gradient:
    if( this.backgroundGradientBasisColor == null ) // create it the first time
     {
       updateBackgroundGradientColors(x,y,w,h,isSelected);
     }  
    else
    if( this.backgroundGradientBasisColor != tabAreaBackground ) // adjust
     {
       updateBackgroundGradientColors(x,y,w,h,isSelected);
     }
    else
    if( h != this.numberOfGradientColors ) // adjust
     {
       updateBackgroundGradientColors(x,y,w,h,isSelected);
     }                                                                    

    
    // Now paint the gradient:
    int y1,y2;
    for( int i=0; i < this.numberOfGradientColors; i++ )
     {
       g.setColor( this.backgroundGradientColors[i] );
       y1 = (i*h)/ this.numberOfGradientColors;
       y2 = ((i+1)*h)/ this.numberOfGradientColors;
       g.fillRect( x, y+y1, w, y+y2-y1 );
     }
  } // paintBackgroundGradient






  private void updateBackgroundGradientColors( int x, int y, int w, int h, 
                                               boolean isSelected )
  {
    // Recreate the array, if the size has to be changed:
    if(  this.numberOfGradientColors != h )
     {
       // GC assistance:
       if( this.backgroundGradientColors != null )
        {
          for( int i=0; i < this.backgroundGradientColors.length; i++ )
           {
             this.backgroundGradientColors[i] = null;
           }
          this.backgroundGradientColors = null;
        }
       // Recreate:
       this.numberOfGradientColors = h;
       this.backgroundGradientColors = new Color[ this.numberOfGradientColors ];
     }
    else
     {
       // GC assistance:(all elements will be overwritten, so set them to null
       // already here)
       if( this.backgroundGradientColors != null )
        {
          for( int i=0; i < this.backgroundGradientColors.length; i++ )
           {
             this.backgroundGradientColors[i] = null;
           }
        }
     }
    // backgroundGradientBasisColor also is used for testing for adjustments above.
    // If it (=the reference) changes, the gradient is updated here.
    this.backgroundGradientBasisColor = tabAreaBackground;
    int rBasis = this.backgroundGradientBasisColor.getRed();
    int gBasis = this.backgroundGradientBasisColor.getGreen();
    int bBasis = this.backgroundGradientBasisColor.getBlue();
    // Define the color shifts:  
    int[] colorShift = new int[this.numberOfGradientColors];
    double centerIndex = this.numberOfGradientColors/2.0;
    double maxShift = 72.0;
    double shift = 0.0;
    double normFactor = 4*maxShift;
    double offset = 0;
    int sign = 1;
    for( int i=0; i < this.numberOfGradientColors; i++ )             
     {
       offset = 0.5 - (1.0*i)/this.numberOfGradientColors;
       shift  = offset*offset*normFactor;
       if( i > centerIndex ) sign = -1;
       colorShift[i] = sign * (int)shift;
     }
    // And set the colors:
    int r,g,b;
    for( int i=0; i < this.numberOfGradientColors; i++ )
     {
       r = rBasis + colorShift[i]; if( r < 0 ) r = 0; if( r > 255 ) r = 255;
       g = gBasis + colorShift[i]; if( g < 0 ) g = 0; if( g > 255 ) g = 255;
       b = bBasis + colorShift[i]; if( b < 0 ) b = 0; if( b > 255 ) b = 255;
       this.backgroundGradientColors[i] = new Color(r,g,b);
     } // for
    colorShift = null;

    // Finally define the tabPaneBackgroundColor, which we change slightly
    // from the backgroundGradientBasisColor:
    this.updateCustomTabPaneBackgroundColor();
    
  } // updateBackgroundGradientColors





  private void updateCustomTabPaneBackgroundColor()
  {                            
    // Define the tabPaneBackgroundColor, which we change slightly
    // from the backgroundGradientBasisColor:
    int r = tabAreaBackground.getRed();
    int g = tabAreaBackground.getGreen();
    int b = tabAreaBackground.getBlue();
    int bg_offset = 24;
    if( (r+g+b) > 384 )
     {
       // Bright background. Decrease the contrast, shift towards middle.
       r -= bg_offset; if(r < 0) r = 0;
       g -= bg_offset; if(g < 0) g = 0;
       b -= bg_offset; if(b < 0) b = 0;
     }
    else
     {
       // Dark background. Decrease the contrast, shift towards middle.
       r += bg_offset; if(r > 255) r = 0;
       g += bg_offset; if(g > 255) g = 0;
       b += bg_offset; if(b > 255) b = 0;
     }
    // Be nice to the memory: Only create it, if its required:
    if( customTabPaneBackgroundColor != null )
     {
       if(  (customTabPaneBackgroundColor.getRed()   != r) ||
            (customTabPaneBackgroundColor.getGreen() != g) ||
            (customTabPaneBackgroundColor.getBlue()  != b)   )
        {
           customTabPaneBackgroundColor = new Color(r,g,b);
        }
     }
    else
     {
       customTabPaneBackgroundColor = new Color(r,g,b);
     }
  } // updateCustomTabPaneBackgroundColor





/**
 *  Sets the foreground color so that it has
 *  a good contrast to the background color.
 */
 public void maximizeForeGroundContrastToBackground()
 {
   // Set it white or black, depending from background
   // grayscale average :
   int grayScaleAverage = ( tabAreaBackground.getRed() +
                            tabAreaBackground.getGreen() +
                            tabAreaBackground.getBlue() ) / 3;
   /*
   if( grayScaleAverage > 127 )
    {
      this.setForeground( new Color(0,0,0) );
    }
   else
    {
      this.setForeground( new Color(255,255,255) );
    }
   */ 
 }
          



} // CustomMetalTabbedPaneUI
          
          
          
          
          
