REM CT angelegt 04.11.2019
REM SPA 20191007 Check . KEV-Import nicht existent.. Standardaufruf
REM SPA 20191208 Konfirm fuer den ersten tag auskommentieren .. checken .. spaeter wieder rein
REM              Archvierung Script _Start_import_Archivieren existiert nicht
REM SPA 20200109 Einbau der Infomeldung, wenn Fehler seitens der BuBa bei der Einreichung gemeldet werden ..
REM SPA 20200710 Einbau der Weiterleitung der Quittierungsdatei an KIT
REM SPA 20200713 - Einbau Protokollcheck - Logging
REM SPA 20200721 - Protokollerweiterung
REM              

FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_KEV_EXPORT_error.log) do (SET fehler=%%i)
if %fehler%==1 goto Ende

REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

REM SPA 20200109 Loeschen der Meldungsdatei
F:
cd F:\CAM_009_PROD\PROTOKOLL\LB
del /q Meldung_BuBa_Fehler.txt

:Quittung
F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD
call _Start_EXTRANET_Test_KEVEA.cmd

cd F:\CAM_009_PROD\PROGRAMM\LB
call KEVCheckQuittung.bat F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log
goto Start

timeout /T 900

goto Quittung

:Start

F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Download_KEVEA.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Quittung.cmd_DL_EA" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Import_Downloads.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Quittung.cmd_DL_IM" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM SPA 20200109 Wenn Fehler sonst Weiter
if %fehler%==0 goto Weiter
copy /Y F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\KEV_Export*.xml F:\CAM_009_PROD\PROTOKOLL\LB\Meldung_BuBa_Fehler.txt
REM Aufruf CheckLog

REM Im Fehlerfall Confirm etc unterlassen und springen ... goto Ende

REM SPA 20200109 Hier geht es immer weiter
:Weiter

cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Automatiken.cmd

REM Confirm
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
REM call _Start_KEV_Confirm_KEVEA.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
REM powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Quittung.cmd_DL_CON" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM SPA 20200710 Einbau der Weiterleitung der Quittierungsdatei an KIT
REM 
powershell F:\CAM_009_PROD\PROGRAMM\LB\TransferBUBAQUITT2KIT.ps1 > %NLBProtokolldir%\Infos_TRKEVEINQUITT_PS_%mybasedate%.log
REM Auch im Fehlerfall einfach weiter

REM Importverzeichnis aufräumen - Archivieren der Importdateien
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA
replace /r/a F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\KEV_Export*.xml F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\Archiv
del /q F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\KEV_Export*.xml
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\Archiv
zip -m F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\Archiv\BuBaQuitt_%Zip-Date-Suffix%.zip KEV_Export*.xml

:Ende

REM Fertigereignis in PROST setzen
FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_Import_Quittung.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

REM SPA 20200713 - Einbau Protokollcheck - Logging
goto GanzEnde

:Fehler
powershell F:\CAM_009_PROD\PROGRAMM\LB\camprot_versand.ps1
exit 1
# Hier geht es immer raus 
:GanzEnde
