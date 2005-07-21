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

	// userID -> set{ssid1, ssid2, ssid3 ...}
	//private HashMap userId2ssids;

	///// ---- service management -----
	// service id -> serviceInfo
	//private HashMap serviceId2serviceInfo;

	// service id -> factory
	private HashMap serviceId2serviceFactory;

	// serviceId -> number of runing instances
	//private HashMap serviceId2NumberOfInstances;

	// userdId -> UserProfile
	//private HashMap userId2UserProfile;

	// userId -> asid
	//private HashMap userId2asid;

	// asid -> userId
	//private HashMap asid2userId;

	// ssid -> userId
	//private HashMap ssid2userId;

	// ssid -> bpelCallbackData
	//private HashMap ssid2bpelForwardCallbackData;

	// ssid -> service id
	//private HashMap ssid2serviceId;

	private ServiceInfoEntityLocalHome serviceInfoEntityLocalHome = null;

	private AccessSessionEntityLocalHome accessSessionEntityLocalHome = null;

	private ServiceSessionBeanEntityLocalHome serviceSessionBeanEntityLocalHome = null;

	private UserProfileEntityLocalHome userProfileEntityLocalHome = null;

	private BPELCallbackDataEntityLocalHome bpelCallbackDataEntityLocalHome = null;

	/**
	 *  
	 */
	public KernelRepository() {
		log.debug("-> start processing ...");
		this.asid2accessSession = new HashMap();
		this.ssid2serviceSession = new HashMap();
		//this.userId2ssids = new HashMap();
		//this.serviceId2serviceInfo = new HashMap();
		this.serviceId2serviceFactory = new HashMap();
		//this.serviceId2NumberOfInstances = new HashMap();
		//this.userId2UserProfile = new HashMap();
		//this.userId2asid = new HashMap();
		//this.asid2userId = new HashMap();
		//this.ssid2bpelForwardCallbackData = new HashMap();
		//this.ssid2userId = new HashMap();
		//this.ssid2serviceId = new HashMap();
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
	 * 
	 * @param accessSession
	 * @throws KernelRepositoryException
	 */
	public void addAccessSession(AccessSessionLocal accessSession)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		try {
			String asid = accessSession.getSessionId();
			//String uid = accessSession.getUserId();
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
				// userId -> {ssid1, ssid2, ...}
				/*
				 * List ssids = new ArrayList(); if
				 * (this.userId2ssids.containsKey(userId)) { log.debug("the list
				 * of ssids already exist!"); ssids = (List)
				 * this.userId2ssids.get(userId); } if (ssids.contains(ssid)) {
				 * log.error("there already exists such ssid in the list");
				 * throw new KernelRepositoryException( "addServiceSession
				 * failed: the ssid already exist in the list"); } else {
				 * log.debug("add the ssid to the list"); ssids.add(ssid); }
				 * this.userId2ssids.remove(userId);
				 * this.userId2ssids.put(userId, ssids);
				 */
				//this.ssid2userId.put(ssid, userId);
				String serviceId = serviceSession.getServiceId();
				if (log.isDebugEnabled())
					log
							.debug("working with following service id: "
									+ serviceId);
				//this.ssid2serviceId.put(ssid, serviceId);

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
				/*
				 * if (this.userId2ssids.containsKey(userId)) { List ssids =
				 * (List) this.userId2ssids.get(userId); if
				 * (ssids.contains(ssid)) { log .debug("everything's OK ->
				 * removing session from data structures"); String serviceId =
				 * (String) this.ssid2serviceId.get(ssid);
				 * this.ssid2serviceId.remove(ssid); if (log.isDebugEnabled())
				 * log.debug("working with following service id: " + serviceId);
				 * int i = this.getNumberOfServiceInstancesAsInt(serviceId);
				 * this.setNumberOfServiceInstances(serviceId, --i); if
				 * (log.isDebugEnabled()) log.debug("set the current number of
				 * instance to " + i + " for serviceId = " + serviceId);
				 * log.debug("clear the ssid2serviceSession");
				 * this.ssid2serviceSession.remove(ssid); ssids.remove(ssid);
				 * log.debug("clear the userId2ssids");
				 * this.userId2ssids.remove(userId);
				 * this.userId2ssids.put(userId, ssids); log.debug("clear the
				 * ssid2userId"); //this.ssid2userId.remove(ssid); } else {
				 * log.error("the ssid doesn't exist in the list"); throw new
				 * KernelRepositoryException( "removeServiceSession failed: the
				 * ssid is not assigned to the given userId"); } } else {
				 * log.error("current user doesn't have a list of ssids
				 * assigned"); throw new KernelRepositoryException(
				 * "removeServiceSession failed: given user id doesn't have a
				 * list of ssids assigned"); }
				 */
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
	 * 
	 * @param ssid
	 * @throws KernelRepositoryException
	 */
	public void adminRemoveServiceSession(String ssid)
			throws KernelRepositoryException {
		log.debug("-> start processing ...");
		try {
			if (this.ssid2serviceSession.containsKey(ssid)) {
				//String userId = (String) this.ssid2userId.get(ssid);
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
		/*
		 * if (this.userId2ssids.containsKey(userId)) { List ssids = (List)
		 * this.userId2ssids.get(userId); ret = new
		 * ServiceSessionLocal[ssids.size()]; int index = 0; for (Iterator i =
		 * ssids.iterator(); i.hasNext();) { String ssid = (String) i.next();
		 * ret[index++] = this.getServiceSession(ssid); } }
		 */
		try {
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
		/*
		 * if (serviceId == null || serviceId.length() == 0) throw new
		 * KernelRepositoryException("invalid service id"); if
		 * (this.serviceId2serviceInfo.containsKey(serviceId)) { if
		 * (log.isDebugEnabled()) log.debug("removing the info for the serviceId = " +
		 * serviceId); this.serviceId2serviceInfo.remove(serviceId);
		 * this.serviceId2NumberOfInstances.remove(serviceId); } else {
		 * log.error("info for specified service id doesn't exist!"); throw new
		 * KernelRepositoryException( "info for specified service id doesn't
		 * exist!"); }
		 */
		log.debug("-> ... processing DONE!");
	}

	/**
	 * 
	 * @throws KernelRepositoryException
	 */
	public void emptyServiceInfo() throws KernelRepositoryException {
		log.debug("-> start processing ...");
		//this.serviceId2serviceInfo = new HashMap();
		//this.serviceId2NumberOfInstances = new HashMap();
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
		/*
		 * if (this.serviceId2serviceInfo.containsKey(serviceId)) {
		 * log.debug("found the right info"); ret = (ServiceInfo)
		 * this.serviceId2serviceInfo.get(serviceId); } else { log.error("info
		 * for specified service id doesn't exist!"); throw new
		 * KernelRepositoryException( "info for specified service id doesn't
		 * exist!"); }
		 */
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
				//log.debug("type = " + i.next().getClass().getName());
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
		}
		/*
		 * ret = new ServiceInfo[this.serviceId2serviceInfo.size()]; if
		 * (log.isDebugEnabled()) log.debug("found " + ret.length + " items!");
		 * int index = 0; for (Iterator i =
		 * this.serviceId2serviceInfo.values().iterator(); i .hasNext();) { if
		 * (log.isDebugEnabled()) log.debug("working with index = " + index);
		 * ret[index++] = (ServiceInfo) i.next(); }
		 */
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
		/*
		 * if (this.userId2UserProfile.containsKey(id)) { log.error("UserProfile
		 * already exist in the repository"); throw new
		 * KernelRepositoryException( "Couldn't add UserProfile into repository -
		 * already exists!"); } else { this.userId2UserProfile.put(id,
		 * userProfile); }
		 */
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
		/*
		 * if (this.userId2UserProfile.containsKey(userId)) { if
		 * (log.isDebugEnabled()) log.debug("removing the info for the userId = " +
		 * userId); this.userId2UserProfile.remove(userId); } else {
		 * log.error("info for specified user id doesn't exist!"); throw new
		 * KernelRepositoryException( "info for specified user id doesn't
		 * exist!"); }
		 */
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
		/*
		 * if (this.userId2UserProfile.containsKey(userId)) { log.debug("found
		 * the right info"); ret = (IUserProfile)
		 * this.userId2UserProfile.get(userId); } else { log.error("info for
		 * specified user id doesn't exist!"); throw new
		 * KernelRepositoryException( "info for specified user id doesn't
		 * exist!"); }
		 */
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
		/*
		 * ret = this.userId2UserProfile.containsKey(userId); log.debug("-> ...
		 * processing DONE!");
		 */
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
		/*
		 * IUserProfile[] ret = new
		 * IUserProfile[this.userId2UserProfile.keySet() .size()]; int index =
		 * 0; for (Iterator i = this.userId2UserProfile.values().iterator(); i
		 * .hasNext();) { ret[index++] = (IUserProfile) i.next(); }
		 */
		IUserProfile[] ret = new UserProfile[0];
		String uid = "";
		try {
			Collection collection = this.userProfileEntityLocalHome.findAll();
			if (log.isDebugEnabled())
				log.debug("found " + collection.size() + " items");
			ret = new UserProfile[collection.size()];
			int index = 0;
			for (Iterator i = collection.iterator(); i.hasNext();) {
				UserProfileEntityLocal local = (UserProfileEntityLocal) i
						.next();
				uid = local.getUserId();
				ret[index].setUserId(uid);
				ret[++index].setPEUserProfile(new C_UserProfile(local
						.getC_UserProfile()));
			}
		} catch (FinderException ex) {
			log.error("caught ex:" + ex.toString());
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
	 * @param asid
	 */
	/*
	 * public void updateAccessSessionData(String userId, String asid) {
	 * this.userId2asid.put(userId, asid); this.asid2userId.put(asid, userId); }
	 */

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
		/*
		 * if (this.userId2asid.containsKey(userId)) { ret = (String)
		 * this.userId2asid.get(userId); } else { throw new
		 * KernelRepositoryException( "mapping to asid for specified user id
		 * doesn't exist"); }
		 */
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
		/*
		 * if (this.asid2userId.containsKey(asid)) { ret = (String)
		 * this.asid2userId.get(asid); } else { throw new
		 * KernelRepositoryException( "mapping to user id for specified asid
		 * doesn't exist"); }
		 */
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
		/*
		 * if (this.ssid2bpelForwardCallbackData.containsKey(ssid)) {
		 * log.error("Callback data already exists in the repository"); throw
		 * new KernelRepositoryException( "Couldn't add data into repository -
		 * already exists!"); } else { if (log.isDebugEnabled()) log.debug("add
		 * callbackdata for ssid = " + ssid);
		 * this.ssid2bpelForwardCallbackData.put(ssid, data); }
		 */
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
		/*
		 * if (this.ssid2bpelForwardCallbackData.containsKey(ssid)) {
		 * log.debug("found the right data"); ret = (ForwardBPELCallbackData)
		 * this.ssid2bpelForwardCallbackData .get(ssid);
		 * this.ssid2bpelForwardCallbackData.remove(ssid); } else {
		 * log.error("data record for specified ssid doesn't exist!"); throw new
		 * KernelRepositoryException( "data record for specified ssid doesn't
		 * exist!"); }
		 */
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
		/*
		 * for (Iterator i =
		 * this.serviceId2NumberOfInstances.keySet().iterator(); i .hasNext();) {
		 * String sid = (String) i.next();
		 * this.serviceId2NumberOfInstances.put(sid, new Integer(0)); }
		 */
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
		/*
		 * if (this.serviceId2NumberOfInstances.containsKey(serviceId)) {
		 * Integer i = (Integer) this.serviceId2NumberOfInstances
		 * .get(serviceId); ret = i.toString(); } else { log.error("UNKNOWN
		 * SERVICE ID: " + serviceId); throw new
		 * KernelRepositoryException("Unknown service id = " + serviceId); }
		 * log.debug("-> ... processing DONE!");
		 */
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
			/*
			 * if (clazzName
			 * .equals("org.emayor.servicehandling.kernel.eMayorServiceFactory")) {
			 * log .debug("using the default factory -> so the def class loader
			 * as well"); _class = Class.forName(clazzName); } else {
			 */
			log.debug("using the service class loader");
			ClassLoader _loader = this.getClass().getClassLoader();
			ServiceClassLoader loader = new ServiceClassLoader(_loader);
			_class = loader.loadServiceFactoryClass(serviceId);
			//}
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