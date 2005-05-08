@setlocal
@cls
@echo off

if %JAVA_HOME%//==// goto no_java_home
@set __KEYTOOL__=%JAVA_HOME%\bin\keytool
@goto doit


:doit
@echo export the client public key into client.cert
%__KEYTOOL__% -export -keystore client.keystore -alias client -file client.cer -storepass client
@echo EXPORTED

@echo export the first server public key into server1.cert
%__KEYTOOL__% -export -keystore server1.keystore -alias server1 -file server1.cer -storepass server1
@echo EXPORTED

@echo export the second server public key into server2.cert
%__KEYTOOL__% -export -keystore server2.keystore -alias server2 -file server2.cer -storepass server2
@echo EXPORTED




@echo import the both public keys into first server keystore
%__KEYTOOL__% -import -keystore server1.keystore -alias client -file client.cer -storepass server1
%__KEYTOOL__% -import -keystore server1.keystore -alias server2 -file server2.cer -storepass server1
@echo IMPORTED

@echo import the both public keys into second server keystore
%__KEYTOOL__% -import -keystore server2.keystore -alias client -file client.cer -storepass server2
%__KEYTOOL__% -import -keystore server2.keystore -alias server1 -file server1.cer -storepass server2
@echo IMPORTED

@echo import the both public keys into client keystore
%__KEYTOOL__% -import -keystore client.keystore -alias server1 -file server1.cer -storepass client
%__KEYTOOL__% -import -keystore client.keystore -alias server2 -file server2.cer -storepass client
@echo IMPORTED
@goto end


:no_java_home
@echo the JAVA_HOME environment variable ist not set but it has to point to the your java installation directory e.g. e:/java/j2sdkse
@echo please fix this before continue
@goto end

:end
@echo DONE


@endlocal