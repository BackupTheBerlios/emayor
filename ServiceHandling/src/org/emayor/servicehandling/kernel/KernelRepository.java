/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardBPELCallbackData;

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
    private HashMap userId2ssids;

    ///// ---- service management -----
    // service id -> serviceInfo
    private HashMap serviceId2serviceInfo;

    // service id -> factory
    private HashMap serviceId2serviceFactory;

    // userdId -> UserProfile
    private HashMap userId2UserProfile;

    // userId -> asid
    private HashMap userId2asid;

    // asid -> userId
    private HashMap asid2userId;

    // ssid -> bpelCallbackData
    private HashMap ssid2bpelForwardCallbackData;

    /**
     *  
     */
    public KernelRepository() {
        log.debug("-> start processing ...");
        this.asid2accessSession = new HashMap();
        this.ssid2serviceSession = new HashMap();
        this.userId2ssids = new HashMap();
        this.serviceId2serviceInfo = new HashMap();
        this.serviceId2serviceFactory = new HashMap();
        this.userId2UserProfile = new HashMap();
        this.userId2asid = new HashMap();
        this.asid2userId = new HashMap();
        this.ssid2bpelForwardCallbackData = new HashMap();
        log.debug("-> ... processing DONE!");
    }

    public void addAccessSession(AccessSessionLocal accessSession)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        try {
            String asid = accessSession.getSessionId();
            String uid = accessSession.getUserId();
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

    public void removeAccessSession(String asid)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        if (this.asid2accessSession.containsKey(asid)) {
            String uid = (String) this.asid2userId.get(asid);
            this.asid2accessSession.remove(asid);
            this.userId2asid.remove(uid);
            this.asid2userId.remove(asid);
        } else {
            log.error("access session to be removed doesn't exist!");
            throw new KernelRepositoryException("Unknown asid: " + asid);
        }
        log.debug("-> ... processing DONE!");
    }

    public AccessSessionLocal getAccessSession(String asid)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        AccessSessionLocal ret = null;
        if (this.asid2accessSession.containsKey(asid)) {
            ret = (AccessSessionLocal) this.asid2accessSession.get(asid);
        } else {
            log.error("trying to get access session doesen't exist!");
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
                List ssids = new ArrayList();
                if (this.userId2ssids.containsKey(userId)) {
                    log.debug("the list of ssids already exist!");
                    ssids = (List) this.userId2ssids.get(userId);
                }
                if (ssids.contains(ssid)) {
                    log.error("there already exists such ssid in the list");
                    throw new KernelRepositoryException(
                            "addServiceSession failed: the ssid already exist in the list");
                } else {
                    log.debug("add the ssid to the list");
                    ssids.add(ssid);
                }
                this.userId2ssids.remove(userId);
                this.userId2ssids.put(userId, ssids);
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
        if (this.ssid2serviceSession.containsKey(ssid)) {
            if (this.userId2ssids.containsKey(userId)) {
                List ssids = (List) this.userId2ssids.get(userId);
                if (ssids.contains(ssid)) {
                    log
                            .debug("everything's OK -> removing session from data structures");
                    this.ssid2serviceSession.remove(ssid);
                    ssids.remove(ssid);
                    this.userId2ssids.remove(userId);
                    this.userId2ssids.put(userId, ssids);
                } else {
                    log.error("the ssid doesn't exist in the list");
                    throw new KernelRepositoryException(
                            "removeServiceSession failed: the ssid is not assigned to the given userId");
                }
            } else {
                log.error("current user doesn't have a list of ssids assigned");
                throw new KernelRepositoryException(
                        "removeServiceSession failed: given user id doesn't have a list of ssids assigned");
            }
        } else {
            log.error("service session to be removed doesn't exist!");
            throw new KernelRepositoryException(
                    "removeServiceSession failed: Unknown ssid=" + ssid);
        }
        log.debug("-> ... processing DONE!");
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
        if (this.userId2ssids.containsKey(userId)) {
            List ssids = (List) this.userId2ssids.get(userId);
            ret = new ServiceSessionLocal[ssids.size()];
            int index = 0;
            for (Iterator i = ssids.iterator(); i.hasNext();) {
                String ssid = (String) i.next();
                ret[index++] = this.getServiceSession(ssid);
            }
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public void addServiceInfo(ServiceInfo serviceInfo)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        String id = serviceInfo.getServiceId();
        if (this.serviceId2serviceInfo.containsKey(id)) {
            log.error("ServiceInfo already exist in the repository");
            throw new KernelRepositoryException(
                    "Couldn't add ServiceInfo into repository - already exists!");
        } else {
            this.serviceId2serviceInfo.put(id, serviceInfo);
        }
        log.debug("-> ... processing DONE!");
    }

    public void removeServiceInfo(String serviceId)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        if (serviceId == null || serviceId.length() == 0)
            throw new KernelRepositoryException("invalid service id");
        if (this.serviceId2serviceInfo.containsKey(serviceId)) {
            if (log.isDebugEnabled())
                log.debug("removing the info for the serviceId = " + serviceId);
            this.serviceId2serviceInfo.remove(serviceId);
        } else {
            log.error("info for specified service id doesn't exist!");
            throw new KernelRepositoryException(
                    "info for specified service id doesn't exist!");
        }
        log.debug("-> ... processing DONE!");
    }

    public ServiceInfo getServiceInfo(String serviceId)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        if (serviceId == null || serviceId.length() == 0)
            throw new KernelRepositoryException("invalid service id");
        ServiceInfo ret = null;
        if (this.serviceId2serviceInfo.containsKey(serviceId)) {
            log.debug("found the right info");
            ret = (ServiceInfo) this.serviceId2serviceInfo.get(serviceId);
        } else {
            log.error("info for specified service id doesn't exist!");
            throw new KernelRepositoryException(
                    "info for specified service id doesn't exist!");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public ServiceInfo[] listServicesInfo() throws KernelRepositoryException {
        log.debug("-> start processing ...");
        ServiceInfo[] ret = null;
        ret = new ServiceInfo[this.serviceId2serviceInfo.size()];
        if (log.isDebugEnabled())
            log.debug("found " + ret.length + " items!");
        int index = 0;
        for (Iterator i = this.serviceId2serviceInfo.values().iterator(); i
                .hasNext();) {
            if (log.isDebugEnabled())
                log.debug("working with index = " + index);
            ret[index++] = (ServiceInfo) i.next();
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

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
            this.serviceId2serviceFactory.put(serviceId, serviceFactory);
        }
        log.debug("-> ... processing DONE!");
    }

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

    public IeMayorServiceFactory getServiceFactory(String serviceId)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        IeMayorServiceFactory ret = null;
        if (serviceId == null || serviceId.length() == 0)
            throw new KernelRepositoryException("invalid service id");
        if (this.serviceId2serviceFactory.containsKey(serviceId)) {
            ret = (IeMayorServiceFactory) this.serviceId2serviceFactory
                    .get(serviceId);
        } else {
            log.error("factory for specified service id doesn't exist!");
            throw new KernelRepositoryException(
                    "factory for specified service id doesn't exist!");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public void addUserProfile(IUserProfile userProfile)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        String id = userProfile.getUserId();
        if (this.userId2UserProfile.containsKey(id)) {
            log.error("UserProfile already exist in the repository");
            throw new KernelRepositoryException(
                    "Couldn't add UserProfile into repository - already exists!");
        } else {
            this.userId2UserProfile.put(id, userProfile);
        }
        log.debug("-> ... processing DONE!");
    }

    public void removeUserProfile(String userId)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        if (userId == null || userId.length() == 0)
            throw new KernelRepositoryException("invalid user id");
        if (this.userId2UserProfile.containsKey(userId)) {
            if (log.isDebugEnabled())
                log.debug("removing the info for the userId = " + userId);
            this.userId2UserProfile.remove(userId);
        } else {
            log.error("info for specified user id doesn't exist!");
            throw new KernelRepositoryException(
                    "info for specified user id doesn't exist!");
        }
        log.debug("-> ... processing DONE!");
    }

    public IUserProfile getUserProfile(String userId)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        if (userId == null || userId.length() == 0)
            throw new KernelRepositoryException("invalid user id");
        IUserProfile ret = null;
        if (this.userId2UserProfile.containsKey(userId)) {
            log.debug("found the right info");
            ret = (IUserProfile) this.userId2UserProfile.get(userId);
        } else {
            log.error("info for specified user id doesn't exist!");
            throw new KernelRepositoryException(
                    "info for specified user id doesn't exist!");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public boolean existUserProfile(String userId)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        boolean ret = false;
        if (userId == null || userId.length() == 0)
            throw new KernelRepositoryException("invalid user id");
        ret = this.userId2UserProfile.containsKey(userId);
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public void updateAccessSessionData(String userId, String asid) {
        this.userId2asid.put(userId, asid);
        this.asid2userId.put(asid, userId);
    }

    public String getAsidByUserId(String userId)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        String ret = "";
        if (this.userId2asid.containsKey(userId)) {
            ret = (String) this.userId2asid.get(userId);
        } else {
            throw new KernelRepositoryException(
                    "mapping to asid for specified user id doesn't exist");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public String getUserIdByAsid(String asid) throws KernelRepositoryException {
        log.debug("-> start processing ...");
        String ret = "";
        if (this.asid2userId.containsKey(asid)) {
            ret = (String) this.asid2userId.get(asid);
        } else {
            throw new KernelRepositoryException(
                    "mapping to user id for specified asid doesn't exist");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }

    public void addForwardBPELCallbackData(ForwardBPELCallbackData data)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        String ssid = data.getSsid();
        if (this.ssid2bpelForwardCallbackData.containsKey(ssid)) {
            log.error("Callback data already exists in the repository");
            throw new KernelRepositoryException(
                    "Couldn't add data into repository - already exists!");
        } else {
        	if (log.isDebugEnabled())
        		log.debug("add callbackdata for ssid = " + ssid);
            this.ssid2bpelForwardCallbackData.put(ssid, data);
        }
        log.debug("-> ... processing DONE!");
    }

    public ForwardBPELCallbackData getForwardBPELCallbackData(String ssid)
            throws KernelRepositoryException {
        log.debug("-> start processing ...");
        if (log.isDebugEnabled())
            log.debug("working with following ssid: " + ssid);
        if (ssid == null || ssid.length() == 0)
            throw new KernelRepositoryException("invalid service session id");
        ForwardBPELCallbackData ret = null;
        if (this.ssid2bpelForwardCallbackData.containsKey(ssid)) {
            log.debug("found the right data");
            ret = (ForwardBPELCallbackData) this.ssid2bpelForwardCallbackData
                    .get(ssid);
            this.ssid2bpelForwardCallbackData.remove(ssid);
        } else {
            log.error("data record for specified ssid doesn't exist!");
            throw new KernelRepositoryException(
                    "data record for specified ssid doesn't exist!");
        }
        log.debug("-> ... processing DONE!");
        return ret;
    }
}