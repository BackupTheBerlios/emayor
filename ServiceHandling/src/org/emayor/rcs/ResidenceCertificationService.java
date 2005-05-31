/*
 * Created on Feb 23, 2005
 */
package org.emayor.rcs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.IeMayorService;
import org.emayor.servicehandling.kernel.eMayorServiceException;
import org.emayor.servicehandling.kernel.bpel.starter.eMayorServiceInvoker;

/**
 * @author tku
 */
public class ResidenceCertificationService implements IeMayorService {
	private static Logger log = Logger
			.getLogger(ResidenceCertificationService.class);

	public static final String DEF_ENDPOINT = "http://localhost:9700/orabpel/default/ResidenceCertifcationRequest_v10/1.0";

	public static final String DEF_XMLFILE = "SampleResidenceCertificationRequestDocument.xml";

	private String serviceId = "";

	private String serviceEndpoint = DEF_ENDPOINT;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#setup()
	 */
	public void setup(String serviceId, String serviceEndpoint)
			throws eMayorServiceException {
		log.debug("-> start processing ...");
		if (serviceId == null || serviceId.length() == 0)
			throw new eMayorServiceException("invalid service id");
		this.serviceId = serviceId;
		if (serviceEndpoint != null && serviceEndpoint.length() != 0)
			this.serviceEndpoint = serviceEndpoint;
		if (log.isDebugEnabled()) {
			log.debug("working with following service id: " + this.serviceId);
			log.debug("working with following service endpoint: "
					+ this.serviceEndpoint);
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
				.getXMLDocument(), "<empty/>");
		log.debug("-> ... processing DONE!");
	}

	public void startService(String uid, String ssid, String requestDocument)
			throws eMayorServiceException {
		log.debug("-> start processing ...");
		/*
		 * this.startIt(IeMayorService.FORWARD_NO, uid, ssid, requestDocument, "
		 * <empty/>");
		 */
		log.debug("-> ... processing DONE!");
		throw new eMayorServiceException("NOT SUPPORTED BY THIS SERVICE");
	}

	private void startIt(String forward, String uid, String ssid, String req,
			String reqDigSig) throws eMayorServiceException {
		log.debug("-> start processing ...");
		try {
			/*
			 * VMID guid = new VMID(); String conversationId = guid.toString();
			 * if (log.isDebugEnabled()) log.debug("got new conversation id: " +
			 * conversationId); log.debug("creating the input data structure");
			 * EMayorServiceRequestType type = new EMayorServiceRequestType();
			 * if (type == null) log.error("the type ref is NULL"); if
			 * (log.isDebugEnabled()) { StringBuffer b = new StringBuffer();
			 * b.append("forward : ").append(forward); b.append("\nuid :
			 * ").append(uid); b.append("\nssid : ").append(ssid);
			 * log.debug("\n" + b.toString()); } type.setForwarded(forward);
			 * type.setReqestDocument(req); type.setReqDocDigSig(reqDigSig);
			 * type.setSsid(ssid); type.setUid(uid);
			 * type.setStatus(IeMayorService.STATUS_NO);
			 * type.setServiceId(this.serviceId); type.setExtraField1("
			 * <empty/>"); type.setExtraField2(" <empty/>");
			 * type.setExtraField3(" <empty/>"); type.setExtraField4("
			 * <empty/>");
			 * 
			 * MessageID messageID = new MessageID(conversationId);
			 * 
			 * ReplyTo replyTo = new ReplyTo("http://some.url", "", "");
			 * 
			 * RCSInvoker client = new RCSInvoker(this.serviceEndpoint,
			 * messageID, replyTo, type); client.call();
			 */
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
			URL url = this.getClass().getResource(DEF_XMLFILE);
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