REM PRODKEV *****************************************************************************************************************************************************************
REM _Start_Reports_KEV_RUECK.cmd - Beginn
REM TXS Reporterstellung Rueckmeldung KEV SPA 20191203
REM     Generisches Reporting, um daraus die ehemelaige collcockpit-Datei zu generieren
REM     Der generische Report wird automatisch erzeugt, wenn der Import der Bestandsdaten gelaufen ist
REM     Danach Weiterverarbeitung im PowerShellScripten
REM SPA - 20191204
REM SPA - 20191210 ABACUS-Dateinamen
REM SPA - 18.06.2020 - Responsedatei wird auf TVL-Server in das Unterverzeichnis spot kopiert
REM SPA - 20200713 Einbau dp txt für Dateinamen und  Protokolldatei
REM *************************************************************************************************************************************************************************

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

set TXSProtokolldir=F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle
set NLBProtokolldir=F:\CAM_009_PROD\PROTOKOLL\LB

REM *************************************************************************************************************************************************************************
REM Bereinigung der Zwischendateien soll in den Scripten erfolgen
REM Die Reports werden hier geloescht
REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_REPORTS\KEVRUECK
del /q NLBCAMRueck*.xlsm
del /q NLBCAMRueck*.csv
REM *************************************************************************************************************************************************************************
REM LogFiles zippen & archivieren
REM *************************************************************************************************************************************************************************
cd F:\CAM_009_PROD\PROTOKOLL\LB
zip -m F:\CAM_009_PROD\PROTOKOLL\LB\RMInfosKEVUm_%Zip-Date-Suffix%.zip Infos_ReadKEVUmw_PS_*.log
zip -m F:\CAM_009_PROD\PROTOKOLL\LB\RMInfosKEV_%Zip-Date-Suffix%.zip Infos_ReadKEVRueck_PS_*.log
REM SPA - 20200713 Einbau dp txt für Dateinamen und  Protokolldatei
zip -m F:\CAM_009_PROD\PROTOKOLL\LB\RMInfosKEVParm_%Zip-Date-Suffix%.zip Infos_ReadKEVPARM_PS_*.log
del /q Meldung.txt
REM *************************************************************************************************************************************************************************
REM SPA - 20200713 Einbau dp txt für Dateinamen und  Protokolldatei
REM dp.txt in F:\CAM_009_PROD\STATUS\dptxt checken .. nicht da, dann Abbruch
REM *************************************************************************************************************************************************************************
if not exist F:\CAM_009_PROD\STATUS\dptxt\dp.txt goto dpfehlt
REM *************************************************************************************************************************************************************************
REM Report liegt im Verzeichnis  F:\CAM_009_PROD(PREP)\Daten\BuBa\Report vor Kopie nach F:\TXS_009_REPORTS\KEVRUECK
REM *************************************************************************************************************************************************************************
REM Positionierung auf F-Platte bleibt bestehen
F:
cd F:\CAM_009_PROD\Daten\BuBa\Report
copy F:\CAM_009_PROD\Daten\BuBa\Report\NLBCAM*.xlsm F:\TXS_009_REPORTS\KEVRUECK
del /q F:\CAM_009_PROD\Daten\BuBa\Report\NLBCAM*.xlsm
REM Positionierung auf F-Platte bleib bestehen
REM *************************************************************************************************************************************************************************
REM ToDo 1 .. Datumsqualifier weg
REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_REPORTS\KEVRUECK
ren NLBCAMRueck*.xlsm NLBCAMRueck.xlsm
REM *************************************************************************************************************************************************************************
REM ToDo 2 .. Umwandlung von xlsm -> csv
REM *************************************************************************************************************************************************************************
powershell F:\CAM_009_PROD\PROGRAMM\LB\XLSM2CSV_CAMRUECK.ps1 > %NLBProtokolldir%\Infos_ReadKEVUmw_PS_%mybasedate%.log
if %errorlevel%==1 goto fehler
REM *************************************************************************************************************************************************************************
REM ToDo 3 .. Aus dem generischen Report diverse Outputdatei erzeugen für alle Abnehmer ABACUS, CMS, OTC und KIT
REM *************************************************************************************************************************************************************************
powershell F:\CAM_009_PROD\PROGRAMM\LB\ReadCAMRueckAll.ps1 > %NLBProtokolldir%\Infos_ReadKEVRueck_PS_%mybasedate%.log
if %errorlevel%==1 goto fehler
REM *************************************************************************************************************************************************************************
REM ToDo 4 .. Dateien jeweils in die Transferverzeichnisse kopieren
REM *************************************************************************************************************************************************************************
REM copy F:\TXS_009_REPORTS\KEVRUECK\*TXS2SPOT.KEVBeleihung*.csv F:\TXS_009_Transfer\TXSKEV\export\ABACUS
REM del /q F:\TXS_009_REPORTS\KEVRUECK\*TXS2SPOT.KEVBeleihung*.csv
REM NEU
copy F:\TXS_009_REPORTS\KEVRUECK\TXS2SPOT_KEVBELEIHUNG*.csv F:\TXS_009_Transfer\TXSKEV\export\ABACUS
del /q F:\TXS_009_REPORTS\KEVRUECK\TXS2SPOT_KEVBELEIHUNG*.csv
REM Neu
copy F:\TXS_009_REPORTS\KEVRUECK\OTC_TXSKEV*.csv F:\TXS_009_Transfer\TXSKEV\export\OTC
del /q F:\TXS_009_REPORTS\KEVRUECK\OTC_TXSKEV*.csv
copy F:\TXS_009_REPORTS\KEVRUECK\ReportTXS_009_CMC_EB_KEV*.txt F:\TXS_009_Transfer\TXSKEV\export\CMC
del /q F:\TXS_009_REPORTS\KEVRUECK\ReportTXS_009_CMC_EB_KEV*.txt
copy F:\TXS_009_REPORTS\KEVRUECK\KIT_TXSKEV*.csv F:\TXS_009_Transfer\TXSKEV\export\KIT
del /q F:\TXS_009_REPORTS\KEVRUECK\KIT_TXSKEV*.csv
copy F:\TXS_009_REPORTS\KEVRUECK\NLBCAM*.xlsm F:\TXS_009_Transfer\TXSKEV\export\Base
del /q F:\TXS_009_REPORTS\KEVRUECK\NLBCAM*.xlsm
copy F:\TXS_009_REPORTS\KEVRUECK\NLBCAM*.csv F:\TXS_009_Transfer\TXSKEV\export\Base
del /q F:\TXS_009_REPORTS\KEVRUECK\NLBCAM*.csv
goto Ende
REM *************************************************************************************************************************************************************************
REM REM _Start_Reports_KEV_RUECK.cmd - Ende
REM *************************************************************************************************************************************************************************
REM SPA - 20200713 Einbau dp txt für Dateinamen und  Protokolldatei
REM Fehlerroutine dp.txtx fehlt
:dpfehlt
echo %date% %time% Keine dp.txt-Datei vorhanden fuer CAM-Rueckmeldung %mybasedate% >%NLBProtokolldir%\Infos_ReadKEVPARM_PS_%mybasedate%.log
echo 
goto fehler
REM Fehlerroutine bedeutet .. keine Datei versandt alles Infos zusammen
:fehler
cd F:\CAM_009_PROD\PROTOKOLL\LB
copy /Y Infos_ReadKEV*.log Meldung.txt
powershell F:\CAM_009_PROD\PROGRAMM\LB\kevrueck_versand.ps1 
REM exit 1
# Hier geht es immer raus 
:Ende


