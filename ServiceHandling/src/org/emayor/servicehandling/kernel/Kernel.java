/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.ejb.FinderException;

import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.C_ServiceProfile;
import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.eMayor.PolicyEnforcement.E_PolicyEnforcementException;
import org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcementLocal;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionBeanEntityLocalHome;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocal;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardBPELCallbackData;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class Kernel implements IKernel {
	private static Logger log = Logger.getLogger(Kernel.class);

	private SimpleIdGeneratorLocal idGen = null;

	private KernelRepository repository;

	//private PolicyEnforcement pe;
	private PolicyEnforcementLocal pe;

	private static Kernel _self = null;

	private Kernel() throws KernelException {
		log.debug("-> start processing ...");
		this.repository = new KernelRepository();
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			this.idGen = locator.getSimpleIdGeneratorLocal();
			if (this.idGen == null)
				log.warn("the ref to the id gen is NULL!!!!");
			else
				log.debug("got the id gen ref");
			this.pe = locator.getPolicyEnforcementLocal();
			if (pe == null)
				log.warn("the reference to the policy enforcer is NULL!!!!");
			Config config = Config.getInstance();
			if (config.getProperty(Config.EMAYOR_OPERATING_MODE).equals(
					"production")) {
				log.info("working with the production data - production mode");
				//this.initDeployedServices();
			} else {
				log.info("working with the internal test data - test mode");
				this.initTestData();
			}
		} catch (ServiceLocatorException ex) {
			log.error("caught ex:" + ex.toString());
			throw new KernelException("couldn't create a id generator");
		} catch (ConfigException confex) {
			log.error("caught ex:" + confex.toString());
			throw new KernelException("couldn't read the configuration");
		}
		log.debug("initialize the factories ...");
		this.initializeServiceFactories();
		log.debug("-> ... processing DONE!");
	}

	static {
		log.debug("<<<<<<<static initializer:>>>>>>>>>>>>");
	}

	/**
	 * Obtaining an instance of the Kernel class (singelton). If the internal
	 * variable keeping reference to the kernel object is null the new one will
	 * be created.
	 * 
	 * @return and instance of the Kerel class
	 * @throws KernelException
	 */
	public static final synchronized Kernel getInstance()
			throws KernelException {
		log.debug("-> start processing ...");
		if (_self == null) {
			_self = new Kernel();
			log.debug("initialize all existed service sessions");
			_self.initializeServiceSessions();
		}
		return _self;
	}

	/**
	 * Creating a new instance of the Access Session. Note the session is not
	 * active (started) yet.
	 * 
	 * @return the id of the newly created Access Session
	 * @throws KernelException
	 *             in case of eception an instance of the KernelException will
	 *             be thrown
	 */
	public synchronized String createAccessSession() throws KernelException {
		log.debug("-> start processing ...");
		String ret = null;
		try {
			log.debug("getting instance of ServiceLocator");
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			log.debug("getting instance of AccessSessionLocal");
			AccessSessionLocal accessSessionLocal = serviceLocator
					.getAccessSessionLocal();
			log.debug("saving current instance of AccessSessionLocal");
			this.repository.addAccessSession(accessSessionLocal);
			ret = accessSessionLocal.getSessionId();
		} catch (ServiceLocatorException slex) {
			log.error("caught ex: " + slex.toString());
			throw new KernelException(slex.toString());
		} catch (KernelRepositoryException kex) {
			log.error("caught ex: " + kex.toString());
			throw new KernelException(
					"Couldn' store the new access session into repository!");
		} catch (SessionException sex) {
			log.error("caught ex: " + sex.toString());
			throw new KernelException(sex);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Obtaining the reference to the local interface of an existing Service
	 * Session.
	 * 
	 * @param asid
	 *            the id of required Access Session
	 * @return reference pointing to the local interface of the required Service
	 *         Session
	 * @throws KernelException
	 */
	public synchronized AccessSessionLocal getAccessSession(String asid)
			throws KernelException {
		log.debug("-> start processing ...");
		AccessSessionLocal ret = null;
		try {
			ret = this.repository.getAccessSession(asid);
			log.debug("got the access session from repository");
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException(
					"Couldn' get the specified access session from repository!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Obtaining information about a required Access Session.
	 * 
	 * @param asid
	 * @return an instance of the AccessSessionInfo class containing the
	 *         information about specified Access Session
	 * @throws KernelException
	 */
	public synchronized AccessSessionInfo getAccessSessionInfo(String asid)
			throws KernelException {
		log.debug("-> start processing ...");
		AccessSessionInfo ret = null;
		try {
			AccessSessionLocal _as = this.getAccessSession(asid);
			ret = new AccessSessionInfo();
			ret.setSessionId(_as.getSessionId());
			ret.setStartDate(_as.getStartDate());
			ret.setUserId(_as.getUserId());
		} catch (AccessSessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Unable to get the service session data!");
		} catch (SessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Unable to get the service session data!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Deleting the instance of the Access Session stored in the repository.
	 * 
	 * @param asid
	 *            the id of the instance of the Access Session to be deleted
	 * @throws KernelException
	 */
	public synchronized boolean deleteAccessSession(String asid)
			throws KernelException {
		log.debug("-> start processing ...");
		boolean ret = false;
		try {
			this.repository.removeAccessSession(asid);
			ret = true;
		} catch (KernelRepositoryException kex) {
			log.error("caught ex: " + kex.toString());
			throw new KernelException(
					"Couldn' remove the access session from repository!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Creating a new instance of the Service Session. Note the session is not
	 * active (started) yet.
	 * 
	 * @param asid
	 *            the id of the access session will own the instance of the
	 *            Service Session to be created
	 * @param serviceId
	 *            the id of the service will be hosted by the instance of the
	 *            Service Session to be created
	 * @param userId
	 *            the id of the user is about to create the instance of the
	 *            Service Session
	 * @return the reference to the local interface of the newly created Service
	 *         Session
	 * @throws KernelException
	 *             in case of eception an instance of the KernelException will
	 *             be thrown
	 */
	public synchronized ServiceSessionLocal createServiceSession(String asid,
			String serviceId, String userId) throws KernelException {
		//      ############## added by Sergiu :) ############
		boolean bAccessResult = false;
		log.debug("Get Service Access Permition ...");
		try {

			bAccessResult = this.pe.F_AuthorizeService(this.getUserProfile(
					userId).getPEUserProfile().F_getUserProfileasString(),
					serviceId);
		} catch (Exception e) {
			throw new KernelException(e.toString());
		}

		log.debug("Got Permition = " + bAccessResult);
		if (bAccessResult == false) {
			throw new KernelException("Access to the Service " + serviceId
					+ " is not allowed!");
		} else {

			// ########### end of added by Sergiu :) ############

			ServiceSessionLocal ret = null;
			try {
				log.debug("getting instance of ServiceLocator");
				ServiceLocator serviceLocator = ServiceLocator.getInstance();
				log.debug("a new service session");
				ret = serviceLocator.getServiceSessionLocal(asid);
				if (log.isDebugEnabled() && ret != null)
					log.debug("the new ssid = " + ret.getSessionId());
				ret.setCreatorId(userId);
				log.debug("set service id into service session instance");
				ret.setServiceId(serviceId);
				log.debug("get the factory for the given service");
				IeMayorServiceFactory factory = this.repository
						.getServiceFactory(serviceId);
				log.debug("create the service instance using got factory");
				IeMayorService service = factory.createService(serviceId, ret
						.getSessionId());
				log.debug("call setup method on the service instance");
				service.setup(serviceId);
				log.debug("assign the service to the service session");
				ret.seteMayorService(service);
				log.debug("save the current instance into repository");
				this.repository.addServiceSession(ret, userId);
			} catch (ServiceLocatorException slex) {
				log.error("caught ex: " + slex.toString());
				throw new KernelException(slex.toString());
			} catch (SessionException sex) {
				log.error("caught ex: " + sex.toString());
			} catch (KernelRepositoryException krex) {
				log.error("caught ex: " + krex.toString());
			} catch (eMayorServiceException emsex) {
				log.error("caught ex: " + emsex.toString());
			}
			log.debug("-> ... processing DONE!");
			return ret;
		}
	}

	/**
	 *  
	 */
	public synchronized ServiceSessionLocal getServiceSession(String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		ServiceSessionLocal ret = null;
		try {
			ret = this.repository.getServiceSession(ssid);
			log.debug("got the access session from repository");
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException(
					"Couldn' get the specified service session from repository!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 *  
	 */
	public synchronized ServiceSessionInfo getServiceSessionInfo(String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		ServiceSessionInfo ret = null;
		try {
			ServiceSessionLocal _ss = this.getServiceSession(ssid);
			ret = new ServiceSessionInfo();
			ret.setAsid(_ss.getAccessSessionId());
			ret.setServiceId(_ss.getServiceId());
			ret.setSessionId(_ss.getSessionId());
			ret.setStartDate(_ss.getStartDate());
		} catch (ServiceSessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Unable to get the service session data!");
		} catch (SessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Unable to get the service session data!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 *  
	 */
	public synchronized boolean deleteServiceSession(String asid, String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		boolean ret = false;
		try {
			if (asid == null) {
				log.info("the asid was null -> using the admin delete method!");
				this.repository.adminRemoveServiceSession(ssid);
				ret = true;
			} else {
				log.info("tha asid wasn't null, asid=" + asid);
				String userId = this.getUserIdByASID(asid);
				if (log.isDebugEnabled())
					log.debug("got uid from repository: " + userId);
				this.repository.removeServiceSession(ssid, userId);
				ret = true;
			}
		} catch (KernelRepositoryException kex) {
			log.error("caught ex: " + kex.toString());
			throw new KernelException(
					"deleteServiceSession failed: problem with kernel repository");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 *  
	 */
	public synchronized ServiceInfo[] listAvailableServices(
			C_UserProfile userProfile) throws KernelException {
		log.debug("-> start processing ...");
		ServiceInfo[] ret = new ServiceInfo[0];
		try {
			ret = this.repository.listServicesInfo();
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Couldn't get the service info data!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 *  
	 */
	public synchronized ServiceInfo[] listAllAvailableServices()
			throws KernelException {
		log.debug("-> start processing ...");
		ServiceInfo[] ret = new ServiceInfo[0];
		try {
			ret = this.repository.listServicesInfo();
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("listing of services info failed");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 *  
	 */
	public synchronized ServiceInfo[] listAllActiveServices()
			throws KernelException {
		log.debug("-> start processing ...");
		ServiceInfo[] ret = new ServiceInfo[0];
		try {
			ServiceInfo[] services = this.repository.listServicesInfo();
			List list = new ArrayList();
			for (int i = 0; i < services.length; i++) {
				if (log.isDebugEnabled())
					log.debug("ACTIVE STATUS OF CURRENT SERVICE: "
							+ services[i].getActive());
				if (services[i].isActive()) {
					if (log.isDebugEnabled())
						log.debug("found next service with id: "
								+ services[i].getServiceId());
					list.add(services[i]);
				}
			}
			ret = new ServiceInfo[list.size()];
			int index = 0;
			for (Iterator i = list.iterator(); i.hasNext();) {
				ret[index++] = (ServiceInfo) i.next();
			}
			if (log.isDebugEnabled()) {
				log.debug("!!! got " + ret.length + " items !!!");
			}
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("listing of active services info failed");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 *  
	 */
	public synchronized String getUserIdByASID(String asid)
			throws KernelException {
		log.debug("-> start processing ...");
		String ret = "defid";
		try {
			ret = this.repository.getUserIdByAsid(asid);
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException("problem with getting user id");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	private void initTestData() {
		try {
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setServiceId("ResidenceCertificationService/v10");
			serviceInfo.setServiceVersion("1.0");
			serviceInfo.setServiceName("Residence Certification Service");
			serviceInfo
					.setServiceFactoryClassName("org.emayor.servicehandling.kernel.eMayorServiceFactory");
			serviceInfo
					.setServiceClassName("org.emayor.rcs.ResidenceCertificationService");
			serviceInfo.setServiceDescription("Description of the service 1.");
			this.repository.addServiceInfo(serviceInfo);
			serviceInfo = new ServiceInfo();
			serviceInfo.setServiceId("Service2/v10");
			serviceInfo.setServiceVersion("1.0");
			serviceInfo.setServiceName("service 2");
			serviceInfo
					.setServiceFactoryClassName("org.emayor.servicehandling.kernel.eMayorServiceFactory");
			serviceInfo.setServiceClassName("org.emayor.service.Service2");
			serviceInfo.setServiceDescription("Description of the service 2.");
			this.repository.addServiceInfo(serviceInfo);
			serviceInfo = new ServiceInfo();
			serviceInfo.setServiceId("Service3/v10");
			serviceInfo.setServiceVersion("1.0");
			serviceInfo.setServiceName("service 3");
			serviceInfo
					.setServiceFactoryClassName("org.emayor.servicehandling.kernel.eMayorServiceFactory");
			serviceInfo.setServiceClassName("org.emayor.service.Service3");
			serviceInfo.setServiceDescription("Description of the service 3.");
			this.repository.addServiceInfo(serviceInfo);

		} catch (KernelRepositoryException kex) {
			log.error("caught ex:" + kex.toString());
		}
	}

	private void initializeServiceFactory(String serviceId, String clazzName)
			throws KernelException {
		log.debug("-> start processing ...");
		try {
			IeMayorServiceFactory factory = this.repository
					.loadServiceFactory(serviceId);
			if (factory != null)
				log.debug("factory reference is NOT null");
			else
				throw new KernelException(
						"Couldn't load the service factory for service with id: "
								+ serviceId);
			this.repository.addServiceFactory(serviceId, factory);
		} catch (KernelRepositoryException kex) {
			log.error("caught ex: " + kex.toString());
			throw new KernelException(
					"the new factory couldn't be saved into repository");
		}
	}

	private void initializeServiceFactories() throws KernelException {
		log.debug("-> start processing ...");
		ServiceInfo[] services = this.listAllAvailableServices();
		if (log.isDebugEnabled())
			log.debug("found " + services.length + " deployed services!");
		for (int i = 0; i < services.length; i++) {
			String serviceId = services[i].getServiceId();
			if (log.isDebugEnabled())
				log.debug("got service name: " + serviceId);
			String className = services[i].getServiceFactoryClassName();
			this.initializeServiceFactory(serviceId, className);
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 *  
	 */
	public synchronized String getServiceClassNameByServiceName(
			String serviceName) throws KernelException {
		log.debug("-> start processing ...");
		String ret = null;
		try {
			ServiceInfo info = this.repository.getServiceInfo(serviceName);
			ret = info.getServiceClassName();
		} catch (KernelRepositoryException kex) {
			log.debug("caght ex: " + kex.toString());
			throw new KernelException("unknown service name");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 *  
	 */
	public synchronized IUserProfile getUserProfile(String userId)
			throws KernelException {
		log.debug("-> start processing ...");
		IUserProfile ret = null;
		try {
			Config config = Config.getInstance();
			String mode = config.getProperty(
					Config.EMAYOR_OPERATING_MODE_EMAIL, "production");
			String email = config.getProperty(
					Config.EMAYOR_EMAIL_TEST_USER_ADDRESS,
					"eMayor.User@localhost");
			ret = this.repository.getUserProfile(userId);
			if (ret == null) {
				log.error("unknown user id -> profile doesn't exist");
				throw new KernelException(
						"UserProfile doesn't exist for the user with id: "
								+ userId);
			}
			if (mode.equals("production")) {
				log.debug("production mode");
			} else {
				// for testing purposes the email address has been
				// stored in the certificate has to be replaced
				// by a local one
				log.info("working in test mode -> set the test email address");
				ret.getPEUserProfile().setUserEmail(email);
			}
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException(
					"problem with obtaining the user profile from repository");
		} catch (ConfigException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"problem with obtaining the user profile from repository");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 *  
	 */
	public synchronized IServiceProfile getServiceProfile(String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		IServiceProfile ret = null;
		String serviceId = null;
		try {
			log.debug("get the service session");
			ServiceSessionLocal serviceSession = this.getServiceSession(ssid);
			serviceId = serviceSession.getServiceId();
			if (log.isDebugEnabled())
				log.debug("got the service id = " + serviceId);
			ret = this.getServiceProfileByServiceId(serviceId);
		} catch (ServiceSessionException ssex) {
			log.error("caught ex: " + ssex.toString());
			throw new KernelException("couldn't obtain the service profile");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 *  
	 */
	public synchronized IServiceProfile getServiceProfileByServiceId(
			String serviceId) throws KernelException {
		log.debug("-> start processing ...");
		IServiceProfile ret = null;
		try {
			if (log.isDebugEnabled())
				log.debug("got the service id = " + serviceId);
			log.debug("get the service info from repository");
			IServiceInfo serviceInfo = this.repository
					.getServiceInfo(serviceId);
			log.debug("create the policy enforcer service profile");
			C_ServiceProfile serviceProfile = new C_ServiceProfile();

			ret = new ServiceProfile();
			ret.setServiceInfo(serviceInfo);
			ret.setPEServiceProfile(serviceProfile);
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException("couldn't obtain the service profile");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Authenticating the given user.
	 * 
	 * @param asid
	 * @param certificates
	 * @return
	 * @throws KernelException
	 */
	public synchronized String authenticateUser(String asid,
			X509Certificate[] certificates) throws KernelException {
		log.debug("-> start processing ...");
		String ret = null;
		String userId = null;
		log.debug("try to get the user profile from policy enforcer");
		try {
			// PE: get user profile
			String cUserProfileStr = this.pe.F_getUserProfile(certificates);
			if (cUserProfileStr == null || cUserProfileStr.length() == 0) {
				throw new KernelException("Couldn't authenticate given user!");
			} else {
				if (log.isDebugEnabled())
					log.debug("got C_UserProfile as string: \n"
							+ cUserProfileStr);
				log.debug("trying to get C_UserProfile as object");
				C_UserProfile userProfile = new C_UserProfile(cUserProfileStr);
				userId = String.valueOf(certificates[0].hashCode());
				if (!this.repository.existUserProfile(userId)) {
					IUserProfile up = new UserProfile();
					up.setUserId(String.valueOf(certificates[0].hashCode()));
					up.setPEUserProfile(userProfile);
					repository.addUserProfile(up);
				} else {
					log.debug("the user already exists in the repository");
				}
				log.debug("try to authenticate the user!");
				// PE: authenticate user by using the user profile
				if (this.pe.F_AuthenticateUser(cUserProfileStr)) {
					if (certificates != null) {
						log.debug(">>>>>>>>>>> got user name: "
								+ userProfile.getUserName());
						log.debug(">>>>>>>>>>> got user mail: "
								+ userProfile.getUserEmail());
					}
					ret = userId;
					if (log.isDebugEnabled())
						log.debug("returning the user id : " + ret);
				} else {
					ret = null;
				}
			}
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException(
					"authenticateUser failed: problem with repository by handling user profile");
		} catch (E_PolicyEnforcementException pex) {
			log.error("caught ex: " + pex.toString());
			throw new KernelException(
					"authenticateUser failed: problem with policy enforcer");
		} catch (C_UserProfile.E_UserProfileException upex) {
			log.error("caught ex: " + upex.toString());
			throw new KernelException(
					"authenticateUser failed: problem with user profile transformation");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Obtaining the Service Sessions of the user given by the id.
	 * 
	 * @param userId
	 *            the id of the use has started the sessions
	 * @return an array containing the references of the all Service Sessions
	 *         started by the specified user
	 * @throws KernelException
	 */
	public synchronized ServiceSessionLocal[] getUsersServiceSessions(
			String userId) throws KernelException {
		log.debug("-> start processing ...");
		ServiceSessionLocal[] ret = new ServiceSessionLocal[0];
		try {
			ret = this.repository.getAllServiceSessions(userId);
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException(
					"getUsersServiceSessions failed: problem with the kernel repository");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Obtaining the Access Session Id corresponding the the given user id.
	 * 
	 * @param userId
	 * @return
	 * @throws KernelException
	 */
	public synchronized String getAsidByUserID(String userId)
			throws KernelException {
		log.debug("-> start processing ...");
		if (log.isDebugEnabled())
			log.debug("working with following userId: " + userId);
		String ret = "defid";
		try {
			ret = this.repository.getAsidByUserId(userId);
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException("problem with getting asid");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Storing the forward callback data in the repository.
	 * 
	 * @param data
	 * @throws KernelException
	 */
	public synchronized void addForwardBPELCallbackData(
			ForwardBPELCallbackData data) throws KernelException {
		log.debug("-> start processing ...");
		try {
			this.repository.addForwardBPELCallbackData(data);
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException(
					"Couldn't add the specified callback data to repository!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Restoring the forward callback data from the repository.
	 * 
	 * @param ssid
	 * @return
	 * @throws KernelException
	 */
	public synchronized ForwardBPELCallbackData getForwardBPELCallbackData(
			String ssid) throws KernelException {
		log.debug("-> start processing ...");
		ForwardBPELCallbackData ret = null;
		try {
			ret = this.repository.getForwardBPELCallbackData(ssid);
			log.debug("got the callback data from repository");
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException(
					"Couldn't get the specified callback data from repository!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	private void initDeployedServices() throws KernelException {
		log.debug("-> start processing ...");
		try {
			Config config = Config.getInstance();
			String deployDir = config.getQuilifiedDirectoryName(config
					.getProperty("emayor.service.info.dir"));
			if (log.isDebugEnabled())
				log.debug("working with following deploy dir: " + deployDir);
			File file = new File(deployDir);
			File[] files = file.listFiles(new ServiceInfoFilenameFilter());
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					String filename = files[i].getAbsolutePath();
					if (log.isDebugEnabled())
						log.debug("working with filename: " + filename);
					Properties props = new Properties();
					props.load(new FileInputStream(files[i]));
					ServiceInfo serviceInfo = new ServiceInfo();
					if (!serviceInfo.unmarshall(props)) {
						log
								.error("couldn't unmarshall the service info properly");
						throw new KernelException(
								"couldn't unmarshall the service info properly, filename="
										+ filename);
					} else {
						this.repository.addServiceInfo(serviceInfo);
					}
				}
			} else {
				log.debug("the files array is NULL");
			}
		} catch (ConfigException confex) {
			log.error("caught ex: " + confex.toString());
			throw new KernelException(
					"couldn't read the configuration file properly");
		} catch (IOException ioex) {
			log.error("caught ex: " + ioex.toString());
			throw new KernelException(
					"couldn't read the service properties properly");
		} catch (KernelRepositoryException kex) {
			log.error("caught ex:" + kex.toString());
			throw new KernelException(
					"couldn't store a service info into repository");
		}
		log.debug("-> ... processing DONE!");
	}

	private class ServiceInfoFilenameFilter implements FilenameFilter {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
		 */
		public boolean accept(File dir, String filename) {
			log.debug("-> start processing ...");
			if (log.isDebugEnabled()) {
				log.debug("current dir     : " + dir.toString());
				log.debug("current filename: " + filename);
			}
			boolean ret = false;
			if (filename != null && filename.endsWith("service")) {
				ret = true;
			}
			return ret;
		}
	}

	/**
	 * Reloading the information about the deployed services from repository.
	 * 
	 * @throws KernelException
	 */
	public synchronized void reloadDeployedServices() throws KernelException {
		log.debug("-> start processing ...");
		try {
			this.repository.emptyServiceInfo();
			//this.initDeployedServices();
			this.initializeServiceFactories();
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't reload the services - repository problem!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Listing all Access Sessions currently stored in the repository.
	 * 
	 * @return
	 * @throws KernelException
	 */
	public synchronized AccessSessionInfo[] listAccessSessions()
			throws KernelException {
		log.debug("-> start processing ...");
		AccessSessionInfo[] ret = new AccessSessionInfo[0];
		try {
			AccessSessionLocal[] ss = this.repository.getAllAccessSessions();
			ret = new AccessSessionInfo[ss.length];
			for (int i = 0; i < ss.length; i++) {
				AccessSessionLocal asl = ss[i];
				AccessSessionInfo info = new AccessSessionInfo();
				info.setSessionId(asl.getSessionId());
				info.setUserId(asl.getUserId());
				ret[i] = info;
			}
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't connect to the kernel repository!");
		} catch (AccessSessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Couldn't get the user id!");
		} catch (SessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Couldn't get the access session id!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Listing all Service Session currently stored in the repository.
	 * 
	 * @return
	 * @throws KernelException
	 */
	public synchronized ServiceSessionInfo[] listServiceSessions()
			throws KernelException {
		log.debug("-> start processing ...");
		ServiceSessionInfo[] ret = new ServiceSessionInfo[0];
		try {
			ServiceSessionLocal[] ss = this.repository.listAllServiceSessions();
			ret = new ServiceSessionInfo[ss.length];
			for (int i = 0; i < ss.length; i++) {
				ServiceSessionLocal ssl = ss[i];
				ServiceSessionInfo info = new ServiceSessionInfo();
				info.setAsid(ssl.getAccessSessionId());
				info.setServiceId(ssl.getServiceId());
				info.setSessionId(ssl.getSessionId());
				ret[i] = info;
			}
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't connect to the kernel repository!");
		} catch (ServiceSessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Couldn't get the service session data!");
		} catch (SessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Couldn't get the session id!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Listing the information about all Service Sessions stored currently in
	 * the repository belonging to the specified user.
	 * 
	 * @param uid
	 * @return
	 * @throws
	 */
	public synchronized ServiceSessionInfo[] listServiceSessions(String uid)
			throws KernelException {
		log.debug("-> start processing ...");
		ServiceSessionInfo[] ret = new ServiceSessionInfo[0];
		try {
			ServiceSessionLocal[] ss = this.repository
					.getAllServiceSessions(uid);
			ret = new ServiceSessionInfo[ss.length];
			for (int i = 0; i < ss.length; i++) {
				ServiceSessionLocal ssl = ss[i];
				ServiceSessionInfo info = new ServiceSessionInfo();
				info.setAsid(ssl.getAccessSessionId());
				info.setServiceId(ssl.getServiceId());
				info.setSessionId(ssl.getSessionId());
				ret[i] = info;
			}
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't connect to the kernel repository!");
		} catch (ServiceSessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Couldn't get the service session data!");
		} catch (SessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Couldn't get the session id!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Listing the information about all known users to the system.
	 * 
	 * @return
	 * @throws KernelException
	 */
	public synchronized IUserProfile[] listUserProfiles()
			throws KernelException {
		log.debug("-> start processing ...");
		IUserProfile[] ret = new IUserProfile[0];
		try {
			ret = this.repository.listKnownUserProfiles();
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't connect to the kernel repository!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Listing all currently logged in users at the system.
	 * 
	 * @return
	 * @throws KernelException
	 */
	public synchronized IUserProfile[] listLoggedInUsers()
			throws KernelException {
		log.debug("-> start processing ...");
		IUserProfile[] ret = new IUserProfile[0];
		try {
			AccessSessionLocal[] as = this.repository.getAllAccessSessions();
			ret = new IUserProfile[as.length];
			for (int i = 0; i < as.length; i++) {
				String uid = as[i].getUserId();
				ret[i] = this.getUserProfile(uid);
			}
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't connect to the kernel repository!");
		} catch (AccessSessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Couldn't get the access session data!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Delivering the number of active (running) instances of specified service.
	 * 
	 * @param serviceId
	 * @return
	 * @throws KernelException
	 */
	public synchronized String getNumberOfInstances(String serviceId)
			throws KernelException {
		log.debug("-> start processing ...");
		String ret = "0";
		try {
			ret = this.repository.getNumberOfServiceInstances(serviceId);
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException();
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Obtaining a mapping between the service id and number of active instance
	 * of this service.
	 * 
	 * @return
	 * @throws KernelException
	 */
	public synchronized HashMap getNumberOfInstancesMap()
			throws KernelException {
		log.debug("-> start processing ...");
		HashMap ret = new HashMap();
		try {
			ret = this.repository.getNumberOfServiceInstancesMap();
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException();
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Reseting the number of instances of all services to 0.
	 * 
	 * @throws KernelException
	 */
	public synchronized void resetNumberOfInstances() throws KernelException {
		log.debug("-> start processing ...");
		try {
			this.repository.resetNumberOfServiceInstances();
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException();
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Reseting the number of instances of specified service to 0.
	 * 
	 * @param serviceId
	 * @throws KernelException
	 */
	public synchronized void resetNumberOfInstances(String serviceId)
			throws KernelException {
		log.debug("-> start processing ...");
		try {
			this.repository.resetNumberOfServiceInstances(serviceId);
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't reset the number of instances for the given service.");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Changing the status of the specified service.
	 * 
	 * @param serviceId
	 * @param active
	 * @throws KernelException
	 */
	public synchronized void changeServiceStatus(String serviceId,
			boolean active) throws KernelException {
		log.debug("-> start processing ...");
		try {
			ServiceInfo info = this.repository.getServiceInfo(serviceId);
			info.setActive(active);
			this.repository.changeServiceInfo(info);
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't change the status of the given service!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Removing the profile of the specified user.
	 * 
	 * @param uid
	 * @throws KernelException
	 */
	public synchronized void removeUserProfile(String uid)
			throws KernelException {
		log.debug("-> start processing ...");
		try {
			this.repository.removeUserProfile(uid);
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Couldn't remove the given user profile!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Deploying of a nes service. Note! The newly deployed service is not
	 * active yet. It has to be activated explicitly (@see
	 * Kernel#changeServiceStatus(String, boolean)).
	 * 
	 * @param serviceProfile
	 * @throws KernelException
	 */
	public synchronized void deployService(IServiceProfile serviceProfile)
			throws KernelException {
		log.debug("-> start processing ...");
		try {
			this.repository.addServiceInfo((ServiceInfo) serviceProfile
					.getServiceInfo());
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Couldn't deploy the new service!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Undeploying the specified service.
	 * 
	 * @param serviceId
	 * @throws KernelException
	 */
	public synchronized void undeployService(String serviceId)
			throws KernelException {
		log.debug("-> start processing ...");
		try {
			this.repository.removeServiceInfo(serviceId);
			this.repository.removeServiceFactory(serviceId);
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't undeploy the service with serviceId = "
							+ serviceId);
		}
		log.debug("-> ... processing DONE!");
	}

	private void initializeServiceSessions() throws KernelException {
		log.debug("-> start processing ...");
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			ServiceSessionBeanEntityLocalHome home = locator
					.getServiceSessionBeanEntityLocalHome();
			Collection col = home.findAllSsids();
			if (log.isDebugEnabled())
				log.debug("found " + col.size()
						+ " service sessions in the repository");
			for (Iterator i = col.iterator(); i.hasNext();) {
				String ssid = (String) i.next();
				if (log.isDebugEnabled())
					log.debug("restoring service session with ssid: " + ssid);
				ServiceSessionLocal sslocal = locator.getServiceSessionLocal(
						ssid, "asid");
				String serviceId = sslocal.getServiceId();
				String userId = sslocal.getCreatorId();
				log.debug("get the factory for the given service");
				IeMayorServiceFactory factory = this.repository
						.getServiceFactory(serviceId);
				log.debug("create the service instance using got factory");
				IeMayorService service = factory.createService(serviceId, ssid);
				log.debug("call setup method on the service instance");
				service.setup(serviceId);
				log.debug("assign the service to the service session");
				sslocal.seteMayorService(service);
				log.debug("save the current instance into repository");
				if (log.isDebugEnabled()) {
					log.debug("the ssid of current ss is: " + ssid);
					log.debug("serviceId: " + serviceId);
					log.debug("userId   : " + userId);
				}
				this.repository.addServiceSession(sslocal, userId);
			}
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Internal error during location of some local interfaces!");
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't get all exsiting service session ids!");
		} catch (ServiceSessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't access the data of the service session!");
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("Internal kernel repository error!");
		} catch (eMayorServiceException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException(
					"Couldn't create a intance od the service class!");
		}
		log.debug("-> ... processing DONE!");
	}
}