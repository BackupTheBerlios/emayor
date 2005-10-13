/*
 * Copyright  1999-2004 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.eMayor.PolicyEnforcement.XMLSignature;








import java.io.StringReader;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import org.apache.xml.security.keys.KeyInfo;



//import org.apache.xml.security.samples.utils.resolver.OfflineResolver;
import org.apache.xml.security.signature.XMLSignature;

import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.apache.log4j.*;
import org.eMayor.PolicyEnforcement.E_PolicyEnforcementException;


/**
 *
 *
 *
 *
 * @author $Author: emayor $
 *
 */
public class VerifySignature {
	private static final Logger log = Logger.getLogger(VerifySignature.class);
	
  
   public boolean Verify(String SignedDocument) throws E_PolicyEnforcementException {
      if (log.isDebugEnabled()) 
   		log.debug("PE::VerifySignature:: Startig process ...");
      log.debug("PE::VerifySignature:: Got XML Document \n" + SignedDocument);
      if (SignedDocument==null) throw new E_PolicyEnforcementException("PE::VerifySignature:: Ivalid String"); 
      javax.xml.parsers.DocumentBuilderFactory dbf =
      javax.xml.parsers.DocumentBuilderFactory.newInstance();

     

      dbf.setNamespaceAware(true);
     
      boolean result = true;
     

      try {
      	
      		javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
      		db.setErrorHandler(new org.apache.xml.security.utils.IgnoreAllErrorHandler());
      		StringReader myStrReader = new StringReader(SignedDocument);
    		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::got the String Reader...");
    		
    		InputSource myInputSource = new InputSource(myStrReader);
      		
      		org.w3c.dom.Document doc = db.parse(myInputSource);
     		
     		if (log.isDebugEnabled()) log.debug("PE::VerifySignature:: Document parsed .. looking for ds:Signature elements ..");
            Element nscontext = XMLUtils.createDSctx(doc, "ds",
                                                  Constants.SignatureSpecNS);
     		NodeList myNodeList = XPathAPI.selectNodeList(doc,
     				"//ds:Signature", nscontext);
     		int Nodes = myNodeList.getLength();
     		if (log.isDebugEnabled()) log.debug("PE::VerifySignature:: Found " + Nodes + "Signatures");
     		
     		
     		
     		for (int i=0;i<Nodes;i++) {
	         Element sigElement = (Element)myNodeList.item(i);
	         if (log.isDebugEnabled()) log.debug("PE::VerifySignature:: Get Signature number " + Nodes);
	        
	         XMLSignature signature = new XMLSignature(sigElement,
	         		"");
	
	        
	         KeyInfo ki = signature.getKeyInfo();
	
	         if (ki != null) {
	            if (ki.containsX509Data()) {
	            	if (log.isDebugEnabled()) log.debug("PE::VerifySignature::Could find a X509Data element in the KeyInfo");
	            }
	
	            X509Certificate cert = signature.getKeyInfo().getX509Certificate();
	
	            if (cert != null) {
	            	if (log.isDebugEnabled()) log.debug("PE::VerifySignature::Try to verify the signature using the X509 Certificate: " + cert);
	               
	            	boolean SigResult = signature.checkSignatureValue(cert);
	            	if (log.isDebugEnabled()) log.debug("PE::VerifySignature::Result for Signature Nr."+i+" is: " +(SigResult ? "valid (good)"
                            : "invalid !!!!! (bad)"));
	               result = result && SigResult;
	            } else {
	            	if (log.isDebugEnabled()) log.debug("PE::VerifySignature::Did not find a Certificate, try to get the key info...");
	
	               PublicKey pk = signature.getKeyInfo().getPublicKey();
	              
	               
	               if (pk != null) {
	               	if (log.isDebugEnabled())  log.debug("PE::VerifySignature::Try to verify the signature using the public key: "
	                     + pk);
	                 
	               	boolean ResultSignVer = signature.checkSignatureValue(pk);
	               	if (log.isDebugEnabled())  log.debug("PE::VerifySignature::The XML signature is "
	                                     + (ResultSignVer
	                                        ? "valid (good)"
	                                        : "invalid !!!!! (bad)"));
	               	result = result && ResultSignVer;
	               } else {
	               	if (log.isDebugEnabled())  log.debug("PE::VerifySignature::Did not find a public key, so I can't check the signature");
	               	result = false;
	               }
	            }
	         } else {
	         	if (log.isDebugEnabled())  log.debug("PE::VerifySignature::Did not find a KeyInfo");
	         	result = false;
	         }
         }
	    } catch (Exception ex) {
	    	if (log.isDebugEnabled()) log.debug("PE::VerifySignature::Exception " + ex.toString());
	    	throw new E_PolicyEnforcementException(
					"PolicyEnforcement::VerifySignature:: Exception \n"
						+ ex.toString());
	    	
	    }
	    if (log.isDebugEnabled()) log.debug("PE::VerifySignature::end processing with result " + (result? "Signature Valid": "Signature Invalid"));  
      
	    return result;
   }

   static {
   
   
      org.apache.xml.security.Init.init();
   }
}
