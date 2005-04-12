/*
 * $ Created on Feb 9, 2005 by tku $
 */
package org.emayor.servicehandling.kernel;

import java.rmi.Remote;

/**
 * @author <a href="mailto:Tomasz.Kusber@fokus.fraunhofer.de"><font size="-1">Tomasz Kusber</font></a><font size="-1"> FHI FOKUS (C)</font>
 */
public interface IForward extends Remote {
	
	public void forwardRequest(ForwardMessage forwardMessage) throws ForwardException;
	
	public void forwardReplay(ForwardMessage forwardMessage) throws ForwardException;
}
