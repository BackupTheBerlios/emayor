package org.emayor.notification.interfaces;

import java.util.Date;
import java.util.Properties;
import javax.ejb.EJBObject;

import org.emayor.notification.exception.NotificationException;



/**
 * @author Maximilian Schmidt
 *
 * an interface representing a notification producer
 * to be implemented by every producer and its localhome interface (neccesary for casting)
 */
public interface INotificationProducer extends EJBObject {
	
	/**
	 * configures the producer with properties
	 * 
	 * @param config configuration via properties 
	 */
	void configure(Properties config)
	throws java.rmi.RemoteException, NotificationException;
	
	/**
	 * starts the procuders with a specific timeout
	 * 
	 * @param expire	date when producer expires (timeout)
	 * @throws NotificationException
	 */
	void start(Date expire)
	throws java.rmi.RemoteException, NotificationException;
	
	/**
	 * stops the producer, deleting all timers and without any notification done
	 * @throws NotificationException
	 */
	void stop()
	throws java.rmi.RemoteException, NotificationException;
	
	/**
	 * notify the user via eMail with a given message
	 * 
	 * @param msg	message to be send
	 * @throws NotificationException
	 */
	void send()
	throws java.rmi.RemoteException, NotificationException;
}