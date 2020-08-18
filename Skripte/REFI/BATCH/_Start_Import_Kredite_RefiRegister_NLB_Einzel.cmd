REM CT 20150605 letzte Anpassungen für RefiReg

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

F:
cd F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD

rem ------------STARTE REFI Konsortialgeschaefte--------------------
call TXSBatch.exe -env="F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\txs.ini" -v -nodelcf -nos -cds -cdr -import=F:\User\Kredite_DeutscheHypo_009_TXS.xml
copy F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\TXSBatch.log F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\Archiv\TXSBatch_Kredite_Sonder_%Date-Suffix%.log


