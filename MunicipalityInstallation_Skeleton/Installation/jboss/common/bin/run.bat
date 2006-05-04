@echo off
rem -------------------------------------------------------------------------
rem JBoss Bootstrap Script for Win32
rem -------------------------------------------------------------------------

rem $Id: run.bat,v 1.13 2004/05/06 15:52:34 tdiesler Exp $

@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT"  setlocal

set DIRNAME=.\
if "%OS%" == "Windows_NT" set DIRNAME=%~dp0%
set PROGNAME=run.bat
if "%OS%" == "Windows_NT" set PROGNAME=%~nx0%

rem Read all command line arguments

REM
REM The %ARGS% env variable commented out in favor of using %* to include
REM all args in java command line. See bug #840239. [jpl]
REM
REM set ARGS=
REM :loop
REM if [%1] == [] goto endloop
REM         set ARGS=%ARGS% %1
REM         shift
REM         goto loop
REM :endloop

rem Find run.jar, or we can't continue

set RUNJAR=%DIRNAME%\run.jar
if exist "%RUNJAR%" goto FOUND_RUN_JAR
echo Could not locate %RUNJAR%. Please check that you are in the
echo bin directory when running this script.
goto END

:FOUND_RUN_JAR

if not "%JAVA_HOME%" == "" goto ADD_TOOLS

set JAVA=java

echo JAVA_HOME is not set.  Unexpected results may occur.
echo Set JAVA_HOME to the directory of your local JDK to avoid this message.
goto SKIP_TOOLS

:ADD_TOOLS

set JAVA=%JAVA_HOME%\bin\java

if exist "%JAVA_HOME%\lib\tools.jar" goto SKIP_TOOLS
echo Could not locate %JAVA_HOME%\lib\tools.jar. Unexpected results may occur.
echo Make sure that JAVA_HOME points to a JDK and not a JRE.

:SKIP_TOOLS

rem Include the JDK javac compiler for JSP pages. The default is for a Sun JDK
rem compatible distribution to which JAVA_HOME points

set JAVAC_JAR=%JAVA_HOME%\lib\tools.jar

rem If JBOSS_CLASSPATH is empty, don't include it, as this will 
rem result in including the local directory, which makes error tracking
rem harder.
if "%JBOSS_CLASSPATH%" == "" (
	set JBOSS_CLASSPATH=%JAVAC_JAR%;%RUNJAR%
) ELSE (
	set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%JAVAC_JAR%;%RUNJAR%
)


rem Setup JBoss specific properties
set JAVA_OPTS=%JAVA_OPTS% -Dprogram.name=%PROGNAME%
rem set JAVA_OPTS=%JAVA_OPTS% -Djavax.net.debug=SSL
set JBOSS_HOME=%DIRNAME%\..

rem Sun JVM memory allocation pool parameters. Modify as appropriate.
set JAVA_OPTS=%JAVA_OPTS% -Xms128m -Xmx512m 
set JAVA_OPTS=%JAVA_OPTS% -Djavax.net.ssl.keyStore=%JBOSS_HOME%\server\default\conf\Server.keystore
set JAVA_OPTS=%JAVA_OPTS% -Djavax.net.ssl.keyStorePassword=test
set JAVA_OPTS=%JAVA_OPTS% -Djava.protocol.handler.pkgs=com.sun.net.ssl.internal.www.protocol
set JAVA_OPTS=%JAVA_OPTS% -Djavax.net.ssl.trustStore=%JBOSS_HOME%\server\default\conf\Server.truststore
set JAVA_OPTS=%JAVA_OPTS% -Djavax.net.ssl.trustStorePassword=123456

rem JPDA options. Uncomment and modify as appropriate to enable remote debugging.
rem set JAVA_OPTS=-classic -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y %JAVA_OPTS%

rem Setup the java endorsed dirs
set JBOSS_ENDORSED_DIRS=%JBOSS_HOME%\lib\endorsed

rem testing the rmi
set JAVA_OPTS=%JAVA_OPTS% -Djava.security.policy=%JBOSS_HOME%\server\default\conf\wideopen.policy

rem set JBOSS_CLASSPATH=%JBOSS_CLASSPATH%;%JBOSS_HOME%\server\default\lib\TestLegacyAachenClient.jar
rem set CLASSPATH=%JBOSS_HOME%\server\default\lib\TestLegacyAachenClient.jar

echo ===============================================================================
echo .
echo   JBoss Bootstrap Environment
echo .
echo   JBOSS_HOME: %JBOSS_HOME%
echo .
echo   JAVA: %JAVA%
echo .
echo   JAVA_OPTS: %JAVA_OPTS%
echo .
echo   CLASSPATH: %JBOSS_CLASSPATH%
echo .
echo   JBOSS_ENDORSED_DIRS: %JBOSS_ENDORSED_DIRS%
echo .
echo ===============================================================================
echo .

:RESTART

echo ******************************************************************
echo "%JAVA%" %JAVA_OPTS% -Djava.endorsed.dirs=%JBOSS_ENDORSED_DIRS% -classpath "%JBOSS_CLASSPATH%" org.jboss.Main %*
echo *******************************************************************
"%JAVA%" %JAVA_OPTS% -Djava.endorsed.dirs=%JBOSS_ENDORSED_DIRS% -classpath "%JBOSS_CLASSPATH%" org.jboss.Main %*
IF ERRORLEVEL 10 GOTO RESTART

:END
if "%NOPAUSE%" == "" pause

:END_NO_PAUSE
