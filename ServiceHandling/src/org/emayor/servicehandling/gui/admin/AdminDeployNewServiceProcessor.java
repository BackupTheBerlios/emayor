/*
 * Created on Jun 9, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.File;
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
import org.emayor.servicehandling.kernel.AdminServiceProfileData;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

import com.oreilly.servlet.MultipartRequest;

/**
 * @author tku
 */
public class AdminDeployNewServiceProcessor extends AbstractRequestProcessor {
    private final static Logger log = Logger
            .getLogger(AdminDeployNewServiceProcessor.class);

    private MultipartRequest mreq = null;

    public void setMultipartRequest(MultipartRequest mreq) {
        log.debug("-> start processing ...");
        this.mreq = mreq;
    }

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
                AdminServiceProfileData profile = new AdminServiceProfileData();
                String sid = mreq.getParameter("SID");
                profile.setServiceId(sid);
                String version = mreq.getParameter("VERSION");
                profile.setServiceVersion(version);
                String name = mreq.getParameter("NAME");
                profile.setServiceName(name);
                String description = mreq.getParameter("DESCRIPTION");
                profile.setServiceDescription(description);
                File file = mreq.getFile("CLAZZ");
                String clazz = file.getName();
                String factory;
                file = mreq.getFile("FACTORY");
                if (file == null) {
                    log.debug("factory isn't specified -> using the def one");
                    factory = null;
                } else {
                    factory = file.getName();
                }

                if (log.isDebugEnabled()) {
                    log.debug("--- got service profile ---");
                    log.debug("id                : " + sid);
                    log.debug("version           : " + version);
                    log.debug("name              : " + name);
                    log.debug("description       : " + description);
                    log.debug("class name        : " + clazz);
                    log.debug("factory class name: " + factory);
                }

                admin.deployNewService(profile, clazz, factory);
                admin.remove();
                ShortInfoPageData data = new ShortInfoPageData();
                data.setPageTitle("Deploy new service - operation status.");
                data.setSleepTime("3");
                data.setRedirectionUrl("WelcomePage.jsp");
                data.setSectionTitle("Status information.");
                data
                        .setRedirectionText("The new service has been successfuly deployed on the platform.");
                data.setRedirectionAction("WelcomePage.jsp");
                data.setRedirectionCancelAction("MAINMENU");
                data.setRedirectionCancelButtonTitle("     GO     ");
                session.setAttribute(ShortInfoPageData.ATT_NAME, data);
                ret = "admin/ShortInfoPage.jsp";
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
                data.setMessage("Couldn't remove the required user profile.");
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