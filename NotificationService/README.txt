$ 11.02.2005 $

---prerequisites---
1.Installed JBOSS application server version 4.0
2. libraries that should be available to JBOSS:
		- quartz (www.quartzscheduler.org or lib/quartz.jar)
		- javamail (java.sun.com/products/javamail/ or lib/javamail.jar)
		- lib/activation.jar (also included in original packages for quartz/javamail)

--installing--
1. start the JBOSS
   %JBOSS_HOME%/bin/run.{bat,sh}
2. drag and drop the NotificationApp.ear into deploy dir of the 
   running JBOSS server - normally it's default server
   JBOSS_HOME/server/default/deploy/
   
NOTE: for deploying in the eMayor platform we only need NotificationEJB.jar 
serverside and NotificationEJB-client.jar clientside

--testing--
1. connect to your JBOSS http://localhost:8080/notification
2. fill out the form and submit
3. check your mailbox

--using--
have a look notification.web.TestServlet for an example:

			/* create a mail message */
			Message msg = ... 
			
			/* get context & lookup for service */
			Context context = new InitialContext();
			Object ref = context.lookup("ejb/NotificationManager");
			NotificationManagerHome home = (NotificationManagerHome) javax.rmi.PortableRemoteObject.narrow(ref,NotificationManagerHome.class);
			
			/* set up the manager */
			INotificationManager manager = home.create();
			
			/* create a new notification producer for mails */
			Integer key = manager.createEmailNotificationProducer(prop);
			
			/* get the producer and use it for notification */
			(manager.getNotificationProducer(key)).notifyViaMail(msg);
			
			/* delete the producer */
			manager.deleteNotificationProducer(key);
			
			/* remove the manager */
			manager.remove();

NOTE:
If you consider changing something in the implementation concerning the xdoclet environment, you
should recognize, that the JBoss-IDE plugin for eclipse has still some nasty bugs. So afterwards
you should check the content of all generated .xml files as well as the produced local/remote
interfaces.

In fact sometimes WEB-INF/*.xml does only contain one of several ejb-references, so you´ll have
to add the others manually to each file. Also in my implementation for casting issues i´ve made 
the remote interface of an EJB extend the interface of the specific manager/producer 
(INotificationManager/INotificationProducer) to have casting between several implementations 
(e.g. different producers) done comfortable.