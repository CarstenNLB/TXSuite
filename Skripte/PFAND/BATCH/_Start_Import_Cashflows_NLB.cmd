REM SU 20130503
REM IMPORT ZAHLUNGSSTRÖME

REM Ende wenn eine der Importdateien fehlt
if not exist F:\TXS_009_PROD\IMPORT\FRISCO_TXS.xml exit
if not exist F:\TXS_009_PROD\IMPORT\FRISCO_BLB_TXS.xml exit
if not exist F:\TXS_009_PROD\IMPORT\Cashflows_MIDAS_TXS.xml exit
if not exist F:\TXS_009_PROD\IMPORT\Cashflows_LoanIQ_TXS.xml exit
if not exist F:\TXS_009_PROD\IMPORT\Cashflows_MAVIS_TXS.xml exit

REM Ende wenn eine der Dateien zu klein (=defekt) ist

FOR /F "usebackq" %%A IN ('F:\TXS_009_PROD\IMPORT\FRISCO_BLB_TXS.xml') DO set size=%%~zA
if %size% LSS 4096 exit

FOR /F "usebackq" %%A IN ('F:\TXS_009_PROD\IMPORT\FRISCO_TXS.xml') DO set size=%%~zA
if %size% LSS 4096 exit

FOR /F "usebackq" %%A IN ('F:\TXS_009_PROD\IMPORT\Cashflows_MIDAS_TXS.xml') DO set size=%%~zA
if %size% LSS 4096 exit

FOR /F "usebackq" %%A IN ('F:\TXS_009_PROD\IMPORT\Cashflows_LoanIQ_TXS.xml') DO set size=%%~zA
if %size% LSS 4096 exit

FOR /F "usebackq" %%A IN ('F:\TXS_009_PROD\IMPORT\Cashflows_MAVIS_TXS.xml') DO set size=%%~zA
if %size% LSS 4096 exit

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

REM XML-Dateien konkatenieren
F:
cd F:\TXS_009_PROD\PROGRAMM\LB

call XMLMerger_Cashflows.bat

if not exist F:\TXS_009_PROD\IMPORT\Cashflows_Gesamt_TXS.xml exit

F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

rem ------------STARTE CASHFLOWS--------------------
call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nos -basedate=%mybasedate% -importcf=F:\TXS_009_PROD\IMPORT\Cashflows_Gesamt_TXS.xml
echo Status: %errorlevel% > F:\TXS_009_PROD\STATUS\LB\Status_CF_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF not %errorlevel%==0 echo Text: Cashflow fehlerhaft !! >> F:\TXS_009_PROD\STATUS\LB\Status_CF_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF %errorlevel%==0 echo Text: Cashflow beliefert !! >> F:\TXS_009_PROD\STATUS\LB\Status_CF_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Cashflows_%Date-Suffix%.log

call F:\PROST\CheckLog.bat
if %errorlevel%==1 goto Fehler

rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Import_Cashflows.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

:Fehler