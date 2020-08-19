rem SPA 20191218 - Spiegelung 
rem ########################################################################
rem START SPIEGELUNG INS KEV - BARWERTSYSTEM
rem ########################################################################
REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%
REM Time-Suffix
set Time-Suffix=%time:~0,2%%time:~3,2%%time:~6,2%
if "%Time-Suffix:~0,1%"==" " set Time-Suffix=0%Time-Suffix:~1,6%
REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%

REM Datenbanken
set DBProd=DNLBPROD
set DBBarw=DNLBBARW

REM Passwörter
set user=cam
set pwd_prod=<###pwd###>
set pwd_barw=<###pwd###>

rem EXPORT
expdp %user%/%pwd_prod%@%DBProd% schemas=CAM DIRECTORY=spiegel DUMPFILE=spiegel_cam.dmp NOLOGFILE=y parallel=2 reuse_dumpfiles=y
rem ab hier kann in Produktion gearbeitet werden

F:
rem Schema löschen und neu anlegen
cd F:\TXS-Sicherung\KEV
call Drop_and_Create_cam_DNLBBARW.cmd

rem import
impdp %user%/%pwd_barw%@%DBBarw% schemas=CAM DIRECTORY=spiegel DUMPFILE=spiegel_cam.dmp NOLOGFILE=y

rem ########################################################################
rem ENDE SPIEGELUNG INS BARWERTSYSTEM
rem ########################################################################
REM Bei CAM müssen noch zwei DBSetups gemacht werden
REM a) DBUpdate
F:
cd F:\CAM_SPIEGELUNG\Update
call _start_UpdateDB.cmd
cd F:\CAM_SPIEGELUNG\Update
ren log log_%Date-Suffix%%Time-Suffix%
zip -m -r SpiegelungLog_Update_%Zip-Date-Suffix%.zip log_*
REM b) Prepare
F:
cd F:\CAM_SPIEGELUNG\CamPrep
call _start_prepare_CAM.cmd
cd F:\CAM_SPIEGELUNG\CamPrep
ren log log_%Date-Suffix%%Time-Suffix%
zip -m -r SpiegelungLog_PrePare_%Zip-Date-Suffix%.zip log_*
REM c) Knotenaktualisierung
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_BARW
call _Start_BARW_KEV_Automatiken.cmd
REM Feddich

rem PROST bedienen
FOR /F "tokens=1-4 delims= " %%A in (F:\CAM_009_PROD\STATUS\LB\Prost_Start_Spiegelung.txt) do (
   java -jar F:\PROST\KEV\Abschluss.jar de.nordlbit.prost.FilePing.Abschluss F:\PROST\KEV\prost.ini %%A %%B %%C %%D
)

exit