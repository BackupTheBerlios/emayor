package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;

/**
 *   The input component for readonly entries which
 *   have the schema type DateType.
 * 
 *   jpl  
 */

import javax.swing.*;
import java.util.StringTokenizer;



public class ReadonlyDateTypeComponent extends JLabel
{


  public ReadonlyDateTypeComponent()
  {
  
  }


  
  
 /**
  *  Overriden method: Scan the date
  *  which should have the pattern YYYY-MM-DD
  *  and if that is correct, display it in the
  *  format dd / mm / yyyy
  */ 
  public void setText( final String newText )
  {
    try // assume correct structure: YYYY-MM-DD:
    {
      StringTokenizer tok = new StringTokenizer(newText,"-");
      if( tok.hasMoreTokens() )
      {
        String yearNumber = tok.nextToken();
        if( tok.hasMoreTokens() )
        {
          String monthNumber = tok.nextToken();
          if( tok.hasMoreTokens() )
          {
            String dayNumber = tok.nextToken();
            // and set it, but in the form DD / MM / YYYY:
            StringBuffer textBuffer = new StringBuffer();            
            textBuffer.append(dayNumber);
            textBuffer.append(" / ");        
            textBuffer.append(monthNumber);
            textBuffer.append(" / ");
            textBuffer.append(yearNumber);            
            super.setText( textBuffer.toString() );
          }
        }
      }
    }
    catch( Exception eee )
    {
      // Structure is not correct -> put all in the first field:
      System.out.println("***");
      System.out.println("*** EditableDateTypeInputcomponent:");
      System.out.println("***");
      System.out.println("*** Invalid initial date value:");
      System.out.println("*** date= " + newText);
      System.out.println("*** has not the form YYYY-MM-DD");
      System.out.println("***");
      super.setText( newText );
    }
  } // setText


  
  public void setTextFromNumbers( final int yearNumber, 
                                  final int monthNumber, 
                                  final int dayNumber )
  {
    try
    {
      // set it, but in the form DD / MM / YYYY:
      StringBuffer textBuffer = new StringBuffer();            
      textBuffer.append(dayNumber);
      textBuffer.append(" / ");        
      textBuffer.append(monthNumber);
      textBuffer.append(" / ");
      textBuffer.append(yearNumber);            
      super.setText( textBuffer.toString() );
    }
    catch( Exception eee )
    {
      eee.printStackTrace();
    }
  } // setTextFromNumbers

  
}

