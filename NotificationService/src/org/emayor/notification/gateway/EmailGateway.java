/*
 * Created on 09.02.2005
 */
package org.emayor.notification.gateway;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.log4j.Logger;
import org.emayor.notification.exception.NotificationException;
import org.emayor.servicehandling.config.Config;
import org.emayor.servicehandling.config.ConfigException;

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
			
			Config eConfig = null;
			
			// set up new properties			
			try {
				eConfig = Config.getInstance();
			} catch (ConfigException e3) {
				throw new NotificationException("Configuration not avaiable",e3);
			}
			
			
			/* read properties */
			try {
				
				host = eConfig.getProperty("emayor.notification.email.smtp.host");
			} catch (ConfigException e1) {
				throw new NotificationException("cannot read email properties",e1);
			}
			
			try {
				user = eConfig.getProperty("emayor.notification.email.smtp.user");
				pass = eConfig.getProperty("emayor.notification.email.smtp.pass");
				auth = eConfig.getProperty("emayor.notification.email.smtp.auth");
			} catch (ConfigException e4) {
				e4.printStackTrace();
			}
			
			/* check whether configuration complete */
			if (host == null) {
				throw new NotificationException("Gateway: Host not specified");
			}
		}
		
		if (log.isDebugEnabled()) { 
			log.debug("host     = "+host);
			log.debug("auth     = "+auth);
			log.debug("user     = "+user);
			log.debug("password = "+pass);
		}
		
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
			if (auth != null && auth.equals("true")) {
				if (user == null || pass == null) throw new NotificationException("Gateway: user or pass not specified");
				
				
				/*
				 * transport authorization
				 */
				trans.connect(host,user,pass);
				/*
				 * send out
				 */
				if (log.isDebugEnabled()) log.debug("sending message out ...");
				trans.sendMessage(msg,msg.getAllRecipients());
			} else {
				/*
				 * no authentication -> user and password left empty
				 */
				trans.connect(host,null,null);
				/*
				 * send out
				 */
				if (log.isDebugEnabled()) log.debug("sending message out ...");
				trans.sendMessage(msg,msg.getAllRecipients());
			}
			
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