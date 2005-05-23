/*
 * $ Created on May 9, 2005 by tku $
 */
package org.eMayor.PolicyEnforcement.config;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"><font size="-1">Tomasz Kusber</font></a><font size="-1"> FHI FOKUS (C)</font>
 */
public class ConfigException extends Exception {

	/**
	 * 
	 */
	public ConfigException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ConfigException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ConfigException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ConfigException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
