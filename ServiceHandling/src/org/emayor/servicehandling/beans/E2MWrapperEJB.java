/*
 * $ Created on 19.08.2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.eMayor.AdaptationLayer.interfaces.E2MLocal;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @ejb.bean name="E2MWrapper" display-name="Name for E2MWrapper"
 *           description="Description for E2MWrapper"
 *           jndi-name="ejb/emayor/sh/E2MWrapper" type="Stateless"
 *           view-type="remote"
 */
public class E2MWrapperEJB implements SessionBean {
	private static final Logger log = Logger.getLogger(E2MWrapperEJB.class);

	private SessionContext ctx = null;

	private E2MLocal e2m = null;

	/**
	 *  
	 */
	public E2MWrapperEJB() {
		super();
		log.debug("-> start processing ...");
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
			log.debug("get the locator ...");
			ServiceLocator locator = ServiceLocator.getInstance();
			log.debug("get the E2M local reference ...");
			this.e2m = locator.getE2MLocal();
			log.debug("got the reference successfuly");
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new CreateException(
					"Couldn't obtain reference to the M2E bean");
		}
	}

	/**
	 * Business method
	 * 
	 * @ejb.interface-method view-type = "remote"
	 */
	public String serviceRequestPropagator(String request, String type) {
		log.debug("-> start processing ...");
		String ret = "";
		try {
			log.debug("get the E2M reference");
			this.e2m.ServiceRequestPropagator(request, Integer.valueOf(type)
					.intValue());
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
			ret = "<ERROR/>";
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

}