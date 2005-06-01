/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.utils;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;
import org.eMayor.FormatTransformation.interfaces.TemplateManager;
import org.eMayor.FormatTransformation.interfaces.TemplateManagerHome;
import org.eMayor.FormatTransformation.interfaces.Transformer;
import org.eMayor.FormatTransformation.interfaces.TransformerHome;
import org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcement;
import org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcementHome;
import org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcementLocal;
import org.eMayor.PolicyEnforcement.interfaces.PolicyEnforcementLocalHome;
import org.eMayor.ServiceHandling.PrintingUtility.interfaces.Printer;
import org.eMayor.ServiceHandling.PrintingUtility.interfaces.PrinterHome;
import org.emayor.ContentRouting.interfaces.ContentRouter;
import org.emayor.ContentRouting.interfaces.ContentRouterHome;
import org.emayor.ContentRouting.interfaces.ContentRouterLocal;
import org.emayor.ContentRouting.interfaces.ContentRouterLocalHome;
import org.emayor.notification.wrapper.interfaces.BPELNotificationWrapper;
import org.emayor.notification.wrapper.interfaces.BPELNotificationWrapperHome;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.interfaces.AccessManagerLocalHome;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.AccessSessionLocalHome;
import org.emayor.servicehandling.interfaces.AdminManagerLocal;
import org.emayor.servicehandling.interfaces.AdminManagerLocalHome;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.interfaces.KernelLocalHome;
import org.emayor.servicehandling.interfaces.ServiceCallbackManagerLocal;
import org.emayor.servicehandling.interfaces.ServiceCallbackManagerLocalHome;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocalHome;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocal;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocalHome;
import org.emayor.servicehandling.interfaces.UserTaskManagerLocal;
import org.emayor.servicehandling.interfaces.UserTaskManagerLocalHome;
import org.emayor.servicehandling.kernel.bpel.forward.client.IForwardManagerBPELCallbackService;
import org.emayor.servicehandling.kernel.bpel.starter.IeMayorServiceStarterService;
import org.emayor.servicehandling.model.UTWrapperEJB;
import org.emayor.servicehandling.model.UTWrapperEJBHome;

/**
 * @author tku
 */
public class ServiceLocator {
    private static final Logger log = Logger.getLogger(ServiceLocator.class);

    private static ServiceLocator _self = null;

    private Context initialContext = null;

    private Context bpelInitialContext = null;

    private ServiceLocator() throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        this.initInitialContext();
        this.initBpelInitialContext();
        log.debug("-> ... processing DONE!");
    }

    /**
     * 
     * @return
     * @throws ServiceLocatorException
     */
    public static synchronized final ServiceLocator getInstance()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        if (_self == null)
            _self = new ServiceLocator();
        return _self;
    }

    public synchronized AccessManagerLocal getAccessManager()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        AccessManagerLocal ret = null;
        try {
            Object ref = this.initialContext
                    .lookup(AccessManagerLocalHome.JNDI_NAME);
            AccessManagerLocalHome home = (AccessManagerLocalHome) PortableRemoteObject
                    .narrow(ref, AccessManagerLocalHome.class);
            ret = home.create();
            log.debug("got the reference !");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /**
     * 
     * @return
     * @throws ServiceLocatorException
     */
    public synchronized AccessSessionLocal getAccessSessionLocal()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        AccessSessionLocal ret = null;
        try {
            Object ref = this.initialContext
                    .lookup(AccessSessionLocalHome.JNDI_NAME);
            AccessSessionLocalHome home = (AccessSessionLocalHome) PortableRemoteObject
                    .narrow(ref, AccessSessionLocalHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized SimpleIdGeneratorLocal getSimpleIdGeneratorLocal()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        SimpleIdGeneratorLocal ret = null;
        try {
            Object ref = this.initialContext
                    .lookup(SimpleIdGeneratorLocalHome.JNDI_NAME);
            SimpleIdGeneratorLocalHome home = (SimpleIdGeneratorLocalHome) PortableRemoteObject
                    .narrow(ref, SimpleIdGeneratorLocalHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized ServiceSessionLocal getServiceSessionLocal(
            String accessSessionId) throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        ServiceSessionLocal ret = null;
        try {
            Object ref = this.initialContext
                    .lookup(ServiceSessionLocalHome.JNDI_NAME);
            ServiceSessionLocalHome home = (ServiceSessionLocalHome) PortableRemoteObject
                    .narrow(ref, ServiceSessionLocalHome.class);
            ret = home.create(accessSessionId);
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized KernelLocal getKernelLocal()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        KernelLocal ret = null;
        try {
            Object ref = this.initialContext.lookup(KernelLocalHome.JNDI_NAME);
            KernelLocalHome home = (KernelLocalHome) PortableRemoteObject
                    .narrow(ref, KernelLocalHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized UserTaskManagerLocal getUserTaskManagerLocal()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        UserTaskManagerLocal ret = null;
        try {
            Object ref = this.initialContext
                    .lookup(UserTaskManagerLocalHome.JNDI_NAME);
            UserTaskManagerLocalHome home = (UserTaskManagerLocalHome) PortableRemoteObject
                    .narrow(ref, UserTaskManagerLocalHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized PolicyEnforcement getPolicyEnforcement()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        PolicyEnforcement ret = null;
        try {
            Object ref = this.initialContext
                    .lookup(PolicyEnforcementHome.JNDI_NAME);
            PolicyEnforcementHome home = (PolicyEnforcementHome) PortableRemoteObject
                    .narrow(ref, PolicyEnforcementHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        } catch (RemoteException rex) {
            log.error("caught ex: " + rex.toString());
            throw new ServiceLocatorException(rex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized PolicyEnforcementLocal getPolicyEnforcementLocal()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        PolicyEnforcementLocal ret = null;
        try {
            Object ref = this.initialContext
                    .lookup(PolicyEnforcementLocalHome.JNDI_NAME);
            PolicyEnforcementLocalHome home = (PolicyEnforcementLocalHome) PortableRemoteObject
                    .narrow(ref, PolicyEnforcementLocalHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized Printer getPrinter() throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        Printer ret = null;
        try {
            Object ref = this.initialContext.lookup(PrinterHome.JNDI_NAME);
            PrinterHome home = (PrinterHome) PortableRemoteObject.narrow(ref,
                    PrinterHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        } catch (RemoteException rex) {
            log.error("caught ex: " + rex.toString());
            throw new ServiceLocatorException(rex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized UTWrapperEJB getUTWrapperRemoteInterface()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        UTWrapperEJB ret = null;
        try {
            Object ref = this.bpelInitialContext.lookup("UTWrapperEJB");
            UTWrapperEJBHome home = (UTWrapperEJBHome) PortableRemoteObject
                    .narrow(ref, UTWrapperEJBHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        } catch (RemoteException rex) {
            log.error("caught ex: " + rex.toString());
            throw new ServiceLocatorException(rex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized BPELNotificationWrapper getBPELNotificationWrapper()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        BPELNotificationWrapper ret = null;
        try {
            Object ref = this.bpelInitialContext
                    .lookup(BPELNotificationWrapperHome.JNDI_NAME);
            BPELNotificationWrapperHome home = (BPELNotificationWrapperHome) PortableRemoteObject
                    .narrow(ref, BPELNotificationWrapperHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        } catch (RemoteException rex) {
            log.error("caught ex: " + rex.toString());
            throw new ServiceLocatorException(rex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized Transformer getTransfromer()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        Transformer ret = null;
        try {
            Object ref = this.bpelInitialContext
                    .lookup(TransformerHome.JNDI_NAME);
            TransformerHome home = (TransformerHome) PortableRemoteObject
                    .narrow(ref, TransformerHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        } catch (RemoteException rex) {
            log.error("caught ex: " + rex.toString());
            throw new ServiceLocatorException(rex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized TemplateManager getTemplateManager()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        TemplateManager ret = null;
        try {
            Object ref = this.bpelInitialContext
                    .lookup(TemplateManagerHome.JNDI_NAME);
            TemplateManagerHome home = (TemplateManagerHome) PortableRemoteObject
                    .narrow(ref, TemplateManagerHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        } catch (RemoteException rex) {
            log.error("caught ex: " + rex.toString());
            throw new ServiceLocatorException(rex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized ContentRouter getContentRouter()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        ContentRouter ret = null;
        try {
            Object ref = this.initialContext
                    .lookup(ContentRouterHome.JNDI_NAME);
            ContentRouterHome home = (ContentRouterHome) PortableRemoteObject
                    .narrow(ref, ContentRouterHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        } catch (RemoteException rex) {
            log.error("caught ex: " + rex.toString());
            throw new ServiceLocatorException(rex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized ContentRouterLocal getContentRouterLocal()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        ContentRouterLocal ret = null;
        try {
            Object ref = this.initialContext
                    .lookup(ContentRouterLocalHome.JNDI_NAME);
            ContentRouterLocalHome home = (ContentRouterLocalHome) PortableRemoteObject
                    .narrow(ref, ContentRouterLocalHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    /*
     * public synchronized UserProfileLocalHome getUserProfileLocalHome() throws
     * ServiceLocatorException { log.debug("-> starting processing ...");
     * UserProfileLocalHome ret = null; try { Object ref =
     * this.bpelInitialContext .lookup("UserProfileLocal"); UserProfileLocalHome
     * home = (UserProfileLocalHome) PortableRemoteObject .narrow(ref,
     * UserProfileLocalHome.class); ret = home; log.debug("got the reference!"); }
     * catch (NamingException nex) { log.error("caught ex: " + nex.toString());
     * throw new ServiceLocatorException(nex); } log.debug("-> ... processing
     * DONE!"); return ret; }
     */
    public synchronized ServiceCallbackManagerLocal getServiceCallbackManagerLocal()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        ServiceCallbackManagerLocal ret = null;
        try {
            Object ref = this.bpelInitialContext
                    .lookup(ServiceCallbackManagerLocalHome.JNDI_NAME);
            ServiceCallbackManagerLocalHome home = (ServiceCallbackManagerLocalHome) PortableRemoteObject
                    .narrow(ref, ServiceCallbackManagerLocalHome.class);
            ret = home.create();
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized IForwardManagerBPELCallbackService getIForwardManagerBPELCallbackService()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        IForwardManagerBPELCallbackService ret = null;
        InitialContext initialContext = this
                .getInitialContextForWSClient("ForwardManagerBPELClient");
        try {
            ret = (IForwardManagerBPELCallbackService) initialContext
                    .lookup("java:comp/env/service/ForwardManagerBPELClient");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized IeMayorServiceStarterService getIeMayorServiceStarterService()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        IeMayorServiceStarterService ret = null;
        InitialContext initialContext = this
                .getInitialContextForWSClient("IeMayorServiceStarterServiceClient");
        try {
            ret = (IeMayorServiceStarterService) initialContext
                    .lookup("java:comp/env/service/IeMayorServiceStarterServiceClient");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized AdminManagerLocal getAdminManagerLocal()
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        AdminManagerLocal ret = null;
        try {
            Object ref = this.bpelInitialContext
                    .lookup(AdminManagerLocalHome.JNDI_NAME);
            AdminManagerLocalHome home = (AdminManagerLocalHome) PortableRemoteObject
                    .narrow(ref, AdminManagerLocalHome.class);
            ret = home.create("admin");
            log.debug("got the reference!");
        } catch (NamingException nex) {
            log.error("caught ex: " + nex.toString());
            throw new ServiceLocatorException(nex);
        } catch (CreateException cex) {
            log.error("caught ex: " + cex.toString());
            throw new ServiceLocatorException(cex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public synchronized Context getInitialContext() {
        log.debug("-> starting processing ...");
        return this.initialContext;
    }

    private void initInitialContext() throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        try {
            this.initialContext = new InitialContext();
        } catch (NamingException ex) {
            log.error("caught ex: " + ex.toString());
            throw new ServiceLocatorException(ex);
        }
        log.debug("-> ... processing DONE!");
    }

    private InitialContext getInitialContextForWSClient(String clientName)
            throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        InitialContext ret = null;
        try {
            Properties env = new Properties();
            env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            env
                    .setProperty(Context.URL_PKG_PREFIXES,
                            "org.jboss.naming.client");
            env.setProperty(Context.PROVIDER_URL, "jnp://localhost:1099");
            env.setProperty("j2ee.clientName", clientName);
            ret = new InitialContext(env);
        } catch (NamingException ex) {
            log.error("caught ex: " + ex.toString());
            throw new ServiceLocatorException(ex);
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    private void initBpelInitialContext() throws ServiceLocatorException {
        log.debug("-> starting processing ...");
        try {
            Hashtable env = new Hashtable();
            Config config = Config.getInstance();
            // Standalone OC4J connection details
            env.put(Context.INITIAL_CONTEXT_FACTORY, config
                    .getProperty("bpel.engine.ut.initial.context.factory"));
            env.put(Context.SECURITY_PRINCIPAL, config
                    .getProperty("bpel.engine.ut.security.principal"));
            env.put(Context.SECURITY_CREDENTIALS, config
                    .getProperty("bpel.engine.ut.security.credentials"));
            env.put(Context.PROVIDER_URL, config
                    .getProperty("bpel.engine.ut.provider.url"));
            this.bpelInitialContext = new InitialContext(env);
        } catch (NamingException ex) {
            log.error("caught ex: " + ex.toString());
            throw new ServiceLocatorException(ex);
        } catch (ConfigException confex) {
            log.error("caught ex: " + confex.toString());
            throw new ServiceLocatorException(
                    "couldn't read t he configuration");
        }
        log.debug("-> ... processing DONE!");
    }
}