package org.emayor.RepresentationLayer.ClientTier.Utilities.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.Vector;

import java.beans.*;

import org.emayor.RepresentationLayer.ClientTier.LanguageProperties;

  /**
   *   A small calendar popup window, which
   *   can be optionally used for entering
   *   date inputs.
   * 
   *   It is instanciated by objects of class
   *   EditableDateTypeInputComponent in this package.
   * 
   *   It uses the methods of the DateInputListener object passed
   *   to the constructor for writing back date element selections.
   *   DateInputListener is an interface, which must be implemented
   *   by an object, which wants to display this popup frame.
   *  
   *   1.2.06   jpl    for the finetuning version
   */



public class CalendarPopupFrame extends JDialog
{


  private DateInputListener dateInputListener;

  private JComboBox monthList;
  
  private LanguageProperties languageProperties;
  
  private String calendarTitle; // in natural language
  private String[] dayNames;
  private String[] monthNames;
  
  private JTextField yearInputField;
  
  private JTable dayTable;
  
  private Frame parentFrame;
  
  private int yearValue = 2000; // in user coords
  private int monthValue = 1;   // in user coords
  private int dayValue   = 1;   // in user coords
  
  
  private ReadonlyDateTypeComponent selectedDate;
  
 /**
  * Constructor
  * @param dateInputListener  used for writing changes to
  * @param theParentFrame     base for modality (here: only keep the frame on top)
  */
  public CalendarPopupFrame( final DateInputListener _dateInputListener,
                             final Frame theParentFrame,
                             final int initialDayValue,
                             final int initialMonthValue,
                             final int initialYearValue,
                             final LanguageProperties _languageProperties,
                             final ImageIcon okIcon )
  {
    super(theParentFrame,true); // must be modal == block input on parent
    // Set the parentFrame as parent. This will keep the parentframe
    // in the background, while this window is visible.
    this.dateInputListener = _dateInputListener;
    this.parentFrame = theParentFrame;
    this.languageProperties = _languageProperties;
    
    this.yearValue  = initialYearValue;
    this.monthValue = initialMonthValue;
    this.dayValue   = initialDayValue;
    

    // Read language specific texts:
    this.calendarTitle = this.languageProperties.getTextFromLanguageResource("Calendar.Name");    
    String dayList = this.languageProperties.getTextFromLanguageResource("Calendar.DayList");
    String monthList = this.languageProperties.getTextFromLanguageResource("Calendar.MonthList");
    // Fallback to english defaults on errors:
    if( dayList.equals("Calendar.DayList") ) // languageProperties just inserts the key on errors
    {
     dayList = "Mon,Tue,Wed,Thu,Fri,Sat,Sun";
    }
    if( monthList.equals("Calendar.MonthList") ) // languageProperties just inserts the key on errors
    {
      monthList = "Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec";
    }
    this.dayNames = this.parseTextList(dayList);
    this.monthNames = this.parseTextList(monthList);
    
    
    this.getContentPane().setLayout( new BorderLayout() );
    final int fontSize = UIManager.getFont("Label.font").getSize();
    int gap = 1 + fontSize/8;                     

    JPanel framePanel = new JPanel( new BorderLayout(1,1) );
    Border ob = BorderFactory.createRaisedBevelBorder();
    Border ib = BorderFactory.createEmptyBorder(gap,gap,gap,gap);
    framePanel.setBorder( BorderFactory.createCompoundBorder(ob,ib) );
        
    this.setTitle( this.calendarTitle );
    
    int bSize = 1 + fontSize/2;
    GradientPanel mainWrapPanel =
       new GradientPanel( new BorderLayout(bSize,bSize),
                          GradientPanel.ApplyUpperLeftCornerHighLight,
                          GradientPanel.StrongGradientStrength,
                          GradientPanel.ActiveTitleForeground  );
    framePanel.add( mainWrapPanel, BorderLayout.CENTER );

    
    String closeText = "    " + 
                       this.languageProperties.getTextFromLanguageResource("Form.Done") +
                       "    ";
    JSenseButton closeButton = new JSenseButton(closeText,true);
    
    ob = BorderFactory.createEtchedBorder();        
    ib = BorderFactory.createEmptyBorder(gap,gap,gap,gap);
    closeButton.setBorder( BorderFactory.createCompoundBorder(ob,ib) );
    closeButton.setFont( UIManager.getFont("Tree.font") );
    if( okIcon != null ) closeButton.setIcon(okIcon);
    closeButton.addActionListener( new ActionListener()
     {                                                                                           
       public void actionPerformed( ActionEvent e )
       {
         setVisible(false);
         dateInputListener = null; // help the GC
       }
     });
    JPanel buttonPanel = new JPanel( new FlowLayout(FlowLayout.CENTER,fontSize/4,0) );
    buttonPanel.setOpaque(false);
    buttonPanel.add(closeButton);
    mainWrapPanel.add( buttonPanel, BorderLayout.SOUTH );
        
    JPanel inputPanel = this.createCalendarInputPanel();
    inputPanel.setOpaque(false);
    mainWrapPanel.add( inputPanel, BorderLayout.NORTH );
         
    // the current date info panel:
    this.selectedDate = new ReadonlyDateTypeComponent();
    this.selectedDate.setTextFromNumbers(this.yearValue,this.monthValue,this.dayValue);
    JPanel infoPanel = new JPanel( new FlowLayout() );
    infoPanel.add( this.selectedDate );
    mainWrapPanel.add( infoPanel, BorderLayout.CENTER );
         
    this.getContentPane().add( framePanel, BorderLayout.CENTER );
    this.pack();    
  } // constructor
  
    
  
  
 /**
  *  Called from the constructor.
  *  This method creates the calendar input panel
  *  with all required functionality.
  * 
  *  @return the calendar input panel 
  */ 
  private JPanel createCalendarInputPanel()
  {
    final JPanel inputPanel = new JPanel( new BorderLayout(0,0) );
    inputPanel.setOpaque(false);
      
    this.monthList = new JComboBox(this.monthNames);
    this.monthList.setSelectedIndex( monthValue-1 );
    this.monthList.addItemListener( new ItemListener()
    {
      public void itemStateChanged(ItemEvent e)
      {
        int selectedIndex = monthList.getSelectedIndex();
        monthValue = selectedIndex + 1;
        updateCalendar();
        inputPanel.updateUI();
        dateInputListener.setMonth(monthValue);
        //System.out.println("UPDATE month to: " + monthValue );        
      }
    });
    
    this.yearInputField = new JTextField( String.valueOf(yearValue) );
    this.yearInputField.addActionListener( new ActionListener()
    {
      public void actionPerformed( ActionEvent e )
      {
        try
        {
          yearValue = Integer.parseInt( yearInputField.getText() );
          updateCalendar();
          inputPanel.updateUI();
          dateInputListener.setYear(yearValue);
          //System.out.println("UPDATE year to: " + yearValue );
        }
        catch( Exception eee)
        {
          eee.printStackTrace();
        }
      }
    });
    
    
    JPanel topPanel = new JPanel( new FlowLayout() );
    topPanel.setOpaque(false);
    topPanel.add(this.monthList);
    topPanel.add(this.yearInputField);
                
    DefaultTableModel model = new DefaultTableModel(6,7)
    {
        public boolean isCellEditable(int row, int column) 
        {
          return false;
        }
    };    
    this.dayTable = new JTable(model);
    this.dayTable.setCellSelectionEnabled(true);
    
    this.dayTable.addMouseListener( new MouseAdapter()
    {
      public void mousePressed(MouseEvent e) 
      {
        int col = dayTable.getSelectedColumn();
        int row = dayTable.getSelectedRow();
        //System.out.println("Selected col/row " + col + " / " + row);
        String dayText = dayTable.getValueAt(row,col).toString();
        try
        {
          dayValue = Integer.parseInt(dayText);
          updateCalendar();
          dateInputListener.setDay(dayValue);
        }
        catch( Exception eee )
        {
          // picked value outside day of month - nop
          //eee.printStackTrace();
        }
      }
    });
    
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer()
    {
      public void setValue(Object value) 
      {
        if( value instanceof String )
        {
          String s = (String)value;
          if( s.length()== 0 )
          {
            super.setBackground( new Color(190,190,190) );
          }
          else
          {
            super.setBackground( new Color(255,255,255) );
          }
        }
        super.setValue(value);
      }    
    };


    this.dayTable.setGridColor( new Color(140,140,180) );
    for( int col=0; col < this.dayTable.getColumnCount(); col++)
     {
      final TableColumn tc = this.dayTable.getColumnModel().getColumn(col);
      tc.setCellRenderer( renderer );
      tc.setCellEditor( null );
     } // for
    
    
    for( int i=0; i < this.dayTable.getColumnCount(); i++ )
    {
      String cn = this.dayTable.getColumnName(i);      
      TableColumn tc = this.dayTable.getColumn(cn);
      String headerValue = ( i < this.dayNames.length ) ? this.dayNames[i] : "-";
      tc.setHeaderValue( headerValue );
    }  
   
    this.updateCalendar();
    
    JScrollPane scrollPane = new JScrollPane( this.dayTable );
    
    inputPanel.add( topPanel,BorderLayout.NORTH );
    inputPanel.add( this.dayTable.getTableHeader(),BorderLayout.CENTER );
    inputPanel.add( this.dayTable,BorderLayout.SOUTH );
    return inputPanel;
  }



  private void updateCalendar()
  {    
    this.setDayListFor(yearValue,monthValue);
    if( this.selectedDate != null )
    {
      this.selectedDate.setTextFromNumbers(this.yearValue,this.monthValue,this.dayValue);
      this.selectedDate.updateUI();
    }  
  } // updateCalendar

 
     
  
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

   
   

  private String[] setDayListFor( int year, int month )
  {
    Calendar calendar = Calendar.getInstance();
    try
    {
      // first make all tablecells empty:
      for( int row=0; row < this.dayTable.getRowCount(); row++ )
      {
        for( int col=0; col < this.dayTable.getColumnCount(); col++ )
        {
          this.dayTable.setValueAt("",row,col);        
        }
      }
      // update:
      calendar.set(year,month-1,1);  // year, month (zero based), 1 = first day of month      
      int dayOfWeek = calendar.get( Calendar.DAY_OF_WEEK );
      int dayNameIndex = this.getDayOfWeekNameIndex( dayOfWeek );
      int rowIndex = 0;
      int colIndex = dayNameIndex;   
      int dayNumber = 1;
      while( calendar.get(Calendar.MONTH) == month-1 ) // until the month changes
      {
        String dayName = this.getDayNameForIndex(dayNameIndex); // week cyclic 0..6
        this.dayTable.setValueAt( String.valueOf(dayNumber),rowIndex,colIndex );

        //System.out.println("dayname= " + dayName + " has day number: " + dayNumber );
        
        // increment all values accordingly:
        calendar.add(Calendar.DATE,1);
        colIndex++;
        if( colIndex > 6 ){ colIndex = 0; rowIndex++;  }
        dayNameIndex++;
        if( dayNameIndex > 6 ) dayNameIndex = 0;
        dayNumber++;        
      } // while
    }
    catch( Exception eee )
    {
      eee.printStackTrace();
    }
    return null;
  } // calculateDayList
  
  
  
  
  
  private String getDayNameForIndex(final int dayNameIndex)
  {
    String name = "-";
    if( ( dayNameIndex >= 0) && (dayNameIndex < 7) )
    {
      name = this.dayNames[dayNameIndex];
    }
    return name;
  }
 
  
  
  
 /**
  *  Converts Calendar dayOfWeek into
  *  name in natural language arrayindex of this.dayNames .
  */ 
  private int getDayOfWeekNameIndex( final int dayOfWeek )
  {
      int index = 0;
      switch( dayOfWeek )
      {
        case Calendar.MONDAY:    index = 0; break;
        case Calendar.TUESDAY:   index = 1; break;
        case Calendar.WEDNESDAY: index = 2; break;
        case Calendar.THURSDAY:  index = 3; break;
        case Calendar.FRIDAY:    index = 4; break;
        case Calendar.SATURDAY:  index = 5; break;
        case Calendar.SUNDAY:    index = 6; break;
        default: ;
      }
      return index;
  }
 
  
  public void showFrameAbove( Point basisLocation )
  {
    final int fontSize = UIManager.getFont("Label.font").getSize();
    double frameHeight = this.getPreferredSize().getHeight();
    double frameWidth = this.getPreferredSize().getWidth();
    int xp = basisLocation.x - (int)frameWidth/2;
    int yp = basisLocation.y - (int)frameHeight -fontSize;
    this.setLocation( new Point(xp,yp) );
    this.setVisible(true);
  }

  
  
  
} // CalendarPopupFrame


