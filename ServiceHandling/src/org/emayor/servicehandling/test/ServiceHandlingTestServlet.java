/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.test;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Class
 * 
 * @web.servlet name = "ServiceHandlingTest" 
 * 				display-name = "Name for ServiceHandlingTest" 
 * 				description="Description for ServiceHandlingTest"
 * 
 * @web.servlet-mapping url-pattern = "/ServiceHandlingTest"
 * 
 * @web.ejb-local-ref 
 * 	name = "AccessManager" 
 * 	type = "Session" 
 * 	home = "org.emayor.servicehandling.interfaces.AccessManagerLocalHome"
 *	local ="org.emayor.servicehandling.interfaces.AccessManagerLocal"
 *  description = "Reference to the AccessManager EJB"
 * 
 * @jboss.ejb-ref-jndi 
 * 	ref-name = "AccessManger" 
 * 	jndi-name = "AccessManager" 
 */
public class ServiceHandlingTestServlet extends HttpServlet {
	private static Logger log = Logger
			.getLogger(ServiceHandlingTestServlet.class);

	/**
	 *  
	 */
	public ServiceHandlingTestServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if (log.isDebugEnabled())
			log.debug("got following action from request: " + action);
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		if (action.equalsIgnoreCase("welcome")) {
			out.println("<html><head><title>After welcome!</title></head>");
			out.println("<body>");
			out.println("<a href=\"ServiceHandlingTest?action=welcome1\">test it again</a>");
			out.println("</body>");
			out.println("<html>");
		}
		else {
			out.println("<html><head><title>After after welcome!</title></head>");
			out.println("<body>");
			out.println("<a href=\"ServiceHandlingTest?action=welcome1\">test it again</a>");
			out.println("</body>");
			out.println("<html>");
		}
		out.close();
	}

	public String getServletInfo() {
		return "Control servlet for test handling GUI";
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.service(req, resp);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.service(request, response);
	}

}