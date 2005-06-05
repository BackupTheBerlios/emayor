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
import org.emayor.servicehandling.interfaces.AdminManagerLocal;
import org.emayor.servicehandling.kernel.AccessSessionInfo;
import org.emayor.servicehandling.kernel.AdminException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class AdminListAccessSessionsProcessor extends AbstractRequestProcessor {
    private static final Logger log = Logger
            .getLogger(AdminListAccessSessionsProcessor.class);

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
        } else {
            try {
                ServiceLocator locator = ServiceLocator.getInstance();
                AdminManagerLocal admin = locator.getAdminManagerLocal();
                AccessSessionInfo[] infos = admin.listAccessSessions();
                session.setAttribute("ACCESS_SESSION_INFO_ARRAY", infos);
                ret = "admin/ListOfAccessSessions.jsp";
            } catch (ServiceLocatorException ex) {
                log.error("caught ex: " + ex.toString());
                AdminErrorPageData data = new AdminErrorPageData();
                data
                        .setPageTitle("Couldn't connect to the admin interface on the platfom.");
                session.setAttribute(AdminErrorPageData.ATT_NAME, data);
                ret = "admin/ErrorPage.jsp";
            } catch (AdminException ex) {
                log.error("caught ex: " + ex.toString());
                AdminErrorPageData data = new AdminErrorPageData();
                data.setPageTitle("Couldn't list the running access sessions.");
                session.setAttribute(AdminErrorPageData.ATT_NAME, data);
                ret = "admin/ErrorPage.jsp";
            }
        }
        return ret;
    }
}