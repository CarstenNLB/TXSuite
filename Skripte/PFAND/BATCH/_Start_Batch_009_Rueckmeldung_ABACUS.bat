REM CT - angelegt am 22.03.2018
REM spa - angepasst am 01.11.2019 fuer den 31.10.2019
REM spa - angepasst am 19.12.2019 fuer den 31.12.2019

set statusdir=F:\TXS_009_PROD\PROTOKOLL\Reports\ABANLBSON.txt
echo REM Start_SonderCopy_201912131_ %date% %time% >> %statusdir%

REM Date-Suffix
set Date-Suffix=D%date:~8,2%%date:~3,2%%date:~0,2%

echo Date-Suffix: %Date-Suffix%

REM DateSAPCMS-Suffix
set DateSAPCMS-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

echo DateSAPCMS-Suffix: %DateSAPCMS-Suffix%

REM Zip-Date-Suffix
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

REM Aktuelle Reports an ABACUS verschicken
echo REM Wechsel_Verzeichnis_ %date% %time% >> %statusdir%
F:
cd F:\TXS_009_REPORTS\ABA\AktuelleReports
REM NLB
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_KREDIT.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_KREDIT_20191231.csv

copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_LOANDEPOTS.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_LOANDEPOTS_20191231.csv

copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPAKTIV.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPAKTIV_20191231.csv

copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPPASSIV.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPPASSIV_20191231.csv

copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_KREDIT.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_KREDIT_20191231.csv

copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_LOANDEPOTS.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_LOANDEPOTS_20191231.csv
    
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPAKTIV.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPAKTIV_20191231.csv

copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPPASSIV.csv F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPPASSIV_20191231.csv
echo REM Aktuell_auf_Datum_201912131_kopiert_ %date% %time% >> %statusdir%

REM ********* VORSICHT *********
REM REM REM Reports an ABACUS verschicken

net use Y: /DELETE /y
net use Y: \\nlbprod11-vf\txstvl$\send <###pwd###> /USER:KBK\TXSTRV01 

REM REM REM los gehts 
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_KREDIT_20191231.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_LOANDEPOTS_20191231.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPAKTIV_20191231.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPPASSIV_20191231.csv Y:
REM BLB per 23.10.2017 eingefügt
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_KREDIT_20191231.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_LOANDEPOTS_20191231.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPAKTIV_20191231.csv Y:
copy F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPPASSIV_20191231.csv Y:
REM
net use Y: /DELETE /y
REM 
echo REM Dateien_nach_ABACUS_versandt_ %date% %time% >> %statusdir%
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ABACUS_20191031.zip F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_KREDIT_20191231.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ABACUS_20191031.zip F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_LOANDEPOTS_20191231.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ABACUS_20191031.zip F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPAKTIV_20191231.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ABACUS_20191031.zip F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_009_ABA_EB_WPPASSIV_20191231.csv 
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ABACUS_20191031.zip F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_KREDIT_20191231.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ABACUS_20191031.zip F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_LOANDEPOTS_20191231.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ABACUS_20191031.zip F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPAKTIV_20191231.csv
zip -m F:\TXS_009_REPORTS\ABA\AktuelleReports\ABACUS_20191031.zip F:\TXS_009_REPORTS\ABA\AktuelleReports\ReportTXS_004_ABA_EB_WPPASSIV_20191231.csv
echo REM Dateien_gezippt_ %date% %time% >> %statusdir%
echo REM Ende_SonderCopy_201912131_ %date% %time% >> %statusdir%