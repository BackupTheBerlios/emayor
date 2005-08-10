/*
 * $ Created on Aug 11, 2005 by tku $
 */
package org.emayor.services.admin;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.AbstracteMayorService;
import org.emayor.servicehandling.kernel.eMayorServiceException;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class AdminService extends AbstracteMayorService {
	private static final Logger log = Logger.getLogger(AdminService.class);

	public AdminService() {
		if (log.isDebugEnabled()) {
			log.debug("-> start processing ...");
			log.debug("-> ... processing DONE!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#startService(java.lang.String,
	 *      java.lang.String)
	 */
	public void startService(String uid, String ssid)
			throws eMayorServiceException {
		if (log.isDebugEnabled()) {
			log.debug("-> start processing ...");
			log.debug("-> ... processing DONE!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#startService(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void startService(String uid, String ssid, String requestDocument)
			throws eMayorServiceException {
		if (log.isDebugEnabled()) {
			log.debug("-> start processing ...");
			log.debug("-> ... processing DONE!");
		}
	}

}