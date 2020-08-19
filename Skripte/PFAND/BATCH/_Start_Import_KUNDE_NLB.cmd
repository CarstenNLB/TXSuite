REM SU 20130503
REM Import Kundendaten

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

if not exist F:\TXS_009_PROD\IMPORT\Kunde_TXS.xml exit
if not exist F:\TXS_009_PROD\IMPORT\Kunde_BLB_TXS.xml exit

REM XML-Dateien konkatenieren
F:
cd F:\TXS_009_PROD\PROGRAMM\LB

call XMLMerger_Kunde.bat

if not exist F:\TXS_009_PROD\IMPORT\Kunde_Gesamt_TXS.xml exit

F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

rem ------------STARTE GESCHAEFTSPARTNER--------------------
call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nodelcf -nos -basedate=%mybasedate% -import=F:\TXS_009_PROD\IMPORT\Kunde_Gesamt_TXS.xml
echo Status: %errorlevel% > F:\TXS_009_PROD\STATUS\LB\Status_KD_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF not %errorlevel%==0 echo Text: Kunde fehlerhaft !! >> F:\TXS_009_PROD\STATUS\LB\Status_KD_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF %errorlevel%==0 echo Text: Kunde beliefert !! >> F:\TXS_009_PROD\STATUS\LB\Status_KD_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Kunde_%Date-Suffix%.log

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Import_Kunde.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)



