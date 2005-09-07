/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.beans;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.kernel.forward.ContentRoutingWrapper;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardMessageBPEL;
import org.emayor.servicehandling.kernel.bpel.forward.server.IForwardManagerBPEL;

/**
 * @ejb.bean name="ForwardManagerBPEL" display-name="Name for
 *           ForwardManagerBPEL" description="Description for
 *           ForwardManagerBPEL" jndi-name="ejb/eMayor/sh/ForwardManagerBPEL"
 *           type="Stateless" view-type="local"
 */
public class ForwardManagerBPEL_EJB implements SessionBean, IForwardManagerBPEL {
	private static final Logger log = Logger
			.getLogger(ForwardManagerBPEL_EJB.class);

	private SessionContext ctx;

	/**
	 *  
	 */
	public ForwardManagerBPEL_EJB() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx) throws EJBException,
			RemoteException {
		log.debug("-> start processing ...");
		this.ctx = ctx;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		log.debug("-> start processing ...");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		log.debug("-> start processing ...");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		log.debug("-> start processing ...");

	}

	private void forward(ForwardMessageBPEL message, int type) {
		log.debug("-> start processing ...");
		if (log.isDebugEnabled())
			ForwardMessageBPEL.printForwardMessage(message);
		String local = "";
		String remote = "";
		String queueName = "";
		String host = "";
		try {
			Config config = Config.getInstance();
			local = config
					.getProperty(Config.EMAYOR_PLATFORM_INSTANCE_ID);
			remote = message.getRemoteMunicipalityId();
			queueName = config.getProperty(Config.FORWARD_MANAGER_QUEUE_NAME);
			host = config.getProperty(Config.FORWARD_MANAGER_QUEUE_HOST);
			if (log.isDebugEnabled()) {
				log.debug("local       = " + local);
				log.debug("remote      = " + remote);
				log.debug("queue name  = " + queueName);
				log.debug("host        = " + host);
			}
		} catch (ConfigException confex) {
			log.error("caught ex: " + confex.toString());
		}
		Context context;

		try {
			context = new InitialContext();

			ContentRoutingWrapper content = new ContentRoutingWrapper();
			log.debug("lookup local forward ...");
			String replyTo = ContentRoutingWrapper.getAccessPoint(local,
					"forward");
			if (log.isDebugEnabled())
				log.debug("got local access point: " + replyTo);
			log.debug("lookup remote forward ...");
			String to = ContentRoutingWrapper.getAccessPoint(remote, "forward");
			if (log.isDebugEnabled())
				log.debug("got remote access point: " + to);
			log.debug("forwarding to: " + to);
			Properties props = new Properties();
			props.setProperty("java.naming.factory.initial",
					"org.jnp.interfaces.NamingContextFactory");
			props.setProperty("java.naming.factory.url.pkgs",
					"org.jboss.naming:org.jnp.interfaces");
			props.setProperty("java.naming.provider.url", host);

			log.debug("getting context: "
					+ props.getProperty("java.naming.provider.url"));
			context = new InitialContext(props);
			log.debug("getting factory ...");
			QueueConnectionFactory factory = (QueueConnectionFactory) context
					.lookup("ConnectionFactory");
			log.debug("getting connection ...");
			QueueConnection connect = factory.createQueueConnection();
			log.debug("getting queue ...");
			Queue queue = (Queue) context.lookup(queueName);
			log.debug("getting session ...");
			QueueSession session = connect.createQueueSession(false,
					QueueSession.AUTO_ACKNOWLEDGE);
			log.debug("starting connection ...");
			connect.start();
			log.debug("creating sender ...");
			QueueSender sender = session.createSender(queue);
			log.debug("get message ...");
			MapMessage msg = session.createMapMessage();
			log.debug("create message properties (REQUEST) ...");
			msg.setInt("type", type);
			msg.setString("ssid", message.getSsid());
			msg.setString("uid", message.getUid());
			msg.setString("doc", message.getDocument());
			msg.setString("doc1", message.getDocument1());
			msg.setString("doc2", message.getDocument2());
			msg.setString("doc3", message.getDocument3());
			msg.setString("doc4", message.getDocument4());
			msg.setString("serviceId", message.getServiceId());
			log.debug("sending message to queue ...");
			msg.setString("replyTo", replyTo);
			msg.setString("to", to);
			if (log.isDebugEnabled()) {
				log.debug("-------------------------------");
				log.debug("to          	: " + to);
				log.debug("replyTo     	: " + replyTo);
				log.debug("host        	: " + host);
				log.debug("doc1			: " + msg.getString("doc1"));
				log.debug("doc2			: " + msg.getString("doc2"));
				log.debug("doc3			: " + msg.getString("doc3"));
				log.debug("doc4			: " + msg.getString("doc4"));
				log.debug("-------------------------------");
			}
			sender.send(msg);
		} catch (NamingException e) {
			log.error("caught ex: " + e.toString());
			return;
		} catch (JMSException e) {
			log.debug("caught ex: " + e.toString());
			return;
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void forwardRequest(ForwardMessageBPEL message) {
		log.debug("-> start processing ...");
		ForwardMessageBPEL.printForwardMessage(message);
		forward(message, IForwardManagerBPEL.REQUEST);
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void forwardResponse(ForwardMessageBPEL message) {
		log.debug("-> start processing ...");
		ForwardMessageBPEL.printForwardMessage(message);
		forward(message, IForwardManagerBPEL.RESPONSE);
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		log.debug("-> start processing ...");
	}

}