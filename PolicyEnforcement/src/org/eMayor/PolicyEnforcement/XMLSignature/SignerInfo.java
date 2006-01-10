
package org.eMayor.PolicyEnforcement.XMLSignature;








import iaik.pkcs.pkcs11.provider.IAIKPkcs11;

import java.io.InputStream;
import java.io.StringReader;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Properties;

import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;



/**
 *
 *
 *
 *
 * @author $Author: emayor $
 *
 */
public class SignerInfo {
    private boolean bValidationResult=true;
    private int iSignaturesNumber=0;
    private String[] asSignerRoule = null;
    private String[] asCN = null;
    private boolean[] abValidationResult = null; 
    private IAIKPkcs11 iaikPkcs11Provider_= null;
    
    
    public SignerInfo (String XMLSignedDocument) throws java.lang.Exception{
		 org.apache.xml.security.Init.init();
		 
// parse the input document
		System.out.println("PE::SignerInfo:: Startig process ...");
		if (XMLSignedDocument==null) throw new java.lang.Exception("PE::SignerInfo:: Ivalid Input Document");	
// Create Dom Factory
		javax.xml.parsers.DocumentBuilderFactory dbf =
		javax.xml.parsers.DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        
        
        try {
          	
          		javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
          		db.setErrorHandler(new org.apache.xml.security.utils.IgnoreAllErrorHandler());
          		StringReader myStrReader = new StringReader(XMLSignedDocument);
        		InputSource myInputSource = new InputSource(myStrReader);
          		org.w3c.dom.Document doc = db.parse(myInputSource);
         		System.out.println("PE::SignerInfo:: Document parsed .. looking for ds:Signature elements ..");
        
 // Get Number of Signatures
         		Element nscontext = XMLUtils.createDSctx(doc, "ds",
                Constants.SignatureSpecNS);
         		NodeList myNodeList = XPathAPI.selectNodeList(doc, "//ds:Signature", nscontext);
         		int Nodes = myNodeList.getLength();
         		System.out.println("PE::SignerInfo:: Found " + Nodes + " Signatures");
         		iSignaturesNumber = Nodes;
         		   		
         		
         		if (Nodes>0) {
 // Create arrays
         			asSignerRoule = new String[Nodes];
         			asCN = new String[Nodes];
         			abValidationResult = new boolean[Nodes];
         			
 // Validate each Signature
         			
         			for (int i=0;i<Nodes;i++) {
             			
             			
 // Remove not needed Signatures
             			
             			org.w3c.dom.Document docTemp = (org.w3c.dom.Document)doc.cloneNode(true);
             			
             			if (Nodes>1){
        	     			for (int j=Nodes-1; j>=(i+1); j--) {
        	     				System.out.println("PE::SignerInfo:: Removing" + (j+1) + "Signature");
        	     				NodeList TempNodeList= docTemp.getElementsByTagName("ds:Signature");
        	     				System.out.println("PE::SignerInfo:: Got the SignatureNodeList with " +TempNodeList.getLength()+" Items" );
        	             		
        	     				Node TempNode = TempNodeList.item(j);
        	     				System.out.println("PE::SignerInfo:: Got the SignatureNode " +(j+1));
        	     				if (TempNode==null) System.out.println("PE::VerifySignature:: the SignatureNode " +(j+1)+" is null error");
        	             		Node MyTempRoot = TempNode.getParentNode();
        	             		System.out.println("PE::SignerInfo:: Got the SignatureNode Root ");
        	     				if (TempNode==null) System.out.println("PE::VerifySignature:: the SignatureNode Root is null: error!!!!");
        	             		
        	             		MyTempRoot.removeChild(TempNode);
        	             		System.out.println("PE::SignerInfo:: Removed the SignatureNode " +(j+1));
        	             		
        	     			}
             			}
                 		
                 		Element nscontextTemp = XMLUtils.createDSctx(docTemp, "ds",
                                Constants.SignatureSpecNS);
                 		NodeList myNodeListTemp = XPathAPI.selectNodeList(docTemp,
                 				"//ds:Signature", nscontextTemp);
                 		Element sigElement = (Element)myNodeListTemp.item(i);
                   		System.out.println("PE::SignerInfo:: Get Signature number " + (i+1));
        	        
        	         XMLSignature signature = new XMLSignature(sigElement,
        	         		"");
        	         
        	        
        	         KeyInfo ki = signature.getKeyInfo();
        	
        	         if (ki != null) {
        	            if (ki.containsX509Data()) {
        	            	System.out.println("PE::SignerInfo::Could find a X509Data element in the KeyInfo");
        	            }
        	
        	            X509Certificate cert = signature.getKeyInfo().getX509Certificate();
        	
        	            if (cert != null) {
        	            	System.out.println("PE::SignerInfo::Try to verify the signature using the X509 Certificate" );
        	            	
 	// Get The Siger Role
        	            	
        	            	String SignerRole = this.getSignerRole(cert);
        	            	asSignerRoule[i] = SignerRole;
        	            	String SignerCN = cert.getSubjectDN().getName();
        	            	asCN[i] = SignerCN; 
        	               
        	            	boolean SigResult = signature.checkSignatureValue(cert);
        	            	System.out.println("PE::SignerInfo::Result for Signature Nr."+(i+1)+" is: " +(SigResult ? "valid (good)"
                                    : "invalid !!!!! (bad)"));
        	            	
        	            	
        	            	bValidationResult = bValidationResult && SigResult;
        	            	abValidationResult[i] = SigResult;
        	            	
        	            } else {
        	            	System.out.println("PE::SignerInfo::Did not find a Certificate, try to get the key info...");
        	            	
        	            	
        	            	
        	               PublicKey pk = signature.getKeyInfo().getPublicKey();
        	              
        	               
        	               if (pk != null) {
        	               	System.out.println("PE::SignerInfo::Try to verify the signature using the public key: "
        	                     + pk);
       	              
       // Init the arrays
        	            	String SignerRole = "N/A";
        	            	asSignerRoule[i] = SignerRole;
        	            	String SignerCN = "N/A";
        	            	asCN[i] = SignerCN;  
        	               	boolean ResultSignVer = signature.checkSignatureValue(pk);
        	               	System.out.println("PE::SignerInfo::The XML signature is "
        	                                     + (ResultSignVer
        	                                        ? "valid (good)"
        	                                        : "invalid !!!!! (bad)"));
        	               	bValidationResult = bValidationResult && ResultSignVer;
        	               	abValidationResult[i] = ResultSignVer;
        	               } else {
        	               		throw new Exception("PE::SignerInfo::Error::Did not find a public key, so I can't check the signature");
        	               }
        	            }
        	         } else {
        	         	throw new Exception("PE::SignerInfo::Error::Did not find a KeyInfo");
        	         	
        	         }         			
         			
         			
        			
         			}
         		}
         } catch (Exception ex) {
	    	throw new Exception("PE::SignerInfo:: Exception \n" + ex.toString());
	    }
		System.out.println("PE::SignerInfo:: ... End process");
	}
	
    
 private String getSignerRole(X509Certificate myCert) {
 	
 // This function reads the eMayor X509 Extension
 	
 	String result = "Guest";
 	
 // If no extension found, "Guest" is returned	
 try {
 	
 
 	byte[] b = myCert.getExtensionValue("1.2.3.4");
	if (b != null && b.length > 4) {
		if (b[2] == 19) {
			String myRole = (new String(b)).toString();
			result = myRole.substring(4);
		}
	}
 } catch (Exception e) {
 	System.out.println("PE::SignerInfo:: Unable to parce the eMayor extension:: " + e.toString());
 }
 	return result;
 }
    
// Public Functions for Joerg 
 public boolean getValidationResult() {
 	// Returns the Document Validation Result (All signatures)
 	return bValidationResult;
 }
 
 public int getSignaturesNumber() {
 	// Returns the number of Signatures in the document
 	return iSignaturesNumber;
 }
 
 public String getCommonName(int iSigNr) throws Exception{
 	if ((iSigNr>0) && (iSigNr<= iSignaturesNumber)) {
 		return this.asCN[iSigNr-1];
 	}
 	else 
 	{
 		throw new Exception("PE::SignerInfo::getCommonName::Error::Invalid Signature Number");
 	}
 }
 
 public String getSignerRole(int iSigNr) throws Exception{
 	if ((iSigNr>0) && (iSigNr<= iSignaturesNumber)) {
 		return this.asSignerRoule[iSigNr-1];
 	}
 	else 
 	{
 		throw new Exception("PE::SignerInfo::getSignerRole::Error::Invalid Signature Number");
 	}
 }
 
 public boolean getSignatureStatus(int iSigNr) throws Exception{
 	if ((iSigNr>0) && (iSigNr<= iSignaturesNumber)) {
 		return this.abValidationResult[iSigNr-1];
 	}
 	else 
 	{
 		throw new Exception("PE::SignerInfo::getSignatureStatus::Error::Invalid Signature Number");
 	}
 }
 public void initCryptoProviders()
 {
   System.out.println("initializing: initCryptoProviders()");
   try 
   {
     if ((iaikPkcs11Provider_ = IAIKPkcs11.getProviderInstance(1)) == null) 
     {
       Properties pkcs11ProviderConfig = new Properties();
       // Changed J.Plaz: added the /default folder in the resource address below
       //InputStream configStream = this.getClass().getClassLoader().getResourceAsStream("iaik/pkcs/pkcs11/provider/default/IAIKPkcs11.properties");
       InputStream configStream = this.getClass().getClassLoader().getResourceAsStream("org/emayor/client/controlers/xmlsigner/IAIKPkcs11.properties");
       pkcs11ProviderConfig.load(configStream);
       iaikPkcs11Provider_ = new IAIKPkcs11(pkcs11ProviderConfig);
     }
     if (Security.getProvider(iaikPkcs11Provider_.getName()) == null) 
     {
       Security.addProvider(iaikPkcs11Provider_);
     }
   } 
   catch (Throwable ex) 
   {
     ex.printStackTrace();
   }
   System.err.flush();
  
   System.out.println("Registered providers are:");
   Provider[] providers = Security.getProviders();
   for (int i = 0; i < providers.length; i++) 
   {
     System.out.println("at position " + i + ": " + providers[i]);
   }  
   System.out.println("...finished initializing");
   System.out.flush();
 }
 
 public void unloadCryptoProviders()
 {
    System.out.print("preparing for unloading...");
     try {
       Security.removeProvider(iaikPkcs11Provider_.getName());
     } catch (Throwable ex) {
       ex.printStackTrace();
     }
     
 }
}
