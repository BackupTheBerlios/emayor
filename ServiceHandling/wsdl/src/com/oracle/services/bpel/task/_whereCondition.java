/**
 * _whereCondition.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.oracle.services.bpel.task;

public class _whereCondition  implements java.io.Serializable {
    private java.lang.String title;
    private java.lang.String creator;
    private java.lang.String modifier;
    private java.lang.String assignee;
    private com.oracle.services.bpel.task._whereCondition_status status;
    private java.lang.Integer priority;
    private java.lang.String customKey;

    public _whereCondition() {
    }

    public java.lang.String getTitle() {
        return title;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public java.lang.String getCreator() {
        return creator;
    }

    public void setCreator(java.lang.String creator) {
        this.creator = creator;
    }

    public java.lang.String getModifier() {
        return modifier;
    }

    public void setModifier(java.lang.String modifier) {
        this.modifier = modifier;
    }

    public java.lang.String getAssignee() {
        return assignee;
    }

    public void setAssignee(java.lang.String assignee) {
        this.assignee = assignee;
    }

    public com.oracle.services.bpel.task._whereCondition_status getStatus() {
        return status;
    }

    public void setStatus(com.oracle.services.bpel.task._whereCondition_status status) {
        this.status = status;
    }

    public java.lang.Integer getPriority() {
        return priority;
    }

    public void setPriority(java.lang.Integer priority) {
        this.priority = priority;
    }

    public java.lang.String getCustomKey() {
        return customKey;
    }

    public void setCustomKey(java.lang.String customKey) {
        this.customKey = customKey;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _whereCondition)) return false;
        _whereCondition other = (_whereCondition) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.creator==null && other.getCreator()==null) || 
             (this.creator!=null &&
              this.creator.equals(other.getCreator()))) &&
            ((this.modifier==null && other.getModifier()==null) || 
             (this.modifier!=null &&
              this.modifier.equals(other.getModifier()))) &&
            ((this.assignee==null && other.getAssignee()==null) || 
             (this.assignee!=null &&
              this.assignee.equals(other.getAssignee()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.priority==null && other.getPriority()==null) || 
             (this.priority!=null &&
              this.priority.equals(other.getPriority()))) &&
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
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getCreator() != null) {
            _hashCode += getCreator().hashCode();
        }
        if (getModifier() != null) {
            _hashCode += getModifier().hashCode();
        }
        if (getAssignee() != null) {
            _hashCode += getAssignee().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getPriority() != null) {
            _hashCode += getPriority().hashCode();
        }
        if (getCustomKey() != null) {
            _hashCode += getCustomKey().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_whereCondition.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", ">whereCondition"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "creator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "modifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assignee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "assignee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", ">whereCondition>status"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("priority");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "priority"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "int"));
        elemField.setMinOccurs(0);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customKey");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "customKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
