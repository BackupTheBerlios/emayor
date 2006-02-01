package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;


/**
 *  A html editor with inbuilt hyperlink listener
 * 
 *  Created: J.Plaz, Juli 2005
 */


import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.event.*;





public class JHTMLEditorPane extends JEditorPane
{


 private JHTMLEditorPane thisEditorPane; // reference to self



 // constructor
 public JHTMLEditorPane( final String htmlString )
 {
    super();
    thisEditorPane = this;
    try
     {
       this.setContentType("text/html");
       this.setCaretColor(Color.green);
       if( htmlString != null )
        {
          //this.setPage("file:///" + absoluteFilePathName);
          this.setText( htmlString );
        }
       this.setEditable(false);
       // feed the HyperlinkListener with the filename :
       this.addHyperlinkListener( new Hyperactive() );
     }
    catch( Exception e)
     {
       e.printStackTrace();
     }
 } // constructor










  /**
  * taken out of JEditorPane Java 1.2.2 source / was not accessible
  * ...made bigger changes
  *
  * Scroll the view to the given reference location
  * (i.e. the value returned by the <code>UL.getRef</code>
  * method for the url being displayed).  By default, this
  * method only knows how to locate a reference in an
  * HTMLDocument.  The implementation calls the
  * <code>scrollRectToVisible</code> method to
  * accomplish the actual scrolling.  If scrolling to a
  * reference location is needed for document types other
  * than html, this method should be reimplemented.
  * This method will have no effect if the component
  * is not visible.
  *
  * @param reference the named location to scroll to.
  */
  public void scrollToReference(String reference)
  {
	  Document d = getDocument();
	  if (d instanceof HTMLDocument)
    {
	    HTMLDocument doc = (HTMLDocument) d;
	    HTMLDocument.Iterator iter = doc.getIterator(HTML.Tag.A);
	    for (; iter.isValid(); iter.next())
      {
		    AttributeSet a = iter.getAttributes();
		    String nm = (String) a.getAttribute(HTML.Attribute.NAME);
		    if ((nm != null) && nm.equals(reference))
        {
		      // found a matching reference in the document.
		      try
          {
           final int startOffset = iter.getStartOffset();
           this.setSelectionColor( new Color(255,100,80) );
           this.setSelectedTextColor(Color.yellow);

           // select up to next space :
           String testString = doc.getText(startOffset,80);
           int selectionLength = 0;
           while( (testString.charAt(selectionLength) != ' ') &&
                  (testString.charAt(selectionLength) != '\n') &&
                  (testString.charAt(selectionLength) != '\t') &&
                  ( selectionLength < 78 ) )
            {
             selectionLength++;
            }

            this.requestFocus();
            this.setCaretPosition(startOffset);
            this.moveCaretPosition(startOffset+selectionLength);
 	       }
         catch (BadLocationException ble)
          {
           System.out.println("scrollToReference: BadLocationException catched.");
           getToolkit().beep();
		       }
		     } // if
	     } // for
	   } else
    {
      System.out.println("scrollToReference error : doc wasnt html");
    }
  } // scrollToReference





  
  public void publicScrollToReference(String reference)
  {
   // pass to protected method :
   this.scrollToReference(reference);
  } 







class Hyperactive implements HyperlinkListener
{
  public void hyperlinkUpdate(HyperlinkEvent e)
  {
   //System.out.println("\n1 with type: "+e.getEventType());
   if( e.getEventType() == HyperlinkEvent.EventType.ACTIVATED )
    {
       JEditorPane pane = (JEditorPane) e.getSource();
       if (e instanceof HTMLFrameHyperlinkEvent)
        {
          HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;

          //ystem.out.println("Hyperactive[1]: HTMLFrameHyperlinkEvent Target = " + evt.getTarget());

          HTMLDocument doc = (HTMLDocument)thisEditorPane.getDocument();
          doc.processHTMLFrameHyperlinkEvent(evt);
        }
       else
        {
         try
          {
             String newHTMLPathName = e.getDescription();

             /*
             System.out.println("Hyperactive[2] event type: " + e.getClass().getName() );
             System.out.println("Hyperactive[2] description= " + newHTMLPathName );
             System.out.println("Hyperactive[2] url= " + e.getURL().toString("UTF-8") );
             */

             if (newHTMLPathName.toLowerCase().endsWith(".pdf"))
             {
               newHTMLPathName = e.getURL().getFile();
               //System.out.println("Running: " + newHTMLPathName);
               // spawn pdf viewer
               String[] a = new String[]{"cmd", "/c", newHTMLPathName};
               try
               {
                 Process p = Runtime.getRuntime().exec(a);
               }
               catch (Exception ex)
               {
                 ex.printStackTrace();
               }
             }
             else
             {
               // This doesn't work generally...

               String pageAddress = newHTMLPathName;
               if( !newHTMLPathName.toLowerCase().startsWith("http:") )
                {
                  pageAddress = "file:///" + newHTMLPathName;
                }

               //ystem.out.println("Hyperactive[2]: pageAdress= " + pageAdress );

               thisEditorPane.setPage(pageAddress);
               scrollRectToVisible(new Rectangle(1,1));
               System.out.println("Hyperlink Listener: Set page to: " + pageAddress );
             }
          }
         catch( Throwable t )
          {
            System.out.println(">HTMLTemplateEditorPane: Couldn't follow " + t.getMessage());
            //t.printStackTrace();
          }
        }
    }
  } // hyperlinkUpdate
} // Hyperactive



} // JHTMLEditorPane










