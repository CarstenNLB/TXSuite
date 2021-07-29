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

public class Pfandobjekt {

  private String pfandobjektNummer;
  private String ewert;
  private String bwert;
  private String bwertdat;
  private String deckung;
  private String ertragsf;
  private String rechtlich;
  private String wirtschaftlich;
  private String nutzungsart;
  private String eigennutzung;
  private String projektentwicklung;
  private String fertigstellung;
  private String gebiet;
  private String gesamtgrundschuld;
  private String gewerblich;
  private String hausnr;
  private String jahr;
  private String kat;
  private String land;
  private String otyp;
  private String plz;
  private String str;
  private String swert;
  private String whrg;
  private String zusatz;
  private String ort;

  /**
   * Strasse komplett (mit Hausnummer und eventuell zweiter Strasse)
   */
  private String strhn;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor - initialisiert die Variablen mit leeren Strings
   * @param pvLogger
   */
  public Pfandobjekt(Logger pvLogger)
  {
    ivLogger = pvLogger;

    pfandobjektNummer = new String();
    ewert = new String();
    bwert = new String();
    bwertdat = new String();
    deckung = new String();
    ertragsf = new String();
    rechtlich = new String();
    wirtschaftlich = new String();
    nutzungsart = new String();
    eigennutzung = new String();
    projektentwicklung = new String();
    fertigstellung = new String();
    gebiet = new String();
    gesamtgrundschuld = new String();
    gewerblich = new String();
    hausnr = new String();
    jahr = new String();
    kat = new String();
    land = new String();
    otyp = new String();
    plz = new String();
    str = new String();
    swert = new String();
    whrg = new String();
    zusatz = new String();
    ort = new String();
    strhn = new String();
  }

  /**
   * Einlesen der Pfandobjekt-Daten
   * @param pvDateiname Dateiname der Pfandobjekt-Daten
   * @param pvListePfandobjekt Liste der Pfandobjekt-Daten
   * @param pvLogger log4j-Logger
   * @return Anzahl der eingelesenen Pfandobjekt-Daten
   */
  public static int readPfandobjekt(String pvDateiname, HashMap<String, Pfandobjekt> pvListePfandobjekt, Logger pvLogger)
  {
    String lvZeile = null;
    int lvZaehlerPfandobjekte = 0;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDH = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDH);
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte Pfandobjekt-Datei nicht oeffnen!");
      return lvZaehlerPfandobjekte;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Deckung-Datei ein
      {
        Pfandobjekt lvPfandobjekt = new Pfandobjekt(pvLogger);
        if (lvPfandobjekt.parsePfandobjekt(lvZeile)) // Parsen der Felder
        {
          lvZaehlerPfandobjekte++;
          pvListePfandobjekt.put(lvPfandobjekt.getPfandobjektNummer(), lvPfandobjekt);
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
      pvLogger.error("Konnte Pfandobjekt-Datei nicht schliessen!");
    }

    return lvZaehlerPfandobjekte;
  }


  /**
   * Zerlegt eine Pfandobjektzeile in die einzelnen Felder/Werte
   * @param pvZeile
   * @return
   */
  private boolean parsePfandobjekt(String pvZeile)
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
         this.setPfandobjekt(lvLfd, lvTemp.trim());

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
    this.setPfandobjekt(lvLfd, lvTemp.trim());

    return true;
  }

  /**
   * Setzt einen Wert der Tabelle 'Pfandobjekt'
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setPfandobjekt(int pvPos, String pvWert)
  {
    switch (pvPos)
    {
      case 0:
        this.pfandobjektNummer = pvWert;
        break;
      case 1:
        this.ewert = pvWert.replace(",", ".");
        break;
      case 2:
        this.bwert = pvWert.replace(",", ".");
        break;
      case 3:
        this.bwertdat = pvWert;
        break;
      case 4:
        this.deckung = pvWert;
        break;
      case 5:
        this.ertragsf = pvWert;
        break;
      case 6:
        this.rechtlich = pvWert;
        break;
      case 7:
        this.wirtschaftlich = pvWert;
        break;
      case 8:
        this.nutzungsart = pvWert;
        break;
      case 9:
        this.eigennutzung = pvWert;
        break;
      case 10:
        this.projektentwicklung = pvWert;
        break;
      case 11:
        this.fertigstellung = pvWert;
        break;
      case 12:
        this.gebiet = pvWert;
        break;
      case 13:
        this.gesamtgrundschuld = pvWert;
        break;
      case 14:
        this.gewerblich = pvWert;
        break;
      case 15:
        this.hausnr = pvWert;
        break;
      case 16:
        this.jahr = pvWert;
        break;
      case 17:
        this.kat = pvWert;
        break;
      case 18:
        this.land = pvWert;
        break;
      case 19:
        this.otyp = pvWert;
        break;
      case 20:
        this.plz = pvWert;
        break;
      case 21:
        this.str = pvWert;
        break;
      case 22:
        this.swert = pvWert.replace(",", ".");
        break;
      case 23:
        this.whrg = pvWert;
        break;
      case 24:
        this.zusatz = pvWert;
        break;
      case 25:
        this.ort = pvWert;
        break;
      case 26:
        this.strhn = pvWert;
        break;
      default:
        ivLogger.error("Pfandobjekt: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
    }
  }

  public String getPfandobjektNummer() {
    return pfandobjektNummer;
  }

  public void setPfandobjektNummer(String pfandobjektNummer) {
    this.pfandobjektNummer = pfandobjektNummer;
  }

  public String getBwert() {
    return bwert;
  }

  public void setBwert(String bwert) {
    this.bwert = bwert;
  }

  public String getBwertdat() {
    return bwertdat;
  }

  public void setBwertdat(String bwertdat) {
    this.bwertdat = bwertdat;
  }

  public String getDeckung() {
    return deckung;
  }

  public void setDeckung(String deckung) {
    this.deckung = deckung;
  }

  public String getErtragsf() {
    return ertragsf;
  }

  public void setErtragsf(String ertragsf) {
    this.ertragsf = ertragsf;
  }

  public String getEwert() {
    return ewert;
  }

  public void setEwert(String ewert) {
    this.ewert = ewert;
  }
  public String getRechtlich() {
    return rechtlich;
  }

  public void setRechtlich(String rechtlich) {
    this.rechtlich = rechtlich;
  }

  public String getWirtschaftlich() {
    return wirtschaftlich;
  }

  public void setWirtschaftlich(String wirtschaftlich) {
    this.wirtschaftlich = wirtschaftlich;
  }

  public String getNutzungsart() {
    return nutzungsart;
  }

  public void setNutzungsart(String nutzungsart) {
    this.nutzungsart = nutzungsart;
  }

  public String getEigennutzung() {
    return eigennutzung;
  }

  public void setEigennutzung(String eigennutzung) {
    this.eigennutzung = eigennutzung;
  }

  public String getProjektentwicklung() {
    return projektentwicklung;
  }

  public void setProjektentwicklung(String projektentwicklung) {
    this.projektentwicklung = projektentwicklung;
  }


  public String getFertigstellung() {
    return fertigstellung;
  }

  public void setFertigstellung(String fertigstellung) {
    this.fertigstellung = fertigstellung;
  }

  public String getGebiet() {
    return gebiet;
  }

  public void setGebiet(String gebiet) {
    this.gebiet = gebiet;
  }

  public String getGesamtgrundschuld() {
    return gesamtgrundschuld;
  }

  public void setGesamtgrundschuld(String gesamtgrundschuld) {
    this.gesamtgrundschuld = gesamtgrundschuld;
  }

  public String getGewerblich() {
    return gewerblich;
  }

  public void setGewerblich(String gewerblich) {
    this.gewerblich = gewerblich;
  }

  public String getHausnr() {
    return hausnr;
  }

  public void setHausnr(String hausnr) {
    this.hausnr = hausnr;
  }

  public String getJahr() {
    return jahr;
  }

  public void setJahr(String jahr) {
    this.jahr = jahr;
  }

  public String getKat() {
    return kat;
  }

  public void setKat(String kat) {
    this.kat = kat;
  }

  public String getLand() {
    return land;
  }

  public void setLand(String land) {
    this.land = land;
  }

  public String getOtyp() {
    return otyp;
  }

  public void setOtyp(String otyp) {
    this.otyp = otyp;
  }

  public String getPlz() {
    return plz;
  }

  public void setPlz(String plz) {
    this.plz = plz;
  }

  public String getStr() {
    return str;
  }

  public void setStr(String str) {
    this.str = str;
  }

  public String getSwert() {
    return swert;
  }

  public void setSwert(String swert) {
    this.swert = swert;
  }

  public String getWhrg() {
    return whrg;
  }

  public void setWhrg(String whrg) {
    this.whrg = whrg;
  }

  public String getZusatz() {
    return zusatz;
  }

  public void setZusatz(String zusatz) {
    this.zusatz = zusatz;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String pvOrt) {
    this.ort = pvOrt;
  }

  public String getStrhn() {
    return strhn;
  }

  public void setStrhn(String strhn) {
    this.strhn = strhn;
  }
}
