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
public class GetInputDataPageProcessor extends AbstractProcessor {
    private static final Logger log = Logger
            .getLogger(GetInputDataPageProcessor.class);

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
            if (task == null) {
                log.debug("still waiting cause got null ref !");
                ret = "JustWait.jsp";
            } else {
            	int taskType = task.getTaskType();
                log.debug("SUCCESS - got the task");
                DocumentBuilderFactory factory = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                String xmldoc = task.getXMLDocument();
                StringReader reader = new StringReader(xmldoc);
                InputSource inputSource = new InputSource(reader);
                Document root = builder.parse(inputSource);
                if (root != null) {
                    log.debug("root: " + root.toString());
                } else
                    log.debug("root is NULL");

                session.setAttribute("CURR_TASK", task);
                
                if (taskType == CVDocumentTypes.CV_USER_REGISTRATION_REQUEST) {
                	String reqTitle = XPathAPI
                    .selectSingleNode(
                            root,
                            "/UserProfile/CitizenDetails/CitizenName/CitizenNameTitle/text()")
                    .getNodeValue();
                	String reqForename = XPathAPI
                    .selectSingleNode(
                            root,
                            "/UserProfile/CitizenDetails/CitizenName/CitizenNameForename/text()")
                    .getNodeValue();
                    String reqSurname = XPathAPI
					
                    .selectSingleNode(
                            root,
							"/UserProfile/CitizenDetails/CitizenName/CitizenNameSurname/text()")
                    .getNodeValue();

                    String reqEMail = XPathAPI

                    .selectSingleNode(
                            root,
                    		"/UserProfile/CitizenDetails/ContactDetails/Email/EmailAddress/text()")
                    .getNodeValue();
                    String reqSex = XPathAPI
                    .selectSingleNode(
                            root,
                    		"/UserProfile/CitizenDetails/CitizenSex/text()")
                    .getNodeValue();
                    String reqLang = XPathAPI
                    .selectSingleNode(
                            root,
                    		"/UserProfile/CitizenDetails/PreferredLanguages/text()")
                    .getNodeValue();
                    
                    session.setAttribute("REQ_FORENAME", reqForename);
    	            session.setAttribute("REQ_TITLE", reqTitle);
    	            session.setAttribute("REQ_LANG", reqLang.toLowerCase());
    	            session.setAttribute("REQ_SEX", (reqSex.equals("f") || reqSex.equals("m")) ? reqSex : "m");
                    session.setAttribute("REQ_SURNAME", reqSurname);
                    session.setAttribute("REQ_EMAIL", reqEMail);

                    ret = "URSDataPage.jsp";
                } else if (taskType == CVDocumentTypes.CV_RESIDENCE_CERTIFICATE_REQUEST) {

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