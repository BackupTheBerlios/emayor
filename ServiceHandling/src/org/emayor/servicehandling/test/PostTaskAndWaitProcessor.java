/*
 * Created on Mar 22, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.emayor.rcs.datachecker.DataCheckerException;
import org.emayor.rcs.datachecker.RCSDataChecker;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utclient.CVDocumentTypes;
import org.emayor.servicehandling.utclient.InputDataCollector;
import org.emayor.servicehandling.utclient.UserTaskServiceClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author tku
 */
public class PostTaskAndWaitProcessor extends AbstractProcessor {
    private static final Logger log = Logger
            .getLogger(PostTaskAndWaitProcessor.class);

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
            Task task = (Task) session.getAttribute("CURR_TASK");
            int taskType = task.getTaskType();
            String taskId = req.getParameter("taskid");
            String xmldoc = task.getXMLDocument();

            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringReader reader = new StringReader(xmldoc);
            InputSource inputSource = new InputSource(reader);
            Document root = builder.parse(inputSource);
            if (root != null)
                log.debug("root: " + root.toString());
            else
                log.debug("root is NULL");

            String reqForename = req.getParameter("REQ_FORENAME");
            String reqSurname = req.getParameter("REQ_SURNAME");
            String reqEmail = req.getParameter("REQ_EMAIL");


            if (taskType == CVDocumentTypes.CV_USER_REGISTRATION_REQUEST) {
            	String reqTitle = req.getParameter("REQ_TITLE");
                String reqSex = req.getParameter("REQ_SEX");
                String reqLang = req.getParameter("REQ_LANG");
                
	            	Node node = XPathAPI
					.selectSingleNode(
	                    root,
	                    "/UserProfile/CitizenDetails/CitizenName/CitizenNameForename/text()");
	        	node
	            	.setNodeValue((reqForename != null && reqForename.length() != 0) ? (reqForename)
	                    : ("-"));
	        	
	        	node = XPathAPI
	            	.selectSingleNode(
	                    root,
	                    "/UserProfile/CitizenDetails/CitizenName/CitizenNameSurname/text()");
	        	node
	            	.setNodeValue((reqSurname != null && reqSurname.length() != 0) ? (reqSurname)
	                    : ("-"));
	        	
	        	node = XPathAPI
	            	.selectSingleNode(
	                    root,
						"/UserProfile/CitizenDetails/ContactDetails/Email/EmailAddress/text()");
	        	node
	            .setNodeValue((reqEmail != null && reqEmail.length() != 0) ? (reqEmail)
	                    : ("-"));
	        	
	        	node = XPathAPI
	        	.selectSingleNode(
	                root,
					"/UserProfile/CitizenDetails/PreferredLanguages/text()");
	        	node
	            .setNodeValue((reqLang != null && reqLang.length() != 0) ? (reqLang)
	                    : ("-"));
	        	
	        	node = XPathAPI
	        	.selectSingleNode(
	                root,
					"/UserProfile/CitizenDetails/CitizenName/CitizenNameTitle/text()");
		    	node
		        .setNodeValue((reqTitle != null && reqTitle.length() != 0) ? (reqTitle)
		                : ("-"));
		
		    	node = XPathAPI
		    	.selectSingleNode(
		            root,
					"/UserProfile/CitizenDetails/CitizenSex/text()");
				node
			    .setNodeValue((reqSex != null && reqSex.length() != 0) ? (reqSex)
			            : ("-"));
	
	        	TransformerFactory tFactory = TransformerFactory.newInstance();
	            Transformer transformer = tFactory.newTransformer();
	            DOMSource source = new DOMSource(root);
	            StringWriter sw = new StringWriter();
	            StreamResult result = new StreamResult(sw);
	            transformer.transform(source, result);
	            String xmlString = sw.toString();
	            if (log.isDebugEnabled())
	                log.debug("got after transformation: " + xmlString);
	            task.setXMLDocument(xmlString);
	        	
				InputDataCollector collector = new InputDataCollector();
				collector.postInputData(task, asid, ssid);
	
				session.removeAttribute("SSID");
				session.removeAttribute("CURR_TASK");
				
				ret = "MainMenu.jsp";
				
				userTaskServiceClient.completeTask(asid, task);

            } else if (taskType == CVDocumentTypes.CV_RESIDENCE_CERTIFICATE_REQUEST
                    || taskType == CVDocumentTypes.CV_RESIDENCE_CERTIFICATE_REQUEST
                    || taskType == CVDocumentTypes.CV_NEGATIVE_RESIDENCE_CERTIFICATE_DOCUMENT) {
                String reqServingMunicipality = req
                        .getParameter("REQ_SERVING_MUNICIPALITY");
                String reqReceivingMunicipality = req
                        .getParameter("REQ_RECEIVING_MUNICIPALITY");
                Node node = XPathAPI
                        .selectSingleNode(
                                root,
                                "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameForename/text()");
                node
                        .setNodeValue((reqForename != null && reqForename
                                .length() != 0) ? (reqForename) : ("-"));
                node = XPathAPI
                        .selectSingleNode(
                                root,
                                "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameSurname/text()");
                node
                        .setNodeValue((reqSurname != null && reqSurname
                                .length() != 0) ? (reqSurname) : ("-"));
                node = XPathAPI
                        .selectSingleNode(
                                root,
                                "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/ContactDetails/Email/EmailAddress/text()");
                node
                        .setNodeValue((reqEmail != null && reqEmail.length() != 0) ? (reqEmail)
                                : ("-"));
                node = XPathAPI
                        .selectSingleNode(root,
                                "/ResidenceCertificationRequestDocument/ServingMunicipalityDetails/text()");
                node
                        .setNodeValue((reqServingMunicipality != null && reqServingMunicipality
                                .length() != 0) ? (reqServingMunicipality)
                                : ("-"));
                node = XPathAPI
                        .selectSingleNode(root,
                                "/ResidenceCertificationRequestDocument/ReceivingMunicipalityDetails/text()");
                node
                        .setNodeValue((reqReceivingMunicipality != null && reqReceivingMunicipality
                                .length() != 0) ? (reqReceivingMunicipality)
                                : ("-"));
                RCSDataChecker dataChecker = new RCSDataChecker();
                boolean decision = dataChecker.checkData(root);
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                DOMSource source = new DOMSource(root);
                StringWriter sw = new StringWriter();
                StreamResult result = new StreamResult(sw);
                transformer.transform(source, result);
                String xmlString = sw.toString();
                if (log.isDebugEnabled())
                    log.debug("got after transformation: " + xmlString);
                task.setXMLDocument(xmlString);
                if (decision) {
                    log.debug("the data has been filled in properly");
                    log
                            .debug("presenting the data to be signed and posted back to the BPEL");
                    session.setAttribute("CURR_TASK", task);
                    session.setAttribute("SSID", ssid);
                    //session.setAttribute("SNAME", sname);
                    session.setAttribute("SLEEP_TIME", "0");
                    session.setAttribute("REDIRECTION_URL",
                            "ServiceHandlingTest?action=RCSDataCompletePage");
                    session.setAttribute("PAGE_TITLE",
                            "Waiting for input data page!");
                    session.setAttribute("REDIRECTION_TEXT",
                            "Please wait a while - we are working for you!");
                    session
                            .setAttribute("REDIRECTION_CANCEL_ACTION",
                                    "Welcome");
                    session.setAttribute("REDIRECTION_ACTION",
                            "ServiceHandlingTest");
                    ret = "JustWait.jsp";
                } else {
                    log.debug("still missing some input data!!!!");
                    log.debug("redirecing to the RCS data incomplet");
                    session.setAttribute("CURR_TASK", task);
                    session.setAttribute("SSID", ssid);
                    //session.setAttribute("SNAME", sname);
                    session.setAttribute("SLEEP_TIME", "0");
                    session
                            .setAttribute("REDIRECTION_URL",
                                    "ServiceHandlingTest?action=RCSDataNotCompletePage");
                    session.setAttribute("PAGE_TITLE",
                            "Waiting for input data page!");
                    session.setAttribute("REDIRECTION_TEXT",
                            "Please wait a while - we are working for you!");
                    session
                            .setAttribute("REDIRECTION_CANCEL_ACTION",
                                    "Welcome");
                    session.setAttribute("REDIRECTION_ACTION",
                            "ServiceHandlingTest");
                    ret = "JustWait.jsp";
                }
            }
        } catch (ParserConfigurationException pcex) {
            log.error("caught ex: " + pcex.toString());
            // TODO handle ex
        } catch (SAXException saxex) {
            log.error("caught ex: " + saxex.toString());
            // TODO handle ex
        } catch (TransformerException tex) {
            log.error("caught ex: " + tex.toString());
            // TODO handle ex
        } catch (UserTaskException utex) {
            log.error("caught ex: " + utex.toString());
            // TODO handle ex
        } catch (DataCheckerException ex) {
            log.error("caught ex: " + ex.toString());
            // TODO handle ex
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }
}