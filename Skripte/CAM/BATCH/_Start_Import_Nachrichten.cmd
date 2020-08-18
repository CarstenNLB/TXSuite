REM _Start_Import_Nachrichten.cmd
REM SPA 20200818 - Aufbau Download und Versand von Nachrichten

FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_MACNN_error.log) do (SET fehler=%%i)
if %fehler%==1 goto Fehler

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

set TXSProtokolldir=F:\CAM_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set NLBProtokolldir=F:\CAM_009_PROD\PROTOKOLL\LB

REM *************************************************************************************************************************************************************************
REM Bereinigung der Zwischendateien soll in den Scripten erfolgen
REM Die pdf-Dateien werden hier geloescht
REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_Transfer\TXSKEV\export\QMMACNN
del /q *.pdf
REM *************************************************************************************************************************************************************************
REM LogFiles zippen & archivieren
REM *************************************************************************************************************************************************************************
F:
cd F:\CAM_009_PROD\PROTOKOLL\LB
zip -m F:\CAM_009_PROD\PROTOKOLL\LB\RMInfos_TRKEVMACNN2QM_%Zip-Date-Suffix%.zip Infos_TRKEVMACNN2QM_PS_*.log
del /q Meldung.txt

REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

REM Downloads und formalen Import MACNN - der wird in der Im-/Exportübersicht als Unbekannt (N/A) geführt
REM Download MACNN
F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Download_MACNN.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Nachrichten.cmd_DL_MACNN" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM Nach jedem Schritt ist eine Kontrolle moeglich!

REM Importskript
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Import_Downloads.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Nachrichten.cmd_DL_IM" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM For later use -- Knotenberechnung .. sollte keine Relevanz haben 
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Automatiken.cmd

rem Erster Lauf sollte kontrollliert laufen .. ohne confirm .. diese muss manuell gestartet werden 
REM F:
REM cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
REM call _Start_KEV_Confirm_MACNN.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
REM powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Nachrichten.cmd_IN_CON" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM Importverzeichnis aufräumen - 
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN
copy   F:\CAM_009_PROD\Daten\BuBa\import\KEVEN\*.pdf   F:\TXS_009_Transfer\TXSKEV\export\QMMACNN
del /q F:\CAM_009_PROD\Daten\BuBa\import\KEVEN\*.pdf
REM Positionierung belibt erhalten
F:
cd F:\TXS_009_Transfer\TXSKEV\export\QMMACNN
REM *************************************************************************************************************************************************************************
REM Transfer der Datei an OE QM
REM *************************************************************************************************************************************************************************
powershell F:\CAM_009_PROD\PROGRAMM\LB\TransferKEVMACNN2QM.ps1 > %NLBProtokolldir%\Infos_TRKEVMACNN2QM_PS_%mybasedate%.log
if %errorlevel%==1 goto fehler
REM Hier hat der Versand funktioniert .. also ins Archiv zippen
replace /r/a  F:\TXS_009_Transfer\TXSKEV\export\QMMACNN\*.pdf   F:\TXS_009_Transfer\TXSKEV\export\QMMACNN\Archiv
del /q  F:\TXS_009_Transfer\TXSKEV\export\QMMACNN\*.pdf
F:
cd F:\TXS_009_Transfer\TXSKEV\export\QMMACNN\Archiv
zip -m F:\TXS_009_Transfer\TXSKEV\export\QMMACNN\Archiv\KEV2QMMACNN_%Zip-Date-Suffix%.zip *.pdf
REM Nicht ueber den Fehler laufen 
goto Ende

:fehler
REM Wenn kein Versand möglich
F:
cd F:\CAM_009_PROD\PROTOKOLL\LB
copy /Y Infos_TRKEVMACNN2QM_PS*.log Meldung.txt
powershell F:\CAM_009_PROD\PROGRAMM\LB\kevmacnn_versand.ps1 
REM exit 1

# Hier geht es immer raus 
:Ende

REM Fertigereignis in PROST setzen
FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_Import_NACHRICHTEN.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

exit

:PRFehler
powershell F:\CAM_009_PROD\PROGRAMM\LB\camprot_versand.ps1
exit 1
