What you need installed:
 - BPEL PM
 - MySQL
 - Tomcat 5.x.x (use the one from BerliOS - or notice the comments below)
 - JBoss 4.0.0
 - JDK 1.4.2

The installation script uses automatic detection of installed packages (JBoss/JDK/etc.).
If nothing is detected input is requested from the user. However, it may happen
that a product is available twice (e.g. 2 JBoss´s) and that the wrong one would be 
taken automatically. In that case you should edit Installation/municipality.properties and
set this value by hand.

DMS issues (if not using tomcat from CVS)
------------------------------------------
1. Tomcat5 should run on port 28080 <- this is needed for the EJB-layer to run properly
2. Use the modified version of Xinco from CVS and make sure everything is in place (tomcat libraries)
	- deploy xinco on tomcat
	- deploy axis on tomcat
	- verify axis (http://localhost:28080/xinco/happyaxis.jsp AND http://localhost:28080/axis/happyaxis.jsp)
	- try to connect to XincoAdmin http://localhost:28080/xinco/XincoAdmin
	- try to get XincoExplorer running http://localhost:28080/xinco/client/XincoExplorer.jnlp

DMS issues (tomcat on port != 28080)
------------------------------------
1. Edit tomcat\conf\server.conf and modify the port settings
2. Edit tomcat\webapps\xinco\client\XincoExplorer.jnlp and modify the port settings
3. Download XincoWrapper from CVS and modify ports 
	- edit XincoWrapper\src\de\fraunhofer\dms\xinco\wrapper\XincoWrapperEJB.java (somewhere around line 89)
	- run packaging
	- redeploy XincoWrapper.jar on JBoss

UDDI issues (tomcat on port != 28080)
------------------------------------
1. Edit tomcat\webapps\juddi\WEB-INF\juddi.properties and modify the port settings