##############################################################################
## mavis_xml_korrektur.ps1
## Skript um Sonderzeichen im MAVIS import korrekt zu maskieren
## Aufruf während der Importverarbeitung MAVIS
## Stefan Unnasch / 101062 / 20131126
##############################################################################
<#
.SYNOPSIS
Skript um Sonderzeichen im MAVIS import korrekt zu maskieren
.AUTHOR
Stefan Unnasch / 101062 ext / -6741
#>

$MAVISfile = Get-ChildItem -Path "F:\TXS_009_TRANSFER\MAVIS\IMPORT\A1PBAT.BM.I009.DPMAVIS.M710X.XMLOUT.D*" 
(get-content $MAVISfile) | foreach-object {$_ -replace "&", "&amp;"} | set-content $MAVISfile