/*
 * $ Created on Aug 11, 2005 by tku $
 */
package org.emayor.services.ur;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.eMayorServiceException;
import org.emayor.servicehandling.kernel.eMayorServiceFactory;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class UserRegistrationServiceFactory extends eMayorServiceFactory {

	private static final Logger log = Logger
			.getLogger(UserRegistrationServiceFactory.class);

	/**
	 * @throws eMayorServiceException
	 */
	public UserRegistrationServiceFactory() throws eMayorServiceException {
		super();
		if (log.isDebugEnabled()) {
			log.debug("-> start processing ...");
			log.debug("-> ... processing DONE!");
		}
	}

}