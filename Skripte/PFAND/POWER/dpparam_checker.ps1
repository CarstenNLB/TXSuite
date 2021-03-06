##############################################################################
## dpparam_checker.ps1
## Skript zum Test .. einer Dateisperre
## Stephan Pape / 101062 / 20180413
##############################################################################
<#
.SYNOPSIS
Skript zum Test der Parameterdatei dp.txt
.AUTHOR
Stephan Pape / 101062 / -5874
#>
function Test-FileLock {
  param (
    [parameter(Mandatory=$true)][string]$Path
  ) 
  $oFile = New-Object System.IO.FileInfo $Path

  try {
    $oStream = $oFile.Open([System.IO.FileMode]::Open, [System.IO.FileAccess]::ReadWrite, [System.IO.FileShare]::None)

    if ($oStream) {
      $oStream.Close()
    }
    $false
  } catch {
    # file is locked by a process.
     "LOCKED" | Out-File F:\TXS_009_PROD\PARAM\dptest.err
     $smtp = New-Object Net.Mail.SmtpClient("172.20.144.53")
     $sendby = "Marktdaten@nordlb.de (do not reply)"
     $sendto = "txsuite@nordlb.de"
     $header = "Marktdatenverarbeitung abgebrochen ...."
     $body   = "dp.txt war durch FRISCO gelockt .. Bitte spaeter wieder anstarten;" 
     $smtp.Send($sendby,$sendto,$header,$body)
     return $true
  }
}
# Aufruf mit der Datei 
Test-FileLock "F:\TXS_009_PROD\PARAM\dp.txt"
