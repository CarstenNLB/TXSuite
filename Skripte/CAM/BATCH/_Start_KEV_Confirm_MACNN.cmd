REM SPA 20200731 - MACCs-Vorbereitung MACNN - NEU InfoDateien
REM SPA 20200801 - MACCs-Umstellung

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%
REM Time-Suffix
set Time-Suffix=%time:~0,2%%time:~3,2%%time:~6,2%
if "%Time-Suffix:~0,1%"==" " set Time-Suffix=0%Time-Suffix:~1,6%

F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD


rem ------------TESTE Confirm --------------------

del /q F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\TextWriterOutput.log
call CAM.exe -env="F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\CAM_Buba.ini" -kev_confirm=MACNN
echo %errorlevel% > F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_MACNN_Confirm_error.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAM_KEV_CON_MACNN_%Date-Suffix%%Time-Suffix%.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\TextWriterOutput.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAMPROTO_TEST_KEV_CON_MACNN_%Date-Suffix%%Time-Suffix%.log