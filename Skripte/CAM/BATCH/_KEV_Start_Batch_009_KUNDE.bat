REM CT - angelegt am 15.10.2019

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

F:
cd F:\CAM_009_PROD\PROGRAMM\LB

REM Kunde starten
call KundeKEV.bat

copy /Y F:\CAM_009_PROD\DATEN\LB\KEV_Kunde_TXS.xml F:\CAM_009_PROD\Import\KEV_Kunde_TXS.xml

REM Datei archivieren
copy /y F:\CAM_009_PROD\DATEN\LB\Kunde_KEV.xml F:\CAM_009_PROD\DATEN\LB\Archiv\Kunde_KEV_%Rest-Date-Suffix%.xml
del /q F:\CAM_009_PROD\DATEN\LB\Kunde_KEV.xml
cd F:\CAM_009_PROD\DATEN\LB\Archiv
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\Kunde_KEV_%Date-Suffix%.zip Kunde_KEV_%Rest-Date-Suffix%.xml

FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_KUNDE.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

exit