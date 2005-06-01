/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public class AdminException extends Exception {

    /**
     * 
     */
    public AdminException() {
        super();
    }

    /**
     * @param arg0
     */
    public AdminException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public AdminException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public AdminException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

}
