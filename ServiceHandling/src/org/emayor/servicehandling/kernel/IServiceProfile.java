/*
 * Created on Mar 10, 2005
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

import org.eMayor.PolicyEnforcement.C_ServiceProfile;

/**
 * @author tku
 */
public interface IServiceProfile extends Serializable {

	public IServiceInfo getServiceInfo();

	public void setServiceInfo(IServiceInfo serviceInfo);

	public C_ServiceProfile getPEServiceProfile();

	public void setPEServiceProfile(C_ServiceProfile serviceProfile);

}