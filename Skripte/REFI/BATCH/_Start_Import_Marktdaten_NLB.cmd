REM SP 20150420 letzte Anpassungen für RefiReg
REM Import Markdaten

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

rem ------------STARTE MARKTDATEN--------------------
call TXSBatch.exe -env="F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\txs.ini" -v -nodelcf -import=F:\REF_009_PROD\IMPORT\Marktdaten_TXS.xml
copy F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\TXSBatch.log F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\Archiv\TXSBatch_Marktdaten_%Date-Suffix%.log

