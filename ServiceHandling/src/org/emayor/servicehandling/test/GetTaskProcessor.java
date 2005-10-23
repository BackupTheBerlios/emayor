/*
 * Created on Mar 22, 2005
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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utclient.CVDocumentTypes;
import org.emayor.servicehandling.utclient.UserTaskServiceClient;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author tku
 */
public class GetTaskProcessor extends AbstractProcessor {
    private static final Logger log = Logger.getLogger(GetTaskProcessor.class);

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
            //String sname = (String) session.getAttribute("SNAME");
            if (log.isDebugEnabled()) {
                log.debug("got asid : " + asid);
                log.debug("got ssid : " + ssid);
                //log.debug("got sname: " + sname);
                log.debug("got role : " + role);
            }
            UserTaskServiceClient userTaskServiceClient = new UserTaskServiceClient();
            Task task = userTaskServiceClient.lookupTask(asid, ssid);
            int taskType = task.getTaskType();
            if (task == null) {
                log.debug("still waiting cause got null ref !");
                ret = "JustWait.jsp";
            } else {
                log.debug("SUCCESS - got the task");
                DocumentBuilderFactory factory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                String xmldoc = task.getXMLDocument();
                StringReader reader = new StringReader(xmldoc);
                InputSource inputSource = new InputSource(reader);
                Document root = builder.parse(inputSource);
                if (root != null)
                    log.debug("root: " + root.toString());
                else
                    log.debug("root is NULL");

                session.setAttribute("CURR_TASK", task);

                if (taskType == CVDocumentTypes.CV_USER_REGISTRATION_REQUEST) {
                    String reqForename = XPathAPI
                            .selectSingleNode(root,
                                    "/UserProfile/CitizenName/CitizenNameForename/text()")
                            .getNodeValue();
                    String reqSurname = XPathAPI
                            .selectSingleNode(root,
                                    "/UserProfile/CitizenName/CitizenNameSurname/text()")
                            .getNodeValue();
                    String reqEMail = XPathAPI
                            .selectSingleNode(root,
                                    "/UserProfile/ContactDetails/Email/EmailAddress/text()")
                            .getNodeValue();
                    session.setAttribute("REQ_FORENAME", reqForename);
                    session.setAttribute("REQ_SURNAME", reqSurname);
                    session.setAttribute("REQ_EMAIL", reqEMail);
                    ret = "URSDataPage.jsp";
                } else if (taskType == CVDocumentTypes.CV_RESIDENCE_CERTIFICATE_REQUEST
                        || taskType == CVDocumentTypes.CV_RESIDENCE_CERTIFICATE_REQUEST
                        || taskType == CVDocumentTypes.CV_NEGATIVE_RESIDENCE_CERTIFICATE_DOCUMENT) {
                    String reqForename = XPathAPI
                            .selectSingleNode(
                                    root,
                                    "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameForename/text()")
                            .getNodeValue();
                    String reqSurname = XPathAPI
                            .selectSingleNode(
                                    root,
                                    "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameSurname/text()")
                            .getNodeValue();
                    String reqEMail = XPathAPI
                            .selectSingleNode(
                                    root,
                                    "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/ContactDetails/Email/EmailAddress/text()")
                            .getNodeValue();

                    session.setAttribute("REQ_FORENAME", reqForename);
                    session.setAttribute("REQ_SURNAME", reqSurname);
                    session.setAttribute("REQ_EMAIL", reqEMail);
                    ret = "RCSDataPage.jsp";
                }
            }
        } catch (UserTaskException utex) {
            log.error("caught ex: " + utex.toString());
            // TODO handle ex
        } catch (ParserConfigurationException pcex) {
            log.error("caught ex: " + pcex.toString());
            // TODO handle ex
        } catch (SAXException saxex) {
            log.error("caught ex: " + saxex.toString());
            // TODO handle ex
        } catch (TransformerException tex) {
            log.error("caught ex: " + tex.toString());
            // TODO handle ex
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }
}