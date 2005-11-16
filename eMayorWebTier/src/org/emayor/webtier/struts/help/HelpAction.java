package org.emayor.webtier.struts.help;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.emayor.webtier.shared.TextResourceKeys;


public class HelpAction extends Action 
{

	
  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
  {
    HelpForm helpForm = (HelpForm)form;
    System.out.println("HelpAction.execute(). Passed parameter names:");
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

    // Get the helptopic key from the request:
    String helpTopicKey = request.getParameter(TextResourceKeys.HelpTopicKeyTag);
    System.out.println(">>>helpTopicKey= " + helpTopicKey );


    // Initialize the form with this:
    // Set the language from the passed languageParameterValue and store it in the session object
    // or read it from the session object, if languageParameterValue is null.
    helpForm.initializeAttibutes( request.getSession(),languageParameterValue,helpTopicKey );

    //System.out.println(">>>Current language is: " + helpForm.getLanguage() );
    return mapping.findForward("showHelp");
} // execute
	
  
  
	
} // HelpAction



