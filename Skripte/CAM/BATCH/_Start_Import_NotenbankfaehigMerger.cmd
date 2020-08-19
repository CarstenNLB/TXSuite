REM SPA 20200805 - Schuldnerdateien mergen
REM SPA 20200818 - Zippen der Protokolldatei
REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%
REM Protokoll
set NLBProtokolldir=F:\CAM_009_PROD\PROTOKOLL\LB
REM *************************************************************************************************************************************************************************
REM ToDo 0 .. Loeschen und alte Dateien zippen
REM *************************************************************************************************************************************************************************
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN
del /q NotenbankTXS.xlsm
del /q NotenbankDatei*.xml
REM *************************************************************************************************************************************************************************
F:
cd F:\CAM_009_PROD\PROTOKOLL\LB
zip -m F:\CAM_009_PROD\PROTOKOLL\LB\RMInfos_NBS_Konkat_%Zip-Date-Suffix%.zip Infos_NBS_Konkat_*.log
REM Positionierung auf F-Platte bleibt bestehen
REM *************************************************************************************************************************************************************************
REM ToDo 1 .. Datumsqualifier weg und umbenennen
REM *************************************************************************************************************************************************************************
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEN
ren Notenbankfaehige_ICAS_Schuldner*.xml NotenbankDatei1.xml
ren Oeffentliche_Schuldner*.xml NotenbankDatei2.xml
REM *************************************************************************************************************************************************************************
REM ToDo 2 .. Konkatenieren
REM *************************************************************************************************************************************************************************
F:
cd F:\CAM_009_PROD\PROGRAMM\LB
KEVXMLMerger.bat > %NLBProtokolldir%\Infos_NBS_Konkat_%mybasedate%.log
REM Ende Gelaende
