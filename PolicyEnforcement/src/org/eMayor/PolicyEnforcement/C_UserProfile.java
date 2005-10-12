/*
 * Created on Feb 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;


import java.io.Serializable;

import java.io.*;
import java.security.cert.X509Certificate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



import org.apache.log4j.Logger;
import org.apache.xml.security.keys.content.x509.XMLX509Certificate;
import org.w3c.dom.*;
import org.xml.sax.*;
//import InputSource;
import org.apache.xml.security.utils.Constants;


/**
 * @author root
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class C_UserProfile implements Serializable{
	private static final Logger log = Logger
	.getLogger(C_UserProfile.class);


/**
 * 
 * @uml.property name="m_S_UserName" 
 */
private String m_S_UserName;

/**
 * 
 * @uml.property name="m_S_UserEmail" 
 */
private String m_S_UserEmail;

/**
 * 
 * @uml.property name="m_S_UserRole" 
 */
private String m_S_UserRole;

/**
 * 
 * @uml.property name="m_S_OrganisationUnit" 
 */
private String m_S_OrganisationUnit;

/**
 * 
 * @uml.property name="m_S_UserOrganisation" 
 */
private String m_S_UserOrganisation;

/**
 * 
 * @uml.property name="m_S_UserST" 
 */
private String m_S_UserST;

/**
 * 
 * @uml.property name="m_S_UserCountry" 
 */
private String m_S_UserCountry;

/**
 * 
 * @uml.property name="m_X509_CertChain" 
 */
private X509Certificate m_X509_CertChain[];

/**
 * 
 * @uml.property name="m_S_UserName"
 */
public String getUserName() {
	return this.m_S_UserName;

}

/**
 * 
 * @uml.property name="m_S_UserName"
 */
public void setUserName(String sUserName) {
	this.m_S_UserName = sUserName;
}

/**
 * 
 * @uml.property name="m_S_UserEmail"
 */
public String getUserEmail() {
	return this.m_S_UserEmail;
}

/**
 * 
 * @uml.property name="m_S_UserEmail"
 */
public void setUserEmail(String sUserEmail) {
	this.m_S_UserEmail = sUserEmail;
}

/**
 * 
 * @uml.property name="m_S_UserRole"
 */
public String getUserRole() {
	return this.m_S_UserRole;
}

/**
 * 
 * @uml.property name="m_S_UserRole"
 */
public void setUserRole(String sUserRole) {
	this.m_S_UserRole = sUserRole;
}

/**
 * 
 * @uml.property name="m_S_OrganisationUnit"
 */
public String getOrganisationUnit() {
	return this.m_S_OrganisationUnit;
}

/**
 * 
 * @uml.property name="m_S_OrganisationUnit"
 */
public void setOrganisationUnit(String sOrganisationUnit) {
	this.m_S_OrganisationUnit = sOrganisationUnit;
}

/**
 * 
 * @uml.property name="m_S_UserOrganisation"
 */
public String getUserOrganisation() {
	return this.m_S_UserOrganisation;
}

/**
 * 
 * @uml.property name="m_S_UserOrganisation"
 */
public void setUserOrganisation(String sUserOrganisation) {
	this.m_S_UserOrganisation = sUserOrganisation;
}

/**
 * 
 * @uml.property name="m_S_UserST"
 */
public String getUserST() {
	return this.m_S_UserST;
}

/**
 * 
 * @uml.property name="m_S_UserST"
 */
public void setUserST(String sUserST) {
	this.m_S_UserST = sUserST;
}

/**
 * 
 * @uml.property name="m_S_UserCountry"
 */
public String getUserCountry() {
	return this.m_S_UserCountry;
}

/**
 * 
 * @uml.property name="m_S_UserCountry"
 */
public void setUserCountry(String sUserCountry) {
	this.m_S_UserCountry = sUserCountry;
}

/**
 * 
 * @uml.property name="m_X509_CertChain"
 */
public X509Certificate[] getX509_CertChain() {
	return this.m_X509_CertChain;
}

/**
 * 
 * @uml.property name="m_X509_CertChain"
 */
public void setX509_CertChain(X509Certificate[] newChain) {
	this.m_X509_CertChain = newChain;
}

// Constructors For XML and XML String
public class E_UserProfileException extends Exception
{
   public E_UserProfileException(String s)
   {
      super(s);
   }
}

public C_UserProfile(X509Certificate[] x509_CertChain) throws
E_UserProfileException{
	
	if (log.isDebugEnabled()) log.debug("C_UserProfile(X509Cert)::Start processing...");
	
	if ((x509_CertChain==null) || (x509_CertChain.length < 1)) {
		throw new E_UserProfileException("C_UserProfile(X509Certificate):: No Certificate Provided");
		
	} else {
		
		this.setX509_CertChain(x509_CertChain);
		java.security.Principal newPrincipal = x509_CertChain[0].getSubjectDN();		
		
		C_ParseX509DN myX509DNParser = new C_ParseX509DN(newPrincipal.getName());
		this.setUserName(myX509DNParser.m_S_CN);
		this.setUserEmail(myX509DNParser.m_S_Email);
		this.setOrganisationUnit(myX509DNParser.m_S_OU);
		this.setUserOrganisation(myX509DNParser.m_S_O);
		this.setUserST(myX509DNParser.m_S_ST);
		this.setUserCountry( myX509DNParser.m_S_C);
		byte[] b = x509_CertChain[0].getExtensionValue("1.2.3.4");
		if (b != null && b.length > 4) {
			if (b[2] == 19) {
				String myRole = (new String(b)).toString();
				this.setUserRole(myRole.substring(4));
				

			}

		} else {
			this.setUserRole("Guest");

		}
		
		
	}
	if (log.isDebugEnabled()) log.debug("C_UserProfile(X509Certificate)::End processing...");
}

public C_UserProfile(Document InputDocument) throws
E_UserProfileException {
	if (log.isDebugEnabled()) log.debug("C_UserProfile(Document)::Start processing...");
	if (InputDocument == null) throw new E_UserProfileException("C_UserProfile(Document):: No Document Provided");
	F_CreateUserProfile(InputDocument);
	if (log.isDebugEnabled()) log.debug("C_UserProfile(Document)::End processing...");
}

private boolean F_CreateUserProfile(Document InputDocument) throws
E_UserProfileException {
	if (log.isDebugEnabled()) log.debug("F_CreateUserProfile(Document)::Start processing...");
	try {
		
		// Chek if this document is an User Profile Docuemnt
		Element myRoot= InputDocument.getDocumentElement();
		if (myRoot.getNodeName()!="UserProfile")
		{
			throw new E_UserProfileException("C_UserProfile:: Invaild Input Dom Document");
		}
		
//		 Get The UserName
		
			this.setUserName(myRoot.getAttribute("UserName"));
			
		
//		 Get The UserEmail
		
			this.setUserEmail(myRoot.getAttribute("UserEmail"));
		
		/*rivate String m_S_UserRole;
private X509Certificate m_X509_CertChain[];
*/
		
//			 Get The UserRole
			
			this.setUserRole(myRoot.getAttribute("UserRole"));
				
//			 Get The OrganisationUnit
			
			this.setOrganisationUnit(myRoot.getAttribute("OrganisationUnit"));

//			 Get The UserOrganisation
			
			this.setUserOrganisation(myRoot.getAttribute("UserOrganisation"));

//			 Get The UserST
			
			this.setUserST(myRoot.getAttribute("UserST"));
			
//			 Get The UserCountry
			
			this.setUserCountry(myRoot.getAttribute("UserCountry"));

//			Get The cerificates Chain
			
			
			
			NodeList MyNodeList = myRoot.getElementsByTagName("X509CertChain");
			Element MyCerificatesChain = (Element)(MyNodeList).item(0);
			int iNumberOfCerts = Integer.parseInt(MyCerificatesChain.getAttribute("Length"));
			Element MyFirstChiald = (Element) MyCerificatesChain.getFirstChild();
			this.m_X509_CertChain = new X509Certificate[iNumberOfCerts];			
			while (MyFirstChiald!=null)
			{
				int ChainOrder = Integer.parseInt(MyFirstChiald.getAttribute("ChainOrder"));
				
				XMLX509Certificate MyXMLCert = new XMLX509Certificate(MyFirstChiald, "");
				
				this.m_X509_CertChain[ChainOrder] = MyXMLCert.getX509Certificate();
				
				MyFirstChiald = (Element) MyFirstChiald.getNextSibling();
			}
			
			
			
			
			
			if (log.isDebugEnabled()) log.debug("F_CreateUserProfile(Document)::End processing...");	
	return true;
	
	}
	catch (Exception e){
		throw new E_UserProfileException("F_CreateUserProfile::Parce Error \n" + e.toString());
	}
	}
private Document F_StringtoDocument(String myXMLDocument) throws E_UserProfileException{
	
	
	if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::Start processing...\n");
	if (myXMLDocument==null) throw new E_UserProfileException("C_UserProfile::F_StringtoDocument:: Ivalid String"); 
	
	try {
		
		
		DocumentBuilderFactory myFactory = DocumentBuilderFactory.newInstance();
		
		myFactory.setNamespaceAware(true);
		
		
		
		DocumentBuilder myDocBuilder = myFactory.newDocumentBuilder();
	
				
		StringReader myStrReader = new StringReader(myXMLDocument);
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::got the String Reader...");
		
		InputSource myInputSource = new InputSource(myStrReader);
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::got the Input Source...");
		Document myDocument = myDocBuilder.parse(myInputSource);
		//Document myDocument = myDocBuilder.parse(myXMLDocument);
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::got the DOM Document...");
		
		
		
		
		//Reparce the Document
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::Rebuild The Document...");
		Document myNewDocument = myDocBuilder.newDocument();
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::Got New Document...");
		Element myRoot = myDocument.getDocumentElement();
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::Got Root node of the Old Document...");
		Element myNewRoot = (Element) myNewDocument.importNode(myRoot, false);
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::Import the root into the new Document...");
		NodeList MyCertChainList = myDocument.getElementsByTagName("X509CertChain");
		
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::Got the Certificate Chain List with " + MyCertChainList.getLength() + "Elements...");
		
		
		Element myCertChain = (Element) MyCertChainList.item(0);
		
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::Got the First Certificate Chain from List...");
		Element myNewCertChain = (Element)myNewDocument.importNode(myCertChain, false);
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::Import the Chain in the new Document...");
		
		String LenghtAttrib = myCertChain.getAttribute("Length");
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::Got Attribute Lenght = "+LenghtAttrib+"...");
		int iCerts = Integer.parseInt(LenghtAttrib);
		//if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::Parced Attribute Lenght = "+iCerts+"...");
		
		NodeList MyCertsList = myDocument.getElementsByTagName("X509Certificate");
		//if (MyCertsList.getLength()<1) MyCertsList = myDocument.getElementsByTagName("ds:X509Certificate");
		if (MyCertsList.getLength()<1) throw new E_UserProfileException("F_StringtoDocument(String)::Error: Unable to get retrive the X509Certificate Node");
		for (int i = 0; i< iCerts; i++)
		{
			Node myCert = MyCertsList.item(i);
		
			
			Node myNewCert = myNewDocument.importNode(myCert, true);
		
			
			
			myNewCertChain.appendChild(myNewCert);
		
		}
		myNewRoot.appendChild(myNewCertChain);
		
		myNewDocument.appendChild(myNewRoot);
		
		
		if (log.isDebugEnabled()) log.debug("F_StringtoDocument(String)::End processing..."); 
		return myNewDocument;
	}
	catch (Exception e)
	{
		e.printStackTrace();
		throw new E_UserProfileException("F_StringtoDocument(String)::Error \n  " + e.toString());
		
	}
	
}
	
public C_UserProfile(String myXMLDocument) throws E_UserProfileException{
	if (log.isDebugEnabled()) log.debug("C_UserProfile(String)::Start processing...");
	if (myXMLDocument == null) throw new E_UserProfileException("C_UserProfile(String)::Error:: received a null string  ");
		
   this.F_CreateUserProfile(F_StringtoDocument(myXMLDocument));
   if (log.isDebugEnabled()) log.debug("C_UserProfile(String)::End processing...");
	
	
}
public Document F_getUserProfileasDomDocument() throws
E_UserProfileException {
	if (log.isDebugEnabled()) log.debug("F_getUserProfileasDomDocument::Start processing...");
	Document myDocument = null;
	try {
		String sigConst = Constants.getSignatureSpecNSprefix();
		Constants.setSignatureSpecNSprefix("");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		//dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
	    
		myDocument = db.newDocument();
		//Create The root
		Element newRoot = myDocument.createElement("UserProfile");
		
		
		// Create the User Name Element
		
		newRoot.setAttribute("UserName", this.m_S_UserName);
		
		
		// Create the User Email Element
		newRoot.setAttribute("UserEmail", this.m_S_UserEmail);
		
		
//		 Create the User Role Element
		newRoot.setAttribute("UserRole", this.m_S_UserRole);
		
		
		
//		OrganisationUnit Element
		newRoot.setAttribute("OrganisationUnit", this.m_S_OrganisationUnit);
		
		
// 		UserOrganisation Element		
		newRoot.setAttribute("UserOrganisation", this.m_S_UserOrganisation);
		
		
//		UserST Element		
		newRoot.setAttribute("UserST", this.m_S_UserST);
		
		
//		UserCountry Element
		newRoot.setAttribute("UserCountry", this.m_S_UserCountry);
		
		
//      X509 Certificates Chain Elelemt
		
		Element eCertChain = myDocument.createElement("X509CertChain");
		eCertChain.setAttribute("Length", String.valueOf(m_X509_CertChain.length));
		newRoot.appendChild(eCertChain);
		// Create lelements for each certificate
		
		for (int i = 0; i< m_X509_CertChain.length; i++)
		{
			X509Certificate myCert =  m_X509_CertChain[i];
			// Create an Dom Element containg the certificate
			//Document myXMLDocument=db.newDocument();
			
			XMLX509Certificate myXMLCert = new XMLX509Certificate(myDocument, myCert);
			Element myCertElement = myXMLCert.getElement();
			myCertElement.setAttribute("ChainOrder",String.valueOf(i));
			
			
			
			
			//Node myImportedNode =  myDocument.importNode(myCertElement,true);
			
			
			//((Element) myImportedNode).setAttribute("ChainOrder", String.valueOf(i));			
			
			
			eCertChain.appendChild(myCertElement);
			
	
			
			
		}
		myDocument.appendChild(newRoot);
		
		Constants.setSignatureSpecNSprefix(sigConst);
		if (log.isDebugEnabled()) log.debug("F_getUserProfileasDomDocument::End processing...");
		
		return myDocument;
		
	}
	catch (Exception e){
		throw new E_UserProfileException("C_UserProfile:getUserProfileasDomDocument:Error \n" + e.toString());
		
	}
	
	}
	public String F_getUserProfileasString() throws
	E_UserProfileException{
		if (log.isDebugEnabled()) log.debug("F_getUserProfileasString::Start processing...");
		try{
			Document myDocument = this.F_getUserProfileasDomDocument();
			TransformerFactory myFactory = TransformerFactory.newInstance();
			
			Transformer myTransformer = myFactory.newTransformer();
			
			DOMSource mySource =new DOMSource(myDocument);
			StringWriter mySW = new StringWriter();
			StreamResult myResult = new StreamResult(mySW);
			myTransformer.transform(mySource, myResult);
			
			if (log.isDebugEnabled()) log.debug("F_getUserProfileasString::End processing...");
			
			return mySW.toString();
			//return myResult.toString();
		} catch (Exception e)
		{
			throw new E_UserProfileException("C_UserProfile::F_GetUserProfileasString:: Error \n "+ e.toString());
		}
		
	}
}
