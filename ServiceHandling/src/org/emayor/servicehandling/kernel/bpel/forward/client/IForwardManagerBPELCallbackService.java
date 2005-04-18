/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.kernel.bpel.forward.client;

import java.net.URL;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

/**
 * @author tku
 */
public interface IForwardManagerBPELCallbackService extends Service {

    public String getForwardManagerBPELCallbackPortAddress();

    public IForwardManagerBPELCallback getForwardManagerBPELCallbackPort()
            throws ServiceException;

    public IForwardManagerBPELCallback getForwardManagerBPELCallbackPort(
            URL portAddress) throws ServiceException;

}