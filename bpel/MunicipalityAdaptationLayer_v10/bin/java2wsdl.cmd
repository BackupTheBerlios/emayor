@setlocal
@echo off

@set AH=e:\lib\axis
@set EJB_JAR=..\BPEL-INF\lib\jbossall-client.jar

set CP=%AH%\lib\axis-ant.jar
set CP=%CP%;%AH%\lib\axis.jar
set CP=%CP%;%AH%\lib\commons-discovery.jar
set CP=%CP%;%AH%\lib\commons-logging.jar
set CP=%CP%;%AH%\lib\jaxrpc.jar
set CP=%CP%;%AH%\lib\log4j-1.2.8.jar
set CP=%CP%;%AH%\lib\saaj.jar
set CP=%CP%;%AH%\lib\wsdl4j.jar
set CP=%CP%;..\BPEL-INF\lib\M2EWrapperClient.jar;%EJB_JAR%

java -cp %CP% org.apache.axis.wsdl.Java2WSDL -u LITERAL -l "http://localhost:8080/" -o ../M2E-gen.wsdl org.emayor.servicehandling.interfaces.E2MWrapper

@endlocal