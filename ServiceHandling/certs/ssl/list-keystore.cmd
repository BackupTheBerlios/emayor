@setlocal
@echo off

if %JAVA_HOME%//==// goto no_java_home
@set __KEYTOOL__=%JAVA_HOME%\bin\keytool

if %1//==// goto def_keystore
@echo inputed the following keystore location %1
@set __KEYSTORE__=%1
@goto list_it

:def_keystore
@echo no keystore location inputed, so using default keystore client.keystore
@echo note please! the dafault password is client
@set __KEYSTORE__=client.keystore
@goto list_it

:list_it
%__KEYTOOL__% -list -v -keystore %__KEYSTORE__%
@goto end

:no_java_home
@echo the JAVA_HOME environment variable ist not set but it has to point to the your java installation directory e.g. e:/java/j2sdkse
@echo please fix this before continue
goto end

:end

@endlocal