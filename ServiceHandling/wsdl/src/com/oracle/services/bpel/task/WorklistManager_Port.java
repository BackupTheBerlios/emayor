/**
 * WorklistManager_Port.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.oracle.services.bpel.task;

public interface WorklistManager_Port extends java.rmi.Remote {
    public void initiateTask(com.oracle.services.bpel.task._task payload) throws java.rmi.RemoteException;
    public void updateTask(com.oracle.services.bpel.task._task payload) throws java.rmi.RemoteException;
    public void completeTask(com.oracle.services.bpel.task._task payload) throws java.rmi.RemoteException;
    public com.oracle.services.bpel.task._task lookupTask(java.lang.String payload) throws java.rmi.RemoteException;
    public com.oracle.services.bpel.task._tasklist listTasks(com.oracle.services.bpel.task._whereCondition payload) throws java.rmi.RemoteException;
}
