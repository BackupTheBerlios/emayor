/*
 * Created on Feb 27, 2005
 */
package org.emayor.rcs.datachecker;

/**
 * @author tku
 */
public class DataCheckerException extends Exception {

	/**
	 * 
	 */
	public DataCheckerException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public DataCheckerException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public DataCheckerException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DataCheckerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
