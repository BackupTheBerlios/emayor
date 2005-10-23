/*
 * Created on Feb 27, 2005
 */
package org.emayor.rcs.datachecker;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author tku
 */
public class RCSDataChecker {
    private static Logger log = Logger.getLogger(RCSDataChecker.class);

    /**
     *  
     */
    public RCSDataChecker() {
        super();
    }

    public boolean checkData(String xmlDocument) throws DataCheckerException {
        log.debug("-> start processing ...");
        boolean ret = false;
        log.debug("-> get the document root");
        if (log.isDebugEnabled())
            log.debug("got xml:\n " + xmlDocument);
        Document root = this.getRootDocument(xmlDocument);
        log.debug("-> checking the data");
        ret = this.checkRequesterDetails(root)
                && this.checkConcernedPersonDetails(root);
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public boolean checkData(Document xmlDocument) throws DataCheckerException {
        log.debug("-> start processing ...");
        boolean ret = false;
        ret = this.checkRequesterDetails(xmlDocument)
                && this.checkConcernedPersonDetails(xmlDocument);
        log.debug("-> ... processing DONE!");
        return ret;
    }

    private Document getRootDocument(String xmlDocument)
            throws DataCheckerException {
        log.debug("getRootDocument -> start processing ...");
        Document ret = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            StringReader stringReader = new StringReader(xmlDocument);
            InputSource inputSource = new InputSource(stringReader);
            ret = documentBuilder.parse(inputSource);
            log.debug("getRootDocument -> the xml has been parsed");
        } catch (FactoryConfigurationError fcer) {
            log.error("getRootDocument -> caught ex: " + fcer.toString());
            throw new DataCheckerException("");
        } catch (ParserConfigurationException pcex) {
            log.error("getRootDocument -> caught ex: " + pcex.toString());
            throw new DataCheckerException("");
        } catch (IOException ioex) {
            log.error("getRootDocument -> caught ex: " + ioex.toString());
            throw new DataCheckerException("");
        } catch (SAXException saxex) {
            log.error("getRootDocument -> caught ex: " + saxex.toString());
            throw new DataCheckerException("");
        }
        log.debug("getRootDocument -> ... processing DONE!");
        return ret;
    }

    private boolean checkRequesterDetails(Document root)
            throws DataCheckerException {
        log.debug("checkRequesterDetails -> start processing ...");
        boolean ret = false;
        final String XP_FORNAME = "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameForename/text()";
        final String XP_SURNAME = "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameSurname/text()";
        final String XP_EMAILADR = "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/ContactDetails/Email/EmailAddress/text()";
        try {
            String forename = XPathAPI.selectSingleNode(root, XP_FORNAME)
                    .getNodeValue();
            String surname = XPathAPI.selectSingleNode(root, XP_SURNAME)
                    .getNodeValue();
            String emailAddress = XPathAPI.selectSingleNode(root, XP_EMAILADR)
                    .getNodeValue();
            if (log.isDebugEnabled()) {
                log.debug("checkRequesterDetails -> got forename: " + forename);
                log.debug("checkRequesterDetails -> got surname : " + surname);
                log.debug("checkRequesterDetails -> got email   : "
                        + emailAddress);
            }
            ret = (forename != null && forename.trim().length() != 0 && !forename
                    .trim().equals("-"))
                    && (surname != null && surname.trim().length() != 0 && !surname
                            .trim().equals("-"))
                    && (emailAddress != null && emailAddress.trim().length() != 0 && !emailAddress
                            .trim().equals("-"));
        } catch (TransformerException tex) {
            log.error("checkRequesterDetails -> caught ex: " + tex.toString());
            throw new DataCheckerException("");
        }
        log.debug("checkRequesterDetails -> ... processing DONE!");
        return ret;
    }

    private boolean checkConcernedPersonDetails(Document root)
            throws DataCheckerException {
        log.debug("checkConcernedPersonDetails -> start processing ...");
        boolean ret = false;
        final String XP_FORNAME = "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameForename/text()";
        final String XP_SURNAME = "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameSurname/text()";
        final String XP_EMAILADR = "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/ContactDetails/Email/EmailAddress/text()";
        try {
            String forename = XPathAPI.selectSingleNode(root, XP_FORNAME)
                    .getNodeValue();
            String surname = XPathAPI.selectSingleNode(root, XP_SURNAME)
                    .getNodeValue();
            String emailAddress = XPathAPI.selectSingleNode(root, XP_EMAILADR)
                    .getNodeValue();
            if (log.isDebugEnabled()) {
                log.debug("checkConcernedPersonDetails -> got forename: "
                        + forename);
                log.debug("checkConcernedPersonDetails -> got surname : "
                        + surname);
                log.debug("checkConcernedPersonDetails -> got email   : "
                        + emailAddress);
            }
            ret = (forename != null && forename.trim().length() != 0)
                    && (surname != null && surname.trim().length() != 0)
                    && (emailAddress != null && emailAddress.trim().length() != 0);
        } catch (TransformerException tex) {
            log.error("checkConcernedPersonDetails -> caught ex: "
                    + tex.toString());
            throw new DataCheckerException("");
        }
        log.debug("checkConcernedPersonDetails -> ... processing DONE!");
        return ret;
    }
}