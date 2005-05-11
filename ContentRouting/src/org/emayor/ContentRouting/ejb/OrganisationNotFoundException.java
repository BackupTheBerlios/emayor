package org.emayor.ContentRouting.ejb;

/**
 * Represents the exception thrown when the requested organization could not be
 * found.
 * 
 * @author Nikolaos Oikonomidis, University of Siegen
 *  
 */
public class OrganisationNotFoundException extends ContentRouterException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
 

    /**
     * @param message
     *            The message passed when the exception occurs.
     */
    public OrganisationNotFoundException(String message) {
        super(message);
    }
}