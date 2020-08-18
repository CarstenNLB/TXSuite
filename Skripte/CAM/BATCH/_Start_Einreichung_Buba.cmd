REM erstellt TT 20191209
REM SPA 20200713 Erweiterung fuer die Rueckmeldung - Datum hinterlegen
REM SPA 20200713 - Alte dp-Datei sichern
REM SPA 20200713 - Einbau Protokollcheck - Logging
REM SPA 20200721 - Protokollerweiterung

copy /y F:\CAM_009_PROD\STATUS\dptxt\dp.txt F:\CAM_009_PROD\STATUS\dptxt\dp.txt.vortag
del /q F:\CAM_009_PROD\STATUS\dptxt\dp.txt

REM DP-Suffix fuer Rueckmeldung
set DP-Suffix=%date:~6,4%-%date:~3,2%-%date:~0,2%
echo %DP-Suffix% >F:\CAM_009_PROD\STATUS\dptxt\dp.txt

F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Export_Upload.cmd

FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_KEV_EXPORT_error.log) do (SET fehler=%%i)
if %fehler%==1 goto Fehler

REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Einreichung_Buba.cmd" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM Fertigereignis in PROST setzen
FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_Einreichung_Buba.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

exit

:Fehler
F:\PROST\KEV\SendMail-Einreichung_Buba.bat
exit

:PRFehler
powershell F:\CAM_009_PROD\PROGRAMM\LB\camprot_versand.ps1
exit 
