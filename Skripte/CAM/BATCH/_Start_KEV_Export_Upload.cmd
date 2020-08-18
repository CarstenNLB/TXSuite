REM TT 20190730 - KEV Import BuBa
REM 06.08.2019 Thomas Tripp
REM SPA 20191207 - Time
REM     Exportiert Dateien nach F:\CAM_009_PROD\DATEN\Buba\Export
REM     Datei KEV-Einreichung_JJJJ-MM-TT...pdf wird in F:\CAM_009_PROD\DATEN\BuBa\Report abgelegt 
REM     Per Script wird pdf an OE QM Pfandbrief versandt
REM SPA 20191208 Archivierung

REM Monatsqualifier
set Zip-Date-Suffix=%date:~6,4%%date:~3,2%
REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%
REM Time-Suffix
set Time-Suffix=%time:~0,2%%time:~3,2%%time:~6,2%
if "%Time-Suffix:~0,1%"==" " set Time-Suffix=0%Time-Suffix:~1,6%

F:
REM Anpassung des Pfades .. ohne Unterverzeichnis Scripte, da CAM im Hauptverzeichnis liegt
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD

rem ------------Export und Upload an BuBa --------------------
del /q F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\TextWriterOutput.log
call CAM.exe -env="F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\CAM_Buba.ini" -kev_export=file;extranet
echo %errorlevel% > F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_KEV_EXPORT_error.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAM_KEV_EXP_PLUSUPL_%Date-Suffix%%Time-Suffix%.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\TextWriterOutput.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAMPROTO_KEV_EXP_PLUSUPL_%Date-Suffix%%Time-Suffix%.log

REM -- Einreichungsdatei verschicken und archivieren 
F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD
call _Start_Transfer_KEVPDF2OEQM.cmd

REM Importverzeichnis aufräumen - Archivieren der Importdateien
F:
cd F:\CAM_009_PROD\DATEN\BuBa\export
replace /r/a F:\CAM_009_PROD\DATEN\BuBa\export\KEV_Export*.xml F:\CAM_009_PROD\DATEN\BuBa\export\Archiv
del /q F:\CAM_009_PROD\DATEN\BuBa\export\KEV_Export*.xml
F:
cd F:\CAM_009_PROD\DATEN\BuBa\export\Archiv
zip -m F:\CAM_009_PROD\DATEN\BuBa\export\Archiv\BuBaEinr_%Zip-Date-Suffix%.zip KEV_Export*.xml
