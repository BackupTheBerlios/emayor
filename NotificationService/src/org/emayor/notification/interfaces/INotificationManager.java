package org.emayor.notification.interfaces;

import java.util.Date;
import java.util.Properties;

import javax.ejb.EJBObject;

import org.emayor.notification.exception.NotificationException;




/**
 * 
 * @author Maximilian Schmidt
 *
 * Represents an interface for a manager handling different
 * notification tasks 
 */
public interface INotificationManager extends EJBObject {
	
	/**
	 * Adds a new notification task (NotificationProducer) to the set of avaiable tasks and
	 * returns the corresponding hashkey (equal to hashcode as integer-object) as a back-reference
	 * Note: expires after a time in millis according to @link notification.manager.NotificationManager.EXPIRE
	 * 
	 * @param config		configuration for producer
	 * 
	 * @return hashcode of producer as reference
	 * @throws NotificationException
	 */
	Integer createNotificationProducer(Properties config)
	throws java.rmi.RemoteException, NotificationException;
	
	/**
	 * Adds a new notification task (NotificationProducer) to the set of avaiable tasks and
	 * returns the corresponding hashkey (equal to hashcode as integer-object) as a back-reference
	 * 
	 * @param config		configuration for producer
	 * @param	expire		producer expires after this date
	 * 
	 * @return hashcode of producer as reference
	 * @throws NotificationException
	 */
	Integer createNotificationProducer(Properties config, Date expire)
	throws java.rmi.RemoteException, NotificationException;
	
	/**
	 * Removes a notification task from the set of avaiable tasks
	 * 
	 * @param key				hashcode as a reference to the producer
	 * 
	 * @return false if delete fails, true otherwise
	 * @throws NotificationException
	 */
	boolean deleteNotificationProducer(Integer key)
	throws java.rmi.RemoteException, NotificationException;
	
	/**
	 * Get a specific task by using its hashkey
	 * 
	 * @param key											hashcode as a reference to the producer
	 * 
	 * @return producer referenced by key
	 */
	INotificationProducer getNotificationProducer(Integer key)
	throws java.rmi.RemoteException;
}