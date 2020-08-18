REM Log-Date-Suffix
set Log-Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%

call TXSBatch.exe -rueckmeldungshpo -rtx="NLB-PfandBG.Hypothekenpfandbrief, NLB-PfandBG.™ffentlicher Pfandbrief, Schiffspfandbrief.Schiffspfandbrief, Flugzeugpfandbrief.Flugzeugpfandbrief" -responseall=F:\TXS_009_PROD\DATEN\LB\Rueckmeldung_NLB.xml
copy F:\TXS_009_PROD\DATEN\LB\Rueckmeldung_NLB.xml F:\TXS_009_PROD\DATEN\LB\Rueckmeldung_NLBshpo.xml
copy F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\TXSBatch.log F:\TXS_009_PROD\PROGRAMM\TXS\TXS_PROD\Protokolle\Archiv\TXSBatch_Rueckmeldung_Sicherheiten_%Log-Date-Suffix%.log
