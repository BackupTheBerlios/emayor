/*
 * Created on Mar 10, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.C_ServiceProfile;
import org.eMayor.PolicyEnforcement.C_ServiceStep;
import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcement;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.kernel.BPELPolicyEnforcerException;
import org.emayor.servicehandling.kernel.IBPELPolicyEnforcer;
import org.emayor.servicehandling.kernel.IServiceProfile;
import org.emayor.servicehandling.kernel.IUserProfile;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @ejb.bean name="BPELPolicyEnforcer" display-name="Name for
 *           BPELPolicyEnforcer" description="Description for
 *           BPELPolicyEnforcer" jndi-name="ejb/BPELPolicyEnforcer"
 *           type="Stateless" view-type="both"
 *  
 */
public class BPELPolicyEnforcerEJB implements SessionBean, IBPELPolicyEnforcer {
	private static final Logger log = Logger
			.getLogger(BPELPolicyEnforcerEJB.class);

	private SessionContext ctx = null;

	private PolicyEnforcement policyEnforcement;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx) throws EJBException,
			RemoteException {
		log.debug("-> start processing ...");
		this.ctx = ctx;
		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		log.debug("-> start processing ...");
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		log.debug("-> start processing ...");
		try {
			log.debug("get the service locator instance");
			ServiceLocator locator = ServiceLocator.getInstance();
			log.debug("get the policy enforcer remote reference");
			this.policyEnforcement = locator.getPolicyEnforcement();
			if (this.policyEnforcement != null)
				log.debug("the policy enforcer remote reference not NULL");
		} catch (ServiceLocatorException slex) {
			log.error("caught ex: " + slex.toString());
			throw new CreateException(
					"Couldn't create the instance - problem with policy enforcer bean");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	public boolean F_VerifyXMLSignature(String xmlDocument)
			throws BPELPolicyEnforcerException {
		log.debug("-> start processing ...");
		boolean ret = false;
		try {
			ret = this.policyEnforcement.F_VerifyXMLSignature(xmlDocument);
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new BPELPolicyEnforcerException(rex.toString());
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	public String F_TimeStampXMLDocument(String xmlDocumentDoc)
			throws BPELPolicyEnforcerException {
		log.debug("-> start processing ...");
		String ret = null;
		try {
			ret = this.policyEnforcement.F_TimeStampXMLDocument(xmlDocumentDoc);
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new BPELPolicyEnforcerException(rex.toString());
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	public boolean F_VerifyXMLTimeStampedDocument(String xmlDocument)
			throws BPELPolicyEnforcerException {
		log.debug("-> start processing ...");
		boolean ret = false;
		try {
			ret = this.policyEnforcement
					.F_VerifyXMLTimeStampedDocument(xmlDocument);
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new BPELPolicyEnforcerException(rex.toString());
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "both"
	 */
	public boolean F_AuthorizeServiceStep(String userId, String ssid,
			String serviceStepId) throws BPELPolicyEnforcerException {
		log.debug("-> start processing ...");
		boolean ret = false;
		try {
			if (log.isDebugEnabled()) {
				StringBuffer b = new StringBuffer();
				b.append("userid       : ").append(userId);
				b.append("\nssid         : ").append(ssid);
				b.append("\nserviceStepId: ").append(serviceStepId);
				log.debug("working with following data:\n" + b.toString());
			}
			ServiceLocator locator = ServiceLocator.getInstance();
			log.debug("get the kernel reference");
			KernelLocal kernel = locator.getKernelLocal();
			IUserProfile _userProfile = kernel.getUserProfile(userId);
			C_UserProfile userProfile = _userProfile.getPEUserProfile();
			IServiceProfile _serviceProfile = kernel.getServiceProfile(ssid);
			C_ServiceProfile serviceProfile = _serviceProfile
					.getPEServiceProfile();
			C_ServiceStep _serviceStep = new C_ServiceStep();
			ret = this.policyEnforcement.F_AuthorizeServiceStep(userProfile,
					serviceProfile, _serviceStep);
			if (log.isDebugEnabled())
				log.debug("got from policy enforcer: " + ret);
		} catch (ServiceLocatorException slex) {
			log.error("caught ex: " + slex.toString());
			throw new BPELPolicyEnforcerException(
					"Couldn't create the instance - problem to get kernel");
		} catch (KernelException kex) {
			log.error("caught ex: " + kex.toString());
			throw new BPELPolicyEnforcerException(kex.toString());
		} catch (RemoteException rex) {
			log.error("caught ex: " + rex.toString());
			throw new BPELPolicyEnforcerException(rex.toString());
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}