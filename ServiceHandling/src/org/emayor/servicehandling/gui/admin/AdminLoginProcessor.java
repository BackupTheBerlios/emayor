/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AdminManagerLocal;
import org.emayor.servicehandling.kernel.AdminException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class AdminLoginProcessor extends AbstractRequestProcessor {
    private final static Logger log = Logger
            .getLogger(AdminLoginProcessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.emayor.servicehandling.gui.admin.IRequestProcessor#process(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String ret = "admin/WelcomePage.jsp";
        try {
            ServiceLocator locator = ServiceLocator.getInstance();
            AdminManagerLocal mgr = locator.getAdminManagerLocal();
            String uid = req.getParameter("uid");
            String pswd = req.getParameter("pswd");
            if (log.isDebugEnabled()) {
                log.debug("got uid  = " + uid);
                log.debug("got pswd = " + pswd);
            }
            boolean b = mgr.login(uid, pswd);
            if (b) {
                log.debug("LOGIN WAS SUCCESSFUL !!!");
                HttpSession session = req.getSession(true);
            } else {
                ret = "admin/LoginForm.jsp";
            }
        } catch (ServiceLocatorException ex) {

        } catch (AdminException ex) {

        }
        return ret;
    }

}