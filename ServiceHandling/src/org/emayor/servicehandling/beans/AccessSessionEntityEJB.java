/*
 * $ Created on Jun 30, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import org.apache.log4j.Logger;

/**
 * @ejb.bean name="AccessSessionEntity" display-name="Name for
 *           AccessSessionEntity" description="Description for
 *           AccessSessionEntity"
 *           jndi-name="ejb/emayor/sh/entity/AccessSessionEntity" type="CMP"
 *           cmp-version="2.x" view-type="local" primkey-field = "asid"
 * @jboss.persistence table-name = "ACCESS_SESSION" create-table = "true"
 *                    datasource = "java:/MySqlDS" datasource-mapping = "mySQL"
 *                    remove-table = "true"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT DISTINCT OBJECT(o) FROM
 *             AccessSessionEntity AS o WHERE o.userId = ?1" result-type-mapping =
 *             "Local" signature = "java.util.Collection
 *             findByUserId(java.lang.String userId)"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT DISTINCT OBJECT(o) FROM
 *             AccessSessionEntity AS o" result-type-mapping = "Local" signature =
 *             "java.util.Collection findAll()"
 *  
 */
public abstract class AccessSessionEntityEJB implements EntityBean {
	private final static Logger log = Logger
			.getLogger(AccessSessionEntityEJB.class);

	/**
	 *  
	 */
	public AccessSessionEntityEJB() {
		super();
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
	 */
	public void setEntityContext(EntityContext ctx) throws EJBException,
			RemoteException {
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#unsetEntityContext()
	 */
	public void unsetEntityContext() throws EJBException, RemoteException {
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbRemove()
	 */
	public void ejbRemove() throws RemoveException, EJBException,
			RemoteException {
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbLoad()
	 */
	public void ejbLoad() throws EJBException, RemoteException {
		log.debug("-> start processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbStore()
	 */
	public void ejbStore() throws EJBException, RemoteException {
		log.debug("-> start processing ...");
	}

	/**
	 * Getter for CMP Field asid
	 * 
	 * @ejb.pk-field
	 * @ejb.persistent-field
	 * @ejb.interface-method view-type="local"
	 * @jboss.column-name name = "ASID"
	 * @jboss.sql-type type = "VARCHAR(100)"
	 * @jboss.jdbc-type type = "VARCHAR"
	 * @jboss.persistence not-null = "true"
	 */
	public abstract java.lang.String getAsid();

	/**
	 * Setter for CMP Field asid
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setAsid(java.lang.String value);

	/**
	 * Create method
	 * 
	 * @ejb.create-method view-type = "local"
	 */
	public String ejbCreate(String asid) throws javax.ejb.CreateException {
		log.debug("-> start processing ...");
		this.setAsid(asid);
		return null;
	}

	/**
	 * Post Create method
	 */
	public void ejbPostCreate(String asid) throws javax.ejb.CreateException {
		log.debug("-> start processing ...");
	}

	/**
	 * Getter for CMP Field userId
	 * 
	 * 
	 * @ejb.persistent-field
	 * @ejb.interface-method view-type="local"
	 * @jboss.column-name name = "USERID"
	 * @jboss.sql-type type = "VARCHAR(50)"
	 * @jboss.jdbc-type type = "VARCHAR"
	 */
	public abstract String getUserId();

	/**
	 * Setter for CMP Field userId
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setUserId(String value);

	/**
	 * Getter for CMP Field startDate
	 * 
	 * 
	 * @ejb.persistent-field
	 * @ejb.interface-method view-type="local"
	 * @jboss.column-name name = "STARTDATE"
	 * @jboss.sql-type type = "DATETIME"
	 * @jboss.jdbc-type type = "TIMESTAMP"
	 */
	public abstract java.util.Date getStartDate();

	/**
	 * Setter for CMP Field startDate
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setStartDate(java.util.Date value);

}