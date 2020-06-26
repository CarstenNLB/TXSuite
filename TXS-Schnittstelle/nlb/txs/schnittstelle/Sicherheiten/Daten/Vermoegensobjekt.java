/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten.Daten;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
@Deprecated
public class Vermoegensobjekt 
{
   /**
    * Sicherheiten-ID
    */
   private String ivSicherheitenID;
  
   /**
    * Objekt-ID
    */
   private String ivObjektID;
   
   /**
    * Objekt-Typ
    */
   private String ivObjektTyp;
   
   /**
    * Eigent�mer
    */
   private String ivEigentuemer;
   
   /**
    * Partnerfunktion
    */
   private String ivPartnerfunktion;
   
   /**
    * Nominalwert
    */
   private String ivNominalwert;
   
   /**
    * W�hrung des Nominalwertes
    */
   private String ivNominalwertWaehrung;
   
   /**
    * Beleihungswert
    */
   private String ivBeleihungswert;
   
   /**
    * W�hrung des Beleihungswert
    */
   private String ivBeleihungswertWaehrung;
   
   /**
    * Verf�gungsbetrag 
    */
   private String ivVerfuegungsbetrag;
   
   /**
    * W�hrung des Verf�gungsbetrag
    */
   private String ivVerfuegungsbetragWaehrung;
   
   /**
    * Strasse
    */
   private String ivStrasse;
   
   /**
    * Ort
    */
   private String ivOrt;
   
   /**
    * Postleitzahl
    */
   private String ivPostleitzahl;
   
   /**
    * Land
    */
   private String ivLand;
   
   /**
    * Gebiet
    */
   private String ivGebiet;
   
   /**
    * Hausnummer
    */
   private String ivHausnummer;
   
   /**
    * Nutzungsart
    */
   private String ivNutzungsart;
   
   /**
    * Ertragswert
    */
   private String ivErtragswert;
   
   /**
    * W�hrung des Ertragswert
    */
   private String ivErtragswertWaehrung;
   
   /**
    * Sachwert
    */
   private String ivSachwert;
   
   /**
    * W�hrung des Sachwerts
    */
   private String ivSachwertWaehrung;
   
   /**
    * Nutzung
    */
   private String ivNutzung;
   
   /**
    * Gewerbliche Nutzung
    */
   private String ivGewerblicheNutzung;
   
   /**
    * Fertigstellung %
    */
   private String ivFertigstellungProzent;
   
   /**
    * Fertigstellungsdatum
    */
   private String ivFertigstellungsdatum;
   
   /**
    * Gesamtfl�che
    */
   private String ivGesamtflaeche;
   
   /**
    * Deckungskennzeichen
    */
   private String ivDeckungskennzeichen;
   
   /**
    * Bemerkung
    * Bemerkung ausl�ndisches Sicherungsrecht nur, wenn COUNTRY <> 'DE'
    */
   private String ivBemerkung;

    /**
     * @return the sicherheitenID
     */
    public String getSicherheitenID() {
        return this.ivSicherheitenID;
    }
    
    /**
     * @param pvSicherheitenID the sicherheitenID to set
     */
    public void setSicherheitenID(String pvSicherheitenID) {
        this.ivSicherheitenID = pvSicherheitenID;
    }
    
    /**
     * @return the objektID
     */
    public String getObjektID() {
        return this.ivObjektID;
    }
    
    /**
     * @param pvObjektID the objektID to set
     */
    public void setObjektID(String pvObjektID) {
        this.ivObjektID = pvObjektID;
    }
    
    /**
     * @return the objektTyp
     */
    public String getObjektTyp() {
        return this.ivObjektTyp;
    }
    
    /**
     * @param pvObjektTyp the objektTyp to set
     */
    public void setObjektTyp(String pvObjektTyp) {
        this.ivObjektTyp = pvObjektTyp;
    }
    
    /**
     * @return the eigentuemer
     */
    public String getEigentuemer() {
        return this.ivEigentuemer;
    }
    
    /**
     * @param pvEigentuemer the eigentuemer to set
     */
    public void setEigentuemer(String pvEigentuemer) {
        this.ivEigentuemer = pvEigentuemer;
    }
    
    /**
     * @return the partnerfunktion
     */
    public String getPartnerfunktion() {
        return this.ivPartnerfunktion;
    }
    
    /**
     * @param pvPartnerfunktion the partnerfunktion to set
     */
    public void setPartnerfunktion(String pvPartnerfunktion) {
        this.ivPartnerfunktion = pvPartnerfunktion;
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
    public void setNominalwert(String pvNominalwert) 
    {
        this.ivNominalwert = StringKonverter.convertWertfeldSAPCMS(pvNominalwert, 2);
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
    public void setBeleihungswert(String pvBeleihungswert) 
    {
        this.ivBeleihungswert = StringKonverter.convertWertfeldSAPCMS(pvBeleihungswert, 2);
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
     * @return the verfuegungsbetrag
     */
    public String getVerfuegungsbetrag() {
        return this.ivVerfuegungsbetrag;
    }
    
    /**
     * @param pvVerfuegungsbetrag the verfuegungsbetrag to set
     */
    public void setVerfuegungsbetrag(String pvVerfuegungsbetrag) 
    {
        this.ivVerfuegungsbetrag = StringKonverter.convertWertfeldSAPCMS(pvVerfuegungsbetrag, 2);
    }
    
    /**
     * @return the verfuegungsbetragWaehrung
     */
    public String getVerfuegungsbetragWaehrung() {
        return this.ivVerfuegungsbetragWaehrung;
    }
    
    /**
     * @param pvVerfuegungsbetragWaehrung the verfuegungsbetragWaehrung to set
     */
    public void setVerfuegungsbetragWaehrung(String pvVerfuegungsbetragWaehrung) {
        this.ivVerfuegungsbetragWaehrung = pvVerfuegungsbetragWaehrung;
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
     * @return the land
     */
    public String getLand() {
        return this.ivLand;
    }
    
    /**
     * @param pvLand the land to set
     */
    public void setLand(String pvLand) {
        this.ivLand = pvLand;
    }
    
    /**
     * @return the gebiet
     */
    public String getGebiet() {
        return this.ivGebiet;
    }
    
    /**
     * @param pvGebiet the gebiet to set
     */
    public void setGebiet(String pvGebiet) {
        this.ivGebiet = pvGebiet;
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
     * @return the ertragswert
     */
    public String getErtragswert() {
        return this.ivErtragswert;
    }
    
    /**
     * @param pvErtragswert the ertragswert to set
     */
    public void setErtragswert(String pvErtragswert) 
    {
        this.ivErtragswert = StringKonverter.convertWertfeldSAPCMS(pvErtragswert, 2);
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
     * @return the sachwert
     */
    public String getSachwert() {
        return this.ivSachwert;
    }
    
    /**
     * @param pvSachwert the sachwert to set
     */
    public void setSachwert(String pvSachwert) 
    {
        this.ivSachwert = StringKonverter.convertWertfeldSAPCMS(pvSachwert, 2);
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
     * @return the gewerblicheNutzung
     */
    public String getGewerblicheNutzung() {
        return this.ivGewerblicheNutzung;
    }
    
    /**
     * @param pvGewerblicheNutzung the gewerblicheNutzung to set
     */
    public void setGewerblicheNutzung(String pvGewerblicheNutzung) 
    {
        this.ivGewerblicheNutzung = StringKonverter.convertWertfeldSAPCMS(pvGewerblicheNutzung, 6);
    }
    
    /**
     * @return the fertigstellungProzent
     */
    public String getFertigstellungProzent() {
        return this.ivFertigstellungProzent;
    }
    
    /**
     * @param pvFertigstellungProzent the fertigstellungProzent to set
     */
    public void setFertigstellungProzent(String pvFertigstellungProzent) 
    {
        this.ivFertigstellungProzent = StringKonverter.convertWertfeldSAPCMS(pvFertigstellungProzent, 6);
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
   * @return 
   * 
   */
  public String toString()
  {
      String lvOut = new String();
      
      lvOut = "Vermoegensobjekt:" + StringKonverter.lineSeparator;
      lvOut = lvOut + "Sicherheiten-ID: " + ivSicherheitenID + StringKonverter.lineSeparator;
      lvOut = lvOut + "Objekt-ID: " + ivObjektID + StringKonverter.lineSeparator;
      lvOut = lvOut + "Objekt-Typ: " + ivObjektTyp + StringKonverter.lineSeparator;
      lvOut = lvOut + "Eigent�mer: " + ivEigentuemer + StringKonverter.lineSeparator;
      lvOut = lvOut + "Partnerfunktion: " + ivPartnerfunktion + StringKonverter.lineSeparator;
      lvOut = lvOut + "Nominalwert: " + ivNominalwert + StringKonverter.lineSeparator;
      lvOut = lvOut + "W�hrung des Nominalwertes" + ivNominalwertWaehrung + StringKonverter.lineSeparator;
      lvOut = lvOut + "Beleihungswert: " + ivBeleihungswert + StringKonverter.lineSeparator;
      lvOut = lvOut + "W�hrung des Beleihungswert: " + ivBeleihungswertWaehrung + StringKonverter.lineSeparator;
      lvOut = lvOut + "Verf�gungsbetrag: " + ivVerfuegungsbetrag + StringKonverter.lineSeparator;
      lvOut = lvOut + "W�hrung des Verf�gungsbetrag: " + ivVerfuegungsbetragWaehrung + StringKonverter.lineSeparator;
      lvOut = lvOut + "Strasse: " + ivStrasse + StringKonverter.lineSeparator;
      lvOut = lvOut + "Ort: " + ivOrt + StringKonverter.lineSeparator;
      lvOut = lvOut + "Postleitzahl: " + ivPostleitzahl + StringKonverter.lineSeparator;
      lvOut = lvOut + "Land: " + ivLand + StringKonverter.lineSeparator;
      lvOut = lvOut + "Gebiet: " + ivGebiet + StringKonverter.lineSeparator;
      lvOut = lvOut + "Hausnummer: " + ivHausnummer + StringKonverter.lineSeparator;
      lvOut = lvOut + "Nutzungsart: " + ivNutzungsart + StringKonverter.lineSeparator;
      lvOut = lvOut + "Ertragswert: " + ivErtragswert + StringKonverter.lineSeparator;
      lvOut = lvOut + "W�hrung des Ertragswert: " + ivErtragswertWaehrung + StringKonverter.lineSeparator;
      lvOut = lvOut + "Sachwert: " + ivSachwert + StringKonverter.lineSeparator;
      lvOut = lvOut + "W�hrung des Sachwerts: " + ivSachwertWaehrung + StringKonverter.lineSeparator;
      lvOut = lvOut + "Nutzung: " + ivNutzung + StringKonverter.lineSeparator;
      lvOut = lvOut + "Gewerbliche Nutzung: " + ivGewerblicheNutzung + StringKonverter.lineSeparator;
      lvOut = lvOut + "Fertigstellung %: " + ivFertigstellungProzent + StringKonverter.lineSeparator;
      lvOut = lvOut + "Fertigstellungsdatum: " + ivFertigstellungsdatum + StringKonverter.lineSeparator;
      lvOut = lvOut + "Gesamtfl�che: " + ivGesamtflaeche + StringKonverter.lineSeparator;
      lvOut = lvOut + "Deckungskennzeichen: " + ivDeckungskennzeichen + StringKonverter.lineSeparator;
      lvOut = lvOut + "Bemerkung: " + ivBemerkung + StringKonverter.lineSeparator;
      
      return lvOut;
  }
}
