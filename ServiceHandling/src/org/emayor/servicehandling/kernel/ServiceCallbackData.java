/*
 * Created on Apr 12, 2005
 */
package org.emayor.servicehandling.kernel;

import java.io.Serializable;

/**
 * @author tku
 */
public class ServiceCallbackData implements Serializable {
    private String ssid;
    private String uid;
    private String requestDocument;
    private String resultDocument;
    private ServiceCallbackProperty property1;
    private ServiceCallbackProperty property2;
    private ServiceCallbackProperty property3;
    private ServiceCallbackProperty property4;
    
    /**
     * @return Returns the property1.
     */
    public ServiceCallbackProperty getProperty1() {
        return property1;
    }
    /**
     * @param property1 The property1 to set.
     */
    public void setProperty1(ServiceCallbackProperty property1) {
        this.property1 = property1;
    }
    /**
     * @return Returns the property2.
     */
    public ServiceCallbackProperty getProperty2() {
        return property2;
    }
    /**
     * @param property2 The property2 to set.
     */
    public void setProperty2(ServiceCallbackProperty property2) {
        this.property2 = property2;
    }
    /**
     * @return Returns the property3.
     */
    public ServiceCallbackProperty getProperty3() {
        return property3;
    }
    /**
     * @param property3 The property3 to set.
     */
    public void setProperty3(ServiceCallbackProperty property3) {
        this.property3 = property3;
    }
    /**
     * @return Returns the property4.
     */
    public ServiceCallbackProperty getProperty4() {
        return property4;
    }
    /**
     * @param property4 The property4 to set.
     */
    public void setProperty4(ServiceCallbackProperty property4) {
        this.property4 = property4;
    }
    /**
     * @return Returns the requestDocument.
     */
    public String getRequestDocument() {
        return requestDocument;
    }
    /**
     * @param requestDocument The requestDocument to set.
     */
    public void setRequestDocument(String requestDocument) {
        this.requestDocument = requestDocument;
    }
    /**
     * @return Returns the resultDocument.
     */
    public String getResultDocument() {
        return resultDocument;
    }
    /**
     * @param resultDocument The resultDocument to set.
     */
    public void setResultDocument(String resultDocument) {
        this.resultDocument = resultDocument;
    }
    /**
     * @return Returns the ssid.
     */
    public String getSsid() {
        return ssid;
    }
    /**
     * @param ssid The ssid to set.
     */
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
    /**
     * @return Returns the uid.
     */
    public String getUid() {
        return uid;
    }
    /**
     * @param uid The uid to set.
     */
    public void setUid(String uid) {
        this.uid = uid;
    }
}
