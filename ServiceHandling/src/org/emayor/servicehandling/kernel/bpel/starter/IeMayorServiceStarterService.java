/*
 * $ Created on Jun 1, 2005 by tku $
 */
package org.emayor.servicehandling.kernel.bpel.starter;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"> <font
 *         size="-1">Tomasz Kusber </font> </a> <font size="-1"> FHI FOKUS (C)
 *         </font>
 */
public interface IeMayorServiceStarterService extends javax.xml.rpc.Service {
	public java.lang.String geteMayorServiceStarter_v10PortAddress();

	public org.emayor.servicehandling.kernel.bpel.starter.IeMayorServiceStarter geteMayorServiceStarter_v10Port()
			throws javax.xml.rpc.ServiceException;

	public org.emayor.servicehandling.kernel.bpel.starter.IeMayorServiceStarter geteMayorServiceStarter_v10Port(
			java.net.URL portAddress) throws javax.xml.rpc.ServiceException;

}