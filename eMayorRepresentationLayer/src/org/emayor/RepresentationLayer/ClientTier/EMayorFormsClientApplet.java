package org.emayor.RepresentationLayer.ClientTier;


  /**
   *  The JApplet, which displays and processes
   *  eMayorForms.
   *
   *  Usage: See the list of applet parameters.
   * 
   *  UoZurich, jpl
   */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

import org.emayor.RepresentationLayer.ClientTier.Controlers.printing.FormPrinterDialog;
import org.emayor.RepresentationLayer.ClientTier.Controlers.printing.PrintableJPanel;
import org.emayor.RepresentationLayer.ClientTier.Controlers.printing.XMLDocumentPrinterDialog;
import org.emayor.RepresentationLayer.ClientTier.Logic.DocumentProcessor;
import org.emayor.RepresentationLayer.ClientTier.Logic.SandboxCompliantDiskManger;
import org.emayor.RepresentationLayer.ClientTier.Utilities.gui.*;
import org.emayor.RepresentationLayer.Shared.Utilities.DataUtilities;
import org.emayor.RepresentationLayer.ClientTier.Utilities.ThreadEngine.ThreadEngine;



public class EMayorFormsClientApplet extends JApplet implements ResourceLoader
{

  // The version of the applet:
  private final static String AppletVersion= "2.0";


  // The eMayorForms xml document, which is loaded from the
  // server in the applet's init() method.
  private StringBuffer eMayorFormDocument = null;

  // Swing UI components:
  private JPanel menuPanel;
  private PrintableJPanel formsInteractionPanel;
  private JPanel formsModelPanel;
  private JTextArea formsModelTextArea;

  private JTabbedPane tabPane = null;
  private JSenseButton print_eDocumentButton;  
  private JSenseMenu fileMenu;
  private JMenuItem saveFormMenuItem;
  private JMenuItem loadFormMenuItem;
  private JMenuItem signatureInfoMenuItem;
  
  // applet logic flags
  private boolean hasStarted = false;
  
  // Properties, downloaded from the server:
  private LanguageProperties languageProperties = null;
  private EnumerationProperties enumerationProperties = null;

  // Text from the applet parameter values:
  private String postURL = "";
  private String appletPropertiesURL = "";
  private String enumerationPropertiesURL = "";
  private String redirectionAddressAfterPost = null;
  
  // The document processor creates the GUI with all
  // functionailty, including the validation thread.
  private DocumentProcessor docProcessor = null;
    
  
  // If this flag is set, the applet does not download
  // an eMayorForm from the server, but lets the client
  // select a saved eMayorForm from his filesystem. (FormService mode)
  private boolean read_eMayorFormFromClient = false;
  
  
  // Use UTF-8 for file read operations
  private static final Charset charset = Charset.forName("UTF-8");

  // The language for the UI. It's set in the init() method.
  private String language = "en"; // english as default
  
  
  // The error manager displays error messages and controls
  // the JTextArea for the error display at the bottom of the applet.
  private ErrorManager errorManager;  
  
  
  
  public EMayorFormsClientApplet()
  {               
    // Add custom UI components :
    UIManager.put("TabbedPaneUI", "org.emayor.RepresentationLayer.ClientTier.Utilities.gui.CustomMetalTabbedPaneUI" );

    // Create the errormanager as soon as possible, so
    // attempts to add error messages do work:
    this.errorManager = new ErrorManager(this);
    
    this.getContentPane().setLayout( new BorderLayout(2,2) );
    this.menuPanel = new JPanel( new FlowLayout(FlowLayout.LEFT,0,0) );
    this.attachTheMenuTo( this.menuPanel );
        
    this.getContentPane().add( menuPanel, BorderLayout.NORTH );

    this.formsInteractionPanel = new PrintableJPanel( new BorderLayout(0,0) );
    
    this.formsModelPanel = new JPanel( new BorderLayout(2,2) );
    this.formsModelTextArea = new JTextArea();
    this.formsModelTextArea.setEditable(false);
    this.formsModelPanel.add( this.formsModelTextArea, BorderLayout.CENTER );
    // add a print button and print functionality to the formsModelPanel:
    // The text of the button cannot be set here already, this is done in the init() method,
    // after the languageProperties are available.
    this.print_eDocumentButton = new JSenseButton(" ", true );
    this.print_eDocumentButton.setIcon( this.loadImageIcon("/pictures/applet/selection.gif"));
    this.print_eDocumentButton.setFocusPainted(false);
    this.print_eDocumentButton.addActionListener(new java.awt.event.ActionListener()
     {
       public void actionPerformed(ActionEvent e)
       {
         print_EMayorForm_XML_Document();
       }
     });
        
    this.tabPane = new JTabbedPane();
    
    // The formsInteractionPanel shall be displayed on the top,
    // so insert a borderlayout wrapper panel:
    JPanel wrapPanel = new JPanel( new BorderLayout(0,0) );
    wrapPanel.add(this.formsInteractionPanel, BorderLayout.NORTH );
    
    JScrollPane interactionScrollPane = new JScrollPane( wrapPanel);
    int labelFontSize = UIManager.getFont("Label.font").getSize();
    int unitIncrement = labelFontSize;
    interactionScrollPane.getVerticalScrollBar().setUnitIncrement( unitIncrement );
    int blockIncrement = unitIncrement*4;
    interactionScrollPane.getVerticalScrollBar().setBlockIncrement( blockIncrement );
            
    // Note: The tab titles can be updated (translated into the specific languages)
    //       in the init method when the languageProperties object has been created.
    
    tabPane.addTab("Interaction", interactionScrollPane);
    tabPane.addTab("Model Data",new JScrollPane( this.formsModelPanel ) );
    
    tabPane.addChangeListener( new ChangeListener()
    {
      public void stateChanged( ChangeEvent e )
      {
        // The model can have been changed by the user, so update this:
        if( docProcessor != null )
        {
          docProcessor.updateTheModelTabContent();
        }
      }
    });

    this.getContentPane().add( tabPane, BorderLayout.CENTER );
    
    // add the error manager textarea to the south:
    JScrollPane errorManagerScrollPane = new JScrollPane( this.errorManager.getErrorTextArea(),
                                                          JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
    this.getContentPane().add( errorManagerScrollPane, BorderLayout.SOUTH );
    
    // Write out the version:
    System.out.println("eMayor GUI Version: " + AppletVersion );
    
  } // Constructor


  
  
  
  public ErrorManager getErrormanager()
  {
    return this.errorManager;
  }
  
  
  
  
  private void attachTheMenuTo( final JPanel basePanel )
  {
    JMenuItem item; // temporary worker attribute used below
    String menuIconName;

    // Note: The language specific text for the menu and its items
    //       is set in the init() method, where languageProperties
    //        becomes available.
    
    // Create the filemenu :
    this.fileMenu = new JSenseMenu( " File " ,this );
    fileMenu.setMnemonic('s');
    // and add actions:
    this.saveFormMenuItem = new JMenuItem( " Save form on your PC " );
    item = fileMenu.add( this.saveFormMenuItem );
    item.setIcon( this.loadImageIcon("/pictures/applet/save.gif") );
    item.addActionListener(new java.awt.event.ActionListener()
     {
        public void actionPerformed( ActionEvent e )
        {
          save_EMayorForm_XML_Model_ToDisk();
        }
     });
    this.loadFormMenuItem = new JMenuItem( " Load form from your PC " );
    item = fileMenu.add( this.loadFormMenuItem );
    item.setIcon( this.loadImageIcon("/pictures/applet/load.gif") );
    item.addActionListener(new java.awt.event.ActionListener()
     {
        public void actionPerformed( ActionEvent e )
        {
          load_eDocument_FromDisk_IntoExisting_eMayorForm();
        }
     });

    this.signatureInfoMenuItem = new JMenuItem("Signature Information");
    item = fileMenu.add( this.signatureInfoMenuItem );
    item.setIcon( this.loadImageIcon("/pictures/applet/selection.gif") );
    item.addActionListener(new java.awt.event.ActionListener()
     {
        public void actionPerformed( ActionEvent e )
        {
          showSignatureInformation();
        }
     });
    
    // Create the menuBar :
    JMenuBar menuBar = new JMenuBar();
    Font theFont = UIManager.getFont("TextField.font");
    int fontSize = theFont.getSize();
    menuBar.setMargin( new Insets(0,fontSize,0,fontSize) );

    // Get rid of the complicating border :
    menuBar.setBorder( BorderFactory.createEmptyBorder(0,0,0,0));

    menuBar.setOpaque(false); // because otherwise we wouldn't see
                              // the underlying GradientPanel

    // add the menues to the menuBar :
    menuBar.add( fileMenu        );
    
    basePanel.add( menuBar);
  } // createTheMenu



  
  
  
 /**
  *  Called by the applet menu.
  *  It calculates informations about signatures inside
  *  the initially received e-document.
  *  Calculations are done outside the EDT, but a modal
  *  progress dialog informs the user about it.
  *  Results are displayed in frame.
  */ 
  private void showSignatureInformation()
  {
    // For the progress dialog, we need the parent frame to make it modal on this one:
    final Frame appletParentFrame = JOptionPane.getFrameForComponent(this);
    Runnable signatureInfoRunnable = new Runnable()
    {
      public void run()
      {
        try
        {
          // instanciate the signatureInfoProcessor:
          ImageIcon progressIcon = loadImageIcon("/pictures/applet/selection.gif");
          SignatureInfoProcessor signatureInfoProcessor = new SignatureInfoProcessor( languageProperties );        
          signatureInfoProcessor.showSignatureInformation( eMayorFormDocument,
                                                           appletParentFrame,
                                                           progressIcon );
        }
        catch( Exception eee )
        {
          String message = "Reason:" + eee.getMessage();
          String title = "Unable to display signature information.";
          JOptionPane.showMessageDialog(EMayorFormsClientApplet.this,message,title,JOptionPane.WARNING_MESSAGE);        
          eee.printStackTrace();
        }
      }
    };
    ThreadEngine.getInstance().addRunnable(signatureInfoRunnable,"signatureInfoRunnable");
  } // showSignatureInformation

  

  
  
  
 /**
  *  Print the xml model to a printer, using a printer dialog
  *  for letting the user adjust printer settings.
  */ 
  public void print_EMayorForm_XML_Document()
  {
    String eMayorFormModelAsString = this.docProcessor.get_EMayorFormModelAsString();
    // For the dialog, we need the parent frame to make it modal on this one:
    Frame appletParentFrame = JOptionPane.getFrameForComponent(this);
    String dialogTitle = this.languageProperties.getTextFromLanguageResource("PrinterDialog.Title");
    XMLDocumentPrinterDialog printerDialog = 
         new XMLDocumentPrinterDialog( appletParentFrame,this,this.languageProperties,dialogTitle,
                                       eMayorFormModelAsString );
    printerDialog.setVisible(true);    
  } // print_EMayorForm_XML_Model


  
  
  
  /**
   *  Print the interaction panel to a printer, using a printer dialog
   *  for letting the user adjust printer settings.
   */ 
   public void print_EMayorForm_InteractionPanel()
   {
     // Recreate the formsInteractionPanel, but use an other instance
     // and set the version for printing flag:
     try
     {
       PrintableJPanel formsPanelForPrinting = new PrintableJPanel( new BorderLayout(0,0) );
       // process it and display the result in the context pane:
       DocumentProcessor printableDocProcessor = 
                           new DocumentProcessor( formsPanelForPrinting,
                                                  this.formsModelTextArea,
                                                  this.languageProperties,
                                                  this.enumerationProperties,
                                                  this.language,
                                                  this,
                                                  true /* for printing */ );
       printableDocProcessor.processDocument( this.eMayorFormDocument );
       // For the dialog, we need the parent frame to make it modal on this one:
       Frame appletParentFrame = JOptionPane.getFrameForComponent(this);
       String dialogTitle = this.languageProperties.getTextFromLanguageResource("PrinterDialog.Title");
       FormPrinterDialog printerDialog = 
            new FormPrinterDialog( appletParentFrame,this,this.languageProperties,dialogTitle,
                                   formsPanelForPrinting );
       printerDialog.setVisible(true);    
      }
      catch( Exception e )
      {
        e.printStackTrace();
      }   
   } // print_EMayorForm_InteractionPanel

   
   
   
   
  
  
 /**
  *  Uses JNLP services for writing to the client filesystem
  *  without having to request applet security permisions for the filesystem.
  *  It is controled by JNLP and the JRE and runs inside the default sandbox.
  *  It is called from the applet's menu and optionally from eMayorForm's
  *  SaveModelToDiskSubmitter buttons, if present.
  */ 
  public void save_EMayorForm_XML_Model_ToDisk()
  {
    SandboxCompliantDiskManger m = new SandboxCompliantDiskManger(this);
    
    // Reinsert the original schema location to the e-document,
    // before it is saved, so the xml signature still is valid:
    this.getDocumentProcessor().setLocalSchemaLocation(false);
    String eMayorFormModelAsString = this.docProcessor.get_EMayorFormModelAsString();
    // but insert the schema location pointing to the
    // local path again for the further processing:
    this.getDocumentProcessor().setLocalSchemaLocation(true);
        
    System.out.println(" ");
    System.out.println("Saving the following model as string:");
    System.out.println(eMayorFormModelAsString);
    System.out.println(" ");
    try
    {
      m.saveData( eMayorFormModelAsString,"*.xml" );
      // Note: saveData has its own error feedback
    }
    catch( Exception ex )
    {
      System.out.println("*** save_EMayorForm_XML_Model_ToDisk: Unable to load from disk: ");
      System.out.println("exception message= " + ex.getMessage() );
      this.showInstallationPolicyError();
    }
    catch( Error err )
    {
      System.out.println("*** save_EMayorForm_XML_Model_ToDisk: Unable to load from disk: ");
      System.out.println("exception message= " + err.getMessage() );
      this.showInstallationPolicyError();
    }
  } // save_EMayorForm_XML_Model_ToDisk


  
  
  /**
   *  Uses JNLP services for reading from the client filesystem
   *  without having to request applet security permisions for the filesystem.
   *  It is controled by JNLP and the JRE and runs inside the default sandbox.
   */ 
  private void load_eDocument_FromDisk_IntoExisting_eMayorForm()
  {
    String eMayorFormModelAsString = null;
    try
    {
      SandboxCompliantDiskManger m = new SandboxCompliantDiskManger(this);    
      eMayorFormModelAsString = m.loadData();
    }
    catch( Exception ex )
    {
      System.out.println("*** load_eDocument_FromDisk_IntoExisting_eMayorForm: Unable to load from disk: ");
      System.out.println("exception message= " + ex.getMessage() );
      this.showInstallationPolicyError();
    }
    catch( Error err )
    {
      System.out.println("*** load_eDocument_FromDisk_IntoExisting_eMayorForm: Unable to load from disk: ");
      System.out.println("error message= " + err.getMessage() );
      this.showInstallationPolicyError();
    }
    System.out.println(" ");
    System.out.println("Loaded the following e-document as string:");
    System.out.println(eMayorFormModelAsString);
    System.out.println(" ");
    // If the user has clicked the cancel button, eMayorFormModelAsString
    // is null. Otherwise load it:
    if( eMayorFormModelAsString != null )
    {
      //TextFrame f = new TextFrame("Client Applet: Loaded eMayorForm from disk:",
      //                            eMayorFormModelAsString );
      this.docProcessor.setEMayorFormModelContentFrom( eMayorFormModelAsString );
    }
  }


  
  

 /**
  *  Part of the FormService:
  *  The user can select an eDocument which was previously saved to
  *  the client computer filesystem.
  *  This e-document is sent to the server, which returns the
  *  associated eMayorForm containing this e-document as model.
  *  This eMayorForm then is displayed on the client.
  */ 
  private StringBuffer get_eMayorFormUsingTheFormService()
  { 
    String eDocument = null;
    StringBuffer eMayorForm_Result = null;
    try
    {
      SandboxCompliantDiskManger m = new SandboxCompliantDiskManger(this);    
      eDocument = m.loadData();
    }
    catch( Exception ex )
    {
      System.out.println("*** get_eMayorFormUsingTheFormService: Unable to load from disk: ");
      System.out.println("exception message= " + ex.getMessage() );
      this.showInstallationPolicyError();
    }
    catch( Error err )
    {
      System.out.println("*** get_eMayorFormUsingTheFormService: Unable to load from disk: ");
      System.out.println("error message= " + err.getMessage() );
      this.showInstallationPolicyError();
    }
    System.out.println(" ");
    System.out.println("Loaded the following e-document as string:");
    System.out.println(eDocument);
    System.out.println(" ");
    // If the user has clicked the cancel button, eDocument
    // is null. Otherwise load it:
    if( eDocument != null )
    {
      // Call the server for putting the e-document into the
      // associated eMayorForm, which is returned then.
      // The target URL for post is set in the applet parameters
      // and will call processDocumentInFormService() on the server.
      eMayorForm_Result = this.postDocumentAndReturnReply(eDocument,"");    
    }
    else
    {
      System.out.println("get_eMayorFormUsingTheFormService: No e-document (dialog was cancelled)-> returning an empty eMayorForm.");    
    }
    return eMayorForm_Result;
  } // get_eMayorFormUsingTheFormService


  
  
  
  
 /**
  *   Message for the user, caused by a missing eMayor specific
  *   entries in the .java.policy file in user.home or cause
  *   by a missing .java.policy file itself.
  */ 
  private void showInstallationPolicyError()
  {
    String message = "The eMayor client is not properly installed.\nPlease reinstall eMayor.";
    String title = "Missing applet access rights.";
    JOptionPane.showMessageDialog(this,message,title,JOptionPane.WARNING_MESSAGE);
  }
  

  
  
 /**
  *  Called when this applet is loaded into the browser.
  */
  public void init()
  {
    System.out.println("eMayorFormsClientApplet.init() starts.");

    JDialog.setDefaultLookAndFeelDecorated(true); // Java Style for Dialogs
    
    // Display the load screen.
    // See http://java.sun.com/docs/books/tutorial/uiswing/components/applet.html
    // One must change to the event dispatch thread and wait for termination too.
    // Note that the browsers call this method in a their system thread context.
    try
    {
      EventQueue.invokeAndWait( new Runnable()
      {
        public void run()
        {
          displayLoadScreen();
        }
      });
    } 
    catch( Exception e )
    {
      System.err.println("*** init(): Couldn't display the load screen.");
    }
    
    // Check if the applet should just read an eMayorForm from the
    // client computer and display it (FormService):
    String read_eMayorFormFromClient_Value = getParameter("read_eMayorFormFromClient");
    
    this.read_eMayorFormFromClient = false;
    if( ( read_eMayorFormFromClient_Value != null ) &&
        ( read_eMayorFormFromClient_Value.equals("true") ) )
    {
      this.read_eMayorFormFromClient = true;  
    }
    // Get the name of the eMayorForm xml document to be downloaded
    // from the server:
    String eMayorFormName = getParameter("eMayorFormName");
    // Either we are in the FormService mode (read_eMayorFormFromClient is set)
    // and the user can choose a previously saved eMayorForm from disk,
    // which we will display,
    // or
    // we are in the usual mode, where we have to request  the eMayorForm
    // defined by eMayorFormName from the server, so:
    //
    if( ( eMayorFormName != null ) || (read_eMayorFormFromClient==true) )
    {
      // Set the language member attribute:
      this.language = super.getParameter("Language");
      if( this.language == null ) this.language = "en"; // english as default 
      
      // Request the applet properties:
      this.appletPropertiesURL = getParameter("appletPropertiesURL");
      if( this.appletPropertiesURL != null )
      {
        System.out.println("Got the following appletPropertiesURL: " + this.appletPropertiesURL );
      }
      else
      {
        System.out.println("No appletPropertiesURL. Set default.");
        this.appletPropertiesURL = "service.do?do=getAppletProperties"; //default
      }

      // Request the enumeration properties:
      this.enumerationPropertiesURL = getParameter("enumerationPropertiesURL");
      if( this.enumerationPropertiesURL != null )
      {
        System.out.println("Got the following enumerationPropertiesURL: " + this.enumerationPropertiesURL);
      }
      else
      {
        System.out.println("No enumerationPropertiesURL. Set default.");
        this.enumerationPropertiesURL = "service.do?do=getEnumerationProperties"; //default
      }
      System.out.println( "Init(): Language is: " + language );

      // Following specification:
      // If a Language parameter is specified, try to download
      // a language properties file with name: 
      // languageFileName = "eMayorForms_" + language + ".properties";
      languageProperties = new LanguageProperties( language,super.getDocumentBase(),this.appletPropertiesURL );
      
      // Then try to download the optional enumeration properties:      
      this.enumerationProperties = new EnumerationProperties(super.getDocumentBase(),this.enumerationPropertiesURL);
            
      try
      {
      	// Update some GUI text now: (do nothing on error here)
      	try
		{
          String interactionTitle = this.languageProperties.getTextFromLanguageResource("Message.Interaction");
          this.tabPane.setTitleAt(0,interactionTitle);
          String modelTitle = this.languageProperties.getTextFromLanguageResource("Message.ElectronicDocument");
          this.tabPane.setTitleAt(1,modelTitle);
          // The texts for the menu and its items:          
          String fileMenuName = this.languageProperties.getTextFromLanguageResource("FileMenu.Name");
          this.fileMenu.setText("  " + fileMenuName + "  ");
          String saveFormToDisk = this.languageProperties.getTextFromLanguageResource("FileMenu.SaveFormToDisk");
          this.saveFormMenuItem.setText("  " + saveFormToDisk + "  ");
          String loadFormFromDisk = this.languageProperties.getTextFromLanguageResource("FileMenu.LoadFormFromDisk");
          this.loadFormMenuItem.setText("  " + loadFormFromDisk + "  ");         
          String signatureInfoTitle = this.languageProperties.getTextFromLanguageResource("Message.SignatureInfo");
          this.signatureInfoMenuItem.setText("  " + signatureInfoTitle + "  ");
        }
      	catch( Exception e2 )
		{
          System.out.println("Unable to update some applet UI texts.");
		}
      	
      	// Also the post URL is optional.
      	// For readonly documents, a button is optional at all.
        this.postURL = getParameter("postURL");
      	System.out.println("Got the following postURL: " + this.postURL );
      	if( (postURL == null) || (postURL.length() == 0) )
      	{
      	  System.out.println("postURL: Not passed as parameter.");
      	}
        else
        {
       	  System.out.println("postURL: " + postURL + " was passed as parameter.");
        }
      	// The redirectionAddressAfterPost tag is optional. If present,
      	// the applet will tell the browser to redirect to this address
      	// after it has received a reply for the post of a document.
      	this.redirectionAddressAfterPost = getParameter("redirectionAddressAfterPost");
      	if( this.redirectionAddressAfterPost != null )
      	{
          System.out.println("Got the following redirectionAddressAfterPost: " + this.redirectionAddressAfterPost);
      	}
      	else
      	{
          System.out.println("No redirectionAddressAfterPost.");
        }
  
        // Only download the eMayorForm from the server, if we are
        // not in the FormService mode:
        if( !read_eMayorFormFromClient ) // The FormService mode
        {
          // download the eMayorForm xml document:
          System.out.println("downloading eMayorforms UTF-8 inputfile " + eMayorFormName );
          this.eMayorFormDocument = this.download_UTF_8_Document(eMayorFormName);
        }
        
        // Debug file write:
        // DataUtilities.DebugFileWriteTo(this.eMayorFormDocument.toString(),"applet_eMayorForm_receivedFromWebTier.xml");

        // Assign the print button text now:
        String print_eDocumentButtonText = this.languageProperties.getTextFromLanguageResource("PrinterDialog.PrintDocumentButtonText");
        this.print_eDocumentButton.setText("   " + print_eDocumentButtonText + "   ");
                        
      }
      catch( Exception e )
      {      
        e.printStackTrace();
        final String eMessage = e.getMessage();
        try           
        {    
          EventQueue.invokeAndWait( new Runnable()
          {
            public void run()
            {
              displayErrorScreen( eMessage );
            }
          });
        }
        catch( Exception e3465 )
        {
        }
        System.out.println("*** ");
        System.out.println("*** Unable to get required files from the server.");
        System.out.println("*** ");
        this.errorManager.addErrorMessage("Unable to get required files from the server.",false);
      }
    } // if
    else
    {
      try
      {
        EventQueue.invokeAndWait( new Runnable()
        {
          public void run()
          {
            displayErrorScreen( "Missing eMayorFormsName applet parameter." );
          }
        });
      }
      catch( Exception e6234 )
      {
      }
      this.errorManager.addErrorMessage("Missing eMayorFormsName applet parameter.",false);
    }
  } // init

  




 /**     
  *  Display the load screen. Called in the EDT.
  */
  private void displayLoadScreen()
  {                                                           
    System.out.println("showLoadScreen() called.");
    this.formsInteractionPanel.removeAll();
    this.formsInteractionPanel.setLayout( new FlowLayout() );
    this.formsInteractionPanel.setBackground( new Color(180,255,180) );
    this.formsInteractionPanel.add( new JLabel("loading...") );
  } // showLoadScreen


 /**
  *  Display the error user feedback screen. Called in the EDT.
  */                      
  private void displayErrorScreen( final String errorMessage )                      
  {                                                           
    System.out.println("displayErrorScreen() called.");                              
    Dimension pDim = this.formsInteractionPanel.getSize();
    this.formsInteractionPanel.removeAll();
    this.formsInteractionPanel.setSize( pDim.width, pDim.height );
    this.formsInteractionPanel.setLayout( new BorderLayout(4,4) );
    this.formsInteractionPanel.setBackground( new Color(255,180,180) );
    this.formsInteractionPanel.add( new JLabel("Error..."), BorderLayout.CENTER );
    JTextArea errortextArea = new JTextArea("Error: " + errorMessage,4,52);
    this.formsInteractionPanel.add( new JScrollPane(errortextArea), BorderLayout.SOUTH );
  } // showLoadScreen
                                                                                    

  
  
  
  
  
 /**
  *  Downloads the file with the passed name from the server,
  *  from which the applet has been loaded.                                                
  *  The document should be a text file, not a binary one.
  * 
  *  Uses UTF-8 character encoding.
  */
  private StringBuffer download_UTF_8_Document( final String documentName ) throws Exception
  {
    System.out.println("downloadDocument() documentName= " + documentName );
    URL documentURL = new URL( super.getDocumentBase() ,documentName );
    InputStream documentInputStream = documentURL.openStream();
    ByteArrayOutputStream buffer = DataUtilities.ReadByteFileFromInputStream(documentInputStream);
    String downloadedDocument = buffer.toString("UTF-8");
    
    //TextFrame f = new TextFrame("Client Applet: Downloaded document " + documentName,
    //                            downloadedDocument );
    
    /*
    System.out.println(" ");
    System.out.println(" ");
    System.out.println(" Downloaded document:");
    System.out.println(" ");
    System.out.println(downloadedDocument);
    System.out.println(" ");
    System.out.println(" ");
    System.out.println(" ");
    */
    
    return new StringBuffer( downloadedDocument );
  } // downloadDocument
                           

  
  
  
 /**
  *  Post the string document to the server from where the
  *  applet has been loaded.
  *  Then wait for the string response, suppose it is html
  *  and display it in a html editor pane.
  * 
  *  documentParameter are used by the button submitters,
  *  like accept or deny buttons for passing those button attributes.
  */
  public boolean postDocument( final String document,
  		                       final String documentParameter )
  {
    // The user must wait the transmission:
    String waitText = this.languageProperties.getTextFromLanguageResource("Message.PleaseWait");
    this.showHTML( waitText ); // EDT safe method
    
    //TextFrame f = new TextFrame("Client Applet: Document posted to the server:",
    //                            document );
    
    boolean success = true;
    try
    {
       StringBuffer htmlReply = this.postDocumentAndReturnReply(document,documentParameter);       
       if( htmlReply.length() > 0 ) // all is ok
       {
         // Look, if the the applet has received a page redirection directive,
         // in which case, the applet will tell the browser to
         // redirect to that page:
         if( this.redirectionAddressAfterPost != null )
         {
           this.gotoRedirectionPage();       
         }         
       }
       else // don't redirect, but inform about the error:
       {
       	 String errorText = "<html><body>No reply from the server. An error has occured.</body></html>";
         this.showHTML( errorText );
         success = false;
       }
    }                                 
    catch(Exception e)
    {
      success = false;
      System.out.println("*** postDocument(): Exception catched:");
      e.printStackTrace();
    }
    System.out.println("postDocument() has ended.");
    return success;
  } // postDocument


  
  
 /**
  *  Post the passed document to the server and return
  *  the reply of the server.
  */ 
  public StringBuffer postDocumentAndReturnReply( final String document,
                                                  final String documentParameter )
  {
    StringBuffer reply = new StringBuffer();
    try
    {
       // Establish a connection to the server:
       System.out.println("postDocument(): using basis URL: " + super.getDocumentBase().toExternalForm() );
       System.out.println("postDocument(): postURL " + this.postURL );
       URL url = new URL( super.getDocumentBase(), this.postURL );

       URLConnection con = url.openConnection();
       System.out.println("postDocument(): Received a URLConnection subclass of type " +
                     con.getClass().getName() );
       con.setDoInput(true);
       con.setDoOutput(true);
       con.setUseCaches(false);
       
       con.setRequestProperty("CONTENT_LENGTH", "" + document.length() ); // actually not checked
       System.out.println("postDocument(): Set content-length to: " + document.length() );
       System.out.println("postDocument(): Getting an output stream...");
       
       OutputStream os = con.getOutputStream();
       OutputStreamWriter osw = new OutputStreamWriter(os);
       osw.write("RequestDocument=");
       
       osw.write( URLEncoder.encode(document, "UTF-8") );
       
       if( ( documentParameter != null ) && (documentParameter.length() > 0) )
       {
         osw.write("&RequestDocumentParameter=");
         osw.write( URLEncoder.encode(documentParameter, "UTF-8") );       
       }
       osw.flush();
       osw.close();
       
       System.out.println("postDocument(): Output stream flushed.");       
       System.out.println("postDocument(): Getting an reply...");
       
       InputStream is = con.getInputStream();
       // any response ?
       InputStreamReader isr = new InputStreamReader(is,charset);
       BufferedReader br = new BufferedReader(isr);
       String line = null;
       while( (line = br.readLine()) != null )
       {
         reply.append( line );
         reply.append( "\n" );
         System.out.println("postDocument() response: " + line);
       } // while       
    }                                 
    catch(Exception e)
    {
      System.out.println("*** postDocument(): Exception catched:");
      e.printStackTrace();
    }
    return reply;
  } // postDocumentAndReturnReply
  
  
  

  
  
 /**
  *  show text in applet. EDT safe.
  */ 
  private void showHTML( final String htmlString )
  {
    System.out.println("showHTML() called.");
    EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {
      	showHTML_in_EDT(htmlString);
      }
    });
    // If we are outside the EDT, add some time for Swing:
    if( !EventQueue.isDispatchThread() )
    {
      // showHTML requires some time, so give 0.7 sec:
      try{ Thread.sleep(777); } catch( Exception esdf ){}    
    }
  }
  

  
  
  
  private void showHTML_in_EDT( final String htmlString )
  {
    Dimension pDim = this.formsInteractionPanel.getSize();
    this.formsInteractionPanel.removeAll();
    this.formsInteractionPanel.setSize( pDim.width, pDim.height );
    this.formsInteractionPanel.setLayout( new BorderLayout(4,4) );
    this.formsInteractionPanel.setBackground( new Color(255,255,255) );       
    JHTMLEditorPane editorPane = new JHTMLEditorPane( htmlString );
    this.formsInteractionPanel.add( editorPane, BorderLayout.CENTER );
    this.formsInteractionPanel.updateUI();
  }
  
  
  
  
 /** 
  * Look, if the reply contains a page redirection directive,
  * in which case, the applet will tell the browser to
  * redirect to that page:
  * 
  * Called after a post process, or by the RedirectSubmitter button.
  */
  public void gotoRedirectionPage()
  {
  	System.out.println("Starting redirector thread now. (Will wait 0.1 seconds)");
  	// Delay this for giving Swing some display task time:
  	Thread redirector = new Thread()
	{
  		public void run()
  		{
          try{ Thread.sleep(111); } catch( Exception e ){}
  		  // Put it into the EDT: (required by Swing conventions)	
          EventQueue.invokeLater( new Runnable()
		  {
            public void run()
            {
              gotoRedirectionPage_InUserThread();
            }
          });
  		}
	}; // redirector
	redirector.start();	
  } // checkProcessPageRedirection
  

  
  
 /**
  *  See checkProcessPageRedirection(), which calls this. 
  */ 
  private void gotoRedirectionPage_InUserThread()
  {
  	System.out.println("Redirecting the browser to: " + this.redirectionAddressAfterPost );
  	try
	{
      URL redirectionURL = new URL( super.getDocumentBase(), this.redirectionAddressAfterPost );
  	  this.getAppletContext().showDocument(redirectionURL);
	}
  	catch( Exception e )
	{
  	  System.out.println("*** Applet: Browser page redirection to " +
                         this.redirectionAddressAfterPost +
						 " has failed. Exception is: ");
  	  e.printStackTrace();
	}
  } // checkRedirection_InThread
 
  
  public DocumentProcessor getDocumentProcessor()
  {
    return this.docProcessor;
  }
  

  
  
  
  
 /**
  *   Called to start the applet's execution.
  */
  public void start()
  {
    System.out.println("start() called.");
    if( ( !this.hasStarted ) && // only once
        (  this.errorManager.getNumberOfErrors() == 0  ) ) // if no error screen is active already
    {
      this.hasStarted = true;      
      try
      {
        // process it and display the result in the context pane:
        this.docProcessor = new DocumentProcessor( this.formsInteractionPanel,
                                                   this.formsModelTextArea,
                                                   this.languageProperties,
                                                   this.enumerationProperties,
                                                   this.language,
                                                   this,
                                                   false /* not for printing */ );
        // If we are in FormService mode, let the user select
        // an eMayorForm from his file system initially:
        if( this.read_eMayorFormFromClient )
        {
          this.eMayorFormDocument = this.get_eMayorFormUsingTheFormService();
          if( this.eMayorFormDocument != null )
          {
            // process the downloaded eMayorFormDocument:
            this.docProcessor.processDocument( this.eMayorFormDocument );
          }
          else // The user has cancelled the file load dialog, so just go back:
          {
            this.gotoRedirectionPage();
          }
        }
        else
        {
          // process the downloaded eMayorFormDocument:
          this.docProcessor.processDocument( this.eMayorFormDocument );
        }
      }
      catch( Exception e )
      {
        e.printStackTrace();
        final String eMessage = e.getMessage();
        try
        {    
          EventQueue.invokeAndWait( new Runnable()
          {
            public void run()
            {
              displayErrorScreen( eMessage );
            }
          });
        }
        catch( Exception e3465 )
        {
        }
      }  
    }
    // always tell the user, if errors are present:
    if( this.errorManager.getNumberOfErrors() > 0 )
    {
      // decouple from the active thread:      
      Thread errorInfoThread = new Thread()
      {
        public void run()
        {
          // Give Swing some time first:
          try{ Thread.sleep(444); } catch( Exception ee ){}
          // then add an info task to the EDT:
          if( languageProperties == null )
          {
            errorManager.addErrorMessage("Multilingual support not possible: Property files could not be downloaded from the server.",false);         
          }          
          final String message = (languageProperties != null) ?
                                  languageProperties.getTextFromLanguageResource("Applet.ErrorDialogText"):
                                  "Warning";
          final String title = (languageProperties != null) ?
                                languageProperties.getTextFromLanguageResource("Applet.ErrorDialogTitle"):
                                "Some errors have occured. See the description on the bottom of the applet.";
          EventQueue.invokeLater( new Runnable()
          {
            public void run()
            {
              JOptionPane.showMessageDialog(EMayorFormsClientApplet.this,message,title,JOptionPane.WARNING_MESSAGE);      
            }
          });
        }   
      };
      errorInfoThread.start();
    }
  } // start

  
  
  
  
  
 /**
  *  Called to stop (temporarily or permanently) the applet's execution.
  */
  public void stop() 
  {
    System.out.println("stop() called.");
  } // stop



 /**
  *  Called when the applet is no longer used from the browser.
  *  One can free resources here.
  */
  public void destroy() 
  {
    System.out.println("destroy() called.");
    if( this.docProcessor != null )
    {
      this.docProcessor.stop();
    }
  } // destroy


  
  
  public LanguageProperties getLanguageProperties()
  {
    return this.languageProperties;  
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
        this.errorManager.addErrorMessage( "Unable to download " + pathName, false );
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
          this.errorManager.addErrorMessage( "Couldn't read stream from file: " + pathName, false );
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
      this.errorManager.addErrorMessage( errorMessage, false );
      return null;
    }
  }


  

  
} // eMayorFormsClientApplet







