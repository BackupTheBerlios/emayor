/*
 * Created on Feb 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;
import java.security.cert.X509Certificate;

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

public C_UserProfile(Document InputDocument){
	
	try {
		NodeList MyNodeList = InputDocument.getElementsByTagName("UserName");
		if  (MyNodeList.getLength() == 1)
		{ 
			Node MyNode = MyNodeList.item(0);
			String sUserName =MyNode.getNodeValue();
			
		}
		else {
			// Generate an parce exception
		}
	
	}
	catch (Exception e){
		e.printStackTrace();
	}
	}
	
}
//	public static void main(String[] args) {
//	}

