/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public class ServiceSessionException extends SessionException {

	/**
	 * 
	 */
	public ServiceSessionException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ServiceSessionException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ServiceSessionException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ServiceSessionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
