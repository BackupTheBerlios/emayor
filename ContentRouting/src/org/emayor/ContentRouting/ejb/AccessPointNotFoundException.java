package org.emayor.ContentRouting.ejb;

/**
 * Represents the exception thrown when no access point URL could be found for
 * the given service.
 * 
 * @author Nikolaos Oikonomidis, University of Siegen
 *  
 */
public class AccessPointNotFoundException extends ContentRouterException {

    /**
     * @param message
     *            The message passed when the exception occurs.
     */
    public AccessPointNotFoundException(String message) {
        super(message);
    }
}