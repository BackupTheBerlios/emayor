/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

import javax.security.cert.X509Certificate;

import org.emayor.policyenforcer.C_UserProfile;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;

/**
 * @author tku
 */
public interface IAccessSession extends ISession {
	/**
	 * This method carries out the uthentication of the user based on his/her
	 * X509 certificate.
	 * 
	 * @param certificate
	 *            X509 certificate
	 * @return true in case the user has been authenticated, otherwise false
	 * @throws AccessSessionException
	 */
	public boolean authenticateUser(X509Certificate certificate)
			throws AccessSessionException;

	/**
	 * This method returns all currently running service sessions of the user.
	 * 
	 * @return an arry containing the references to all running sessions of
	 *         current user; if there is no service session the array of the
	 *         length 0 will be returned.
	 * @throws AccessSessionException
	 */
	public ServiceSessionLocal[] getAllServiceSessions()
			throws AccessSessionException;

	/**
	 * A reference to a particular runing service session can be obtained by
	 * using this method.
	 * 
	 * @param ssid
	 *            the service session id
	 * @return a reference to the required session or null it the specified
	 *         session doesn't exist
	 * @throws AccessSessionException
	 */
	public ServiceSessionLocal getServiceSession(String ssid)
			throws AccessSessionException;

	/**
	 * A services session for the service specified by the input parameter will
	 * be started.
	 * 
	 * @param serviceId
	 *            the id of the required service
	 * @return the service session id
	 * @throws AccessSessionException
	 */
	public String startServiceSession(String serviceId, boolean isForwarded)
			throws AccessSessionException;

	/**
	 * A specified session wil be stoped.
	 * 
	 * @param ssid
	 *            service session id
	 * @return true if the action was successfull, otherwise false
	 * @throws AccessSessionException
	 */
	public boolean stopServiceSession(String ssid)
			throws AccessSessionException;

	/**
	 * All running service session belonging to current user will be stoped.
	 * 
	 * @return
	 * @throws AccessSessionException
	 */
	public boolean stopAllServiceSessions() throws AccessSessionException;

	/**
	 * A list of all available service for current user will be returned.
	 * 
	 * @return
	 * @throws AccessSessionException
	 */
	public ServiceInfo[] listAvailableServices() throws AccessSessionException;

	/**
	 * The current access session will be stoped. (it means the user has logged
	 * out)
	 * 
	 * @return
	 * @throws AccessSessionException
	 */
	public boolean stop() throws AccessSessionException;

	/**
	 * The profile of the current user will be returned
	 * 
	 * @return the user profile
	 * @throws AccessSessionException
	 */
	public C_UserProfile getUserProfile() throws AccessSessionException;
}