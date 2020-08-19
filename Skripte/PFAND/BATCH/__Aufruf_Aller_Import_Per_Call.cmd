REM Position
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
REM Script fuer alle Importe
REM 1
REM call _Start_Import_Marktdaten_NLB.cmd
REM 2
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
REM call _Start_Import_KUNDE_NLB.cmd
REM 3
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
REM call _Start_Import_KREDITE_LoanIQ_NLB.cmd
REM 4
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
REM call _Start_Import_PASSIV_LoanIQ_NLB.cmd
REM 5
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
REM call _Start_Import_KREDITE_MIDAS_NLB.cmd
REM 6
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
REM call _Start_Import_Wertpapiere_NLB.bat
REM 7
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
REM call _Start_Import_KREDITE_NLB.cmd
REM 8
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Import_Cashflows_NLB.cmd
REM 9
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Ueberwachungsmonitor.cmd
REM 10
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Kennzahlen_Reports_PROD_NLB.cmd
REM 11
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Spiegelung_NLB.cmd
REM 12
F:
REM cd F:\PROST
REM call Meldung_PROD_NLB_Frei.bat .. exit enthalten
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Meldung_PROD_NLB_Frei.cmd
REM 13
F:
cd F:\TXS_009_REPORTS\CMC
call _start_cmc_nlb.bat
REM 14
F:
cd F:\TXS_009_REPORTS\ABA
call _start_aba_nlb.bat
REM 15
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Kennzahlen_Reports_BARW_NLB.cmd
REM 15a Monatsultimo
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Monatsultimo_PROD_NLB.cmd
REM 16
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_copy_reports.bat
REM 17
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Versende_Reports_FRC.cmd
REM 18
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Reports_Treasury.cmd
REM 19
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD
call _Start_Versende_Reports_Treasury.cmd
REM 19
F:
cd F:\REF_009_PROD
call _Start_Belieferung_RefiRegister.cmd




