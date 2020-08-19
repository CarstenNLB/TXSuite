REM CT - angelegt am 27.04.2016 

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

net use T: /DELETE /y
net use T: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo MIDAS-Daten von T: holen
xcopy /y T:\SPOT2TXS_M_A_009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LB\SPOT2TXS_M_A_009.csv
xcopy /y T:\SPOT2TXS_M_A_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\MIDAS\IMPORT\Archiv
xcopy /y T:\SPOT2TXS_M_C_009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LB\SPOT2TXS_M_C_009.csv
xcopy /y T:\SPOT2TXS_M_C_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\MIDAS\IMPORT\Archiv

del /q T:\SPOT2TXS_M_A_009_%Rest-Date-Suffix%.csv
del /q T:\SPOT2TXS_M_C_009_%Rest-Date-Suffix%.csv

net use T: /DELETE /y

F:
cd F:\TXS_009_PROD\PROGRAMM\LB

REM MIDAS-Kredite starten
call MIDAS.bat

REM echo %errorlevel% > F:\TXS_009_PROD\DATEN\LB\MIDAS_ERRORLEVEL.txt

if %errorlevel% GTR 6 goto Fehler

REM MIDAS-Cashflows starten
call CashflowsMIDAS.bat

copy /Y F:\TXS_009_PROD\DATEN\LB\Kredite_MIDAS_TXS.xml F:\TXS_009_PROD\Import\Kredite_MIDAS_TXS.xml
copy /Y F:\TXS_009_PROD\DATEN\LB\Cashflows_MIDAS_TXS.xml F:\TXS_009_PROD\Import\Cashflows_MIDAS_TXS.xml

cd F:\TXS_009_TRANSFER\MIDAS\IMPORT\Archiv
zip -m F:\TXS_009_TRANSFER\MIDAS\IMPORT\Archiv\MIDAS_Kredite_%Date-Suffix%.zip SPOT2TXS_M_A_009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_TRANSFER\MIDAS\IMPORT\Archiv\MIDAS_Cashflow_%Date-Suffix%.zip SPOT2TXS_M_C_009_%Rest-Date-Suffix%.csv

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_MIDAS.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit

:Fehler
F:\PROST\SendMail-MIDAS.bat

exit