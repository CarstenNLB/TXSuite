##############################################################################
## prostcheck
## Script zur Kontrolle ob die TXS-Prozesssteuerung PROST läuft
## Stefan Unnasch / 101062 / 20150408
##############################################################################
<#
.SYNOPSIS
Script zur Kontrolle ob die TXS-Prozesssteuerung PROST läuft
.AUTHOR
Stefan Unnasch / 101062 ext / -6741
#>

## letzte Bootzeit ermitteln
$mysys = Get-WmiObject -class Win32_OperatingSystem
$lastboot = ($mysys | Select-Object  __SERVER,@{label='LastBootUpTime';expression={$_.ConvertToDateTime($_.LastBootUpTime)}} | select LastBootUpTime).LastBootUpTime.Datetime
$machine = ($mysys | Select-Object  __SERVER,@{label='LastBootUpTime';expression={$_.ConvertToDateTime($_.LastBootUpTime)}} | select __SERVER).__SERVER


##Write-Host $lastboot

## alle Prozesse holen
$cmdlines = (Get-WmiObject win32_process -Filter "name like '%'" | select commandline)

$prostalive = 0
$prostline =""

## commandline auf prost prüfen
foreach ($line in $cmdlines)
{
  if ($line -match 'prost.bd.Vorgang')
  {
    $prostline = $line.commandline
  
    ##Write-Host $prostline
    
    $prostalive++
  } 
}

if ($prostalive -eq 0)
{
   ##Write-Host "PROST nicht gefunden"
   $headerstart = 'ACHTUNG'
   $prostline = 'PROST scheint nicht zu laufen' 
}
else
{
   $headerstart = 'STATUS'
   ##Write-Host "PROST gefunden"
   ##Write-Host $prostline
}

##Write-Host $lastboot

## mail zusammenbauen und verschicken

## mailserver
$smtp = New-Object Net.Mail.SmtpClient("smtp.kbk.nordlb.local")

$sendby = "prostcheck@nordlb.de (do not reply)"
$sendto = "TXSuite@nordlb.de"

$header = $headerstart + ' für ' + $machine + ' / letzter Neustart ' + $lastboot

$body = $prostline

##Write-Host $header
##Write-Host $body

$smtp.Send($sendby,$sendto,$header,$body)