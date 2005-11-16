package org.emayor.webtier.struts.Municipality;

import java.security.cert.X509Certificate;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
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
public class MunicipalityAction extends Action
{

  // With this, the number of failed authentications for the user
  // in the current session is counted:
  private int failedAuthenticationTries = 0;

  
  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
  {
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 1);

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
     System.out.println("***  MunicipalityAction: requestedMunicipality NOT FOUND."); 
    

    // User login and authentication if not already done.
    // Just check, if this user has an ASID or not, and get it if there is no ASID.
    // This also set the user ROLE.
    String forwardName = "showMunicipalityServices";
    boolean isAuthenticated = this.getIsUserAuthenticated( request, request.getSession() );    
    if( !isAuthenticated )
    {
      HttpSession session = request.getSession(true);
      this.doAuthenticateUser( request, session );
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
    municipalityForm.initializeAttibutes( request.getSession(),languageParameterValue,
    		                              requestedMunicipality.getMunicipalityNameKey(),
                                          requestedMunicipality.getServices() );

    //System.out.println(">>>Current language is: " + municipalityForm.getLanguage() );
    return mapping.findForward(forwardName);
} // execute

  
  

  
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
                                     final HttpSession session )
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
                System.out.println("Session: Set the max inactive interval to 900 seconds (15 min)");
                session.setMaxInactiveInterval(900);
            
                // Set the ASID in the session object -> means: user is seen as authenticated:
                session.setAttribute("ASID", asid);
                // Debug output:
                System.out.println(">> ");
                System.out.println(">> ");
                System.out.println(">> WEBTIER: AUTHENTICATION successful.");
                System.out.println(">> ");
                System.out.println("ASID= " + asid );
              
                if( access.getUserProfile(asid).getPEUserProfile().getUserRole().equalsIgnoreCase("Citizen") )
                {
                  session.setAttribute("ROLE", "citizen");
                  System.out.println(">> ROLE= citizen");
                } 
                else
                {
                  session.setAttribute("ROLE", "civilservant");
                  System.out.println(">> ROLE= civilservant");
                }
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

