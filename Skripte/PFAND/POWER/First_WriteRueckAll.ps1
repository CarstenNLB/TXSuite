##############################################################################
## WriteRueckAll.ps1
## Rueckmeldedateien erzeugen
##      Basis sind die Infos, die aus ReadRueckAll.ps1 erzeugt worden sind
##      Zusätzliche Eingabedatei AbgangFiltern.txt
##      Ausgabe von Abgangfiltern.txs und TXS2CMS_004/009.csv
##      Sinn und Zweck: Die Negation (Deckungskennzeichen 'N' soll 
##                      nur einmal an SAP CMS geliefert werden.. das muss
##                      sich gemerkt werden (in der Abgangsliste ..)
## Zwei Versionen
## --> a) Sonderlauf ... nur einmal .. Ablage-Datei auf Triple umstellen
##                                     von reiner Objektdatei
## --> b) Standard .. arbeiten mit Triple BONr/SiNr/KtoNr
##    Returnwerte
##                  0 -> alles ok
##                  1 -> Eingabedatei(en) fehl(t|en)
##                  2 -> AbgangFiltern nach Sort hat keinen Inhalt
##                  3 -> NLB_RUECK-Datei nach Sort hat keinen Inhalt
##                  4 -> AbgangFiltern nach der Verarbietung hat keinen Inhalt
##                  8 -> SAP CMS 009 - Datei wurde nicht erzeugt
##                  16-> SAP CMS 009 - Datei wurde erzeugt, ist aber leer
##                  32-> SAP CMS 000 - Datei wurde nicht erzeugt
##                  64-> SAP CMS 000 - Datei wurde erzeugt, ist aber leer
##                  12 =  4 +  8
##                  20 =  4 + 16
##                  36 =  4 + 32
##                  68 =  4 + 64
##                  40 =  8 + 32
##                  72 =  8 + 64
##                  48 = 16 + 32
##                  80 = 16 + 64
##                  44 =  4 +  8 + 32
##                  76 =  4 +  8 + 64
##                  52 =  4 + 16 + 32
##                  84 =  4 + 16 + 64
##
## Ablösung TXS Rueckmeldverarbeitung Sicherheiten 
## SPA 20190805
##############################################################################
<#
.SYNOPSIS
Skript zum Verarbeiten der Rueckmeldedateien
Teil 2 
.AUTHOR
SPA / 101062 / -5874
#>

# Definition der Eingabezeilen durch Semikolon getrennt (am Ende auch ..)
# 00 Mandant (004/009)
# 01 KontoNr (ResAZ)
# 02 Ausplatzierungsmerkmal (2Stellig - ResAK)
# 03 Deckungskennzeichen Finanzgeschaeft (1stellig) DeckFG
# 04 Sicherheitennummer (ResSi)
# 05 Deckungskennzeichen Sicherheit (DeckSi)
# 06 Vermoegensobjektnummer (ResVO)
# 07 Deckungskennzeichen Sicherheit (DeckSi) (VO hat kann kein eigenes Kennzeichen besitzen, haengt von der Si ab)

# Diese Struktur soll spaeter die sortierte Abgangsliste besitzen.. aktuell dort nur Vermoegensobjektnummr (mit ;)
# Ausplatzierung/Deckungskz nicht notwendig .. da hier nur die N-Zeilen relevant 
# 00 Vermoegensobjektnummer (NonVO)
# 01 Sicherheitennummer (NonSi)
# 02 KontoNr (NonAZ)

# Mainteil
echo "Start WriteRueckAll Datum $(Get-Date -DisplayHint Date)"
# Zentrale Returnvariable
$ZentrRet = 0
# Dateinamen angeben 
# Zuerst Abgangfilter.txt
$DateiIn1  = "AbgangFiltern.txt"
$DateiOut1 = "AbgangFilternSort.txt"
$DateiNeu1 = "AbgangFilternNeu.txt"
# Dann  NLB_Rueck.txt
$DateiIn2  = "NLB_Rueck.txt"
$DateiOut2 = "NLB_RueckSort.txt"
# Dateien SAP CMS
$SAPCMS009 = "TXS2CMS_009.csv"
$SAPCMS004 = "TXS2CMS_004.csv"
# Zu Beginn schon einmal Aufräumen .. Daten vom Vortag
# Was muss weg .. Zwischendateien
# Ausgabedatei leerputzen, nur wenn da
# $DateiOut1 = "AbgangFilternSort.txt"
$DateiExist = Test-Path -Path $DateiOut1 -PathType Leaf
if ( $DateiExist ) {
     Remove-Item $DateiOut1
} 
# $DateiNeu1 = "AbgangFilternNeu.txt"
$DateiExist = Test-Path -Path $DateiNeu1 -PathType Leaf
if ( $DateiExist ) {
     Remove-Item $DateiNeu1
} 
# $DateiOut2 = "NLB_RueckSort.txt"
$DateiExist = Test-Path -Path $DateiOut2 -PathType Leaf
if ( $DateiExist ) {
     Remove-Item $DateiOut2
} 
# .. Ergebnisdateien
# $SAPCMS009 = "TXS2CMS_009.csv"
$DateiExist = Test-Path -Path $SAPCMS009 -PathType Leaf
if ( $DateiExist ) {
     Remove-Item $SAPCMS009
} 
# $SAPCMS004 = "TXS2CMS_004.csv"
$DateiExist = Test-Path -Path $SAPCMS004 -PathType Leaf
if ( $DateiExist ) {
     Remove-Item $SAPCMS004
} 
# Test, ob Eingabedateien vorhanden
# $DateiIn1  = "AbgangFiltern.txt"
# $DateiIn2  = "NLB_Rueck.txt"
$Datei1Exist = Test-Path -Path $Dateiin1 -PathType Leaf
$Datei2Exist = Test-Path -Path $Dateiin2 -PathType Leaf
if ( ($Datei1Exist) -and ($Datei2Exist) ) {
    #Nur zur Info .. 
    echo "Anzahl Zeilen $Dateiin1 $((Get-Content $Dateiin1).length)"
    echo "Anzahl Zeilen $Dateiin2 $((Get-Content $Dateiin2).length)"
}
else { # Oha .. nicht alles da .. mit Returncode raus
    echo "Etwas daneben gegangen----------------------------------------"
    echo "Datei $Dateiin1 vorhanden: $Datei1Exist"
    echo "Datei $Dateiin2 vorhanden: $Datei2Exist"
    $ZentrRet = 1
    echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
    exit $ZentrRet
} # Oha .. nicht alles da .. mit Returncode raus
# Sort und dopplete Einträge elemenieren als ersten Part
# Zuerst Abgangfilter.txt
$AnzIn1 = $(Get-Content $DateiIn1).length
echo "Anzahl SORTREIN $AnzIn1"
# DateiIn1 sortieren (hier steht noch VONr drin) und Duplikate raus .. sort -unique
Get-Content $DateiIn1 | Sort -Unique | Set-Content $DateiOut1
$AnzOut1 = $(Get-Content $DateiOut1).length
echo "Anzahl SORTRAUS $AnzOut1"
echo "Ende AbgangFilternSort Datum $(Get-Date -DisplayHint Date)"
if ($AnzOut1 -eq 0) { # AbgangFiltern ist leer ... raus
    $ZentrRet = 2
    echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
    exit $ZentrRet
} # AbgangFiltern ist leer ... raus
# Dann  NLB_Rueck.txt
# Wieviele Elemente hat Sie denn 
$AnzIn2 = $(Get-Content $DateiIn2).length
echo "Anzahl NLB__RUECK $AnzIn2"
# DateiIn2 sortieren (nach VONr/SiNr/KtoNr) .. ersten/letzten Satz raus (Ueberschrift und Zeilenende)
# Für Testzwecke die naechsten Zeilen auskommentieren
Get-Content $DateiIn2 | Select-Object -Skip 1 | Select-Object -SkipLast 1 | Sort-Object { $_.Split(';')[6]+$_.Split(';')[4]+$_.Split(';')[1] } | Set-Content $DateiOut2
# Wieviele Elemente hat Sie denn 
$AnzOut2 = $(Get-Content $DateiOut2).length
echo "Anzahl NLB__RUECKSORT $AnzOut2"
echo "Ende RueckSort Datum $(Get-Date -DisplayHint Date)"
if ($AnzOut2 -eq 0) { # AbgangFiltern ist leer ... raus
    $ZentrRet = 3
    echo "Etwas daneben gegangen-Return $ZentrRet-----------------------"
    exit $ZentrRet
} # AbgangFiltern ist leer ... raus
# DateiOut1 in Array einlesen
$Unterstr = '_'
# Array definieren 
$Abgleich = @()
# Jetz befüllen
Get-Content $DateiOut1 | ForEach-Object {
  # Initialisierung je Eingabezeile
  # 00 Vermoegensobjektnummer (NonVO)
  # 01 Sicherheitennummer (NonSi)
  # 02 KontoNr (NonAZ)
  # Zeile ohne Leerzeichen ablegen
  $Zeile1 = ($_).Trim()
  $Zeile1Array = $Zeile1 -Split "_"
  $NonVo  = $Zeile1Array[0]
  #$NonSi  = $Zeile1Array[1]
  $NonSi  = 'SICHER'
  #$NonAZ  = $Zeile1Array[2]
  $NonAZ  =  'KONTO'
  $SuchZ1 =  $NonVo+$Unterstr+$NonSi+$Unterstr+$NonAZ
  # Kein Splitten .. ganze Zeile in das Array
  $Abgleich += $SuchZ1
} 
echo $Abgleich.count
# Jetzt ist die Tabelle  Abgleich mit dem Inhalt der Datei AbgangFilternSort gefüllt ..
#Testausgabe .. falls notwendig
#forEach($Val in $Abgleich) {
#  Write-Host $Val
#}
echo "Ende DateiAbgangFilternSort geladen Datum $(Get-Date -DisplayHint Date)"
# Jetzt sortiert NLB_Rueckdatei einlesen und Zeilenweise pruefen..zusammen mit der Datei AbgangFilternSort
# Relevant ist hier das Auplatzierungsmerkmal..
# Ausplatzierungsmerkmal ungleich 99 und ungleich {F|H|K|O|S}0 ... dann je nach Mandant in SAPCMS-Rueckmeldedatei
# Ausplatzierungsmerkmal = 99 
#                             Triple in Abgangsliste => Keine Ausgabe der Zeile
#                             Triple nicht in Abgangsliste, dann Ausgabe der Zeile (je nach Mandant) und Werte in Abgangsliste hinzufuegen
# Ausplatzierungsmerkmal = {F|H|K|O|S}0
#                             Triple in Abgangsliste => Ausgabe der Zeile (ja nach Mandant) und Werte in Abgangsliste loeschen
#                             Triple nicht in Abgangsliste, dann Ausgabe der Zeile (je nach Mandant)
# Am Ende die Abgangsliste ausgeben
# 
# Variablen für die Ausgabe
# Dateien SAP CMS
# Zaehlvariablen
# Wieviele waren für die LB gedacht
$AnzSS009    = 0
$AnzSS004    = 0
# Wieviele wurden davon an SPA CMS uebergeben
$ZaehlS009   = 0
$ZaehlS004   = 0
# Wieviele sind rausgeflogen
$NotS009     = 0
$NotS004     = 0
# Wieviele sind in die Abgleichsliste rein- bzw. rausgekommen
$AbgleichIn  = 0
$AbgleichOut = 0
# Vergleich
$NLBVGL      = "009"
$BLBVGL      = "004"
# Headerdefinition
$HeadDate1 = Get-Date -UFormat "%d.%m.%Y;"
$HeadDate2 = Get-Date -UFormat "%Y%m%d%H%M%S;"
$Header009 = "00;009;TXS_PROD;txsbatch;"+$HeadDate1+$HeadDate2+$AnzOut2+";"
$Header004 = "00;004;TXS_PROD;txsbatch;"+$HeadDate1+$HeadDate2+$AnzOut2+";"
echo $Header009
echo $Header004
# Aus Perfomancegruenden wird erst einmal nur NORDLB durchlaufen, danach das selbe mit der BLB
echo "Start NORDLB_Rueck $(Get-Date -DisplayHint Date)"
Get-Content $DateiOut2 | ForEach-Object {
  # 00 Mandant (004/009) (ResLB)
  # 01 KontoNr (ResAZ)
  # 02 Ausplatzierungsmerkmal (2Stellig - ResAK)
  # 03 Deckungskennzeichen Finanzgeschaeft (1stellig) DeckFG
  # 04 Sicherheitennummer (ResSi)
  # 05 Deckungskennzeichen Sicherheit (DeckSi)
  # 06 Vermoegensobjektnummer (ResVO)
  # 07 Deckungskennzeichen Sicherheit (DeckSi) (VO hat kann kein eigenes Kennzeichen besitzen, haengt von der Si ab)
  #    Eigener Name.. damit keine Verwechselung 
  $Zeile2 = ($_).Trim()
  $Zeile2Array = $Zeile2 -Split ";"
  # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
  $Re2LB        = $Zeile2Array[0]
  $Re2AZ        = $Zeile2Array[1]
  $NONAZ        = 'KONTO'
  $Re2AK        = $Zeile2Array[2]
  $Re2DeckFG    = $Zeile2Array[3]
  $Re2Si        = $Zeile2Array[4]
  $NONSi        = 'SICHER'
  $Re2DeckSi    = $Zeile2Array[5]
  $Re2VO        = $Zeile2Array[6]
  $Re2DeckVo    = $Zeile2Array[7]
  $Semikolon    = ";"
  $OutputAll    = "50;"+$Re2AZ+$Semikolon+$Re2DeckFG+$Semikolon+$Re2Si+$Semikolon+$Re2DeckSi+$Semikolon+$Re2VO+$Semikolon+$Re2DeckVO+$Semikolon
  # $SuchFeld     = $Re2VO+$Unterstr+$Re2Si+$Unterstr+$Re2AZ - ab zweitem Lauf 
  $SuchFeld     = $Re2VO+$Unterstr+$NONSi+$Unterstr+$NONAZ # zum Start
  # Feld zum Hinzufüegen in die Abgangsliste
  $SuchKop      = $Re2VO+$Unterstr+$Re2Si+$Unterstr+$Re2AZ
  if ($Re2LB -ieq $NLBVGL) { # nur NORDLB
      # Sonderfaelle .. da kommt es auf die Abgangsliste an
      if ( ($Re2AK -ieq "99") -or 
           ($Re2AK -ieq "F0") -or 
           ($Re2AK -ieq "H0") -or 
           ($Re2AK -ieq "K0") -or 
           ($Re2AK -ieq "S0") -or 
           ($Re2AK -ieq "O0") ) { 
        $Gefunden = $Abgleich -contains $SuchFeld 
        if ($Re2AK -ieq "99") { # die die raus sollen
          if ($Gefunden) { # Zeile in Abgangsliste
            # Standardfall
            # super nix zu tun .. nur zaehler hochsetzen
            $NotS009++
            # Sonderfall .. korrektes Triple ablegen - einmalig
            $Abgleich += $SuchKop
            $AbgleichIn++
          } # Zeile in Abgangsliste
          else { # leider mehr zu tun
            # Zeile geht raus 
            if ( $ZaehlS009 -eq 0 ) { # nur vor der ersten Zeile den Header
                # Eine Datei sollte aber eine Kopfzeile besitzen ...
                echo $Header009
            } # nur vor der ersten Zeile den Header
            echo $OutputAll
            $ZaehlS009++
            # Sorge dafuer tragen, dass nur einmal gemeldet wird
            $Abgleich += $SuchKop
            $AbgleichIn++
          } # leider mehr zu tun
        } # die die raus sollen
        else { # die neu gekommen sind .. ggfs. aus Abgangsliste loeschen #
         # Auf jeden Fall geht die Zeile raus
            if ( $ZaehlS009 -eq 0 ) { # nur vor der ersten Zeile den Header
                # Eine Datei sollte aber eine Kopfzeile besitzen ...
                echo $Header009
            } # nur vor der ersten Zeile den Header
            echo $OutputAll
            $ZaehlS009++
            if ($Gefunden) { # wenn da, dann entfernen
                $Abgleich = $Abgleich | where {$_ -ne $SuchKop}
                $AbgleichOut++
            }# wenn da, dann entfernen
        } # die neu gekommen sind .. ggfs. aus Abgangsliste loeschen #          
       } # Sonderfaelle
       else { #Standard 
         if ( $ZaehlS009 -eq 0 ) { # nur vor der ersten Zeile den Header
             # Eine Datei sollte aber eine Kopfzeile besitzen ...
             echo $Header009
         } # nur vor der ersten Zeile den Header
         echo $OutputAll
         $ZaehlS009++
       } #Standard 
     $AnzSS009++
  } # nur NORDLB
} | Set-Content $SAPCMS009
echo "Ende NORDLB_Rueck $(Get-Date -DisplayHint Date)"
# Jetzt BLB
echo "Start BLB_Rueck $(Get-Date -DisplayHint Date)"
Get-Content $DateiOut2 | ForEach-Object {
  # 00 Mandant (004/009) (ResLB)
  # 01 KontoNr (ResAZ)
  # 02 Ausplatzierungsmerkmal (2Stellig - ResAK)
  # 03 Deckungskennzeichen Finanzgeschaeft (1stellig) DeckFG
  # 04 Sicherheitennummer (ResSi)
  # 05 Deckungskennzeichen Sicherheit (DeckSi)
  # 06 Vermoegensobjektnummer (ResVO)
  # 07 Deckungskennzeichen Sicherheit (DeckSi) (VO hat kann kein eigenes Kennzeichen besitzen, haengt von der Si ab)
  #    Eigener Name.. damit keine Verwechselung 
  $Zeile2 = ($_).Trim()
  $Zeile2Array = $Zeile2 -Split ";"
  # Test um bestimmte Felder auszugeben - Array faengt bei 0 an
  $Re2LB        = $Zeile2Array[0]
  $Re2AZ        = $Zeile2Array[1]
  $NONAZ        = 'KONTO'
  $Re2AK        = $Zeile2Array[2]
  $Re2DeckFG    = $Zeile2Array[3]
  $Re2Si        = $Zeile2Array[4]
  $NONSi        = 'SICHER'
  $Re2DeckSi    = $Zeile2Array[5]
  $Re2VO        = $Zeile2Array[6]
  $Re2DeckVo    = $Zeile2Array[7]
  $Semikolon    = ";"
  $OutputAll    = "50;"+$Re2AZ+$Semikolon+$Re2DeckFG+$Semikolon+$Re2Si+$Semikolon+$Re2DeckSi+$Semikolon+$Re2VO+$Semikolon+$Re2DeckVO+$Semikolon
  # $SuchFeld     = $Re2VO+$Unterstr+$Re2Si+$Unterstr+$Re2AZ - ab zweitem Lauf 
  $SuchFeld     = $Re2VO+$Unterstr+$NONSi+$Unterstr+$NONAZ # zum Start
  # Feld zum Hinzufüegen in die Abgangsliste
  $SuchKop      = $Re2VO+$Unterstr+$Re2Si+$Unterstr+$Re2AZ
  if ($Re2LB -ieq $BLBVGL) { # nur BLB
      # Sonderfaelle .. da kommt es auf die Abgangsliste an
      if ( ($Re2AK -ieq "99") -or 
           ($Re2AK -ieq "F0") -or 
           ($Re2AK -ieq "H0") -or 
           ($Re2AK -ieq "K0") -or 
           ($Re2AK -ieq "S0") -or 
           ($Re2AK -ieq "O0") ) { 
        $Gefunden = $Abgleich -contains $SuchFeld 
        if ($Re2AK -ieq "99") { # die die raus sollen
          if ($Gefunden) { # Zeile in Abgangsliste
            # Standardfall
            # super nix zu tun .. nur zaehler hochsetzen
            $NotS004++
            # Sonderfall .. korrektes Triple ablegen - einmalig
            $Abgleich += $SuchKop
            $AbgleichIn++
          } # Zeile in Abgangsliste
          else { # leider mehr zu tun
            # Zeile geht raus 
            if ( $ZaehlS004 -eq 0 ) { # nur vor der ersten Zeile den Header
                # Eine Datei sollte aber eine Kopfzeile besitzen ...
                echo $Header004
            } # nur vor der ersten Zeile den Header
            echo $OutputAll
            $ZaehlS004++
            # Sorge dafuer tragen, dass nur einmal gemeldet wird
            $Abgleich += $SuchKop
            $AbgleichIn++
          } # leider mehr zu tun
        } # die die raus sollen
        else { # die neu gekommen sind .. ggfs. aus Abgangsliste loeschen #
         # Auf jeden Fall geht die Zeile raus
            if ( $ZaehlS004 -eq 0 ) { # nur vor der ersten Zeile den Header
                # Eine Datei sollte aber eine Kopfzeile besitzen ...
                echo $Header004
            } # nur vor der ersten Zeile den Header
            echo $OutputAll
            $ZaehlS004++
            if ($Gefunden) { # wenn da, dann entfernen
                $Abgleich = $Abgleich | where {$_ -ne $SuchKop}
                $AbgleichOut++
            }# wenn da, dann entfernen
        } # die neu gekommen sind .. ggfs. aus Abgangsliste loeschen #          
       } # Sonderfaelle
       else { #Standard 
         if ( $ZaehlS004 -eq 0 ) { # nur vor der ersten Zeile den Header
             # Eine Datei sollte aber eine Kopfzeile besitzen ...
             echo $Header004
         } # nur vor der ersten Zeile den Header
         echo $OutputAll
         $ZaehlS004++
       } #Standard 
     $AnzSS004++
  } # nur BLB
} | Set-Content $SAPCMS004
echo "Ende BLB_Rueck $(Get-Date -DisplayHint Date)"
# Es fehlt noch die vernünftige Ausgabe der Abgangsliste
# Sonderfall .. die Zeilen mit SICHERKONTO rausloeschen
$SonderFeld = $NONSi+$Unterstr+$NONAZ 
$Abgleich   = $Abgleich | where {($_.IndexOf($SonderFeld) -eq -1)}
$Abgleich | Sort -unique | Out-File $DateiNeu1
# Wieviele sind jetzt in der Abgangsdatei
$AnzNeu1 = $(Get-Content $DateiNeu1).length
# Ausgabe der interessanten Infos
echo "Anzahl Zeilen in der Rueckmeldedatei: $AnzOut2"
echo "        davon fuer NORDLB vorgesehen: $AnzSS009"
echo "          davon an SAP CMS geschickt: $ZaehlS009"
echo "          davon     nicht  geschickt: $NotS009"
echo "        davon fuer    BLB vorgesehen: $AnzSS004"
echo "          davon an SAP CMS geschickt: $ZaehlS004"
echo "          davon     nicht  geschickt: $NotS004"
echo " Anzahl    Abgangsdatei ursprueglich: $AnzOut1"
echo "                               jetzt: $AnzNeu1"
echo "        im Lauf zusaetzlich gemeldet: $AbgleichIn"
echo "        im Lauf            geloescht: $AbgleichOut"
# Abgangsdatei
if ($AnzNeu1 -eq 0) { # Datei ist leer .. auch nicht gut
    $ZentrRet = 4  
} # Datei ist leer .. auch nicht gut
# $SAPCMS009 = "TXS2CMS_009.csv"
$DateiExist = Test-Path -Path $SAPCMS009 -PathType Leaf
if ( $DateiExist ) { # soll da sein
  if ($(Get-Content $SAPCMS009).length -eq 0) { # Datei ist leer .. auch nicht gut
    $ZentrRet = 16  
  } # Datei ist leer .. auch nicht gut
} 
else { # Oha nicht da .. nicht gut
    $ZentrRet = 8  
} # Oha nicht da .. nicht gut
# $SAPCMS004 = "TXS2CMS_004.csv"
$DateiExist = Test-Path -Path $SAPCMS004 -PathType Leaf
if ( $DateiExist ) {
  if ($(Get-Content $SAPCMS004).length -eq 0) { # Datei ist leer .. auch nicht gut
    $ZentrRet+= 64  
  } # Datei ist leer .. auch nicht gut
} 
else { # Oha nicht da .. nicht gut
    $ZentrRet+= 32  
} # Oha nicht da .. nicht gut
# wird alles am Anfang bereinigt .. damit im Fehlerfall noch alles da ist
echo "Ende WriteRueckAll Datum $(Get-Date -DisplayHint Date) - Return $ZentrRet"
exit $ZentrRet
# Ende Gelende :-)