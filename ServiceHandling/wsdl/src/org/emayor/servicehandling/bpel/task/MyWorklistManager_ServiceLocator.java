/**
 * MyWorklistManager_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.emayor.servicehandling.bpel.task;

public class MyWorklistManager_ServiceLocator extends org.apache.axis.client.Service implements org.emayor.servicehandling.bpel.task.MyWorklistManager_Service {

    // Use to get a proxy class for MyWorklistManagerPort
    private final java.lang.String MyWorklistManagerPort_address = "http://koralin:9700/orabpel/default/MyWorklistManager/1.0";

    public java.lang.String getMyWorklistManagerPortAddress() {
        return MyWorklistManagerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MyWorklistManagerPortWSDDServiceName = "MyWorklistManagerPort";

    public java.lang.String getMyWorklistManagerPortWSDDServiceName() {
        return MyWorklistManagerPortWSDDServiceName;
    }

    public void setMyWorklistManagerPortWSDDServiceName(java.lang.String name) {
        MyWorklistManagerPortWSDDServiceName = name;
    }

    public org.emayor.servicehandling.bpel.task.MyWorklistManager_Port getMyWorklistManagerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MyWorklistManagerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMyWorklistManagerPort(endpoint);
    }

    public org.emayor.servicehandling.bpel.task.MyWorklistManager_Port getMyWorklistManagerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.emayor.servicehandling.bpel.task.MyWorklistManagerBindingStub _stub = new org.emayor.servicehandling.bpel.task.MyWorklistManagerBindingStub(portAddress, this);
            _stub.setPortName(getMyWorklistManagerPortWSDDServiceName());
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
            if (org.emayor.servicehandling.bpel.task.MyWorklistManager_Port.class.isAssignableFrom(serviceEndpointInterface)) {
                org.emayor.servicehandling.bpel.task.MyWorklistManagerBindingStub _stub = new org.emayor.servicehandling.bpel.task.MyWorklistManagerBindingStub(new java.net.URL(MyWorklistManagerPort_address), this);
                _stub.setPortName(getMyWorklistManagerPortWSDDServiceName());
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
        if ("MyWorklistManagerPort".equals(inputPortName)) {
            return getMyWorklistManagerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://emayor.org/servicehandling/bpel/task", "MyWorklistManager");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("MyWorklistManagerPort"));
        }
        return ports.iterator();
    }

}
