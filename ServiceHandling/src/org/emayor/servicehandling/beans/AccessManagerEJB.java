/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.kernel.AccessSessionException;
import org.emayor.servicehandling.kernel.IAccess;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.RunningServicesInfo;
import org.emayor.servicehandling.kernel.ServicesInfo;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="AccessManager" display-name="Name for AccessManager"
 *           description="Description for AccessManager"
 *           jndi-name="ejb/AccessManager" type="Stateless" view-type="local"
 *  
 */
public class AccessManagerEJB implements SessionBean, IAccess {
	private static Logger log = Logger.getLogger(AccessManagerEJB.class);

	private SessionContext ctx;

	private KernelLocal kernel;

	/**
	 *  
	 */
	public AccessManagerEJB() {
		super();
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			this.kernel = locator.getKernelLocal();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
		}
	}

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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public String createAccessSession() throws AccessException {
		log.debug("-> start processing ...");
		String ret = "12ee34";
		try {
			ret = kernel.createAccessSession();
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AccessException(
					"Kernel couldn't create a new access session!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public String startService(String accessSessionId, String serviceName)
			throws AccessException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
		return null;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public boolean stopService(String accessSessionId, String serviceSessionId)
			throws AccessException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
		return false;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public ServicesInfo listAvailableServices(String accessSessionId)
			throws AccessException {
		log.debug("-> start processing ...");
		ServicesInfo ret = new ServicesInfo();
		if (accessSessionId.equals("-1001")) {
			log.debug("anonymous login - list of services");
			try {
				ret.setServicesInfo(this.kernel.listAllAvailableServices());
			} catch (KernelException ex) {
				log.error("caught ex: " + ex);
				throw new AccessException(
						"Unable to get the default list of available services!");
			}
		} else {
			try {
				AccessSessionLocal as = this.kernel
						.getAccessSession(accessSessionId);
				ret.setServicesInfo(as.listAvailableServices());
			} catch (KernelException ex) {
				log.error("caught ex: " + ex);
				throw new AccessException(
						"Unable to get the list of available services!");
			} catch (AccessSessionException asex) {
				log.error("caught ex: " + asex);
				throw new AccessException(
						"Unable to get the list of available services!");
			}
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public RunningServicesInfo listRunningServices(String accessSessionId)
			throws AccessException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		RunningServicesInfo ret = new RunningServicesInfo();

		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public boolean stopAccessSession(String accessSessionId)
			throws AccessException {
		log.debug("-> start processing ...");
		boolean ret = false;
		try {
			AccessSessionLocal as = this.kernel
					.getAccessSession(accessSessionId);
			ret = as.stop();
			as.remove();
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AccessException(
					"Couldn't obtain the right Access Session instance!");
		} catch (AccessSessionException asex) {
			log.error("caught ex: " + asex.toString());
			throw new AccessException("Couldn't stop the access session");
		} catch (RemoveException rex) {
			log.error("caught ex: " + rex.toString());
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}