/**
 * _ResidenceCertifcationRequest_v10Request.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.emayor.ResidenceCertifcationRequest_v10;

public class _ResidenceCertifcationRequest_v10Request  implements java.io.Serializable {
    private org.emayor.www.eMayorServiceRequest_xsd.EMayorServiceRequestType input;

    public _ResidenceCertifcationRequest_v10Request() {
    }

    public org.emayor.www.eMayorServiceRequest_xsd.EMayorServiceRequestType getInput() {
        return input;
    }

    public void setInput(org.emayor.www.eMayorServiceRequest_xsd.EMayorServiceRequestType input) {
        this.input = input;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof _ResidenceCertifcationRequest_v10Request)) return false;
        _ResidenceCertifcationRequest_v10Request other = (_ResidenceCertifcationRequest_v10Request) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.input==null && other.getInput()==null) || 
             (this.input!=null &&
              this.input.equals(other.getInput())));
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
        if (getInput() != null) {
            _hashCode += getInput().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(_ResidenceCertifcationRequest_v10Request.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://emayor.org/ResidenceCertifcationRequest_v10", ">ResidenceCertifcationRequest_v10Request"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("input");
        elemField.setXmlName(new javax.xml.namespace.QName("http://emayor.org/ResidenceCertifcationRequest_v10", "input"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "eMayorServiceRequestType"));
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
