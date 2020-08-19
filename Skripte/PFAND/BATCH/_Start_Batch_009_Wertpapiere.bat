REM CT - angelegt am 24.05.2018 
REM SPA - 27.06.2019 - Aufruf F:\TXS_009_PROD\PROGRAMM\LB\NLB_MAVISRefzins.cmd
REM SPA - 02.07.2019 - F:\TXS_009_PROD\PROGRAMM\LB\NLB_MAVISRefzins.cmd auskommentiert .. neues JAR-File

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

net use T: /DELETE /y
net use T: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo Wertpapier-Daten von T: holen
xcopy /y T:\SPOT2TXS_WP_CASHFLOW_009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LB\SPOT2TXS_WP_CASHFLOW_009_Original.csv
xcopy /y T:\SPOT2TXS_WP_CASHFLOW_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\SPOTWP\IMPORT\Archiv
xcopy /y T:\SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml F:\TXS_009_PROD\DATEN\LB\SPOT2TXS_GD_009.xml
xcopy /y T:\SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml F:\TXS_009_TRANSFER\GATTUNGSDATEN\IMPORT\Archiv

rem Gattungsdaten fuer KEV kopieren
copy /y F:\TXS_009_PROD\DATEN\LB\SPOT2TXS_GD_009.xml F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_GD_009.xml

del /q T:\SPOT2TXS_WP_CASHFLOW_009_%Rest-Date-Suffix%.csv
del /q T:\SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml

net use T: /DELETE /y

F:
cd F:\TXS_009_PROD\PROGRAMM\LB

REM CT 24.05.2018 - Noch nicht aktiviert
REM Wertpapiere starten
call Wertpapiere.bat

REM Zuerst Wertpapiere-Cashflows sortieren - CT 07.08.2018
call FileSort_WP_Cashflow.bat

REM Wertpapiere-Cashflows starten
call CashflowsWertpapiere.bat

REM SPA - 02.07.2019 - Aufruf F:\TXS_009_PROD\PROGRAMM\LB\NLB_MAVISRefzins.cmd auskommentiert 
REM SPA - 27.06.2019 - Aufruf F:\TXS_009_PROD\PROGRAMM\LB\NLB_MAVISRefzins.cmd
REM Start
REM echo Verarbeitung Start NLBMAVIS_Refzins >> F:\TXS_009_PROD\PROTOKOLL\LB\WPMAVISVerarbeitung.txt
REM echo %date% >> F:\TXS_009_PROD\PROTOKOLL\LB\WPMAVISVerarbeitung.txt
REM echo %time% >> F:\TXS_009_PROD\PROTOKOLL\LB\WPMAVISVerarbeitung.txt
REM call NLB_MAVISRefzins.cmd
REM echo Verarbeitung Ende NLBMAVIS_Refzins >> F:\TXS_009_PROD\PROTOKOLL\LB\WPMAVISVerarbeitung.txt
REM echo %date% >> F:\TXS_009_PROD\PROTOKOLL\LB\WPMAVISVerarbeitung.txt
REM echo %time% >> F:\TXS_009_PROD\PROTOKOLL\LB\WPMAVISVerarbeitung.txt
REM Ende

copy /Y F:\TXS_009_PROD\DATEN\LB\MAVIS_TXS.xml F:\TXS_009_PROD\Import\MAVIS_TXS.xml
copy /Y F:\TXS_009_PROD\DATEN\LB\Cashflows_MAVIS_TXS.xml F:\TXS_009_PROD\Import\Cashflows_MAVIS_TXS.xml

cd F:\TXS_009_TRANSFER\SPOTWP\IMPORT\Archiv
zip -m F:\TXS_009_TRANSFER\SPOTWP\IMPORT\Archiv\SPOTWP_Aktiv_%Date-Suffix%.zip SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_TRANSFER\SPOTWP\IMPORT\Archiv\SPOTWP_CASHFLOW_%Date-Suffix%.zip SPOT2TXS_WP_CASHFLOW_009_%Rest-Date-Suffix%.csv

cd F:\TXS_009_TRANSFER\GATTUNGSDATEN\IMPORT\Archiv
zip -m F:\TXS_009_TRANSFER\GATTUNGSDATEN\IMPORT\Archiv\GattungsdatenResponse_%Date-Suffix%.zip SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Wertpapiere.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit