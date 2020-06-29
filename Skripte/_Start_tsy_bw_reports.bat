REM SP-10.09.2014 CMC-Pfad angepasst auf \\kbk.nordlb.local\funktion\
REM Unnasch 20130503
REM Unnasch 20140707 / Anpassungen um dem FB ein weiteres .zipfile mit anderer Namenskonvention zur Verfügung zu stellen 
REM Unnasch 20170112 / Bereinigt wegen nach _Start_neue_bw_reports.bat ausgelagerter neuer Mechanismen
REM Unnasch 20170118 / nur noch die TXS-Reports für tsy und diese im Barwertsystem
@echo on
cls

REM Aktualisierung der Barwertszenarien
REM Reports verpacken
REM Reports verschicken

REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM F:\TXS_009_PROD\PARAM\dp.txt kommt aus FRISCO.bar via FRISCODPFILE aus txs_batch.ini
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

REM Auch die Protokolle der Reporterstellung sichern 
set protokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\Protokolle

REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM Ergebnisverzeichnisse löschen
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
F:
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*
del /q F:\TXS_009_REPORTS\TXS\P_3\*.*
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*
del /q F:\TXS_009_REPORTS\TXS\P_TEMP\*.*

REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM Barwertszenarien Stress berechnen SU 20120103
REM Parallel SU 20151201
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.BW-Berechnung-Kommunal-Stress -basedate=%mybasedate% -applog=%protokolldir%\applog_barw_komm_stress.log -errlog=%protokolldir%\errlog_barw_komm_stress.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.BW-Berechnung-Hypotheken-Stress -basedate=%mybasedate% -applog=%protokolldir%\applog_barw_hypo_stress.log -errlog=%protokolldir%\errlog_barw_hypo_stress.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.BWFlugzeugStress -basedate=%mybasedate% -applog=%protokolldir%\applog_barw_flug_stress.log -errlog=%protokolldir%\errlog_barw_flug_stress.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.BWSchiffStress -basedate=%mybasedate% -applog=%protokolldir%\applog_barw_schf_stress.log -errlog=%protokolldir%\errlog_barw_schf_stress.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.BWOEPGKommStress -basedate=%mybasedate% -applog=%protokolldir%\applog_barw_oepg_stress.log -errlog=%protokolldir%\errlog_barw_oepg_stress.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log

REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

copy F:\TXS_009_REPORTS\TXS\P_1\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*

copy F:\TXS_009_REPORTS\TXS\P_2\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*

copy F:\TXS_009_REPORTS\TXS\P_3\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_3\*.*

copy F:\TXS_009_REPORTS\TXS\P_4\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*

copy F:\TXS_009_REPORTS\TXS\P_5\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*

REM in das Reportverzeichnis
cd F:\TXS_009_REPORTS\TXS

REM ggf vorherige Stände löschen
del /q ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip

REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM SU 20120418
REM Versand der Stresstests an CMC, CMC bekommt hier nur Stress
REM zip ohne -m

zip ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip *Stress*

REM Es folgen für CMC weitere Reports unten

REM Überbleibsel löschen
del /q *.xl*
del /q *.pdf
del /q *.csv
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM Barwertszenarien ohne Stress berechnen SU 20120103
REM Parallel SU 20151201
REM zum Ausführen wieder in das TXS-Verzeichnis
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_1.ini -execute=szenarioreport.BW-Berechnung-Kommunal -basedate=%mybasedate% -applog=%protokolldir%\applog_barw_komm.log -errlog=%protokolldir%\errlog_barw_komm.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_2.ini -execute=szenarioreport.BW-Berechnung-Hypotheken -basedate=%mybasedate% -applog=%protokolldir%\applog_barw_hypo.log -errlog=%protokolldir%\errlog_barw_hypo.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_3.ini -execute=szenarioreport.BWFlugzeug -basedate=%mybasedate% -applog=%protokolldir%\applog_barw_flug.log -errlog=%protokolldir%\errlog_barw_flug.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_4.ini -execute=szenarioreport.BWSchiff -basedate=%mybasedate% -applog=%protokolldir%\applog_barw_schf.log -errlog=%protokolldir%\errlog_barw_schf.log

start txsjobserver.exe -env=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_BARW\TXS_barw_p_5.ini -execute=szenarioreport.BWOEPGKomm -basedate=%mybasedate% -applog=%protokolldir%\applog_barw_oepg.log -errlog=%protokolldir%\errlog_barw_oepg.log

powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %protokolldir%\process_ende.log
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

copy F:\TXS_009_REPORTS\TXS\P_1\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_1\*.*

copy F:\TXS_009_REPORTS\TXS\P_2\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_2\*.*

copy F:\TXS_009_REPORTS\TXS\P_3\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_3\*.*

copy F:\TXS_009_REPORTS\TXS\P_4\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_4\*.*

copy F:\TXS_009_REPORTS\TXS\P_5\*.* F:\TXS_009_REPORTS\TXS
del /q F:\TXS_009_REPORTS\TXS\P_5\*.*

REM in das Reportverzeichnis
cd F:\TXS_009_REPORTS\TXS

REM weitere Reports an TSY hinzufügen

zip ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip *.xl*

REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
REM CMC verschicken und archivieren 
net use H: /delete /y
REM REM net use Q: /delete

rem seit 06.09.2014 anders - SP net use R: \\Vfopt001\funktion$ Edrpwd100 /USER:KBK\TXSTRV01
net use H: \\kbk.nordlb.local\funktion Edrpwd100 /USER:KBK\TXSTRV01

copy ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip H:\Treasury\Treasury_Coll_Mgt_Report

net use H: /delete /y
REM ********* VORSICHT *********

REM ins archiv
copy ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip archiv

REM Arbeitstand löschen
del /q ReportTXS_009_CMC_%date:~6,4%%date:~3,2%%date:~0,2%.zip
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

REM Überbleibsel löschen
del /q *.xl*
del /q *.pdf
del /q *.csv
REM +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
