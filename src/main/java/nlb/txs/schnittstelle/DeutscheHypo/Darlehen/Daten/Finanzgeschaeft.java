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

public class Finanzgeschaeft {

  private String kontonummer;
  private String kundennummer;
  private String org;
  private String quelle;
  private String abetrag;
  private String aktivpassiv;
  private String ausstat;
  private String auszdat;
  private String averpfl;
  private String az;
  private String kat;
  private String konsfuehrer;
  private String konskredit;
  private String nbetrag;
  private String pfbrfrel;
  private String repo;
  private String rkapi;
  private String typ;
  private String vwhrg;
  private String whrg;
  private String zusdat;
  private String deckschl;
  private String prodschl;
  private String roll;
  private String deckungsschluessel;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;
  /**
   *
   */
  public Finanzgeschaeft(Logger pvLogger)
  {
    ivLogger = pvLogger;
    kontonummer = new String();
    kundennummer = new String();
    org = new String();
    quelle = new String();
    abetrag = new String();
    aktivpassiv = new String();
    ausstat = new String();
    auszdat = new String();
    averpfl = new String();
    az = new String();
    kat = new String();
    konsfuehrer = new String();
    konskredit = new String();
    nbetrag = new String();
    pfbrfrel = new String();
    repo = new String();
    rkapi = new String();
    typ = new String();
    vwhrg = new String();
    whrg = new String();
    zusdat = new String();
    deckschl = new String();
    prodschl = new String();
    roll = new String();
    deckungsschluessel = new String();
  }

  /**
   * Einlesen der Finanzgeschaefte-Daten
   * @param pvDateiname Dateiname der Finanzgeschaefte-Daten
   * @param pvListeFinanzgeschaefte Liste der Finanzgeschaefte-Daten
   * @param pvLogger log4j-Logger
   * @return Anzahl der eingelesenen Finanzgeschaefte-Daten
   */
  public static int readFinanzgeschaefte(String pvDateiname, HashMap<String, Finanzgeschaeft> pvListeFinanzgeschaefte, Logger pvLogger)
  {
    String lvZeile = null;
    int lvZaehlerFinanzgeschaefte = 0;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDH = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDH);
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte Finanzgeschaefte-Datei nicht oeffnen!");
      return lvZaehlerFinanzgeschaefte;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Finanzgeschaefte-Datei ein
      {
        Finanzgeschaeft lvFinanzgeschaeft = new Finanzgeschaeft(pvLogger);
        if (lvFinanzgeschaeft.parseFinanzgeschaeft(lvZeile)) // Parsen der Felder
        {
          lvZaehlerFinanzgeschaefte++;
          pvListeFinanzgeschaefte.put(lvFinanzgeschaeft.getKontonummer(), lvFinanzgeschaeft);
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
      pvLogger.error("Konnte Finanzgeschaefte-Datei nicht schliessen!");
    }

    return lvZaehlerFinanzgeschaefte;
  }

  /**
   * Zerlegt eine Finanzgeschaeftzeile in die einzelnen Felder/Werte
   * @param pvZeile die zu zerlegende Zeile
   * @return Immer 'true'
   */
  private boolean parseFinanzgeschaeft(String pvZeile)
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
         this.setFinanzgeschaeft(lvLfd, lvTemp.trim());

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
    this.setFinanzgeschaeft(lvLfd, lvTemp.trim());

    return true;
  }

  /**
   * Setzt einen Wert der Tabelle 'Finanzgeschaeft'
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setFinanzgeschaeft(int pvPos, String pvWert)
  {
    switch (pvPos)
    {
      case 0:
        this.kontonummer = pvWert;
        break;
      case 1:
        this.kundennummer = pvWert;
        break;
      case 2:
        this.org = pvWert;
        break;
      case 3:
        this.quelle = pvWert;
        break;
      case 4:
        this.abetrag = pvWert.replace(",", ".");
        break;
      case 5:
        this.aktivpassiv = pvWert;
        break;
      case 6:
        this.ausstat = pvWert;
        break;
      case 7:
        this.auszdat = pvWert;
        break;
      case 8:
        this.averpfl = pvWert.replace(",", ".");
        break;
      case 9:
        this.az = pvWert;
        break;
      case 10:
        this.kat = pvWert;
        break;
      case 11:
        this.konsfuehrer = pvWert;
        break;
      case 12:
        this.konskredit = pvWert;
        break;
      case 13:
        this.nbetrag = pvWert.replace(",", ".");
        break;
      case 14:
        this.pfbrfrel = pvWert;
        break;
      case 15:
        this.repo = pvWert;
        break;
      case 16:
        this.rkapi = pvWert.replace(",", ".");
        break;
      case 17:
        this.typ = pvWert;
        break;
      case 18:
        this.vwhrg = pvWert;
        break;
      case 19:
        this.whrg = pvWert;
        break;
      case 20:
        this.zusdat = pvWert;
        break;
      case 21:
        this.deckschl = pvWert;
        break;
      case 22:
        this.prodschl = pvWert;
        break;
      case 23:
        this.roll = pvWert;
        break;
      case 24:
        this.deckungsschluessel = pvWert;
        break;
      default:
        ivLogger.error("Finanzgeschaeft: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
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
   * Liefert die Kontonummer
   * @return Kontonummer
   */
  public String getKontonummer() {
    return kontonummer;
  }

  /**
   * Setzt die Kontonummer
   * @param pvKontonummer Kontonummer
   */
  public void setKontonummer(String pvKontonummer) {
    this.kontonummer = pvKontonummer;
  }

  /**
   * Liefert die Kundennummer
   * @return Kundennummer
   */
  public String getKundennummer() {
    return kundennummer;
  }

  /**
   * Setzt die Kundennummer
   * @param pvKundennummer Kundennummer
   */
  public void setKundennummer(String pvKundennummer) {
    this.kundennummer = pvKundennummer;
  }

  /**
   * Liefert den Originator
   * @return Originator
   */
  public String getOrg() {
    return org;
  }

  /**
   * Setzt den Originator
   * @param org Originator
   */
  public void setOrg(String org) {
    this.org = org;
  }

  /**
   * Liefert die Quelle
   * @return Quelle
   */
  public String getQuelle() {
    return quelle;
  }

  /**
   * Setzt die Quelle
   * @param quelle Quelle
   */
  public void setQuelle(String quelle) {
    this.quelle = quelle;
  }

  /**
   * Liefert den Auszahlungsbetrag
   * @return Auszahlungsbetrag
   */
  public String getAbetrag() {
    return abetrag;
  }

  /**
   * Setzt den Auszahlungsbetrag
   * @param abetrag
   */
  public void setAbetrag(String abetrag) {
    this.abetrag = abetrag;
  }

  /**
   * Liefert das Kennzeichen 'AktivPassiv'
   * @return Kennzeichen 'AktivPassiv'
   */
  public String getAktivpassiv() {
    return aktivpassiv;
  }

  /**
   * Setzt das Kennzeichen 'AktivPassiv'
   * @param aktivpassiv Kennzeichen 'AktivPassiv'
   */
  public void setAktivpassiv(String aktivpassiv) {
    this.aktivpassiv = aktivpassiv;
  }

  /**
   * Liefert die Ausstatung
   * @return Ausstatung
   */
  public String getAusstat() {
    return ausstat;
  }

  /**
   * Setzt die Ausstatung
   * @param ausstat Ausstatung
   */
  public void setAusstat(String ausstat) {
    this.ausstat = ausstat;
  }

  /**
   * Liefert das Auszahlungsdatum
   * @return Auszahlungsdatum
   */
  public String getAuszdat() {
    return auszdat;
  }

  /**
   * Setzt das Auszahlungsdatum
   * @param auszdat Auszahlungsdatum
   */
  public void setAuszdat(String auszdat) {
    this.auszdat = auszdat;
  }

  public String getAverpfl() {
    return averpfl;
  }

  public void setAverpfl(String averpfl) {
    this.averpfl = averpfl;
  }

  /**
   * Liefert das Aktenzeichen
   * @return Aktenzeichen
   */
  public String getAz() {
    return az;
  }

  /**
   * Setzt das Aktenzeichen
   * @param az Aktenzeichen
   */
  public void setAz(String az) {
    this.az = az;
  }

  /**
   * Liefert die Kategorie
   * @return Kategorie
   */
  public String getKat() {
    return kat;
  }

  /**
   * Setzt die Kategorie
   * @param kat Kategorie
   */
  public void setKat(String kat) {
    this.kat = kat;
  }

  /**
   * Liefert den Konsortialfuehrer
   * @return Konsortialfuehrer
   */
  public String getKonsfuehrer() {
    return konsfuehrer;
  }

  /**
   * Setzt den Konsortialfuehrer
   * @param konsfuehrer Konsortialfuehrer
   */
  public void setKonsfuehrer(String konsfuehrer) {
    this.konsfuehrer = konsfuehrer;
  }

  public String getKonskredit() {
    return konskredit;
  }

  public void setKonskredit(String konskredit) {
    this.konskredit = konskredit;
  }

  public String getNbetrag() {
    return nbetrag;
  }

  public void setNbetrag(String nbetrag) {
    this.nbetrag = nbetrag;
  }

  public String getPfbrfrel() {
    return pfbrfrel;
  }

  public void setPfbrfrel(String pfbrfrel) {
    this.pfbrfrel = pfbrfrel;
  }

  public String getRepo() {
    return repo;
  }

  public void setRepo(String repo) {
    this.repo = repo;
  }

  public String getRkapi() {
    return rkapi;
  }

  public void setRkapi(String rkapi) {
    this.rkapi = rkapi;
  }

  public String getTyp() {
    return typ;
  }

  public void setTyp(String typ) {
    this.typ = typ;
  }

  public String getVwhrg() {
    return vwhrg;
  }

  public void setVwhrg(String vwhrg) {
    this.vwhrg = vwhrg;
  }

  public String getWhrg() {
    return whrg;
  }

  public void setWhrg(String whrg) {
    this.whrg = whrg;
  }

  public String getZusdat() {
    return zusdat;
  }

  public void setZusdat(String zusdat) {
    this.zusdat = zusdat;
  }

  public String getDeckschl() {
    return deckschl;
  }

  public void setDeckschl(String deckschl) {
    this.deckschl = deckschl;
  }

  /**
   *
   * @return
   */
  public String getProdschl() {
    return prodschl;
  }

  /**
   *
   * @param prodschl
   */
  public void setProdschl(String prodschl) {
    this.prodschl = prodschl;
  }

  /**
   *
   * @return
   */
  public String getRoll() {
    return roll;
  }

  /**
   *
   * @param roll
   */
  public void setRoll(String roll) {
    this.roll = roll;
  }

  /**
   * Liefert den Deckungsschluessel (11, 12, 13 oder 14)
   * @return Deckungsschluessel
   */
  public String getDeckungsschluessel()
  {
    return deckungsschluessel;
  }

  /**
   * Setzt den Deckungsschluessel
   * @param pvDeckungsschluessel Deckungsschluessel (11, 12, 13 oder 14)
   */
  public void setDeckungsschluessel(String pvDeckungsschluessel)
  {
    this.deckungsschluessel = pvDeckungsschluessel;
  }
}
