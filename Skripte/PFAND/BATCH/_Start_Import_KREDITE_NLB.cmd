REM CT 20140313
REM Kredite importieren

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

if not exist F:\TXS_009_PROD\IMPORT\Kredite_TXS.xml exit
REM if not exist F:\TXS_009_PROD\IMPORT\Kredite_Flugzeuge_TXS.xml exit
if not exist F:\TXS_009_PROD\IMPORT\Kredite_OEPG_TXS.xml exit
REM if not exist F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_TXS.xml exit

if not exist F:\TXS_009_PROD\IMPORT\Kredite_BLB_TXS.xml exit
REM if not exist F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_BLB_TXS.xml exit

rem FOR /F "usebackq" %%A IN ('F:\TXS_009_PROD\IMPORT\Kredite_TXS.xml') DO set size=%%~zA
rem if %size% LSS 4096 exit

rem FOR /F "usebackq" %%A IN ('F:\TXS_009_PROD\IMPORT\Kredite_BLB_TXS.xml') DO set size=%%~zA
rem if %size% LSS 4096 exit

REM XML-Dateien konkatenieren
F:
cd F:\TXS_009_PROD\PROGRAMM\LB

call XMLMerger_Kredite.bat
REM call XMLMerger_Kredite_Schiffe.bat

if not exist F:\TXS_009_PROD\IMPORT\Kredite_Gesamt_TXS.xml exit
REM if not exist F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_Gesamt_TXS.xml exit

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

rem ------------STARTE KREDITE PfandBG-------------
call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nodelcf -nos -aad=ADARLPFBG,ADAWPPFBG -basedate=%mybasedate% -import=F:\TXS_009_PROD\IMPORT\Kredite_Gesamt_TXS.xml
echo Status: %errorlevel% > F:\TXS_009_PROD\STATUS\LB\Status_KR_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF not %errorlevel%==0 echo Text: Kredite fehlerhaft !! >> F:\TXS_009_PROD\STATUS\LB\Status_KR_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF %errorlevel%==0 echo Text: Kredite beliefert !! >> F:\TXS_009_PROD\STATUS\LB\Status_KR_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Kredite_PfandBG_%Date-Suffix%.log

call F:\PROST\CheckLog.bat
if %errorlevel%==1 goto Fehler

rem ------------STARTE KREDITE OEPG----------------
call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nodelcf -nos -aad=ADARLOEPG,ADAWPOEPG -basedate=%mybasedate% -import=F:\TXS_009_PROD\IMPORT\Kredite_OEPG_TXS.xml
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Kredite_OEPG_%Date-Suffix%.log

call F:\PROST\CheckLog.bat
if %errorlevel%==1 goto Fehler

rem ------------STARTE KREDITE Schiffe-------------
REM call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nodelcf -nos -aad=ADARLSCHF,ADAWPSCHF -basedate=%mybasedate% -import=F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_Gesamt_TXS.xml
REM copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Kredite_Schiffe_%Date-Suffix%.log

REM call F:\PROST\CheckLog.bat
REM if %errorlevel%==1 goto Fehler

rem ------------STARTE KREDITE Flugzeuge-----------
REM call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nodelcf -nos -aad=ADARLFLUG,ADAWPFLUG -basedate=%mybasedate% -import=F:\TXS_009_PROD\IMPORT\Kredite_Flugzeuge_TXS.xml
REM copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Kredite_Flugzeuge_%Date-Suffix%.log

REM call F:\PROST\CheckLog.bat
REM if %errorlevel%==1 goto Fehler

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Import_Darlehen.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

:Fehler
