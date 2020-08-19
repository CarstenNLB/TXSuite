REM 20200617 SPA - Responsedatei wird auf TVL-Server in das Unterverzeichnis spot kopiert
REM 20190827 SPA - Protokollierung auf Standard umgestellt
REM 20170903 SPA - Verschmelzung - es werden drei Versionen erstellt - also 12 Dateien
REM              - 4 Dateien Gesamt - werden nur archiviert
REM              - 4 Dateien NLB - werden versandt
REM              - 4 Dateien BLB - werden versandt
REM 28.04.2016 - Kopie in F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT
REM            - Kopie der set - Definitionen von set report* nach set spotreport*
REM            - Kopie des ABA.txt nach spotreport* vor dem del
REM 29.04.2016 - Am Ende des Scriptes die Archivierung
REM 02.05.2016 - Versenden
REM 20150513 - TVL-Verzeichnis aktualisiert
REM Skript zur Erzeugung von Einzelbarwertauswertungen aus TXS für das ABA
REM Unnasch 20121130
REM *************************************************************************************************************************************************************************
set statusdir=F:\TXS_009_PROD\PROTOKOLL\Reports\ABANLBREP.txt
REM *************************************************************************************************************************************************************************
REM basedate ermitteln
REM *************************************************************************************************************************************************************************
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
for /f "delims=- tokens=1,2,3" %%i in (F:\TXS_009_PROD\PARAM\dp.txt) do (
set "myJahr=%%i"
set "myMonat=%%j"
set "myTag=%%k"
)
REM *************************************************************************************************************************************************************************
echo REM Start startABANLB %mybasedate% am %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM Defines
REM *************************************************************************************************************************************************************************
set DatApp=%myJahr%%myMonat%%myTag%
REM NORDLB
set report1=F:\TXS_009_REPORTS\ABA\ReportTXS_009_ABA_EB_KREDIT_%DatApp%.csv
set report2=F:\TXS_009_REPORTS\ABA\ReportTXS_009_ABA_EB_WPAKTIV_%DatApp%.csv
set report3=F:\TXS_009_REPORTS\ABA\ReportTXS_009_ABA_EB_WPPASSIV_%DatApp%.csv
set report4=F:\TXS_009_REPORTS\ABA\ReportTXS_009_ABA_EB_LOANDEPOTS_%DatApp%.csv
set spotreport1=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK1_009_%DatApp%_KREDIT.csv
set spotreport2=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK2_009_%DatApp%_WPAKTIV.csv
set spotreport3=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK3_009_%DatApp%_WPPASSIV.csv
set spotreport4=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK4_009_%DatApp%_TERMINGELD.csv
REM BLB
set report5=F:\TXS_009_REPORTS\ABA\ReportTXS_004_ABA_EB_KREDIT_%DatApp%.csv
set report6=F:\TXS_009_REPORTS\ABA\ReportTXS_004_ABA_EB_WPAKTIV_%DatApp%.csv
set report7=F:\TXS_009_REPORTS\ABA\ReportTXS_004_ABA_EB_WPPASSIV_%DatApp%.csv
set report8=F:\TXS_009_REPORTS\ABA\ReportTXS_004_ABA_EB_LOANDEPOTS_%DatApp%.csv
set spotreport5=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK1_004_%DatApp%_KREDIT.csv
set spotreport6=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK2_004_%DatApp%_WPAKTIV.csv
set spotreport7=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK3_004_%DatApp%_WPPASSIV.csv
set spotreport8=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK4_004_%DatApp%_TERMINGELD.csv
REM GESAMT NLB und BLB
set report9=F:\TXS_009_REPORTS\ABA\ReportTXS_GES_ABA_EB_KREDIT_%DatApp%.csv
set report10=F:\TXS_009_REPORTS\ABA\ReportTXS_GES_ABA_EB_WPAKTIV_%DatApp%.csv
set report11=F:\TXS_009_REPORTS\ABA\ReportTXS_GES_ABA_EB_WPPASSIV_%DatApp%.csv
set report12=F:\TXS_009_REPORTS\ABA\ReportTXS_GES_ABA_EB_LOANDEPOTS_%DatApp%.csv
set spotreport9=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK1_GES_%DatApp%_KREDIT.csv
set spotreport10=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK2_GES_%DatApp%_WPAKTIV.csv
set spotreport11=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK3_GES_%DatApp%_WPPASSIV.csv
set spotreport12=F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK4_GES_%DatApp%_TERMINGELD.csv
REM *************************************************************************************************************************************************************************
REM Wechsel in das aba-Verzeichnis
REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_REPORTS\ABA
REM Aufräumen 
REM *************************************************************************************************************************************************************************
REM ggf. stehen gebliebene Ausgabe löschen
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende Bereinigung_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM *************************************************************************************************************************************************************************
REM BEREICH 1 - NLB - 
REM *********************************************************************************************************
REM sql-report kredit ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neu009_ABA_kredit.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report1%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport1%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_KREDIT.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neu009_aba_kredit_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report aktive wertpapiere ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neu009_ABA_aktive_wp.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report2%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport2%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPAKTIV.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neu009_aba_aktive_wp_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report passive wertpapiere ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neu009_ABA_passive_wp.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report3%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport3%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPPASSIV.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neu009_aba_passive_wp_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report Termingelder ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neu009_aba_festgeld.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report4%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport4%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_LOANDEPOTS.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neu009_aba_festgeld_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM BEREICH 2 - BLB - 
REM *********************************************************************************************************
REM sql-report kredit ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neu004_ABA_kredit.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report5%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport5%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_KREDIT.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neu004_aba_kredit_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report aktive wertpapiere ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neu004_ABA_aktive_wp.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report6%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport6%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPAKTIV.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neu004_aba_aktive_wp_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report passive wertpapiere ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neu004_ABA_passive_wp.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report7%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport7%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPPASSIV.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neu004_aba_passive_wp_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report Termingelder ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neu004_aba_festgeld.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report8%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport8%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_LOANDEPOTS.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neu004_aba_festgeld_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM BEREICH 3 - NLB und BLB - 
REM *********************************************************************************************************
REM sql-report kredit ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neuges_ABA_kredit.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report9%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport9%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_GES_ABA_EB_KREDIT.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt1
echo REM Ende neuges_aba_kredit_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report aktive wertpapiere ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neuges_ABA_aktive_wp.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report10%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport10%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_GES_ABA_EB_WPAKTIV.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neuges_aba_aktive_wp_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report passive wertpapiere ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neuges_ABA_passive_wp.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report11%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport11%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_GES_ABA_EB_WPPASSIV.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neuges_aba_passive_wp_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM sql-report Termingelder ausführen
sqlplus txsadmin/<###pwd###>@DNLBBARW @F:\TXS_009_REPORTS\ABA\neuges_aba_festgeld.sql
REM *********************************************************************************************************
REM umkopieren
copy F:\TXS_009_REPORTS\ABA\ABA.txt %report12%
copy F:\TXS_009_REPORTS\ABA\ABA.txt %spotreport12%
copy F:\TXS_009_REPORTS\ABA\ABA.txt F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_GES_ABA_EB_LOANDEPOTS.csv
del F:\TXS_009_REPORTS\ABA\ABA.txt
echo REM Ende neuges_aba_festgeld_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM ********* Test auf Grösse der Reports
@echo off
REM das Skript gibt einen errorlevel, falls ReportTXS_009_ABA_EB_KREDIT*.txt <= 5kB ist
powershell F:\TXS_009_PROD\PROGRAMM\LB\aba_checker.ps1
@echo on
if ERRORLEVEL 1 goto :FEHLER
echo REM Ende Pruefung_auf_Groesse_ok_ %date% %time% >> %statusdir%
REM ********* Ende Test auf Grösse der Reports
REM *********************************************************************************************************
REM Verschicken und archivieren 
REM ********* VORSICHT *********
REM Verschicken ABA

net use Y: /DELETE /y
net use Y: \\nlbprod11-vf\txstvl$\send <###pwd###> /USER:KBK\TXSTRV01

REM *********************************************************************************************************
REM los gehts NLB und BLB 
copy %report1% Y:
copy %report2% Y:
copy %report3% Y:
copy %report4% Y:
copy %report5% Y:
copy %report6% Y:
copy %report7% Y:
copy %report8% Y:

net use Y: /DELETE /y
REM *********************************************************************************************************
REM ********* VORSICHT *********
echo REM Ende ABA_Versand_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM ggf. vorherige Stände löschen 
del /q ReportTXS_009_ABA_EB_%date:~6,4%%date:~3,2%%date:~0,2%.zip
del /q ReportTXS_004_ABA_EB_%date:~6,4%%date:~3,2%%date:~0,2%.zip
del /q ReportTXS_GES_ABA_EB_%date:~6,4%%date:~3,2%%date:~0,2%.zip

zip -m ReportTXS_009_ABA_EB_%date:~6,4%%date:~3,2%%date:~0,2%.zip ReportTXS_009_ABA_EB*.csv
zip -m ReportTXS_004_ABA_EB_%date:~6,4%%date:~3,2%%date:~0,2%.zip ReportTXS_004_ABA_EB*.csv
zip -m ReportTXS_GES_ABA_EB_%date:~6,4%%date:~3,2%%date:~0,2%.zip ReportTXS_GES_ABA_EB*.csv

REM alte Stelle löschen
REM del /q ReportTXS_009_ABA_EB*.txt

copy *.zip archiv
del /q *.zip
echo REM Ende Aufraeumen1_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM spotabacus
cd F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT
REM *********************************************************************************************************
REM ********* VORSICHT *********
REM Verschicken nach SPOT
REM SPA SPOT 20200716 Unterverzeichnis spot ----------------

net use Y: /DELETE /y

net use Y: \\nlbprod11-vf\txstvl$\send\spot <###pwd###> /USER:KBK\TXSTRV01

copy F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK1_009*.csv Y:
copy F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK2_009*.csv Y:
copy F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK3_009*.csv Y:
copy F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK4_009*.csv Y:
copy F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK1_004*.csv Y:
copy F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK2_004*.csv Y:
copy F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK3_004*.csv Y:
copy F:\TXS_009_TRANSFER\SPOTABACUS\EXPORT\TXS2SPOT_DECKUNGSSTOCK4_004*.csv Y:

net use Y: /DELETE /y
echo REM Ende SPOT_Versand_ %date% %time% >> %statusdir%
REM *********************************************************************************************************
REM Nach dem verschicken das zippen 
del /q TXS2SPOT_DECKUNGSSTOCK_ALL_%date:~6,4%%date:~3,2%%date:~0,2%.zip
zip -m TXS2SPOT_DECKUNGSSTOCK_ALL_%date:~6,4%%date:~3,2%%date:~0,2%.zip TXS2SPOT_DECKUNGSSTOCK*.csv
copy *.zip archiv
del /q *.zip
echo REM Ende Aufraeumen2_ %date% %time% >> %statusdir%
echo REM Ende  startABANLB %mybasedate% am %date% %time% >> %statusdir%
echo REM ________________________________________________________________ >> %statusdir%
REM *********************************************************************************************************
rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Reports_ABACUS.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

:FEHLER
