/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

import java.util.HashMap;

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
	// serviceName -> serviceInfo
	private HashMap serviceName2serviceInfo;

	// factory name -> factory
	private HashMap factoryName2factory;

	/**
	 *  
	 */
	public KernelRepository() {
		log.debug("-> start processing ...");
		this.asid2accessSession = new HashMap();
		this.ssid2serviceSession = new HashMap();
		this.userId2ssids = new HashMap();
		this.serviceName2serviceInfo = new HashMap();
		this.factoryName2factory = new HashMap();
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
			log.error("trying to remove access session doesn't exist!");
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
	
	public void addServiceInfo(ServiceInfo serviceInfo) throws KernelRepositoryException {
		log.debug("-> start processing ...");
		String serviceName = serviceInfo.getServiceName();
		if (this.serviceName2serviceInfo.containsKey(serviceInfo)) {
			
		}
		else {
			this.serviceName2serviceInfo.put(serviceName, serviceInfo);
		}
		log.debug("-> ... processing DONE!");
	}
	
	public void removeServiceInfo(String serviceName) throws KernelRepositoryException {
		log.debug("-> start processing ...");
		
		log.debug("-> ... processing DONE!");
	}
	
	public ServiceInfo getServiceInfo(String serviceInfo) throws KernelRepositoryException {
		log.debug("-> start processing ...");
		ServiceInfo ret = null;
		
		log.debug("-> ... processing DONE!");
		return ret;
	}

}