package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;

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
 *   It implements the DateInputListener interface methods
 *   which it uses for communication with the CalendarPopupFrame
 *   which the user can optionally open for entering
 *   dates with mouseclicks.
 *
 *   UoZurich, jpl  
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import java.util.Vector;
import java.util.StringTokenizer;

import org.emayor.RepresentationLayer.ClientTier.LanguageProperties;


public class EditableDateTypeInputComponent extends JPanel 
                                            implements DocumentListener,DateInputListener
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
    
    // The button, which pops up the CalendarPopupFrame
    // for optionally entering dates with mouseclicks:
    private JSenseButton calendarButton;
    
    // keep the instance of an opened popup frame here
    // with this we can deny to open multiple frames -
    // just one frame should be opened.
    private CalendarPopupFrame calendarPopupFrame = null;

    private LanguageProperties languageProperties;
    
    private ImageIcon okIcon;
    
    
   /**
    * Constructor
    * @param yearText:   in the specific language
    * @param monthText:  in the specific language
    * @param dayText:    in the specific language
    */ 
    public EditableDateTypeInputComponent( final String yearText, 
                                           final String monthText, 
                                           final String dayText,
                                           final LanguageProperties _languageProperties,
                                           final ImageIcon _okIcon )
    {
      this.languageProperties = _languageProperties;
      this.okIcon = _okIcon;
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
      
      final int fontSize = UIManager.getFont("Label.font").getSize();
      int gap = 1 + fontSize/8;                     

      this.add( Box.createHorizontalStrut(fontSize) );
      
      this.calendarButton = new JSenseButton("Calendar",true);
      Border ob = BorderFactory.createEtchedBorder();        
      Border ib = BorderFactory.createEmptyBorder(gap,gap,gap,gap);
      this.calendarButton.setBorder( BorderFactory.createCompoundBorder(ob,ib) );
      this.calendarButton.setFont( UIManager.getFont("Tree.font") );
      this.add( this.calendarButton );
      this.calendarButton.addActionListener( new ActionListener()
      {
        public void actionPerformed( ActionEvent e )
        {
          showCalendarPopupFrame();
        }
      });
      
      this.setOpaque(false);  // transparent
      this.setBorder( BorderFactory.createEmptyBorder(0,0,0,0) ); // no border
    } // constructor



    
    
   /**
    *  Opens the calendar popup frame.
    *  This can be used for entering a date using
    *  the mouse.
    */ 
    private void showCalendarPopupFrame()
    {
      // Actually the popup frame is designed as JDialog,
      // which keeps focus, so it should not be possible
      // to open a second one from the parent frame, but
      // for security we check:
      if( this.calendarPopupFrame != null )
      {
        // a popup already exist, so just bring this one to front:
        this.calendarPopupFrame.setVisible(false);
        this.calendarPopupFrame = null;
      }
        
      final Frame appletParentFrame = JOptionPane.getFrameForComponent(this);
      // Create the calendar popup frame and pass the instance of this object
      // as DateInputListener to the constructor. The Calendar popup frame
      // uses this for writing changes back to this object.
      int dayValue = 1;
      int monthValue = 1;
      int yearValue = 2000;
      try{ dayValue   = Integer.parseInt( this.day.getText()   ); } catch( Exception e1 ){}
      try{ monthValue = Integer.parseInt( this.month.getText() ); } catch( Exception e2 ){}
      try{ yearValue  = Integer.parseInt( this.year.getText()  ); } catch( Exception e3 ){}
      
      this.calendarPopupFrame = new CalendarPopupFrame( this,appletParentFrame,
                                                        dayValue,
                                                        monthValue,
                                                        yearValue,
                                                        this.languageProperties,
                                                        this.okIcon );
      // Show the popup right below the calendar button:
      final int fontSize = UIManager.getFont("Label.font").getSize();
      int buttonX = this.calendarButton.getLocation().x;
      int buttonY = this.calendarButton.getLocation().y;
      // convert to appletParentFrame coordsystem:
      Point frameOffset = SwingUtilities.convertPoint(this.calendarButton.getParent(),
                                                      buttonX,buttonY,appletParentFrame );
      
      
      Point frameLocation = appletParentFrame.getLocationOnScreen();
      int popupX = frameLocation.x + frameOffset.x;
      int popupY = frameLocation.y + frameOffset.y;
      Point popupLocation = new Point(popupX,popupY);      
      this.calendarPopupFrame.showFrameAbove(popupLocation);        
    } // showCalendarPopupFrame

    

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
    
    
    
   /**
    *   DateInputListener interface method
    */ 
    public void setDay( int dayValue )
    {
      String dayText = Integer.toString(dayValue);
      if( dayText.length() == 1 ) dayText = "0" + dayText;
      this.day.setText( dayText );
    }
    
    
   /**
    *   DateInputListener interface method
    */ 
    public void setMonth( int monthValue )
    {
      String monthText = Integer.toString(monthValue);
      if( monthText.length() == 1 ) monthText = "0" + monthText;
      this.month.setText( monthText );
    }
    
    
   /**
    *   DateInputListener interface method
    */ 
    public void setYear( int yearValue )
    {
      this.year.setText( Integer.toString(yearValue) );    
    }
   
    
    
} // DateTypeInputComponent


