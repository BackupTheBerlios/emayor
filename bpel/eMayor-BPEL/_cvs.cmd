@setlocal
@echo off
echo ------------------------------------------------------------------------------

if %1//////==////// goto NO_FIRST_ARG

@call ..\cvs_env.cmd

if %1==update goto CMD_UPDATE
if %1==commit goto CMD_COMMIT
if %1==add goto CMD_ADD
goto BAD_COMMAND

:CMD_UPDATE
echo ... updating sources ....
%CVS_CMD% -d %CVS_SERVER_URL% update %2 %3 %4 %5 %6 %7 %8 %9
goto END

:CMD_COMMIT
echo ... comiting changes ....
%CVS_CMD% -d %CVS_SERVER_URL% commit %2 %3 %4 %5 %6 %7 %8 %9
goto END 

:CMD_ADD
echo ... adding new sources ....
%CVS_CMD% -d %CVS_SERVER_URL% add %2 %3 %4 %5 %6 %7 %8 %9
goto END

:NO_FIRST_ARG
echo The cvs command is mnissing
echo Please chose {update,commit,add}
goto END

:BAD_COMMAND
echo The command you're using is not supported
echo Please chose {update,commit,add}
goto END

:END
echo ------------------------------------------------------------------------------
@endlocal