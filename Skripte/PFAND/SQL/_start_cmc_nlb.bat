REM *************************************************************************************************************************************************************************
REM _start_cmc_nlb.bat          
REM SPA 20190809 Outputdateien standardisieren und mit CheckRepLog versehen
REM SPA 20200108 Flugzeuge raus
REM *************************************************************************************************************************************************************************
@echo on
cls

set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set statusdir=F:\TXS_009_PROD\PROTOKOLL\Reports\CMCNLBREP.txt

REM basedate ermitteln
REM *************************************************************************************************************************************************************************
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
for /f "delims=- tokens=1,2,3" %%i in (F:\TXS_009_PROD\PARAM\dp.txt) do (
set "myJahr=%%i"
set "myMonat=%%j"
set "myTag=%%k"
)
REM *************************************************************************************************************************************************************************

echo REM Start startCMCNLB %mybasedate% am %date% %time% >> %statusdir%

REM *************************************************************************************************************************************************************************
REM Defines
REM *************************************************************************************************************************************************************************
set DatApp=%myJahr%%myMonat%%myTag%

set report1=F:\TXS_009_REPORTS\CMC\ReportTXS_009_CMC_EB_KREDIT_%DatApp%.txt
set report2=F:\TXS_009_REPORTS\CMC\ReportTXS_009_CMC_EB_WPAKTIV_%DatApp%.txt
set report3=F:\TXS_009_REPORTS\CMC\ReportTXS_009_CMC_EB_WPPASSIV_%DatApp%.txt
set report4=F:\TXS_009_REPORTS\CMC\ReportTXS_009_CMC_EB_LOANDEPOTS_%DatApp%.txt

set report5=F:\TXS_009_REPORTS\CMC\ablauf_detail_passive_wp_%date:~6,4%%date:~3,2%%date:~0,2%.txt

REM Wechsel in das cmc-Verzeichnis
REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_REPORTS\CMC

REM Aufräumen 
REM *************************************************************************************************************************************************************************
del /q *.xl*

F:
REM Wechsel in das Barwertverzeichnis
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

REM *********************************************************************************************************
REM alle alten Barwertberechnungen löschen
REM *********************************************************************************************************

sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\CMC\cmc_txs_tabellen_loeschen.sql 

echo REM Ende cmc_tabellen_loeschen_ %date% %time% >> %statusdir%
REM *********************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Namenskonvention Teil 1: SCMC fuer StartCMCnlb  nur fuer CMC
REM Namenskonvention Teil 2: P, A, T fuer Produktion, Abnahme, Test
REM Namenskonvention Teil 3: Bnn  Nummer je Aufrufblock Zaehler faengt bei 01 an
REM Namenskonvention Teil 4: FL, HY, KO, OE, SH fuer die Transaktionen oder PF fuer Projekt Pfand
REM Namenskonvention Teil 5: AL, EL, PL fuer applog, errlog, perflog
REM Beschreibung in der Ueberschrift, aber nicht mehr im Namen 
REM *************************************************************************************************************************************************************************
REM Stress => SCMC_P_B01 fuer alle hier
REM *************************************************************************************************************************************************************************

REM *********************************************************************************************************
REM Berechnungen ausführen
REM *********************************************************************************************************
REM Wechsel in das Barwertverzeichnis
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

REM Berechnung PfandBG Kommunal durchführen
start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.EB-Kommunal -basedate=%mybasedate% -applog=%protokolldir%\SCMC_P_B01_KO_AL.log -errlog=%protokolldir%\SCMC_P_B01_KO_EL.log -perflog=%protokolldir%\SCMC_P_B01_KO_PL.log

REM Berechnung PfandBG Hypotheken durchführen
start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.EB-Hypotheken -basedate=%mybasedate% -applog=%protokolldir%\SCMC_P_B01_HY_AL.log -errlog=%protokolldir%\SCMC_P_B01_HY_EL.log -perflog=%protokolldir%\SCMC_P_B01_HY_PL.log

REM Berechnung Schiffe durchführen
start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.EBSchiff -basedate=%mybasedate% -applog=%protokolldir%\SCMC_P_B01_SH_AL.log -errlog=%protokolldir%\SCMC_P_B01_SH_EL.log -perflog=%protokolldir%\SCMC_P_B01_SH_PL.log

REM Berechnung OEPG Kommunal durchführen
start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.EBOEPGKomm -basedate=%mybasedate% -applog=%protokolldir%\SCMC_P_B01_OE_AL.log -errlog=%protokolldir%\SCMC_P_B01_OE_EL.log -perflog=%protokolldir%\SCMC_P_B01_OE_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SCMC_P_B01_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SCMC_P_B01_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SCMC_P_B01_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SCMC_P_B01_OE_AL.log
echo REM Ende txsjobserver_ %date% %time% >> %statusdir%

REM *********************************************************************************************************
REM Wechsel in das cmc-Verzeichnis
F:
cd F:\TXS_009_REPORTS\CMC
REM Achtung: folgenden Eintrag in txs.ini beachten
REM [Reporting]
REM ExportPath=F:\TXS_009_REPORTS\CMC
REM durch den jobserver erzeugt Reports löschen
del /q F:\TXS_009_REPORTS\CMC\*.xl*
REM ggf. stehen gebliebene Ausgabe löschen
del /q F:\TXS_009_REPORTS\CMC\cmc.txt
echo REM Ende Bereinigung_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report kredit ausführen
REM *********************************************************************************************************
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\CMC\neu_cmc_kredit.sql
REM umkopieren
copy F:\TXS_009_REPORTS\CMC\cmc.txt %report1%
del F:\TXS_009_REPORTS\CMC\cmc.txt
echo REM Ende cmc_kredit_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report aktive wertpapiere ausführen
REM *********************************************************************************************************
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\CMC\neu_cmc_aktive_wp.sql
REM umkopieren
copy F:\TXS_009_REPORTS\CMC\cmc.txt %report2%
del F:\TXS_009_REPORTS\CMC\cmc.txt
echo REM Ende cmc_aktive_wp_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report passive wertpapiere ausführen
REM *********************************************************************************************************
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\CMC\neu_cmc_passive_wp.sql
REM umkopieren
copy F:\TXS_009_REPORTS\CMC\cmc.txt %report3%
del F:\TXS_009_REPORTS\CMC\cmc.txt
echo REM Ende cmc_passive_wp_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report Termingelder ausführen
REM *********************************************************************************************************
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\CMC\neu_cmc_festgeld.sql
REM umkopieren
copy F:\TXS_009_REPORTS\CMC\cmc.txt %report4%
del F:\TXS_009_REPORTS\CMC\cmc.txt
echo REM Ende festgeld_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM Archivieren und verschicken
REM ggf. vorherige Ständ elöschen 
del /q ReportTXS_009_CMC_EB_%date:~6,4%%date:~3,2%%date:~0,2%.zip

zip ReportTXS_009_CMC_EB_%date:~6,4%%date:~3,2%%date:~0,2%.zip ReportTXS_009_CMC_EB*.txt
echo REM Ende Zip_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM *********  F:\TXS_009_PROD\PROTOKOLL\LB\repstat.txt erzeugen
REM SU 20161017
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\CMC\repstat.sql
echo REM Ende repstat_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM ********* Test auf Grösse der Reports
@echo off
REM das Skript gibt einen errorlevel, falls ReportTXS_009_CMC_EB_KREDIT*.txt <= 5kB ist
powershell F:\TXS_009_PROD\PROGRAMM\LB\cmc_checker.ps1
@echo on
if ERRORLEVEL 1 goto :FEHLER
echo REM Ende Pruefung_auf_Groesse_ok_ %date% %time% >> %statusdir%
REM ********* Ende Test auf Grösse der Reports
REM *********************************************************************************************************
REM ********* VORSICHT *********
REM Verschicken CMC
net use H: /delete /y

rem seit 06.09.2014 anders - SP net use R: \\Vfopt001\funktion$ Edrpwd100 /USER:KBK\TXSTRV01
net use H: \\kbk.nordlb.local\funktion <###pwd###> /USER:KBK\TXSTRV01

copy ReportTXS_009_CMC_EB_%date:~6,4%%date:~3,2%%date:~0,2%.zip H:\Treasury\Treasury_Coll_Mgt_Report

net use H: /delete /y
REM *********************************************************************************************************
REM ********* VORSICHT *********
echo REM Ende CMC_Versand_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM ********* VORSICHT *********
net use H: \\nlbprod11-vf\txstvl$\send <###pwd###> /USER:KBK\TXSTRV01

REM Kopie      
copy ReportTXS_009_CMC_EB*.txt  H:

net use H: /delete /y

REM neue Stelle löschen
del /q ReportTXS_009_CMC_EB*.txt
REM ********* VORSICHT *********
echo REM Ende EAI_Versand_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM *********************************************************************************************************
REM SQL-Report Ablaufart
REM *********************************************************************************************************
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\CMC\neu_ablauf_detail_passive_wp.sql

copy F:\TXS_009_REPORTS\CMC\ablauf_detail_passive_wp.txt %report5%
del /q F:\TXS_009_REPORTS\CMC\ablauf_detail_passive_wp.txt

net use H: /delete /y

rem seit 06.09.2014 anders - SP net use R: \\Vfopt001\funktion$ Edrpwd100 /USER:KBK\TXSTRV01
net use H: \\kbk.nordlb.local\funktion <###pwd###> /USER:KBK\TXSTRV01
                                
copy ablauf_detail_passive_wp_%date:~6,4%%date:~3,2%%date:~0,2%.txt H:\Treasury\Treasury_Coll_Mgt_Report
net use H: /delete /y

echo REM Ende CMC_II_Versand_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM Aufraeumen
copy %report5% archiv
del /q %report5%

copy *.zip archiv
del /q *.zip
echo REM Ende Aufraeumen_ %date% %time% >> %statusdir%
echo REM Ende  startCMCNLB %mybasedate% am %date% %time% >> %statusdir%
echo REM ________________________________________________________________ >> %statusdir%
REM *********************************************************************************************************
rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Reports_CMC.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)
REM *********************************************************************************************************
:FEHLER

