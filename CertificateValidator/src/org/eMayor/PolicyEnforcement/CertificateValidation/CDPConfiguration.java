/*
 * Created on 19 Απρ 2005
 *
 */
package org.eMayor.PolicyEnforcement.CertificateValidation;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * <p>Title: CDPConfiguration </p>
 * <p>Description: A wrapper class for a CDP configuration xml file.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Expertnet S.A.</p>
 * @author Alexandros Kaliontzoglou
 *
 */
public class CDPConfiguration {

	Vector cdpUris = new Vector();
	
    /** A wrapper for a CDP configuration file. CDPConfiguration parses
     * 	the xml structure of the CRLDistributionPoints.xml file, that contains the uris
     *  where CRLs are available for downloading.
     */
	public CDPConfiguration() {
		
		Document sourceDoc = null;
        DocumentBuilderFactory dFactory = null;
        
        if (dFactory == null) {
        	//Instantiate a DocumentBuilderFactory if not already instantiated
            dFactory = DocumentBuilderFactory.newInstance();
            // And setNamespaceAware (required when parsing xsl files)
            dFactory.setNamespaceAware(true);
        }

        try {
            //Use the DocumentBuilderFactory to create a DocumentBuilder.
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            //Use the DocumentBuilder to parse the source
            InputStream configIs = this.getClass().getResourceAsStream("CRLDistributionPoints.xml");
            sourceDoc = dBuilder.parse(configIs);
                    
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		
		NodeList list = sourceDoc.getElementsByTagName("CDP");
		for (int i=0; i<list.getLength(); i++) {
			
			Element node = (Element) list.item(i);
			NodeList sublist = node.getElementsByTagName("ServerUrl");
			String serverUrl = ((Text)((Element) sublist.item(0)).getFirstChild()).getNodeValue();
			sublist = node.getElementsByTagName("CRLFileName");
			String CRLFileName = ((Text)((Element) sublist.item(0)).getFirstChild()).getNodeValue();
			String cdpUri = serverUrl + "/" + CRLFileName;
			cdpUris.add(cdpUri);
		}
	}
	
    /** Returns the uris of availble crl servers packed in a Vector object.
     *  @return The Vector containing the Uris of usable CDPs.
     */
	public Vector getCDPs() {
		return cdpUris;
	}
}
