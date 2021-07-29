/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class Grundbuch {

  private String pfandobjektNummer;
  private String sicherheitenNummer;
  private String band;
  private String blatt;
  private String gbvon;
  private String gericht;
  private String kat;
  private String lfdnr;
  private String flur;
  private String flurst;
  private String gem;
  private String abt;
  private String rang;
  private String grundpfandrechtWaehrung;
  private String abetragAktuell;
  private String nbetrag;
  private String vbetrag;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor - initialisiert die Variablen mit leeren Strings
   * @param pvLogger log4j-Logger
   */
  public Grundbuch(Logger pvLogger)
  {
    ivLogger = pvLogger;
    pfandobjektNummer = new String();
    sicherheitenNummer = new String();
    band = new String();
    blatt = new String();
    gbvon = new String();
    gericht = new String();
    kat = new String();
    lfdnr = new String();
    flur = new String();
    flurst = new String();
    gem = new String();
    abt = new String();
    rang = new String();
    grundpfandrechtWaehrung = new String();
    abetragAktuell = new String();
    nbetrag = new String();
    vbetrag = new String();
  }

  /**
   * Einlesen der Grundbuch-Daten
   * @param pvDateiname Dateiname der Grundbuch-Daten
   * @param pvListeGrundbuch Liste der Grundbuch-Daten
   * @param pvLogger log4j-Logger
   * @return Anzahl der eingelesenen Grundbuch-Daten
   */
  public static int readGrundbuch(String pvDateiname, HashMap<String, Grundbuch> pvListeGrundbuch, Logger pvLogger)
  {
    String lvZeile = null;
    int lvZaehlerGrundbuch = 0;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDH = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDH);
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte Grundbuch-Datei nicht oeffnen!");
      return lvZaehlerGrundbuch;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Grundbuch-Datei ein
      {
        Grundbuch lvGrundbuch = new Grundbuch(pvLogger);
        if (lvGrundbuch.parseGrundbuch(lvZeile)) // Parsen der Felder
        {
          lvZaehlerGrundbuch++;
          pvListeGrundbuch.put(new String("" + lvZaehlerGrundbuch), lvGrundbuch);
        }
      }
    }
    catch (Exception e)
    {
      pvLogger.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte Grundbuch-Datei nicht schliessen!");
    }

    return lvZaehlerGrundbuch;
  }

  /**
   * Zerlegt eine Grundbuchzeile in die einzelnen Felder/Werte
   * @param pvZeile die zu zerlegende Zeile
   * @return immer 'true'
   */
  private boolean parseGrundbuch(String pvZeile)
  {
    String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
    int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
    int    lvZzStr=0;              // pointer fuer satzbereich

    // Steuerung/Iteration Eingabesatz
    for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
    {
      // wenn Semikolon erkannt
      if (pvZeile.charAt(lvZzStr) == ';')
      {
         this.setGrundbuch(lvLfd, lvTemp.trim());

        lvLfd++;                  // naechste Feldnummer
        // loeschen Zwischenbuffer
        lvTemp = new String();
      }
      else
      {
        // uebernehmen Byte aus Eingabesatzposition
        lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
      }
    } // ende for

    // Letzte Feld auch noch setzen
    this.setGrundbuch(lvLfd, lvTemp.trim());

    return true;
  }

  /**
   * Setzt einen Wert der Tabelle 'Deckung'
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setGrundbuch(int pvPos, String pvWert)
  {
    switch (pvPos)
    {
      case 0:
        this.pfandobjektNummer = pvWert;
        break;
      case 1:
        this.sicherheitenNummer = pvWert;
        break;
      case 2:
        this.blatt = pvWert;
        break;
      case 3:
        this.band = pvWert;
        break;
      case 4:
        this.gbvon = pvWert;
        break;
      case 5:
        this.gericht = pvWert;
        break;
        case 6:
        this.lfdnr = pvWert;
        break;
      case 7:
        this.gem = pvWert;
        break;
      case 8:
        this.flurst = pvWert;
        break;
      case 9:
        this.flur = pvWert;
        break;
      case 10:
        this.grundpfandrechtWaehrung = pvWert;
        break;
      case 11:
        this.rang = pvWert;
        break;
      case 12:
        // gsbetrag
        break;
      case 13:
        this.vbetrag = pvWert.replace(",", ".");
        break;
      case 14:
        this.abt = pvWert;
        break;

       case 15:
        // einzel
        break;
      default:
        ivLogger.error("Grundbuch: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
    }
  }

  /**
   * Liefert das Grundbuch als String
   * @return
   */
  public String toString()
  {
    StringBuilder lvHelpString = new StringBuilder();
    // Nicht implementiert...

     return lvHelpString.toString();
  }

  /**
   *
   * @return
   */
  public String getPfandobjektNummer() {
    return pfandobjektNummer;
  }

  /**
   *
   * @param pfandobjektNummer
   */
  public void setPfandobjektNummer(String pfandobjektNummer) {
    this.pfandobjektNummer = pfandobjektNummer;
  }

  /**
   *
   * @return
   */
  public String getSicherheitenNummer() {
    return sicherheitenNummer;
  }

  /**
   *
   * @param sicherheitenNummer
   */
  public void setSicherheitenNummer(String sicherheitenNummer) {
    this.sicherheitenNummer = sicherheitenNummer;
  }

  /**
   *
   * @return
   */
  public String getBand() {
    return band;
  }

  /**
   *
   * @param band
   */
  public void setBand(String band) {
    this.band = band;
  }

  /**
   *
   * @return
   */
  public String getBlatt() {
    return blatt;
  }

  /**
   *
   * @param blatt
   */
  public void setBlatt(String blatt) {
    this.blatt = blatt;
  }

  /**
   *
   * @return
   */
  public String getGbvon() {
    return gbvon;
  }

  /**
   *
   * @param gbvon
   */
  public void setGbvon(String gbvon) {
    this.gbvon = gbvon;
  }

  /**
   *
   * @return
   */
  public String getGericht() {
    return gericht;
  }

  /**
   *
   * @param gericht
   */
  public void setGericht(String gericht) {
    this.gericht = gericht;
  }

  /**
   *
   * @return
   */
  public String getKat() {
    return kat;
  }

  /**
   *
   * @param kat
   */
  public void setKat(String kat) {
    this.kat = kat;
  }

  /**
   *
   * @return
   */
  public String getLfdnr() {
    return lfdnr;
  }

  /**
   *
   * @param lfdnr
   */
  public void setLfdnr(String lfdnr) {
    this.lfdnr = lfdnr;
  }

  /**
   *
   * @return
   */
  public String getFlur() {
    return flur;
  }

  /**
   *
   * @param flur
   */
  public void setFlur(String flur) {
    this.flur = flur;
  }

  /**
   *
   * @return
   */
  public String getFlurst() {
    return flurst;
  }

  /**
   *
   * @param flurst
   */
  public void setFlurst(String flurst) {
    this.flurst = flurst;
  }

  /**
   *
   * @return
   */
  public String getGem() {
    return gem;
  }

  /**
   *
   * @param gem
   */
  public void setGem(String gem) {
    this.gem = gem;
  }

  /**
   *
   * @return
   */
  public String getAbt() {
    return abt;
  }

  /**
   *
   * @param abt
   */
  public void setAbt(String abt) {
    this.abt = abt;
  }

  /**
   *
   * @return
   */
  public String getRang() {
    return rang;
  }

  /**
   *
   * @param rang
   */
  public void setRang(String rang) {
    this.rang = rang;
  }

  /**
   *
   * @return
   */
  public String getGrundpfandrechtWaehrung() {
    return grundpfandrechtWaehrung;
  }

  /**
   *
   * @param grundpfandrechtWaehrung
   */
  public void setGrundpfandrechtWaehrung(String grundpfandrechtWaehrung) {
    this.grundpfandrechtWaehrung = grundpfandrechtWaehrung;
  }

  /**
   *
   * @return
   */
  public String getAbetragAktuell() {
    return abetragAktuell;
  }

  /**
   *
   * @param abetragAktuell
   */
  public void setAbetragAktuell(String abetragAktuell) {
    this.abetragAktuell = abetragAktuell;
  }

  /**
   * Liefert den Nennbetrag
   * @return Nennbetrag
   */
  public String getNbetrag() {
    return nbetrag;
  }

  /**
   * Setzt den Nennbetrag
   * @param nbetrag Nennbetrag
   */
  public void setNbetrag(String nbetrag) {
    this.nbetrag = nbetrag;
  }

  /**
   * Liefert den Verfuegungsbetrag
   * @return Verfuegungsbetrag
   */
  public String getVbetrag() {
    return vbetrag;
  }

  /**
   * Setzt den Verfuegungsbetrag
   * @param vbetrag Verfuegungsbetrag
   */
  public void setVbetrag(String vbetrag) {
    this.vbetrag = vbetrag;
  }
}
