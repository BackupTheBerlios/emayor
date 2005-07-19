/*
 * Created on Jun 8, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
public class AdminSwitchSystemConfigurationProcessor extends
        AbstractRequestProcessor {
    private static final Logger log = Logger
            .getLogger(AdminSwitchSystemConfigurationProcessor.class);

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
            
            Set configNames = null;
            String configID = null;
			try {
				configNames = Config.getInstance().getConfigNames();
				configID = Config.getInstance().getConfigID();
			} catch (ConfigException e) {
				configNames = new HashSet();
			}
            session.setAttribute("ALL_CONFIG_IDS",configNames);
            session.setAttribute("CURRENT_CONFIG_ID",configID);
            
            ret = "admin/ConfigList.jsp";
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}