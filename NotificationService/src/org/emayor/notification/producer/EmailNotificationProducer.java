/*
 * Generated by XDoclet - Do not edit!
 */
package org.emayor.notification.producer;

import org.emayor.notification.interfaces.INotificationProducer;

/**
 * Remote interface for EmailNotificationProducer.
 */
public interface EmailNotificationProducer
   extends javax.ejb.EJBObject, INotificationProducer
{
   /**
    * Business method
    * @throws NotificationException
    * @see org.emayor.notification.interfaces.INotificationProducer#configure(java.util.Properties)    */
   public void configure( java.util.Properties config )
      throws org.emayor.notification.exception.NotificationException, java.rmi.RemoteException;

   /**
    * Business method
    * @throws NotificationException
    * @see org.emayor.notification.interfaces.INotificationProducer#start(java.util.Date)    */
   public void start( java.util.Date expire )
      throws org.emayor.notification.exception.NotificationException, java.rmi.RemoteException;

   /**
    * Business method
    * @throws NotificationException
    * @see org.emayor.notification.interfaces.INotificationProducer#stop()    */
   public void stop(  )
      throws org.emayor.notification.exception.NotificationException, java.rmi.RemoteException;

   /**
    * Business method
    * @throws NotificationException
    * @see org.emayor.notification.interfaces.INotificationProducer#send()    */
   public void send(  )
      throws org.emayor.notification.exception.NotificationException, java.rmi.RemoteException;

}