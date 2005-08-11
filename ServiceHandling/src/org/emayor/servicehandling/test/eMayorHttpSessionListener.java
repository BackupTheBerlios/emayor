/*
 * $ Created on Aug 12, 2005 by tku $
 */
package org.emayor.servicehandling.test;

import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class eMayorHttpSessionListener implements HttpSessionListener {
	private static final Logger log = Logger
			.getLogger(eMayorHttpSessionListener.class);

	/**
	 *  
	 */
	public eMayorHttpSessionListener() {
		super();
		// TODO Auto-generated constructor stub
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		HttpSession session = se.getSession();
		if (session != null) {
			String asid = (String) session.getAttribute("ASID");
			if (asid != null) {
				if (log.isDebugEnabled())
					log.debug("working with asid: " + asid);
				try {
					ServiceLocator locator = ServiceLocator.getInstance();
					log.debug("get the access manager reference");
					AccessManagerLocal am = locator.getAccessManager();
					log.debug("stop the access session of current user");
					am.stopAccessSession(asid);
					log.debug("access session stoped!");
					am.remove();
				} catch (ServiceLocatorException ex) {
					log.error("caught ex: " + ex.toString());
				} catch (AccessException ex) {
					log.error("caught ex: " + ex.toString());
				} catch (EJBException ex) {
					log.error("caught ex: " + ex.toString());
				} catch (RemoveException ex) {
					log.error("caught ex: " + ex.toString());
				}
			} else {
				log.warn("the ASID reference was null!!!");
			}
		} else {
			log.warn("the session reference was null!!!");
		}
	}
}