REM TT 20191202 Pruefung ob Notenbankfaehige zum Download vorhanden sind
REM SPA 20191207 - Time
REM SPA 20200731 - MACCs-Vorbereitung
REM SPA 20200801 - MACCs-Umstellung
REM SPA 20200804 - Erweiterung auf oeffentliche Schuldner

REM Date-Suffix
rem set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%%time:~0,2%%time:~3,2%
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%
REM Time-Suffix
set Time-Suffix=%time:~0,2%%time:~3,2%%time:~6,2%
if "%Time-Suffix:~0,1%"==" " set Time-Suffix=0%Time-Suffix:~1,6%

F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD

rem ------------TESTE Download --------------------
del /q F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\TextWriterOutput.log
call CAM.exe     -env="F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\CAM_Buba.ini" -extranet_import="MACIN,MACIP";"F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN";<###pwd###>
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAM_TEST_DL_KEV_ENEP_%Date-Suffix%%Time-Suffix%.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\TextWriterOutput.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAMPROTO_TEST_KEV_DL_ENEP_%Date-Suffix%%Time-Suffix%.log

