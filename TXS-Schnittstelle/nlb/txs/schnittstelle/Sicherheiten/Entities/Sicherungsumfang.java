/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten.Entities;

import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetragListe;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class Sicherungsumfang
{
  /**
   * GUID fuer Tabelle CMS_CAG_RBL (Sicherungsumfang)
   */
  private String ivId;

  /**
   * GUID fuer Tabelle CMS_CAG (Sicherheitenvereinbarung)
   */
  private String ivSicherheitenvereinbarungId;

  /**
   * GUID fuer Tabelle CMS_CAG_POR (???)
   */
  private String ivCAG_POR;

  /**
   * Kennzeichen des Sicherheitenvereinbarungsteils
   */
  private String ivKennzeichen;

  /**
   * Wert des Sicherheitenvereinbarungsteil
   */
  private String ivWert;

  /**
   * Gibt die Groesse des Sicherheitenvereinbarungsteils in SV an
   */
  private String ivGroesse;

  /**
   * Sperrkategorie fuer Sicherheitenvereinbarungsteils
   */
  private String ivSperrkategorie;

  /**
   * Einschraenkung fuer Teil
   */
  private String ivEinschraenkung;

  /**
   * SV-ID basierend auf einer anderen (Referenz-)SV
   */
  private String ivReferenzId;

  /**
   * Legt Rangverhaeltnis des SV-Teils fest
   */
  private String ivRang;

  /**
   * Kennzeichen fuer Geschaeftswertart
   */
  private String ivGeschaeftswertartKennzeichen;

  /**
   * Laufende Nummer der Akte an einem Lagerort
   */
  private String ivLaufendeNummerAkte;

  /**
   * Kreditsystem
   */
  private String ivKreditsystem;

  /**
   * GeschaeftswertID
   */
  private String ivGeschaeftswertId;

  /**
   * Identifikationsnummer des Geschaeftspartners
   */
  private String ivGeschaeftspartnerId;

  /**
   * Geschaeftspartnerfunktion
   */
  private String ivGeschaeftsparterfunktion;

  /**
   * Gueltigkeitsbeginn der Verknuepfung SV - GW
   */
  private String ivGueltigkeitsbeginn;

  /**
   * Gueltigkeitsende der Verknuepfung SV - GW
   */
  private String ivGueltigkeitsende;

  /**
   * Prioritaet der Verknuepfung SV - GW
   */
  private String ivPrioritaet;

  /**
   * Rangklasse der Verknuepfung SV - GW
   */
  private String ivRangklasse;

  /**
   * Kennzeichen fuer manuelle/automatische Links
   */
  private String ivLinksKennzeichen;

  /**
   * Gibt an, ob SV eine Uebergangssicherheit ist
   */
  private String ivUebergangssicherheit;

  /**
   * Zuweisungsbetrag
   */
  private String ivZuweisungsbetrag;

  /**
   * Zuweisungsbetragwaehrung
   */
  private String ivZuweisungsbetragWaehrung;

  /**
   * Relevant fuer Deckungsregister
   */
  private String ivDeckungsregisterRelevant;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor
   * @param pvLogger log4j-Logger
   */
  public Sicherungsumfang(Logger pvLogger)
  {
    this.ivLogger = pvLogger;
    this.ivId = new String();
    this.ivSicherheitenvereinbarungId = new String();
    this.ivCAG_POR = new String();
    this.ivKennzeichen = new String();
    this.ivWert = new String();
    this.ivGroesse = new String();
    this.ivSperrkategorie = new String();
    this.ivEinschraenkung = new String();
    this.ivReferenzId = new String();
    this.ivRang = new String();
    this.ivGeschaeftswertartKennzeichen = new String();
    this.ivLaufendeNummerAkte = new String();
    this.ivKreditsystem = new String();
    this.ivGeschaeftswertId = new String();
    this.ivGeschaeftspartnerId = new String();
    this.ivGeschaeftsparterfunktion = new String();
    this.ivGueltigkeitsbeginn = new String();
    this.ivGueltigkeitsende = new String();
    this.ivPrioritaet = new String();
    this.ivRangklasse = new String();
    this.ivLinksKennzeichen = new String();
    this.ivUebergangssicherheit = new String();
    this.ivZuweisungsbetrag = new String();
    this.ivZuweisungsbetragWaehrung = new String();
    this.ivDeckungsregisterRelevant = new String();
  }

  /**
   * Liefert die ID
   * @return the id
   */
  public String getId() {
    return this.ivId;
  }

  /**
   * Setzt die ID
   * @param pvId the id to set
   */
  public void setId(String pvId) {
    this.ivId = pvId;
  }

  /**
   * Liefert die ID der Sicherheitenvereinbarung
   * @return the sicherheitenvereinbarungId
   */
  public String getSicherheitenvereinbarungId() {
    return this.ivSicherheitenvereinbarungId;
  }

  /**
   * Setzt die ID der Sicherheitenvereinbarung
   * @param pvSicherheitenvereinbarungId the sicherheitenvereinbarungId to set
   */
  public void setSicherheitenvereinbarungId(String pvSicherheitenvereinbarungId) {
    this.ivSicherheitenvereinbarungId = pvSicherheitenvereinbarungId;
  }

  /**
   * Liefert CAG_POR
   * @return the cAG_POR
   */
  public String getCAG_POR() {
    return this.ivCAG_POR;
  }

  /**
   * Setzt CAG_POR
   * @param pvCAGPOR the cAG_POR to set
   */
  public void setCAG_POR(String pvCAGPOR) {
    this.ivCAG_POR = pvCAGPOR;
  }

  /**
   * Liefert das Kennzeichen
   * @return the kennzeichen
   */
  public String getKennzeichen() {
    return this.ivKennzeichen;
  }

  /**
   * Setzt das Kennzeichen
   * @param pvKennzeichen the kennzeichen to set
   */
  public void setKennzeichen(String pvKennzeichen) {
    this.ivKennzeichen = pvKennzeichen;
  }

  /**
   * Liefert den Wert
   * @return the wert
   */
  public String getWert() {
    return this.ivWert;
  }

  /**
   * Setzt den Wert
   * @param pvWert the wert to set
   */
  public void setWert(String pvWert) {
    this.ivWert = pvWert;
  }

  /**
   * Liefert die Groesse
   * @return the groesse
   */
  public String getGroesse() {
    return this.ivGroesse;
  }

  /**
   * Setzt die Groesse
   * @param pvGroesse the groesse to set
   */
  public void setGroesse(String pvGroesse) {
    this.ivGroesse = pvGroesse;
  }

  /**
   * Liefert die Sperrkategorie
   * @return the sperrkategorie
   */
  public String getSperrkategorie() {
    return this.ivSperrkategorie;
  }

  /**
   * Setzt die Sperrkategorie
   * @param pvSperrkategorie the sperrkategorie to set
   */
  public void setSperrkategorie(String pvSperrkategorie) {
    this.ivSperrkategorie = pvSperrkategorie;
  }

  /**
   * Liefert die Einschraenkung
   * @return the einschraenkung
   */
  public String getEinschraenkung() {
    return this.ivEinschraenkung;
  }

  /**
   * Setzt die Einschraenkung
   * @param pvEinschraenkung the einschraenkung to set
   */
  public void setEinschraenkung(String pvEinschraenkung) {
    this.ivEinschraenkung = pvEinschraenkung;
  }

  /**
   * Liefert die ReferenzID
   * @return the referenzId
   */
  public String getReferenzId() {
    return this.ivReferenzId;
  }

  /**
   * Setzt die ReferenzID
   * @param pvReferenzId the referenzId to set
   */
  public void setReferenzId(String pvReferenzId) {
    this.ivReferenzId = pvReferenzId;
  }

  /**
   * Liefert den Rang
   * @return the rang
   */
  public String getRang() {
    return this.ivRang;
  }

  /**
   * Setzt den Rang
   * @param pvRang the rang to set
   */
  public void setRang(String pvRang) {
    this.ivRang = pvRang;
  }

  /**
   * Liefert das Kennzeichen Geschaeftswertart
   * @return the geschaeftswertartKennzeichen
   */
  public String getGeschaeftswertartKennzeichen() {
    return this.ivGeschaeftswertartKennzeichen;
  }

  /**
   * Setzt das Kennzeichen Geschaeftswertart
   * @param pvGeschaeftswertartKennzeichen the geschaeftswertartKennzeichen to set
   */
  public void setGeschaeftswertartKennzeichen(String pvGeschaeftswertartKennzeichen) {
    this.ivGeschaeftswertartKennzeichen = pvGeschaeftswertartKennzeichen;
  }

  /**
   * Liefert die laufende Nummer der Akte
   * @return the laufendeNummerAkte
   */
  public String getLaufendeNummerAkte() {
    return this.ivLaufendeNummerAkte;
  }

  /**
   * Setzt die laufende Nummer der Akte
   * @param pvLaufendeNummerAkte the laufendeNummerAkte to set
   */
  public void setLaufendeNummerAkte(String pvLaufendeNummerAkte) {
    this.ivLaufendeNummerAkte = pvLaufendeNummerAkte;
  }

  /**
   * Liefert das Kreditsystem
   * @return the kreditsystem
   */
  public String getKreditsystem() {
    return this.ivKreditsystem;
  }

  /**
   * Setzt das Kreditsystem
   * @param pvKreditsystem the kreditsystem to set
   */
  public void setKreditsystem(String pvKreditsystem) {
    this.ivKreditsystem = pvKreditsystem;
  }

  /**
   * Liefert die ID des Geschaeftswerts
   * @return the geschaeftswertId
   */
  public String getGeschaeftswertId() {
    return this.ivGeschaeftswertId;
  }

  /**
   * Setzt die ID des Geschaeftswerts
   * @param pvGeschaeftswertId the geschaeftswertId to set
   */
  public void setGeschaeftswertId(String pvGeschaeftswertId) {
    this.ivGeschaeftswertId = pvGeschaeftswertId;
  }

  /**
   * Liefert die ID des Geschaeftspartners
   * @return the geschaeftspartnerId
   */
  public String getGeschaeftspartnerId() {
    return this.ivGeschaeftspartnerId;
  }

  /**
   * Setzt die ID des Geschaeftspartners
   * @param pvGeschaeftspartnerId the geschaeftspartnerId to set
   */
  public void setGeschaeftspartnerId(String pvGeschaeftspartnerId) {
    this.ivGeschaeftspartnerId = pvGeschaeftspartnerId;
  }

  /**
   * Liefert die Geschaeftspartnerfunktion
   * @return the geschaeftsparterfunktion
   */
  public String getGeschaeftsparterfunktion() {
    return this.ivGeschaeftsparterfunktion;
  }

  /**
   * Setzt die Geschaeftspartnerfunktion
   * @param pvGeschaeftsparterfunktion the geschaeftsparterfunktion to set
   */
  public void setGeschaeftsparterfunktion(String pvGeschaeftsparterfunktion) {
    this.ivGeschaeftsparterfunktion = pvGeschaeftsparterfunktion;
  }

  /**
   * Liefert den Gueltigkeitsbeginn
   * @return the gueltigkeitsbeginn
   */
  public String getGueltigkeitsbeginn() {
    return this.ivGueltigkeitsbeginn;
  }

  /**
   * Setzt den Gueltigkeitsbeginn
   * @param pvGueltigkeitsbeginn the gueltigkeitsbeginn to set
   */
  public void setGueltigkeitsbeginn(String pvGueltigkeitsbeginn) {
    this.ivGueltigkeitsbeginn = pvGueltigkeitsbeginn;
  }

  /**
   * Liefert das Gueltigkeitsende
   * @return the gueltigkeitsende
   */
  public String getGueltigkeitsende() {
    return this.ivGueltigkeitsende;
  }

  /**
   * Setzt das Gueltigkeitsende
   * @param pvGueltigkeitsende the gueltigkeitsende to set
   */
  public void setGueltigkeitsende(String pvGueltigkeitsende) {
    this.ivGueltigkeitsende = pvGueltigkeitsende;
  }

  /**
   * Liefert die Prioritaet
   * @return the prioritaet
   */
  public String getPrioritaet() {
    return this.ivPrioritaet;
  }

  /**
   * Setzt die Prioritaet
   * @param pvPrioritaet the prioritaet to set
   */
  public void setPrioritaet(String pvPrioritaet) {
    this.ivPrioritaet = pvPrioritaet;
  }

  /**
   * Liefert die Rangklasse
   * @return the rangklasse
   */
  public String getRangklasse() {
    return this.ivRangklasse;
  }

  /**
   * Setzt die Rangklasse
   * @param pvRangklasse the rangklasse to set
   */
  public void setRangklasse(String pvRangklasse) {
    this.ivRangklasse = pvRangklasse;
  }

  /**
   * Liefert das Kennzeichen Links
   * @return the linksKennzeichen
   */
  public String getLinksKennzeichen() {
    return this.ivLinksKennzeichen;
  }

  /**
   * Setzt das Kennzeichen Links
   * @param pvLinksKennzeichen the linksKennzeichen to set
   */
  public void setLinksKennzeichen(String pvLinksKennzeichen) {
    this.ivLinksKennzeichen = pvLinksKennzeichen;
  }

  /**
   * Liefert die Uebergangssicherheit
   * @return the uebergangssicherheit
   */
  public String getUebergangssicherheit() {
    return this.ivUebergangssicherheit;
  }

  /**
   * Setzt die Uebergangssicherheit
   * @param pvUebergangssicherheit the uebergangssicherheit to set
   */
  public void setUebergangssicherheit(String pvUebergangssicherheit) {
    this.ivUebergangssicherheit = pvUebergangssicherheit;
  }

  /**
   * Liefert den Zuweisungsbetrag
   * @return the zuweisungsbetrag
   */
  public String getZuweisungsbetrag() {
    return this.ivZuweisungsbetrag;
  }

  /**
   * Setzt den Zuweisungsbetrag
   * @param pvZuweisungsbetrag the zuweisungsbetrag to set
   */
  public void setZuweisungsbetrag(String pvZuweisungsbetrag) {
    this.ivZuweisungsbetrag = pvZuweisungsbetrag;
  }

  /**
   * Liefert die Waehrung des Zuweisungsbetrags
   * @return the zuweisungsbetragWaehrung
   */
  public String getZuweisungsbetragWaehrung() {
    return this.ivZuweisungsbetragWaehrung;
  }

  /**
   * Setzt die Waehrung des Zuweisungsbetrags
   * @param pvZuweisungsbetragWaehrung the zuweisungsbetragWaehrung to set
   */
  public void setZuweisungsbetragWaehrung(String pvZuweisungsbetragWaehrung) {
    this.ivZuweisungsbetragWaehrung = pvZuweisungsbetragWaehrung;
  }

  /**
   * Liefert das Kennzeichen, ob der Sicherungsumfang Deckungsregister relevant ist.
   * @return Kennzeichen DeckungregisterRelevant
   */
  public String getDeckungsregisterRelevant()
  {
    return this.ivDeckungsregisterRelevant;
  }

  /**
   * Setzt das Kennzeichen, ob der Sicherungsumfang Deckungsregister relevant ist.
   * @param pvDeckungsregisterRelevant Kennzeichen DeckungsregisterRelevant
   */
  public void setDeckungsregisterRelevant(String pvDeckungsregisterRelevant)
  {
    this.ivDeckungsregisterRelevant = pvDeckungsregisterRelevant;
  }

  /**
   * Zerlegt eine Zeile in die unterschiedlichen Daten/Felder
   * @param pvZeile die zu zerlegende Zeile
   * @param pvObjektZuweisungsbetragListe Liste der Zuweisungsbetraege fuer dieses Objekt
   * @param pvQuelle Quellsystem (SAP CMS oder VVS)
   */
  public void parseSicherungsumfang(String pvZeile, ObjektZuweisungsbetragListe pvObjektZuweisungsbetragListe, int pvQuelle)
  {
    String lvTemp = new String();  // arbeitsbereich/zwischenspeicher feld
    int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

    // steuerung/iteration eingabesatz
    for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
    {

      // wenn semikolon erkannt
      if (pvZeile.charAt(lvZzStr) == '\t')
      {
        this.setSicherungsumfangSPOT(lvLfd, lvTemp, pvObjektZuweisungsbetragListe);

        lvLfd++;                  // naechste Feldnummer

        // loeschen Zwischenbuffer
        lvTemp = new String();

      }
      else
      {
        // uebernehmen byte aus eingabesatzposition
        lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
      }
    } // ende for

    // Letzte Feld auch noch setzen
    this.setSicherungsumfangSPOT(lvLfd, lvTemp, pvObjektZuweisungsbetragListe);
  }

  /**
   * Setzt einen Wert des Sicherungsumfangs
   * @param pvPos Position
   * @param pvWert Wert
   * @param pvObjektZuweisungsbetragListe
   */
  private void setSicherungsumfang(int pvPos, String pvWert, ObjektZuweisungsbetragListe pvObjektZuweisungsbetragListe)
  {
    switch (pvPos)
    {
      case 0:
        // Satzart
        break;
      case 1:
        this.setId(pvWert);
        break;
      case 2:
        this.setSicherheitenvereinbarungId(pvWert);
        break;
      case 3:
        this.setCAG_POR(pvWert);
        break;
      case 4:
        this.setKennzeichen(pvWert);
        break;
      case 5:
        this.setWert(pvWert);
        break;
      case 6:
        this.setGroesse(pvWert.replace(",", ".").trim());
        break;
      case 7:
        this.setSperrkategorie(pvWert);
        break;
      case 8:
        this.setEinschraenkung(pvWert);
        break;
      case 9:
        this.setReferenzId(pvWert);
        break;
      case 10:
        this.setRang(pvWert);
        break;
      case 11:
        this.setGeschaeftswertartKennzeichen(pvWert);
        break;
      case 12:
        this.setLaufendeNummerAkte(pvWert);
        break;
      case 13:
        this.setKreditsystem(pvWert);
        break;
      case 14:
        this.setGeschaeftswertId(pvWert.substring(pvWert.lastIndexOf("-") + 1));
        break;
      case 15:
        this.setGeschaeftspartnerId(pvWert);
        break;
      case 16:
        this.setGeschaeftsparterfunktion(pvWert);
        break;
      case 17:
        this.setGueltigkeitsbeginn(pvWert);
        break;
      case 18:
        this.setGueltigkeitsende(pvWert);
        break;
      case 19:
        this.setPrioritaet(pvWert);
        break;
      case 20:
        this.setRangklasse(pvWert);
        break;
      case 21:
        this.setLinksKennzeichen(pvWert);
        break;
      case 22:
        this.setUebergangssicherheit(pvWert);
        break;
      case 23:
        pvWert = pvWert.replace(".", "");
        pvWert = pvWert.replace(",", ".");
        this.setZuweisungsbetrag(pvWert.trim());
        // Zuweisungsbetrag in die Liste eintragen
        //System.out.println("Sicherheiten-ID: " + this.getSicherheitenvereinbarungId() + " Zuweisungsbetrag: " +  this.getZuweisungsbetrag());
        if (pvObjektZuweisungsbetragListe != null)
        {
          pvObjektZuweisungsbetragListe.addObjektZuweisungsbetrag(this.getSicherheitenvereinbarungId(), this.getZuweisungsbetrag());
        }
        if (this.getId().equals("001A4A14F4881ED992FF56E1200442FC"))
        {
          this.setZuweisungsbetrag("3940944.53");
        }
        if (this.getId().equals("001A4A14F4881ED992DE117A91DAF97D"))
        {
          this.setZuweisungsbetrag("7380000");
        }

        break;
      case 24:
        this.setZuweisungsbetragWaehrung(pvWert);
        if (this.getId().equals("001A4A14F4881ED992FF56E1200442FC"))
        {
          this.setZuweisungsbetragWaehrung("EUR");
        }
        if (this.getId().equals("001A4A14F4881ED992DE117A91DAF97D"))
        {
          this.setZuweisungsbetragWaehrung("EUR");
        }

        break;
      case 25:
        this.setDeckungsregisterRelevant(pvWert);
        break;
      default:
        ivLogger.info("Sicherungsumfang: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
    }
  }

  /**
   * Setzt einen Wert des Sicherungsumfangs
   * @param pvPos Position
   * @param pvWert Wert
   * @param pvObjektZuweisungsbetragListe
   */
  private void setSicherungsumfangSPOT(int pvPos, String pvWert, ObjektZuweisungsbetragListe pvObjektZuweisungsbetragListe)
  {
    switch (pvPos)
    {
      case 0:
        // Satzart
        break;
      case 1:
        this.setId(pvWert);
        break;
      case 2:
        this.setSicherheitenvereinbarungId(pvWert);
        break;
      case 3:
        this.setCAG_POR(pvWert);
        break;
      case 4:
        this.setKennzeichen(pvWert);
        break;
      case 5:
        this.setWert(pvWert);
        break;
      case 6:
        this.setGroesse(pvWert.trim());
        break;
      case 7:
        this.setSperrkategorie(pvWert);
        break;
      case 8:
        this.setEinschraenkung(pvWert);
        break;
      case 9:
        this.setReferenzId(pvWert);
        break;
      case 10:
        this.setRang(pvWert);
        break;
      case 11:
        this.setGeschaeftswertartKennzeichen(pvWert);
        break;
      case 12:
        this.setLaufendeNummerAkte(pvWert);
        break;
      case 13:
        this.setKreditsystem(pvWert);
        break;
      case 14:
        this.setGeschaeftswertId(pvWert.substring(pvWert.lastIndexOf("-") + 1));
        break;
      case 15:
        this.setGeschaeftspartnerId(pvWert);
        break;
      case 16:
        this.setGeschaeftsparterfunktion(pvWert);
        break;
      case 17:
        this.setGueltigkeitsbeginn(pvWert);
        break;
      case 18:
        this.setGueltigkeitsende(pvWert);
        break;
      case 19:
        this.setPrioritaet(pvWert);
        break;
      case 20:
        this.setRangklasse(pvWert);
        break;
      case 21:
        this.setLinksKennzeichen(pvWert);
        break;
      case 22:
        this.setUebergangssicherheit(pvWert);
        break;
      case 23:
        ////pvWert = pvWert.replace(".", "");
        //pvWert = pvWert.replace(",", ".");
        this.setZuweisungsbetrag(pvWert.trim());
        // Zuweisungsbetrag in die Liste eintragen
        if (pvObjektZuweisungsbetragListe != null)
        {
          //ivLogger.info("In: " + this.getSicherheitenvereinbarungId() + " Betrag: " + this.getZuweisungsbetrag());
          pvObjektZuweisungsbetragListe.addObjektZuweisungsbetrag(this.getSicherheitenvereinbarungId(), this.getZuweisungsbetrag());
        }
        if (this.getId().equals("001A4A14F4881ED992FF56E1200442FC"))
        {
          this.setZuweisungsbetrag("3940944.53");
        }
        if (this.getId().equals("001A4A14F4881ED992DE117A91DAF97D"))
        {
          this.setZuweisungsbetrag("7380000");
        }
        //System.out.println("Sicherheiten-ID: " + this.getSicherheitenvereinbarungId() + " Zuweisungsbetrag: " +  this.getZuweisungsbetrag());
        break;
      case 24:
        this.setZuweisungsbetragWaehrung(pvWert);
        break;
      case 25:
        this.setDeckungsregisterRelevant(pvWert);
        break;
      default:
        ivLogger.info("Sicherungsumfang: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
    }
  }


  /**
   * Schreibt den Inhalt eines Sicherungsumfang in einen String
   * @return
   */
  public String toString()
  {
    StringBuilder lvOut = new StringBuilder();

    lvOut.append("Id: " + ivId + StringKonverter.lineSeparator);
    lvOut.append("SicherheitenvereinbarungId: " + this.ivSicherheitenvereinbarungId + StringKonverter.lineSeparator);
    lvOut.append("CAG_POR: " + this.ivCAG_POR + StringKonverter.lineSeparator);
    lvOut.append("Kennzeichen: " + this.ivKennzeichen + StringKonverter.lineSeparator);
    lvOut.append("Wert: " + this.ivWert + StringKonverter.lineSeparator);
    lvOut.append("Groesse: " + this.ivGroesse + StringKonverter.lineSeparator);
    lvOut.append("Sperrkategorie: " + this.ivSperrkategorie + StringKonverter.lineSeparator);
    lvOut.append("Einschraenkung: " + this.ivEinschraenkung + StringKonverter.lineSeparator);
    lvOut.append("ReferenzId: " + this.ivReferenzId + StringKonverter.lineSeparator);
    lvOut.append("Rang: " + this.ivRang + StringKonverter.lineSeparator);
    lvOut.append("GeschaeftswertartKennzeichen: " + this.ivGeschaeftswertartKennzeichen + StringKonverter.lineSeparator);
    lvOut.append("LaufendeNummerAkte: " + this.ivLaufendeNummerAkte + StringKonverter.lineSeparator);
    lvOut.append("Kreditsystem: " + this.ivKreditsystem + StringKonverter.lineSeparator);
    lvOut.append("GeschaeftswertId: " + this.ivGeschaeftswertId + StringKonverter.lineSeparator);
    lvOut.append("GeschaeftspartnerId: " + this.ivGeschaeftspartnerId + StringKonverter.lineSeparator);
    lvOut.append("Geschaeftspartnerfunktion: " + this.ivGeschaeftsparterfunktion + StringKonverter.lineSeparator);
    lvOut.append("Gueltigkeitsbeginn: " + this.ivGueltigkeitsbeginn + StringKonverter.lineSeparator);
    lvOut.append("Gueltigkeitsende: " + this.ivGueltigkeitsende + StringKonverter.lineSeparator);
    lvOut.append("Prioritaet: " + this.ivPrioritaet + StringKonverter.lineSeparator);
    lvOut.append("Rangklasse: " + this.ivRangklasse + StringKonverter.lineSeparator);
    lvOut.append("LinksKennzeichen: " + this.ivLinksKennzeichen + StringKonverter.lineSeparator);
    lvOut.append("Uebergangssicherheit: " + this.ivUebergangssicherheit + StringKonverter.lineSeparator);
    lvOut.append("Zuweisungsbetrag: " + this.ivZuweisungsbetrag + StringKonverter.lineSeparator);
    lvOut.append("Zuweisungsbetragwaehrung: " + this.ivZuweisungsbetragWaehrung + StringKonverter.lineSeparator);
    lvOut.append("DeckungsregisterRelevant: " + this.ivDeckungsregisterRelevant + StringKonverter.lineSeparator);

    return lvOut.toString();
  }
}
