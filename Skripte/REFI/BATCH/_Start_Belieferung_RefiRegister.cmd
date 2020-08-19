REM 20190430 SPA Spiegelung BARW abgestellt zu Testzwecken 
REM 20180627 SPA Loeschen Uerberwachungsmonitor im UEMonitor-Script hinterlegt
REM              Testlauf Barwert hier raus
REM CT - angelegt am 10.10.2017 - Anpassung SP 21.12.2017 LIQ-Verarbeitung eingebaut

F:
cd F:\REF_009_PROD

REM Importdateien erstellen
call F:\REF_009_PROD\_Start_Batch_009_RefiRegister.bat 

REM LIQ-Verarbeitung
call F:\REF_009_PROD\_Start_Batch_009_LIQ2RefiRegister.bat

F:
cd F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD

REM Import starten
call F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\_Start_Import_FULL_RefiRegister_PROD_NLB.cmd

REM Ueberwachungsmonitor
call F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\_Start_Ueberwachungsmonitor.cmd

REM CT 15.05.2020 - Fertigereignis vor Spiegelung schreiben - Spiegelung ausgeschaltet

rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\REF_009_PROD\STATUS\LB\Prost_Belieferung_RefiRegister.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)

REM Spiegelung ins Barwertsystem
call F:\REF_009_PROD\PROGRAMM\TXS\REF_PROD\_Start_Spiegelung_RefiRegister_NLB.cmd

rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\REF_009_PROD\STATUS\LB\Prost_Belieferung_RefiRegister.txt) do (
  java -jar F:\PROST\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\prost.ini %%A %%B %%C %%D
)
