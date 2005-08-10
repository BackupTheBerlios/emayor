/*
 * $ Created on Aug 11, 2005 by tku $
 */
package org.emayor.services.admin;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.eMayorServiceException;
import org.emayor.servicehandling.kernel.eMayorServiceFactory;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class AdminServiceFactory extends eMayorServiceFactory {
	private final static Logger log = Logger
			.getLogger(AdminServiceFactory.class);

	/**
	 * @throws eMayorServiceException
	 */
	public AdminServiceFactory() throws eMayorServiceException {
		super();
		if (log.isDebugEnabled()) {
			log.debug("-> start processing ...");
			log.debug("-> ... processing DONE!");
		}
	}

}