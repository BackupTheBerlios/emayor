/**
 * ResidenceCertifcationRequest_v10CallbackServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.emayor.ResidenceCertifcationRequest_v10;

public class ResidenceCertifcationRequest_v10CallbackServiceLocator extends org.apache.axis.client.Service implements org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10CallbackService {

    // Use to get a proxy class for ResidenceCertifcationRequest_v10CallbackPort
    private final java.lang.String ResidenceCertifcationRequest_v10CallbackPort_address = "http://set.by.caller";

    public java.lang.String getResidenceCertifcationRequest_v10CallbackPortAddress() {
        return ResidenceCertifcationRequest_v10CallbackPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ResidenceCertifcationRequest_v10CallbackPortWSDDServiceName = "ResidenceCertifcationRequest_v10CallbackPort";

    public java.lang.String getResidenceCertifcationRequest_v10CallbackPortWSDDServiceName() {
        return ResidenceCertifcationRequest_v10CallbackPortWSDDServiceName;
    }

    public void setResidenceCertifcationRequest_v10CallbackPortWSDDServiceName(java.lang.String name) {
        ResidenceCertifcationRequest_v10CallbackPortWSDDServiceName = name;
    }

    public org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10Callback getResidenceCertifcationRequest_v10CallbackPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ResidenceCertifcationRequest_v10CallbackPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getResidenceCertifcationRequest_v10CallbackPort(endpoint);
    }

    public org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10Callback getResidenceCertifcationRequest_v10CallbackPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10CallbackBindingStub _stub = new org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10CallbackBindingStub(portAddress, this);
            _stub.setPortName(getResidenceCertifcationRequest_v10CallbackPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10Callback.class.isAssignableFrom(serviceEndpointInterface)) {
                org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10CallbackBindingStub _stub = new org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10CallbackBindingStub(new java.net.URL(ResidenceCertifcationRequest_v10CallbackPort_address), this);
                _stub.setPortName(getResidenceCertifcationRequest_v10CallbackPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("ResidenceCertifcationRequest_v10CallbackPort".equals(inputPortName)) {
            return getResidenceCertifcationRequest_v10CallbackPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://emayor.org/ResidenceCertifcationRequest_v10", "ResidenceCertifcationRequest_v10CallbackService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("ResidenceCertifcationRequest_v10CallbackPort"));
        }
        return ports.iterator();
    }

}
