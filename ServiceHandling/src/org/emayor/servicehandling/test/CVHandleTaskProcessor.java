/*
 * Created on Mar 22, 2005
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
import org.emayor.servicehandling.utclient.CivilServantTaskServiceClient;

/**
 * @author tku
 */
public class CVHandleTaskProcessor extends AbstractProcessor {
    private static final Logger log = Logger
            .getLogger(CVHandleTaskProcessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.emayor.servicehandling.test.IProcessor#process(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
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
            String action = req.getParameter("Submit");
            if (log.isDebugEnabled())
                log.debug("got action from request: " + action);
            Task task = (Task) session.getAttribute("CURR_TASK");

            if (action.equalsIgnoreCase("Approve")) {
                log.debug("this is a case of approval");
                task.setStatus("YES");
            } else {
                log.debug("this is a case of declination");
                task.setStatus("NO");
            }
            CivilServantTaskServiceClient utm = new CivilServantTaskServiceClient();
            log.debug("try to complete task");
            utm.completeTask(asid, task);

            session.setAttribute("SLEEP_TIME", "5");
            session.setAttribute("REDIRECTION_URL",
                    "ServiceHandlingTest?action=listMyTasks");
            session.setAttribute("PAGE_TITLE", "Waiting for response ...");
            session.setAttribute("REDIRECTION_TEXT",
                    "The chosen task has been successfull finished!");
            session.setAttribute("REDIRECTION_CANCEL_ACTION", "Welcome");
            session.setAttribute("REDIRECTION_ACTION", "ServiceHandlingTest");
            ret = "JustWait.jsp";
            /**
            Task[] tasks = utm.getMyTasks(asid);
            tasks = Utils.setTypeToTasks(tasks);
            session.setAttribute("MY_TASKS", tasks);
            ret = "CVListTasks.jsp";
            */
        } catch (UserTaskException utex) {
            log.error("caught ex: " + utex.toString());
            // handle exception
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }
}