/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public class SessionException extends Exception {

	/**
	 * 
	 */
	public SessionException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public SessionException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public SessionException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SessionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
