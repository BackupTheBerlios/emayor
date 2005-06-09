/*
 * Created on Feb 15, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.kernel.ServicesInfo;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class WelcomeProcessor extends AbstractProcessor {
    private static Logger log = Logger.getLogger(WelcomeProcessor.class);

    /**
     *  
     */
    public WelcomeProcessor() {
        super();
    }

    public String process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("-> start processing ...");
        String ret = "ServiceListing.jsp";
        HttpSession session = req.getSession();
        try {
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            AccessManagerLocal access = serviceLocator.getAccessManager();
            // the number "-1001" means the user id is not used
            ServicesInfo servicesInfo = access.listAvailableServices("-1001");
            log.debug("got the services ("
                    + servicesInfo.getServicesInfo().length
                    + ") -> inserting into session");
            session.setAttribute("SERVICES_INFO", servicesInfo
                    .getServicesInfo());
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            // TODO hadle exception
        } catch (AccessException aex) {
            log.error("caught ex: " + aex.toString());
            // TODO handle ex
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}