##############################################################################
## cmc_checker.ps1
## Skript um die korrekte Erstellung der cmc-reports zu kontrollieren
## Aufruf während Erstellung der cmc reports
## Florian Spata / 101062 / 20150928
## FS 20150928
## FS 20160225 Ändern des Betreffs der Statusmail
## SU 20161017 repstat aufgenommen
##############################################################################
<#
.SYNOPSIS
Skript um die korrekte Erstellung der cmc-reports zu kontrollieren
.AUTHOR
Florian Spata / 101062 ext / -9987
#>


$Reportfile = Get-ChildItem -Path F:\TXS_009_REPORTS\CMC\* -Include ReportTXS_009_CMC_EB_KREDIT*.txt
 
 if ($Reportfile.length -gt 5KB)
 {
 $mailtext = 'Alles okay'
 $headerstart = ' Erfolgreiche Versendung '
 }
 else
 {
 $mailtext = 'Reports sind kaputt oder nicht im Verzeichnis'
 $headerstart = ' Achtung - Fehler bei der Erstellung '
 }
#________________________________________
#Rückgabewert

if ($Reportfile.length -gt 5KB) 
{
 $Rückgabewert = 0
 }
 else
 {
 $Rückgabewert = 1
 }

$Rückgabewert | Out-File F:\TXS_009_PROD\PARAM\cmc_rueck.txt

#________________________________________
## mail zusammenbauen und verschicken


## mailserver
$smtp = New-Object Net.Mail.SmtpClient("smtp.kbk.nordlb.local")

$sendby = "CMC_Reports_Test@nordlb.de (do not reply)"
$sendto = "txsuite@nordlb.de"

$header = $headerstart + ' der ' + ' CMC Reports inkl. Import- und BW-Statistik für ' 

$dptxt = (get-content("F:\TXS_009_PROD\PARAM\dp.txt"))

$header = $header + $dptxt 

##Write-Host $header
##Write-Host $body

$body = get-content F:\TXS_009_PROD\PROTOKOLL\LB\repstat.txt -delimiter ";"

$smtp.Send($sendby,$sendto,$header,$body)

return $Rückgabewert