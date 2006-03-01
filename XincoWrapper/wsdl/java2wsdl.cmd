@setlocal
@echo off

@set AH=..\

set CP=%AH%\lib\axis-ant.jar
set CP=%CP%;%AH%\lib\axis.jar
set CP=%CP%;%AH%\lib\commons-discovery-0.2.jar
set CP=%CP%;%AH%\lib\commons-logging-1.0.4.jar
set CP=%CP%;%AH%\lib\jaxrpc.jar
set CP=%CP%;%AH%\lib\log4j-1.2.8.jar
set CP=%CP%;%AH%\lib\saaj.jar
set CP=%CP%;%AH%\lib\wsdl4j-1.5.1.jar
set CP=%CP%;%AH%\lib\java.jar
set CP=%CP%;%AH%\lib\javamail.jar
set CP=%CP%;%AH%\lib\activation.jar
set CP=%CP%;%AH%\lib\ejb-api.jar
set CP=%CP%;%AH%\lib\jta-api.jar
set CP=%CP%;%AH%\..\Xinco\src\WEB-INF\classes
set CP=%CP%;..\bin

java -cp %CP% org.apache.axis.wsdl.Java2WSDL -u LITERAL -y DOCUMENT -l "http://localhost:80/eMayor/ForwardManager" -o XincoWrapper.wsdl --PkgtoNS "de.fraunhofer.dms.xinco.wrapper"="uri:de.fraunhofer.dms.xinco.wrapper" de.fraunhofer.dms.xinco.wrapper.XincoWrapper

@endlocal