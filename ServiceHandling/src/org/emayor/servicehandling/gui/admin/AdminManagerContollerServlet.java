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
            action = "index";
        if (log.isDebugEnabled())
            log.debug("CURRENT ACTION IS " + action);
        IRequestProcessor p = null;
        if (action.equalsIgnoreCase("index")) {
            p = new IndexProcessor();
        } else if (action.equalsIgnoreCase("login")) {
            p = new AdminLoginProcessor();
        } else if (action.equalsIgnoreCase("logout")) {
            p = new AdminLogoutProcessor();
        } else if (action.equalsIgnoreCase("mainmenu")) {
            p = new AdminMainMenuProcessor();
        } else if (action.equalsIgnoreCase("RELOAD_SERVICES")) {
            p = new AdminReloadServicesProcessor();
        } else if (action.equalsIgnoreCase("RELOAD_CONFIGURATION")) {
            p = new AdminReloadConfigurationProcessor();
        } else {
            log.info("current action = UNKNOWN");
            p = new DummyProcessor();
        }
        page = p.process(request, response);
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