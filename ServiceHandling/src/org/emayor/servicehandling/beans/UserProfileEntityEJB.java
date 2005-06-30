/*
 * $ Created on Jul 1, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import org.apache.log4j.Logger;

/**
 * @ejb.bean name="UserProfileEntity" display-name="Name for UserProfileEntity"
 *           description="Description for UserProfileEntity"
 *           jndi-name="ejb/UserProfileEntity" type="CMP" cmp-version="2.x"
 *           view-type="local" primkey-field = "userId"
 * @jboss.persistence table-name = "USER_PROFILE" create-table = "true"
 *                    datasource = "java:/MySqlDS" datasource-mapping = "mySQL"
 *                    remove-table = "true"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT OBJECT(o) FROM
 *             UserProfileEntity AS o" result-type-mapping = "Local" signature =
 *             "java.util.Collection findAll()"
 */
public abstract class UserProfileEntityEJB implements EntityBean {
	private static final Logger log = Logger
			.getLogger(UserProfileEntityEJB.class);

	/**
	 *  
	 */
	public UserProfileEntityEJB() {
		super();
		log.debug("-> starting processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
	 */
	public void setEntityContext(EntityContext ctx) throws EJBException,
			RemoteException {
		log.debug("-> starting processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#unsetEntityContext()
	 */
	public void unsetEntityContext() throws EJBException, RemoteException {
		log.debug("-> starting processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbRemove()
	 */
	public void ejbRemove() throws RemoveException, EJBException,
			RemoteException {
		log.debug("-> starting processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		log.debug("-> starting processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		log.debug("-> starting processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbLoad()
	 */
	public void ejbLoad() throws EJBException, RemoteException {
		log.debug("-> starting processing ...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.EntityBean#ejbStore()
	 */
	public void ejbStore() throws EJBException, RemoteException {
		log.debug("-> starting processing ...");
	}

	/**
	 * Getter for CMP Field userId
	 * 
	 * @ejb.pk-field
	 * @ejb.persistent-field
	 * @ejb.interface-method view-type="local"
	 * @jboss.column-name name = "USERID"
	 * @jboss.sql-type type = "VARCHAR(100)"
	 */
	public abstract java.lang.String getUserId();

	/**
	 * Setter for CMP Field userId
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setUserId(java.lang.String value);

	/**
	 * Create method
	 * 
	 * @ejb.create-method view-type = "local"
	 */
	public java.lang.String ejbCreate(java.lang.String userId)
			throws javax.ejb.CreateException {
		log.debug("-> starting processing ...");
		this.setUserId(userId);
		return null;
	}

	/**
	 * Post Create method
	 */
	public void ejbPostCreate(java.lang.String userId)
			throws javax.ejb.CreateException {
		log.debug("-> starting processing ...");
	}

	/**
	 * Getter for CMP Field c_UserProfile
	 * 
	 * 
	 * @ejb.persistent-field
	 * @ejb.interface-method view-type="local"
	 * @jboss.column-name name = "CUSERPROFILE"
	 * @jboss.sql-type type = "TEXT"
	 */
	public abstract java.lang.String getC_UserProfile();

	/**
	 * Setter for CMP Field c_UserProfile
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setC_UserProfile(java.lang.String value);

}