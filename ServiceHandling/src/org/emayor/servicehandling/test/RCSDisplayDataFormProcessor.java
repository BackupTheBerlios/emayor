/*
 * Created on Feb 25, 2005
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
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utclient.InputDataCollector;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author tku
 */
public class RCSDisplayDataFormProcessor extends AbstractProcessor {
	private static Logger log = Logger
			.getLogger(RCSDisplayDataFormProcessor.class);

	/**
	 *  
	 */
	public RCSDisplayDataFormProcessor() {
		super();
	}

	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "Error.jsp";
		try {
			HttpSession session = req.getSession(false);
			String asid = (String) session.getAttribute("ASID");
			String ssid = (String) session.getAttribute("SSID");
			String role = (String) session.getAttribute("ROLE");
			if (log.isDebugEnabled()) {
				log.debug("got asid: " + asid);
				log.debug("got ssid: " + ssid);
			}
			Task task = (Task) session.getAttribute("CURR_TASK");
			String taskId = req.getParameter("taskid");
			String xmldoc = task.getXMLDocument();
			String reqForename = req.getParameter("REQ_FORENAME");
			String reqSurname = req.getParameter("REQ_SURNAME");
			String reqEmail = req.getParameter("REQ_EMAIL");
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
			Node node = XPathAPI
					.selectSingleNode(
							root,
							"/ResidenceCertificationRequestDocument/RequesterDetails/CitizenName/CitizenNameForename/text()");
			node
					.setNodeValue((reqForename != null && reqForename.length() != 0) ? (reqForename)
							: ("-"));
			node = XPathAPI
					.selectSingleNode(
							root,
							"/ResidenceCertificationRequestDocument/RequesterDetails/CitizenName/CitizenNameSurname/text()");
			node
					.setNodeValue((reqSurname != null && reqSurname.length() != 0) ? (reqSurname)
							: ("-"));
			node = XPathAPI
					.selectSingleNode(
							root,
							"/ResidenceCertificationRequestDocument/RequesterDetails/ContactDetails/Email/EmailAddress/text()");
			node
					.setNodeValue((reqEmail != null && reqEmail.length() != 0) ? (reqEmail)
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
			Task taskNew = collector.validateInputData(task, asid, ssid);
			xmldoc = taskNew.getXMLDocument();
			if (log.isDebugEnabled())
				log.debug("got after validation: " + xmldoc);
			reader = new StringReader(xmldoc);
			inputSource = new InputSource(reader);
			root = builder.parse(inputSource);
			if (root != null)
				log.debug("root: " + root.toString());
			else
				log.debug("root is NULL");
			reqForename = XPathAPI
					.selectSingleNode(
							root,
							"/ResidenceCertificationRequestDocument/RequesterDetails/CitizenName/CitizenNameForename/text()")
					.getNodeValue();
			reqSurname = XPathAPI
					.selectSingleNode(
							root,
							"/ResidenceCertificationRequestDocument/RequesterDetails/CitizenName/CitizenNameSurname/text()")
					.getNodeValue();
			reqEmail = XPathAPI
					.selectSingleNode(
							root,
							"/ResidenceCertificationRequestDocument/RequesterDetails/ContactDetails/Email/EmailAddress/text()")
					.getNodeValue();
			session.setAttribute("CURR_TASK", taskNew);
			session.setAttribute("REQ_FORENAME", reqForename);
			session.setAttribute("REQ_SURNAME", reqSurname);
			session.setAttribute("REQ_EMAIL", reqEmail);
			ret = "RCSDataPage.jsp";
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
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}