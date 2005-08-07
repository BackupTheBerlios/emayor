/*
 * $ Created on May 9, 2005 by tku $
 */
package org.emayor.servicehandling.config;

import java.io.File;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.PlatformConfigurationEntityLocal;
import org.emayor.servicehandling.interfaces.PlatformConfigurationEntityLocalHome;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class Config {
	private static final Logger log = Logger.getLogger(Config.class);

	/*
	 * Configuration values 
	 */
	
	// the unique id of the platform instance e.g. Aachen,Bozen
	public static final int EMAYOR_PLATFORM_INSTANCE_ID = 0;
	
	// arbitrary fulle name of the municipality - informative character
	public static final int EMAYOR_MUNICIPALITY_NAME = 1;

	// this property indicates whether the internal test services should
	// be loaded (test) or the services contained in the deploy directory
	public static final int EMAYOR_OPERATING_MODE = 2;

	// test - the forwarding will be simulated, it means there is no
	// need to start an extra (second) BPEL PM
	// production - a real process will be started in case of forwarding
	public static final int EMAYOR_OPERATING_MODE_FORWARD = 3;

	// deployment directory for the emayor services
	public static final int EMAYOR_TMP_DIR = 4;

	// email on the emayor platform
	public static final int EMAYOR_OPERATING_MODE_EMAIL = 5;
	public static final int EMAYOR_EMAIL_TEST_USER_ADDRESS = 6;

	// forward manager 
	public static final int FORWARD_MANAGER_QUEUE_HOST = 7;
	public static final int FORWARD_MANAGER_QUEUE_NAME = 8;
	public static final int EMAYOR_OPERATING_MODE_CONTENT_ROUTING = 9;
	public static final int FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_NAME = 10;
	public static final int FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_NAME = 11;
	public static final int FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_ADDRESS = 12;
	public static final int FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_ADDRESS = 13;
	
	// server1 or server2
	public static final int FORWARD_MANAGER_TEST_REMOTE_PROFILE_ID = 14;

	// eMayor BPEL service invoker
	public static final int EMAYOR_SERVICE_INVOKER_ENDPOINT = 15;

	// eMayor admin interface
	// YES or NO
	public static final int EMAYOR_ADMIN_INTERFACE_IS_ENABLED = 16;
	public static final int EMAYOR_ADMIN_INTERFACE_USERID = 17;
	public static final int EMAYOR_ADMIN_INTERFACE_PASSWORD = 18;

	// bpel engine initial context configuration for the UTWrapper application
	public static final int BPEL_ENGINE_UT_INITIAL_CONTEXT_FACTORY = 19;
	public static final int BPEL_ENGINE_UT_SECURITY_PRINCIPAL = 20;
	public static final int BPEL_ENGINE_UT_SECURITY_CREDENTIALS = 21;
	public static final int BPEL_ENGINE_UT_PROVIDER_URL = 22;
	public static final int BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME = 23;
	public static final int BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD = 24;

	// Policy Enforcer Configurartion
	public static final int EMAYOR_PE_INFO_DIR = 25;

	// Notification (Email Gateway) Configuration
	// host = smtp-server (required!)
	// auth = is the authentification required (true,false, assumed false when empty)
	// user = authenticate as user (optional)
	// pass = user´s password (optional)
	public static final int EMAYOR_NOTIFICATION_EMAIL_SMTP_HOST = 26;
	public static final int EMAYOR_NOTIFICATION_EMAIL_SMTP_USER = 27;
	public static final int EMAYOR_NOTIFICATION_EMAIL_SMTP_PASS = 28;
	public static final int EMAYOR_NOTIFICATION_EMAIL_SMTP_AUTH = 29;
	
	// Content Routing
	public static final int EMAYOR_CONTENT_ROUTING_LOCAL_INQUIRY = 30;
	public static final int EMAYOR_CONTENT_ROUTING_REMOTE_INQUIRY = 31;
	public static final int EMAYOR_CONTENT_ROUTING_LOCAL_PUBLISH = 32;
	public static final int EMAYOR_CONTENT_ROUTING_USERID = 33;
	public static final int EMAYOR_CONTENT_ROUTING_PASSWORD = 34;
	
	// marker for active configuration
	public static final int CONFIG_IS_ACTIVE = 35;
	
	public static final int EMAYOR_PE_CRL_DISTRIBUTION_URL = 36;
	public static final int EMAYOR_PE_CRL_USE_DEFAULT_DISTRIBUTION_URL = 37;
	
	/*
	 * INSERT NEW FIELDS HERE
	 */
	
	private final boolean WRITE_CONFIG = false;
	private final boolean READ_CONFIG  = true;
	
	
	private static Config _self;
	
	private String JBOSS_HOME_DIR = "";
	
	private String configID = "default";
	
	private PlatformConfigurationEntityLocalHome home = null;
	private PlatformConfigurationEntityLocal local = null;
	
	private Properties old2new = null;
	
	/**
	 *  
	 */
	private Config() throws ConfigException {
		log.debug("-> start processing ...");
		boolean exists = false;
		
		try {
			/*
			 * cannot use service locator, cause this
			 * creates endless loops
			 */
			//loc = ServiceLocator.getInstance();
			//home = loc.getPlatformConfigurationEntityLocalHome();
			Object ref = new InitialContext()
					.lookup(PlatformConfigurationEntityLocalHome.JNDI_NAME);
			home = (PlatformConfigurationEntityLocalHome) PortableRemoteObject
					.narrow(ref, PlatformConfigurationEntityLocalHome.class);
			
			/*
			 * search for active configuration
			 */
			for (Iterator i = home.findAll().iterator(); i.hasNext();) {
				local = (PlatformConfigurationEntityLocal) i.next();
				if (local.getIsActive().booleanValue()) {
					this.configID = local.getConfigID();
				}
			}
			
			/*
			 * otherwise switch to hardcoded value
			 */
			if (local == null) {
				local = home.create(this.configID);
			} else {
				exists = true;
			}
		} catch (CreateException e) {
			log.debug("using config >"+this.configID+"< already registered");
			exists = true;
		} catch (NamingException e) {
			e.printStackTrace();
			throw new ConfigException("init failed:"+e.getMessage(),e.getCause());
		} catch (FinderException e) {
			try {
				/*
				 * if no active config is found, we just create a new one
				 */
				log.debug("no active config specified, using >"+this.configID+"<");
				local = home.create(this.configID);
			} catch (CreateException e1) {
				e1.printStackTrace();
				throw new ConfigException("init failed:"+e1.getMessage(),e1.getCause());
			}
		}
		if (local == null) {
			try {
				local = home.findByPrimaryKey(this.configID);
			} catch (FinderException e1) {
				throw new ConfigException("init failed:"+e1.getMessage(),e1.getCause());
			}
		}
		
		this.old2new = new Properties();
		this.init();
		this.JBOSS_HOME_DIR = System.getProperty("jboss.server.home.dir");
		
		if (!exists) this.loadConfiguration();
		
		log.debug("-> ... processing DONE!");
	}
	
	public static synchronized Config getInstance() throws ConfigException {
		log.debug("-> start processing ...");
		if (_self == null)
			_self = new Config();
		log.debug("-> ... processing DONE!");
		return _self;
	}

	private void init() {
		old2new.setProperty("emayor.platform.instance.id",
				new Integer(EMAYOR_PLATFORM_INSTANCE_ID).toString());
		old2new.setProperty("emayor.municipality.name",
				new Integer(EMAYOR_MUNICIPALITY_NAME).toString());
		old2new.setProperty("emayor.operating.mode",
				new Integer(EMAYOR_OPERATING_MODE).toString());
		old2new.setProperty("emayor.operating.mode.forward",
				new Integer(EMAYOR_OPERATING_MODE_FORWARD).toString());
		old2new.setProperty("emayor.tmp.dir",
				new Integer(EMAYOR_TMP_DIR).toString());
		old2new.setProperty("emayor.operating.mode.email",
				new Integer(EMAYOR_OPERATING_MODE_EMAIL).toString());
		old2new.setProperty("emayor.email.test.user.address",
				new Integer(EMAYOR_EMAIL_TEST_USER_ADDRESS).toString());
		old2new.setProperty("forward.manager.queue.host",
				new Integer(FORWARD_MANAGER_QUEUE_HOST).toString());
		old2new.setProperty("forward.manager.queue.name",
				new Integer(FORWARD_MANAGER_QUEUE_NAME).toString());
		old2new.setProperty("emayor.operating.mode.content.routing",
				new Integer(EMAYOR_OPERATING_MODE_CONTENT_ROUTING).toString());
		old2new.setProperty("forward.manager.test.local.municipality.name",
				new Integer(FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_NAME).toString());
		old2new.setProperty("forward.manager.test.remote.municipality.name",
				new Integer(FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_NAME).toString());
		old2new.setProperty("forward.manager.test.local.municipality.address",
				new Integer(FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_ADDRESS).toString());
		old2new.setProperty("forward.manager.test.remote.municipality.address",
				new Integer(FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_ADDRESS).toString());
		old2new.setProperty("forward.manager.test.remote.profile.id",
				new Integer(FORWARD_MANAGER_TEST_REMOTE_PROFILE_ID).toString());
		old2new.setProperty("emayor.service.invoker.endpoint",
				new Integer(EMAYOR_SERVICE_INVOKER_ENDPOINT).toString());
		old2new.setProperty("emayor.admin.interface.is.enabled",
				new Integer(EMAYOR_ADMIN_INTERFACE_IS_ENABLED).toString());
		old2new.setProperty("emayor.admin.interface.userid",
				new Integer(EMAYOR_ADMIN_INTERFACE_USERID).toString());
		old2new.setProperty("emayor.admin.interface.password",
				new Integer(EMAYOR_ADMIN_INTERFACE_PASSWORD).toString());
		old2new.setProperty("bpel.engine.ut.initial.context.factory",
				new Integer(BPEL_ENGINE_UT_INITIAL_CONTEXT_FACTORY).toString());
		old2new.setProperty("bpel.engine.ut.security.principal",
				new Integer(BPEL_ENGINE_UT_SECURITY_PRINCIPAL).toString());
		old2new.setProperty("bpel.engine.ut.security.credentials",
				new Integer(BPEL_ENGINE_UT_SECURITY_CREDENTIALS).toString());
		old2new.setProperty("bpel.engine.ut.provider.url",
				new Integer(BPEL_ENGINE_UT_PROVIDER_URL).toString());
		old2new.setProperty("bpel.engine.ut.security.domain.name",
				new Integer(BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME).toString());
		old2new.setProperty("bpel.engine.ut.security.domain.password",
				new Integer(BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD).toString());
		old2new.setProperty("emayor.pe.info.dir",
				new Integer(EMAYOR_PE_INFO_DIR).toString());
		old2new.setProperty("emayor.notification.email.smtp.host",
				new Integer(EMAYOR_NOTIFICATION_EMAIL_SMTP_HOST).toString());
		old2new.setProperty("emayor.notification.email.smtp.user",
				new Integer(EMAYOR_NOTIFICATION_EMAIL_SMTP_USER).toString());
		old2new.setProperty("emayor.notification.email.smtp.pass",
				new Integer(EMAYOR_NOTIFICATION_EMAIL_SMTP_PASS).toString());
		old2new.setProperty("emayor.notification.email.smtp.auth",
				new Integer(EMAYOR_NOTIFICATION_EMAIL_SMTP_AUTH).toString());
		old2new.setProperty("emayor.contentrouting.local.inquiry.url",
				new Integer(EMAYOR_CONTENT_ROUTING_LOCAL_INQUIRY).toString());
		old2new.setProperty("emayor.contentrouting.local.publish.url",
				new Integer(EMAYOR_CONTENT_ROUTING_LOCAL_PUBLISH).toString());
		old2new.setProperty("emayor.contentrouting.remote.inquiry.url",
				new Integer(EMAYOR_CONTENT_ROUTING_REMOTE_INQUIRY).toString());
		old2new.setProperty("emayor.contentrouting.userid",
				new Integer(EMAYOR_CONTENT_ROUTING_USERID).toString());
		old2new.setProperty("emayor.contentrouting.password",
				new Integer(EMAYOR_CONTENT_ROUTING_PASSWORD).toString());
		old2new.setProperty("config.is.active",
				new Integer(CONFIG_IS_ACTIVE).toString());
		old2new.setProperty("emayor.pe.crl.distributionURL",
				new Integer(EMAYOR_PE_CRL_DISTRIBUTION_URL).toString());
		old2new.setProperty("emayor.pe.crl.useDefaultURL",
				new Integer(EMAYOR_PE_CRL_USE_DEFAULT_DISTRIBUTION_URL).toString());
	}

	public synchronized String getProperty(String propName, String defValue)
			throws ConfigException {
		
		log.debug("-> start processing ...");
		
		String result = null;
		int propID = -1;
		
		if (old2new.getProperty(propName) != null) {
			propID = Integer.parseInt(old2new.getProperty(propName));
		}
		
		//log.debug("OLD property request by name: "+propName+" equals ID:"+propID);
		
		result = getProperty(propID,defValue);

		
		
		log.debug("-> ... processing DONE!");
		return result;
	}

	public synchronized String getProperty(int propID, String defValue)
		throws ConfigException {
		
		log.debug("-> start processing ...");
		
		String result = null;
		
		result = propertyAction(propID,READ_CONFIG,defValue,this.local);
		
		
		log.debug("-> ... processing DONE!");
		return result;
	}
	
	public synchronized String getProperty(String propertyName)
	throws ConfigException {
		return this.getProperty(propertyName, null);
	}
	
	public synchronized String getProperty(int propID)
	throws ConfigException {
		return this.getProperty(propID, null);
	}
	
	public synchronized String setProperty(int propID, String value)
	throws ConfigException {
		return this.propertyAction(propID, WRITE_CONFIG, value, this.local);
	}
	
	public synchronized String setProperty(String key, String value)
	throws ConfigException {
		int propID = -1;
		if (old2new.getProperty(key) != null) {
			propID = Integer.parseInt(old2new.getProperty(key));
		} 
		return this.setProperty(propID,value);
	}
	
	public synchronized String setProperty(int propID, boolean value)
	throws ConfigException {
		return this.propertyAction(propID, WRITE_CONFIG, Boolean.toString(value), this.local);
	}

	public synchronized String setProperty(String key, boolean value)
	throws ConfigException {
		int propID = -1;
		if (old2new.getProperty(key) != null) {
			propID = Integer.parseInt(old2new.getProperty(key));
		} 
		return this.setProperty(propID,value);
	}
	
	/*
	 * performs a write or read from properties using
	 * propID is reference to property to read/write
	 * write  is indicator whether read from or write to the property
	 * value  is the default value if a read is performed
	 *        or the value that should be written in the other case
	 */
	public synchronized String propertyAction(int propID, boolean action, String value, PlatformConfigurationEntityLocal local)
			throws ConfigException {
		
		if ((!action) && (!WRITE_CONFIG))
			log.debug("property set ("+local.getConfigID()+") [ID:"+propID+"="+value+"]");
		
		String result = null;
		
		switch (propID) {
		
		case(EMAYOR_PLATFORM_INSTANCE_ID):
			if (action && READ_CONFIG) result = local.getEMayorPlatformInstanceID();
			else local.setEMayorPlatformInstanceID(value);
			break;
	
		case(EMAYOR_MUNICIPALITY_NAME):
			if (action && READ_CONFIG) result = local.getEMayorMunicipalityName();
			else local.setEMayorMunicipalityName(value);
			break;
			

		case(EMAYOR_OPERATING_MODE):
			if (action && READ_CONFIG) result = local.getEMayorOperatingMode();
			else local.setEMayorOperatingMode(value);
			break;
			

		case(EMAYOR_OPERATING_MODE_FORWARD):
			if (action && READ_CONFIG) result = local.getEMayorOperatingModeForward();
			else local.setEMayorOperatingModeForward(value);
			break;
			

		case(EMAYOR_TMP_DIR):
			if (action && READ_CONFIG) result = local.getEMayorTmpDir();
			else local.setEMayorTmpDir(value);
			break;
			

		case(EMAYOR_OPERATING_MODE_EMAIL):
			if (action && READ_CONFIG) result = local.getEMayorOperationModeEmail();
			else local.setEMayorOperationModeEmail(value);
			break;
			
			
		case(EMAYOR_EMAIL_TEST_USER_ADDRESS):
			if (action && READ_CONFIG) result = local.getEMayorEmailTestUserAddress();
			else local.setEMayorEmailTestUserAddress(value);
			break;
			
 
		case(FORWARD_MANAGER_QUEUE_HOST):
			if (action && READ_CONFIG) result = local.getForwardManagerQueueHost();
			else local.setForwardManagerQueueHost(value);
			break;
			
			
		case(FORWARD_MANAGER_QUEUE_NAME):
			if (action && READ_CONFIG) result = local.getForwardManagerQueueName();
			else local.setForwardManagerQueueName(value);
			break;
			
			
		case(EMAYOR_OPERATING_MODE_CONTENT_ROUTING):
			if (action && READ_CONFIG) result = local.getEMayorOperationModeContentRouting();
			else local.setEMayorOperationModeContentRouting(value);
			break;
			
			
		case(FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_NAME):
			if (action && READ_CONFIG) result = local.getForwardManagerTestLocalMunicipalityName();
			else local.setForwardManagerTestLocalMunicipalityName(value);
			break;
			
			
		case(FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_NAME):
			if (action && READ_CONFIG) result = local.getForwardManagerTestRemoteMunicipalityName();
			else local.setForwardManagerTestRemoteMunicipalityName(value);
			break;
			
			
		case(FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_ADDRESS):
			if (action && READ_CONFIG) result = local.getForwardManagerTestLocalMunicipalityAddress();
			else local.setForwardManagerTestLocalMunicipalityAddress(value);
			break;
			
			
		case(FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_ADDRESS):
			if (action && READ_CONFIG) result = local.getForwardManagerTestRemoteMunicipalityAddress();
			else local.setForwardManagerTestRemoteMunicipalityAddress(value);
			break;
			
		
		case(FORWARD_MANAGER_TEST_REMOTE_PROFILE_ID):
			if (action && READ_CONFIG) result = local.getForwardManagerTestRemoteProfileID();
			else local.setForwardManagerTestRemoteProfileID(value);
			break;
			

		case(EMAYOR_SERVICE_INVOKER_ENDPOINT):
			if (action && READ_CONFIG) result = local.getEMayorServiceInvokerEndpoint();
			else local.setEMayorServiceInvokerEndpoint(value);
			break;
			

		case(EMAYOR_ADMIN_INTERFACE_IS_ENABLED):
			if (action && READ_CONFIG) result = local.getEMayorAdminInterfaceIsEnabled();
			else local.setEMayorAdminInterfaceIsEnabled(value);
			break;
			
			
		case(EMAYOR_ADMIN_INTERFACE_USERID):
			if (action && READ_CONFIG) result = local.getEMayorAdminInterfaceUserID();
			else local.setEMayorAdminInterfaceUserID(value);
			break;
			
			
		case(EMAYOR_ADMIN_INTERFACE_PASSWORD):
			if (action && READ_CONFIG) result = local.getEMayorAdminInterfacePassword();
			else local.setEMayorAdminInterfacePassword(value);
			break;
			
			
		case(BPEL_ENGINE_UT_INITIAL_CONTEXT_FACTORY):
			if (action && READ_CONFIG) result = local.getBpelEngineUtInitialContextFactory();
			else local.setBpelEngineUtInitialContextFactory(value);
			break;
			
			
		case(BPEL_ENGINE_UT_SECURITY_PRINCIPAL):
			if (action && READ_CONFIG) result = local.getBpelEngineUtSecurityPrincipal();
			else local.setBpelEngineUtSecurityPrincipal(value);
			break;
			
			
		case(BPEL_ENGINE_UT_SECURITY_CREDENTIALS):
			if (action && READ_CONFIG) result = local.getBpelEngineUtSecurityCredentials();
			else local.setBpelEngineUtSecurityCredentials(value);
			break;
			
			
		case(BPEL_ENGINE_UT_PROVIDER_URL):
			if (action && READ_CONFIG) result = local.getBpelEngineUtProviderURL();
			else local.setBpelEngineUtProviderURL(value);
			break;
			
			
		case(BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME):
			if (action && READ_CONFIG) result = local.getBpelEngineUtSecurityDomain();
			else local.setBpelEngineUtSecurityDomain(value);
			break;
			
			
		case(BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD):
			if (action && READ_CONFIG) result = local.getBpelEngineUtSecurityDomainPassword();
			else local.setBpelEngineUtSecurityDomainPassword(value);
			break;
			

		case(EMAYOR_PE_INFO_DIR):
			if (action && READ_CONFIG) result = local.getEMayorPeInfoDir();
			else local.setEMayorPeInfoDir(value);
			break;
			
			
		case(EMAYOR_NOTIFICATION_EMAIL_SMTP_HOST):
			if (action && READ_CONFIG) result = local.getEMayorNotificationEmailSMTPHost();
			else local.setEMayorNotificationEmailSMTPHost(value);
			break;
			
			
		case(EMAYOR_NOTIFICATION_EMAIL_SMTP_USER):
			if (action && READ_CONFIG) result = local.getEMayorNotificationEmailSMTPUser();
			else local.setEMayorNotificationEmailSMTPUser(value);
			break;
			
			
		case(EMAYOR_NOTIFICATION_EMAIL_SMTP_PASS):
			if (action && READ_CONFIG) result = local.getEMayorNotificationEmailSMTPPass();
			else local.setEMayorNotificationEmailSMTPPass(value);
			break;
			
			
		case(EMAYOR_NOTIFICATION_EMAIL_SMTP_AUTH):
			if (action && READ_CONFIG) result = local.getEMayorNotificationEmailSMTPAuth();
			else local.setEMayorNotificationEmailSMTPAuth(value);
			break;
		
		case(EMAYOR_CONTENT_ROUTING_LOCAL_INQUIRY):
			if (action && READ_CONFIG) result = local.getEMayorContentRoutingLocalInquiryURL();
			else local.setEMayorContentRoutingLocalInquiryURL(value);
			break;			
		
		case(EMAYOR_CONTENT_ROUTING_REMOTE_INQUIRY):
			if (action && READ_CONFIG) result = local.getEMayorContentRoutingRemoteInquiryURL();
			else local.setEMayorContentRoutingRemoteInquiryURL(value);
			break;						

		case(EMAYOR_CONTENT_ROUTING_LOCAL_PUBLISH):
			if (action && READ_CONFIG) result = local.getEMayorContentRoutingLocalPublishURL();
			else local.setEMayorContentRoutingLocalPublishURL(value);
			break;									
			
		case(EMAYOR_CONTENT_ROUTING_USERID):
			if (action && READ_CONFIG) result = local.getEMayorContentRoutingUserID();
			else local.setEMayorContentRoutingUserID(value);
			break;
			
		case(EMAYOR_CONTENT_ROUTING_PASSWORD):
			if (action && READ_CONFIG) result = local.getEMayorContentRoutingPassword();
			else local.setEMayorContentRoutingPassword(value);
			break;
		
		case(CONFIG_IS_ACTIVE):
			if (action && READ_CONFIG) result = local.getIsActive().toString();
			else local.setIsActive(Boolean.valueOf(value));
			break;
		
		case(EMAYOR_PE_CRL_DISTRIBUTION_URL):
			if (action && READ_CONFIG) result = local.getEMayorPeCrlDistributionURL();
			else local.setEMayorPeCrlDistributionURL(value);
			break;
		
		case(EMAYOR_PE_CRL_USE_DEFAULT_DISTRIBUTION_URL):
			if (action && READ_CONFIG) result = local.getEMayorPeCrlUseDefaultDistributionURL().toString();
			else local.setEMayorPeCrlUseDefaultDistributionURL(Boolean.valueOf(value));
			break;
			
			
		}
		
		
		if (action && READ_CONFIG && result == null) {
			log.debug("using a default value: " + value);
			result = value;
		}
		
		if (action && READ_CONFIG)
			log.debug("property request ("+local.getConfigID()+") [ID:"+propID+"="+result+"]");
		
		log.debug("-> ... processing DONE!");
		return result;
	}	

	public synchronized String getQuilifiedDirectoryName(String dirName) {
		StringBuffer b = new StringBuffer(JBOSS_HOME_DIR);
		b.append(File.separator).append(dirName);
		return b.toString();
	}
	
	public synchronized String getTmpDir() {
		String tmpDir = null;
	    try {
			tmpDir = getProperty(EMAYOR_TMP_DIR);
		} catch (ConfigException e) {
			tmpDir = "";
		}
		return tmpDir;
	}
	
	private final void loadConfiguration() throws ConfigException {
		setProperty(EMAYOR_PLATFORM_INSTANCE_ID,"Aachen");
		setProperty(EMAYOR_MUNICIPALITY_NAME,"Municipality of Aachen");
		setProperty(EMAYOR_OPERATING_MODE,"production");
		setProperty(EMAYOR_OPERATING_MODE_FORWARD,"test");
		setProperty(EMAYOR_TMP_DIR,"tmp/emayor");
		setProperty(EMAYOR_OPERATING_MODE_EMAIL,"test");
		setProperty(EMAYOR_EMAIL_TEST_USER_ADDRESS,"eMayor.User@localhost");
		setProperty(FORWARD_MANAGER_QUEUE_HOST,"localhost:1099");
		setProperty(FORWARD_MANAGER_QUEUE_NAME,"queue/testQueue");
		setProperty(EMAYOR_OPERATING_MODE_CONTENT_ROUTING,"test");
		setProperty(FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_NAME,"Aachen");
		setProperty(FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_NAME,"Bozen");
		setProperty(FORWARD_MANAGER_TEST_LOCAL_MUNICIPALITY_ADDRESS,"https://localhost:8443/ForwardEJB/IForward");
		setProperty(FORWARD_MANAGER_TEST_REMOTE_MUNICIPALITY_ADDRESS,"https://localhost:18443/ForwardEJB/IForward");
		setProperty(FORWARD_MANAGER_TEST_REMOTE_PROFILE_ID,"server2");
		setProperty(EMAYOR_SERVICE_INVOKER_ENDPOINT,"http://localhost:9700/orabpel/default/eMayorServiceStarter_v10/1.0");
		setProperty(EMAYOR_ADMIN_INTERFACE_IS_ENABLED,"YES");
		setProperty(EMAYOR_ADMIN_INTERFACE_USERID,"admin");
		setProperty(EMAYOR_ADMIN_INTERFACE_PASSWORD,"admin123");
		setProperty(BPEL_ENGINE_UT_INITIAL_CONTEXT_FACTORY,"com.evermind.server.rmi.RMIInitialContextFactory");
		setProperty(BPEL_ENGINE_UT_SECURITY_PRINCIPAL,"admin");
		setProperty(BPEL_ENGINE_UT_SECURITY_CREDENTIALS,"welcome");
		setProperty(BPEL_ENGINE_UT_PROVIDER_URL,"rmi://localhost/UTWrapperApp");
		setProperty(BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME,"default");
		setProperty(BPEL_ENGINE_UT_SECURITY_DOMAIN_PASSWORD,"bpel");
		setProperty(EMAYOR_PE_INFO_DIR,"conf/policies");
		setProperty(EMAYOR_NOTIFICATION_EMAIL_SMTP_HOST,"localhost");
		setProperty(EMAYOR_NOTIFICATION_EMAIL_SMTP_USER,"eMayor.User");
		setProperty(EMAYOR_NOTIFICATION_EMAIL_SMTP_PASS,"emayor");
		setProperty(EMAYOR_NOTIFICATION_EMAIL_SMTP_AUTH,"true");
		setProperty(EMAYOR_CONTENT_ROUTING_LOCAL_INQUIRY,"http://localhost:28080/juddi/inquiry");
		setProperty(EMAYOR_CONTENT_ROUTING_LOCAL_PUBLISH,"http://localhost:28080/juddi/publish");
		setProperty(EMAYOR_CONTENT_ROUTING_REMOTE_INQUIRY,"http://localhost:28080/juddi/inquiry");
		setProperty(EMAYOR_CONTENT_ROUTING_USERID,"juddi");
		setProperty(EMAYOR_CONTENT_ROUTING_PASSWORD,"juddi");
		setProperty(CONFIG_IS_ACTIVE,Boolean.TRUE.toString());
		setProperty(EMAYOR_PE_CRL_DISTRIBUTION_URL,"https://testvalueForCRL/");
		setProperty(EMAYOR_PE_CRL_USE_DEFAULT_DISTRIBUTION_URL,Boolean.TRUE.toString());
	}
	
	public synchronized Set getConfigNames() {
		
		Set result = new HashSet();
		
		try {
			Collection c = home.findAll();
			Iterator i = c.iterator();
			PlatformConfigurationEntityLocal temp;
			while (i.hasNext()) {
				temp = (PlatformConfigurationEntityLocal) i.next();
				result.add(temp.getConfigID());
			}
		} catch (FinderException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public synchronized Properties getAllProperties() {
		log.debug("-> start processing ...");
		Properties result = new Properties();
		String property;
		for (Enumeration e = old2new.keys(); e.hasMoreElements();) {
			property = (String) e.nextElement();
			/* don´t push active state in properties */
			if (!property.equals("config.is.active")) {
				try {
					result.setProperty(property,getProperty(property));
				} catch (ConfigException e1) {
				}
			}
		}
		log.debug("-> processing DONE ...");
		return result;
	}
	
	/*
	 * switch to existing config
	 * (and create if not exists)
	 */
	public boolean switchConfig(String configID) {
		boolean result = false;
		
		PlatformConfigurationEntityLocal oldLocal = local;
		
		try {
			this.local = home.create(configID);
			loadConfiguration();
			init();
			oldLocal.setIsActive(Boolean.FALSE);
			this.configID = configID;
			result = true;
		} catch (CreateException e) {
			try {
				this.local = home.findByPrimaryKey(configID);
				init();
				oldLocal.setIsActive(Boolean.FALSE);
				this.local.setIsActive(Boolean.TRUE);
				this.configID = configID;
				result = true;
			} catch (FinderException e1) {
				this.local = oldLocal;
			}
		} catch (ConfigException e) {}
		
		return result;
	}
	
	/*
	 * create new config
	 */
	public boolean createConfig(String configID, Properties configValues) throws ConfigException {
		boolean result = true;
		
		PlatformConfigurationEntityLocal tempLocal;
		String key, value;
		int propID;
		
		try {
			tempLocal = home.create(configID);
		} catch (CreateException e) {
			try {
				tempLocal = home.findByPrimaryKey(configID);
			} catch (FinderException e1) {
				throw new ConfigException("create failed: "+e1.getMessage(),e1.getCause());
			}
		}
		
		if (configValues == null) {
			for (Enumeration e = old2new.propertyNames();e.hasMoreElements();) {
				key = (String) e.nextElement();
				propID = Integer.parseInt(old2new.getProperty(key));
				value = getProperty(propID);
				propertyAction(propID,WRITE_CONFIG,value,tempLocal);
			}
		} else {
			for (Enumeration e = configValues.propertyNames();e.hasMoreElements();) {
				key = (String) e.nextElement();
				value = configValues.getProperty(key);
				if (old2new.containsKey(key)) {
					propID = Integer.parseInt(old2new.getProperty(key));
					propertyAction(propID,WRITE_CONFIG,value,tempLocal);	
				}
			}
		}
		
		return result;
	}
	
	public String getConfigID() {
		return this.configID;
	}
	
}