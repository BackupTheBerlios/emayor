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

    /**
     * 
     * @param accessSessionId
     * @param certificates
     * @return
     * @throws AccessException
     */
    public boolean startAccessSession(String accessSessionId,
            X509Certificate[] certificates) throws AccessException;

    /**
     * 
     * @param accessSessionId
     * @return
     * @throws AccessException
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

    /**
     * In this case the request document has been already prepared e.g.
     * be the GUI and can be directly passed by to the service.
     * @param accessSessionId a unique access session id returned by 
     * the start of the access session
     * @param serviceId a unique service id
     * @param requestDocument filled and signed service request document
     * @return a unique service session id will be delivered as return value
     * @throws AccessException
     */
    public String startService(String accessSessionId, String serviceId,
            String requestDocument) throws AccessException;
    
    /**
     * 
     * @param accessSessionId
     * @param serviceId
     * @param message
     * @return
     * @throws AccessException
     */
    public String startForwardedService(String accessSessionId,
            String serviceId, ForwardMessage message) throws AccessException;

    /**
     * 
     * @param accessSessionId
     * @param serviceSessionId -
     *            the service session id points to an instance of the service
     *            session which contains only one running service. (The service
     *            name would be not enough here, because the user can start
     *            several times the same service)
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
    
    /**
     * 
     * @param asid
     * @return
     * @throws AccessException
     */
    public UserProfile getUserProfile(String asid) throws AccessException;
}