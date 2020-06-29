rem ########################################################################
rem START SPIEGELUNG INS BARWERTSYSTEM
rem ########################################################################

REM Datenbanken
set DBProd=DNLBPROD
set DBBarw=DNLBBARW

REM Passwörter
set user=txsadmin
set pwd_prod=gammaNLB0
set pwd_barw=gammaNLB1

rem EXPORT
rem SU 20151006 query um txsadmin.imp_cashflow_manuell:"WHERE rownum<1" ergänzt, auch diese Tabelle wir im BW-System nicht gebraucht.
rem expdp %user%/%pwd%@%DBProd% schemas=TXSADMIN DIRECTORY=spiegel DUMPFILE=spiegel.dmp NOLOGFILE=y parallel=2 reuse_dumpfiles=y query=txsadmin.cashflow_aggregat:\"WHERE rownum<1\"

expdp %user%/%pwd_prod%@%DBProd% schemas=TXSADMIN DIRECTORY=spiegel DUMPFILE=spiegel.dmp NOLOGFILE=y parallel=2 reuse_dumpfiles=y query='txsadmin.imp_cashflow_manuell:"WHERE rownum<1"','txsadmin.cashflow_aggregat:"WHERE rownum<1"'
rem ab hier kann in Produktion gearbeitet werden

F:
rem Schema löschen und neu anlegen
cd F:\TXS-Sicherung\Pfandbrief

call Drop_and_Create_txsadmin_DNLBBARW.cmd

rem import
impdp %user%/%pwd_barw%@%DBBarw% schemas=TXSADMIN DIRECTORY=spiegel DUMPFILE=spiegel.dmp NOLOGFILE=y

rem ########################################################################
rem ENDE SPIEGELUNG INS BARWERTSYSTEM
rem ########################################################################



