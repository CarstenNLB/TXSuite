##############################################################################
## DBSichProt_checker.ps1
## Skript zur Erstellung eines Protokolls für die DB-Sicherung
## Stephan Pape / 101062 / 20180525
##############################################################################
<#
.SYNOPSIS
Skript zur Protokollierung und Meldung der DB-Sicherung per Mail
.AUTHOR
Stephan Pape / 101062 / -5874
#>
Param (
    [parameter(Mandatory=$true)][string]$Typ,
    [parameter(Mandatory=$true)][string]$Path
) 
$datum = Get-Date
$Ausgabe = 'Oracle-DB-Sicherung Produktion(' + $Typ + ') - Datum: ' + $datum.Day + '.' + $datum.Month + '.' + $datum.Year
#Datei einlesen
$file = Get-Content $Path 
$header = $Ausgabe + ' war nicht erfolgreich;'
#Suchen, ob successfully completed enthalten
foreach ($str in $file) 
{ 
	if ($str -like '*successfully completed*') { 
	 $header = $Ausgabe + ' war erfolgreich;'
	}	 
}
$body = get-content $Path
$smtp = New-Object Net.Mail.SmtpClient("172.20.144.53")
$sendby = "TXSDBSave@nordlb.de (do not reply)"
$sendto = "BetriebTXS@nordlb.de"
$smtp.Send($sendby,$sendto,$header,$body)
return $true
