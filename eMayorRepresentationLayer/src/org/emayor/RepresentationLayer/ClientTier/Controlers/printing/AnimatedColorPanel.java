package org.emayor.RepresentationLayer.ClientTier.Controlers.printing;

/**
 *  A panel, which [slowly] animates its background colors,
 *  but with only consuming neglectable cpu time.
 *
 *  It has a constant height of labelfont/2 and can be
 *  displayed somewhere on the bottom of another panel.
 *
 *  Note, that one must start/end the animation manually by
 *  by a call to activateAnimation().
 *  One can use a ComponentListener attached to a toplevel container
 *  for this purpose.
 *
 *  One can use it for busy info messages. The animation
 *  attracts the users attention, so one knows what's going on.
 */

import java.awt.*;
import javax.swing.*;


public class AnimatedColorPanel extends JPanel
{

  private ActivatedColorThread activatedColorThread = null;
  // The thread, which runs in the selected state.

  public AnimatedColorPanel thisPanel;
  private boolean allIsReadyForSpecialUpdates;
  private Paint paint; // used in activated state
  private float gradientLength = 40;

  private long delayTime = 100;

  
  /**
   *  This one creates a panel with BoxLayout consisting
   *  of a vertical strut, which keeps the height on the
   *  label font size.
   *
   *  delayTime in millisec is the time between animation updates.
   *  It should be bigger than 99 millisecs and controls the cpu load.
   */
   public AnimatedColorPanel( long delayTime )
   {
     super();
     this.delayTime = delayTime;
     if( this.delayTime < 44 ) this.delayTime = 44; // be nice to other threads
     int panelHeight = 2+UIManager.getFont("Label.font").getSize()/2;
     BoxLayout boxLayout = new BoxLayout( this,BoxLayout.Y_AXIS );
     this.setLayout( boxLayout );
     this.add( Box.createVerticalStrut(panelHeight) );
     this.setOpaque(false); // enables us to see the background paint
     this.thisPanel = this;
     this.paint = this.getBackground(); // initially
     EventQueue.invokeLater(new Runnable()
      {
        public void run()
        {
          allIsReadyForSpecialUpdates = true;
        }
      });
     this.updateSpecialUI(); // one time for initializing
   } // Constructor




  /**
   *  This one requires a LayoutManager and doesn't and
   *  the geometry must be handles by the caller.
   *
   *  delayTime in millisec is the time between animation updates.
   *  It should be bigger than 99 millisecs and controls the cpu load.
   *
   */
   public AnimatedColorPanel( LayoutManager layoutManager,
                              long delayTime )
   {
     super();
     this.delayTime = delayTime;
     if( this.delayTime < 44 ) this.delayTime = 44; // be nice to other threads
     this.setLayout( layoutManager );
     this.setOpaque(false); // enables us to see the background paint
     this.thisPanel = this;
     EventQueue.invokeLater(new Runnable()
      {
        public void run()
        {
          allIsReadyForSpecialUpdates = true;
        }
      });
     this.updateSpecialUI(); // one time for initializing
   } // Constructor







  /**
   *  This must be called manually for starting/ending
   *  the animation.
   *  One can use a component listener (on a toplevel container)
   *  for this purpose.
   */
   public synchronized void activateAnimation( boolean doActivate )
   {
     if( doActivate )
      {
        if( this.activatedColorThread == null )
         {
           this.paint = this.getBackground(); // initially
           this.gradientLength = this.getWidth()/2f;
           this.activatedColorThread = new ActivatedColorThread( this );
           this.activatedColorThread.start();
         }
      }
     else
      {
        if( this.activatedColorThread != null )
         {
           this.activatedColorThread.doTerminate();
           this.activatedColorThread = null;
           this.paint = this.getBackground(); // initially
           this.repaint();
         }
      }
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
   *  Process additional update-work, after having called
   *  the parent method.
   */
   public void updateUI()
   {
     super.updateUI();
     // additional updates :
     if( this.allIsReadyForSpecialUpdates )
      {
        updateSpecialUI();
      } 
   }



   public void updateSpecialUI()
   {
     // Check if we have an efcn theme :
     //this.setForeground( UIManager.getColor("Label.foreground") );
     //this.setBackground( UIManager.getColor("Label.background") );
   } // updateSpecialUI





   public void paint( final Graphics g )
   {                     
     final Graphics2D graphics2D = (Graphics2D)g;
     if( this.paint != null )
      {
        Rectangle rec = this.getVisibleRect();
        graphics2D.setPaint(this.paint);
        graphics2D.fill( rec );
      }
     else
      {
        this.paint = this.getBackground(); // initially
      }
     super.paint(g);
   }


   public Color slightlyBrighter( final Color color )
   {
     int r = color.getRed()   + 32; if( r > 255 ) r = 255;
     int g = color.getGreen() + 32; if( g > 255 ) g = 255;
     int b = color.getBlue()  + 32; if( b > 255 ) b = 255;
     return new Color( r,g,b );
   } // slightlyBrighter



   public Color slightlyDarker( final Color color )
   {
     int r = color.getRed()   - 32; if( r < 0 ) r = 0;
     int g = color.getGreen() - 32; if( g < 0 ) g = 0;
     int b = color.getBlue()  - 32; if( b < 0 ) b = 0;
     return new Color( r,g,b );
   } // slightlyBrighter



   public void setPaint( final Paint newPaint )
   {                                                             
     if( newPaint != null ) this.paint = newPaint;
     this.repaint();
   }

                




  /**
   *  This thread performs the animated color change while the
   *  button is activated.
   */
   private class ActivatedColorThread extends Thread
   {
      private Color brightColor;
      private Color darkColor;
      private boolean terminateThread = false;
      private JPanel parentPanel;


      public ActivatedColorThread( JPanel thePanel )
      {
        this.parentPanel = thePanel;
        this.setDaemon(true); // let it terminate, when the JVM shuts down

        this.setPriority( Thread.NORM_PRIORITY-1 );
        // This f.ex. has an effect on the compiling times, where it
        // is used for the compile info label : It can make the
        // compile times shorter.

        // Give other threads more attention:
        this.setPriority( Thread.MIN_PRIORITY );
        this.brightColor = this.parentPanel.getBackground().brighter().brighter();
        this.darkColor   = this.parentPanel.getBackground().darker().darker();
      } // Constructor


      public void run()
      {
        int maximumLocation = 0;
        int maximumLocationStep = 5;

        int colorOffsetRed = (int)(Math.random()*80.0 ) - 40;
        int colorOffsetStepRed = (Math.random() > 0.5) ? 5 : -5;

        int colorOffsetGreen = (int)(Math.random()*80.0 ) - 40;
        int colorOffsetStepGreen = (Math.random() > 0.5) ? 4 : -4;

        int colorOffsetBlue = (int)(Math.random()*80.0 ) - 40;
        int colorOffsetStepBlue = (Math.random() > 0.5) ? 4 : -4;

        // Take the 2:1 average of background and foreground color :
        Color bg = getBackground();
        Color fg = getForeground();
        final int rBasis = ( 2*bg.getRed()   + fg.getRed()   ) / 3;
        final int gBasis = ( 2*bg.getGreen() + fg.getGreen() ) / 3;
        final int bBasis = ( 2*bg.getBlue()  + fg.getBlue()  ) / 3;
        final Color basisColor = new Color( rBasis, gBasis, bBasis );

        int shiftLength = 2*this.parentPanel.getWidth();
        
        while( !this.terminateThread )
         {
            // turn direction at borders :
            if( maximumLocation > shiftLength )
             {
               maximumLocationStep = -6;
             }
            if( maximumLocation < 6 )
             {
               maximumLocationStep = +6;
             }
            maximumLocation += maximumLocationStep;

            // Make the color change effect medium ( +- 60 ) :
            // Invert the step at the borders:
            colorOffsetRed += colorOffsetStepRed;
            if( ( colorOffsetRed >  120 ) || ( colorOffsetRed < -120 ) ) 
                  colorOffsetStepRed = -colorOffsetStepRed;

            colorOffsetGreen += colorOffsetStepGreen;
            if( ( colorOffsetGreen >  120 ) || ( colorOffsetGreen < -120 ) )
                  colorOffsetStepGreen = -colorOffsetStepGreen;

            colorOffsetBlue += colorOffsetStepBlue;
            if( ( colorOffsetBlue >  120 ) || ( colorOffsetBlue < -120 ) )
                  colorOffsetStepBlue = -colorOffsetStepBlue;

            int c_Red   = basisColor.getRed()   + colorOffsetRed;    if( c_Red   > 255  ) c_Red   = 255;  if( c_Red < 0   ) c_Red   = 0;
            int c_Green = basisColor.getGreen() - colorOffsetGreen;  if( c_Green > 255  ) c_Green = 255;  if( c_Green < 0 ) c_Green = 0;
            int c_Blue  = basisColor.getBlue()  + colorOffsetBlue;   if( c_Blue  > 255  ) c_Blue  = 255;  if( c_Blue < 0  ) c_Blue  = 0;

            final int cRed   = c_Red;
            final int cGreen = c_Green;
            final int cBlue  = c_Blue;
            final int currentLocation = maximumLocation;

            // Don't paint, if the parentPanel is not showing or not visible :
            if( this.parentPanel.isShowing() && this.parentPanel.isVisible() )
             {
               EventQueue.invokeLater( new Runnable()
                {
                  public void run()
                  {
                     if( !terminateThread )
                     {
                        Color secondColor = new Color(cRed,cGreen,cBlue);
                        Color firstColor = encreaseContrast( getBackground(),secondColor );
                        final GradientPaint gradientPaint =
                              new GradientPaint( 2f*currentLocation,0f,
                                                 firstColor,
                                                 2f*currentLocation + gradientLength,0f,
                                                 secondColor,
                                                 true /* cyclic */ );
                        if( gradientPaint != null)
                         {
                           setPaint( gradientPaint );
                         }
                     }
                  }
                });
             }
            try{ Thread.sleep(delayTime); } catch(Exception wurscht ){}
         } // thread while loop
      } // run





      public void doTerminate()
      {
        this.terminateThread = true;
      }


      public Color encreaseContrast( Color basisColor, Color checkColor )
      {
        int basisValue = basisColor.getRed() + basisColor.getGreen() + basisColor.getBlue();
        int checkValue = checkColor.getRed() + checkColor.getGreen() + checkColor.getBlue();
        int r = 0;
        int g = 0;
        int b = 0;
        int offset = 64;
        if( basisValue > checkValue )
         {
           r = basisColor.getRed()   + offset; if( r > 255 ) r = 255;
           g = basisColor.getGreen() + offset; if( g > 255 ) g = 255;
           b = basisColor.getBlue()  + offset; if( b > 255 ) b = 255;
         }
        else
         {
           r = basisColor.getRed()   - offset; if( r < 0 ) r = 0;
           g = basisColor.getGreen() - offset; if( g < 0 ) g = 0;
           b = basisColor.getBlue()  - offset; if( b < 0 ) b = 0;
         }
        return new Color(r,g,b); 
      } // encreaseContrast


   } // class ActivatedColorThread
  
  



}
