package org.emayor.eMayorWebTier.struts.Municipality;


import javax.servlet.http.HttpSession;


import org.emayor.eMayorWebTier.Utilities.*;
import org.emayor.eMayorWebTier.municipalities.*;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.ServicesInfo;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.kernel.ServiceInfo;


/**
 *
 *  May 13, 2005 created, Joerg Plaz
 * 
 */
public class MunicipalityForm extends ExtendedActionForm
{
  
  private String municipalityNameKey = "undefined_key";
  private MunicipalityService[] services = null;

  private int numberOfFailedAuthenticationTries = 0;
  
  // serverName and serverPortNumber are used to create
  // the URL for the login page which is accessed when the user logs out
  // for relogin
  private String serverName = null;
  private int serverPortNumber = 80;

  
  
  public MunicipalityForm()
  {
  	super();
  	System.out.println("%%");
  	System.out.println("%% MunicipalityForm INSTANCE CREATED.");
  	System.out.println("%%");
  } // constructor
  

  
  
 /**  
  * Called by the associated action, which passes the
  * municipality which it has resolved from the http request.
  */ 
  public void initializeAttibutes( final HttpSession session,
                                   final String languageParameterValue,
  		                           final String _municipalityNameKey,
                                   final MunicipalityService[] _services,
                                   final String _serverName,
                                   final int _serverPortNumber )
  {
  	super.initialize(session,languageParameterValue); // language initialization

  	this.municipalityNameKey = _municipalityNameKey;
    this.services = _services;

    this.serverName = _serverName;
    this.serverPortNumber = _serverPortNumber;
    
  	Utilities.PrintLn(">>MunicipalityForm.initialize() starts. (Accessing SH Accessmanager)");
	try 
	{
      /* ----- Start of code taken from the ServiceHandling's WelcomeProcessor */
      ServiceLocator serviceLocator = ServiceLocator.getInstance();
      AccessManagerLocal access = serviceLocator.getAccessManager();
      // The number "-1001" means the user id is not used.
      ServicesInfo servicesInfo = access.listAvailableServices("-1001");
      /* ----- End of code taken from the ServiceHandling's WelcomeProcessor */

      // Print it to the console:
  	  ServiceInfo[] infos = servicesInfo.getServicesInfo();
  	  Utilities.PrintLn("-------- Start List Of Available Services -------");
      for( int i = 0; i < infos.length; i++ ) 
      {
		ServiceInfo info = infos[i];
		String serviceName = info.getServiceName();
		String serviceDescription = info.getServiceDescription();
		String serviceId = info.getServiceId();
		Utilities.PrintLn("SH service: Name= " +
				           serviceName + 
						  "  description= " +
						  serviceDescription +
						  "  Id= " +
						  serviceId );
      }
  	  Utilities.PrintLn("Number of services: " + infos.length );
  	  Utilities.PrintLn("-------- End List Of Available Services -------");
            
	
	} 
	catch( Exception ex ) 
	{
      ex.printStackTrace();
	}
  	Utilities.PrintLn(">>MunicipalityForm.initialize() has ended.");	
        
  } // initializeAttibutes

    
  public String getNameKeyOfMunicipality()
  {
    return this.municipalityNameKey;
  }
  
  public String getMunicipalityNameKey()
  {
    return this.municipalityNameKey;
  }
  
  
  
  
  public String getNameOfMunicipality()
  {	
    MunicipalitiesManager mm = MunicipalitiesManager.GetInstance();
    Municipality municipality = mm.getMunicipalityByKey(this.municipalityNameKey);    
    String name = null;
    if( municipality != null )
    {
      name = municipality.getMunicipalityNameForLanguage( super.getLanguage() );
    }
    // don't return null, otherwise calling JSP's run into exceptions:
    if( name == null ) 
    {
      System.out.println("*** MunicipalityForm.getNameOfMunicipality(): name of municipality is not defined.");
      name = "undefined";
    }
    return name;    
  }
  
  
  public MunicipalityServiceParameter[] getServices()
  {
  	MunicipalityServiceParameter[] parms = 
  		new MunicipalityServiceParameter[ this.services.length];
  	for( int i=0; i < parms.length; i++ )
  	{
      parms[i] = new MunicipalityServiceParameter(
      		           this.services[i].getServiceIdentifier(),
      		           this.services[i].getMunicipalityNameKey(),
					   this.getLanguage() );
  	}
    return parms;
  }

  
  
  
  
 /**
  *  Called by municipalityAction, when an authentication
  *  has failed. 
  */ 
  public void setNumberOfFailedAuthenticationTries( int number )
  {
    this.numberOfFailedAuthenticationTries = number;
  }
  
  
  
  
 /**
  *  Return a text saying that the authentication has failed
  *  and add the counter of failed authentication tries. 
  */ 
  public String getAuthenticationFailureInformation()
  {
    String line1 = super.getTextFromResource( TextResourceKeys.AuthenticationFailureInformation );
    String line2 = super.getTextFromResource( TextResourceKeys.NumberOfAuthenticationFailures );
    return line1 + "<br>" + line2 + ": " + String.valueOf(this.numberOfFailedAuthenticationTries);
  }
  

  
  /** 
   *  This is called by the logout_SwitchToHTTP.jsp.
   * 
   *  This URL has to use the (unsecure) HTTP protocol and
   *  contain the language parameters which
   *  are set on this form. 
   */
   public String getLogoutURL()
   {
     StringBuffer loginURL = new StringBuffer();
     loginURL.append("logout.do?");     
     loginURL.append("language=");
     loginURL.append( super.getLanguage() );     
     return loginURL.toString();
   }
  
  
  
  
  public String getHelpTopicKey()
  {
  	return TextResourceKeys.HelpTopic_MunicipalityKey;
  }

  
  
  
  /**
   *  For the ProcessStep Applet, three methods (called by JSP's) are required here:
   *  getProcessStepTextList(), processStepDescriptionList() and getProcessStepIndex()
   * 
   *  This method returns a ";" separated list of texts for the
   *  process step list view.
   * 
   *  The texts are read from the textresource and returned in the
   *  selected natural language of the client.
   * 
   *  @return comma separated list of texts for each step.
   */ 
   public String getProcessStepTextList()
   {
     StringBuffer textList = new StringBuffer();
     textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_MunicipalityIndex) );
     return textList.toString();
   }
   

  /**
   *  For the ProcessStep Applet, three methods (called by JSP's) are required here:
   *  getProcessStepTextList(), processStepDescriptionList() and getProcessStepIndex()
   * 
   *  This method returns a ";" separated list of descriptions for the
   *  process step list view.
   * 
   *  The texts are read from the textresource and returned in the
   *  selected natural language of the client.
   * 
   *  @return comma separated list of texts for each step.
   */ 
   public String getProcessStepDescriptionList()
   {
     StringBuffer textList = new StringBuffer();
     textList.append( super.getTextFromResource(TextResourceKeys.ProcessStep_MunicipalityIndex_Description) );
     return textList.toString();
   }
   
   
  /**
   *  For the ProcessStep Applet, three methods (called by JSP's) are required here:
   *  getProcessStepTextList(), processStepDescriptionList() and getProcessStepIndex()
   * 
   *  This method returns the index for the
   *  current process step. The associated entry shall be highlighted
   *  and means the current step in the list. Steps on the left are past
   *  steps, and steps on the right are to be carried out in the future. 
   *  @return index of current step (zero based)
   */ 
   public String getProcessStepIndex()
   {
     return "0"; // The third entry = the software check step
   }
  
  
  
  
  
} // MunicipalityForm
