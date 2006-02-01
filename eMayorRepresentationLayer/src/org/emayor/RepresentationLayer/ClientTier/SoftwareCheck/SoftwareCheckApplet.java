package org.emayor.RepresentationLayer.ClientTier.SoftwareCheck;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.swing.*;


/**
 * 
 *  Checks the current installation on the client
 *  and updates the installation on demand
 *  or completely installs all required files
 *  on demand.
 * 
 *  Dec. 28, 2005  jpl  for the eMayor client tier finetuning version
 *
 */

public class SoftwareCheckApplet extends JApplet
{
   // The default messages, which should be overridden
   // by values passed through the applet parameter
   // in the current nautral language:
   private String information_IsChecking = "Checking the eMayor client software...";
   private String information_IsUpToDate = "The eMayor client software is up to date.";
   private String information_MustBeInstalled = "The eMayor client software must be installed.";
   private String information_MustBeUpdated   = "The eMayor client software must be updated.";
   private String information_HasBeenInstalled = "The client software has been installed.";
   private String information_HasBeenUpdated   = "The client software has been updated.";
   private String information_RestartBrowser = "eMayor must be restarted.\nYour browser window will be closed now.";
   private String information_Proceed = "Proceed";
   private String information_Cancel = "Cancel";
   // URL's for redirection:
   private String nextPageURL = "";
   private String loginPageURL = "";
   // relative URL for getting the server's MD5 checksums of the client software files:
   private String md5_RequestURL = "";

   // relative URL for getting code fil
   private String clientFileRequestURL = "";
   
   private JLabel informationLabel  = new JLabel();
   
   private ImageIcon okIcon = null;
   private ImageIcon invalidIcon = null;
   private ImageIcon workingIcon = null;
   
   private JTextArea remarksArea = new JTextArea(3,56);
   
   private SoftwareChecker softwareChecker = null;
   
   
   
   public SoftwareCheckApplet()
   {
     this.getContentPane().setLayout( new BorderLayout() );
     JPanel mainPanel = new JPanel();
     BoxLayout layout = new BoxLayout(mainPanel,BoxLayout.Y_AXIS);
     mainPanel.setLayout( layout );
     int gap = UIManager.getFont("Label.font").getSize();
     mainPanel.setBorder( BorderFactory.createEmptyBorder(gap,2*gap,gap,2*gap));
     
     remarksArea.setEditable(false);
     remarksArea.setBorder( BorderFactory.createEmptyBorder(gap,2*gap,gap,2*gap) );
     remarksArea.setBackground( UIManager.getColor("Label.background") );
          
     mainPanel.add( this.informationLabel  );
     mainPanel.add( this.remarksArea );
     this.getContentPane().add(mainPanel,BorderLayout.CENTER);
     
   } // constructor


   
  
  /**
   *  Called when this applet is loaded into the browser.
   */
   public void init()
   {
     // Set values in the natural language, if available:
     String s = null;
     s = super.getParameter("ClientSoftwareChecking");        if( s != null ) this.information_IsChecking = s;
     s = super.getParameter("ClientSoftwareIsUpToDate");      if( s != null ) this.information_IsUpToDate = s;
     s = super.getParameter("ClientSoftwareMustBeInstalled"); if( s != null ) this.information_MustBeInstalled = s;
     s = super.getParameter("ClientSoftwareMustBeUpdated");   if( s != null ) this.information_MustBeUpdated = s;
     s = super.getParameter("ClientBrowserRestart");          if( s != null ) this.information_RestartBrowser = s;
     
     s = super.getParameter("ClientSoftwareHasBeenInstalled"); if( s != null ) this.information_HasBeenInstalled = s;
     s = super.getParameter("ClientSoftwareHasBeenUpdated");   if( s != null ) this.information_HasBeenUpdated = s;
     // button text's:
     s = super.getParameter("Proceed");                       if( s != null ) this.information_Proceed = s;
     s = super.getParameter("Cancel");                        if( s != null ) this.information_Cancel = s;
     // and the URL's:
     s = super.getParameter("NextPageURL");                   if( s != null ) this.nextPageURL = s;
     s = super.getParameter("LoginPageURL");                  if( s != null ) this.loginPageURL = s;     
     s = super.getParameter("MD5_RequestURL");                if( s != null ) this.md5_RequestURL = s;     
     s = super.getParameter("ClientFileRequestURL");          if( s != null ) this.clientFileRequestURL = s;
 
     // and the icon's:
     this.okIcon      = this.loadImageIcon("/pictures/applet/ok.gif");
     this.invalidIcon = this.loadImageIcon("/pictures/applet/invalid.gif");
     this.workingIcon = this.loadImageIcon("/pictures/applet/working.gif");
   } // init


   
   
   public ImageIcon getWorkingIcon()
   {
     return this.workingIcon;
   }
   
   
   public String getMD5RequestURL()
   {
     return this.md5_RequestURL;
   }
   

   
   public String getClientFileRequestURL()
   {
     return this.clientFileRequestURL;
   }
   
   
   
  /**
   *  Called by the SoftwareChecker.
   *  Swing safe, when called from a user thread.
   */ 
   public void redirectBrowserToNextPage()
   {   
     this.redirectBrowserToPage( this.nextPageURL );
   }

   
   
   
  /**
   *  Called by the SoftwareChecker.
   *  Swing safe, when called from a user thread.
   */ 
   public void redirectBrowserToLoginPage()
   {
     this.redirectBrowserToPage( this.loginPageURL );
   }

   
   
   
   
  /**
   *  Swing safe, when called from a user thread.
   */ 
   public void redirectBrowserToPage( final String urlString )
   {
     System.out.println("Redirecting the browser to: " + urlString );
     EventQueue.invokeLater( new Runnable()
     {
       public void run()
       {
         try
         {
           URL redirectionURL = new URL( getDocumentBase(), urlString );
           getAppletContext().showDocument(redirectionURL);
         }
         catch( Exception e )
         {
           System.out.println("*** Applet: Browser page redirection to " +
                              urlString + " has failed. Exception is: ");
           e.printStackTrace();
         }  
       }
     });
   }
   
   

   
  /**
   *  Called by the SoftwareChecker. Swing safe, when called from a user thread.
   */ 
   public void setIsUpToDate( )
   {
     EventQueue.invokeLater( new Runnable()
     {
       public void run()
       {
         informationLabel.setText(information_IsUpToDate);
         informationLabel.setIcon( okIcon );
       }
     });   
   }
  
   

   
  /**
   *  Called by the SoftwareChecker. Swing safe, when called from a user thread.
   */ 
   public void setMustBeInstalled( )
   {
      EventQueue.invokeLater( new Runnable()
      {
        public void run()
        {
          informationLabel.setText(information_MustBeInstalled);
          informationLabel.setIcon( workingIcon );
        }
      });   
   }
  
   
   
   
   /**
    *  Called by the SoftwareChecker. Swing safe, when called from a user thread.
    */ 
    public void setMustBeUpdated( )
    {
       EventQueue.invokeLater( new Runnable()
       {
         public void run()
         {
           informationLabel.setText(information_MustBeUpdated);
           informationLabel.setIcon( workingIcon );
         }
       });   
    }

    
    
  /**
   *  Called by the SoftwareChecker. Swing safe, when called from a user thread.
   */ 
   public void setIsInstalledSuccessfully( )
   {
     EventQueue.invokeLater( new Runnable()
     {
       public void run()
       {
         informationLabel.setText(information_HasBeenInstalled);
         informationLabel.setIcon( okIcon );
         remarksArea.setText(information_RestartBrowser);
       }
     });   
     EventQueue.invokeLater( new Runnable()
     {
       public void run()
       {
         String message = information_RestartBrowser;
         String title = "eMayor Client Software";
         JOptionPane.showMessageDialog(SoftwareCheckApplet.this,message,title,JOptionPane.INFORMATION_MESSAGE);        
         System.out.println("------------- SHUTTING DOWN THE JVM ------------------");
         System.exit(0);
       }
     });        
   } // setIsInstalledSuccessfully
   

   
   
  /**
   *  Called by the SoftwareChecker. Swing safe, when called from a user thread.
   */ 
   public void setIsUpdatedSuccessfully( )
   {
     EventQueue.invokeLater( new Runnable()
     {
       public void run()
       {
         informationLabel.setText(information_HasBeenUpdated);
         informationLabel.setIcon( okIcon );
         remarksArea.setText(information_RestartBrowser);
       }
     });
     EventQueue.invokeLater( new Runnable()
     {
       public void run()
       {
         String message = information_RestartBrowser;
         String title = "eMayor Client Software";
         JOptionPane.showMessageDialog(SoftwareCheckApplet.this,message,title,JOptionPane.INFORMATION_MESSAGE);        
         System.out.println("------------- SHUTTING DOWN THE JVM ------------------");
         System.exit(0);
       }
     });        
   } // setIsUpdatedSuccessfully
   
   
   
   public String getInstallationMessage()
   {
     return this.information_MustBeInstalled + "\n" + 
            this.information_Proceed + " ?";
   }
   

   public String getUpdateMessage()
   {
     return this.information_MustBeInstalled + "\n" + 
            this.information_Proceed + " ?";
   }
   
   public String getCancelMessage()
   {
     return this.information_Cancel; // in the selected natural language
   }
      
   
  /**
   *   Called to start the applet's execution.
   */
   public void start()
   {
     if( this.softwareChecker == null )
     {
       // Set UI:
       this.informationLabel.setText( this.information_IsChecking );
       this.informationLabel.setIcon( this.workingIcon );
       // Start checker thread:
       this.softwareChecker = new SoftwareChecker(this);
       this.softwareChecker.start();
     }
   }   


   
   
  /**
   *  Called to stop (temporarily or permanently) the applet's execution.
   */
   public void stop() 
   {
     if( this.softwareChecker != null )
     {
       this.softwareChecker.terminateThread();
       this.softwareChecker = null;
     }
   } // stop

   
   
   
  /**
   *  Called when the applet is no longer used from the browser.
   *  One can free resources here.
   */
   public void destroy() 
   {
    if( this.softwareChecker != null )
    {
      this.softwareChecker.terminateThread();
      this.softwareChecker = null;
    }
   }

   
   
   
   /**                       
    *  ResourceLoader Interface method.
    *  Try to load it from the file system, from resource or from a host.
    */
    public ImageIcon loadImageIcon( String pathName )
    {  
      System.out.println("loadImageIcon() called for pathname= " + pathName );
      ImageIcon icon = null;
        try // in each case INTERCEPT nullpointer exceptions - just return null
        {
           URL url = this.getClass().getResource(pathName);
           if( url != null )
           {
              System.out.println("URL= " + url.toString());
              icon = new ImageIcon( Toolkit.getDefaultToolkit().getImage(url) );
           }
           System.out.println("icon 1 =" + icon );
           if( icon == null ) // next attempt
           {
              icon = this.loadImageFromHost(pathName);
              System.out.println("icon 2 =" + icon );
              if( icon == null ) // last attempt
              {
                icon = new ImageIcon(pathName);
                System.out.println("icon 3 =" + icon );
              }
           }
        }
        catch( Exception ee )
        {
          System.err.println( "Unable to download " + pathName );
          ee.printStackTrace();
          System.err.println("*** Applet.loadImageIcon() connection is down for file request.");
        }  
      return icon; 
    }



   /**                       
    * Load the image for the specified frame of animation. Since
    * this runs as an applet, we use getResourceAsStream for 
    * efficiency and so it'll work in older versions of Java Plug-in.
    */
    private ImageIcon loadImageFromHost( String pathName ) throws Exception 
    {                                                
      int MAX_IMAGE_SIZE = 128000;  //Change this to the size of your biggest image, in bytes.
      BufferedInputStream imageStream = null;
      try
      {
          System.out.println("loadImageFromHost name= " + pathName + " ....." );
          URL imageURL = new URL( super.getDocumentBase() ,pathName );
          InputStream rawInputStream = imageURL.openStream();
          imageStream = new BufferedInputStream( rawInputStream );
          System.out.println("completed." );
      }
      catch( Exception ee )
      {
        ee.printStackTrace();
      }      
      //BufferedInputStream imgStream = new BufferedInputStream( super.getClass().getResourceAsStream(pathName));
      if (imageStream != null)
      {     
          byte buf[] = new byte[MAX_IMAGE_SIZE];
          int count = 0;
          try 
          {
            count = imageStream.read(buf);
            imageStream.close();
          } 
          catch (java.io.IOException ioe) 
          {
            System.err.println( "Couldn't read stream from file: " + pathName );
            return null;
          }
          if (count <= 0) 
          {
            System.err.println("Empty file: " + pathName);
            return null;
          }
          return new ImageIcon(Toolkit.getDefaultToolkit().createImage(buf));
      } 
      else 
      {
        String errorMessage = "Couldn't find file: " + pathName;
        System.err.println( errorMessage );
        return null;
      }
    }
    
    
} // ClientAppletInstaller


