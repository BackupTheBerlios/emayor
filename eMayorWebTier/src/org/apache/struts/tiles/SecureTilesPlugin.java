/*
 * $Header: /home/xubuntu/berlios_backup/github/tmp-cvs/emayor/Repository/eMayorWebTier/src/org/apache/struts/tiles/SecureTilesPlugin.java,v 1.1 2005/11/16 10:51:49 emayor Exp $
 * $Revision: 1.1 $
 * $Date: 2005/11/16 10:51:49 $
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
 * Implements sslext plugin functionality for use with Tiles.
 *
 * @author Kim Turner
 * @author Steve Ditlinger
 */

package org.apache.struts.tiles;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;

import org.apache.commons.digester.*;
import org.apache.commons.logging.*;
import org.apache.struts.action.*;
import org.apache.struts.config.*;
import org.apache.struts.util.*;

public class SecureTilesPlugin extends TilesPlugin implements SecurePlugInInterface {

    protected String addSession = DEFAULT_ADD_SESSION;
    protected String httpPort = DEFAULT_HTTP_PORT;
    protected String httpsPort = DEFAULT_HTTPS_PORT;
    protected String enable = DEFAULT_ENABLE;

    protected String resourceName = "org.apache.struts.action.SecureResources";
    protected MessageResources resources = null;
    protected String registrations[] = {
        "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN",
        "/org/apache/struts/resources/web-app_2_2.dtd",
        "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN",
        "/org/apache/struts/resources/web-app_2_3.dtd"
    };

    protected Log log = LogFactory.getLog(SecureTilesPlugin.class);
    protected ModuleConfig config = null;
    protected ActionServlet servlet = null;
    protected String servletName = null;
    protected Collection servletMappings = new ArrayList();

    public void destroy() {
        super.destroy();
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

    public void init(ActionServlet servlet, ModuleConfig moduleConfig)
            throws ServletException {

        this.config = moduleConfig;
        this.servlet = servlet;
        initMappings();
        servlet.getServletContext().setAttribute(SECURE_PLUGIN, this);
        super.init(servlet, moduleConfig);
    }

    protected void initRequestProcessorClass(ModuleConfig config) throws ServletException {
        String tilesProcessorClassname = SecureTilesRequestProcessor.class.getName();
        ControllerConfig ctrlConfig = config.getControllerConfig();
        String configProcessorClassname = ctrlConfig.getProcessorClass();
        Class configProcessorClass;
        try {
            configProcessorClass = RequestUtils.applicationClass(configProcessorClassname);
        } catch (java.lang.ClassNotFoundException ex) {
            log.fatal("Can't set TilesRequestProcessor: bad class name '"
                    + configProcessorClassname
                    + "'.");
            throw new ServletException(ex);
        }
        if (configProcessorClassname.equals(RequestProcessor.class.getName())
                || configProcessorClassname.endsWith(tilesProcessorClassname)) {
            ctrlConfig.setProcessorClass(tilesProcessorClassname);
            return;
        }
        Class tilesProcessorClass = SecureTilesRequestProcessor.class;
        if (!tilesProcessorClass.isAssignableFrom(configProcessorClass)) { // Not compatible
            String msg = "TilesPlugin : Specified RequestProcessor not compatible with TilesRequestProcessor";
            if (log.isFatalEnabled()) {
                log.fatal(msg);
            }
            throw new ServletException(msg);
        } // end if
    }

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
        if (log.isDebugEnabled()) {
            log.debug("Scanning web.xml for ActionServlet URL mappings");
        }
        InputStream input = null;
        try {
            input =
                    servlet.getServletContext().getResourceAsStream("/WEB-INF/web.xml");
            digester.parse(input);
        } catch (Throwable e) {
            log.error(resources.getMessage("configWebXml"), e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    ;
                }
            }
        }
    }

    protected void initResources() throws ServletException {

        try {
            resources = MessageResources.getMessageResources(resourceName);
        } catch (MissingResourceException e) {
            log.error("Cannot load internal resources from '" + resourceName + "'",
                    e);
            throw new UnavailableException
                    ("Cannot load internal resources from '" + resourceName + "'");
        }
    }

    public void addServletMapping(String servletName, String urlPattern) {

        if (log.isDebugEnabled()) {
            log.debug("Process servletName=" + servletName +
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
