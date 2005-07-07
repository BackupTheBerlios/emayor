/*
 * Created on Jul 8, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import org.apache.log4j.Logger;

/**
 * @ejb.bean name="BPELCallbackDataEntity" display-name="Name for
 *           BPELCallbackDataEntity" description="Description for
 *           BPELCallbackDataEntity"
 *           jndi-name="ejb/emayor/sh/entity/BPELCallbackDataEntity" type="CMP"
 *           cmp-version="2.x" view-type="local" primkey-field = "ssid"
 * @jboss.persistence table-name = "BPELCALLBACKDATA" create-table = "true"
 *                    datasource = "java:/MySqlDS" datasource-mapping = "mySQL"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT OBJECT(o) FROM
 *             BPELCallbackDataEntity AS o" result-type-mapping = "Local"
 *             signature = "java.util.Collection findAll()"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT OBJECT(o) FROM
 *             BPELCallbackDataEntity AS o WHERE o.userId = ?1"
 *             result-type-mapping = "Local" signature = "java.util.Collection
 *             findByUserId(java.lang.String userId)"
 */
public abstract class BPELCallbackDataEntityEJB implements EntityBean {
    private final static Logger log = Logger
            .getLogger(BPELCallbackDataEntityEJB.class);

    private EntityContext ctx;

    /**
     *  
     */
    public BPELCallbackDataEntityEJB() {
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
        this.ctx = ctx;
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
     * Create method
     * 
     * @ejb.create-method view-type = "local"
     */
    public java.lang.String ejbCreate(java.lang.String ssid)
            throws javax.ejb.CreateException {
        log.debug("-> start processing ...");
        this.setSsid(ssid);
        return null;
    }

    /**
     * Post Create method
     */
    public void ejbPostCreate(java.lang.String ssid)
            throws javax.ejb.CreateException {
        log.debug("-> start processing ...");
    }

    /**
     * Getter for CMP Field ssid
     * 
     * @ejb.pk-field
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.sql-type type = "VARCHAR(100)"
     * @jboss.jdbc-type type = "VARCHAR"
     * @jboss.persistence not-null = "true"
     */
    public abstract java.lang.String getSsid();

    /**
     * Setter for CMP Field ssid
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setSsid(java.lang.String value);

    /**
     * Getter for CMP Field messageId
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.sql-type type = "VARCHAR(250)"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract java.lang.String getMessageId();

    /**
     * Setter for CMP Field messageId
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setMessageId(java.lang.String value);

    /**
     * Getter for CMP Field address
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.sql-type type = "VARCHAR(250)"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract java.lang.String getAddress();

    /**
     * Setter for CMP Field address
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setAddress(java.lang.String value);

    /**
     * Getter for CMP Field userId
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.sql-type type = "VARCHAR(250)"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract java.lang.String getUserId();

    /**
     * Setter for CMP Field userId
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setUserId(java.lang.String value);

    /**
     * Getter for CMP Field portType
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.sql-type type = "VARCHAR(250)"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract java.lang.String getPortType();

    /**
     * Setter for CMP Field portType
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setPortType(java.lang.String value);

    /**
     * Getter for CMP Field serviceName
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.sql-type type = "VARCHAR(250)"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract java.lang.String getServiceName();

    /**
     * Setter for CMP Field serviceName
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setServiceName(java.lang.String value);

}