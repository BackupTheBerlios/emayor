/*
 * Created on Mar 14, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.ejb.RemoveException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class LoginProcessor extends AbstractProcessor {
	private static final Logger log = Logger.getLogger(LoginProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.test.IProcessor#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "index.jsp";
		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			AccessManagerLocal access = serviceLocator.getAccessManager();
			String asid = access.createAccessSession();
			log.debug("try to authenticate the user");
			X509Certificate[] certificates = (X509Certificate[]) req
					.getAttribute("javax.servlet.request.X509Certificate");
			if (certificates != null) {
				log.debug("certificates.length = " + certificates.length);
				for (int i = 0; i < certificates.length; i++)
					log.debug("certificates[" + i + "] = "
							+ certificates[i].toString());
			} else
				log.debug("certificates are NULL");
			boolean b = access.startAccessSession(asid, certificates);
			log.debug("user authenticated? : " + b);
			HttpSession session = req.getSession(true);
			log.debug("set the max inactive interval to 900 seconds (15 min)");
			session.setMaxInactiveInterval(900);
			session.setAttribute("ASID", asid);
			if (log.isDebugEnabled())
				log.debug("got following asid=" + asid);
			if (access.getUserProfile(asid).getPEUserProfile().getUserRole()
					.equalsIgnoreCase("Citizen")) {
				session.setAttribute("ROLE", "citizen");
				ret = "MainMenu.jsp";
			} else {
				session.setAttribute("ROLE", "civilservant");
				ret = "CVMainMenu.jsp";
			}
			access.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			// TODO hadle exception
		} catch (AccessException aex) {
			log.error("caught ex: " + aex.toString());
			// TODO handle ex
		} catch (RemoveException rex) {
			log.error("caught ex: " + rex.toString());
			// TODO handle ex
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

}