package org.emayor.notification.producer;

import java.rmi.RemoteException;

import javax.ejb.RemoveException;
import javax.mail.Message;


import org.emayor.notification.exception.NotificationException;
import org.emayor.notification.interfaces.INotificationProducer;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * @author Maximilian Schmidt
 *
 * represents a timer for producers
 */
public class ProducerTimerJob implements StatefulJob {

	/**
	 * execute an default action depending on the type of producer
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap map = arg0.getJobDetail().getJobDataMap();
		INotificationProducer prod = (INotificationProducer) map.get("producer");

		try {
			prod.stop();
			prod.remove();
		} catch (RemoveException e) {
			throw new JobExecutionException("producer: remove failed");
		} catch (RemoteException e) {
			throw new JobExecutionException(e);
		} catch (NotificationException e) {
			throw new JobExecutionException(e);
		}
	}

}

