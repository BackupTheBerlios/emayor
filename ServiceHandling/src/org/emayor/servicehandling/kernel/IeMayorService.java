/*
 * Created on Feb 23, 2005
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

/**
 * @author tku
 */
public interface IeMayorService extends Serializable {
	
	public void setup() throws eMayorServiceException;
	
	//public void customize(IUserServiceProfile userServiceProfile) throws eMayorServiceException;
	
	public void cleanup() throws eMayorServiceException;
	
	public void endService() throws eMayorServiceException;
}
