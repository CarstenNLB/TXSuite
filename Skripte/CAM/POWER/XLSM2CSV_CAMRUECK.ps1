##############################################################################
## XLSM2CSV_CAMRUECK.ps1
## Skript zum Umwandeln von XLSM zu csv
## CAMKEV Rueckmeldung .. Dateien fuer Abnehmer
##        aus dem generischen Reporting
## SPA 20190910
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
$excelFiles = @(Get-ChildItem -Path F:\TXS_009_REPORTS\KEVRUECK\* -Include *CAMRueck*.xlsm)

if ($excelFiles.length -eq 0) {
	echo "Fehler bei der Verarbeitung der TXSKEV-Rueckmeldung - Kein Report NLBCAMRueck vorhanden" 
 	exit 1
} else {
 Foreach($file in $excelFiles)
 {
 	try {
    echo "TXSKEV - Rueckmeldedatei: $file "
   
    $workbook = $excel.workbooks.open($file.fullname)
 
    $workbook.saveas($file.directoryname + "\" + $file.basename, $xlFixedFormat)
 
    $workbook.close($false)
  }
  catch 
  {
    echo "Fehler bei der Umwandlung der NLBCAMRueck - Datei in csv-Format" 
 	  exit 1	
  }
 }
$excel.quit()
exit 0
}
#REM Ende XLSM2CSV_CAMRUECK.ps1