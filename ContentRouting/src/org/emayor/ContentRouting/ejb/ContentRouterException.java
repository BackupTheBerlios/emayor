package org.emayor.ContentRouting.ejb;

/**
 * Represents a general exception thrown from the content routing logic. This is
 * the superclass of all other exceptions within the package.
 * 
 * @author Nikolaos Oikonomidis, University of Siegen
 *  
 */
public class ContentRouterException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *            The message passed when the exception occurs.
     */
    public ContentRouterException(String message) {
        super(message);
    }

}