/*
 * Created on Feb 23, 2005
 */
package org.emayor.rcs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.dgc.VMID;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.IeMayorService;
import org.emayor.servicehandling.kernel.eMayorServiceException;
import org.emayor.www.eMayorServiceRequest_xsd.EMayorServiceRequestType;
import org.xmlsoap.schemas.ws.addressing.MessageID;
import org.xmlsoap.schemas.ws.addressing.ReplyTo;

/**
 * @author tku
 */
public class ResidenceCertificationService implements IeMayorService {
	private static Logger log = Logger
			.getLogger(ResidenceCertificationService.class);

	//private ResidenceCertifcationRequest_v10_Port port = null;

	public static final String DEF_ENDPOINT = "";

	public static final String DEF_XMLFILE = "org.emayor.rcs.SampleResidenceCertificationRequestDocument.xml";

	//private String endpoint =
	// "http://localhost:9700/orabpel/default/ResidenceCertifcationRequest_v10/1.0";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#setup()
	 */
	public void setup() throws eMayorServiceException {
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#cleanup()
	 */
	public void cleanup() throws eMayorServiceException {
		log.debug("-> start processing ...");
		//this.port = null;
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
		throw new eMayorServiceException("NOT IMPLEMENTED");
	}

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
				.getXMLDocument(), "");
		log.debug("-> ... processing DONE!");
	}

	private void startIt(String forward, String uid, String ssid, String req,
			String reqDigSig) throws eMayorServiceException {
		try {
			VMID guid = new VMID();
			String conversationId = guid.toString();
			if (log.isDebugEnabled())
				log.debug("got new converation id: " + conversationId);
			log.debug("creating the input data structure");
			EMayorServiceRequestType type = new EMayorServiceRequestType();
			if (type == null)
				log.error("the type ref is NULL");
			type.setForwarded(forward);
			type.setReqestDocument(req);
			type.setReqDocDigSig(reqDigSig);
			type.setSsid(ssid);
			type.setUid(uid);
			type.setStatus(IeMayorService.STATUS_NO);

			MessageID messageID = new MessageID(conversationId);

			ReplyTo replyTo = new ReplyTo(
					"http://localhost:8080/axis/services/LoanFlowClientCallback",
					"LoanFlowCallback", "LoanFlowCallbackService");
			RCSInvoker client = new RCSInvoker(DEF_ENDPOINT, messageID,
					replyTo, type);
			client.call();
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			throw new eMayorServiceException("");
		}
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

	private InitialContext getInitialContext() throws NamingException {
		Properties env = new Properties();
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		env.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming.client");
		env.setProperty(Context.PROVIDER_URL, "jnp://localhost:1099");
		env.setProperty("j2ee.clientName", "ws4ee-client");
		return new InitialContext(env);
	}

	private String getXMLDocument() throws eMayorServiceException {
		log.debug("-> start processing ...");
		String ret = "";
		try {
			//InputStream is = ClassLoader.getSystemResourceAsStream(DEF_XMLFILE);
			InputStream is = ResidenceCertificationService.class.getResourceAsStream(DEF_XMLFILE);
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			//InputStream is = classLoader.getResourceAsStream(DEF_XMLFILE);
			URL url = classLoader.getResource(DEF_XMLFILE);
			if (url != null)
				log.debug("got url for resource: " + url.toString());
			else
				log.error("DUPA");
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