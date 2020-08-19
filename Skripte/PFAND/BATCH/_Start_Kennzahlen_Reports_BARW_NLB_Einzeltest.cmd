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

F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

call txsjobserver.exe -execute=report.AusLimP20 -basedate=%mybasedate% -applog=%protokolldir%\SKRB_P_B10_A2_AL.log -errlog=%protokolldir%\SKRB_P_B10_A2_EL.log -perflog=%protokolldir%\SKRB_P_B10_A2_PL.log

cd F:\TXS_009_REPORTS\TXS

ren AusLimP20* Ausnutzung_Limitierung_20_2a.xlsm

copy Ausnutzung_Limitierung_20_2a.xlsm Ausnutzung_Limitierung_20_2a_%date:~6,4%%date:~3,2%%date:~0,2%.xlsm
