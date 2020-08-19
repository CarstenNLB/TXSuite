REM CT - angelegt am 15.04.2013 
REM SP - 15.09.2015 - xcopy ins Unterverzeichnis FMO
REM SU - 07.01.2016 - xcopy nach FMO auskommentiert, um Laufzeit in der Verarbeitung zu gewinnen.

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

set Host-Date-Suffix=%mybasedate:~2,2%%mybasedate:~5,2%%mybasedate:~8,2%

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo FRISCO-Datei von W: holen
xcopy /y W:\A1PBAT.NT.I009.LM2TXS.CFXML.D%Host-Date-Suffix% F:\TXS_009_TRANSFER\FRISCO\IMPORT\

del /q W:\A1PBAT.NT.I009.LM2TXS.CFXML.D%Host-Date-Suffix%

net use W: /DELETE /y

F:
cd F:\TXS_009_PROD\PROGRAMM\LB
call FRISCO.bat

cd F:\TXS_009_TRANSFER\FRISCO\IMPORT
zip -m F:\TXS_009_TRANSFER\FRISCO\IMPORT\%Date-Suffix%.zip A1PBAT.NT.I009.LM2TXS.CFXML.D%Host-Date-Suffix%

FOR /F "tokens=1-4 delims= " %%A in (F:\TXS_009_PROD\STATUS\LB\Prost_FRISCO.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

exit