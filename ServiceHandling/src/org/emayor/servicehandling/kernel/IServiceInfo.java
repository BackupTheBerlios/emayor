/*
 * $ Created on Dec 2, 2004 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public interface IServiceInfo extends Serializable {

	public String getServiceId();
	
	public String getServiceVersion();
	
	public String getServiceName();

	public String getServiceClassName();

	public String getServiceFactoryClassName();
	
	public String getServiceDescription();
	
}