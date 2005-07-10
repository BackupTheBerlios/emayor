/*
 * $ Created on Jul 11, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import org.apache.log4j.Logger;

/**
 * @ejb.bean name="PlatformConfigurationEntity" display-name="Name for
 *           PlatformConfigurationEntity" description="Description for
 *           PlatformConfigurationEntity"
 *           jndi-name="ejb/emayor/sh/entity/PlatformConfigurationEntity"
 *           type="CMP" cmp-version="2.x" view-type="local" primkey-field =
 *           "configId"
 * @jboss.persistence table-name = "PLATFORMCONFIG" create-table = "true"
 *                    datasource = "java:/MySqlDS" datasource-mapping = "mySQL"
 *                    remove-table = "false"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT OBJECT(o) FROM
 *             PlatformConfigurationEntity AS o" result-type-mapping = "Local"
 *             signature = "java.util.Collection findAll()"
 */
public abstract class PlatformConfigurationEntityEJB implements EntityBean {
	private final static Logger log = Logger
			.getLogger(PlatformConfigurationEntityEJB.class);

	/**
	 *  
	 */
	public PlatformConfigurationEntityEJB() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
	 */
	public void setEntityContext(EntityContext ctx) throws EJBException,
			RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#unsetEntityContext()
	 */
	public void unsetEntityContext() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbRemove()
	 */
	public void ejbRemove() throws RemoveException, EJBException,
			RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbLoad()
	 */
	public void ejbLoad() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbStore()
	 */
	public void ejbStore() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Getter for CMP Field configId
	 * 
	 * @ejb.pk-field
	 * @ejb.persistent-field
	 * @ejb.interface-method view-type="local"
	 * @jboss.column-name name = "CONFIGID"
	 * @jboss.sql-type type = "VARCHAR(100)"
	 * @jboss.jdbc-type type = "VARCHAR"
	 * @jboss.persistence not-null = "true"
	 */
	public abstract java.lang.String getConfigId();

	/**
	 * Setter for CMP Field configId
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setConfigId(java.lang.String value);

	/**
	 * Create method
	 * 
	 * @ejb.create-method view-type = "local"
	 */
	public java.lang.String ejbCreate(java.lang.String configId)
			throws javax.ejb.CreateException {
		this.setConfigId(configId);
		return null;
	}

	/**
	 * Post Create method
	 */
	public void ejbPostCreate(java.lang.String configId)
			throws javax.ejb.CreateException {
	}
}