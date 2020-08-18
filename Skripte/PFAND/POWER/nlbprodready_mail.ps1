##############################################################################
## nlbprodready_mail.ps1
## Skript zum Versand der "PROD ist fertig"-mail
## Aufruf während desd täglichen TXS-imports 
## Stefan Unnasch / 101062 / 20181130
##############################################################################
<#
.SYNOPSIS
Skript zum Versand der "PROD ist fertig"-mail
.AUTHOR
Stefan Unnasch / 101062 ext
#>

## mail zusammenbauen und verschicken

$date = get-date -format g
$sub = "TXS: Das Produktionssystem steht Ihnen zur Verfuegung."

$server = "172.20.144.53"

Send-MailMessage -From "BetriebTXS@nordlb.de" -to "txs-m-nlb@nordlb.de" -Cc "BetriebTXS@nordlb.de" -Subject $sub -smtpserver $server 