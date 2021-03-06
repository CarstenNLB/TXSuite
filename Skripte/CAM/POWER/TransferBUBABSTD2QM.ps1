##############################################################################
## TransferBUBABSTD2QM.ps1
## Skript zum Versand an OE QM - speziell Nordlb KEV BuBa Bestand *.pdf
## Aufruf aus _Start_Transfer_KEVBUBABSTD2OEQM.cmd
## SPA 20191203
## SPA 20200526 Mailadresse lpm-reports auf MACCS umgestellt
##############################################################################
<#
.SYNOPSIS
Skript zum Versand einer Datei
.AUTHOR
SPA / 101062 / -5874
#>

$kevFiles = @(Get-ChildItem -Path F:\TXS_009_Transfer\TXSKEV\export\QM\* -Include Nordlb*.pdf)
if ($kevFiles.length -eq 0) {
	echo "Fehler beim Senden der TXS_KEV_Bundesbankbestandsdatei - Keine Datei vorhanden" 
 	exit 1
} else {
  Foreach($file in $kevFiles)
  {
   try 
   { 
    echo "KEV-BuBaBestandsdatei: $file"
  
    ## mail zusammenbauen und verschicken
    $date = get-date -format g
    $sub = "TXSKEV: BuBa Bestandsdatei vom " + $date
    $server = "172.20.144.53"
    Send-MailMessage -From "BetriebTXS@nordlb.de" -to "MACCS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments ($file.fullname)
    Send-MailMessage -From "BetriebTXS@nordlb.de" -to "BetriebTXS@nordlb.de" -Subject $sub -smtpserver $server  -Attachments ($file.fullname)
   }
   catch
   {
 	  echo "Fehler beim Senden der TXS_KEV_Bundesbankbestandsdatei" 
 	  exit 1
   }
  }
  exit 0
}
#REM Ende TransferBUBABSTD2QM.ps1