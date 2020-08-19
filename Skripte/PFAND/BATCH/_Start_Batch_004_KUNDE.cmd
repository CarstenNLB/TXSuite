REM CT - angelegt am 25.09.2013 
REM SP - 15.09.2015 - xcopy ins Unterverzeichnis FMO
REM CT 21.07.2017 - Anpassungen FITS

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo Kunde-Response von W: holen
xcopy /y W:\SPOT2TXS_004_%Rest-Date-Suffix%.xml F:\TXS_004_PROD\DATEN\LB\SPOT2TXS_004.xml
xcopy /y W:\SPOT2TXS_004_%Rest-Date-Suffix%.xml F:\TXS_004_TRANSFER\KUNDE\IMPORT\
del /q W:\SPOT2TXS_004_%Rest-Date-Suffix%.xml

net use W: /DELETE /y

F:
cd F:\TXS_004_PROD\PROGRAMM\LB

REM KundeResponseSplitter starten
call KundeResponseSplitter.bat
copy /Y F:\TXS_004_PROD\DATEN\LB\Kunde_KEV.xml F:\CAM_009_PROD\DATEN\LB\Kunde_KEV_BLB.xml

F:
cd F:\TXS_004_PROD\PROGRAMM\LB
REM Kunde starten
call Kunde.bat

copy /Y F:\TXS_004_PROD\DATEN\LB\Kunde_TXS.xml F:\TXS_004_PROD\Import\Kunde_TXS.xml
copy /Y F:\TXS_004_PROD\DATEN\LB\Kunde_TXS.xml F:\TXS_009_PROD\Import\Kunde_BLB_TXS.xml

cd F:\TXS_004_TRANSFER\KUNDE\IMPORT
zip -m F:\TXS_004_TRANSFER\KUNDE\IMPORT\Archiv\Kunde_%Date-Suffix%.zip SPOT2TXS_004_%Rest-Date-Suffix%.xml

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_004_PROD\STATUS\LB\Prost_Kunde.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)
