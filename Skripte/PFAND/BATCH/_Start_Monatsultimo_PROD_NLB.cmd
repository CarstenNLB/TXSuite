REM *************************************************************************************************************************************************************************
REM _Start_Monatsultimo_PROD_NLB.cmd
REM SPA 20190704 Redesign 
REM SPA 20190802 Outputdateien standardisieren und mit CheckRepLog versehen
REM SPA 20200108 Flugzeuge raus
REM SPA 20200506 basedate vervollständigt (B01)
REM *************************************************************************************************************************************************************************
@echo on
cls

set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set statusdir=F:\TXS_009_PROD\PROTOKOLL\Reports\MONATSULTIMOPROD.txt

REM basedate ermitteln
REM *************************************************************************************************************************************************************************
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
REM *************************************************************************************************************************************************************************

echo REM Start MonatsUltimo_PROD %mybasedate% am %date% %time% >> %statusdir%

F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

REM *************************************************************************************************************************************************************************
REM Namenskonvention Teil 1: SMUP fuer StartMonatsUltimoPROD fuer alle
REM Namenskonvention Teil 2: P, A, T fuer Produktion, Abnahme, Test
REM Namenskonvention Teil 3: Bnn  Nummer je Aufrufblock Zaehler faengt bei 01 an
REM Namenskonvention Teil 4: FL, HY, KO, OE, SH fuer die Transaktionen oder PF fuer Projekt Pfand
REM Namenskonvention Teil 5: AL, EL, PL fuer applog, errlog, perflog
REM Beschreibung in der Ueberschrift, aber nicht mehr im Namen 
REM *************************************************************************************************************************************************************************
REM Monatsultimoreports => SMUP_B01 fuer alle hier
REM *************************************************************************************************************************************************************************

REM SU20140731 Monatliche Reports

call txsjobserver.exe -execute=report.EinzelSchiff -basedate=%mybasedate% -applog=%protokolldir%\SMUP_P_B01_SH_AL.log -errlog=%protokolldir%\SMUP_P_B01_SH_EL.log -perflog=%protokolldir%\SMUP_P_B01_SH_PL.log

call txsjobserver.exe -execute=report.ImmoDeckung -basedate=%mybasedate% -applog=%protokolldir%\SMUP_P_B01_ID_AL.log -errlog=%protokolldir%\SMUP_P_B01_ID_EL.log -perflog=%protokolldir%\SMUP_P_B01_ID_PL.log

call F:\PROST\CheckRepLog.bat %protokolldir%\SMUP_P_B01_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SMUP_P_B01_ID_AL.log

echo REM Ende Einzel_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM fs Barwerreports ohne -weitere Deckung -sichernde Ueberdeckung -Liquideckung
REM BarwertSpezial => SMUP_B02 fuer alle hier
REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

call txsjobserver.exe -execute=szenarioreport.Barwertreport12taeglichSchiffe -basedate=%mybasedate% -applog=%protokolldir%\SMUP_P_B02_SH_AL.log -errlog=%protokolldir%\SMUP_P_B02_SH_EL.log -perflog=%protokolldir%\SMUP_P_B02_SH_PL.log

call F:\PROST\CheckRepLog.bat %protokolldir%\SMUP_P_B02_SH_AL.log
echo REM Ende Barwert12_ %date% %time% >> %statusdir%
REM in das Reportverzeichnis
F:
cd F:\TXS_009_REPORTS\TXS

ren Cashflow_*_Schiffspfandbrief_Schiffspfandbrief_Marktzins* Cashflow_Schiffspfandbrief_Marktzins_quartalsweise.xlsm
del Cashflow_*_Schiffspfandbrief_Schiffspfandbrief_Hochzins*
del Cashflow_*_Schiffspfandbrief_Schiffspfandbrief_Niedrigzins*

REM Zippen erfolgt im _Start_copy_reports.bat
echo REM Ende Zip_ %date% %time% >> %statusdir%
echo REM Ende  MonatsUltimo_PROD %mybasedate% am %date% %time% >> %statusdir%
echo REM ________________________________________________________________ >> %statusdir%

rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Monatsultimoverarbeitung.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)