/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import org.apache.log4j.Logger;
import org.emayor.policyenforcer.C_UserProfile;
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

	private static Kernel _self = null;

	private Kernel() throws KernelException {
		log.debug("-> start processing ...");
		this.repository = new KernelRepository();
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			this.idGen = locator.getSimpleIdGeneratorLocal();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex:" + ex.toString());
		}
		this.initTestData();
		log.debug("intialize the factories ...");
		this.initializeServiceFactories();
		log.debug("-> ... processing DONE!");
	}

	public static final Kernel getInstance() throws KernelException {
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
		} catch (KernelRepositoryException kex) {
			log.error("caught ex: " + kex.toString());
			throw new KernelException(
					"Couldn' store the new access session into repository!");
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
		} catch (ServiceLocatorException slex) {
			log.error("caught ex: " + slex.toString());
			throw new KernelException(slex.toString());
		} catch (SessionException sex) {
			log.error("caught ex: " + sex.toString());
		} catch (KernelRepositoryException krex) {
			log.error("caught ex: " + krex.toString());
		} catch(eMayorServiceException emsex) {
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
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		ServiceSessionLocal ret = null;
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
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		boolean ret = false;
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
		return "defid";
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

}