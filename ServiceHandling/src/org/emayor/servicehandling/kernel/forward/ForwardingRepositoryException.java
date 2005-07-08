/*
 * Created on May 11, 2005
 */
package org.emayor.servicehandling.kernel.forward;

/**
 * @author tku
 */
public class ForwardingRepositoryException extends Exception {

    /**
     * 
     */
    public ForwardingRepositoryException() {
        super();
    }

    /**
     * @param arg0
     */
    public ForwardingRepositoryException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public ForwardingRepositoryException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ForwardingRepositoryException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
