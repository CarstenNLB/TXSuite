##############################################################################
## ReadTXSBatchLog.ps1
## Beliebiges Protokoll einlesen und nach |F| und |E| mit Abbruchtext scannen
##    Protokollierung Commandline und Starttime plus Ergebnis des Scans
##    Eingabedatei wird vom Batchprgramm oder Jobserver erstellt und als
##                 Uebergabeparameter zur Verfuegung gestellt.
##    Returnwerte
##                  0 -> Alles ok
##                  1 -> Eingabedatei fehlt - BatchLog-Datei
##                  2 -> BatchLogDatei beinhaltet abbruchreife Informationen
##                  4 -> BatchLogDatei beinhaltet Fehler, die geprüft werden solltn
## SPA 20200710
## SPA 20200720  Aufrufname als Übergabeparameter und Ausgabe etwas umgestaltet
##############################################################################
<#
.SYNOPSIS
Skript zum Verarbeiten beliebiger Protokolldateien
Aufruf über Parameter pathin und pathout
.AUTHOR
SPA / 101062 / -5874
#>
# Hier startet das Hauptprogramm - mit Checks der Parameter
param (
       [parameter(mandatory=$True)][string]$LogDatIn, 
       [parameter(mandatory=$True)][string]$LogDatOut,
       [parameter(mandatory=$True)][string]$Aufruf
)
echo "Param1 $LogDatIn"
echo "Param2 $LogDatOut"
echo "Param3 $Aufruf"
# Test der Laufzeit => Date/Time
echo "Start ReadTXSBatchLog Datum $(Get-Date -DisplayHint Date)"
# Test, ob Eingabedateien vorhanden
# Zentrale Returnvariable
$ZentrRet    = 0
$DateiExist = Test-Path -Path $LogDatIn -PathType Leaf
if ($DateiExist) { # Gut etwas da
  #Nur zur Info .. 
  echo "Anzahl Zeilen $LogDatIn $((Get-Content $LogDatIn).length)"
} # Gut etwas da
else { # nicht gut, Inputdatei fehlt
  echo "Etwas daneben gegangen----------------------------------------"
  echo "Datei $LogDatIn vorhanden: $DateiExist"
  $ZentrRet = 1
  echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
} # nicht gut, Inputdatei fehlt
# Nur, wenn Voraussetzungen ok, dann weiter
if ( $ZentrRet -eq 0 ) { # Bisher alles gut 
  # Jetzt geht es richtig los ..
  # Inputdatei - Definitionsblock - der TXS Protokolldatei
  # Definition der Einlesezeile ins Array - Zaehler faengt bei 0 an
  # 00 Datum (nur Tag)
  # 01 Startzeit
  # 02 Methode
  # 03 LogLevel
  # 04 Klassifizierung (I Info, E Fehler, W Warnung, F Fataler Fehler)
  # 05 Aufgabe
  # 06 Zu analysierender Text 
  # 06 Unter 
  # 06 StartTime, CommandLine
  $SuchZeit      = "StartTime"
  $SuchAufruf    = "CommandLine"
  $SuchException = "Exception"
  $SuchKl_I      = "I"
  $SuchKl_E      = "E"
  $SuchKl_W      = "W"
  $SuchKl_F      = "F"
  # Arbeitsvariablen
  $AufrufZeile   = "--"
  $AufrufZeit    = "--"
  $AnzI          = 0
  $AnzE          = 0
  $AnzEE         = 0
  $AnzW          = 0
  $AnzF          = 0
  $Abbruch       = 0
  $GefTime       = 0
  $GefLine       = 0
  Get-Content $LogDatIn | ForEach-Object {
    # Initialisierung je Eingabezeile
    # Zeile ohne Leerzeichen ablegen
    $Zeile = ($_).Trim()
    # Zeile splitten ... Trenner ist PipeSymbol - 7 Felder - oben steht die Definition
    $ZeilenArray = $Zeile -Split "\|"
    # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
    $ResDat       = $ZeilenArray[0]
    $ResZeit      = $ZeilenArray[1]
    $ResMethode   = $ZeilenArray[2]
    $ResLogLev    = $ZeilenArray[3]
    $ResKlasse    = $ZeilenArray[4]
    $ResAufgabe   = $ZeilenArray[5]
    $ResAnalyse   = $ZeilenArray[6]
    # echo "$ResDat $ResZeit $ResMethode $ResLogLev $ResKlasse $ResAufgabe $ResAnalyse"
    if ( $GefTime -eq 0 ) {
       # Zeile nur solange suchen, bis sie gefunden wurden
       $Gefunden = $ResAnalyse.contains($SuchZeit)
       # echo "Gefunden $Gefunden $ResAnalyse $SuchZeit" 
       if ($Gefunden) { # StartTime gefunden  
         $TempV1      = $ResAnalyse.Replace(' ','')
         $AufrufZeit  = $TempV1
         $GefTime      = 1
         #echo "StartTime $AufrufZeit"
       } # StartTime gefunden
    } # Suche StartTime
    if ( $GefLine -eq 0 ) {
       # Zeile nur solange suchen, bis sie gefunden wurden
       $Gefunden = $ResAnalyse.contains($SuchAufruf)
       # echo "Gefunden $Gefunden $ResAnalyse $SuchAufruf" 
       if ($Gefunden) { # CommandLine gefunden  
         $TempV2      = $ResAnalyse.Replace(' ','')
         $AufrufZeile = $TempV2
         $GefLine     = 1
         # echo "CommandLine $AufrufZeile"
       } # CommandLine gefunden
    } # Suche CommandLine
    # Immer auf der Suche nach der Exception und Klassifizierung
    if ( $ResKlasse -eq $SuchKl_I ) { # Info - nix besonderes
      $AnzI++
    } # Info - nix besonderes
    if ( $ResKlasse -eq $SuchKl_W ) { # Warnung - eigentlich nix besonderes
      $AnzW++
    } # Warnung - eigentlich nix besonderes
    if ( $ResKlasse -eq $SuchKl_E ) { # Fehler - kann etwas bedeuten - check Exception
      $AnzE++
    } # Fehler - kann etwas bedeuten - check Exception
    if ( $ResKlasse -eq $SuchKl_F ) { # Fataler Fehler - auf jeden Fall raus
      $AnzF++
    } # Fataler Fehler - auf jeden Fall raus
    $Gefunden = $ResAnalyse.contains($SuchException)
    if ($Gefunden) { # Exception gefunden - Abbruch 
      #echo "Exception $ResAnalyse"
      $AnzEE++
    } # Exception gefunden - Abbruch 
  } # Ende GET-Content

} # Bisher alles gut $ZentrRet -eq 0 
if ($AnzE -ne 0) { # Hier sollte geprüft werden
    $ZentrRet = 4
} # Hier sollte geprüft werden
if ($AnzF -ne 0 -or $AnzEE -ne 0) { # Abbruch .. höherwertiger als AnzE
    $ZentrRet = 2
} # Abbruch .. höherwertiger als AnzE
$AusgabeZentr = "$ZentrRet $Aufruf am Datum $(Get-Date -DisplayHint Date) -- ReadTXSBatchLog $LogDatIn Return $ZentrRet(F$AnzF-EE$AnzEE-E$AnzE-W$AnzW-I$AnzI) $AufrufZeit $AufrufZeile"
$AusgabeZentr | Out-File -FilePath $LogDatOut -Append

# Test der Laufzeit => Date/Time
echo $AusgabeZentr
echo "Ende ReadTXSBatchLog Datum $(Get-Date -DisplayHint Date)"
exit $ZentrRet
# Ende Gelende :-)       