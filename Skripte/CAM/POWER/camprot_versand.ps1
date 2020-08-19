##############################################################################
## camprot_versand.ps1
## Skript zum Versand von F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
## Aufruf während der Batchbverarbeitung von KEV auch BuBa
## Stephan Pape / 101062 / 20200710
##############################################################################
<#
.SYNOPSIS
Skript zum Versand von F:\CAM_009_PROD\PROTOKOLL\LB\CAMMeldung.txt
.AUTHOR
Stephan Pape / 101062 
#>

## mail zusammenbauen und verschicken

$date = get-date -format g
$sub = "TXSKEV: Fehler bei der Batch- oder BuBa-Verarbeitung " + $date

$server = "172.20.144.53"

Send-MailMessage -From "BetriebTXS@nordlb.de" -to "stephan.pape@nordlb.de" -Subject $sub -smtpserver $server  -Attachments "F:\CAM_009_PREP\PROTOKOLL\LB\CAMMeldung.txt" 