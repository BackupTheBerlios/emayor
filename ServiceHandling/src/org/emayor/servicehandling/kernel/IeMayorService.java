/*
 * Created on Feb 23, 2005
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

/**
 * @author tku
 */
public interface IeMayorService extends Serializable {
	
	public static String FORWARD_NO = "NO";
	public static String FORWARD_YES = "YES";
	
	public static String STATUS_NO = "NO";
	public static String STATUS_YES = "YES";

	public void setup() throws eMayorServiceException;

	//public void customize(IUserServiceProfile userServiceProfile) throws
	// eMayorServiceException;

	public void cleanup() throws eMayorServiceException;

	public void endService() throws eMayorServiceException;

	public void startService(String uid, String ssid)
			throws eMayorServiceException;

	public void forward(String uid, String ssid, String req, String reqDigSig)
			throws eMayorServiceException;
}