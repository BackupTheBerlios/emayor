/*
 * Created on Feb 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.Test;

import javax.servlet.http.HttpServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import org.eMayor.PolicyEnforcement.interfaces.*;
import org.eMayor.PolicyEnforcement.C_UserProfile;

import java.io.PrintWriter;
import java.security.cert.X509Certificate;


/**
 * Servlet Class
 *
 * @web.servlet              name="eMayorTest"
 *                           display-name="Name for eMayorTest"
 *                           description="Description for eMayorTest"
 * @web.servlet-mapping      url-pattern="/eMayorTest"
 * @web.servlet-init-param   name="A parameter"
 *                           value="A value"
  * @web.ejb-ref 			 name = "ejb/PolicyEnforcement"
 * 							 type = "Session"
 * 							 home = "org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcement"
 * 							 remote = "org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcementHome"
 * 							 description = "Reference to the Fibo EJB"
 * @jboss.ejb-ref-jndi 		 ref-name = "ejb/PolicyEnforcement"
 * 							 jndi-name = "ejb/PolicyEnforcement"
 */
public class eMayorTest extends HttpServlet {

	/**
	 * 
	 * @uml.property name="home"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private PolicyEnforcementHome home;

	public eMayorTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		try{
			Context myContext = new InitialContext();
			Object ref = myContext.lookup("java:/comp/env/ejb/PolicyEnforcement");
			home = (PolicyEnforcementHome) PortableRemoteObject.narrow(ref, PolicyEnforcementHome.class);
		} catch (Exception e) {
			throw new ServletException("Lookup of java:/comp/env failed");
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException,
		IOException {
		// TODO Auto-generated method stub
		X509Certificate certChain[] = (X509Certificate[]) req.getAttribute("javax.servlet.request.X509Certificate");
		 
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>");
		out.println("eMayor Policy Enforcemnet Test");
		out.println("</title></head>");
		out.println("<body>");
		out.println("<p>");
		
		try {
			PolicyEnforcement bean = home.create();
			C_UserProfile myUserProfile = new C_UserProfile(bean.F_getUserProfile(certChain));
			bean.remove();
			if (myUserProfile==null){
				out.println("<h1>You did not provide a certificate!</h1>");
			} else {
				out.println("<h1>The following profile was created</h1>");
				out.println("<p>");
				out.println("<br>");
				out.print("Your Name is: <b>");
				out.println(myUserProfile.getUserName());
				out.println("</b><p>");
				out.println("<br>");
				out.print("Your Email is: <b>");
				out.println(myUserProfile.getUserEmail());
				out.println("</b><p>");
				out.println("<br>");
				out.print("Your Organisation Unit is: <b>");
				out.println(myUserProfile.getOrganisationUnit());
				out.println("</b><p>");
				out.println("<br>");
				out.print("Your Organisation is: <b>");
				out.println(myUserProfile.getUserOrganisation());
				out.println("</b><p>");
				out.println("<br>");
				out.print("Your State is: <b>");
				out.println(myUserProfile.getUserST());
				out.println("</b><p>");
				out.println("<br>");
				out.print("Your Country is: <b>");
				out.println(myUserProfile.getUserCountry());
				out.println("</b><p>");
				out.println("<br>");
				out.print("Your Roule is: <b>");
				out.println(myUserProfile.getUserRole());
				out.println("</b><p>");
				out.println("<br>");
				out.println("Your Certificate is:");
				out.println(myUserProfile.getX509_CertChain()[0].toString());
				out.println("<p>");
				out.println("<br>");
// Code to test the to Streang anf From Streang Method				
				
			}
			
				
		} catch (Exception e) {
			out.println(e.getMessage());
			e.printStackTrace(out);
		} finally {
			out.println("</body></html>");
			out.close();
		}
		
		
		
		
	}

}