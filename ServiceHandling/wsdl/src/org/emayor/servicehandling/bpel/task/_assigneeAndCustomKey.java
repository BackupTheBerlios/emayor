/**
 * _assigneeAndCustomKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.emayor.servicehandling.bpel.task;

public class _assigneeAndCustomKey  implements java.io.Serializable {
    private java.lang.String assignee;
    private java.lang.String customKey;

    public _assigneeAndCustomKey() {
    }

    public java.lang.String getAssignee() {
        return assignee;
    }

    public void setAssignee(java.lang.String assignee) {
        this.assignee = assignee;
    }

    public java.lang.String getCustomKey() {
        return customKey;
    }

    public void setCustomKey(java.lang.String customKey) {
        this.customKey = customKey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _assigneeAndCustomKey)) return false;
        _assigneeAndCustomKey other = (_assigneeAndCustomKey) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.assignee==null && other.getAssignee()==null) || 
             (this.assignee!=null &&
              this.assignee.equals(other.getAssignee()))) &&
            ((this.customKey==null && other.getCustomKey()==null) || 
             (this.customKey!=null &&
              this.customKey.equals(other.getCustomKey())));
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
        if (getAssignee() != null) {
            _hashCode += getAssignee().hashCode();
        }
        if (getCustomKey() != null) {
            _hashCode += getCustomKey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_assigneeAndCustomKey.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://emayor.org/servicehandling/bpel/task", ">assigneeAndCustomKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assignee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://emayor.org/servicehandling/bpel/task", "assignee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://emayor.org/servicehandling/bpel/task", "customKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
