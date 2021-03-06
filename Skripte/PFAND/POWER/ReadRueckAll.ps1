##############################################################################
## ReadRueckAll.ps1
## Rueckmeldedatei per generischem Reporing einlesen und mit den richtigen 
##                 Schluessen ausgeben
##    Eingabedateien werden per generischen Reporting erzeugt und
##                 per Script von xlsm nach .csv umgewandelt - ohne Datum
##    Ausgabedateien werden je Eingabedatei erstellt.
##    Nach diesem Script werden sie per copy konkateniert
##    
##    Returnwerte
##                  0 -> alles ok
##                  1 -> Eingabedatei(en) fehl(t|en)
##                  2 -> Ausgabedatei(en) fehl(t|en)
## Ablösung TXS Rueckmeldverarbeitung Sicherheiten 
## SPA 20190805
## SPA 20200128 - Ausbau Flugzeuge und Ausbau Rueckmeldung von VO mit F
##############################################################################
<#
.SYNOPSIS
Skript zum Verarbeiten der Rueckmeldedateien
function csv_einlesen liest immer eine Datei ein 
Aufruf über Parameter pathin und pathout
.AUTHOR
SPA / 101062 / -5874
#>

function csv_einlesen {
  param ([parameter(mandatory=$True)][string]$Dateiin, [parameter(mandatory=$True)][string]$Dateiout)

        # Definition der Einlesezeile ins Array - Zaehler faengt bei 0 an
        # 00 OriginatorEnum;
        # 01 Quelle;
        # 02 Aktenzeichen;
        # 03 ExternerSliceKey;
        # 04 FGAusplatzierungsmerkmal;
        # 05 SliceAusplatzierungsmerkmal;
        # 06 Assigned;
        # 07 Blacklist;
        # 08 PoolName;
        # 09 TransName; 
        # 10 KRSHGueltigZum;
        # 11 KRSHStorno;
        # 12 KRSHBetrag;
        # 13 SiExternerShKey;
        # 14 SiZugeordneteAktenzeichen;
        # 15 SiHasStornierung;
        # 16 Verfuegungsbetrag;
        # 17 GBGueltigZum;
        # 18 GBStorno;
        # 19 VZGueltigZum;
        # 20 VZGeloeschtAm;
        # 21 PfandObjektNr;
        # 22 Pfandbriefrelevanz;
        # 23 GueltigZum;
        # 24 Beleihungswert;
        # 25 BeleihungswertDatum;
        # 26 EigenanteilAktuellerBeleihungswert;
        # 27 EingetragenerBeleihungswert;
        # 28 IsInDeckung;
        # 29 IstFlugzeug;
        # 30 IstSchiff

        # Definition der Ausgabezeile durch Semikolon getrennt (am Ende auch ..)
        # 00 Mandant (004/009)
        # 01 KontoNr (ResAZ)
        # 02 Ausplatzierungsmerkmal (2Stellig - ResAK)
        # 03 Deckungskennzeichen Finanzgeschaeft (1stellig) DeckFG
        # 04 Sicherheitennummer (ResSi)
        # 05 Deckungskennzeichen Sicherheit (DeckSi)
        # 06 Vermoegensobjektnummer (ResVO)
        # 07 Deckungskennzeichen Sicherheit (DeckSi) (VO hat kann kein eigenes Kennzeichen besitzen, haengt von der Si ab)

        # Arbeitsvariablen
        # Daten aus dem letzten Lauf
        $LastAK   = "--"
        $LastAZ   = "--"
        $LastSi   = "--"
        $LastVO   = "--"
        $LastStor = "--"
        # Ergebnis Vergleich letzte und aktuelle Zeile
        $VGLAK    = "-"
        $VGLAZ    = "-"
        $VGLSi    = "-"
        $VGLVO    = "-"
        $VGLStor  = "-"
        # Deckungskennzeichen - Ableitung aus Ausplatzierungsmerkmal und Feld KRSHStorno
        # Ausplatzierungsmerkmal == "99" => Alle Deckungskennzeichen = "N"
        # DeckFG ist Deckungskennzeichen für das Finanzgeschäft
        # DeckSi ist Deckungskennzeichen für Sicherheit und Vermögensobjekt.
        # Default == "N" heißt rausgeflogen .. keine Zuordnung mehr
        $DeckFG   = "N"
        $DeckSi   = "N"

        # Für jede Datei gilt ... erste Zeile ueberlesen
        $line     = 0
        Get-Content $Dateiin | ForEach-Object {
             # Initialisierung je Eingabezeile
             $StatusAusgabeJN = 'J'
             # Zeile ohne Leerzeichen ablegen
             $Zeile = ($_).Trim()
             # Zeile splitten ... Trenner ist Semikolon - 31 Felder - oben steht die Definition
             $ZeilenArray = $Zeile -Split ";"
             # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
             $ResLB        = $ZeilenArray[0]
             $LBMandant    = "009"
             $ResQuelle    = $ZeilenArray[1]
             $ResAZ        = $ZeilenArray[2]
             $ResAK        = $ZeilenArray[5]
             $KRSHStorno   = $ZeilenArray[11]
             $ResSi        = $ZeilenArray[13]
             $ResGBStorno  = $ZeilenArray[18]
             $ResVO        = $ZeilenArray[21]
             if ( $line -eq 0 ) {
                  # Eine Datei sollte aber eine Kopfzeile besitzen ...
                  if ( $Dateiin -eq  "NLB_Rueck_HK.csv" ) {
                     echo 'LB;AZ;AM;DFG;SINR;DSi;VONR;DVO;'
                  }
                  $StatusAusgabeJN = 'N'
                  $line = 1
             }
             # Nur Ausgabe, wenn Quelle nicht TXS
             if ( $ResQuelle -ieq "TXS" ) {
                $StatusAusgabeJN = 'N'
             }
              # Nur Ausgabe, wenn nicht VO leer ..
             if ([string]::IsNullOrEmpty($ResVO)) { 
               $StatusAusgabeJN = 'N'
             }
             # Nur Ausgabe, wenn VoNr kein Unterstrich hat
             if ( (($ResVO).LastIndexOf("_")) -ne '-1' ) {
                 $StatusAusgabeJN = 'N'
             }
             # Nur Ausgabe, wenn VONr kein Minuszeichen hat
             if ( (($ResVO).LastIndexOf("-")) -ne '-1' ) {
                 $StatusAusgabeJN = 'N'
             }
             # Nur Ausgabe, wenn SiNr kein Unterstrich hat
             if ( (($ResSi).LastIndexOf("_")) -ne '-1' ) {
                 $StatusAusgabeJN = 'N'
             }
             # Nur Ausgabe, wenn SiNr kein Komma hat
             if ( (($ResSi).LastIndexOf(",")) -ne '-1' ) {
                 $StatusAusgabeJN = 'N'
             }
             # Nur Ausgabe, wenn SiNr kein Minuszeichen hat
             if ( (($ResSi).LastIndexOf("-")) -ne '-1' ) {
                 $StatusAusgabeJN = 'N'
             }
             # Nur Ausgabe, wenn nicht GBStorno = WAHR
             if ( $ResGBStorno -ieq "WAHR" ) {
                $StatusAusgabeJN = 'N'
             }
             # Check zum Vergleich - Vorpruefung, ob Werte mit Vorzeile uebereinstimmen
             # Ausplatzierungsmerkmal
             $VGLAK      = "N"
             if ($LastAK -ieq $ResAK) {
                 $VGLAK   = "J"
             }
             # Konto
             $VGLAZ       = "N"
             if ($LastAZ -ieq $ResAZ) {
                 $VGLAZ   = "J"
             }
             # Sicherheit
             $VGLSi       = "N"
             if ($LastSi -ieq $ResSi) {
                 $VGLSi   = "J"
             }
             # Vermögensobjekt
             $VGLVO       = "N"
             if ($LastVO -ieq $ResVO) {
                 $VGLVO   = "J"
             }
             # Storno KZ
             $VGLStor     = "N"
             if ($LastStor -ieq $ResGBStorno) {
                 $VGLStor = "J"
             }
             # Alles gleich .. soso 
             # Inhaltlich gleich .. ohne Storno 
             if ( ($VGLAK    -ieq "J") -and 
                  ($VGLAZ    -ieq "J") -and 
                  ($VGLSi    -ieq "J") -and 
                  ($VGLVO    -ieq "J") -and
                  ($VGLStor  -ieq "J") ) {
                  $StatusAusgabeJN = 'N'
             }
             # Das was uebrig bleibt.. darf raus
             if ($StatusAusgabeJN -eq 'J') { 
               if ( ($ResLB).StartsWith("Brem")) {
                 $LBMandant    = "004"
               }
               # Auspl = "99" bedeutet .. egal, wo es herkam .. raus 
               if ( $ResAK -ieq "99" ) {
                    $DeckFG = "N"
                    $DeckSi = "N"
               }
               # Abbildungen von auspl => Deckungskennzeichen
               # SPA 20200128 Flugzeuge nicht mehr relevant
               # Auspl beginnt mit F bedeutet Flugzeug 
               #       F0        => W
               #       F1/F3     => U
               #       F2        => 4
               #       F4        => 8
               # Auspl beginnt mit S bedeutet Schiff 
               #       S0        => V
               #       S1/S3     => S
               #       S2        => 3
               #       S4        => 7
               # Auspl beginnt mit O bedeutet OEPG alte Gesetzgebung - keine Sicherheiten in SAP CMS
               #       O0/O1/O3  => A  O1 gibt es theoretisch nicht mehr
               #       O2        => 0  O2 gibt es theoretisch nicht mehr
               #       O4        => 9
               # Auspl beginnt mit H bedeutet Hypotheken, K Kommunal (Oeffentlich)
               # Aus historischen Gründen eigene Felder mit gleicher Abbildung
               #       H0/K0     => F
               #       H1/K1     => D
               #       H3/K3     => D
               #       K2        => 1
               #       H2        => 2
               #       K4        => 5
               #       H4        => 6
               # DeckFG geht immer direkt auf Auspl
               # DeckSi zusaetzlich auf KRSHStorno, wobei Storno FALSCH => immer "N"
               # D.h. Default = "N" und wenn Storn = WAHR, dann setzen
               $DeckSi = "N"
               # SPA 20200128 
               # Erst die Flugzeuge
               # SPA 20200128 if ( ($ResAK).StartsWith("F")) {
               # SPA 20200128   if ($ResAK -ieq "F0"){
               # SPA 20200128       $DeckFG = 'W'
               # SPA 20200128    # Storno = FALSCH?
               # SPA 20200128    if ( $KRSHStorno -ieq "FALSCH" ) {
               # SPA 20200128       $DeckSi = 'W'
               # SPA 20200128    }
               # SPA 20200128   }
               # SPA 20200128   if ( ($ResAK -ieq "F1") -or 
               # SPA 20200128        ($ResAK -ieq "F3") ) {
               # SPA 20200128      $DeckFG = 'U'
               # SPA 20200128    if ( $KRSHStorno -ieq "FALSCH" ) {
               # SPA 20200128       $DeckSi = 'U'
               # SPA 20200128    }
               # SPA 20200128   }
               # SPA 20200128   # F2 / F4 haben keine Sicherheiten in SAP CMS
               # SPA 20200128 }    
               # Dann die Schiffe
               if ( ($ResAK).StartsWith("S")) {
                 if ($ResAK -ieq "S0"){
                     $DeckFG = 'V'
                  # Storno = FALSCH?
                  if ( $KRSHStorno -ieq "FALSCH" ) {
                     $DeckSi = 'V'
                  }
                 }
                 if ( ($ResAK -ieq "S1") -or 
                      ($ResAK -ieq "S3") ) {
                    $DeckFG = 'S'
                  if ( $KRSHStorno -ieq "FALSCH" ) {
                     $DeckSi = 'S'
                  }
                 }
                 # S2 / S4 haben keine Sicherheiten in SAP CMS
               }    
               # OEPG hat auch keine Sicherheiten in SAP CMS
               # Kommunal hat auch keine Sicherheiten in SAP CMS
               # Jetzt fehlen noch die Hypotheken
               if ( ($ResAK).StartsWith("H")) {
                 if ($ResAK -ieq "H0"){
                     $DeckFG = 'F'
                  # Storno = FALSCH?
                  if ( $KRSHStorno -ieq "FALSCH" ) {
                     $DeckSi = 'F'
                  }
                 }
                 if ( ($ResAK -ieq "H1") -or 
                      ($ResAK -ieq "H3") ) {
                    $DeckFG = 'D'
                  if ( $KRSHStorno -ieq "FALSCH" ) {
                     $DeckSi = 'D'
                  }
                 }
                 # S2 / S4 haben keine Sicherheiten in SAP CMS
               }    
               echo $LBMandant';'$ResAZ';'$ResAK';'$DeckFG';'$ResSi';'$DeckSi';'$ResVO';'$DeckSi';'
             }
             $LastAK   = $ResAK
             $LastAZ   = $ResAZ
             $LastSi   = $ResSi
             $LastVO   = $ResVO
             $LastStor = $ResGBStorno
        } | Set-Content $Dateiout

 } 
 # ende function csv_einlesen 
 # Hier beginnnt das Hauptprogramm
 # Es gibt drei Eingabedateien und eine Ausgabedatei
 # Test der Laufzeit => Date/Time
 echo "Start ReadRueckAll Datum $(Get-Date -DisplayHint Date)"
 $Dateiin1     = "NLB_Rueck_HK.csv"
 ## SPA 20200128 $Dateiin2     = "NLB_Rueck_FL.csv"
 $Dateiin3     = "NLB_Rueck_SH.csv"
 $DateiN1out   = "NLBHK_Rueck.txt"
 ## SPA 20200128 $DateiN2out   = "NLBFL_Rueck.txt"
 $DateiN3out   = "NLBSH_Rueck.txt"
 $DateiNALLOut = "NLB_Rueck.txt"
 # Ausgabedatei leerputzen, nur wenn da
 $Datei0Exist = Test-Path -Path $DateiNALLOut -PathType Leaf
 if ( $Datei0Exist ) {
      Remove-Item $DateiNALLOut
 }
  $Datei1Exist = Test-Path -Path $DateiN1out -PathType Leaf
 if ( $Datei1Exist ) {
      Remove-Item $DateiN1out
 }
 ## SPA 20200128
 ##$Datei2Exist = Test-Path -Path $DateiN2out -PathType Leaf
 ##if ( $Datei2Exist ) {
 ##     Remove-Item $DateiN2out
 ##}
 $Datei3Exist = Test-Path -Path $DateiN3out -PathType Leaf
 if ( $Datei3Exist ) {
      Remove-Item $DateiN3out
 }
 # Test, ob Eingabedateien vorhanden
 # Zentrale Returnvariable
 $ZentrRet    = 0
 $Datei1Exist = Test-Path -Path $Dateiin1 -PathType Leaf
 ## SPA 20200128 $Datei2Exist = Test-Path -Path $Dateiin2 -PathType Leaf
 $Datei3Exist = Test-Path -Path $Dateiin3 -PathType Leaf
 ## SPA 20200128 if ( ($Datei1Exist) -and ($Datei2Exist) -and ($Datei3Exist) ) {
 if ( ($Datei1Exist) -and ($Datei3Exist) ) { 
    #Nur zur Info .. 
    echo "Anzahl Zeilen $Dateiin1 $((Get-Content $Dateiin1).length)"
    ## SPA 20200128 echo "Anzahl Zeilen $Dateiin2 $((Get-Content $Dateiin2).length)"
    echo "Anzahl Zeilen $Dateiin3 $((Get-Content $Dateiin3).length)"
    csv_einlesen -Dateiin $Dateiin1 -Dateiout $DateiN1out
    ## SPA 20200128 csv_einlesen -Dateiin $Dateiin2 -Dateiout $DateiN2out
    csv_einlesen -Dateiin $Dateiin3 -Dateiout $DateiN3out
    $Datei4Exist = Test-Path -Path $DateiN1out -PathType Leaf
    ## SPA 20200128 $Datei5Exist = Test-Path -Path $DateiN2out -PathType Leaf
    $Datei6Exist = Test-Path -Path $DateiN3out -PathType Leaf
    ## SPA 20200128 if ( ($Datei4Exist) -and ($Datei5Exist) -and ($Datei6Exist) ) { # Alles gut
    if ( ($Datei4Exist) -and ($Datei6Exist) ) { # Alles gut
         echo "Anzahl Zeilen $DateiN1out $((Get-Content $DateiN1out).length)"
         ## SPA 20200128 echo "Anzahl Zeilen $DateiN2out $((Get-Content $DateiN2out).length)"
         echo "Anzahl Zeilen $DateiN3out $((Get-Content $DateiN3out).length)"
    } # Alles gut
    else { # Bei der Verarbeitung hat etwas nicht geklappt
         echo "Etwas daneben gegangen----------------------------------------"
         echo "Datei $DateiN1out vorhanden: $Datei4Exist"
         ## SPA 20200128 echo "Datei $DateiN2out vorhanden: $Datei5Exist"
         echo "Datei $DateiN3out vorhanden: $Datei6Exist"
         echo "Etwas daneben gegangen----------------------------------------"
         $ZentrRet    = 2
    } # Bei der Verarbeitung hat etwas nicht geklappt
 }
 else {
    echo "Etwas daneben gegangen----------------------------------------"
    echo "Datei $Dateiin1 vorhanden: $Datei1Exist"
    ## SPA 20200128 echo "Datei $Dateiin2 vorhanden: $Datei2Exist"
    echo "Datei $Dateiin3 vorhanden: $Datei3Exist"
    echo "Etwas daneben gegangen----------------------------------------"
    $ZentrRet    = 1
 }
 echo "Ende ReadRueckAll Datum $(Get-Date -DisplayHint Date)  - Return $ZentrRet"
 exit $ZentrRet
 # Ende Gelende :-)