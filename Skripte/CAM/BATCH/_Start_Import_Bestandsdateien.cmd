REM CT angelegt 04.11.2019
REM SPA 20191207 Check Import wurde standarsisiert, daher Anpassung des Scriptnamen
REM SPA 20191208 Konfirm auskommentiert w/Check beim ersten Lauf
REM              Archivierung
REM SPA 20200414 Erste Abfrage dupliziert und auf -1 angepasst
REM SPA 20200713 - Einbau Protokollcheck - Logging
REM SPA 20200721 - Protokollerweiterung

FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_KEVEB_error.log) do (SET fehler=%%i)
if %fehler%==1 goto Fehler
if %fehler%==-1 goto Fehler

REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Download_KEVEB.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Bestandsdateien.cmd_DL_B" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Import_Downloads.cmd

FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_KEV_Import_error.log) do (SET fehler=%%i)
if %fehler%==1 goto Fehler

cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Automatiken.cmd

REM SPA 20191007 Erstellung der Rückmeldeinformation und Versand an ABACUS (SPoT), CMC und OTC
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD
call _Start_Reports_KEV_Rueck.cmd

REM Versand der BuBa-Bestandsdatei an OE QM Pfandbrief

cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD
call _Start_Transfer_KEVBUBABSTD2OEQM.cmd

REM Jetzt Archivierung 
REM Importverzeichnis aufräumen - Archivieren der Importdateien
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEB
replace /r/a F:\CAM_009_PROD\DATEN\BuBa\import\KEVEB\bestand*.xml F:\CAM_009_PROD\DATEN\BuBa\import\KEVEB\Archiv
replace /r/a F:\CAM_009_PROD\DATEN\BuBa\import\KEVEB\rueckgabe*.xml F:\CAM_009_PROD\DATEN\BuBa\import\KEVEB\Archiv
del /q F:\CAM_009_PROD\DATEN\BuBa\import\KEVEB\bestand*.xml
del /q F:\CAM_009_PROD\DATEN\BuBa\import\KEVEB\rueckgabe*.xml
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEB\Archiv
zip -m F:\CAM_009_PROD\DATEN\BuBa\import\KEVEB\Archiv\BuBaNoBaBstd_%Zip-Date-Suffix%.zip bestand*.xml
zip -m F:\CAM_009_PROD\DATEN\BuBa\import\KEVEB\Archiv\BuBaNoBaRueck_%Zip-Date-Suffix%.zip rueckgabe*.xml

rem Erster Lauf sollte kontrollliert laufen .. ohne confirm .. diese muss manuell gestartet werden 
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Confirm_KEVEB.cmd
REM SPA 20200713 - Einbau Protokollcheck - Logging
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadTXSBatchLog.ps1 -Aufruf "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\_Start_Import_Bestandsdateien.cmd_DL_CON" -LogDatIn "F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log" -LogDatOut F:\CAM_009_PROD\PROTOKOLL\LB\CAMProtokoll.txt >F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
REM IF not %errorlevel%==0 goto PRFehler

REM so jetzt sollte alles komplett sein

REM Fertigereignis in PROST setzen
FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_Import_Bestandsdateien.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

exit

:Fehler
F:\PROST\KEV\SendMail-Bestandsdatei.bat
exit

:PRFehler
powershell F:\CAM_009_PROD\PROGRAMM\LB\camprot_versand.ps1
exit
