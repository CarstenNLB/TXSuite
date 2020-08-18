REM CT 20170517 letzte Anpassungen für RefiReg
REM Import Kundendaten

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

rem ------------STARTE Kunde--------------------
call TXSBatch.exe -env="F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\txs.ini" -v -nodelcf -import=F:\REF_009_PROD\IMPORT\Kunde_RefiReg_TXS.xml
copy F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\TXSBatch.log F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\Archiv\TXSBatch_Kunde_%Date-Suffix%.log

