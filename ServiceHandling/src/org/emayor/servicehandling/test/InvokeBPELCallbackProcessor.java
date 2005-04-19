/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.bpel.forward.client.ForwardBPELServiceCallbackInvoker;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardMessageBPEL;

/**
 * @author tku
 */
public class InvokeBPELCallbackProcessor extends AbstractProcessor {
    private final static Logger log = Logger
            .getLogger(InvokeBPELCallbackProcessor.class);

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
        HttpSession session = req.getSession();
        try {
            String asid = (String) session.getAttribute("ASID");
            String role = (String) session.getAttribute("ROLE");
            if (log.isDebugEnabled()) {
                log.debug("got asid: " + asid);
                log.debug("got role: " + role);
            }
            String _ssid = req.getParameter("SSID");
            ForwardBPELServiceCallbackInvoker invoker = new ForwardBPELServiceCallbackInvoker();
            ForwardMessageBPEL msg = new ForwardMessageBPEL();
            msg.setSsid(_ssid);
            msg.setUid("tku");
            msg.setDocument("test");
            msg.setDocument1("empty");
            msg.setDocument2("empty");
            msg.setDocument3("empty");
            msg.setDocument4("empty");
            invoker.invoke(msg);
            ret = "MainMenu.jsp";
        } catch (Exception ex) {
            log.error("caught ex: " + ex.toString());
        }
        return ret;
    }

}