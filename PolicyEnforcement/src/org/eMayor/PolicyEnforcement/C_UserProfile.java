/*
 * Created on Feb 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;


import java.io.StringReader;
import java.security.cert.X509Certificate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.security.keys.content.x509.XMLX509Certificate;
import org.w3c.dom.*;
import org.xml.sax.InputSource;



/**
 * @author root
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class C_UserProfile {

private String m_S_UserName;
private String m_S_UserEmail;
private String m_S_UserRole;
private String m_S_OrganisationUnit;
private String m_S_UserOrganisation;
private String m_S_UserST;
private String m_S_UserCountry;
private X509Certificate m_X509_CertChain[];

public String getUserName(){
	return this.m_S_UserName;
}
public void setUserName(String sUserName){
	this.m_S_UserName = sUserName;
}

public String getUserEmail(){
	return this.m_S_UserEmail;
}
public void setUserEmail(String sUserEmail){
	this.m_S_UserEmail = sUserEmail;
}

public String getUserRole(){
	return this.m_S_UserRole;
}
public void setUserRole(String sUserRole){
	this.m_S_UserRole = sUserRole;
}

public String getOrganisationUnit(){
	return this.m_S_OrganisationUnit;
}
public void setOrganisationUnit(String sOrganisationUnit){
	this.m_S_OrganisationUnit = sOrganisationUnit;
}

public String getUserOrganisation(){
	return this.m_S_UserOrganisation;
}
public void setUserOrganisation(String sUserOrganisation){
	this.m_S_UserOrganisation = sUserOrganisation;
}


public String getUserST(){
	return this.m_S_UserST;
}
public void setUserST(String sUserST){
	this.m_S_UserST = sUserST;
}

public String getUserCountry(){
	return this.m_S_UserCountry;
}
public void setUserCountry(String sUserCountry){
	this.m_S_UserCountry = sUserCountry;
}

public X509Certificate[] getX509_CertChain(){
	return this.m_X509_CertChain;
}
public void setX509_CertChain(X509Certificate[] newChain){
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

public C_UserProfile() {
	super();
}



public C_UserProfile(Document InputDocument) throws
E_UserProfileException {
	
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
			
			
			
			
			
			
	
	
	}
	catch (Exception e){
		throw new E_UserProfileException("C_UserProfile::Parce Error \n" + e.toString());
	}
	}
	
public C_UserProfile(String myXMLDocument) throws E_UserProfileException{
	
	try {
		DocumentBuilderFactory myFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder myDocBuilder = myFactory.newDocumentBuilder();
		StringReader myStrReader = new StringReader(myXMLDocument);
		InputSource myInputSource = new InputSource(myStrReader);
	
		Document myDocument = myDocBuilder.parse(myInputSource);
		//C_UserProfile(myDocument);
	}
	catch (Exception e)
	{
		throw new E_UserProfileException("C_UserProfile(String)::Error \n  " + e.toString());
	}
	
	
}
public Document getUserProfileasDomDocument() throws
E_UserProfileException {
	Document myDocument = null;
	try {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
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
			Document myXMLDocument=db.newDocument();
			
			XMLX509Certificate myXMLCert = new XMLX509Certificate(myXMLDocument, myCert);
			Element myCertElement = myXMLCert.getElement();
			
			
			
			
			Node myImportedNode =  myDocument.importNode(myCertElement,true);
			
			
			((Element) myImportedNode).setAttribute("ChainOrder", String.valueOf(i));			
			
			
			eCertChain.appendChild(myImportedNode);
			
	
			
			
		}
		myDocument.appendChild(newRoot);
		
		return myDocument;
		
	}
	catch (Exception e){
		throw new E_UserProfileException("C_UserProfile:getUserProfileasDomDocument:Error \n" + e.toString());
		
	}
	
	}
	
}
