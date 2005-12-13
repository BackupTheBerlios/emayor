package org.emayor.webtier.struts.errors;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.emayor.webtier.shared.TextResourceKeys;

public class ErrorPageAction extends Action 
{

	
	
  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
  {
    ErrorPageForm errorPageForm = (ErrorPageForm)form;
    System.out.println("ErrorPageAction.execute(). Passed parameter names:");
    Enumeration namesEnumeration = request.getParameterNames();
    int count = 0;
    String languageParameterValue = null;
    while( namesEnumeration.hasMoreElements() )
    {
      String parameterName = namesEnumeration.nextElement().toString();
//    Caution: The above call depends from the Default Platform Character Encoding
      
      String parameterValue = request.getParameter(parameterName);
      if( parameterName.equals("language") ) // Take over the language, if its present:
      {
      	languageParameterValue = parameterValue; // Take over the new language in the initialize() call below.
      }
      System.out.println("> name= " + parameterName + "  value= " + parameterValue );
      count++;  
    }
    System.out.println(">" + count + " parameters.");
    // End Test
  	
    // Note: This method here usually gets called, when classfiles with syntax errors
    //       are packaged or if exceptions are thrown by webtier classes.
    //       It should never be called on a working system, maybe
    //       there are possibilities to force this, like system instabilities,
    //       one never knows... so:    
    String errorDescription = "<p>Specific description:<p>";
    // add specific description if available:
    String specificDescription = request.getParameter("description");
    if( specificDescription == null ) 
    {
      specificDescription = "No specific description available.";
    }
    errorDescription = errorDescription + specificDescription;
        
    // Initialize the form:
    errorPageForm.initialize( request.getSession(),languageParameterValue,errorDescription );
  	  	
    return mapping.findForward("showErrorPage");
  } // execute
  
  
	
	
} // ErrorPageAction



