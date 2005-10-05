/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.emayor.servicehandling.interfaces.AccessSessionEntityLocal;
import org.emayor.servicehandling.interfaces.AccessSessionEntityLocalHome;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.BPELCallbackDataEntityLocal;
import org.emayor.servicehandling.interfaces.BPELCallbackDataEntityLocalHome;
import org.emayor.servicehandling.interfaces.ServiceInfoEntityLocal;
import org.emayor.servicehandling.interfaces.ServiceInfoEntityLocalHome;
import org.emayor.servicehandling.interfaces.ServiceSessionBeanEntityLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionBeanEntityLocalHome;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.interfaces.UserProfileEntityLocal;
import org.emayor.servicehandling.interfaces.UserProfileEntityLocalHome;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardBPELCallbackData;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;
import org.xmlsoap.schemas.ws.addressing.MessageID;
import org.xmlsoap.schemas.ws.addressing.ReplyTo;

/**
 * @author tku
 */
public class KernelRepository {
	private static Logger log = Logger.getLogger(KernelRepository.class);

	///// ---- session management -----
	// asid -> accessSession
	private HashMap asid2accessSession;

	// ssid -> serviceSessionLocal
	private HashMap ssid2serviceSession;

	// service id -> factory
	private HashMap serviceId2serviceFactory;

	private ServiceInfoEntityLocalHome serviceInfoEntityLocalHome = null;

	private AccessSessionEntityLocalHome accessSessionEntityLocalHome = null;

	private ServiceSessionBeanEntityLocalHome serviceSessionBeanEntityLocalHome = null;

	private UserProfileEntityLocalHome userProfileEntityLocalHome = null;

	private BPELCallbackDataEntityLocalHome bpelCallbackDataEntityLocalHome = null;

	/**
	 * The constructor.
	 */
	public KernelRepository() {
		log.debug("-> start processing ...");
		this.asid2accessSession = new HashMap();
		this.ssid2serviceSession = new HashMap();
		this.serviceId2serviceFactory = new HashMap();
		try {
			ServiceLocator locator = ServiceLocator.getInstance();
			this.serviceInfoEntityLocalHome = locator
					.getServiceInfoEntityLocalHome();
			this.accessSessionEntityLocalHome = locator
					.getAccessSessionEntityLocalHome();
			this.serviceSessionBeanEntityLocalHome = locator
					.getServiceSessionBeanEntityLocalHome();
			this.userProfileEntityLocalHome = locator
					.getUserProfileEntityLocalHome();
			this.bpelCallbackDataEntityLocalHome = locator
					.getBPELCallbackDataEntityLocalHome();
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Adding an instance of Access Session into repository.
	 * 
	 * @param accessSession
	 *            the reference pointing to the instance of Access Session to be
	 *            put into repository
	 * @throws KernelRepositoryException
	 *             will be thrown in case the instance already exists in the
	 *             repository
	 */
	public void addAccessSession(AccessSessionLocal accessSession)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		try {
			String asid = accessSession.getSessionId();
			if (!this.asid2accessSession.containsKey(asid)) {
				this.asid2accessSession.put(asid, accessSession);
			} else {
				log.error("The asid " + asid
						+ " already exists in the repository!");
				throw new KernelRepositoryException(
						"The asid already exists in the repository!");
			}
		} catch (SessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't get the asid from session object!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param asid
	 * @throws KernelRepositoryException
	 */
	public void removeAccessSession(String asid)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (this.asid2accessSession.containsKey(asid)) {
			//String uid = (String) this.asid2userId.get(asid);
			this.asid2accessSession.remove(asid);
			//this.userId2asid.remove(uid);
			//this.asid2userId.remove(asid);
		} else {
			log.error("access session to be removed doesn't exist!");
			throw new KernelRepositoryException("Unknown asid: " + asid);
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param asid
	 * @return
	 * @throws KernelRepositoryException
	 */
	public AccessSessionLocal getAccessSession(String asid)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		AccessSessionLocal ret = null;
		if (this.asid2accessSession.containsKey(asid)) {
			ret = (AccessSessionLocal) this.asid2accessSession.get(asid);
		} else {
			log.error("try to get access session which doesn't exist!");
			throw new KernelRepositoryException("Unknown asid: " + asid);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Add a new service session into the repository.
	 * 
	 * @param serviceSession
	 *            service session
	 * @throws KernelRepositoryException
	 */
	public void addServiceSession(ServiceSessionLocal serviceSession,
			String userId) throws KernelRepositoryException {
		log.debug("-> start processing ...");
		try {
			String ssid = serviceSession.getSessionId();
			if (!this.ssid2serviceSession.containsKey(ssid)) {
				// ssid -> ServiceSessionLocal
				this.ssid2serviceSession.put(ssid, serviceSession);
				String serviceId = serviceSession.getServiceId();
				if (log.isDebugEnabled())
					log
							.debug("working with following service id: "
									+ serviceId);
				int i = this.getNumberOfServiceInstancesAsInt(serviceId);
				this.setNumberOfServiceInstances(serviceId, ++i);
				if (log.isDebugEnabled())
					log.debug("set the current number of instance to " + (i)
							+ " for serviceId = " + serviceId);
			} else {
				log.error("The ssid " + ssid
						+ " already exists in the repository!");
				throw new KernelRepositoryException(
						"The asid already exists in the repository!");
			}
		} catch (SessionException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't get the ssid from session object!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Remove the service session specified by the given service session id.
	 * 
	 * @param ssid
	 *            service session id
	 * @throws KernelRepositoryException
	 */
	public void removeServiceSession(String ssid, String userId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (log.isDebugEnabled())
			log.debug("working with following ssid: " + ssid);
		try {
			ServiceSessionBeanEntityLocal local = this.serviceSessionBeanEntityLocalHome
					.findByPrimaryKey(ssid);
			String serviceId = local.getServiceId();
			if (this.ssid2serviceSession.containsKey(ssid)) {
				this.ssid2serviceSession.remove(ssid);
				int i = this.getNumberOfServiceInstancesAsInt(serviceId);
				this.setNumberOfServiceInstances(serviceId, --i);
				if (log.isDebugEnabled())
					log.debug("set the current number of instance to " + i
							+ " for serviceId = " + serviceId);
			} else {
				log.error("service session to be removed doesn't exist!");
				throw new KernelRepositoryException(
						"removeServiceSession failed: Unknown ssid=" + ssid);
			}
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find a record for service session with id: "
							+ ssid);
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Removing of the exsisting Service Session allowed only to the admin.
	 * 
	 * @param ssid
	 *            the id of the Service Session to be removed
	 * @throws KernelRepositoryException
	 */
	public void adminRemoveServiceSession(String ssid)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		try {
			if (this.ssid2serviceSession.containsKey(ssid)) {
				ServiceSessionBeanEntityLocal local = this.serviceSessionBeanEntityLocalHome
						.findByPrimaryKey(ssid);
				String userId = local.getUserId();
				this.removeServiceSession(ssid, userId);
			} else {
				log.error("service session to be removed doesn't exist!");
				throw new KernelRepositoryException(
						"removeServiceSession failed: Unknown ssid=" + ssid);
			}
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find the service session record with id: " + ssid);
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @return
	 * @throws KernelRepositoryException
	 */
	public AccessSessionLocal[] getAllAccessSessions()
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		AccessSessionLocal[] ret = new AccessSessionLocal[this.asid2accessSession
				.keySet().size()];
		int index = 0;
		for (Iterator i = this.asid2accessSession.values().iterator(); i
				.hasNext();) {
			ret[index++] = (AccessSessionLocal) i.next();
		}
		if (log.isDebugEnabled())
			log.debug("found " + ret.length
					+ " access sessions in kernel repository");
		return ret;
	}

	/**
	 * Get service session specified by given service session id.
	 * 
	 * @param ssid
	 *            service session id to be obtained
	 * @return
	 * @throws KernelRepositoryException
	 */
	public ServiceSessionLocal getServiceSession(String ssid)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		ServiceSessionLocal ret = null;
		if (this.ssid2serviceSession.containsKey(ssid)) {
			ret = (ServiceSessionLocal) this.ssid2serviceSession.get(ssid);
		} else {
			log.error("trying to get service session, which doesen't exist!");
			throw new KernelRepositoryException("Unknown ssid: " + ssid);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * Get all service sessions belonging to the user specified by the given
	 * userId.
	 * 
	 * @param userId
	 * @return
	 * @throws KernelRepositoryException
	 */
	public ServiceSessionLocal[] getAllServiceSessions(String userId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		ServiceSessionLocal[] ret = new ServiceSessionLocal[0];
		try {
			if (log.isDebugEnabled())
				log.debug("find the ss's for userid = " + userId);
			Collection collection = this.serviceSessionBeanEntityLocalHome
					.findByCreatorID(userId);
			int index = 0;
			if (log.isDebugEnabled())
				log.debug("found " + collection.size() + " items");
			ret = new ServiceSessionLocal[collection.size()];
			for (Iterator i = collection.iterator(); i.hasNext();) {
				String ssid = ((ServiceSessionBeanEntityLocal) i.next())
						.getSsid();
				ret[index++] = (ServiceSessionLocal) this.ssid2serviceSession
						.get(ssid);
			}
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find the session data in the database");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @return
	 * @throws KernelRepositoryException
	 */
	public ServiceSessionLocal[] listAllServiceSessions()
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		ServiceSessionLocal[] ret = new ServiceSessionLocal[this.ssid2serviceSession
				.keySet().size()];
		int index = 0;
		for (Iterator i = this.ssid2serviceSession.values().iterator(); i
				.hasNext();) {
			ret[index++] = (ServiceSessionLocal) i.next();
		}
		if (log.isDebugEnabled())
			log.debug("found " + ret.length
					+ " service sessions in kernel repository");
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @param serviceInfo
	 * @throws KernelRepositoryException
	 */
	public void addServiceInfo(ServiceInfo serviceInfo)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		String id = serviceInfo.getServiceId();
		try {
			if (log.isDebugEnabled()) {
				log.debug("------ adding new service with info ------");
				log.debug("service id                : "
						+ serviceInfo.getServiceId());
				log.debug("service name              : "
						+ serviceInfo.getServiceName());
				log.debug("servie description        : "
						+ serviceInfo.getServiceDescription());
				log.debug("service class name        : "
						+ serviceInfo.getServiceClassName());
				log.debug("service factory class name: "
						+ serviceInfo.getServiceFactoryClassName());
				log.debug("service version           : "
						+ serviceInfo.getServiceVersion());
				log.debug("is service active?        : "
						+ serviceInfo.getActive());
			}
			if (log.isDebugEnabled())
				log.debug("create the new entitty bean for id: " + id);
			ServiceInfoEntityLocal si = this.serviceInfoEntityLocalHome
					.create(id);
			si.setServiceClassName(serviceInfo.getServiceClassName());
			si.setServiceDescription(serviceInfo.getServiceDescription());
			si.setServiceEndpoint(serviceInfo.getServiceEndpoint());
			si.setServiceFactoryClassName(serviceInfo
					.getServiceFactoryClassName());
			si.setServiceName(serviceInfo.getServiceName());
			si.setServiceVersion(serviceInfo.getServiceVersion());
			si.setActive(new Boolean(serviceInfo.isActive()));
			si.setInstances(new Integer(0));
			si.setServiceClass(serviceInfo.getServiceClass());
			si.setServiceFactoryClass(serviceInfo.getServiceFactoryClass());
			log.debug("ADDED THE NEW SERVICE INFO");
		} catch (CreateException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't store persistently the info about a new service");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param serviceId
	 * @throws KernelRepositoryException
	 */
	public void removeServiceInfo(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (serviceId == null || serviceId.length() == 0)
			throw new KernelRepositoryException("invalid service id");
		try {
			ServiceInfoEntityLocal si = this.serviceInfoEntityLocalHome
					.findByPrimaryKey(serviceId);
			si.remove();
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find the service info for id: " + serviceId);
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't remove the service info for id: " + serviceId);
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @throws KernelRepositoryException
	 */
	public void emptyServiceInfo() throws KernelRepositoryException {
		log.debug("-> start processing ...");
		this.serviceId2serviceFactory = new HashMap();
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param serviceId
	 * @return
	 * @throws KernelRepositoryException
	 */
	public ServiceInfo getServiceInfo(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (serviceId == null || serviceId.length() == 0)
			throw new KernelRepositoryException("invalid service id");
		ServiceInfo ret = null;
		try {
			ServiceInfoEntityLocal si = this.serviceInfoEntityLocalHome
					.findByPrimaryKey(serviceId);
			ret = new ServiceInfo();
			ret.setActive(si.getActive().booleanValue());
			ret.setServiceClassName(si.getServiceClassName());
			ret.setServiceDescription(si.getServiceDescription());
			ret.setServiceEndpoint(si.getServiceEndpoint());
			ret.setServiceFactoryClassName(si.getServiceFactoryClassName());
			ret.setServiceId(si.getServiceId());
			ret.setServiceName(si.getServiceName());
			ret.setServiceVersion(si.getServiceVersion());
			ret.setServiceClass(si.getServiceClass());
			ret.setServiceFactoryClass(si.getServiceFactoryClass());
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find the service info for id: " + serviceId);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @param serviceInfo
	 * @throws KernelRepositoryException
	 */
	public void changeServiceInfo(ServiceInfo serviceInfo)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (serviceInfo == null)
			throw new KernelRepositoryException(
					"Bad input data - reference was null");
		try {
			ServiceInfoEntityLocal si = this.serviceInfoEntityLocalHome
					.findByPrimaryKey(serviceInfo.getServiceId());
			si.setServiceClassName(serviceInfo.getServiceClassName());
			si.setServiceDescription(serviceInfo.getServiceDescription());
			si.setServiceEndpoint(serviceInfo.getServiceEndpoint());
			si.setServiceFactoryClassName(serviceInfo
					.getServiceFactoryClassName());
			si.setServiceName(serviceInfo.getServiceName());
			si.setServiceVersion(serviceInfo.getServiceVersion());
			si.setActive(new Boolean(serviceInfo.isActive()));
			// it is possible, that the service factory belonging to this
			// service
			// has changed, thus it is required to load it again next time
			// it is needed - removing current version from the repository
			if (this.serviceId2serviceFactory.containsKey(serviceInfo
					.getServiceId()))
				this.serviceId2serviceFactory
						.remove(serviceInfo.getServiceId());
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find the service info for id: "
							+ serviceInfo.getServiceId());
		}
		/*
		 * this.removeServiceInfo(id); this.addServiceInfo(serviceInfo);
		 */
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @return
	 * @throws KernelRepositoryException
	 */
	public ServiceInfo[] listServicesInfo() throws KernelRepositoryException {
		log.debug("-> start processing ...");
		ServiceInfo[] ret = new ServiceInfo[0];
		try {
			Collection collection = this.serviceInfoEntityLocalHome.findAll();
			ret = new ServiceInfo[collection.size()];
			if (log.isDebugEnabled())
				log.debug("got collection size = " + ret.length);
			int index = 0;
			for (Iterator i = collection.iterator(); i.hasNext();) {
				ServiceInfoEntityLocal si = (ServiceInfoEntityLocal) i.next();
				ServiceInfo s = new ServiceInfo();
				s.setActive(si.getActive().booleanValue());
				s.setServiceClassName(si.getServiceClassName());
				s.setServiceDescription(si.getServiceDescription());
				s.setServiceEndpoint(si.getServiceEndpoint());
				s.setServiceFactoryClassName(si.getServiceFactoryClassName());
				s.setServiceId(si.getServiceId());
				s.setServiceName(si.getServiceName());
				s.setServiceVersion(si.getServiceVersion());
				ret[index++] = s;
			}
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find the data into application servers database (FinderException");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @param serviceId
	 * @param serviceFactory
	 * @throws KernelRepositoryException
	 */
	public void addServiceFactory(String serviceId,
			IeMayorServiceFactory serviceFactory)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (serviceId == null || serviceId.length() == 0)
			throw new KernelRepositoryException("invalid service id");
		if (serviceFactory == null)
			throw new KernelRepositoryException("invalid factory reference");
		if (this.serviceId2serviceFactory.containsKey(serviceId)) {
			log.error("mapping already exists!");
			throw new KernelRepositoryException(
					"Couldn't add the factory to the repository - already exists there!");
		} else {
			if (log.isDebugEnabled())
				log.debug("add service factory for service id = " + serviceId);
			this.serviceId2serviceFactory.put(serviceId, serviceFactory);
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param serviceId
	 * @throws KernelRepositoryException
	 */
	public void removeServiceFactory(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (serviceId == null || serviceId.length() == 0)
			throw new KernelRepositoryException("invalid service id");
		if (this.serviceId2serviceFactory.containsKey(serviceId)) {
			if (log.isDebugEnabled())
				log.debug("removing the factory for the serviceId = "
						+ serviceId);
			this.serviceId2serviceFactory.remove(serviceId);
		} else {
			log.error("factory for specified service id doesn't exist!");
			throw new KernelRepositoryException(
					"factory for specified service id doesn't exist!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param serviceId
	 * @return
	 * @throws KernelRepositoryException
	 */
	public IeMayorServiceFactory getServiceFactory(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (log.isDebugEnabled())
			log.debug("try to get the factory for service with id: "
					+ serviceId);
		IeMayorServiceFactory ret = null;
		if (serviceId == null || serviceId.length() == 0)
			throw new KernelRepositoryException("invalid service id");
		if (this.serviceId2serviceFactory.containsKey(serviceId)) {
			log
					.debug("found an instance in the repository (cache) -> using this one");
			ret = (IeMayorServiceFactory) this.serviceId2serviceFactory
					.get(serviceId);
		} else {
			log
					.debug("factory instance doesn't exist in the repository -> loading one");
			ret = this.loadServiceFactory(serviceId);
			log.debug("save the loaded instance for later usage -> cache");
			this.serviceId2serviceFactory.put(serviceId, ret);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @param userProfile
	 * @throws KernelRepositoryException
	 */
	public void addUserProfile(IUserProfile userProfile)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		String id = userProfile.getUserId();
		try {
			UserProfileEntityLocal local = this.userProfileEntityLocalHome
					.create(id);
			local.setC_UserProfile(userProfile.getPEUserProfile()
					.F_getUserProfileasString());
		} catch (CreateException ex) {
			log.error("caught ex:" + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't create a profile for user with id: " + id);
		} catch (C_UserProfile.E_UserProfileException ex) {
			log.error("caught ex:" + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't get string representation of the C_UserProfile for use with id: "
							+ id);
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param userId
	 * @throws KernelRepositoryException
	 */
	public void removeUserProfile(String userId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (userId == null || userId.length() == 0)
			throw new KernelRepositoryException("invalid user id");
		try {
			UserProfileEntityLocal local = this.userProfileEntityLocalHome
					.findByPrimaryKey(userId);
			local.remove();
		} catch (FinderException ex) {
			log.error("caught ex:" + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find a record for user with id: " + userId);
		} catch (RemoveException ex) {
			log.error("caught ex:" + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't remove a record for user with id: " + userId);
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws KernelRepositoryException
	 */
	public IUserProfile getUserProfile(String userId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (userId == null || userId.length() == 0)
			throw new KernelRepositoryException("invalid user id");
		IUserProfile ret = null;
		try {
			UserProfileEntityLocal local = this.userProfileEntityLocalHome
					.findByPrimaryKey(userId);
			ret = new UserProfile();
			ret.setUserId(local.getUserId());
			ret.setPEUserProfile(new C_UserProfile(local.getC_UserProfile()));

		} catch (FinderException ex) {
			log.error("caught ex:" + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find a record for user with id: " + userId);
		} catch (C_UserProfile.E_UserProfileException ex) {
			log.error("caught ex:" + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't get C_UserProfile from string for use with id: "
							+ userId);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws KernelRepositoryException
	 */
	public boolean existUserProfile(String userId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		boolean ret = false;
		if (userId == null || userId.length() == 0)
			throw new KernelRepositoryException("invalid user id");
		try {
			UserProfileEntityLocal local = this.userProfileEntityLocalHome
					.findByPrimaryKey(userId);
			ret = true;
		} catch (FinderException ex) {
			ret = false;
		}
		return ret;
	}

	/**
	 * 
	 * @return
	 * @throws KernelRepositoryException
	 */
	public IUserProfile[] listKnownUserProfiles()
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		IUserProfile[] ret = new UserProfile[0];
		String uid = "";
		try {
			Collection collection = this.userProfileEntityLocalHome.findAll();
			if (log.isDebugEnabled())
				log.debug("found " + collection.size() + " items");
			ret = new UserProfile[collection.size()];
			if (log.isDebugEnabled())
				log.debug("the ret array length is " + ret.length);
			int index = 0;
			for (Iterator i = collection.iterator(); i.hasNext();) {
				UserProfileEntityLocal local = (UserProfileEntityLocal) i
						.next();
				uid = local.getUserId();
				if (log.isDebugEnabled())
					log.debug("got uid: " + uid);
				ret[index] = new UserProfile();
				ret[index].setUserId(uid);
				log.debug("trying to set the c_UserProfile");
				ret[index++].setPEUserProfile(new C_UserProfile(local
						.getC_UserProfile()));
			}
		} catch (FinderException ex) {
			log.error("caught ex:" + ex.toString());
			if (log.isDebugEnabled())
				ex.printStackTrace();
			throw new KernelRepositoryException(
					"Couldn't find the user profiles");
		} catch (C_UserProfile.E_UserProfileException ex) {
			log.error("caught ex:" + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't get C_UserProfile from string for use with id: "
							+ uid);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws KernelRepositoryException
	 */
	public String getAsidByUserId(String userId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		String ret = "";
		try {
			Collection collection = this.accessSessionEntityLocalHome
					.findByUserId(userId);
			int i = collection.size();
			if (log.isDebugEnabled())
				log.debug("size of the collection = " + i);
			if (i == 1) {
				Object[] array = collection.toArray();
				AccessSessionEntityLocal local = (AccessSessionEntityLocal) array[0];
				ret = local.getAsid();
				if (log.isDebugEnabled())
					log.debug("got the asid = " + ret);
			} else {
				// TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!
				throw new KernelRepositoryException(
						"ERROR! There are more than one access sessions for this user");
			}
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find access session record with user with id: "
							+ userId);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @param asid
	 * @return
	 * @throws KernelRepositoryException
	 */
	public String getUserIdByAsid(String asid) throws KernelRepositoryException {
		log.debug("-> start processing ...");
		String ret = "";
		try {
			AccessSessionEntityLocal local = this.accessSessionEntityLocalHome
					.findByPrimaryKey(asid);
			ret = local.getUserId();
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find access session record with asid: " + asid);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @param data
	 * @throws KernelRepositoryException
	 */
	public void addForwardBPELCallbackData(ForwardBPELCallbackData data)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		String ssid = data.getSsid();
		BPELCallbackDataEntityLocal local = null;
		try {
			local = this.bpelCallbackDataEntityLocalHome.findByPrimaryKey(ssid);
		} catch (FinderException ex) {
			log
					.debug("caught FinderException -> superb, it means the record doesn't exit yet!");
			local = null;
		}
		if (local == null) {
			try {
				local = this.bpelCallbackDataEntityLocalHome.create(ssid);
				local.setMessageId(data.getMessageID().toString());
				local.setUserId(data.getUid());
				local.setAddress(data.getReplyTo().getAddress());
				local.setPortType(data.getReplyTo().getPortType());
				local.setServiceName(data.getReplyTo().getServiceName());
			} catch (CreateException ex) {
				log.error("caught ex: " + ex.toString());
				throw new KernelRepositoryException(
						"Couldn't create bpel callback data record for ssid: "
								+ ssid);
			}
		} else {
			log.error("the record containing bpel callback data for ssid = "
					+ ssid + " already exist!");
			throw new KernelRepositoryException(
					"the record containing bpel callback data for ssid = "
							+ ssid + " already exist!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param ssid
	 * @return
	 * @throws KernelRepositoryException
	 */
	public ForwardBPELCallbackData getForwardBPELCallbackData(String ssid)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (log.isDebugEnabled())
			log.debug("working with following ssid: " + ssid);
		if (ssid == null || ssid.length() == 0)
			throw new KernelRepositoryException("invalid service session id");
		ForwardBPELCallbackData ret = null;
		try {
			BPELCallbackDataEntityLocal local = this.bpelCallbackDataEntityLocalHome
					.findByPrimaryKey(ssid);
			ret = new ForwardBPELCallbackData();
			ret.setSsid(local.getSsid());
			ret.setUid(local.getUserId());
			ret.setMessageID(new MessageID(local.getMessageId()));
			ret.setReplyTo(new ReplyTo(local.getAddress(), local.getPortType(),
					local.getServiceName()));
			local.remove();
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find the bpel callback data for ssid: " + ssid);
		} catch (RemoveException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't remove bpel callback data for ssid: " + ssid);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @param serviceId
	 * @throws KernelRepositoryException
	 */
	public void resetNumberOfServiceInstances(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		this.setNumberOfServiceInstances(serviceId, 0);
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @throws KernelRepositoryException
	 */
	public void resetNumberOfServiceInstances()
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		try {
			Collection collection = this.serviceInfoEntityLocalHome.findAll();
			for (Iterator i = collection.iterator(); i.hasNext();) {
				ServiceInfoEntityLocal local = (ServiceInfoEntityLocal) i
						.next();
				local.setInstances(new Integer(0));
			}
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't get the service info for all deployed services!");
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param serviceId
	 * @param number
	 * @throws KernelRepositoryException
	 */
	public void setNumberOfServiceInstances(String serviceId, int number)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		try {
			ServiceInfoEntityLocal local = this.serviceInfoEntityLocalHome
					.findByPrimaryKey(serviceId);
			local.setInstances(new Integer(number));
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find the servie info for id: " + serviceId);
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @param serviceId
	 * @return
	 * @throws KernelRepositoryException
	 */
	public String getNumberOfServiceInstances(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		String ret = "0";
		try {
			ServiceInfoEntityLocal local = this.serviceInfoEntityLocalHome
					.findByPrimaryKey(serviceId);
			ret = local.getInstances().toString();
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find the servie info for id: " + serviceId);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public int getNumberOfServiceInstancesAsInt(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		int ret = 0;
		try {
			ServiceInfoEntityLocal local = this.serviceInfoEntityLocalHome
					.findByPrimaryKey(serviceId);
			ret = local.getInstances().intValue();
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't find the servie info for id: " + serviceId);
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	/**
	 * 
	 * @return
	 * @throws KernelRepositoryException
	 */
	public HashMap getNumberOfServiceInstancesMap()
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		//return this.serviceId2NumberOfInstances;
		HashMap ret = new HashMap();
		try {
			Collection collection = this.serviceInfoEntityLocalHome.findAll();
			for (Iterator i = collection.iterator(); i.hasNext();) {
				ServiceInfoEntityLocal local = (ServiceInfoEntityLocal) i
						.next();
				ret.put(local.getServiceId(), local.getInstances().toString());
			}
		} catch (FinderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException(
					"Couldn't get the service info for all deployed services!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}

	public IeMayorServiceFactory loadServiceFactory(String serviceId)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		if (log.isDebugEnabled()) {
			log.debug("loading factory class for service with service id: "
					+ serviceId);
		}
		IeMayorServiceFactory ret = null;
		try {
			Class _class = null;
			log.debug("using the service class loader");
			ClassLoader _loader = this.getClass().getClassLoader();
			ServiceClassLoader loader = new ServiceClassLoader(_loader);
			_class = loader.loadServiceFactoryClass(serviceId);
			if (_class != null)
				log.debug("forName called successful - class NOT null");
			ret = (IeMayorServiceFactory) _class.newInstance();
			if (ret != null)
				log.debug("factory reference is NOT null");
			ret.setup();
		} catch (IllegalAccessException iaex) {
			log.error("caught ex: " + iaex.toString());
			throw new KernelRepositoryException(
					"illegal access to the class instance");
		} catch (InstantiationException iex) {
			log.error("caught ex: " + iex.toString());
			throw new KernelRepositoryException("cannot get an instance");
		} catch (eMayorServiceException emsex) {
			log.error("caught ex: " + emsex.toString());
			throw new KernelRepositoryException("factory setup failed");
		} catch (ServiceClassloaderException ex) {
			log.error("caught ex: " + ex.toString());
			throw new KernelRepositoryException("A class loader exception!");
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}