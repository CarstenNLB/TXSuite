REM CT 20171214 Neu f�r LIQ2RefiReg

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

F:
cd F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD

rem ------------STARTE REFI Konsortialgeschaefte--------------------
call TXSBatch.exe -env="F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\txs.ini" -v -nodelcf -nos -cds -cdr -import=F:\REF_009_PROD\IMPORT\Kredite_RefiRegister_LoanIQ_TXS.xml
copy F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\TXSBatch.log F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\Protokolle\Archiv\TXSBatch_Kredite_RefiRegister_LoanIQ_%Date-Suffix%.log


