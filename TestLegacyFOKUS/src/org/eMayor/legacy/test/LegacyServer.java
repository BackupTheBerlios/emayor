/*
 * $ Created on 18.08.2005 by tku $
 */
package org.eMayor.legacy.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.xpath.XPathAPI;
import org.eMayor.AdaptationLayer.ejb.M2Einterface;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class LegacyServer extends UnicastRemoteObject implements M2Einterface {
    private final static Logger log = Logger.getLogger(LegacyServer.class);

    private static final Charset charset = Charset.forName("UTF-8");

    /**
     * @throws java.rmi.RemoteException
     */
    public LegacyServer() throws RemoteException {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eMayor.AdaptationLayer.ejb.M2Einterface#ResidenceRequest(java.lang.String)
     */
    public String ResidenceRequest(String request) throws RemoteException {
        // TODO Auto-generated method stub
        if (log.isDebugEnabled())
            log.debug("input document:\n" + request);
        String ret = null;
        try {
            log.debug("get DocumentBuilderFactory");
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            log.debug("parse input -> build DOM");
            InputSource inputSource = new InputSource(new StringReader(request));
            Document root = builder.parse(inputSource);
            log.debug("select the surname node value");
            String municipality = XPathAPI
                    .selectSingleNode(
                            root,
                            "/ResidenceCertificationRequestDocument/ServingMunicipalityDetails/text()")
                    .getNodeValue();
            String forename = XPathAPI
            .selectSingleNode(
                    root,
                    "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameForename/text()")
            .getNodeValue();
            String surname = XPathAPI
            .selectSingleNode(
                    root,
                    "/ResidenceCertificationRequestDocument/ConcernedPersonDetails/CitizenName/CitizenNameSurname/text()")
            .getNodeValue();
            if (log.isDebugEnabled())
                log.debug("got from request document - municipality: " + municipality + ", name: "+forename+" "+surname);
            if (municipality != null && forename != null && surname != null) {
            	ret = this.readDocument("/xml/"+municipality+"/"+forename+surname+".xml");
            	if (ret != null) {
            		if (log.isDebugEnabled()) log.debug("got *POSITIVE* result!");
            	} else {
            		if (log.isDebugEnabled()) log.debug("got *NO* result!");
            	}
            }
            if (ret == null) {
            	ret = this.readDocument("/xml/"+municipality+"/NegativeDocument.xml");
            	if (log.isDebugEnabled()) log.debug("got *NEGATIVE* result!");
            }
            if (ret == null) ret = "";
            
        } catch (Exception ex) {
            log.error("caught ex: " + ex.toString());
        }
        if (log.isDebugEnabled())
            log.debug("response document:\n" + ret);
        return ret;
    }

    private final String readDocument(String doc) {
        String ret = null;
        if (log.isDebugEnabled())
            log.debug("try to read document: " + doc);
        try {
            InputStream is = this.getClass().getResourceAsStream(doc);
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    charset));
            String line = null;
            StringBuffer b = new StringBuffer();
            while ((line = br.readLine()) != null)
                b.append(line.trim());
            if (log.isDebugEnabled())
                log.debug("read document:\n" + b.toString());
            ret = b.toString();
        } catch (Exception ex) {
            log.error("caught ex: " + ex.toString());
        }
        return ret;
    }
}