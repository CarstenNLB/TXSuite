/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Rueckmeldung.Sicherheiten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class RueckmeldungSicherheiten {
 // OriginatorEnum;Quelle;Aktenzeichen;ExternerSliceKey;FGAusplatzierungsmerkmal;SliceAusplatzierungsmerkmal;Assigned;Blacklist;PoolName;TransName;KRSHGueltigZum;KRSHStorno;KRSHBetrag;SiExternerShKey;SiZugeordneteAktenzeichen;SiHasStornierung;Verfuegungsbetrag;GBGueltigZum;GBStorno;VZGueltigZum;VZGeloeschtAm;PfandObjektNr;Pfandbriefrelevanz;GueltigZum;Beleihungswert;BeleihungswertDatum;IsInDeckung

  private String ivOriginatorEnum;
  private String ivQuelle;
  private String ivAktenzeichen;
  private String ivExternerSliceKey;
  private String ivFGAusplatzierungsmerkmal;
  private String ivSliceAusplatzierungsmerkmal;
  private String ivAssigned;
  private String ivBlacklist;
  private String ivPoolName;
  private String ivTransName;
  private String ivKRSHGueltigZum;
  private String ivKRSHStorno;
  private String ivKRSHBetrag;
  private String ivSiExternerShKey;
  private String ivSiZugeordneteAktenzeichen;
  private String ivSiHasStornierung;
  private String ivVerfuegungsbetrag;
  private String ivGBGueltigZum;
  private String ivGBStorno;
  private String ivVZGueltigZum;
  private String ivVZGeloeschtAm;
  private String ivPfandObjektNr;
  private String ivPfandbriefrelevanz;
  private String ivGueltigZum;
  private String ivBeleihungswert;
  private String ivBeleihungswertDatum;
  private String ivIsInDeckung;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor - Initialisierung mit leeren Strings
   */
  public RueckmeldungSicherheiten(Logger pvLogger)
  {
    this.ivLogger = pvLogger;
    this.ivOriginatorEnum = new String();
    this.ivQuelle = new String();
    this.ivAktenzeichen = new String();
    this.ivExternerSliceKey = new String();
    this.ivFGAusplatzierungsmerkmal = new String();
    this.ivSliceAusplatzierungsmerkmal = new String();
    this.ivAssigned = new String();
    this.ivBlacklist = new String();
    this.ivPoolName = new String();
    this.ivTransName = new String();
    this.ivKRSHGueltigZum = new String();
    this.ivKRSHStorno = new String();
    this.ivKRSHBetrag = new String();
    this.ivSiExternerShKey = new String();
    this.ivSiZugeordneteAktenzeichen = new String();
    this.ivSiHasStornierung = new String();
    this.ivVerfuegungsbetrag = new String();
    this.ivGBGueltigZum = new String();
    this.ivGBStorno = new String();
    this.ivVZGueltigZum = new String();
    this.ivVZGeloeschtAm = new String();
    this.ivPfandObjektNr = new String();
    this.ivPfandbriefrelevanz = new String();
    this.ivGueltigZum = new String();
    this.ivBeleihungswert = new String();
    this.ivBeleihungswertDatum = new String();
    this.ivIsInDeckung = new String();
  }

  /**
   *
   * @return
   */
  public String getOriginatorEnum() {
    return ivOriginatorEnum;
  }

  /**
   *
   * @param pvOriginatorEnum
   */
  public void setOriginatorEnum(String pvOriginatorEnum) {
    this.ivOriginatorEnum = pvOriginatorEnum;
  }

  /**
   *
   * @return
   */
  public String getQuelle() {
    return ivQuelle;
  }

  /**
   *
   * @param pvQuelle
   */
  public void setQuelle(String pvQuelle) {
    this.ivQuelle = ivQuelle;
  }

  /**
   *
   * @return
   */
  public String getAktenzeichen() {
    return ivAktenzeichen;
  }

  /**
   *
   * @param pvAktenzeichen
   */
  public void setAktenzeichen(String pvAktenzeichen) {
    this.ivAktenzeichen = pvAktenzeichen;
  }

  /**
   *
   * @return
   */
  public String getExternerSliceKey() {
    return ivExternerSliceKey;
  }

  /**
   *
   * @param pvExternerSliceKey
   */
  public void setExternerSliceKey(String pvExternerSliceKey) {
    this.ivExternerSliceKey = pvExternerSliceKey;
  }

  /**
   *
   * @return
   */
  public String getFGAusplatzierungsmerkmal() {
    return ivFGAusplatzierungsmerkmal;
  }

  /**
   *
   * @param pvFGAusplatzierungsmerkmal
   */
  public void setFGAusplatzierungsmerkmal(String pvFGAusplatzierungsmerkmal) {
    this.ivFGAusplatzierungsmerkmal = pvFGAusplatzierungsmerkmal;
  }

  /**
   *
   * @return
   */
  public String getSliceAusplatzierungsmerkmal() {
    return ivSliceAusplatzierungsmerkmal;
  }

  /**
   *
   * @param pvSliceAusplatzierungsmerkmal
   */
  public void setSliceAusplatzierungsmerkmal(String pvSliceAusplatzierungsmerkmal) {
    this.ivSliceAusplatzierungsmerkmal = pvSliceAusplatzierungsmerkmal;
  }

  /**
   *
   * @return
   */
  public String getAssigned() {
    return ivAssigned;
  }

  /**
   *
   * @param pvAssigned
   */
  public void setAssigned(String pvAssigned) {
    this.ivAssigned = pvAssigned;
  }

  /**
   *
   * @return
   */
  public String getBlacklist() {
    return ivBlacklist;
  }

  /**
   *
   * @param pvBlacklist
   */
  public void setBlacklist(String pvBlacklist) {
    this.ivBlacklist = pvBlacklist;
  }

  /**
   *
   * @return
   */
  public String getPoolName() {
    return ivPoolName;
  }

  /**
   *
   * @param pvPoolName
   */
  public void setPoolName(String pvPoolName) {
    this.ivPoolName = pvPoolName;
  }

  /**
   *
   * @return
   */
  public String getTransName() {
    return ivTransName;
  }

  /**
   *
   * @param pvTransName
   */
  public void setTransName(String pvTransName) {
    this.ivTransName = pvTransName;
  }

  /**
   *
   * @return
   */
  public String getKRSHGueltigZum() {
    return ivKRSHGueltigZum;
  }

  /**
   *
   * @param pvKRSHGueltigZum
   */
  public void setKRSHGueltigZum(String pvKRSHGueltigZum) {
    this.ivKRSHGueltigZum = pvKRSHGueltigZum;
  }

  /**
   *
   * @return
   */
  public String getKRSHStorno() {
    return ivKRSHStorno;
  }

  /**
   *
   * @param pvKRSHStorno
   */
  public void setKRSHStorno(String pvKRSHStorno) {
    this.ivKRSHStorno = pvKRSHStorno;
  }

  /**
   *
   * @return
   */
  public String getKRSHBetrag() {
    return ivKRSHBetrag;
  }

  /**
   *
   * @param pvKRSHBetrag
   */
  public void setKRSHBetrag(String pvKRSHBetrag) {
    this.ivKRSHBetrag = pvKRSHBetrag;
  }

  /**
   *
   * @return
   */
  public String getSiExternerShKey() {
    return ivSiExternerShKey;
  }

  /**
   *
   * @param pvSiExternerShKey
   */
  public void setSiExternerShKey(String pvSiExternerShKey) {
    this.ivSiExternerShKey = pvSiExternerShKey;
  }

  /**
   *
   * @return
   */
  public String getSiZugeordneteAktenzeichen() {
    return ivSiZugeordneteAktenzeichen;
  }

  /**
   *
   * @param pvSiZugeordneteAktenzeichen
   */
  public void setSiZugeordneteAktenzeichen(String pvSiZugeordneteAktenzeichen) {
    this.ivSiZugeordneteAktenzeichen = pvSiZugeordneteAktenzeichen;
  }

  /**
   *
   * @return
   */
  public String getSiHasStornierung() {
    return ivSiHasStornierung;
  }

  /**
   *
   * @param pvSiHasStornierung
   */
  public void setSiHasStornierung(String pvSiHasStornierung) {
    this.ivSiHasStornierung = pvSiHasStornierung;
  }

  /**
   *
   * @return
   */
  public String getVerfuegungsbetrag() {
    return ivVerfuegungsbetrag;
  }

  /**
   *
   * @param pvVerfuegungsbetrag
   */
  public void setVerfuegungsbetrag(String pvVerfuegungsbetrag) {
    this.ivVerfuegungsbetrag = pvVerfuegungsbetrag;
  }

  /**
   *
   * @return
   */
  public String getGBGueltigZum() {
    return ivGBGueltigZum;
  }

  /**
   *
   * @param pvGBGueltigZum
   */
  public void setGBGueltigZum(String pvGBGueltigZum) {
    this.ivGBGueltigZum = pvGBGueltigZum;
  }

  /**
   *
   * @return
   */
  public String getGBStorno() {
    return ivGBStorno;
  }

  /**
   *
   * @param pvGBStorno
   */
  public void setGBStorno(String pvGBStorno) {
    this.ivGBStorno = pvGBStorno;
  }

  /**
   *
   * @return
   */
  public String getVZGueltigZum() {
    return ivVZGueltigZum;
  }

  /**
   *
   * @param pvVZGueltigZum
   */
  public void setVZGueltigZum(String pvVZGueltigZum) {
    this.ivVZGueltigZum = pvVZGueltigZum;
  }

  /**
   *
   * @return
   */
  public String getVZGeloeschtAm() {
    return ivVZGeloeschtAm;
  }

  /**
   *
   * @param pvVZGeloeschtAm
   */
  public void setVZGeloeschtAm(String pvVZGeloeschtAm) {
    this.ivVZGeloeschtAm = pvVZGeloeschtAm;
  }

  /**
   *
   * @return
   */
  public String getPfandObjektNr() {
    return ivPfandObjektNr;
  }

  /**
   *
   * @param pvPfandObjektNr
   */
  public void setPfandObjektNr(String pvPfandObjektNr) {
    this.ivPfandObjektNr = pvPfandObjektNr;
  }

  /**
   *
   * @return
   */
  public String getPfandbriefrelevanz() {
    return ivPfandbriefrelevanz;
  }

  /**
   *
   * @param pvPfandbriefrelevanz
   */
  public void setPfandbriefrelevanz(String pvPfandbriefrelevanz) {
    this.ivPfandbriefrelevanz = pvPfandbriefrelevanz;
  }

  /**
   *
   * @return
   */
  public String getGueltigZum() {
    return ivGueltigZum;
  }

  /**
   *
   * @param pvGueltigZum
   */
  public void setGueltigZum(String pvGueltigZum) {
    this.ivGueltigZum = pvGueltigZum;
  }

  /**
   *
   * @return
   */
  public String getBeleihungswert() {
    return ivBeleihungswert;
  }

  /**
   *
   * @param pvBeleihungswert
   */
  public void setBeleihungswert(String pvBeleihungswert) {
    this.ivBeleihungswert = pvBeleihungswert;
  }

  /**
   *
   * @return
   */
  public String getBeleihungswertDatum() {
    return ivBeleihungswertDatum;
  }

  /**
   *
   * @param pvBeleihungswertDatum
   */
  public void setBeleihungswertDatum(String pvBeleihungswertDatum) {
    this.ivBeleihungswertDatum = pvBeleihungswertDatum;
  }

  /**
   *
   * @return
   */
  public String getIsInDeckung() {
    return ivIsInDeckung;
  }

  /**
   *
   * @param pvIsInDeckung
   */
  public void setIsInDeckung(String pvIsInDeckung) {
    this.ivIsInDeckung = pvIsInDeckung;
  }

  /**
   * Einlesen der RueckmeldungSicherheiten
   * @param pvDateiname Dateiname der RueckmeldungSicherheiten
   * @param pvListeDeckung Liste der RueckmeldungSicherheiten
   * @param pvLogger log4j-Logger
   * @return Anzahl der eingelesenen RueckmeldungSicherheiten
   */
  public static int readRueckmeldungSicherheiten(String pvDateiname, HashMap<String, RueckmeldungSicherheiten> pvListeRueckmeldungSicherheiten, Logger pvLogger)
  {
    String lvZeile = null;
    int lvZaehlerSicherheiten = 0;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDeckung = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDeckung);
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte RueckmeldungSicherheiten-Datei nicht oeffnen!");
      return lvZaehlerSicherheiten;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Deckung-Datei ein
      {
        RueckmeldungSicherheiten lvRueckmeldungSicherheiten = new RueckmeldungSicherheiten(pvLogger);
        if (lvRueckmeldungSicherheiten.parseRueckmeldungSicherheiten(lvZeile)) // Parsen der Felder
        {
          lvZaehlerSicherheiten++;
          pvListeRueckmeldungSicherheiten.put(lvRueckmeldungSicherheiten.getSiExternerShKey(), lvRueckmeldungSicherheiten);
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
      pvLogger.error("Konnte RueckmeldungSicherheiten-Datei nicht schliessen!");
    }

    return lvZaehlerSicherheiten;
  }

  /**
   * Zerlegt eine RueckmeldungSicherheiten-Zeile in die einzelnen Felder/Werte
   * @param pvZeile die zu zerlegende Zeile
   * @return Immer true
   */
  private boolean parseRueckmeldungSicherheiten(String pvZeile)
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
        this.setRueckmeldungSicherheiten(lvLfd, lvTemp.trim());

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
    this.setRueckmeldungSicherheiten(lvLfd, lvTemp.trim());

    return true;
  }

  /**
   * Setzt einen Wert der RueckmeldungSicherheiten
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setRueckmeldungSicherheiten(int pvPos, String pvWert)
  {
    switch (pvPos)
    {
      case 0:
        this.ivOriginatorEnum = pvWert;
        break;
      case 1:
        this.ivQuelle = pvWert;
        break;
      case 2:
        this.ivAktenzeichen = pvWert;
        break;
      case 3:
        this.ivExternerSliceKey = pvWert;
        break;
      case 4:
        this.ivFGAusplatzierungsmerkmal = pvWert;
        break;
      case 5:
        this.ivSliceAusplatzierungsmerkmal = pvWert;
        break;
      case 6:
        this.ivAssigned = pvWert;
        break;
      case 7:
        this.ivBlacklist = pvWert;
        break;
      case 8:
        this.ivPoolName = pvWert;
        break;
      case 9:
        this.ivTransName = pvWert;
        break;
      case 10:
        this.ivKRSHGueltigZum = pvWert;
        break;
      case 11:
        this.ivKRSHStorno = pvWert;
        break;
      case 12:
        this.ivKRSHBetrag = pvWert;
        break;
      case 13:
        this.ivSiExternerShKey = pvWert;
        break;
      case 14:
        this.ivSiZugeordneteAktenzeichen = pvWert;
        break;
      case 15:
        this.ivSiHasStornierung = pvWert;
        break;
      case 16:
        this.ivVerfuegungsbetrag = pvWert;
        break;
      case 17:
        this.ivGBGueltigZum = pvWert;
        break;
      case 18:
        this.ivGBStorno = pvWert;
        break;
      case 19:
        this.ivVZGueltigZum = pvWert;
        break;
      case 20:
        this.ivVZGeloeschtAm = pvWert;
        break;
      case 21:
        this.ivPfandObjektNr = pvWert;
        break;
      case 22:
        this.ivPfandbriefrelevanz = pvWert;
        break;
      case 23:
        this.ivGueltigZum = pvWert;
        break;
      case 24:
        this.ivBeleihungswert = pvWert;
        break;
      case 25:
        this.ivBeleihungswertDatum = pvWert;
        break;
      case 26:
        this.ivIsInDeckung = pvWert;
        break;
      default:
        ivLogger.error("RueckmeldungSicherheiten: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
    }
  }

  /**
   * Liefert die RueckmeldungSicherheiten als String
   * @return
   */
  public String toString()
  {
    StringBuilder lvHelpString = new StringBuilder();
    // Nicht implementiert...

    return lvHelpString.toString();
  }

}
