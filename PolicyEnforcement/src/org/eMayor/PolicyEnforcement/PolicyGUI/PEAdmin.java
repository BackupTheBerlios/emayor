/*
 * Created on 08.08.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement.PolicyGUI;

import javax.servlet.http.HttpServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;

/**
 * Servlet Class
 *
 * @web.servlet              name="PEAdmin"
 *                           display-name="Name for PEAdmin"
 *                           description="Description for PEAdmin"
 * @web.servlet-mapping      url-pattern="/PEAdmin"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
 */
public class PEAdmin extends HttpServlet {

	/**
	 * 
	 */
	public PEAdmin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// TODO Auto-generated method stub
	
		
		
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException,
		IOException {
		// TODO Auto-generated method stub
		String page ="http://141.99.152.70";
		resp.sendRedirect(page);
	}

	protected void doPost(
		HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
