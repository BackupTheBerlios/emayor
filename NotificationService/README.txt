$ 11.02.2005 $

NOT UP TO DATE, WILL BE REWRITTEN SOON

---prerequisites---
1.Installed JBOSS application server version 4.0
2. libraries that should be available to JBOSS:
		- quartz (www.quartzscheduler.org or lib/quartz.jar)
		- javamail (java.sun.com/products/javamail/ or lib/javamail.jar)
		- lib/activation.jar (also included in original packages for quartz/javamail)

--installing--
1. start the JBOSS
   %JBOSS_HOME%/bin/run.{bat,sh}
2. drag and drop the NotificationEJB.jar into deploy dir of the 
   running JBOSS server - normally it's default server
   JBOSS_HOME/server/default/deploy/
3. start a mail server somewhere
4. place configuration for sending mails in
   JBOSS_HOME/server/default/conf/mail.properties
   Here an example:
   -------8<--------
   mail.smtp.host=mailhub.fokus.fraunhofer.de
   mail.smtp.user=mxs
   mail.smtp.pass=password
   mail.smtp.auth=true
   -------8<--------
   When using a mailserver that doesn´t require authentication you need only the first line,
   mentioning the mail-host.
   
   When using together with the eMayor-app you should peform step 5 as well.
   When using together with the eMayor-app and BPEL backend you should also perform steps 6/7
   
5. deploy the eMayor package (eMayor.ear) with the BPELNotificationWrapper.jar inside
6. place NotificationWrapperEJB-client.jar in ORABPEL_HOME\system\appserver\oc4j\j2ee\home\applib
6. start a local BPEL PM-Server and deploy the NotificationRequest

--testing--
1.  connect to your JBOSS http://localhost:8443/sh
2.  check the jboss output for the userId
2.  connect to your BPEL Console and fill out the form
2a. enter "email" as NotificationMedium and the correct userId
2b. submit
3.  check the mailaccount specified in your browsers certificate


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