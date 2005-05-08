@setlocal
@echo off

@rem usage:
@rem export-certificate.cmd keystore cer-file alias 

if %JAVA_HOME%//==// goto no_java_home
@set __KEYTOOL__=%JAVA_HOME%\bin\keytool

if %1//==// goto def_keystore
@echo inputed the following keystore location %1
@set __KEYSTORE__=%1
@goto param2

:def_keystore
@echo no keystore location inputed, so using default keystore client.keystore
@echo note please! the dafault password is client
@set __KEYSTORE__=client.keystore
@goto param2

:param2
if %2//==// goto def_cer_file
@echo inputed the following certificate file location %2
@set __CERFILE__=%2
@goto param3

:def_cer_file
@echo no certificate location location inputed, so using default certificate file client.cer
@echo note please! the dafault password is client
@set __CERFILE__=client.cer
@goto param3

:param3
if %3//==// goto def_alias
@echo inputed the following alias %3
@set __ALIAS__=%3
@goto import_it

:def_alias
@echo no alias inputed, so using default one client
@echo note please! the dafault password is client
@set __ALIAS__=client
@goto import_it

:import_it
%__KEYTOOL__% -import -keystore %__KEYSTORE__% -alias %__ALIAS__% -file %__CERFILE__%
@goto end

:no_java_home
@echo the JAVA_HOME environment variable ist not set but it has to point to the your java installation directory e.g. e:/java/j2sdkse
@echo please fix this before continue
@goto end

:end

@endlocal