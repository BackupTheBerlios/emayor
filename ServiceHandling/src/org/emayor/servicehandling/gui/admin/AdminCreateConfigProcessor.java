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

/**
 * @author tku
 */
public class AdminCreateConfigProcessor extends
        AbstractRequestProcessor {
    private static final Logger log = Logger
            .getLogger(AdminCreateConfigProcessor.class);

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
            String elem, key;
            Properties config = (Properties) session.getAttribute("SYSTEM_CONFIGURATION");
            for (Enumeration e = req.getParameterNames();e.hasMoreElements();) {
            	elem = (String) e.nextElement();
            	if (elem.matches("ECONFIG-(.*?)$")) {
            		key = elem.replaceAll("ECONFIG-","");
            		config.setProperty(key,req.getParameter(elem));
            		
            	}
            }
            session.setAttribute("SYSTEM_CONFIGURATION",config);
            
            /*
            Set configNames = null; 
			try {
				configNames = Config.getInstance().getConfigNames();
			} catch (ConfigException e) {
				configNames = new HashSet();
			}
            session.setAttribute("CONIFIG_IDS",configNames);
            */
            ret = "admin/CreateConfig.jsp";
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}