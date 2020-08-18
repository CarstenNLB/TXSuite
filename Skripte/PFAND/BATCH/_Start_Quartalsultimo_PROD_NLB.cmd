REM *************************************************************************************************************************************************************************
REM _Start_Quartalssultimo_PROD_NLB.cmd
REM SPA 20190704 Redesign 
REM SPA 20190802 Outputdateien standardisieren und mit CheckRepLog versehen
REM SPA 20200506 basedate vervollständigt (B01)
REM *************************************************************************************************************************************************************************
@echo on
cls

set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set statusdir=F:\TXS_009_PROD\PROTOKOLL\Reports\QUARTALSULTIMOPROD.txt

REM basedate ermitteln
REM *************************************************************************************************************************************************************************
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
REM *************************************************************************************************************************************************************************

echo REM Start QuartalsUltimo_PROD %mybasedate% am %date% %time% >> %statusdir%

REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

REM *************************************************************************************************************************************************************************
REM Namenskonvention Teil 1: SQUP fuer StartMonatsUltimoPROD fuer alle
REM Namenskonvention Teil 2: P, A, T fuer Produktion, Abnahme, Test
REM Namenskonvention Teil 3: Bnn  Nummer je Aufrufblock Zaehler faengt bei 01 an
REM Namenskonvention Teil 4: FL, HY, KO, OE, SH fuer die Transaktionen oder PF fuer Projekt Pfand
REM Namenskonvention Teil 5: AL, EL, PL fuer applog, errlog, perflog
REM Beschreibung in der Ueberschrift, aber nicht mehr im Namen 
REM *************************************************************************************************************************************************************************
REM Monatsultimoreports => SQUP_B01 fuer alle hier
REM *************************************************************************************************************************************************************************

REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM Zinsszenarien Moodys aktualisieren CT 10.12.2014
call txsjobserver.exe -execute=zinsszenario.Aktualisierung-Zinsszenario-Moodys-Hoch    -basedate=%mybasedate% -applog=%protokolldir%\SQUP_P_B01_MH_AL.log -errlog=%protokolldir%\SQUP_P_B01_MH_EL.log -perflog=%protokolldir%\SQUP_P_B01_MH_PL.log
call txsjobserver.exe -execute=zinsszenario.Aktualisierung-Zinsszenario-Moodys-Niedrig -basedate=%mybasedate% -applog=%protokolldir%\SQUP_P_B01_MN_AL.log -errlog=%protokolldir%\SQUP_P_B01_MN_EL.log -perflog=%protokolldir%\SQUP_P_B01_MN_PL.log
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
call F:\PROST\CheckRepLog.bat %protokolldir%\SQUP_P_B01_MH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SQUP_P_B01_MN_AL.log

echo REM Ende  QuartalsUltimo_PROD %mybasedate% am %date% %time% >> %statusdir%
echo REM ________________________________________________________________ >> %statusdir%

rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Quartalsultimoverarbeitung.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss 009 %%A %%B %%C %%D
)