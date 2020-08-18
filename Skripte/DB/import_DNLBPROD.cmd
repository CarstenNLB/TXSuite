rem exit  - NOLOGFILE=y 
rem Produktion
set PATH=F:\app\product\12.1.0\client_1\BIN;%PATH%
impdp txsadmin/<###pwd###>@DNLBPROD full=n schemas=TXSADMIN directory=SPIEGEL dumpfile=DNLBPROD_Mi.dmp
pause