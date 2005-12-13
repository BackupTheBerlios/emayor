package org.emayor.webtier.struts.Municipality;

import java.security.cert.X509Certificate;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.utclient.ServiceLocator;

import org.emayor.webtier.struts.Municipality.MunicipalityForm;
import org.emayor.webtier.municipalities.MunicipalitiesManager;
import org.emayor.webtier.municipalities.Municipality;
import org.emayor.webtier.shared.TextResourceKeys;



/**
 *  Action associated to municipality.jsp  
 *
 *  May 13, 2005 created, Joerg Plaz
 * 
 */
public class MunicipalityAction extends DispatchAction
{

  // With this, the number of failed authentications for the user
  // in the current session is counted:
  private int failedAuthenticationTries = 0;

  
  public ActionForward showIndex( ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response )
  {
    response.setHeader("Cache-Control","no-cache"); //forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //causes the proxy cache to see the page as "stale"
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility


    String authenticationType = request.getAuthType();
    System.out.println("MunicipalityAction: authenticationType = " + authenticationType );

    System.out.println("MunicipalityAction: request attributes: ");
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
    
    System.out.println("%%%%%  ");
    System.out.println("%%%%%  ");
    System.out.println("%%%%%  Session has type: " + request.getSession().getClass().getName() );
    System.out.println("%%%%%  ");
    System.out.println("%%%%%  ");
    System.out.println("%%%%%  ");
    
        
    MunicipalityForm municipalityForm = (MunicipalityForm)form;
    System.out.println("MunicipalityAction.execute(). Passed parameter names:");
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
    // If no key has been passed, set the cached one from the form:
    if( municipalityKey == null )
    {
      municipalityKey = municipalityForm.getNameKeyOfMunicipality();    
    }
    // if its found now, switch to localized template.htm:
    if( municipalityKey != null )
    {
        // and change to the localized template.htm now, if possible:
        try
        {
          // The name key is point separated: f.ex. Name.Aachen, take the part after the point:
          StringTokenizer tok = new StringTokenizer(municipalityKey,".");
          tok.nextToken();
          String municipalityFolderName = tok.nextToken();
          municipalityForm.loadLocalizedHTMLTemplateForMunicipality(municipalityFolderName);
          System.out.println("Switched to localized template.htm for " + municipalityFolderName);
        }
        catch( Exception eee )
        {
          System.out.println("*** Unable to set localized template.htm.");
          eee.printStackTrace();
          System.out.println("*** -> Using global template.htm.");
        }              
    } // if
    // If no language has been passed, set the cached on from the form:
    if( languageParameterValue == null )
    {
      languageParameterValue = municipalityForm.getLanguage();    
    }
    System.out.println(">>>municipalityKey= " + municipalityKey );
    System.out.println(">>>language= " + languageParameterValue );
    
    // Retrieve the municipality with the name
    // passed in the request:
    MunicipalitiesManager mwa = MunicipalitiesManager.GetInstance();
    Municipality requestedMunicipality = mwa.getMunicipalityByKey(municipalityKey);
    
    if( requestedMunicipality == null )
    {
      System.out.println("***  MunicipalityAction: requestedMunicipality NOT FOUND.");
      // Now we trigger the global error handler (see emayor/webtier/struts/errors/GlobalErrorHandler.java)
      // and pass the reason, which then is displayed on the client:
      throw new RuntimeException("The selected municipality is not available in eMayor.");
    }
    // Remark: Do not remove the above if statement. The requestedMunicipality is
    // accessed below and must be valid then.
    
    
    // User login and authentication if not already done.
    // Just check, if this user has an ASID or not, and get it if there is no ASID.
    // This also set the user ROLE.
    String forwardName = "showMunicipalityServices";
    boolean isAuthenticated = this.getIsUserAuthenticated( request, request.getSession() );    
    if( !isAuthenticated )
    {
      HttpSession session = request.getSession(true);
      this.doAuthenticateUser( request, session, requestedMunicipality );
      // If the authentication has succeeded, the following should return true:
      isAuthenticated = this.getIsUserAuthenticated( request, request.getSession() );
      System.out.println("MunicipalityAction: Authentification performed. Success: " + isAuthenticated );          
      if( !isAuthenticated )
      {
        this.failedAuthenticationTries++; // increment the counter
        // and inform the model - it will be retrieved there by the associated jsp.
        municipalityForm.setNumberOfFailedAuthenticationTries(this.failedAuthenticationTries);
        forwardName = "showAuthenticationInformation";
      }
    }
    else
    {
      System.out.println("MunicipalityAction: No authentification required. User already is authenticated.");    
    }
    
    // Initialize the form with this:
    // Set the language from the passed languageParameterValue and store it in the session object
    // or read it from the session object, if languageParameterValue is null.
    String serverName = request.getServerName();
    int serverPortNumber = request.getServerPort();
    municipalityForm.initializeAttibutes( request.getSession(),languageParameterValue,
    		                              requestedMunicipality.getMunicipalityNameKey(),
                                          requestedMunicipality.getServices(),
                                          serverName,
                                          serverPortNumber  );
    //System.out.println(">>>Current language is: " + municipalityForm.getLanguage() );
    return mapping.findForward(forwardName);
  } // execute


  
  /**
   *  Logout the user (CS or CT).
   * 
   *  Execution method of this DispatchAction
   */    
   public ActionForward logout( ActionMapping mapping,
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
      this.checkLogout(request);

      String sslID = (String)request.getAttribute("javax.servlet.request.ssl_session");
      if( sslID != null )
      {
        System.out.println("-|-|-  sslID exists and is: " + sslID);
      }
      else
      {
        System.out.println("-|-|-  sslID does NOT exist.");      
      }
      
      
      String actionForwardName = "logout_SwitchToHTTP"; // logout_SwitchToHTTP is the first step for logging out
      return mapping.findForward( actionForwardName );
   } // logout    
   
   

   
   /**
    *  Logout the user (CS or CT) if required.
    */    
    private void checkLogout( HttpServletRequest request )
    {
      if( this.getIsUserAuthenticated( request, request.getSession() ) )
      {
         System.out.println("MunicipalityAction.LOGOUT: User was authenticated -> deauthenticating user");

         HttpSession session = request.getSession(false);
         String asid = (String)session.getAttribute("ASID");
         String ssid = (String)session.getAttribute("SSID");
         System.out.println("MunicipalityAction.LOGOUT: logging out user with ASID= " + asid);        
         try
         {        
           ServiceLocator serviceLocator = ServiceLocator.getInstance();
           AccessManagerLocal access = serviceLocator.getAccessManager();              
           boolean sessionStopped = access.stopAccessSession(asid);
           if( sessionStopped )
           {
             System.out.println("MunicipalityAction.LOGOUT: Access session stopped successfully.");
           }
           else
           {
             System.out.println("*** MunicipalityAction.LOGOUT: Access session could not be stopped...");
           }
           System.out.println("MunicipalityAction: Trying to remove access references...");
           // Remove all access references:
           access.remove();
           session.removeAttribute("ASID");
           System.out.println("MunicipalityAction.LOGOUT: done...");
         }
         catch( Exception e )
         {
           System.out.println("*** MunicipalityAction.LOGOUT: Failure:");
           e.printStackTrace();
         }
      }
      else
      {
         System.out.println("MunicipalityAction.LOGOUT: User was not authenticated -> NOP");     
      }
 
      System.out.println("Logout check: Stored login url:   " + 
            (String)request.getSession().getAttribute("LoginPageURL") +
            "   for nice redirecting to here again after the user has logged out.");
    
    
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


   
   
   /**
    *  User Authentication and Login.
    *  Uses code from the ServiceHandling's LoginProcessor.
    *  Gets ASID and user ROLE if successfull.
    */
    private void doAuthenticateUser( final HttpServletRequest request,
                                     final HttpSession session,
                                     final Municipality municipality )
    {
      // Check if an ID for an AccessSession exists in this session:
      boolean isAuthenticated = ( session.getAttribute("ASID") != null );
      if( !isAuthenticated )
      {
        try 
        {
          System.out.println("MunicipalityAction.doAuthenticateUser(): Initializing the SH.");
          ServiceLocator serviceLocator = ServiceLocator.getInstance();
          AccessManagerLocal access = serviceLocator.getAccessManager();
          String asid = access.createAccessSession();
          System.out.println("got asid= " + asid);
          // This code is taken from the Login processor:
          System.out.println("MunicipalityAction.doAuthenticateUser(): Trying to authenticate the user.");
          X509Certificate[] certificates = (X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate");
          if( certificates != null )
          {
            System.out.println("MunicipalityAction.doAuthenticateUser(): Certificates.length = " + certificates.length);
            for( int i = 0; i < certificates.length; i++ )
              System.out.println("MunicipalityAction.doAuthenticateUser(): >>certificates[" + i + "] = " + certificates[i].toString());
//            Caution: The above call depends from the Default Platform Character Encoding 
            if( certificates.length > 0 )
            {
              boolean userIsAuthenticated = access.startAccessSession( asid, certificates );
              System.out.println("MunicipalityAction.doAuthenticateUser(): User authenticated: " + userIsAuthenticated);
              
              /* --------- TEMPORARY: make it fail for simulating logins with invalid certificates
              if( (userIsAuthenticated) && ( failedAuthenticationTries < 4 ) )
              {
                userIsAuthenticated = false;
              }
              ------------ */
              
              if( userIsAuthenticated )
              {              
                
                // Set the ASID in the session object -> means: user is seen as authenticated:
                session.setAttribute("ASID", asid);
                // Debug output:
                System.out.println(">> ");
                System.out.println(">> ");
                System.out.println(">> WEBTIER: AUTHENTICATION successful.");
                System.out.println(">> ");
                System.out.println("ASID= " + asid );
              
                boolean isCitizenRole = access.getUserProfile(asid).getPEUserProfile().getUserRole().equalsIgnoreCase("Citizen");
                String userRole = (isCitizenRole) ? "citizen" : "civilservant";
                session.setAttribute("ROLE", userRole);
                System.out.println(">> ROLE= " + userRole);
                                
                // Try to set the session timeout from the value read from the municipality.properties file,
                // depending on the user role:
                int sessionTimeoutInSeconds = 3600; // 1 hour default
                String sessionTimeOutKey = (isCitizenRole) ? Municipality.MunicipalitySessionTimeoutForCitizen :
                                                             Municipality.MunicipalitySessionTimeoutForCivilServant;
                String timeoutString = municipality.getMunicipalityPropertyValue(sessionTimeOutKey);
                if( timeoutString != null )
                {
                  try
                  {
                    sessionTimeoutInSeconds = Integer.parseInt(timeoutString);
                  }
                  catch( Exception anyEx )
                  {
                    System.out.println(">> The session timeout could not be set from property file. Using default= " +
                                       sessionTimeoutInSeconds + " seconds.");
                  }
                }
                session.setMaxInactiveInterval(sessionTimeoutInSeconds);            
                System.out.println(">> Session timeout for role " + userRole + " is set to " + 
                                   sessionTimeoutInSeconds + " seconds.");
                
              }
              else
              {
                System.out.println(">> ");
                System.out.println(">> WEBTIER: AUTHENTICATION has returned a negative result.");
                System.out.println(">> ");
              }
              System.out.println(">> ");
              System.out.println(">> ");
              access.remove(); // AccessmanagerLocal extends EJBLocalObject, which contains remove()
            }
            else
            {
                System.out.println("MunicipalityAction.doAuthenticateUser(): *** Login is not accepted.");
            }
          }
          else
          {
            System.out.println("MunicipalityAction.doAuthenticateUser(): *** Certificates are NULL. Login is not accepted.");
          }
        } 
        catch( Exception ex)
        {
          ex.printStackTrace();
        }
      } // if not authenticated
      else
      {
        System.out.println("MunicipalityAction.doAuthenticateUser(): User already has been authenticated.");
      }
    } // doAuthenticateUser
   
   
   
   
  
} // MunicipalityAction

