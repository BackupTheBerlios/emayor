import java.applet.Applet;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.StringTokenizer;


public class LoginButtonApplet extends Applet implements ActionListener
{

   private int appletWidth  = 500;
   private int appletHeight =  90;

   private Label titleLabel = new Label();
   private TextArea textArea = new TextArea("",3,30,TextArea.SCROLLBARS_NONE);    
   private Button loginButton = new Button("      Login     ");
   
   private Color greenColor  = new Color(160,255,140);              
   private Color redColor    = new Color(255,160,140);
   private Color yellowColor = new Color(250,250,140);
   private Color whiteColor  = new Color(255,255,255);
   private Color blackColor  = new Color(  0,  0,  0);

   private String loginButtonTarget = "";
   
   
   
   public LoginButtonApplet()
   {
     this.setSize( appletWidth, appletHeight );
     // The parent panel is initialized with FlowLayout, we change it :
     this.setLayout( new BorderLayout() );

     this.textArea.setEditable(false);
     this.textArea.setColumns(20);
     this.textArea.setRows(4);
     this.textArea.setSize(320,90);
     Panel buttonPanel = new Panel( new FlowLayout() );
     buttonPanel.add( this.loginButton);
     
     this.add( buttonPanel, BorderLayout.WEST );
     this.add( this.textArea, BorderLayout.CENTER );
      
     this.loginButton.addActionListener(this);
     
     this.textArea.setBackground( this.whiteColor );
     this.textArea.setForeground( this.blackColor );

     this.setBackground( this.whiteColor );
     this.setForeground( this.blackColor );   
   }



   
   public void actionPerformed( ActionEvent e )
   {
     System.out.println("Redirecting the browser to: " + this.loginButtonTarget );
     try
     {
       URL redirectionURL = new URL( super.getDocumentBase(), this.loginButtonTarget );
       this.getAppletContext().showDocument(redirectionURL);
     }
     catch( Exception exc )
     {
       System.out.println("*** Applet: Browser page redirection to " +
                           this.loginButtonTarget +
                           " has failed. Exception is: ");
       exc.printStackTrace();
     }
   }

   
   
   /**
    * Called by the browser or applet viewer to inform
    * this applet that it has been loaded into the system. It is always
    * called before the first time that the <code>start</code> method is
    * called.
    */
    public void init()
    {
      // try to read and set the background color:
      try
      {
        String bgParameter = getParameter("BackgroundColor");
        StringTokenizer tok = new StringTokenizer(bgParameter,",");
        int numberOfTokens = tok.countTokens();
        if( numberOfTokens == 3 )
        {
            int r = Integer.valueOf( tok.nextToken() ).intValue();
            int g = Integer.valueOf( tok.nextToken() ).intValue();
            int b = Integer.valueOf( tok.nextToken() ).intValue();
            Color bgColor = new Color(r,g,b);
            this.setBackground( bgColor );            
        }
      }
      catch( Exception eee)
      {
        System.out.println("*** Unable to set the background color.");
      }
      
      String jvmVersion = System.getProperty("java.version");
      String message = "";
      if( jvmVersion.compareTo("1.5.0_05") >= 0 )
      {
        this.loginButtonTarget = getParameter("buttonTarget");
        message = getParameter("JavaPluginIsUpToDateMessage");    // is language specific
        if( message == null ) message = "Java Plugin is up to date."; // fallback
       }
      else
      {
        // Change button target:
        this.loginButtonTarget = "http://www.java.com"; // redirect to java software download
        this.loginButton.setLabel("   Java Plugin   ");
        message = getParameter("OutDatedPluginMessage");    // is language specific
        if( message == null ) message = "Java Plugin is outdated"; // fallback
      }  
      message = this.translateUnicodeShortcutsInLine(message);
      // manually insert newlines:
      StringBuffer buffer = new StringBuffer(message);
      int index = 0;
      int column = 0;
      while( index < buffer.length() )
      {
          if( column > 50 )
          {
            if( buffer.charAt(index) == ' ' )
            {
              buffer.insert(index,"\n");
              column = 0;
            }
            else
            {
             column++;
            }
          }
          else
          {
            column++;
          }  
          index++;
      } // while 
      this.textArea.append(buffer.toString());      
    } // init

         
         

   /**
    * Called by the browser or applet viewer to inform
    * this applet that it should start its execution. It is called after
    * the <code>init</code> method and each time the applet is revisited
    * in a Web page.
    */
    public synchronized void start()
    {      
    } // start

     
   /**
    * Called by the browser or applet viewer to inform
    * this applet that it should stop its execution. It is called when
    * the Web page that contains this applet has been replaced by
    * another page, and also just before the applet is to be destroyed.
    */
    public synchronized void stop()
    {
    } // stop
   

    
    
    private String translateUnicodeShortcutsInLine( String line )
    {
      if( (line != null) &&  (line.indexOf("&") >= 0) )
      {
      line = line.replaceAll("&aacute;","" + '\u00E1');
      line = line.replaceAll("&agrave;","" + '\u00E0');

      line = line.replaceAll("&oacute;","" + '\u00F3');
      line = line.replaceAll("&ograve;","" + '\u00F2');

      line = line.replaceAll("&eacute;","" + '\u00E9');
      line = line.replaceAll("&egrave;","" + '\u00E8');
      
      line = line.replaceAll("&iacute;","" + '\u00ED');
      line = line.replaceAll("&igrave;","" + '\u00EC');

      line = line.replaceAll("&uacute;","" + '\u00FA');
      line = line.replaceAll("&ugrave;","" + '\u00F9');

      line = line.replaceAll("&ccedil;","" + '\u00E7');

      line = line.replaceAll("&auml;","" + '\u00E4');
      line = line.replaceAll("&ouml;","" + '\u00F6');
      line = line.replaceAll("&uuml;","" + '\u00FC');
      //System.out.println("Translated -----------------------> " + text );
      }
      return line;
    }
    
    
   
} // LoginButtonApplet
