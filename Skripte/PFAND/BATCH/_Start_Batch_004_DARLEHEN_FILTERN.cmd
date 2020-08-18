REM CT - angelegt am 25.09.2013 
REM SP - 15.09.2015 - xcopy ins Unterverzeichnis FMO
REM CT - 21.07.2017 Anpassungen FITS

REM Zip-Date-Suffix
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Host-Date-Suffix=%mybasedate:~2,2%%mybasedate:~5,2%%mybasedate:~8,2%

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo DarKa-Datei von W: holen
xcopy /y W:\A1PBAT.NT.I004.LMTXS.KSXML.D%Host-Date-Suffix% F:\TXS_004_TRANSFER\DARLEHEN\IMPORT\
del /q W:\A1PBAT.NT.I004.LMTXS.KSXML.D%Host-Date-Suffix%

net use W: /DELETE /y

REM Darlehendatei kopieren
copy F:\TXS_004_TRANSFER\DARLEHEN\IMPORT\A1PBAT.NT.I004.LMTXS.KSXML.D%Host-Date-Suffix% F:\TXS_004_PROD\DATEN\LB\Darlehen_I004.txt

F:
cd F:\TXS_004_TRANSFER\DARLEHEN\IMPORT
zip -m F:\TXS_004_TRANSFER\DARLEHEN\IMPORT\Archiv\Darlehen_%Zip-Date-Suffix%.zip A1PBAT.NT.I004.LMTXS.KSXML.D%Host-Date-Suffix%

REM Darlehen-Filtern starten
cd F:\TXS_004_PROD\PROGRAMM\LB
call Darlehen_Filtern.bat

REM CAM-Dateien kopieren
copy F:\TXS_004_PROD\DATEN\LB\DarlehenKEV.txt F:\CAM_009_PROD\DATEN\LB\DarlehenKEV_BLB.txt

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_004_PROD\STATUS\LB\Prost_DarlehenFiltern.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)