REM SU 20130503
REM IMPORT ZAHLUNGSSTRÖME

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

rem ------------STARTE CASHFLOWS--------------------
call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nos -basedate=%mybasedate% -importcf=F:\TXS_009_PROD\IMPORT\FRISCO_Gesamt_TXS.xml
echo Status: %errorlevel% > F:\TXS_009_PROD\STATUS\LB\Status_CF_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF not %errorlevel%==0 echo Text: Cashflow fehlerhaft !! >> F:\TXS_009_PROD\STATUS\LB\Status_CF_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF %errorlevel%==0 echo Text: Cashflow beliefert !! >> F:\TXS_009_PROD\STATUS\LB\Status_CF_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_FRISCO_%Date-Suffix%.log
