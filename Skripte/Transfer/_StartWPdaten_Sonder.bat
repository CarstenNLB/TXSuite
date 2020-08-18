REM SPA 22.05.2020 WP - daten vom TVL-Server holen - Sonderscript - Lumpensammler
REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%*

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

REM Bestände/CashFlows
xcopy /y W:\SPOT2TXS_WP_CASHFLOW_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\SPOTWP\IMPORT\Archiv
xcopy /y W:\SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\SPOTWP\IMPORT\Archiv
REM Gattunsdaten
xcopy /y W:\SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml F:\TXS_009_TRANSFER\GATTUNGSDATEN\IMPORT\Archiv

REM Dateien im TVL-Verzeichnis loeschen
del /q W:\SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv 
del /q W:\SPOT2TXS_WP_CASHFLOW_009_%Rest-Date-Suffix%.csv
del /q W:\SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml

net use W: /DELETE /y

F:
cd F:\TXS_009_TRANSFER\SPOTWP\IMPORT\Archiv
zip -m F:\TXS_009_TRANSFER\SPOTWP\IMPORT\Archiv\SPOTWP_Aktiv_%Date-Suffix%.zip SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_TRANSFER\SPOTWP\IMPORT\Archiv\SPOTWP_CASHFLOW_%Date-Suffix%.zip SPOT2TXS_WP_CASHFLOW_009_%Rest-Date-Suffix%.csv
cd F:\TXS_009_TRANSFER\GATTUNGSDATEN\IMPORT\Archiv
zip -m F:\TXS_009_TRANSFER\GATTUNGSDATEN\IMPORT\Archiv\GattungsdatenResponse_%Date-Suffix%.zip SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml
exit

