REM *************************************************************************************************************************************************************************
REM _Start_Kennzahlen_Reports_PROD_NLB.cmd
REM Part 1: TXS Reporterstellung - nur DNLBPROD - hier
REM Nachfolger
REM Part 2: Spiegelung der Produktion in die Barwert-DB - _Start_SPIEGELUNG_NLB.cmd
REM SPA 20190704 Redesign 
REM SPA 20190802 Outputdateien standardisieren und mit CheckRepLog versehen
REM SPA 20200108 Flugzeuge raus
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Aufräumen
REM *************************************************************************************************************************************************************************
@echo on
cls

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set statusdir=F:\TXS_009_PROD\PROTOKOLL\Reports\KENNZAHLENREPORTPROD.txt

echo REM Start KennzahlenReports_PROD %mybasedate% am %date% %time% >> %statusdir%

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
REM Namenskonvention Teil 1: SKRP fuer StartKennzahlenReportPROD fuer alle
REM Namenskonvention Teil 2: P, A, T fuer Produktion, Abnahme, Test
REM Namenskonvention Teil 3: Bnn  Nummer je Aufrufblock Zaehler faengt bei 01 an
REM Namenskonvention Teil 4: FL, HY, KO, OE, SH fuer die Transaktionen oder PF fuer Projekt Pfand
REM Namenskonvention Teil 5: AL, EL, PL fuer applog, errlog, perflog
REM Beschreibung in der Ueberschrift, aber nicht mehr im Namen 
REM *************************************************************************************************************************************************************************
REM Stress => SKRP_P_B01 fuer alle hier
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_1.ini -execute=szenarioreport.BW-Berechnung-Hypotheken-Stress -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B01_HY_AL.log -errlog=%protokolldir%\SKRP_P_B01_HY_EL.log -perflog=%protokolldir%\SKRP_P_B01_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_2.ini -execute=szenarioreport.BW-Berechnung-Kommunal-Stress -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B01_KO_AL.log -errlog=%protokolldir%\SKRP_P_B01_KO_EL.log -perflog=%protokolldir%\SKRP_P_B01_KO_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_3.ini -execute=szenarioreport.BWSchiffStress -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B01_SH_AL.log -errlog=%protokolldir%\SKRP_P_B01_SH_EL.log -perflog=%protokolldir%\SKRP_P_B01_SH_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_5.ini -execute=szenarioreport.BWOEPGKommStress -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B01_OE_AL.log -errlog=%protokolldir%\SKRP_P_B01_OE_EL.log -perflog=%protokolldir%\SKRP_P_B01_OE_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B01_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B01_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B01_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B01_OE_AL.log

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
cd F:\TXS_009_REPORTS\TXS\P_5
ren LiquiVorschau_*-*-*PG_*Marktzins*.xlsm LiquiVorschau_OEPG_Oeffentlicher_Pfandbrief.xlsm
ren Cashflow_*-*-*PG_*(Stress)*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Stress.xlsm
copy LiquiVorschau_OEPG_Oeffentlicher_Pfandbrief.xlsm LiquiVorschau_OEPG_Oeffentlicher_Pfandbrief_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Stress.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Stress_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy LiquiVorschau_OEPG_Oeffentlicher_Pfandbrief*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Stress*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*
REM *************************************************************************************************************************************************************************

echo REM Ende Stress_ %date% %time% >> %statusdir%

REM *************************************************************************************************************************************************************************
REM normal mit LZB 0 0 0 0  => SKRP_P_B02 fuer alle hier
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_1.ini -execute=szenarioreport.BW-Berechnung-Hypotheken -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B02_HY_AL.log -errlog=%protokolldir%\SKRP_P_B02_HY_EL.log -perflog=%protokolldir%\SKRP_P_B02_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_2.ini -execute=szenarioreport.BW-Berechnung-Kommunal -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B02_KO_AL.log -errlog=%protokolldir%\SKRP_P_B02_KO_EL.log -perflog=%protokolldir%\SKRP_P_B02_KO_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_3.ini -execute=szenarioreport.BWSchiff -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B02_SH_AL.log -errlog=%protokolldir%\SKRP_P_B02_SH_EL.log -perflog=%protokolldir%\SKRP_P_B02_SH_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\TXS_p_5.ini -execute=szenarioreport.BWOEPGKomm -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B02_OE_AL.log -errlog=%protokolldir%\SKRP_P_B02_OE_EL.log -perflog=%protokolldir%\SKRP_P_B02_OE_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B02_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B02_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B02_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B02_OE_AL.log

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
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow_*-*-*PG_*Niedrigzins*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Niedrigzins.xlsm
ren Cashflow_*-*-*PG_*Hochzins*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Hochzins.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Niedrigzins.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Niedrigzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Hochzins.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Hochzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Niedrigzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Hochzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*
REM *************************************************************************************************************************************************************************

echo REM Ende normal_mit_LZB_0_0_0_0_ %date% %time% >> %statusdir%

REM *************************************************************************************************************************************************************************
REM Kennzahlen für Cockpit muessen vor den Grenzwertreports laufen => SKRP_P_B03 fuer alle hier
REM *************************************************************************************************************************************************************************

REM zum Ausführen wieder in das TXS-Verzeichnis
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

start txsjobserver.exe -execute="kennzahlen.Kennzahlen PfandBG" -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B03_PF_AL.log -errlog=%protokolldir%\SKRP_P_B03_PF_EL.log -perflog=%protokolldir%\SKRP_P_B03_PF_PL.log

start txsjobserver.exe -execute="kennzahlen.Kennzahlen Schiffe" -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B03_SH_AL.log -errlog=%protokolldir%\SKRP_P_B03_SH_EL.log -perflog=%protokolldir%\SKRP_P_B03_SH_PL.log

start txsjobserver.exe -execute="kennzahlen.Kennzahlen Flugzeuge" -basedate=%mybasedate% -applog=%protokolldir%\SKRP_P_B03_FL_AL.log -errlog=%protokolldir%\SKRP_P_B03_FL_EL.log -perflog=%protokolldir%\SKRP_P_B03_FL_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B03_PF_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B03_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRP_P_B03_FL_AL.log

echo REM Ende Kennzahlen_für_Cockpit_ %date% %time% >> %statusdir%
echo REM Ende KennzahlenReports_PROD %mybasedate% am %date% %time% >> %statusdir%
echo REM ________________________________________________________________ >> %statusdir%


REM *************************************************************************************************************************************************************************
rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Kennzahlen_Reports_PROD_NLB.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)
REM ************************************************************************************************************************************************************************* 