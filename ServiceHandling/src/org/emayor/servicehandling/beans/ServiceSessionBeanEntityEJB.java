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
 * @ejb.bean name="ServiceSessionBeanEntity" display-name="Name for
 *           ServiceSessionBeanEntity" description="Description for
 *           ServiceSessionBeanEntity"
 *           jndi-name="ejb/emayor/sh/entity/ServiceSessionBeanEntity"
 *           type="CMP" cmp-version="2.x" view-type="local" primkey-field =
 *           "ssid"
 * @jboss.persistence table-name = "SERVICE_SESSION" create-table = "true"
 *                    datasource = "java:/MySqlDS" datasource-mapping = "mySQL"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT OBJECT(o) FROM
 *             ServiceSessionBeanEntity AS o WHERE o.asid = ?1"
 *             result-type-mapping = "Local" signature = "java.util.Collection
 *             findByASID(java.lang.String asid)"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT OBJECT(o) FROM
 *             ServiceSessionBeanEntity AS o WHERE o.serviceId = ?1"
 *             result-type-mapping = "Local" signature = "java.util.Collection
 *             findByServiceID(java.lang.String serviceId)"
 *  
 */
public abstract class ServiceSessionBeanEntityEJB implements EntityBean {
	private static final Logger log = Logger
			.getLogger(ServiceSessionBeanEntityEJB.class);

	/**
	 *  
	 */
	public ServiceSessionBeanEntityEJB() {
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
	 * Getter for CMP Field ssid
	 * 
	 * @ejb.pk-field
	 * @ejb.persistent-field
	 * @ejb.interface-method view-type="local"
	 * @jboss.column-name name = "SSID"
	 * @jboss.sql-type type = "VARCHAR(100)"
	 */
	public abstract String getSsid();

	/**
	 * Setter for CMP Field ssid
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setSsid(String value);

	/**
	 * Create method
	 * 
	 * @ejb.create-method view-type = "local"
	 */
	public String ejbCreate(String ssid) throws javax.ejb.CreateException {
		log.debug("-> start processing ...");
		this.setSsid(ssid);
		return null;
	}

	/**
	 * Post Create method
	 */
	public void ejbPostCreate(String ssid) throws javax.ejb.CreateException {
		// TODO Auto-generated method stub
	}

	/**
	 * Getter for CMP Field asid
	 * 
	 * 
	 * @ejb.persistent-field
	 * @ejb.interface-method view-type="local"
	 * @jboss.column-name name = "ASID"
	 * @jboss.sql-type type = "VARVHAR(100)"
	 */
	public abstract String getAsid();

	/**
	 * Setter for CMP Field asid
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setAsid(String value);

	/**
	 * Getter for CMP Field serviceId
	 * 
	 * 
	 * @ejb.persistent-field
	 * @ejb.interface-method view-type="local"
	 * @jboss.column-name name = "SERVICEID"
	 * @jboss.sql-type type = "VARVHAR(200)"
	 */
	public abstract String getServiceId();

	/**
	 * Setter for CMP Field serviceId
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setServiceId(String value);

	/**
	 * Getter for CMP Field startDate
	 * 
	 * 
	 * @ejb.persistent-field
	 * @ejb.interface-method view-type="local"
	 * @jboss.column-name name = "STARTDATE"
	 * @jboss.sql-type type = "DATETIME"
	 */
	public abstract java.util.Date getStartDate();

	/**
	 * Setter for CMP Field startDate
	 * 
	 * @ejb.interface-method view-type="local"
	 */
	public abstract void setStartDate(java.util.Date value);

}