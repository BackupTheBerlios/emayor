/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocal;
import org.emayor.servicehandling.kernel.IServiceSession;
import org.emayor.servicehandling.kernel.IeMayorService;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.ServiceSessionException;
import org.emayor.servicehandling.kernel.SessionException;
import org.emayor.servicehandling.kernel.eMayorServiceException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @ejb.bean name="ServiceSession" display-name="ServiceSessionEJB"
 *           description="Description for ServiceSession"
 *           jndi-name="ejb/ServiceSession" type="Stateful" view-type="local"
 */
public class ServiceSessionEJB implements SessionBean, IServiceSession {
    private static Logger log = Logger.getLogger(ServiceSessionEJB.class);

    private String asid;

    private String ssid;

    private String serviceId;

    private SessionContext ctx;

    private IeMayorService eMayorService;

    /**
     *  
     */
    public ServiceSessionEJB() {
        super();
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
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.ejb.SessionBean#ejbRemove()
     */
    public void ejbRemove() throws EJBException, RemoteException {
        // TODO Auto-generated method stub
        log.debug("-> start processing ...");
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.ejb.SessionBean#ejbActivate()
     */
    public void ejbActivate() throws EJBException, RemoteException {
        // TODO Auto-generated method stub
        log.debug("-> start processing ...");
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.ejb.SessionBean#ejbPassivate()
     */
    public void ejbPassivate() throws EJBException, RemoteException {
        // TODO Auto-generated method stub
        log.debug("-> start processing ...");
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public String getAccessSessionId() throws ServiceSessionException {
        log.debug("getting asid ...");
        return this.asid;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public void setAccessSessionId(String asid) throws ServiceSessionException {
        if (log.isDebugEnabled())
            log.debug("setting asid to " + asid);
        this.asid = asid;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public String getServiceId() throws ServiceSessionException {
        log.debug("getting service name");
        return this.serviceId;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public void setServiceId(String serviceId) throws ServiceSessionException {
        log.debug("getting service name");
        this.serviceId = serviceId;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public void stopService(String reason) throws ServiceSessionException {
        // TODO Auto-generated method stub
        log.debug("-> start processing ...");
        throw new ServiceSessionException("NOT IMPLEMENTED !!!");
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public String getSessionId() throws SessionException {
        log.debug("-> start processing ...");
        return this.ssid;
    }

    /**
     * Default create method
     * 
     * @throws CreateException
     * @ejb.create-method
     */
    public void ejbCreate(String asid) throws CreateException {
        log.debug("-> start processing ...");
        if (asid == null || asid.length() == 0)
            throw new CreateException("the given asid has to be a valid value!");
        this.asid = asid;
        try {
            ServiceLocator serviceLocator = ServiceLocator.getInstance();
            SimpleIdGeneratorLocal simpleIdGeneratorLocal = serviceLocator
                    .getSimpleIdGeneratorLocal();
            this.ssid = simpleIdGeneratorLocal.generateId();
            simpleIdGeneratorLocal.remove();
            if (log.isDebugEnabled())
                log.debug("generated following ssid : " + ssid);
        } catch (ServiceLocatorException slex) {
            log.error("caught ex: " + slex.toString());
            throw new CreateException(slex.toString());
        } catch (RemoveException rex) {
            log.error("caught ex: " + rex.toString());
            throw new CreateException("internal error");
        }
        log.debug("-> ... processing DONE!");
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public IeMayorService geteMayorService() throws ServiceSessionException {
        log.debug("-> start processing ...");
        return this.eMayorService;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public void seteMayorService(IeMayorService emayorService)
            throws ServiceSessionException {
        log.debug("-> start processing ...");
        this.eMayorService = emayorService;
        log.debug("-> ... processing DONE!");
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public void startService(String userId, boolean isForwarded, String xmlDoc,
            String docSig) throws ServiceSessionException {
        log.debug("-> start processing ...");
        try {
            log.debug("starting the service :-)");
            if (isForwarded)
                this.eMayorService.forward(userId, this.ssid, xmlDoc, docSig);
            else {
                this.eMayorService.startService(userId, this.ssid);
            }
        } catch (eMayorServiceException emsex) {
            log.error("caught ex: " + emsex.toString());
            throw new ServiceSessionException("Couldn't start the service :-(");
        }
        log.debug("-> ... processing DONE!");
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public void endService() throws ServiceSessionException {
        log.debug("-> start processing ...");
        try {
            ServiceLocator locator = ServiceLocator.getInstance();
            KernelLocal kernel = locator.getKernelLocal();
            kernel.deleteServiceSession(this.asid, this.ssid);
            kernel.remove();
        } catch (ServiceLocatorException slex) {
            log.error("cannot get an instance of the service locator");
            throw new ServiceSessionException(
                    "cannot get an instance of the service locator");
        } catch (KernelException kex) {
            log.error("cannot delete successful service session: ssid=" + ssid);
            throw new ServiceSessionException(
                    "cannot delete successful service session");
        } catch (RemoveException rex) {
            log.error("cannot remove the instance of the kernel ejb");
            throw new ServiceSessionException(
                    "cannot remove the instance of the kernel ejb");
        }
        log.debug("-> ... processing DONE!");
    }
}