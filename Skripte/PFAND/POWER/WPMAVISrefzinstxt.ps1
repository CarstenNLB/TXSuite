###########
#Script zueinfügen der refzinstxt= Variable
#by Thomas Tripp 27.06.2019 - PROD
###########

#Suchparameter festlegen


copy "F:\TXS_009_PROD\DATEN\LB\MAVIS_TXS.xml" "F:\TXS_009_PROD\DATEN\LB\inarbeit.txt" -force

Start-Sleep -Seconds 1

#echo $Contend_Error = Select-String -path MAVIS_TXS.txt -SimpleMatch "$pattern"

Get-Content "F:\TXS_009_PROD\DATEN\LB\inarbeit.txt" | ForEach-Object {
        if($_ -match "refzins="){
            if ($_ -match "tilgabw="){
                
                #Datei in temporäre Variable übergeben
                $temp = $_ 
            
                # Sting index finden, 
                $tempsub = $temp.LastIndexOf('refzins="')
            
                #alles vor dem Stingindex entfernen 
                $temprefzins = $temp.Substring($tempsub) 
            
                #alles nach refzins="xxx" entfernen, dazu index nachfolgender zeichen finden
                $temprefzinsindex = $temprefzins.IndexOf("tilgabw=") 
            
                #Länge des Stings ermittlen
                $length = $temprefzins.Length 
            
                #länge des Stings abzüglich der Zeichen, die erhalten bleiben sollen
                $length = $length - $temprefzinsindex 
            
                # entfernen vom "rest"
                $temprefzins = $temprefzins.Remove($temprefzinsindex, $length) 

                #Inhalt der "" ermitteln:
                $variablerteil=$temprefzins.TrimStart("refzins=")

                #Neuen Sting bauen:
                $replacetext = $temprefzins + "refzinstxt=" + $variablerteil

                $_.Replace($temprefzins, $replacetext)
            } else {
                
                #Datei in temporäre Variable übergeben
                $temp = $_ 
            
                # Sting index finden, 
                $tempsub = $temp.LastIndexOf('refzins="')
            
                #alles vor dem Stingindex entfernen 
                $temprefzins = $temp.Substring($tempsub) 
            
                #alles nach refzins="xxx" entfernen, dazu index nachfolgender zeichen finden
                $temprefzinsindex = $temprefzins.IndexOf("tilgdat=") 
            
                #Länge des Stings ermittlen
                $length = $temprefzins.Length 
            
                #länge des Stings abzüglich der Zeichen, die erhalten bleiben sollen
                $length = $length - $temprefzinsindex 
            
                # entfernen vom "rest"
                $temprefzins = $temprefzins.Remove($temprefzinsindex, $length) 

                #Inhalt der "" ermitteln:
                $variablerteil=$temprefzins.TrimStart("refzins=")

                #Neuen Sting bauen:
                $replacetext = $temprefzins + "refzinstxt=" + $variablerteil

                $_.Replace($temprefzins, $replacetext)
            }

        } else {
        echo $_
        }
    }| Set-Content "F:\TXS_009_PROD\DATEN\LB\MAVIS_TXS.xml"

    Remove-Item "F:\TXS_009_PROD\DATEN\LB\inarbeit.txt" 

    Start-Sleep -Seconds 3