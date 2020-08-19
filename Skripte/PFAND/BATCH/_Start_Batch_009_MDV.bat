@echo off
REM CT - angelegt am 15.04.2013 
REM SP - 15.09.2015 - xcopy ins Unterverzeichnis FMO

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo Marktdaten-Dateien von W: holen
xcopy /y W:\mds_edrexport_fx_i009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\MDV\IMPORT\

del /q W:\mds_edrexport_fx_i009_%Rest-Date-Suffix%.csv

xcopy /y W:\mds_edrexport_rate_i009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\MDV\IMPORT\

del /q W:\mds_edrexport_rate_i009_%Rest-Date-Suffix%.csv

net use W: /DELETE /y

F:
REM Marktdaten kopieren
copy F:\TXS_009_TRANSFER\MDV\IMPORT\mds_edrexport_fx_i009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LB\Wechselkurse.txt
copy F:\TXS_009_TRANSFER\MDV\IMPORT\mds_edrexport_rate_i009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LB\Zinskurse.txt

REM MDV starten
cd F:\TXS_009_PROD\PROGRAMM\LB
call Marktdaten.bat

copy F:\TXS_009_PROD\IMPORT\Marktdaten_TXS.xml F:\REF_009_PROD\IMPORT\Marktdaten_TXS.xml

cd F:\TXS_009_TRANSFER\MDV\IMPORT
zip -m F:\TXS_009_TRANSFER\MDV\IMPORT\Archiv\MDV_%Date-Suffix%.zip mds_edrexport_fx_i009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_TRANSFER\MDV\IMPORT\Archiv\MDV_%Date-Suffix%.zip mds_edrexport_rate_i009_%Rest-Date-Suffix%.csv

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_MDV.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit