/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.kernel.bpel.forward.client;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.GenericHandler;

import org.apache.log4j.Logger;

/**
 * @author tku
 */
public class ForwardManagerBPELClientHandler extends GenericHandler {
    private static final Logger log = Logger
            .getLogger(ForwardManagerBPELClientHandler.class);

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.rpc.handler.Handler#getHeaders()
     */
    public QName[] getHeaders() {
        // TODO Auto-generated method stub
        return null;
    }

}