/*
 * Created on Feb 21, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utclient.UserTaskServiceClient;

/**
 * @author tku
 */
public class ListMyTasksProcessor extends AbstractProcessor {
	private static Logger log = Logger.getLogger(ListMyTasksProcessor.class);

	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "Error.jsp";
		try {
			HttpSession session = req.getSession(false);
			String asid = (String) session.getAttribute("ASID");
			UserTaskServiceClient client = new UserTaskServiceClient();
			session.setAttribute("MY_TASKS", client.getMyTasks(asid));
			ret = "listTasks.jsp";
		} catch (UserTaskException ex) {
			log.error("caught ex: " + ex.toString());
			// TODO hadle exception
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}