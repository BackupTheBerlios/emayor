/*
 * Created on 29.07.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.emayor.servicehandling.gui.admin;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.util.Enumeration;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.ServiceUnavailableException;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.eMayor.PolicyEnforcement.C_UserProfile.E_UserProfileException;
import org.emayor.ContentRouting.ejb.AccessPointNotFoundException;
import org.emayor.ContentRouting.ejb.BindingTemplateNotFoundException;
import org.emayor.ContentRouting.ejb.OrganisationNotFoundException;
import org.emayor.ContentRouting.ejb.ServiceNotFoundException;
import org.emayor.ContentRouting.interfaces.ContentRouterLocal;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.interfaces.UserTaskManagerLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.kernel.AccessSessionException;
import org.emayor.servicehandling.kernel.BPELDomainCredentials;
import org.emayor.servicehandling.kernel.Kernel;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.ServiceInfo;
import org.emayor.servicehandling.kernel.ServiceSessionException;
import org.emayor.servicehandling.kernel.ServicesInfo;
import org.emayor.servicehandling.kernel.Tasks;
import org.emayor.servicehandling.model.UTWrapperEJB;
import org.emayor.servicehandling.model.UTWrapperException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;


/**
 * @author mxs
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PlatformTest {
	
	/*
	 * Context for PlatformTest webservice invocation 
	 */
    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[1];
        org.apache.axis.description.OperationDesc oper;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("process");
        oper.addParameter(new javax.xml.namespace.QName("http://org.emayor/servicehandling/gui/admin/PlatformTest", "PlatformTestProcessRequest"), new javax.xml.namespace.QName("http://org.emayor/servicehandling/gui/admin/PlatformTest", "PlatformTestProcessRequest"), org.emayor.servicehandling.gui.admin.PlatformTestProcessRequest.class, org.apache.axis.description.ParameterDesc.IN, false, false);
        oper.setReturnType(new javax.xml.namespace.QName("http://org.emayor/servicehandling/gui/admin/PlatformTest", "PlatformTestProcessResponse"));
        oper.setReturnClass(org.emayor.servicehandling.gui.admin.PlatformTestProcessResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://org.emayor/servicehandling/gui/admin/PlatformTest", "PlatformTestProcessResponse"));
        oper.setStyle(org.apache.axis.enum.Style.DOCUMENT);
        oper.setUse(org.apache.axis.enum.Use.LITERAL);
        _operations[0] = oper;

    }

	
	//private final String TEST_CERTIFICATE_CITIZEN_FILENAME = "Citizen.p12";
	//private final String TEST_CERTIFICATE_SERVANT_FILENAME = "Servant.p12";
	
	//private final String TEST_CERTIFICATE_CITIZEN_STRING_BASE64 = "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";
	//private final String TEST_CERTIFICATE_SERVANT_STRING_BASE64 = "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";
	
    /*
     * test in order they should be performed
     */
    public static final int TEST_CONFIG 	= 0;
    public static final int TEST_JNDI 		= 1;
    public static final int TEST_LOGIN		= 2;
    public static final int TEST_BPEL 		= 3;
    public static final int TEST_UT 		= 4;
    public static final int TEST_CR 		= 5;
    public static final int TEST_MAIL 		= 6;
    public static final int TEST_SERV 		= 7;
    
    /*
     * Certificate for login testing as XML String
     */
	private final String TEST_CERTIFICATE_PROFILE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><UserProfile OrganisationUnit=\"FOKUS\" UserCountry=\"DE\" UserEmail=\"kusber@fokus.fraunhofer.de\" UserName=\"Tomasz Kusber\" UserOrganisation=\"FOKUS\" UserRole=\"Citizen\" UserST=\"Berlin\">	<X509CertChain Length=\"1\">		<X509Certificate xmlns=\"http://www.w3.org/2000/09/xmldsig#\" ChainOrder=\"0\">MIIECjCCAvKgAwIBAgIBBjANBgkqhkiG9w0BAQUFADCBkDEbMBkGA1UEAxMSZU1heW9yIFVvU2llZ2VuIENBMQswCQYDVQQGEwJERTEMMAoGA1UECBMDTlJXMQ8wDQYDVQQHEwZTaWVnZW4xGDAWBgNVBAoTD2VNYXlvciBVb1NpZWdlbjErMCkGCSqGSIb3DQEJARYcc2VyZ2l1LnRjYWNpdWNAdW5pLXNpZWdlbi5kZTAeFw0wNTAzMTAxMzI1MjNaFw0wNjAzMTAxMzI1MjNaMIGBMQswCQYDVQQGEwJERTEPMA0GA1UECBMGQmVybGluMQ4wDAYDVQQKEwVGT0tVUzEOMAwGA1UECxMFRk9LVVMxFjAUBgNVBAMTDVRvbWFzeiBLdXNiZXIxKTAnBgkqhkiG9w0BCQEWGmt1c2JlckBmb2t1cy5mcmF1bmhvZmVyLmRlMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDocZVaAmKjvCEmcFSsDLWA6YgpIhRo+AY8yW76x5baPYtnKwQJHhcciRp6+SvHvhzF1dGyZJ8+UG+6PSQAOg6YlOMnAiEoa3zuemfTFB+sqgv3v5p56sDcVhH628RWhPqBqUopikr5WYXm6UD0KsgUx0jAIkML72AJYZsks5O04wIDAQABo4H/MIH8MAkGA1UdEwQCMAAwHQYDVR0OBBYEFJ64E6pWgTkBO4+PR3BsLkJfPfyMMIG9BgNVHSMEgbUwgbKAFGwANjDsHUCa9HpVVd7qNGVpLHbToYGWpIGTMIGQMRswGQYDVQQDExJlTWF5b3IgVW9TaWVnZW4gQ0ExCzAJBgNVBAYTAkRFMQwwCgYDVQQIEwNOUlcxDzANBgNVBAcTBlNpZWdlbjEYMBYGA1UEChMPZU1heW9yIFVvU2llZ2VuMSswKQYJKoZIhvcNAQkBFhxzZXJnaXUudGNhY2l1Y0B1bmktc2llZ2VuLmRlggEAMBAGAyoDBAQJEwdDaXRpemVuMA0GCSqGSIb3DQEBBQUAA4IBAQAYjRSdsh2myJosT8GYwU5BuREEFHwBa/p8Mcv052R0x7lOL69zAVnbEveZ5/kBOwv/z3Z5B9CjH1v9NAtYyWKGui7xsu0QcQzbEtyZ7BmoU5tDoL8F4SrCyXPQs8FQlOkKRvOpUNXwol3k5rOjO8M4sg6tzyOvV86ir6D35ec3msOInJq4ABfZRyKAJS6u00RUWDGRQcogL1x6BYLIcsGhFDX774lP1d3FBfmoR+Gnt/dAO1LbKWsKL5ejBDJO79YYt3HRWdhOhqvTkyC1Mm8BYC+Mvf9CZDKJQH1Z6y6jM29w/CdtrDOftJxOcVHLGcO22pcEMToY1mpuvgPdCcpQ</X509Certificate>	</X509CertChain></UserProfile>";
	
	/*
	 * Logger
	 */
	Logger log = null;
	
	/*
	 * Global configuration reference, set up once
	 */
	private Config config = null;
	
	/*
	 * Global error String, set when a test returns a failure 
	 */
	private String error = null;
	
	/*
	 * Initialise Test Environment
	 */
	public PlatformTest() {
		log = Logger.getLogger(this.getClass());
		try {
			this.config = Config.getInstance();
		} catch (ConfigException e) {
			error = e.getMessage();
		}
	}
	
	/*
	 * return last error that occurred
	 */
	public String getError() {
		return this.error;
	}
	
	/*
	 * test wheter a complete config is available
	 * and if we could write new settings
	 */
	public boolean testConfig() {
		log.debug("start ...");
		boolean result = true;
		this.error = null;
		
		try {

			if (this.config == null) result = false;
			
			Properties properties = config.getAllProperties();
			String value = null;
			String key = null;
			for (Enumeration e = properties.keys(); e.hasMoreElements();) {
				key = (String) e.nextElement();
				value = config.getProperty(key);				
				if (value == null || value.equals("")) throw new Exception("no value for key >"+key+"<");
			}
			
			config.setProperty(Config.EMAYOR_PLATFORM_INSTANCE_ID,
								config.getProperty(Config.EMAYOR_PLATFORM_INSTANCE_ID));
		
		} catch (Exception e) {
			result = false;
			this.error = e.getMessage();
			e.printStackTrace();
		}
		
		log.debug("done ...");
		return result;
	}

	/*
	 * test whether we can access JNDI
	 */
	public boolean testJNDI() {
		log.debug("start ...");
		boolean result = true;
		this.error = null;
		
		try {
			Properties props = new Properties();
			props.setProperty("java.naming.factory.initial",
					"org.jnp.interfaces.NamingContextFactory");
			props.setProperty("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			
			props.setProperty("java.naming.provider.url",config.getProperty(Config.FORWARD_MANAGER_QUEUE_HOST));

			Context context = new InitialContext(props);
			
			QueueConnectionFactory factory = (QueueConnectionFactory) context
					.lookup("ConnectionFactory");
			
			QueueConnection connect = factory.createQueueConnection();
			
			connect.close();
			
		} catch (ConfigException e) {
			result = false;
			this.error = "Config error: "+ e.getMessage();
			e.printStackTrace();
		} catch (NamingException e) {
			result = false;
			if (e instanceof CommunicationException)
				this.error = "JNDI not available";
			else if (e.getCause() != null && e.getCause() instanceof ConnectException)
				this.error = "JNDI not available";
			else
				this.error = "JNDI is not complete functional, standard service 'ConnectionFactory' not bound!";
			e.printStackTrace();
		} catch (JMSException e) {
			result = false;
			this.error = "JNDI is not complete functional, JMS not available!";
			e.printStackTrace();
		} catch (Exception e) {
			result = false;
			this.error = "General error: "+ e.getMessage();
			e.printStackTrace();
		}
		
		log.debug("done ...");
		return result;
	}
	
	/*
	 * test whether content-routing is functional
	 * - that is whether juddi is working correctly
	 * 
	 * searches for own forward entry using the municipality-id
	 */
	public boolean testContentRouting() {
		log.debug("start ...");
		boolean result = true;
		this.error = null;

		String municipality = null;
		String service = null;
		
		try {
			
			municipality = config.getProperty(Config.EMAYOR_PLATFORM_INSTANCE_ID);
			service = "forward";
			
			ServiceLocator loc = ServiceLocator.getInstance();
			
			ContentRouterLocal c_router = loc.getContentRouterLocal();
			
			c_router.getAccessPoint(municipality,service);
			
		} catch (ServiceLocatorException e) {
			result = false;
			this.error = "ServiceLocator error: "+ e.getMessage();
			e.printStackTrace();
		} catch (OrganisationNotFoundException e) {
			result = false;
			this.error = "UDDI entry for local municipality ("+municipality+") not found";
			e.printStackTrace();
		} catch (ServiceNotFoundException e) {
			result = false;
			this.error = "UDDI entry for local service ("+service+") not found";
			e.printStackTrace();
		} catch (BindingTemplateNotFoundException e) {
			result = false;
			this.error = "UDDI service ("+service+") for business ("+municipality+") not found";
			e.printStackTrace();
		} catch (AccessPointNotFoundException e) {
			result = false;
			this.error = "UDDI entry for local service ("+service+") endpoint not found";
			e.printStackTrace();
		} catch (ServiceUnavailableException e) {
			result = false;
			this.error = "UDDI not available, verify configuration settings and check that the service is running.";
			e.printStackTrace();
		} catch (ConfigException e) {
			result = false;
			this.error = "Config error: "+ e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			result = false;
			this.error = "General error: "+ e.getMessage();
			e.printStackTrace();
		}
		
		log.debug("done ...");
		return result;		
	}
	
	/*
	 * test login with the standard certificate
	 */
	public boolean testLogin() {
		return testLogin(TEST_CERTIFICATE_PROFILE);
	}
	
	/*
	 * test login
	 * 
	 * get a kernel instance, an access sesion, login 
	 * and perform some dumb invariant testings
	 */
	public boolean testLogin(String certProfile) {
		log.debug("start ...");
		boolean result = true;
		this.error = null;
		
		try {

			C_UserProfile profile = new C_UserProfile(certProfile);			
			Kernel kern = Kernel.getInstance();
			String asid = kern.createAccessSession();
			AccessSessionLocal session = kern.getAccessSession(asid);
			result = session.authenticateUser(profile.getX509_CertChain());
			String uid = kern.getUserIdByASID(asid);
			
			/* check invariants */
			if (	
					(! session.getUserId().equals(uid)) ||
					(! kern.getAsidByUserID(uid).equals(asid))
					
				) result = false;

			session.stop();
			session.remove();
			
			try {
				kern.getAccessSession(asid);
				result = false;
			} catch (Exception e1) {}
			
			
		} catch (E_UserProfileException e) {
			result = false;
			this.error = "User Profile out of date - upgrade the PlatformTest routines";
			e.printStackTrace();
		} catch (KernelException e) {
			result = false;
			this.error = "Error on kernel query: "+e.getMessage() 
				+ "\n (Hint: Not correctly logged out? - Restart JBoss, login & try again)";
			e.printStackTrace();
		} catch (AccessSessionException e) {
			result = false;
			this.error = "Error on user authentication: "+e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			result = false;
			this.error = "Unknown error occured: " +e.getMessage();
			e.printStackTrace();
		}
		
		log.debug("done ...");
		return result;		
	}

	/*
	 * test mail service by trying to connect to it
	 */
	public boolean testMail() {
		log.debug("start ...");
		boolean result = true;
		this.error = null;
		
		try {
		
			String host = config.getProperty(Config.EMAYOR_NOTIFICATION_EMAIL_SMTP_HOST);
			String auth = config.getProperty(Config.EMAYOR_NOTIFICATION_EMAIL_SMTP_AUTH);
			String user = config.getProperty(Config.EMAYOR_NOTIFICATION_EMAIL_SMTP_USER);
			String pass = config.getProperty(Config.EMAYOR_NOTIFICATION_EMAIL_SMTP_PASS);
			
			Properties props = new Properties();
			props.setProperty("mail.smtp.host",host);
			props.setProperty("mail.smtp.user",user);
			props.setProperty("mail.smtp.pass",pass);
			props.setProperty("mail.smtp.auth",auth);
			
			Session session = Session.getDefaultInstance(props,null);
			Transport trans = null;
			
			trans = session.getTransport("smtp");
			
			trans.connect(host,user,pass);
						
			trans.close();
			
		} catch (ConfigException e) {
			result = false;
			this.error = "Config Exception" + e.getMessage();
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			result = false;
			this.error = "Mail Service not available, verify host settings and check that the service is running";
			e.printStackTrace();
		} catch (MessagingException e) {
			result = false;
			this.error = "Mail Service not available, verify host settings and check that the service is running and the authentication settings are correct.";
			e.printStackTrace();
		} catch (Exception e) {
			result = false;
			this.error = "General error:" + e.getMessage();
			e.printStackTrace();
		}
		
		log.debug("done ...");
		return result;		
	}
	
	/*
	 * test BPEL by using the PlatformTest-service, that should have been deployed
	 */
	public boolean testBPEL() {
		log.debug("start ...");
		boolean result = true;
		this.error = null;
		
		try {
			
			String domain = config.getProperty(Config.BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME);
			
			PlatformTest_Service service = null;
			InitialContext initialContext = this.getInitialContextForWSClient("PlatformTestClient");
			
			service = (PlatformTest_Service) initialContext
				.lookup("java:comp/env/service/PlatformTestClient");
			
			Call call = (Call) service.createCall();
			call.setUseSOAPAction(true);
			call.setSOAPActionURI("process");
			call.setEncodingStyle(null);
			call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
					Boolean.FALSE);
			call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
					Boolean.FALSE);
			call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
			call.setTargetEndpointAddress("http://localhost:9700/orabpel/"+domain+"/PlatformTest/1.0");
			call.setOperation(_operations[0]);
			
			call.setOperationName(new javax.xml.namespace.QName("", "process"));
			
			String requestStr = "test";
			PlatformTestProcessRequest request = new PlatformTestProcessRequest();
			request.setInput(requestStr);
			PlatformTestProcessResponse response = (PlatformTestProcessResponse) call.invoke(new Object[] { request });
			
			if (	response == null || 
					response.getResult() == null || 
					(!response.getResult().equals(requestStr))
				) 
				throw new Exception("Process testing returned a failure");
											      
		} catch (ConfigException e) {
			result = false;
			this.error = "BPEL Service lookup failed, caused by Config: " + e.getMessage();
			e.printStackTrace();
		} catch (NamingException e) {
			result = false;
			this.error = "BPEL Service lookup failed, caused by JNDI: " + e.getMessage();
			e.printStackTrace();
		} catch (ServiceException e) {
			result = false;
			this.error = "BPEL Service lookup failed, caused by client: " + e.getMessage();
			e.printStackTrace();
		} catch (RemoteException e) {
			result = false;
			if (e.getCause() != null & e.getCause() instanceof ConnectException)
				this.error = "BPEL not available";
			else if (e.getCause() != null & e.getCause() instanceof ServerException)
				this.error = "BPEL not available";
			else if (e.getMessage().replaceAll("\n","").matches("^(.*?)PlatformTest(.*?)ORABPEL-05205(.*?)$"))
				this.error = "BPEL available, but PlatformTest not deployed - functional check not complete";
			else
				this.error = "BPEL Service lookup failed, caused by BPEL: " + e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			result = false;
			this.error = e.getMessage();
			e.printStackTrace();
		}
		
		log.debug("done ...");
		return result;		
	}
	
	public boolean testUTWrapper() {
		return testUTWrapper(TEST_CERTIFICATE_PROFILE);
	}
	
	public boolean testUTWrapper(String certProfile) {
		log.debug("start ...");
		boolean result = true;
		this.error = null;
		
		AccessSessionLocal session = null;
		
		try {
			C_UserProfile profile = new C_UserProfile(certProfile);
			Kernel kern = Kernel.getInstance();
			String asid = kern.createAccessSession();
			session = kern.getAccessSession(asid);
			result = session.authenticateUser(profile.getX509_CertChain());
			String uid = kern.getUserIdByASID(asid);
			
			ServiceLocator loc = ServiceLocator.getInstance();
			UserTaskManagerLocal ut_loc = loc.getUserTaskManagerLocal();
			UTWrapperEJB ut_ejb = loc.getUTWrapperRemoteInterface();
			
			BPELDomainCredentials credentials = new BPELDomainCredentials();
			credentials.setDomainName(config.getProperty(Config.BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME));
			credentials.setDomainPassword(config.getProperty(Config.BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD));
			
			Tasks tasks = ut_ejb.listTasksByAssignee(uid,credentials);
			
			if (tasks.getTasks() == null) {
				result = false;
				throw new Exception("could not get list of tasks for uid "+uid);
			}
		} catch (E_UserProfileException e) {
			result = false;
			this.error = "User Profile out of date - upgrade the PlatformTest routines";
			e.printStackTrace();
		} catch (KernelException e) {
			result = false;
			this.error = "Error on kernel query: "+e.getMessage();
			e.printStackTrace();
		} catch (AccessSessionException e) {
			result = false;
			this.error = "Error on user authentication: "+e.getMessage();
			e.printStackTrace();
		} catch (ServiceLocatorException e) {
			result = false;
			if (e.getCause() != null && e.getCause() instanceof NamingException) {
				if (e.getCause().getCause() != null && e.getCause().getCause() instanceof ConnectException)
					this.error = "BPEL not available";
				else
					this.error = "lookup failed, validate RMI address";
			} else this.error = "Error on service-locator query: "+e.getMessage();
			e.printStackTrace();
		} catch (ConfigException e) {
			result = false;
			this.error = "Error accessing configuration options: " + e.getMessage();
			e.printStackTrace();
		} catch (RemoteException e) {
			result = false;
			this.error = "Error accessing remote instance of UTWrapper: "+e.getMessage();
			e.printStackTrace();
		} catch (UTWrapperException e) {
			result = false;
			this.error = "Error while accessing BPEL service: " +e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			result = false;
			this.error = "Unknown error occured: " +e.getMessage();
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.stop();
					session.remove();
				} catch (Exception e1) {}
				
			}
		}
		
		log.debug("done ...");
		return result;		
	}
	
	public boolean testServices() {
		return testServices(TEST_CERTIFICATE_PROFILE);
	}
	
	/*
	 * start & stop all available services
	 */
	public boolean testServices(String certProfile) {
		log.debug("start ...");
		boolean result = true;
		this.error = null;
		
		String serviceStep = null;
		AccessSessionLocal session = null;
		
		try {
			C_UserProfile profile = new C_UserProfile(certProfile);
			
			Kernel kern = Kernel.getInstance();
			String asid = kern.createAccessSession();
			session = kern.getAccessSession(asid);
			result = session.authenticateUser(profile.getX509_CertChain());
			String uid = kern.getUserIdByASID(asid);
			
			ServiceLocator loc = ServiceLocator.getInstance();
			AccessManagerLocal access = loc.getAccessManager();
			ServicesInfo accessinfo = access.listAvailableServices("-1001");
			
			ServiceInfo[] services = accessinfo.getServicesInfo();
			
			ServiceSessionLocal local = null;
			ServiceInfo service = null;
			String sid, ssid;
			
			log.debug("got "+services.length+" services");
			
			for (int i=0; i<services.length; i++) {
				if (services[i].isActive()) {
					service = services[i];
					sid = service.getServiceId();
					serviceStep = "starting "+sid;
					log.debug(serviceStep+"....");
					ssid = access.startService(asid,sid);
					serviceStep = "stopping "+sid;
					log.debug(serviceStep+"....");
					access.stopService(asid,ssid);
					serviceStep = null;
				}
			}
			
			
			
		} catch (E_UserProfileException e) {
			result = false;
			this.error = "User Profile out of date - upgrade the PlatformTest routines";
			e.printStackTrace();
		} catch (KernelException e) {
			result = false;
			if (serviceStep != null) {
				this.error = "Error on "+serviceStep+" :" +e.getMessage();	
			}
			else this.error = "Error on kernel query: "+e.getMessage();
			e.printStackTrace();
		} catch (AccessSessionException e) {
			result = false;
			this.error = "Error on user authentication: "+e.getMessage();
			e.printStackTrace();
		} catch (ServiceLocatorException e) {
			result = false;
			this.error = "Error on service-locator query: " +e.getMessage();
			e.printStackTrace();
		} catch (AccessException e) {
			result = false;
			this.error = "Error on access-manager query: " +e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			result = false;
			this.error = "Unknown error occured: " +e.getMessage();
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.stop();
					session.remove();
				} catch (Exception e1) {}
				
			}
		}
		
		log.debug("done ...");
		return result;
	}
	
	private InitialContext getInitialContextForWSClient(String clientName) throws ConfigException, NamingException {
		log.debug("-> starting processing ...");
		InitialContext ret = null;
		
		Properties env = new Properties();
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		env.setProperty(Context.URL_PKG_PREFIXES,
				"org.jboss.naming.client");
		env.setProperty(Context.PROVIDER_URL, 
				"jnp://"+config.getProperty(Config.FORWARD_MANAGER_QUEUE_HOST));
		env.setProperty("j2ee.clientName", clientName);
			
		ret = new InitialContext(env);
		
		log.debug("-> ... processing DONE!");
		return ret;
	}
}
