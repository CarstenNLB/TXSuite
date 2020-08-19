REM _Start_Transfer_KEVPDF2OEQM.cmd
REM 20191203 spa
REM SPA 20200731 - MACCs-Vorbereitung
REM SPA 20200801 - MACCs-Umstellung
REM Beim Export aus CAM_KEV fuer den BuBa Upload wird eine Datei 
REM     KEV-Einreichung_JJJJ-MM-TT-HHMMSS.PDF erzeugt
REM Diese Datei wird aus F:\CAM_009_PROD(PREP)\Daten\BuBa\Report
REM     nach F:\TXS_009_Transfer\TXSKEV\export\QM verschoben
REM Danach versandt an Fachbereich via PowerShell und anschliessend gezippt und archiviert

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

set TXSProtokolldir=F:\CAM_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set NLBProtokolldir=F:\CAM_009_PROD\PROTOKOLL\LB

REM Zip-Date-Suffix
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%
REM *************************************************************************************************************************************************************************
REM Bereinigung der Zwischendateien soll in den Scripten erfolgen
REM Die Reports werden hier geloescht
REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_Transfer\TXSKEV\export\QM
del /q KEV-Einreichung*.pdf
del /q MACCs-Einreichung*.pdf
REM *************************************************************************************************************************************************************************
REM LogFiles zippen & archivieren
REM *************************************************************************************************************************************************************************
F:
cd F:\CAM_009_PROD\PROTOKOLL\LB
zip -m F:\CAM_009_PROD\PROTOKOLL\LB\RMInfos_TRKEVEIN2QM_%Zip-Date-Suffix%.zip Infos_TRKEVEIN2QM_PS_*.log
del /q Meldung.txt
REM Positionierung auf F-Platte bleibt bestehen
F:
cd F:\CAM_009_PROD\Daten\BuBa\Report
copy F:\CAM_009_PROD\Daten\BuBa\Report\KEV-Einreichung*.pdf   F:\TXS_009_Transfer\TXSKEV\export\QM
copy F:\CAM_009_PROD\Daten\BuBa\Report\MACCs-Einreichung*.pdf F:\TXS_009_Transfer\TXSKEV\export\QM
del /q F:\CAM_009_PROD\Daten\BuBa\Report\KEV-Einreichung*.pdf
del /q F:\CAM_009_PROD\Daten\BuBa\Report\MACCs-Einreichung*.pdf
REM Positionierung auf F-Platte bleib bestehen
F:
cd F:\TXS_009_Transfer\TXSKEV\export\QM
REM *************************************************************************************************************************************************************************
REM Transfer der Datei an OE QM
REM *************************************************************************************************************************************************************************
powershell F:\CAM_009_PROD\PROGRAMM\LB\TransferKEVEin2QM.ps1 > %NLBProtokolldir%\Infos_TRKEVEIN2QM_PS_%mybasedate%.log
if %errorlevel%==1 goto fehler
REM Hier hat der Versand funktioniert .. also ins Archiv zippen
replace /r/a  F:\TXS_009_Transfer\TXSKEV\export\QM\KEV-Einreichung*.pdf   F:\TXS_009_Transfer\TXSKEV\export\QM\Archiv
replace /r/a  F:\TXS_009_Transfer\TXSKEV\export\QM\MACCs-Einreichung*.pdf F:\TXS_009_Transfer\TXSKEV\export\QM\Archiv
del /q  F:\TXS_009_Transfer\TXSKEV\export\QM\KEV-Einreichung*.pdf
del /q  F:\TXS_009_Transfer\TXSKEV\export\QM\MACCs-Einreichung*.pdf
F:
cd F:\TXS_009_Transfer\TXSKEV\export\QM\Archiv
zip -m F:\TXS_009_Transfer\TXSKEV\export\QM\Archiv\KEV2QMEIN_%Zip-Date-Suffix%.zip KEV-Einreichung*.pdf
zip -m F:\TXS_009_Transfer\TXSKEV\export\QM\Archiv\MAC2QMEIN_%Zip-Date-Suffix%.zip MACCs-Einreichung*.pdf
REM Nicht ueber den Fehler laufen 
goto Ende
:fehler
REM Wenn kein Versand möglich
F:
cd F:\CAM_009_PROD\PROTOKOLL\LB
copy /Y Infos_TRKEVEIN2QM_PS*.log Meldung.txt
powershell F:\CAM_009_PROD\PROGRAMM\LB\keveinr_versand.ps1 
REM exit 1
# Hier geht es immer raus 
:Ende
