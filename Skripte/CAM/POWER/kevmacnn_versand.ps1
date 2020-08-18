##############################################################################
## kevmacnn_versand.ps1
## Skript zum Versand von F:\CAM_009_PROD\PROTOKOLL\LB\Meldung.txt
## Aufruf während des Versandes der TXS MACCs-Nachrichtendatei
## Stephan Pape / 101062 / 20200818
##############################################################################
<#
.SYNOPSIS
Skript zum Versand von F:\CAM_009_PROD\PROTOKOLL\LB\Meldung.txt
.AUTHOR
Stephan Pape / 101062 
#>

## mail zusammenbauen und verschicken

$date = get-date -format g
$sub = "TXS MACCs: Fehler beim Versand der Nachrichtendatei " + $date

$server = "172.20.144.53"

Send-MailMessage -From "BetriebTXS@nordlb.de" -to "BetriebTXS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments "F:\CAM_009_PROD\PROTOKOLL\LB\Meldung.txt" 