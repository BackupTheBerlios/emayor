package org.emayor.RepresentationLayer.ClientTier.Logout;


import java.awt.EventQueue;

import javax.swing.*;

import org.emayor.RepresentationLayer.ClientTier.SoftwareCheck.SoftwareCheckApplet;


  /**
   *   The task of this logout applet is just
   *   to show an informational dialog and
   *   after that close the browser window
   *   by shutting down the JVM.
   * 
   *   For having the required access rights
   *   for shut down the JVM, it must be RSA signed.
   * 
   *   24.1.06  jpl
   */


public class LogoutApplet extends JApplet
{

  private String logoutMessage = "Logout: The browser frame will be closed now.";
  private String proceedButtonText = "Proceed";
  //private String cancelButtonText = "Cancel";
  

  public LogoutApplet()
  {
  }

  /**
   *  Called when this applet is loaded into the browser.
   */
   public void init()
   {
     // Read the applet parameters: They contain texts in the selected
     // natural language:
     String s = null;
     s = super.getParameter("LogoutMessage"); if( s != null ) this.logoutMessage = s;
     s = super.getParameter("ProceedText");   if( s != null ) this.proceedButtonText = s;
     //s = super.getParameter("CancelText");    if( s != null ) this.cancelButtonText = s;
   }
   
   
   /**
    *   Called to start the applet's execution.
    */
    public void start()
    {
     EventQueue.invokeLater( new Runnable()
     {
       public void run()
       {
         String message = logoutMessage;
         String title = "eMayor";        
         String yesOption = "  " + proceedButtonText + "  ";
         //String noOption  = "  " + cancelButtonText + "  ";
         Object[] options = { yesOption /*,noOption*/ };
         int reply = JOptionPane.showOptionDialog( LogoutApplet.this,
                                                   message,title,
                                                   JOptionPane.DEFAULT_OPTION,
                                                   JOptionPane.WARNING_MESSAGE,
                                                   null, options, options[0] );
         // only reply == 0 possible.         
         System.out.println("------------- SHUTTING DOWN THE JVM ------------------");
         System.exit(0);
       }
     });        
    } // start  
    

    
    
} // LogoutApplet

