/*
 * $ Created on Mar 1, 2005 by tku $
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utclient.InputDataCollector;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class RCSPostSignRequestProcessor extends AbstractProcessor {
	private static Logger log = Logger
			.getLogger(RCSPostSignRequestProcessor.class);

	/**
	 *  
	 */
	public RCSPostSignRequestProcessor() {
		super();
	}

	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "Error.jsp";
		try {
			HttpSession session = req.getSession(false);
			String asid = (String) session.getAttribute("ASID");
			String ssid = (String) session.getAttribute("SSID");
			String role = (String) session.getAttribute("ROLE");
			if (log.isDebugEnabled()) {
				log.debug("got asid: " + asid);
				log.debug("got ssid: " + ssid);
				log.debug("got role: " + role);
			}
			Task task = (Task) session.getAttribute("CURR_TASK");
			String taskId = req.getParameter("taskid");
			String xmldoc = task.getXMLDocument();

			InputDataCollector collector = new InputDataCollector();
			collector.postInputData(task, asid, ssid);

			session.removeAttribute("SSID");
			session.removeAttribute("CURR_TASK");
			
			ret = "MainMenu.jsp";
		} catch (UserTaskException utex) {
			log.error("caught ex: " + utex.toString());
			// TODO handle ex
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}