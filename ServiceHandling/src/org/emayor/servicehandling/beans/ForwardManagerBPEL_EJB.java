/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardMessageBPEL;
import org.emayor.servicehandling.kernel.bpel.forward.server.IForwardManagerBPEL;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="ForwardManagerBPEL" display-name="Name for
 *           ForwardManagerBPEL" description="Description for
 *           ForwardManagerBPEL"
 *           jndi-name="ejb/eMayor/servicehandling/ForwardManagerBPEL"
 *           type="Stateless" view-type="local"
 */
public class ForwardManagerBPEL_EJB implements SessionBean, IForwardManagerBPEL {
    private static final Logger log = Logger
            .getLogger(ForwardManagerBPEL_EJB.class);

    private SessionContext ctx;

    /**
     *  
     */
    public ForwardManagerBPEL_EJB() {
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
    public void forwardRequest(ForwardMessageBPEL message) {
        log.debug("-> start processing ...");

        log.debug("-> ... processing DONE!");
    }

    /**
     * Business Method
     * 
     * @ejb.interface-method view-type = "local"
     *  
     */
    public void forwardResponse(ForwardMessageBPEL message) {
        log.debug("-> start processing ...");

        log.debug("-> ... processing DONE!");
    }

    /**
     * Default create method
     * 
     * @throws CreateException
     * @ejb.create-method
     */
    public void ejbCreate() throws CreateException {
        log.debug("-> start processing ...");
    }

}