REM CT 02.09.2017 - Wird für die BLB Verarbeitung nicht mehr benötigt!
exit

REM CT - angelegt am 29.09.2011 
REM SP - 15.09.2015 - "Alt" aus Namen und Aufruf "schtasks" entfernt 
REM CT 21.07.2017 - Anpassungen FITS

REM Date-Suffix
set Date-Suffix=D%date:~8,2%%date:~3,2%%date:~0,2%

echo Date-Suffix: %Date-Suffix%

REM DateSAPCMS-Suffix
set DateSAPCMS-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

echo DateSAPCMS-Suffix: %DateSAPCMS-Suffix%

F:
cd F:\TXS_004_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Rueckmeldung_BLB.bat
call _Start_Rueckmeldung_Finanzierung_BLB.bat

REM Rueckmeldung starten
cd F:\TXS_004_PROD\PROGRAMM\LB
call Rueckmeldung.bat

copy F:\TXS_004_PROD\DATEN\LB\RueckmeldungDarlehen.txt F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix%
del /q F:\TXS_004_PROD\DATEN\LB\RueckmeldungDarlehen.txt

copy F:\TXS_004_PROD\DATEN\LB\RueckmeldungSAPCMS.txt F:\TXS_004_TRANSFER\SAPCMS\EXPORT\TXS2CMS_004_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_004_PROD\DATEN\LB\RueckmeldungSAPCMS.txt

copy F:\TXS_004_PROD\DATEN\LB\TXS2MAVIS_004_%DateSAPCMS-Suffix%.csv F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix%
del /q F:\TXS_004_PROD\DATEN\LB\TXS2MAVIS_004_%DateSAPCMS-Suffix%.csv

REM CT - angelegt am 29.09.2011 
REM SP - 15.09.215 - ALt aus Aufruf entfernt.
REM CT 21.07.2017 - Anpassungen FITS

echo %date% >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
echo %time% >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt

REM Date-Suffix
set Date-Suffix=D%date:~8,2%%date:~3,2%%date:~0,2%

REM DateSAPCMS-Suffix
set DateSAPCMS-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM Zip-Date-Suffix
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

echo Netzlaufwerke verbinden >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
net use Y: /DELETE /y
net use Y: \\nlbprod11-vf\txstvl$\send <###pwd###> /USER:KBK\TXSTRV01 
REM eventuell einbauen /persistent:yes

echo Rueckmeldung Darlehen verschicken >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
echo Datei: F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix% >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt

copy F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix% Y:
replace /r/a F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix% F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\Archiv
del /q F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix%

echo Rueckmeldung SAPCMS verschicken >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
echo Datei: F:\TXS_004_TRANSFER\SAPCMS\EXPORT\TXS2CMS_004_%DateSAPCMS-Suffix%.csv >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt

copy F:\TXS_004_TRANSFER\SAPCMS\EXPORT\TXS2CMS_004_%DateSAPCMS-Suffix%.csv Y:
replace /r/a F:\TXS_004_TRANSFER\SAPCMS\EXPORT\TXS2CMS_004_%DateSAPCMS-Suffix%.csv F:\TXS_004_TRANSFER\SAPCMS\EXPORT\Archiv
del /q F:\TXS_004_TRANSFER\SAPCMS\EXPORT\TXS2CMS_004_%DateSAPCMS-Suffix%.csv

echo Rueckmeldung MAVIS verschicken >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
echo Datei: F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix% >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt

REM CT 16.12.2016 - Verschicken an TVL erst einmal auskommentiert
REM FS/SU aufgenommen per 20170206
copy F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix% Y:
replace /r/a F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix% F:\TXS_004_TRANSFER\MAVIS\EXPORT\Archiv
del /q F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix%

rem Rueckmeldedateien ins Zip schreiben
F:
cd F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\Archiv
zip -m F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\Archiv\DarlehenRueckmeldung_%Zip-Date-Suffix%.zip A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix%

cd F:\TXS_004_TRANSFER\SAPCMS\EXPORT\Archiv
zip -m F:\TXS_004_TRANSFER\SAPCMS\EXPORT\Archiv\TXS2CMS_%Zip-Date-Suffix%.zip TXS2CMS_004_%DateSAPCMS-Suffix%.csv

cd F:\TXS_004_TRANSFER\MAVIS\EXPORT\Archiv
zip -m F:\TXS_004_TRANSFER\MAVIS\EXPORT\Archiv\TXS2MAVIS_%Zip-Date-Suffix%.zip A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix%

net use Y: /DELETE /y

exit