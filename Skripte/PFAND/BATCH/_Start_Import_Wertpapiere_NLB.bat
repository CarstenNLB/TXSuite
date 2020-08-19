REM Erstellt CT 28.05.2018
REM Wertpapiere importieren

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

if not exist F:\TXS_009_PROD\IMPORT\MAVIS_TXS.xml exit

rem FOR /F "usebackq" %%A IN ('F:\TXS_009_PROD\IMPORT\MAVIS_TXS.xml') DO set size=%%~zA
rem if %size% LSS 4096 exit

F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

rem ------------STARTE WP-MAVIS--------------------
call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nodelcf -nos -aau=PMAVIPFBG,PMAVIFLUG,PMAVISCHF,PMAVIOEPG -aad=AMAVIPFBG,AMAVIFLUG,AMAVISCHF,AMAVIOEPG -basedate=%mybasedate% -import=F:\TXS_009_PROD\IMPORT\MAVIS_TXS.xml
echo Status: %errorlevel% > F:\TXS_009_PROD\STATUS\LB\Status_MavisWP_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF not %errorlevel%==0 echo Text: MavisWP fehlerhaft !! >> F:\TXS_009_PROD\STATUS\LB\Status_MavisWP_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF %errorlevel%==0 echo Text: MavisWP beliefert !! >> F:\TXS_009_PROD\STATUS\LB\Status_MavisWP_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_MAVIS_%Date-Suffix%.log

call F:\PROST\CheckLog.bat
if %errorlevel%==1 goto Fehler

REM Verzeichnis anlegen und kopieren
mkdir F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

copy F:\TXS_009_PROD\IMPORT\MAVIS_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

del /q F:\TXS_009_PROD\IMPORT\MAVIS_TXS.xml

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Import_Wertpapiere.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

:Fehler
