/*
 * Created on Jun 7, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AdminManagerLocal;
import org.emayor.servicehandling.kernel.AdminException;
import org.emayor.servicehandling.kernel.UserProfile;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class AdminLookupUserProfileProcessor extends AbstractRequestProcessor {
	private final static Logger log = Logger
			.getLogger(AdminLookupUserProfileProcessor.class);

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
				String uid = req.getParameter("UID");
				if (log.isDebugEnabled())
					log.debug("got as UID = " + uid);
				if (uid == null || uid.length() == 0) {
					log.debug("the uid is not set -> try with the uid2");
					uid = req.getParameter("UID2");
				} else {
					if (log.isDebugEnabled())
						log.debug("got from SSID : " + uid);
				}
				if (uid == null || uid.length() == 0) {
					AdminErrorPageData data = new AdminErrorPageData();
					data.setPageTitle("Please specify properly user id!");
					session.setAttribute(AdminErrorPageData.ATT_NAME, data);
					ret = "admin/ErrorPage.jsp";
				} else {
					if (log.isDebugEnabled())
						log.debug("working with the uid = " + uid);
					UserProfile info = admin.lookupUserProfile(uid);
					if (info != null) {
						if (log.isDebugEnabled())
							log.debug("got the user profile with id = "
									+ info.getUserId());
						session.setAttribute("USER_PROFILE_INFO", info);
						ret = "admin/UserProfileInfo.jsp.jsp";
					} else {
						AdminErrorPageData data = new AdminErrorPageData();
						data
								.setPageTitle("Couldn't find the user profile with given id: <<"
										+ uid + ">>!");
						session.setAttribute(AdminErrorPageData.ATT_NAME, data);
						ret = "admin/ErrorPage.jsp";
					}
				}
			} catch (ServiceLocatorException ex) {
				log.error("caught ex: " + ex.toString());
				AdminErrorPageData data = new AdminErrorPageData();
				data
						.setPageTitle("Couldn't connect to the admin interface on the platfom.");
				session.setAttribute(AdminErrorPageData.ATT_NAME, data);
				ret = "admin/ErrorPage.jsp";
			} catch (AdminException ex) {
				log.error("caught ex: " + ex.toString());
				AdminErrorPageData data = new AdminErrorPageData();
				data.setPageTitle("Couldn't lookup the required user profile.");
				session.setAttribute(AdminErrorPageData.ATT_NAME, data);
				ret = "admin/ErrorPage.jsp";
			}
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

}