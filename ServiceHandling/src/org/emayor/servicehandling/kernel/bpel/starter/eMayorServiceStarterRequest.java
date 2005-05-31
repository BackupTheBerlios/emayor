/*
 * $ Created on Jun 1, 2005 by tku $
 */
package org.emayor.servicehandling.kernel.bpel.starter;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public class eMayorServiceStarterRequest implements java.io.Serializable {
	private java.lang.String eMayorServiceId;

	private org.emayor.servicehandling.kernel.bpel.service.eMayorServiceRequest eMayorServiceRequest;

	public eMayorServiceStarterRequest() {
	}

	public java.lang.String getEMayorServiceId() {
		return eMayorServiceId;
	}

	public void setEMayorServiceId(java.lang.String eMayorServiceId) {
		this.eMayorServiceId = eMayorServiceId;
	}

	public org.emayor.servicehandling.kernel.bpel.service.eMayorServiceRequest getEMayorServiceRequest() {
		return eMayorServiceRequest;
	}

	public void setEMayorServiceRequest(
			org.emayor.servicehandling.kernel.bpel.service.eMayorServiceRequest eMayorServiceRequest) {
		this.eMayorServiceRequest = eMayorServiceRequest;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof eMayorServiceStarterRequest))
			return false;
		eMayorServiceStarterRequest other = (eMayorServiceStarterRequest) obj;
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
				&& ((this.eMayorServiceId == null && other.getEMayorServiceId() == null) || (this.eMayorServiceId != null && this.eMayorServiceId
						.equals(other.getEMayorServiceId())))
				&& ((this.eMayorServiceRequest == null && other
						.getEMayorServiceRequest() == null) || (this.eMayorServiceRequest != null && this.eMayorServiceRequest
						.equals(other.getEMayorServiceRequest())));
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
		if (getEMayorServiceId() != null) {
			_hashCode += getEMayorServiceId().hashCode();
		}
		if (getEMayorServiceRequest() != null) {
			_hashCode += getEMayorServiceRequest().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			eMayorServiceStarterRequest.class);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName(
				"http://www.emayor.org/eMayorServiceStarterRequest.xsd",
				"eMayorServiceStarterRequestType"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EMayorServiceId");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://www.emayor.org/eMayorServiceStarterRequest.xsd",
				"eMayorServiceId"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.w3.org/2001/XMLSchema", "string"));
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("EMayorServiceRequest");
		elemField.setXmlName(new javax.xml.namespace.QName(
				"http://www.emayor.org/eMayorServiceStarterRequest.xsd",
				"eMayorServiceRequest"));
		elemField.setXmlType(new javax.xml.namespace.QName(
				"http://www.emayor.org/eMayorServiceRequest.xsd",
				"eMayorServiceRequestType"));
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