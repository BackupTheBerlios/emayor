/*
 * Created on Jun 20, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.emayor.servicehandling.kernel.Task;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author tku
 */
public class RCSDataNotCompleteProcessor extends AbstractProcessor {
    private final static Logger log = Logger
            .getLogger(RCSDataNotCompleteProcessor.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.emayor.servicehandling.test.IProcessor#process(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public String process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.debug("-> start processing ...");
        String ret = "Error.jsp";
        try {
            HttpSession session = req.getSession();
            String asid = (String) session.getAttribute("ASID");
            String ssid = (String) session.getAttribute("SSID");
            String role = (String) session.getAttribute("ROLE");
            if (log.isDebugEnabled()) {
                log.debug("got asid : " + asid);
                log.debug("got ssid : " + ssid);
                log.debug("got role : " + role);
            }
            Task task = (Task) session.getAttribute("CURR_TASK");
            String taskId = req.getParameter("taskid");
            String xmldoc = task.getXMLDocument();
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringReader reader = new StringReader(xmldoc);
            InputSource inputSource = new InputSource(reader);
            Document root = builder.parse(inputSource);
            if (root != null) {
                log.debug("root: " + root.toString());
            } else
                log.debug("root is NULL");

            session.setAttribute("CURR_TASK", task);
            String reqForename = XPathAPI
                    .selectSingleNode(
                            root,
                            "/ResidenceCertificationRequestDocument/RequesterDetails/CitizenName/CitizenNameForename/text()")
                    .getNodeValue();
            String reqSurname = XPathAPI
                    .selectSingleNode(
                            root,
                            "/ResidenceCertificationRequestDocument/RequesterDetails/CitizenName/CitizenNameSurname/text()")
                    .getNodeValue();
            String reqEMail = XPathAPI
                    .selectSingleNode(
                            root,
                            "/ResidenceCertificationRequestDocument/RequesterDetails/ContactDetails/Email/EmailAddress/text()")
                    .getNodeValue();
            String reqServingMunicipality = XPathAPI
                    .selectSingleNode(root,
                            "/ResidenceCertificationRequestDocument/ServingMunicipalityDetails/text()")
                    .getNodeValue();
            String reqReceivingMunicipality = XPathAPI
                    .selectSingleNode(root,
                            "/ResidenceCertificationRequestDocument/ReceivingMunicipalityDetails/text()")
                    .getNodeValue();
            session.setAttribute("REQ_FORENAME", reqForename);
            session.setAttribute("REQ_SURNAME", reqSurname);
            session.setAttribute("REQ_EMAIL", reqEMail);
            session.setAttribute("REQ_SERVING_MUNICIPALITY",
                    reqServingMunicipality);
            session.setAttribute("REQ_RECEIVING_MUNICIPALITY",
                    reqReceivingMunicipality);

            ret = "RCSDataPage.jsp";
        } catch (Exception tex) {
            log.error("caught ex: " + tex.toString());
            // TODO handle ex
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

}