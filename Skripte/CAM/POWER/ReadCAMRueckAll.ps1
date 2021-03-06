##############################################################################
## ReadCAMRueckAll.ps1
## Rueckmeldedatei per generischem Reporing einlesen und mit den richtigen 
##                 Schluessen ausgeben
##    Eingabedateien werden per generischen Reporting erzeugt und
##                 per Script von xlsm nach .csv umgewandelt - ohne Datum
##    Ausgabedateien werden je Eingabedatei erstellt.
##    Nach diesem Script werden sie per copy konkateniert
##    
##    Returnwerte
##                  1 -> Eingabedatei fehlt  - der generische Report
##                  2 -> Eingabedatei fehlt  - dp.txt
##                  3 -> Eingabedatei dp.txt - Datum nicht verwertbar
##                  4 -> Ausgabedatei fehlt  - Basisausgabedatei
##                  8 -> Ausgabedatei fehlt  - Meldewesen
##                  16-> Ausgabedatei fehlt  - OTC
##                  32-> Ausgabedatei fehlt  - CMC
##                  64-> Ausgabedatei fehlt  - KIT
## CAMKEV - Abnehmer begluecken  
## SPA 20191009
## SPA 20191114 Anpassung CMC-Dateinamen
## SPA 20191115 Anpassung KIT-Dateinamen
## SPA 20191204 Umlaute angepasst
## SPA 20191210 Anpassung Dateiname SPOT
## SPA 20200709 Anpassung Datum aus dp.txt nehmen
##############################################################################
<#
.SYNOPSIS
Skript zum Verarbeiten der TXS CAMKEV Rueckmeldedatei - generisches Reporting
function csv_einlesen liest immer eine Datei ein 
Aufruf über Parameter pathin und pathout
.AUTHOR
SPA / 101062 / -5874
#>
# Hier beginnnt das Hauptprogramm - Teil 1 - Einlesen der KEV-Rueckmeldedatei
# Test der Laufzeit => Date/Time
echo "Start ReadCAMRueckAll Datum $(Get-Date -DisplayHint Date)"
$Dateiin1    = "NLBCAMRueck.csv"
$Dateiin2    = "F:\CAM_009_PROD\STATUS\dptxt\dp.txt"
#$Dateiin2    = "T:\TXS\_TXS_Projekt\LPM_Ablösung\ToDo_Einsatz_Prod\Update_202007\dp.txt"
$Dateiout1   = "NLBCAMWork.txt"
# Ausgabedatei leerputzen, nur wenn da
$Datei3Exist = Test-Path -Path $Dateiout1 -PathType Leaf
if ( $Datei3Exist ) {
     Remove-Item $Dateiout1
}
# Test, ob Eingabedateien vorhanden
# Zentrale Returnvariable
$ZentrRet    = 0
$Datei1Exist = Test-Path -Path $Dateiin1 -PathType Leaf
if ($Datei1Exist) { # Gut etwas da
  #Nur zur Info .. 
  echo "Anzahl Zeilen $Dateiin1 $((Get-Content $Dateiin1).length)"
} # Gut etwas da
else { # nicht gut, Inputdatei fehlt
  echo "Etwas daneben gegangen----------------------------------------"
  echo "Datei $Dateiin1 vorhanden: $Datei1Exist"
  $ZentrRet = 1
  echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
  exit $ZentrRet
} # nicht gut, Inputdatei fehlt
$Datei2Exist = Test-Path -Path $Dateiin2 -PathType Leaf
if ($Datei2Exist) { # Gut etwas da
  #Nur zur Info .. 
  echo "Anzahl Zeilen $Dateiin2 $((Get-Content $Dateiin2).length)"
} # Gut etwas da
else { # nicht gut, Inputdatei fehlt
  echo "Etwas daneben gegangen----------------------------------------"
  echo "Datei $Dateiin2 vorhanden: $Datei2Exist"
  $ZentrRet = 2
  echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
  exit $ZentrRet
} # nicht gut, Inputdatei fehlt
# Default, damit man es merkt, wenn es nicht funktioniert
$DPTXTJJ    = "1900"
$DPTXTMM    = "00"
$DPTXTTT    = "00"
$Datcheck   = 0 # Test, ob Datum nutzbar
# Datum aus dp.txt lesen
Get-Content $Dateiin2 | ForEach-Object {
    # Zeile ohne Leerzeichen ablegen
    $Zeiledp = ($_).Trim()
    # Zeile splitten ... Trenner ist Semikolon - 31 Felder - oben steht die Definition
    $ZeilendpArray = $Zeiledp -Split "-"
    # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
    $TESTTXTJJ    = $ZeilendpArray[0]
    $TESTTXTMM    = $ZeilendpArray[1]
    $TESTTXTTT    = $ZeilendpArray[2]
} # keine Ausgabe 
#$testzahl = $TESTTXTJJ -match '^[0-9]+$'
$testzahl = $TESTTXTJJ -match '[2][0-9][0-9][0-9]'
if ($testzahl) { # numerisch
    $DPTXTJJ = $TESTTXTJJ
} # numerisch
else { # nicht numerisch 
   $Datcheck = 1
   # formaler else-Zwei..es zieht die Vorbelegung
} # nicht numerisch 
$testzahl = $TESTTXTMM -match '[0-1][0-9]'
if ($testzahl) { # numerisch
    $testnum = $TESTTXTMM -as [int]
    if ($testnum -gt 0 -and $testnum -lt 13) { # Monatsbereich
        $DPTXTMM = $TESTTXTMM
    } # Monatsbereich
    else { #passt nicht
      $Datcheck = 1
    } # passt nicht 
} # numerisch
else { # nicht numerisch 
   # formaler else-Zwei..es zieht die Vorbelegung
   $Datcheck = 1
} # nicht numerisch 
$testzahl = $TESTTXTTT -match '[0-3][0-9]'
if ($testzahl) { # numerisch
    $testnum1 = $TESTTXTTT -as [int]
    if ($testnum1 -gt 0 -and $testnum1 -lt 32) { # Tagesbereich
        $DPTXTTT = $TESTTXTTT
    } # Tagesbereich
    else { #passt nicht
      $Datcheck = 1
    } # passt nicht 
} # numerisch
else { # nicht numerisch 
   $Datcheck = 1
   # formaler else-Zwei..es zieht die Vorbelegung
} # nicht numerisch 
if ($Datcheck -eq 0) { # Gut nutzbar
  #Nur zur Info .. 
  $DPDatum = $DPTXTJJ+$DPTXTMM+$DPTXTTT
  echo "Buchungstag aus dptxt: $DPDATUM"
} # Gut nutzbar
else { # nicht gut, Inputdatei fehlt
  echo "Etwas daneben gegangen----------------------------------------"
  echo "Datei Datei - $Dateiin2 ; Datum - $TESTTXTJJ+$TESTTXTMM+$TESTTXTTT"
  $ZentrRet = 3
  echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
  exit $ZentrRet
} # nicht gut, Inputdatei fehlt
# Jetzt geht es richtig los ..
# Inputdatei - Definitionsblock - der Exceldatei aus dem CAM_KEV
# Definition der Einlesezeile ins Array - Zaehler faengt bei 0 an
# 00 Landesbank (ResLB)
# 01 Quelle (ResQuelle)
# 02 KundenNummer (ResKDNr)
# 03 TXS Schuldner-ID der Bundesbank (TXSBuBaSID)
# 04 TXS AlternativSchuldner-ID der Bundesbank (TXSAlterSID)
# 05 Schuldner-ID der Bundesbank (ResBuBaSID)
# 06 KUSYMA (ResKusyma)
# 07 TransaktionsID (ResTransID)
# 08 Forderungs-ID der Bundesbank (ResBuBaFID)
# 09 Anwendungsnummer (ResAnw)
# 10 Produktschluessel (ResPSCHL)
# 11 Ausplatzierungsmerkmal (ResAK)
# 12 Deckungsschluessel (ResDeckFG)
# 13 Restkapital (ResKap)
# 14 WährungNom (ResWhrgNom)
# 15 Beleihungsbetrag (ResBel)
# 16 Währung (ResWhrg)
# 17 Datum Valuta (ResVal)
# 18 Datum Fälligkeit (ResFae)
# 19 Datum BuBaAkzept (ResBuBaEin)
# 20 Datum Einbuchung (ResEinb)
# 21 Art der Forderung (ResAF)
# 22 SliceState (ResSlS1)
# 23 SliceStateProposed (ResSlSP)
# 24 SliceStateProposedNew (ResSlSPN)
# 25 AccountNumber (ResAccNr)
# 26 ExternalKey (ResExtKey)
# 27 RemainingAmountUsed (ResRemAU)
# 28 SchuldnerId (ResSID)
# 29 Role (ResRole)
# 30 GetKevValutaDeduced (ResGKVD)
# 31 GetKevMaturityDeduced (ResGKMD)
# 32 GetKevInstrumentTypeDeduced (ResGKKITD)
# 33 DateTime (ResTimeStart)
# 34 DateTimeEnd (ResTimeEnd)
# 35 SliceState (ResSlS2)
# 36 LastItemOfDay (ResLastItem)
# 37 FixedItem (ResFixItem)
#
# Generelle Ausgabedatei fuer alle 
# Definition der Ausgabezeile durch Semikolon getrennt (am Ende auch ..)
# 00 Mandant (004/009)
# 01 TransaktionsID (TRANSID)        
# 02 KundenNummer (KDNR)
# 03 KUSYMA (KUSY)
# 04 Schuldner-ID der Bundesbank (BUBASID)
# 05 Forderungs-ID der Bundesbank (BUBAFID)
# 06 Anwendungsnummer (ANW)
# 07 Produktschluessel (PRODSCH)
# 08 Ausplatzierungsmerkmal (2Stellig - AK)
# 09 Deckungskennzeichen Finanzgeschaeft (1stellig DFG)       
# 10 Restkapital Komma (RKAPK)
# 11 Restkapital Punkt (RKAPP)
# 12 Beleihungsbetrag Komma (BELK)
# 13 Beleihungsbetrag Punkt (BELP)
# 14 WährungNom (WHRGNOM)
# 15 WährungBel (WHRGBEL)
# 16 Datum Valuta (VAL)
# 17 Datum Fälligkeit (FAE)
# 18 Datum Einbuchung BuBa (EINB)
# 19 Art der Forderung (ARTF)
# 20 Quelle (QUELLE)
# 21 DatumforFutureUse (FORF)

# Arbeitsvariablen
# Daten aus dem letzten Lauf
$LastTRANSID = "--"
# Ergebnis Vergleich letzte und aktuelle Zeile
$VGLTRANSID = "-"
$RESAK     = "A1"
$DEFAK     = "A1"   # fuer Abnehmer OTC/CMC hart setzen
$RESDFG    = "N"
$DEFDFG    = "N"    # fuer Abnehmer OTC/CMC hart setzen
$RESFORF   = "01.01.2019"
# Für jede Datei gilt ... erste Zeile überlesen, dafür eine eigene Überschrift
$line       = 0
$AnzAlle    = 0
$AnzBuBa    = 0
$AnzDop     = 0
$AnzNONBuBa = 0
Get-Content $Dateiin1 | ForEach-Object {
    # Initialisierung je Eingabezeile
    $StatusAusgabeJN = 'J'
    # Zeile ohne Leerzeichen ablegen
    $Zeile = ($_).Trim()
    # Zeile splitten ... Trenner ist Semikolon - 31 Felder - oben steht die Definition
    $ZeilenArray = $Zeile -Split ";"
    # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
    $LBMandant    = "009"
    $ResLB        = $ZeilenArray[0]
    $ResQuelle    = $ZeilenArray[1]
    $ResKDNr      = $ZeilenArray[2]
    $TXSBuBaSID   = $ZeilenArray[3]
    $TXSAlterSID  = $ZeilenArray[4]
    $ResBuBaSID   = $ZeilenArray[5]
    $ResKusyma    = $ZeilenArray[6]
    $ResTransID   = $ZeilenArray[7]
    $ResBuBaFID   = $ZeilenArray[8]
    $ResAnw       = $ZeilenArray[9]
    $ResPSCHL     = $ZeilenArray[10]
    $ResAK        = $ZeilenArray[11]
    $ResDeckFG    = $ZeilenArray[12]
    $ResKap       = $ZeilenArray[13]
    $ResWhrgNom   = $ZeilenArray[14]
    $ResBel       = $ZeilenArray[15]
    $ResWhrg      = $ZeilenArray[16]
    $ResVal       = $ZeilenArray[17]
    $ResFae       = $ZeilenArray[18]
    $ResBuBaEin   = $ZeilenArray[19]
    $ResEinb      = $ZeilenArray[20]
    $ResAF        = $ZeilenArray[21]
    $ResSlS1      = $ZeilenArray[22]
    $ResSlSP      = $ZeilenArray[23]
    $ResSlSPN     = $ZeilenArray[24]
    $ResAccNr     = $ZeilenArray[25]
    $ResExtKey    = $ZeilenArray[26]
    $ResRemAU     = $ZeilenArray[27]
    $ResSID       = $ZeilenArray[28]
    $ResRole      = $ZeilenArray[29]
    $ResGKVD      = $ZeilenArray[30]
    $ResGKMD      = $ZeilenArray[31]
    $ResGKKITD    = $ZeilenArray[32]
    $ResTimeStart = $ZeilenArray[33]
    $ResTimeEnd   = $ZeilenArray[34]
    $ResSlS2      = $ZeilenArray[35]
    $ResLastItem  = $ZeilenArray[36]
    $ResFixItem   = $ZeilenArray[37]
    if ( $line -eq 0 ) {
        # Eine Datei sollte aber eine Kopfzeile besitzen ...aber nicht die ürsprüngliche 
        echo 'LB;TRANSID;KDNR;KUSY;BUBASID;BUBAFID;ANW;PRODSCH;RESAK;DECKFG;RKAPK;RKAPP;WHRGNOM;BELK;BELP;WHRGBEL;VAL;FAE;EINB;ARTF;QUELLE;FORF;'
        $StatusAusgabeJN = 'N'
        $line = 1
    }
    else { # nur für den Zähler
       $AnzAlle++
    } # nur für den Zähler
    # Check zum Vergleich - Vorpruefung, ob Werte mit Vorzeile uebereinstimmen
    # Ausplatzierungsmerkmal
    $VGLTRANSID = "N"
    if ($LastTRANSID -ieq $ResTransID) {
        $VGLTRANSID = "J"
    }
    # Alles gleich .. soso .. aktuell nur ein Feld zum Vergleich .. kann mehr werden 
    # Inhaltlich gleich .. 
    if ($VGLTRANSID -ieq "J") {
        $StatusAusgabeJN = 'N'
        $AnzDop++
    }
    # Das was uebrig bleibt.. darf raus
    if ($StatusAusgabeJN -eq 'J') { 
      if ( ($ResLB).StartsWith("Brem")) {
            $LBMandant    = "004"
      }
      # Felder zuordnen
      $TRANSID    = $ResTransID       
      $KDNR       = $ResKDNr
      $KUSY       = $ResKusyma
      if ([string]::IsNullOrEmpty($ResBuBaSID)) { #Leerim Falle det manuellen Online-Einreichung
          $BUBASID = $TXSBuBaSID 
      } #Leer
      else { # Nichtleer -> Standard
          $BUBASID = $ResBuBaSID
      } # Nichtleer -> Standard
      $BUBAFID    = $ResBuBaFID
      $ANW        = $ResAnw
      $PRODSCH    = $ResPSCHL
      $AK         = $RESAK
      $DFG        = $RESDFG
      # Leer oder nicht LEER
      # TXS sschreibt in Fällen, wo die Einreichung manuell erfolgte, nicht in ResKap rein sondern in ResRemAU 
      if ([string]::IsNullOrEmpty($ResRemAU)) { #Leer 
          $RKAPK = "0,00"
          $RKAPP = "0.00"
      } #Leer
      else { #Nicht LEER
          $RKAPK = $ResRemAU 
          # Komma durch Punkt ersetzen
          $RKAPP = ($ResRemAU).replace(",",".")
      } #Nicht LEER
      # Leer oder nicht LEER
      if ([string]::IsNullOrEmpty($ResBel)) { #Leer 
          $BELK = "0,00"
          $BELP = "0.00"
          $StatusAusgabeJN = 'N'
      } #Leer
      else { #Nicht LEER
          $BELK = $ResBel 
          # Komma durch Punkt ersetzen
          $BELP = ($ResBel).replace(",",".")
      } #Nicht LEER
      $WHRGNOM    = $ResWhrgNom
      $WHRGBEL    = $ResWhrg
      $VAL        = $ResVal 
      $FAE        = $ResFae
      $EINB       = $ResBuBaEin
      if ([string]::IsNullOrEmpty($ResBuBaEin) ) { #Leer 
          $StatusAusgabeJN = 'N'
      } #Leer 
      $ARTF       = $ResAF
      $QUELLE     = $ResQuelle 
      $FORF       = $RESFORF             
      # Felder ausgeben, wenn bei der BuBa
      if ($StatusAusgabeJN -eq 'J') {
          echo $LBMandant';'$TRANSID';'$KDNR';'$KUSY';'$BUBASID';'$BUBAFID';'$ANW';'$PRODSCH';'$AK';'$DFG';'$RKAPK';'$RKAPP';'$WHRGNOM';'$BELK';'$BELP';'$WHRGBEL';'$VAL';'$FAE';'$EINB';'$ARTF';'$QUELLE';'$FORF';'
          $AnzBuBa++
      }
      else { #nicht an die BuBa
          $AnzNONBuBa++
      } #nicht an die BuBa
    }
    $LastTRANSID = $ResTransID
} | Set-Content $Dateiout1
# Hier endet das Hauptprogramm - Teil 1 - Einlesen der KEV-Rueckmeldedatei
# Ausgabe der interessanten Infos
echo "Anzahl Zeilen in der KEV-Rueckmeldedatei: $AnzAlle"
echo "           davon von der BuBa akzeptiert: $AnzBuBa"
echo "           davon              noch offen: $AnzNONBuBa"
echo "           davon      doppelte Eintraege: $AnzDop"
echo "Ende Einlesen KEV-Datei Datum $(Get-Date -DisplayHint Date)"
# Ueberpruefung
$Datei2Exist = Test-Path -Path $Dateiout1 -PathType Leaf
if ($Datei2Exist) {
   #Nur zur Info .. 
   echo "Anzahl Zeilen $Dateiout1 $((Get-Content $Dateiout1).length)"
} # Alles gut
else { # Bei der Verarbeitung hat etwas nicht geklappt
   echo "Etwas daneben gegangen----------------------------------------"
   echo "Datei $Dateiout1 vorhanden: $Datei2Exist"
   $ZentrRet    = 4
   echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
   exit $ZentrRet
}
# Soweit so gut 
# Jetzt wird je Abnehmer eine Datei erzeugt, Basis zum Einlesen die eben erzeugte Ausgabedatei
$NLBVGL      = "009"
$BLBVGL      = "004"
# Erster Abnehmer Meldewesen
# Headerdefinition
# $HeadDate1 = Get-Date -UFormat "%Y%m%d"
$HeadDate1 = $DPDatum
$HeadDate2 = Get-Date -UFormat "%Y%m%d%H%M%S"

# $DateiMelde009  = $HeadDate1+"_TXS2SPOT.KEVBeleihung_009_"+$HeadDate2+".csv"
# $DateiMelde004  = $HeadDate1+"_TXS2SPOT.KEVBeleihung_004_"+$HeadDate2+".csv"
$DateiMelde009  = "TXS2SPOT_KEVBELEIHUNG_009_"+$HeadDate1+"_"+$HeadDate2+".csv"
$DateiMelde004  = "TXS2SPOT_KEVBELEIHUNG_004_"+$HeadDate1+"_"+$HeadDate2+".csv"
echo "Meldewesen-DateiName NLB: $DateiMelde009" 
echo "Meldewesen-DateiName BLB: $DateiMelde004" 
# Ausgabedatei leerputzen, nur wenn da.. geht eigemtlich nicht w_Timestamp
$Datei3Exist = Test-Path -Path $DateiMelde009 -PathType Leaf
if ( $Datei3Exist ) {
     Remove-Item $DateiMelde009
}
$Datei4Exist = Test-Path -Path $DateiMelde004 -PathType Leaf
if ( $Datei4Exist ) {
     Remove-Item $DateiMelde004
}
# Jetzt erste Ausgabedatei einlesen und die Ausgabedatei fuer Meldewesen schreiben
$line       = 0
$AnzAlle    = 0
$AnzNLB     = 0
$AnzBLB     = 0
Get-Content $Dateiout1| ForEach-Object {
    # Initialisierung je Eingabezeile
    $StatusAusgabeJN = 'J'
    # Zeile ohne Leerzeichen ablegen
    $Zeile = ($_).Trim()
    # Zeile splitten ... Trenner ist Semikolon - 31 Felder - oben steht die Definition
    $ZeilenArray = $Zeile -Split ";"
    # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
    $LBMandant  = $ZeilenArray[0]
    $TRANSID    = $ZeilenArray[1]
    $KDNR       = $ZeilenArray[2]
    $KUSY       = $ZeilenArray[3]
    $BUBASID    = $ZeilenArray[4]
    $BUBAFID    = $ZeilenArray[5]
    $ANW        = $ZeilenArray[6]
    $PRODSCH    = $ZeilenArray[7]
    $AK         = $ZeilenArray[8]
    $DFG        = $ZeilenArray[9]
    $RKAPK      = $ZeilenArray[10]
    $RKAPP      = $ZeilenArray[11]
    $WHRGNOM    = $ZeilenArray[12]
    $BELK       = $ZeilenArray[13]
    $BELP       = $ZeilenArray[14]
    $WHRGBEL    = $ZeilenArray[15]
    $VAL        = $ZeilenArray[16]
    $FAE        = $ZeilenArray[17]
    $EINB       = $ZeilenArray[18]
    $ARTF       = $ZeilenArray[19]
    $QUELLE     = $ZeilenArray[20]
    $FORF       = $ZeilenArray[21]
    # Felder ausgeben, wenn bei der BuBa
    if ( $line -eq 0 ) {
       # Eine Datei sollte aber eine Kopfzeile besitzen ...aber nicht die ürsprüngliche 
       echo 'TRANSAKTIONS_ID;FORDERUNG_ID_BUBA;NOMINALBETRAG;NOMINALWAEHRUNG;BELEIHUNGSBETRAG;BELEIHUNGSWAEHRUNG;VALUTA_DATUM;FAELLIGKEIT_DATUM;EINBUCHUNG_DATUM;FORDERUNGSART;SCHULDNER_ID_BUBA;ANWENDUNGSNUMMER;'
       $StatusAusgabeJN = 'N'
       $line = 1
    }
    else { # nur für den Zähler
       $AnzAlle++
    } # nur für den Zähler
    # Fuer spaeter, wenn BLB und NLB getrennt angeliefert werden muesste
    if ($StatusAusgabeJN -eq 'J') { 
      if ($LBMandant -ieq $NLBVGL) { # nur NORDLB
          echo $TRANSID';'$BUBAFID';'$RKAPK';'$WHRGNOM';'$BELK';'$WHRGBEL';'$VAL';'$FAE';'$EINB';'$ARTF';'$BUBASID';'$ANW';'
          $AnzNLB++
      } # nur NORDLB
    } # Ausgabe NLB
} | Set-Content $DateiMelde009
echo "Ende Ausgabe Meldwesen NLB Datum $(Get-Date -DisplayHint Date)"
$line       = 0
$AnzAlle    = 0
$AnzBLB     = 0
Get-Content $Dateiout1| ForEach-Object {
    # Initialisierung je Eingabezeile
    $StatusAusgabeJN = 'J'
    # Zeile ohne Leerzeichen ablegen
    $Zeile = ($_).Trim()
    # Zeile splitten ... Trenner ist Semikolon - 31 Felder - oben steht die Definition
    $ZeilenArray = $Zeile -Split ";"
    # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
    $LBMandant  = $ZeilenArray[0]
    $TRANSID    = $ZeilenArray[1]
    $KDNR       = $ZeilenArray[2]
    $KUSY       = $ZeilenArray[3]
    $BUBASID    = $ZeilenArray[4]
    $BUBAFID    = $ZeilenArray[5]
    $ANW        = $ZeilenArray[6]
    $PRODSCH    = $ZeilenArray[7]
    $AK         = $ZeilenArray[8]
    $DFG        = $ZeilenArray[9]
    $RKAPK      = $ZeilenArray[10]
    $RKAPP      = $ZeilenArray[11]
    $WHRGNOM    = $ZeilenArray[12]
    $BELK       = $ZeilenArray[13]
    $BELP       = $ZeilenArray[14]
    $WHRGBEL    = $ZeilenArray[15]
    $VAL        = $ZeilenArray[16]
    $FAE        = $ZeilenArray[17]
    $EINB       = $ZeilenArray[18]
    $ARTF       = $ZeilenArray[19]
    $QUELLE     = $ZeilenArray[20]
    $FORF       = $ZeilenArray[21]
    # Felder ausgeben, wenn bei der BuBa
    if ( $line -eq 0 ) {
       # Eine Datei sollte aber eine Kopfzeile besitzen ...aber nicht die ürsprüngliche 
       echo 'TRANSAKTIONS_ID;FORDERUNG_ID_BUBA;NOMINALBETRAG;NOMINALWAEHRUNG;BELEIHUNGSBETRAG;BELEIHUNGSWAEHRUNG;VALUTA_DATUM;FAELLIGKEIT_DATUM;EINBUCHUNG_DATUM;FORDERUNGSART;SCHULDNER_ID_BUBA;ANWENDUNGSNUMMER;'
       $StatusAusgabeJN = 'N'
       $line = 1
    }
    else { # nur für den Zähler
       $AnzAlle++
    } # nur für den Zähler
    # Fuer spaeter, wenn BLB und NLB getrennt angeliefert werden muesste
    if ($StatusAusgabeJN -eq 'J') { 
      if ($LBMandant -ieq $BLBVGL) { # nur BLB
          echo $TRANSID';'$BUBAFID';'$RKAPK';'$WHRGNOM';'$BELK';'$WHRGBEL';'$VAL';'$FAE';'$EINB';'$ARTF';'$BUBASID';'$ANW';'
          $AnzBLB++
      } # nur BLB
    } # Ausgabe BLB
} | Set-Content $DateiMelde004
echo "Ende Ausgabe Meldwesen BLB Datum $(Get-Date -DisplayHint Date)"
# Teil 2 Ausgabe 
# Teil 2a - Meldewesen
# Ausgabe der interessanten Infos
echo "Anzahl Zeilen in der Meldung: $AnzAlle"
echo "           davon         NLB: $AnzNLB"
echo "           davon         BLB: $AnzBLB"
echo "Ende Ausgabe Meldwesen gesamt Datum $(Get-Date -DisplayHint Date)"
# Ausgabedatei pruefen
$Datei3Exist = Test-Path -Path $DateiMelde009 -PathType Leaf
$Datei4Exist = Test-Path -Path $DateiMelde004 -PathType Leaf
if ( ($Datei3Exist) -and ($Datei4Exist) ) {
   #Nur zur Info .. 
   echo "Anzahl Zeilen $DateiMelde009 $((Get-Content $DateiMelde009).length)"
   echo "Anzahl Zeilen $DateiMelde004 $((Get-Content $DateiMelde004).length)"
} # Alles gut
else { # Bei der Verarbeitung hat etwas nicht geklappt
   echo "Etwas daneben gegangen----------------------------------------"
   echo "Datei $DateiMelde009 vorhanden: $Datei3Exist"
   echo "Datei $DateiMelde004 vorhanden: $Datei4Exist"
   $ZentrRet    = 8
   echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
   exit $ZentrRet
}
# Zweiter Abnehmer OTC
# Headerdefinition
# $HeadDate1 = Get-Date -UFormat "%Y%m%d"
$HeadDate1 = $DPDatum
$HeadDate2 = Get-Date -UFormat "%Y%m%d%H%M%S"

$DateiMeldeOTC  = "OTC_TXSKEV_"+$HeadDate1+".csv"
echo "OTC-DateiName: $DateiMeldeOTC" 
# Ausgabedatei leerputzen, nur wenn da.. geht eigemtlich nicht w_Timestamp
$Datei5Exist = Test-Path -Path $DateiMeldeOTC -PathType Leaf
if ( $Datei5Exist ) {
     Remove-Item $DateiMeldeOTC
}
# Jetzt erste Ausgabedatei einlesen und die Ausgabedatei fuer OTC schreiben
$line       = 0
$AnzAlle    = 0
$AnzOTC     = 0
Get-Content $Dateiout1| ForEach-Object {
    # Initialisierung je Eingabezeile
    $StatusAusgabeJN = 'J'
    # Zeile ohne Leerzeichen ablegen
    $Zeile = ($_).Trim()
    # Zeile splitten ... Trenner ist Semikolon - 31 Felder - oben steht die Definition
    $ZeilenArray = $Zeile -Split ";"
    # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
    $LBMandant  = $ZeilenArray[0]
    $TRANSID    = $ZeilenArray[1]
    $KDNR       = $ZeilenArray[2]
    $KUSY       = $ZeilenArray[3]
    $BUBASID    = $ZeilenArray[4]
    $BUBAFID    = $ZeilenArray[5]
    $ANW        = $ZeilenArray[6]
    $PRODSCH    = $ZeilenArray[7]
    $AK         = $ZeilenArray[8]
    $DFG        = $ZeilenArray[9]
    $RKAPK      = $ZeilenArray[10]
    $RKAPP      = $ZeilenArray[11]
    $WHRGNOM    = $ZeilenArray[12]
    $BELK       = $ZeilenArray[13]
    $BELP       = $ZeilenArray[14]
    $WHRGBEL    = $ZeilenArray[15]
    $VAL        = $ZeilenArray[16]
    $FAE        = $ZeilenArray[17]
    $EINB       = $ZeilenArray[18]
    $ARTF       = $ZeilenArray[19]
    $QUELLE     = $ZeilenArray[20]
    $FORF       = $ZeilenArray[21]
    # Felder ausgeben, wenn bei der BuBa
    if ( $line -eq 0 ) {
       # Eine Datei sollte aber eine Kopfzeile besitzen ...aber nicht die ürsprüngliche 
       echo 'Institut;Transaktions-ID;KundenNr;Kusyma;Schuldner-ID BuBa;Forderungs-ID BuBa;Anwendungsnummer;Produktschluessel;Ausplatzierungsmerkmal;Deckungsschluessel;Restkapital;WaehrungNom;Beleihungsbetrag;WaehrungBel;Datum Valuta;Datum Faelligkeit;Datum Einbuchung;Art der Forderung;'
       $StatusAusgabeJN = 'N'
       $line = 1
    }
    else { # nur für den Zähler
       $AnzAlle++
    } # nur für den Zähler
    # Fuer spaeter, wenn BLB und NLB getrennt angeliefert werden muesste
    if ($StatusAusgabeJN -eq 'J') { 
      echo $LBMandant';'$TRANSID';'$KDNR';'$KUSY';'$BUBASID';'$BUBAFID';'$ANW';'$PRODSCH';'$DEFAK';'$DEFDFG';'$RKAPK';'$WHRGNOM';'$BELK';'$WHRGBEL';'$VAL';'$FAE';'$EINB';'$ARTF';'
      $AnzOTC++
    }
} | Set-Content $DateiMeldeOTC
# Teil 2 Ausgabe 
# Teil 2b - OTC
# Ausgabe der interessanten Infos
echo "Anzahl Zeilen in der Meldung: $AnzAlle"
echo "           davon   fuer  OTC: $AnzOTC"
echo "Ende Ausgabe OTC      Datum $(Get-Date -DisplayHint Date)"
# Ausgabedatei pruefen
$Datei5Exist = Test-Path -Path $DateiMeldeOTC -PathType Leaf
if ( $Datei5Exist ) {
   #Nur zur Info .. 
   echo "Anzahl Zeilen $DateiMeldeOTC $((Get-Content $DateiMeldeOTC).length)"
} # Alles gut
else { # Bei der Verarbeitung hat etwas nicht geklappt
   echo "Etwas daneben gegangen----------------------------------------"
   echo "Datei $DateiMeldeOTC vorhanden: $Datei5Exist"
   $ZentrRet    = 16
   echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
   exit $ZentrRet
}
# Dritter Abnehmer CMC
# Headerdefinition
# $HeadDate1 = Get-Date -UFormat "%Y%m%d"
$HeadDate1 = $DPDatum
$HeadDate2 = Get-Date -UFormat "%Y%m%d%H%M%S"

$DateiMeldeCMC  = "ReportTXS_009_CMC_EB_KEV_"+$HeadDate1+".txt"
echo "CMC-DateiName: $DateiMeldeCMC" 
# Ausgabedatei leerputzen, nur wenn da.. geht eigemtlich nicht w_Timestamp
$Datei6Exist = Test-Path -Path $DateiMeldeCMC -PathType Leaf
if ( $Datei6Exist ) {
     Remove-Item $DateiMeldeCMC
}
# Jetzt erste Ausgabedatei einlesen und die Ausgabedatei fuer OTC schreiben
$line       = 0
$AnzAlle    = 0
$AnzCMC     = 0
Get-Content $Dateiout1| ForEach-Object {
    # Initialisierung je Eingabezeile
    $StatusAusgabeJN = 'J'
    # Zeile ohne Leerzeichen ablegen
    $Zeile = ($_).Trim()
    # Zeile splitten ... Trenner ist Semikolon - 31 Felder - oben steht die Definition
    $ZeilenArray = $Zeile -Split ";"
    # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
    $LBMandant  = $ZeilenArray[0]
    $TRANSID    = $ZeilenArray[1]
    $KDNR       = $ZeilenArray[2]
    $KUSY       = $ZeilenArray[3]
    $BUBASID    = $ZeilenArray[4]
    $BUBAFID    = $ZeilenArray[5]
    $ANW        = $ZeilenArray[6]
    $PRODSCH    = $ZeilenArray[7]
    $AK         = $ZeilenArray[8]
    $DFG        = $ZeilenArray[9]
    $RKAPK      = $ZeilenArray[10]
    $RKAPP      = $ZeilenArray[11]
    $WHRGNOM    = $ZeilenArray[12]
    $BELK       = $ZeilenArray[13]
    $BELP       = $ZeilenArray[14]
    $WHRGBEL    = $ZeilenArray[15]
    $VAL        = $ZeilenArray[16]
    $FAE        = $ZeilenArray[17]
    $EINB       = $ZeilenArray[18]
    $ARTF       = $ZeilenArray[19]
    $QUELLE     = $ZeilenArray[20]
    $FORF       = $ZeilenArray[21]
    # Felder ausgeben, wenn bei der BuBa
    if ( $line -eq 0 ) {
       # Eine Datei sollte aber eine Kopfzeile besitzen ...aber nicht die ürsprüngliche 
       echo 'Institut;Transaktions-ID;KundenNr;Kusyma;Schuldner-ID BuBa;Forderungs-ID BuBa;Anwendungsnummer;Produktschluessel;Ausplatzierungsmerkmal;Deckungsschluessel;Restkapital;WaehrungNom;Beleihungsbetrag;WaehrungBel;Datum Valuta;Datum Faelligkeit;Datum Einbuchung;Art der Forderung;'
       $StatusAusgabeJN = 'N'
       $line = 1
    }
    else { # nur für den Zähler
       $AnzAlle++
    } # nur für den Zähler
    # Fuer spaeter, wenn BLB und NLB getrennt angeliefert werden muesste
    if ($StatusAusgabeJN -eq 'J') { 
      echo $LBMandant';'$TRANSID';'$KDNR';'$KUSY';'$BUBASID';'$BUBAFID';'$ANW';'$PRODSCH';'$DEFAK';'$DEFDFG';'$RKAPP';'$WHRGNOM';'$BELP';'$WHRGBEL';'$VAL';'$FAE';'$EINB';'$ARTF';'
      $AnzCMC++
    }
} | Set-Content $DateiMeldeCMC
# Teil 2 Ausgabe 
# Teil 2c - CMC
# Ausgabe der interessanten Infos
echo "Anzahl Zeilen in der Meldung: $AnzAlle"
echo "           davon   fuer  CMC: $AnzCMC"
echo "Ende Ausgabe CMC      Datum $(Get-Date -DisplayHint Date)"
# Ausgabedatei pruefen
$Datei6Exist = Test-Path -Path $DateiMeldeCMC -PathType Leaf
if ( $Datei6Exist ) {
   #Nur zur Info .. 
   echo "Anzahl Zeilen $DateiMeldeCMC $((Get-Content $DateiMeldeCMC).length)"
} # Alles gut
else { # Bei der Verarbeitung hat etwas nicht geklappt
   echo "Etwas daneben gegangen----------------------------------------"
   echo "Datei $DateiMeldeCMC vorhanden: $Datei6Exist"
   $ZentrRet    = 32
   echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
   exit $ZentrRet
}
# Vierter Abnehmer KIT selber fuer Rueckmeldung
# Headerdefinition
# $HeadDate1 = Get-Date -UFormat "%Y%m%d"
$HeadDate1 = $DPDatum
$HeadDate2 = Get-Date -UFormat "%Y%m%d%H%M%S"

$DateiMeldeKIT  = "KIT_TXSKEV_RUECKNEU_"+$HeadDate1+".csv"
echo "KITDateiName: $DateiMeldeKIT" 
# Ausgabedatei leerputzen, nur wenn da.. geht eigemtlich nicht w_Timestamp
$Datei7Exist = Test-Path -Path $DateiMeldeKIT -PathType Leaf
if ( $Datei7Exist ) {
     Remove-Item $DateiMeldeKIT
}
# Jetzt erste Ausgabedatei einlesen und die Ausgabedatei fuer OTC schreiben
$line       = 0
$AnzAlle    = 0
$AnzKIT     = 0
Get-Content $Dateiout1| ForEach-Object {
    # Initialisierung je Eingabezeile
    $StatusAusgabeJN = 'J'
    # Zeile ohne Leerzeichen ablegen
    $Zeile = ($_).Trim()
    # Zeile splitten ... Trenner ist Semikolon - 31 Felder - oben steht die Definition
    $ZeilenArray = $Zeile -Split ";"
    # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
    $LBMandant  = $ZeilenArray[0]
    $TRANSID    = $ZeilenArray[1]
    $KDNR       = $ZeilenArray[2]
    $KUSY       = $ZeilenArray[3]
    $BUBASID    = $ZeilenArray[4]
    $BUBAFID    = $ZeilenArray[5]
    $ANW        = $ZeilenArray[6]
    $PRODSCH    = $ZeilenArray[7]
    $AK         = $ZeilenArray[8]
    $DFG        = $ZeilenArray[9]
    $RKAPK      = $ZeilenArray[10]
    $RKAPP      = $ZeilenArray[11]
    $WHRGNOM    = $ZeilenArray[12]
    $BELK       = $ZeilenArray[13]
    $BELP       = $ZeilenArray[14]
    $WHRGBEL    = $ZeilenArray[15]
    $VAL        = $ZeilenArray[16]
    $FAE        = $ZeilenArray[17]
    $EINB       = $ZeilenArray[18]
    $ARTF       = $ZeilenArray[19]
    $QUELLE     = $ZeilenArray[20]
    $FORF       = $ZeilenArray[21]
    # Felder ausgeben, wenn bei der BuBa
    if ( $line -eq 0 ) {
       # KIT-Rueckmeldedatei hat keine Heade
       $StatusAusgabeJN = 'N'
       $line = 1
    }
    else { # nur für den Zähler
       $AnzAlle++
    } # nur für den Zähler
    # Fuer spaeter, wenn BLB und NLB getrennt angeliefert werden muesste
    if ($StatusAusgabeJN -eq 'J') { 
      echo $LBMandant';'$QUELLE';'$ANW';'$KDNR';'$TRANSID';'$BELP';'
      $AnzKIT++
    }
} | Set-Content $DateiMeldeKIT
# Teil 2 Ausgabe 
# Teil 2d - KIT
# Ausgabe der interessanten Infos
echo "Anzahl Zeilen in der Meldung: $AnzAlle"
echo "           davon   fuer  KIT: $AnzKIT"
echo "Ende Ausgabe KIT      Datum $(Get-Date -DisplayHint Date)"
# Ausgabedatei pruefen
$Datei7Exist = Test-Path -Path $DateiMeldeKIT -PathType Leaf
if ( $Datei7Exist ) {
   #Nur zur Info .. 
   echo "Anzahl Zeilen $DateiMeldeKIT $((Get-Content $DateiMeldeKIT).length)"
} # Alles gut
else { # Bei der Verarbeitung hat etwas nicht geklappt
   echo "Etwas daneben gegangen----------------------------------------"
   echo "Datei $DateiMeldeKIT vorhanden: $Datei7Exist"
   $ZentrRet    = 64
   echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
   exit $ZentrRet
}
echo "Ende ReadCAMRueckAll Datum $(Get-Date -DisplayHint Date)  - Return $ZentrRet"
exit $ZentrRet
# Ende Gelende :-)        