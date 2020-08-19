REM CT - angelegt am 25.09.2013 
REM SP - 15.09.2015 - xcopy ins Unterverzeichnis FMO
REM CT - 21.07.2017 Anpassung FITS
REM SPA- 11.06.2018 Anpassung Rel 9.10 - vorbereitet .. mit REM versehen
REM SPA- 18.06.2018 Anpassung Rel 9.10 - REM entfernt

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

echo Verarbeitung Darlehen >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt

echo %date% >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
echo %time% >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo SAPCMS-Datei von W: holen
xcopy /y W:\CMS2TXS_004_%Rest-Date-Suffix%.csv F:\TXS_004_TRANSFER\SAPCMS\IMPORT\
del /q W:\CMS2TXS_004_%Rest-Date-Suffix%.csv

net use W: /DELETE /y

REM SAPCMS-Datei kopieren
copy F:\TXS_004_TRANSFER\SAPCMS\IMPORT\CMS2TXS_004_%Rest-Date-Suffix%.csv F:\TXS_004_PROD\DATEN\LB\CMS2TXS_004_Original.txt

replace /r/a F:\TXS_004_TRANSFER\SAPCMS\IMPORT\CMS2TXS_004_%Rest-Date-Suffix%.csv F:\TXS_004_TRANSFER\SAPCMS\IMPORT\Archiv
del /q F:\TXS_004_TRANSFER\SAPCMS\IMPORT\CMS2TXS_004_%Rest-Date-Suffix%.csv

F:
cd F:\TXS_004_TRANSFER\SAPCMS\IMPORT\Archiv
zip -m F:\TXS_004_TRANSFER\SAPCMS\IMPORT\Archiv\CMS2TXS_%Date-Suffix%.zip CMS2TXS_004_%Rest-Date-Suffix%.csv

REM Darlehen starten
cd F:\TXS_004_PROD\PROGRAMM\LB

REM Festsetzungsdatum aus SAP CMS entfernen
call FileFeldDelete.bat

REM KundennummernUmstellung PfandBG
call KundennummernUmstellung_PFBG.bat

REM Kredite PfandBG
call Darlehen.bat

REM KundeOriginatorUmstellung PfandBG
call KundeOriginatorUmstellung_Kredite.bat

REM Verarbeitung fuer Schiffe herausgenommen, da alle Schiffskredite nach LoanIQ migriert wurden. - CT 17.12.2018
REM KundennummernUmstellung Schiffe
REM call KundennummernUmstellung_Schiffe.bat

REM Kredite Schiffe
REM call DarlehenSchiffe.bat

REM KundeOriginatorUmstellung Schiffe
REM call KundeOriginatorUmstellung_Kredite_Schiffe.bat

REM Check Kredite_Originator_TXS.xml
echo Verarbeitung Start BLB_Refzins >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
echo %date% >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
echo %time% >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
call BLB_Refzins.cmd
echo Verarbeitung Ende BLB_Refzins >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
echo %date% >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
echo %time% >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt

copy F:\TXS_004_PROD\DATEN\LB\Kredite_Originator_TXS.xml F:\TXS_004_PROD\IMPORT\Kredite_TXS.xml
REM copy F:\TXS_004_PROD\DATEN\LB\Kredite_Schiffe_Originator_TXS.xml F:\TXS_004_PROD\IMPORT\Kredite_Schiffe_TXS.xml
copy F:\TXS_004_PROD\DATEN\LB\Kredite_Originator_TXS.xml F:\TXS_009_PROD\IMPORT\Kredite_BLB_TXS.xml
REM copy F:\TXS_004_PROD\DATEN\LB\Kredite_Schiffe_Originator_TXS.xml F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_BLB_TXS.xml

echo Verarbeitung beendet >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
echo %date% >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
echo %time% >> F:\TXS_004_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_004_PROD\STATUS\LB\Prost_Darlehen.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)
