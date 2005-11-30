package org.emayor.client;


  /**
   *   Just a repository for adding errors,
   *   which then are displayable in the applet.
   * 
   *   30.11.2005  jpl
   */

import java.awt.Color;
import javax.swing.JTextArea;


public class ErrorManager
{

  private JTextArea errorsTextArea = new JTextArea();
  
  private int numberOfErrors = 0;

  
  public ErrorManager()
  {
    this.errorsTextArea.append("The Applet is running correctly.");
    this.errorsTextArea.setEditable(false);
    this.errorsTextArea.setRows(3);
  }

  
 /**
  * @return the error display, which is a JTextArea.
  */ 
  public JTextArea getErrorTextArea()
  {
    return this.errorsTextArea;
  }
  
  
 /**
  *  Add a single error description on a single line
  */ 
  public void addErrorMessage( String errorMessage )
  {
    // on the first incoming error message, clear the
    // initial content ( the ok message ) and set
    // the background to a bright red color:
    if( this.numberOfErrors == 0 )
    {
      this.errorsTextArea.setBackground( new Color(255,200,200) );
      this.errorsTextArea.setText("");
    }
    this.errorsTextArea.append( errorMessage );
    this.errorsTextArea.append( "\n" );
    this.numberOfErrors++;
    System.err.println(errorMessage);
  }

  
  public int getNumberOfErrors()
  {
    return this.numberOfErrors;
  }
  
}
