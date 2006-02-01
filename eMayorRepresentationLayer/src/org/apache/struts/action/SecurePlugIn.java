/*
 * $Header: /home/xubuntu/berlios_backup/github/tmp-cvs/emayor/Repository/eMayorRepresentationLayer/src/org/apache/struts/action/SecurePlugIn.java,v 1.1 2006/02/01 15:32:56 emayor Exp $
 * $Revision: 1.1 $
 * $Date: 2006/02/01 15:32:56 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Struts", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

/**
 * Implements sslext plugin functionality
 *
 * @author Steve Ditlinger
 * @author Kim Turner
 */

package org.apache.struts.action;

import org.apache.struts.action.SecurePlugInInterface;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;

import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.util.*;

public class SecurePlugIn implements SecurePlugInInterface {


    protected String addSession = DEFAULT_ADD_SESSION;
    protected String httpPort = DEFAULT_HTTP_PORT;
    protected String httpsPort = DEFAULT_HTTPS_PORT;
    protected String enable = DEFAULT_ENABLE;

    private Log sLog = LogFactory.getLog(SecurePlugIn.class);

    /**
     * The application configuration for our owning module.
     */
    private ModuleConfig config = null;

    /**
     * The {@link ActionServlet} owning this application.
     */
    private ActionServlet servlet = null;

    /**
     * The servlet name under which we are registered in our web application
     * deployment descriptor.
     */
    private String servletName = null;

    /**
     * The servlet mappings for the Struts Action Servlet
     */
    private Collection servletMappings = new ArrayList();

    /**
     * The set of public identifiers, and corresponding resource names, for
     * the versions of the configuration file DTDs that we know about.  There
     * <strong>MUST</strong> be an even number of Strings in this list!
     */
    protected String registrations[] = {
        "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN",
        "/org/apache/struts/resources/web-app_2_2.dtd",
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN",
        "/org/apache/struts/resources/web-app_2_3.dtd"
    };

    /**
     * The resources object for our internal resources.
     */
    protected MessageResources resources = null;


    /**
     * The Java base name of our internal resources.
     * @since Struts 1.1
     */
    protected String resourceName = "org.apache.struts.action.SecureResources";

    /**
     * Initialize some instance variables and
     * the ServletContext (application) to make this PlugIn's
     * properties accessible from the whole app.
     *
     * @param servlet The Struts ActionServlet instance for the whole application
     * @param config The ModuleConfig for our owning module
     * @exception ServletException if we cannot configure ourselves correctly
     */
    public void init(ActionServlet servlet, ModuleConfig config)
            throws ServletException {

        this.config = config;
        this.servlet = servlet;

        initMappings();
        servlet.getServletContext().setAttribute(SECURE_PLUGIN, this);
    }

    /**
     * Remove stuff from the ServletContext (application).
     */
    public void destroy() {
        servlet.getServletContext().removeAttribute(SECURE_PLUGIN);
    }

    public void setHttpsPort(String s) {
        this.httpsPort = s;
    }

    public void setHttpPort(String s) {
        this.httpPort = s;
    }

    public String getHttpsPort() {
        return this.httpsPort;
    }

    public String getHttpPort() {
        return this.httpPort;
    }

    public Collection getServletMappings() {
        return this.servletMappings;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String s) {
        enable = s;
    }

    public String getAddSession() {
        return addSession;
    }

    public void setAddSession(String addSession) {
        this.addSession = addSession;
    }

    /**
     *
     * @return
     */
    public boolean getSslExtAddSession() {

        return Boolean.valueOf(getAddSession()).booleanValue();
    }

    /**
     *
     * @return
     */
    public boolean getSslExtEnable() {

            return Boolean.valueOf(getEnable()).booleanValue();
   }

    /**
     * Initialize the servlet mappings under which the Struts ActionServlet
     * is accessed.  This will be used when searching for action mappings in the
     * Struts configuration files when creating links, etc.
     */
    protected void initMappings() throws ServletException {

        // Get the action servlet name
        this.servletName = servlet.getServletConfig().getServletName();

        // Prepare a Digester to scan the web application deployment descriptor
        Digester digester = new Digester();
        digester.push(this);
        digester.setNamespaceAware(true);
        digester.setValidating(false);

        // Register our local copy of the DTDs that we can find
        for (int i = 0; i < registrations.length; i += 2) {
            URL url = this.getClass().getResource(registrations[i + 1]);
            if (url != null) {
                digester.register(registrations[i], url.toString());
            }
        }

        // Configure the processing rules that we need
        digester.addCallMethod("web-app/servlet-mapping",
                "addServletMapping", 2);
        digester.addCallParam("web-app/servlet-mapping/servlet-name", 0);
        digester.addCallParam("web-app/servlet-mapping/url-pattern", 1);

        // Process the web application deployment descriptor
        if (sLog.isDebugEnabled()) {
            sLog.debug("Scanning web.xml for ActionServlet URL mappings");
        }
        InputStream input = null;
        try {
            input =
                    servlet.getServletContext().getResourceAsStream("/WEB-INF/web.xml");
            digester.parse(input);
        } catch (Throwable e) {
            sLog.error(resources.getMessage("configWebXml"), e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    ;
                }
            }
        }

//          // Record a servlet context attribute (if appropriate)
//          if (sLog.isDebugEnabled()) {
//              sLog.debug("Mapping for servlet '" + servletName + "' = '" +
//                  servletMapping + "'");
//          }
//
//          if (servletMapping != null) {
//              getServletContext().setAttribute(Globals.SERVLET_KEY, servletMapping);
//          }

    }

    /**
     * Initialize our internal MessageResources bundle.
     *
     * @exception ServletException if we cannot initialize these resources
     */
    protected void initResources() throws ServletException {

        try {
            resources = MessageResources.getMessageResources(resourceName);
        } catch (MissingResourceException e) {
            sLog.error("Cannot load internal resources from '" + resourceName + "'",
                    e);
            throw new UnavailableException
                    ("Cannot load internal resources from '" + resourceName + "'");
        }
    }

    /**
     * Remember all servlet mapping from our web application deployment
     * descriptor, if it is for the Struts ActionServlet.
     *
     * @param servletName The name of the servlet being mapped
     * @param urlPattern The URL pattern to which this servlet is mapped
     */
    public void addServletMapping(String servletName, String urlPattern) {

        if (sLog.isDebugEnabled()) {
            sLog.debug("Process servletName=" + servletName +
                    ", urlPattern=" + urlPattern);
        }
        if (servletName == null) {
            return;
        }
        if (servletName.equals(this.servletName)) {
            this.servletMappings.add(urlPattern);
        }
    }
}
