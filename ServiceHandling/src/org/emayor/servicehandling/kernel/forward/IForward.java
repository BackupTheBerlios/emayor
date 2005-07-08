/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel.forward;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"><font size="-1">Tomasz Kusber</font></a><font size="-1"> FHI FOKUS (C)</font>
 */
public interface IForward extends Remote {
	
	public static final int REQUEST = 1;
	public static final int RESPONSE = 2;
	
	public void forwardRequest(ForwardMessage forwardMessage) throws RemoteException;
	
	public void forwardResponse(ForwardMessage forwardMessage) throws RemoteException;
}
