/*
 * $ Created on May 25, 2005 by tku $
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class ReloadPlatformConfigurationProcessor extends AbstractProcessor {
	private static final Logger log = Logger
			.getLogger(ReloadPlatformConfigurationProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.test.IProcessor#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "Error.jsp";
		try {
			HttpSession session = req.getSession(false);
			String role = (String) session.getAttribute("ROLE");
			Config config = Config.getInstance();
			config.reloadConfiguration();
			ret = (role.equals("citizen")) ? "MainMenu.jsp" : "CVMainMenu.jsp";
		} catch (ConfigException cex) {
			log.error("caught ex: " + cex.toString());
			// TODO handle ex
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

}