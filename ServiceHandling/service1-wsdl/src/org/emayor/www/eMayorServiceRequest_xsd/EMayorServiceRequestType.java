/**
 * EMayorServiceRequestType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.emayor.www.eMayorServiceRequest_xsd;

public class EMayorServiceRequestType  implements java.io.Serializable {
    private java.lang.String forwarded;
    private java.lang.String status;
    private java.lang.String ssid;
    private java.lang.String uid;
    private java.lang.String reqestDocument;
    private java.lang.String serviceId;
    private java.lang.String reqDocDigSig;
    private java.lang.String extraField1;
    private java.lang.String extraField2;
    private java.lang.String extraField3;
    private java.lang.String extraField4;

    public EMayorServiceRequestType() {
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

    public java.lang.String getServiceId() {
        return serviceId;
    }

    public void setServiceId(java.lang.String serviceId) {
        this.serviceId = serviceId;
    }

    public java.lang.String getReqDocDigSig() {
        return reqDocDigSig;
    }

    public void setReqDocDigSig(java.lang.String reqDocDigSig) {
        this.reqDocDigSig = reqDocDigSig;
    }

    public java.lang.String getExtraField1() {
        return extraField1;
    }

    public void setExtraField1(java.lang.String extraField1) {
        this.extraField1 = extraField1;
    }

    public java.lang.String getExtraField2() {
        return extraField2;
    }

    public void setExtraField2(java.lang.String extraField2) {
        this.extraField2 = extraField2;
    }

    public java.lang.String getExtraField3() {
        return extraField3;
    }

    public void setExtraField3(java.lang.String extraField3) {
        this.extraField3 = extraField3;
    }

    public java.lang.String getExtraField4() {
        return extraField4;
    }

    public void setExtraField4(java.lang.String extraField4) {
        this.extraField4 = extraField4;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EMayorServiceRequestType)) return false;
        EMayorServiceRequestType other = (EMayorServiceRequestType) obj;
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
            ((this.serviceId==null && other.getServiceId()==null) || 
             (this.serviceId!=null &&
              this.serviceId.equals(other.getServiceId()))) &&
            ((this.reqDocDigSig==null && other.getReqDocDigSig()==null) || 
             (this.reqDocDigSig!=null &&
              this.reqDocDigSig.equals(other.getReqDocDigSig()))) &&
            ((this.extraField1==null && other.getExtraField1()==null) || 
             (this.extraField1!=null &&
              this.extraField1.equals(other.getExtraField1()))) &&
            ((this.extraField2==null && other.getExtraField2()==null) || 
             (this.extraField2!=null &&
              this.extraField2.equals(other.getExtraField2()))) &&
            ((this.extraField3==null && other.getExtraField3()==null) || 
             (this.extraField3!=null &&
              this.extraField3.equals(other.getExtraField3()))) &&
            ((this.extraField4==null && other.getExtraField4()==null) || 
             (this.extraField4!=null &&
              this.extraField4.equals(other.getExtraField4())));
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
        if (getServiceId() != null) {
            _hashCode += getServiceId().hashCode();
        }
        if (getReqDocDigSig() != null) {
            _hashCode += getReqDocDigSig().hashCode();
        }
        if (getExtraField1() != null) {
            _hashCode += getExtraField1().hashCode();
        }
        if (getExtraField2() != null) {
            _hashCode += getExtraField2().hashCode();
        }
        if (getExtraField3() != null) {
            _hashCode += getExtraField3().hashCode();
        }
        if (getExtraField4() != null) {
            _hashCode += getExtraField4().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EMayorServiceRequestType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "eMayorServiceRequestType"));
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
        elemField.setFieldName("serviceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "serviceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reqDocDigSig");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "reqDocDigSig"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extraField1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "extraField1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extraField2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "extraField2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extraField3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "extraField3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extraField4");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.emayor.org/eMayorServiceRequest.xsd", "extraField4"));
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
