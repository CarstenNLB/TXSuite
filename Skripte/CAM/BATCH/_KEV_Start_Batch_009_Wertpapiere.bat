REM CT - angelegt am 29.10.2019

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

F:
cd F:\CAM_009_PROD\PROGRAMM\LB

REM Wertpapiere starten
call WertpapiereKEV.bat

copy /Y F:\CAM_009_PROD\DATEN\LB\KEV_Wertpapiere_TXS.xml F:\CAM_009_PROD\Import\KEV_Wertpapiere_TXS.xml

REM Dateien archivieren
copy /y F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_GD_009.xml F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml
copy /y F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_WP_BESTAND_009.csv F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv
del /q F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_GD_009.xml
del /q F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_WP_BESTAND_009.csv
cd F:\CAM_009_PROD\DATEN\LB\Archiv
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_GD_009_%Date-Suffix%.zip SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_WP_BESTAND_009_%Date-Suffix%.zip SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv

FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_Wertpapiere.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

exit