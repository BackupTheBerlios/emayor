/*
 * Created on Feb 22, 2005
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
import org.emayor.servicehandling.utclient.CVDocumentTypes;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author tku
 */
public class ShowTaskDetailsProcessor extends AbstractProcessor {
	private static Logger log = Logger
			.getLogger(ShowTaskDetailsProcessor.class);

	/**
	 *  
	 */
	public ShowTaskDetailsProcessor() {
		super();
	}

	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "Error.jsp";
		try {
			HttpSession session = req.getSession(false);
			String taskId = req.getParameter("taskId");
			String taskIndex = req.getParameter("taskIndex");
			Task[] tasks = (Task[]) session.getAttribute("MY_TASKS");
			int index = Integer.parseInt(taskIndex);
			Task task = tasks[index];
			if (task.getTaskId().equals(taskId)) {
				log.debug("the task id is OK");
				String xmldoc = task.getXMLDocument();
				if (log.isDebugEnabled())
					log.debug("got xml: \n" + xmldoc);
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				StringReader reader = new StringReader(xmldoc);
				InputSource inputSource = new InputSource(reader);
				Document root = builder.parse(inputSource);
				if (root != null)
					log.debug("root: " + root.toString());

				if (task.getTaskType() == CVDocumentTypes.CV_RESIDENCE_CERTIFICATE_REQUEST) {
					log
							.debug("this is a residance certification request document");
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
					String reqEmail = XPathAPI
							.selectSingleNode(
									root,
									"/ResidenceCertificationRequestDocument/RequesterDetails/ContactDetails/Email/EmailAddress/text()")
							.getNodeValue();

					session.setAttribute("CURR_TASK", task);
					session.setAttribute("REQ_FORENAME", reqForename);
					session.setAttribute("REQ_SURNAME", reqSurname);
					session.setAttribute("REQ_EMAIL", reqEmail);
					ret = "CVDisplayRCRequest.jsp";
				} else if (task.getTaskType() == CVDocumentTypes.CV_RESIDENCE_DOCUMENT) {
					log.debug("this is a residance certification document");
					String forename = XPathAPI
							.selectSingleNode(
									root,
									"/ResidenceCertificationDocument/CertifiedConcernedPersons/CitizenDetails/CitizenName/CitizenNameForename/text()")
							.getNodeValue();
					String surname = XPathAPI
							.selectSingleNode(
									root,
									"/ResidenceCertificationDocument/CertifiedConcernedPersons/CitizenDetails/ContactDetails/Email/EmailAddress/text()")
							.getNodeValue();
					String email = XPathAPI
							.selectSingleNode(
									root,
									"/ResidenceCertificationDocument/CertifiedConcernedPersons/CitizenDetails/CitizenName/CitizenNameSurname/text()")
							.getNodeValue();
					String sex = XPathAPI
							.selectSingleNode(
									root,
									"/ResidenceCertificationDocument/CertifiedConcernedPersons/CitizenDetails/CitizenSex/text()")
							.getNodeValue();
					session.setAttribute("CURR_TASK", task);
					session.setAttribute("FORENAME", forename);
					session.setAttribute("SURNAME", surname);
					session.setAttribute("EMAIL", email);
					session.setAttribute("SEX", sex);
					ret = "ShowTaskDetails.jsp";
				} else {
					log.debug("unknown document type");
					session.setAttribute("ERR_MSG", "Unknown document type");
					ret = "Error.jsp";
				}
			} else {
				log.debug("the task id was wrong !!!!!");
				session.setAttribute("ERR_MSG", "The task id is not valid!");
				ret = "Error.jsp";
			}
		} catch (ParserConfigurationException pcex) {
			log.error("caught ex: " + pcex.toString());
		} catch (SAXException saxex) {
			log.error("caught ex: " + saxex.toString());
		} catch (TransformerException tex) {
			log.error("caught ex: " + tex.toString());
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

}