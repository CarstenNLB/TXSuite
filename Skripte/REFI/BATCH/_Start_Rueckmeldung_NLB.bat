REM Log-Date-Suffix
set Log-Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

rem call TXSBatch.exe -rtx="Refinanzierungsregister.Coop eingetragene Genossenschaft" -responseall=F:\REF_009_PROD\DATEN\LB\Rueckmeldung_NLB.xml
call TXSBatch.exe -response=F:\REF_009_PROD\DATEN\LB\Rueckmeldung_NLB.xml

rem call TXSBatch.exe -rtx="NLB-PfandBG.Hypothekenpfandbrief, NLB-PfandBG.™ffentlicher Pfandbrief, ™PG.Hypothekenpfandbrief, ™PG.™ffentlicher Pfandbrief, Schiffspfandbrief.Schiffspfandbrief, Flugzeugpfandbrief.Flugzeugpfandbrief" -responseall=F:\TXS_009_PROD\DATEN\LB\Rueckmeldung_NLB.xml
rem copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Rueckmeldung_%Log-Date-Suffix%.log
