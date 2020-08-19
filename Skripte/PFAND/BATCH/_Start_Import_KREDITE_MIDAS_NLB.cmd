REM Kredite MIDAS importieren
REM spa 20180320 aad-Parameter eingefuehrt 

REM CT 19.05.2020 - SPOT Release 

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM CT 20160510
REM FSp 20160513 "-aad=AMIDFLUG" in die Aufrufzeile eingefügt
REM -aad=AMIDFLUG entfernt / CT/SU 20160808
REM -aad=AMIDFLUG wieder aufgenommen / FS/SU 201600915

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

F:
cd F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD

rem ------------STARTE KREDITE MIDAS-------------
call TXSBatch.exe -env="F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\txs.ini" -v -nodelcf -nos -aad=AMIDFLUG,AMIDPFBG -basedate=%mybasedate% -import=F:\TXS_009_PROD\IMPORT\Kredite_MIDAS_TXS.xml
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Kredite_MIDAS_%Date-Suffix%.log

call F:\PROST\CheckLog.bat
if %errorlevel%==1 goto Fehler

rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Import_Kredite_MIDAS.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

:Fehler