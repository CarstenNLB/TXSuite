@echo off
REM 20180525 spa - Einbau Protokollierung mit Mailversand
REM Aufruf von DBSichProt_checker.ps1 .. fuer PFAND und REFI
REM 20191129 spa -> CAM
REM Neuer Suffix
set Tages-Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%


set PATH=F:\app\product\12.1.0\client_1\BIN;%PATH%

net use X: /DELETE
net use X: \\nlbprod11-vf\shrtxs$ /USER:kbk\TXSTRV01 <###pwd###>

REM Leerdateien anlegen, da ansonsten der Export fehlschlaegt
type NUL > X:\DNLBPROD_Mo.dmp
type NUL > X:\DNLBPROD_Refi_Mo.dmp
type NUL > X:\DNLBPROD_CAM_Mo.dmp
type NUL > X:\DNLBPROD_CAMB_Mo.dmp

REM Sicherung Pfandbrief, Refi, CAM und CAMB erstellen
F:
cd \Tagessicherung
REM Alte Dateien loeschen
del /q F:\Tagessicherung\DNLBPROD_Mo.zip
del /q F:\Tagessicherung\DNLBPROD_Refi_Mo.zip
del /q F:\Tagessicherung\DNLBPROD_CAM_Mo.zip
del /q F:\Tagessicherung\DNLBPROD_CAMB_Mo.zip
REM 20180525 spa - Loeschen der Protokolldateien - Start
del /q F:\Tagessicherung\Oracle_DBexport_Pfand_*.log
del /q F:\Tagessicherung\Oracle_DBexport_Refi_*.log
del /q F:\Tagessicherung\Oracle_DBexport_CAM_*.log
del /q F:\Tagessicherung\Oracle_DBexport_CAMB_*.log
REM 20180525 spa - Loeschen der Protokolldateien - Ende

REM Pfandbrief exportieren - 20180528 Protokolldatei eingebaut statt NOLOGFILE=y
expdp txsadmin/<###pwd###>@DNLBPROD schemas=TXSADMIN DIRECTORY=spiegel DUMPFILE=DNLBPROD_Mo.dmp LOGFILE=export_pfand.log parallel=2 reuse_dumpfiles=y
REM Refi exportieren  - 20180528 Protokolldatei eingebaut statt NOLOGFILE=y
expdp txsrefi/<###pwd###>@DNLBPROD schemas=TXSREFI DIRECTORY=spiegel DUMPFILE=DNLBPROD_Refi_Mo.dmp LOGFILE=export_refi.log parallel=2 reuse_dumpfiles=y
REM KEV CAM exportieren - 20180528 Protokolldatei eingebaut statt NOLOGFILE=y
expdp cam/<###pwd###>@DNLBPROD schemas=CAM DIRECTORY=spiegel DUMPFILE=DNLBPROD_CAM_Mo.dmp LOGFILE=export_cam.log parallel=2 reuse_dumpfiles=y
expdp cambatch/<###pwd###>@DNLBPROD schemas=CAMBATCH DIRECTORY=spiegel DUMPFILE=DNLBPROD_CAMB_Mo.dmp LOGFILE=export_camb.log parallel=2 reuse_dumpfiles=y

REM 20180525 spa - Protokolldateien kopieren - Start
copy X:\export_pfand.log F:\Tagessicherung\Oracle_DBexport_Pfand_%Tages-Date-Suffix%.log
copy X:\export_Refi.log F:\Tagessicherung\Oracle_DBexport_Refi_%Tages-Date-Suffix%.log
copy X:\export_cam.log F:\Tagessicherung\Oracle_DBexport_CAM_%Tages-Date-Suffix%.log
copy X:\export_camb.log F:\Tagessicherung\Oracle_DBexport_CAMB_%Tages-Date-Suffix%.log
REM 20180525 spa - Protokolldateien kopieren - Ende

REM Pfandbrief zippen
zip -m X:\DNLBPROD_Mo.zip X:\DNLBPROD_Mo.dmp
copy X:\DNLBPROD_Mo.zip F:\Tagessicherung\DNLBPROD_Mo.zip
del /q X:\DNLBPROD_Mo.zip

REM Refi zippen
zip -m X:\DNLBPROD_Refi_Mo.zip X:\DNLBPROD_Refi_Mo.dmp
copy X:\DNLBPROD_Refi_Mo.zip F:\Tagessicherung\DNLBPROD_Refi_Mo.zip
del /q X:\DNLBPROD_Refi_Mo.zip

REM CAM zippen
zip -m X:\DNLBPROD_CAM_Mo.zip X:\DNLBPROD_CAM_Mo.dmp
copy X:\DNLBPROD_CAM_Mo.zip F:\Tagessicherung\DNLBPROD_CAM_Mo.zip
del /q X:\DNLBPROD_CAM_Mo.zip

REM CAMB zippen
zip -m X:\DNLBPROD_CAMB_Mo.zip X:\DNLBPROD_CAMB_Mo.dmp
copy X:\DNLBPROD_CAMB_Mo.zip F:\Tagessicherung\DNLBPROD_CAMB_Mo.zip
del /q X:\DNLBPROD_CAMB_Mo.zip

net use X: /DELETE

REM 20180525 spa - Verarbeitung der Protokolldateien mit Mailversand - Start
powershell F:\Tagessicherung\DBSichProt_checker.ps1 -Typ Pfandbrief -Path F:\Tagessicherung\Oracle_DBexport_Pfand_%Tages-Date-Suffix%.log
powershell F:\Tagessicherung\DBSichProt_checker.ps1 -Typ RefiReg -Path F:\Tagessicherung\Oracle_DBexport_Refi_%Tages-Date-Suffix%.log
powershell F:\Tagessicherung\DBSichProt_checker.ps1 -Typ CAM -Path F:\Tagessicherung\Oracle_DBexport_CAM_%Tages-Date-Suffix%.log
REM 20180525 spa - Verarbeitung der Protokolldateien mit Mailversand - Ende

@echo on
exit