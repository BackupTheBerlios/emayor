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
import org.emayor.servicehandling.kernel.AccessSessionInfo;
import org.emayor.servicehandling.kernel.AdminException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class AdminLookupAccessSessionInfoProcessor extends
		AbstractRequestProcessor {
	private static final Logger log = Logger
			.getLogger(AdminLookupAccessSessionInfoProcessor.class);

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
				String asid = req.getParameter("ASID");
				if (log.isDebugEnabled())
					log.debug("got as ASID = " + asid);
				if (asid == null || asid.length() == 0) {
					log.debug("the asid is not set -> try with the asid2");
					asid = req.getParameter("ASID2");
				} else {
					if (log.isDebugEnabled())
						log.debug("got from ASID : " + asid);
				}
				if (asid == null || asid.length() == 0) {
					AdminErrorPageData data = new AdminErrorPageData();
					data
							.setPageTitle("Please specify properly access session id!");
					session.setAttribute(AdminErrorPageData.ATT_NAME, data);
					ret = "admin/ErrorPage.jsp";
				} else {
					if (log.isDebugEnabled())
						log.debug("working with the asid = " + asid);
					AccessSessionInfo info = admin.lookupAccessSession(asid);
					if (info != null) {
						if (log.isDebugEnabled())
							log.debug("got the access session info with id = "
									+ info.getSessionId());
						session.setAttribute("ACCESS_SESSION_INFO", info);
						ret = "admin/AccessSessionInfo.jsp";
					} else {
						AdminErrorPageData data = new AdminErrorPageData();
						data
								.setPageTitle("Couldn't find the access session with given id: <<"
										+ asid + ">>!");
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
				data
						.setPageTitle("Couldn't lookup the required access session.");
				session.setAttribute(AdminErrorPageData.ATT_NAME, data);
				ret = "admin/ErrorPage.jsp";
			}
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

}