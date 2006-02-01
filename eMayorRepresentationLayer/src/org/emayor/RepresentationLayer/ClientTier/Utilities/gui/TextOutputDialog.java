package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;

/**
 *  A modal frame with a text area.
 *
 *  10.1.06   jpl
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class TextOutputDialog extends JDialog
{


   private Frame parentFrame = null;

   
   public TextOutputDialog( final Frame  _parentFrame,
                            final String frameTitle,
                            final String frameText,
                            final String closeButtonText )
   {
     super( _parentFrame,false);   
     this.parentFrame = _parentFrame;

     this.setTitle(frameTitle);
     
     this.getContentPane().setLayout( new BorderLayout() );
     final int fontSize = UIManager.getFont("Label.font").getSize();
     int gap = 1 + fontSize/8;                     

     JPanel framePanel = new JPanel( new BorderLayout(1,1) );
     Border ob = BorderFactory.createRaisedBevelBorder();
     Border ib = BorderFactory.createEmptyBorder(gap,gap,gap,gap);
     framePanel.setBorder( BorderFactory.createCompoundBorder(ob,ib) );

     SmoothBackgroundPanel mainWrapPanel =
        new SmoothBackgroundPanel( new BorderLayout(1,1),
                                   SmoothBackgroundPanel.PanelBackGroundColorIdentifier,
                                   SmoothBackgroundPanel.SmoothCenterMode );
     framePanel.add( mainWrapPanel, BorderLayout.CENTER );
 
     
     JTextArea textArea = new JTextArea(frameText,24,56);
     mainWrapPanel.add( new JScrollPane(textArea), BorderLayout.CENTER );
     
     JPanel buttonPanel = new JPanel( new FlowLayout(FlowLayout.LEFT));
     JSenseButton closeButton = new JSenseButton(closeButtonText,true);
     closeButton.addActionListener( new ActionListener()
     {
       public void actionPerformed( ActionEvent e )
       {
         setVisible(false);
       }
     });
     buttonPanel.add(closeButton);
     mainWrapPanel.add( buttonPanel, BorderLayout.SOUTH );
     
     this.getContentPane().add( framePanel, BorderLayout.CENTER );
     this.pack();
   } // Constructor

   

   
     
   
   
 /**
  *  Shows the window centered on the parentframe.
  */
  public void showCentered()
  {
    final Dimension size = this.getSize();
    Point parentOrigin = new Point(0,0);
    
    if( parentOrigin == null ) System.out.println("**** parentOrigin is null ****");
    if( this.parentFrame == null ) System.out.println("**** this.parentFrame is null ****");

    GraphicsEnvironment localEnvirnonment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Rectangle usableRect = localEnvirnonment.getMaximumWindowBounds();        

    int hOffset = (usableRect.width)/2;
    int vOffset = (usableRect.height)/2;

    int xp = hOffset - size.width/2;
    int yp = vOffset - size.height/2;
    this.setLocation(xp,yp);
    super.setVisible(true);
  }



}
