package org.emayor.RepresentationLayer.ClientTier.ProcessStep;


  /**
   *   This class represents the graphical view of
   *   the ProcessStepApplet.
   * 
   *   25.01.06   jpl
   */

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;


public class ProcessStep_View extends JPanel
{

  private Color pastStepColor    = new Color(210,210,210);
  private Color currentStepColor = new Color(255,210,210);
  private Color futureStepColor  = new Color(210,210,255);

  private Color pastStepBackgroundColor = new Color(92,92,92);
  private Color currentStepBackgroundColor = new Color(148,64,64);
  private Color futureStepBackgroundColor = new Color(64,64,148);

  
 /**
  *  Constructor
  *  @param processStepTextList  contains the comma separated texts
  *  @param processStepCurrentIndex  is the index of the current step
  */
  public ProcessStep_View( final String processStepTextList,
                           final String processStepDescriptionList,
                           final int processStepCurrentIndex,
                           final Color panelBackgroundColor )
  {
    BoxLayout layout = new BoxLayout(this,BoxLayout.X_AXIS);
    this.setLayout( layout );
    this.setOpaque(false);  // transparent
    int gap = UIManager.getFont("Label.font").getSize();
    this.displayContents( processStepTextList,
                          processStepDescriptionList,
                          processStepCurrentIndex,
                          panelBackgroundColor );
  }
  
  
  
  private void displayContents( final String processStepTextList,
                                final String processStepDescriptionList,
                                final int processStepCurrentIndex,
                                final Color panelBackgroundColor )
  {
    String[] stepTexts = this.parseTextList( processStepTextList,";" );
    String[] stepDescriptions = this.parseTextList( processStepDescriptionList,";" );
    
    System.out.println("displayContents: Number of steps= " + stepTexts.length);
    
    int gap = UIManager.getFont("Label.font").getSize();
    for( int i=0; i < stepTexts.length; i++ )
    {
      Color textColor = null;
      Color stepBackgroundColor = null;    
      if( i < processStepCurrentIndex )
      {
        textColor = this.pastStepColor;
        stepBackgroundColor = this.pastStepBackgroundColor;
      }
      else
      if( i > processStepCurrentIndex )
      {
        textColor = this.futureStepColor;
        stepBackgroundColor = this.futureStepBackgroundColor;
      }
      else
      {
        textColor = this.currentStepColor;
        stepBackgroundColor = this.currentStepBackgroundColor;
      }      
      ArrowPanel arrowPanel = new ArrowPanel( stepTexts[i],textColor,      
                                              stepBackgroundColor,
                                              panelBackgroundColor );      
      if( stepDescriptions.length > i )
      {
        arrowPanel.setToolTipText(stepDescriptions[i]);
      }      
      
      this.add(arrowPanel);
      System.out.println("ProcessStepView: Added label with text: " + stepTexts[i] );
    } // for i
    
    this.add( Box.createHorizontalStrut(1024) );
  } // displayContents
  
  
  
  
 /**
  *  Tokenizes the passed array:
  */ 
  private String[] parseTextList( final String list, String separator )
  {
    StringTokenizer tok = new StringTokenizer(list,separator);
    Vector tokens = new Vector();
    while( tok.hasMoreTokens() )
    {
      String token = tok.nextToken();
      tokens.addElement(token);
    }
    String[] elementArray = new String[tokens.size()];
    tokens.copyInto(elementArray);
    return elementArray;
  }

  
  
} // ProcessStepView


