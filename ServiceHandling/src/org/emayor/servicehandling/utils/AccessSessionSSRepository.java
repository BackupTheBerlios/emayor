/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.kernel.SessionException;

/**
 * @author tku
 */
public class AccessSessionSSRepository {
	private static Logger log = Logger
			.getLogger(AccessSessionSSRepository.class);

	// serviceName -> a list of ssids
	private HashMap serviceName2ssids;

	// ssid -> ServiceSessionLocal
	private HashMap ssid2ssLocal;

	/**
	 * Default constructor - prepares the internal data structures.
	 */
	public AccessSessionSSRepository() {
		this.serviceName2ssids = new HashMap();
		this.ssid2ssLocal = new HashMap();
	}

	/**
	 * 
	 * @param serviceName
	 * @param ssLocal
	 */
	public void put(ServiceSessionLocal ssLocal) {
		String key = null;
		String serviceName = null;
		try {
			key = ssLocal.getSessionId();
			serviceName = ssLocal.getServiceName();
		} catch (SessionException ex) {
			log.error("caught exception: " + ex.toString());
			return;
		}
		Object obj = this.serviceName2ssids.get(serviceName);
		List ssids = (obj == null) ? new ArrayList() : (ArrayList) obj;
		if (!ssids.contains(key)) {
			ssids.add(key);
			this.serviceName2ssids.remove(serviceName);
			this.serviceName2ssids.put(serviceName, ssids);
		} else {
			log
					.debug("such key already exists in the serviceName2ssids repository!");
		}
		if (!this.ssid2ssLocal.containsKey(key)) {
			this.ssid2ssLocal.put(key, ssLocal);
		} else {
			log
					.debug("such key already exists in the ssid2ssLocal repository!");
		}

	}

	/**
	 * 
	 * @param serviceName
	 * @return
	 */
	public String[] getAllServiceSessionIds(String serviceName) {
		String[] ret = new String[0];
		if (this.serviceName2ssids.containsKey(serviceName)) {
			List ssids = (List) this.serviceName2ssids.get(serviceName);
			int index = 0;
			ret = new String[ssids.size()];
			for (Iterator i = ssids.iterator(); i.hasNext();)
				ret[index++] = (String) i.next();
		}
		return ret;
	}

	/**
	 * Returns the reference to the instance of the specified service session
	 * local interface.
	 * 
	 * @param ssid -
	 *            service session id
	 * @return reference tot the instance of the ServiceSessionLocal
	 */
	public ServiceSessionLocal getServiceSessionLocal(String ssid) {
		ServiceSessionLocal ret = null;
		if (ssid != null && ssid.length() != 0) {
			if (this.ssid2ssLocal.containsKey(ssid)) {
				ret = (ServiceSessionLocal) this.ssid2ssLocal.get(ssid);
			}
		}
		return ret;
	}

	public boolean remove(String ssid) {
		boolean ret = false;
		if (ssid != null && ssid.length() != 0) {
			ServiceSessionLocal ssLocal = this.getServiceSessionLocal(ssid);
			if (ssid != null) {
				String serviceName = null;
				try {
					serviceName = ssLocal.getServiceName();
				} catch (SessionException ex) {
					log.error("caught ex: couldn't remove service session: "
							+ ex.toString());
					return false;
				}
				List ssids = this.getServiceSessionIdsList(serviceName);
				if (ssids != null && ssids.contains(ssid)) {
					ssids.remove(ssid);
					this.serviceName2ssids.remove(serviceName);
					this.serviceName2ssids.put(serviceName, ssids);
					ret = true;
				}
			}
		}
		return ret;
	}

	public List getServiceSessionIdsList(String serviceName) {
		List ret = null;
		if (this.serviceName2ssids.containsKey(serviceName)) {
			ret = (List) this.serviceName2ssids.get(serviceName);
		}
		return ret;
	}

	/**
	 * Delivers a list of all instances of ServiceSessionLocal interfaces.
	 * @return
	 */
	public List getAllServiceSessions() {
		List ret = new ArrayList();
		for (Iterator i = this.ssid2ssLocal.entrySet().iterator(); i.hasNext();) {
			ret.add(i.next());
		}
		return ret;
	}
}