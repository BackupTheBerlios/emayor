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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#createAccessSession()
	 */
	public String createAccessSession() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.createAccessSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#getAccessSession(java.lang.String)
	 */
	public AccessSessionLocal getAccessSession(String asid)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getAccessSession(asid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#deleteAccessSession(java.lang.String)
	 */
	public boolean deleteAccessSession(String asid) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.deleteAccessSession(asid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#createServiceSession(java.lang.String,
	 *      java.lang.String)
	 */
	public ServiceSessionLocal createServiceSession(String asid,
			String serviceName) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.createServiceSession(asid, serviceName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#getServiceSession(java.lang.String)
	 */
	public ServiceSessionLocal getServiceSession(String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getServiceSession(ssid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#deleteServiceSession(java.lang.String)
	 */
	public boolean deleteServiceSession(String ssid) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.deleteServiceSession(ssid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#listAvailableServices(org.emayor.policyenforcer.C_UserProfile)
	 */
	public ServiceInfo[] listAvailableServices(C_UserProfile userProfile)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.listAvailableServices(userProfile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IKernel#listAllAvailableServices()
	 */
	public ServiceInfo[] listAllAvailableServices() throws KernelException {
		// TODO Auto-generated method stub
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