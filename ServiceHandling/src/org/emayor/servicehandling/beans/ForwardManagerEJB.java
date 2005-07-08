/*
 * $ Created on Jul 8, 2005 by tku $
 */
package org.emayor.servicehandling.beans;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Iterator;
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
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.handler.MessageContext;

import org.apache.log4j.Logger;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;
import org.emayor.servicehandling.interfaces.AccessManagerLocal;
import org.emayor.servicehandling.kernel.AccessException;
import org.emayor.servicehandling.kernel.bpel.forward.client.ForwardBPELServiceCallbackInvoker;
import org.emayor.servicehandling.kernel.bpel.forward.client.ForwardManagerBPELClientException;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardMessageBPEL;
import org.emayor.servicehandling.kernel.forward.ContentRoutingWrapper;
import org.emayor.servicehandling.kernel.forward.ForwardMessage;
import org.emayor.servicehandling.kernel.forward.ForwardingRepository;
import org.emayor.servicehandling.kernel.forward.ForwardingRepositoryException;
import org.emayor.servicehandling.kernel.forward.IForward;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;

/**
 * @ejb.bean name="ForwardManager" display-name="Name for ForwardManager"
 *           description="Description for ForwardManager"
 *           jndi-name="ejb/eMayor/sh/ForwardManager" type="Stateless"
 *           view-type="local"
 */
public class ForwardManagerEJB implements SessionBean, IForward {
	private final static Logger log = Logger.getLogger(ForwardManagerEJB.class);

	private SessionContext ctx;

	/**
	 *  
	 */
	public ForwardManagerEJB() {
		super();
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void forwardRequest(ForwardMessage forwardMessage) {
		log.debug("-> start processing ...");

		String forwardMode = "production";
		AccessManagerLocal accessManager = null;
		String asid = "";
		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			accessManager = serviceLocator.getAccessManager();
			asid = accessManager.createAccessSession();
		} catch (ServiceLocatorException slex) {
			log.error("caught ex: " + slex.toString());
		} catch (AccessException acex) {
			log.error("caught ex: " + acex.toString());
		}

		if (this.login(asid, accessManager)) {
			log.debug("#####################LOGIN############################");
			log.debug("#                 login successful!                  #");
			log.debug("#####################LOGIN############################");

			String local = "";
			String queueName = "";
			String host = "";
			String serviceId = "";
			;
			try {
				Config config = Config.getInstance();
				forwardMode = config.getProperty(
						"emayor.operating.mode.forward", "test");
				local = config
						.getProperty("forward.manager.test.local.municipality.name");
				queueName = config.getProperty("forward.manager.queue.name");
				host = config.getProperty("forward.manager.queue.host");
				serviceId = forwardMessage.getServiceId();
				if (log.isDebugEnabled()) {
					log.debug("forward mode = " + forwardMode);
					log.debug("local        = " + local);
					log.debug("queue name   = " + queueName);
					log.debug("host         = " + host);
					log.debug("service id   = " + serviceId);
					log.debug("replay ID    = " + forwardMessage.getReplyID());
				}
			} catch (ConfigException confex) {
				log.error("caught ex: " + confex.toString());
			}
			Context context;

			if (forwardMode.equals("production")) {
				log.info("forwarding in the [production] mode");
				try {
					String ssid = accessManager.startForwardedService(asid,
							serviceId, forwardMessage);
					if (log.isDebugEnabled())
						log.debug("the new ssid is " + ssid);
					ForwardingRepository repos = ForwardingRepository
							.getInstance();
					repos.storeForwardRequest(ssid, forwardMessage);
				} catch (AccessException acex) {
					log.error("caught ex: " + acex.toString());
				} catch (ForwardingRepositoryException ex) {
					log.error("caught ex: " + ex.toString());
				}
			} else {
				try {
					log.info("forwarding in the [test] mode");
					context = new InitialContext();

					log.debug("lookup local forward ...");
					String replyTo = ContentRoutingWrapper.getAccessPoint(
							local, "forward");
					log.debug("getting reply to address ...");
					String to = forwardMessage.getReplyAddress();
					Properties props = new Properties();
					props.setProperty("java.naming.factory.initial",
							"org.jnp.interfaces.NamingContextFactory");
					props.setProperty("java.naming.factory.url.pkgs",
							"org.jboss.naming:org.jnp.interfaces");
					props.setProperty("java.naming.provider.url", host);

					log.debug("getting context ...");
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
					MapMessage message = session.createMapMessage();
					log.debug("create message properties (RESPONSE) ...");
					message.setInt("type", IForward.RESPONSE);
					message.setString("ssid", forwardMessage.getReplyService());
					message.setString("uid", forwardMessage.getReplyID());
					String resCertificate = this.getTestResponse();
					if (log.isDebugEnabled())
						log.debug("got test response: " + resCertificate);
					message.setString("doc", resCertificate);
					message.setString("doc1", forwardMessage.getDocuments()
							.getItem(1));
					message.setString("doc2", forwardMessage.getDocuments()
							.getItem(2));
					message.setString("doc3", forwardMessage.getDocuments()
							.getItem(3));
					message.setString("doc4", forwardMessage.getDocuments()
							.getItem(4));
					message.setString("serviceId", forwardMessage
							.getServiceId());
					if (log.isDebugEnabled()) {
						log.debug("local   = " + local);
						log.debug("queue   = " + queueName);
						log.debug("host    = " + host);
						log.debug("replyto = " + replyTo);
						log.debug("to      = " + to);
					}
					message.setString("replyTo", replyTo);
					message.setString("to", to);
					log.debug("sending message to queue ...");
					sender.send(message);

				} catch (NamingException e1) {
					log.error("caught ex: " + e1.toString());
				} catch (JMSException e) {
					log.error("caught ex: " + e.toString());
				}
			}
		} else {
			log.error("forward login failed");
		}
		try {
			log.debug("<<< do log out >>>");
			accessManager.stopAccessSession(asid);
			//accessManager.remove();
		} catch (AccessException acex) {
			log.error("caught ex: " + acex.toString());
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Business Method
	 * 
	 * @ejb.interface-method view-type = "local"
	 *  
	 */
	public void forwardResponse(ForwardMessage forwardMessage) {
		log.debug("-> start processing ...");
		log.info("got response: ssid = " + forwardMessage.getReplyService());
		ForwardMessageBPEL message = new ForwardMessageBPEL();
		message.setSsid(forwardMessage.getReplyService());
		message.setUid(forwardMessage.getReplyID());
		message.setDocument(forwardMessage.getDocuments().getItem(0));
		message.setDocument1(forwardMessage.getDocuments().getItem(1));
		message.setDocument2(forwardMessage.getDocuments().getItem(2));
		message.setDocument3(forwardMessage.getDocuments().getItem(3));
		message.setDocument4(forwardMessage.getDocuments().getItem(4));
		message.setServiceId(forwardMessage.getServiceId());
		if (log.isDebugEnabled()) {
			log.debug("ssid       : " + message.getSsid());
			log.debug("uid        : " + message.getUid());
			log.debug("reqDoc     : " + message.getDocument());
			log.debug("serviceId  : " + message.getServiceId());
		}
		log.debug("using callback invoker ...");
		try {
			ForwardBPELServiceCallbackInvoker callback = new ForwardBPELServiceCallbackInvoker();
			callback.invoke(message);
		} catch (ForwardManagerBPELClientException ex) {
			log.error("caught ex: " + ex.toString());
		}
		log.debug("-> ... processing DONE!");
	}

	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		// TODO Auto-generated method stub
	}

	private boolean login(String asid, AccessManagerLocal accessManager) {
		log.debug("-> ... processing DONE!");
		log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		log.debug("!!!!!!!! LOGIN STARTED !!!!!!!!!!!!!!!!!!!!!!!!!");
		log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		boolean ret = false;
		try {
			//CertificatesRepository repos = CertificatesRepository.getInstance();
			MessageContext messageContext = this.ctx.getMessageContext();
			log.debug("-------------------------------------------");
			if (log.isDebugEnabled()) {
				for (Iterator i = messageContext.getPropertyNames(); i
						.hasNext();) {
					log.debug("next prop name: " + i.next());
				}
			}
			HttpServletRequest request = (HttpServletRequest) messageContext
					.getProperty("transport.http.servletRequest");
			if (request != null && log.isDebugEnabled()) {
				log.debug("Request is not null: " + request.toString());
				log.debug("request protocol : " + request.getProtocol());
				log.debug("is request secure: " + request.isSecure());
				log.debug("~~~~~~~~~~~~~~~~~~~###~~~~~~~~~~~~~~~~~~~~~~");
				for (Enumeration e = request.getAttributeNames(); e
						.hasMoreElements();) {
					log.debug("next attr name: " + e.nextElement());
				}
				log.debug("~~~~~~~~~~~~~~~~~~~###~~~~~~~~~~~~~~~~~~~~~~");
			}
			log.debug("try to authenticate the user");
			X509Certificate[] certificates = (X509Certificate[]) request
					.getAttribute("javax.servlet.request.X509Certificate");
			if (certificates != null) {
				if (log.isDebugEnabled()) {
					log.debug("certificates.length = " + certificates.length);
					for (int i = 0; i < certificates.length; i++)
						log.debug("certificates[" + i + "] = "
								+ certificates[i].toString());
				}
				ret = accessManager.startAccessSession(asid, certificates);
			} else
				log.error("ERRROR: certificates are NULL");
			ret = false;
			/*
			 * String profileStr = repos.getUserProfileString("server1");
			 * C_UserProfile userProfile = new C_UserProfile(profileStr);
			 * certificates = userProfile.getX509_CertChain();
			 */
			ret = accessManager.startAccessSession(asid, certificates);
			log.debug("-------------------------------------------");
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
		}
		return ret;
	}

	private String getTestResponse() {
		log.debug("-> ... processing DONE!");
		String ret = "";
		try {
			InputStream is = this.getClass().getResourceAsStream(
					"SampleResidenceCertificationDocument.xml");
			StringBuffer b = new StringBuffer();
			String line = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null)
				b.append(line.trim());
			ret = b.toString();
		} catch (Exception ex) {
			log.error("caught ex: " + ex.toString());
		}
		return ret;
	}
}