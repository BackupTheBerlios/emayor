/*
 * Created on Feb 23, 2005
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

/**
 * @author tku
 */
public interface IeMayorServiceFactory extends Serializable {
    /**
     * 
     * @throws eMayorServiceException
     */
    public void setup() throws eMayorServiceException;

    /**
     * 
     * @throws eMayorServiceException
     */
    public void cleanup() throws eMayorServiceException;

    /**
     * 
     * @param serviceId
     * @param ssid
     * @return
     * @throws eMayorServiceException
     */
    public IeMayorService createService(String serviceId, String ssid)
            throws eMayorServiceException;

}