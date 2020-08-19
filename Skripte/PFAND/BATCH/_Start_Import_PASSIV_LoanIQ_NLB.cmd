REM Passiv LoanIQ importieren
REM CT 08.08.2017 erstellt

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

if not exist F:\TXS_009_PROD\IMPORT\Passiv_LoanIQ_TXS.xml exit

F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

rem ------------STARTE KREDITE LoanIQ-------------
call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nodelcf -nos -aau=PLIQPFBG,PLIQOEPG,PLIQFLUG,PLIQSCHF -basedate=%mybasedate% -import=F:\TXS_009_PROD\IMPORT\Passiv_LoanIQ_TXS.xml  
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Passiv_LoanIQ_%Date-Suffix%.log

call F:\PROST\CheckLog.bat
if %errorlevel%==1 goto Fehler

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Import_LoanIQ-Passiv.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

:Fehler
