/**
 * ResidenceCertifcationRequest_v10_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.emayor.ResidenceCertifcationRequest_v10;

public class ResidenceCertifcationRequest_v10_ServiceLocator extends org.apache.axis.client.Service implements org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10_Service {

    // Use to get a proxy class for ResidenceCertifcationRequest_v10Port
    private final java.lang.String ResidenceCertifcationRequest_v10Port_address = "http://koralin:9700/orabpel/default/ResidenceCertifcationRequest_v10/1.0";

    public java.lang.String getResidenceCertifcationRequest_v10PortAddress() {
        return ResidenceCertifcationRequest_v10Port_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ResidenceCertifcationRequest_v10PortWSDDServiceName = "ResidenceCertifcationRequest_v10Port";

    public java.lang.String getResidenceCertifcationRequest_v10PortWSDDServiceName() {
        return ResidenceCertifcationRequest_v10PortWSDDServiceName;
    }

    public void setResidenceCertifcationRequest_v10PortWSDDServiceName(java.lang.String name) {
        ResidenceCertifcationRequest_v10PortWSDDServiceName = name;
    }

    public org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10_Port getResidenceCertifcationRequest_v10Port() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ResidenceCertifcationRequest_v10Port_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getResidenceCertifcationRequest_v10Port(endpoint);
    }

    public org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10_Port getResidenceCertifcationRequest_v10Port(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10BindingStub _stub = new org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10BindingStub(portAddress, this);
            _stub.setPortName(getResidenceCertifcationRequest_v10PortWSDDServiceName());
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
            if (org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10_Port.class.isAssignableFrom(serviceEndpointInterface)) {
                org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10BindingStub _stub = new org.emayor.ResidenceCertifcationRequest_v10.ResidenceCertifcationRequest_v10BindingStub(new java.net.URL(ResidenceCertifcationRequest_v10Port_address), this);
                _stub.setPortName(getResidenceCertifcationRequest_v10PortWSDDServiceName());
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
        if ("ResidenceCertifcationRequest_v10Port".equals(inputPortName)) {
            return getResidenceCertifcationRequest_v10Port();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://emayor.org/ResidenceCertifcationRequest_v10", "ResidenceCertifcationRequest_v10");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("ResidenceCertifcationRequest_v10Port"));
        }
        return ports.iterator();
    }

}
