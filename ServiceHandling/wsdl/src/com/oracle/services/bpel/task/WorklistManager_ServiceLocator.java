/**
 * WorklistManager_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.oracle.services.bpel.task;

public class WorklistManager_ServiceLocator extends org.apache.axis.client.Service implements com.oracle.services.bpel.task.WorklistManager_Service {

    // Use to get a proxy class for WorklistManagerPort
    private final java.lang.String WorklistManagerPort_address = "http://localhost:9700/orabpel/default/WorklistManager/1.0";

    public java.lang.String getWorklistManagerPortAddress() {
        return WorklistManagerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WorklistManagerPortWSDDServiceName = "WorklistManagerPort";

    public java.lang.String getWorklistManagerPortWSDDServiceName() {
        return WorklistManagerPortWSDDServiceName;
    }

    public void setWorklistManagerPortWSDDServiceName(java.lang.String name) {
        WorklistManagerPortWSDDServiceName = name;
    }

    public com.oracle.services.bpel.task.WorklistManager_Port getWorklistManagerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WorklistManagerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWorklistManagerPort(endpoint);
    }

    public com.oracle.services.bpel.task.WorklistManager_Port getWorklistManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.oracle.services.bpel.task.WorklistManagerBindingStub _stub = new com.oracle.services.bpel.task.WorklistManagerBindingStub(portAddress, this);
            _stub.setPortName(getWorklistManagerPortWSDDServiceName());
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
            if (com.oracle.services.bpel.task.WorklistManager_Port.class.isAssignableFrom(serviceEndpointInterface)) {
                com.oracle.services.bpel.task.WorklistManagerBindingStub _stub = new com.oracle.services.bpel.task.WorklistManagerBindingStub(new java.net.URL(WorklistManagerPort_address), this);
                _stub.setPortName(getWorklistManagerPortWSDDServiceName());
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
        if ("WorklistManagerPort".equals(inputPortName)) {
            return getWorklistManagerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services.oracle.com/bpel/task", "WorklistManager");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("WorklistManagerPort"));
        }
        return ports.iterator();
    }

}
