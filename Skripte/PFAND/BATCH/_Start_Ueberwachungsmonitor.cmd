REM SPA 20191111 - Gläubigerwechsel eingebaut
REM SPA 20190812 - Loeschen der Report-Protokolldateien 
REM SPA 20180627 - Am Ende Audruf von Ueberwachungsmonitor loeschen
REM Angelegt CT 15.05.2018

REM SU 201812 _Start_nlbstat.cmd aufgenommen

REM *************************************************************************************************************************************************************************
REM Lieferstatistik in DB einpflegen und verschicken
REM sollte nur einmal pro Stichtag laufen
REM *************************************************************************************************************************************************************************
call F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\_Start_nlbstat.cmd

REM *************************************************************************************************************************************************************************
REM Report-LogFiles loeschen 
REM *************************************************************************************************************************************************************************
del /q F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\s*_al.log
del /q F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\s*_el.log
del /q F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\s*_pl.log
REM *************************************************************************************************************************************************************************
REM Weiter geht es 
REM 
REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM Verzeichnis anlegen und kopieren
mkdir F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Marktdaten_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

copy F:\TXS_009_PROD\IMPORT\Kunde_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

rem copy F:\TXS_009_PROD\IMPORT\MAVIS_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Cashflows_MAVIS_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

rem copy F:\TXS_009_PROD\IMPORT\DARLWP_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_009_PROD\IMPORT\DARLWP_Schiffe_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_009_PROD\IMPORT\DARLWP_Flugzeuge_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_009_PROD\IMPORT\DARLWP_OEPG_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

copy F:\TXS_009_PROD\IMPORT\Kredite_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Kredite_Flugzeuge_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Kredite_OEPG_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

copy F:\TXS_009_PROD\IMPORT\FRISCO_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

REM MIDAS kopieren
copy F:\TXS_009_PROD\IMPORT\Kredite_MIDAS_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Cashflows_MIDAS_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

copy F:\TXS_009_PROD\IMPORT\Kredite_LoanIQ_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Passiv_LoanIQ_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Cashflows_LoanIQ_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

REM BLB-Dateien im NLB-Verzeichnis kopieren
copy F:\TXS_009_PROD\IMPORT\Kunde_BLB_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_009_PROD\IMPORT\MAVIS_BLB_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Cashflows_MAVIS_BLB_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_009_PROD\IMPORT\DARLWP_BLB_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_009_PROD\IMPORT\DarlWP_Schiffe_BLB_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Kredite_BLB_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_BLB_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\FRISCO_BLB_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

REM Gesamt-Dateien kopieren
copy F:\TXS_009_PROD\IMPORT\Kredite_Gesamt_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_Gesamt_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Kunde_Gesamt_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_009_PROD\IMPORT\DARLWP_Gesamt_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_009_PROD\IMPORT\DARLWP_Schiffe_Gesamt_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_009_PROD\IMPORT\MAVIS_Gesamt_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_009_PROD\IMPORT\Cashflows_Gesamt_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

REM BLB Verzeichnis anlegen und Dateien kopieren
mkdir F:\TXS_004_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

REM BLB-Dateien kopieren
copy F:\TXS_004_PROD\IMPORT\Kunde_TXS.xml F:\TXS_004_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_004_PROD\IMPORT\MAVIS_TXS.xml F:\TXS_004_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_004_PROD\IMPORT\Cashflows_MAVIS_TXS.xml F:\TXS_004_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_004_PROD\IMPORT\DARLWP_TXS.xml F:\TXS_004_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
rem copy F:\TXS_004_PROD\IMPORT\DarlWP_Schiffe_TXS.xml F:\TXS_004_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_004_PROD\IMPORT\Kredite_TXS.xml F:\TXS_004_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_004_PROD\IMPORT\Kredite_Schiffe_TXS.xml F:\TXS_004_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\TXS_004_PROD\IMPORT\FRISCO_TXS.xml F:\TXS_004_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

del /q F:\TXS_009_PROD\IMPORT\Marktdaten_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\Kunde_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\MAVIS_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Cashflows_MAVIS_TXS.xml

rem del /q F:\TXS_009_PROD\IMPORT\DARLWP_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\DARLWP_Schiffe_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\DARLWP_Flugzeuge_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\DARLWP_OEPG_TXS.xml

del /q F:\TXS_009_PROD\IMPORT\Kredite_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Kredite_Flugzeuge_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Kredite_OEPG_TXS.xml

del /q F:\TXS_009_PROD\IMPORT\FRISCO_TXS.xml

del /q F:\TXS_009_PROD\IMPORT\Kredite_LoanIQ_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Passiv_LoanIQ_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Cashflows_LoanIQ_TXS.xml

REM MIDAS loeschen
del /q F:\TXS_009_PROD\IMPORT\Kredite_MIDAS_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Cashflows_MIDAS_TXS.xml

REM BLB-Dateien im NLB-Verzeichnis loeschen
rem del /q F:\TXS_009_PROD\IMPORT\Kunde_BLB_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\MAVIS_BLB_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Cashflows_MAVIS_BLB_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\DARLWP_BLB_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\DarlWP_Schiffe_BLB_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Kredite_BLB_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_BLB_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\FRISCO_BLB_TXS.xml

REM Gesamt-Dateien loeschen
del /q F:\TXS_009_PROD\IMPORT\Kredite_Gesamt_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_Gesamt_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\Kunde_Gesamt_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\DARLWP_Gesamt_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\DARLWP_Schiffe_Gesamt_TXS.xml
rem del /q F:\TXS_009_PROD\IMPORT\MAVIS_Gesamt_TXS.xml
del /q F:\TXS_009_PROD\IMPORT\Cashflows_Gesamt_TXS.xml

REM BLB-Dateien loeschen
rem del /q F:\TXS_004_PROD\IMPORT\Kunde_TXS.xml
rem del /q F:\TXS_004_PROD\IMPORT\MAVIS_TXS.xml
del /q F:\TXS_004_PROD\IMPORT\Cashflows_MAVIS_TXS.xml
rem del /q F:\TXS_004_PROD\IMPORT\DARLWP_TXS.xml
rem del /q F:\TXS_004_PROD\IMPORT\DarlWP_Schiffe_TXS.xml
del /q F:\TXS_004_PROD\IMPORT\Kredite_TXS.xml
del /q F:\TXS_004_PROD\IMPORT\Kredite_Schiffe_TXS.xml
del /q F:\TXS_004_PROD\IMPORT\FRISCO_TXS.xml

rem for later use
rem Sonderfall Sicherung vor UeMonitor
REM call F:\Tagessicherung\Tagessicherung_Sonder.cmd
rem Sonderfall Sicherung vor UeMonitor

rem ----------------------------------------------------------
REM Überwachungsmonitor für Gläubigerwechsel
call F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXSJobServer.exe -execute=monitor.MonitorJobGW
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSJobServer.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_UEMonitorGW_%Date-Suffix%.log
rem ----------------------------------------------------------

rem ----------------------------------------------------------
REM Überwachungsmonitor starten SU 20111221 - Überprüfung auf Fehler später checken w/Parallelläufe
call F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXSJobServer.exe -execute=monitor.MonitorJob
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSJobServer.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_UEMonitor_%Date-Suffix%.log
rem copy /Y F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSJobServer.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log 
rem call F:\PROST\CheckLog.bat
rem if %errorlevel%==1 goto Fehler
rem ----------------------------------------------------------

rem ----------------------------------------------------------
REM Überwachungsmonitor Gläubigerwechsel loeschen starten - mit 2 Tage in der Konfiguration - Laufzeit normalerweise 1 Minute
call F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXSJobServer.exe -execute=monitor.MonitorJobGW_CLEANUP
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSJobServer.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_UEMonitorLoeschGW_%Date-Suffix%.log
rem ----------------------------------------------------------

rem ----------------------------------------------------------
REM Überwachungsmonitor loeschen starten - mit 2 Tage in der Konfiguration - Laufzeit normalerweise 1 Minute
call F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXSJobServer.exe -execute=monitor.MonitorJob_CLEANUP
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSJobServer.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_UEMonitorLoesch_%Date-Suffix%.log
rem ----------------------------------------------------------


rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Ueberwachungsmonitor.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

