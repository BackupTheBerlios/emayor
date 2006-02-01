package org.emayor.eMayorWebTier.Utilities;



/**
 *  A bidirectional date format, which works on the milliseconds long
 *  attribute returned by System.currentTimeMillis().
 *
 *  The String representation is produced by toString("UTF-8").
 *
 *  This date additionally interprets DateNotSetTime as date-not-set:
 *  so FormattedDate(DateNotSetTime) means date not set and toString("UTF-8") in this case
 *  will return an empty string.
 *
 *
 * 19.5.05  jplaz
 * 
 */


import java.util.*;
import java.text.SimpleDateFormat;
 

public class FormattedDate
{

// Note: Update the equals() method, when attributes are added here.

private Date date;

// The time used for dates without a time set.
// One could use Long.MIN_VALUE for this, but a smaller amount is
// better for the comparators used in the tables, which must return an int
// value for comparison. They create differences in long format and divide
// it by 100000. The below defined number this way is shifed to
// 70 * 365 * 24 * 36, which is inside the int format too.
// The absolute date, which corresponds to this DateNotSetTime, is
// around the 1. jan. 1900, which never is used otherwise.
public final static long DateNotSetTime =  - 70L * 365L * 24L * 3600L * 1000L;
                               
             
public FormattedDate( long milliseconds )
{
  this.date = new Date(milliseconds);
} // Constructor


/**
*  datePattern is something like "dd.MM.yy" german or
*  "MM.dd.yyyy" english                    
*/              
public FormattedDate( final String dateString,
                      final String datePattern )
{                                    
  this.date = new Date(DateNotSetTime); // default == date not set
  this.setDateByScanningText(dateString,datePattern);
}


public String to_String()  
{
  if( this.date.getTime() == DateNotSetTime ) // means date not set
   {
     return "";
   }                                                                        
  else
   {
     final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
     return formatter.format(this.date);
   }                                  
} // toString




public String getDateAsString( final String datePattern )  
{
  if( this.date.getTime() == DateNotSetTime ) // means date not set
   {
     return "";
   }                                                                        
  else
   {
     final SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
     return formatter.format(this.date);
   }                                  
} // getDateAsString





/**
*  Parses the passed date string of the form dd.mm.yy
*  Returns false, it it failed.
*  datePattern is something like "dd.MM.yy" german or
*  "MM.dd.yyyy" english
*/
public boolean setDateByScanningText( final String rawDateString, 
                                      final String datePattern )
{ 
  boolean success = true;
  if( rawDateString.equals("") ) // date is not set, this is allowed
   {
     this.date = new Date(DateNotSetTime);
   }
  else
   {
     String dateString = rawDateString.trim();
     try
      {
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        this.date = formatter.parse( dateString );
        // A specific behaviour: For inputs like 02.03.x, the formatter doesn't
        // raise an exception, but the created date is an empty string, which
        // is not correct, so catch this too and return false in this case.
        if( this.to_String().length() == 0 )
         {
           new Throwable().printStackTrace();    
           System.out.println("***FormattedDate: End condition: Rejected input: $" + dateString + "$" +
                              " with datePattern= " + datePattern );
           success = false;
         }
      }
     catch( Exception e )
      {
        System.out.println("***FormattedDate: Rejected input: $" + dateString + "$" );
        success = false;
      }     
   }
  return success;
} // setDateByScanningText




public void setTime( long millisecs )
{                        
  this.date.setTime(millisecs);
}



public long getTime()
{                        
  return this.date.getTime();
}


public boolean equals( FormattedDate otherDate )
{
  return ( this.getTime() == otherDate.getTime() );
}
                    


public static void main( String[] args )
{
  FormattedDate fd = new FormattedDate( FormattedDate.DateNotSetTime );
  System.out.println("date not set long value is = " + FormattedDate.DateNotSetTime );
  System.out.println("date not set means millisecs = " + fd.getTime() );
  System.out.println("date not set text representation= empty string=  " + fd.to_String() );

}



} // FormattedDate



