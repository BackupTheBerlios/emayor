/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public class AccessSessionInfo extends SessionInfo {

    private String userId;

    /**
     *  
     */
    public AccessSessionInfo() {
        super();
    }
    
    public AccessSessionInfo(String asid, String uid) {
        super(asid);
        this.userId = uid;
    }

    /**
     * @return Returns the userId.
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId The userId to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}