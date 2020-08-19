REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\CAM_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

copy /y F:\CAM_009_PROD\DATEN\LB\DarlehenKEV.txt F:\CAM_009_PROD\DATEN\LB\Archiv\DarlehenKEV_%Rest-Date-Suffix%.txt
copy /y F:\CAM_009_PROD\DATEN\LB\DarlehenKEV_BLB.txt F:\CAM_009_PROD\DATEN\LB\Archiv\DarlehenKEV_BLB_%Rest-Date-Suffix%.txt
copy /y F:\CAM_009_PROD\DATEN\LB\Kunde_KEV.xml F:\CAM_009_PROD\DATEN\LB\Archiv\Kunde_KEV_%Rest-Date-Suffix%.xml
copy /y F:\CAM_009_PROD\DATEN\LB\Kunde_KEV_BLB.xml F:\CAM_009_PROD\DATEN\LB\Archiv\Kunde_KEV_BLB_%Rest-Date-Suffix%.xml
copy /y F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_GD_009.xml F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml
copy /y F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_WP_BESTAND_009.csv F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv
copy /y F:\CAM_009_PROD\DATEN\LoanIQ\SPOT2TXS_L_A_009.csv F:\CAM_009_PROD\DATEN\LoanIQ\Archiv\SPOT2TXS_L_A_009_%Rest-Date-Suffix%.csv

del /q F:\CAM_009_PROD\DATEN\LB\DarlehenKEV.txt
del /q F:\CAM_009_PROD\DATEN\LB\DarlehenKEV_BLB.txt
del /q F:\CAM_009_PROD\DATEN\LB\Kunde_KEV.xml
del /q F:\CAM_009_PROD\DATEN\LB\Kunde_KEV_BLB.xml
del /q F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_GD_009.xml
del /q F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_WP_BESTAND_009.csv
del /q F:\CAM_009_PROD\DATEN\LoanIQ\SPOT2TXS_L_A_009.csv

cd F:\CAM_009_PROD\DATEN\LB\Archiv
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\DarlehenKEV_%Date-Suffix%.zip DarlehenKEV_%Rest-Date-Suffix%.txt
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\DarlehenKEV_BLB_%Date-Suffix%.zip DarlehenKEV_BLB_%Rest-Date-Suffix%.txt
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\Kunde_KEV_%Date-Suffix%.zip Kunde_KEV_%Rest-Date-Suffix%.xml
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\Kunde_KEV_BLB_%Date-Suffix%.zip Kunde_KEV_BLB_%Rest-Date-Suffix%.xml
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_GD_009_%Date-Suffix%.zip SPOT2TXS_GD_009_%Rest-Date-Suffix%.xml
zip -m F:\CAM_009_PROD\DATEN\LB\Archiv\SPOT2TXS_WP_BESTAND_009_%Date-Suffix%.zip SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv

cd F:\CAM_009_PROD\DATEN\LoanIQ\Archiv
zip -m F:\CAM_009_PROD\DATEN\LoanIQ\Archiv\SPOT2TXS_L_A_009_%Date-Suffix%.zip SPOT2TXS_L_A_009_%Rest-Date-Suffix%.csv
