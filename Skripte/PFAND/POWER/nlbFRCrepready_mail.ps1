##############################################################################
## nlbFRCrepready_mail.ps1
## Skript zum Versand der Erledigtmeldung an FRC
## Aufruf während desd täglichen TXS-imports 
## Stefan Unnasch / 101062 / 20190119
##############################################################################
<#
.SYNOPSIS
Skript zum Versand der "PROD ist fertig"-mail
.AUTHOR
Stefan Unnasch / 101062 ext
#>

## mail zusammenbauen und verschicken

$date = get-date -format g
$sub = "TXS: Die FRC-Reports wurden erzeugt"

$server = "172.20.144.53"

Send-MailMessage -From "BetriebTXS@nordlb.de" -to "liquidity_risk@nordlb.de" -Cc "BetriebTXS@nordlb.de" -Subject $sub -smtpserver $server 