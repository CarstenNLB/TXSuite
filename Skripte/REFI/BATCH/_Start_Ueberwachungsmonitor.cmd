REM 20180627 - SPA Ueberwachungsmonitor loeschen angehaengt
REM Ueberwachungsmonitor starten
REM spa 20180320 Rel 8.10.600 - Aufruf um MonitorJob erweitert
REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

call TXSJobServer.exe -execute=monitor.MonitorJob
copy F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\TXSJobServer.log F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\Archiv\TXSBatch_UEMonitor_%Date-Suffix%.log

call TXSJobServer.exe -execute=monitor.MonitorJob_CLEANUP
copy F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\TXSJobServer.log F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\Archiv\TXSBatch_UEMonitorLoesch_%Date-Suffix%.log


FOR /F "tokens=1-4 delims= " %%A in (F:\REF_009_PROD\STATUS\LB\Prost_Refi_Ueberwachungsmonitor.txt) do (
  java -jar F:\PROST_REFI\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST_REFI\prost.ini %%A %%B %%C %%D
)


