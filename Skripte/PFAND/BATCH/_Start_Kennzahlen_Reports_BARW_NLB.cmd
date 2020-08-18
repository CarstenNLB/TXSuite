REM *************************************************************************************************************************************************************************
REM _Start_Kennnzahlen_Reports_BARW_NLB.cmd
REM Part 5: TXS Reporterstellung - nur DNLBBARW
REM Vorlaeufer
REM Part 4: CMC und ABACUS beliefern
REM -----4.1 _start_cmc_nlb.bat
REM -----4.2 _start_aba_nlb.bat
REM Nachfolger
REM Part 6: _Start_copy_reports.bat
REM SPA 20190704 Redesign  
REM SPA 20200108 Flugzeuge raus
REM SPA 20200506 basedate vervollständigt B01/B10/B11
REM *************************************************************************************************************************************************************************
@echo on
cls

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set statusdir=F:\TXS_009_PROD\PROTOKOLL\Reports\KENNZAHLENREPORTBARW.txt

echo REM Start KennzahlenReports_BARW %mybasedate% am %date% %time% >> %statusdir%

REM *************************************************************************************************************************************************************************
REM Namenskonvention Teil 1: SKRB fuer StartKennzahlenReportBarw fuer alle
REM Namenskonvention Teil 2: P, A, T fuer Produktion, Abnahme, Test
REM Namenskonvention Teil 3: Bnn  Nummer je Aufrufblock Zaehler faengt bei 01 an
REM Namenskonvention Teil 4: FL, HY, KO, OE, SH fuer die Transaktionen oder PF fuer Projekt Pfand
REM Namenskonvention Teil 5: AL, EL, PL fuer applog, errlog, perflog
REM Beschreibung in der Ueberschrift, aber nicht mehr im Namen 
REM *************************************************************************************************************************************************************************
REM Vier Reports Grenzwerte => SKRB_P_B01 fuer alle hier
REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -execute=report.Grenzwerte_SchiPfe -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B01_SH_AL.log -errlog=%protokolldir%\SKRB_P_B01_SH_EL.log -perflog=%protokolldir%\SKRB_P_B01_SH_PL.log
start txsjobserver.exe -execute=report.Grenzwerte_FluPfe -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B01_FL_AL.log -errlog=%protokolldir%\SKRB_P_B01_FL_EL.log -perflog=%protokolldir%\SKRB_P_B01_FL_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

start txsjobserver.exe -execute=report.Grenzwerte_OEPG -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B01_OE_AL.log -errlog=%protokolldir%\SKRB_P_B01_OE_EL.log -perflog=%protokolldir%\SKRB_P_B01_OE_PL.log
start txsjobserver.exe -execute=report.Grenzwerte_HyPfe_OEPfe -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B01_PF_AL.log -errlog=%protokolldir%\SKRB_P_B01_PF_EL.log -perflog=%protokolldir%\SKRB_P_B01_PF_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B01_PF_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B01_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B01_FL_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B01_OE_AL.log

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

echo REM Ende Grenzwerte_ %date% %time% >> %statusdir%

REM *************************************************************************************************************************************************************************
REM jetzt der Teil 24M, drei reports für FRC, fünf reports für QM PF   
REm => SKRB_P_B02 fuer alle hier
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_REPORTS\FRC
del /q F:\TXS_009_REPORTS\FRC\*.*

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_klassisch_24M -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B02_HY_AL.log -errlog=%protokolldir%\SKRB_P_B02_HY_EL.log -perflog=%protokolldir%\SKRB_P_B02_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_klassisch_24M -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B02_KK_AL.log -errlog=%protokolldir%\SKRB_P_B02_KK_EL.log -perflog=%protokolldir%\SKRB_P_B02_KK_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Kommunal_weitere_24M -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B02_KW_AL.log -errlog=%protokolldir%\SKRB_P_B02_KW_EL.log -perflog=%protokolldir%\SKRB_P_B02_KW_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.Schiff_klassisch_24M -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B02_SH_AL.log -errlog=%protokolldir%\SKRB_P_B02_SH_EL.log -perflog=%protokolldir%\SKRB_P_B02_SH_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B02_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B02_KK_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B02_KW_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B02_SH_AL.log

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
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow*.xlsm Cashflow_Schiffspfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsm
copy Cashflow_Schiffspfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsm F:\TXS_009_REPORTS\TXS\P_TEMP

del /q F:\TXS_009_REPORTS\TXS\P_5\*.*
REM *************************************************************************************************************************************************************************
echo REM Ende FRC_Reports_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM normal mit LZB 0 0 0 400  => SKRB_P_B03 fuer alle hier
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_000400 -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B03_HY_AL.log -errlog=%protokolldir%\SKRB_P_B03_HY_EL.log -perflog=%protokolldir%\SKRB_P_B03_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_000400 -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B03_KO_AL.log -errlog=%protokolldir%\SKRB_P_B03_KO_EL.log -perflog=%protokolldir%\SKRB_P_B03_KO_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_000400 -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B03_SH_AL.log -errlog=%protokolldir%\SKRB_P_B03_SH_EL.log -perflog=%protokolldir%\SKRB_P_B03_SH_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.OEPG_000400 -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B03_OE_AL.log -errlog=%protokolldir%\SKRB_P_B03_OE_EL.log -perflog=%protokolldir%\SKRB_P_B03_OE_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B03_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B03_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B03_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B03_OE_AL.log

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
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow_*-*-*PG_*Marktzins*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Marktzins.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Marktzins.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*
REM *************************************************************************************************************************************************************************
echo REM Ende normal_mit_LZB_0_0_0_400_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM Gattungsklassische Deckung => SKRB_P_B04 fuer alle hier
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Gattungsklassische_Deckung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B04_HY_AL.log -errlog=%protokolldir%\SKRB_P_B04_HY_EL.log -perflog=%protokolldir%\SKRB_P_B04_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_Gattungsklassische_Deckung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B04_KO_AL.log -errlog=%protokolldir%\SKRB_P_B04_KO_EL.log -perflog=%protokolldir%\SKRB_P_B04_KO_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Gattungsklassische_Deckung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B04_SH_AL.log -errlog=%protokolldir%\SKRB_P_B04_SH_EL.log -perflog=%protokolldir%\SKRB_P_B04_SH_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.OEPG_Kommunal_Gattungsklassische_Deckung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B04_OE_AL.log -errlog=%protokolldir%\SKRB_P_B04_OE_EL.log -perflog=%protokolldir%\SKRB_P_B04_OE_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B04_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B04_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B04_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B04_OE_AL.log

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
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow_*_*PG_*ffentlicher*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Gattungsklassische_Deckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.* 
REM *************************************************************************************************************************************************************************
echo REM Ende Gattungsklassische_Deckung_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM Sichernde Überdeckung => SKRB_P_B05 fuer alle hier
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Sichernde_Ueberdeckung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B05_HY_AL.log -errlog=%protokolldir%\SKRB_P_B05_HY_EL.log -perflog=%protokolldir%\SKRB_P_B05_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_Sichernde_Ueberdeckung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B05_KO_AL.log -errlog=%protokolldir%\SKRB_P_B05_KO_EL.log -perflog=%protokolldir%\SKRB_P_B05_KO_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Sichernde_Ueberdeckung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B05_SH_AL.log -errlog=%protokolldir%\SKRB_P_B05_SH_EL.log -perflog=%protokolldir%\SKRB_P_B05_SH_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.OEPG_Kommunal_Sichernde_Ueberdeckung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B05_OE_AL.log -errlog=%protokolldir%\SKRB_P_B05_OE_EL.log -perflog=%protokolldir%\SKRB_P_B05_OE_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B05_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B05_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B05_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B05_OE_AL.log

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
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow_*_*PG_*ffentlicher*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.* 
REM *************************************************************************************************************************************************************************
echo REM Ende Sichernde_Überdeckung_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM Sichernde Überdeckung 24M - Reports nur in den Ordner Taeglich .. keine Kopie mit Datum... SKRB_P_B06 fuer alle hier
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Sichernde_Ueb24M -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B06_HY_AL.log -errlog=%protokolldir%\SKRB_P_B06_HY_EL.log -perflog=%protokolldir%\SKRB_P_B06_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_Sichernde_Ueb24M -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B06_KO_AL.log -errlog=%protokolldir%\SKRB_P_B06_KO_EL.log -perflog=%protokolldir%\SKRB_P_B06_KO_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Sichernde_Ueb24M -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B06_SH_AL.log -errlog=%protokolldir%\SKRB_P_B06_SH_EL.log -perflog=%protokolldir%\SKRB_P_B06_SH_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.OEPG_Kommunal_Sichernde_Ueb24M -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B06_OE_AL.log -errlog=%protokolldir%\SKRB_P_B06_OE_EL.log -perflog=%protokolldir%\SKRB_P_B06_OE_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B06_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B06_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B06_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B06_OE_AL.log

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_1
ren Cashflow_*_NLB-PfandBG_Hypothekenpfandbrief_Marktzins_Fest*.xlsm Cashflow_Hypothekenpfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsm
rem copy Cashflow_Hypothekenpfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsm Cashflow_Hypothekenpfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Hypothekenpfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_1\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_2
ren Cashflow_*_NLB-PfandBG_*ffentlicher*.xlsm Cashflow_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsm
rem copy Cashflow_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsm Cashflow_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_2\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_3
ren Cashflow_*_Schiffspfandbrief_Schiffspfandbrief_Marktzins_Fest*.xlsm Cashflow_Schiffspfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsm
rem copy Cashflow_Schiffspfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsm Cashflow_Schiffspfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_Schiffspfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_3\*.* 
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\TXS\P_5
ren Cashflow_*_*PG_*ffentlicher*.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsm
rem copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsm Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich*.* F:\TXS_009_REPORTS\TXS\P_TEMP
del /q F:\TXS_009_REPORTS\TXS\P_5\*.* 
REM *************************************************************************************************************************************************************************
echo REM Ende Sichernde_Überdeckung_24M_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM Weitere Deckung I SKRB_P_B07 fuer alle hier
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Weitere_Deckung_I -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B07_HY_AL.log -errlog=%protokolldir%\SKRB_P_B07_HY_EL.log -perflog=%protokolldir%\SKRB_P_B07_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_Weitere_Deckung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B07_KO_AL.log -errlog=%protokolldir%\SKRB_P_B07_KO_EL.log -perflog=%protokolldir%\SKRB_P_B07_KO_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Weitere_Deckung_I -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B07_SH_AL.log -errlog=%protokolldir%\SKRB_P_B07_SH_EL.log -perflog=%protokolldir%\SKRB_P_B07_SH_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B07_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B07_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B07_SH_AL.log

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
echo REM Ende Weitere_Deckung_I_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM Weitere Deckung II -> SKRB_P_B08 fuer alle hier
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Weitere_Deckung_II -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B08_HY_AL.log -errlog=%protokolldir%\SKRB_P_B08_HY_EL.log -perflog=%protokolldir%\SKRB_P_B08_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Weitere_Deckung_II -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B08_SH_AL.log -errlog=%protokolldir%\SKRB_P_B08_SH_EL.log -perflog=%protokolldir%\SKRB_P_B08_SH_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B08_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B08_SH_AL.log
 
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
 
echo REM Ende Weitere_Deckung_II_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM Liquiditätssicherung -> SKRB_P_B09 fuer alle hier
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.Hypotheken_Liquiditaetssicherung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B09_HY_AL.log -errlog=%protokolldir%\SKRB_P_B09_HY_EL.log -perflog=%protokolldir%\SKRB_P_B09_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.Kommunal_Liquiditaetssicherung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B09_KO_AL.log -errlog=%protokolldir%\SKRB_P_B09_KO_EL.log -perflog=%protokolldir%\SKRB_P_B09_KO_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.Schiff_Liquiditaetssicherung -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B09_SH_AL.log -errlog=%protokolldir%\SKRB_P_B09_SH_EL.log -perflog=%protokolldir%\SKRB_P_B09_SH_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B09_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B09_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B09_SH_AL.log
 
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

echo REM Ende Liquiditätssicherung_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM Einsammeln der Ergebnisse und Umwandlung nach .xlsx
REM *************************************************************************************************************************************************************************

powershell F:\TXS_009_PROD\PROGRAMM\LB\neu_xlsm2xlsx.ps1

copy F:\TXS_009_REPORTS\TXS\P_TEMP\Liqui*.* F:\TXS_009_REPORTS\TXS\ 

echo REM Ende Excelumbenennung_ %date% %time% >> %statusdir%

REM *************************************************************************************************************************************************************************
REM vier Reports Ausnutzung Limite -> SKRB_P_B10 fuer alle hier
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -execute=report.AusLimP13 -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B10_A1_AL.log -errlog=%protokolldir%\SKRB_P_B10_A1_EL.log -perflog=%protokolldir%\SKRB_P_B10_A1_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

start txsjobserver.exe -execute=report.AusLimP20 -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B10_A2_AL.log -errlog=%protokolldir%\SKRB_P_B10_A2_EL.log -perflog=%protokolldir%\SKRB_P_B10_A2_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

start txsjobserver.exe -execute=report.AusLimP22 -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B10_A3_AL.log -errlog=%protokolldir%\SKRB_P_B10_A3_EL.log -perflog=%protokolldir%\SKRB_P_B10_A3_PL.log
start txsjobserver.exe -execute=report.AusLimP26b -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B10_A4_AL.log -errlog=%protokolldir%\SKRB_P_B10_A4_EL.log -perflog=%protokolldir%\SKRB_P_B10_A4_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B10_A1_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B10_A2_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B10_A3_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B10_A4_AL.log

cd F:\TXS_009_REPORTS\TXS

ren AusLimP13* Ausnutzung_Limitierung_13_1.xlsm
ren AusLimP20* Ausnutzung_Limitierung_20_2a.xlsm
ren AusLimP22* Ausnutzung_Limitierung_22_5.xlsm
ren AusLimP26b* Ausnutzung_Limitierung_26b_4.xlsm

copy Ausnutzung_Limitierung_13_1.xlsm Ausnutzung_Limitierung_13_1_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Ausnutzung_Limitierung_20_2a.xlsm Ausnutzung_Limitierung_20_2a_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Ausnutzung_Limitierung_22_5.xlsm Ausnutzung_Limitierung_22_5_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
copy Ausnutzung_Limitierung_26b_4.xlsm Ausnutzung_Limitierung_26b_4_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm

echo REM Ende Ausnutzung_Limite_ %date% %time% >> %statusdir%

REM *************************************************************************************************************************************************************************
REM Sichernde Übersicherung Liquiditätssicherung -> SKRB_P_B11 fuer alle hier
REM neu per 201809
REM *************************************************************************************************************************************************************************

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -execute=report.SiUeLi_HyPfe_OEPfe -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B11_PF_AL.log -errlog=%protokolldir%\SKRB_P_B11_PF_EL.log -perflog=%protokolldir%\SKRB_P_B11_PF_PL.log
start txsjobserver.exe -execute=report.SiUeLi_OEPG -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B11_OE_AL.log -errlog=%protokolldir%\SKRB_P_B11_OE_EL.log -perflog=%protokolldir%\SKRB_P_B11_OE_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

start txsjobserver.exe -execute=report.SiUeLi_SchiPfe -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B11_SH_AL.log -errlog=%protokolldir%\SKRB_P_B11_SH_EL.log -perflog=%protokolldir%\SKRB_P_B11_SH_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B11_PF_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B11_OE_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SKRB_P_B11_SH_AL.log
 
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
echo REM Ende Sichernde_Übersicherung_Liquiditätssicherung_ %date% %time% >> %statusdir%

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
zip -m -9 ReportTXS_009_taeglich.zip Cashflow_Schiffspfandbrief_Gattungsklassische_Deckung_Marktzins_taeglich.xlsx

REM Sichernde Überdeckung 24M
zip -m -9 ReportTXS_009_taeglich.zip Cashflow_Hypothekenpfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsx 
zip -m -9 ReportTXS_009_taeglich.zip Cashflow_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsx 
zip -m -9 ReportTXS_009_taeglich.zip Cashflow_Schiffspfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsx 
zip -m -9 ReportTXS_009_taeglich.zip Cashflow_OEPG_Oeffentlicher_Pfandbrief_Sichernde_Ueberdeckung_Marktzins_taeglich.xlsx 

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

rem nun die restlichen Dateien - dies sind die mit daypointer - nach ReportTXS_009_%date:~6,4%%date:~3,2%%date:~0,2%.zip
cd F:\TXS_009_REPORTS\TXS

zip -m -9 ReportTXS_009_%date:~6,4%%date:~3,2%%date:~0,2%.zip *.xl*

echo REM Ende Zippen_ %date% %time% >> %statusdir%
echo REM Ende KennzahlenReports_BARW %mybasedate% am %date% %time% >> %statusdir%
echo REM ________________________________________________________________ >> %statusdir%

rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Kennzahlen_Reports_BARW_NLB.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini txs-m-nlb@nordlb.de BetriebTXS@nordlb.de F:\PROST\Mail_Fachbereich.txt %%A %%B %%C %%D
)
