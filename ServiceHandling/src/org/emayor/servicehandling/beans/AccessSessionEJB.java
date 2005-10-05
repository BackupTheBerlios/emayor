/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.emayor.servicehandling.interfaces.AccessSessionEntityLocal;
import org.emayor.servicehandling.interfaces.AccessSessionEntityLocalHome;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.interfaces.SimpleIdGeneratorLocal;
import org.emayor.servicehandling.kernel.AccessSessionException;
import org.emayor.servicehandling.kernel.IAccessSession;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.ServiceInfo;
import org.emayor.servicehandling.kernel.ServiceSessionException;
import org.emayor.servicehandling.kernel.SessionException;
import org.emayor.servicehandling.utils.AccessSessionSSRepository;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @ejb.bean name="AccessSession" display-name="Name for AccessSession"
 *           description="Description for AccessSession"
 *           jndi-name="ejb/emayor/sh/AccessSession" type="Stateful"
 *           view-type="local"
 */
public class AccessSessionEJB implements SessionBean, IAccessSession {
	private static Logger log = Logger.getLogger(AccessSessionEJB.class);

	private AccessSessionEntityLocal accessSessionData;

	private SessionContext ctx;

	private AccessSessionSSRepository repository = null;

	/**
	 *  
	 */
	public AccessSessionEJB() {
		super();
		log.debug("-> start processing ...");
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
		log.debug("-> start processing ...");
		try {
			this.accessSessionData.remove();
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
		}
		log.debug("-> ... processing DONE!");
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
	 * @see IAccessSession#authenticateUser(X509Certificate[])
	 */
	public boolean authenticateUser(X509Certificate[] certificates)
			throws AccessSessionException {
		log.debug("-> start processing ...");
		boolean ret = false;
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			String str = kernel.authenticateUser(this.getSessionId(),
					certificates);
			if (str != null && str.length() != 0) {
				log.debug("the user has been authenticated!");
				//this.userId = str;
				this.setUserId(str);
				if (log.isDebugEnabled())
					log.debug("the user id = " + str);
				//this.isSessionActive = true;
				ret = true;
				ServiceSessionLocal[] serviceSessions = kernel
						.getUsersServiceSessions(str);
				log.debug("get the service sessions of current user!");
				for (int i = 0; i < serviceSessions.length; i++) {
					serviceSessions[i].setAccessSessionId(this.getSessionId());
					this.repository.put(serviceSessions[i]);
				}
			} else {
				log.debug("the user has NOT been authenticated!");
				ret = false;
			}
			kernel.remove();
		} catch (ServiceLocatorException slex) {
			log.error("caught ex: " + slex.toString());
			throw new AccessSessionException(
					"authenticateUser failed: Internal error - service locator problem");
		} catch (KernelException kex) {
			log.error("caught ex: " + kex.toString());
			throw new AccessSessionException(
					"authenticateUser failed: Internal error - kernel problem");
		} catch (RemoveException rex) {
			log.error("caught ex: " + rex.toString());
			throw new AccessSessionException(
					"authenticateUser failed: internal error");
		} catch (ServiceSessionException ssex) {
			log.error("caught ex: " + ssex.toString());
			throw new AccessSessionException(
					"authenticateUser failed: couldn't assign the new asid to running the service session");
		} catch (SessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AccessSessionException(
					"authenticateUser failed: Internal error - couldn't get the asid");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IAccessSession#getAllServiceSessions()
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
	 * @see IAccessSession#getServiceSession(String)
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
	 * @see IAccessSession#startServiceSession(String, boolean, String, String)
	 */
	public String startServiceSession(String serviceId, boolean isForwarded,
			String xmlDoc, String docSig) throws AccessSessionException {
		log.debug("-> start processing ...");
		if (log.isDebugEnabled())
			log.debug("input: serviceId=" + serviceId);
		String ret = null;
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			String userId = this.getUserId();
			ServiceSessionLocal serviceSessionLocal = kernel
					.createServiceSession(this.getSessionId(), serviceId,
							userId);
			kernel.remove();
			ret = serviceSessionLocal.getSessionId();
			if (log.isDebugEnabled())
				log.debug("got ssid from kernel: " + ret);
			this.repository.put(serviceSessionLocal);
			log.debug("start the service");
			serviceSessionLocal.startService(userId, isForwarded, xmlDoc,
					docSig);
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
		} catch (RemoveException rex) {
			log.error("caught ex: " + rex.toString());
			throw new AccessSessionException("internal error");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IAccessSession#startServiceSession(String, String)
	 */
	public String startServiceSession(String serviceId, String requestDocument)
			throws AccessSessionException {
		log.debug("-> start processing ...");
		if (log.isDebugEnabled())
			log.debug("input: serviceId=" + serviceId);
		String ret = null;
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			String userId = this.getUserId();
			ServiceSessionLocal serviceSessionLocal = kernel
					.createServiceSession(this.getSessionId(), serviceId,
							userId);
			kernel.remove();
			ret = serviceSessionLocal.getSessionId();
			if (log.isDebugEnabled())
				log.debug("got ssid from kernel: " + ret);
			this.repository.put(serviceSessionLocal);
			log.debug("start the service");
			serviceSessionLocal.startServiceRequestCompleted(userId, false,
					requestDocument, "</empty>");
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
		} catch (RemoveException rex) {
			log.error("caught ex: " + rex.toString());
			throw new AccessSessionException("internal error");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IAccessSession#stopServiceSession(String)
	 */
	public boolean stopServiceSession(String ssid)
			throws AccessSessionException {
		log.debug("-> start processing ...");
		boolean ret = false;
		try {
			log
					.debug("obtain the service session instance reference from local repository");
			ServiceSessionLocal serviceSession = this.getServiceSession(ssid);
			log.debug("remove service session instance from local repository");
			this.repository.remove(ssid);
			log.debug("en the service session");
			serviceSession.endService();
			log.debug("remove the service session ejb instance");
			serviceSession.remove();
			ret = true;
		} catch (ServiceSessionException ssex) {
			log.error("caught ex: " + ssex.toString());
			throw new AccessSessionException(
					"stopAccessSession failed: couldn't end the service session");
		} catch (RemoveException rex) {
			log.error("caucht ex: " + rex.toString());
			throw new AccessSessionException(
					"stopAccessSession failed: couldn't remove the instance of the service session");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IAccessSession#stopAllServiceSessions()
	 */
	public boolean stopAllServiceSessions() throws AccessSessionException {
		// TODO Auto-generated method stub
		throw new AccessSessionException("NOT IMPLEMENTED YET !!!!");
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IAccessSession#listAvailableServices()
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
	 * @see IAccessSession#stop()
	 */
	public boolean stop() throws AccessSessionException {
		log.debug("-> starting processing ...");
		boolean ret = false;
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			ret = kernel.deleteAccessSession(this.getSessionId());
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AccessSessionException(
					"Couldn't get reference to the kernel!");
		} catch (KernelException kex) {
			log.debug("caught ex: " + kex.toString());
			throw new AccessSessionException(
					"removing the access session from kernel failed");
		} catch (RemoveException rex) {
			log.error("caught ex: " + rex.toString());
			throw new AccessSessionException("internal error");
		} catch (SessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AccessSessionException(
					"Internal error - couldn't get the asid");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IAccessSession#getUserProfile()
	 */
	public C_UserProfile getUserProfile() throws AccessSessionException {
		throw new AccessSessionException("NOT IMPLEMENTED !!!");
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see org.emayor.servicehandling.kernel.ISession#getSessionId()
	 */
	public String getSessionId() throws SessionException {
		return this.accessSessionData.getAsid();
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
			String asid = simpleIdGeneratorLocal.generateId();
			simpleIdGeneratorLocal.remove();
			AccessSessionEntityLocalHome entHome = serviceLocator
					.getAccessSessionEntityLocalHome();
			//AccessSessionEntityLocal local = entHome.create(this.asid);
			this.accessSessionData = entHome.create(asid);
			Calendar cal = Calendar.getInstance();
			//this.startDate = cal.getTime();
			//local.setStartDate(cal.getTime());
			log.debug("setting the current date and time");
			this.accessSessionData.setStartDate(cal.getTime());
		} catch (ServiceLocatorException slex) {
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
	 * @see IAccessSession#getUserId()
	 */
	public String getUserId() throws AccessSessionException {
		log.debug("-> start processing ...");
		String ret = this.accessSessionData.getUserId();
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IAccessSession#setUserId(String)
	 */
	public void setUserId(String userId) throws AccessSessionException {
		log.debug("-> start processing ...");
		this.accessSessionData.setUserId(userId);
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see org.emayor.servicehandling.kernel.ISession#getStartDate()
	 */
	public Date getStartDate() throws SessionException {
		log.debug("-> start processing ...");
		Date ret = this.accessSessionData.getStartDate();
		log.debug("-> ... processing DONE!");
		return ret;
	}
}