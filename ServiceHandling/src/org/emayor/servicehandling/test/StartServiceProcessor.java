/*
 * Created on Feb 24, 2005
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
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utclient.InputDataCollector;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class StartServiceProcessor {
	private static Logger log = Logger.getLogger(StartServiceProcessor.class);

	/**
	 *  
	 */
	public StartServiceProcessor() {
		super();
	}

	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		HttpSession session = req.getSession();
		String asid = (String) session.getAttribute("ASID");
		if (log.isDebugEnabled())
			log.debug("got asid from session: " + asid);
		String serviceName = req.getParameter("ServiceName");
		if (log.isDebugEnabled())
			log.debug("got service name: " + serviceName);
		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			AccessManagerLocal access = serviceLocator.getAccessManager();
			String ssid = access.startService(asid, serviceName);
			if (log.isDebugEnabled())
				log.debug("got ssid: " + ssid);
			session.setAttribute("SSID", ssid);
			
			InputDataCollector collector = new InputDataCollector();
			Task task = collector.getInputDataForm(asid, ssid);
			
			session.setAttribute("CURR_TASK", task);
			
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			// TODO hadle exception
		} catch (AccessException aex) {
			log.error("caught ex: " + aex.toString());
			// TODO handle ex
		} catch(UserTaskException utex) {
			log.error("caught ex: " + utex.toString());
			// TODO handle ex
		}
		log.debug("-> ... processing DONE!");
	}
}