REM SPA 13.04.2018 wegen Lock der dp-Datei durch Friscoverarbeitung
REM CT 05.02.2018 Importiert die Marktdaten und berechnet die Zinsszenarien

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM Hier 13.04.2018 Aenderungsanfang --------------------------------------------------
REM Rueckgabewert Powerscript ueber eigene Datei, wenn da, dann Fehler und Mailversand
del /q F:\TXS_009_PROD\PARAM\dptest.err

powershell F:\TXS_009_PROD\PROGRAMM\LB\dpparam_checker.ps1
if exist F:\TXS_009_PROD\PARAM\dptest.err goto Fehler
REM Hier 13.04.2018 Aenderungsende ----------------------------------------------------

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

rem +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

rem ------------STARTE MARKTDATEN--------------------
call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nodelcf -basedate=%mybasedate% -import=F:\TXS_009_PROD\IMPORT\Marktdaten_TXS.xml
echo Status: %errorlevel% > F:\TXS_009_PROD\STATUS\LB\Status_MA_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF not %errorlevel%==0 echo Text: Marktdaten fehlerhaft !! >> F:\TXS_009_PROD\STATUS\LB\Status_MA_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
IF %errorlevel%==0 echo Text: Marktdaten beliefert !! >> F:\TXS_009_PROD\STATUS\LB\Status_MA_009_%date:~0,2%_%date:~3,2%_%date:~6,4%.txt
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Marktdaten_%Date-Suffix%.log
rem +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

call F:\PROST\CheckLog.bat
if %errorlevel%==1 goto Fehler

REM Verzeichnis anlegen und kopieren
rem mkdir F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

rem copy F:\TXS_009_PROD\IMPORT\Marktdaten_TXS.xml F:\TXS_009_PROD\IMPORT\%date:~6,4%%date:~3,2%%date:~0,2%

rem del /q F:\TXS_009_PROD\IMPORT\Marktdaten_TXS.xml

rem +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
rem Szenarien hier direkt aufgenommen SU 20171116
call F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\_Start_Zinsszenarien_NLB.cmd
rem +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Import_MDV.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

:Fehler


