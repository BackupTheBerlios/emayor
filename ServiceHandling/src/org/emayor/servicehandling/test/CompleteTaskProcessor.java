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
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utclient.UserTaskServiceClient;

/**
 * @author tku
 */
public class CompleteTaskProcessor extends AbstractProcessor {
	private static Logger log = Logger.getLogger(CompleteTaskProcessor.class);

	/**
	 *  
	 */
	public CompleteTaskProcessor() {
		super();
	}

	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "Error.jsp";
		try {
			HttpSession session = req.getSession(false);
			Task task = (Task) session.getAttribute("CURRENT_TASK");
			String asid = (String) session.getAttribute("ASID");

			// do a lot with data !!!!

			UserTaskServiceClient client = new UserTaskServiceClient();
			client.completeTask(asid, task);
			log.debug("DONE");
			ret = "MainMenu.jsp";
		} catch (UserTaskException ex) {
			log.error("caught ex: " + ex.toString());
			// TODO hadle exception
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}