/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.security.cert.X509Certificate;
import java.util.HashMap;

import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardBPELCallbackData;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public interface IKernel {
    /**
     * 
     * @return
     * @throws KernelException
     */
    public String createAccessSession() throws KernelException;

    /**
     * 
     * @param asid
     * @return
     * @throws KernelException
     */
    public AccessSessionLocal getAccessSession(String asid)
            throws KernelException;

    /**
     * 
     * @return
     * @throws KernelException
     */
    public AccessSessionInfo[] listAccessSessions() throws KernelException;

    /**
     * 
     * @param asid
     * @return
     * @throws KernelException
     */
    public AccessSessionInfo getAccessSessionInfo(String asid)
            throws KernelException;

    /**
     * Removes the specified instance of the access session.
     * 
     * @param asid
     *            access session id to be deleted
     * @return true if the the session has been deleted successful, otherwise
     *         false
     * @throws KernelException
     */
    public boolean deleteAccessSession(String asid) throws KernelException;

    /**
     * Creates a new instance of service session.
     * 
     * @param asid
     *            the id of the access session is abot to create this service
     *            session
     * @param serviceName
     *            the service id
     * @return true if the the session has been deleted successful, otherwise
     *         false
     * @throws KernelException
     */
    public ServiceSessionLocal createServiceSession(String asid,
            String serviceName, String userId) throws KernelException;

    /**
     * 
     * @param ssid
     * @return
     * @throws KernelException
     */
    public ServiceSessionLocal getServiceSession(String ssid)
            throws KernelException;

    /**
     * 
     * @param asid
     *            current access session id
     * @param ssid
     *            service session id
     * @return
     * @throws KernelException
     */
    public boolean deleteServiceSession(String asid, String ssid)
            throws KernelException;

    /**
     * 
     * @return
     * @throws KernelException
     */
    public ServiceSessionInfo[] listServiceSessions() throws KernelException;

    /**
     * 
     * @param ssid
     * @return
     * @throws KernelException
     */
    public ServiceSessionInfo getServiceSessionInfo(String ssid)
            throws KernelException;

    /**
     * 
     * @param uid
     * @return
     * @throws KernelException
     */
    public ServiceSessionInfo[] listServiceSessions(String uid)
            throws KernelException;

    /**
     * Get all runing service sessions started by given user.
     * 
     * @param userId
     * @return
     * @throws KernelException
     */
    public ServiceSessionLocal[] getUsersServiceSessions(String userId)
            throws KernelException;

    /*
     * public boolean checkAuthorization(I_Credentials credentials) throws
     * KernelException;
     */

    /**
     * 
     * @param userProfile
     * @return
     * @throws KernelException
     */
    public ServiceInfo[] listAvailableServices(C_UserProfile userProfile)
            throws KernelException;

    /**
     * 
     * @return
     * @throws KernelException
     */
    public ServiceInfo[] listAllAvailableServices() throws KernelException;

    /**
     * 
     * @param asid
     * @return
     * @throws KernelException
     */
    public String getUserIdByASID(String asid) throws KernelException;

    /**
     * 
     * @param userId
     * @return
     * @throws KernelException
     */
    public String getAsidByUserID(String userId) throws KernelException;

    /**
     * 
     * @param userId
     * @return
     * @throws KernelException
     */
    public IUserProfile getUserProfile(String userId) throws KernelException;

    /**
     * 
     * @return
     * @throws KernelException
     */
    public IUserProfile[] listUserProfiles() throws KernelException;

    /**
     * 
     * @return
     * @throws KernelException
     */
    public IUserProfile[] listLoggedInUsers() throws KernelException;

    /**
     * 
     * @param certificates
     * @return system unique user id
     * @throws KernelException
     */
    public String authenticateUser(String asid, X509Certificate[] certificates)
            throws KernelException;

    /**
     * 
     * @param ssid
     * @return
     * @throws KernelException
     */
    public IServiceProfile getServiceProfile(String ssid)
            throws KernelException;

    /**
     * 
     * @param serviceId
     * @return
     * @throws KernelException
     */
    public IServiceProfile getServiceProfileByServiceId(String serviceId)
            throws KernelException;

    /**
     * 
     * @param serviceName
     * @return
     * @throws KernelException
     */
    public String getServiceClassNameByServiceName(String serviceName)
            throws KernelException;

    /**
     * 
     * @param data
     * @throws KernelException
     */
    public void addForwardBPELCallbackData(ForwardBPELCallbackData data)
            throws KernelException;

    /**
     * It means get and remove!
     * 
     * @param ssid
     * @return
     * @throws KernelException
     */
    public ForwardBPELCallbackData getForwardBPELCallbackData(String ssid)
            throws KernelException;

    /**
     * 
     * @throws KernelException
     */
    public void reloadDeployedServices() throws KernelException;

    /**
     * 
     * @param serviceId
     * @return
     * @throws KernelException
     */
    public String getNumberOfInstances(String serviceId) throws KernelException;

    /**
     * 
     * @throws KernelException
     */
    public void resetNumberOfInstances() throws KernelException;

    /**
     * 
     * @param serviceId
     * @throws KernelException
     */
    public void resetNumberOfInstances(String serviceId) throws KernelException;

    /**
     * 
     * @return
     * @throws KernelException
     */
    public HashMap getNumberOfInstancesMap() throws KernelException;

    /**
     * 
     * @param serviceId
     * @param active
     * @throws KernelException
     */
    public void changeServiceStatus(String serviceId, boolean active)
            throws KernelException;

    /**
     * 
     * @param uid
     * @throws KernelException
     */
    public void removeUserProfile(String uid) throws KernelException;

}