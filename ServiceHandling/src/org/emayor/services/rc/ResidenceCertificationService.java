/*
 * Created on Feb 23, 2005
 */
package org.emayor.services.rc;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.kernel.AbstracteMayorService;
import org.emayor.servicehandling.kernel.IeMayorService;
import org.emayor.servicehandling.kernel.eMayorServiceException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author tku
 */
public class ResidenceCertificationService extends AbstracteMayorService {
	private static Logger log = Logger
			.getLogger(ResidenceCertificationService.class);

	public static final String DEF_XML_FILE = "SampleResidenceCertificationRequestDocument.xml";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IeMayorService#startService(java.lang.String,
	 *      java.lang.String)
	 */
	public void startService(String uid, String ssid)
			throws eMayorServiceException {
		log.debug("-> start processing ...");
		String xmlDocument = this.getXMLDocumentFromResource(DEF_XML_FILE);
		xmlDocument = this.editRequest(xmlDocument,uid);
		this.startIt(IeMayorService.FORWARD_NO, uid, ssid, xmlDocument, "<empty/>");
		log.debug("-> ... processing DONE!");
	}
	
	private String editRequest(String xmlDocument, String uid) {
		String result = xmlDocument;
		C_UserProfile profile = this.getUserProfile(uid); 
		
		log.debug("got xml document: "+xmlDocument);
		log.debug("got user profile: "+profile.toString());
		
		if (profile != null) {
		    try {
		        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        StringReader stringReader = new StringReader(xmlDocument);
		        InputSource inputSource = new InputSource(stringReader);
		        Document document = builder.parse(inputSource);
		        
		        /* forename */
		        String value = profile.getUserName().split(" ")[0];
		        if (value != null)
		        	XPathAPI.selectSingleNode(document, "/ResidenceCertificationRequestDocument/RequesterDetails/CitizenName/CitizenNameForename/text()").setNodeValue(value);
		        
		        /* surename */
		        value = profile.getUserName().split(" ")[1];
		        if (value != null)
		        	XPathAPI.selectSingleNode(document, "/ResidenceCertificationRequestDocument/RequesterDetails/CitizenName/CitizenNameSurname/text()").setNodeValue(value);
		        
		        /* email */
		        value = profile.getUserEmail();
		        if (value != null)
		        	XPathAPI.selectSingleNode(document, "/ResidenceCertificationRequestDocument/RequesterDetails/ContactDetails/Email/EmailAddress/text()").setNodeValue(value);
		        
		        /* language */
		        //value = profile.getUserCountry();
		        //if (value != null)
		        //	XPathAPI.selectSingleNode(document, "/ResidenceCertificationRequestDocument/RequesterDetails/PreferredLanguages/text()").setNodeValue(value);
		        
		        /* loginserver/receiving/serving set to local */
		        Config config = Config.getInstance();
		        value = config.getProperty(Config.EMAYOR_PLATFORM_INSTANCE_ID);
		        if (value != null) {
		        	XPathAPI.selectSingleNode(document, "/ResidenceCertificationRequestDocument/LoginServer/text()").setNodeValue(value);
		        	XPathAPI.selectSingleNode(document, "/ResidenceCertificationRequestDocument/ServingMunicipalityDetails/text()").setNodeValue(value);
		        	XPathAPI.selectSingleNode(document, "/ResidenceCertificationRequestDocument/ReceivingMunicipalityDetails/text()").setNodeValue(value);
		        }
		        
		        TransformerFactory transFactory = TransformerFactory.newInstance();
		        Transformer trans = null;
		        trans = transFactory.newTransformer();
		        StringWriter writer = new StringWriter();
		        DOMSource source = new DOMSource(document);
		        StreamResult stream = new StreamResult(writer);
		        trans.transform(source,stream);
		        result = writer.toString();
		      } catch (Exception e) 
		      {
		        e.printStackTrace();
		        result = e.getMessage();
		      }	
		}
		
		return result;
	}

	public void startService(String uid, String ssid, String requestDocument)
			throws eMayorServiceException {
		log.debug("-> start processing ...");
		throw new eMayorServiceException("NOT SUPPORTED BY THIS SERVICE");
	}

}