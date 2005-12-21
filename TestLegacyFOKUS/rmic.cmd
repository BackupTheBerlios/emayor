@setlocal

@set CP=.\lib\log4j-1.2.8.jar;.\lib\E2MInterface.jar;.\classes

%JAVA_HOME%\bin\rmic -classpath %CP% -d .\classes org.eMayor.legacy.test.LegacyServer

@endlocal