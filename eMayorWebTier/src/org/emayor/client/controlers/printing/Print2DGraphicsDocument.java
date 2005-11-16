package org.emayor.client.controlers.printing;


/**
 *   Made from some samples of the
 *   SUN Print Service API User Guide [for jsdk 1.5]
 */


import java.io.*;
import java.awt.*;
import java.net.*;
import java.awt.image.*;
import java.awt.print.*;
import javax.swing.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.print.event.*;
import java.util.Vector;
    
import org.emayor.client.LanguageProperties;
import org.emayor.client.ResourceLoader;


public class Print2DGraphicsDocument implements PrintJobListener
{


  private Printable printableObject;
  private PrintRequestAttributeSet attributes;
  private JDialog parentDialog;
  private ResourceLoader resourceLoader;
  private LanguageProperties languageProperties;

  private final Vector printJobEventQueue = new Vector();



  public Print2DGraphicsDocument( final Printable thePrintableObject,
                             final PrintRequestAttributeSet theAttributes,
                             final JDialog theParentDialog,
                             final ResourceLoader theResourceLoader,
                             final LanguageProperties _languageProperties )
  {
    this.printableObject = thePrintableObject;
    this.attributes = theAttributes;
    this.parentDialog = theParentDialog;
    this.resourceLoader = theResourceLoader;
    this.languageProperties = _languageProperties;
  }

                                                                   


  public void printTheDocument()
  {
    // This method preferably is called in the ThreadEngine or at least in a user thread.
    // When the print job is running, the thread is forced into wait() state
    // and notified, as soon as the printer transfer has been completed or an error
    // has occured. This way, the unwanted possibility to crash the transfer by a
    // jvm shutdown (exit(0)) is smaller.
    if( EventQueue.isDispatchThread() )
     {
       String msg = "Print2DGraphicsDoc.printTheDocument: Develop error: Must be called in a user thread. Returning...";
       System.out.println(msg);
       JOptionPane.showMessageDialog(this.parentDialog,msg);
     }

    // Construct the print request specification.
    // The print data is a Printable object.

    //DocFlavor docFlavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
    DocFlavor docFlavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;

    // Locate a print service that can handle the request:
    PrintService[] services = PrintServiceLookup.lookupPrintServices( docFlavor, attributes );
    if( services.length > 0 )
     {
       PrintService selectedService = ServiceUI.printDialog( null, // GraphicsConfiguration
                                                             0, 0,
                                                             services,
                                                             services[0], // default service
                                                             docFlavor,
                                                             attributes );
       if( selectedService != null )
        {
          String printMessage = this.languageProperties.getTextFromLanguageResource("PrinterDialog.PrintMessage");
          final ProgressWindow progressWindow =
            new ProgressWindow( printMessage,null,this.parentDialog,
                                this.resourceLoader );

          EventQueue.invokeLater( new Runnable()
           {
             public void run()
             {
               progressWindow.showCentered();
             }
           });
          try{ Thread.sleep(88); } catch( Exception e ){}

          System.out.println("The currently selected printer is: " + selectedService.getName());
          // Create a print job for the chosen service:
          DocPrintJob docPrintJob = selectedService.createPrintJob();
          docPrintJob.addPrintJobListener( this );       
          try                                         
           {
             // Create a Doc object to hold the print data.
             // Note: PrintableDoc is a nested class of this class.
             Doc doc = new PrintableDoc(this.printableObject);
             // Print the doc as specified:
             docPrintJob.print( doc, attributes );
             // Wait for the terminate call of the printjob, or for error messages:
             while( this.listenForPrinterEvents() )
              {
                /* for ever */
                try{ Thread.sleep(200); } catch( Exception anyEx ){}
              }
           }
          catch( PrintException printException )
           {
             String msg = "Print error. Returning...";
             System.out.println(msg);
             JOptionPane.showMessageDialog(this.parentDialog,msg);
             printException.printStackTrace();
           }
          docPrintJob.removePrintJobListener( this );
          docPrintJob = null;

          progressWindow.setVisible(false);
          progressWindow.dispose();
        }
     }
  } // Constructor






 /**
  *   Wait for the terminate call of the printjob, or for error messages.
  *   As soon as it returns false, the calling print method
  *   can leave its listener loop, stop listening and terminate.
  */
  private boolean listenForPrinterEvents()
  { 
    boolean canTerminate = false;
    if( this.printJobEventQueue.size() > 0 )
     {
       PrintJobEvent event = (PrintJobEvent)this.printJobEventQueue.elementAt(0);
       this.printJobEventQueue.removeElementAt(0);
       final int eventReason = event.getPrintEventType();
       String reasonString = "Unknown";
       switch( eventReason )
        {
          case PrintJobEvent.DATA_TRANSFER_COMPLETE : reasonString = "DATA_TRANSFER_COMPLETE"; break;
          case PrintJobEvent.JOB_CANCELED :           reasonString = "JOB_CANCELED"; break;
          case PrintJobEvent.JOB_COMPLETE :           reasonString = "JOB_COMPLETE"; break;
          case PrintJobEvent.JOB_FAILED :             reasonString = "JOB_FAILED"; break;
          case PrintJobEvent.REQUIRES_ATTENTION :     reasonString = "REQUIRES_ATTENTION"; break;
          case PrintJobEvent.NO_MORE_EVENTS :         reasonString = "NO_MORE_EVENTS"; break;
          default:
        } // switch
       if( ( eventReason != PrintJobEvent.DATA_TRANSFER_COMPLETE  ) &&
           ( eventReason != PrintJobEvent.JOB_COMPLETE            ) &&
           ( eventReason != PrintJobEvent.NO_MORE_EVENTS          )    )
        {
          String msg = "PrinterJob sends event with reason: " + reasonString;
          System.out.println(msg);
          JOptionPane.showMessageDialog(this.parentDialog,msg);
        }
       canTerminate = ( eventReason == PrintJobEvent.NO_MORE_EVENTS );
     }
    return canTerminate; 
  } // processPrintEventQueue






 /**
  *
  * Implementation of the PrintJobListener interface.
  *
  * Called to notify the client that data has been successfully
  * transferred to the print service, and the client may free
  * local resources allocated for that data.  The client should
  * not assume that the data has been completely printed after
  * receiving this event.
  * If this event is not received the client should wait for a terminal
  * event (completed/canceled/failed) before freeing the resources.
  * @param pje the job generating this event
  */
  public void printDataTransferCompleted( PrintJobEvent pje )
  {
    this.printJobEventQueue.addElement( pje );
  }



 /**
  *
  * Implementation of the PrintJobListener interface.
  *
  * Called to notify the client that the job completed successfully.
  * @param pje the job generating this event
  */
  public void printJobCompleted( PrintJobEvent pje )
  {
    this.printJobEventQueue.addElement( pje );
  }
  


 /**
  *
  * Implementation of the PrintJobListener interface.
  *
  * Called to notify the client that the job failed to complete
  * successfully and will have to be resubmitted.
  * @param pje the job generating this event
  */
  public void printJobFailed(PrintJobEvent pje)
  {
    this.printJobEventQueue.addElement( pje );
  }


 /**
  *
  * Implementation of the PrintJobListener interface.
  *
  * Called to notify the client that the job was canceled
  * by a user or a program.
  * @param pje the job generating this event
  */
  public void printJobCanceled(PrintJobEvent pje)
  {
    this.printJobEventQueue.addElement( pje );
  }



 /**
  *
  * Implementation of the PrintJobListener interface.
  *
  * Called to notify the client that no more events will be delivered.
  * One cause of this event being generated is if the job
  * has successfully completed, but the printing system
  * is limited in capability and cannot verify this.
  * This event is required to be delivered if none of the other
  * terminal events (completed/failed/canceled) are delivered.
  * @param pje the job generating this event
  */
  public void printJobNoMoreEvents( PrintJobEvent pje )
  {
    this.printJobEventQueue.addElement( pje );
  }


 /**
  *
  * Implementation of the PrintJobListener interface.
  *
  * Called to notify the client that an error has occurred that the
  * user might be able to fix.  One example of an error that can
  * generate this event is when the printer runs out of paper.
  * @param pje the job generating this event
  */
  public void printJobRequiresAttention( PrintJobEvent pje )
  {
    this.printJobEventQueue.addElement( pje );
  }




  class PrintableDoc implements Doc
  {

    private Printable printable;

    public PrintableDoc( Printable printable_Object )
    {
      this.printable = printable_Object;
    }

    public DocFlavor getDocFlavor()
    {
      //return DocFlavor.SERVICE_FORMATTED.PAGEABLE;
      return DocFlavor.SERVICE_FORMATTED.PRINTABLE;
    }
    public DocAttributeSet getAttributes()
    {
      return null;
    }
    public Object getPrintData() throws IOException
    {
      return printable;
    }
    public Reader getReaderForText() throws IOException
    {
      return null;
    }
    public InputStream getStreamForBytes() throws IOException
    {
      return null;
    }
  } // class PrintableDoc


}
