REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM Aufruf der TXS-eigenen Bereinigungen via jobserver
REM SP/SU 20180621
REM Einstellungen dazu via TXS in (Menue-Verwaltung-Datenhaushalt-Datenbereinigungsjobs verwalten)
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set statusdir=F:\TXS_009_PROD\PROTOKOLL\Reports

REM Markdaten und Wechselkurse, jeweils 1 Jahr
call txsjobserver.exe -execute=reorganisation.Marktdaten -applog=%protokolldir%\applog_prod_Reorg_Markt.log -errlog=%protokolldir%\errlog_prod_Reorg_Markt.log

REM Barwerte, 1 Monat
call txsjobserver.exe -execute=reorganisation.Barwert -applog=%protokolldir%\applog_prod_Reorg_Barw.log -errlog=%protokolldir%\errlog_prod_Reorg_Barw.log

REM Logging, 1 Monat
call txsjobserver.exe -execute=reorganisation.Logging -applog=%protokolldir%\applog_prod_Reorg_Logg.log -errlog=%protokolldir%\errlog_prod_Reorg_Logg.log