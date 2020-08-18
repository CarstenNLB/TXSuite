REM 20181130 / SU / tägliche Liefermeldung erstellen und verschicken

REM Alten Stand löschen, die Hitorie findet sich in den Tabellen, siehe u.s. SQL.
del /q F:\TXS_009_PROD\PROTOKOLL\LB\nlbstat.txt

REM Aufruf des SQLs
sqlplus txsadmin/<###pwd###>@DNLBPROD @F:\TXS_009_PROD\PROGRAMM\LB\nlbstat.sql

REM Aufruf mailversand
powershell F:\TXS_009_PROD\PROGRAMM\LB\nlbstats_versand.ps1