/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class AdminLoginProcessor extends AbstractRequestProcessor {
	private final static Logger log = Logger
			.getLogger(AdminLoginProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.gui.admin.IRequestProcessor#process(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String ret = "admin/WelcomePage.jsp";
		/*
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			AdminManagerLocal mgr = locator.getAdminManagerLocal();
			String uid = req.getParameter("uid");
			String pswd = req.getParameter("pswd");
			if (log.isDebugEnabled()) {
				log.debug("got uid  = " + uid);
				log.debug("got pswd = " + pswd);
			}
			boolean b = false;
			try {
				b = mgr.login(uid, pswd);
			} catch (AdminException e) {
				log.error("caught ex: " + e.toString());
			}
			if (b) {
				log.info("ADMIN LOGIN WAS SUCCESSFUL !!!");
				HttpSession session = req.getSession(true);
			} else {
				ret = "admin/LoginForm.jsp";
			}
			
			mgr.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());

		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());

		}*/
		return ret;
	}

}