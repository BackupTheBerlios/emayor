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

	private Kernel() {
		log.debug("-> start processing ...");
		this.repository = new KernelRepository();
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			this.idGen = locator.getSimpleIdGeneratorLocal();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex:" + ex.toString());
		}
		this.initTestData();
		log.debug("-> ... processing DONE!");
	}

	public static final Kernel getInstance() {
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
			String serviceName) throws KernelException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		ServiceSessionLocal ret = null;
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

		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	private void initTestData() {
		try {
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setServiceId("Service1/v10");
			serviceInfo.setServiceVersion("1.0");
			serviceInfo.setServiceName("service 1");
			serviceInfo.setServiceFactoryClassName("service factory 1");
			serviceInfo.setServiceClassName("org.emayor.service.Service1");
			serviceInfo.setServiceDescription("Description of the service 1.");
			this.repository.addServiceInfo(serviceInfo);
			serviceInfo = new ServiceInfo();
			serviceInfo.setServiceId("Service2/v10");
			serviceInfo.setServiceVersion("1.0");
			serviceInfo.setServiceName("service 2");
			serviceInfo.setServiceFactoryClassName("service factory 2");
			serviceInfo.setServiceClassName("org.emayor.service.Service2");
			serviceInfo.setServiceDescription("Description of the service 2.");
			this.repository.addServiceInfo(serviceInfo);
			serviceInfo = new ServiceInfo();
			serviceInfo.setServiceId("Service3/v10");
			serviceInfo.setServiceVersion("1.0");
			serviceInfo.setServiceName("service 3");
			serviceInfo.setServiceFactoryClassName("service factory 3");
			serviceInfo.setServiceClassName("org.emayor.service.Service3");
			serviceInfo.setServiceDescription("Description of the service 3.");
			this.repository.addServiceInfo(serviceInfo);

		} catch (KernelRepositoryException kex) {
			log.error("caught ex:" + kex.toString());
		}
	}

}