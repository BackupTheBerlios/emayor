/*
 * Created on Feb 15, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import java.security.cert.X509Certificate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.kernel.ServicesInfo;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class WelcomeProcessor {
	private static Logger log = Logger.getLogger(WelcomeProcessor.class);

	/**
	 *  
	 */
	public WelcomeProcessor() {
		super();
	}

	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		HttpSession session = req.getSession();
		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			AccessManagerLocal access = serviceLocator.getAccessManager();
			String asid = access.createAccessSession();
			log.debug("try to authenticate the user");
			X509Certificate[] certificates = (X509Certificate[]) req.getAttribute("javax.servlet.request.X509Certificate");
			if (certificates != null) {
				log.debug("certificates.length = " + certificates.length);
				for (int i = 0; i < certificates.length; i++)
					log.debug("certificates["+i+"] = " + certificates[i].toString());
			}
			else 
				log.debug("certificates are NULL");
			boolean ret = access.startAccessSession(asid, certificates);
			log.debug("user authenticated? : " + ret);
			session.setAttribute("ASID", asid);
			if (log.isDebugEnabled())
				log.debug("got following asid=" + asid);
			ServicesInfo servicesInfo = access.listAvailableServices("-1001");
			session.setAttribute("SERVICES_INFO", servicesInfo.getServicesInfo());
		}
		catch(ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			// TODO hadle exception
		}
		catch(AccessException aex) {
			log.error("caught ex: " + aex.toString());
			// TODO handle ex
		}
		log.debug("-> ... processing DONE!");
	}

}