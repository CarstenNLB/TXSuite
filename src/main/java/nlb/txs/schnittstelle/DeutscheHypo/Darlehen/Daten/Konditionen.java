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

public class Konditionen {

  private String kontonummer;
  private String atkonv;
  private String atkonvtag;
  private String bankkal;
  private String bernom;
  private String cap;
  private String datltztanp;
  private String faellig;
  private String fixkalart;
  private String fixkonv;
  private String fixtage;
  private String fixtagedir;
  private String fixtagemod;
  private String floor;
  private String kalfix;
  private String kalkonv;
  private String kondkey;
  private String lrate;
  private String mantilg;
  private String manzins;
  private String monendkonv;
  private String nomzins;
  private String refzins;
  private String spread;
  private String tilgbeg;
  private String tilgdat;
  private String tilgryth;
  private String tilgsatz;
  private String tilgver;
  private String vfaellig;
  private String whrg;
  private String zahltyp;
  private String zinsbeg;
  private String zinsdat;
  private String zinsryth;
  private String zinstyp;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor - initialisiert die Variablen mit leeren Strings
   * @param pvLogger log4j-Logger
   */
  public Konditionen(Logger pvLogger)
  {
    ivLogger = pvLogger;

    kontonummer = new String();
    atkonv = new String();
    atkonvtag = new String();
    bankkal = new String();
    bernom = new String();
    cap = new String();
    datltztanp = new String();
    faellig = new String();
    fixkalart = new String();
    fixkonv = new String();
    fixtage = new String();
    fixtagedir = new String();
    fixtagemod = new String();
    floor = new String();
    kalfix = new String();
    kalkonv = new String();
    kondkey = new String();
    lrate = new String();
    mantilg = new String();
    manzins = new String();
    monendkonv = new String();
    nomzins = new String();
    refzins = new String();
    spread = new String();
    tilgbeg = new String();
    tilgdat = new String();
    tilgryth = new String();
    tilgsatz = new String();
    tilgver = new String();
    vfaellig = new String();
    whrg = new String();
    zahltyp = new String();
    zinsbeg = new String();
    zinsdat = new String();
    zinsryth = new String();
    zinstyp = new String();
  }

  /**
   * Einlesen der Konditionen-Daten
   * @param pvDateiname Dateiname der Konditionen-Daten
   * @param pvListeKonditionen Liste der Konditionen-Daten
   * @param pvLogger log4j-Logger
   * @return Anzahl der eingelesenen Konditionen-Daten
   */
  public static int readKonditionen(String pvDateiname, HashMap<String, Konditionen> pvListeKonditionen, Logger pvLogger)
  {
    String lvZeile = null;
    int lvZaehlerKonditionen = 0;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDH = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDH);
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte Konditionen-Datei nicht oeffnen!");
      return lvZaehlerKonditionen;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Konditionen-Datei ein
      {
        Konditionen lvKondition = new Konditionen(pvLogger);
        if (lvKondition.parseKonditionen(lvZeile)) // Parsen der Felder
        {
          lvZaehlerKonditionen++;
          pvListeKonditionen.put(lvKondition.getKontonummer(), lvKondition);
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
      pvLogger.error("Konnte Konditionen-Datei nicht schliessen!");
    }

    return lvZaehlerKonditionen;
  }

  /**
   * Zerlegt eine Konditionszeile in die einzelnen Felder/Werte
   * @param pvZeile die zu zerlegende Zeile
   * @return immer 'true'
   */
  public boolean parseKonditionen(String pvZeile)
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
        //System.out.println(lvTemp);
        this.setKondition(lvLfd, lvTemp.trim());

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
    this.setKondition(lvLfd, lvTemp.trim());

    return true;
  }

  /**
   * Setzt einen Wert der Tabelle 'Kondition'
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setKondition(int pvPos, String pvWert)
  {
    switch (pvPos)
    {
      case 0:
        this.kontonummer = pvWert;
        break;
      case 1:
        this.atkonv = pvWert;
        break;
      case 2:
        this.atkonvtag = pvWert;
        break;
      case 3:
        this.bankkal = pvWert;
        break;
      case 4:
        this.bernom = pvWert;
        break;
      case 5:
        this.cap = pvWert;
        break;
      case 6:
        this.datltztanp = pvWert;
        break;
      case 7:
        this.faellig = pvWert;
        break;
      case 8:
        this.fixkalart = pvWert;
        break;
      case 9:
        this.fixkonv = pvWert;
        break;
      case 10:
        this.fixtage = pvWert;
        break;
      case 11:
        this.fixtagedir = pvWert;
        break;
      case 12:
        this.fixtagemod = pvWert;
        break;
      case 13:
        this.floor = pvWert;
        break;
      case 14:
        this.kalfix = pvWert;
        break;
      case 15:
        this.kalkonv = pvWert;
        break;
      case 16:
        this.kondkey = pvWert;
        break;
      case 17:
        this.lrate = pvWert.replace(",", ".");
        break;
      case 18:
        this.mantilg = pvWert;
        break;
      case 19:
        this.manzins = pvWert;
        break;
      case 20:
        this.monendkonv = pvWert;
        break;
      case 21:
        this.nomzins = pvWert;
        break;
      case 22:
        this.refzins = pvWert;
        break;
      case 23:
        this.spread = pvWert;
        break;
      case 24:
        this.tilgbeg = pvWert;
        break;
      case 25:
        this.tilgdat = pvWert;
        break;
      case 26:
        this.tilgryth = pvWert;
        break;
      case 27:
        this.tilgsatz = pvWert;
        break;
      case 28:
        this.tilgver = pvWert;
        break;
      case 29:
        this.vfaellig = pvWert;
        break;
      case 30:
        this.whrg = pvWert;
        break;
      case 31:
        this.zahltyp = pvWert;
        break;
      case 32:
        this.zinsbeg = pvWert;
        break;
      case 33:
        this.zinsdat = pvWert;
        break;
      case 34:
        this.zinsryth = pvWert;
        break;
      case 35:
        this.zinstyp = pvWert;
        break;
      default:
        ivLogger.error("Konditionen: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
    }
  }

  /**
   *
   * @return
   */
  public String getKontonummer() {
    return kontonummer;
  }

  /**
   *
   * @param kontonummer
   */
  public void setKontonummer(String kontonummer) {
    this.kontonummer = kontonummer;
  }

  /**
   *
   * @return
   */
  public String getAtkonv() {
    return atkonv;
  }

  /**
   *
   * @param atkonv
   */
  public void setAtkonv(String atkonv) {
    this.atkonv = atkonv;
  }

  /**
   *
   * @return
   */
  public String getAtkonvtag() {
    return atkonvtag;
  }

  /**
   *
   * @param atkonvtag
   */
  public void setAtkonvtag(String atkonvtag) {
    this.atkonvtag = atkonvtag;
  }

  /**
   *
   * @return
   */
  public String getBankkal() {
    return bankkal;
  }

  /**
   *
   * @param bankkal
   */
  public void setBankkal(String bankkal) {
    this.bankkal = bankkal;
  }

  /**
   *
   * @return
   */
  public String getBernom() {
    return bernom;
  }

  /**
   *
   * @param bernom
   */
  public void setBernom(String bernom) {
    this.bernom = bernom;
  }

  /**
   *
   * @return
   */
  public String getCap() {
    return cap;
  }

  /**
   *
   * @param cap
   */
  public void setCap(String cap) {
    this.cap = cap;
  }

  /**
   *
   * @return
   */
  public String getDatltztanp() {
    return datltztanp;
  }

  /**
   *
   * @param datltztanp
   */
  public void setDatltztanp(String datltztanp) {
    this.datltztanp = datltztanp;
  }

  /**
   *
   * @return
   */
  public String getFaellig() {
    return faellig;
  }

  /**
   *
   * @param faellig
   */
  public void setFaellig(String faellig) {
    this.faellig = faellig;
  }

  /**
   *
   * @return
   */
  public String getFixkalart() {
    return fixkalart;
  }

  /**
   *
   * @param fixkalart
   */
  public void setFixkalart(String fixkalart) {
    this.fixkalart = fixkalart;
  }

  /**
   *
   * @return
   */
  public String getFixkonv() {
    return fixkonv;
  }

  /**
   *
   * @param fixkonv
   */
  public void setFixkonv(String fixkonv) {
    this.fixkonv = fixkonv;
  }

  /**
   *
   * @return
   */
  public String getFixtage() {
    return fixtage;
  }

  /**
   *
   * @param fixtage
   */
  public void setFixtage(String fixtage) {
    this.fixtage = fixtage;
  }

  /**
   *
   * @return
   */
  public String getFixtagedir() {
    return fixtagedir;
  }

  /**
   *
   * @param fixtagedir
   */
  public void setFixtagedir(String fixtagedir) {
    this.fixtagedir = fixtagedir;
  }

  /**
   *
   * @return
   */
  public String getFixtagemod() {
    return fixtagemod;
  }

  /**
   *
   * @param fixtagemod
   */
  public void setFixtagemod(String fixtagemod) {
    this.fixtagemod = fixtagemod;
  }

  /**
   *
   * @return
   */
  public String getFloor() {
    return floor;
  }

  /**
   *
   * @param floor
   */
  public void setFloor(String floor) {
    this.floor = floor;
  }

  /**
   *
   * @return
   */
  public String getKalfix() {
    return kalfix;
  }

  /**
   *
   * @param kalfix
   */
  public void setKalfix(String kalfix) {
    this.kalfix = kalfix;
  }

  /**
   *
   * @return
   */
  public String getKalkonv() {
    return kalkonv;
  }

  /**
   *
   * @param kalkonv
   */
  public void setKalkonv(String kalkonv) {
    this.kalkonv = kalkonv;
  }

  /**
   *
   * @return
   */
  public String getKondkey() {
    return kondkey;
  }

  public void setKondkey(String kondkey) {
    this.kondkey = kondkey;
  }

  public String getLrate() {
    return lrate;
  }

  public void setLrate(String lrate) {
    this.lrate = lrate;
  }

  public String getMantilg() {
    return mantilg;
  }

  public void setMantilg(String mantilg) {
    this.mantilg = mantilg;
  }

  public String getManzins() {
    return manzins;
  }

  public void setManzins(String manzins) {
    this.manzins = manzins;
  }

  public String getMonendkonv() {
    return monendkonv;
  }

  public void setMonendkonv(String monendkonv) {
    this.monendkonv = monendkonv;
  }

  public String getNomzins() {
    return nomzins;
  }

  public void setNomzins(String nomzins) {
    this.nomzins = nomzins;
  }

  public String getRefzins() {
    return refzins;
  }

  public void setRefzins(String refzins) {
    this.refzins = refzins;
  }

  public String getSpread() {
    return spread;
  }

  public void setSpread(String spread) {
    this.spread = spread;
  }

  public String getTilgbeg() {
    return tilgbeg;
  }

  public void setTilgbeg(String tilgbeg) {
    this.tilgbeg = tilgbeg;
  }

  public String getTilgdat() {
    return tilgdat;
  }

  public void setTilgdat(String tilgdat) {
    this.tilgdat = tilgdat;
  }

  public String getTilgryth() {
    return tilgryth;
  }

  public void setTilgryth(String tilgryth) {
    this.tilgryth = tilgryth;
  }

  public String getTilgsatz() {
    return tilgsatz;
  }

  public void setTilgsatz(String tilgsatz) {
    this.tilgsatz = tilgsatz;
  }

  public String getTilgver() {
    return tilgver;
  }

  public void setTilgver(String tilgver) {
    this.tilgver = tilgver;
  }

  public String getVfaellig() {
    return vfaellig;
  }

  public void setVfaellig(String vfaellig) {
    this.vfaellig = vfaellig;
  }

  public String getWhrg() {
    return whrg;
  }

  public void setWhrg(String whrg) {
    this.whrg = whrg;
  }

  public String getZahltyp() {
    return zahltyp;
  }

  public void setZahltyp(String zahltyp) {
    this.zahltyp = zahltyp;
  }

  public String getZinsbeg() {
    return zinsbeg;
  }

  public void setZinsbeg(String zinsbeg) {
    this.zinsbeg = zinsbeg;
  }

  public String getZinsdat() {
    return zinsdat;
  }

  public void setZinsdat(String zinsdat) {
    this.zinsdat = zinsdat;
  }

  public String getZinsryth() {
    return zinsryth;
  }

  public void setZinsryth(String zinsryth) {
    this.zinsryth = zinsryth;
  }

  public String getZinstyp() {
    return zinstyp;
  }

  public void setZinstyp(String zinstyp) {
    this.zinstyp = zinstyp;
  }
}
