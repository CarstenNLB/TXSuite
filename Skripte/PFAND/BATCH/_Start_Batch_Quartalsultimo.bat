REM Durchfuehrung Quartalsultimo
REM Tripp 02.10.2019
REM Tripp Abbruch durch falsche Pfadangaben korrigiert, nun wird geht jedem call eine Pfadangabe voraus! 04.10.2019

F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

REM Kennzahlen Reports NLB
call _Start_Kennzahlen_Reports_PROD_NLB.cmd

REM Spiegelung NLB
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Spiegelung_NLB.cmd

REM Reports CMC und ABACUS
REM CMC
cd F:\TXS_009_REPORTS\CMC
call _start_cmc_nlb.bat

REM ABACUS
cd F:\TXS_009_REPORTS\ABA
call _start_aba_nlb.bat

cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
REM Kennzahlen Reports Barw NLB
call _Start_Kennzahlen_Reports_BARW_NLB.cmd

REM Monatsultimo
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Monatsultimo_PROD_NLB.cmd

REM Quartalsultimo
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Quartalsultimo_PROD_NLB.cmd

REM Copy Reports
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_copy_reports.bat

REM Versende FRC Reports
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Versende_Reports_FRC.cmd

REM Start Treasury
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Reports_Treasury.cmd

REM Versende Treasury
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Versende_Reports_Treasury.cmd