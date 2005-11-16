package org.emayor.client.Utilities.gui;

  /**
   *  A panel used as background for panels.
   *  Has some gradient and other drawing stuff on it.
   *
   *  Note: You have to call setOpaque(false) for all
   *        swing objects, which you add to this panel,
   *        otherwise you wont see the panel color gradient.
   */


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.plaf.metal.*;
import javax.swing.event.*;


                  
public class GradientPanel extends JPanel
{

  // The gradient types :
  public static int ApplyUpperLeftCornerHighLight = 0;
  public static int ApplyVerticalHighLight        = 1;
  public static int ApplyHorizontalHighLight      = 2;

  // Some predefined color keystrings :
  public static String ActiveTitleBackground = "InternalFrame.activeTitleBackground";
  public static String PanelBackground       = "Panel.background";


  // The gradient strength :
  public static int LightGradientStrength  = 0;
  public static int MediumGradientStrength = 1;
  public static int StrongGradientStrength = 2;


  private Color lightColor = new Color(190,190,250);
  private Color mediumColor   = new Color(120,120,180);
  private Color darkColor   = new Color(80,80,120);
  float xGradient;

  private Color basisColor; // around this the gradient will be

  // gradient look : (defaults)
  private int gradientType     = ApplyUpperLeftCornerHighLight;
  private int gradientStrength = MediumGradientStrength;
  private String colorKey      = ActiveTitleBackground;

  float gradientLength;


  private int finalColorOffset;
  private int colorOffset;



  private boolean useParentBackGround = false; // when a background color has been assigned

  private ImageIcon backgroundImage = null;
  // This is set according to the current theme, and changes,
  // when the theme changes.

  private boolean useBackgroundPicture = true;
  // this can be turn on/off using enableBackgroundPicture().

  private ImageIcon customBackgroundImage = null;
  // This can be set by using one of the constructors.
  // If this is set, this backgroundimage is used always,
  // and not the one associated to the current theme.


  private boolean readyForSpecialUpdates = false;
  // Needed, as some updateUI() calls are too early
  // for the special UI updates, cause some components
  // could not be existing yet.



 /**
  *  Creates a panel where you can pass
  *  theGradientType = ApplyUpperLeftCornerHighLight
  *                 or ApplyVerticalHighLight
  *                 or ApplyHorizontalHighLight
  *
  *  and
  *  theGradientStrength = LightGradientStrength
  *                     or MediumGradientStrength
  *                     or StrongGradientStrength
  *
  *  and
  *  theColorKey = null
  *             or ActiveTitleBackground
  *             or PanelBackground
  *             or any valid theme colorkey.
  *
  */
  public GradientPanel( final int    theGradientType,
                        final int    theGradientStrength,
                        final String theColorKey )
  {
    this( new BorderLayout(),true,theGradientType,theGradientStrength,theColorKey );
  }





 /**
  *  Creates a panel where you can pass
  *  theGradientType = ApplyUpperLeftCornerHighLight
  *                 or ApplyVerticalHighLight
  *                 or ApplyHorizontalHighLight
  *
  *  and
  *  theGradientStrength = LightGradientStrength
  *                     or MediumGradientStrength
  *                     or StrongGradientStrength
  *
  *  and
  *  theColorKey = null
  *             or ActiveTitleBackground
  *             or PanelBackground
  *             or any valid theme colorkey.
  *
  */
  public GradientPanel( LayoutManager layout,
                        int    theGradientType,
                        int    theGradientStrength,
                        String theColorKey    )
  {
    this(layout,true,theGradientType,theGradientStrength,theColorKey);
  }




 /**
  *  Creates a panel where you can pass
  *  theGradientType = ApplyUpperLeftCornerHighLight
  *                 or ApplyVerticalHighLight
  *                 or ApplyHorizontalHighLight
  *
  *  and
  *  theGradientStrength = LightGradientStrength
  *                     or MediumGradientStrength
  *                     or StrongGradientStrength
  *
  *  and
  *  theColorKey = null
  *             or ActiveTitleBackground
  *             or PanelBackground
  *             or any valid theme colorkey.
  *
  */
  public GradientPanel( LayoutManager layout,
                        boolean isDoubleBuffered,
                        int    theGradientType,
                        int    theGradientStrength,
                        String theColorKey   )
  {
    super(layout,isDoubleBuffered);
    this.gradientType = theGradientType;
    this.gradientStrength = theGradientStrength;
    if( colorKey != null )
     {
       this.colorKey = theColorKey;
     } // else use the default
    // scale the gradient along with the current font size :
    float unitSize = UIManager.getFont("TextField.font").getSize2D();
    this.xGradient = unitSize;

    this.colorOffset = 0; // This will increased to the value of
                          // finalColorOffset in a few steps by the
                          // startupThread.

    this.finalColorOffset = 70; // medium gradient strength
    if( this.gradientStrength == LightGradientStrength )
     {
       this.finalColorOffset = 40;
     }
    if( this.gradientStrength == StrongGradientStrength )
     {
       this.finalColorOffset = 90;
     }


    this.updateSpecialUI(); // sets basisColor, startColor and endColor

    // Launch the startupthread, which will increase the coloroffset :
    final StartupThread startupThread = new StartupThread(this);
    // but launch him after all swing work has been done -
    // so set it into the swing queue :
    EventQueue.invokeLater( new Runnable()
     {
       public void run()
       {
         startupThread.start();
       }
     });
    EventQueue.invokeLater( new Runnable()
     {
        public void run()
        {
          readyForSpecialUpdates = true;
        }
     });
  } // Constructor







 /**
  *  This last constructor additionally defines a custom background
  *  picture. If this constructor is used, the passed backround
  *  picture will be used for ever, instead of getting or changing
  *  the background picture with the theme.
  */
  public GradientPanel( LayoutManager layout,
                        int    theGradientType,
                        int    theGradientStrength,
                        String theColorKey,
                        ImageIcon customBackgroundImage   )
  {
    this( layout,theGradientType,theGradientStrength,theColorKey);
    this.customBackgroundImage = customBackgroundImage;
  }


 /**
  * Just calls <code>paint(g)</code>.  This method was overridden to
  * prevent an unnecessary call to clear the background.
  *
  * @param g the Graphics context in which to paint
  */
  public void update(Graphics g)
  {
    this.paint(g);
  }


 /**
  *  Overwritten method. Additionally updates special components.
  */
  public void updateUI()
  {
    super.updateUI();
    if( this.readyForSpecialUpdates )
     {
         if( !useParentBackGround ) // only if setBackground was never called
          {
            updateSpecialUI();
            // rescale the gradient along with the current font size :
            float unitSize = UIManager.getFont("TextField.font").getSize2D();
            xGradient = unitSize;
          }
     }
  }






 /**
  *  Seta a fixed background color, and with that : turns out the
  *  UIManager update mechanism.
  */
  public void setBackground( Color bgColor )
  {
    super.setBackground(bgColor);
    this.useParentBackGround = true; // turns off UIManager special update
    this.basisColor = super.getBackground();
    this.calculateColors();
  }



 /**
  * Calculate the start and endcolor of the gradient
  * taking the basisColor as center color :
  */
  private void calculateColors()
  {
    int rBase = this.basisColor.getRed();
    int gBase = this.basisColor.getGreen();
    int bBase = this.basisColor.getBlue();
    // start color is lighter :
    int rStart = rBase + colorOffset;
    int gStart = gBase + colorOffset;
    int bStart = bBase + colorOffset;
    if(  (rStart <= 255) && (gStart <= 255) && (bStart <= 255) )
     {
       this.lightColor = new Color( rStart,gStart,bStart );
     } else
     {
       if( rStart > 255 ) rStart = 255;
       if( gStart > 255 ) gStart = 255;
       if( bStart > 255 ) bStart = 255;
       this.lightColor = new Color( rStart,gStart,bStart );
     }

    this.mediumColor = this.basisColor;

    rStart = rBase - colorOffset;
    gStart = gBase - colorOffset;
    bStart = bBase - colorOffset;
    if(  (rStart >= 0) && (gStart >= 0) && (bStart >= 0) )
     {
       this.darkColor = new Color( rStart,gStart,bStart );
     } else
     {
       if( rStart < 0 ) rStart = 0;
       if( gStart < 0 ) gStart = 0;
       if( bStart < 0 ) bStart = 0;
       this.darkColor = new Color( rStart,gStart,bStart );
     }

    // If the current panel has a background image, we use this one and
    // set the medium color slightly transparent :
    // The background image is valid, when there is a theme with background
    // image OR when a custom backgroundimage was set :
    if( this.customBackgroundImage != null )
     {
        // A custom image was set, so set the color a bit transparent :
        this.mediumColor = new Color(  mediumColor.getRed(),
                                       mediumColor.getGreen(),
                                       mediumColor.getBlue(),
                                       230 );
        // and use this one :
        if( this.useBackgroundPicture )
         {
           this.backgroundImage = this.customBackgroundImage;
         }
     }
  } // calculateColors

     


  public void enableBackgoundPicture( final boolean doEnable )
  {
    this.useBackgroundPicture = doEnable;
    if( doEnable )
     {
       this.calculateColors();
     } else
     {
       this.backgroundImage = null;
     }
  }



 /**
  *  Must be called, when the lf theme changes.
  *  Called by the propertychange listener above.
  */
  public void updateSpecialUI()
  {
    // set it as basis :
    this.basisColor = UIManager.getColor(this.colorKey);
    // Calculate the start and endColors from that :
    this.calculateColors();
  }


 /**
  *  Overwritten paint method to have a slight color gradient.
  */
  public void paint( Graphics g )
  {
    Graphics2D graphics2D = (Graphics2D)g;
    final Paint savePaint = graphics2D.getPaint();

    // draw the background image, if we have one :
    if( this.backgroundImage != null )
     {
      int xMax = this.getWidth();
      int yMax = this.getHeight();
      int imageWidth  = this.backgroundImage.getIconWidth();
      int imageHeight = this.backgroundImage.getIconHeight();
      // Security [prevents endless loop, case an attribute is zero]
      if( (xMax > 0) &&
          (yMax > 0) &&
          (imageWidth > 0) &&
          (imageHeight > 0)    )
       {
          int x = 0;
          int y = 0;
          while( x < xMax )
           {
             while( y < yMax )
              {
                graphics2D.drawImage( this.backgroundImage.getImage(),x,y,imageWidth,imageHeight,
                                      this.backgroundImage.getImageObserver() );
                y += imageHeight;
              } // while y
             y = 0;
             x += imageWidth;
           } // while x
       } // if
     } // if

    if( this.gradientType == ApplyUpperLeftCornerHighLight )
     {
      GradientPaint upperLeftGradientPaint =
                    new GradientPaint( 0f,0f,
                                       lightColor,
                                       xGradient,xGradient*5.0f,
                                       mediumColor );

      graphics2D.setPaint( upperLeftGradientPaint );
      graphics2D.fill( graphics2D.getClip() );
     }
    else
    if( this.gradientType == ApplyHorizontalHighLight )
     {
      GradientPaint upperLeftGradientPaint =
                    new GradientPaint( 0f,0f,
                                       lightColor,
                                       xGradient*5.0f,0f,
                                       mediumColor );

      graphics2D.setPaint( upperLeftGradientPaint );
      graphics2D.fill( graphics2D.getClip() );
     }
    else
    if( this.gradientType == ApplyVerticalHighLight )
     {

      this.gradientLength = xGradient;
      if( gradientLength > this.getHeight()/2.5f )
       {
         gradientLength = this.getHeight()/2.5f;
       }
      GradientPaint upperVerticalGradientPaint =
                    new GradientPaint( 0f,0f,
                                       this.lightColor,
                                       0f, gradientLength,
                                       this.mediumColor );

      GradientPaint lowerVerticalGradientPaint =
                    new GradientPaint( 0f,getHeight(),
                                       this.darkColor,
                                       0f,getHeight() - gradientLength,
                                       this.mediumColor );

      Shape saveClip = graphics2D.getClip();

      Rectangle rLower = new Rectangle( 0,getHeight()/2,getWidth(),1+getHeight()/2 );
      graphics2D.setPaint( lowerVerticalGradientPaint );
      graphics2D.fill( rLower );

      Rectangle rUpper = new Rectangle( 0,0,getWidth(),1+getHeight()/2 );
      graphics2D.setPaint( upperVerticalGradientPaint );
      graphics2D.fill( rUpper );

      graphics2D.setClip(saveClip);
     }
    graphics2D.setPaint( savePaint );
    super.paintChildren(graphics2D);
  } // paint
  




  public float getGradientLength()
  {
    return this.gradientLength;
  }





 /**
  *   Changes the color offset. Must be called inside
  *   the event dispatch thread.
  */
  public void setColorOffset( int newValue )
  {
    this.colorOffset = newValue;
    this.calculateColors();
    if( this.isShowing() )
     {
       this.updateUI();
     }
  }




  public int getFinalColorOffset()
  {
    return this.finalColorOffset;
  }





 /**
  * The startup thread, which makes this panel shape itself
  * in the first 3 seconds of its lifetime
  */
  private class StartupThread extends Thread
  {
     private GradientPanel bgPanel;

     public StartupThread( GradientPanel bgPanel )
     {
       this.bgPanel = bgPanel;
       this.setDaemon(true);
     }

     public void run()
     {
       int loops = 16;
       int offsetStep = bgPanel.getFinalColorOffset()/loops;
       for( int i=1; i <= loops; i++ )
        {
           final int colorOffset = i*offsetStep;
           SwingUtilities.invokeLater( new Runnable()
            {
              public void run()
              {
                bgPanel.setColorOffset(colorOffset);
              }
            });
           if( i==1 )
            {
              // Wait half a second initially - increases the effect :
              try{ Thread.sleep(660); } catch( Exception sdfkjh ){ }
            }
           else
            {
              try{ Thread.sleep(90); } catch(Exception wurscht ){}
            }
        }
     }

  } // class StartupThread



} // BackgroundPanel




   
