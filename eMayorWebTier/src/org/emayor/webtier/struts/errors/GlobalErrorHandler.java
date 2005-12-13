package org.emayor.webtier.struts.errors;


import java.net.URLEncoder;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.struts.config.*;


public class GlobalErrorHandler extends ExceptionHandler  
{

	
  public ActionForward execute( Exception ex,
                                ExceptionConfig ae,
                                ActionMapping mapping,
                                ActionForm formInstance,
                                HttpServletRequest request,
                                HttpServletResponse response) throws ServletException 
  {
    String errorDescription = "Null exception";
    if( ex != null ) errorDescription = ex.getMessage(); // Now errorDescription can be null, so:
    if( errorDescription == null ) errorDescription = "Null exception";    
    // limit its size, because we add it to the http get parameter:
    if( errorDescription.length() > 86 ) errorDescription = errorDescription.substring(0,83) + "...";
    // urlencode it:
    String parameter = "No information available.";
    try
    {
      parameter = URLEncoder.encode(errorDescription, "UTF-8");
    }
    catch( Exception eee )
    {
     // no action to be taken here. Parameter default is already set above.
    }
    String forwardName = "/error.do?description=" + parameter; // "showErrorGlobal";
    System.out.println("$$$");
  	System.out.println("$$$ GlobalErrorHandler.execute() called.");
    System.out.println("$$$");
    System.out.println("$$$ forward to: " + forwardName);
    System.out.println("$$$");
    System.out.println("$$$");

    //ex.printStackTrace();
    return new ActionForward(forwardName);
  } // execute
	
  
} // GlobalErrorHandler


