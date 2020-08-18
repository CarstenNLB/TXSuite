REM CT - angelegt am 15.10.2019

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

F:
REM DarKaKEV starten
cd F:\CAM_009_PROD\PROGRAMM\LB
call DarKaKEV.bat

REM Importdatei kopieren
copy F:\CAM_009_PROD\DATEN\LB\KEV_DarKa_TXS.xml F:\CAM_009_PROD\IMPORT\KEV_DarKa_TXS.xml

REM Datei archivieren
copy /y F:\CAM_009_PROD\DATEN\LB\DarlehenKEV.txt F:\CAM_009_PROD\DATEN\LB\Archiv\DarlehenKEV_%Rest-Date-Suffix%.txt
del /q F:\CAM_009_PROD\DATEN\LB\DarlehenKEV.txt
cd F:\CAM_009_PROD\DATEN\LB\Archiv
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\DarlehenKEV_%Date-Suffix%.zip DarlehenKEV_%Rest-Date-Suffix%.txt

FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_DARLEHEN.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

exit