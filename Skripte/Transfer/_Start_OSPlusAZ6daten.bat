REM SPA 14.11.2019 OSPlus AZ6 - daten vom TVL-Server holen
REM SPA 17.06.2020 Erweiterung auf 004
REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

REM Aktiv / CashFlow / Rückstände
xcopy /y W:\SPOT2TXS_AZ6_A_009_%Rest-Date-Suffix%.csv F:\TXS_009_Transfer\OSPlusAZ6\IMPORT\Archiv
xcopy /y W:\SPOT2TXS_AZ6_C_009_%Rest-Date-Suffix%.csv F:\TXS_009_Transfer\OSPlusAZ6\IMPORT\Archiv
xcopy /y W:\SPOT2TXS_AZ6_R_009_%Rest-Date-Suffix%.csv F:\TXS_009_Transfer\OSPlusAZ6\IMPORT\Archiv
xcopy /y W:\SPOT2TXS_AZ6_A_004_%Rest-Date-Suffix%.csv F:\TXS_004_Transfer\OSPlusAZ6\IMPORT\Archiv
xcopy /y W:\SPOT2TXS_AZ6_C_004_%Rest-Date-Suffix%.csv F:\TXS_004_Transfer\OSPlusAZ6\IMPORT\Archiv
xcopy /y W:\SPOT2TXS_AZ6_R_004_%Rest-Date-Suffix%.csv F:\TXS_004_Transfer\OSPlusAZ6\IMPORT\Archiv

REM Dateien im TVL-Verzeichnis loeschen
del /q W:\SPOT2TXS_AZ6_A_009_%Rest-Date-Suffix%.csv
del /q W:\SPOT2TXS_AZ6_C_009_%Rest-Date-Suffix%.csv
del /q W:\SPOT2TXS_AZ6_R_009_%Rest-Date-Suffix%.csv
del /q W:\SPOT2TXS_AZ6_A_004_%Rest-Date-Suffix%.csv
del /q W:\SPOT2TXS_AZ6_C_004_%Rest-Date-Suffix%.csv
del /q W:\SPOT2TXS_AZ6_R_004_%Rest-Date-Suffix%.csv

net use W: /DELETE /y

F:
cd F:\TXS_009_Transfer\OSPlusAZ6\IMPORT\Archiv
zip -m F:\TXS_009_Transfer\OSPlusAZ6\IMPORT\Archiv\SPOT2TXS_AZ6_A_009_%Date-Suffix%.zip SPOT2TXS_AZ6_A_009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_Transfer\OSPlusAZ6\IMPORT\Archiv\SPOT2TXS_AZ6_C_009_%Date-Suffix%.zip SPOT2TXS_AZ6_C_009_%Rest-Date-Suffix%.csv
zip -m F:\TXS_009_Transfer\OSPlusAZ6\IMPORT\Archiv\SPOT2TXS_AZ6_R_009_%Date-Suffix%.zip SPOT2TXS_AZ6_R_009_%Rest-Date-Suffix%.csv
cd F:\TXS_004_Transfer\OSPlusAZ6\IMPORT\Archiv
zip -m F:\TXS_004_Transfer\OSPlusAZ6\IMPORT\Archiv\SPOT2TXS_AZ6_A_004_%Date-Suffix%.zip SPOT2TXS_AZ6_A_004_%Rest-Date-Suffix%.csv
zip -m F:\TXS_004_Transfer\OSPlusAZ6\IMPORT\Archiv\SPOT2TXS_AZ6_C_004_%Date-Suffix%.zip SPOT2TXS_AZ6_C_004_%Rest-Date-Suffix%.csv
zip -m F:\TXS_004_Transfer\OSPlusAZ6\IMPORT\Archiv\SPOT2TXS_AZ6_R_004_%Date-Suffix%.zip SPOT2TXS_AZ6_R_004_%Rest-Date-Suffix%.csv

exit

