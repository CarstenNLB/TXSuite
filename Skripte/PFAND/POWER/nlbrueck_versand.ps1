##############################################################################
## nlbrueck_versand.ps1
## Skript zum Versand von F:\TXS_009_PROD\PROTOKOLL\LB\Meldung.txt
## Aufruf während der NORDLB-Rueckmeldung im Falle eines Fehlers
## Stephan Pape / 101062 / 2018113090730
##############################################################################
<#
.SYNOPSIS
Skript zum Versand von F:\TXS_009_PROD\PROTOKOLL\LB\Meldung.txt
.AUTHOR
Stephan Pape / 101062 
#>

## mail zusammenbauen und verschicken

$date = get-date -format g
$sub = "TXS: Fehler bei der Rueckmeldung " + $date

$server = "172.20.144.53"

Send-MailMessage -From "BetriebTXS@nordlb.de" -to "BetriebTXS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments "F:\TXS_009_PROD\PROTOKOLL\LB\Meldung.txt" 