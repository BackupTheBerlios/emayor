/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessManagerLocalHome;

/**
 * Servlet Class
 * 
 * @web.servlet name="ServiceHandlingTest" display-name="Name for
 *              ServiceHandlingTest" description="Description for
 *              ServiceHandlingTest"
 * 
 * @web.servlet-mapping url-pattern = "/ServiceHandlingTest"
 * 
 * 
 * @web.ejb-local-ref name = "ejb/AccessManager" type = "Session" home =
 *                    "org.emayor.servicehandling.interfaces.AccessManagerLocalHome"
 *                    local =
 *                    "org.emayor.servicehandling.interfaces.AccessManagerLocal"
 *                    description = "Reference to the AccessManager EJB" link =
 *                    "AccessManager"
 *  
 */
public class ServiceHandlingTestServlet extends HttpServlet {
	private static Logger log = Logger
			.getLogger(ServiceHandlingTestServlet.class);

	private AccessManagerLocalHome home;

	/**
	 *  
	 */
	public ServiceHandlingTestServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Context context = new InitialContext();
			Object ref = context.lookup("AccessManagerLocal");
			home = (AccessManagerLocalHome) PortableRemoteObject.narrow(ref,
					AccessManagerLocalHome.class);
		} catch (Exception e) {
			throw new ServletException("Lookup of java:/comp/env/ failed");
		}
		log.debug("access manager successful lookuped");
	}

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if (log.isDebugEnabled())
			log.debug("got following action from request: " + action);

		if (action.equalsIgnoreCase("welcome")) {
			log.debug("processing welcome request");
			WelcomeProcessor p = new WelcomeProcessor();
			p.process(req, resp);
			log.debug("sending redirection to ServiceListing.jsp");
			resp.sendRedirect("ServiceListing.jsp");
		} else if (action.equalsIgnoreCase("Logout")) {
			log.debug("processing Logout request");
			LogoutProcessor p = new LogoutProcessor();
			p.process(req, resp);
			resp.sendRedirect("index.jsp");
		} else if (action.equalsIgnoreCase("listMyTasks")) {
			log.debug("processing the listMyTasks request");
			ListMyTasksProcessor p = new ListMyTasksProcessor();
			p.process(req, resp);
			resp.sendRedirect("listTasks.jsp");
		} else if (action.equalsIgnoreCase("showTaskDetails")) {
			log.debug("processing the showTaskDetails request");
			ShowTaskDetailsProcessor p = new ShowTaskDetailsProcessor();
			p.process(req, resp);
			resp.sendRedirect("ShowTaskDetails.jsp");
		} else if (action.equalsIgnoreCase("completeTask")) {
			log.debug("processing the completeTask request");
			CompleteTaskProcessor p = new CompleteTaskProcessor();
			p.process(req, resp);
			resp.sendRedirect("index.jsp");
		} else if (action.equalsIgnoreCase("StartService")) {
			log.debug("processing the StartService request");
			StartServiceProcessor p = new StartServiceProcessor();
			p.process(req, resp);
			resp.sendRedirect("RCSDataPage.jsp");
		} else if (action.equalsIgnoreCase("ValidateInputData")) {
			log.debug("processing the StartService request");
			RCSDisplayDataFormProcessor p = new RCSDisplayDataFormProcessor();
			p.process(req, resp);
			resp.sendRedirect("RCSDataPage.jsp");
		} else if (action.equalsIgnoreCase("ServiceHandlingPostSignRequest")) {
			log.debug("processing the StartService request");
			RCSPostSignRequestProcessor p = new RCSPostSignRequestProcessor();
			p.process(req, resp);
			resp.sendRedirect("index.jsp");
		} else {
			HttpSession session = req.getSession(false);
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out
					.println("<html><head><title>After after welcome!</title></head>");
			out.println("<body>");
			out
					.println("<a href=\"ServiceHandlingTest?action=welcome1\">test it again</a>");
			out.println("<p/>");
			out.println("current access session id = ");
			out.println((String) session.getAttribute("ASID"));
			out.println("<br/>");
			out.println("</body>");
			out.println("</html>");
			out.close();
		}
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