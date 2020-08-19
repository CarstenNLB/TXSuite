REM *************************************************************************************************************************************************************************
REM _Start_Reports_CMC.cmd
REM Part 9: Transfer der Daten zum FB CMC - _Start_Reports_CMC.cmd
REM Kopieren auf das Fachbereichslaufwerk CMC
REM Vorlauefer
REM Part 8: TXS Reporterstellung BARW für CMC - _Start_tsy_bw_reports.bat
REM Nachfolger
REM PFANDBRIEF-Verarbeitung fertig
REM SPA 20190704 Redesign 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Parameter setzen
REM *************************************************************************************************************************************************************************

FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle

F:
REM in das Reportverzeichnis
cd F:\TXS_009_REPORTS\TXS

REM *************************************************************************************************************************************************************************
REM CMC verschicken und archivieren 
net use H: /delete /y

net use H: \\kbk.nordlb.local\funktion <###pwd###> /USER:KBK\TXSTRV01

copy ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip H:\Treasury\Treasury_Coll_Mgt_Report

net use H: /delete /y
REM ********* VORSICHT *********
REM *************************************************************************************************************************************************************************
REM ins archiv
copy ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip archiv

REM Arbeitstand löschen
del /q ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip
REM *************************************************************************************************************************************************************************

REM Überbleibsel löschen
del /q *.xl*
del /q *.pdf
del /q *.csv
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Versende_Reports_Treasury.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)
REM *************************************************************************************************************************************************************************

