REM Exit für SSM wieder rausgenommen per 01.10.2018
REM *************************************************************************************************************************************************************************
REM TXS Reporterstellung 
REM SU 201612
REM SU 201701
REM SU 201809 Anpassungen und neue Reports
REM SU 201809 Anpassungen für SSM
REM SU 201812 Grenzwerte direkt nach den Kennzahlen
REM SU 201812 Verlagerung weiterer Reports ins Barwertsystem
REM SU 201901 FRC-Reports aufgenommen
REM 
REM Barwert: geteilt in parallelisierte Blöcke, maximal fünf gleichzeitige Reportläufe und Umbenennung in die Fachbereichsvorgaben, dann Umwandlung .xslm in .xlsx
REM          Liquivorschauen als .xslm 
REM andere: geteilt in parallelisierte Blöcke, keine Umwandlung
REM Die TXS-Reporterstellung für TSY wurde ins Barwertsystem ausgelagert 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Aufräumen
REM *************************************************************************************************************************************************************************

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle

F:
REM auch ggf. Überbleibsel von vorherigen Versuchen am selben Tag
del /q F:\TXS_009_REPORTS\TXS\ReportTXS_009_%date:~6,4%%date:~3,2%%date:~0,2%.zip
del /q F:\TXS_009_REPORTS\TXS\ReportTXS_009_taeglich.zip

cd F:\TXS_009_REPORTS\TXS\P_1
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*

cd F:\TXS_009_REPORTS\TXS\P_2
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*

cd F:\TXS_009_REPORTS\TXS\P_3
del /q F:\TXS_009_REPORTS\TXS\P_3\*.*

cd F:\TXS_009_REPORTS\TXS\P_4
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*

cd F:\TXS_009_REPORTS\TXS\P_5
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*

cd F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_TEMP\*.*

REM *************************************************************************************************************************************************************************
REM Stress 
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_1.ini -execute=szenarioreport.BW-Berechnung-Hypotheken-Stress -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_hypo_stress.log -errlog=%protokolldir%\errlog_prod_hypo_stress.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_2.ini -execute=szenarioreport.BW-Berechnung-Kommunal-Stress -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_komm_stress.log -errlog=%protokolldir%\errlog_prod_komm_stress.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_3.ini -execute=szenarioreport.BWSchiffStress -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_schf_stress.log -errlog=%protokolldir%\errlog_prod_schf_stress.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_4.ini -execute=szenarioreport.BWFlugzeugStress -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_flug_stress.log -errlog=%protokolldir%\errlog_prod_flug_stress.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_5.ini -execute=szenarioreport.BWOEPGKommStress -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_oepg_stress.log -errlog=%protokolldir%\errlog_prod_oepg_stress.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_1

ren LiquiVorschau_*-*-*Hypotheken*Marktzins*.xlsm LiquiVorschau_Hypothekenpfandbrief.xlsm
ren Cashflow_*-*-*Hypotheken*(Stress)*.xlsm Cashflow_Hypothekenpfandbrief_Stress.xlsm
copy LiquiVorschau_Hypothekenpfandbrief.xlsm LiquiVorschau_Hypothekenpfandbrief_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Hypothekenpfandbrief_Stress.xlsm Cashflow_Hypothekenpfandbrief_Stress_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy LiquiVorschau_Hypothekenpfandbrief*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_Hypothekenpfandbrief_Stress*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_2
ren LiquiVorschau_*-*-*ffentlicher*Marktzins*.xlsm LiquiVorschau_Oeffentlicher_Pfandbrief.xlsm
ren Cashflow_*-*-*ffentlicher*(Stress)*.xlsm Cashflow_Oeffentlicher_Pfandbrief_Stress.xlsm
copy LiquiVorschau_Oeffentlicher_Pfandbrief.xlsm LiquiVorschau_Oeffentlicher_Pfandbrief_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Stress.xlsm Cashflow_Oeffentlicher_Pfandbrief_Stress_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy LiquiVorschau_Oeffentlicher_Pfandbrief*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_Oeffentlicher_Pfandbrief_Stress*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_3
ren LiquiVorschau_*-*-*Schiff*Marktzins*.xlsm LiquiVorschau_Schiffspfandbrief.xlsm  
ren Cashflow_*-*-*Schiff*(Stress)*.xlsm Cashflow_Schiffspfandbrief_Stress.xlsm
copy LiquiVorschau_Schiffspfandbrief.xlsm LiquiVorschau_Schiffspfandbrief_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Schiffspfandbrief_Stress.xlsm Cashflow_Schiffspfandbrief_Stress_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy LiquiVorschau_Schiffspfandbrief*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_Schiffspfandbrief_Stress*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_3\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_4
ren LiquiVorschau_*-*-*Flug*Marktzins*.xlsm LiquiVorschau_Flugzeugpfandbrief.xlsm
ren Cashflow_*-*-*Flug*(Stress)*.xlsm Cashflow_Flugzeugpfandbrief_Stress.xlsm
copy LiquiVorschau_Flugzeugpfandbrief.xlsm LiquiVorschau_Flugzeugpfandbrief_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Flugzeugpfandbrief_Stress.xlsm Cashflow_Flugzeugpfandbrief_Stress_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy LiquiVorschau_Flugzeugpfandbrief*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_Flugzeugpfandbrief_Stress*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_5
ren LiquiVorschau_*-*-*PG_*Marktzins*.xlsm LiquiVorschau_OEPG_Oeffentlicher_Pfandbrief.xlsm
ren Cashflow_*-*-*PG_*(Stress)*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Stress.xlsm
copy LiquiVorschau_OEPG_Oeffentlicher_Pfandbrief.xlsm LiquiVorschau_OEPG_Oeffentlicher_Pfandbrief_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Stress.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Stress_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy LiquiVorschau_OEPG_Oeffentlicher_Pfandbrief*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Stress*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM normal mit LZB 0 0 0 0  
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_1.ini -execute=szenarioreport.BW-Berechnung-Hypotheken -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_hypo_0000.log -errlog=%protokolldir%\errlog_prod_hypo_0000.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_2.ini -execute=szenarioreport.BW-Berechnung-Kommunal -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_komm_0000.log -errlog=%protokolldir%\errlog_prod_komm_0000.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_3.ini -execute=szenarioreport.BWSchiff -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_schf_0000.log -errlog=%protokolldir%\errlog_prod_schf_0000.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_4.ini -execute=szenarioreport.BWFlugzeug -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_flug_0000.log -errlog=%protokolldir%\errlog_prod_flug_0000.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_5.ini -execute=szenarioreport.BWOEPGKomm -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_oepg_0000.log -errlog=%protokolldir%\errlog_prod_oepg_0000.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_1
ren Cashflow_*-*-*Hypotheken*Niedrigzins*.xlsm Cashflow_Hypothekenpfandbrief_Niedrigzins.xlsm
ren Cashflow_*-*-*Hypotheken*Hochzins*.xlsm Cashflow_Hypothekenpfandbrief_Hochzins.xlsm
copy Cashflow_Hypothekenpfandbrief_Niedrigzins.xlsm Cashflow_Hypothekenpfandbrief_Niedrigzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm 
copy Cashflow_Hypothekenpfandbrief_Hochzins.xlsm Cashflow_Hypothekenpfandbrief_Hochzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Hypothekenpfandbrief_Niedrigzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_Hypothekenpfandbrief_Hochzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_2
ren Cashflow*-*-*ffentlicher*Niedrigzins*.xlsm Cashflow_Oeffentlicher_Pfandbrief_Niedrigzins.xlsm
ren Cashflow*-*-*ffentlicher*Hochzins*.xlsm Cashflow_Oeffentlicher_Pfandbrief_Hochzins.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Niedrigzins.xlsm Cashflow_Oeffentlicher_Pfandbrief_Niedrigzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Hochzins.xlsm Cashflow_Oeffentlicher_Pfandbrief_Hochzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Niedrigzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_Oeffentlicher_Pfandbrief_Hochzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_3
ren Cashflow_*-*-*Schiff*Niedrigzins*.xlsm Cashflow_Schiffspfandbrief_Niedrigzins.xlsm
ren Cashflow_*-*-*Schiff*Hochzins*.xlsm Cashflow_Schiffspfandbrief_Hochzins.xlsm
copy Cashflow_Schiffspfandbrief_Niedrigzins.xlsm Cashflow_Schiffspfandbrief_Niedrigzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Schiffspfandbrief_Hochzins.xlsm Cashflow_Schiffspfandbrief_Hochzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Schiffspfandbrief_Niedrigzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_Schiffspfandbrief_Hochzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_3\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_4
ren Cashflow_*-*-*Flug*Niedrigzins*.xlsm Cashflow_Flugzeugpfandbrief_Niedrigzins.xlsm
ren Cashflow_*-*-*Flug*Hochzins*.xlsm Cashflow_Flugzeugpfandbrief_Hochzins.xlsm
copy Cashflow_Flugzeugpfandbrief_Niedrigzins.xlsm Cashflow_Flugzeugpfandbrief_Niedrigzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Flugzeugpfandbrief_Hochzins.xlsm Cashflow_Flugzeugpfandbrief_Hochzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Flugzeugpfandbrief_Niedrigzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_Flugzeugpfandbrief_Hochzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow_*-*-*PG_*Niedrigzins*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Niedrigzins.xlsm
ren Cashflow_*-*-*PG_*Hochzins*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Hochzins.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Niedrigzins.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Niedrigzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Hochzins.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Hochzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Niedrigzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Hochzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Kennzahlen für Cockpit SU 20120103
REM SU 20170124 diese müssen *vor* den Grenzwertreports ermittelt werden 
REM SU 20180903 neue Stelle weiter vorne
REM *************************************************************************************************************************************************************************

REM zum Ausführen wieder in das TXS-Verzeichnis
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

start txsjobserver.exe -execute="kennzahlen.Kennzahlen PfandBG" -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_kzp.log -errlog=%protokolldir%\errlog_prod_kzp.log

start txsjobserver.exe -execute="kennzahlen.Kennzahlen Schiffe" -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_kzs.log -errlog=%protokolldir%\errlog_prod_kzs.log

start txsjobserver.exe -execute="kennzahlen.Kennzahlen Flugzeuge" -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_kzf.log -errlog=%protokolldir%\errlog_prod_kzf.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

REM *************************************************************************************************************************************************************************
REM Start Vorgezogene Belieferung an cmc/abacus wg. SSM
REM *************************************************************************************************************************************************************************

rem ----------------------------------------------------------
REM Spiegelung ins Barwertsystem SU 20130503
call F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\_Start_SPIEGELUNG_NLB.cmd
rem ----------------------------------------------------------

REM *************************************************************************************************************************************************************************
REM Ab hier kann im Produktionssystem gearbeitet werden
REM *************************************************************************************************************************************************************************
powershell F:\TXS_009_PROD\PROGRAMM\LB\nlbprodready_mail.ps1

rem ----------------------------------------------------------
rem Einzelbarwertreporting auf dem Barwertsystem ausführen
call F:\TXS_009_REPORTS\CMC\_start_cmc_nlb.bat
rem ----------------------------------------------------------

rem ----------------------------------------------------------
rem Auswertungen für ABACUS auf dem Barwertsystem ausführen
call F:\TXS_009_REPORTS\ABA\_start_aba_nlb.bat
rem ----------------------------------------------------------

REM *************************************************************************************************************************************************************************
REM Ende Vorgezogene Belieferung an cmc/abacus wg. SSM
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM vier Reports Grenzwerte
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver -execute=report.Grenzwerte_SchiPfe
start txsjobserver -execute=report.Grenzwerte_FluPfe

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

start txsjobserver -execute=report.Grenzwerte_OEPG
start txsjobserver -execute=report.Grenzwerte_HyPfe_OEPfe

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

REM in das Reportverzeichnis
cd F:\TXS_009_REPORTS\TXS

ren Grenzwerte*FluPfe* Grenzwerte_FluPfe.xlsm
ren Grenzwerte*HyPfe_OEPfe* Grenzwerte_HyPfe_OEPfe.xlsm
ren Grenzwerte*OEPG* Grenzwerte_OEPG.xlsm
ren Grenzwerte*SchiPfe* Grenzwerte_SchiPfe.xlsm

copy Grenzwerte_FluPfe.xlsm Grenzwerte_FluPfe_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Grenzwerte_HyPfe_OEPfe.xlsm Grenzwerte_HyPfe_OEPfe_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Grenzwerte_OEPG.xlsm Grenzwerte_OEPG_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Grenzwerte_SchiPfe.xlsm Grenzwerte_SchiPfe_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm

REM *************************************************************************************************************************************************************************
REM jetzt der Teil 24M, drei reports für FRC mit Versand hier, fünf reports für QM PF  
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_REPORTS\FRC
del /q F:\TXS_009_REPORTS\FRC\*.*

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_klassisch_24M -basedate=%mybasedate% -applog=%protokolldir%\applog_Hypotheken_klassisch_24M.log -errlog=%protokolldir%\errlog_Hypotheken_klassisch_24M.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_klassisch_24M -basedate=%mybasedate% -applog=%protokolldir%\applog_Kommunal_klassisch_24M.log -errlog=%protokolldir%\errlog_Kommunal_klassisch_24M.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Kommunal_weitere_24M -basedate=%mybasedate% -applog=%protokolldir%\applog_Kommunal_weitere_24M.log -errlog=%protokolldir%\errlog_Kommunal_weitere_24M.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.Flugzeug_klassisch_24M -basedate=%mybasedate% -applog=%protokolldir%\applog_Flugzeug_klassisch_24M.log -errlog=%protokolldir%\errlog_Flugzeug_klassisch_24M.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.Schiff_klassisch_24M -basedate=%mybasedate% -applog=%protokolldir%\applog_Schiff_klassisch_24M.log -errlog=%protokolldir%\errlog_Schiff_klassisch_24M.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

REM *************************************************************************************************************************************************************************
REM Umbenennen der Reports (geht so nur, wenn ein file pro Verzeichnis vorhanden ist), für FRC und QM PF
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_1
ren Cashflow*.xlsm %mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%_Cashflows_Hypothekenpfandbrief_Gattungsklassische_Deckung.xlsm
copy %mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%_Cashflows_Hypothekenpfandbrief_Gattungsklassische_Deckung.xlsm F:\TXS_009_REPORTS\FRC

ren %mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%_Cashflows_Hypothekenpfandbrief_Gattungsklassische_Deckung.xlsm Cashflow_Hypothekenpfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsm
copy Cashflow_Hypothekenpfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsm F:\TXS_009_REPORTS\TXS\P_TEMP

del /q F:\TXS_009_REPORTS\TXS\P_1\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_2

ren Cashflow*.xlsm %mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%_Cashflows_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung.xlsm
copy %mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%_Cashflows_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung.xlsm F:\TXS_009_REPORTS\FRC

ren %mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%_Cashflows_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung.xlsm Cashflow_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsm F:\TXS_009_REPORTS\TXS\P_TEMP

del /q F:\TXS_009_REPORTS\TXS\P_2\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_3

ren Cashflow*.xlsm %mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%_Cashflows_Oeffentlicher_Pfandbrief_Weitere_Deckung.xlsm
copy %mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%_Cashflows_Oeffentlicher_Pfandbrief_Weitere_Deckung.xlsm F:\TXS_009_REPORTS\FRC

ren %mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%_Cashflows_Oeffentlicher_Pfandbrief_Weitere_Deckung.xlsm Cashflow_Oeffentlicher_Pfandbrief_Weitere_Deckung_Marktzins_taeglich.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Weitere_Deckung_Marktzins_taeglich.xlsm F:\TXS_009_REPORTS\TXS\P_TEMP

del /q F:\TXS_009_REPORTS\TXS\P_3\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_4
ren Cashflow*.xlsm Cashflow_Flugzeugpfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsm
copy Cashflow_Flugzeugpfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsm F:\TXS_009_REPORTS\TXS\P_TEMP

del /q F:\TXS_009_REPORTS\TXS\P_4\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow*.xlsm Cashflow_Schiffspfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsm
copy Cashflow_Schiffspfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsm F:\TXS_009_REPORTS\TXS\P_TEMP

del /q F:\TXS_009_REPORTS\TXS\P_5\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Kopieren der drei FRC-Reports  
REM *************************************************************************************************************************************************************************

net use * /delete /y

net use J: \\kbk.NordLB.local\programme\frcrc\FRCRC_RTSY_Liquidity\LST\TXS_Reports Edrpwd100 /user:kbk\txstrv01

copy F:\TXS_009_REPORTS\FRC\*.* J:\

net use J: /delete /y

REM *************************************************************************************************************************************************************************
REM Erledigtmeldung  
REM *************************************************************************************************************************************************************************

powershell F:\TXS_009_PROD\PROGRAMM\LB\nlbFRCrepready_mail.ps1

REM *************************************************************************************************************************************************************************
REM normal mit LZB 0 0 0 400  
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_000400 -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_hypo_000400.log -errlog=%protokolldir%\errlog_prod_hypo_000400.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_000400 -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_komm_000400.log -errlog=%protokolldir%\errlog_prod_komm_000400.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_000400 -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_schf_000400.log -errlog=%protokolldir%\errlog_prod_schf_000400.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.Flugzeug_000400 -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_flug_000400.log -errlog=%protokolldir%\errlog_prod_flug_000400.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.OEPG_000400 -basedate=%mybasedate% -applog=%protokolldir%\applog_prod_oepg_000400.log -errlog=%protokolldir%\errlog_prod_oepg_000400.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_1
ren Cashflow_*-*-*Hypotheken*Marktzins*.xlsm Cashflow_Hypothekenpfandbrief_Marktzins.xlsm
copy Cashflow_Hypothekenpfandbrief_Marktzins.xlsm Cashflow_Hypothekenpfandbrief_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Hypothekenpfandbrief_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_2
ren Cashflow*-*-*ffentlicher*Marktzins*.xlsm Cashflow_Oeffentlicher_Pfandbrief_Marktzins.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Marktzins.xlsm Cashflow_Oeffentlicher_Pfandbrief_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_3
ren Cashflow_*-*-*Schiff*Marktzins*.xlsm Cashflow_Schiffspfandbrief_Marktzins.xlsm
copy Cashflow_Schiffspfandbrief_Marktzins.xlsm Cashflow_Schiffspfandbrief_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Schiffspfandbrief_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_3\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_4
ren Cashflow_*-*-*Flug*Marktzins*.xlsm Cashflow_Flugzeugpfandbrief_Marktzins.xlsm
copy Cashflow_Flugzeugpfandbrief_Marktzins.xlsm Cashflow_Flugzeugpfandbrief_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Flugzeugpfandbrief_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow_*-*-*PG_*Marktzins*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Marktzins.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Marktzins.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Gattungsklassische Deckung
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Gattungsklassische_Deckung -basedate=%mybasedate% -applog=%protokolldir%\Hypotheken_Gattungsklassische_Deckung.log -errlog=%protokolldir%\err_Hypotheken_Gattungsklassische_Deckung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_Gattungsklassische_Deckung -basedate=%mybasedate% -applog=%protokolldir%\Kommunal_Gattungsklassische_Deckung.log -errlog=%protokolldir%\err_Kommunal_Gattungsklassische_Deckung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Gattungsklassische_Deckung -basedate=%mybasedate% -applog=%protokolldir%\Schiff_Gattungsklassische_Deckung.log -errlog=%protokolldir%\err_Schiff_Gattungsklassische_Deckung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.Flugzeug_Gattungsklassische_Deckung -basedate=%mybasedate% -applog=%protokolldir%\Flugzeug_Gattungsklassische_Deckung.log -errlog=%protokolldir%\err_Flugzeug_Gattungsklassische_Deckung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.OEPG_Kommunal_Gattungsklassische_Deckung -basedate=%mybasedate% -applog=%protokolldir%\OEPG_Kommunal_Gattungsklassische_Deckung.log -errlog=%protokolldir%\err_OEPG_Kommunal_Gattungsklassische_Deckung.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_1
ren Cashflow_*_NLB-PfandBG_Hypothekenpfandbrief_Marktzins_Fest*.xlsm Cashflow_Hypothekenpfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm
copy Cashflow_Hypothekenpfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm Cashflow_Hypothekenpfandbrief_Gattungsklassische_Deckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Hypothekenpfandbrief_Gattungsklassische_Deckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_2
ren Cashflow_*_NLB-PfandBG_*ffentlicher*.xlsm Cashflow_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm Cashflow_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_3
ren Cashflow_*_Schiffspfandbrief_Schiffspfandbrief_Marktzins_Fest*.xlsm Cashflow_Schiffspfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm
copy Cashflow_Schiffspfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm Cashflow_Schiffspfandbrief_Gattungsklassische_Deckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Schiffspfandbrief_Gattungsklassische_Deckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_3\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_4
ren Cashflow_*_Flugzeugpfandbrief_Flugzeugpfandbrief_Marktzins_Fest*.xlsm Cashflow_Flugzeugpfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm
copy Cashflow_Flugzeugpfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm Cashflow_Flugzeugpfandbrief_Gattungsklassische_Deckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Flugzeugpfandbrief_Gattungsklassische_Deckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow_*_*PG_*ffentlicher*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Sichernde Überdeckung
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Sichernde_Ueberdeckung -basedate=%mybasedate% -applog=%protokolldir%\Hypotheken_Sichernde_Ueberdeckung.log -errlog=%protokolldir%\err_Hypotheken_Sichernde_Ueberdeckung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_Sichernde_Ueberdeckung -basedate=%mybasedate% -applog=%protokolldir%\Kommunal_Sichernde_Ueberdeckung.log -errlog=%protokolldir%\err_Kommunal_Sichernde_Ueberdeckung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Sichernde_Ueberdeckung -basedate=%mybasedate% -applog=%protokolldir%\Schiff_Sichernde_Ueberdeckung.log -errlog=%protokolldir%\err_Schiff_Sichernde_Ueberdeckung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.Flugzeug_Sichernde_Ueberdeckung -basedate=%mybasedate% -applog=%protokolldir%\Flugzeug_Sichernde_Ueberdeckung.log -errlog=%protokolldir%\err_Flugzeug_Sichernde_Ueberdeckung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.OEPG_Kommunal_Sichernde_Ueberdeckung -basedate=%mybasedate% -applog=%protokolldir%\OEPG_Kommunal_Sichernde_Ueberdeckung.log -errlog=%protokolldir%\err_OEPG_Kommunal_Sichernde_Ueberdeckung.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_1
ren Cashflow_*_NLB-PfandBG_Hypothekenpfandbrief_Marktzins_Fest*.xlsm Cashflow_Hypothekenpfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm
copy Cashflow_Hypothekenpfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm Cashflow_Hypothekenpfandbrief_Sichernde_Ueberdeckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Hypothekenpfandbrief_Sichernde_Ueberdeckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_1\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_2
ren Cashflow_*_NLB-PfandBG_*ffentlicher*.xlsm Cashflow_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm Cashflow_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_2\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_3
ren Cashflow_*_Schiffspfandbrief_Schiffspfandbrief_Marktzins_Fest*.xlsm Cashflow_Schiffspfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm
copy Cashflow_Schiffspfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm Cashflow_Schiffspfandbrief_Sichernde_Ueberdeckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Schiffspfandbrief_Sichernde_Ueberdeckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_3\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_4
ren Cashflow_*_Flugzeugpfandbrief_Flugzeugpfandbrief_Marktzins_Fest*.xlsm Cashflow_Flugzeugpfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm
copy Cashflow_Flugzeugpfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm Cashflow_Flugzeugpfandbrief_Sichernde_Ueberdeckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Flugzeugpfandbrief_Sichernde_Ueberdeckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_4\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow_*_*PG_*ffentlicher*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Weitere Deckung I
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Weitere_Deckung_I -basedate=%mybasedate% -applog=%protokolldir%\Hypotheken_Weitere_Deckung_I.log -errlog=%protokolldir%\err_Hypotheken_Weitere_Deckung_I.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_Weitere_Deckung -basedate=%mybasedate% -applog=%protokolldir%\Kommunal_Weitere_Deckung.log -errlog=%protokolldir%\err_Kommunal_Weitere_Deckung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Weitere_Deckung_I -basedate=%mybasedate% -applog=%protokolldir%\Schiff_Weitere_Deckung_I.log -errlog=%protokolldir%\err_Schiff_Weitere_Deckung_I.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.Flugzeug_Weitere_Deckung_I -basedate=%mybasedate% -applog=%protokolldir%\Flugzeug_Weitere_Deckung_I.log -errlog=%protokolldir%\err_Flugzeug_Weitere_Deckung_I.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_1
ren Cashflow_*_NLB-PfandBG_Hypothekenpfandbrief_Marktzins_Fest*.xlsm Cashflow_Hypothekenpfandbrief_Weitere_Deckung_I_Marktzins.xlsm
copy Cashflow_Hypothekenpfandbrief_Weitere_Deckung_I_Marktzins.xlsm Cashflow_Hypothekenpfandbrief_Weitere_Deckung_I_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Hypothekenpfandbrief_Weitere_Deckung_I_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_1\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_2
ren Cashflow_*_NLB-PfandBG_*ffentlicher*.xlsm Cashflow_Oeffentlicher_Pfandbrief_Weitere_Deckung_Marktzins.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Weitere_Deckung_Marktzins.xlsm Cashflow_Oeffentlicher_Pfandbrief_Weitere_Deckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Weitere_Deckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_2\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_3
ren Cashflow_*_Schiffspfandbrief_Schiffspfandbrief_Marktzins_Fest*.xlsm Cashflow_Schiffspfandbrief_Weitere_Deckung_I_Marktzins.xlsm
copy Cashflow_Schiffspfandbrief_Weitere_Deckung_I_Marktzins.xlsm Cashflow_Schiffspfandbrief_Weitere_Deckung_I_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Schiffspfandbrief_Weitere_Deckung_I_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_3\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_4
ren Cashflow_*_Flugzeugpfandbrief_Flugzeugpfandbrief_Marktzins_Fest*.xlsm Cashflow_Flugzeugpfandbrief_Weitere_Deckung_I_Marktzins.xlsm
copy Cashflow_Flugzeugpfandbrief_Weitere_Deckung_I_Marktzins.xlsm Cashflow_Flugzeugpfandbrief_Weitere_Deckung_I_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Flugzeugpfandbrief_Weitere_Deckung_I_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_4\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Weitere Deckung II
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Weitere_Deckung_II -basedate=%mybasedate% -applog=%protokolldir%\Hypotheken_Weitere_Deckung_II.log -errlog=%protokolldir%\err_Hypotheken_Weitere_Deckung_II.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Weitere_Deckung_II -basedate=%mybasedate% -applog=%protokolldir%\Schiff_Weitere_Deckung_II.log -errlog=%protokolldir%\err_Schiff_Weitere_Deckung_II.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.Flugzeug_Weitere_Deckung_II -basedate=%mybasedate% -applog=%protokolldir%\Flugzeug_Weitere_Deckung_II.log -errlog=%protokolldir%\err_Flugzeug_Weitere_Deckung_II.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
 
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_1
ren Cashflow_*_NLB-PfandBG_Hypothekenpfandbrief_Marktzins_Fest*.xlsm Cashflow_Hypothekenpfandbrief_Weitere_Deckung_II_Marktzins.xlsm
copy Cashflow_Hypothekenpfandbrief_Weitere_Deckung_II_Marktzins.xlsm Cashflow_Hypothekenpfandbrief_Weitere_Deckung_II_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Hypothekenpfandbrief_Weitere_Deckung_II_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_1\*.* 
REM *************************************************************************************************************************************************************************
 
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_3
ren Cashflow_*_Schiffspfandbrief_Schiffspfandbrief_Marktzins_Fest*.xlsm Cashflow_Schiffspfandbrief_Weitere_Deckung_II_Marktzins.xlsm
copy Cashflow_Schiffspfandbrief_Weitere_Deckung_II_Marktzins.xlsm Cashflow_Schiffspfandbrief_Weitere_Deckung_II_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Schiffspfandbrief_Weitere_Deckung_II_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_2\*.* 
REM *************************************************************************************************************************************************************************
 
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_4
ren Cashflow_*_Flugzeugpfandbrief_Flugzeugpfandbrief_Marktzins_Fest*.xlsm Cashflow_Flugzeugpfandbrief_Weitere_Deckung_II_Marktzins.xlsm
copy Cashflow_Flugzeugpfandbrief_Weitere_Deckung_II_Marktzins.xlsm Cashflow_Flugzeugpfandbrief_Weitere_Deckung_II_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Flugzeugpfandbrief_Weitere_Deckung_II_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_3\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Liquiditätssicherung
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Liquiditaetssicherung -basedate=%mybasedate% -applog=%protokolldir%\Hypotheken_Liquiditaetssicherung.log -errlog=%protokolldir%\err_Hypotheken_Liquiditaetssicherung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_Liquiditaetssicherung -basedate=%mybasedate% -applog=%protokolldir%\Kommunal_Liquiditaetssicherung.log -errlog=%protokolldir%\err_Kommunal_Liquiditaetssicherung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Liquiditaetssicherung -basedate=%mybasedate% -applog=%protokolldir%\Schiff_Liquiditaetssicherung.log -errlog=%protokolldir%\err_Schiff_Liquiditaetssicherung.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.Flugzeug_Liquiditaetssicherung -basedate=%mybasedate% -applog=%protokolldir%\Flugzeug_Liquiditaetssicherung.log -errlog=%protokolldir%\err_Flugzeug_Liquiditaetssicherung.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
 
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_1
ren Cashflow_*_NLB-PfandBG_Hypothekenpfandbrief_Marktzins_Fest*.xlsm Cashflow_Hypothekenpfandbrief_Liquiditaetssicherung_Marktzins.xlsm
copy Cashflow_Hypothekenpfandbrief_Liquiditaetssicherung_Marktzins.xlsm Cashflow_Hypothekenpfandbrief_Liquiditaetssicherung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Hypothekenpfandbrief_Liquiditaetssicherung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_1\*.* 
REM *************************************************************************************************************************************************************************
 
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_2
ren Cashflow_*_NLB-PfandBG_*ffentlicher*.xlsm Cashflow_Oeffentlicher_Pfandbrief_Liquiditaetssicherung_Marktzins.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Liquiditaetssicherung_Marktzins.xlsm Cashflow_Oeffentlicher_Pfandbrief_Liquiditaetssicherung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Liquiditaetssicherung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_2\*.* 
REM *************************************************************************************************************************************************************************
 
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_3
ren Cashflow_*_Schiffspfandbrief_Schiffspfandbrief_Marktzins_Fest*.xlsm Cashflow_Schiffspfandbrief_Liquiditaetssicherung_Marktzins.xlsm
copy Cashflow_Schiffspfandbrief_Liquiditaetssicherung_Marktzins.xlsm Cashflow_Schiffspfandbrief_Liquiditaetssicherung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Schiffspfandbrief_Liquiditaetssicherung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_3\*.* 
REM *************************************************************************************************************************************************************************
 
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_4
ren Cashflow_*_Flugzeugpfandbrief_Flugzeugpfandbrief_Marktzins_Fest*.xlsm Cashflow_Flugzeugpfandbrief_Liquiditaetssicherung_Marktzins.xlsm
copy Cashflow_Flugzeugpfandbrief_Liquiditaetssicherung_Marktzins.xlsm Cashflow_Flugzeugpfandbrief_Liquiditaetssicherung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Flugzeugpfandbrief_Liquiditaetssicherung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_4\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Einsammeln der Ergebnisse und Umwandlung nach .xlsx
REM *************************************************************************************************************************************************************************

powershell F:\TXS_009_PROD\PROGRAMM\LB\neu_xlsm2xlsx.ps1

copy F:\TXS_009_REPORTS\TXS\P_TEMP\Liqui*.* F:\TXS_009_REPORTS\TXS\ 

REM *************************************************************************************************************************************************************************
REM vier Reports Ausnutzung Limite
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver -execute=report.AusLimP13

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

start txsjobserver -execute=report.AusLimP20

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

start txsjobserver -execute=report.AusLimP22
start txsjobserver -execute=report.AusLimP26b

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

cd F:\TXS_009_REPORTS\TXS

ren AusLimP13* Ausnutzung_Limitierung_13_1.xlsm
ren AusLimP20* Ausnutzung_Limitierung_20_2a.xlsm
ren AusLimP22* Ausnutzung_Limitierung_22_5.xlsm
ren AusLimP26b* Ausnutzung_Limitierung_26b_4.xlsm

copy Ausnutzung_Limitierung_13_1.xlsm Ausnutzung_Limitierung_13_1_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Ausnutzung_Limitierung_20_2a.xlsm Ausnutzung_Limitierung_20_2a_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Ausnutzung_Limitierung_22_5.xlsm Ausnutzung_Limitierung_22_5_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Ausnutzung_Limitierung_26b_4.xlsm Ausnutzung_Limitierung_26b_4_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm

REM *************************************************************************************************************************************************************************
REM Sichernde Übersicherung Liquiditätssicherung
REM neu per 201809
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -execute=report.SiUeLi_HyPfe_OEPfe
start txsjobserver.exe -execute=report.SiUeLi_OEPG

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

start txsjobserver -execute=report.SiUeLi_SchiPfe 
start txsjobserver.exe -execute=report.SiUeLi_FluPfe 

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
 
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS
ren Sichernde_Ueberdeckung_Liquiditaetssicherung_HyPfe_OEPfe*.xlsm Sichernde_Ueberdeckung_Liquiditaetssicherung_HyPfe_OEPfe.xlsm
REM *************************************************************************************************************************************************************************
 
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS
ren Sichernde_Ueberdeckung_Liquiditaetssicherung_OPEG*.xlsm Sichernde_Ueberdeckung_Liquiditaetssicherung_OEPG.xlsm
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS
ren Sichernde_Ueberdeckung_Liquiditaetssicherung_SchiPfe*.xlsm Sichernde_Ueberdeckung_Liquiditaetssicherung_SchiPfe.xlsm
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS
ren Sichernde_Ueberdeckung_Liquiditaetssicherung_FluPfe*.xlsm Sichernde_Ueberdeckung_Liquiditaetssicherung_FluPfe.xlsm
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM verpacken aller reports aus F:\TXS_009_REPORTS\TXS
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM zunächst die Dateien ohne daypointer nach ReportTXS_009_taeglich.zip
REM *************************************************************************************************************************************************************************

REM in das Reportverzeichnis
cd F:\TXS_009_REPORTS\TXS

zip -m -9 ReportTXS_009_taeglich.zip Cashflow_Hypothekenpfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsx 
zip -m -9 ReportTXS_009_taeglich.zip Cashflow_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsx
zip -m -9 ReportTXS_009_taeglich.zip Cashflow_Oeffentlicher_Pfandbrief_Weitere_Deckung_Marktzins_taeglich.xlsx 
zip -m -9 ReportTXS_009_taeglich.zip Cashflow_Flugzeugpfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsx
zip -m -9 ReportTXS_009_taeglich.zip Cashflow_Schiffspfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsx

zip -m -9 ReportTXS_009_taeglich.zip *zins.xl*
zip -m -9 ReportTXS_009_taeglich.zip *brief.xl*
zip -m -9 ReportTXS_009_taeglich.zip *tress.xl*

REM Grenzwerte
zip -m -9 ReportTXS_009_taeglich.zip Grenzwerte_FluPfe.xlsm
zip -m -9 ReportTXS_009_taeglich.zip Grenzwerte_HyPfe_OEPfe.xlsm
zip -m -9 ReportTXS_009_taeglich.zip Grenzwerte_OEPG.xlsm
zip -m -9 ReportTXS_009_taeglich.zip Grenzwerte_SchiPfe.xlsm

REM Ausnutzung 
zip -m -9 ReportTXS_009_taeglich.zip Ausnutzung_Limitierung_13_1.xlsm
zip -m -9 ReportTXS_009_taeglich.zip Ausnutzung_Limitierung_20_2a.xlsm
zip -m -9 ReportTXS_009_taeglich.zip Ausnutzung_Limitierung_22_5.xlsm
zip -m -9 ReportTXS_009_taeglich.zip Ausnutzung_Limitierung_26b_4.xlsm

REM Sichernde Übersicherung Liquiditätssicherung
zip -m -9 ReportTXS_009_taeglich.zip Sichernde_Ueberdeckung_Liquiditaetssicherung_HyPfe_OEPfe.xlsm
zip -m -9 ReportTXS_009_taeglich.zip Sichernde_Ueberdeckung_Liquiditaetssicherung_OEPG.xlsm
zip -m -9 ReportTXS_009_taeglich.zip Sichernde_Ueberdeckung_Liquiditaetssicherung_SchiPfe.xlsm
zip -m -9 ReportTXS_009_taeglich.zip Sichernde_Ueberdeckung_Liquiditaetssicherung_FluPfe.xlsm

REM *************************************************************************************************************************************************************************
REM diese sind damit komplett 
REM zusätzlich die Beleihungswerte
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver -execute=report.BelWertFlug
start txsjobserver -execute=report.BelWertSchiff

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

rem nun die restlichen Dateien - dies sind die mit daypointer - nach ReportTXS_009_%date:~6,4%%date:~3,2%%date:~0,2%.zip
cd F:\TXS_009_REPORTS\TXS

zip -m -9 ReportTXS_009_%date:~6,4%%date:~3,2%%date:~0,2%.zip *.xl*

rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Kennzahlen_Reports.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini txs-m-nlb@nordlb.de BetriebTXS@nordlb.de F:\PROST\Mail_Fachbereich.txt %%A %%B %%C %%D
)
