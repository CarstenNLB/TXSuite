REM *************************************************************************************************************************************************************************
REM _Start_Reports_Treasury.cmd
REM SPA 20190704 Redesign 
REM SPA 20190802 Outputdateien standardisieren und mit CheckRepLog versehen
REM SPA 20200108 Flugzeuge raus
REM SPA 20200714 Anforderung CMC die FRC-Reports mit zu versenden
REM *************************************************************************************************************************************************************************
@echo on
cls

set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set statusdir=F:\TXS_009_PROD\PROTOKOLL\Reports\REPORTTREASURY.txt

REM basedate ermitteln
REM *************************************************************************************************************************************************************************
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
REM *************************************************************************************************************************************************************************

echo REM Start StartReportsTreasury %mybasedate% am %date% %time% >> %statusdir%

REM *************************************************************************************************************************************************************************
REM Aufräumen
REM *************************************************************************************************************************************************************************

F:
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*
del /q F:\TXS_009_REPORTS\TXS\P_3\*.*
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*
del /q F:\TXS_009_REPORTS\TXS\P_TEMP\*.*

REM *************************************************************************************************************************************************************************
REM Namenskonvention Teil 1: SRTR fuer StartReportTReasury nur fuer CMC
REM Namenskonvention Teil 2: P, A, T fuer Produktion, Abnahme, Test
REM Namenskonvention Teil 3: Bnn  Nummer je Aufrufblock Zaehler faengt bei 01 an
REM Namenskonvention Teil 4: FL, HY, KO, OE, SH fuer die Transaktionen oder PF fuer Projekt Pfand
REM Namenskonvention Teil 5: AL, EL, PL fuer applog, errlog, perflog
REM Beschreibung in der Ueberschrift, aber nicht mehr im Namen 
REM *************************************************************************************************************************************************************************
REM Stress => SRTR_P_B01 fuer alle hier
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.BW-Berechnung-Kommunal-Stress -basedate=%mybasedate% -applog=%protokolldir%\SRTR_P_B01_KO_AL.log -errlog=%protokolldir%\SRTR_P_B01_KO_EL.log -perflog=%protokolldir%\SRTR_P_B01_KO_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.BW-Berechnung-Hypotheken-Stress -basedate=%mybasedate% -applog=%protokolldir%\SRTR_P_B01_HY_AL.log -errlog=%protokolldir%\SRTR_P_B01_HY_EL.log -perflog=%protokolldir%\SRTR_P_B01_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.BWSchiffStress -basedate=%mybasedate% -applog=%protokolldir%\SRTR_P_B01_SH_AL.log -errlog=%protokolldir%\SRTR_P_B01_SH_EL.log -perflog=%protokolldir%\SRTR_P_B01_SH_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.BWOEPGKommStress -basedate=%mybasedate% -applog=%protokolldir%\SRTR_P_B01_OE_AL.log -errlog=%protokolldir%\SRTR_P_B01_OE_EL.log -perflog=%protokolldir%\SRTR_P_B01_OE_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SRTR_P_B01_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SRTR_P_B01_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SRTR_P_B01_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SRTR_P_B01_OE_AL.log

REM *************************************************************************************************************************************************************************

copy F:\TXS_009_REPORTS\TXS\P_1\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*

copy F:\TXS_009_REPORTS\TXS\P_2\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*

copy F:\TXS_009_REPORTS\TXS\P_4\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*

copy F:\TXS_009_REPORTS\TXS\P_5\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*

echo REM Ende Stress_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM in das Reportverzeichnis
cd F:\TXS_009_REPORTS\TXS

REM ggf vorherige Stände löschen
del /q ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip
REM *************************************************************************************************************************************************************************
REM zip ohne -m

zip ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip *Stress*

echo REM Ende Zip_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM Es folgen für CMC weitere Reports unten

REM Überbleibsel löschen
del /q *.xl*
del /q *.pdf
del /q *.csv
REM *************************************************************************************************************************************************************************

REM *************************************************************************************************************************************************************************
REM Barwertszenarien ohne Stress => SRTR_P_B02 fuer alle hier
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.BW-Berechnung-Kommunal -basedate=%mybasedate% -applog=%protokolldir%\SRTR_P_B02_KO_AL.log -errlog=%protokolldir%\SRTR_P_B02_KO_EL.log -perflog=%protokolldir%\SRTR_P_B02_KO_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.BW-Berechnung-Hypotheken -basedate=%mybasedate% -applog=%protokolldir%\SRTR_P_B02_HY_AL.log -errlog=%protokolldir%\SRTR_P_B02_HY_EL.log -perflog=%protokolldir%\SRTR_P_B02_HY_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.BWSchiff -basedate=%mybasedate% -applog=%protokolldir%\SRTR_P_B02_SH_AL.log -errlog=%protokolldir%\SRTR_P_B02_SH_EL.log -perflog=%protokolldir%\SRTR_P_B02_SH_PL.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.BWOEPGKomm -basedate=%mybasedate% -applog=%protokolldir%\SRTR_P_B02_OE_AL.log -errlog=%protokolldir%\SRTR_P_B02_OE_EL.log -perflog=%protokolldir%\SRTR_P_B02_OE_PL.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

call F:\PROST\CheckRepLog.bat %protokolldir%\SRTR_P_B02_KO_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SRTR_P_B02_HY_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SRTR_P_B02_SH_AL.log
call F:\PROST\CheckRepLog.bat %protokolldir%\SRTR_P_B02_OE_AL.log

copy F:\TXS_009_REPORTS\TXS\P_1\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*

copy F:\TXS_009_REPORTS\TXS\P_2\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*

copy F:\TXS_009_REPORTS\TXS\P_4\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*

copy F:\TXS_009_REPORTS\TXS\P_5\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*

echo REM Ende OhneStress_ %date% %time% >> %statusdir%
REM SPA 20200714 Anforderung CMC die FRC-Reports mit zu versenden
copy F:\TXS_009_REPORTS\FRC\*.* F:\TXS_009_REPORTS\TXS
echo REM Ende FRCReports_ %date% %time% >> %statusdir%
REM *************************************************************************************************************************************************************************
REM in das Reportverzeichnis
cd F:\TXS_009_REPORTS\TXS
REM *************************************************************************************************************************************************************************
REM weitere Reports an CMChinzufügen
REM *************************************************************************************************************************************************************************
zip ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip *.xl*
echo REM Ende Zip_ %date% %time% >> %statusdir%
echo REM Ende  StartReportsTreasury %mybasedate% am %date% %time% >> %statusdir%
echo REM ________________________________________________________________ >> %statusdir%

REM *************************************************************************************************************************************************************************
rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Reports_Treasury.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)
REM *************************************************************************************************************************************************************************
