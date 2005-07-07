/*
 * Created on 11.03.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.emayor.notification.wrapper.ejb;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import org.emayor.notification.exception.NotificationException;
import org.emayor.notification.interfaces.INotificationManager;
import org.emayor.notification.manager.NotificationManagerHome;
import org.emayor.servicehandling.interfaces.KernelLocal;
import org.emayor.servicehandling.kernel.KernelException;
import org.emayor.servicehandling.kernel.SessionException;
import org.emayor.servicehandling.utils.ServiceLocator;
import org.emayor.servicehandling.utils.ServiceLocatorException;


/**
 * @ejb.bean name="BPELNotificationWrapper"
 *           display-name="Name for BPELNotificationWrapper"
 *           description="Description for BPELNotificationWrapper"
 *           jndi-name="ejb/BPELNotificationWrapper"
 *           type="Stateless"
 *           view-type="remote"
 */
public class BPELNotificationWrapperEJB implements SessionBean {

	/**
	 * 
	 */
	public BPELNotificationWrapperEJB() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx)
		throws EJBException,
		RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 */
	public void sendNotificationMessage(String medium, String[] args)
	throws NotificationException {
		
		/* debugging */
		Logger log = Logger.getLogger(this.getClass());
		log.debug("medium  : "+medium);
		
		/*
		 * generic properties for producer
		 * 
		 * type = medium
		 * propertyX = args[X]
		 */
		Properties prop = System.getProperties();
		
		prop.put("type",medium);
		
		int i;
		for (i = 0; i<args.length; i++) {
			prop.put("property"+i,args[i]);
		}
		
		/*
		 * append special properties if necessary or rename existing properties
		 * and check for correctness of the given arguments (args)
		 */
		if (medium.equals("email")) {
			// we need at least these 4 field
			if (args.length < 4) throw new NotificationException("wrapper for email messaging requires more arguments!");
			String userId = args[0];
			String subject = args[1];
			String body = args[2];
			String message = args[3];
			log.debug("userId  : "+userId);
			log.debug("subject : "+subject);
			log.debug("body    : "+body);
			log.debug("message : "+message);
			
			if (userId == null) {
				throw new NotificationException("No UserID submitted, request failed.");
			}
				            	
	    	/* get email address */
			
			String sessionId = null;
			String address = null;
			
			try {
				ServiceLocator sloc = ServiceLocator.getInstance();
				KernelLocal kern = sloc.getKernelLocal();
				address = kern.getUserProfile(userId).getPEUserProfile().getUserEmail();
				//sessionId = sloc.getAccessSessionLocal().getSessionId();
				sessionId = "----------";
				
			} catch (ServiceLocatorException e1) {
				throw new NotificationException(e1);
			} catch (KernelException e1) {
				throw new NotificationException(e1);
			} 

			if (address == null) {
				throw new NotificationException("User has no valid email address.");
			} else {
				log.debug("address = "+address);
			}
			
	    		
			log.debug("setting up mail properties ...");

			// TODO delete!
			//address = "mxs@fokus.fraunhofer.de";
			//String host = "192.168.73.2";
			//prop.put("mail.smtp.host",host);
			
			/*
			 * set properties for producer configuration
			 */
			prop.put("address",address);
			prop.put("body",body);
			prop.put("subject",subject);
			prop.put("message",message);
			prop.put("type",medium);
		}
		
		/* get manager */
		log.debug("looking up NotificationManager ...");
		
		Context context;
		Object ref = null;
		
		try {
			context = new InitialContext();
			ref = context.lookup("ejb/NotificationManager");
		} catch (NamingException e2) {
			throw new NotificationException(e2);
		}
		
		log.debug("getting home interface ...");
		NotificationManagerHome home = (NotificationManagerHome) javax.rmi.PortableRemoteObject.narrow(ref,NotificationManagerHome.class);
		
		/*
		 * set up our manager
		 */
		log.debug("creating manager instance ...");
		
		INotificationManager manager = null;
		
		try {
			manager = home.create();
		} catch (RemoteException e3) {
			throw new NotificationException(e3);
		} catch (CreateException e3) {
			throw new NotificationException(e3);
		}
		
		/* create a new notification producer for mails */
		Integer key;
		try {
			log.info("creating producer ...");
			key = manager.createNotificationProducer(prop);
			
			log.info("sending notification ...");
			/* get the producer and use it for notification */
			(manager.getNotificationProducer(key)).send();
			
			log.info("deleting producer ...");
			/* delete the producer */
			manager.deleteNotificationProducer(key);
			
		} catch (RemoteException e4) {
			throw new NotificationException(e4);
		} catch (NotificationException e4) {
			throw new NotificationException(e4);
		} finally {

			try {
				log.info("removing manager ...");
				/* remove manager */
				manager.remove();
			} catch (RemoveException e4) {
				throw new NotificationException(e4);
			} catch (RemoteException e4) {
				throw new NotificationException(e4);
			}
		}
	}
	
	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
	}
}