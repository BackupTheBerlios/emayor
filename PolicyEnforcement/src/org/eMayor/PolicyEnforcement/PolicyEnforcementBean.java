/*
 * Created on Feb 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.eMayor.PolicyEnforcement;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import javax.ejb.CreateException;

import org.w3c.dom.Document;

import java.security.cert.X509Certificate;

/**
 * @ejb.bean name="PolicyEnforcement"
 *           display-name="eMayor Policy Enforcemet Bean"
 *           description="Description for PolicyEnforcement"
 *           jndi-name="ejb/PolicyEnforcement"
 *           type="Stateless"
 *           view-type="remote"
 */
public class PolicyEnforcementBean implements SessionBean {

	/**
	 * 
	 */
	public PolicyEnforcementBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx)
		throws EJBException,
		RemoteException {
		// TODO Auto-generated method stub
		

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		// TODO Auto-generated method stub
	}

	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public C_UserProfile F_getUserProfile(X509Certificate x509_CertChain[]) {
		// TODO Auto-generated method stub
		if (x509_CertChain == null) {
			return null;
		} else {
			C_UserProfile newUserProfile = new C_UserProfile();
			newUserProfile.setX509_CertChain(x509_CertChain);
			java.security.Principal newPrincipal = x509_CertChain[0].getSubjectDN();		
			
			C_ParseX509DN myX509DNParser = new C_ParseX509DN(newPrincipal.getName());
			newUserProfile.setUserName(myX509DNParser.m_S_CN);
			newUserProfile.setUserEmail(myX509DNParser.m_S_Email);
			newUserProfile.setOrganisationUnit(myX509DNParser.m_S_OU);
			newUserProfile.setUserOrganisation(myX509DNParser.m_S_O);
			newUserProfile.setUserST(myX509DNParser.m_S_ST);
			newUserProfile.setUserCountry( myX509DNParser.m_S_C);
			byte[] b = x509_CertChain[0].getExtensionValue("1.2.3.4");
			if (b != null && b.length > 4) {
				if (b[2] == 19) {
					String myRole = (new String(b)).toString();
					newUserProfile.setUserRole(myRole.substring(4));

				}

			} else {
				newUserProfile.setUserRole("Guest");

			}
			try {
				Document MyTestDocument = newUserProfile.getUserProfileasDomDocument();
			}
			catch (C_UserProfile.E_UserProfileException e)
			{
				e.printStackTrace();
			}
			return newUserProfile;
		}

	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public boolean F_AuthenticateUser(C_UserProfile UserProfile) {
		// TODO Auto-generated method stub

		return true;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public boolean F_VerifyXMLSignature(String xmlDocument) {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public String F_TimeStampXMLDocument(String xmlDocumentDoc) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public boolean F_VerifyXMLTimeStampedDocument(String xmlDocument) {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public boolean F_AuthorizeService(
		C_UserProfile UserProfile,
		C_ServiceProfile ServiceProfile) {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public boolean F_AuthorizeServiceStep(
		C_UserProfile UserProfile,
		C_ServiceProfile ServiceProfile,
		C_ServiceStep ServiceStep) {
		// TODO Auto-generated method stub
		return true;
	}
}