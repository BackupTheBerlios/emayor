/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"><font size="-1">Tomasz Kusber</font></a><font size="-1"> FHI FOKUS (C)</font>
 */
public class ServiceException extends Exception {

	/**
	 * 
	 */
	public ServiceException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ServiceException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ServiceException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
