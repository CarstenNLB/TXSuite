REM CT - angelegt am 25.09.2013 
REM CT 21.07.2017 - Anpassungen FITS
REM SPA 17.06.2020 - Responsedatei wird auf TVL-Server in das Unterverzeichnis spot kopiert

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Rest-Date-Suffix=%mybasedate:~0,4%%mybasedate:~5,2%%mybasedate:~8,2%

echo Start Kundecopy >> F:\TXS_004_PROD\PROTOKOLL\LB\Kundecopy.txt

echo Datum: %date% >> F:\TXS_004_PROD\PROTOKOLL\LB\Kundecopy.txt
echo Zeit: %time% >> F:\TXS_004_PROD\PROTOKOLL\LB\Kundecopy.txt
 
F:
REM KundeRequestMerger starten
cd F:\TXS_004_PROD\PROGRAMM\LB
call KundeRequestMerger.bat

REM --------------------------------------------------------
REM SU SPOT 20120410 ---------------------------------------
REM SPA SPOT 20200716 Unterverzeichnis spot ----------------

net use Y: /DELETE /y
net use Y: \\nlbprod11-vf\txstvl$\send\spot <###pwd###> /USER:KBK\TXSTRV01

REM Keine Verbindung zum Netzlaufwerk möglich
rem if not exist Y: echo Keine Verbindung zum Netzlaufwerk Y: moeglich >> F:\TXS_004_PROD\PROTOKOLL\LB\Kundecopy.txt

rem echo Kunde-Request nach Y >> F:\TXS_004_PROD\PROTOKOLL\LB\Kundecopy.txt
copy F:\TXS_004_TRANSFER\KUNDE\EXPORT\TXS2SPOT_004_%Rest-Date-Suffix%.xml Y:

echo TXS2SPOT_004 kopiert >> F:\TXS_004_PROD\PROTOKOLL\LB\Kundecopy.txt

F:
cd F:\TXS_004_TRANSFER\KUNDE\EXPORT
zip -m F:\TXS_004_TRANSFER\KUNDE\EXPORT\Archiv\KundeRequest_%Date-Suffix%.zip TXS2SPOT_004_%Rest-Date-Suffix%.xml

echo TXS2SPOT_004 archiviert >> F:\TXS_004_PROD\PROTOKOLL\LB\Kundecopy.txt

net use Y: /DELETE /y

echo Ende Kundecopy >> F:\TXS_004_PROD\PROTOKOLL\LB\Kundecopy.txt

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_004_PROD\STATUS\LB\Prost_KundeRequest.txt) do (
   java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)
