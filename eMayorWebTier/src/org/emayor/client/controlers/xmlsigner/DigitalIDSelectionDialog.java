package org.emayor.client.controlers.xmlsigner;

/**
 *   Lets the user select an digital identity.
 *   It is called by XMLSigner_eMayorVersion, if
 *   more than one digital identities are found
 *   on the smartcard.
 * 
 *   Created  J.Plaz , 11.October 2005
 */

import java.awt.*;      
import java.awt.event.*;  
import javax.swing.*;
import javax.swing.border.*;                                  
                                               
import org.emayor.client.LanguageProperties;
import org.emayor.client.Utilities.gui.GradientPanel;
import org.emayor.client.Utilities.gui.JSenseButton;
import org.emayor.client.Utilities.gui.CustomEtchedBorder;
import org.emayor.client.LanguageProperties;



public class DigitalIDSelectionDialog extends JDialog
{


  private boolean wasOkButtonPressed = true;
  private int selectedIndex = 0;
  private String[] namesForSelection;
  private LanguageProperties languageProperties;

  public DigitalIDSelectionDialog( final Frame ownerFrame,
                                   final LanguageProperties _languageProperties,
                                   final ImageIcon selectionIcon,
                                   final ImageIcon cancelIcon,
                                   final String[] _namesForSelection )
  {
    super(ownerFrame," ",true); // must be modal
    this.namesForSelection = _namesForSelection;
    this.languageProperties = _languageProperties;
    String selectionTitle = languageProperties.getTextFromLanguageResource("DigitalIdentityDialog.SelectionTitle");
    // Security: Don't break the dialog by its title, when
    // people have removed the above key in the text properties file:
    if( selectionTitle == null ) selectionTitle="Selection [applet property is missing]";
    this.setTitle(selectionTitle);
    

    JPanel informationPanel = new JPanel( new BorderLayout(1,1) );
    BoxLayout informationPanelLayout = new BoxLayout(informationPanel,BoxLayout.Y_AXIS);
    informationPanel.setLayout(informationPanelLayout);
    // am empty border:
    int fontSize = UIManager.getFont("Label.font").getSize();
    informationPanel.setBorder( BorderFactory.createEmptyBorder(fontSize/2,fontSize,fontSize/2,fontSize) );
    // Get the information text in the active language: 
    String informationLine1 = this.languageProperties.getTextFromLanguageResource("DigitalIdentityDialog.SelectionInformation.Line1");
    String informationLine2 = this.languageProperties.getTextFromLanguageResource("DigitalIdentityDialog.SelectionInformation.Line2");
    String informationLine3 = this.languageProperties.getTextFromLanguageResource("DigitalIdentityDialog.SelectionInformation.Line3");
    
    JLabel infoLabel1 = new JLabel(informationLine1);
    JLabel infoLabel2 = new JLabel(informationLine2);
    JLabel infoLabel3 = new JLabel(informationLine3);

    informationPanel.add( infoLabel1 );
    informationPanel.add( infoLabel2 );
    informationPanel.add( infoLabel3 );

    JPanel buttonPanel = new GradientPanel( new FlowLayout(),
                                            GradientPanel.ApplyVerticalHighLight,
                                            GradientPanel.MediumGradientStrength,
                                            GradientPanel.ActiveTitleBackground );
    BoxLayout buttonPanelLayout = new BoxLayout(buttonPanel,BoxLayout.Y_AXIS);
    buttonPanel.setLayout( buttonPanelLayout );
        
    // Precalculate the width for the largest text and make all buttons
    // the same width:
    int buttonWidth = 0;
    int tmpWidth = 0;
    for( int i=0; i < this.namesForSelection.length; i++ )
    {
      tmpWidth = (7*this.namesForSelection[i].length() * fontSize)/8;
      if( tmpWidth > buttonWidth ) buttonWidth = tmpWidth;
    }
    int buttonHeight = (7*fontSize)/3;
    
    // Create the selection buttons in separate panels
    // and connect the button listener with the appropriate action
    for( int i=0; i < this.namesForSelection.length; i++ )
    {
      final int finalLoopIndex = i;
      JSenseButton button = new JSenseButton( this.namesForSelection[i],selectionIcon,true );
      button.setSize(buttonWidth,buttonHeight);
      button.setPreferredSize( new Dimension(buttonWidth,buttonHeight) );
      button.setHorizontalAlignment(SwingConstants.LEFT);
      button.addActionListener( new ActionListener()
      {
        public void actionPerformed( ActionEvent e )
        {
          selectedIndex = finalLoopIndex; 
          setVisible(false); // modal lock ends after that one too
        }
      });
      JPanel entryPanel = new JPanel( new FlowLayout() );
      entryPanel.add(button);
      buttonPanel.add(entryPanel);
    } // for
    
    // Need a cancel button:
    JPanel cancelPanel = new JPanel( new FlowLayout() );
    // Just a top border:
    CustomEtchedBorder eb2 = new CustomEtchedBorder(true,false,false,false);
    cancelPanel.setBorder(eb2);
    String cancelText = this.languageProperties.getTextFromLanguageResource("Form.Deny");
    JSenseButton cancelButton = new JSenseButton( cancelText,cancelIcon,true );
    cancelButton.addActionListener( new ActionListener()
    {
      public void actionPerformed( ActionEvent e )
      {
        wasOkButtonPressed = false;
        setVisible(false); // modal lock ends after that one too
      }
    });
    cancelPanel.add(cancelButton);
        
    // Add the stuff:
    
    this.getContentPane().setLayout( new BorderLayout(1,1) );
  
    // Use a wrap panel because of the etched border:
    JPanel buttonWrapPanel= new JPanel( new BorderLayout(1,1) );
    buttonWrapPanel.setBorder( BorderFactory.createEmptyBorder(fontSize/2,fontSize,fontSize/2,fontSize) );
    buttonWrapPanel.add( buttonPanel,BorderLayout.CENTER );
    
    // Use a wrap panel because of the etched border:
    JPanel informationWrapPanel= new JPanel( new BorderLayout(1,1) );
    informationWrapPanel.setBorder( BorderFactory.createEmptyBorder(fontSize/2,fontSize,fontSize/2,fontSize) );
    informationWrapPanel.add( informationPanel,BorderLayout.CENTER );

    this.getContentPane().add( informationWrapPanel, BorderLayout.NORTH );
    this.getContentPane().add( buttonWrapPanel,      BorderLayout.CENTER );
    this.getContentPane().add( cancelPanel,          BorderLayout.SOUTH );
    
    this.pack();

    // and center it:
    final Dimension size = this.getSize();
    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int xp = (screen.width-size.width)/2;
    int yp = (screen.height-size.height)/2;
    this.setLocation(xp,yp);
  
  } // constructor

  
  

  
  
  public boolean getWasOkButtonPressed()
  {
    return this.wasOkButtonPressed;  
  }
  

  
  
  public int getSelectedIndex()
  {
    return this.selectedIndex;  
  }  

  
  
  
  
} // DigitalIDSelectionDialog



