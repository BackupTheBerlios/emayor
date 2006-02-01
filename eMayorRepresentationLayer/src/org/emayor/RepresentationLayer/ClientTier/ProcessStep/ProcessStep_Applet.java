package org.emayor.RepresentationLayer.ClientTier.ProcessStep;

import java.awt.*;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.*;


   /**
    *   An applet, which displays a process step bar.
    *   It reads a comma separated list of texts for each step and
    *   the index of the current step from the applet parameters.
    * 
    *   Steps before the current step on the left are considered
    *   as processed steps in the past.
    * 
    *   Steps after the current step on the right are considered
    *   as steps to be processed in the future.
    */


public class ProcessStep_Applet extends JApplet
{

   private String errorMessage = null; // Set on error cases, to be displayed in this case.
   
   private String processStepTextList = ""; // holds the ";" separated list of texts
   private String processStepDescriptionList = ""; // holds the ";" separated list of descriptions
   private int processStepCurrentIndex = 0; // Holds the zero based index of the current entry
   
   private Color backgroundColor = new Color(242,242,242);
   
   private JPanel mainPanel;
   private JScrollPane scrollPane;
   
   private boolean contentHasBeenSet = false; // monoflop
   

   
   
   public ProcessStep_Applet()
   {
     this.getContentPane().setLayout( new BorderLayout() );
     this.mainPanel = new JPanel();
     this.mainPanel.setLayout( new BorderLayout(2,2) );
     int gap = UIManager.getFont("Label.font").getSize();
     this.mainPanel.setBorder( BorderFactory.createEmptyBorder(gap/8,gap/2,gap/8,gap/2));
     // No scrollbar - for space reasons, but a scrollPane:
     this.scrollPane = new JScrollPane( mainPanel,
                                        JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
     // no border:
     this.scrollPane.setBorder( BorderFactory.createEmptyBorder(0,0,0,0) );
     this.getContentPane().add(scrollPane,BorderLayout.CENTER);
     
     System.out.println("ProcessStepApplet.constructor: Set tooltips to react immediately.");
     ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
     toolTipManager.setInitialDelay(99);
     toolTipManager.setDismissDelay(30000);
     toolTipManager.setReshowDelay(99);
     
     System.out.println(">>ProcessStepApplet  V0.910");
  } // constructor
  

   
   
  /**
   *  Called when this applet is loaded into the browser.
   */
   public void init()
   {
     try
     {
        // Read the applet parameters:
        this.processStepTextList = super.getParameter("ProcessStepTextList");
        this.processStepDescriptionList = super.getParameter("ProcessStepDescriptionList");
        String processStepCurrentIndexString = super.getParameter("ProcessStepIndex");
        try
        {
          this.processStepCurrentIndex = Integer.parseInt(processStepCurrentIndexString);        
        }
        catch( Exception e1 )
        {
          // Set the error message, so it can be displayed in the applet:
          errorMessage = "Parameter ProcessStepIndex missing or not a number: " + e1.getMessage();
          e1.printStackTrace();
        }
        this.readBackgroundColor();
        this.setBackground( this.backgroundColor );
        this.mainPanel.setBackground( this.backgroundColor );
        this.scrollPane.setBackground( this.backgroundColor );
     }
     catch( Exception eee )
     {
       // Set the error message, so it can be displayed in the applet:
       errorMessage = "Error: " + eee.getMessage();
       eee.printStackTrace();
     }
   } // init

   

   
   
   private void readBackgroundColor()
   {
     // optional - default is set on error.
     String rgbList = super.getParameter("BackgroundColor");
     // should contain 3 numbers r,g,b:
     if( rgbList != null )
     {
       String[] colors = this.parseTextList( rgbList );
       if( colors.length == 3 )
       {
         try
         {
           int r = Integer.parseInt(colors[0].trim());
           int g = Integer.parseInt(colors[1].trim());
           int b = Integer.parseInt(colors[2].trim());
           this.backgroundColor = new Color(r,g,b);
           System.out.println("The background color was read and set. XXX");
         }
         catch( Exception eee )
         {
           eee.printStackTrace();
         }
       }
       else
       {
        System.out.println("*** Could not set background color: <> 3 rgb values");
       }
     }
     else
     {
       System.out.println("BackgroundColor: No parameter set. Using default value.");
     }
   }
 
   
   
   
   
   /**
    *  list is comma separated - return the tokens in an array:
    */ 
    private String[] parseTextList( final String list )
    {
      StringTokenizer tok = new StringTokenizer(list,",");
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
   
   
   
   /**
    *   Called to start the applet's execution.
    */
    public void start()
    {
      System.out.println(">>>ProcessStepApplet.start() called.");
      if( !contentHasBeenSet ) // set the mainpanel content only once
      {
        contentHasBeenSet = true;
        this.setupGraphicalView();
      }
    }
    

    
   /**
    *  Setup the view objects into the main panel.
    */ 
    private void setupGraphicalView()
    {
      if( this.errorMessage == null ) // no error -> show the process step view
      {
        ProcessStep_View view = new ProcessStep_View( this.processStepTextList,
                                                    this.processStepDescriptionList,
                                                    this.processStepCurrentIndex,
                                                    this.backgroundColor );
        this.mainPanel.add( view, BorderLayout.CENTER );
        this.mainPanel.updateUI();
        System.out.println("ProcessStepApplet: The view has been added. XXX");
      }
      else
      {
        this.mainPanel.add( new JLabel(this.errorMessage) );
      }
    } // setGraphicalView
    
    
    
    public void stop()
    {
      System.out.println(">>>ProcessStepApplet.stop() called.");
    }

    
    /**
     *  Called when the applet is no longer used from the browser.
     *  One can free resources here.
     */
     public void destroy() 
     {
        System.out.println(">>>ProcessStepApplet.destroy() called.");
     }
     
    
} // ProcessStepApplet


