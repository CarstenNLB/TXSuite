REM SP - 12.02.2019 - Rückmeldung MAVIS BLB auskommentiert.
REM CT - 21.07.2017 - Anpassungen FITS
REM SP - 15.09.2015 - ALt aus Aufruf entfernt.
REM CT - angelegt am 29.09.2011 

echo %date% >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
echo %time% >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt

REM Date-Suffix
set Date-Suffix=D%date:~8,2%%date:~3,2%%date:~0,2%

REM DateSAPCMS-Suffix
set DateSAPCMS-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM Zip-Date-Suffix
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

echo Netzlaufwerke verbinden >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
net use Y: /DELETE
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

REM SP - 12.02.2019 - Rückmeldung MAVIS BLB auskommentiert - START
REM echo Rueckmeldung MAVIS verschicken >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
REM echo Datei: F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix% >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
REM ---------------------------------------------------------------------------------------------------------------------------------------
REM copy F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix% Y:
REM replace /r/a F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix% F:\TXS_004_TRANSFER\MAVIS\EXPORT\Archiv
REM del /q F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix%
REM SP - 12.02.2019 - Rückmeldung MAVIS BLB auskommentiert - ENDE

rem Rueckmeldedateien ins Zip schreiben
F:
cd F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\Archiv
zip -m F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\Archiv\DarlehenRueckmeldung_%Zip-Date-Suffix%.zip A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix%

cd F:\TXS_004_TRANSFER\SAPCMS\EXPORT\Archiv
zip -m F:\TXS_004_TRANSFER\SAPCMS\EXPORT\Archiv\TXS2CMS_%Zip-Date-Suffix%.zip TXS2CMS_004_%DateSAPCMS-Suffix%.csv

REM SP 12.02.2019 - Dieser Teil bleibt solange bestehen, bis BLB inhaltlich ausgebaut worden ist.. daher weiter ins Archiv zippen
cd F:\TXS_004_TRANSFER\MAVIS\EXPORT\Archiv
zip -m F:\TXS_004_TRANSFER\MAVIS\EXPORT\Archiv\TXS2MAVIS_%Zip-Date-Suffix%.zip A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix%

net use Y: /DELETE
