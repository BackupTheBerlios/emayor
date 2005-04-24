/*
 * Created on Apr 18, 2005
 */
package org.emayor.servicehandling.kernel.bpel.forward.server;

import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardMessageBPEL;

/**
 * @author tku
 */
public interface IForwardManagerBPEL {
	
	public static final int REQUEST  = 1;
	public static final int RESPONSE = 2;
	
    public void forwardRequest(ForwardMessageBPEL message);

    public void forwardResponse(ForwardMessageBPEL message);
}