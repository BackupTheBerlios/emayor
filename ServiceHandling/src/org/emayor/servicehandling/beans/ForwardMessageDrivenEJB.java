/*
 * $ Created on Jul 8, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.forward.ArrayOfStrings;
import org.emayor.servicehandling.kernel.forward.ForwardMessage;
import org.emayor.servicehandling.kernel.forward.ForwardingRepository;
import org.emayor.servicehandling.kernel.forward.ForwardingRepositoryException;
import org.emayor.servicehandling.kernel.forward.IForwardService;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @ejb.bean name="ForwardMessageDriven" display-name="Name for
 *           ForwardMessageDriven" description="Description for
 *           ForwardMessageDriven" destination-type="javax.jms.Queue"
 *           acknowledge-mode="Auto-acknowledge"
 * @jboss.destination-jndi-name name = "queue/eMayor-ForwardQueue"
 */
public class ForwardMessageDrivenEJB implements MessageDrivenBean,
		MessageListener {
	private static final Logger log = Logger
			.getLogger(ForwardMessageDrivenEJB.class);

	public static final int REQUEST = 1;

	public static final int RESPONSE = 2;

	private MessageDrivenContext context;

	static org.apache.axis.description.OperationDesc [] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[2];
		org.apache.axis.description.OperationDesc oper;
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("forwardRequest");
		oper.addParameter(new javax.xml.namespace.QName(
				"uri:org.emayor.servicehandling.kernel.forward",
				"forwardRequestElement"), new javax.xml.namespace.QName(
				"uri:org.emayor.servicehandling.kernel.forward",
				"ForwardMessage"),
				org.emayor.servicehandling.kernel.forward.ForwardMessage.class,
				org.apache.axis.description.ParameterDesc.IN, false, false);
		oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
		oper.setStyle(org.apache.axis.enum.Style.DOCUMENT);
		oper.setUse(org.apache.axis.enum.Use.LITERAL);
		_operations[0] = oper;

		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("forwardResponse");
		oper.addParameter(new javax.xml.namespace.QName(
				"uri:org.emayor.servicehandling.kernel.forward",
				"forwardResponseElement"), new javax.xml.namespace.QName(
				"uri:org.emayor.servicehandling.kernel.forward",
				"ForwardMessage"),
				org.emayor.servicehandling.kernel.forward.ForwardMessage.class,
				org.apache.axis.description.ParameterDesc.IN, false, false);
		oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
		oper.setStyle(org.apache.axis.enum.Style.DOCUMENT);
		oper.setUse(org.apache.axis.enum.Use.LITERAL);
		_operations[1] = oper;

	}

	/**
	 *  
	 */
	public ForwardMessageDrivenEJB() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.MessageDrivenBean#setMessageDrivenContext(javax.ejb.MessageDrivenContext)
	 */
	public void setMessageDrivenContext(MessageDrivenContext ctx)
			throws EJBException {
		this.context = ctx;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.MessageDrivenBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		log.debug("-> starting processing ...");
		MapMessage msg = (MapMessage) message;

		try {
			log.debug("get message type ...");
			int type = msg.getInt("type");
			log.debug("get remote address ...");
			String host = msg.getString("to");
			if (log.isDebugEnabled())
				log.debug("working with following host: " + host);
			log.debug("lookup context ...");
			ServiceLocator locator = ServiceLocator.getInstance();
			IForwardService service = locator.getIForwardService();
			log.debug("create call ...");
			Call call = (Call) service.createCall();
			log.debug("set call properties ...");
			call.setUseSOAPAction(true);
			call.setSOAPActionURI("");
			call.setEncodingStyle(null);
			call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
					Boolean.FALSE);
			call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
					Boolean.FALSE);
			call
					.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);

			switch (type) {
			case REQUEST: {
				if (log.isDebugEnabled())
					log.debug("send request: " + host);
				log.debug("create message ...");
				ForwardMessage msg2 = new ForwardMessage();
				msg2.setType(msg.getInt("type"));
				msg2.setReplyAddress(msg.getString("replyTo"));
				msg2.setReplyID(msg.getString("uid"));
				msg2.setReplyService(msg.getString("ssid"));
				msg2.setServiceId(msg.getString("serviceId"));
				ArrayOfStrings array = new ArrayOfStrings();
				String[] documents = new String[5];
				documents[0] = msg.getString("doc");
				documents[1] = msg.getString("doc1");
				documents[2] = msg.getString("doc2");
				documents[3] = msg.getString("doc3");
				documents[4] = msg.getString("doc4");
				array.setItem(documents);
				msg2.setDocuments(array);
				call.setTargetEndpointAddress(host);
				call.setOperation(_operations[0]);
				call.setOperationName(new javax.xml.namespace.QName(
						"uri:org.emayor.servicehandling.kernel.forward",
						"forwardRequest"));
				call.invokeOneWay(new Object[] { msg2 });
				log.debug("invoked one way forwardRequest operation!");
				break;
			}
			case RESPONSE: {
				if (log.isDebugEnabled())
					log.debug("send response: " + host);
				log.debug("create message ...");
				ForwardMessage msg2 = new ForwardMessage();
				String localssid = msg.getString("ssid");
				String localuid = msg.getString("uid");
				ForwardMessage request = null;
				try {
					ForwardingRepository repos = ForwardingRepository
							.getInstance();
					request = repos.retrieveForwardRequest(localssid);
				} catch (ForwardingRepositoryException ex) {
					log.error("caught ex: " + ex.toString());
				}
				String ssid = request.getReplyService();
				String uid = request.getReplyID();
				String reqDoc = request.getDocuments().getItem(0);
				if (log.isDebugEnabled()) {
					log.debug("local ssid   : " + localssid);
					log.debug("local uid    : " + localuid);
					log.debug("origin ssid  : " + ssid);
					log.debug("origin uid   : " + uid);
				}
				msg2.setType(msg.getInt("type"));
				msg2.setReplyAddress(msg.getString("replyTo"));
				msg2.setReplyID(uid);
				msg2.setReplyService(ssid);
				ArrayOfStrings array = new ArrayOfStrings();
				String[] documents = new String[5];
				documents[0] = msg.getString("doc");
				documents[1] = msg.getString("doc1");
				documents[2] = msg.getString("doc2");
				documents[3] = msg.getString("doc3");
				// the request will be saved as doc4
				documents[4] = reqDoc;
				msg2.setServiceId(msg.getString("serviceId"));
				array.setItem(documents);
				msg2.setDocuments(array);
				if (log.isDebugEnabled())
					log.debug("got from request replyTo: "
							+ request.getReplyAddress());
				call.setTargetEndpointAddress(request.getReplyAddress());
				log.debug("operation settings ...");
				call.setOperation(_operations[1]);
				call.setOperationName(new javax.xml.namespace.QName(
						"uri:org.emayor.servicehandling.kernel.forward",
						"forwardResponse"));
				call.invokeOneWay(new Object[] { msg2 });
				log.debug("invoked one way forwardRequest operation!");
				break;
			}
			default: {
				log.error("got undefined type...");
			}
			}
		} catch (JMSException e) {
			log.error("caught ex: " + e.toString());
		} catch (ServiceException e) {
			log.error("caught ex: " + e.toString());
		} catch (ServiceLocatorException ex) {
			log.error("caught ex: " + ex.toString());
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 */
	public void ejbCreate() {
		// TODO Auto-generated method stub
	}
}