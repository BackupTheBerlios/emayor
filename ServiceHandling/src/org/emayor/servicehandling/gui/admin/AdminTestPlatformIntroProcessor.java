/*
 * Created on Jun 8, 2005
 */
package org.emayor.servicehandling.gui.admin;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;

/**
 * @author tku
 */
public class AdminTestPlatformIntroProcessor extends
        AbstractRequestProcessor {
    private static final Logger log = Logger
            .getLogger(AdminTestPlatformIntroProcessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.emayor.servicehandling.gui.admin.IRequestProcessor#process(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("-> start processing ...");
        String ret = "admin/FatalError.jsp";
        HttpSession session = req.getSession(false);
        if (session == null) {
            log.debug("no valid session !");
        } else {
        	try {
        	TreeMap map = new TreeMap();
        	Config config = Config.getInstance();
            map.put(new Integer(PlatformTest.TEST_MAIL),"Check for mail service ("+config.getProperty(Config.EMAYOR_NOTIFICATION_EMAIL_SMTP_HOST)+")...");
            map.put(new Integer(PlatformTest.TEST_JNDI),"Check if JNDI is functional ("+config.getProperty(Config.FORWARD_MANAGER_QUEUE_HOST)+") ...");
            map.put(new Integer(PlatformTest.TEST_LOGIN),"Check login procedure ...");
            map.put(new Integer(PlatformTest.TEST_CR),"Check content routing ("+config.getProperty(Config.EMAYOR_CONTENT_ROUTING_LOCAL_INQUIRY)+") ...");
            map.put(new Integer(PlatformTest.TEST_CONFIG),"Validate configuration ...");
            map.put(new Integer(PlatformTest.TEST_BPEL),"Check for BPEL Domain ("+config.getProperty(Config.BPEL_ENGINE_UT_SECURITY_DOMAIN_NAME)+")...");
            map.put(new Integer(PlatformTest.TEST_UT),"Check if Taskmanager is functional ("+config.getProperty(Config.BPEL_ENGINE_UT_PROVIDER_URL)+")...");
            //map.put(new Integer(PlatformTest.TEST_SERV),"Test availability of active services (start/stop every active service once - currently endless loops may occur if services already running) ...");
            session.setAttribute("ETESTS",map);
        	ret = "admin/TestPlatformIntro.jsp";
        	} catch (Exception e) {
        	}
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}