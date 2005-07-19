/*
 * Created on Jun 8, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.interfaces.PlatformConfigurationEntityLocal;

/**
 * @author tku
 */
public class AdminCreateConfigPostProcessor extends
        AbstractRequestProcessor {
    private static final Logger log = Logger
            .getLogger(AdminCreateConfigPostProcessor.class);

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
                Config config = Config.getInstance();
                String key, value;
                Properties confValues = (Properties) session.getAttribute("SYSTEM_CONFIGURATION");
                String configID = req.getParameter("ConfigID");
                PlatformConfigurationEntityLocal newConfig;
                Set allConfigNames = config.getConfigNames();
                if ( configID != null 
                		&& (!configID.equals("")) 
						&& (!allConfigNames.contains(configID))
						&& confValues != null) {
                	config.createConfig(configID,confValues);
                	ret = "admin/WelcomePage.jsp";
                } else {
                	AdminErrorPageData data = new AdminErrorPageData();
                	data.setPageTitle("Configuration >"+configID+"< cannot be created, because it is already existant.");
                	data.setMessage("Configuration already existant!");
                	session.setAttribute(AdminErrorPageData.ATT_NAME,data);
                	ret = "admin/ErrorPage.jsp";
                }
            } catch (ConfigException ex) {
                log.error("caught ex: " + ex.toString());
                AdminErrorPageData data = new AdminErrorPageData();
                data.setPageTitle("System Error!");
                data
                        .setMessage("There was a problem to get the system configuration.\nPlease try it again later.");
                session.setAttribute(AdminErrorPageData.ATT_NAME, data);
                ret = "admin/ErrorPage.jsp";
            }
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}