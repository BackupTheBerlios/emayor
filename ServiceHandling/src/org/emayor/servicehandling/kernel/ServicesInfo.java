/*
 * $ Created on Feb 9, 2005 by tku $
 * 
 * It is workaround, because the ws implementation of the jboss
 * cannot deal directly with arrays !
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class ServicesInfo implements Serializable {

	private ServiceInfo[] servicesInfo;

	/**
	 * @return Returns the info.
	 */
	public ServiceInfo[] getServicesInfo() {
		return this.servicesInfo;
	}

	/**
	 * 
	 * @param serviceInfos
	 */
	public void setServicesInfo(ServiceInfo[] serviceInfos) {
		this.servicesInfo = serviceInfos;
	}
}