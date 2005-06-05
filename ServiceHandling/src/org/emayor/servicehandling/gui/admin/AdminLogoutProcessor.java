/*
 * Created on Jun 6, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class AdminLogoutProcessor extends AbstractRequestProcessor {
    private static final Logger log = Logger
            .getLogger(AdminLogoutProcessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.emayor.servicehandling.gui.admin.IRequestProcessor#process(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("-> start processing ...");
        String ret = "admin/FatalError.jsp";
        HttpSession session = req.getSession(false);
        if (session == null) {
            log.debug("no valid session !");
        }
        else {
            log.debug("invalidate the session");
            session.invalidate();
            ret = "admin/LoginForm.jsp";
        }
        return ret;
    }

}