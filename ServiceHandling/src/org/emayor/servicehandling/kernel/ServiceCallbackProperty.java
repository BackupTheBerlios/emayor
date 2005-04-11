/*
 * Created on Apr 12, 2005
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

/**
 * @author tku
 */
public class ServiceCallbackProperty implements Serializable {
    private String propertyName;
    private String propertyValue;
    /**
     * @return Returns the propertyName.
     */
    public String getPropertyName() {
        return propertyName;
    }
    /**
     * @param propertyName The propertyName to set.
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    /**
     * @return Returns the propertyValue.
     */
    public String getPropertyValue() {
        return propertyValue;
    }
    /**
     * @param propertyValue The propertyValue to set.
     */
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}	
