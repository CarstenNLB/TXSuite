REM _Start_EXTRANET_Test_MACNN.cmd
REM SPA 20200818 - Script zum Test von Nachrichtendateien

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
call CAM.exe     -env="F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\CAM_Buba.ini" -extranet_test=MACNN;<###pwd###>
echo %errorlevel% > F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_MACNN_error.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAM_TEST_MAC_NN_%Date-Suffix%%Time-Suffix%.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\TextWriterOutput.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAMPROTO_TEST_MACC_NN_%Date-Suffix%%Time-Suffix%.log
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_EXTRANET_Test_MACCN.cmd" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto Fehler

REM Fertigereignis in PROST setzen
FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_Test_MACCN.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

REM SPA 20200713 - Einbau Protokollcheck - Logging
goto Ende

:Fehler
powershell F:\CAM_009_PROD\PROGRAMM\LB\camprot_versand.ps1
exit 1
# Hier geht es immer raus 
:Ende
