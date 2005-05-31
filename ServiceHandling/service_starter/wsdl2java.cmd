@setlocal
@echo off

@set AH=e:\lib\axis

set CP=%AH%\lib\axis-ant.jar
set CP=%CP%;%AH%\lib\axis.jar
set CP=%CP%;%AH%\lib\commons-discovery.jar
set CP=%CP%;%AH%\lib\commons-logging.jar
set CP=%CP%;%AH%\lib\jaxrpc.jar
set CP=%CP%;%AH%\lib\log4j-1.2.8.jar
set CP=%CP%;%AH%\lib\saaj.jar
set CP=%CP%;%AH%\lib\wsdl4j.jar
set CP=%CP%;..\bin

java -cp %CP% org.apache.axis.wsdl.WSDL2Java -o src --NStoPkg "http://www.emayor.org/eMayorServiceStarter_v10"="org.emayor.servicehandling.kernel.bpel.starter" --NStoPkg "http://www.emayor.org/eMayorServiceStarterRequest.xsd"="org.emayor.servicehandling.kernel.bpel.starter" --NStoPkg "http://www.emayor.org/eMayorServiceRequest.xsd"="org.emayor.servicehandling.kernel.bpel.service" ./wsdl/eMayorServiceStarter_v10_jboss_client.wsdl

@endlocal