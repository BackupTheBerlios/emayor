/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

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

	/**
	 * 
	 * @param accessSessionId
	 * @param serviceName
	 * @return
	 */
	public String startService(String accessSessionId, String serviceName)
			throws AccessException;

	/**
	 * 
	 * @param accessSessionId
	 * @param serviceSessionId - the service session id points the an instance
	 * of the service session which contains only one running service. (The service
	 * name would be not enough here, because the user can start several time the
	 * same service)
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
}