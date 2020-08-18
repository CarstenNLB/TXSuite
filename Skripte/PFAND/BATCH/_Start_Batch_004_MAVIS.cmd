REM CT - angelegt am 25.09.2013
REM DW/SU - Ergänzungen wg. Korrektur von Sonderzeichen
REM SP - 15.09.2015 - xcopy ins Unterverzeichnis FMO
REM CT 21.07.2017 - Anpassungen FITS

REM Date-Suffix nur YYYYMM
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Host-Date-Suffix=%mybasedate:~2,2%%mybasedate:~5,2%%mybasedate:~8,2%

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo MAVIS-Datei von W: holen
xcopy /y W:\A1PBAT.BM.I004.DPMAVIS.M710X.XMLOUT.D%Host-Date-Suffix% F:\TXS_004_TRANSFER\MAVIS\IMPORT\
del /q W:\A1PBAT.BM.I004.DPMAVIS.M710X.XMLOUT.D%Host-Date-Suffix%

net use W: /DELETE /y

REM CT 28.05.2017 - Nur noch vom TVL-Server abholen 
REM exit

REM in das Programm/Scriptverzeichnis
F:
cd F:\TXS_004_PROD\PROGRAMM\LB

REM Script zum Korrigieren der Sonderzeichen
powershell F:\TXS_004_PROD\PROGRAMM\LB\mavis_xml_korrektur.ps1

REM eigentliche MAVIS Verarbeitung
call MAVIS.bat

REM copy F:\TXS_004_PROD\IMPORT\MAVIS_TXS.xml F:\TXS_009_PROD\IMPORT\MAVIS_BLB_TXS.xml

REM Archivieren
cd F:\TXS_004_TRANSFER\MAVIS\IMPORT
xcopy /y A1PBAT.BM.I004.DPMAVIS.M710X.XMLOUT.D%Host-Date-Suffix% F:\TXS_004_PROD\DATEN\LB\A1PBAT.BM.I004.DPMAVIS.M710X.XMLOUT
zip -m F:\TXS_004_TRANSFER\MAVIS\IMPORT\%Date-Suffix%.zip A1PBAT.BM.I004.DPMAVIS.M710X.XMLOUT.D%Host-Date-Suffix%

exit

REM PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_004_PROD\STATUS\LB\Prost_MAVIS.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

