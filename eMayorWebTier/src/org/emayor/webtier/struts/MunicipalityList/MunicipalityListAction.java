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


import org.emayor.webtier.shared.TextResourceKeys;
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
    response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

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

    // if no language is passed, keep the one from the session scope form, if available:
    if( languageParameterValue == null )
    {
      languageParameterValue = municipalityListForm.getLanguage();
    }
    
    String localLinkParameter = request.getParameter("do");
    boolean useLocalLinks = ( ( localLinkParameter != null ) &&
                              ( localLinkParameter.equals("UseLocalLinks") ) );
    
    System.out.println("----- localLinkParameter = " + localLinkParameter );
    System.out.println("----- useLocalLinks = " + useLocalLinks );
    
    // Set the language from the passed languageParameterValue and store it in the session object
    // or read it from the session object, if languageParameterValue is null.
    //
    // If useLocalLinks is set, local links are inserted for the municipalities list,
    // otherwise the global links from the MunicipalityURLMapping.properties file
    // are used.
    
    String protocol = request.getProtocol();
    // remove the version information:
    int slashPosition = protocol.indexOf("/");
    if( slashPosition >= 0 ) protocol = protocol.substring(0,slashPosition);    
    String requestURL = protocol + "://" + request.getServerName();
    int serverPortNumber = request.getServerPort();
    if( serverPortNumber != 80 )
    {
      try
      {
        requestURL = requestURL + ":" + String.valueOf(serverPortNumber);
      }
      catch( Exception ezeze )
      {
        System.out.println("***MuncipalityListAction: Unable to add portnumber to muncipaluty server links");
        ezeze.printStackTrace();
      }
    }
    requestURL = requestURL + "/eMayor/login.do";
    // Example: http://server_address:8080/eMayor/login.do?municipalityNameKey=Name.Siena&language=it
    
    System.out.println("MuncipalityListAction local login basis URL= " + requestURL);
    
    municipalityListForm.initialize( request.getSession(),
                                     languageParameterValue,
                                     useLocalLinks,
                                     requestURL );
    
    //System.out.println(">>>Current language is: " + municipalityListForm.getLanguage() );

    return mapping.findForward("showMunicipalityList");
  } // execute
  
  
  
  
} // MunicipalityListAction
