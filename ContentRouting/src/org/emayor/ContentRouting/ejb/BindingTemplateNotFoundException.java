/*
 * Created on 15.12.2004
 */
package org.emayor.ContentRouting.ejb;

/**
 * Represents the exception thrown when no binding template could be found for
 * the given service.
 * 
 * @author Nikolaos Oikonomidis, University of Siegen
 *  
 */
public class BindingTemplateNotFoundException extends ContentRouterException {

    /**
     * @param message
     *            The message passed when the exception occurs.
     */
    public BindingTemplateNotFoundException(String message) {
        super(message);

    }
}