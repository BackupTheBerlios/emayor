/*
 * Created on Feb 23, 2005
 */
package org.emayor.services.rc;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.AbstracteMayorService;
import org.emayor.servicehandling.kernel.IeMayorService;
import org.emayor.servicehandling.kernel.eMayorServiceException;

/**
 * @author tku
 */
public class ResidenceCertificationService extends AbstracteMayorService {
	private static Logger log = Logger
			.getLogger(ResidenceCertificationService.class);

	public static final String DEF_XML_FILE = "SampleResidenceCertificationRequestDocument.xml";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#startService(java.lang.String,
	 *      java.lang.String)
	 */
	public void startService(String uid, String ssid)
			throws eMayorServiceException {
		log.debug("-> start processing ...");
		this.startIt(IeMayorService.FORWARD_NO, uid, ssid, this
				.getXMLDocumentFromResource(DEF_XML_FILE), "<empty/>");
		log.debug("-> ... processing DONE!");
	}

	public void startService(String uid, String ssid, String requestDocument)
			throws eMayorServiceException {
		log.debug("-> start processing ...");
		throw new eMayorServiceException("NOT SUPPORTED BY THIS SERVICE");
	}

}