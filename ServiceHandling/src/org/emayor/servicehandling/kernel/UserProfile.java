/*
 * Created on Mar 10, 2005
 */
package org.emayor.servicehandling.kernel;

import org.apache.log4j.Logger;
import org.eMayor.PolicyEnforcement.C_UserProfile;

/**
 * @author tku
 */
public class UserProfile implements IUserProfile {
	private final static Logger log = Logger.getLogger(UserProfile.class);

	private C_UserProfile pEUserProfile;

	private String userId;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IUserProfile#getUserId()
	 */
	public String getUserId() {
		return this.userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IUserProfile#setUserId(java.lang.String)
	 */
	public void setUserId(String userId) {
		if (log.isDebugEnabled())
			log.debug("setting the userId to: " + userId);
		this.userId = userId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IUserProfile#getPEUserProfile()
	 */
	public C_UserProfile getPEUserProfile() {
		return this.pEUserProfile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emayor.servicehandling.kernel.IUserProfile#setPEUserProfile(org.eMayor.PolicyEnforcement.C_UserProfile)
	 */
	public void setPEUserProfile(C_UserProfile userProfile) {
		this.pEUserProfile = userProfile;
	}

}