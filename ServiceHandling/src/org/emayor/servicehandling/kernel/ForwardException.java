/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"><font size="-1">Tomasz Kusber</font></a><font size="-1"> FHI FOKUS (C)</font>
 */
public class ForwardException extends Exception {

	/**
	 * 
	 */
	public ForwardException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ForwardException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ForwardException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ForwardException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
