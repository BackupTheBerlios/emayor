/*
 * Created on Feb 23, 2005
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

/**
 * @author tku
 */
public interface IeMayorServiceFactory extends Serializable {

	public void setup() throws eMayorServiceException;
	
	public void cleanup() throws eMayorServiceException;
	
	public IeMayorService createService(String serviceName, String ssid) throws eMayorServiceException;
}
