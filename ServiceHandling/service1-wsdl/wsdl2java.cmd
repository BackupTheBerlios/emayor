@setlocal
@echo off

@set AH=e:\lib\axis

@set CP=%AH%\lib\axis.jar;%AH%\lib\wsdl4j.jar;%AH%\lib\commons-logging.jar;%AH%\lib\commons-discovery.jar;%AH%\lib\jaxrpc.jar;%AH%\lib\saaj.jar

java -cp %CP% org.apache.axis.wsdl.WSDL2Java -o src ResidenceCertificationService.wsdl

@endlocal