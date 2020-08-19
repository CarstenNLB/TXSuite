REM SPA 22.05.2020 MIDAS - daten vom TVL-Server holen - Sonderscript - Lumpensammler
REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%*

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

REM Aktiv / CashFlow
xcopy /y W:\SPOT2TXS_M_A_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\MIDAS\IMPORT\Archiv
xcopy /y W:\SPOT2TXS_M_C_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\MIDAS\IMPORT\Archiv

REM Dateien im TVL-Verzeichnis loeschen
del /q W:\SPOT2TXS_M_A_009_%Rest-Date-Suffix%.csv
del /q W:\SPOT2TXS_M_C_009_%Rest-Date-Suffix%.csv

net use W: /DELETE /y

F:
cd F:\TXS_009_TRANSFER\MIDAS\IMPORT\Archiv
zip -m F:\TXS_009_TRANSFER\MIDAS\IMPORT\Archiv\MIDAS_Kredite_%Date-Suffix%.zip SPOT2TXS_M_A_009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_TRANSFER\MIDAS\IMPORT\Archiv\MIDAS_Cashflow_%Date-Suffix%.zip SPOT2TXS_M_C_009_%Rest-Date-Suffix%.csv
exit

