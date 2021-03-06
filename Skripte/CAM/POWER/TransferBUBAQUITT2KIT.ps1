##############################################################################
## TransferBUBAQUITT2KIT.ps1
## Skript zum Versand an OE KIT - speziell Quittierung nach Einreichung
## Aufruf aus _Start_Import_Quittung.cms
## SPA 20200710
##############################################################################
<#
.SYNOPSIS
Skript zum Versand einer Datei
.AUTHOR
SPA / 101062 / -5874
#>

$kevFiles = @(Get-ChildItem -Path F:\CAM_009_PROD\DATEN\BuBa\import\KEVEA\* -Include KEV_Export*.xml)
if ($kevFiles.length -eq 0) {
	echo "Fehler beim Senden der TXS_KEV_Bundesbankquittierungsdatei - Keine Datei vorhanden" 
 	exit 1
} else {
  Foreach($file in $kevFiles)
  {
   try 
   { 
    echo "KEV-BuBaBestandsdatei: $file"
  
    ## mail zusammenbauen und verschicken
    $date = get-date -format g
    $sub = "TXSKEV: BuBa Quittierungsdatei vom " + $date
    $server = "172.20.144.53"
    Send-MailMessage -From "BetriebTXS@nordlb.de" -to "BetriebTXS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments ($file.fullname)
   }
   catch
   {
 	  echo "Fehler beim Senden der TXS_KEV_Bundesbankquittierungsdatei" 
 	  exit 1
   }
  }
  exit 0
}
#REM Ende TransferBUBAQUITT2KIT.ps1