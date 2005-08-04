/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;

import com.oreilly.servlet.MultipartRequest;

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

	private String tempDir;

	/**
	 *  
	 */
	public AdminManagerContollerServlet() {
		log.debug("-> start processing ...");
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.debug("-> start processing ...");
		try {
			Config _config = Config.getInstance();
			this.tempDir = _config.getTmpDir();
			this.tempDir = _config.getQuilifiedDirectoryName(this.tempDir);
			if (log.isDebugEnabled())
				log.debug("the temp dir is: " + this.tempDir);
		} catch (ConfigException ex) {
			log.error("Couldn't get the temp directory");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.debug("-> start processing ...");
		String page = "admin/LoginForm.jsp";
		String type = request.getContentType();
		MultipartRequest mrequest = null;
		String action = null;
		if (type != null && type.startsWith("multipart/form-data")) {
			log.debug("this is a multipart request");
			mrequest = new MultipartRequest(request, this.tempDir);
			action = mrequest.getParameter("action");
		} else {
			action = request.getParameter("action");
		}
		if (action == null)
			action = "INDEX";
		if (log.isDebugEnabled())
			log.debug("CURRENT ACTION IS " + action);
		IRequestProcessor p = null;
		if (action.equalsIgnoreCase("INDEX")) {
			p = new IndexProcessor();
		} else if (action.equalsIgnoreCase("CREATECONFIG")) {
			p = new AdminCreateConfigProcessor();
		} else if (action.equalsIgnoreCase("CREATECONFIGPOST")) {
			p = new AdminCreateConfigPostProcessor();
		} else if (action.equalsIgnoreCase("RECONFIGURE")) {
			p = new AdminReconfigureProcessor();
		} else if (action.equalsIgnoreCase("SWITCHCONFIG")) {
			p = new AdminSwitchSystemConfigurationPostProcessor();
		} else if (action.equalsIgnoreCase("LOGIN")) {
			p = new AdminLoginProcessor();
		} else if (action.equalsIgnoreCase("LOGOUT")) {
			p = new AdminLogoutProcessor();
		} else if (action.equalsIgnoreCase("MAINMENU")) {
			p = new AdminMainMenuProcessor();
		} else if (action.equalsIgnoreCase("RELOAD_SERVICES")) {
			p = new AdminReloadServicesProcessor();
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
		} else if (action.equalsIgnoreCase("SHOW_SYSTEM_CONFIGURATION")) {
			p = new AdminShowSystemConfigurationProcessor();
		} else if (action.equalsIgnoreCase("EDIT_SYSTEM_CONFIGURATION")) {
			p = new AdminEditSystemConfigurationProcessor();
		} else if (action.equalsIgnoreCase("SWITCH_SYSTEM_CONFIGURATION")) {
			p = new AdminSwitchSystemConfigurationProcessor();
		} else if (action.equalsIgnoreCase("REMOVE_ACCESS_SESSION")) {
			p = new AdminRemoveAccessSessionProcessor();
		} else if (action.equalsIgnoreCase("REMOVE_SERVICE_SESSION")) {
			p = new AdminRemoveServiceSessionProcessor();
		} else if (action.equalsIgnoreCase("REMOVE_USER_PROFILE")) {
			p = new AdminRemoveUserProfileProcessor();
		} else if (action.equalsIgnoreCase("CHANGE_SERVICE_STATUS")) {
			p = new AdminChangeServiceStatusProcessor();
		} else if (action.equalsIgnoreCase("DEPLOY_NEW_SERVICE_INPUT")) {
			p = new AdminDeployNewServiceInputProcessor();
		} else if (action.equalsIgnoreCase("DEPLOY_NEW_SERVICE")) {
			AdminDeployNewServiceProcessor pp = new AdminDeployNewServiceProcessor();
			pp.setMultipartRequest(mrequest);
			p = pp;
		} else if (action.equalsIgnoreCase("UNDEPLOY_SERVICE_INPUT")) {
			p = new AdminUndeployServiceInputProcessor();
		} else if (action.equalsIgnoreCase("UNDEPLOY_SERVICE")) {
			p = new AdminUndeployServiceProcessor();
		} else if (action.equalsIgnoreCase("CHANGE_LOGGING_PREFERENCES")) {
			p = new AdminChangeLoggingPreferencesProcessor();
		} else if (action.equalsIgnoreCase("CHANGE_LOG4J_CONFIG_POST")) {
			p = new AdminChangeLoggingPreferencesPostProcessor();
		} else if (action.equalsIgnoreCase("TEST_PLATFORM_INTRO")) {
			p = new AdminTestPlatformIntroProcessor();
		} else if (action.equalsIgnoreCase("TEST_PLATFORM_EXEC")) {
			p = new AdminTestPlatformExecProcessor();
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