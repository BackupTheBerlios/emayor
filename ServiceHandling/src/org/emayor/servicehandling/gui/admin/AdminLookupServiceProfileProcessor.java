/*
 * Created on Jun 7, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;

import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AdminManagerLocal;
import org.emayor.servicehandling.kernel.AdminException;
import org.emayor.servicehandling.kernel.AdminServiceProfileData;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class AdminLookupServiceProfileProcessor extends
		AbstractRequestProcessor {
	private static final Logger log = Logger
			.getLogger(AdminLookupServiceProfileProcessor.class);

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
				String sid = req.getParameter("SID");
				if (log.isDebugEnabled())
					log.debug("got as SID = " + sid);
				if (sid == null || sid.length() == 0) {
					log.debug("the sid is not set -> try with the sid2");
					sid = req.getParameter("SID2");
				} else {
					if (log.isDebugEnabled())
						log.debug("got from SSID : " + sid);
				}
				if (sid == null || sid.length() == 0) {
					AdminErrorPageData data = new AdminErrorPageData();
					data.setPageTitle("Please specify properly service id!");
					session.setAttribute(AdminErrorPageData.ATT_NAME, data);
					ret = "admin/ErrorPage.jsp";
				} else {
					if (log.isDebugEnabled())
						log.debug("working with the sid = " + sid);
					AdminServiceProfileData info = admin
							.lookupServiceProfile(sid);
					if (info != null) {
						if (log.isDebugEnabled())
							log.debug("got the service profile with id = "
									+ info.getServiceId());
						session.setAttribute("SERVICE_PROFILE_INFO", info);
						ret = "admin/ServiceProfileInfo.jsp";
					} else {
						AdminErrorPageData data = new AdminErrorPageData();
						data
								.setPageTitle("Couldn't find the service profile with given id: <<"
										+ sid + ">>!");
						session.setAttribute(AdminErrorPageData.ATT_NAME, data);
						ret = "admin/ErrorPage.jsp";
					}
				}
				admin.remove();
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
				data
						.setPageTitle("Couldn't lookup the required service profile.");
				session.setAttribute(AdminErrorPageData.ATT_NAME, data);
				ret = "admin/ErrorPage.jsp";
			} catch (EJBException ex) {
				log.error("caught ex: " + ex.toString());
				AdminErrorPageData data = new AdminErrorPageData();
				data.setPageTitle("Internal Error!");
				data.setMessage("This was an fatal internal error!");
				session.setAttribute(AdminErrorPageData.ATT_NAME, data);
				ret = "admin/ErrorPage.jsp";
			} catch (RemoveException ex) {
				log.error("caught ex: " + ex.toString());
				AdminErrorPageData data = new AdminErrorPageData();
				data.setPageTitle("Internal Error!");
				data.setMessage("This was an fatal internal error!");
				session.setAttribute(AdminErrorPageData.ATT_NAME, data);
				ret = "admin/ErrorPage.jsp";
			}
		}
		return ret;
	}

}