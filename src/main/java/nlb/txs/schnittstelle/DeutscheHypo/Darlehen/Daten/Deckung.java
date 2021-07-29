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

public class Deckung {

  private String aktivpassiv;
  private String bis;
  private String decktyp;
  private String key;
  private String nbetrag;
  private String nliqui;
  private String pool;
  private String prj;
  private String tx;
  private String von;
  private String whrg;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor - Initialisierung mit leeren Strings
   * @param pvLogger log4j-Logger
   */
  public Deckung(Logger pvLogger)
  {
    ivLogger = pvLogger;
    aktivpassiv = new String();
    bis = new String();
    decktyp = new String();
    key = new String();
    nbetrag = new String();
    nliqui = new String();
    pool = new String();
    prj = new String();
    tx = new String();
    von = new String();
    whrg = new String();
  }

  /**
   * Einlesen der Deckungsdaten
   * @param pvDateiname Dateiname der Deckungsdaten
   * @param pvListeDeckung Liste der Deckungsdaten
   * @param pvLogger log4j-Logger
   * @return Anzahl der eingelesenen Deckungsdaten
   */
  public static int readDeckung(String pvDateiname, HashMap<String, Deckung> pvListeDeckung, Logger pvLogger)
  {
    String lvZeile = null;
    int lvZaehlerDeckung = 0;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDeckung = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDeckung);
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte Deckung-Datei nicht oeffnen!");
      return lvZaehlerDeckung;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Deckung-Datei ein
      {
        Deckung lvDeckung = new Deckung(pvLogger);
        if (lvDeckung.parseDeckung(lvZeile)) // Parsen der Felder
        {
          lvZaehlerDeckung++;
          pvListeDeckung.put(lvDeckung.getKey(), lvDeckung);
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
      pvLogger.error("Konnte Deckung-Datei nicht schliessen!");
    }

    return lvZaehlerDeckung;
  }

  /**
   * Zerlegt eine Deckungszeile in die einzelnen Felder/Werte
   * @param pvZeile die zu zerlegende Zeile
   * @return Immer true
   */
  private boolean parseDeckung(String pvZeile)
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
        this.setDeckung(lvLfd, lvTemp.trim());

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
    this.setDeckung(lvLfd, lvTemp.trim());

    return true;
  }

  /**
   * Setzt einen Wert der Tabelle 'Deckung'
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setDeckung(int pvPos, String pvWert)
  {
    switch (pvPos)
    {
      case 0:
        this.aktivpassiv = pvWert;
        break;
      case 1:
        this.bis = pvWert;
        break;
      case 2:
        this.decktyp = pvWert;
        break;
      case 3:
        this.key = pvWert;
        break;
      case 4:
        this.nbetrag = pvWert;
        break;
      case 5:
        this.nliqui = pvWert;
        break;
      case 6:
        this.pool = pvWert;
        break;
      case 7:
        this.prj = pvWert;
        break;
      case 8:
        this.tx = pvWert;
        break;
      case 9:
        this.von = pvWert;
        break;
      case 10:
        this.whrg = pvWert;
        break;
      default:
        ivLogger.error("Deckung: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
    }
  }

  /**
   * Liefert die Deckung als String
   * @return
   */
  public String toString()
  {
    StringBuilder lvHelpString = new StringBuilder();
    // Nicht implementiert...

    return lvHelpString.toString();
  }

  /**
   * Liefert das Kennzeichen 'Aktivpassiv'
   * @return Kennzeichen 'Aktivpassiv'
   */
  public String getAktivpassiv() {
    return aktivpassiv;
  }

  /**
   * Setzt das Kennzeichen 'Aktivpassiv'
   * @param aktivpassiv Kennzeichen 'Aktivpassiv'
   */
  public void setAktivpassiv(String aktivpassiv) {
    this.aktivpassiv = aktivpassiv;
  }

  /**
   * Liefert den Bis-Betrag
   * @return Bis-Betrag
   */
  public String getBis() {
    return bis;
  }

  /**
   * Setzt den Bis-Betrag
   * @param bis Bis-Betrag
   */
  public void setBis(String bis) {
    this.bis = bis;
  }

  /**
   * Liefert den Deckungstyp
   * @return Deckungstyp
   */
  public String getDecktyp() {
    return decktyp;
  }

  /**
   * Setzt den Deckungstyp
   * @param decktyp Deckungstyp
   */
  public void setDecktyp(String decktyp) {
    this.decktyp = decktyp;
  }

  /**
   * Liefert den Key/ID
   * @return Key/ID
   */
  public String getKey() {
    return key;
  }

  /**
   * Setzt den Key/ID
   * @param key Key/ID
   */
  public void setKey(String key) {
    this.key = key;
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
   * Liefert das Kennzeichen 'Nliqui'
   * @return Kennzeichen 'Nliqui'
   */
  public String getNliqui() {
    return nliqui;
  }

  /**
   * Setzt das Kennzeichen 'Nliqui'
   * @param nliqui Kennzeichen 'Nliqui'
   */
  public void setNliqui(String nliqui) {
    this.nliqui = nliqui;
  }

  /**
   * Liefert den Pool
   * @return Pool
   */
  public String getPool() {
    return pool;
  }

  /**
   * Setzt den Pool
   * @param pool Pool
   */
  public void setPool(String pool) {
    this.pool = pool;
  }

  /**
   * Liefert das Projekt
   * @return Projekt
   */
  public String getPrj() {
    return prj;
  }

  /**
   * Setzt das Projekt
   * @param prj Projekt
   */
  public void setPrj(String prj) {
    this.prj = prj;
  }

  /**
   * Liefert die Transaction
   * @return Transaction
   */
  public String getTx() {
    return tx;
  }

  /**
   * Setzt die Transaction
   * @param tx Transaction
   */
  public void setTx(String tx) {
    this.tx = tx;
  }

  /**
   * Liefert den Von-Betrag
   * @return Von-Betrag
   */
  public String getVon() {
    return von;
  }

  /**
   * Setzt den Von-Betrag
   * @param von Von-Betrag
   */
  public void setVon(String von) {
    this.von = von;
  }

  /**
   * Liefert die Waehrung
   * @return Waehrung
   */
  public String getWhrg() {
    return whrg;
  }

  /**
   * Setzt die Waehrung
   * @param whrg Waehrung
   */
  public void setWhrg(String whrg) {
    this.whrg = whrg;
  }

}
