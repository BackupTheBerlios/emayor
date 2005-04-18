/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.kernel.bpel.forward.client;

/**
 * @author tku
 */
public class ForwardManagerBPELClientException extends Exception {
    /**
     *  
     */
    public ForwardManagerBPELClientException() {
        super();
    }

    /**
     * @param arg0
     */
    public ForwardManagerBPELClientException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public ForwardManagerBPELClientException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public ForwardManagerBPELClientException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}