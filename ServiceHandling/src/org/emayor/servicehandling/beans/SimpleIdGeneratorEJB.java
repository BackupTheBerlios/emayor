/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.utils.IIdGenerator;
import org.emayor.servicehandling.utils.IdGeneratorRoot;

/**
 * @ejb.bean name="SimpleIdGenerator" display-name="Name for SimpleIdGenerator"
 *           description="Description for SimpleIdGenerator"
 *           jndi-name="ejb/SimpleIdGenerator" type="Stateless"
 *           view-type="local"
 */
public class SimpleIdGeneratorEJB implements IIdGenerator {
	private static final Logger log = Logger
			.getLogger(SimpleIdGeneratorEJB.class);

	private IdGeneratorRoot idGeneratorRoot;

	private SessionContext ctx;

	/**
	 *  
	 */
	public SimpleIdGeneratorEJB() {
		super();
		this.idGeneratorRoot = IdGeneratorRoot.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx) throws EJBException,
			RemoteException {
		this.ctx = ctx;
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
	public String generateId() {
		return this.idGeneratorRoot.getNextId();
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
	}

}