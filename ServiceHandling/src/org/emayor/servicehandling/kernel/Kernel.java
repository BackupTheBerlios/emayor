/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.security.cert.X509Certificate;

import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.C_ServiceProfile;
import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcementLocal;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocal;
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
				log
						.warn("--------------->the re to the policy enforcer is NULL!!!!");
			else
				log.warn("--------------->the pe reference: " + pe.toString());
		} catch (ServiceLocatorException ex) {
			log.error("caught ex:" + ex.toString());
		}
		this.initTestData();
		log.debug("intialize the factories ...");
		this.initializeServiceFactories();
		log.debug("-> ... processing DONE!");
	}

	public static final Kernel getInstance() throws KernelException {
		log.debug("-> start processing ...");
		if (_self == null)
			_self = new Kernel();
		return _self;
	}

	/**
	 *  
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
	 *  
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
	 *  
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#createServiceSession(java.lang.String,
	 *      java.lang.String)
	 */
	public synchronized ServiceSessionLocal createServiceSession(String asid,
			String serviceId) throws KernelException {
		log.debug("-> start processing ...");
		ServiceSessionLocal ret = null;
		try {
			log.debug("getting instance of ServiceLocator");
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			log.debug("a new service session");
			ret = serviceLocator.getServiceSessionLocal(asid);
			if (log.isDebugEnabled() && ret != null)
				log.debug("the new ssid = " + ret.getSessionId());
			IServiceInfo serviceInfo = this.repository
					.getServiceInfo(serviceId);
			IeMayorServiceFactory factory = this.repository
					.getServiceFactory(serviceId);
			IeMayorService service = factory.createService(serviceId, ret
					.getSessionId());
			log.debug("call setup method on the service instance");
			service.setup();
			log.debug("assign the service to the service session");
			ret.seteMayorService(service);
			log.debug("save the curren tinstance into repository");
			this.repository.addServiceSession(ret);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#getServiceSession(java.lang.String)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#deleteServiceSession(java.lang.String)
	 */
	public synchronized boolean deleteServiceSession(String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		boolean ret = false;
		try {
			this.repository.removeServiceSession(ssid);
			ret = true;
		} catch (KernelRepositoryException kex) {
			log.error("caught ex: " + kex.toString());
			throw new KernelException(
					"Couldn' remove the service session from repository!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#listAvailableServices(org.emayor.policyenforcer.C_UserProfile)
	 */
	public synchronized ServiceInfo[] listAvailableServices(
			C_UserProfile userProfile) throws KernelException {
		log.debug("-> start processing ...");
		ServiceInfo[] ret = new ServiceInfo[0];
		try {
			ret = this.repository.listServicesInfo();
		} catch (KernelRepositoryException ex) {

		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#listAllAvailableServices()
	 */
	public synchronized ServiceInfo[] listAllAvailableServices()
			throws KernelException {
		log.debug("-> start processing ...");
		ServiceInfo[] ret = new ServiceInfo[0];
		try {
			ret = this.repository.listServicesInfo();
		} catch (KernelRepositoryException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelException("listing of serices info failed");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public synchronized String getUserIdByASID(String asid)
			throws KernelException {
		log.debug("-> start processing ...");
		String ret = "defid";
		try {
			ret = this.getAccessSession(asid).getUserId();
		} catch (AccessSessionException asex) {
			log.error("caught ex: " + asex.toString());
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

	private void initializeServiceFactories() throws KernelException {
		log.debug("-> start processing ...");
		try {
			ServiceInfo[] services = this.listAllAvailableServices();
			for (int i = 0; i < services.length; i++) {
				String serviceId = services[i].getServiceId();
				if (log.isDebugEnabled())
					log.debug("got service name: " + serviceId);
				String className = services[i].getServiceFactoryClassName();
				if (log.isDebugEnabled())
					log.debug("create factory: " + className);
				Class _class = Class.forName(className);
				if (_class != null)
					log.debug("forName called successful - class NOT null");
				IeMayorServiceFactory factory = (IeMayorServiceFactory) _class
						.newInstance();
				if (factory != null)
					log.debug("factroy reference is NOT null");
				factory.setup();
				this.repository.addServiceFactory(serviceId, factory);
			}
		} catch (ClassNotFoundException cnfex) {
			log.error("caught ex: " + cnfex.toString());
			throw new KernelException(
					"the specified class not found in classpath");
		} catch (IllegalAccessException iaex) {
			log.error("caught ex: " + iaex.toString());
			throw new KernelException("illegal access to the class instance");
		} catch (InstantiationException iex) {
			log.error("caught ex: " + iex.toString());
			throw new KernelException("cannot get an instance");
		} catch (eMayorServiceException emsex) {
			log.error("caught ex: " + emsex.toString());
			throw new KernelException("factory setup failed");
		} catch (KernelRepositoryException kex) {
			log.error("caught ex: " + kex.toString());
			throw new KernelException(
					"the new factory couldn't be saved into repository");
		}
		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#getServiceClassNameByServiceName(java.lang.String)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#getUserProfile(java.lang.String)
	 */
	public IUserProfile getUserProfile(String userId) throws KernelException {
		log.debug("-> start processing ...");
		IUserProfile ret = null;
		try {
			ret = this.repository.getUserProfile(userId);
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException(
					"problem with obtaining the user profile from repository");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#getServiceProfile(java.lang.String)
	 */
	public IServiceProfile getServiceProfile(String ssid)
			throws KernelException {
		// TODO
		log.debug("-> start processing ...");
		IServiceProfile ret = null;
		try {
			log.debug("get the service session");
			ServiceSessionLocal serviceSession = this.getServiceSession(ssid);
			String serviceId = serviceSession.getServiceId();
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
		} catch (ServiceSessionException ssex) {
			log.error("caught ex: " + ssex.toString());
			throw new KernelException("couldn't obtain the service profile");
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException("couldn't obtain the service profile");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#authenticateUser(javax.security.cert.X509Certificate[])
	 */
	public String authenticateUser(X509Certificate[] certificates)
			throws KernelException {
		log.debug("-> start processing ...");
		String ret = null;
		String userId = null;
		log.debug("try to get the user profile from policy enforcer");
		C_UserProfile userProfile = this.pe.F_getUserProfile(certificates);
		try {
			userId = String.valueOf(certificates[0].hashCode());
			if (!this.repository.existUserProfile(userId)) {
				IUserProfile up = new UserProfile();
				up.setUserId(String.valueOf(certificates[0].hashCode()));
				up.setPEUserProfile(userProfile);
				repository.addUserProfile(up);
			} else {
				log.debug("the user already exists in the repository");
			}
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
			throw new KernelException(
					"problem with repository by handling user profile");
		}
		log.debug("try to authenticate the user!");
		if (this.pe.F_AuthenticateUser(userProfile)) {
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
		log.debug("-> ... processing DONE!");
		return ret;
	}

}