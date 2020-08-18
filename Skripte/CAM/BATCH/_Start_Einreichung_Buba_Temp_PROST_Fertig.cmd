REM Temporaeres Script .. PROST-Aufgae auf feddich setzen 
F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD
REM Fertigereignis in PROST setzen
FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_Einreichung_Buba.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

