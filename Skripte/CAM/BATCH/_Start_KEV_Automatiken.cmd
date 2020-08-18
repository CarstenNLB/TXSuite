REM SPA 20190611 - Initialer Aufbau KEV Automatiken
REM SPA 20191202 - Time
REM                Knotenberechnung jeglicher Art
REM                -cpss    ?   
REM                -cn      Ja beinhaltet alle folgende Schalter
REM                -css     Ja
REM                -cna     Ja
REM                -cnv     Ja
REM                -cndcd   Ja
REM                -cnk     Ja
REM                -vs      Ja

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%%date:~0,2%
REM Time-Suffix
set Time-Suffix=%time:~0,2%%time:~3,2%%time:~6,2%
if "%Time-Suffix:~0,1%"==" " set Time-Suffix=0%Time-Suffix:~1,6%

F:
cd F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD

rem ------------STARTE Knotenberechnung--------------------
call CAM.exe -env=F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\cam.ini -cn 
copy F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\CAM_Dialog.log F:\CAM_009_PROD\PROGRAMM\TXS\CAM_PROD\Protokolle\Archiv\CAM_Automatik_%Date-Suffix%%Time-Suffix%.log


