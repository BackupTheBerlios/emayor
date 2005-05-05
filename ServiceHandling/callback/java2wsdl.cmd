@setlocal
@echo off

@set AH=c:\eclipse\workspace\Forwarding\

set CP=%AH%\lib\axis-ant.jar
set CP=%CP%;%AH%\lib\axis.jar
set CP=%CP%;%AH%\lib\commons-discovery.jar
set CP=%CP%;%AH%\lib\commons-logging.jar
set CP=%CP%;%AH%\lib\jaxrpc.jar
set CP=%CP%;%AH%\lib\log4j-1.2.8.jar
set CP=%CP%;%AH%\lib\saaj.jar
set CP=%CP%;%AH%\lib\wsdl4j.jar
set CP=%CP%;..\classes

java -cp %CP% org.apache.axis.wsdl.Java2WSDL -u LITERAL -y DOCUMENT -l "http://localhost:8080/eMayorEJB/ServiceCallbackManager" -o ServiceCallbackManager.wsdl --PkgtoNS "org.emayor.servicehandling.kernel"="http://emayor.org/ServiceCallbackManager" org.emayor.servicehandling.kernel.IServiceCallbackManager

@endlocal