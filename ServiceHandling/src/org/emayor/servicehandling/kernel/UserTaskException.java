/*
 * Created on Feb 15, 2005
 */
package org.emayor.servicehandling.kernel;


/**
 * @author tku
 */
public class UserTaskException extends Exception {

	/**
	 * 
	 */
	public UserTaskException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public UserTaskException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public UserTaskException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public UserTaskException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
