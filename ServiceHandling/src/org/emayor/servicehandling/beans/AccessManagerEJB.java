/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.kernel.IAccess;
import org.emayor.servicehandling.kernel.RunningServicesInfo;
import org.emayor.servicehandling.kernel.ServicesInfo;

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

	/**
	 *  
	 */
	public AccessManagerEJB() {
		super();
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
	public RunningServicesInfo listRunningServices(String accessSessionId)
			throws AccessException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		
		log.debug("-> ... processing DONE!");
		return null;
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

}