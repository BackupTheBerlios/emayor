/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.kernel.bpel.forward.client;

import javax.ejb.RemoveException;
import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardBPELCallbackData;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardMessageBPEL;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;
import org.xmlsoap.schemas.ws.addressing.MessageID;
import org.xmlsoap.schemas.ws.addressing.RelatesTo;
import org.xmlsoap.schemas.ws.addressing.ReplyTo;
import org.xmlsoap.schemas.ws.addressing.WSAddressingDefs;

/**
 * @author tku
 */
public class ForwardBPELServiceCallbackInvoker {
    private static final Logger log = Logger
            .getLogger(ForwardBPELServiceCallbackInvoker.class);

    private static String THIS_NAMESPACE = "http://eMayor.org/sh/bpel/forward/service";

    private static String PARAMETER_NAMESPACE = "http://eMayor.org/sh/bpel/forward/data";

    private static String SOAP_ACTION = "onResult";

    private static String STYLE = "document";
    
    private static String ADDRESS;

    private static QName SERVICE_NAME;

    private static QName PORT_TYPE;

    private static QName PORT;

    private static QName OPERATION_NAME;

    static {
        SERVICE_NAME = new QName(THIS_NAMESPACE,
                "IForwardManagerBPELCallbackService");
        PORT_TYPE = new QName(THIS_NAMESPACE, "IForwardManagerBPELCallback");
        PORT = new QName(THIS_NAMESPACE, "ForwardManagerBPELCallbackPort");
        OPERATION_NAME = new QName(THIS_NAMESPACE, "onResult");
        ADDRESS = "http://localhost:19700/orabpel/default/ForwardRequest_v10/1.0/forwardPL/IForwardManagerBPELServiceRequester";
    }

    public void invoke(ForwardMessageBPEL msg)
            throws ForwardManagerBPELClientException {
        log.debug("-> start processing ...");
        try {
            ServiceLocator locator = ServiceLocator.getInstance();
            KernelLocal kernel = locator.getKernelLocal();
            log.debug("obtained the ref to the kernel");
            ForwardBPELCallbackData data = kernel
                    .getForwardBPELCallbackData(msg.getSsid());
            log.debug("got the callback data from repository");
            kernel.remove();
            log.debug("removed the kernel reference");
            ReplyTo replyTo = data.getReplyTo();
            log.debug("got the ReplyTo data structure");
            MessageID messageID = data.getMessageID();
            log.debug("got the MessageID data structure");
            RelatesTo relatesTo = new RelatesTo(messageID.toString());
            log.debug("the RelatesTo data stucture has been created");
            IForwardManagerBPELCallbackService service = locator
                    .getIForwardManagerBPELCallbackService();
            log.debug("got the client service interface");
            Call call = service.createCall(PORT, OPERATION_NAME);
            if (call != null) {
                log.debug("the call has been created -> go ahead");
                call.setProperty("SSID", msg.getSsid());
                call
                        .setProperty(WSAddressingDefs.RELATES_TO_ELEMENT,
                                relatesTo);
                if (log.isDebugEnabled())
                    log.debug("set tarrget endpoint to: "
                            + replyTo.getAddress());
                call.setTargetEndpointAddress(replyTo.getAddress());
                //call.setTargetEndpointAddress(ADDRESS);
                call.setProperty(Call.SOAPACTION_USE_PROPERTY,
                        new Boolean(true));
                if (log.isDebugEnabled())
                    log.debug("set the SOAPACTION_URI_PROPERTY to: "
                            + SOAP_ACTION);
                call.setProperty(Call.SOAPACTION_URI_PROPERTY, SOAP_ACTION);
                if (log.isDebugEnabled())
                    log.debug("set the OPERATION_STYLE_PROPERTY to: " + STYLE);
                call.setProperty(Call.OPERATION_STYLE_PROPERTY, STYLE);
                log.debug("set the payload data");
                Object[] value = new Object[1];
                value[0] = msg;
                log.debug("invoking the one way call!");
                call.invokeOneWay(value);
            } else {
                log.debug("sorry, couldn't create the call: call = null");
            }
        } catch (ServiceLocatorException slex) {
            log.error("caught ex: " + slex.toString());
            throw new ForwardManagerBPELClientException(slex);
        } catch (ServiceException sex) {
            log.error("caught ex: " + sex.toString());
            throw new ForwardManagerBPELClientException(sex);
        } catch (KernelException kex) {
            log.error("caught ex: " + kex.toString());
            throw new ForwardManagerBPELClientException(kex);
        } catch (RemoveException rex) {
            log.error("caught ex: " + rex.toString());
            throw new ForwardManagerBPELClientException(rex);
        }
        log.debug("-> ... processing DONE!");
    }
}