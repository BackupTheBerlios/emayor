package org.emayor.ContentRouting.ejb;

/**
 * Represents the exception thrown when no service could be found for the given
 * organization.
 * 
 * @author Nikolaos Oikonomidis, University of Siegen
 *  
 */
public class ServiceNotFoundException extends ContentRouterException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *            The message passed when the exception occurs.
     */
    public ServiceNotFoundException(String message) {
        super(message);
    }
}