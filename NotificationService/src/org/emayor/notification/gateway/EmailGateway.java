/*
 * Created on 09.02.2005
 */
package org.emayor.notification.gateway;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.log4j.Logger;
import org.emayor.notification.exception.NotificationException;

/**
 * @author Maximilian Schmidt
 *
 * a gateway for sending mail, implemented as a singelton
 * 
 */
public class EmailGateway {
	private static EmailGateway singleton;
	
	/**
	 * get an instance - the one and only instance allowed
	 * 
	 * @return the repository instance 
	 */
	public synchronized static EmailGateway getInstance() {
		if (singleton == null) singleton = new EmailGateway();
		return singleton;
	}
	
	/**
	 * send out the given message and in case of failure, send it 
	 * back to the sender
	 * 
	 * @param msg	message to be send out
	 * @throws NotificationException
	 */
	public void sendEmail(Message msg, Properties config) throws NotificationException {
		Logger log = Logger.getLogger(this.getClass());
		log.debug("got gateway ...");
			
		/*
		 * receive properties
		 */
		String host = config.getProperty("mail.smtp.host");
		String user = config.getProperty("mail.smtp.user");
		String pass = config.getProperty("mail.smtp.pass");
		String auth = config.getProperty("mail.smtp.auth");
		
		/*
		 * if no mailhost specified, search for config file
		 * - otherwise no mail could be send 
		 * (falling back to localhost is NOT an option)
		 */
		if (host == null) {
			// set up new properties
			config = new Properties();
			try {
				// access jboss configuration file
				String configuration = System.getProperty("jboss.server.home.dir")+"\\conf\\mail.properties";
				File conffile = new File(configuration);
				if (conffile.exists()) {
					log.debug("loading configuration from file: "+configuration);
					// load configuration
					config.load(new FileInputStream(conffile));
				} else {
					throw new NotificationException("Gateway: Host not specified and no config available");
				}
			} catch (Exception e1) {
				throw new NotificationException("Gateway: Host not specified and loading config from file failed");
			}
			
			/* reread properties */
			host = config.getProperty("mail.smtp.host");
			user = config.getProperty("mail.smtp.user");
			pass = config.getProperty("mail.smtp.pass");
			auth = config.getProperty("mail.smtp.auth");
			
			/* check whether configuration complete */
			if (host == null) {
				throw new NotificationException("Gateway: Host not specified");
			}
		}
		
		log.debug("host = "+host);
		
		/*
		 * get a new session and configure it
		 */
		Session session = Session.getDefaultInstance(config,null);
		Transport trans = null;
		try {
			/*
			 * get an new smtp transport layer
			 */
			trans = session.getTransport("smtp");
		} catch (NoSuchProviderException e2) {
			throw new NotificationException(e2);
		}
		
		try {
			/*
			 * check for authentication parameters
			 */
			if (auth != null) {
				log.debug("user = "+user);
				if (user == null || pass == null) throw new NotificationException("Gateway: user or pass not specified");
				
				
				/*
				 * transport authorization
				 */
				log.debug("connect ....");
				trans.connect(host,user,pass);
				/*
				 * send out
				 */
				log.debug("sending message out ....");
				trans.sendMessage(msg,msg.getAllRecipients());
			} else {
				/*
				 * no authentication -> user and password left empty
				 */
				log.debug("connect ....");
				trans.connect(host,null,null);
				/*
				 * send out
				 */
				log.debug("sending message out ....");
				trans.sendMessage(msg,msg.getAllRecipients());
			}
			log.debug("message send out ....");
			
		} catch (MessagingException e) {
			/*
			 * if send failed, return to sender
			 * !! this one is pure evil - until reciepent == sender we may get a loop !!
			 */
			/*try {
				if (msg.getReplyTo() != null)
					msg.setRecipients(Message.RecipientType.TO,msg.getReplyTo());
				else if (msg.getFrom() != null)
					msg.setRecipients(Message.RecipientType.TO,msg.getFrom());/
				msg.setSubject("SEND failed: " + msg.getSubject());
				trans.connect(host,null,null);
				log.debug("send ....");
				trans.sendMessage(msg,msg.getAllRecipients());
			} catch (Exception e1) {
				throw new NotificationException(e1);
			} 
			*/
			throw new NotificationException(e);
		} finally {
			try {
				/*
				 * close transport layer
				 */
				trans.close();
			} catch (MessagingException e1) {
				throw new NotificationException(e1);
			}
		}
	}
	
}