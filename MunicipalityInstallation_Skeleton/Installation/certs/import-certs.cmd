@setlocal
@echo off

if "___%JAVA_HOME%___" == "______" goto NO_JAVA_HOME

if "___%JRE_HOME%___" == "______" goto NO_JRE_HOME

set KEYTOOL="%JAVA_HOME%\bin\keytool"

rem JAVA_HOME
set KEYSTORE="%JAVA_HOME%\jre\lib\security\cacerts"
copy %KEYSTORE% %KEYSTORE%.old
%KEYTOOL% -import -noprompt -keystore %KEYSTORE% -storepass changeit -alias eMayor-old -trustcacerts -file ca-cert.pem
%KEYTOOL% -import -noprompt -keystore %KEYSTORE% -storepass changeit -alias eMayorOperational -trustcacerts -file eMayorOperational.cer
%KEYTOOL% -import -noprompt -keystore %KEYSTORE% -storepass changeit -alias eMayorRoot -trustcacerts -file eMayorRoot.crt

rem JRE_HOME
set KEYSTORE="%JRE_HOME%\lib\security\cacerts"
copy %KEYSTORE% %KEYSTORE%.old
%KEYTOOL% -import -noprompt -keystore %KEYSTORE% -storepass changeit -alias eMayor-old -trustcacerts -file ca-cert.pem
%KEYTOOL% -import -noprompt -keystore %KEYSTORE% -storepass changeit -alias eMayorOperational -trustcacerts -file eMayorOperational.cer
%KEYTOOL% -import -noprompt -keystore %KEYSTORE% -storepass changeit -alias eMayorRoot -trustcacerts -file eMayorRoot.crt
goto END

:NO_JAVA_HOME
echo ERROR
echo Please set up the JAVA_HOME env var
goto END

:NO_JRE_HOME
echo ERROR
echo Please set up the JRE_HOME env var
goto END

:END

@endlocal