/*
 * Created on Feb 24, 2005
 */
package org.emayor.servicehandling.test;

import java.io.IOException;
import java.io.StringReader;

import javax.ejb.RemoveException;
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
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.kernel.Task;
import org.emayor.servicehandling.kernel.UserTaskException;
import org.emayor.servicehandling.utclient.InputDataCollector;
import org.emayor.servicehandling.utclient.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author tku
 */
public class StartServiceProcessor extends AbstractProcessor {
	private static Logger log = Logger.getLogger(StartServiceProcessor.class);

	/**
	 *  
	 */
	public StartServiceProcessor() {
		super();
	}

	public String process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("-> start processing ...");
		String ret = "Error.jsp";
		HttpSession session = req.getSession();
		String asid = (String) session.getAttribute("ASID");
		if (log.isDebugEnabled())
			log.debug("got asid from session: " + asid);
		String serviceName = req.getParameter("ServiceName");
		if (log.isDebugEnabled())
			log.debug("got service name: " + serviceName);
		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			AccessManagerLocal access = serviceLocator.getAccessManager();
			String ssid = access.startService(asid, serviceName);
			if (log.isDebugEnabled()) {
				log.debug("got asid: " + asid);
				log.debug("got ssid: " + ssid);
			}
			session.setAttribute("SSID", ssid);

			InputDataCollector collector = new InputDataCollector();
			Task task = collector.getInputDataForm(asid, ssid);

			if (log.isDebugEnabled())
				log.debug("got request xml: " + task.getXMLDocument());
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
			session.setAttribute("CURR_TASK", task);
			session.setAttribute("REQ_FORENAME", reqForename);
			session.setAttribute("REQ_SURNAME", reqSurname);
			session.setAttribute("REQ_EMAIL", reqEMail);
			access.remove();
			ret = "RCSDataPage.jsp";
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			// TODO hadle exception
		} catch (AccessException aex) {
			log.error("caught ex: " + aex.toString());
			// TODO handle ex
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
		} catch(RemoveException rex) {
			log.error("caught ex: " + rex.toString());
			// TODO handle ex
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}