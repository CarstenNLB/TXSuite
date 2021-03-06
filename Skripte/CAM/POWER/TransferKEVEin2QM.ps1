##############################################################################
## TransferKEVEin2QM.ps1
## Skript zum Versand an OE QM - speziell KEV-Einreichung*.pdf
## Aufruf aus _Start_Transfer_KEVPDF2OEQM.cmd
## SPA 20191202
## SPA 20200526 Mailadresse lpm-reports auf MACCS umgestellt
## SPA 20200731 MACCs-Einreichungsdatei für Schuldner
##############################################################################
<#
.SYNOPSIS
Skript zum Versand einer Datei
.AUTHOR
SPA / 101062 / -5874
#>

$kevFiles = @(Get-ChildItem -Path F:\TXS_009_Transfer\TXSKEV\export\QM\* -Include *-Einreichung*.pdf)
if ($kevFiles.length -eq 0) {
	echo "Fehler beim Senden der TXS_KEV_Einreichungsquittierung - Keine Datei vorhanden" 
 	exit 1
} else {
  Foreach($file in $kevFiles)
  {
   try 
   { 
    echo "KEV-Einreichungsdatei: $file"
  
    ## mail zusammenbauen und verschicken
    $date = get-date -format g
    $sub = "TXSKEV: Einreichung am " + $date
    $server = "172.20.144.53"
    Send-MailMessage -From "BetriebTXS@nordlb.de" -to "MACCS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments ($file.fullname)
    Send-MailMessage -From "BetriebTXS@nordlb.de" -to "BetriebTXS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments ($file.fullname)
   }
   catch
   {
 	  echo "Fehler beim Senden der TXS_KEV_Einreichungsquittierung" 
 	  exit 1
   }
  }
  exit 0
}
#REM Ende TransferKEVEin2QM.ps1