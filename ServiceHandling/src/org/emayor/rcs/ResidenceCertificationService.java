/*
 * Created on Feb 23, 2005
 */
package org.emayor.rcs;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.IeMayorService;
import org.emayor.servicehandling.kernel.eMayorServiceException;

/**
 * @author tku
 */
public class ResidenceCertificationService implements IeMayorService {
	private static Logger log = Logger
			.getLogger(ResidenceCertificationService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#setup()
	 */
	public void setup() throws eMayorServiceException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#cleanup()
	 */
	public void cleanup() throws eMayorServiceException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#endService()
	 */
	public void endService() throws eMayorServiceException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

}