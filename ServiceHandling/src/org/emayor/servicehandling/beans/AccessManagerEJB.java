/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;
import java.security.cert.X509Certificate;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.kernel.AccessSessionException;
import org.emayor.servicehandling.kernel.ForwardMessage;
import org.emayor.servicehandling.kernel.IAccess;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.RunningServicesInfo;
import org.emayor.servicehandling.kernel.ServiceInfo;
import org.emayor.servicehandling.kernel.ServicesInfo;
import org.emayor.servicehandling.kernel.SessionException;
import org.emayor.servicehandling.kernel.UserProfile;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @ejb.bean name="AccessManager" display-name="Name for AccessManager"
 *           description="Description for AccessManager"
 *           jndi-name="ejb/emayor/sh/AccessManager" type="Stateless"
 *           view-type="local"
 *  
 */
public class AccessManagerEJB implements SessionBean, IAccess {
    private static Logger log = Logger.getLogger(AccessManagerEJB.class);

    private SessionContext ctx;

    private KernelLocal kernel;

    /**
     *  
     */
    public AccessManagerEJB() {
        super();
        log.debug("-> start processing ...");
        try {
            ServiceLocator locator = ServiceLocator.getInstance();
            this.kernel = locator.getKernelLocal();
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
        }
        log.debug("-> ... processing DONE!");
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
    public String createAccessSession() throws AccessException {
        log.debug("-> start processing ...");
        String ret = "12ee34";
        try {
            log.debug("starting the access session -> kernel");
            ret = kernel.createAccessSession();
            if (log.isDebugEnabled())
                log.debug("got from kernel asid = " + ret);
        } catch (KernelException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AccessException(
                    "Kernel couldn't create a new access session!");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public String startService(String accessSessionId, String serviceId)
            throws AccessException {
        log.debug("-> start processing ...");
        String ret = "";
        try {
            log.debug("getting the current access session from kernel");
            AccessSessionLocal as = this.kernel
                    .getAccessSession(accessSessionId);
            if (log.isDebugEnabled())
                log.debug("got access session with id: " + as.getSessionId());
            ret = as.startServiceSession(serviceId, false, "", "");
            if (log.isDebugEnabled())
                log.debug("started service ssid = " + ret);
        } catch (KernelException ex) {
            log.error("caught ex: " + ex);
            throw new AccessException(
                    "Unable to get the list of available services!");
        } catch (AccessSessionException aex) {
            log.error("caught ex: " + aex.toString());
            throw new AccessException("Unable to start the service: "
                    + serviceId);
        } catch (SessionException sex) {
            log.error("caught ex: " + sex.toString());
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public String startService(String accessSessionId, String serviceId,
            String requestDocument) throws AccessException {
        log.debug("-> start processing ...");
        String ret = "";
        try {
            log.debug("getting the current access session from kernel");
            AccessSessionLocal as = this.kernel
                    .getAccessSession(accessSessionId);
            if (log.isDebugEnabled())
                log.debug("got access session with id: " + as.getSessionId());
            ret = as.startServiceSession(serviceId, requestDocument);
            if (log.isDebugEnabled())
                log.debug("started service ssid = " + ret);
        } catch (KernelException ex) {
            log.error("caught ex: " + ex);
            throw new AccessException(
                    "Unable to get the list of available services!");
        } catch (AccessSessionException aex) {
            log.error("caught ex: " + aex.toString());
            throw new AccessException("Unable to start the service: "
                    + serviceId);
        } catch (SessionException sex) {
            log.error("caught ex: " + sex.toString());
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public boolean stopService(String accessSessionId, String serviceSessionId)
            throws AccessException {
        log.debug("-> start processing ...");
        boolean ret = false;
        try {
            log.debug("getting the current access session from kernel");
            AccessSessionLocal as = this.kernel
                    .getAccessSession(accessSessionId);
            if (log.isDebugEnabled())
                log.debug("got access session with id: " + as.getSessionId());
            ret = as.stopServiceSession(serviceSessionId);
        } catch (KernelException ex) {
            log.error("caught ex: " + ex);
            throw new AccessException(
                    "Unable to get the list of available services!");
        } catch (AccessSessionException aex) {
            log.error("caught ex: " + aex.toString());
            throw new AccessException("Unable to stop the service!");
        } catch (SessionException sex) {
            log.error("caught ex: " + sex.toString());
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public ServicesInfo listAvailableServices(String accessSessionId)
            throws AccessException {
        log.debug("-> start processing ...");
        ServicesInfo ret = new ServicesInfo();
        if (accessSessionId == null || accessSessionId.equals("-1001")) {
            log.debug("anonymous login - list of services");
            try {
                // listing of all active services without differentiating
                // among the users
                ServiceInfo[] services = this.kernel.listAllActiveServices();
                if (log.isDebugEnabled())
                    log.debug("currently got " + services.length + " services");
                ret.setServicesInfo(services);
            } catch (KernelException ex) {
                log.error("caught ex: " + ex);
                throw new AccessException(
                        "Unable to get the default list of available services!");
            }
        } else {
            try {
                // lookup only the active services the current user is allowed
                // to start
                AccessSessionLocal as = this.kernel
                        .getAccessSession(accessSessionId);
                ret.setServicesInfo(as.listAvailableServices());
            } catch (KernelException ex) {
                log.error("caught ex: " + ex);
                throw new AccessException(
                        "Unable to get the list of available services!");
            } catch (AccessSessionException asex) {
                log.error("caught ex: " + asex);
                throw new AccessException(
                        "Unable to get the list of available services!");
            }
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public RunningServicesInfo listRunningServices(String accessSessionId)
            throws AccessException {
        // TODO Auto-generated method stub
        log.debug("-> start processing ...");
        RunningServicesInfo ret = new RunningServicesInfo();

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
        log.debug("-> start processing ...");

        log.debug("-> ... processing DONE!");
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public boolean stopAccessSession(String accessSessionId)
            throws AccessException {
        log.debug("-> start processing ...");
        boolean ret = false;
        try {
            log.debug("getting the current access session from kernel");
            AccessSessionLocal as = this.kernel
                    .getAccessSession(accessSessionId);
            if (log.isDebugEnabled())
                log.debug("got access session with id: " + as.getSessionId());
            log.debug("stop the access session!");
            ret = as.stop();
            log.debug("remove the AccessSessionEJB");
            as.remove();
        } catch (KernelException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AccessException(
                    "Couldn't obtain the right Access Session instance!");
        } catch (AccessSessionException asex) {
            log.error("caught ex: " + asex.toString());
            throw new AccessException("Couldn't stop the access session");
        } catch (RemoveException rex) {
            log.error("caught ex: " + rex.toString());
        } catch (SessionException sex) {
            log.error("caught ex: " + sex.toString());
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public String startForwardedService(String accessSessionId,
            String serviceId, ForwardMessage message) throws AccessException {
        log.debug("-> start processing ...");
        String ret = "";
        try {
            log.debug("getting the current access session from kernel");
            AccessSessionLocal as = this.kernel
                    .getAccessSession(accessSessionId);
            if (log.isDebugEnabled())
                log.debug("got access session with id: " + as.getSessionId());
            ret = as.startServiceSession(serviceId, true, message
                    .getDocuments().getItem(0), "");
            if (log.isDebugEnabled())
                log.debug("started service ssid = " + ret);
        } catch (KernelException ex) {
            log.error("caught ex: " + ex);
            throw new AccessException(
                    "Unable to get the list of available services!");
        } catch (AccessSessionException aex) {
            log.error("caught ex: " + aex.toString());
            throw new AccessException("Unable to start the service: "
                    + serviceId);
        } catch (SessionException sex) {
            log.error("caught ex: " + sex.toString());
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public boolean startAccessSession(String asid,
            X509Certificate[] certificates) throws AccessException {
        log.debug("-> start processing ...");
        boolean ret = false;
        try {
            AccessSessionLocal as = this.kernel.getAccessSession(asid);
            ret = as.authenticateUser(certificates);
        } catch (KernelException kex) {
            log.error("caught ex: " + kex.toString());
            throw new AccessException(
                    "specified access session probably doesn't exist");
        } catch (AccessSessionException asex) {
            log.error("caught ex: " + asex.toString());
            throw new AccessException("user authentication failed");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public UserProfile getUserProfile(String asid) throws AccessException {
        log.debug("-> start processing ...");
        UserProfile ret = null;
        try {
            AccessSessionLocal as = this.kernel.getAccessSession(asid);
            ret = (UserProfile) this.kernel.getUserProfile(as.getUserId());
        } catch (KernelException kex) {
            log.error("caught ex: " + kex.toString());
            throw new AccessException(
                    "specified access session probably doesn't exist");
        } catch (AccessSessionException asex) {
            log.error("caught ex: " + asex.toString());
            throw new AccessException("user authentication failed");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }
}