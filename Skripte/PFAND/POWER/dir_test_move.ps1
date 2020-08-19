################################################################################################################
# filemover TXS_SIA_PROD in die KBK-Welt
# 20170825 SU
# Dieses Skript läuft via F:\TXS_009_PROD\PROGRAMM\LB\dir_test_move.bat per task-scheduler TXS_REPORT_MOVER
# Es werden alle Dateien aus den Reportverzeichnissen f:\txs_sia_prod in die KBK-Welt geschoben
# Es muss geprüft werden, ob Dateien noch in der Erstellung sind, bevor ein Kopiervorgang gestartet wird
# G: und H: müssen vor Aufrufen verbunden sein
################################################################################################################
<#
.SYNOPSIS
filemover TXS_SIA_PROD in die KBK-Welt
.AUTHOR
Stefan Unnasch / 101062 ext 
#>

function test_move
{
<#
.SYNOPSIS
Für alle Dateien im übergebenen Verzeichniss
Prüfung per open read/write auf 'offene' Datei und dann move
#>

    param ([parameter(mandatory=$True)][string]$pathin, [parameter(mandatory=$True)][string]$pathout) 

    $dir = get-childitem($pathin)

    foreach ($file in $dir)
    {
      try 
      {
        $mopentest = $file.OpenWrite()
        $mopentest.Close();

        Move-Item -literalpath ($file.FullName) ($pathout + "\" + $file.name)
      }
      catch
      {
        write-host "locked"
      }
    }
}

# Treuhänder-Verzeichnisse (...TH)
test_move -pathin "F:\TXS_SIA_PROD\NLB\PFAND\TH" -pathout "G:\Registerabschluesse\Deckungsregister"

test_move -pathin "F:\TXS_SIA_PROD\NLB\REFIREG\TH" -pathout "G:\Registerabschluesse\Refinanzierungsregister"

# Verzeichnisse des Fachbereichs (...FB)
test_move -pathin "F:\TXS_SIA_PROD\NLB\PFAND\FB" -pathout "H:\3 TXS-Reports"

test_move -pathin "F:\TXS_SIA_PROD\NLB\REFIREG\FB" -pathout "H:\3 TXS-Reports\Refinanzierungsregister"

test_move -pathin "F:\TXS_SIA_PROD\NLB\CAMKEV" -pathout "H:\3 TXS-Reports\CAMKEV"
      
