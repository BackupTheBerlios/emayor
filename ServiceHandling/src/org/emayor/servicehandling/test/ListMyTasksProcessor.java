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
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utclient.CVDocumentTypes;
import org.emayor.servicehandling.utclient.CivilServantTaskServiceClient;
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
			String role = (String) session.getAttribute("ROLE");
			Task[] tasks = null;
			if (role.equalsIgnoreCase("citizen")) {
				log.debug("list tasks for a sitizen");
				UserTaskServiceClient client = new UserTaskServiceClient();
				tasks = client.getMyTasks(asid);
				ret = "listTasks.jsp";
			} else {
				log.debug("list tasks for a civil servant");
				CivilServantTaskServiceClient client = new CivilServantTaskServiceClient();
				tasks = client.getMyTasks(asid);
				ret = "CVListTasks.jsp";
			}
			session.setAttribute("MY_TASKS", tasks);
			for (int i = 0; i < tasks.length; i++) {
				switch (tasks[i].getTaskType()) {
				case CVDocumentTypes.CV_BANK_ACCOUNT_CHANGE_REQUEST:
					tasks[i].setExtraInfo("Bank account change request");
					break;
				case CVDocumentTypes.CV_FAMILY_RESIDENCE_CERTIFICATE_REQUEST:
					tasks[i].setExtraInfo("Family residence certification request");
					break;
				case CVDocumentTypes.CV_RESIDENCE_CERTIFICATE_REQUEST:
					tasks[i].setExtraInfo("Residence certification request");
					break;
				case CVDocumentTypes.CV_RESIDENCE_DOCUMENT:
					tasks[i].setExtraInfo("Residence document");
					break;
				case CVDocumentTypes.CV_TAXES_MANAGEMENT_ACTIVATION_REQUEST:
					tasks[i].setExtraInfo("Tax management activation request");
					break;
				}
			}
		} catch (UserTaskException ex) {
			log.error("caught ex: " + ex.toString());
			// TODO hadle exception
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}