/*
 * Created on Feb 14, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public class KernelException extends Exception {

	/**
	 * 
	 */
	public KernelException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public KernelException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public KernelException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public KernelException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
