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

public class Kunde {

  private String kundennummer;
  private String org;
  private String quelle;
  private String ADR_AVNM;
  private String nname;
  private String str;
  private String hausnummer;
  private String plz;
  private String ort;
  private String text;
  private String land;
  private String kusyma;
  private String kugr;
  private String gebiet;
  private String rating_id;
  private String rating_master;
  private String rating_datum;
  private String rating_tool_id;
  private String rating_tool;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor - initialisiert die Varaiblen mit leeren Strings
   * @param pvLogger log4j-Logger
   */
  public Kunde(Logger pvLogger)
  {
    ivLogger = pvLogger;
    kundennummer = new String();
    org = new String();
    quelle = new String();
    ADR_AVNM = new String();
    nname = new String();
    str = new String();
    hausnummer = new String();
    plz = new String();
    ort = new String();
    text = new String();
    land = new String();
    kusyma = new String();
    kugr = new String();
    gebiet = new String();
    rating_id = new String();
    rating_master = new String();
    rating_datum = new String();
    rating_tool_id = new String();
    rating_tool = new String();
  }

  /**
   * Einlesen der Kunden-Daten
   * @param pvDateiname Dateiname der Kunden-Daten
   * @param pvListeKunden Liste der Kunden-Daten
   * @param pvLogger log4j-Logger
   * @return Anzahl der eingelesenen Kunden-Daten
   */
  public static int readKunden(String pvDateiname, HashMap<String, Kunde> pvListeKunden, Logger pvLogger)
  {
    String lvZeile = null;
    int lvZaehlerKunden = 0;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDH = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDH);
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte Kunden-Datei nicht oeffnen!");
      return lvZaehlerKunden;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Deckung-Datei ein
      {
        Kunde lvKunde = new Kunde(pvLogger);
        if (lvKunde.parseKunde(lvZeile)) // Parsen der Felder
        {
          lvZaehlerKunden++;
          pvListeKunden.put(lvKunde.getKundennummer(), lvKunde);
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
      pvLogger.error("Konnte Kunden-Datei nicht schliessen!");
    }

    return lvZaehlerKunden;
  }

  /**
   * Zerlegt eine Kundenzeile in die einzelnen Felder/Werte
   * @param pvZeile
   * @return
   */
  public boolean parseKunde(String pvZeile)
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
        this.setKunde(lvLfd, lvTemp.trim());

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
    this.setKunde(lvLfd, lvTemp.trim());

    return true;
  }

  /**
   * Setzt einen Wert der Tabelle 'Kunde'
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setKunde(int pvPos, String pvWert)
  {
    switch (pvPos)
    {
      case 0:
        this.kundennummer = pvWert;
        break;
      case 1:
        this.org = pvWert;
        break;
      case 2:
        this.quelle = pvWert;
        break;
      case 3:
        this.ADR_AVNM = pvWert;
        break;
      case 4:
        this.nname = pvWert;
        break;
      case 5:
        this.str = pvWert;
        break;
      case 6:
        this.hausnummer = pvWert;
        break;
      case 7:
        this.plz = pvWert;
        break;
      case 8:
        this.ort = pvWert;
        break;
      case 9:
        this.text = pvWert;
        break;
      case 10:
        this.land = pvWert;
        break;
      case 11:
        this.kusyma = pvWert;
        break;
      case 12:
        this.kugr = pvWert;
        break;
      case 13:
        this.gebiet = pvWert;
        break;
      case 14:
        this.rating_id = pvWert;
        break;
      case 15:
        this.rating_master = pvWert;
        break;
      case 16:
        this.rating_datum = pvWert;
        break;
      case 17:
        this.rating_tool_id = pvWert;
        break;
      case 18:
        this.rating_tool = pvWert;
        break;
      default:
        ivLogger.error("Kunde: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
    }
  }

  public String getKundennummer() {
    return kundennummer;
  }

  public void setKundennummer(String kundennummer) {
    this.kundennummer = kundennummer;
  }

  public String getOrg() {
    return org;
  }

  public void setOrg(String org) {
    this.org = org;
  }

  public String getQuelle() {
    return quelle;
  }

  public void setQuelle(String quelle) {
    this.quelle = quelle;
  }

  public String getADR_AVNM() {
    return ADR_AVNM;
  }

  public void setADR_AVNM(String ADR_AVNM) {
    this.ADR_AVNM = ADR_AVNM;
  }

  public String getNname() {
    return nname;
  }

  public void setNname(String nname) {
    this.nname = nname;
  }

  public String getStr() {
    return str;
  }

  public void setStr(String str) {
    this.str = str;
  }

  public String getHausnummer() {
    return hausnummer;
  }

  public void setHausnummer(String hausnummer) {
    this.hausnummer = hausnummer;
  }

  public String getPlz() {
    return plz;
  }

  public void setPlz(String plz) {
    this.plz = plz;
  }

  public String getOrt() {
    return ort;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getLand() {
    return land;
  }

  public void setLand(String land) {
    this.land = land;
  }

  public String getKusyma() {
    return kusyma;
  }

  public void setKusyma(String kusyma) {
    this.kusyma = kusyma;
  }

  public String getKugr() {
    return kugr;
  }

  public void setKugr(String kugr) {
    this.kugr = kugr;
  }

  public String getGebiet() {
    return gebiet;
  }

  public void setGebiet(String gebiet) {
    this.gebiet = gebiet;
  }

  public String getRating_id() {
    return rating_id;
  }

  public void setRating_id(String rating_id) {
    this.rating_id = rating_id;
  }

  public String getRating_master() {
    return rating_master;
  }

  public void setRating_master(String rating_master) {
    this.rating_master = rating_master;
  }

  public String getRating_datum() {
    return rating_datum;
  }

  public void setRating_datum(String rating_datum) {
    this.rating_datum = rating_datum;
  }

  public String getRating_tool_id() {
    return rating_tool_id;
  }

  public void setRating_tool_id(String rating_tool_id) {
    this.rating_tool_id = rating_tool_id;
  }

  public String getRating_tool() {
    return rating_tool;
  }

  public void setRating_tool(String rating_tool) {
    this.rating_tool = rating_tool;
  }
}
