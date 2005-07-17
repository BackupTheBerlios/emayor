/*
 * Created on Jun 8, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

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
public class AdminReconfigureProcessor extends
        AbstractRequestProcessor {
    private static final Logger log = Logger
            .getLogger(AdminReconfigureProcessor.class);

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
                String elem, key;
                Properties oldConf = (Properties) session.getAttribute("SYSTEM_CONFIGURATION");
                for (Enumeration e = req.getParameterNames();e.hasMoreElements();) {
                	elem = (String) e.nextElement();
                	if (elem.matches("ECONFIG-(.*?)$")) {
                		key = elem.replaceAll("ECONFIG-","");
                		if (oldConf.getProperty(key) != null && (! oldConf.getProperty(key).equals(req.getParameter(elem)))) {
                			log.debug("update setting - ["+key+"="+req.getParameter(elem)+"]");
                			config.setProperty(key,req.getParameter(elem));
                		}
                		
                	}
                }
                session.setAttribute("SYSTEM_CONFIGURATION",config.getAllProperties());
                ret = "admin/SystemConfigurationInfo.jsp";
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