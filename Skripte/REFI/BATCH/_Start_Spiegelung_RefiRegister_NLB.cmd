rem SP 20170517 - Spiegelung ausgestellt, damit FB arbeiten kann
rem ########################################################################
rem START SPIEGELUNG INS BARWERTSYSTEM
rem ########################################################################

REM CT 14.05.2020 - Spiegelung wegen Test der Kredite Investionsbank Magdeburg ausgeschaltet

exit


REM Datenbanken
set DBProd=DNLBPROD
set DBBarw=DNLBBARW

REM Passwörter
set user=txsrefi
set pwd_prod=<###pwd###>
set pwd_barw=<###pwd###>

rem EXPORT
expdp %user%/%pwd_prod%@%DBProd% schemas=TXSREFI DIRECTORY=spiegel DUMPFILE=spiegel_refi.dmp NOLOGFILE=y parallel=2 reuse_dumpfiles=y
rem ab hier kann in Produktion gearbeitet werden

F:
rem Schema löschen und neu anlegen
cd F:\TXS-Sicherung\RefiRegister

call Drop_and_Create_txsrefi_DNLBBARW.cmd

rem import
impdp %user%/%pwd_barw%@%DBBarw% schemas=TXSREFI DIRECTORY=spiegel DUMPFILE=spiegel_refi.dmp NOLOGFILE=y

rem ########################################################################
rem ENDE SPIEGELUNG INS BARWERTSYSTEM
rem ########################################################################

rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\REF_009_PROD\STATUS\LB\Prost_Spiegelung_RefiRegister.txt) do (
  java -jar F:\PROST_REFI\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST_REFI\prost.ini %%A %%B %%C %%D
)

