REM CT - angelegt am 15.01.2015
REM CT 21.07.2017 - Anpassungen FITS

REM echo Start Gattungsdaten-Request >> F:\TXS_004_PROD\PROTOKOLL\LB\Gattungsdaten_Request.txt

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

net use T: /DELETE /y
net use T: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo Wertpapiere von T: holen
xcopy /y T:\SPOT2TXS_WP_BESTAND_004_%Rest-Date-Suffix%.csv F:\TXS_004_PROD\DATEN\LB\SPOT2TXS_WP_BESTAND_004.csv
xcopy /y T:\SPOT2TXS_WP_BESTAND_004_%Rest-Date-Suffix%.csv F:\TXS_004_TRANSFER\SPOTWP\IMPORT\Archiv

del /q T:\SPOT2TXS_WP_BESTAND_004_%Rest-Date-Suffix%.csv 

net use T: /DELETE /y

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_004_PROD\STATUS\LB\Prost_Gattungsdatenrequest.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)
