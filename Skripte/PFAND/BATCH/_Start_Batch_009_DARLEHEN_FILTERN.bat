REM CT - angelegt am 15.04.2013 
REM SP - 15.09.2015 - xcopy ins Unterverzeichnis FMO
REM SU - 07.01.2016 - xcopy nach FMO auskommentiert, um Laufzeit in der Verarbeitung zu gewinnen.

REM Zip-Date-Suffix
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Host-Date-Suffix=%mybasedate:~2,2%%mybasedate:~5,2%%mybasedate:~8,2%

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo DarKa-Datei von W: holen
xcopy /y W:\A1PBAT.NT.I009.LMTXS.KSXML.D%Host-Date-Suffix% F:\TXS_009_TRANSFER\DARLEHEN\IMPORT\

del /q W:\A1PBAT.NT.I009.LMTXS.KSXML.D%Host-Date-Suffix%

net use W: /DELETE /y

F:
REM Darlehendatei kopieren
copy F:\TXS_009_TRANSFER\DARLEHEN\IMPORT\A1PBAT.NT.I009.LMTXS.KSXML.D%Host-Date-Suffix% F:\TXS_009_PROD\DATEN\LB\Darlehen_I009.txt

cd F:\TXS_009_TRANSFER\DARLEHEN\IMPORT
zip -m F:\TXS_009_TRANSFER\DARLEHEN\IMPORT\Archiv\Darlehen_%Zip-Date-Suffix%.zip A1PBAT.NT.I009.LMTXS.KSXML.D%Host-Date-Suffix%

REM Darlehen-Filtern starten
cd F:\TXS_009_PROD\PROGRAMM\LB
call DarlehenFiltern.bat

REM RefiRegister-Dateien kopieren
copy F:\TXS_009_PROD\DATEN\LB\AktivkontenDaten.txt F:\REF_009_PROD\DATEN\LB\AktivkontenDaten.txt
copy F:\TXS_009_PROD\DATEN\LB\Konsortialgeschaefte.txt F:\REF_009_PROD\DATEN\LB\Konsortialgeschaefte.txt
copy F:\TXS_009_PROD\DATEN\LB\Konsortialdarlehen.txt F:\REF_009_PROD\DATEN\LB\Konsortialdarlehen.txt

REM CAM-Dateien kopieren
copy F:\TXS_009_PROD\DATEN\LB\DarlehenKEV.txt F:\CAM_009_PROD\DATEN\LB\DarlehenKEV.txt

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_DarlehenFiltern.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit