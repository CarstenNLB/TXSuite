REM SPA 20200813 - PROD

F:
REM Anpassung des Pfades .. ohne Unterverzeichnis Scripte, da CAM im Hauptverzeichnis liegt
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD

rem ------------Export und Upload an BuBa --------------------
del /q F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\TextWriterOutput.log
call CAM.exe -env="F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\CAM_Buba.ini" -kev_export=file
REM errorlevel wird nicht auf das LogFile umgeleitet 
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAM_KEV_EXP_OHNEUPL_%Date-Suffix%%Time-Suffix%.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\TextWriterOutput.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAMPROTO_KEV_EXP_OHNEUPL_%Date-Suffix%%Time-Suffix%.log


