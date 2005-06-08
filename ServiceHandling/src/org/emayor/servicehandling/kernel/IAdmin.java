/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public interface IAdmin {

	/**
	 * Check whether the admin interface is enabled or not.
	 * 
	 * @return true if the admin interface is enabled, otherwise false
	 * @throws AdminException
	 */
	public boolean isEnabled() throws AdminException;

	/**
	 * Admin login.
	 * 
	 * @param uid
	 *            admin user id
	 * @param pswd
	 *            admin password
	 * @return true if the inputed credentials are correct, otherwise false
	 * @throws AdminException
	 */
	public boolean login(String uid, String pswd) throws AdminException;

	/**
	 * Raloads the service profiles.
	 * 
	 * @throws AdminException
	 */
	public void reloadServices() throws AdminException;

	/**
	 * Reload the platform's configuration stored in the <it>emayor.properties
	 * </it> file.
	 * 
	 * @throws AdminException
	 */
	public void reloadConfiguration() throws AdminException;

	/**
	 * Listing of all at the moment logged in users.
	 * 
	 * @return a list of user profiles currently logged in at the system
	 * @throws AdminException
	 */
	public UserProfile[] listLoggedInUsers() throws AdminException;

	/**
	 * 
	 * @param uid
	 * @return
	 * @throws AdminException
	 */
	public UserProfile lookupUserProfile(String uid) throws AdminException;

	/**
	 * 
	 * @return
	 * @throws AdminException
	 */
	public UserProfile[] listAllKnownUsers() throws AdminException;

	/**
	 * 
	 * @return
	 * @throws AdminException
	 */
	public AccessSessionInfo[] listAccessSessions() throws AdminException;

	/**
	 * 
	 * @param asid
	 * @return
	 * @throws AdminException
	 */
	public AccessSessionInfo lookupAccessSession(String asid)
			throws AdminException;

	/**
	 * 
	 * @return
	 * @throws AdminException
	 */
	public ServiceSessionInfo[] listServiceSessions() throws AdminException;

	/**
	 * 
	 * @param ssid
	 * @return
	 * @throws AdminException
	 */
	public ServiceSessionInfo lookupServiceSession(String ssid)
			throws AdminException;

	/**
	 * 
	 * @return
	 * @throws AdminException
	 */
	public AdminServiceProfileData[] listDeployedServices()
			throws AdminException;

	public AdminServiceProfileData lookupServiceProfile(String serviceId)
			throws AdminException;

	/**
	 * 
	 * @param asid
	 * @throws AdminException
	 */
	public void removeAccessSession(String asid) throws AdminException;

	/**
	 * 
	 * @param ssid
	 * @throws AdminException
	 */
	public void removeServiceSession(String ssid) throws AdminException;

	/**
	 * 
	 * @param uid
	 * @throws AdminException
	 */
	public void removeUserProfile(String uid) throws AdminException;

	/**
	 * 
	 * @param serviceId
	 * @param active
	 * @throws AdminException
	 */
	public void changeServiceStatus(String serviceId, boolean active)
			throws AdminException;

	/**
	 * 
	 * @param serviceProfile
	 * @param serviceClassName
	 * @param serviceFactoryName
	 * @throws AdminException
	 */
	public void deployNewService(AdminServiceProfileData serviceProfile,
			String serviceClassName, String serviceFactoryName)
			throws AdminException;

	/**
	 * 
	 * @param serviceId
	 * @throws AdminException
	 */
	public void undeployService(String serviceId) throws AdminException;
}