@setlocal
@echo off
echo ------------------------------------------------------------------------------

if %1//////==////// goto NO_FIRST_ARG

@call ..\cvs_env.cmd

if %1==update goto CMD_UPDATE
if %1==commit goto CMD_COMMIT
if %1==add goto CMD_ADD
if %1==co goto CMD_CO
if %1==import goto CMD_IMPORT
goto BAD_COMMAND

:CMD_UPDATE
echo ... updating sources ....
set CUR_CMD=update
goto PROCESSING

:CMD_COMMIT
echo ... comiting changes ....
set CUR_CMD=commit 
goto PROCESSING

:CMD_ADD
echo ... adding new sources ....
set CUR_CMD=add
goto PROCESSING

:CMD_CO
echo ... checking out something ...
set CUR_CMD=co
goto PROCESSING

:CMD_IMPORT
echo ... importing the files ...
set CUR_CMD=import
goto PROCESSING

:PROCESSING
%CVS_CMD% -d %CVS_SERVER_URL% %CUR_CMD% %2 %3 %4 %5 %6 %7 %8 %9
goto END

:NO_FIRST_ARG
echo The cvs command is mnissing
echo Please chose {update,commit,add,co,import}
goto END

:BAD_COMMAND
echo The command you're using is not supported
echo Please chose {update,commit,add,co,import}
goto END

:END
echo ------------------------------------------------------------------------------
@endlocal