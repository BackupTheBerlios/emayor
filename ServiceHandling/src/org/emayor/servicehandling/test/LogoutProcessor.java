/*
 * Created on Feb 15, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

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
public class LogoutProcessor {
	private static Logger log = Logger.getLogger(LogoutProcessor.class);

	/**
	 *  
	 */
	public LogoutProcessor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		HttpSession session = req.getSession(false);
		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			AccessManagerLocal access = serviceLocator.getAccessManager();
			String asid = (String)session.getAttribute("ASID");
			boolean ret = access.stopAccessSession(asid);
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			// TODO hadle exception
		} catch (AccessException aex) {
			log.error("caught ex: " + aex.toString());
			// TODO handle ex
		}
		log.debug("-> ... processing DONE!");
	}

}