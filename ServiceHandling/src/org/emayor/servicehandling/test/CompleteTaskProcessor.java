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

import org.emayor.servicehandling.bpel.task._task;

/**
 * @author tku
 */
public class CompleteTaskProcessor {
	private static Logger log = Logger.getLogger(CompleteTaskProcessor.class);

	/**
	 *  
	 */
	public CompleteTaskProcessor() {
		super();
	}

	public void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		try {
			HttpSession session = req.getSession(false);
			Task task = (Task)session.getAttribute("CURRENT_TASK");
			String asid = (String)session.getAttribute("ASID");
			
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			UserTaskManagerLocal utm = serviceLocator.getUserTaskManagerLocal();
			_task t = task.getOriginalTask();
			t.setCustomKey("the custom key");
			task.setOriginalTask(t);
			utm.completeTask(asid, task);
			log.debug("DONE");
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