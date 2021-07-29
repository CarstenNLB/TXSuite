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

public class Sicherheiten {

  private String sicherheitenNummer;
  private String org;
  private String quelle;
  private String whrg;
  private String zuwbetrag;
  private String art;
  private String nbetrag;
  private String pbsatz;
  private String vbetrag;
  private String SicherheitenAnteilNennbetrag;
  private String ivKundennummer;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor - initialisiert die Variablen mit leeren Strings
   * @param pvLogger
   */
  public Sicherheiten(Logger pvLogger)
  {
    ivLogger = pvLogger;

    sicherheitenNummer = new String();
    org = new String();
    quelle = new String();;
    whrg = new String();
    zuwbetrag = new String();
    art = new String();
    nbetrag = new String();
    pbsatz = new String();
    vbetrag = new String();
    SicherheitenAnteilNennbetrag = new String();
    ivKundennummer = new String();
  }

  /**
   * Einlesen der Sicherheiten-Daten
   * @param pvDateiname Dateiname der Sicherheiten-Daten
   * @param pvListeSicherheiten Liste der Sicherheiten-Daten
   * @param pvLogger log4j-Logger
   * @return Anzahl der eingelesenen Sicherheiten-Daten
   */
  public static int readSicherheiten(String pvDateiname, HashMap<String, Sicherheiten> pvListeSicherheiten, Logger pvLogger)
  {
    String lvZeile = null;
    int lvZaehlerSicherheiten = 0;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDH = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDH);
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte Sicherheiten-Datei nicht oeffnen!");
      return lvZaehlerSicherheiten;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Deckung-Datei ein
      {
        Sicherheiten lvSicherheit = new Sicherheiten(pvLogger);
        if (lvSicherheit.parseSicherheit(lvZeile)) // Parsen der Felder
        {
          lvZaehlerSicherheiten++;
          pvListeSicherheiten.put(lvSicherheit.getSicherheitenNummer(), lvSicherheit);
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
      pvLogger.error("Konnte Sicherheiten-Datei nicht schliessen!");
    }

    return lvZaehlerSicherheiten;
  }

  /**
   * Zerlegt eine Sicherheitenzeile in die einzelnen Felder/Werte
   * @param pvZeile die zu zerlegende Zeile
   * @return immer 'true'
   */
  private boolean parseSicherheit(String pvZeile)
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
        this.setSicherheit(lvLfd, lvTemp.trim());

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
    this.setSicherheit(lvLfd, lvTemp.trim());

    return true;
  }

  /**
   * Setzt einen Wert der Tabelle 'Sicherheiten'
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setSicherheit(int pvPos, String pvWert)
  {
    switch (pvPos)
    {
      case 0:
        this.sicherheitenNummer = pvWert;
        break;
      case 1:
        this.org = pvWert;
        break;
      case 2:
        this.quelle = pvWert;
        break;
      case 3:
        this.whrg = pvWert;
        break;
      case 4:
        this.nbetrag = pvWert.replace(",", ".");
        break;
      case 5:
        this.art = pvWert;
        break;
      case 6:
        this.pbsatz = pvWert;
        break;
      case 7:
        this.vbetrag = pvWert.replace(",", ".");
        break;
      case 8:
        this.ivKundennummer = pvWert;
        break;
        default:
        ivLogger.error("Sicherheit: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
    }
  }

  public String getSicherheitenNummer() {
    return sicherheitenNummer;
  }

  public void setSicherheitenNummer(String sicherheitenNummer) {
    this.sicherheitenNummer = sicherheitenNummer;
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

  public String getWhrg() {
    return whrg;
  }

  public void setWhrg(String whrg) {
    this.whrg = whrg;
  }

  public String getZuwbetrag() {
    return zuwbetrag;
  }

  public void setZuwbetrag(String zuwbetrag) {
    this.zuwbetrag = zuwbetrag;
  }

  public String getArt() {
    return art;
  }

  public void setArt(String art) {
    this.art = art;
  }

  public String getNbetrag() {
    return nbetrag;
  }

  public void setNbetrag(String nbetrag) {
    this.nbetrag = nbetrag;
  }

  public String getPbsatz() {
    return pbsatz;
  }

  public void setPbsatz(String pbsatz) {
    this.pbsatz = pbsatz;
  }

  public String getVbetrag() {
    return vbetrag;
  }

  public void setVbetrag(String vbetrag) {
    this.vbetrag = vbetrag;
  }

  public String getSicherheitenAnteilNennbetrag() {
    return SicherheitenAnteilNennbetrag;
  }

  public void setSicherheitenAnteilNennbetrag(String pvSicherheitenAnteilNennbetrag) {
    SicherheitenAnteilNennbetrag = pvSicherheitenAnteilNennbetrag;
  }

  public String getKundennummer()
  {
    return ivKundennummer;
  }

  public void setKundennummer(String pvKundennummer)
  {
    this.ivKundennummer = pvKundennummer;
  }
}
