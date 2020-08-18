REM TT 20190723 - KEV Download BuBa
REM ACHTUNG Skipt geht, aber in der INI wird ein User zum Download wird benötigt. (Tripp)
REM SPA 20191207 - Time
REM SPA 20200731 - MACCs-Vorbereitung
REM SPA 20200801 - MACCs-Umstellung

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%
REM Time-Suffix
set Time-Suffix=%time:~0,2%%time:~3,2%%time:~6,2%
if "%Time-Suffix:~0,1%"==" " set Time-Suffix=0%Time-Suffix:~1,6%

F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD


rem ------------TESTE Download --------------------

REM call CAM.exe -env="F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\CAM_Buba.ini" -kev_download="KEVEB";writefiles
call CAM.exe     -env="F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\CAM_Buba.ini" -kev_download=MACBK;writefiles
echo %errorlevel% > F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_KEVEB_error.log
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAM_KEV_DL_B_%Date-Suffix%%Time-Suffix%.log

