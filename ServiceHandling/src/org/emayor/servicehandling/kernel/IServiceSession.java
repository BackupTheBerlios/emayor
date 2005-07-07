/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public interface IServiceSession extends ISession {

    /**
     * Get the id of the access session this service session is belongs to. This
     * id is not constant, because every time the user is logging in at the
     * system the new access session is provided to him. Nevertheless system is
     * taking care about the setting the accurate value of this parameter for
     * the time the user is logged in.
     * 
     * @return
     * @throws ServiceSessionException
     */
    public String getAccessSessionId() throws ServiceSessionException;

    /**
     * Set the id of the access session, this service session belongs to.
     * 
     * @param asid
     * @throws ServiceSessionException
     */
    public void setAccessSessionId(String asid) throws ServiceSessionException;

    /**
     * Obtain the service id
     * 
     * @return the id of this service
     * @throws ServiceSessionException
     */
    public String getServiceId() throws ServiceSessionException;

    /**
     * The service id will be set.
     * 
     * @param serviceId
     *            the id of this service
     * @throws ServiceSessionException
     */
    public void setServiceId(String serviceId) throws ServiceSessionException;

    /**
     * This method starts the service.
     * 
     * @param userId
     *            the system wide unique id of current user
     * @param isForwarded
     *            this parameter indicates whether the request has been
     *            processed localy or forwarded from another eMayor platform.
     * @param xmlDoc
     *            the request document in xml format
     * @param docSig
     *            possible digital signature of the xml document, if any.
     * @throws ServiceSessionException
     */
    public void startService(String userId, boolean isForwarded, String xmlDoc,
            String docSig) throws ServiceSessionException;

    /**
     * 
     * @param userId
     * @param isForwarded
     * @param xmlDoc
     * @param docSig
     * @throws ServiceSessionException
     */
    public void startServiceRequestCompleted(String userId,
            boolean isForwarded, String xmlDoc, String docSig)
            throws ServiceSessionException;

    /**
     * The service will be immediately stoped with the given reason. NOT
     * IMPLEMENTED IN CURRENT VERSION !
     * 
     * @param reason
     *            the reason, why the service had to be stoped
     * @throws ServiceSessionException
     */
    public void stopService(String reason) throws ServiceSessionException;

    /**
     * The service is going to be finished. This method should be only called if
     * the service has ended properly, otherwise if it is needed to abort the
     * service please use the stopService method.
     * 
     * @throws ServiceSessionException
     */
    public void endService() throws ServiceSessionException;

    /**
     * Obtain the eMayor service containing by this service session.
     * 
     * @return
     * @throws ServiceSessionException
     */
    public IeMayorService geteMayorService() throws ServiceSessionException;

    /**
     * Set the eMayor service for this service session.
     * 
     * @param emayorService
     * @throws ServiceSessionException
     */
    public void seteMayorService(IeMayorService emayorService)
            throws ServiceSessionException;

    /**
     * Get the creator id, it means the id of the user, who has created this
     * service session.
     * 
     * @return the creator id (user id)
     * @throws ServiceSessionException
     */
    public String getCreatorId() throws ServiceSessionException;

    /**
     * Set the creator id, it means the id of the user, who has created this
     * service session.
     * 
     * @param creatorId
     *            the creator id (user id)
     * @throws ServiceSessionException
     */
    public void setCreatorId(String creatorId) throws ServiceSessionException;
}