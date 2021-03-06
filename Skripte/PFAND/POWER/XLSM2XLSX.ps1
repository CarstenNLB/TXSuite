##############################################################################
## XLSM2XLSX.ps1
## Skript zum Umwandeln von XLSM zu XLSX
## Aufruf nach der Erzeugung der TXS-Barwertreports
## Stefan Unnasch / 101062 / 20140707
##############################################################################
<#
.SYNOPSIS
Skript zum Umwandeln von XLSM zu XLSX
.AUTHOR
Stefan Unnasch / 101062 ext / -6741
#>

[reflection.assembly]::LoadWithPartialName("Microsoft.Office.InterOp.Excel")

$xlFixedFormat = [Microsoft.Office.Interop.Excel.XlFileFormat]::xlOpenXMLWorkbook

$excel = new-object -comobject excel.application
$excelFiles = Get-ChildItem -Path F:\TXS_009_REPORTS\TXS\* -Include Cash*.xlsm 

Foreach($file in $excelFiles)
{
 #Write-Host $file.fullname
 #Write-Host $file.basename
 
 $workbook = $excel.workbooks.open($file.fullname)
 
 $workbook.saveas($file.directoryname + "\" + $file.basename, $xlFixedFormat)
 
 $workbook.close()
}

$excel.quit()