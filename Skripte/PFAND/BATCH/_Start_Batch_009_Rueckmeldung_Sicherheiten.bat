REM _Start_Batch_009_Rueckmeldung_Sicherheiten.bat
REM CT - angelegt am 20.03.2018 
REM SPA  20190802
REM ----> Ausbau dploesch.txt
REM ----> Alternative Rueckmeldedateiverarbeitung via generischen Reporting / PowerShell
REM ---->    statt TXS-Rueckmeldeverarbeitung 

REM DateSAPCMS-Suffix
set DateSAPCMS-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

echo DateSAPCMS-Suffix: %DateSAPCMS-Suffix%

REM Zip-Date-Suffix
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%
REM Positionierung auf F-Platte bleib bestehen
F:
REM SPA  20190730 - Start - alte Version auskommentiert
REM cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
REM call _Start_Rueckmeldung_Sicherheiten_NLB.bat
REM Rueckmeldung starten
REM cd F:\TXS_009_PROD\PROGRAMM\LB
REM call Rueckmeldung_Sicherheiten.bat
REM SPA  20190730 - Ende - alte Version auskommentiert
REM SPA  20190730 - Start Alternative
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Reports_NLB_Rueck.cmd
if %errorlevel%==1 goto fehler
REM SPA  20190730 - Ende  Alternative
REM Hier geht es normal weiter .. wenn es keinen Fehler gab
REM SAP CMS NLB
copy F:\TXS_009_PROD\DATEN\LB\RueckmeldungSAPCMS_NLB.txt F:\TXS_009_TRANSFER\SAPCMS\EXPORT\TXS2CMS_009_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_009_PROD\DATEN\LB\RueckmeldungSAPCMS_NLB.txt

REM SAP CMS BLB
copy F:\TXS_009_PROD\DATEN\LB\RueckmeldungSAPCMS_BLB.txt F:\TXS_004_TRANSFER\SAPCMS\EXPORT\TXS2CMS_004_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_009_PROD\DATEN\LB\RueckmeldungSAPCMS_BLB.txt

REM Dateien verschicken
net use Y: /DELETE /y
net use Y: \\nlbprod11-vf\txstvl$\send <###pwd###> /USER:KBK\TXSTRV01 

REM echo Rueckmeldung SAPCMS verschicken >> F:\TXS_009_PROD\PROTOKOLL\LB\Rueckcopy.txt
REM echo Datei: F:\TXS_009_TRANSFER\SAPCMS\EXPORT\TXS2CMS_009_%DateSAPCMS-Suffix%.csv >> F:\TXS_009_PROD\PROTOKOLL\LB\Rueckcopy.txt

copy F:\TXS_009_TRANSFER\SAPCMS\EXPORT\TXS2CMS_009_%DateSAPCMS-Suffix%.csv Y:
replace /r/a F:\TXS_009_TRANSFER\SAPCMS\EXPORT\TXS2CMS_009_%DateSAPCMS-Suffix%.csv F:\TXS_009_TRANSFER\SAPCMS\EXPORT\Archiv
del /q F:\TXS_009_TRANSFER\SAPCMS\EXPORT\TXS2CMS_009_%DateSAPCMS-Suffix%.csv

REM Rueckmeldedateien ins Zip schreiben
F:
cd F:\TXS_009_TRANSFER\SAPCMS\EXPORT\Archiv
zip -m F:\TXS_009_TRANSFER\SAPCMS\EXPORT\Archiv\TXS2CMS_%Zip-Date-Suffix%.zip TXS2CMS_009_%DateSAPCMS-Suffix%.csv

REM Rueckmeldung BLB verschicken
F:
cd F:\TXS_004_PROD

REM echo Rueckmeldung SAPCMS verschicken >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
REM echo Datei: F:\TXS_004_TRANSFER\SAPCMS\EXPORT\TXS2CMS_004_%DateSAPCMS-Suffix%.csv >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt

copy F:\TXS_004_TRANSFER\SAPCMS\EXPORT\TXS2CMS_004_%DateSAPCMS-Suffix%.csv Y:
replace /r/a F:\TXS_004_TRANSFER\SAPCMS\EXPORT\TXS2CMS_004_%DateSAPCMS-Suffix%.csv F:\TXS_004_TRANSFER\SAPCMS\EXPORT\Archiv
del /q F:\TXS_004_TRANSFER\SAPCMS\EXPORT\TXS2CMS_004_%DateSAPCMS-Suffix%.csv

REM Rueckmeldedateien ins Zip schreiben
F:
cd F:\TXS_004_TRANSFER\SAPCMS\EXPORT\Archiv
zip -m F:\TXS_004_TRANSFER\SAPCMS\EXPORT\Archiv\TXS2CMS_%Zip-Date-Suffix%.zip TXS2CMS_004_%DateSAPCMS-Suffix%.csv

net use Y: /DELETE  /y

:fehler

REM PROST immer 

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Rueckmeldung_Sicherheiten.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit