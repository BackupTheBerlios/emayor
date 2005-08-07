/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.interfaces.ServiceInfoEntityLocal;
import org.emayor.servicehandling.interfaces.ServiceInfoEntityLocalHome;
import org.emayor.servicehandling.kernel.AccessSessionInfo;
import org.emayor.servicehandling.kernel.AdminException;
import org.emayor.servicehandling.kernel.AdminServiceProfileData;
import org.emayor.servicehandling.kernel.IAdmin;
import org.emayor.servicehandling.kernel.IServiceProfile;
import org.emayor.servicehandling.kernel.IUserProfile;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.ServiceClassLoader;
import org.emayor.servicehandling.kernel.ServiceClassloaderException;
import org.emayor.servicehandling.kernel.ServiceInfo;
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
					Config.EMAYOR_ADMIN_INTERFACE_IS_ENABLED, "NO");
			this.isEnabled = str.equals("YES");
			str = config
					.getProperty(Config.EMAYOR_ADMIN_INTERFACE_USERID, "UNKNOWN");
			if (str == null || str.length() == 0 || str.equals("UNKNOWN")) {
				log
						.info("the admin uid is not set -> disable the admin interface at all");
				this.isEnabled = false;
			} else {
				log.debug("found the correct admin uid -> go on");
				this.uid = str;
			}
			str = config.getProperty(Config.EMAYOR_ADMIN_INTERFACE_PASSWORD,
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
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't get the profile data from kernel!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
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
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't get the profile data from kernel!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		}
		return ret;
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
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't get the profile data from kernel!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
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
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't get the access session from the kernel!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
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
		log.debug("-> start processing ...");
		AccessSessionInfo ret = new AccessSessionInfo();
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			log.debug("get the ref to the kernel");
			KernelLocal kernel = locator.getKernelLocal();
			ret = kernel.getAccessSessionInfo(asid);
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't get the access session from the kernel!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
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
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't get the service session from the kernel!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
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
		log.debug("-> start processing ...");
		ServiceSessionInfo ret = new ServiceSessionInfo();
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			log.debug("get the ref to the kernel");
			KernelLocal kernel = locator.getKernelLocal();
			ret = kernel.getServiceSessionInfo(ssid);
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't get the service session from the kernel!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
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
	public AdminServiceProfileData[] listDeployedServices()
			throws AdminException {
		log.debug("-> start processing ...");
		AdminServiceProfileData[] ret = new AdminServiceProfileData[0];
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			log.debug("get the ref to the kernel");
			KernelLocal kernel = locator.getKernelLocal();
			ServiceInfo[] infos = kernel.listAllAvailableServices();
			ret = new AdminServiceProfileData[infos.length];
			for (int i = 0; i < ret.length; i++) {
				ServiceInfo info = infos[i];
				ret[i] = new AdminServiceProfileData(info);
				String serviceId = info.getServiceId();
				ret[i].setNumberOfInstances(kernel
						.getNumberOfInstances(serviceId));
			}
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't get the service data from the kernel!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
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
	public AdminServiceProfileData lookupServiceProfile(String serviceId)
			throws AdminException {
		log.debug("-> start processing ...");
		AdminServiceProfileData ret = new AdminServiceProfileData();
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			log.debug("get the ref to the kernel");
			KernelLocal kernel = locator.getKernelLocal();
			IServiceProfile profile = kernel
					.getServiceProfileByServiceId(serviceId);
			ret = new AdminServiceProfileData(profile.getServiceInfo());
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't get the service data from the kernel!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
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
	public void changeServiceStatus(String serviceId, boolean active)
			throws AdminException {
		log.debug("-> start processing ...");
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			kernel.changeServiceStatus(serviceId, active);
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't change the status of the specified service!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		}
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void removeAccessSession(String asid) throws AdminException {
		log.debug("-> start processing ...");
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			kernel.deleteAccessSession(asid);
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't remove the specified access session!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		}
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void removeServiceSession(String ssid) throws AdminException {
		log.debug("-> start processing ...");
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			kernel.deleteServiceSession(null, ssid);
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't remove the specified service session!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		}
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void removeUserProfile(String uid) throws AdminException {
		log.debug("-> start processing ...");
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			kernel.removeUserProfile(uid);
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't connect to the kernel!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException(
					"Couldn't remove the specified user profile!");
		} catch (EJBException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal error!");
		}
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void deployNewService(AdminServiceProfileData serviceProfile,
			String serviceClassName, String serviceFactoryName)
			throws AdminException {
		log.debug("-> start processing ...");
		String sid = serviceProfile.getServiceId();
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			ServiceInfoEntityLocalHome home = locator
					.getServiceInfoEntityLocalHome();
			ServiceInfoEntityLocal local = null;
			try {
				local = home.findByPrimaryKey(sid);
			} catch (FinderException ex) {
				log
						.debug("the service with this is doesn't exist yet -> OK :-)");
				local = null;
			}
			if (local == null) {
				log.debug("storing service profile");
				serviceProfile.setServiceClassName(this
						.getClassFullName(serviceProfile.getServiceClass()));
				serviceProfile.setServiceFactoryClassName(this
						.getClassFullName(serviceProfile
								.getServiceFactoryClass()));
				serviceProfile.setServiceEndpoint("---");
				this.storeProfile(serviceProfile);
			} else {
				throw new AdminException("The service with id=" + sid
						+ " already exist!");
			}
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Internal locator error!");
		}
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void undeployService(String serviceId) throws AdminException {
		log.debug("-> start processing ...");
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			kernel.undeployService(serviceId);
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't undeploy the new service!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't undeploy the new service!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
		}
		log.debug("-> ... processing DONE!");
	}

	private String getClassFullName(byte[] bytes) throws AdminException {
		log.debug("-> start processing ...");
		String ret = null;
		try {
			ClassLoader _classLoader = this.getClass().getClassLoader();
			ServiceClassLoader loader = new ServiceClassLoader(_classLoader);
			Class clazz = loader.loadClass(bytes);
			ret = clazz.getName();
		} catch (ServiceClassloaderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't load the downloaded class!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}


	private void storeProfile(AdminServiceProfileData profile)
			throws AdminException {
		log.debug("-> start processing ...");
		IServiceProfile sp = new ServiceProfile();
		sp.setServiceInfo(profile);
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			KernelLocal kernel = locator.getKernelLocal();
			locator = null;
			kernel.deployService(sp);
			kernel.remove();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't deploy the new service!");
		} catch (KernelException ex) {
			log.error("caught ex: " + ex.toString());
			throw new AdminException("Couldn't deploy the new service!");
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
		}
		log.debug("-> ... processing DONE!");
	}
}