REM CT - angelegt am 14.11.2017 

REM ZIP-Date-Suffix
set ZIP-Date-Suffix=%date:~6,4%%date:~3,2%

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

echo Verarbeitung LIQ2RefiRegister starten >> F:\REF_009_PROD\PROTOKOLL\LOANIQ\LIQ2RefiRegisterVerarbeitung.txt

echo %date% >> F:\REF_009_PROD\PROTOKOLL\LOANIQ\LIQ2RefiRegisterVerarbeitung.txt
echo %time% >> F:\REF_009_PROD\PROTOKOLL\LOANIQ\LIQ2RefiRegisterVerarbeitung.txt

REM LIQ2RefiRegister starten
F:
cd F:\REF_009_PROD\PROGRAMM\LB
call LoanIQ2RefiRegister.bat

copy F:\REF_009_PROD\DATEN\LOANIQ\Kredite_RefiRegister_LoanIQ_TXS.xml F:\REF_009_PROD\IMPORT\Kredite_RefiRegister_LoanIQ_TXS.xml 
del /q F:\REF_009_PROD\DATEN\LOANIQ\Kredite_RefiRegister_LoanIQ_TXS.xml

echo Verarbeitung LIQ2RefiRegister beendet >> F:\REF_009_PROD\PROTOKOLL\LOANIQ\LIQ2RefiRegisterVerarbeitung.txt
echo %date% >> F:\REF_009_PROD\PROTOKOLL\LOANIQ\LIQ2RefiRegisterVerarbeitung.txt
echo %time% >> F:\REF_009_PROD\PROTOKOLL\LOANIQ\LIQ2RefiRegisterVerarbeitung.txt