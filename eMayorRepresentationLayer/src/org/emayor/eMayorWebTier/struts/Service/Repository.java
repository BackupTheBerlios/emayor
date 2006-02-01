package org.emayor.eMayorWebTier.struts.Service;

/**
 *  May 19, 2005 created, Joerg Plaz
 */

import java.util.*;


import org.apache.struts.action.ActionForm;

import org.emayor.eMayorWebTier.Utilities.TextResourceKeys;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.utclient.CVDocumentTypes;




public class Repository extends ActionForm
{

  private Vector documents = new Vector(); // of RepositoryDocument elements
  
  
  
  public Repository()
  {    
  }
  
  
 /**
  *   Called by the documentrepository.jsp
  *   for displaying the documents in html format. 
  */ 
  public RepositoryDocument[] getDocuments()
  {
    // Return the elements of the documents vector as array:
    final RepositoryDocument[] docs = new RepositoryDocument[this.documents.size()];
    this.documents.copyInto( docs );
    return docs;
  }

  /**
   *   Called by the ServiceAction
   *   for displaying the selected document in ePF format. 
   */ 
   public RepositoryDocument getDocumentAt(final int index) throws Exception
   {
   	 return (RepositoryDocument)this.documents.elementAt(index);
   }
  
  
  public void addTask( final Task task,
  		               final ServiceForm serviceForm )
  {
  	// Fill in the task's attributes into the RepositoryDocument object
  	// used by the webtier.
  	// This is the interface part between ServiceHandling and WebTier structures.
  	// Check / update compatibility issues here on both sides.
    String serviceIdentifier = "<undefined service>";
    // The following switch is based on tku's org.emayor.servicehandling.test.utils.Utils.java
    switch (task.getTaskType()) 
    {    
    case CVDocumentTypes.CV_BANK_ACCOUNT_CHANGE_REQUEST:
           serviceIdentifier = serviceForm.getTextFromResource(TextResourceKeys.ChangeAccountDataServiceKey);       
           break;
      case CVDocumentTypes.CV_FAMILY_RESIDENCE_CERTIFICATE_REQUEST:
           serviceIdentifier = serviceForm.getTextFromResource(TextResourceKeys.RequestFamilyResidenceCertificationServiceKey);       
           break;
      case CVDocumentTypes.CV_RESIDENCE_CERTIFICATE_REQUEST:
           serviceIdentifier = serviceForm.getTextFromResource(TextResourceKeys.RequestResidenceCertificationServiceKey);       
           break;
      case CVDocumentTypes.CV_RESIDENCE_DOCUMENT:
           serviceIdentifier = serviceForm.getTextFromResource(TextResourceKeys.ResidenceCertificate );       
           break;
      case CVDocumentTypes.CV_TAXES_MANAGEMENT_ACTIVATION_REQUEST:
           serviceIdentifier = serviceForm.getTextFromResource(TextResourceKeys.TaxManagementActivationRequestKey);       
           break;
      case CVDocumentTypes.CV_USER_REGISTRATION_REQUEST:
           serviceIdentifier = serviceForm.getTextFromResource(TextResourceKeys.UserRegistrationRequestKey);       
           break;
      case CVDocumentTypes.CV_NEGATIVE_RESIDENCE_CERTIFICATE_DOCUMENT:
           serviceIdentifier = serviceForm.getTextFromResource(TextResourceKeys.NegativeResidenceCertificate);       
           break;
           
      default:
        System.out.println("***  ");
        System.out.println("***  ");
        System.out.println("***  ");
        System.out.println("***  WEBTIER: inconcistency, problem with ServiceHandling.Task:");
        System.out.println("***  ");
        System.out.println("*** Repository: No custom service identifier found for");
        System.out.println("*** task.getTaskType()= " + task.getTaskType() );
        System.out.println("*** - using the task id for it.");
        System.out.println("*** The SH Task.getTaskType() method did NOT contains");
        System.out.println("*** a known identifier.");
        System.out.println("***  ");
        System.out.println("***  ");
        System.out.println("***  ");
      serviceIdentifier = task.getTaskId(); // The default, if the following doesn't find a better one
    } // switch
    
    System.out.println("");  
    System.out.println("------------------------------------------------------------");  
    System.out.println("");  
    System.out.println("title [serviceIdentifier] for new webtier task element:");  
    System.out.println("");  
    System.out.println("serviceIdentifier= " + serviceIdentifier);  
    System.out.println("task.getExtraInfo(): " + task.getExtraInfo());  
    System.out.println("task.getStatus: " + task.getStatus() );  
    System.out.println("");  
    System.out.println("");  
    System.out.println("------------------------------------------------------------");  
    System.out.println("");  
        
    /* Status: Available translated texts are:  
    public static final String Status_Completed          = "Repository.Status_Completed";
    public static final String Status_IsProcessed        = "Repository.Status_IsProcessed";
    public static final String Status_ProblemsHaveArised = "Repository.Status_ProblemsHaveArised";
    
    public static final String Status_NoRemarks             = "Repository.Status_NoRemarks";
    public static final String Status_RemarksAboutEmailInfo = "Repository.Status_RemarksAboutEmailInfo";
    */
    
    String statusKey = TextResourceKeys.Status_Completed;
    String taskStatus = task.getStatus();
    
    // BPEL uses right now:  "closed" und "open" and "error" so:
    String status    = task.getStatus(); // default: just use this plain text
    String remarks   = serviceForm.getTextFromResource(TextResourceKeys.Status_NoRemarks);
    if( taskStatus.equals("open") )
    {
      status  = serviceForm.getTextFromResource(TextResourceKeys.Status_IsProcessed);
      remarks = serviceForm.getTextFromResource(TextResourceKeys.Status_NoRemarks);
    }
    else
    if( taskStatus.equals("closed") )
    {
      status  = serviceForm.getTextFromResource(TextResourceKeys.Status_Completed);
      remarks = serviceForm.getTextFromResource(TextResourceKeys.Status_NoRemarks);
    }
    else
    if( taskStatus.equals("error") )
    {
      status  = serviceForm.getTextFromResource(TextResourceKeys.Status_ProblemsHaveArised);
      remarks = serviceForm.getTextFromResource(TextResourceKeys.Status_RemarksAboutEmailInfo);
    }

    // Insert the requester name at the beginning of the remarks:
    String requesterName = task.getRequester();
    if( requesterName != null )
    {
      remarks = serviceForm.getTextFromResource(TextResourceKeys.Name_Requester) +
                " : " + requesterName + "   " + remarks;

    }
    
    Calendar startTime = task.getIncoming();
    // enddate:
    Calendar endTime = task.getDeadline();

    String xmlDocument = task.getXMLDocument();
    String documentResponse = task.getDocumentResponse();
     
    String municipalityNameKey = serviceForm.getMunicipalityNameKey();
    String language = serviceForm.getLanguage();
    // The documentIndex (= array index ) is used in the JSP's for
    // identifying the document, which the user wants to download.
    // (without having to add scriptlet code there)
    int documentIndex = this.documents.size();
  	RepositoryDocument doc = new RepositoryDocument( serviceIdentifier,municipalityNameKey,language,
                                                     status,statusKey,remarks,
                                                     startTime,endTime,
                                                     xmlDocument,
                                                     documentResponse,
													 documentIndex,
                                                     task );
    // sort them after time:
    this.addDocumentSortedByTime( doc );    
    // after having changed the order, we must update the indices again:
    for( int i=0; i < this.documents.size(); i++ )
    {
      RepositoryDocument theDocument = (RepositoryDocument)this.documents.elementAt(i);
      theDocument.setDocumentIndex(i);
    }
    
  } // addTask
  
  
  
  
  
  private void addDocumentSortedByTime( final RepositoryDocument document )
  {
     synchronized( this.documents )
     {
        long documentTime = document.getUTFTimeOfRequest();
        int i=0;
        long registeredDocTime = 0;
        int elementIndexForNewDocument = -1;
        while( i < this.documents.size() )
        {
          registeredDocTime = ((RepositoryDocument)this.documents.elementAt(i)).getUTFTimeOfRequest();
          if( registeredDocTime < documentTime ) // take this slot
          {
            elementIndexForNewDocument = i;
            break;
          }
          i++;
        }
        // Now either insert or add:
        // If the elementIndexForNewDocument is -1, one can add. This occurs
        // if either the vector is empty, or all entries are newer.
        // Otherwise insert:
        if( elementIndexForNewDocument == -1 )
        {
          this.documents.addElement( document );
        }
        else
        {
          this.documents.insertElementAt( document, elementIndexForNewDocument );        
        }
     } // sync  
  }
  
  
  
  
  
} // RepositoryForm

