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
			config = new Properties();
			try {
				String configuration = System.getProperty("jboss.server.home.dir")+"\\conf\\mail.properties";
				File conffile = new File(configuration);
				if (conffile.exists()) {
					log.debug("loading configuration from file: "+configuration);
					config.load(new FileInputStream(conffile));
				} else {
					throw new NotificationException("Gateway: Host not specified and no config available");
				}
			} catch (Exception e1) {
				throw new NotificationException("Gateway: Host not specified and loading config from file failed");
			}
			/* reread config */
			host = config.getProperty("mail.smtp.host");
			user = config.getProperty("mail.smtp.user");
			pass = config.getProperty("mail.smtp.pass");
			auth = config.getProperty("mail.smtp.auth");
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
				trans.connect(host,user,pass);
				/*
				 * send out
				 */
				log.debug("sending message out ....");
				trans.sendMessage(msg,msg.getAllRecipients());
			} else {
				/*
				 * no authentication -> no transport layer needed
				 * hostname obtained from properties
				 */
				log.debug("sending message ....");
				log.debug("connect ....");
				trans.connect(host,null,null);
				log.debug("send ....");
				trans.sendMessage(msg,msg.getAllRecipients());
				/*
				log.debug("properties ....");
				System.setProperties(config);
				log.debug("sending message, host = "+System.getProperty("mail.smtp.host"));
				log.debug("sending message, address = "+msg.getAllRecipients()[0].toString());
				Transport.send(msg);
				*/
			}
			log.debug("message send out ....");
			
		} catch (MessagingException e) {
			/*
			 * if send failed, return to sender
			 */
			try {
				if (msg.getReplyTo() != null)
					msg.setRecipients(Message.RecipientType.TO,msg.getReplyTo());
				else if (msg.getFrom() != null)
					msg.setRecipients(Message.RecipientType.TO,msg.getFrom());
			/*
			 * set new subject according to type of error
			 */
				msg.setSubject("SEND failed: " + msg.getSubject());
				trans.connect(host,null,null);
				log.debug("send ....");
				trans.sendMessage(msg,msg.getAllRecipients());
			} catch (Exception e1) {
				throw new NotificationException(e1);
			} 
		} finally {
			try {
				/*
				 * close transport layer
				 */
				trans.close();
			} catch (MessagingException e1) {
				e1.getStackTrace();
				throw new NotificationException(e1);
			}
		}
	}
	
}