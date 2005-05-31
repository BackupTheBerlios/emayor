/*
 * $ Created on Jun 1, 2005 by tku $
 */
package org.emayor.servicehandling.kernel.bpel.starter;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"><font size="-1">Tomasz Kusber</font></a><font size="-1"> FHI FOKUS (C)</font>
 */
public class eMayorServiceInvokerException extends Exception {

	/**
	 * 
	 */
	public eMayorServiceInvokerException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public eMayorServiceInvokerException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public eMayorServiceInvokerException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public eMayorServiceInvokerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
