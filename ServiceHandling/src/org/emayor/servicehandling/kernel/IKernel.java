/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import org.eMayor.PolicyEnforcement.C_UserProfile;
import org.emayor.servicehandling.interfaces.AccessSessionLocal;
import org.emayor.servicehandling.interfaces.ServiceSessionLocal;

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
	 * @param asid
	 * @return
	 * @throws KernelException
	 */
	public boolean deleteAccessSession(String asid) throws KernelException;

	/**
	 * 
	 * @param asid
	 * @param serviceName
	 * @return
	 * @throws KernelException
	 */
	public ServiceSessionLocal createServiceSession(String asid,
			String serviceName) throws KernelException;

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
	 * @param ssid
	 * @return
	 * @throws KernelException
	 */
	public boolean deleteServiceSession(String ssid) throws KernelException;

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
	public IUserProfile getUserProfile(String userId) throws KernelException;

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
	 * @param serviceName
	 * @return
	 * @throws KernelException
	 */
	public String getServiceClassNameByServiceName(String serviceName)
			throws KernelException;
}