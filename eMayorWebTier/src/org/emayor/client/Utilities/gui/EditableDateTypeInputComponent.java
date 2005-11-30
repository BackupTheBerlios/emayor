package org.emayor.client.Utilities.gui;

/**
 *   The input component for editable entries which
 *   have the schema type DateType.
 * 
 *   It consists of 3 textfields for
 *   year, month and day.
 *
 *   It wraps the contained complexity (makes it transparent)
 *   and behaves like a textfield holding a
 *   simple string.
 *
 *   jpl  
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.Vector;
import java.util.StringTokenizer;



public class EditableDateTypeInputComponent extends JPanel implements DocumentListener
{


    private int numberOfColumns = 0;   // default is zero -> pack it.
    
    private Vector registeredDocumentListeners = new Vector(); // of DocumentListener's
    
    
    // The text fields for day, month and year:
    private JTextField year;
    private JTextField month;
    private JTextField day;
    
    // The associated labels:
    private JLabel yearLabel;
    private JLabel monthLabel;
    private JLabel dayLabel;
    
    
   /**
    * Constructor
    * @param yearText:   in the specific language
    * @param monthText:  in the specific language
    * @param dayText:    in the specific language
    */ 
    public EditableDateTypeInputComponent( String yearText, 
                                           String monthText, 
                                           String dayText )
    {
      
      // textfields:
      this.year = new JTextField();
      this.month = new JTextField();
      this.day = new JTextField();
      
      this.year.setColumns(4);
      this.month.setColumns(2);
      this.day.setColumns(2);
      
      // Use specific language for the labels:
      this.dayLabel   = new JLabel(dayText);  
      this.monthLabel = new JLabel("   " + monthText);  
      this.yearLabel  = new JLabel("   " + yearText);  
    
      this.setLayout( new FlowLayout() );

      this.add( this.dayLabel   );
      this.add( this.day        );
      
      this.add( this.monthLabel );
      this.add( this.month      );
      
      this.add( this.yearLabel  );
      this.add( this.year       );
      
      this.year.getDocument().addDocumentListener( this );
      this.month.getDocument().addDocumentListener( this );
      this.day.getDocument().addDocumentListener( this );
      
      this.setOpaque(false);  // transparent
      this.setBorder( BorderFactory.createEmptyBorder(0,0,0,0) ); // no border
    } // constructor


    


    public void setText( final String newText )
    {
      try // assume correct structure: YYYY-MM-DD:
      {
        StringTokenizer tok = new StringTokenizer(newText,"-");
        if( tok.hasMoreTokens() )
        {
          String yearNumber = tok.nextToken();
          this.year.setText(yearNumber);
          if( tok.hasMoreTokens() )
          {
            String monthNumber = tok.nextToken();
            this.month.setText( monthNumber );
            if( tok.hasMoreTokens() )
            {
              String dayNumber = tok.nextToken();
              this.day.setText( dayNumber );
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
        System.out.println("*** hs not the form YYYY-MM-DD");
        System.out.println("***");
        this.day.setText( newText );
      }
    }
    
    
    public String getText()
    {
      StringBuffer actualContent = new StringBuffer();
      actualContent.append( this.year.getText() );
      actualContent.append( "-");
      actualContent.append( this.month.getText() );
      actualContent.append( "-");
      actualContent.append( this.day.getText() );
      return actualContent.toString();
    }


    
    public void setColumns( final int num_Colors )
    {
      this.numberOfColumns = num_Colors;
    }
    
    
    
    
    public void addDocumentListener( DocumentListener docListener )
    {
      this.registeredDocumentListeners.addElement( docListener );
    }

    
    public void removeDocumentListener( DocumentListener docListener )
    {
      synchronized( this.registeredDocumentListeners )
      {
        int i=0;
        while( i < this.registeredDocumentListeners.size() )
        {
          DocumentListener registeredListener = (DocumentListener)this.registeredDocumentListeners.elementAt(i);
          if( registeredListener == docListener ) // reference comparison
          {
            this.registeredDocumentListeners.removeElementAt(i);
          }
          else
          {
            i++;
          }
        }
      }
    }

    
    /**
     * Gives notification that there was an insert into the document.  The 
     * range given by the DocumentEvent bounds the freshly inserted region.
     *
     * @param e the document event
     */
    public void insertUpdate(DocumentEvent e)
    {
      synchronized( this.registeredDocumentListeners )
      {
        for( int i=0; i < this.registeredDocumentListeners.size(); i++ )
        {
          DocumentListener docListener = (DocumentListener)this.registeredDocumentListeners.elementAt(i);
          docListener.insertUpdate(e);
        }
      }
    }

    /**
     * Gives notification that a portion of the document has been 
     * removed.  The range is given in terms of what the view last
     * saw (that is, before updating sticky positions).
     *
     * @param e the document event
     */
    public void removeUpdate(DocumentEvent e)
    {
      synchronized( this.registeredDocumentListeners )
      {
        for( int i=0; i < this.registeredDocumentListeners.size(); i++ )
        {
          DocumentListener docListener = (DocumentListener)this.registeredDocumentListeners.elementAt(i);
          docListener.removeUpdate(e);
        }
      }
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     *
     * @param e the document event
     */
    public void changedUpdate(DocumentEvent e)
    {
      synchronized( this.registeredDocumentListeners )
      {
        for( int i=0; i < this.registeredDocumentListeners.size(); i++ )
        {
          DocumentListener docListener = (DocumentListener)this.registeredDocumentListeners.elementAt(i);
          docListener.changedUpdate(e);
        }
      }
    }
    
    
   
    
    
} // DateTypeInputComponent


