/*
 * $ Created on Dec 9, 2004 by tku $
 */
package org.emayor.servicehandling.kernel;

import org.apache.log4j.Logger;


/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class ServiceInfo implements IServiceInfo {
	private static Logger log = Logger.getLogger(ServiceInfo.class);
	
	private String serviceId;
	
	private String serviceVersion;
	
	private String serviceName;

	private String serviceFactoryClassName;

	private String serviceClassName;
	
	private String serviceDescription;

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		log.debug("-> start processing ...");
		this.serviceName = serviceName;
	}

	/**
	 * @return Returns the serviceClassName.
	 */
	public String getServiceClassName() {
		log.debug("-> start processing ...");
		return serviceClassName;
	}

	/**
	 * @param serviceClassName
	 *            The serviceClassName to set.
	 */
	public void setServiceClassName(String serviceClassName) {
		log.debug("-> start processing ...");
		this.serviceClassName = serviceClassName;
	}

	/**
	 * @return Returns the serviceFactoryClassName.
	 */
	public String getServiceFactoryClassName() {
		log.debug("-> start processing ...");
		return serviceFactoryClassName;
	}

	/**
	 * @param serviceFactoryClassName
	 *            The serviceFactoryClassName to set.
	 */
	public void setServiceFactoryClassName(String serviceFactoryClassName) {
		log.debug("-> start processing ...");
		this.serviceFactoryClassName = serviceFactoryClassName;
	}
	/**
	 * @return Returns the serviceDescription.
	 */
	public String getServiceDescription() {
		log.debug("-> start processing ...");
		return serviceDescription;
	}
	/**
	 * @param serviceDescription The serviceDescription to set.
	 */
	public void setServiceDescription(String serviceDescription) {
		log.debug("-> start processing ...");
		this.serviceDescription = serviceDescription;
	}
	/**
	 * @return Returns the serviceId.
	 */
	public String getServiceId() {
		log.debug("-> start processing ...");
		return serviceId;
	}
	/**
	 * @param serviceId The serviceId to set.
	 */
	public void setServiceId(String serviceId) {
		log.debug("-> start processing ...");
		this.serviceId = serviceId;
	}
	/**
	 * @return Returns the serviceVersion.
	 */
	public String getServiceVersion() {
		log.debug("-> start processing ...");
		return serviceVersion;
	}
	/**
	 * @param serviceVersion The serviceVersion to set.
	 */
	public void setServiceVersion(String serviceVersion) {
		log.debug("-> start processing ...");
		this.serviceVersion = serviceVersion;
	}
}

