REM SPA - angelegt am 18.04.2018 wie Gattungsdaten 
REM SPOT BUBA - Datei 

REM Date-Suffix
set Date-Suffix=%date:~6,4%%date:~3,2%

net use W: /DELETE /y
net use W: \\nlbprod11-vf\txstvl$\receive <###pwd###> /USER:KBK\TXSTRV01

rem echo BuBa-Datei von W: holen
dir W:\SPOT2TXS_BUBA_004*.* >> F:\TXS_009_Transfer\SPOTBUBA\IMPORT\ARCHIV\TVL.log
xcopy /y W:\SPOT2TXS_BUBA_004_*.csv F:\TXS_009_Transfer\SPOTBUBA\IMPORT\ARCHIV
del /q W:\SPOT2TXS_BUBA_004_*.csv

net use W: /DELETE /y

F:
cd F:\TXS_009_Transfer\SPOTBUBA\IMPORT\ARCHIV
zip -m F:\TXS_009_Transfer\SPOTBUBA\IMPORT\ARCHIV\SPOTBUBA_%Date-Suffix%.zip SPOT2TXS_BUBA_004_*.csv

exit
