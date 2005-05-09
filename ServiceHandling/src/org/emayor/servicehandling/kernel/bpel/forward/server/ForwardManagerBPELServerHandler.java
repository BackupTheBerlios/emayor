/*
 * Created on Apr 18, 2005
 */
package org.emayor.servicehandling.kernel.bpel.forward.server;

import java.util.Iterator;

import javax.ejb.RemoveException;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardBPELCallbackData;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;
import org.xmlsoap.schemas.ws.addressing.MessageID;
import org.xmlsoap.schemas.ws.addressing.ReplyTo;
import org.xmlsoap.schemas.ws.addressing.WSAddressingDefs;

/**
 * @author tku
 */
public class ForwardManagerBPELServerHandler extends GenericHandler {
	private final static Logger log = Logger
			.getLogger(ForwardManagerBPELServerHandler.class);

	private static final String SERVICE_NAMESPACE = "http://eMayor.org/sh/bpel/forward/service";

	private static final String DATA_NAMESPACE = "http://eMayor.org/sh/bpel/forward/data";

	private static String SSID_ELEMENT_NAME;

	private static String UID_ELEMENT_NAME;

	protected QName[] headers;

	static {
		SSID_ELEMENT_NAME = "ssid";
		UID_ELEMENT_NAME = "uid";
	}

	public void init(HandlerInfo config) {
		log.debug("-> start processing ...");
		headers = config.getHeaders();
		log.debug("-> ... processing DONE!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.xml.rpc.handler.Handler#getHeaders()
	 */
	public QName[] getHeaders() {
		log.debug("-> start processing ...");
		return this.headers;
	}

	public boolean handleRequest(MessageContext context) {
		log.debug("-> start processing ...");
		boolean ret = false;
		ForwardBPELCallbackData data = new ForwardBPELCallbackData();
		try {
			SOAPMessageContext messageContext = (SOAPMessageContext) context;
			org.apache.axis.MessageContext mc = (org.apache.axis.MessageContext) context;
			String soapActionURI = mc.getSOAPActionURI().trim();
			if (log.isDebugEnabled())
				log.debug("got from request action uri = " + soapActionURI);
			if ((soapActionURI != null)
					&& (soapActionURI.equalsIgnoreCase("\"forwardRequest\""))) {
				log
						.debug("it is forwardRequest -> try to retrieve the addressing data");
				SOAPMessage message = messageContext.getMessage();
				SOAPPart soapPart = message.getSOAPPart();
				SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
				SOAPHeader soapHeader = soapEnvelope.getHeader();
				SOAPBody soapBody = message.getSOAPBody();

				Name name = soapEnvelope.createName("fRequest", "",
						SERVICE_NAMESPACE);

				Iterator it = soapBody.getChildElements(name);
				String ssid = "";
				while (it.hasNext()) {
					log.debug("found the payload element");
					SOAPElement element = (SOAPElement) it.next();
					name = soapEnvelope.createName(SSID_ELEMENT_NAME, "",
							DATA_NAMESPACE);
					Iterator it2 = element.getChildElements(name);
					while (it2.hasNext()) {
						log.debug("found ssid !");
						SOAPElement element1 = (SOAPElement) it2.next();
						ssid = element1.getValue();
						if (log.isDebugEnabled())
							log.debug("got ssid = " + ssid);
						data.setSsid(ssid);
					}
					name = soapEnvelope.createName(UID_ELEMENT_NAME, "",
							DATA_NAMESPACE);
					it2 = element.getChildElements(name);
					while (it2.hasNext()) {
						log.debug("found uid !");
						SOAPElement element1 = (SOAPElement) it2.next();
						String tmp = element1.getValue();
						if (log.isDebugEnabled())
							log.debug("got uid = " + tmp);
						data.setUid(tmp);
					}
				}

				name = soapEnvelope.createName(
						WSAddressingDefs.MESSAGE_ID_ELEMENT, "ns1",
						WSAddressingDefs.NAMESPACE);
				it = soapHeader.getChildElements(name);
				while (it.hasNext()) {
					SOAPElement element = (SOAPElement) it.next();
					MessageID messageID = new MessageID();
					messageID.unmarshall(element);
					data.setMessageID(messageID);
					if (log.isDebugEnabled())
						log.debug("found message id: " + messageID.toString());
				}

				name = soapEnvelope.createName(
						WSAddressingDefs.REPLY_TO_ELEMENT, "ns3",
						WSAddressingDefs.NAMESPACE);
				it = soapHeader.getChildElements(name);
				while (it.hasNext()) {
					SOAPElement element = (SOAPElement) it.next();
					ReplyTo replyTo = new ReplyTo();
					replyTo.unmarshall(element);
					data.setReplyTo(replyTo);
					if (log.isDebugEnabled())
						log.debug("found replyTo: address: "
								+ replyTo.getAddress());
				}
				ServiceLocator locator = ServiceLocator.getInstance();
				KernelLocal kernel = locator.getKernelLocal();
				if (log.isDebugEnabled())
					log.debug("adding the callbackdata for ssid= "
							+ data.getSsid());
				kernel.addForwardBPELCallbackData(data);
				kernel.remove();
				ret = true;
			} else {
				log.debug("it wasn't forwardRequest -> so continue!");
				ret = true;
			}
		} catch (SOAPException soapex) {
			log.error("caught ex: " + soapex.toString());
			ret = false;
		} catch (ServiceLocatorException slex) {
			log.error("caught ex: " + slex.toString());
			ret = false;
		} catch (KernelException kex) {
			log.error("caught ex: " + kex.toString());
			ret = false;
		} catch (RemoveException rex) {
			log.error("caught ex: " + rex.toString());
			ret = false;
		}
		log.debug("-> ... processing DONE!");
		return ret;
	}
}