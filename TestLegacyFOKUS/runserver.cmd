@setlocal

@rem set OPTS=-Djava.rmi.server.codebase=file://e:/working\eMayor\dev\TestLegacySeville\classes\
@set OPTS=%OPTS% -Djava.security.policy=wideopen.policy
@rem set OPTS=%OPTS% -Djava.security.policy.manager


@set CP=.\lib\log4j-1.2.8.jar;.\lib\E2MInterface.jar;.\dist\TestLegacy.jar

@rem start "Legacy Bozen" %JAVA_HOME%\bin\java -cp %CP% %OPTS% org.eMayor.legacy.test.ServiceStarter %*
%JAVA_HOME%\bin\java -cp %CP% %OPTS% org.eMayor.legacy.test.ServiceStarter %*

@endlocal