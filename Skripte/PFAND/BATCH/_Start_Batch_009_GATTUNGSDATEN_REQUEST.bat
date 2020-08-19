REM CT - angelegt am 03.12.2014
REM Abholen der Wertpapierbestandsdatei aus SPOT vom TVL-Ordner 
REM Erzeugen und Verschicken des Gattungsdatenrequests an SPOT via TVL-Ordner
REM Achtung, Datum in dp.txt beachten
REM SPA 17.06.2020 - Responsedatei wird auf TVL-Server in das Unterverzeichnis spot kopiert

echo Start Gattungsdaten-Request >> F:\TXS_009_PROD\PROTOKOLL\LB\Gattungsdaten_Request.txt

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

net use T: /DELETE /y
net use T: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo Wertpapiere von T: holen
xcopy /y T:\SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv F:\TXS_009_PROD\DATEN\LB\SPOT2TXS_WP_BESTAND_009.csv
xcopy /y T:\SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv F:\TXS_009_TRANSFER\SPOTWP\IMPORT\Archiv

rem Wertpapier-Bestandsdaten fuer KEV kopieren
copy /y F:\TXS_009_PROD\DATEN\LB\SPOT2TXS_WP_BESTAND_009.csv F:\CAM_009_PROD\DATEN\LB\SPOT2TXS_WP_BESTAND_009.csv

del /q T:\SPOT2TXS_WP_BESTAND_009_%Rest-Date-Suffix%.csv 

net use T: /DELETE /y

echo Verarbeitung Gattungsdaten-Request >> F:\TXS_009_PROD\PROTOKOLL\LB\Gattungsdaten_Request.txt
F:
cd F:\TXS_009_PROD\PROGRAMM\LB
call GattungsdatenRequest.bat

copy F:\TXS_009_PROD\DATEN\LB\TXS2SPOT_GD_009_%Rest-Date-Suffix%.xml F:\TXS_009_TRANSFER\GATTUNGSDATEN\EXPORT
del /q F:\TXS_009_PROD\DATEN\LB\TXS2SPOT_GD_009_%Rest-Date-Suffix%.xml

echo Datum: %date% >> F:\TXS_009_PROD\PROTOKOLL\LB\Gattungsdaten_Request.txt
echo Zeit: %time% >> F:\TXS_009_PROD\PROTOKOLL\LB\Gattungsdaten_Request.txt
 
REM --------------------------------------------------------
REM SU SPOT 20120410 ---------------------------------------
REM SPA SPOT 20200716 Unterverzeichnis spot ----------------

net use Y: /DELETE /y
net use Y: \\nlbprod11-vf\txstvl$\send\spot Edrpwd100 /USER:KBK\TXSTRV01

rem echo Gattungsdaten-Request nach Y >> F:\TXS_009_PROD\PROTOKOLL\LB\Gattungsdaten_Request.txt
copy F:\TXS_009_TRANSFER\GATTUNGSDATEN\EXPORT\TXS2SPOT_GD_009_%Rest-Date-Suffix%.xml Y:

echo TXS2SPOT_GD_009 kopiert >> F:\TXS_009_PROD\PROTOKOLL\LB\Gattungsdaten_Request.txt

cd F:\TXS_009_TRANSFER\GATTUNGSDATEN\EXPORT
zip -m F:\TXS_009_TRANSFER\GATTUNGSDATEN\EXPORT\Archiv\GattungsdatenRequest_%Date-Suffix%.zip TXS2SPOT_GD_009_%Rest-Date-Suffix%.xml

echo TXS2SPOT_GD_009 archiviert >> F:\TXS_009_PROD\PROTOKOLL\LB\Gattungsdaten_Request.txt

net use Y: /DELETE /y

echo Ende Gattungsdaten-Request >> F:\TXS_009_PROD\PROTOKOLL\LB\Gattungsdaten_Request.txt

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_Gattungsdatenrequest.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit