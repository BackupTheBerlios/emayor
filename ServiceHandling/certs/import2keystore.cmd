@setlocal
@ECHO OFF

if %JAVA_HOME%\\\\\\==\\\\\\ goto NO_JAVA_HOME

if %1\\\\\==\\\\\ goto NO_KEYSTORE

@set KEYTOOL=%JAVA_HOME%\bin\keytool.exe

%KEYTOOL% -import -trustcacerts -alias aMayor -file ca-cert.pem -keystore %1 

goto END


:NO_JAVA_HOME
echo ERROR: set the JAVA_HOME env variable
goto END

:NO_KEYSTORE
echo ERROR: the keystore file is missing
goto USAGE


:USAGE
echo usage:
echo import2keystore.cm keystorefile
echo sample: import2keystore c:\java\jre\lib\security\cacerts
goto END

:END

@endlocal