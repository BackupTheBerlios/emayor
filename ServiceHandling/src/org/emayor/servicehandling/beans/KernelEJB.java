/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.policyenforcer.C_UserProfile;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.kernel.IKernel;
import org.emayor.servicehandling.kernel.Kernel;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.ServiceInfo;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="Kernel" display-name="Name for Kernel"
 *           description="Description for Kernel" jndi-name="ejb/Kernel"
 *           type="Stateless" view-type="local"
 */
public class KernelEJB implements SessionBean, IKernel {
	private static Logger log = Logger.getLogger(KernelEJB.class);

	private Kernel kernel;
	private SessionContext ctx;

	/**
	 *  
	 */
	public KernelEJB() {
		super();
		this.kernel = Kernel.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx) throws EJBException,
			RemoteException {
		this.ctx = ctx;
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
	public String createAccessSession() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.createAccessSession();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public AccessSessionLocal getAccessSession(String asid)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getAccessSession(asid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public boolean deleteAccessSession(String asid) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.deleteAccessSession(asid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public ServiceSessionLocal createServiceSession(String asid,
			String serviceName) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.createServiceSession(asid, serviceName);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public ServiceSessionLocal getServiceSession(String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getServiceSession(ssid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public boolean deleteServiceSession(String ssid) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.deleteServiceSession(ssid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public ServiceInfo[] listAvailableServices(C_UserProfile userProfile)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.listAvailableServices(userProfile);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public ServiceInfo[] listAllAvailableServices() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.listAllAvailableServices();
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		
	}

}