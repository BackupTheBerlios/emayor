/*
 * Created on Mar 10, 2005
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

import org.eMayor.PolicyEnforcement.C_UserProfile;

/**
 * @author tku
 */
public interface IUserProfile extends Serializable {
	
	public String getUserId();
	public void setUserId(String userId);
	
	public C_UserProfile getPEUserProfile();
	public void setPEUserProfile(C_UserProfile userProfile);
}
