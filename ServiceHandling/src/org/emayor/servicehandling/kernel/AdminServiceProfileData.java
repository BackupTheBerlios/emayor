/*
 * Created on Jun 6, 2005
 */
package org.emayor.servicehandling.kernel;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class AdminServiceProfileData extends ServiceInfo {
    private final static Logger log = Logger
            .getLogger(AdminServiceProfileData.class);
    
    private String numberOfInstances;
    
    public AdminServiceProfileData() {
        super();
        this.setNumberOfInstances("0");
    }
    
    public AdminServiceProfileData(IServiceInfo info) {
        this.setServiceClassName(info.getServiceClassName());
        this.setServiceDescription(info.getServiceDescription());
        this.setServiceEndpoint(info.getServiceEndpoint());
        this.setServiceFactoryClassName(info.getServiceFactoryClassName());
        this.setServiceId(info.getServiceId());
        this.setServiceName(info.getServiceName());
        this.setServiceVersion(info.getServiceVersion());
        this.setActive(info.isActive());
        this.setNumberOfInstances("0");
    }
    
    /**
     * @return Returns the numberOfInstances.
     */
    public String getNumberOfInstances() {
        return numberOfInstances;
    }
    /**
     * @param numberOfInstances The numberOfInstances to set.
     */
    public void setNumberOfInstances(String numberOfInstances) {
        this.numberOfInstances = numberOfInstances;
    }
}