/*
 * Created on Feb 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;
import java.security.cert.X509Certificate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

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
		if (myRoot.getNodeName()!="User Profile")
		{
			throw new E_UserProfileException("C_UserProfile:: Invaild Input Dom Document");
		}
		
//		 Get The UserName
		NodeList MyNodeList = InputDocument.getElementsByTagName("UserName");
		if  (MyNodeList.getLength() == 1)
		{ 
			Node MyNode = MyNodeList.item(0);
			String sUserName =MyNode.getNodeValue();
			this.setUserName(sUserName);
			
		}
		else {
			// Generate an parce exception
			throw new E_UserProfileException("C_UserProfile :: Unable to Get User Name");
		}
		
		
	
	}
	catch (Exception e){
		throw new E_UserProfileException("C_UserProfile::Parce Error \n" + e.toString());
	}
	}
	

public Document getUserProfileasDomDocument()throws
E_UserProfileException {
	Document myDocument = null;
	try {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
	    
		myDocument = db.newDocument();
		//Create The root
		Element newRoot = myDocument.createElement("User Profile");
		myDocument.appendChild(newRoot);
		
		// Create the User Name Element
		
		Element eUserName = myDocument.createElement("UserName");
		eUserName.setNodeValue(this.m_S_UserName);
		newRoot.appendChild(eUserName);
		
		// Create the User Email Element
		
		Element eUserEmail = myDocument.createElement("UserEmail");
		eUserEmail.setNodeValue(this.m_S_UserEmail);
		newRoot.appendChild(eUserEmail);
		
//		 Create the User Role Element
		
		Element eUserRole = myDocument.createElement("UserRole");
		eUserRole.setNodeValue(this.m_S_UserRole);
		newRoot.appendChild(eUserRole);
		
		

		
		
		// Get The UserName
		/*NodeList MyNodeList = InputDocument.getElementsByTagName("UserName");
		if  (MyNodeList.getLength() == 1)
		{ 
			Node MyNode = MyNodeList.item(0);
			String sUserName =MyNode.getNodeValue();
			this.setUserName(sUserName);
			
		}
		else {
			// Generate an parce exception
			throw new E_UserProfile("C_UserProfile :: Unable to Get User Name");
		}
		
		
	*/
	}
	catch (Exception e){
		throw new E_UserProfile("C_UserProfile:getUserProfileasDomDocument:Error \n" + e.toString());
	}
	return myDocument;
	}
	
}
