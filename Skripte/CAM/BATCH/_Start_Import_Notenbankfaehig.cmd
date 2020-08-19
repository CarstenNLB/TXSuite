REM CT angelegt 04.11.2019
REM SPA 20191208 Archivierung
REM SPA 20200117 Es stand ein REM vor dem ersten cd .. Skripte.. seit dem 16.12. keine notenbankfaehige Schulderndatei geholt ...
REM              REM geloescht
REM SPA 20200713 - Einbau Protokollcheck - Logging
REM SPA 20200721 - Protokollerweiterung
REM SPA 20200803 - Anpassung an MACCs
REM SPA 20200804 - Erweiterung auf Oeffentliche Schuldner 
REM SPA 20200804 - Es fehlt noch das Zusammenführen - nicht notwendig .. beide Dateien werden korrekt importiert
REM SPA 20200805 - Confirm eingebaut
REM SPA 20200805 - Getrennte Downloads eingebaut

FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_KEVEN_error.log) do (SET fehler=%%i)
if %fehler%==1 goto Fehler

REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

REM Aufteilung des Downloads und Imports MACIN und MACIP
REM Teil 1 MACIN
F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Download_KEVEN.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Notenbankfaehig.cmd_DL_N" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM Nach jedem Schritt ist eine Kontrolle moeglich!

REM Importskript
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Import_Downloads.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Notenbankfaehig.cmd_DL_IM" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM Teil 2 MACIP
F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Download_MACIP.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Notenbankfaehig.cmd_DL_NIP" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM Nach jedem Schritt ist eine Kontrolle moeglich!

REM Importskript
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Import_Downloads.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Notenbankfaehig.cmd_DL_IMIP" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Automatiken.cmd

REM Importverzeichnis aufräumen - Archivieren der Importdateien
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN
copy         F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\notenbankfaehig*.xml         F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv
replace /r/a F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\notenbankfaehig*.xlsx        F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv
copy         F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Oeffentliche_Schuldner*.xml  F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv
replace /r/a F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Oeffentliche_Schuldner*.xlsx F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv
REM del /q F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\notenbankfaehig*.xml 
del /q F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\notenbankfaehig*.xlsx
REM del /q F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Oeffentliche_Schuldner*.xml
del /q F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Oeffentliche_Schuldner*.xlsx
REM replace /r/a F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\notenbankfaehig*.xml F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv
REM replace /r/a F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\notenbankfaehig*.csv F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv
REM del /q F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\notenbankfaehig*.xml 
REM del /q F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\notenbankfaehig*.csv
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv
zip -m F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv\BuBaNoBaFaexml_%Zip-Date-Suffix%.zip notenbankfaehig*.xml
zip -m F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv\BuBaNoBaFaexlsx_%Zip-Date-Suffix%.zip notenbankfaehig*.xlsx
zip -m F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv\BuBaOeBaFaexml_%Zip-Date-Suffix%.zip Oeffentliche_Schuldner*.xml
zip -m F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv\BuBaOeBaFaexlsx_%Zip-Date-Suffix%.zip Oeffentliche_Schuldner*.xlsx
REM zip -m F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv\BuBaNoBaFaexml_%Zip-Date-Suffix%.zip notenbankfaehig*.xml
REM zip -m F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN\Archiv\BuBaNoBaFaecsv_%Zip-Date-Suffix%.zip notenbankfaehig*.csv

rem Erster Lauf sollte kontrollliert laufen .. ohne confirm .. diese muss manuell gestartet werden 
F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Confirm_KEVEN.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Notenbankfaehig.cmd_IN_CON" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM Neu der Merger
F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD
call _Start_Import_NotenbankfaehigMerger.cmd

REM Fertigereignis in PROST setzen
FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_Import_Notenbankfaehig.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

exit

:Fehler
F:\PROST\KEV\SendMail-Notenbankfaehig.bat
exit

:PRFehler
powershell F:\CAM_009_PROD\PROGRAMM\LB\camprot_versand.ps1
exit 1
