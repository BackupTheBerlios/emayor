/*
 * Created on 09.02.2005
 */
package org.emayor.notification.manager;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.emayor.notification.exception.NotificationException;
import org.emayor.notification.interfaces.INotificationManager;
import org.emayor.notification.interfaces.INotificationProducer;
import org.emayor.notification.producer.EmailNotificationProducerHome;


/**
 * a manager for different notification tasks
 * @see org.emayor.notification.interfaces.INotificationManager
 * 
 * @ejb.bean name="NotificationManager"
 *           display-name="Name for NotificationManager"
 *           description="Description for NotificationManager"
 *           jndi-name="ejb/NotificationManager"
 *           type="Stateless"
 *           view-type="remote"
 * @ejb.ejb-ref  ref-name = "EmailNotificationProducer"
 * 				 ejb-name = "EmailNotificationProducer"
 * 				 view-type = "remote"
 */
public class NotificationManagerBean implements SessionBean, INotificationManager {
	
	/* for default a producer will expire after this time */
	public static final int EXPIRE = 1000*60*60*24;
	
	/**
	 * standard constructor
	 */
	public NotificationManagerBean() {
		super();
	}

	/**
	 * Business method
	 * @throws NotificationException
	 * @ejb.interface-method  view-type = "remote"
	 * @see INotificationManager#createNotificationProducer(Properties)
	 */
	public Integer createNotificationProducer(Properties config) 
	throws NotificationException {
		Date expire = new Date();
		expire.setTime(expire.getTime()+EXPIRE);
		return createNotificationProducer(config,expire);
	}
	/**
	 * Business method
	 * @throws NotificationException
	 * @ejb.interface-method  view-type = "remote"
	 * @see INotificationManager#createNotificationProducer(Properties, Date)
	 */
	public Integer createNotificationProducer (Properties config, Date expire) 
	throws NotificationException {
		/* set up a new environment for this producer */
		INotificationProducer producer = null;
		
		/* initialize the return value */
		Integer hashKey = null;

		/* get the remote interface */
		Context context;
		
		if (config.getProperty("type").equals("email")) {
			try {
				context = new InitialContext();
				Object ref = context.lookup("ejb/EmailNotificationProducer");
				EmailNotificationProducerHome home = (EmailNotificationProducerHome) javax.rmi.PortableRemoteObject.narrow(ref,EmailNotificationProducerHome.class);
				/* and use it create a producer */
				producer = home.create();
			} catch (NamingException e) {
				throw new NotificationException("manager: JNDI lookup failed",e);
			} catch (RemoteException e) {
				throw new NotificationException("manager: remote exception",e);
			} catch (CreateException e) {
				throw new NotificationException("manager: create producer failed",e);
			} catch (Exception e) {
				throw new NotificationException("manager: general exception",e);
			}
		} else {
			throw new NotificationException("NotificationProducer of type "+config.getProperty("type")+" not implemented ...");
		}
			
		/* set return value to hashcode of the producer */
		hashKey = new Integer(producer.hashCode());
		try {
			/* configure producer */
			producer.configure(config);
			/* start producer */
			producer.start(expire);
		} catch (RemoteException e) {
			throw new NotificationException(e);
		} catch (NotificationException e) {
			throw new NotificationException(e);
		}
		/* return hashcode */
		return ProducerRepository.getInstance().add(producer);
	}
	/**
	 * Business method
	 * @throws NotificationException
	 * @ejb.interface-method  view-type = "remote"
	 * @see org.emayor.notification.interfaces.INotificationManager#deleteNotificationProducer(java.lang.Integer)
	 */
	public boolean deleteNotificationProducer(Integer key) throws NotificationException {
		/* get a specific producer by its hashkey */
		INotificationProducer prod = ProducerRepository.getInstance().get(key);
		
		/* if producer exists, stop it before deleting */
		if (prod != null) { 
			try {
				prod.stop();
				prod.remove();
			} catch (RemoteException e) {
				throw new NotificationException("manager",e);
			} catch (RemoveException e) {
				throw new NotificationException("producer: remove failed",e);
			}
		}
		
		/* now we can safely remove it */
		return ProducerRepository.getInstance().remove(key);
	}
	/**
	 * Business method
	 * @ejb.interface-method  view-type = "remote"
	 * @see org.emayor.notification.interfaces.INotificationManager#getNotificationProducer(java.lang.Integer)
	 */
	public org.emayor.notification.interfaces.INotificationProducer getNotificationProducer(
		Integer key) {
		/* just redirect */
		return ProducerRepository.getInstance().get(key);
	}
	
	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx)
		throws EJBException,
		RemoteException {

	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {
	}

	/* (non-Javadoc)
	 * @see javax.ejb.EJBObject#getEJBHome()
	 */
	public EJBHome getEJBHome() throws RemoteException {
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.ejb.EJBObject#getPrimaryKey()
	 */
	public Object getPrimaryKey() throws RemoteException {
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.ejb.EJBObject#remove()
	 */
	public void remove() throws RemoteException, RemoveException {
		INotificationManager temp = this;
		temp = null;
	}

	/* (non-Javadoc)
	 * @see javax.ejb.EJBObject#getHandle()
	 */
	public Handle getHandle() throws RemoteException {
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.ejb.EJBObject#isIdentical(javax.ejb.EJBObject)
	 */
	public boolean isIdentical(EJBObject ejbo) throws RemoteException {
		return false;
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