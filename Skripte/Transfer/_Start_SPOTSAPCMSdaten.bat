REM SPA 17.06.2020 SAP CMS via SPoT - Daten vom TVL-Server holen 004/009
REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

REM SAP CMS via SPoT Daten 009 und 004 
xcopy /y W:\CMS2TXS_009_%Rest-Date-Suffix%.csv F:\TXS_009_Transfer\SPOTSAPCMS\IMPORT\Archiv
xcopy /y W:\CMS2TXS_004_%Rest-Date-Suffix%.csv F:\TXS_004_Transfer\SPOTSAPCMS\IMPORT\Archiv

REM Dateien im TVL-Verzeichnis loeschen
del /q W:\CMS2TXS_009_%Rest-Date-Suffix%.csv
del /q W:\CMS2TXS_004_%Rest-Date-Suffix%.csv

net use W: /DELETE /y

F:
cd F:\TXS_009_Transfer\SPOTSAPCMS\IMPORT\Archiv
zip -m F:\TXS_009_Transfer\SPOTSAPCMS\IMPORT\Archiv\CMS2TXS_009_%Date-Suffix%.zip CMS2TXS_009_%Rest-Date-Suffix%.csv
cd F:\TXS_004_Transfer\SPOTSAPCMS\IMPORT\Archiv
zip -m F:\TXS_004_Transfer\SPOTSAPCMS\IMPORT\Archiv\CMS2TXS_004_%Date-Suffix%.zip CMS2TXS_004_%Rest-Date-Suffix%.csv
exit

