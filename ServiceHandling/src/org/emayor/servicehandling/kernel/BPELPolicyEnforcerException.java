/*
 * Created on Mar 10, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public class BPELPolicyEnforcerException extends Exception {

	/**
	 * 
	 */
	public BPELPolicyEnforcerException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public BPELPolicyEnforcerException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public BPELPolicyEnforcerException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BPELPolicyEnforcerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
