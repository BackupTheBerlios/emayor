/*
 * Created on Feb 22, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.UserTaskManagerLocal;
import org.emayor.servicehandling.kernel.ServiceException;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class ShowTaskDetailsProcessor {
	private static Logger log = Logger
			.getLogger(ShowTaskDetailsProcessor.class);

	/**
	 *  
	 */
	public ShowTaskDetailsProcessor() {
		super();
	}

	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		try {
			HttpSession session = req.getSession(false);
			String taskId = req.getParameter("taskId");
			if (log.isDebugEnabled())
				log.debug("got following taskid from session: " + taskId);
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			UserTaskManagerLocal utm = serviceLocator.getUserTaskManagerLocal();
			Task task = utm.lookupTask(taskId);
			session.setAttribute("CURRENT_TASK", task);
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			// TODO hadle exception
		} catch (ServiceException sex) {
			log.error("caught ex: " + sex.toString());
			// TODO hadle exception
		}
		log.debug("-> ... processing DONE!");
	}

}