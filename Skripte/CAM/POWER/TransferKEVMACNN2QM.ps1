##############################################################################
## TransferKEVMACNN2QM.ps1
## Skript zum Versand an OE QM - Nachrichten der BuBa - speziell *.pdf
## Aufruf aus _Start_Import_Nachrichten.cmd
## SPA 20200818 Aufbau mit der ersten Nachrichtendatei nach Umstellung qauf MACCs
##############################################################################
<#
.SYNOPSIS
Skript zum Versand einer Datei
.AUTHOR
SPA / 101062 / -5874
#>

$kevFiles = @(Get-ChildItem -Path F:\TXS_009_Transfer\TXSKEV\export\QMMACNN\* -Include *.pdf)
if ($kevFiles.length -eq 0) {
	echo "Fehler beim Senden der TXS_MACCs_Nachrichtendatei - Keine Datei vorhanden" 
 	exit 1
} else {
  Foreach($file in $kevFiles)
  {
   try 
   { 
    echo "MACCs-Nachrichtendatei: $file"
  
    ## mail zusammenbauen und verschicken
    $date = get-date -format g
    $sub = "TXS MACCs: Nachrichten am " + $date
    $server = "172.20.144.53"
    ## Send-MailMessage -From "BetriebTXS@nordlb.de" -to "MACCS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments ($file.fullname)
    Send-MailMessage -From "BetriebTXS@nordlb.de" -to "BetriebTXS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments ($file.fullname)
   }
   catch
   {
 	  echo "Fehler beim Senden der TXS_MACCs_Nachrichtendatei" 
 	  exit 1
   }
  }
  exit 0
}
#REM Ende TransferKEVMACNN2QM.ps1