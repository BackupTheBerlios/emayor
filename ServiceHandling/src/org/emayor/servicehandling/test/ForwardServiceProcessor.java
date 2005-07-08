/*
 * Created on Apr 25, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.ejb.RemoveException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.kernel.forward.ArrayOfStrings;
import org.emayor.servicehandling.kernel.forward.ForwardMessage;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class ForwardServiceProcessor extends AbstractProcessor {
    private static final Logger log = Logger
            .getLogger(ForwardServiceProcessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.emayor.servicehandling.test.IProcessor#process(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String ret = "Error.jsp";
        HttpSession session = req.getSession();
        String asid = (String) session.getAttribute("ASID");
        if (log.isDebugEnabled())
            log.debug("got asid from session: " + asid);
        String serviceName = req.getParameter("ServiceName");
        if (log.isDebugEnabled())
            log.debug("got service name: " + serviceName);
        String role = (String) session.getAttribute("ROLE");
        try {
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            AccessManagerLocal access = serviceLocator.getAccessManager();
            ForwardMessage forwardMessage = new ForwardMessage();
            forwardMessage.setType(ForwardMessage.REQUEST);
            ArrayOfStrings arrayOfStrings = new ArrayOfStrings();
            arrayOfStrings.setItem(new String[]{"forwarded document"});
            forwardMessage.setDocuments(arrayOfStrings);
            forwardMessage.setReplyAddress("reply address");
            forwardMessage.setReplyID("sss");
            String ssid = access.startForwardedService(asid, serviceName, forwardMessage);
            if (log.isDebugEnabled()) {
                log.debug("got asid: " + asid);
                log.debug("got ssid: " + ssid);
                log.debug("got role: " + role);
            }
            /*
            session.setAttribute("SSID", ssid);
            session.setAttribute("SLEEP_TIME", "10");
            session.setAttribute("REDIRECTION_URL", "ServiceHandlingTest?action=GetInputDataPage");
            session.setAttribute("PAGE_TITLE", "Waiting for input data page!");
            session.setAttribute("REDIRECTION_TEXT", "Please wait a while - we are working for you!");
            session.setAttribute("REDIRECTION_CANCEL_ACTION", "Welcome");
            session.setAttribute("REDIRECTION_ACTION", "ServiceHandlingTest");
            */
            access.remove();
            ret = "MainMenu.jsp";
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            // TODO hadle exception
        } catch (AccessException aex) {
            log.error("caught ex: " + aex.toString());
            // TODO handle ex
        } catch (RemoveException rex) {
            log.error("caught ex: " + rex.toString());
            // TODO handle ex
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}