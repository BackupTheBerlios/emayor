package org.emayor.webtier.struts.login;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.emayor.webtier.shared.ProjectFileVersionInformation;
import org.emayor.webtier.shared.TextResourceKeys;

import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.utclient.ServiceLocator;


public class LoginAction extends Action
{

  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
  {
    response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
    
    String authenticationType = request.getAuthType();
    System.out.println("LoginAction: authenticationType = " + authenticationType );
       
    System.out.println("LoginAction: request attributes: ");
    Enumeration enumeration = request.getAttributeNames();
    while( enumeration.hasMoreElements() )
    {
      String attributeName = (String)enumeration.nextElement();
      Object attributeObject = request.getAttribute(attributeName);
      if( attributeObject != null )
      {
        System.out.println("attribute name:" + attributeName +
                           " with object of type " +
                           attributeObject.getClass().getName() );
      }
      else
      {
        System.out.println("attribute name:" + attributeName +
                " with NULL object.");
      }
    }

    String sslID = (String)request.getAttribute("javax.servlet.request.ssl_session");
    if( sslID != null )
    {
      System.out.println("-|-|-  sslID exists and is: " + sslID);
    }
    else
    {
      System.out.println("-|-|-  sslID does NOT exist.");      
    }

    // if the session contains credentials and/or ssl data
    // remove it:
    System.out.println("LoginAction: checkLogout() starts");
    this.checkLogout( request );
    System.out.println("LoginAction: checkLogout() has ended.");
        
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
    // if no language has been passed, keep the one from the session scope form, if available:
    if( languageParameterValue == null )
    {
      languageParameterValue = loginForm.getLanguage();
    }
    // Get the requested municipalityname from the request:
    String municipalityKey = request.getParameter(TextResourceKeys.MunicipalityNameKeyTag);
    // if no municipalityKey has been passed, keep the one from the session scope form, if available:
    if( municipalityKey == null )
    {
      municipalityKey = loginForm.getMunicipalityNameKey();
    }  
    System.out.println(">>>municipalityKey= " + municipalityKey );
    System.out.println(">>>language= " + languageParameterValue );

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

    // A preparation for a nice redirection to this login page again, after
    // this user has logged out follows here.
    // Because the logout.jsp is based in another actionforward object and
    // invalidates the session (which struts itself belongs to), we make
    // a special case, leave the model 2 approach for the logout.jsp and put this URL
    // directly to the session object, from where it is read by the (model 1) logout.jsp:
    String loginPageURL = HttpUtils.getRequestURL(request).toString();
    String queryString = request.getQueryString();
    if( queryString != null ) loginPageURL = loginPageURL + "?" + queryString;
    request.getSession().setAttribute("LoginPageURL",loginPageURL); 
    
    System.out.println("Stored login url:   " + 
                       (String)request.getSession().getAttribute("LoginPageURL") +
                       "   for nice redirecting to here again after the user has logged out.");
    
    // Initialize the form with this:
    // Set the language from the passed languageParameterValue and store it in the session object
    // or read it from the session object, if languageParameterValue is null.
    loginForm.initializeAttibutes( request.getSession(),languageParameterValue,municipalityKey );

    // The loginform will return MunicipalityNameIsUndefined, if there
    // never has been set a valid name before. If this is the case,
    // the passed municipalityKey on the page call either does not exist
    // or is invalid, so we trigger the Global Error Handler
    // by raising an exception:
    if( loginForm.getNameOfMunicipality().equals(LoginForm.MunicipalityNameIsUndefined) )
    {
      String errorInformation = "";
      if( municipalityKey != null ) errorInformation += municipalityKey + ": ";
      errorInformation += "The selected municipality is not available in eMayor.";
      // Trigger our Global Error Handler:
      throw new RuntimeException(errorInformation);
    }

    System.out.println("LoginAction: forwards to: " + mappingForwardName );
    return mapping.findForward(mappingForwardName);
} // execute



  
  /**
   *  Logout the user (CS or CT) if required.
   */    
   private void checkLogout( HttpServletRequest request )
   {
     if( this.getIsUserAuthenticated( request, request.getSession() ) )
     {
        System.out.println("LOGIN.LOGOUT: User was authenticated -> deauthenticating user");

        HttpSession session = request.getSession(false);
        String asid = (String)session.getAttribute("ASID");
        String ssid = (String)session.getAttribute("SSID");
        System.out.println("LOGIN.LOGOUT: logging out user with ASID= " + asid);        
        try
        {        
          ServiceLocator serviceLocator = ServiceLocator.getInstance();
          AccessManagerLocal access = serviceLocator.getAccessManager();              
          boolean sessionStopped = access.stopAccessSession(asid);
          if( sessionStopped )
          {
            System.out.println("LOGIN.LOGOUT: Access session stopped successfully.");
          }
          else
          {
            System.out.println("*** LOGIN.LOGOUT: Access session could not be stopped...");
          }
          System.out.println("LOGOUT: Trying to remove access references...");
          // Remove all access references:
          access.remove();
          session.removeAttribute("ASID");
          System.out.println("LOGIN.LOGOUT: done...");
        }
        catch( Exception e )
        {
          System.out.println("*** LOGIN.LOGOUT: Failure:");
          e.printStackTrace();
        }
     }
     else
     {
        System.out.println("LOGIN.LOGOUT: User was not authenticated -> NOP");     
     }
   } // checkLogout
  
   
   
   
   /**
    *  Check if user credentials are available
    */
    private boolean getIsUserAuthenticated( final HttpServletRequest request,
                                    final HttpSession session )
    {
      // Check if an ID for an AccessSession exists in this session:
      boolean isAuthenticated = ( session.getAttribute("ASID") != null );
      return isAuthenticated;
    }   

    
    
/* --------------
   public ActionForward checkLogout( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
   {     
      response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
      response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
      response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
      response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility

      System.out.println(" ");
      System.out.println("--------------- MunicipalityAction.logout() --------------");

      MunicipalityForm municipalityForm = (MunicipalityForm)form;

      System.out.println(" ");
      System.out.println(" ");
      System.out.println(" CAUTION!!!!");
      System.out.println(" ");
      System.out.println(" Session is NOT invalidated. ASID/SSID remain.");
      System.out.println(" OUTCOMMENTED TEMPORARY");
      System.out.println(" ");
      System.out.println(" ");

      
      String actionForwardName = "showServiceError";
      if( this.getIsUserAuthenticated( request, request.getSession() ) )
      {
          HttpSession session = request.getSession(false);
          String asid = (String)session.getAttribute("ASID");
          String ssid = (String)session.getAttribute("SSID");
          System.out.println("LOGOUT: logging out user with ASID= " + asid);
          
          
          try
          {        
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            AccessManagerLocal access = serviceLocator.getAccessManager();              
            boolean sessionStopped = access.stopAccessSession(asid);
            if( sessionStopped )
            {
              System.out.println("LOGOUT: Access session stopped successfully.");
            }
            else
            {
              System.out.println("*** LOGOUT: Access session could not be stopped...");
            }
            System.out.println("LOGOUT: Trying to remove access references...");
            // Remove all access references:
            access.remove();
            System.out.println("LOGOUT: done...");
          }
          catch( Exception e )
          {
            System.out.println("*** LOGOUT: Failure:");
            e.printStackTrace();
          }
          finally
          {
            // Invalidate the session in any case:
            session.invalidate();
            System.out.println("LOGOUT: The session has been invalidated.");                  
          }
          
          actionForwardName = "logout_SwitchToHTTP";               
      }
      else
      {
          System.out.println("Logout: Session already had expired.");
          
          // Invalidate the session in any case:
          HttpSession session = request.getSession(false);
          if( session != null )
          {
            session.invalidate();
            System.out.println("LOGOUT: The session has been invalidated.");                          
          }
          
          actionForwardName = "logout_SwitchToHTTP";    
      }                
      return mapping.findForward( actionForwardName );
   } // logout    
-------- */  
  
  
  
	
} // LoginAction

