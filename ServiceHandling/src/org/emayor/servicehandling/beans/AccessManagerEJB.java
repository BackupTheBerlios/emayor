/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.kernel.IAccess;
import org.emayor.servicehandling.kernel.RunningServicesInfo;
import org.emayor.servicehandling.kernel.ServicesInfo;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="AccessManager"
 *           display-name="Name for AccessManager"
 *           description="Description for AccessManager"
 *           jndi-name="ejb/AccessManager"
 *           type="Stateless"
 *           view-type="local"
 */
public class AccessManagerEJB implements SessionBean, IAccess {

	/**
	 * 
	 */
	public AccessManagerEJB() {
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
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public String createAccessSession() throws AccessException {
		// TODO Auto-generated method stub
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
	}

}