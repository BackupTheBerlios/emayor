/**
 * EMayorServiceRequestStructure.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.emayor.www.eMayorServiceRequest_xsd;

public class EMayorServiceRequestStructure  implements java.io.Serializable {
    private java.lang.String forwarded;
    private java.lang.String status;
    private java.lang.String ssid;
    private java.lang.String uid;
    private java.lang.String reqestDocument;
    private java.lang.String reqDocDigSig;

    public EMayorServiceRequestStructure() {
    }

    public java.lang.String getForwarded() {
        return forwarded;
    }

    public void setForwarded(java.lang.String forwarded) {
        this.forwarded = forwarded;
    }

    public java.lang.String getStatus() {
        return status;
    }

    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    public java.lang.String getSsid() {
        return ssid;
    }

    public void setSsid(java.lang.String ssid) {
        this.ssid = ssid;
    }

    public java.lang.String getUid() {
        return uid;
    }

    public void setUid(java.lang.String uid) {
        this.uid = uid;
    }

    public java.lang.String getReqestDocument() {
        return reqestDocument;
    }

    public void setReqestDocument(java.lang.String reqestDocument) {
        this.reqestDocument = reqestDocument;
    }

    public java.lang.String getReqDocDigSig() {
        return reqDocDigSig;
    }

    public void setReqDocDigSig(java.lang.String reqDocDigSig) {
        this.reqDocDigSig = reqDocDigSig;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EMayorServiceRequestStructure)) return false;
        EMayorServiceRequestStructure other = (EMayorServiceRequestStructure) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.forwarded==null && other.getForwarded()==null) || 
             (this.forwarded!=null &&
              this.forwarded.equals(other.getForwarded()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.ssid==null && other.getSsid()==null) || 
             (this.ssid!=null &&
              this.ssid.equals(other.getSsid()))) &&
            ((this.uid==null && other.getUid()==null) || 
             (this.uid!=null &&
              this.uid.equals(other.getUid()))) &&
            ((this.reqestDocument==null && other.getReqestDocument()==null) || 
             (this.reqestDocument!=null &&
              this.reqestDocument.equals(other.getReqestDocument()))) &&
            ((this.reqDocDigSig==null && other.getReqDocDigSig()==null) || 
             (this.reqDocDigSig!=null &&
              this.reqDocDigSig.equals(other.getReqDocDigSig())));
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
        if (getForwarded() != null) {
            _hashCode += getForwarded().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSsid() != null) {
            _hashCode += getSsid().hashCode();
        }
        if (getUid() != null) {
            _hashCode += getUid().hashCode();
        }
        if (getReqestDocument() != null) {
            _hashCode += getReqestDocument().hashCode();
        }
        if (getReqDocDigSig() != null) {
            _hashCode += getReqDocDigSig().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EMayorServiceRequestStructure.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "eMayorServiceRequestStructure"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("forwarded");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "forwarded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ssid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "ssid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "uid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reqestDocument");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "reqestDocument"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reqDocDigSig");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "reqDocDigSig"));
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
