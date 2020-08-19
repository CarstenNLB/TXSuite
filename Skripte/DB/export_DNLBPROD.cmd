rem expdp txsadmin/gammaNLB0@DNLBPROD logfile=export.log schemas=TXSADMIN file=DNLBPROD_20170825_Isabella.dmp
expdp txsadmin/<###pwd###>@DNLBPROD logfile=exportprod.log schemas=TXSADMIN DIRECTORY=spiegel DUMPFILE=DNLBPROD_20200629_SAPCMS.dmp reuse_dumpfiles=y
rem expdp txsadmin/<###pwd###>@DNLBPROD schemas=TXSADMIN DIRECTORY=spiegel DUMPFILE=DNLBPROD_20171127.dmp reuse_dumpfiles=y
pause