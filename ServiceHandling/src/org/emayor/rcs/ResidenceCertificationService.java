/*
 * Created on Feb 23, 2005
 */
package org.emayor.rcs;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10_Port;
import org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10_Service;
import org.emayor.ResidenceCertifcationRequest_v10._ResidenceCertifcationRequest_v10Request;
import org.emayor.servicehandling.kernel.IeMayorService;
import org.emayor.servicehandling.kernel.eMayorServiceException;
import org.emayor.www.eMayorServiceRequest_xsd.EMayorServiceRequestStructure;
import org.emayor.www.eMayorServiceRequest_xsd.EMayorServiceRequestType;

/**
 * @author tku
 */
public class ResidenceCertificationService implements IeMayorService {
	private static Logger log = Logger
			.getLogger(ResidenceCertificationService.class);

	private ResidenceCertifcationRequest_v10_Port port = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#setup()
	 */
	public void setup() throws eMayorServiceException {
		log.debug("-> start processing ...");
		try {
			InitialContext initialContext = this.getInitialContext();
			if (initialContext != null)
				log.debug("the initial context is NOT null");
			ResidenceCertifcationRequest_v10_Service service = (ResidenceCertifcationRequest_v10_Service) initialContext
					.lookup("java:comp/env/service/ResidenceCertifcationServiceJSE");
			if (service != null)
				log.debug("the service is NOT null");
			this.port = service.getResidenceCertifcationRequest_v10Port();
			if (this.port != null)
				log.debug("the port reference is NOT null");
		} catch (NamingException nex) {
			log.error("caught ex: " + nex.toString());
			throw new eMayorServiceException(
					"General problem with naming service");
		} catch (javax.xml.rpc.ServiceException ex) {
			log.error("caught ex: " + ex.toString());
			throw new eMayorServiceException(
					"Couldn't connect to the residence certification service!");
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
		this.port = null;
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
		try {
			log.debug("creating the input data structure");
			_ResidenceCertifcationRequest_v10Request request = new _ResidenceCertifcationRequest_v10Request();
			EMayorServiceRequestType type = new EMayorServiceRequestType();
			EMayorServiceRequestStructure struct = new EMayorServiceRequestStructure();
			struct.setForwarded(IeMayorService.FORWARD_NO);
			// TODO - request document!
			struct.setReqestDocument("<doc><req></req></doc>");
			struct.setSsid(ssid);
			struct.setUid(uid);
			struct.setStatus(IeMayorService.STATUS_NO);
			type.setRequestData(struct);
			request.setInput(type);
			log.debug("the input data structure done!");
			log.debug("starting the request !");
			this.port.initiate(request);
			log.debug("the request has been sent !");
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
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
		try {
			log.debug("creating the input data structure");
			_ResidenceCertifcationRequest_v10Request request = new _ResidenceCertifcationRequest_v10Request();
			EMayorServiceRequestType type = new EMayorServiceRequestType();
			EMayorServiceRequestStructure struct = new EMayorServiceRequestStructure();
			struct.setForwarded(IeMayorService.FORWARD_YES);
			struct.setReqestDocument(req);
			struct.setReqDocDigSig(reqDigSig);
			struct.setSsid(ssid);
			struct.setUid(uid);
			struct.setStatus(IeMayorService.STATUS_NO);
			type.setRequestData(struct);
			request.setInput(type);
			log.debug("the input data structure done!");
			log.debug("starting the request !");
			this.port.initiate(request);
			log.debug("the request has been sent !");
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new eMayorServiceException("");
		}
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

}