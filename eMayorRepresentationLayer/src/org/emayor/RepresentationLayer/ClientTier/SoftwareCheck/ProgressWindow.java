package org.emayor.RepresentationLayer.ClientTier.SoftwareCheck;


/**
 *  A window, which disables its parent while
 *  it is visible. Progressbar displayed.
 *
 *  5.1.06   jpl
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;



public class ProgressWindow extends JDialog 
{
  private JTextArea textArea  = null;
  private JLabel pictureLabel = null;
  private JProgressBar progressBar;
  private boolean wasCancelled = false;
  private AnimatedColorPanel animatedColorPanel = null;

  private Frame parentFrame = null;



 /**
  *  Constructor : With allowCancel parameter. If it's true,
  *  a cancel button is displayed, and one can cancel it, which can be
  *  seen by calling getWasCancelled().
  *  Either a busyMessage or a picture must be passed.
  */
  public ProgressWindow( final String busyMessage,
                         final ImageIcon picture,
                         final Frame theParentFrame,
                         final boolean allowCancel,
                         final String cancelButtonText )
  {
    super(theParentFrame,false);
    // Set the parentFrame as parent. This will keep the parentframe
    // in the background, while this window is visible.
    this.parentFrame = theParentFrame;

    if( this.parentFrame == null ) System.out.println("**** ProgressWindow.constructor: this.parentFrame is null ****");

    this.setUndecorated(true);
    this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

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

    JPanel feedbackPanel = new JPanel( new BorderLayout() );
    feedbackPanel.setOpaque(false);

    this.progressBar = new JProgressBar();
    feedbackPanel.add( this.progressBar, BorderLayout.CENTER );

    animatedColorPanel = new AnimatedColorPanel(147);
    animatedColorPanel.setBorder( BorderFactory.createEtchedBorder() );
    feedbackPanel.add( animatedColorPanel, BorderLayout.SOUTH );

    if( allowCancel ) // Optional cancel button and functionality :
     {                                                                              
       //final ImageIcon cancelIcon = theResourceLoader.loadImageIcon("pics/menupics/cancel.gif");
       //JSenseButton cancelButton = new JSenseButton(" Abbrechen ",cancelIcon,true,theResourceLoader);
       
       JButton cancelButton = new JButton(cancelButtonText);
       
       ob = BorderFactory.createEtchedBorder();        
       ib = BorderFactory.createEmptyBorder(gap,gap,gap,gap);
       cancelButton.setBorder( BorderFactory.createCompoundBorder(ob,ib) );
       cancelButton.setFont( UIManager.getFont("Tree.font") );
       cancelButton.addActionListener( new ActionListener()
        {                                                                                           
          public void actionPerformed( ActionEvent e )
          {
            wasCancelled = true;
            setVisible(false);
          }
        });
       JPanel cancelPanel = new JPanel( new FlowLayout(FlowLayout.CENTER,fontSize/4,0) );
       cancelPanel.setOpaque(false);
       cancelPanel.add(cancelButton);
       JPanel wrapFeedbackPanel = new JPanel( new BorderLayout(0,0) );
       wrapFeedbackPanel.setOpaque(false);
       wrapFeedbackPanel.add( feedbackPanel, BorderLayout.CENTER );
       wrapFeedbackPanel.add( cancelPanel, BorderLayout.EAST );
       mainWrapPanel.add( wrapFeedbackPanel, BorderLayout.SOUTH );
     }
    else
     {
       // No cancel button :
       mainWrapPanel.add( feedbackPanel, BorderLayout.SOUTH );
     }

    if( busyMessage != null )
     {
       JPanel textPanel = new JPanel( new BorderLayout(0,0) );
       textPanel.setOpaque(false);
       textPanel.setBorder( BorderFactory.createEmptyBorder(fontSize,2*fontSize,fontSize/8,2*fontSize) );
       mainWrapPanel.add( textPanel, BorderLayout.NORTH );
       this.textArea = new JTextArea( busyMessage );
       this.textArea.setEditable(false);
       this.textArea.setForeground( UIManager.getColor("Tree.foreground") );
       this.textArea.setBackground( UIManager.getColor("Tree.background") );
       this.textArea.setOpaque(false);
       textPanel.add( this.textArea, BorderLayout.CENTER );
     }
    else
     {
       this.textArea = new JTextArea( "" );
     }

    // Set a minimal width of the textarea:
    this.textArea.setColumns(52);
    
    this.pictureLabel = new JLabel( picture );
    this.pictureLabel.setOpaque(false);
    if( picture != null )
     {
       this.pictureLabel.setBorder( BorderFactory.createEmptyBorder(fontSize/2,fontSize/2,fontSize/2,fontSize/2));
       mainWrapPanel.add( pictureLabel, BorderLayout.CENTER );
     }

    this.getContentPane().add( framePanel, BorderLayout.CENTER );
    this.pack();

    // Attach a componentlistener for starting/ending the
    // AnimatedColorPanel's animation.
    this.addWindowListener( new WindowAdapter()
     {
        public void windowActivated(WindowEvent e)
        {
          animatedColorPanel.setForeground( getForeground() );
          animatedColorPanel.setBackground( getBackground() );
          animatedColorPanel.activateAnimation(true);
        }
        public void windowClosed(WindowEvent e)
        {
          animatedColorPanel.activateAnimation(false);
        }
     });
  } // Constructor






  public void setText( String newMultiLineText )
  {
    this.textArea.setText( newMultiLineText );
  }





  public void setPicture( ImageIcon newPicture )
  {
    this.pictureLabel.setIcon(newPicture);
  }



  public void setProgressMaxValue( int maxValue )
  {
    this.progressBar.setMaximum( maxValue );
  }



  public void setProgressValue( int value )
  {
    this.progressBar.setValue( value );
  }




  /**
   *  VectorTransferStateReceiver implementation.
   *  Tells, how many percent of first tree level nodes
   *  already have been transferred.
   */
   public void setVectorTransferState( int percentage )
   {
     this.setProgressValue( percentage );
   }





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
    this.setVisible(true);
  }




 /**
  *  Shows the window centered at the passed centerPoint.
  */
  public void showCenteredAt( final Point centerPoint )
  {
    int xp = centerPoint.x - this.getSize().width/2;
    int yp = centerPoint.y - this.getSize().height/2;
    this.setLocation(xp,yp);
    this.setVisible(true);
  }



 /**
  *  Shows the window at the passed topLeftPoint.
  */
  public void showAt( int xp, int yp )
  {
    this.setLocation( xp,yp );
    this.setVisible(true);
  }


  
 /**
  *  If the window was passed allowCancel=true on construction,
  *  a cancel button is displayed additionally, the user can cancel
  *  it, and you can check that by a call to this method.
  */
  public boolean getWasCancelled()
  {
    return this.wasCancelled;
  }

}
