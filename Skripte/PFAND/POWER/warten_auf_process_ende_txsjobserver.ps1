##############################################################################
## warten_auf_process_ende_txsjobserver.ps1
## Skript um auf das Ende parallel laufender TXSJobServer Prozesse zu warten
## Aufruf während der Erzeugung der TXS-Barwertreports
## Stefan Unnasch / 101062 / 20151110
##############################################################################
<#
.SYNOPSIS
Skript um auf das Ende parallel laufender TXSJobServer Prozesse zu warten
.AUTHOR
Stefan Unnasch / 101062 ext / -6741
#>

$txsrunning = 1;

while ( $txsrunning -gt 0)
{
  $processes = get-process

  $txsrunning=0;

  ForEach ($process in $processes)
  {
    ##Write-Host $process.Name
  
    if ($process.Name -eq "TXSJobServer") 
    {
      $txsrunning++;
    }

  }
  
  Start-Sleep -s 2

} 

Write-Host "+++++++++++++++++++++++"
Write-Host "no TXSJobServer running"
Write-Host "+++++++++++++++++++++++"
