package org.emayor.webtier.struts.MunicipalityList;



/**
 *
 *  May 12, 2005 created, Joerg Plaz
 * 
 */


import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.log4j.Logger;


import org.emayor.webtier.shared.Utilities;


public class MunicipalityListAction extends Action
{
 
  private Logger log = Logger.getLogger(MunicipalityListAction.class);

  
  public MunicipalityListAction()
  {
  	log.debug("Webtier started (MunicipalityListAction)");
  }
	
	
  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
  {
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 1);

    MunicipalityListForm municipalityListForm = (MunicipalityListForm)form;
 
  	Utilities.PrintLn("MunicipalityListAction.execute(). Passed parameter names:");
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
      Utilities.PrintLn("> name= " + parameterName + "  value= " + parameterValue );
      count++;  
    }
    Utilities.PrintLn(">" + count + " parameters.");

    // Set the language from the passed languageParameterValue and store it in the session object
    // or read it from the session object, if languageParameterValue is null.
    municipalityListForm.initialize( request.getSession(),languageParameterValue );
    
    //System.out.println(">>>Current language is: " + municipalityListForm.getLanguage() );

    return mapping.findForward("showMunicipalityList");
  } // execute
  
  
  
  
} // MunicipalityListAction
