package org.emayor.RepresentationLayer.ClientTier;


  /**
   *   Just a repository for adding errors,
   *   which then are displayable in the applet.
   * 
   *   For severe errors, it additionally can display
   *   a dialog.
   * 
   *   30.11.2005  jpl
   */

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JTextArea;
import javax.swing.JApplet;
import javax.swing.JOptionPane;

public class ErrorManager
{

  private JTextArea errorsTextArea = new JTextArea();
  
  private int numberOfErrors = 0;

  private JApplet parentApplet;
  
  public ErrorManager( final JApplet theParentApplet )
  {
    this.parentApplet = theParentApplet;
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
  *  Add a single error description on a single line.
  *  For severe errors, set the displayDialog flag, which
  *  causes the manager to display a dialog with the passed
  *  error message additionally.
  */ 
  public void addErrorMessage( String errorMessage, boolean displayDialog )
  {
    // Security: Intercept null strings:
    if( errorMessage == null ) errorMessage = "[null]";
  
    // On the first incoming error message, clear the
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
    // Additionally, if desired by the caller, do display the
    // message in a dialog:
    if( displayDialog )
    {
      // Decouple from current thread in any case:
      final String diaMessage = errorMessage;
      EventQueue.invokeLater( new Runnable()
      {
        public void run()
        {
          String title = "Warning";
          JOptionPane.showMessageDialog( parentApplet, diaMessage, title, JOptionPane.WARNING_MESSAGE );        
        }
      });
    }
  }

  
  public int getNumberOfErrors()
  {
    return this.numberOfErrors;
  }
  
}
