/*
 * $ Created on Dec 9, 2004 by tku $
 */
package org.emayor.servicehandling.kernel;


/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class ServiceInfo implements IServiceInfo {
	private String serviceName;

	private String serviceFactoryClassName;

	private String serviceClassName;
	
	private String serviceDescription;

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return Returns the serviceClassName.
	 */
	public String getServiceClassName() {
		return serviceClassName;
	}

	/**
	 * @param serviceClassName
	 *            The serviceClassName to set.
	 */
	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}

	/**
	 * @return Returns the serviceFactoryClassName.
	 */
	public String getServiceFactoryClassName() {
		return serviceFactoryClassName;
	}

	/**
	 * @param serviceFactoryClassName
	 *            The serviceFactoryClassName to set.
	 */
	public void setServiceFactoryClassName(String serviceFactoryClassName) {
		this.serviceFactoryClassName = serviceFactoryClassName;
	}
	/**
	 * @return Returns the serviceDescription.
	 */
	public String getServiceDescription() {
		return serviceDescription;
	}
	/**
	 * @param serviceDescription The serviceDescription to set.
	 */
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
}

