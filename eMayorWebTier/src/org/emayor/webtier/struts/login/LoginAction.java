package org.emayor.webtier.struts.login;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.emayor.webtier.shared.ProjectFileVersionInformation;
import org.emayor.webtier.shared.TextResourceKeys;



public class LoginAction extends Action
{

  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
  {
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 1);

    LoginForm loginForm = (LoginForm)form;
    System.out.println("LoginAction.execute(). Passed parameter names:");
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

    String mappingForwardName = "";
    
    if( request.isSecure() )
    {
        System.out.println("*** ");
        System.out.println("*** LoginAction: is secure SSL: Not allowed, because SSL data already read.");
        System.out.println("*** where no user feedback would be provided in the error case.");
        System.out.println("*** The login page must therefore be called with HTTP protocol to be");
        System.out.println("*** able to perform user feedback on error cases itself.");
        System.out.println("*** ");
        mappingForwardName= "showLoginURLInformation";
    }
    else
    {
      System.out.println("LoginAction: is unsecure HTTP - All conditions are ok for the login.");
      mappingForwardName= "authenticateUser";
      // File version check: Some support files of the webtier are tested
      // to have correct version. If they are not uptodate, show an error page
      // instead of continuing, because correct webtier operation is not
      // guaranteed in this case:
      ProjectFileVersionInformation fileInformation = ProjectFileVersionInformation.GetInstance();
      if( fileInformation.getFilesAreUptodate() ) // ok
      {
        if( municipalityKey != null ) // normal processing
        {
          mappingForwardName= "showLoginPage";
          // and change to the localized template.htm now, if possible:
          try
          {
            // The name key is point separated: f.ex. Name.Aachen, take the part after the point:
            StringTokenizer tok = new StringTokenizer(municipalityKey,".");
            tok.nextToken();
            String municipalityFolderName = tok.nextToken();
            loginForm.loadLocalizedHTMLTemplateForMunicipality(municipalityFolderName);
            System.out.println("Switched to localized template.htm for " + municipalityFolderName);
          }
          catch( Exception eee )
          {
            System.out.println("*** Unable to set localized template.htm.");
            eee.printStackTrace();
            System.out.println("*** -> Using global template.htm.");
          }          
        }
        else
        {
          System.out.println("LoginAction: requestedMunicipality NOT FOUND."); 
          System.out.println("->forwarding to login URL error page.");
          mappingForwardName = "showLoginURLInformation";
          languageParameterValue = loginForm.getLanguage();
          if( languageParameterValue == null ) languageParameterValue = "en";
        }
      }
      else // The webtier installation contains old files -> error page:
      {
        mappingForwardName= "showIncorrectWebTierInstallationPage";         
      }
    } // else    
    // Initialize the form with this:
    // Set the language from the passed languageParameterValue and store it in the session object
    // or read it from the session object, if languageParameterValue is null.
    loginForm.initializeAttibutes( request.getSession(),languageParameterValue,municipalityKey );
    System.out.println("LoginAction: forwards to: " + mappingForwardName );
    return mapping.findForward(mappingForwardName);
} // execute


		
	
} // LoginAction

