REM _Start_Transfer_KEVBUBABSTD2OEQM.cmd
REM 20191203 spa
REM Beim Import des BuBa-Bestandes aus CAM_KEV wird eine Datei 
REM     NordLB KEV BuBa Bestand TT.MM.JJJJ.PDF erzeugt
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
del /q NordLB*.pdf
REM *************************************************************************************************************************************************************************
REM LogFiles zippen & archivieren
REM *************************************************************************************************************************************************************************
F:
cd F:\CAM_009_PROD\PROTOKOLL\LB
zip -m F:\CAM_009_PROD\PROTOKOLL\LB\RMInfos_TRBUBABSTD2QM_%Zip-Date-Suffix%.zip Infos_TRBUBABSTD2QM_PS_*.log
del /q Meldung.txt
REM Positionierung auf F-Platte bleibt bestehen
F:
cd F:\CAM_009_PROD\Daten\BuBa\Report
copy F:\CAM_009_PROD\Daten\BuBa\Report\NordLB*.pdf F:\TXS_009_Transfer\TXSKEV\export\QM
del /q F:\CAM_009_PROD\Daten\BuBa\Report\NordLB*.pdf
REM Positionierung auf F-Platte bleib bestehen
F:
cd F:\TXS_009_Transfer\TXSKEV\export\QM
REM *************************************************************************************************************************************************************************
REM Transfer der Datei an OE QM
REM *************************************************************************************************************************************************************************
powershell F:\CAM_009_PROD\PROGRAMM\LB\TransferBUBABSTD2QM.ps1 > %NLBProtokolldir%\Infos_TRBUBABSTD2QM_PS_%mybasedate%.log
if %errorlevel%==1 goto fehler
REM Hier hat der Versand funktioniert .. also ins Archiv zippen
replace /r/a  F:\TXS_009_Transfer\TXSKEV\export\QM\NordLB*.pdf F:\TXS_009_Transfer\TXSKEV\export\QM\Archiv
del /q  F:\TXS_009_Transfer\TXSKEV\export\QM\NordLB*.pdf
F:
cd F:\TXS_009_Transfer\TXSKEV\export\QM\Archiv
zip -m F:\TXS_009_Transfer\TXSKEV\export\QM\Archiv\KEV2QMBSTD_%Zip-Date-Suffix%.zip NordLB*.pdf
REM Nicht ueber den Fehler laufen 
goto Ende
:fehler
REM Wenn kein Versand möglich
F:
cd F:\CAM_009_PROD\PROTOKOLL\LB
copy /Y Infos_TRBUBABSTD2QM_PS*.log Meldung.txt
powershell F:\CAM_009_PROD\PROGRAMM\LB\kevbstd_versand.ps1 
REM exit 1
# Hier geht es immer raus 
:Ende
