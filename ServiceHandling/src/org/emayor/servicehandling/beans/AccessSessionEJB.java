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
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocal;
import org.emayor.servicehandling.kernel.AccessSessionException;
import org.emayor.servicehandling.kernel.IAccessSession;
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

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
		return false;
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
	public String startServiceSession(String serviceName)
			throws AccessSessionException {
		log.debug("-> start processing ...");
		if (log.isDebugEnabled())
			log.debug("startServiceSession -> input: serviceName="
					+ serviceName);
		String ret = null;
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			/*
			 * KernelLocal kernel = locator.getKernelLocal();
			 * ServiceSessionLocal serviceSessionLocal = kernel
			 * .createServiceSession(this.accessSessionId, serviceName);
			 
			ret = serviceSessionLocal.getSessionId();
			if (log.isDebugEnabled())
				log
						.debug("startServiceSession -> got ssid from kernel: "
								+ ret);
			this.serviceSessionsIds.put(serviceName, ret);
			this.serviceSessions.put(ret, serviceSessionLocal);
			*/
		} catch (Exception ex) {
			log.error("-> caught ex: " + ex.toString());
			throw new AccessSessionException();
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
		
		log.debug("-> ... processing DONE!");
		return false;
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