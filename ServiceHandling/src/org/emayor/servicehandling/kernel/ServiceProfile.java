/*
 * Created on Mar 10, 2005
 */
package org.emayor.servicehandling.kernel;

import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.C_ServiceProfile;

/**
 * @author tku
 */
public class ServiceProfile implements IServiceProfile {
	private static final Logger log = Logger.getLogger(ServiceProfile.class);

	private IServiceInfo serviceInfo;
	private C_ServiceProfile serviceProfile;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IServiceProfile#getServiceInfo()
	 */
	public IServiceInfo getServiceInfo() {
		return this.serviceInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IServiceProfile#setServiceInfo(org.emayor.servicehandling.kernel.IServiceInfo)
	 */
	public void setServiceInfo(IServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IServiceProfile#getPEServiceProfile()
	 */
	public C_ServiceProfile getPEServiceProfile() {
		return this.serviceProfile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IServiceProfile#setPEServiceProfile(org.eMayor.PolicyEnforcement.C_ServiceProfile)
	 */
	public void setPEServiceProfile(C_ServiceProfile serviceProfile) {
		this.serviceProfile = serviceProfile;
	}

}