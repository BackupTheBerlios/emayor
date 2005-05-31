/*
 * $ Created on Jun 1, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.bpel.starter.eMayorServiceInvoker;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public abstract class AbstracteMayorService implements IeMayorService {
	private static final Logger log = Logger
			.getLogger(AbstracteMayorService.class);

	protected String serviceId = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#setup()
	 */
	public void setup(String serviceId) throws eMayorServiceException {
		log.debug("-> start processing ...");
		if (serviceId == null || serviceId.length() == 0)
			throw new eMayorServiceException("invalid service id");
		this.serviceId = serviceId;
		if (log.isDebugEnabled()) {
			log.debug("working with following service id: " + this.serviceId);
		}
		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#cleanup()
	 */
	public void cleanup() throws eMayorServiceException {
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#endService()
	 */
	public void endService() throws eMayorServiceException {
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
		throw new eMayorServiceException("NOT IMPLEMENTED");
	}
	
	protected void startIt(String forward, String uid, String ssid, String req,
			String reqDigSig) throws eMayorServiceException {
		log.debug("-> start processing ...");
		try {
			eMayorServiceInvoker invoker = new eMayorServiceInvoker();
			invoker.invokeService(this.serviceId, forward, uid, ssid, req,
					reqDigSig, IeMayorService.STATUS_NO);
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			throw new eMayorServiceException("");
		}
		log.debug("-> ... processing DONE!");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#forward(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public void forward(String uid, String ssid, String req, String reqDigSig)
			throws eMayorServiceException {
		log.debug("-> start processing ...");
		this.startIt(IeMayorService.FORWARD_YES, uid, ssid, req, reqDigSig);
		log.debug("-> ... processing DONE!");
	}

	protected String getXMLDocumentFromResource(String fileName)
			throws eMayorServiceException {
		log.debug("-> start processing ...");
		String ret = "";
		try {
			URL url = this.getClass().getResource(fileName);
			InputStream is = url.openStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuffer b = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null)
				b.append(line.trim());
			ret = b.toString();
			if (log.isDebugEnabled())
				log.debug("got xml: " + ret);
		} catch (IOException ioex) {
			log.error("caught ex: " + ioex.toString());
			throw new eMayorServiceException("");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}