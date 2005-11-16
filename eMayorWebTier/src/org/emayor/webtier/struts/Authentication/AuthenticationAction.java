package org.emayor.webtier.struts.Authentication;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.emayor.webtier.shared.TextResourceKeys;




public class AuthenticationAction extends Action
{

  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
  {
    AuthenticationForm authenticationForm = (AuthenticationForm)form;
    System.out.println("AuthenticationAction.execute(). Passed parameter names:");
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

    String mappingForwardName= "authenticateUser";
    
    if( request.isSecure() )
    {
      System.out.println("AuthenticationAction: is secure SSL");
      mappingForwardName= "authenticateUser";
    }
    else
    {
      System.out.println("AuthenticationAction: is unsecure HTTP");
      mappingForwardName= "authenticateUser";
    }
    
    if( municipalityKey == null )
    {
      mappingForwardName="showErrorGlobal";
      System.out.println("***  AuthenticationAction: requestedMunicipality NOT FOUND."); 
    }
    
    // Initialize the form with this:
    // Set the language from the passed languageParameterValue and store it in the session object
    // or read it from the session object, if languageParameterValue is null.
    authenticationForm.initializeAttibutes( request.getSession(),languageParameterValue,municipalityKey );

    //System.out.println(">>>Current language is: " + authenticationForm.getLanguage() );
    System.out.println("AuthenticationAction: forwards to: " + mappingForwardName );

    return mapping.findForward(mappingForwardName);
} // execute







} // AuthenticationAction

