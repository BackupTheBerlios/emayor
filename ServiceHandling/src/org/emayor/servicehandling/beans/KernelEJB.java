/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.kernel.AccessSessionInfo;
import org.emayor.servicehandling.kernel.IKernel;
import org.emayor.servicehandling.kernel.IServiceProfile;
import org.emayor.servicehandling.kernel.IUserProfile;
import org.emayor.servicehandling.kernel.Kernel;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.ServiceInfo;
import org.emayor.servicehandling.kernel.ServiceSessionInfo;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardBPELCallbackData;

/**
 * @ejb.bean name="Kernel" display-name="Name for Kernel"
 *           description="Description for Kernel"
 *           jndi-name="ejb/emayor/sh/Kernel" type="Stateless" view-type="local"
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
		try {
			this.kernel = Kernel.getInstance();
		} catch (KernelException kex) {

		}
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
	 * @see IKernel#createAccessSession()
	 */
	public String createAccessSession() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.createAccessSession();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getAccessSession(String)
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
	 * @see IKernel#deleteAccessSession(String)
	 */
	public boolean deleteAccessSession(String asid) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.deleteAccessSession(asid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#createServiceSession(String, String, String)
	 */
	public ServiceSessionLocal createServiceSession(String asid,
			String serviceName, String userId) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.createServiceSession(asid, serviceName, userId);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getServiceSession(String)
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
	 * @see IKernel#deleteServiceSession(String, String)
	 */
	public boolean deleteServiceSession(String asid, String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.deleteServiceSession(asid, ssid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#listAvailableServices(C_UserProfile)
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
	 * @see IKernel#listAllAvailableServices()
	 */
	public ServiceInfo[] listAllAvailableServices() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.listAllAvailableServices();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#listAllActiveServices()
	 */
	public ServiceInfo[] listAllActiveServices() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.listAllActiveServices();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getUserIdByASID(String)
	 */
	public String getUserIdByASID(String asid) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getUserIdByASID(asid);
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {

	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getServiceClassNameByServiceName(String)
	 */
	public String getServiceClassNameByServiceName(String serviceName)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getServiceClassNameByServiceName(serviceName);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getUserProfile(String)
	 */
	public IUserProfile getUserProfile(String userId) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getUserProfile(userId);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getServiceProfile(String)
	 */
	public IServiceProfile getServiceProfile(String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getServiceProfile(ssid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getServiceProfileByServiceId(String)
	 */
	public IServiceProfile getServiceProfileByServiceId(String serviceId)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getServiceProfileByServiceId(serviceId);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#authenticateUser(String, X509Certificate[])
	 */
	public String authenticateUser(String asid, X509Certificate[] certificates)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.authenticateUser(asid, certificates);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getUsersServiceSessions(String)
	 */
	public ServiceSessionLocal[] getUsersServiceSessions(String userId)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getUsersServiceSessions(userId);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getAsidByUserID(String)
	 */
	public String getAsidByUserID(String userId) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getAsidByUserID(userId);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#addForwardBPELCallbackData(ForwardBPELCallbackData)
	 */
	public void addForwardBPELCallbackData(ForwardBPELCallbackData data)
			throws KernelException {
		log.debug("-> start processing ...");
		this.kernel.addForwardBPELCallbackData(data);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getForwardBPELCallbackData(String)
	 */
	public ForwardBPELCallbackData getForwardBPELCallbackData(String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getForwardBPELCallbackData(ssid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#reloadDeployedServices()
	 */
	public void reloadDeployedServices() throws KernelException {
		log.debug("-> start processing ...");
		this.kernel.reloadDeployedServices();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#listAccessSessions()
	 */
	public AccessSessionInfo[] listAccessSessions() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.listAccessSessions();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#listServiceSessions()
	 */
	public ServiceSessionInfo[] listServiceSessions() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.listServiceSessions();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#listServiceSessions(String)
	 */
	public ServiceSessionInfo[] listServiceSessions(String uid)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.listServiceSessions(uid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#listUserProfiles()
	 */
	public IUserProfile[] listUserProfiles() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.listUserProfiles();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#listLoggedInUsers()
	 */
	public IUserProfile[] listLoggedInUsers() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.listLoggedInUsers();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getNumberOfInstances(String)
	 */
	public String getNumberOfInstances(String serviceId) throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getNumberOfInstances(serviceId);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getNumberOfInstancesMap()
	 */
	public HashMap getNumberOfInstancesMap() throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getNumberOfInstancesMap();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#resetNumberOfInstances()
	 */
	public void resetNumberOfInstances() throws KernelException {
		log.debug("-> start processing ...");
		this.kernel.resetNumberOfInstances();
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#resetNumberOfInstances(String)
	 */
	public void resetNumberOfInstances(String serviceId) throws KernelException {
		log.debug("-> start processing ...");
		this.kernel.resetNumberOfInstances(serviceId);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getAccessSessionInfo(String)
	 */
	public AccessSessionInfo getAccessSessionInfo(String asid)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getAccessSessionInfo(asid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#getServiceSessionInfo(String)
	 */
	public ServiceSessionInfo getServiceSessionInfo(String ssid)
			throws KernelException {
		log.debug("-> start processing ...");
		return this.kernel.getServiceSessionInfo(ssid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#changeServiceStatus(String, boolean)
	 */
	public void changeServiceStatus(String serviceId, boolean active)
			throws KernelException {
		log.debug("-> start processing ...");
		this.kernel.changeServiceStatus(serviceId, active);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see IKernel#removeUserProfile(String)
	 */
	public void removeUserProfile(String uid) throws KernelException {
		log.debug("-> start processing ...");
		this.kernel.removeUserProfile(uid);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see org.emayor.servicehandling.kernel.IKernel#deployService(org.emayor.servicehandling.kernel.IServiceProfile)
	 */
	public void deployService(IServiceProfile serviceProfile)
			throws KernelException {
		log.debug("-> start processing ...");
		this.kernel.deployService(serviceProfile);
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 * @see org.emayor.servicehandling.kernel.IKernel#undeployService(java.lang.String)
	 */
	public void undeployService(String serviceId) throws KernelException {
		log.debug("-> start processing ...");
		this.kernel.undeployService(serviceId);
	}
}