REM 20200520 SPA basedate mit eingebaut ..
REM Log-Date-Suffix
set Log-Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

REM basedate ermitteln
FOR /F "TOKENS=1" %%i IN (F:\TXS_009_PROD\PARAM\dp.txt) do (SET mybasedate=%%i)

call TXSBatch.exe -basedate=%mybasedate% -rtx="NLB-PfandBG.Hypothekenpfandbrief, NLB-PfandBG.™ffentlicher Pfandbrief, ™PG.Hypothekenpfandbrief, ™PG.™ffentlicher Pfandbrief, Schiffspfandbrief.Schiffspfandbrief, Flugzeugpfandbrief.Flugzeugpfandbrief" -responseall=F:\TXS_009_PROD\DATEN\LB\Rueckmeldung_NLB.xml
REM call TXSBatch.exe -rtx="NLB-PfandBG.Hypothekenpfandbrief, NLB-PfandBG.™ffentlicher Pfandbrief, Schiffspfandbrief.Schiffspfandbrief, Flugzeugpfandbrief.Flugzeugpfandbrief" -responseall=F:\TXS_009_PROD\DATEN\LB\Rueckmeldung_NLB.xml
copy F:\TXS_009_PROD\DATEN\LB\Rueckmeldung_NLB.xml F:\TXS_009_PROD\DATEN\LB\Rueckmeldung_NLBnurfi.xml
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Rueckmeldung_Finanzgeschaefte_%Log-Date-Suffix%.log
