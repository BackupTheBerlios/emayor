/*
 * Created on Mar 23, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.utclient.UserTaskServiceClient;

/**
 * @author tku
 */
public class AfterDownloadCompletedDocumentProcessor extends AbstractProcessor {
    private static final Logger log = Logger
            .getLogger(AfterDownloadCompletedDocumentProcessor.class);

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
            Task task = (Task) session.getAttribute("CURR_TASK");
            session.removeAttribute("CURR_TASK");
            String asid = (String) session.getAttribute("ASID");
            String ssid = (String) session.getAttribute("SSID");
            String role = (String) session.getAttribute("ROLE");
            if (log.isDebugEnabled()) {
                log.debug("got asid: " + asid);
                log.debug("got ssid: " + ssid);
                log.debug("got role: " + role);
            }
            UserTaskServiceClient client = new UserTaskServiceClient();
            client.completeTask(asid, task);
            ret = "MainMenu.jsp";
        } catch (Exception ex1) {
            log.error("caught ex: " + ex1.toString());
            // TODO hadle exception
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}