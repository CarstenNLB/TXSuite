REM SPA 20200729 - Loan IQ - Rückmeldung Loan IQ auskommentiert w/1802605935 
REM SPA 20180626 - Rueckbau 20180619 - LIQ wird wieder geliefert
REM SPA 20180619 LIQ - Versand unterbinden w/x4-Anlieferungen
REM CT - angelegt am 22.03.2018

REM Date-Suffix
set Date-Suffix=D%date:~8,2%%date:~3,2%%date:~0,2%

echo Date-Suffix: %Date-Suffix%

REM DateSAPCMS-Suffix
set DateSAPCMS-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

echo DateSAPCMS-Suffix: %DateSAPCMS-Suffix%

REM Zip-Date-Suffix
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Rueckmeldung_Finanzgeschaefte_NLB.bat
REM CT 20.03.2018 - Herausgenommen, da es nicht benutzt wird
REM call _Start_Rueckmeldung_Finanzierung_NLB.cmd

REM Rueckmeldung starten
cd F:\TXS_009_PROD\PROGRAMM\LB

call Rueckmeldung_Finanzgeschaefte.bat

REM DarKa NLB
copy F:\TXS_009_PROD\DATEN\LB\RueckmeldungDarlehen_NLB.txt F:\TXS_009_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I009.STEDR.KRSRUECK.%Date-Suffix%
del /q F:\TXS_009_PROD\DATEN\LB\RueckmeldungDarlehen_NLB.txt

REM DarKa BLB
copy F:\TXS_009_PROD\DATEN\LB\RueckmeldungDarlehen_BLB.txt F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix%
del /q F:\TXS_009_PROD\DATEN\LB\RueckmeldungDarlehen_BLB.txt

REM MAVIS NLB
copy F:\TXS_009_PROD\DATEN\LB\TXS2MAVIS_009_%DateSAPCMS-Suffix%.csv F:\TXS_009_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I009.STTXS.MAVRUECK.%Date-Suffix%
del /q F:\TXS_009_PROD\DATEN\LB\TXS2MAVIS_009_%DateSAPCMS-Suffix%.csv

REM MAVIS BLB
copy F:\TXS_009_PROD\DATEN\LB\TXS2MAVIS_004_%DateSAPCMS-Suffix%.csv F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix%
del /q F:\TXS_009_PROD\DATEN\LB\TXS2MAVIS_004_%DateSAPCMS-Suffix%.csv

REM LoanIQ NLB
copy F:\TXS_009_PROD\DATEN\LB\RueckmeldungLoanIQ.xml F:\TXS_009_TRANSFER\LOANIQ\EXPORT\TXS2LIQ_009_%DateSAPCMS-Suffix%.xml
del /q F:\TXS_009_PROD\DATEN\LB\RueckmeldungLoanIQ.xml

REM Dateien verschicken
REM echo Netzlaufwerk verbinden >> F:\TXS_009_PROD\PROTOKOLL\LB\Rueckcopy.txt
net use Y: /DELETE /y
net use Y: \\nlbprod11-vf\txstvl$\send <###pwd###> /USER:KBK\TXSTRV01

REM echo Rueckmeldung Darlehen verschicken >> F:\TXS_009_PROD\PROTOKOLL\LB\Rueckcopy.txt
REM echo Datei: F:\TXS_009_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I009.STEDR.KRSRUECK.%Date-Suffix% >> F:\TXS_009_PROD\PROTOKOLL\LB\Rueckcopy.txt

copy F:\TXS_009_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I009.STEDR.KRSRUECK.%Date-Suffix% Y:
replace /r/a F:\TXS_009_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I009.STEDR.KRSRUECK.%Date-Suffix% F:\TXS_009_TRANSFER\DARLEHEN\EXPORT\Archiv
del /q F:\TXS_009_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I009.STEDR.KRSRUECK.%Date-Suffix%

REM echo Rueckmeldung MAVIS verschicken >> F:\TXS_009_PROD\PROTOKOLL\LB\Rueckcopy.txt
REM echo Datei: F:\TXS_009_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I009.STTXS.MAVRUECK.%Date-Suffix% >> F:\TXS_009_PROD\PROTOKOLL\LB\Rueckcopy.txt

REM FS/SU aufgenommen 20170206 
copy F:\TXS_009_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I009.STTXS.MAVRUECK.%Date-Suffix% Y:
replace /r/a F:\TXS_009_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I009.STTXS.MAVRUECK.%Date-Suffix% F:\TXS_009_TRANSFER\MAVIS\EXPORT\Archiv
del /q F:\TXS_009_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I009.STTXS.MAVRUECK.%Date-Suffix%

REM echo Rueckmeldung LoanIQ verschicken >> F:\TXS_009_PROD\PROTOKOLL\LB\Rueckcopy.txt
REM echo Datei: F:\TXS_009_TRANSFER\LOANIQ\EXPORT\TXS2LIQ_009_%DateSAPCMS-Suffix%.xml >> F:\TXS_009_PROD\PROTOKOLL\LB\Rueckcopy.txt

REM SPA 20180619-COPY auskommentiert - kein Versand - ab 20180626 wieder scharf geschaltet
copy F:\TXS_009_TRANSFER\LOANIQ\EXPORT\TXS2LIQ_009_%DateSAPCMS-Suffix%.xml Y:
replace /r/a F:\TXS_009_TRANSFER\LOANIQ\EXPORT\TXS2LIQ_009_%DateSAPCMS-Suffix%.xml F:\TXS_009_TRANSFER\LOANIQ\EXPORT\Archiv
del /q F:\TXS_009_TRANSFER\LOANIQ\EXPORT\TXS2LIQ_009_%DateSAPCMS-Suffix%.xml

rem Rueckmeldedateien ins Zip schreiben
F:
cd F:\TXS_009_TRANSFER\DARLEHEN\EXPORT\Archiv
zip -m F:\TXS_009_TRANSFER\DARLEHEN\EXPORT\Archiv\DarlehenRueckmeldung_%Zip-Date-Suffix%.zip A1PBAT.NT.I009.STEDR.KRSRUECK.%Date-Suffix%

cd F:\TXS_009_TRANSFER\MAVIS\EXPORT\Archiv
zip -m F:\TXS_009_TRANSFER\MAVIS\EXPORT\Archiv\TXS2MAVIS_%Zip-Date-Suffix%.zip A1PBAT.NT.I009.STTXS.MAVRUECK.%Date-Suffix%

cd F:\TXS_009_TRANSFER\LOANIQ\EXPORT\Archiv
zip -m F:\TXS_009_TRANSFER\LOANIQ\EXPORT\Archiv\TXS2LIQ_%Zip-Date-Suffix%.zip TXS2LIQ_009_%DateSAPCMS-Suffix%.xml

REM Rueckmeldung BLB verschicken
F:
cd F:\TXS_004_PROD

REM echo Rueckmeldung Darlehen verschicken >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
REM echo Datei: F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix% >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt

copy F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix% Y:
replace /r/a F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix% F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\Archiv
del /q F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix%

REM echo Rueckmeldung MAVIS verschicken >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt
REM echo Datei: F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix% >> F:\TXS_004_PROD\PROTOKOLL\LB\Rueckcopy.txt

REM FS/SU aufgenommen per 20170206
rem KEINE Rückmeldung an Bremen
rem copy F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix% Y:
replace /r/a F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix% F:\TXS_004_TRANSFER\MAVIS\EXPORT\Archiv
del /q F:\TXS_004_TRANSFER\MAVIS\EXPORT\A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix%

rem Rueckmeldedateien ins Zip schreiben
F:
cd F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\Archiv
zip -m F:\TXS_004_TRANSFER\DARLEHEN\EXPORT\Archiv\DarlehenRueckmeldung_%Zip-Date-Suffix%.zip A1PBAT.NT.I004.STEDR.KRSRUECK.%Date-Suffix%

cd F:\TXS_004_TRANSFER\MAVIS\EXPORT\Archiv
zip -m F:\TXS_004_TRANSFER\MAVIS\EXPORT\Archiv\TXS2MAVIS_%Zip-Date-Suffix%.zip A1PBAT.NT.I004.STTXS.MAVRUECK.%Date-Suffix%

REM Aktuelle Reports an ABACUS verschicken
REM NLB
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_KREDIT.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_KREDIT_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_KREDIT.csv

copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_LOANDEPOTS.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_LOANDEPOTS_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_LOANDEPOTS.csv
    
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPAKTIV.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPAKTIV_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPAKTIV.csv

copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPPASSIV.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPPASSIV_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPPASSIV.csv
REM BLB per 23.10.2017 eingefügt
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_KREDIT.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_KREDIT_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_KREDIT.csv

copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_LOANDEPOTS.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_LOANDEPOTS_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_LOANDEPOTS.csv
    
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPAKTIV.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPAKTIV_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPAKTIV.csv

copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPPASSIV.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPPASSIV_%DateSAPCMS-Suffix%.csv
del /q F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPPASSIV.csv

REM ********* VORSICHT *********
REM REM REM Reports an ABACUS verschicken

net use Y: /DELETE /y
net use Y: \\nlbprod11-vf\txstvl$\send Edrpwd100 /USER:KBK\TXSTRV01 

REM REM REM los gehts 
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_KREDIT_%DateSAPCMS-Suffix%.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_LOANDEPOTS_%DateSAPCMS-Suffix%.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPAKTIV_%DateSAPCMS-Suffix%.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPPASSIV_%DateSAPCMS-Suffix%.csv Y:
REM BLB per 23.10.2017 eingefügt
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_KREDIT_%DateSAPCMS-Suffix%.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_LOANDEPOTS_%DateSAPCMS-Suffix%.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPAKTIV_%DateSAPCMS-Suffix%.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPPASSIV_%DateSAPCMS-Suffix%.csv Y:

net use Y: /DELETE /y

REM ********* VORSICHT *********

REM Reports archivieren...
F:
cd F:\TXS_009_REPORTS\ABA\AktuelleReports
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_%DateSAPCMS-Suffix%.zip ReportTXS_009_ABA_EB_KREDIT_%DateSAPCMS-Suffix%.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_%DateSAPCMS-Suffix%.zip ReportTXS_009_ABA_EB_LOANDEPOTS_%DateSAPCMS-Suffix%.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_%DateSAPCMS-Suffix%.zip ReportTXS_009_ABA_EB_WPAKTIV_%DateSAPCMS-Suffix%.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_%DateSAPCMS-Suffix%.zip ReportTXS_009_ABA_EB_WPPASSIV_%DateSAPCMS-Suffix%.csv
REM BLB per 23.10.2017 eingefügt
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_%DateSAPCMS-Suffix%.zip ReportTXS_004_ABA_EB_KREDIT_%DateSAPCMS-Suffix%.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_%DateSAPCMS-Suffix%.zip ReportTXS_004_ABA_EB_LOANDEPOTS_%DateSAPCMS-Suffix%.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_%DateSAPCMS-Suffix%.zip ReportTXS_004_ABA_EB_WPAKTIV_%DateSAPCMS-Suffix%.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_%DateSAPCMS-Suffix%.zip ReportTXS_004_ABA_EB_WPPASSIV_%DateSAPCMS-Suffix%.csv

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Rueckmeldung_Finanzgeschaefte.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit