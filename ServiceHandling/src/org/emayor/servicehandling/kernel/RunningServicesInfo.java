/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class RunningServicesInfo implements Serializable {

	private RunningServiceInfo[] runningServicesInfo;

	/**
	 * 
	 * @return
	 */
	public RunningServiceInfo[] getRuningServicesInfo() {
		return this.runningServicesInfo;
	}

	/**
	 * 
	 * @param runningServiceInfos
	 */
	public void setRunningServicesInfo(RunningServiceInfo[] runningServicesInfo) {
		this.runningServicesInfo = runningServicesInfo;
	}
}