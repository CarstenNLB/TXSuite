REM CT - angelegt am 25.07.2017 

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo LoanIQ-Daten von W: holen
REM Aktiv
xcopy /y W:\SPOT2TXS_L_A_009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LOANIQ\SPOT2TXS_L_A_009.csv
xcopy /y W:\SPOT2TXS_L_A_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\LOANIQ\IMPORT\Archiv
REM Aktiv auch nach CAM kopieren
copy /y W:\SPOT2TXS_L_A_009_%Rest-Date-Suffix%.csv F:\CAM_009_PROD\DATEN\LOANIQ\SPOT2TXS_L_A_009.csv

REM Cashflows
xcopy /y W:\SPOT2TXS_L_C_009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LOANIQ\SPOT2TXS_L_C_009.csv
xcopy /y W:\SPOT2TXS_L_C_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\LOANIQ\IMPORT\Archiv

REM Passiv 
xcopy /y W:\SPOT2TXS_L_P_009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LOANIQ\SPOT2TXS_L_P_009.csv
xcopy /y W:\SPOT2TXS_L_P_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\LOANIQ\IMPORT\Archiv

REM Rueckstaende
REM Werden nicht verarbeitet, deshalb nur ins Archiv - CT 25.07.2017
REM xcopy /y W:\SPOT2TXS_L_R_009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LOANIQ\SPOT2TXS_L_R_009.csv
xcopy /y W:\SPOT2TXS_L_R_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\LOANIQ\IMPORT\Archiv

REM Dateien im TVL-Verzeichnis loeschen
del /q W:\SPOT2TXS_L_A_009_%Rest-Date-Suffix%.csv
del /q W:\SPOT2TXS_L_C_009_%Rest-Date-Suffix%.csv
del /q W:\SPOT2TXS_L_P_009_%Rest-Date-Suffix%.csv
del /q W:\SPOT2TXS_L_R_009_%Rest-Date-Suffix%.csv

net use W: /DELETE /y

F:
cd F:\TXS_009_PROD\PROGRAMM\LOANIQ

REM LoanIQ-Aktiv starten
call LoanIQ.bat

REM LoanIQ-Passiv starten
call LoanIQPassiv.bat

REM LoanIQ-Cashflows starten
call CashflowsLoanIQ.bat

copy /Y F:\TXS_009_PROD\DATEN\LOANIQ\Kredite_LoanIQ_TXS.xml F:\TXS_009_PROD\Import\Kredite_LoanIQ_TXS.xml
copy /Y F:\TXS_009_PROD\DATEN\LOANIQ\Passiv_LoanIQ_TXS.xml F:\TXS_009_PROD\Import\Passiv_LoanIQ_TXS.xml
copy /Y F:\TXS_009_PROD\DATEN\LOANIQ\Cashflows_LoanIQ_TXS.xml F:\TXS_009_PROD\Import\Cashflows_LoanIQ_TXS.xml

cd F:\TXS_009_TRANSFER\LOANIQ\IMPORT\Archiv
zip -m F:\TXS_009_TRANSFER\LOANIQ\IMPORT\Archiv\LoanIQ_Cashflow_%Date-Suffix%.zip SPOT2TXS_L_C_009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_TRANSFER\LOANIQ\IMPORT\Archiv\LoanIQ_Kredite_%Date-Suffix%.zip SPOT2TXS_L_A_009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_TRANSFER\LOANIQ\IMPORT\Archiv\LoanIQ_Passiv_%Date-Suffix%.zip SPOT2TXS_L_P_009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_TRANSFER\LOANIQ\IMPORT\Archiv\LoanIQ_Rueckstaende_%Date-Suffix%.zip SPOT2TXS_L_R_009_%Rest-Date-Suffix%.csv

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_LoanIQ.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit