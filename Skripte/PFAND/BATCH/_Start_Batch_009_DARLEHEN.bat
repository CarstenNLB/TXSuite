REM CT - angelegt am 15.04.2013 
REM SP - 15.09.2015 - Erste Kopieranweisung nach ..receive auskommentiert - Altlast
REM SP - 15.09.2015 - xcopy ins Unterverzeichnis FMO
REM SU - 07.01.2016 - xcopy nach FMO auskommentiert, um Laufzeit in der Verarbeitung zu gewinnen.

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

echo Verarbeitung Darlehen >> F:\TXS_009_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt

echo %date% >> F:\TXS_009_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
echo %time% >> F:\TXS_009_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo SAPCMS-Datei von W: holen
xcopy /y W:\CMS2TXS_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\SAPCMS\IMPORT\

del /q W:\CMS2TXS_009_%Rest-Date-Suffix%.csv

net use W: /DELETE /y

F:
REM SAPCMS-Datei kopieren
REM CT 22.10.2014 - SAP CMS Probleme - Belieferung mit "alten" Daten
REM CT 23.10.2014 - reingenommen
copy F:\TXS_009_TRANSFER\SAPCMS\IMPORT\CMS2TXS_009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LB\CMS2TXS_009.txt
copy F:\TXS_009_TRANSFER\SAPCMS\IMPORT\CMS2TXS_009_%Rest-Date-Suffix%.csv F:\REF_009_PROD\DATEN\LB\CMS2TXS_009.txt

replace /r/a F:\TXS_009_TRANSFER\SAPCMS\IMPORT\CMS2TXS_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\SAPCMS\IMPORT\Archiv
del /q F:\TXS_009_TRANSFER\SAPCMS\IMPORT\CMS2TXS_009_%Rest-Date-Suffix%.csv

cd F:\TXS_009_TRANSFER\SAPCMS\IMPORT\Archiv
zip -m F:\TXS_009_TRANSFER\SAPCMS\IMPORT\Archiv\CMS2TXS_%Date-Suffix%.zip CMS2TXS_009_%Rest-Date-Suffix%.csv

REM Darlehen starten
cd F:\TXS_009_PROD\PROGRAMM\LB
REM Kredite PfandBG
call Darlehen.bat

REM Kredite OEPG
call DarlehenOEPG.bat

REM Kredite Schiffe
REM call DarlehenSchiffe.bat

REM Kredite Flugzeuge
REM call DarlehenFlugzeuge.bat

copy F:\TXS_009_PROD\DATEN\LB\Kredite_TXS.xml F:\TXS_009_PROD\IMPORT\Kredite_TXS.xml
copy F:\TXS_009_PROD\DATEN\LB\Kredite_OEPG_TXS.xml F:\TXS_009_PROD\IMPORT\Kredite_OEPG_TXS.xml
REM copy F:\TXS_009_PROD\DATEN\LB\Kredite_Schiffe_TXS.xml F:\TXS_009_PROD\IMPORT\Kredite_Schiffe_TXS.xml
REM copy F:\TXS_009_PROD\DATEN\LB\Kredite_Flugzeuge_TXS.xml F:\TXS_009_PROD\IMPORT\Kredite_Flugzeuge_TXS.xml

echo Verarbeitung beendet >> F:\TXS_009_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
echo %date% >> F:\TXS_009_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt
echo %time% >> F:\TXS_009_PROD\PROTOKOLL\LB\DarlehenVerarbeitung.txt

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Darlehen.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit