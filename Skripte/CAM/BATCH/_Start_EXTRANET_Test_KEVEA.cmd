REM TT 20191202 - KEV Pruefung ob Quittung vorhanden
REM SPA 20191207 - Time
REM SPA 20200713 - Einbau Protokollcheck - Logging
REM SPA 20200721 - Protokollerweiterung
REM SPA 20200731 - MACCs-Vorbereitung
REM SPA 20200801 - MACCs-Umstellung

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
rem call CAM.exe -env="F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\CAM_Buba.ini" -extranet_test=KEVEA;<###pwd###>
call CAM.exe     -env="F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\CAM_Buba.ini" -extranet_test="MACQK,MACQS";<###pwd###>
echo %errorlevel% > F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_KEVEA_error.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAM_TEST_KEV_EA_%Date-Suffix%%Time-Suffix%.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\TextWriterOutput.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAMPROTO_TEST_KEV_EA_%Date-Suffix%%Time-Suffix%.log

REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_EXTRANET_Test_KEVEA.cmd" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto Fehler

REM SPA 20200713 - Einbau Protokollcheck - Logging
goto Ende

:Fehler
powershell F:\CAM_009_PROD\PROGRAMM\LB\camprot_versand.ps1
exit 1
# Hier geht es immer raus 
:Ende
