##############################################################################
## nlbstats_versand.ps1
## Skript zum Versand von F:\TXS_009_PROD\PROTOKOLL\LB\nlbstat.txt
## Aufruf während desd täglichen TXS-imports 
## Stefan Unnasch / 101062 / 20181130
##############################################################################
<#
.SYNOPSIS
Skript zum Versand von F:\TXS_009_PROD\PROTOKOLL\LB\nlbstat.txt
.AUTHOR
Stefan Unnasch / 101062 ext
#>

## mail zusammenbauen und verschicken

$date = get-date -format g
$sub = "TXS: Importstatistik " + $date

$server = "172.20.144.53"

Send-MailMessage -From "BetriebTXS@nordlb.de" -to "txs-m-nlb@nordlb.de" -Cc "BetriebTXS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments "F:\TXS_009_PROD\PROTOKOLL\LB\nlbstat.txt" 