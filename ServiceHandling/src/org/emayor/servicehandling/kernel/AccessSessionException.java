/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public class AccessSessionException extends SessionException {

	/**
	 * 
	 */
	public AccessSessionException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public AccessSessionException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public AccessSessionException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public AccessSessionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
