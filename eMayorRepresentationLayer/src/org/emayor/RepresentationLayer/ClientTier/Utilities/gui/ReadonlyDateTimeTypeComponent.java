package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;

/*
 * Custom UI object for DateTimeType entries
 * for labels or readonly JTextFields.
 *
 * To be used after the trials in version 1.0.1+ 
 * 
 * Created on 24.12.2005, jpl
 */

import java.awt.*;
import javax.swing.*;

import java.util.StringTokenizer;

public class ReadonlyDateTimeTypeComponent extends JPanel
{

  private ReadonlyDateTypeComponent dateComponent = new ReadonlyDateTypeComponent();
  private JLabel timeLabel = new JLabel(" ");

  public ReadonlyDateTimeTypeComponent()
  {
    int gap = UIManager.getFont("TextField.font").getSize();
    this.setLayout( new BorderLayout(2*gap,gap/2) );
    this.add(dateComponent, BorderLayout.WEST);
    this.add(timeLabel, BorderLayout.CENTER);        
    this.setOpaque(false);  // transparent
    this.setBorder( BorderFactory.createEmptyBorder(0,0,0,0) ); // no border
  }
  
  
  
 /**
  *  The only overriden method.
  */ 
  public void setText( final String newDateTimeValue )
  {
    try // assume correct structure: CCYY-MM-DDThh:mm:ssZ
    {
      StringTokenizer tok = new StringTokenizer(newDateTimeValue,"T");
      if( tok.hasMoreTokens() )
      {
        String dateString = tok.nextToken();
        String timeString = tok.nextToken();
        this.dateComponent.setText(dateString);
        this.timeLabel.setText(timeString);
       }
    }
    catch( Exception eee )
    {
      // Structure is not correct -> put all in a JLabel:
      System.out.println("***");
      System.out.println("*** ReadonlyDateTimeTypeComponent:");
      System.out.println("***");
      System.out.println("*** Invalid initial datetime value:");
      System.out.println("*** dateTime value= " + newDateTimeValue);
      System.out.println("*** has not the form CCYY-MM-DDThh:mm:ssZ");
      System.out.println("***");
      this.timeLabel.setText(newDateTimeValue);
    }
  } // setText



} // ReadonlyDateTimeTypeComponent
