/*
 * Created on Feb 23, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.IeMayorService;
import org.emayor.servicehandling.kernel.IeMayorServiceFactory;
import org.emayor.servicehandling.kernel.eMayorServiceException;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="eMayorServiceFactory"
 *           display-name="Name for eMayorServiceFactory"
 *           description="Description for eMayorServiceFactory"
 *           jndi-name="ejb/eMayorServiceFactory"
 *           type="Stateful"
 *           view-type="local"
 */
public class eMayorServiceFactoryEJB implements SessionBean, IeMayorServiceFactory {
	private static Logger log = Logger.getLogger(eMayorServiceFactoryEJB.class);
	/**
	 * 
	 */
	public eMayorServiceFactoryEJB() {
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
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");

		log.debug("-> ... processing DONE!");
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate(String param) throws CreateException {
		// TODO Auto-generated method stub
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "local"
	 */
	public void setup() throws eMayorServiceException {
		log.debug("-> start processing ...");
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "local"
	 */
	public void cleanup() throws eMayorServiceException {
		log.debug("-> start processing ...");
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "local"
	 */
	public IeMayorService createService(String serviceName, String ssid) throws eMayorServiceException {
		// TODO Auto-generated method stub
		log.debug("-> start processing ...");
		
		log.debug("-> ... processing DONE!");
		return null;
	}

}