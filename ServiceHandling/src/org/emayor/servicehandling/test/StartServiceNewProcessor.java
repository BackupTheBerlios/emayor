/*
 * Created on Mar 22, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;

import javax.ejb.RemoveException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @author tku
 */
public class StartServiceNewProcessor extends AbstractProcessor {
    private static final Logger log = Logger
            .getLogger(StartServiceNewProcessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.emayor.servicehandling.test.IProcessor#process(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String ret = "Error.jsp";
        HttpSession session = req.getSession();
        String asid = (String) session.getAttribute("ASID");
        if (log.isDebugEnabled())
            log.debug("got asid from session: " + asid);
        String serviceName = req.getParameter("ServiceName");
        if (log.isDebugEnabled())
            log.debug("got service name: " + serviceName);
        String role = (String) session.getAttribute("ROLE");
        try {
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            AccessManagerLocal access = serviceLocator.getAccessManager();
            String ssid = access.startService(asid, serviceName);
            if (log.isDebugEnabled()) {
                log.debug("got asid: " + asid);
                log.debug("got ssid: " + ssid);
                log.debug("got role: " + role);
            }
            session.setAttribute("SSID", ssid);
            session.setAttribute("SLEEP_TIME", "10");
            session.setAttribute("REDIRECTION_URL", "ServiceHandlingTest?action=GetInputDataPage");
            session.setAttribute("PAGE_TITLE", "Waiting for input data page!");
            session.setAttribute("REDIRECTION_TEXT", "Please wait 10 sec - we are working for you!");
            session.setAttribute("REDIRECTION_CANCEL_ACTION", "Welcome");
            session.setAttribute("REDIRECTION_ACTION", "ServiceHandlingTest");

            /*
             * 
             * InputDataCollector collector = new InputDataCollector(); Task
             * task = collector.getInputDataForm(asid, ssid);
             * 
             * if (log.isDebugEnabled()) log.debug("got request xml: " +
             * task.getXMLDocument()); DocumentBuilderFactory factory =
             * DocumentBuilderFactory .newInstance(); DocumentBuilder builder =
             * factory.newDocumentBuilder(); String xmldoc =
             * task.getXMLDocument(); StringReader reader = new
             * StringReader(xmldoc); InputSource inputSource = new
             * InputSource(reader); Document root = builder.parse(inputSource);
             * if (root != null) log.debug("root: " + root.toString()); else
             * log.debug("root is NULL"); String reqForename = XPathAPI
             * .selectSingleNode( root,
             * "/ResidenceCertificationRequestDocument/RequesterDetails/CitizenName/CitizenNameForename/text()")
             * .getNodeValue(); String reqSurname = XPathAPI .selectSingleNode(
             * root,
             * "/ResidenceCertificationRequestDocument/RequesterDetails/CitizenName/CitizenNameSurname/text()")
             * .getNodeValue(); String reqEMail = XPathAPI .selectSingleNode(
             * root,
             * "/ResidenceCertificationRequestDocument/RequesterDetails/ContactDetails/Email/EmailAddress/text()")
             * .getNodeValue(); session.setAttribute("CURR_TASK", task);
             * session.setAttribute("REQ_FORENAME", reqForename);
             * session.setAttribute("REQ_SURNAME", reqSurname);
             * session.setAttribute("REQ_EMAIL", reqEMail);
             */
            access.remove();
            ret = "JustWait.jsp";
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            // TODO hadle exception
        } catch (AccessException aex) {
            log.error("caught ex: " + aex.toString());
            // TODO handle ex
        } catch (RemoveException rex) {
            log.error("caught ex: " + rex.toString());
            // TODO handle ex
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}