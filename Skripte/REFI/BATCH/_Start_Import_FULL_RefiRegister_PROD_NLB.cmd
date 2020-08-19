REM CT 14.12.2017

@echo on
cls

REM Ende wenn eine der Importdateien fehlt
if not exist F:\REF_009_PROD\IMPORT\Marktdaten_TXS.xml exit
if not exist F:\REF_009_PROD\IMPORT\Kredite_Konsortial_009_TXS.xml exit
if not exist F:\REF_009_PROD\IMPORT\Kredite_RefiRegister_LoanIQ_TXS.xml exit

REM Ende wenn eine der Dateien zu klein (=defekt) ist
FOR /F "usebackq" %%A IN ('F:\REF_009_PROD\IMPORT\Marktdaten_TXS.xml') DO set size=%%~zA
if %size% LSS 4096 exit
FOR /F "usebackq" %%A IN ('F:\REF_009_PROD\IMPORT\Kredite_Konsortial_009_TXS.xml') DO set size=%%~zA
if %size% LSS 4096 exit
FOR /F "usebackq" %%A IN ('F:\REF_009_PROD\IMPORT\Kredite_RefiRegister_LoanIQ_TXS.xml ') DO set size=%%~zA
if %size% LSS 4096 exit

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

F:
rem ----------------------------------------------------------
rem ------------ STARTE MARKTDATEN        --------------------
call F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\_Start_Import_Marktdaten_NLB.cmd
rem ----------------------------------------------------------

rem ----------------------------------------------------------
rem ------------ STARTE Import Kunde        --------------------
call F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\_Start_Import_Kunde_NLB.cmd
rem ----------------------------------------------------------

rem ----------------------------------------------------------
rem ------------ STARTE KREDITE           --------------------
call F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\_Start_Import_Kredite_RefiRegister_NLB.cmd
rem ----------------------------------------------------------

rem ----------------------------------------------------------
rem ------------ STARTE LIQ2RefiRegister  --------------------
call F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\_Start_Import_Kredite_LIQ2RefiRegister_NLB.cmd
rem ----------------------------------------------------------

REM Verzeichnis anlegen und kopieren
mkdir F:\REF_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\REF_009_PROD\IMPORT\Marktdaten_TXS.xml F:\REF_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\REF_009_PROD\IMPORT\Kunde_RefiReg_TXS.xml F:\REF_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\REF_009_PROD\IMPORT\Kredite_Konsortial_009_TXS.xml F:\REF_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%
copy F:\REF_009_PROD\IMPORT\Kredite_RefiRegister_LoanIQ_TXS.xml F:\REF_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

del /q F:\REF_009_PROD\IMPORT\Marktdaten_TXS.xml
del /q F:\REF_009_PROD\IMPORT\Kunde_RefiReg_TXS.xml
del /q F:\REF_009_PROD\IMPORT\Kredite_Konsortial_009_TXS.xml
del /q F:\REF_009_PROD\IMPORT\Kredite_RefiRegister_LoanIQ_TXS.xml
