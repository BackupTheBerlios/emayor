/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.kernel.bpel.forward.client;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.xmlsoap.schemas.ws.addressing.RelatesTo;
import org.xmlsoap.schemas.ws.addressing.WSAddressingDefs;

/**
 * @author tku
 */
public class ForwardManagerBPELClientHandler extends GenericHandler {
    private static final Logger log = Logger
            .getLogger(ForwardManagerBPELClientHandler.class);

    protected QName[] headers;

    private HandlerInfo config;

    /*
     * (non-Javadoc)
     * 
     * @see javax.xml.rpc.handler.Handler#getHeaders()
     */
    public QName[] getHeaders() {
        log.debug("-> start processing ...");
        return this.headers;
    }

    public void init(HandlerInfo config) {
        log.debug("-> start processing ...");
        this.config = config;
    }

    public boolean handleRequest(MessageContext message) {
        log.debug("-> start processing ...");
        boolean ret = false;
        RelatesTo relatesTo = (RelatesTo) message
                .getProperty(WSAddressingDefs.RELATES_TO_ELEMENT);
        log.debug("got the RelatesTo data structure from context");
        if (log.isDebugEnabled())
            log.debug("got relatesTo id: " + relatesTo.toString());
        SOAPMessage soapMessage = ((SOAPMessageContext) message).getMessage();
        log.debug("got the soap message");
        relatesTo.marshall(soapMessage);
        log.debug("marshalled the relatesTo instance");
        ret = true;
        log.debug("-> ... processing DONE!");
        return ret;
    }
}