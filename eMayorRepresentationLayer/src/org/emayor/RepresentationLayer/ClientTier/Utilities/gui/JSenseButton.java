package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;


/**
 *   Behaves completely like an ordinary V1.22 JButton,
 *   but it will signalize mouseovers by slightly
 *   changing the fore/background contrast,
 *   like one is used from the internet.
 *
 *   Additionally it will try to play the sensebutton.wav
 *   sound on each mouseover.
 *
 *   To support change of constructors in future swing versions
 *   just change the constructors below accordingly.
 *
 *   If playing sound is not wished, use the constructor
 *   with passed doPlaySound = false.
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class JSenseButton extends JButton
                          implements MouseListener,MouseMotionListener
{

   private Color saveBackGround = null; // cache for original background color,
                                        // on mouseovers
   private Color saveForeGround = null; // cache for original background color,
                                        // on mouseovers

   // Attributes for caching colors so they can be reused for subsequent calls
   // with the same basiscolor and array size:
   private int numberOfGradientColors = 10;
   private Color backgroundGradientBasisColor = null;
   private Color[] backgroundGradientColors = new Color[numberOfGradientColors];
                               
   private boolean isSelected = false;
   private boolean doAnimate = true;

   private ColorChangeThread colorChangeThread = null;
   public JSenseButton thisButton;


   // default gradient strength
   // By calling setSmoothColorGradient this will be set to 36.0                       
   private double maxColorShift = 72.0; 
   
   
   
                                       
  /**
   *   Creates a button with no set text or icon.
   *   If mainFrameProvider is null, sound is not supported.
   */
  public JSenseButton( boolean doAnimate )
  {
   super(null, null);
   this.addMouseListener(this);
   this.addMouseMotionListener(this);
   this.thisButton = this;
   this.doAnimate = doAnimate;
   this.setFocusPainted(false);
   this.setCursor( new Cursor( Cursor.HAND_CURSOR) );
  }





  /**
   *   Creates a button with an icon.
   *   @param icon  the Icon image to display on the button
   */
  public JSenseButton( Icon icon,
                       boolean doAnimate )
  {
   super(null, icon);
   this.addMouseListener(this);
   this.addMouseMotionListener(this);
   this.thisButton = this;
   this.doAnimate = doAnimate;
   this.setFocusPainted(false);
   this.setCursor( new Cursor( Cursor.HAND_CURSOR) );
  }




  /**
   *   Creates a button with text.
   *   @param text  the text of the button
   */
  public JSenseButton( String text,
                       boolean doAnimate )
  {
   super(text, null);
   this.addMouseListener(this);
   this.addMouseMotionListener(this);
   this.thisButton = this;
   this.doAnimate = doAnimate;
   this.setFocusPainted(false);
   this.setCursor( new Cursor( Cursor.HAND_CURSOR) );
  }




  /**
   *   Creates a button with initial text and an icon.
   *   @param text  the text of the button.
   *   @param icon  the Icon image to display on the button
   */
  public JSenseButton( String text, Icon icon,
                       boolean doAnimate )
  {
   super(text,icon);
   this.addMouseListener(this);
   this.addMouseMotionListener(this);
   this.thisButton = this;
   this.doAnimate = doAnimate;
   this.setFocusPainted(false);
   this.setCursor( new Cursor( Cursor.HAND_CURSOR) );
  }




                  

 /**
  *  Call this, if you want a smaller color gradient.
  */
  public void setSmoothColorGradient()
  {
    this.maxColorShift = 24.0;
  }






 public void mouseEntered( MouseEvent e )
 {
  if( ( !isSelected ) && ( this.isVisible() ) &&
      ( this.isEnabled() ) && ( this.getIsParentFrameOnTop() )  )
   {
     isSelected = true;
     saveForeGround = this.getForeground();
     saveBackGround = this.getBackground();
     int foreGroundGrayScale = saveForeGround.getRed() +
                               saveForeGround.getGreen() +
                               saveForeGround.getBlue() ;
     int backGroundGrayScale = saveBackGround.getRed() +
                               saveBackGround.getGreen() +
                               saveBackGround.getBlue() ;
     final int ColorDelta = 40;
     if( foreGroundGrayScale < backGroundGrayScale )
      {
        // we have dark text on light bg. Enlarger contrast :
        int newRed;
        newRed = saveBackGround.getRed() + ColorDelta;
        if(newRed > 255) { newRed = 255; }
        int newGreen;
        newGreen = saveBackGround.getGreen() + ColorDelta;
        if(newGreen > 255) { newGreen = 255; }
        int newBlue;
        newBlue = saveBackGround.getBlue() + ColorDelta;
        if(newBlue > 255) { newBlue = 255; }
        if( this.doAnimate )
         {
          if( this.colorChangeThread == null )
           {
            colorChangeThread = new ColorChangeThread(thisButton);
            colorChangeThread.setStartColor( getBackground() );
            colorChangeThread.setTargetColor( new Color(newRed,newGreen,newBlue) );
            colorChangeThread.start();
           }
         }
        else
         {
           this.setSuperBackground( new Color(newRed,newGreen,newBlue) );
         }
      }
     else
      {
        // we have light text on dark bg. Enlarger contrast :
        int newRed;
        newRed = saveBackGround.getRed() - ColorDelta;
        if(newRed < 0 ) { newRed = 0; }
        int newGreen;
        newGreen = saveBackGround.getGreen() - ColorDelta;
        if(newGreen < 0 ) { newGreen = 0; }
        int newBlue;
        newBlue = saveBackGround.getBlue() - ColorDelta;
        if(newBlue < 0 ) { newBlue = 0; }
        if( this.doAnimate )
         {
          if( this.colorChangeThread == null )
           {
            colorChangeThread = new ColorChangeThread(thisButton);
            colorChangeThread.setStartColor( getBackground() );
            colorChangeThread.setTargetColor( new Color(newRed,newGreen,newBlue) );
            colorChangeThread.start();
           }
         }
        else
         {
           this.setSuperBackground( new Color(newRed,newGreen,newBlue) );
         }
      }
   } // if
 } // mouseEntered




 public void mouseExited( MouseEvent e )
 {
   this.normalize();
 }




 public void normalize()
 {
   if( ( this.isVisible() ) && ( this.getIsParentFrameOnTop() ) )
    {
     if( this.colorChangeThread != null )
      {
        this.colorChangeThread.doTerminate();
        this.colorChangeThread = null;
      }
     // reset original colors :
     if( saveBackGround != null) { super.setBackground(saveBackGround); }
     isSelected = false;
    }
 } // mouseExited




/**
 *  Additional checks, which actually only are needed,
 *  if the button is inside an JInternalFrame.
 */
 public boolean getIsParentFrameOnTop()
 {
   boolean onTop = true;
   Container workParent = this;
   while( true )
    {
      workParent = workParent.getParent(); // step back
      if(workParent == null)
       {
         break;
       }
      if( workParent instanceof JInternalFrame )
       {
         JInternalFrame iframe = (JInternalFrame)workParent;
         onTop = iframe.isSelected();
         break;
       }
    } // while
   return onTop;
 } // getIsParentFrameActivated




 public void mouseReleased( MouseEvent e )
 {
 }
 public void mousePressed( MouseEvent e )
 {
 }
 public void mouseClicked( MouseEvent e )
 {
 }
 public void mouseMoved( MouseEvent e )
 {
 }
 public void mouseDragged( MouseEvent e )
 {
 }



/**
 *   adjust the cache color and call super
 */
 public void setBackground( Color bgColor )
 {
   this.saveBackGround = bgColor;
   super.setBackground( bgColor);
 }



/**
 *   Used from the ColorChangeThread for setting
 *   the background using the super method.
 */
 public void setSuperBackground( Color bgColor )
 {
   super.setBackground(bgColor);
 }



/**
 *   adjust the cache color and call super
 */
 public void setForeground( Color fgColor )
 {
   this.saveForeGround = fgColor;
   super.setForeground( fgColor);
 }




/**
 *  Overwritten paintComponent method, which paints
 *  the background before it calls the ui delegate's paint
 *  method. Normally, the ui delegate's update method
 *  would paint the background pefore it would call the
 *  same paint method like done by this method.
 */
 protected void paintComponent( Graphics g )
 {
   if( ui != null )
    {
      // For this JSenseButton, we paint a gradient in the
      // background and do not react on the isOpaque attribute.
      this.paintBackgroundGradient( g );
      // Like in the super method, we let the UI delegate do the rest:
      ui.paint(g,this);
    }
 }
                            


                           
                                                         
  private void paintBackgroundGradient( Graphics g )      
  {
    // Check, if we have to (re)create the background gradient:
    if( this.backgroundGradientBasisColor == null ) // create it the first time
     {
       updateBackgroundGradientColors();
     }
    else
    if( this.backgroundGradientBasisColor != this.getBackground() ) // adjust
     {
       updateBackgroundGradientColors();
     }
    else
    if( this.getHeight() != this.numberOfGradientColors ) // adjust
     {
       updateBackgroundGradientColors();
     }
    // Now paint the gradient:
    int y1,y2;
    int w = this.getWidth();
    int h = this.getHeight();
    for( int i=0; i < this.numberOfGradientColors; i++ )
     {
       g.setColor( this.backgroundGradientColors[i] );
       y1 = (i*h)/ this.numberOfGradientColors;
       y2 = ((i+1)*h)/ this.numberOfGradientColors;
       g.fillRect( 0, y1, w, y2-y1 );
     }
  } // paintBackgroundGradient






  private void updateBackgroundGradientColors()
  {
    // Recreate the array, if the size has to be changed:
    if(  this.numberOfGradientColors != this.getHeight() )
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
       this.numberOfGradientColors = this.getHeight();
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
    this.backgroundGradientBasisColor = this.getBackground();
    int rBasis = this.backgroundGradientBasisColor.getRed();
    int gBasis = this.backgroundGradientBasisColor.getGreen();
    int bBasis = this.backgroundGradientBasisColor.getBlue();
    // Define the color shifts:
    int[] colorShift = new int[this.numberOfGradientColors];
    double centerIndex = this.numberOfGradientColors/2.0;
    double shift = 0.0;
    double normFactor = 4 * this.maxColorShift;
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
  } // updateBackgroundGradientColors







/**
 *  Sets the foreground color so that it has
 *  a good contrast to the background color.
 */
 public void maximizeForeGroundContrastToBackground()
 {
   // Set it white or black, depending from background
   // grayscale average :
   int grayScaleAverage = ( this.getBackground().getRed() +
                            this.getBackground().getGreen() +
                            this.getBackground().getBlue() ) / 3;
   if( grayScaleAverage > 127 )
    {
      this.setForeground( new Color(0,0,0) );
    }
   else
    {
      this.setForeground( new Color(255,255,255) );
    }
 }


                                          


 /**
  *  This thread performs the animated color change.
  */
  private class ColorChangeThread extends Thread
  {
     private final JSenseButton parentButton;
     private Color startColor  = new Color(160,160,160);
     private Color targetColor = new Color(100,100,100);
     private boolean terminateThread = false;

     public ColorChangeThread( JSenseButton theButton )
     {
       this.parentButton = theButton;
       this.setDaemon(true);
       // Give other threads more attention:
       this.setPriority( Thread.MIN_PRIORITY );
     }

     public void run()
     {
       try{ Thread.sleep(11); } catch(Exception wurscht00 ){}
       final int loops = 5;
       final float deltaRed   = 1f * (this.targetColor.getRed()   - this.startColor.getRed()   )/loops;
       final float deltaGreen = 1f * (this.targetColor.getGreen() - this.startColor.getGreen() )/loops;
       final float deltaBlue  = 1f * (this.targetColor.getBlue()  - this.startColor.getBlue()  )/loops;
       for( int i=1; i <= loops; i++ )
        {
           try{ Thread.sleep(49); } catch(Exception wurscht ){}
           final int iStep = i;
           SwingUtilities.invokeLater( new Runnable()
            {
              public void run()
              {
                if( !terminateThread )
                 {
                    int cRed   = (int)(startColor.getRed()   + iStep * deltaRed   );
                    int cGreen = (int)(startColor.getGreen() + iStep * deltaGreen );
                    int cBlue  = (int)(startColor.getBlue()  + iStep * deltaBlue  );
                    setSuperBackground(  new Color(cRed,cGreen,cBlue) );
                 }
              }
            });
           try{ Thread.yield(); } catch(Exception wurscht ){}
        }
     } // run


     public void setStartColor( final Color theStartColor )
     {
       this.startColor = theStartColor;
     }


     public void setTargetColor( final Color theTargetColor )
     {
       this.targetColor = theTargetColor;
     }


     public void doTerminate()
     {
       this.terminateThread = true;
     }

  } // class ColorChangeThread




} // JSenseButton           





