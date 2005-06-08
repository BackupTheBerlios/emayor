/*
 * $ Created on Jun 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C) 2005
 *         </font>
 */
public class ServiceClassloaderException extends Exception {

	/**
	 *  
	 */
	public ServiceClassloaderException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ServiceClassloaderException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ServiceClassloaderException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ServiceClassloaderException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}