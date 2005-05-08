/*
 * Created on Apr 19, 2005
 */
package org.emayor.servicehandling.beans;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;
import org.emayor.ContentRouting.ejb.AccessPointNotFoundException;
import org.emayor.ContentRouting.ejb.BindingTemplateNotFoundException;
import org.emayor.ContentRouting.ejb.OrganisationNotFoundException;
import org.emayor.ContentRouting.ejb.ServiceNotFoundException;
import org.emayor.ContentRouting.interfaces.ContentRouterLocal;
import org.emayor.ContentRouting.interfaces.ContentRouterLocalHome;
import org.emayor.forward.ContentRoutingWrapper;
import org.emayor.servicehandling.kernel.bpel.forward.data.ForwardMessageBPEL;
import org.emayor.servicehandling.kernel.bpel.forward.server.IForwardManagerBPEL;

import javax.ejb.CreateException;
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

/**
 * @ejb.bean name="ForwardManagerBPEL" display-name="Name for
 *           ForwardManagerBPEL" description="Description for
 *           ForwardManagerBPEL"
 *           jndi-name="ejb/eMayor/servicehandling/ForwardManagerBPEL"
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
        ForwardMessageBPEL.printForwardMessage(message);
        
		Properties config = System.getProperties();
		String configuration = System.getProperty("jboss.server.home.dir")+"/conf/forward.properties";
		//String configuration = System.getProperty("forward.properties");
		File conffile = new File(configuration);
		try {
			if (conffile.exists()) {
				log.info("loading configuration from file: "+configuration);
				// load configuration
				config.load(new FileInputStream(conffile));
				//config.load(this.getClass().getClassLoader().getResourceAsStream(configuration));
			} else {
				throw new Exception("Configuration not found in: "+configuration);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
			return;
		}
		
		String local = config.getProperty("localMunicipality");
		String remote = config.getProperty("remoteMunicipality");
		String queueName = config.getProperty("queueName");
		String host = config.getProperty("queueHost");
		
		Context context;

		try {
			context = new InitialContext();
			
			/*
			log.info("getting content home ...");
			ContentRouterLocalHome home = (ContentRouterLocalHome) context.lookup("ContentRouterLocal");
			log.info("getting content router ...");
			ContentRouterLocal content = home.create();
			*/
			
			ContentRoutingWrapper content = new ContentRoutingWrapper();
			log.info("lookup local forward ...");
			String replyTo = content.getAccessPoint(local,"forward");
			log.info("lookup remote forward ...");
			String to = content.getAccessPoint(remote,"forward");
			
			log.debug("forwarding to: " + to);
			
			config.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
			config.setProperty("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");
			config.setProperty("java.naming.provider.url",host);
			
			log.info("getting context: "+config.getProperty("java.naming.provider.url"));
			context = new InitialContext(config);
			log.info("getting factory ...");
			QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
			log.info("getting connection ...");
			QueueConnection connect = factory.createQueueConnection();
			log.info("getting queue ...");
			Queue queue = (Queue) context.lookup(queueName);
			log.info("getting session ...");
			QueueSession session = connect.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			log.info("starting connection ...");
			connect.start();
			log.info("creating sender ...");
			QueueSender sender = session.createSender(queue);
			log.info("get message ...");
			MapMessage msg = session.createMapMessage();
			log.info("create message properties (REQUEST) ...");
			msg.setInt("type",type);
			msg.setString("ssid",message.getSsid());
			msg.setString("uid",message.getUid());
			msg.setString("doc",message.getDocument());
			msg.setString("doc1",message.getDocument1());
			msg.setString("doc2",message.getDocument2());
			msg.setString("doc3",message.getDocument3());
			msg.setString("doc4",message.getDocument4());
			log.info("sending message to queue ...");
			msg.setString("replyTo",replyTo);
			msg.setString("to",to);
			sender.send(msg);
		} catch (NamingException e) {
			log.debug("-> ... processing FAILED");
			e.printStackTrace();
		} catch (JMSException e) {
			log.debug("-> ... processing FAILED");
			e.printStackTrace();
		/*
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OrganisationNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BindingTemplateNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccessPointNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			*/
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
		forward(message,IForwardManagerBPEL.REQUEST);
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
		forward(message,IForwardManagerBPEL.RESPONSE);
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