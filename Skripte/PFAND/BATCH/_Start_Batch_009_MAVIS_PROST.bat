REM CT - angelegt am 15.04.2013
REM DW/SU - Ergänzungen wg. Korrektur von Sonderzeichen
REM SP - 15.09.2015 - xcopy ins Unterverzeichnis FMO
REM SU - 07.01.2016 - xcopy nach FMO auskommentiert, um Laufzeit in der Verarbeitung zu gewinnen.

REM Date-Suffix nur YYYYMM
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Host-Date-Suffix=%mybasedate:~2,2%%mybasedate:~5,2%%mybasedate:~8,2%

REM PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_MAVIS.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit