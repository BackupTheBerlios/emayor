/*
 * Created on Feb 23, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public class eMayorServiceException extends Exception {

	/**
	 * 
	 */
	public eMayorServiceException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public eMayorServiceException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public eMayorServiceException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public eMayorServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
