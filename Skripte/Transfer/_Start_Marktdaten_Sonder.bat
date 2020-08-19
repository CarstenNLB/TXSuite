REM SPA 22.05.2020 Markt - daten vom TVL-Server holen - Sonderscript - Lumpensammler
REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%*

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

REM Waehrungskurse und Zinskurven
xcopy /y W:\mds_edrexport_fx_i009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\MDV\IMPORT\
xcopy /y W:\mds_edrexport_rate_i009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\MDV\IMPORT\

REM Dateien im TVL-Verzeichnis loeschen
del /q W:\mds_edrexport_fx_i009_%Rest-Date-Suffix%.csv
del /q W:\mds_edrexport_rate_i009_%Rest-Date-Suffix%.csv

net use W: /DELETE /y

F:
cd F:\TXS_009_TRANSFER\MDV\IMPORT
zip -m F:\TXS_009_TRANSFER\MDV\IMPORT\Archiv\MDV_%Date-Suffix%.zip mds_edrexport_fx_i009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_TRANSFER\MDV\IMPORT\Archiv\MDV_%Date-Suffix%.zip mds_edrexport_rate_i009_%Rest-Date-Suffix%.csv
exit

