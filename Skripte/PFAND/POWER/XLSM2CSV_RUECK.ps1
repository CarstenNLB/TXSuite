##############################################################################
## XLSM2CSV_RUECK.ps1
## Skript zum Umwandeln von XLSM zu csv
## Ablösung TXS Rueckmeldung .. nur Dateien fuer Rueckmeldung
##          aus dem generischen Reporting
## SPA 20190730
##############################################################################
<#
.SYNOPSIS
Skript zum Umwandeln von XLSM zu csv
.AUTHOR
SPA / 101062 / -5874
#>

[reflection.assembly]::LoadWithPartialName("Microsoft.Office.InterOp.Excel")

$xlFixedFormat = [Microsoft.Office.Interop.Excel.XlFileFormat]::xlCSV

$excel = new-object -comobject excel.application
$excelFiles = Get-ChildItem -Path F:\TXS_009_REPORTS\NLBRUECK\* -Include *Rueck*.xlsm 

Foreach($file in $excelFiles)
{
 #Write-Host $file.fullname
 #Write-Host $file.basename
 
 $workbook = $excel.workbooks.open($file.fullname)
 
 $workbook.saveas($file.directoryname + "\" + $file.basename, $xlFixedFormat)
 
 $workbook.close($false)
}

$excel.quit()