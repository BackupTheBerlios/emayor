/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.gui.admin;

import javax.servlet.http.HttpServlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet Class
 * 
 * @web.servlet name="AdminManagerContoller" display-name="Name for
 *              AdminManagerContoller" description="AdminManagerContoller"
 * @web.servlet-mapping url-pattern="/adm"
 *  
 */
public class AdminManagerContollerServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(AdminManagerContollerServlet.class);

	/**
	 *  
	 */
	public AdminManagerContollerServlet() {
		log.debug("-> start processing ...");
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("-> start processing ...");
		String page = "admin/LoginForm.jsp";
		String action = request.getParameter("action");
		if (action == null)
			action = "INDEX";
		if (log.isDebugEnabled())
			log.debug("CURRENT ACTION IS " + action);
		IRequestProcessor p = null;
		if (action.equalsIgnoreCase("INDEX")) {
			p = new IndexProcessor();
		} else if (action.equalsIgnoreCase("LOGIN")) {
			p = new AdminLoginProcessor();
		} else if (action.equalsIgnoreCase("LOGOUT")) {
			p = new AdminLogoutProcessor();
		} else if (action.equalsIgnoreCase("MAINMENU")) {
			p = new AdminMainMenuProcessor();
		} else if (action.equalsIgnoreCase("RELOAD_SERVICES")) {
			p = new AdminReloadServicesProcessor();
		} else if (action.equalsIgnoreCase("RELOAD_CONFIGURATION")) {
			p = new AdminReloadConfigurationProcessor();
		} else if (action.equalsIgnoreCase("LIST_ACCESS_SESSIONS")) {
			p = new AdminListAccessSessionsProcessor();
		} else if (action.equalsIgnoreCase("LIST_SERVICE_SESSIONS")) {
			p = new AdminListServiceSessionsProcessor();
		} else if (action.equalsIgnoreCase("LIST_LOGGED_USERS")) {
			p = new AdminListLoggedInUserProfilesProcessor();
		} else if (action.equalsIgnoreCase("LIST_KNOWN_USERS")) {
			p = new AdminListAllKnownUsersProcessor();
		} else if (action.equalsIgnoreCase("LIST_DEPLOYED_SERVICES")) {
			p = new AdminListDeployedServicesProcessor();
		} else if (action.equalsIgnoreCase("LOOKUP_ACCESS_SESSION_INPUT")) {
			p = new AdminLookupAccessSessionInputProcessor();
		} else if (action.equalsIgnoreCase("LOOKUP_ACCESS_SESSION")) {
			p = new AdminLookupAccessSessionInfoProcessor();
		} else if (action.equalsIgnoreCase("LOOKUP_SERVICE_SESSION_INPUT")) {
			p = new AdminLookupServiceSessionInputProcessor();
		} else if (action.equalsIgnoreCase("LOOKUP_SERVICE_SESSION")) {
			p = new AdminLookupServiceSessionInfoProcessor();
		} else if (action.equalsIgnoreCase("LOOKUP_SERVICE_PROFILE_INPUT")) {
			p = new AdminLookupServiceProfileInputProcessor();
		} else if (action.equalsIgnoreCase("LOOKUP_SERVICE_PROFILE")) {
			p = new AdminLookupServiceProfileProcessor();
		} else if (action.equalsIgnoreCase("LOOKUP_USER_PROFILE_INPUT")) {
			p = new AdminLookupUserProfileInputProcessor();
		} else if (action.equalsIgnoreCase("LOOKUP_USER_PROFILE")) {
			p = new AdminLookupUserProfileProcessor();
		} else {
			log.info("current action = UNKNOWN");
			p = new DummyProcessor();
		}
		page = p.process(request, response);
		log.debug("-> ... processing DONE!");
		response.sendRedirect(page);
	} /*
	   * (non-Javadoc)
	   * 
	   * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	   *      javax.servlet.http.HttpServletResponse)
	   */

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		doPost(req, resp);
	}
}