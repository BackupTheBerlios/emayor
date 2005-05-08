@setlocal
@cls
@echo off

if %JAVA_HOME%//==// goto no_java_home
@set __KEYTOOL__=%JAVA_HOME%\bin\keytool
@goto gen

:gen
@echo generating and storing the certificate for the first server
%__KEYTOOL__% -genkey -keystore server1.keystore -storepass server1 -keypass server1 -keyalg RSA -alias server1 -validity 3650 -dname "cn=eMayor-Server-1,ou=ELAN,O=FHI FOKUS,L=Berlin,C=Germany,dc=org"

@echo generating and storing the certificate for the second server (JBoss2)
%__KEYTOOL__% -genkey -keystore server2.keystore -storepass server2 -keypass server2 -keyalg RSA -alias server2 -validity 3650 -dname "cn=eMayor-Server-2,ou=ELAN,O=FHI FOKUS,L=Berlin,C=Germany,dc=org"

@echo generating and storing the certificate for the client
%__KEYTOOL__% -genkey -keystore client.keystore -storepass client -keypass client -keyalg RSA -alias client -validity 3650 -dname "cn=eMayor-client,ou=ELAN,O=FHI FOKUS,L=Berlin,C=Germany,dc=org"
@goto end

:no_java_home
@echo the JAVA_HOME environment variable ist not set but it has to point to the your java installation directory e.g. e:/java/j2sdkse
@echo please fix this before continue
@goto end

:end
@echo DONE


@endlocal