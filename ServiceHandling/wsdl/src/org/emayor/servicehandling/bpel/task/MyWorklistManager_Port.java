/**
 * MyWorklistManager_Port.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package org.emayor.servicehandling.bpel.task;

public interface MyWorklistManager_Port extends java.rmi.Remote {
    public void completeTask(org.emayor.servicehandling.bpel.task._task payload) throws java.rmi.RemoteException;
    public org.emayor.servicehandling.bpel.task._task lookupTask(java.lang.String payload) throws java.rmi.RemoteException;
    public org.emayor.servicehandling.bpel.task._tasklist listTasksByAssignee(java.lang.String payload) throws java.rmi.RemoteException;
}
