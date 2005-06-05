/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.kernel.AccessSessionInfo;
import org.emayor.servicehandling.kernel.AdminException;
import org.emayor.servicehandling.kernel.IAdmin;
import org.emayor.servicehandling.kernel.IUserProfile;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.ServiceProfile;
import org.emayor.servicehandling.kernel.ServiceSessionInfo;
import org.emayor.servicehandling.kernel.UserProfile;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @ejb.bean name="AdminManager" display-name="Name for AdminManager"
 *           description="Description for AdminManager"
 *           jndi-name="ejb/emayor/sh/admin/AdminManager" type="Stateful"
 *           view-type="local"
 */
public class AdminManagerEJB implements SessionBean, IAdmin {
    private final static Logger log = Logger.getLogger(AdminManagerEJB.class);

    private SessionContext ctx = null;

    private boolean isEnabled;

    private String uid;

    private String pswd;

    /**
     *  
     */
    public AdminManagerEJB() {
        log.debug("-> start processing ...");
        init();
        log.debug("-> ... processing DONE!");
    }

    private void init() {
        log.debug("-> start processing ...");
        try {
            Config config = Config.getInstance();
            String str = config.getProperty(
                    "emayor.admin.interface.is.enabled", "NO");
            this.isEnabled = str.equals("YES");
            str = config
                    .getProperty("emayor.admin.interface.userid", "UNKNOWN");
            if (str == null || str.length() == 0 || str.equals("UNKNOWN")) {
                log
                        .info("the admin uid is not set -> disable the admin interface at all");
                this.isEnabled = false;
            } else {
                log.debug("found the correct admin uid -> go on");
                this.uid = str;
            }
            str = config.getProperty("emayor.admin.interface.password",
                    "UNKNOWN");
            if (str == null || str.length() == 0 || str.equals("UNKNOWN")) {
                log
                        .info("the admin password is not set -> disable the admin interface at all");
                this.isEnabled = false;
            } else {
                log.debug("found the correct admin password -> go on");
                this.pswd = str;
            }
        } catch (ConfigException ex) {
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
     * Default create method
     * 
     * @throws CreateException
     * @ejb.create-method
     */
    public void ejbCreate(String param) throws CreateException {
        log.debug("-> start processing ...");
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public boolean isEnabled() throws AdminException {
        log.debug("-> start processing ...");
        return this.isEnabled;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public boolean login(String uid, String pswd) throws AdminException {
        log.debug("-> start processing ...");
        boolean ret = false;
        if (uid == null || uid.length() == 0) {
            log.debug("bad input admin uid");
            throw new AdminException("bad admin user id");
        }
        if (pswd == null || pswd.length() == 0) {
            log.debug("bad input admin password");
            throw new AdminException("bad admin password");
        }
        if (this.uid.equals(uid)) {
            if (this.pswd.equals(pswd)) {
                log.info("admin has logged in successful!");
                ret = true;
            } else {
                log.debug("the inputed admin password doesn't match");
            }
        } else {
            log.debug("the inputed admin user id doesn't match");
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
    public void reloadServices() throws AdminException {
        log.debug("-> start processing ...");
        try {
            ServiceLocator locator = ServiceLocator.getInstance();
            KernelLocal kernel = locator.getKernelLocal();
            kernel.reloadDeployedServices();
            kernel.remove();
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException("Couldn't reload services!");
        } catch (KernelException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException("Couldn't reload services!");
        } catch (EJBException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException("Internal fatal ERROR!");
        } catch (RemoveException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException("Internal fatal ERROR!");
        }
        log.debug("-> ... processing DONE!");
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public void reloadConfiguration() throws AdminException {
        log.debug("-> start processing ...");
        try {
            Config.getInstance().reloadConfiguration();
            this.init();
        } catch (ConfigException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException("Internal fatal ERROR!");
        }
        log.debug("-> ... processing DONE!");
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public UserProfile[] listLoggedInUsers() throws AdminException {
        log.debug("-> start processing ...");
        UserProfile[] ret = new UserProfile[0];
        try {
            ServiceLocator locator = ServiceLocator.getInstance();
            KernelLocal kernel = locator.getKernelLocal();
            IUserProfile[] tmp = kernel.listLoggedInUsers();
            ret = new UserProfile[tmp.length];
            for (int i = 0; i < tmp.length; i++)
                ret[i] = (UserProfile) tmp[i];
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException("Couldn't connect to the kernel!");
        } catch (KernelException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException(
                    "Couldn't get the profile data from kernel!");
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
    public UserProfile lookupUserProfile(String uid) throws AdminException {
        log.debug("-> start processing ...");
        UserProfile ret = null;
        try {
            ServiceLocator locator = ServiceLocator.getInstance();
            KernelLocal kernel = locator.getKernelLocal();
            ret = (UserProfile) kernel.getUserProfile(uid);
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException("Couldn't connect to the kernel!");
        } catch (KernelException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException(
                    "Couldn't get the profile data from kernel!");
        }
        return null;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public UserProfile[] listAllKnownUsers() throws AdminException {
        log.debug("-> start processing ...");
        UserProfile[] ret = new UserProfile[0];
        try {
            ServiceLocator locator = ServiceLocator.getInstance();
            KernelLocal kernel = locator.getKernelLocal();
            IUserProfile[] tmp = kernel.listUserProfiles();
            ret = new UserProfile[tmp.length];
            for (int i = 0; i < tmp.length; i++)
                ret[i] = (UserProfile) tmp[i];
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException("Couldn't connect to the kernel!");
        } catch (KernelException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException(
                    "Couldn't get the profile data from kernel!");
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
    public AccessSessionInfo[] listAccessSessions() throws AdminException {
        log.debug("-> start processing ...");
        AccessSessionInfo[] ret = new AccessSessionInfo[0];
        try {
            ServiceLocator locator = ServiceLocator.getInstance();
            log.debug("get the ref to the kernel");
            KernelLocal kernel = locator.getKernelLocal();
            ret = kernel.listAccessSessions();
            if (log.isDebugEnabled())
                log.debug("got " + ret.length + " from kernel");
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException("Couldn't connect to the kernel!");
        } catch (KernelException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException(
                    "Couldn't get the access session from the kernel!");
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
    public AccessSessionInfo lookupAccessSession(String asid)
            throws AdminException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public ServiceSessionInfo[] listServiceSessions() throws AdminException {
        log.debug("-> start processing ...");
        ServiceSessionInfo[] ret = new ServiceSessionInfo[0];
        try {
            ServiceLocator locator = ServiceLocator.getInstance();
            log.debug("get the ref to the kernel");
            KernelLocal kernel = locator.getKernelLocal();
            ret = kernel.listServiceSessions();
            if (log.isDebugEnabled())
                log.debug("got " + ret.length + " from kernel");
        } catch (ServiceLocatorException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException("Couldn't connect to the kernel!");
        } catch (KernelException ex) {
            log.error("caught ex: " + ex.toString());
            throw new AdminException(
                    "Couldn't get the service session from the kernel!");
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
    public ServiceSessionInfo lookupServiceSession(String ssid)
            throws AdminException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public ServiceProfile[] listDeployedServices() throws AdminException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public ServiceProfile lookupServiceProfile(String serviceId)
            throws AdminException {
        // TODO Auto-generated method stub
        return null;
    }

}