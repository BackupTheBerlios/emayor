/*
 * $ Created on Jun 1, 2005 by tku $
 */
package org.emayor.servicehandling.kernel.bpel.starter;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public interface IeMayorServiceStarter extends java.rmi.Remote {
	public void initiate(eMayorServiceStarterProcessRequest payload)
			throws java.rmi.RemoteException;
}

