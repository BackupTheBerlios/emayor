@setlocal

@rem set OPTS=-Djava.rmi.server.codebase=file://e:\working\eMayor\dev\TestLegacySeville\classes\
@rem set OPTS=%OPTS% -Djava.security.policy=wideopen.policy


@set CP=.\lib\log4j-1.2.8.jar;.\lib\E2MInterface.jar;.\dist\TestLegacy.jar 

%JAVA_HOME%\bin\java -cp %CP% %OPTS% org.eMayor.legacy.test.test.TestClient %*

@endlocal