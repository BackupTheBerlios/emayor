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

import org.apache.log4j.Logger;

import java.security.cert.X509Certificate;

/**
 * @ejb.bean name="PolicyEnforcement"
 *           display-name="eMayor Policy Enforcemet Bean"
 *           description="Description for PolicyEnforcement"
 *           jndi-name="ejb/PolicyEnforcement"
 *           type="Stateless"
 *           view-type="both"
 */
public class PolicyEnforcementBean implements SessionBean {
	private static final Logger log = Logger
		.getLogger(PolicyEnforcementBean.class);

	// Create the PEP and the PDP
	private static C_PEP MyPEP = null;

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
	 * @ejb.interface-method  view-type = "both"
	 */
	public String F_getUserProfile(X509Certificate x509_CertChain[])
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		try {
			log.debug("PolicyEnfocement->Create User Profile");
			return (new C_UserProfile(x509_CertChain))
				.F_getUserProfileasString();
		} catch (C_UserProfile.E_UserProfileException e) {
			throw new E_PolicyEnforcementException(
				"PolicyEnforcement::F_getUserProfile:: Exception \n"
					+ e.toString());
		}
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_AuthenticateUser(String UserProfile)
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub

		return true;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_VerifyXMLSignature(String xmlDocument)
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public String F_TimeStampXMLDocument(String xmlDocumentDoc)
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_VerifyXMLTimeStampedDocument(String xmlDocument)
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_AuthorizeService(String UserProfile, String ServiceProfile)
		throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		try {
			if (MyPEP == null){
				MyPEP = new C_PEP(new C_PDP());
				log.debug("Policy Enforcement->F_AuthorizeService:: Create the PEP");
			}
				
				
			C_UserProfile MyUserProfile = new C_UserProfile(UserProfile);
			log.debug("Policy Enforcement->F_AuthorizeService:: Got The User Profile with role" + MyUserProfile.getUserRole());
			return MyPEP.F_CanStartService(
				MyUserProfile.getUserRole(),
				ServiceProfile);
		} catch (Exception e) {
			throw new E_PolicyEnforcementException(
				"PolicyEnforcement->F_AuthorizeService: Exeption ::"
					+ e.toString());
		}

	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public boolean F_AuthorizeServiceStep(
		String UserProfile,
		String ServiceProfile,
		String ServiceStep) throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "both"
	 */
	public void F_UpdatePolicies() throws E_PolicyEnforcementException {
		// TODO Auto-generated method stub
		try {
			MyPEP = new C_PEP(new C_PDP());
		} catch (Exception e)
		{
			throw new E_PolicyEnforcementException(
					"PolicyEnforcement->F_UpdatePolicies: Exeption ::"
						+ e.toString());
		}
			
			
		
	}
}