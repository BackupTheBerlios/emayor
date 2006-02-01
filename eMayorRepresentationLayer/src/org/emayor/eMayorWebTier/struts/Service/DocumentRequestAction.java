package org.emayor.eMayorWebTier.struts.Service;

/**
 *  May 20, 2005 created, Joerg Plaz
 */

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



public class DocumentRequestAction extends Action
{

  
  public ActionForward execute( ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response )
  {
    DocumentRequestForm documentForm = (DocumentRequestForm)form; 
    System.out.println("DocumentRequestAction.execute(). Passed parameter names:");
    Enumeration namesEnumeration = request.getParameterNames();
    int count = 0;
    String languageParameterValue = null;
    while( namesEnumeration.hasMoreElements() )
    {
      String parameterName = namesEnumeration.nextElement().toString();
      String parameterValue = request.getParameter(parameterName);
      if( parameterName.equals("language") ) // Take over the language, if its present:
      {
      	languageParameterValue = parameterValue; // Take over the new language in the initialize() call below.
      }
      System.out.println("> name= $" + parameterName + "$  value= $" + parameterValue + "$" );
      count++;  
    }
    System.out.println(">" + count + " parameters.");
    // End Test

    System.out.println("DocumentRequestAction: download request doc to do...");
    // Initialize the form:
    documentForm.initialize( request.getSession(),languageParameterValue );
    // Talk with the SH:
    this.sendDocumentDownloadRequestToServiceHandling( documentForm );
    
  return mapping.findForward("showdocument");
} // execute




/** 
*  Call the SH.
*/ 
private void sendDocumentDownloadRequestToServiceHandling(final DocumentRequestForm documentForm )
{
  // Fill the documentForm with data from SH:
  
  System.out.println("DocumentRequestAction: Send document request to SH: to do...");
}
  
  
  
  
} // DocumentRequestAction

