/*
 * Created on 09.02.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.emayor.notification.producer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.emayor.notification.exception.NotificationException;
import org.emayor.notification.gateway.EmailGateway;
import org.emayor.notification.interfaces.INotificationProducer;
import org.emayor.servicehandling.config.Config;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;


/**
 * @ejb.bean name="EmailNotificationProducer"
 *           display-name="Name for EmailNotificationProducer"
 *           description="Description for EmailNotificationProducer"
 *           jndi-name="ejb/EmailNotificationProducer"
 *           type="Stateful"
 *           view-type="remote"
 */
public class EmailNotificationProducerBean
	implements
		SessionBean, INotificationProducer {

	/* configuration setttings */
	private Properties config = null;
	/* a valid eMail address we can use as a recipient */
	private String emailAddress = null;
	/* local context for timer services */
	private SessionContext ejbContext = null;
	/* a factory for schedulers, so we can use timed jobs */
	private SchedulerFactory schedFac = null;
	/* scheduler */
	private Scheduler scheduler = null;
	/* debug logging */
	private Logger log = Logger.getLogger(this.getClass());
	/* context */
	private SessionContext ctx;
	
	/**
	 * create a new session bean
	 */
	public EmailNotificationProducerBean() {
		super();
		schedFac = new StdSchedulerFactory();
	}

	/**
	 * Business method
	 * @throws NotificationException
	 * @ejb.interface-method  view-type = "remote"
	 * @see org.emayor.notification.interfaces.INotificationProducer#configure(java.util.Properties)
	 */
	public void configure(Properties config) throws NotificationException {
		if (log.isDebugEnabled()) log.debug("configure producer ...");
		this.config = config;
		emailAddress = config.getProperty("address");
		if (emailAddress == null)
			throw new NotificationException("producer configuration: email address invalid ("+emailAddress+")");
	}

	/**
	 * Business method
	 * @throws NotificationException
	 * @ejb.interface-method  view-type = "remote"
	 * @see org.emayor.notification.interfaces.INotificationProducer#start(java.util.Date)
	 */
	public void start(Date expire) throws NotificationException {

		if (config == null) return;
		try {
			/* get a scheduler from the factory an start it, so triggers are working*/
			scheduler = schedFac.getScheduler();
			scheduler.start();
			/* create a new job (StatefulJob) */
			JobDetail jobDetail = new JobDetail(
				expire.toString(),
				scheduler.DEFAULT_GROUP,
				ProducerTimerJob.class);
			/* get a datamap and store a reference, so we can access it from within the job (ProducerTimerJob)*/
			jobDetail.getJobDataMap().put("producer", this);
			/* set up a trigger for the given time */
			SimpleTrigger jobTrigger = new SimpleTrigger(
				expire.toString(),
				scheduler.DEFAULT_GROUP,
				expire);
			/* add everything to the scheduler */
			scheduler.scheduleJob(jobDetail, jobTrigger);
		} catch (SchedulerException e) {
			throw new NotificationException(e.getCause());
		}
	}

	/**
	 * Business method
	 * @throws NotificationException
	 * @ejb.interface-method  view-type = "remote"
	 * @see org.emayor.notification.interfaces.INotificationProducer#stop()
	 */
	public void stop() throws NotificationException {
		//	log.warning("where: stop(" + (new Date()).toString() + ")");
		if (scheduler != null) {
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				throw new NotificationException(e.getCause());
			}
		}
	}

	/**
	 * Business method
	 * @throws NotificationException
	 * @ejb.interface-method  view-type = "remote"
	 * @see org.emayor.notification.interfaces.INotificationProducer#notifyViaMail(javax.mail.Message)
	 */
	public void send() throws NotificationException {
		try {
			/*
			 * get a new session for the message
			 */
			Session sess = Session.getDefaultInstance(config,null);
			
			// get properties from configuration
			String directory 	= System.getProperty("jboss.server.temp.dir");
			String sessionId 	= config.getProperty("sessionId");
			String message 		= config.getProperty("message");
			String subject 		= config.getProperty("subject");
			String body 		= config.getProperty("body");
			
			Config shConfig = Config.getInstance();
			
			String replyTo 	= shConfig.getProperty(Config.EMAYOR_NOTIFICATION_EMAIL_REPLYTO);
			
			// set defaults if not present
			if (body == null) 		body 		= "";
			if (subject == null) 	subject 	= "no subject";
			if (directory == null) 	directory 	= "";
			if (sessionId == null) 	sessionId 	= Long.toString(System.currentTimeMillis());
			if (replyTo == null) 	replyTo		= emailAddress;
			
			/*
			 * create a new message out of the values read
			 */
			Message msg = new MimeMessage(sess);
			msg.setFrom(new InternetAddress(replyTo));
			msg.setSubject(subject);
			msg.setText(body);
			msg.setSentDate(new Date());
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
			
			if (log.isDebugEnabled()) {
				log.debug("TO:        : "+emailAddress);
				log.debug("FROM       : "+replyTo);
				log.debug("subject    : "+subject);
				log.debug("body       : "+body);
				log.debug("SSID       : "+sessionId);
				log.debug("attachment : "+message);
			}
			
			// use attachment or send directly
			if (message != null) {
				// create file for attachment
				FileOutputStream out = new FileOutputStream(directory+File.separatorChar+sessionId);
				PrintStream p = new PrintStream( out,true,"UTF-8" );
				p.println (message);
				p.close();
				out.close();
				
				MimeMultipart mp = new MimeMultipart();
				// real body
				BodyPart bp = new MimeBodyPart();
				bp.setText(body);
				// attachment
				BodyPart attach = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(directory+File.separatorChar+sessionId);
				attach.setDataHandler(new DataHandler(fds));
				attach.setFileName("document.xml");
				attach.setDescription("document.xml");
				attach.setDisposition("attachment; filename=document.xml");
				// add body + attachment
				mp.addBodyPart(bp);
				mp.addBodyPart(attach);
				msg.setContent(mp);
				
				// send it out
				if (log.isDebugEnabled()) log.debug("delivering to email gate ...");
				EmailGateway.getInstance().sendEmail(msg,config);
				fds.getFile().delete();
			} else {
				// send it out
				if (log.isDebugEnabled()) log.debug("delivering to email gate ...");
				EmailGateway.getInstance().sendEmail(msg,config);
			}
			
		} catch (MessagingException e) {
			throw new NotificationException(e);
		} catch (FileNotFoundException e) {
			throw new NotificationException(e);
		} catch (IOException e) {
			throw new NotificationException(e);
		} catch (Exception e) {
			throw new NotificationException(e);
		}
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
		// OMFG !?
		EmailNotificationProducerBean temp = this;
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
		if (this.equals(ejbo)) return true;
		return false;
	}
	
	/**
	 * Default create method
	 * 
	 * @throws CreateException
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#setSessionContext(javax.ejb.SessionContext)
	 */
	public void setSessionContext(SessionContext ctx)
		throws EJBException,
		RemoteException {
		this.ctx = ctx;
	}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbRemove()
	 */
	public void ejbRemove() throws EJBException, RemoteException {}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbActivate()
	 */
	public void ejbActivate() throws EJBException, RemoteException {}

	/* (non-Javadoc)
	 * @see javax.ejb.SessionBean#ejbPassivate()
	 */
	public void ejbPassivate() throws EJBException, RemoteException {}

	
}