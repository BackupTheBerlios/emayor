/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.security.cert.X509Certificate;

import org.apache.log4j.Logger;
import org.emayor.policyenforcer.C_UserProfile;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocal;
import org.emayor.servicehandling.kernel.AccessSessionException;
import org.emayor.servicehandling.kernel.IAccessSession;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.ServiceInfo;
import org.emayor.servicehandling.kernel.SessionException;
import org.emayor.servicehandling.utils.AccessSessionSSRepository;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

import javax.ejb.CreateException;

/**
 * @ejb.bean name="AccessSession" display-name="Name for AccessSession"
 *           description="Description for AccessSession"
 *           jndi-name="ejb/AccessSession" type="Stateful" view-type="local"
 */
public class AccessSessionEJB implements SessionBean, IAccessSession {
	private static Logger log = Logger.getLogger(AccessSessionEJB.class);

	private boolean isSessionActive = false;
	
	private String asid;

	private SessionContext ctx;

	private C_UserProfile userProfile;

	private AccessSessionSSRepository repository = null;

	/**
	 *  
	 */
	public AccessSessionEJB() {
		super();
		this.repository = new AccessSessionSSRepository();
		this.isSessionActive = false;
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
	public boolean authenticateUser(X509Certificate certificate)
			throws AccessSessionException {
		// TODO Auto-generated method stub
		this.isSessionActive = true;
		return true;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public ServiceSessionLocal[] getAllServiceSessions()
			throws AccessSessionException {
		log.debug("-> start processing ...");
		ServiceSessionLocal[] ret;
		List ssLocals = this.repository.getAllServiceSessions();
		ret = new ServiceSessionLocal[ssLocals.size()];
		int index = 0;
		for (Iterator i = ssLocals.iterator(); i.hasNext();) {
			ret[index++] = (ServiceSessionLocal) i.next();
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
	public ServiceSessionLocal getServiceSession(String ssid)
			throws AccessSessionException {
		log.debug("-> start processing ...");
		ServiceSessionLocal ret = null;
		ret = this.repository.getServiceSessionLocal(ssid);
		if (ret == null)
			throw new AccessSessionException(
					"Couldn't find specified service session!");
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public String startServiceSession(String serviceId, boolean isForwarded)
			throws AccessSessionException {
		log.debug("-> start processing ...");
		if (log.isDebugEnabled())
			log.debug("input: serviceId=" + serviceId);
		String ret = null;
		try {
			ServiceLocator locator = ServiceLocator.getInstance();

			KernelLocal kernel = locator.getKernelLocal();
			ServiceSessionLocal serviceSessionLocal = kernel
					.createServiceSession(this.asid, serviceId);
			ret = serviceSessionLocal.getSessionId();
			if (log.isDebugEnabled())
				log.debug("got ssid from kernel: " + ret);
			this.repository.put(serviceSessionLocal);
			log.debug("start the service");
			serviceSessionLocal.startService(isForwarded);
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AccessSessionException("problems with locator service");
		} catch (KernelException kex) {
			log.error("caught ex: " + kex.toString());
			throw new AccessSessionException(
					"Couldn't create a new service session");
		} catch (SessionException sex) {
			log.error("caught ex: " + sex.toString());
			throw new AccessSessionException(
					"Couldn't obtain the ssid of the new service session");
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
	public boolean stopServiceSession(String ssid)
			throws AccessSessionException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public boolean stopAllServiceSessions() throws AccessSessionException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public ServiceInfo[] listAvailableServices() throws AccessSessionException {
		// TODO Auto-generated method stub
		log.debug("-> starting processing ...");
		ServiceInfo[] ret = new ServiceInfo[0];

		log.debug("-> ... processing DONE!");
		return null;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public boolean stop() throws AccessSessionException {
		// TODO Auto-generated method stub
		log.debug("-> starting processing ...");
		boolean ret = false;
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			ret = kernel.deleteAccessSession(this.asid);
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AccessSessionException(
					"Couldn't get reference to the kernel!");
		} catch (KernelException kex) {
			log.debug("caught ex: " + kex.toString());
			throw new AccessSessionException(
					"removing the access session from kernel failed");
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
	public C_UserProfile getUserProfile() throws AccessSessionException {
		return this.userProfile;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public String getSessionId() throws SessionException {
		return this.asid;
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		log.debug("-> start processing ...");
		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			SimpleIdGeneratorLocal simpleIdGeneratorLocal = serviceLocator
					.getSimpleIdGeneratorLocal();
			this.asid = simpleIdGeneratorLocal.generateId();
		} catch (ServiceLocatorException slex) {
			throw new CreateException(slex.toString());
		}
		log.debug("-> ... processing DONE!");
	}

}