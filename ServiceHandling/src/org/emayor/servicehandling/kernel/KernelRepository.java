/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;

/**
 * @author tku
 */
public class KernelRepository {
	private static Logger log = Logger.getLogger(KernelRepository.class);

	///// ---- session management -----
	// asid -> accessSession
	private HashMap asid2accessSession;

	// ssid -> serviceSession
	private HashMap ssid2serviceSession;

	// userID -> set{ssid1, ssid2, ssid3 ...}
	private HashMap userId2ssids;

	///// ---- service management -----
	// service id -> serviceInfo
	private HashMap serviceId2serviceInfo;

	// service id -> factory
	private HashMap serviceId2serviceFactory;

	/**
	 *  
	 */
	public KernelRepository() {
		log.debug("-> start processing ...");
		this.asid2accessSession = new HashMap();
		this.ssid2serviceSession = new HashMap();
		this.userId2ssids = new HashMap();
		this.serviceId2serviceInfo = new HashMap();
		this.serviceId2serviceFactory = new HashMap();
		log.debug("-> ... processing DONE!");
	}

	public void addAccessSession(AccessSessionLocal accessSession)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		try {
			String asid = accessSession.getSessionId();
			if (!this.asid2accessSession.containsKey(asid)) {
				this.asid2accessSession.put(asid, accessSession);
			} else {
				log.error("The asid " + asid
						+ " already exists in the repository!");
				throw new KernelRepositoryException(
						"The asid already exists in the repository!");
			}
		} catch (SessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't get the asid from session object!");
		}
		log.debug("-> ... processing DONE!");
	}

	public void removeAccessSession(String asid)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (this.asid2accessSession.containsKey(asid)) {
			this.asid2accessSession.remove(asid);
		} else {
			log.error("access session to be removed doesn't exist!");
			throw new KernelRepositoryException("Unknown asid: " + asid);
		}
		log.debug("-> ... processing DONE!");
	}

	public AccessSessionLocal getAccessSession(String asid)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		AccessSessionLocal ret = null;
		if (this.asid2accessSession.containsKey(asid)) {
			ret = (AccessSessionLocal) this.asid2accessSession.get(asid);
		} else {
			log.error("trying to get access session doesen't exist!");
			throw new KernelRepositoryException("Unknown asid: " + asid);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public void addServiceInfo(ServiceInfo serviceInfo)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		String id = serviceInfo.getServiceId();
		if (this.serviceId2serviceInfo.containsKey(id)) {
			log.error("ServiceInfo already exist in the repository");
			throw new KernelRepositoryException(
					"Couldn't add ServiceInfo into repository - already exists!");
		} else {
			this.serviceId2serviceInfo.put(id, serviceInfo);
		}
		log.debug("-> ... processing DONE!");
	}

	public void removeServiceInfo(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (serviceId == null || serviceId.length() == 0)
			throw new KernelRepositoryException("invalid service id");
		if (this.serviceId2serviceInfo.containsKey(serviceId)) {
			if (log.isDebugEnabled())
				log.debug("removing the info for the serviceId = " + serviceId);
			this.serviceId2serviceInfo.remove(serviceId);
		} else {
			log.error("info for specified service id doesn't exist!");
			throw new KernelRepositoryException(
					"info for specified service id doesn't exist!");
		}
		log.debug("-> ... processing DONE!");
	}

	public ServiceInfo getServiceInfo(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (serviceId == null || serviceId.length() == 0)
			throw new KernelRepositoryException("invalid service id");
		ServiceInfo ret = null;
		if (this.serviceId2serviceInfo.containsKey(serviceId)) {
			log.debug("found the right info");
			ret = (ServiceInfo) this.serviceId2serviceInfo.get(serviceId);
		} else {
			log.error("info for specified service id doesn't exist!");
			throw new KernelRepositoryException(
					"info for specified service id doesn't exist!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public ServiceInfo[] listServicesInfo() throws KernelRepositoryException {
		log.debug("-> start processing ...");
		ServiceInfo[] ret = null;
		ret = new ServiceInfo[this.serviceId2serviceInfo.size()];
		if (log.isDebugEnabled())
			log.debug("found " + ret.length + " items!");
		int index = 0;
		for (Iterator i = this.serviceId2serviceInfo.values().iterator(); i
				.hasNext();) {
			if (log.isDebugEnabled())
				log.debug("working with index = " + index);
			ret[index++] = (ServiceInfo) i.next();
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public void addServiceFactory(String serviceId,
			IeMayorServiceFactory serviceFactory)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (serviceId == null || serviceId.length() == 0)
			throw new KernelRepositoryException("invalid service id");
		if (serviceFactory == null)
			throw new KernelRepositoryException("invalid factory reference");
		if (this.serviceId2serviceFactory.containsKey(serviceId)) {
			log.error("mapping already exists!");
			throw new KernelRepositoryException(
					"Couldn't add the factory to the repository - already exists there!");
		} else {
			this.serviceId2serviceFactory.put(serviceId, serviceFactory);
		}
		log.debug("-> ... processing DONE!");
	}

	public void removeServiceFactory(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (serviceId == null || serviceId.length() == 0)
			throw new KernelRepositoryException("invalid service id");
		if (this.serviceId2serviceFactory.containsKey(serviceId)) {
			if (log.isDebugEnabled())
				log.debug("removing the factory for the serviceId = "
						+ serviceId);
			this.serviceId2serviceFactory.remove(serviceId);
		} else {
			log.error("factory for specified service id doesn't exist!");
			throw new KernelRepositoryException(
					"factory for specified service id doesn't exist!");
		}
		log.debug("-> ... processing DONE!");
	}

	public IeMayorServiceFactory getServiceFactory(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		IeMayorServiceFactory ret = null;
		if (serviceId == null || serviceId.length() == 0)
			throw new KernelRepositoryException("invalid service id");
		if (this.serviceId2serviceFactory.containsKey(serviceId)) {
			ret = (IeMayorServiceFactory) this.serviceId2serviceFactory
					.get(serviceId);
		} else {
			log.error("factory for specified service id doesn't exist!");
			throw new KernelRepositoryException(
					"factory for specified service id doesn't exist!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}