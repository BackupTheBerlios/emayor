/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.utils;

/**
 * @author tku
 */
public class ServiceLocatorException extends Exception {

	/**
	 * 
	 */
	public ServiceLocatorException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ServiceLocatorException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ServiceLocatorException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ServiceLocatorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
