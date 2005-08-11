/*
 * $ Created on Aug 12, 2005 by tku $
 */
package org.emayor.servicehandling.gui.admin.pe;

import java.io.IOException;

import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.gui.admin.AbstractRequestProcessor;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class StopServiceProcessor extends AbstractRequestProcessor {
	private final static Logger log = Logger
			.getLogger(StopServiceProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.gui.admin.IRequestProcessor#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "adminpe/FatalError.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			log.debug("no valid session !");
		} else {
			try {
				log.debug("invalidate the session");
				String asid = (String) session.getAttribute("ASID");
				String ssid = (String) session.getAttribute("ADMIN_PE_SSID");
				String role = (String) session.getAttribute("ROLE");
				if (log.isDebugEnabled()) {
					log.debug("asid = " + asid);
					log.debug("ssid = " + ssid);
					log.debug("role = " + role);
				}
				log.debug("obtain the access manager interface");
				ServiceLocator serviceLocator = ServiceLocator.getInstance();
				AccessManagerLocal access = serviceLocator.getAccessManager();
				log.debug("stop the admin pe service");
				access.stopService(asid, ssid);
				log.debug("remove the ADMIN_PE_SSID from http session");
				session.removeAttribute("ADMIN_PE_SSID");
				// TODO: which welcome/index page should be shown?
				// citizen's or civil servant's one
				log.debug("return to the main menu page of the citizen");
				access.remove();
				ret = "MainMenu.jsp";
			} catch (ServiceLocatorException ex) {
				log.error("caught ex: " + ex.toString());
			} catch (AccessException ex) {
				log.error("caught ex: " + ex.toString());
			} catch (EJBException ex) {
				log.error("caught ex: " + ex.toString());
			} catch (RemoveException ex) {
				log.error("caught ex: " + ex.toString());
			}
		}
		log.debug("-> processing DONE");
		return ret;
	}

}