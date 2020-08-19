REM *************************************************************************************************************************************************************************
REM _Start_Reports_NLB_RUECK.cmd - Beginn
REM TXS Reporterstellung Rueckmeldung Sicherheitenn SPA 20190730
REM     Generisches Reporting, um die originaere TXS-Rueckmeldung bei den Sicherheiten abzuloesen
REM                            Die TXS-Rueckmeldung dauert zwei Stunden .. und ist fehlerhaft
REM     Der generische Report wird ueber 3 Jobs fuer Flugzeug / Schiff / Hypotheken aufgerufen
REM     Danach Weiterverarbeitung im PowerShellScripten
REM SPA - 20190802
REM SPA - 20200128 - Ausbau Flugzeuge und Ausbau Rueckmeldung von VO mit F
REM *************************************************************************************************************************************************************************

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)
REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

set TXSProtokolldir=F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle
set NLBProtokolldir=F:\TXS_009_PROD\PROTOKOLL\LB

REM *************************************************************************************************************************************************************************
REM Bereinigung der Zwischendateien soll in den Scripten erfolgen
REM Die Reports werden hier geloescht
REM *************************************************************************************************************************************************************************
F:
cd F:\TXS_009_REPORTS\NLBRUECK
del /q NLB_Rueck*.xlsm
del /q NLB_Rueck*.csv
del /q *NORDLBRUECK*.xlsm
REM *************************************************************************************************************************************************************************
REM LogFiles zippen & archivieren
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_PROD\PROTOKOLL\LB
zip -m F:\TXS_009_PROD\PROTOKOLL\LB\RMInfosRead_%Zip-Date-Suffix%.zip Infos_ReadRueck_PS_*.log
zip -m F:\TXS_009_PROD\PROTOKOLL\LB\RMInfosWrite_%Zip-Date-Suffix%.zip Infos_WriteRueck_PS_*.log
del /q Meldung.txt
REM *************************************************************************************************************************************************************************
REM Jetzt parallel Aufruf des generischen Reports NORDLBRUECK ueber die drei hinterlegten Reportjobs NLB_RUECK_FL/HK/SH .. Standard ini. da explizite Dateiangabe
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

REM SPA - 20200128 Nicht mehr PARALLEL, sondern HINTEREINANDER. Kein Start, sondern Call. Damit auch kein Aufruf auf das Warten vom Ende - Powershell warten_auf_process_ende..
REM SPA - 20200128 start txsjobserver.exe -execute=report.NLB_RUECK_FL -basedate=%mybasedate% -applog=%TXSProtokolldir%\applog_RUECK_FL.log -errlog=%TXSProtokolldir%\errlog_RUECK_FL.log -perflog=%TXSProtokolldir%\perflog_RUECK_FL.log

call txsjobserver.exe -execute=report.NLB_RUECK_SH -basedate=%mybasedate% -applog=%TXSProtokolldir%\applog_RUECK_SH.log -errlog=%TXSProtokolldir%\errlog_RUECK_SH.log -perflog=%TXSProtokolldir%\perflog_RUECK_SH.log

call txsjobserver.exe -execute=report.NLB_RUECK_HK -basedate=%mybasedate% -applog=%TXSProtokolldir%\applog_RUECK_HK.log -errlog=%TXSProtokolldir%\errlog_RUECK_HK.log -perflog=%TXSProtokolldir%\perflog_RUECK_HK.log

REM SPA - 20200128 powershell F:\TXS_009_PROD\PROGRAMM\LB\warten_auf_process_ende_txsjobserver.ps1 >> %TXSProtokolldir%\process_ende.log

REM *************************************************************************************************************************************************************************
REM Es sollten jetzt drei Reports vorliegen im Verzeichnis F:\TXS_009_REPORTS\NLBRUECK
REM *************************************************************************************************************************************************************************
REM ToDo 1 .. Datumsqualifier weg
cd F:\TXS_009_REPORTS\NLBRUECK
REM SPA - 20200128 ren FL_NORDLBRUECK*.xlsm NLB_Rueck_FL.xlsm
ren SH_NORDLBRUECK*.xlsm NLB_Rueck_SH.xlsm
ren HK_NORDLBRUECK*.xlsm NLB_Rueck_HK.xlsm
REM *************************************************************************************************************************************************************************
REM ToDo 2 .. Umwandlung von xlsm -> csv
REM *************************************************************************************************************************************************************************
powershell F:\TXS_009_PROD\PROGRAMM\LB\XLSM2CSV_RUECK.ps1
REM *************************************************************************************************************************************************************************
REM ToDo 3 .. Die drei generischen Reports einlesen und nach diversen Kriterien bearbeiten .. dazu ins Script schauen
REM *************************************************************************************************************************************************************************
powershell F:\TXS_009_PROD\PROGRAMM\LB\ReadRueckAll.ps1 > %NLBProtokolldir%\Infos_ReadRueck_PS_%mybasedate%.log
if %errorlevel%==1 goto fehler
REM *************************************************************************************************************************************************************************
REM ToDo 4 .. Die schnellste Variante zum Konkatenieren, der Standardcopy
REM *************************************************************************************************************************************************************************
cd F:\TXS_009_REPORTS\NLBRUECK
REM SPA - 20200128 copy /Y NLBHK_Rueck.txt+NLBFL_Rueck.txt+NLBSH_Rueck.txt NLB_Rueck.txt
copy /Y NLBHK_Rueck.txt+NLBSH_Rueck.txt NLB_Rueck.txt
REM *************************************************************************************************************************************************************************
REM ToDo 5 .. Die SAP CMS - Ausgabedateien erstellen und die Hilfsdatei AbgangFiltern aktualisieren
REM *************************************************************************************************************************************************************************
REM AbgangFiltern in das Arbeistverzeichnis holen
copy /Y F:\TXS_009_PROD\DATEN\LB\AbgangFilternSi.txt F:\TXS_009_REPORTS\NLBRUECK\AbgangFiltern.txt
REM Eine Zwischensicherung
copy /Y F:\TXS_009_PROD\DATEN\LB\AbgangFilternSi.txt F:\TXS_009_REPORTS\NLBRUECK\AbgangFiltern.save
REM Jetzt wird wieder fuer ein paar Minuten gearbeitet
REM *************************************************************************************************************************************************************************
powershell F:\TXS_009_PROD\PROGRAMM\LB\WriteRueckAll.ps1 > %NLBProtokolldir%\Infos_WriteRueck_PS_%mybasedate%.log
if %errorlevel%==1 goto fehler
REM Jetzt liegt alles bereit ... es muss noch alles ins Verzeichnis LB, damit das eigentliche Aufrufscript es auch alles findet
REM Die SAP CMS-Dateien
copy /Y F:\TXS_009_REPORTS\NLBRUECK\TXS2CMS_004.csv F:\TXS_009_PROD\DATEN\LB\RueckmeldungSAPCMS_BLB.txt
copy /Y F:\TXS_009_REPORTS\NLBRUECK\TXS2CMS_009.csv F:\TXS_009_PROD\DATEN\LB\RueckmeldungSAPCMS_NLB.txt
REM und wieder die AbgangFiltern-Datei
copy /Y F:\TXS_009_REPORTS\NLBRUECK\AbgangFilternNeu.txt F:\TXS_009_PROD\DATEN\LB\AbgangFilternSi.txt
REM *************************************************************************************************************************************************************************
REM ToDo 6 .. Bereinigung der Zwischendateien soll in den Scripten erfolgen => kein ToDo
REM Nicht ueber den fehler laufen 
goto Ende
REM *************************************************************************************************************************************************************************
REM REM _Start_Reports_NLB_RUECK.cmd - Ende
REM *************************************************************************************************************************************************************************
REM Fehlerroutine bedeutet .. keine Datei versandt alles Infos zusammen
:fehler
cd F:\TXS_009_PROD\PROTOKOLL\LB
copy /Y Infos*.log Meldung.txt
powershell F:\TXS_009_PROD\PROGRAMM\LB\nlbrueck_versand.ps1 
exit 1
# Hier geht es immer raus 
:Ende


