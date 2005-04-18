/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.kernel.bpel.forward.client;

import java.rmi.Remote;

import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardMessageBPEL;

/**
 * @author tku
 */
public interface IForwardManagerBPELCallback extends Remote {

    public void onResult(ForwardMessageBPEL fResponse)
            throws java.rmi.RemoteException;
}