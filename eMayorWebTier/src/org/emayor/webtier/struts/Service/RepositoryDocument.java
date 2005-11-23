package org.emayor.webtier.struts.Service;



/**
 *  Holds attributes for one document entry in
 *  the repository.
 * 
 *  May 19, 2005 created, Joerg Plaz
 */


import org.emayor.webtier.shared.FormattedDate;
import java.io.Serializable;
import java.util.Calendar;
import org.emayor.servicehandling.kernel.Task;



public class RepositoryDocument implements Serializable
{
  
  
  private String municipalityNameKey = "";
  private String language = "en";
  
  private String documentTitle = "";
  private String documentStatus  = "";
  private String documentStatusKey = "";
  private String documentRemarks = "";
  private Calendar startTime;
  private Calendar endTime;
  private int documentIndex = 0;
   
  private String xmlDocument = null;
  private String documentResponse = null;
  
  // The associated kernel task object:
  private Task task = null;
  
  
  public RepositoryDocument( final String _documentTitle,
                             final String _municipalityNameKey,
                             final String _language,
                             final String _documentStatus,
							 final String _documentStatusKey,
                             final String _documentRemarks,
                             final Calendar _startTime,
                             final Calendar _endTime,
                             final String _xmlDocument,
                             final String _documentResponse,
                             final int _documentIndex,
                             final Task _task )
  {    
    this.municipalityNameKey = _municipalityNameKey;
    this.language = _language;
    
    this.documentTitle = _documentTitle;
    this.documentStatus  = _documentStatus;
    this.documentStatusKey = _documentStatusKey;
    this.documentRemarks = _documentRemarks;
    this.startTime   = _startTime;
    this.endTime     = _endTime;
    this.xmlDocument = _xmlDocument;
    this.documentResponse = _documentResponse;
    this.documentIndex = _documentIndex;
    this.task = _task;
  } // constructor
  

  
  public long getUTFTimeOfRemoval()
  {
    return this.endTime.getTimeInMillis();
  }

  
  
  public long getUTFTimeOfRequest()
  {
    return this.startTime.getTimeInMillis();
  }
  
  
  
  public String getDateOfRemoval()
  {
    int day    = this.endTime.get(Calendar.DAY_OF_MONTH);  // 1..30/31
    int month  = this.endTime.get(Calendar.MONTH) + 1;
    int year   = this.endTime.get(Calendar.YEAR);    
    int hour24 = this.endTime.get(Calendar.HOUR_OF_DAY);     // 0..23
    int min    = this.endTime.get(Calendar.MINUTE);          // 0..59
    int sec    = this.endTime.get(Calendar.SECOND);          // 0..59
    int ms     = this.endTime.get(Calendar.MILLISECOND);     // 0..999
      
    StringBuffer date = new StringBuffer();
    date.append( String.valueOf(day) );
    date.append( "." );
    date.append( String.valueOf(month) );
    date.append(".");
    date.append( String.valueOf(year) );
    date.append("   ");
    if( hour24 < 10 ) date.append("0");  // always 2 columns
    date.append( String.valueOf(hour24) );
    date.append(":");
    if( min < 10 ) date.append("0");  // always 2 columns
    date.append( String.valueOf(min) );
  
    return date.toString();
  }
  

  
  
  
  public String getDateOfRequest()
  {
    int day    = this.startTime.get(Calendar.DAY_OF_MONTH);  // 1..30/31
    int month  = this.startTime.get(Calendar.MONTH) + 1;
    int year   = this.startTime.get(Calendar.YEAR);    
    int hour24 = this.startTime.get(Calendar.HOUR_OF_DAY);     // 0..23
    int min    = this.startTime.get(Calendar.MINUTE);          // 0..59
    int sec    = this.startTime.get(Calendar.SECOND);          // 0..59
    int ms     = this.startTime.get(Calendar.MILLISECOND);     // 0..999
      
    StringBuffer date = new StringBuffer();
    date.append( String.valueOf(day) );
    date.append( "." );
    date.append( String.valueOf(month) );
    date.append(".");
    date.append( String.valueOf(year) );
    date.append("   ");
    if( hour24 < 10 ) date.append("0");  // always 2 columns
    date.append( String.valueOf(hour24) );
    date.append(":");
    if( min < 10 ) date.append("0");  // always 2 columns
    date.append( String.valueOf(min) );
  
    return date.toString();
  }
  
  
  public String getDocumentRemarks()
  {
    return documentRemarks;
  }
  
  
  public String getDocumentStatus()
  {
    return documentStatus;
  }

  
  public String getDocumentStatusKey()
  {
    return documentStatusKey;
  }
  
  
  public String getDocumentTitle()
  {
    return this.documentTitle;  
  }
  
  

  
  public String getXmlDocument()
  {
    return this.xmlDocument;
  }
  
  
  public String getDocumentResponse()
  {
    return this.documentResponse;
  }


  
  public int getDocumentIndex()
  {
  	return this.documentIndex;
  }
  
  
 /**
  *  Return the ServiceHandling task object associated
  *  to this repository object.
  */ 
  public Task getTask()
  {
    return this.task;
  }
  
  
} // RepositoryDocument
