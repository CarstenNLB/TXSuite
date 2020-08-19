REM CT angelegt 04.11.2019
REM SPA 20191007 Check . KEV-Import nicht existent.. Standardaufruf
REM SPA 20191208 Konfirm fuer den ersten tag auskommentieren .. checken .. spaeter wieder rein
REM              Archvierung Script _Start_import_Archivieren existiert nicht
REM SPA 20200109 Einbau der Infomeldung, wenn Fehler seitens der BuBa bei der Einreichung gemeldet werden ..
REM              

FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_KEV_EXPORT_error.log) do (SET fehler=%%i)
if %fehler%==1 goto Ende

REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

REM SPA 20200109 Loeschen der Meldungsdatei
F:
cd F:\CAM_009_PROD\PROTOKOLL\LB
del /q Meldung_BuBa_Fehler.txt

:Quittung
F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD
call _Start_EXTRANET_Test_KEVEA.cmd

cd F:\CAM_009_PROD\PROGRAMM\LB
call KEVCheckQuittung.bat F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log
if %errorlevel%==0 goto Start

timeout /T 900

goto Quittung

:Start

F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Download_KEVEA.cmd

cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Import_Downloads.cmd

REM SPA 20200109 Wenn Fehler sonst Weiter
if %fehler%==0 goto Weiter
copy /Y F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\KEV_Export*.xml F:\CAM_009_PROD\PROTOKOLL\LB\Meldung_BuBa_Fehler.txt
REM Aufruf CheckLog

REM Im Fehlerfall Confirm etc unterlassen und springen ... goto Ende

REM SPA 20200109 Hier geht es immer weiter
:Weiter

cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Automatiken.cmd

REM Confirm
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\KEV_Skripte
call _Start_KEV_Confirm_KEVEA.cmd

REM Importverzeichnis aufräumen - Archivieren der Importdateien
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA
replace /r/a F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\KEV_Export*.xml F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\Archiv
del /q F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\KEV_Export*.xml
F:
cd F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\Archiv
zip -m F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\Archiv\BuBaQuitt_%Zip-Date-Suffix%.zip KEV_Export*.xml

:Ende

REM ohne PROST