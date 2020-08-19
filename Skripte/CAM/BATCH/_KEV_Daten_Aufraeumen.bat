REM Skript zum Aufraeumen der Verzeichnisse 'DATEN\LB' und 'DATEN\LoanIQ'
REM Bestimmte Dateien muessen aus diesen Verzeichnis entfernt werden, da PROST in diesen beiden Verzeichnissen die Importdateien sucht.
REM CT - angelegt am 30.06.2020

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

F:
cd F:\CAM_009_PROD\DATEN\LoanIQ

REM LoanIQ
copy /y F:\CAM_009_PROD\DATEN\LoanIQ\SPOT2TXS_L_A_009.csv F:\CAM_009_PROD\DATEN\LoanIQ\Archiv\SPOT2TXS_L_A_009_%Rest-Date-Suffix%.csv
del /q F:\CAM_009_PROD\DATEN\LoanIQ\SPOT2TXS_L_A_009.csv
cd F:\CAM_009_PROD\DATEN\LoanIQ\Archiv
zip -m F:\CAM_009_PROD\DATEN\LoanIQ\Archiv\SPOT2TXS_L_A_009_%Date-Suffix%.zip SPOT2TXS_L_A_009_%Rest-Date-Suffix%.csv

F:
cd F:\CAM_009_PROD\DATEN\LB

REM Wertpapiere
copy /y F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_GD_009.xml F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml
copy /y F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_WP_BESTAND_009.csv F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv
del /q F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_GD_009.xml
del /q F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_WP_BESTAND_009.csv
cd F:\CAM_009_PROD\DATEN\LB\Archiv
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_GD_009_%Date-Suffix%.zip SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_WP_BESTAND_009_%Date-Suffix%.zip SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv

F:
cd F:\CAM_009_PROD\DATEN\LB

REM DarKa NLB
copy /y F:\CAM_009_PROD\DATEN\LB\DarlehenKEV.txt F:\CAM_009_PROD\DATEN\LB\Archiv\DarlehenKEV_%Rest-Date-Suffix%.txt
del /q F:\CAM_009_PROD\DATEN\LB\DarlehenKEV.txt
cd F:\CAM_009_PROD\DATEN\LB\Archiv
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\DarlehenKEV_%Date-Suffix%.zip DarlehenKEV_%Rest-Date-Suffix%.txt

F:
cd F:\CAM_009_PROD\DATEN\LB

REM DarKa BLB
copy /y F:\CAM_009_PROD\DATEN\LB\DarlehenKEV_BLB.txt F:\CAM_009_PROD\DATEN\LB\Archiv\DarlehenKEV_BLB_%Rest-Date-Suffix%.txt
del /q F:\CAM_009_PROD\DATEN\LB\DarlehenKEV_BLB.txt
cd F:\CAM_009_PROD\DATEN\LB\Archiv
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\DarlehenKEV_BLB_%Date-Suffix%.zip DarlehenKEV_BLB_%Rest-Date-Suffix%.txt

F:
cd F:\CAM_009_PROD\DATEN\LB

REM Kunde NLB
copy /y F:\CAM_009_PROD\DATEN\LB\Kunde_KEV.xml F:\CAM_009_PROD\DATEN\LB\Archiv\Kunde_KEV_%Rest-Date-Suffix%.xml
del /q F:\CAM_009_PROD\DATEN\LB\Kunde_KEV.xml
cd F:\CAM_009_PROD\DATEN\LB\Archiv
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\Kunde_KEV_%Date-Suffix%.zip Kunde_KEV_%Rest-Date-Suffix%.xml

F:
cd F:\CAM_009_PROD\DATEN\LB

REM Kunde BLB
copy /y F:\CAM_009_PROD\DATEN\LB\Kunde_KEV_BLB.xml F:\CAM_009_PROD\DATEN\LB\Archiv\Kunde_KEV_BLB_%Rest-Date-Suffix%.xml
del /q F:\CAM_009_PROD\DATEN\LB\Kunde_KEV_BLB.xml
cd F:\CAM_009_PROD\DATEN\LB\Archiv
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\Kunde_KEV_BLB_%Date-Suffix%.zip Kunde_KEV_BLB_%Rest-Date-Suffix%.xml

