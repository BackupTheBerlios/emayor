/*
 * Created on Jul 1, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import org.apache.log4j.Logger;

/**
 * @ejb.bean name="ServiceInfoEntity" display-name="Name for ServiceInfoEntity"
 *           description="Description for ServiceInfoEntity"
 *           jndi-name="ejb/emayor/sh/entity/ServiceInfoEntity" type="CMP"
 *           cmp-version="2.x" view-type="local" primkey-field = "serviceId"
 * @jboss.persistence table-name = "SERVICE_INFO" create-table = "true"
 *                    datasource = "java:/MySqlDS" datasource-mapping = "mySQL"
 *                    remove-table = "false"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT OBJECT(o) FROM
 *             ServiceInfoEntity AS o" result-type-mapping = "Local" signature =
 *             "java.util.Collection findAll()"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT OBJECT(o) FROM
 *             ServiceInfoEntity AS o WHERE o.active = TRUE" result-type-mapping =
 *             "Local" signature = "java.util.Collection
 *             findAllActiveServicesInfo()"
 * @ejb.finder method-intf = "LocalHome" query = "SELECT OBJECT(o) FROM
 *             ServiceInfoEntity AS o WHERE o.serviceVersion = ?1" result-type-mapping =
 *             "Local" signature = "java.util.Collection
 *             findByVersion(java.lang.String version)"
 */
public abstract class ServiceInfoEntityEJB implements EntityBean {
    private final static Logger log = Logger
        .getLogger(ServiceInfoEntityEJB.class);

    /**
     *  
     */
    public ServiceInfoEntityEJB() {
        super();
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.ejb.EntityBean#setEntityContext(javax.ejb.EntityContext)
     */
    public void setEntityContext(EntityContext ctx)
        throws EJBException,
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
    public void ejbRemove()
        throws RemoveException,
        EJBException,
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
     * Getter for CMP Field serviceId
     * 
     * @ejb.pk-field
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.column-name name = "SERVICEID"
     * @jboss.sql-type type = "VARCHAR(100)"
     * @jboss.jdbc-type type = "VARCHAR"
     * @jboss.persistence not-null = "true"
     */
    public abstract String getServiceId();

    /**
     * Setter for CMP Field serviceId
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setServiceId(String value);

    /**
     * Create method
     * 
     * @ejb.create-method view-type = "local"
     */
    public String ejbCreate(String serviceId) throws javax.ejb.CreateException {
        log.debug("-> start processing ...");
        this.setServiceId(serviceId);
        return null;
    }

    /**
     * Post Create method
     */
    public void ejbPostCreate(String serviceId)
        throws javax.ejb.CreateException {
        // TODO Auto-generated method stub
    }

    /**
     * Getter for CMP Field serviceVersion
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.column-name name = "SERVICEVERSION"
     * @jboss.sql-type type = "VARCHAR(20)"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract String getServiceVersion();

    /**
     * Setter for CMP Field serviceVersion
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setServiceVersion(String value);

    /**
     * Getter for CMP Field serviceName
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.column-name name = "SERVICENAME"
     * @jboss.sql-type type = "VARCHAR(100)"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract String getServiceName();

    /**
     * Setter for CMP Field serviceName
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setServiceName(String value);

    /**
     * Getter for CMP Field serviceClassName
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.column-name name = "SERVICECLASSNAME"
     * @jboss.sql-type type = "VARCHAR(200)"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract String getServiceClassName();

    /**
     * Setter for CMP Field serviceClassName
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setServiceClassName(String value);

    /**
     * Getter for CMP Field serviceFactoryClassName
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.column-name name = "SERVICEFACTORYCLASSNAME"
     * @jboss.sql-type type = "VARCHAR(100)"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract String getServiceFactoryClassName();

    /**
     * Setter for CMP Field serviceFactoryClassName
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setServiceFactoryClassName(String value);

    /**
     * Getter for CMP Field serviceDescription
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.column-name name = "SERVICEDECRIPTION"
     * @jboss.sql-type type = "TEXT"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract String getServiceDescription();

    /**
     * Setter for CMP Field serviceDescription
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setServiceDescription(String value);

    /**
     * Getter for CMP Field serviceEndpoint
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.column-name name = "SERVICEENDPOINT"
     * @jboss.sql-type type = "VARCHAR(250)"
     * @jboss.jdbc-type type = "VARCHAR"
     */
    public abstract String getServiceEndpoint();

    /**
     * Setter for CMP Field serviceEndpoint
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setServiceEndpoint(String value);

    /**
     * Getter for CMP Field active
     * 
     * 
     * @ejb.persistent-field
     * @ejb.interface-method view-type="local"
     * @jboss.column-name name = "ACTIVE"
     * @jboss.sql-type type = "BOOLEAN"
     * @jboss.jdbc-type type = "TINYINT"
     */
    public abstract Boolean getActive();

    /**
     * Setter for CMP Field active
     * 
     * @ejb.interface-method view-type="local"
     */
    public abstract void setActive(Boolean value);

    /**
     * Getter for CMP Field instances
     *
     * 
     * @ejb.persistent-field
     * @ejb.interface-method   view-type="local"
     * @jboss.column-name name = "INSTANCES"
     * @jboss.sql-type type = "INT"
     * @jboss.jdbc-type type = "INTEGER"
     */
    public abstract java.lang.Integer getInstances();

    /**
     * Setter for CMP Field instances
     *
     * @ejb.interface-method   view-type="local"
     */
    public abstract void setInstances(java.lang.Integer value);
    
    /**
     * Getter for CMP Field instances
     *
     * 
     * @ejb.persistent-field
     * @ejb.interface-method   view-type="local"
     * @jboss.column-name name = "SERVICECLASS"
     * @jboss.sql-type type = "BLOB"
     * @jboss.jdbc-type type = "BLOB"
     */
    public abstract byte[] getServiceClass();
    
    /**
     * Setter for CMP Field instances
     *
     * @ejb.interface-method   view-type="local"
     */
    public abstract void setServiceClass(byte[] serviceClass);
    
    /**
     * Getter for CMP Field instances
     *
     * 
     * @ejb.persistent-field
     * @ejb.interface-method   view-type="local"
     * @jboss.column-name name = "SERVICEFACTORYCLASS"
     * @jboss.sql-type type = "BLOB"
     * @jboss.jdbc-type type = "BLOB"
     */
    public abstract byte[] getServiceFactoryClass();
    
    /**
     * Setter for CMP Field instances
     *
     * @ejb.interface-method   view-type="local"
     */
    public abstract void setServiceFactoryClass(byte[] serviceFactoryClass);

}