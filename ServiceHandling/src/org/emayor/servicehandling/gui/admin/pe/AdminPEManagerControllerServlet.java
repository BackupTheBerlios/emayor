/*
 * $ Created on Aug 12, 2005 by tku $
 */
package org.emayor.servicehandling.gui.admin.pe;

import javax.servlet.http.HttpServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.gui.admin.IRequestProcessor;

/**
 * Servlet Class
 * 
 * @web.servlet name="AdminPEManagerController" display-name="Name for
 *              AdminPEManagerController" description="Description for
 *              AdminPEManagerController"
 * @web.servlet-mapping url-pattern="/admpe"
 */
public class AdminPEManagerControllerServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(AdminPEManagerControllerServlet.class);

	/**
	 *  
	 */
	public AdminPEManagerControllerServlet() {
		super();
		log.debug("-> start processing ...");
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.debug("-> start processing ...");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		this.doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String page = "adminpe/DoNothing.jsp";
		String action = req.getParameter("action");
		IRequestProcessor p = null;
		if (action.equalsIgnoreCase("START")) {
			p = new StartServiceProcessor();
		} else if (action.equalsIgnoreCase("STOP")) {
			p = new StopServiceProcessor();
		} else if (action.equalsIgnoreCase("donothing")) {
			p = new DoNothingProcessor();
		} else {
			p = new DoNothingProcessor();
		}
		page = p.process(req, resp);
		log.debug("-> ... processing DONE!");
		resp.sendRedirect(page);
	}
}