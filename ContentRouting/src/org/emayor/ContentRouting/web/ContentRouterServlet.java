package org.emayor.ContentRouting.web;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.servlet.http.HttpServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.emayor.ContentRouting.ejb.AccessPointNotFoundException;
import org.emayor.ContentRouting.ejb.BindingTemplateNotFoundException;
import org.emayor.ContentRouting.ejb.OrganisationNotFoundException;
import org.emayor.ContentRouting.ejb.ServiceNotFoundException;

import org.emayor.ContentRouting.interfaces.ContentRouter;
import org.emayor.ContentRouting.interfaces.ContentRouterHome;

/**
 * Servlet Class
 * 
 * @web.servlet name="GetAccessPoint" display-name="ContentRouter Servlet"
 *              description="eMayor Content Routing Information"
 * 
 * @web.servlet-mapping url-pattern="/GetAccessPoint"
 * 
 * @web.ejb-ref name = "ejb/ContentRouter" type = "Session" home =
 *              "org.emayor.interfaces.ContentRouterHome" remote =
 *              "org.emayor.interfaces.ContentRouter" description = "Reference
 *              to the eMayor Content Routing EJB"
 * 
 * @jboss.ejb-ref-jndi ref-name = "ejb/ContentRouter" jndi-name =
 *                     "ejb/ContentRouter"
 * 
 * @author Nikolaos Oikonomidis, University of Siegen
 */
public class ContentRouterServlet extends HttpServlet {

    private ContentRouterHome home;

    /**
     *  
     */
    public ContentRouterServlet() {
        super();
    }

    public void init(ServletConfig config) throws ServletException {
        try {
            Context context = new InitialContext();
            Object ref = context.lookup("java:comp/env/ejb/ContentRouter");
            home = (ContentRouterHome) PortableRemoteObject.narrow(ref,
                    ContentRouterHome.class);
        } catch (Exception e) {
            throw new ServletException("Lookup of java:/comp/env/ failed");
        }
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>");
        out.println("eMayor Content Routing Information");
        out.println("</title></head>");
        out.println("<body>");

        try {
            ContentRouter bean = home.create();

            String municpalityName = request.getParameter("municipality");
            String serviceName = request.getParameter("service");

            out.println("<p>");
            out.print("The requested URL is:    ");
            out.print("<span style=color:#0000FF>");
            out.print("<b>");
            out.print(bean.getAccessPoint(municpalityName, serviceName));
            out.print("</b>");
            out.print("</span>");
            out.println("</p>");
        } catch (AccessPointNotFoundException e) {
            out.println("Access Point Not Found");
        } catch (BindingTemplateNotFoundException e) {
            out.println("Binding Template Not Found");
        } catch (OrganisationNotFoundException e) {
            out.println("Organisation Not Found");
        } catch (ServiceNotFoundException e) {
            out.println("Service Not Found");
        } catch (Exception e) {
            out.println(e.getMessage());
            e.printStackTrace(out);
        } finally {
            out.println("</body></html>");
            out.close();
        }
    }
}

