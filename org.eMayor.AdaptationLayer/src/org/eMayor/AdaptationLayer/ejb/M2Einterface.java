// This file implement the one and only interface of M2E which is Residence
// Request
package org.eMayor.AdaptationLayer.ejb;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface M2Einterface extends Remote {
    public String ResidenceRequest(String ResidenceCertificationRequestDocument)
            throws RemoteException;
}