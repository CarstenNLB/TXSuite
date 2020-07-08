/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten.Entities;

import nlb.txs.schnittstelle.Sicherheiten.SicherheitenDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class Immobilie 
{    
    /**
     * GUID fuer Tabelle CMS_AST (Immobilie)
     */
    private String ivId;
    
    /**
     * Referenz-ID fuer das Objekt
     */
    private String ivReferenzId;
    
    /**
     * Nominalwert
     */
    private String ivNominalwert;
    
    /**
     * Waehrung des Nominalwerts
     */
    private String ivNominalwertWaehrung;
    
    /**
     * Beleihungswert
     */
    private String ivBeleihungswert;
    
    /**
     * Waehrung des Beleihungswerts
     */
    private String ivBeleihungswertWaehrung;
    
    /**
     * Festsetzungsdatum
     */
    private String ivFestsetzungsdatum;
    
    /**
     * GUID fuer Immobilienobjekt
     */
    private String ivImmobilienobjektId;
    
    /**
     * Objekt-ID
     */
    private String ivObjektId;
    
    /**
     * Immobilienobjektart-ID
     */
    private String ivObjektartId;
    
    /**
     * Adress-ID
     */
    private String ivAdressId;
    
    /**
     * Beschreibung des Immobilienobjekts
     */
    private String ivBeschreibung;
    
    /**
     * GUID fuer Objektteil
     */
    private String ivObjektteilId;
    
    /**
     * Teil-ID fuer das Objekt
     */
    private String ivTeilId;
    
    /**
     * Nutzungsart
     */
    private String ivNutzungsart;
    
    /**
     * Nutzung des Objekts
     */
    private String ivNutzung;
    
    /**
     * Prozentanteil der gewerblichen Nutzung in Bezug zu Hauptobj.
     */
    private String ivProzentanteil;
    
    /**
     * Grad der Baufertigstellung
     */
    private String ivGradBaufertigstellung;
    
    /**
     * Fertigstellungsdatum 
     */
    private String ivFertigstellungsdatum;
    
    /**
     * Gesamtflaeche der Immobilie
     */
    private String ivGesamtflaeche;
    
    /**
     * Mengeneinheit der Grundstuecksflaeche
     */
    private String ivMengeneinheit;
    
    /**
     * Bermerkung
     */
    private String ivBemerkung;
    
    /**
     * Deckungskennzeichen
     */
    private String ivDeckungskennzeichen;
    
    /**
     * Zeitstempel fuer Deckungskennzeichen
     */
    private String ivZeitstempelDeckungskennzeichen;

    /**
     * Makrolage
     */
    private String ivMakrolage;
    
    /**
     * Mikrolage
     */
    private String ivMikrolage;
    
    /**
     * Qualitaet der Immobilie
     */
    private String ivQualitaet;
    
    /**
     * Miethoehe rel. z. Markt
     */
    private String ivMiethoehe;
    
    /**
     * Kennzeichen Hochwassergebiet
     */
    private String ivKennzeichenHochwassergebiet;
    
    /**
     * Kennzeichen Erdbebengebiet
     */
    private String ivKennzeichenErdbebengebiet;
    
    /**
     * Kennzeichen: Denkmalschutzgebiet
     */
    private String ivKennzeichenDenkmalschutzgebiet;
    
    /**
     * Kennzeichen Historische Staette
     */
    private String ivKennzeichenHistorischeStaette;
    
    /**
     * Weist auf Vorhandensein von Wertminderungsfaktoren hin
     */
    private String ivWertminderungsfaktoren;
    
    /**
     * Beschreibung der anderen Wertminderungsfaktoren
     */
    private String ivBeschreibungWertminderungsfaktoren;
    
    /**
     * Laenderschluessel
     */
    private String ivLaenderschluessel;
    
    /**
     * Region
     */
    private String ivRegion;
    
    /**
     * Ort
     */
    private String ivOrt;
    
    /**
     * Postleitzahl
     */
    private String ivPostleitzahl;
    
    /**
     * Strasse
     */
    private String ivStrasse;
    
    /**
     * Hausnummer
     */
    private String ivHausnummer;
    
    /**
     * Ergaenzung zur Hausnummer
     */
    private String ivErgaenzungHausnummer;
    
    /**
     * Gebaeude
     */
    private String ivGebaeude;
    
    /**
     * Sachwert
     */
    private String ivSachwert;
    
    /**
     * Waehrung des Sachwerts
     */
    private String ivSachwertWaehrung;
    
    /** 
     * Ertragswert
     */
    private String ivErtragswert;
    
    /**
     * Waehrung des Ertragswerts
     */
    private String ivErtragswertWaehrung;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
     * Konstruktor
     * @param pvLogger log4j-Logger
     */
    public Immobilie(Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        this.ivId = new String();
        this.ivReferenzId = new String();
        this.ivNominalwert = new String();
        this.ivNominalwertWaehrung = new String();
        this.ivBeleihungswert = new String();
        this.ivBeleihungswertWaehrung = new String();
        this.ivFestsetzungsdatum = new String();
        this.ivImmobilienobjektId = new String();
        this.ivObjektId = new String();
        this.ivObjektartId = new String();
        this.ivAdressId = new String();
        this.ivBeschreibung = new String();
        this.ivObjektteilId = new String();
        this.ivTeilId = new String();
        this.ivNutzungsart = new String();
        this.ivNutzung = new String();
        this.ivProzentanteil = new String();
        this.ivGradBaufertigstellung = new String();
        this.ivFertigstellungsdatum = new String();
        this.ivGesamtflaeche = new String();
        this.ivMengeneinheit = new String();
        this.ivBemerkung = new String();
        this.ivDeckungskennzeichen = new String();
        this.ivZeitstempelDeckungskennzeichen = new String();
        this.ivMakrolage = new String();
        this.ivMikrolage = new String();
        this.ivQualitaet = new String();
        this.ivMiethoehe = new String();
        this.ivKennzeichenHochwassergebiet = new String();
        this.ivKennzeichenErdbebengebiet = new String();
        this.ivKennzeichenDenkmalschutzgebiet = new String();
        this.ivKennzeichenHistorischeStaette = new String();
        this.ivWertminderungsfaktoren = new String();
        this.ivBeschreibungWertminderungsfaktoren = new String();
        this.ivLaenderschluessel = new String();
        this.ivRegion = new String();
        this.ivOrt = new String();
        this.ivPostleitzahl = new String();
        this.ivStrasse = new String();
        this.ivHausnummer = new String();
        this.ivErgaenzungHausnummer = new String();
        this.ivGebaeude = new String();
        this.ivSachwert = new String();
        this.ivSachwertWaehrung = new String();
        this.ivErtragswert = new String();
        this.ivErtragswertWaehrung = new String();
    }

    /**
     * Liefert die ID der Immobilie
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * Setzt die ID der Immobilie
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
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
     * Liefert den Nominalwert
     * @return the nominalwert
     */
    public String getNominalwert() {
        return this.ivNominalwert;
    }

    /**
     * Setzt den Nominalwert
     * @param pvNominalwert the nominalwert to set
     */
    public void setNominalwert(String pvNominalwert) {
        this.ivNominalwert = pvNominalwert;
    }

    /**
     * Liefert die Waehrung des Nominalwerts
     * @return the nominalwertWaehrung
     */
    public String getNominalwertWaehrung() {
        return this.ivNominalwertWaehrung;
    }

    /**
     * Setzt die Waehrung des Nominalwerts
     * @param pvNominalwertWaehrung the nominalwertWaehrung to set
     */
    public void setNominalwertWaehrung(String pvNominalwertWaehrung) {
        this.ivNominalwertWaehrung = pvNominalwertWaehrung;
    }

    /**
     * Liefert den Beleihungswert
     * @return the beleihungswert
     */
    public String getBeleihungswert() {
        return this.ivBeleihungswert;
    }

    /**
     * Setzt den Beleihungswert
     * @param pvBeleihungswert the beleihungswert to set
     */
    public void setBeleihungswert(String pvBeleihungswert) {
        this.ivBeleihungswert = pvBeleihungswert;
    }

    /**
     * Liefert die Waehrung des Beleihungswert
     * @return the beleihungswertWaehrung
     */
    public String getBeleihungswertWaehrung() {
        return this.ivBeleihungswertWaehrung;
    }

    /**
     * Setzt die Waehrung des Beleihungswert
     * @param pvBeleihungswertWaehrung the beleihungswertWaehrung to set
     */
    public void setBeleihungswertWaehrung(String pvBeleihungswertWaehrung) {
        this.ivBeleihungswertWaehrung = pvBeleihungswertWaehrung;
    }

    /**
     * Liefert das Festsetzungsdatum
     * @return
     */
    public String getFestsetzungsdatum() 
    {
		return ivFestsetzungsdatum;
	}

    /**
     * Setzt das Festsetzungsdatum
     * @param pvFestsetzungsdatum
     */
	public void setFestsetzungsdatum(String pvFestsetzungsdatum) 
	{
		this.ivFestsetzungsdatum = pvFestsetzungsdatum;
	}

	  /**
     * Liefert die ID des Immobilienobjekts
     * @return 
     */
    public String getImmobilienobjektId() {
        return this.ivImmobilienobjektId;
    }

    /**
     * Setzt die ID des Immobilienobjekts
     * @param pvImmobilienobjektId 
     */
    public void setImmobilienobjektId(String pvImmobilienobjektId) {
        this.ivImmobilienobjektId = pvImmobilienobjektId;
    }

    /**
     * Liefert die ID des Objekts
     * @return the objektId
     */
    public String getObjektId() {
        return this.ivObjektId;
    }

    /**
     * Setzt die ID des Objekts
     * @param pvObjektId the objektId to set
     */
    public void setObjektId(String pvObjektId) {
        this.ivObjektId = pvObjektId;
    }

    /**
     * Liefert die ID der Objektart
     * @return the objektartId
     */
    public String getObjektartId() {
        return this.ivObjektartId;
    }

    /**
     * Setzt die ID der Objektart
     * @param pvObjektartId the objektartId to set
     */
    public void setObjektartId(String pvObjektartId) {
        this.ivObjektartId = pvObjektartId;
    }

    /**
     * Liefert die ID der Adresse
     * @return the adressId
     */
    public String getAdressId() {
        return this.ivAdressId;
    }

    /**
     * Setzt die ID der Adresse
     * @param pvAdressId the adressId to set
     */
    public void setAdressId(String pvAdressId) {
        this.ivAdressId = pvAdressId;
    }

    /**
     * Liefert die Beschreibung
     * @return the beschreibung
     */
    public String getBeschreibung() {
        return this.ivBeschreibung;
    }

    /**
     * Setzt die Beschreibung
     * @param pvBeschreibung the beschreibung to set
     */
    public void setBeschreibung(String pvBeschreibung) {
        this.ivBeschreibung = pvBeschreibung;
    }

    /**
     * Liefert die ID des Objektteils
     * @return the objektteilId
     */
    public String getObjektteilId() {
        return this.ivObjektteilId;
    }

    /**
     * Setzt die ID des Objektteils
     * @param pvObjektteilId the objektteilId to set
     */
    public void setObjektteilId(String pvObjektteilId) {
        this.ivObjektteilId = pvObjektteilId;
    }

    /**
     * Liefert die TeilID
     * @return the teilId
     */
    public String getTeilId() {
        return this.ivTeilId;
    }

    /**
     * Setzt die TeilID
     * @param pvTeilId the teilId to set
     */
    public void setTeilId(String pvTeilId) {
        this.ivTeilId = pvTeilId;
    }

    /**
     * Liefert die Nutzungsart
     * @return the nutzungsart
     */
    public String getNutzungsart() {
        return this.ivNutzungsart;
    }

    /**
     * Setzt die Nutzungsart
     * @param pvNutzungsart the nutzungsart to set
     */
    public void setNutzungsart(String pvNutzungsart) {
        this.ivNutzungsart = pvNutzungsart;
    }

    /**
     * Liefert die Nutzung
     * @return the nutzung
     */
    public String getNutzung() {
        return this.ivNutzung;
    }

    /**
     * Setzt die Nutzung
     * @param pvNutzung the nutzung to set
     */
    public void setNutzung(String pvNutzung) {
        this.ivNutzung = pvNutzung;
    }

    /**
     * Liefert den Prozentanteil
     * @return the prozentanteil
     */
    public String getProzentanteil() {
        return this.ivProzentanteil;
    }

    /**
     * Setzt den Prozentanteil
     * @param pvProzentanteil the prozentanteil to set
     */
    public void setProzentanteil(String pvProzentanteil) {
        this.ivProzentanteil = pvProzentanteil;
    }

    /**
     * Liefert den Grad der Baufertigstellung
     * @return the gradBaufertigstellung
     */
    public String getGradBaufertigstellung() {
        return this.ivGradBaufertigstellung;
    }

    /**
     * Setzt den Grad der Baufertigstellung
     * @param pvGradBaufertigstellung the gradBaufertigstellung to set
     */
    public void setGradBaufertigstellung(String pvGradBaufertigstellung) {
        this.ivGradBaufertigstellung = pvGradBaufertigstellung;
    }

    /**
     * Liefert das Fertigstellungsdatum
     * @return the fertigstellungsdatum
     */
    public String getFertigstellungsdatum() {
        return this.ivFertigstellungsdatum;
    }

    /**
     * Setzt das Fertigstellugsdatum
     * @param pvFertigstellungsdatum the fertigstellungsdatum to set
     */
    public void setFertigstellungsdatum(String pvFertigstellungsdatum) {
        this.ivFertigstellungsdatum = pvFertigstellungsdatum;
    }

    /**
     * Liefert die Gesamtflaeche
     * @return the gesamtflaeche
     */
    public String getGesamtflaeche() {
        return this.ivGesamtflaeche;
    }

    /**
     * Setzt die Gesamtflaeche
     * @param pvGesamtflaeche the gesamtflaeche to set
     */
    public void setGesamtflaeche(String pvGesamtflaeche) {
        this.ivGesamtflaeche = pvGesamtflaeche;
    }

    /**
     * Liefert die Mengeneinheit
     * @return the mengeneinheit
     */
    public String getMengeneinheit() {
        return this.ivMengeneinheit;
    }

    /**
     * Setzt die Mengeneinheit
     * @param pvMengeneinheit the mengeneinheit to set
     */
    public void setMengeneinheit(String pvMengeneinheit) {
        this.ivMengeneinheit = pvMengeneinheit;
    }

    /**
     * Liefert die Bemerkung
     * @return the bemerkung
     */
    public String getBemerkung() {
        return this.ivBemerkung;
    }

    /**
     * Setzt die Bemerkung
     * @param pvBemerkung the bemerkung to set
     */
    public void setBemerkung(String pvBemerkung) {
        this.ivBemerkung = pvBemerkung;
    }

    /**
     * Liefert das Deckungskennzeichen
     * @return the deckungskennzeichen
     */
    public String getDeckungskennzeichen() {
        return this.ivDeckungskennzeichen;
    }

    /**
     * Setzt das Deckungskennzeichen
     * @param pvDeckungskennzeichen the deckungskennzeichen to set
     */
    public void setDeckungskennzeichen(String pvDeckungskennzeichen) {
        this.ivDeckungskennzeichen = pvDeckungskennzeichen;
    }

    /**
     * Liefert den Zeitstempel des Deckungskennzeichens
     * @return the zeitstempelDeckungskennzeichen
     */
    public String getZeitstempelDeckungskennzeichen() {
        return this.ivZeitstempelDeckungskennzeichen;
    }

    /**
     * Setzt den Zeitstempel des Deckungskennzeichens
     * @param pvZeitstempelDeckungskennzeichen the zeitstempelDeckungskennzeichen to set
     */
    public void setZeitstempelDeckungskennzeichen(String pvZeitstempelDeckungskennzeichen) {
        this.ivZeitstempelDeckungskennzeichen = pvZeitstempelDeckungskennzeichen;
    }

    /**
     * Liefert die Makrolage
     * @return the makrolage
     */
    public String getMakrolage() {
        return this.ivMakrolage;
    }

    /**
     * Setzt die Makrolage
     * @param pvMakrolage the makrolage to set
     */
    public void setMakrolage(String pvMakrolage) {
        this.ivMakrolage = pvMakrolage;
    }

    /**
     * Liefert die Mikrolage
     * @return the mikrolage
     */
    public String getMikrolage() {
        return this.ivMikrolage;
    }

    /**
     * Setzt die Mikrolage
     * @param pvMikrolage the mikrolage to set
     */
    public void setMikrolage(String pvMikrolage) {
        this.ivMikrolage = pvMikrolage;
    }

    /**
     * Liefert die Qualitaet
     * @return the qualitaet
     */
    public String getQualitaet() {
        return this.ivQualitaet;
    }

    /**
     * Setzt die Qualitaet
     * @param pvQualitaet the qualitaet to set
     */
    public void setQualitaet(String pvQualitaet) {
        this.ivQualitaet = pvQualitaet;
    }

    /**
     * Liefert die Miethoehe
     * @return the miethoehe
     */
    public String getMiethoehe() {
        return this.ivMiethoehe;
    }

    /**
     * Setzt die Miethoehe
     * @param pvMiethoehe the miethoehe to set
     */
    public void setMiethoehe(String pvMiethoehe) {
        this.ivMiethoehe = pvMiethoehe;
    }

    /**
     * Liefert das Kennzeichen fuer Hochwassergebiet
     * @return the kennzeichenHochwassergebiet
     */
    public String getKennzeichenHochwassergebiet() {
        return this.ivKennzeichenHochwassergebiet;
    }

    /**
     * Setzt das Kennzeichen fuer Hochwassergebiet
     * @param pvKennzeichenHochwassergebiet the kennzeichenHochwassergebiet to set
     */
    public void setKennzeichenHochwassergebiet(String pvKennzeichenHochwassergebiet) {
        this.ivKennzeichenHochwassergebiet = pvKennzeichenHochwassergebiet;
    }

    /**
     * Liefert das Kennzeichen fuer Erdbebengebiet
     * @return the kennzeichenErdbebengebiet
     */
    public String getKennzeichenErdbebengebiet() {
        return this.ivKennzeichenErdbebengebiet;
    }

    /**
     * Setzt das Kennzeichen fuer Erdbebengebiet
     * @param pvKennzeichenErdbebengebiet the kennzeichenErdbebengebiet to set
     */
    public void setKennzeichenErdbebengebiet(String pvKennzeichenErdbebengebiet) {
        this.ivKennzeichenErdbebengebiet = pvKennzeichenErdbebengebiet;
    }

    /**
     * Liefert das Kennzeichen fuer Denkmalschutzgebiet
     * @return the kennzeichenDenkmalschutzgebiet
     */
    public String getKennzeichenDenkmalschutzgebiet() {
        return this.ivKennzeichenDenkmalschutzgebiet;
    }

    /**
     * Setzt das Kennzeichen fuer Denkmalschutzgebiet
     * @param pvKennzeichenDenkmalschutzgebiet the kennzeichenDenkmalschutzgebiet to set
     */
    public void setKennzeichenDenkmalschutzgebiet(String pvKennzeichenDenkmalschutzgebiet) {
        this.ivKennzeichenDenkmalschutzgebiet = pvKennzeichenDenkmalschutzgebiet;
    }

    /**
     * Liefert das Kennzeichen fuer historische Staette
     * @return the kennzeichenHistorischeStaette
     */
    public String getKennzeichenHistorischeStaette() {
        return this.ivKennzeichenHistorischeStaette;
    }

    /**
     * Setzt das Kennzeichen fuer historische Staette
     * @param pvKennzeichenHistorischeStaette the kennzeichenHistorischeStaette to set
     */
    public void setKennzeichenHistorischeStaette(String pvKennzeichenHistorischeStaette) {
        this.ivKennzeichenHistorischeStaette = pvKennzeichenHistorischeStaette;
    }

    /**
     * Liefert die Wertminderungsfaktoren
     * @return the wertminderungsfaktoren
     */
    public String getWertminderungsfaktoren() {
        return this.ivWertminderungsfaktoren;
    }

    /**
     * Setzt die Wertminderungsfaktoren
     * @param pvWertminderungsfaktoren the wertminderungsfaktoren to set
     */
    public void setWertminderungsfaktoren(String pvWertminderungsfaktoren) {
        this.ivWertminderungsfaktoren = pvWertminderungsfaktoren;
    }

    /**
     * Liefert die Beschreibung des Wertminderungsfaktoren
     * @return the beschreibungWertminderungsfaktoren
     */
    public String getBeschreibungWertminderungsfaktoren() {
        return this.ivBeschreibungWertminderungsfaktoren;
    }

    /**
     * Setzt die Beschreibung der Wertminderungsfaktoren
     * @param pvBeschreibungWertminderungsfaktoren the beschreibungWertminderungsfaktoren to set
     */
    public void setBeschreibungWertminderungsfaktoren(String pvBeschreibungWertminderungsfaktoren) {
        this.ivBeschreibungWertminderungsfaktoren = pvBeschreibungWertminderungsfaktoren;
    }

    /**
     * Liefert den Laenderschluessel
     * @return the laenderschluessel
     */
    public String getLaenderschluessel() {
        return this.ivLaenderschluessel;
    }

    /**
     * Setzt den Laenderschluessel
     * @param pvLaenderschluessel the laenderschluessel to set
     */
    public void setLaenderschluessel(String pvLaenderschluessel) {
        this.ivLaenderschluessel = pvLaenderschluessel;
    }

    /**
     * Liefert die Region
     * @return the region
     */
    public String getRegion() {
        return this.ivRegion;
    }

    /**
     * Setzt die Region
     * @param pvRegion the region to set
     */
    public void setRegion(String pvRegion) {
        this.ivRegion = pvRegion;
    }

    /**
     * Liefert den Ort
     * @return the ort
     */
    public String getOrt() {
        return this.ivOrt;
    }

    /**
     * Setzt den Ort
     * @param pvOrt the ort to set
     */
    public void setOrt(String pvOrt) {
        this.ivOrt = pvOrt;
    }

    /**
     * Liefert die Postleitzahl
     * @return the postleitzahl
     */
    public String getPostleitzahl() {
        return this.ivPostleitzahl;
    }

    /**
     * Setzt die Postleitzahl
     * @param pvPostleitzahl the postleitzahl to set
     */
    public void setPostleitzahl(String pvPostleitzahl) {
        this.ivPostleitzahl = pvPostleitzahl;
    }

    /**
     * Liefert die Strasse
     * @return the strasse
     */
    public String getStrasse() {
        return this.ivStrasse;
    }

    /**
     * Setzt die Strasse
     * @param pvStrasse the strasse to set
     */
    public void setStrasse(String pvStrasse) {
        this.ivStrasse = pvStrasse;
    }

    /**
     * Liefert die Hausnummer
     * @return the hausnummer
     */
    public String getHausnummer() {
        return this.ivHausnummer;
    }

    /**
     * Setzt die Hausnummer
     * @param pvHausnummer the hausnummer to set
     */
    public void setHausnummer(String pvHausnummer) {
        this.ivHausnummer = pvHausnummer;
    }

    /**
     * Liefert die Ergaenzung der Hausnummer
     * @return the ergaenzungHausnummer
     */
    public String getErgaenzungHausnummer() {
        return this.ivErgaenzungHausnummer;
    }

    /**
     * Setzt die Ergaenzung der Hausnummer
     * @param pvErgaenzungHausnummer the ergaenzungHausnummer to set
     */
    public void setErgaenzungHausnummer(String pvErgaenzungHausnummer) {
        this.ivErgaenzungHausnummer = pvErgaenzungHausnummer;
    }

    /**
     * Liefert das Gebaeude
     * @return the gebaeude
     */
    public String getGebaeude() {
        return this.ivGebaeude;
    }

    /**
     * Setzt das Gebaeude
     * @param pvGebaeude the gebaeude to set
     */
    public void setGebaeude(String pvGebaeude) {
        this.ivGebaeude = pvGebaeude;
    }

    /**
     * Liefert den Sachwert
     * @return the sachwert
     */
    public String getSachwert() {
        return this.ivSachwert;
    }

    /**
     * Setzt den Sachwert
     * @param pvSachwert the sachwert to set
     */
    public void setSachwert(String pvSachwert) {
        this.ivSachwert = pvSachwert;
    }

    /**
     * Liefert die Waehrung des Sachwerts
     * @return the sachwertWaehrung
     */
    public String getSachwertWaehrung() {
        return this.ivSachwertWaehrung;
    }

    /**
     * Setzt die Waehrung des Sachwerts
     * @param pvSachwertWaehrung the sachwertWaehrung to set
     */
    public void setSachwertWaehrung(String pvSachwertWaehrung) {
        this.ivSachwertWaehrung = pvSachwertWaehrung;
    }

    /**
     * Liefert den Ertragswert
     * @return the ertragswert
     */
    public String getErtragswert() {
        return this.ivErtragswert;
    }

    /**
     * Setzt den Ertragswert
     * @param pvErtragswert the ertragswert to set
     */
    public void setErtragswert(String pvErtragswert) {
        this.ivErtragswert = pvErtragswert;
    }

    /**
     * Liefert die Waehrung des Ertragswerts
     * @return the ertragswertWaehrung
     */
    public String getErtragswertWaehrung() {
        return this.ivErtragswertWaehrung;
    }

    /**
     * Setzt die Waehrung des Ertragswerts
     * @param pvErtragswertWaehrung the ertragswertWaehrung to set
     */
    public void setErtragswertWaehrung(String pvErtragswertWaehrung) {
        this.ivErtragswertWaehrung = pvErtragswertWaehrung;
    }

    /**
     * Zerlegt eine Zeile in die unterschiedlichen Immobilien-Daten
     * @param pvZeile Die zu zerlegende Zeile
     * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    public void parseImmobilie(String pvZeile, int pvQuelle)
    {

    	String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
    	int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

    	// steuerung/iteration eingabesatz
    	for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
    	{

    		// wenn Tabulator erkannt
    		if (pvZeile.charAt(lvZzStr) == '\t')
    		{
    		  this.setImmobilie(lvLfd, lvTemp, pvQuelle);

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
    	this.setImmobilie(lvLfd, lvTemp, pvQuelle);
   }
    
    /**
     * Setzt einen Wert der Immobilie
     * @param pvPos Position
     * @param pvWert Wert
     * @param pvQuelle Quellsystem (SAP CMS oder VVS)
     */
    private void setImmobilie(int pvPos, String pvWert, int pvQuelle)
    {
        //ivLogger.info("Pos: " + pvPos + " Wert: " + pvWert);
        if (pvQuelle == SicherheitenDaten.SAPCMS)
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
              this.setReferenzId(pvWert);
              break;
          case 3:
              ////pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setNominalwert(pvWert.trim());
              break;
          case 4:
              this.setNominalwertWaehrung(pvWert);
              break;
          case 5:
              ////pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setBeleihungswert(pvWert.trim());
              break;
          case 6:
              this.setBeleihungswertWaehrung(pvWert);
              break;
          case 7:
              if (pvWert.length() == 8)
              {
                  pvWert = DatumUtilities.changeDatePoints(pvWert);
              }
              this.setFestsetzungsdatum(pvWert);
          	  break;
          case 8:
              this.setImmobilienobjektId(pvWert);
              break;
          case 9:
              Long lvHelpLong = new Long(pvWert); // Fuehrende Nullen entfernen
              this.setObjektId(lvHelpLong.toString());
              break;
          case 10:
              this.setObjektartId(pvWert);
              break;
          case 11:
              this.setAdressId(pvWert);
              break;
          case 12:
              this.setBeschreibung(pvWert);
              break;
          case 13:
              this.setObjektteilId(pvWert);
              break;
          case 14:
              this.setTeilId(pvWert);
              break;
          case 15:
              this.setNutzungsart(pvWert);
              //ivLogger.info("Immo-ID: " + ivObjektId + " Nutzungsart: " + pvWert);
              break;
          case 16:
              this.setNutzung(pvWert);
              break;
          case 17:
              ////pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setProzentanteil(pvWert.trim());
              break;
          case 18:
              ////pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setGradBaufertigstellung(pvWert.trim());
              break;
          case 19:
              if (pvWert.length() == 8)
              {
                  pvWert = DatumUtilities.changeDatePoints(pvWert);
              }
              this.setFertigstellungsdatum(pvWert);
              break;
          case 20:
              this.setGesamtflaeche(pvWert);
              break;
          case 21:
              this.setMengeneinheit(pvWert);
              break;
          case 22:
              this.setBemerkung(pvWert);
              break;
          case 23:
              this.setDeckungskennzeichen(pvWert);
              break;
          case 24:
              this.setZeitstempelDeckungskennzeichen(pvWert);
              break;
          case 25:
              this.setMakrolage(pvWert);
              break;
          case 26:
              this.setMikrolage(pvWert);
              break;
          case 27:
              this.setQualitaet(pvWert);
              break;
          case 28:
              this.setMiethoehe(pvWert);
              break;
          case 29:
              this.setKennzeichenHochwassergebiet(pvWert);
              break;
          case 30:
              this.setKennzeichenErdbebengebiet(pvWert);
              break;
          case 31:
              this.setKennzeichenDenkmalschutzgebiet(pvWert);
              break;
          case 32:
              this.setKennzeichenHistorischeStaette(pvWert);
              break;
          case 33:
              this.setWertminderungsfaktoren(pvWert);
              break;
          case 34:
              this.setBeschreibungWertminderungsfaktoren(pvWert);
              break;
          case 35:
              this.setLaenderschluessel(pvWert);
              break;
          case 36:
              this.setRegion(pvWert);
              break;
          case 37:
              this.setOrt(pvWert);
              break;
          case 38:
              this.setPostleitzahl(pvWert);
              break;
          case 39:
              this.setStrasse(pvWert);
              break;
          case 40:
              this.setHausnummer(pvWert);
              break;
          case 41:
              this.setErgaenzungHausnummer(pvWert);
              break;
          case 42:
              this.setGebaeude(pvWert);
              break;
          case 43:
              ////pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setSachwert(pvWert.trim());
              break;
          case 44:
              this.setSachwertWaehrung(pvWert);
              break;
          case 45:
              ////pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setErtragswert(pvWert.trim());
              break;
          case 46:
              this.setErtragswertWaehrung(pvWert);
              break;
          default:
              ivLogger.info("Immobilie SAP CMS: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
          }
        }

        if (pvQuelle == SicherheitenDaten.VVS)
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
                    this.setReferenzId(pvWert);
                    break;
                case 3:
                    pvWert = pvWert.replace(".", "");
                    pvWert = pvWert.replace(",", ".");
                    this.setNominalwert(pvWert.trim());
                    break;
                case 4:
                    this.setNominalwertWaehrung(pvWert);
                    break;
                case 5:
                    pvWert = pvWert.replace(".", "");
                    pvWert = pvWert.replace(",", ".");
                    this.setBeleihungswert(pvWert.trim());
                    break;
                case 6:
                    this.setBeleihungswertWaehrung(pvWert);
                    break;
                case 7:
                    if (pvWert.length() == 8)
                    {
                        pvWert = DatumUtilities.changeDatePoints(pvWert);
                    }
                    this.setFestsetzungsdatum(pvWert);
                    break;
                case 8:
                    this.setImmobilienobjektId(pvWert);
                    break;
                case 9:
                    Long lvHelpLong = new Long(pvWert); // Fuehrende Nullen entfernen
                    this.setObjektId(lvHelpLong.toString());
                    break;
                case 10:
                    this.setObjektartId(pvWert);
                    break;
                case 11:
                    this.setAdressId(pvWert);
                    break;
                case 12:
                    this.setBeschreibung(pvWert);
                    break;
                case 13:
                    this.setObjektteilId(pvWert);
                    break;
                case 14:
                    this.setTeilId(pvWert);
                    break;
                case 15:
                    this.setNutzungsart(pvWert);
                    break;
                case 16:
                    this.setNutzung(pvWert);
                    break;
                case 17:
                    pvWert = pvWert.replace(".", "");
                    pvWert = pvWert.replace(",", ".");
                    this.setProzentanteil(pvWert.trim());
                    break;
                case 18:
                    pvWert = pvWert.replace(".", "");
                    pvWert = pvWert.replace(",", ".");
                    this.setGradBaufertigstellung(pvWert.trim());
                    break;
                case 19:
                    if (pvWert.length() == 8)
                    {
                        pvWert = DatumUtilities.changeDatePoints(pvWert);
                    }
                    this.setFertigstellungsdatum(pvWert);
                    break;
                case 20:
                    this.setGesamtflaeche(pvWert);
                    break;
                case 21:
                    this.setMengeneinheit(pvWert);
                    break;
                case 22:
                    this.setBemerkung(pvWert);
                    break;
                case 23:
                    this.setDeckungskennzeichen(pvWert);
                    break;
                case 24:
                    this.setZeitstempelDeckungskennzeichen(pvWert);
                    break;
                case 25:
                    this.setMakrolage(pvWert);
                    break;
                case 26:
                    this.setMikrolage(pvWert);
                    break;
                case 27:
                    this.setQualitaet(pvWert);
                    break;
                case 28:
                    this.setMiethoehe(pvWert);
                    break;
                case 29:
                    this.setKennzeichenHochwassergebiet(pvWert);
                    break;
                case 30:
                    this.setKennzeichenErdbebengebiet(pvWert);
                    break;
                case 31:
                    this.setKennzeichenDenkmalschutzgebiet(pvWert);
                    break;
                case 32:
                    this.setKennzeichenHistorischeStaette(pvWert);
                    break;
                case 33:
                    this.setWertminderungsfaktoren(pvWert);
                    break;
                case 34:
                    this.setBeschreibungWertminderungsfaktoren(pvWert);
                    break;
                case 35:
                    this.setLaenderschluessel(pvWert);
                    break;
                case 36:
                    this.setRegion(pvWert);
                    break;
                case 37:
                    this.setOrt(pvWert);
                    break;
                case 38:
                    this.setPostleitzahl(pvWert);
                    break;
                case 39:
                    this.setStrasse(pvWert);
                    break;
                case 40:
                    this.setHausnummer(pvWert);
                    break;
                case 41:
                    this.setErgaenzungHausnummer(pvWert);
                    break;
                case 42:
                    this.setGebaeude(pvWert);
                    break;
                case 43:
                    pvWert = pvWert.replace(".", "");
                    pvWert = pvWert.replace(",", ".");
                    this.setSachwert(pvWert.trim());
                    break;
                case 44:
                    this.setSachwertWaehrung(pvWert);
                    break;
                case 45:
                    pvWert = pvWert.replace(".", "");
                    pvWert = pvWert.replace(",", ".");
                    this.setErtragswert(pvWert.trim());
                    break;
                case 46:
                    this.setErtragswertWaehrung(pvWert);
                    break;
                default:
                    ivLogger.info("Immobilie VVS: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
            }
        }
    }
    
    /**
     * Schreibt den Inhalt einer Immobilie in einen String
     * @return
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Id: " + ivId + StringKonverter.lineSeparator);
        lvOut.append("Nominalwert: " + this.ivNominalwert + StringKonverter.lineSeparator);
        lvOut.append("Nominalwertwaehrung: " + this.ivNominalwertWaehrung + StringKonverter.lineSeparator);
        lvOut.append("Beleihungswert: " + this.ivBeleihungswert + StringKonverter.lineSeparator);
        lvOut.append("Beleihungswertwaehrung: " + this.ivBeleihungswertWaehrung + StringKonverter.lineSeparator);
        lvOut.append("Festsetzungsdatum: " + this.ivFestsetzungsdatum + StringKonverter.lineSeparator);
        lvOut.append("ObjektId: " + this.ivImmobilienobjektId + StringKonverter.lineSeparator);
        lvOut.append("ObjektartId: " + this.ivObjektartId + StringKonverter.lineSeparator);
        lvOut.append("AdressId: " + this.ivAdressId + StringKonverter.lineSeparator);
        lvOut.append("Beschreibung: " + this.ivBeschreibung + StringKonverter.lineSeparator);
        lvOut.append("ObjektteilId" + this.ivObjektteilId + StringKonverter.lineSeparator);
        lvOut.append("TeilId: " + this.ivTeilId + StringKonverter.lineSeparator);
        lvOut.append("Nutzungsart: " + this.ivNutzungsart + StringKonverter.lineSeparator);
        lvOut.append("Nutzung: " + this.ivNutzung + StringKonverter.lineSeparator);
        lvOut.append("Prozentanteil: " + this.ivProzentanteil + StringKonverter.lineSeparator);
        lvOut.append("GradBaufertigstellung: " + this.ivGradBaufertigstellung + StringKonverter.lineSeparator);
        lvOut.append("Fertigstellungsdatum: " + this.ivFertigstellungsdatum + StringKonverter.lineSeparator);
        lvOut.append("Gesamtflaeche: " + this.ivGesamtflaeche + StringKonverter.lineSeparator);
        lvOut.append("Mengeneinheit: " + this.ivMengeneinheit + StringKonverter.lineSeparator);
        lvOut.append("Bemerkung: " + this.ivBemerkung + StringKonverter.lineSeparator);
        lvOut.append("Deckungskennzeichen: " + this.ivDeckungskennzeichen + StringKonverter.lineSeparator);
        lvOut.append("ZeitstempelDeckungskennzeichen: " + this.ivZeitstempelDeckungskennzeichen + StringKonverter.lineSeparator);
        lvOut.append("Makrolage: " + this.ivMakrolage + StringKonverter.lineSeparator);
        lvOut.append("Mikrolage: " + this.ivMikrolage + StringKonverter.lineSeparator);
        lvOut.append("Qualitaet: " + this.ivQualitaet + StringKonverter.lineSeparator);
        lvOut.append("Miethoehe: " + this.ivMiethoehe + StringKonverter.lineSeparator);
        lvOut.append("Kennzeichen Hochwassergebiet: " + this.ivKennzeichenHochwassergebiet + StringKonverter.lineSeparator);
        lvOut.append("Kennzeichen Erdbebengebiet: " + this.ivKennzeichenErdbebengebiet + StringKonverter.lineSeparator);
        lvOut.append("Kennzeichen Denkmalschutzgebiet: " + this.ivKennzeichenDenkmalschutzgebiet + StringKonverter.lineSeparator);
        lvOut.append("Kennzeichen Historische Staette: " + this.ivKennzeichenHistorischeStaette + StringKonverter.lineSeparator);
        lvOut.append("Wertminderungsfaktoren: " + this.ivWertminderungsfaktoren + StringKonverter.lineSeparator);
        lvOut.append("Beschreibung Wertminderungsfaktoren: " + this.ivBeschreibungWertminderungsfaktoren + StringKonverter.lineSeparator);
        lvOut.append("Laenderschluessel: " + this.ivLaenderschluessel + StringKonverter.lineSeparator);
        lvOut.append("Region: " + this.ivRegion + StringKonverter.lineSeparator);
        lvOut.append("Ort: " + this.ivOrt + StringKonverter.lineSeparator);
        lvOut.append("Postleitzahl: " + this.ivPostleitzahl + StringKonverter.lineSeparator);
        lvOut.append("Strasse: " + this.ivStrasse + StringKonverter.lineSeparator);
        lvOut.append("Hausnummer: " + this.ivHausnummer + StringKonverter.lineSeparator);
        lvOut.append("Ergaenzung Hausnummer: " + this.ivErgaenzungHausnummer + StringKonverter.lineSeparator);
        lvOut.append("Gebaeude: " + this.ivGebaeude + StringKonverter.lineSeparator);
        lvOut.append("Sachwert: " + this.ivSachwert + StringKonverter.lineSeparator);
        lvOut.append("Sachwertwaehrung: " + this.ivSachwertWaehrung + StringKonverter.lineSeparator);
        lvOut.append("Ertragswert: " + this.ivErtragswert + StringKonverter.lineSeparator);
        lvOut.append("Ertragswertwaehrung: " + this.ivErtragswertWaehrung + StringKonverter.lineSeparator);

        return lvOut.toString();
    }    
}
