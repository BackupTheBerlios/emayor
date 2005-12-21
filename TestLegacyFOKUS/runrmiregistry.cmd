@setlocal

@set CLASSPATH=.\dist\TestLegacy.jar;.\lib\E2MInterface.jar;.\lib\log4j-1.2.8.jar


start "RMI Registry" %JAVA_HOME%\bin\rmiregistry 2001

@endlocal