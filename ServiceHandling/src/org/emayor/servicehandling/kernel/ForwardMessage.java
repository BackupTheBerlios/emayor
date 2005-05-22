/*
 * Created on Feb 24, 2005
 */
package org.emayor.servicehandling.kernel;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class ForwardMessage implements IForwardMessage {
    private static Logger log = Logger.getLogger(ForwardMessage.class);

    public static final int REQUEST = 1;

    public static final int RESPONSE = 2;

    private org.emayor.servicehandling.kernel.bpel.forward.data.ArrayOfStrings documents;

    private java.lang.String replyAddress;

    private java.lang.String replyID;

    private java.lang.String replyService;

    private int type;

    private String serviceId;

    /**
     * @return Returns the serviceId.
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId
     *            The serviceId to set.
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ForwardMessage() {
    }

    public org.emayor.servicehandling.kernel.bpel.forward.data.ArrayOfStrings getDocuments() {
        return documents;
    }

    public void setDocuments(
            org.emayor.servicehandling.kernel.bpel.forward.data.ArrayOfStrings documents) {
        this.documents = documents;
    }

    public java.lang.String getReplyAddress() {
        return replyAddress;
    }

    public void setReplyAddress(java.lang.String replyAddress) {
        this.replyAddress = replyAddress;
    }

    public java.lang.String getReplyID() {
        return replyID;
    }

    public void setReplyID(java.lang.String replyID) {
        this.replyID = replyID;
    }

    public java.lang.String getReplyService() {
        return replyService;
    }

    public void setReplyService(java.lang.String replyService) {
        this.replyService = replyService;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ForwardMessage))
            return false;
        ForwardMessage other = (ForwardMessage) obj;
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true
                && ((this.documents == null && other.getDocuments() == null) || (this.documents != null && this.documents
                        .equals(other.getDocuments())))
                && ((this.replyAddress == null && other.getReplyAddress() == null) || (this.replyAddress != null && this.replyAddress
                        .equals(other.getReplyAddress())))
                && ((this.replyID == null && other.getReplyID() == null) || (this.replyID != null && this.replyID
                        .equals(other.getReplyID())))
                && ((this.replyService == null && other.getReplyService() == null) || (this.replyService != null && this.replyService
                        .equals(other.getReplyService())))
                && this.type == other.getType()
                && ((this.serviceId == null && other.getServiceId() == null) || (this.serviceId != null && this.serviceId
                        .equals(other.getServiceId())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDocuments() != null) {
            _hashCode += getDocuments().hashCode();
        }
        if (getReplyAddress() != null) {
            _hashCode += getReplyAddress().hashCode();
        }
        if (getReplyID() != null) {
            _hashCode += getReplyID().hashCode();
        }
        if (getReplyService() != null) {
            _hashCode += getReplyService().hashCode();
        }
        if (getServiceId() != null) {
            _hashCode += getServiceId().hashCode();
        }
        _hashCode += getType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
            ForwardMessage.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName(
                "http://sh.forward.emayor.org", "ForwardMessage"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documents");
        elemField.setXmlName(new javax.xml.namespace.QName(
                "http://sh.forward.emayor.org", "documents"));
        elemField.setXmlType(new javax.xml.namespace.QName(
                "http://emayor.org/IForward", "ArrayOf_xsd_string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("replyAddress");
        elemField.setXmlName(new javax.xml.namespace.QName(
                "http://sh.forward.emayor.org", "replyAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName(
                "http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("replyID");
        elemField.setXmlName(new javax.xml.namespace.QName(
                "http://sh.forward.emayor.org", "replyID"));
        elemField.setXmlType(new javax.xml.namespace.QName(
                "http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("replyService");
        elemField.setXmlName(new javax.xml.namespace.QName(
                "http://sh.forward.emayor.org", "replyService"));
        elemField.setXmlType(new javax.xml.namespace.QName(
                "http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName(
                "http://sh.forward.emayor.org", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName(
                "http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceId");
        elemField.setXmlName(new javax.xml.namespace.QName(
                "http://sh.forward.emayor.org", "serviceId"));
        elemField.setXmlType(new javax.xml.namespace.QName(
                "http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
            java.lang.String mechType, java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanSerializer(_javaType,
                _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType, java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
                _xmlType, typeDesc);
    }

}