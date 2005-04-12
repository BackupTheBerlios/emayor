/*
 * Created on Apr 12, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.kernel.AccessSessionException;
import org.emayor.servicehandling.kernel.IServiceCallbackManager;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.ServiceCallbackData;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @ejb.bean name="ServiceCallbackManager" display-name="Name for
 *           ServiceCallbackManager" description="Description for
 *           ServiceCallbackManager"
 *           jndi-name="ejb/eMayor/ServiceCallbackManager" type="Stateless"
 *           view-type="local" 
 */
public class ServiceCallbackManagerEJB implements SessionBean,
        IServiceCallbackManager {
    private static final Logger log = Logger
            .getLogger(ServiceCallbackManagerEJB.class);

    private SessionContext ctx;

    /**
     *  
     */
    public ServiceCallbackManagerEJB() {
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
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public String onResult(ServiceCallbackData result) {
        log.debug("-> start processing ...");
        String ret = "NOT_OK";
        String ssid = "";
        String asid = "";
        String userId = "";
        try {
            ssid = result.getSsid();
            userId = result.getUid();
            ServiceLocator locator = ServiceLocator.getInstance();
            KernelLocal kernel = locator.getKernelLocal();
            log.debug("got the kernel");
            asid = kernel.getAsidByUserID(userId);
            if (log.isDebugEnabled()) {
                log.debug("ssid = " + ssid);
                log.debug("asid = " + asid);
                log.debug("uid  = " + userId);
            }
            log.debug("get the access session !");
            AccessSessionLocal accessSession = kernel.getAccessSession(asid);
            log.debug("remove the kernel");
            kernel.remove();
            log.debug("stop the service session");
            accessSession.stopServiceSession(ssid);
            ret = "OK";
        } catch (ServiceLocatorException slex) {
            log.error("problem with the service locator");
        } catch (KernelException kex) {
            log.error("problem with the kernel");
        } catch (AccessSessionException asex) {
            log.error("couldn't stop the ended service session: ssid=" + ssid
                    + " asid=" + asid);
        } catch (RemoveException rex) {
            log.error("couldn't rwmove the kernel bean");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /**
     * Default create method
     * 
     * @throws CreateException
     * @ejb.create-method
     */
    public void ejbCreate() throws CreateException {
        // TODO Auto-generated method stub
    }

}