/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public interface IAdmin {
    
    public boolean isEnabled() throws AdminException;
    
    public boolean login(String uid, String pswd) throws AdminException;

    public void reloadServices() throws AdminException;

    public void reloadConfiguration() throws AdminException;

    public UserProfile[] listLoggedInUsers() throws AdminException;

    public UserProfile lookupUserProfile(String uid) throws AdminException;

    public UserProfile[] listAllKnownUsers() throws AdminException;

    public AccessSessionInfo[] listAccessSessions() throws AdminException;

    public AccessSessionInfo lookupAccessSession(String asid)
            throws AdminException;

    public ServiceSessionInfo[] listServiceSessions() throws AdminException;

    public ServiceSessionInfo lookupServiceSession(String ssid)
            throws AdminException;

    public ServiceProfile[] listDeployedServices() throws AdminException;

    public ServiceProfile lookupServiceProfile(String serviceId)
            throws AdminException;
}