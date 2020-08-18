##############################################################################
## keveinr_versand.ps1
## Skript zum Versand von F:\CAM_009_PROD\PROTOKOLL\LB\Meldung.txt
## Aufruf während des Versandes der TXSKEV-Einreichungsquittierung
## Stephan Pape / 101062 / 20191203
##############################################################################
<#
.SYNOPSIS
Skript zum Versand von F:\CAM_009_PROD\PROTOKOLL\LB\Meldung.txt
.AUTHOR
Stephan Pape / 101062 
#>

## mail zusammenbauen und verschicken

$date = get-date -format g
$sub = "TXSKEV: Fehler bei der Verarbeitung der Rueckmeldung an ABACUS & CMC & OTC " + $date

$server = "172.20.144.53"

Send-MailMessage -From "BetriebTXS@nordlb.de" -to "BetriebTXS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments "F:\CAM_009_PROD\PROTOKOLL\LB\Meldung.txt" 