/*
 * Created on Jun 8, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;

import javax.ejb.EJBException;
import javax.ejb.RemoveException;
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
public class AdminChangeServiceStatusProcessor extends AbstractRequestProcessor {
    private final static Logger log = Logger
            .getLogger(AdminChangeServiceStatusProcessor.class);

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
            log.debug("the got session ref is null -> no valid session");
        } else {
            try {
                String sid = req.getParameter("SID");
                String status = req.getParameter("status");
                if (log.isDebugEnabled()) {
                    log.debug("SID    = " + sid);
                    log.debug("status = " + status);
                }
                ServiceLocator locator = ServiceLocator.getInstance();
                AdminManagerLocal admin = locator.getAdminManagerLocal();
                admin.changeServiceStatus(sid, (status.equals("ON")));
                admin.remove();
                ret = "admin/WelcomePage.jsp";
            } catch (ServiceLocatorException ex) {
                log.error("caught ex: " + ex.toString());
                AdminErrorPageData data = new AdminErrorPageData();
                data.setPageTitle("Fatal Error!");
                data
                        .setMessage("Couldn't connect to the admin interface on the platfom.");
                session.setAttribute(AdminErrorPageData.ATT_NAME, data);
                ret = "admin/ErrorPage.jsp";
            } catch (AdminException ex) {
                log.error("caught ex: " + ex.toString());
                AdminErrorPageData data = new AdminErrorPageData();
                data.setPageTitle("Admin Error!");
                data
                        .setMessage("Couldn't change the status of the requested service.");
                session.setAttribute(AdminErrorPageData.ATT_NAME, data);
                ret = "admin/ErrorPage.jsp";
            } catch (EJBException ex) {
                log.error("caught ex: " + ex.toString());
                AdminErrorPageData data = new AdminErrorPageData();
                data.setPageTitle("Internal Error!");
                data.setMessage("This was an fatal internal error!");
                session.setAttribute(AdminErrorPageData.ATT_NAME, data);
                ret = "admin/ErrorPage.jsp";
            } catch (RemoveException ex) {
                log.error("caught ex: " + ex.toString());
                AdminErrorPageData data = new AdminErrorPageData();
                data.setPageTitle("Internal Error!");
                data.setMessage("This was an fatal internal error!");
                session.setAttribute(AdminErrorPageData.ATT_NAME, data);
                ret = "admin/ErrorPage.jsp";
            }
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }
}