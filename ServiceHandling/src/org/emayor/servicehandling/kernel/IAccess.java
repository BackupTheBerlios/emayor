/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.security.cert.X509Certificate;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public interface IAccess {

	/**
	 * 
	 * @return The id of the just created access session instance.
	 */
	public String createAccessSession() throws AccessException;

	// start access session

	public boolean startAccessSession(String accessSessionId,
			X509Certificate[] certificates) throws AccessException;

	/**
	 *  
	 */
	public boolean stopAccessSession(String accessSessionId)
			throws AccessException;

	/**
	 * 
	 * @param accessSessionId
	 * @param serviceId
	 * @return
	 */
	public String startService(String accessSessionId, String serviceId)
			throws AccessException;

	public String startForwardedService(String accessSessionId,
			String serviceId, ForwardMessage message) throws AccessException;

	/**
	 * 
	 * @param accessSessionId
	 * @param serviceSessionId -
	 *            the service session id points the an instance of the service
	 *            session which contains only one running service. (The service
	 *            name would be not enough here, because the user can start
	 *            several time the same service)
	 * @return
	 */
	public boolean stopService(String accessSessionId, String serviceSessionId)
			throws AccessException;

	/**
	 * 
	 * @param accessSessionId
	 * @return
	 */
	public ServicesInfo listAvailableServices(String accessSessionId)
			throws AccessException;

	/**
	 * 
	 * @param accessSessionId
	 * @return
	 */
	public RunningServicesInfo listRunningServices(String accessSessionId)
			throws AccessException;

	public UserProfile getUserProfile(String asid) throws AccessException;
}