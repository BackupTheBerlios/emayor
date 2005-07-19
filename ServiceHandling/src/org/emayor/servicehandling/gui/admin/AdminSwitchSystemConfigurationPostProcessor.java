/*
 * Created on Jun 8, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;

/**
 * @author tku
 */
public class AdminSwitchSystemConfigurationPostProcessor extends
        AbstractRequestProcessor {
    private static final Logger log = Logger
            .getLogger(AdminSwitchSystemConfigurationPostProcessor.class);

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
        	String newConfigID = req.getParameter("configID"); 
        	try {
	            if (newConfigID != null && (!newConfigID.equals("")) && !newConfigID.equals(Config.getInstance().getConfigID())) {
						Config.getInstance().switchConfig(newConfigID);
	            }
        	} catch (ConfigException e) {
				AdminErrorPageData data = new AdminErrorPageData();
				data.setMessage(e.getMessage());
				data.setPageTitle(e.getMessage());
				session.setAttribute(AdminErrorPageData.ATT_NAME,data);
				ret = "admin/ErrorPage.jsp";
			}
            ret = "admin/WelcomePage.jsp";
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}