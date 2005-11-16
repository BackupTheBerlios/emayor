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
  private long dateOfRequest = 0;
  private long dateOfRemoval = 0;
  private int documentIndex = 0;
   
  private String xmlDocument = null;
  private String documentResponse = null;
  private Calendar deadline = null;
  
  // The associated kernel task object:
  private Task task = null;
  
  
  public RepositoryDocument( final String _documentTitle,
                             final String _municipalityNameKey,
                             final String _language,
                             final String _documentStatus,
							 final String _documentStatusKey,
                             final String _documentRemarks,
                             final long _dateOfRequest,
                             final long _dateOfRemoval,
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
    this.dateOfRequest   = _dateOfRequest;
    this.dateOfRemoval   = _dateOfRemoval;
    this.xmlDocument = _xmlDocument;
    this.documentResponse = _documentResponse;
    this.documentIndex = _documentIndex;
    this.task = _task;
  } // constructor
  

  
  public String getDateOfRemoval()
  {
    FormattedDate date = new FormattedDate( this.dateOfRemoval );
    return date.getDateAsString("dd.MM.yyyy");
  }
  
  
  public String getDateOfRequest()
  {
    FormattedDate date = new FormattedDate( this.dateOfRequest );
    return date.getDateAsString("dd.MM.yyyy");
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


  public Calendar getDeadline()
  {
    return this.deadline;
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
