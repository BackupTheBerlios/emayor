/*
 * Created on Jun 2, 2005
 */
package org.emayor.servicehandling.kernel;

/**
 * @author tku
 */
public class ServiceSessionInfo extends SessionInfo {

    private String asid;
    
    private String serviceId;
    /**
     * 
     */
    public ServiceSessionInfo() {
        super();
    }
    
    public ServiceSessionInfo(String ssid, String asid, String serviceId) {
        super(ssid);
        this.asid = asid;
        this.serviceId = serviceId;
    }

    /**
     * @return Returns the asid.
     */
    public String getAsid() {
        return asid;
    }
    /**
     * @param asid The asid to set.
     */
    public void setAsid(String asid) {
        this.asid = asid;
    }
    /**
     * @return Returns the serviceId.
     */
    public String getServiceId() {
        return serviceId;
    }
    /**
     * @param serviceId The serviceId to set.
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
