/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.SAPCMS.Entities;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

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
     * Konstruktor
     */
    public Immobilie() 
    {
        super();
        this.ivId = new String();
        this.ivReferenzId = new String();
        this.ivNominalwert = new String();
        this.ivNominalwertWaehrung = new String();
        this.ivBeleihungswert = new String();
        this.ivBeleihungswertWaehrung = new String();
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
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
    }

    /**
     * @return the referenzId
     */
    public String getReferenzId() {
        return this.ivReferenzId;
    }

    /**
     * @param pvReferenzId the referenzId to set
     */
    public void setReferenzId(String pvReferenzId) {
        this.ivReferenzId = pvReferenzId;
    }

    /**
     * @return the nominalwert
     */
    public String getNominalwert() {
        return this.ivNominalwert;
    }

    /**
     * @param pvNominalwert the nominalwert to set
     */
    public void setNominalwert(String pvNominalwert) {
        this.ivNominalwert = pvNominalwert;
    }

    /**
     * @return the nominalwertWaehrung
     */
    public String getNominalwertWaehrung() {
        return this.ivNominalwertWaehrung;
    }

    /**
     * @param pvNominalwertWaehrung the nominalwertWaehrung to set
     */
    public void setNominalwertWaehrung(String pvNominalwertWaehrung) {
        this.ivNominalwertWaehrung = pvNominalwertWaehrung;
    }

    /**
     * @return the beleihungswert
     */
    public String getBeleihungswert() {
        return this.ivBeleihungswert;
    }

    /**
     * @param pvBeleihungswert the beleihungswert to set
     */
    public void setBeleihungswert(String pvBeleihungswert) {
        this.ivBeleihungswert = pvBeleihungswert;
    }

    /**
     * @return the beleihungswertWaehrung
     */
    public String getBeleihungswertWaehrung() {
        return this.ivBeleihungswertWaehrung;
    }

    /**
     * @param pvBeleihungswertWaehrung the beleihungswertWaehrung to set
     */
    public void setBeleihungswertWaehrung(String pvBeleihungswertWaehrung) {
        this.ivBeleihungswertWaehrung = pvBeleihungswertWaehrung;
    }

    /**
     * @return 
     */
    public String getImmobilienobjektId() {
        return this.ivImmobilienobjektId;
    }

    /**
     * @param pvImmobilienobjektId 
     */
    public void setImmobilienobjektId(String pvImmobilienobjektId) {
        this.ivImmobilienobjektId = pvImmobilienobjektId;
    }

    /**
     * @return the objektId
     */
    public String getObjektId() {
        return this.ivObjektId;
    }

    /**
     * @param pvObjektId the objektId to set
     */
    public void setObjektId(String pvObjektId) {
        this.ivObjektId = pvObjektId;
    }

    /**
     * @return the objektartId
     */
    public String getObjektartId() {
        return this.ivObjektartId;
    }

    /**
     * @param pvObjektartId the objektartId to set
     */
    public void setObjektartId(String pvObjektartId) {
        this.ivObjektartId = pvObjektartId;
    }

    /**
     * @return the adressId
     */
    public String getAdressId() {
        return this.ivAdressId;
    }

    /**
     * @param pvAdressId the adressId to set
     */
    public void setAdressId(String pvAdressId) {
        this.ivAdressId = pvAdressId;
    }

    /**
     * @return the beschreibung
     */
    public String getBeschreibung() {
        return this.ivBeschreibung;
    }

    /**
     * @param pvBeschreibung the beschreibung to set
     */
    public void setBeschreibung(String pvBeschreibung) {
        this.ivBeschreibung = pvBeschreibung;
    }

    /**
     * @return the objektteilId
     */
    public String getObjektteilId() {
        return this.ivObjektteilId;
    }

    /**
     * @param pvObjektteilId the objektteilId to set
     */
    public void setObjektteilId(String pvObjektteilId) {
        this.ivObjektteilId = pvObjektteilId;
    }

    /**
     * @return the teilId
     */
    public String getTeilId() {
        return this.ivTeilId;
    }

    /**
     * @param pvTeilId the teilId to set
     */
    public void setTeilId(String pvTeilId) {
        this.ivTeilId = pvTeilId;
    }

    /**
     * @return the nutzungsart
     */
    public String getNutzungsart() {
        return this.ivNutzungsart;
    }

    /**
     * @param pvNutzungsart the nutzungsart to set
     */
    public void setNutzungsart(String pvNutzungsart) {
        this.ivNutzungsart = pvNutzungsart;
    }

    /**
     * @return the nutzung
     */
    public String getNutzung() {
        return this.ivNutzung;
    }

    /**
     * @param pvNutzung the nutzung to set
     */
    public void setNutzung(String pvNutzung) {
        this.ivNutzung = pvNutzung;
    }

    /**
     * @return the prozentanteil
     */
    public String getProzentanteil() {
        return this.ivProzentanteil;
    }

    /**
     * @param pvProzentanteil the prozentanteil to set
     */
    public void setProzentanteil(String pvProzentanteil) {
        this.ivProzentanteil = pvProzentanteil;
    }

    /**
     * @return the gradBaufertigstellung
     */
    public String getGradBaufertigstellung() {
        return this.ivGradBaufertigstellung;
    }

    /**
     * @param pvGradBaufertigstellung the gradBaufertigstellung to set
     */
    public void setGradBaufertigstellung(String pvGradBaufertigstellung) {
        this.ivGradBaufertigstellung = pvGradBaufertigstellung;
    }

    /**
     * @return the fertigstellungsdatum
     */
    public String getFertigstellungsdatum() {
        return this.ivFertigstellungsdatum;
    }

    /**
     * @param pvFertigstellungsdatum the fertigstellungsdatum to set
     */
    public void setFertigstellungsdatum(String pvFertigstellungsdatum) {
        this.ivFertigstellungsdatum = pvFertigstellungsdatum;
    }

    /**
     * @return the gesamtflaeche
     */
    public String getGesamtflaeche() {
        return this.ivGesamtflaeche;
    }

    /**
     * @param pvGesamtflaeche the gesamtflaeche to set
     */
    public void setGesamtflaeche(String pvGesamtflaeche) {
        this.ivGesamtflaeche = pvGesamtflaeche;
    }

    /**
     * @return the mengeneinheit
     */
    public String getMengeneinheit() {
        return this.ivMengeneinheit;
    }

    /**
     * @param pvMengeneinheit the mengeneinheit to set
     */
    public void setMengeneinheit(String pvMengeneinheit) {
        this.ivMengeneinheit = pvMengeneinheit;
    }

    /**
     * @return the bemerkung
     */
    public String getBemerkung() {
        return this.ivBemerkung;
    }

    /**
     * @param pvBemerkung the bemerkung to set
     */
    public void setBemerkung(String pvBemerkung) {
        this.ivBemerkung = pvBemerkung;
    }

    /**
     * @return the deckungskennzeichen
     */
    public String getDeckungskennzeichen() {
        return this.ivDeckungskennzeichen;
    }

    /**
     * @param pvDeckungskennzeichen the deckungskennzeichen to set
     */
    public void setDeckungskennzeichen(String pvDeckungskennzeichen) {
        this.ivDeckungskennzeichen = pvDeckungskennzeichen;
    }

    /**
     * @return the zeitstempelDeckungskennzeichen
     */
    public String getZeitstempelDeckungskennzeichen() {
        return this.ivZeitstempelDeckungskennzeichen;
    }

    /**
     * @param pvZeitstempelDeckungskennzeichen the zeitstempelDeckungskennzeichen to set
     */
    public void setZeitstempelDeckungskennzeichen(String pvZeitstempelDeckungskennzeichen) {
        this.ivZeitstempelDeckungskennzeichen = pvZeitstempelDeckungskennzeichen;
    }

    /**
     * @return the makrolage
     */
    public String getMakrolage() {
        return this.ivMakrolage;
    }

    /**
     * @param pvMakrolage the makrolage to set
     */
    public void setMakrolage(String pvMakrolage) {
        this.ivMakrolage = pvMakrolage;
    }

    /**
     * @return the mikrolage
     */
    public String getMikrolage() {
        return this.ivMikrolage;
    }

    /**
     * @param pvMikrolage the mikrolage to set
     */
    public void setMikrolage(String pvMikrolage) {
        this.ivMikrolage = pvMikrolage;
    }

    /**
     * @return the qualitaet
     */
    public String getQualitaet() {
        return this.ivQualitaet;
    }

    /**
     * @param pvQualitaet the qualitaet to set
     */
    public void setQualitaet(String pvQualitaet) {
        this.ivQualitaet = pvQualitaet;
    }

    /**
     * @return the miethoehe
     */
    public String getMiethoehe() {
        return this.ivMiethoehe;
    }

    /**
     * @param pvMiethoehe the miethoehe to set
     */
    public void setMiethoehe(String pvMiethoehe) {
        this.ivMiethoehe = pvMiethoehe;
    }

    /**
     * @return the kennzeichenHochwassergebiet
     */
    public String getKennzeichenHochwassergebiet() {
        return this.ivKennzeichenHochwassergebiet;
    }

    /**
     * @param pvKennzeichenHochwassergebiet the kennzeichenHochwassergebiet to set
     */
    public void setKennzeichenHochwassergebiet(String pvKennzeichenHochwassergebiet) {
        this.ivKennzeichenHochwassergebiet = pvKennzeichenHochwassergebiet;
    }

    /**
     * @return the kennzeichenErdbebengebiet
     */
    public String getKennzeichenErdbebengebiet() {
        return this.ivKennzeichenErdbebengebiet;
    }

    /**
     * @param pvKennzeichenErdbebengebiet the kennzeichenErdbebengebiet to set
     */
    public void setKennzeichenErdbebengebiet(String pvKennzeichenErdbebengebiet) {
        this.ivKennzeichenErdbebengebiet = pvKennzeichenErdbebengebiet;
    }

    /**
     * @return the kennzeichenDenkmalschutzgebiet
     */
    public String getKennzeichenDenkmalschutzgebiet() {
        return this.ivKennzeichenDenkmalschutzgebiet;
    }

    /**
     * @param pvKennzeichenDenkmalschutzgebiet the kennzeichenDenkmalschutzgebiet to set
     */
    public void setKennzeichenDenkmalschutzgebiet(String pvKennzeichenDenkmalschutzgebiet) {
        this.ivKennzeichenDenkmalschutzgebiet = pvKennzeichenDenkmalschutzgebiet;
    }

    /**
     * @return the kennzeichenHistorischeStaette
     */
    public String getKennzeichenHistorischeStaette() {
        return this.ivKennzeichenHistorischeStaette;
    }

    /**
     * @param pvKennzeichenHistorischeStaette the kennzeichenHistorischeStaette to set
     */
    public void setKennzeichenHistorischeStaette(String pvKennzeichenHistorischeStaette) {
        this.ivKennzeichenHistorischeStaette = pvKennzeichenHistorischeStaette;
    }

    /**
     * @return the wertminderungsfaktoren
     */
    public String getWertminderungsfaktoren() {
        return this.ivWertminderungsfaktoren;
    }

    /**
     * @param pvWertminderungsfaktoren the wertminderungsfaktoren to set
     */
    public void setWertminderungsfaktoren(String pvWertminderungsfaktoren) {
        this.ivWertminderungsfaktoren = pvWertminderungsfaktoren;
    }

    /**
     * @return the beschreibungWertminderungsfaktoren
     */
    public String getBeschreibungWertminderungsfaktoren() {
        return this.ivBeschreibungWertminderungsfaktoren;
    }

    /**
     * @param pvBeschreibungWertminderungsfaktoren the beschreibungWertminderungsfaktoren to set
     */
    public void setBeschreibungWertminderungsfaktoren(String pvBeschreibungWertminderungsfaktoren) {
        this.ivBeschreibungWertminderungsfaktoren = pvBeschreibungWertminderungsfaktoren;
    }

    /**
     * @return the laenderschluessel
     */
    public String getLaenderschluessel() {
        return this.ivLaenderschluessel;
    }

    /**
     * @param pvLaenderschluessel the laenderschluessel to set
     */
    public void setLaenderschluessel(String pvLaenderschluessel) {
        this.ivLaenderschluessel = pvLaenderschluessel;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return this.ivRegion;
    }

    /**
     * @param pvRegion the region to set
     */
    public void setRegion(String pvRegion) {
        this.ivRegion = pvRegion;
    }

    /**
     * @return the ort
     */
    public String getOrt() {
        return this.ivOrt;
    }

    /**
     * @param pvOrt the ort to set
     */
    public void setOrt(String pvOrt) {
        this.ivOrt = pvOrt;
    }

    /**
     * @return the postleitzahl
     */
    public String getPostleitzahl() {
        return this.ivPostleitzahl;
    }

    /**
     * @param pvPostleitzahl the postleitzahl to set
     */
    public void setPostleitzahl(String pvPostleitzahl) {
        this.ivPostleitzahl = pvPostleitzahl;
    }

    /**
     * @return the strasse
     */
    public String getStrasse() {
        return this.ivStrasse;
    }

    /**
     * @param pvStrasse the strasse to set
     */
    public void setStrasse(String pvStrasse) {
        this.ivStrasse = pvStrasse;
    }

    /**
     * @return the hausnummer
     */
    public String getHausnummer() {
        return this.ivHausnummer;
    }

    /**
     * @param pvHausnummer the hausnummer to set
     */
    public void setHausnummer(String pvHausnummer) {
        this.ivHausnummer = pvHausnummer;
    }

    /**
     * @return the ergaenzungHausnummer
     */
    public String getErgaenzungHausnummer() {
        return this.ivErgaenzungHausnummer;
    }

    /**
     * @param pvErgaenzungHausnummer the ergaenzungHausnummer to set
     */
    public void setErgaenzungHausnummer(String pvErgaenzungHausnummer) {
        this.ivErgaenzungHausnummer = pvErgaenzungHausnummer;
    }

    /**
     * @return the gebaeude
     */
    public String getGebaeude() {
        return this.ivGebaeude;
    }

    /**
     * @param pvGebaeude the gebaeude to set
     */
    public void setGebaeude(String pvGebaeude) {
        this.ivGebaeude = pvGebaeude;
    }

    /**
     * @return the sachwert
     */
    public String getSachwert() {
        return this.ivSachwert;
    }

    /**
     * @param pvSachwert the sachwert to set
     */
    public void setSachwert(String pvSachwert) {
        this.ivSachwert = pvSachwert;
    }

    /**
     * @return the sachwertWaehrung
     */
    public String getSachwertWaehrung() {
        return this.ivSachwertWaehrung;
    }

    /**
     * @param pvSachwertWaehrung the sachwertWaehrung to set
     */
    public void setSachwertWaehrung(String pvSachwertWaehrung) {
        this.ivSachwertWaehrung = pvSachwertWaehrung;
    }

    /**
     * @return the ertragswert
     */
    public String getErtragswert() {
        return this.ivErtragswert;
    }

    /**
     * @param pvErtragswert the ertragswert to set
     */
    public void setErtragswert(String pvErtragswert) {
        this.ivErtragswert = pvErtragswert;
    }

    /**
     * @return the ertragswertWaehrung
     */
    public String getErtragswertWaehrung() {
        return this.ivErtragswertWaehrung;
    }

    /**
     * @param pvErtragswertWaehrung the ertragswertWaehrung to set
     */
    public void setErtragswertWaehrung(String pvErtragswertWaehrung) {
        this.ivErtragswertWaehrung = pvErtragswertWaehrung;
    }

    /**
     * @param pvZeile 
     * @return 
     * 
     */
    public boolean parseImmobilie(String pvZeile)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
     int    lvZzStr=0;              // pointer fuer satzbereich
     //int    lvZzFld=0;              // bytezaehler je ermitteltes feld
     
     // steuerung/iteration eingabesatz
     for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn semikolon erkannt
       if (pvZeile.charAt(lvZzStr) == '\t')
       {
         this.setImmobilie(lvLfd, lvTemp);
       
         lvLfd++;                  // naechste Feldnummer
         //lvZzFld = 0;              // Bytezaehler
       
         // loeschen Zwischenbuffer
         lvTemp = new String();

       }
       else
       {
           // uebernehmen byte aus eingabesatzposition
           lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
           //lvZzFld++;
       }
     } // ende for
     
     // Letzte Feld auch noch setzen
     this.setImmobilie(lvLfd, lvTemp);     
     
     return true;
   }
    
    /**
     * Setzt einen Wert der Immobilie
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setImmobilie(int pvPos, String pvWert)
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
              this.setImmobilienobjektId(pvWert);
              break;
          case 8:
              Long lvHelpLong = new Long(pvWert); // Fuehrende Nullen entfernen
              this.setObjektId(lvHelpLong.toString());
              break;              
          case 9:
              this.setObjektartId(pvWert);
              break;
          case 10:
              this.setAdressId(pvWert);
              break;
          case 11:
              this.setBeschreibung(pvWert);
              break;
          case 12:
              this.setObjektteilId(pvWert);
              break;
          case 13:
              this.setTeilId(pvWert);
              break;
          case 14:
              this.setNutzungsart(pvWert);
              break;
          case 15:
              this.setNutzung(pvWert);
              break;
          case 16:
              pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setProzentanteil(pvWert.trim());
              break;
          case 17:
              pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setGradBaufertigstellung(pvWert.trim());
              break;
          case 18:
              this.setFertigstellungsdatum(pvWert);
              break;
          case 19:
              this.setGesamtflaeche(pvWert);
              break;
          case 20:
              this.setMengeneinheit(pvWert);
              break;
          case 21:
              this.setBemerkung(pvWert);
              break;
          case 22:
              this.setDeckungskennzeichen(pvWert);
              break;
          case 23:
              this.setZeitstempelDeckungskennzeichen(pvWert);
              break;
          case 24:
              this.setMakrolage(pvWert);
              break;
          case 25:
              this.setMikrolage(pvWert);
              break;
          case 26:
              this.setQualitaet(pvWert);
              break;
          case 27:
              this.setMiethoehe(pvWert);
              break;
          case 28:
              this.setKennzeichenHochwassergebiet(pvWert);
              break;
          case 29:
              this.setKennzeichenErdbebengebiet(pvWert);
              break;
          case 30:
              this.setKennzeichenDenkmalschutzgebiet(pvWert);
              break;
          case 31:
              this.setKennzeichenHistorischeStaette(pvWert);
              break;
          case 32:
              this.setWertminderungsfaktoren(pvWert);
              break;
          case 33:
              this.setBeschreibungWertminderungsfaktoren(pvWert);
              break;
          case 34:
              this.setLaenderschluessel(pvWert);
              break;
          case 35:
              this.setRegion(pvWert);
              break;
          case 36:
              this.setOrt(pvWert);
              break;
          case 37:
              this.setPostleitzahl(pvWert);
              break;
          case 38:
              this.setStrasse(pvWert);
              break;
          case 39:
              this.setHausnummer(pvWert);
              break;
          case 40:
              this.setErgaenzungHausnummer(pvWert);
              break;
          case 41:
              this.setGebaeude(pvWert);
              break;
          case 42:
              pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setSachwert(pvWert.trim());
              break;
          case 43:
              this.setSachwertWaehrung(pvWert);
              break;
          case 44:
              pvWert = pvWert.replace(".", "");
              pvWert = pvWert.replace(",", ".");
              this.setErtragswert(pvWert.trim());
              break;
          case 45:
              this.setErtragswertWaehrung(pvWert);
              break;
          default:
              System.out.println("Immobilie: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt einer Immobilie in einen String
     * @return
     */
    public String toString()
    {
        String lvOut = new String();

        lvOut = "Id: " + ivId + StringKonverter.lineSeparator;
        lvOut = lvOut + "Nominalwert: " + this.ivNominalwert + StringKonverter.lineSeparator;
        lvOut = lvOut + "Nominalwertwaehrung: " + this.ivNominalwertWaehrung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Beleihungswert: " + this.ivBeleihungswert + StringKonverter.lineSeparator;
        lvOut = lvOut + "Beleihungswertwaehrung: " + this.ivBeleihungswertWaehrung + StringKonverter.lineSeparator;
        lvOut = lvOut + "ObjektId: " + this.ivImmobilienobjektId + StringKonverter.lineSeparator;
        lvOut = lvOut + "ObjektartId: " + this.ivObjektartId + StringKonverter.lineSeparator;
        lvOut = lvOut + "AdressId: " + this.ivAdressId + StringKonverter.lineSeparator;
        lvOut = lvOut + "Beschreibung: " + this.ivBeschreibung + StringKonverter.lineSeparator;
        lvOut = lvOut + "ObjektteilId" + this.ivObjektteilId + StringKonverter.lineSeparator;
        lvOut = lvOut + "TeiliId: " + this.ivTeilId + StringKonverter.lineSeparator;
        lvOut = lvOut + "Nutzungsart: " + this.ivNutzungsart + StringKonverter.lineSeparator;
        lvOut = lvOut + "Nutzung: " + this.ivNutzung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Prozentanteil: " + this.ivProzentanteil + StringKonverter.lineSeparator;
        lvOut = lvOut + "GradBaufertigstellung: " + this.ivGradBaufertigstellung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Fertigstellungsdatum: " + this.ivFertigstellungsdatum + StringKonverter.lineSeparator;
        lvOut = lvOut + "Gesamtflaeche: " + this.ivGesamtflaeche + StringKonverter.lineSeparator;
        lvOut = lvOut + "Mengeneinheit: " + this.ivMengeneinheit + StringKonverter.lineSeparator;
        lvOut = lvOut + "Bemerkung: " + this.ivBemerkung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Deckungskennzeichen: " + this.ivDeckungskennzeichen + StringKonverter.lineSeparator;
        lvOut = lvOut + "ZeitstempelDeckungskennzeichen: " + this.ivZeitstempelDeckungskennzeichen + StringKonverter.lineSeparator;
        lvOut = lvOut + "Makrolage: " + this.ivMakrolage + StringKonverter.lineSeparator;
        lvOut = lvOut + "Mikrolage: " + this.ivMikrolage + StringKonverter.lineSeparator;
        lvOut = lvOut + "Qualitaet: " + this.ivQualitaet + StringKonverter.lineSeparator;
        lvOut = lvOut + "Miethoehe: " + this.ivMiethoehe + StringKonverter.lineSeparator;
        lvOut = lvOut + "Kennzeichen Hochwassergebiet: " + this.ivKennzeichenHochwassergebiet + StringKonverter.lineSeparator;
        lvOut = lvOut + "Kennzeichen Erdbebengebiet: " + this.ivKennzeichenErdbebengebiet + StringKonverter.lineSeparator;
        lvOut = lvOut + "Kennzeichen Denkmalschutzgebiet: " + this.ivKennzeichenDenkmalschutzgebiet + StringKonverter.lineSeparator;
        lvOut = lvOut + "Kennzeichen Historische Staette: " + this.ivKennzeichenHistorischeStaette + StringKonverter.lineSeparator;
        lvOut = lvOut + "Wertminderungsfaktoren: " + this.ivWertminderungsfaktoren + StringKonverter.lineSeparator;
        lvOut = lvOut + "Beschreibung Wertminderungsfaktoren: " + this.ivBeschreibungWertminderungsfaktoren + StringKonverter.lineSeparator;
        lvOut = lvOut + "Laenderschluessel: " + this.ivLaenderschluessel + StringKonverter.lineSeparator;
        lvOut = lvOut + "Region: " + this.ivRegion + StringKonverter.lineSeparator;
        lvOut = lvOut + "Ort: " + this.ivOrt + StringKonverter.lineSeparator;
        lvOut = lvOut + "Postleitzahl: " + this.ivPostleitzahl + StringKonverter.lineSeparator;
        lvOut = lvOut + "Strasse: " + this.ivStrasse + StringKonverter.lineSeparator;
        lvOut = lvOut + "Hausnummer: " + this.ivHausnummer + StringKonverter.lineSeparator;
        lvOut = lvOut + "Ergaenzung Hausnummer: " + this.ivErgaenzungHausnummer + StringKonverter.lineSeparator;
        lvOut = lvOut + "Gebaeude: " + this.ivGebaeude + StringKonverter.lineSeparator;
        lvOut = lvOut + "Sachwert: " + this.ivSachwert + StringKonverter.lineSeparator;
        lvOut = lvOut + "Sachwertwaehrung: " + this.ivSachwertWaehrung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Ertragswert: " + this.ivErtragswert + StringKonverter.lineSeparator;
        lvOut = lvOut + "Ertragswertwaehrung: " + this.ivErtragswertWaehrung + StringKonverter.lineSeparator;

        return lvOut;
    }    
}
