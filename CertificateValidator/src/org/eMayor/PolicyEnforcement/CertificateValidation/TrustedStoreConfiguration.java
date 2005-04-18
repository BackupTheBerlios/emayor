/*
 * Created on 19 Απρ 2005
 *
 */
package org.eMayor.PolicyEnforcement.CertificateValidation;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * <p>Title: TrustedStoreConfiguration </p>
 * <p>Description: A wrapper class for a trusted store configuration xml file.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Expertnet S.A.</p>
 * @author Alexandros Kaliontzoglou
 *
 */
public class TrustedStoreConfiguration {
	
	private String _storePath;
	private String _storePass;

    /** A wrapper for a trusted store configuration file. TrustedStoreConfiguration 
     * parses the xml structure of the TrustedStoreConfiguration.xml file, 
     * that contains the path and password to access a trusted store (java keystore)
     * where trusted certificates reside.
     */
	public TrustedStoreConfiguration() {

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
            InputStream configIs = this.getClass().getResourceAsStream("TrustedStoreConfiguration.xml");
            sourceDoc = dBuilder.parse(configIs);
                    
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
			NodeList list = sourceDoc.getElementsByTagName("StorePath");
			_storePath = ((Text)((Element) list.item(0)).getFirstChild()).getNodeValue();
			list = sourceDoc.getElementsByTagName("StorePass");
			_storePass = ((Text)((Element) list.item(0)).getFirstChild()).getNodeValue();

	}
	
    /** Returns the store pass as a String.
     *  @return The String containing the pass to the keystore file.
     */
	public String getStorePass() {
		return _storePass;
	}
	
    /** Returns the store path as a String.
     *  @return The String containing the path of the keystore file.
     */
	public String getStorePath() {
		return _storePath;
	}

}
