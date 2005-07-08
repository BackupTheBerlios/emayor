/*
 * $ Created on Jul 8, 2005 by tku $
 */
package org.emayor.servicehandling.kernel.forward;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public interface IForwardService extends javax.xml.rpc.Service {
	
	public java.lang.String getForwardManagerAddress();

	public IForward getForwardManager() throws javax.xml.rpc.ServiceException;

	public IForward getForwardManager(java.net.URL portAddress)
			throws javax.xml.rpc.ServiceException;
}

