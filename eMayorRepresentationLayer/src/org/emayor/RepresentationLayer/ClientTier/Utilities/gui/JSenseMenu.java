package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;

/**
 *   Behaves completely like an ordinary V1.22 JMenu,
 *   but it will signalize mouseovers by slightly
 *   changing the fore/background contrast,
 *   like one is used from the internet.
 *
 *   Additionally it will try to play the menumouseover.wav
 *   sound on each mouseover.
 *
 *   To support change of constructors in future swing versions
 *   just change the constructors below accordingly.
 *
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;

import org.emayor.RepresentationLayer.ClientTier.ResourceLoader;
                                                 


public class JSenseMenu extends JMenu
                        implements MouseListener,MouseMotionListener
{                   

   Color saveBackGround = null; // cache for original background color,
                                // on mouseovers
   Color saveForeGround = null; // cache for original background color,
                                // on mouseovers
   private boolean isSelected = false;

   private ResourceLoader resourceLoader;


  /**
   * Creates a new JMenu with no text.
   */                                       
   public JSenseMenu( ResourceLoader theResourceLoader )
   {
    super("");
    this.resourceLoader = theResourceLoader;
    this.addMouseListener(this);
    this.addMouseMotionListener(this);
   }



  /**
   * Creates a new JMenu with the supplied string as its text
   *
   * @param s  The text for the menu label
   */
   public JSenseMenu(String s, final  ResourceLoader theResourceLoader  )
   {
    super(s);
    this.resourceLoader = theResourceLoader;
    this.addMouseListener(this);
    this.addMouseMotionListener(this);
   }


  /**
   * Creates a new JMenu with the supplied string as its text
   * and specified as a tear-off menu or not.
   *
   * @param s The text for the menu label
   * @param b can the menu be torn off (not yet implemented)
   */
   public JSenseMenu(String s, boolean b,  ResourceLoader theResourceLoader  )
   {
    super(s);
    this.resourceLoader = theResourceLoader;
    this.addMouseListener(this);
    this.addMouseMotionListener(this);                 
   }


   public JMenuItem add( JMenuItem item )
   {
     JMenuItem newItem = super.add(item);
     return newItem;
   }


 public void mouseEntered( MouseEvent e )
 {

  //if( true ) return;

  boolean mouseButtonDown = ( (e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0 );
  // Important: Don't trigger on mousedrag operations over this menu,
  //            because this captures the focus.

  if( ( !isSelected ) &&
      ( this.isVisible() ) &&
      ( this.getIsParentFrameOnTop() ) &&
      ( !mouseButtonDown ) )
   {
     // this line was inserted, because the menu always gave the focus
     // back to the component lastly having focus.
     // So if the menu gets focus only by roll over the problem is solved.
     // So this line is kind of work-around and can be "weggemacht" on demand.
     this.getParent().requestFocus();

     isSelected = true;
     saveForeGround = this.getForeground();
     saveBackGround = this.getBackground();
     int foreGroundGrayScale = saveForeGround.getRed() +
                               saveForeGround.getGreen() +
                               saveForeGround.getBlue() ;
     int backGroundGrayScale = saveBackGround.getRed() +
                               saveBackGround.getGreen() +
                               saveBackGround.getBlue() ;
     final int ColorDelta = 20;
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
        this.setBackground( new Color(newRed,newGreen,newBlue));
      } else
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
        this.setBackground( new Color(newRed,newGreen,newBlue));
      }
   } // if
 } // mouseEntered



 public void mouseExited( MouseEvent e )
 {

   //if( true ) return;

   if( ( this.isVisible() ) && ( this.getIsParentFrameOnTop() ) )
    {
     // reset original colors :
     if( saveBackGround != null) { this.setBackground(saveBackGround); }
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



} // JSenseMenu

 




