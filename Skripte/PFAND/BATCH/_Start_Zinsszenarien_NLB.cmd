REM *************************************************************************************************************************************************************************
REM _Start_Zinsszenarien_NLB.cmd
REM SPA 20190704 Redesign 
REM SPA 20190802 Outputdateien standardisieren und mit CheckRepLog versehen
REM SPA 20200506 basedate vervollständigt B01/B10/B11
REM *************************************************************************************************************************************************************************

@echo on
cls

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set statusdir=F:\TXS_009_PROD\PROTOKOLL\Reports\ZINSSZENARIEN.txt

echo REM Start Zinsszenarien_NLB %mybasedate% am %date% %time% >> %statusdir%

REM *************************************************************************************************************************************************************************
REM Namenskonvention Teil 1: SZSN fuer StartZinsSzenarienNLN
REM Namenskonvention Teil 2: P, A, T fuer Produktion, Abnahme, Test
REM Namenskonvention Teil 3: Bnn  Nummer je Aufrufblock Zaehler faengt bei 01 an
REM Namenskonvention Teil 4: FL, HY, KO, OE, SH fuer die Transaktionen oder PF fuer Projekt Pfand
REM Namenskonvention Teil 5: AL, EL, PL fuer applog, errlog, perflog
REM Beschreibung in der Ueberschrift, aber nicht mehr im Namen 
REM *************************************************************************************************************************************************************************
REM Aktualisierung der Zinsszenarien => SZSN_P_B01 fuer alle hier
REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
REM *************************************************************************************************************************************************************************
call txsjobserver.exe -execute=zinsszenario.Aktualisierung-Zinsszenario-Normal  -basedate=%mybasedate% -applog=%protokolldir%\SZSN_P_B01_ZO_AL.log -errlog=%protokolldir%\SZSN_P_B01_ZO_EL.log -perflog=%protokolldir%\SZSN_P_B01_ZO_PL.log
call txsjobserver.exe -execute=zinsszenario.Aktualisierung-Zinsszenario-Hoch    -basedate=%mybasedate% -applog=%protokolldir%\SZSN_P_B01_ZH_AL.log -errlog=%protokolldir%\SZSN_P_B01_ZH_EL.log -perflog=%protokolldir%\SZSN_P_B01_ZH_PL.log
call txsjobserver.exe -execute=zinsszenario.Aktualisierung-Zinsszenario-Niedrig -basedate=%mybasedate% -applog=%protokolldir%\SZSN_P_B01_ZN_AL.log -errlog=%protokolldir%\SZSN_P_B01_ZN_EL.log -perflog=%protokolldir%\SZSN_P_B01_ZN_PL.log
REM *************************************************************************************************************************************************************************
call F:\PROST\CheckRepLog.bat %protokolldir%\SZSN_P_B01_ZO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SZSN_P_B01_ZH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SZSN_P_B01_ZN_AL.log

echo REM Ende Zins_ %date% %time% >> %statusdir%
echo REM Ende Zinsszenarien_NLB %mybasedate% am %date% %time% >> %statusdir%
echo REM ________________________________________________________________ >> %statusdir%

