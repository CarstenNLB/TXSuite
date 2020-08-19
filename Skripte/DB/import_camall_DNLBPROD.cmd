set PATH=F:\app\product\12.1.0\client_1\BIN;%PATH%
impdp cambatch/<###pwd###>@DNLBPROD full=n schemas=CAMBATCH logfile=impcambatchprod.log directory=SPIEGEL dumpfile=DNLBPRPR_CAMBATCH_20190614.dmp
pause
set PATH=F:\app\product\12.1.0\client_1\BIN;%PATH%
impdp cam/<###pwd###>@DNLBPROD full=n schemas=CAM logfile=impcamprod.log directory=SPIEGEL dumpfile=DNLBPRPR_CAM_20190614.dmp
pause
