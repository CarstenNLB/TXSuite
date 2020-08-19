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
REM *************************************************************************************************************************************************************************
REM ToDo 5 .. Transfer der Dateien an die Abnehmer 
REM *************************************************************************************************************************************************************************
REM Teil 1 ABACUS -> SPOT .. normaler TVL-Sendordner
REM SPA SPOT 20200718 Unterverzeichnis spot ----------------
F:
cd F:\TXS_009_Transfer\TXSKEV\export\ABACUS
net use Y: /DELETE /y
net use Y: \\nlbprod11-vf\txstvl$\send\spot <###pwd###> /USER:KBK\TXSTRV01
REM copy F:\TXS_009_Transfer\TXSKEV\export\ABACUS\*TXS2SPOT.KEVBeleihung_009*.csv Y:
REM NEU
copy F:\TXS_009_Transfer\TXSKEV\export\ABACUS\TXS2SPOT_KEVBELEIHUNG_009*.csv Y:
REM NEU
net use Y: /DELETE /y
REM *************************************************************************************************************************************************************************
REM Teil 2 OTC 
F:
cd F:\TXS_009_Transfer\TXSKEV\export\OTC
net use Y: /DELETE /y
net use Y: \\Denlb9010dc006p.kbk.nordlb.local\Funktion\VB28 <###pwd###> /user:kbk\txstrv01 
copy F:\TXS_009_Transfer\TXSKEV\export\OTC\OTC_TXSKEV*.csv Y:\VB28_104545_LIQ_Ablaufbilanzen\KEV
net use Y: /DELETE /y
REM *************************************************************************************************************************************************************************
REM Teil 3 CMC .. normaler TVL-Sendordner
F:
cd F:\TXS_009_Transfer\TXSKEV\export\CMC
net use Y: /DELETE /y
net use Y: \\nlbprod11-vf\txstvl$\send <###pwd###> /USER:KBK\TXSTRV01
copy F:\TXS_009_Transfer\TXSKEV\export\CMC\ReportTXS_009_CMC_EB_KEV*.txt Y:
net use Y: /DELETE /y
REM *************************************************************************************************************************************************************************
REM ToDo 6 .. Archivieren 
REM *************************************************************************************************************************************************************************
REM Teil 1 ABACUS 
F:
cd F:\TXS_009_Transfer\TXSKEV\export\ABACUS
replace /r/a  F:\TXS_009_Transfer\TXSKEV\export\ABACUS\TXS2SPOT_KEVBELEIHUNG_004*.csv F:\TXS_009_Transfer\TXSKEV\export\ABACUS\Archiv
replace /r/a  F:\TXS_009_Transfer\TXSKEV\export\ABACUS\TXS2SPOT_KEVBELEIHUNG_009*.csv F:\TXS_009_Transfer\TXSKEV\export\ABACUS\Archiv
del /q  F:\TXS_009_Transfer\TXSKEV\export\ABACUS\TXS2SPOT_KEVBELEIHUNG*.csv
F:
cd F:\TXS_009_Transfer\TXSKEV\export\ABACUS\Archiv
zip -m F:\TXS_009_Transfer\TXSKEV\export\ABACUS\Archiv\KEV2ABACUS009_%Zip-Date-Suffix%.zip TXS2SPOT_KEVBELEIHUNG_009*.csv
zip -m F:\TXS_009_Transfer\TXSKEV\export\ABACUS\Archiv\KEV2ABACUS004_%Zip-Date-Suffix%.zip TXS2SPOT_KEVBELEIHUNG_004*.csv
REM *************************************************************************************************************************************************************************
REM Teil 2 OTC 
F:
cd F:\TXS_009_Transfer\TXSKEV\export\OTC
replace /r/a F:\TXS_009_Transfer\TXSKEV\export\OTC\OTC_TXSKEV*.csv F:\TXS_009_Transfer\TXSKEV\export\OTC\Archiv
del /q F:\TXS_009_Transfer\TXSKEV\export\OTC\OTC_TXSKEV*.csv
F:
cd F:\TXS_009_Transfer\TXSKEV\export\OTC\Archiv
zip -m F:\TXS_009_Transfer\TXSKEV\export\OTC\Archiv\KEV2OTC_%Zip-Date-Suffix%.zip OTC_TXSKEV*.csv
REM *************************************************************************************************************************************************************************
REM Teil 3 CMC 
F:
cd F:\TXS_009_Transfer\TXSKEV\export\CMC
replace /r/a F:\TXS_009_Transfer\TXSKEV\export\CMC\ReportTXS_009_CMC_EB_KEV*.txt F:\TXS_009_Transfer\TXSKEV\export\CMC\Archiv
del /q F:\TXS_009_Transfer\TXSKEV\export\CMC\ReportTXS_009_CMC_EB_KEV*.txt
F:
cd F:\TXS_009_Transfer\TXSKEV\export\CMC\Archiv
zip -m F:\TXS_009_Transfer\TXSKEV\export\CMC\Archiv\KEV2CMC_%Zip-Date-Suffix%.zip ReportTXS_009_CMC_EB_KEV*.txt
REM *************************************************************************************************************************************************************************
REM Teil 4 KIT - 1
F:
cd F:\TXS_009_Transfer\TXSKEV\export\KIT
replace /r/a F:\TXS_009_Transfer\TXSKEV\export\KIT\KIT_TXSKEV*.csv F:\TXS_009_Transfer\TXSKEV\export\KIT\Archiv
del /q F:\TXS_009_Transfer\TXSKEV\export\KIT\KIT_TXSKEV*.csv
F:
cd F:\TXS_009_Transfer\TXSKEV\export\KIT\Archiv
zip -m F:\TXS_009_Transfer\TXSKEV\export\KIT\Archiv\KEV2KIT_%Zip-Date-Suffix%.zip KIT_TXSKEV*.csv
REM *************************************************************************************************************************************************************************
REM Teil 4 KIT - 2
F:
cd F:\TXS_009_Transfer\TXSKEV\export\Base
replace /r/a F:\TXS_009_Transfer\TXSKEV\export\Base\NLBCAM*.xlsm F:\TXS_009_Transfer\TXSKEV\export\Base\Archiv
replace /r/a F:\TXS_009_Transfer\TXSKEV\export\Base\NLBCAM*.csv F:\TXS_009_Transfer\TXSKEV\export\Base\Archiv
del /q F:\TXS_009_Transfer\TXSKEV\export\Base\NLBCAM*.xlsm
del /q F:\TXS_009_Transfer\TXSKEV\export\Base\NLBCAM*.csv
cd F:\TXS_009_Transfer\TXSKEV\export\Base\Archiv
ren NLBCAMRueck.xlsm NLBCAMRueck_%mybasedate%.xlsm
ren NLBCAMRueck.csv NLBCAMRueck_%mybasedate%.csv
zip -m F:\TXS_009_Transfer\TXSKEV\export\Base\Archiv\KEV2BAS1_%Zip-Date-Suffix%.zip NLBCAM*.xlsm
zip -m F:\TXS_009_Transfer\TXSKEV\export\Base\Archiv\KEV2BAS2_%Zip-Date-Suffix%.zip NLBCAM*.csv
REM Nicht ueber den fehler laufen 
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


