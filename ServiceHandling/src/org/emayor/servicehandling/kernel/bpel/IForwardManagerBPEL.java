/*
 * Created on Apr 18, 2005
 */
package org.emayor.servicehandling.kernel.bpel;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author tku
 */
public interface IForwardManagerBPEL extends Remote {
    public void forwardRequest(ForwardMessageBPEL message)
            throws RemoteException;

    public void forwardResponse(ForwardMessageBPEL message)
            throws RemoteException;
}