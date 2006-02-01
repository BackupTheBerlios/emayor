package org.emayor.eMayorWebTier.struts.ClientSoftwareCheck;

  /**
   *  Software Check and Update Form
   *
   *  Dec 2005  created jpl
   */

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.emayor.eMayorWebTier.Utilities.TextResourceKeys;


public class SoftwareCheckAction extends DispatchAction
{

  public ActionForward softwareCheck( ActionMapping mapping,
                                      ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response )
  {
    response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
        
    SoftwareCheckForm softwareCheckForm = (SoftwareCheckForm)form;
    System.out.println("SoftwareCheckAction.check(). Passed parameter names:");
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
      System.out.println("> name= " + parameterName + "  value= " + parameterValue );
      count++;  
    }
    System.out.println(">" + count + " parameters.");

    // Get the requested municipalityname from the request:
    String municipalityKey = request.getParameter(TextResourceKeys.MunicipalityNameKeyTag);

    System.out.println(">>>municipalityKey= " + municipalityKey );

    String mappingForwardName= "softwareCheck";
    
    if( request.isSecure() )
    {
      System.out.println("SoftwareCheckAction: is secure SSL");
      mappingForwardName= "softwareCheck";
    }
    else
    {
      System.out.println("SoftwareCheckAction: is unsecure HTTP");
      mappingForwardName= "softwareCheck";
    }
    
    if( municipalityKey == null )
    {
      mappingForwardName="showErrorGlobal";
      System.out.println("***  SoftwareCheckAction: requestedMunicipality NOT FOUND."); 
    }

    // We need the login page URL as site target, if the user cancels
    // a software installation or update process:
    String loginPageURL = (String)request.getSession().getAttribute("LoginPageURL");
    // null is not allowed - make it empty in this case:
    if( loginPageURL == null ) loginPageURL = "";
    
    
    
    // Initialize the form with this:
    // Set the language from the passed languageParameterValue and store it in the session object
    // or read it from the session object, if languageParameterValue is null.
    softwareCheckForm.initializeAttibutes( request.getSession(),
                                           languageParameterValue,
                                           municipalityKey,
                                           loginPageURL );

    //System.out.println(">>>Current language is: " + authenticationForm.getLanguage() );
    System.out.println("SoftwareCheckAction: forwards to: " + mappingForwardName );

    return mapping.findForward(mappingForwardName);

  } // softwareCheck 

  
  
  
  public ActionForward getMD5CheckSumList( ActionMapping mapping,
                                           ActionForm form,
                                           HttpServletRequest request,
                                           HttpServletResponse response )
  {
    response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
    return mapping.findForward("getMD5CheckSumList");  
  } // getMD5CheckSum
  
  
  

  
  
  public ActionForward getClientFile( ActionMapping mapping,
                                      ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response )
  {
    response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

    
    
    // Set the virtual filepath in the form, so that
    // on request of the JSP, the asociated file is sent to the client:
    String virtualFilePath = request.getParameter("VirtualFilePath");
    String forwardIdentifier = "getClientFile";
    if( virtualFilePath == null )
    {
      System.out.println("*** SoftwareCheckAction.getClientFile():");
      System.out.println("*** Missing parameter: VirtualFilePath");      
      // trigger the error handler:
      throw new RuntimeException("Missing parameter: VirtualFilePath");
    }
    
    
    
    //return mapping.findForward(forwardIdentifier);
    
    
    /* Test code without JSP call: */
    System.out.println("SoftwareCheckAction.getClientFile(): TEST CODE starts");

    ClientSoftwareAdministration ca = ClientSoftwareAdministration.getInstance(0);
    byte[] fileBytes = ca.getFileWithVirtualPath(virtualFilePath);
    
    response.setContentType("application/binary");
    //response.setHeader("Content-disposition", sbContentDispValue.toString());
    response.setContentLength( fileBytes.length );
    try
    {
      ServletOutputStream sos;
      sos = response.getOutputStream();
      sos.flush(); // this line made a big difference.
      sos.write( fileBytes,0,fileBytes.length );
      sos.flush();
      sos.close();
    }
    catch( Exception eee )
    {
      eee.printStackTrace();
    }
    return null;
  } // getMD5CheckSum
  

  
  
} // SoftwareCheckAction



