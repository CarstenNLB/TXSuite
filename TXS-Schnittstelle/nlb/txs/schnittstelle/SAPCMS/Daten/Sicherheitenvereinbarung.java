/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.SAPCMS.Daten;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class Sicherheitenvereinbarung 
{
    /**
     * Sicherheiten-ID
     */
    private String ivSicherheitenID;
    
    /**
     * Sicherheiten-Typ
     */
    private String ivSicherheitenTyp;
    
    /**
     * Sicherheitengeber
     */
    private String ivSicherheitengeber;
    
    /**
     * Partnerfunktion 
     */
    private String ivPartnerfunktion;
    
    /**
     * Gültig von
     */
    private String ivGueltigVon;
    
    /**
     * Gültig bis
     */
    private String ivGueltigBis;
    
    /**
     * Nominalwert
     */
    private String ivNominalwert;
    
    /**
     * Währung des Nominalwertes
     */
    private String ivNominalwertWaehrung;
    
    /**
     * Anzusetzender Wert
     */
    private String ivAnzusetzenderWert;
    
    /**
     * Währung des anzusetzenden Wertes
     */
    private String ivAnzusetzenderWertWaehrung;
    
    /**
     * Verfügungsbetrag
     */
    private String ivVerfuegungsbetrag;
    
    /**
     * Währung des Verfügungsbetrags
     */
    private String ivVerfuegungsbetragWaehrung;
    
    /**
     * Anzahl Portions
     */
    private String ivAnzahlPortions;
    
    /**
     * Gesamtgrundschuld
     */
    private String ivGesamtgrundschuld;
    
    /**
     * Grundschuldbrief
     */
    private String ivGrundschuldbrief;

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
     * @return the sicherheitenTyp
     */
    public String getSicherheitenTyp() {
        return this.ivSicherheitenTyp;
    }

    /**
     * @param pvSicherheitenTyp the sicherheitenTyp to set
     */
    public void setSicherheitenTyp(String pvSicherheitenTyp) {
        this.ivSicherheitenTyp = pvSicherheitenTyp;
    }

    /**
     * @return the sicherheitengeber
     */
    public String getSicherheitengeber() {
        return this.ivSicherheitengeber;
    }

    /**
     * @param pvSicherheitengeber the sicherheitengeber to set
     */
    public void setSicherheitengeber(String pvSicherheitengeber) {
        this.ivSicherheitengeber = pvSicherheitengeber;
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
     * @return the gueltigVon
     */
    public String getGueltigVon() {
        return this.ivGueltigVon;
    }

    /**
     * @param pvGueltigVon the gueltigVon to set
     */
    public void setGueltigVon(String pvGueltigVon) {
        this.ivGueltigVon = pvGueltigVon;
    }

    /**
     * @return the gueltigBis
     */
    public String getGueltigBis() {
        return this.ivGueltigBis;
    }

    /**
     * @param pvGueltigBis the gueltigBis to set
     */
    public void setGueltigBis(String pvGueltigBis) {
        this.ivGueltigBis = pvGueltigBis;
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
     * @return the anzusetzenderWert
     */
    public String getAnzusetzenderWert() {
        return this.ivAnzusetzenderWert;
    }

    /**
     * @param pvAnzusetzenderWert the anzusetzenderWert to set
     */
    public void setAnzusetzenderWert(String pvAnzusetzenderWert) 
    {
        this.ivAnzusetzenderWert = StringKonverter.convertWertfeldSAPCMS(pvAnzusetzenderWert, 2);
    }

    /**
     * @return the anzusetzenderWertWaehrung
     */
    public String getAnzusetzenderWertWaehrung() {
        return this.ivAnzusetzenderWertWaehrung;
    }

    /**
     * @param pvAnzusetzenderWertWaehrung the anzusetzenderWertWaehrung to set
     */
    public void setAnzusetzenderWertWaehrung(String pvAnzusetzenderWertWaehrung) {
        this.ivAnzusetzenderWertWaehrung = pvAnzusetzenderWertWaehrung;
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
    public void setVerfuegungsbetrag(String pvVerfuegungsbetrag) {
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
     * @return the anzahlPortions
     */
    public String getAnzahlPortions() {
        return this.ivAnzahlPortions;
    }

    /**
     * @param pvAnzahlPortions the anzahlPortions to set
     */
    public void setAnzahlPortions(String pvAnzahlPortions) {
        this.ivAnzahlPortions = pvAnzahlPortions;
    }

    /**
     * @return the gesamtgrundschuld
     */
    public String getGesamtgrundschuld() {
        return this.ivGesamtgrundschuld;
    }

    /**
     * @param pvGesamtgrundschuld the gesamtgrundschuld to set
     */
    public void setGesamtgrundschuld(String pvGesamtgrundschuld) {
        this.ivGesamtgrundschuld = pvGesamtgrundschuld;
    }

    /**
     * @return the grundschuldbrief
     */
    public String getGrundschuldbrief() {
        return this.ivGrundschuldbrief;
    }

    /**
     * @param pvGrundschuldbrief the grundschuldbrief to set
     */
    public void setGrundschuldbrief(String pvGrundschuldbrief) {
        this.ivGrundschuldbrief = pvGrundschuldbrief;
    }
    
    /**
     * Schreibt den Inhalt der Sicherheitenvereinbarung in einen String 
     * @return 
     */
    public String toString()
    {
        String lvOut = new String();
        
        lvOut = "Sicherheitenvereinbarung:" + StringKonverter.lineSeparator;
        lvOut = lvOut + "Sicherheiten-ID: " + ivSicherheitenID + StringKonverter.lineSeparator;
        lvOut = lvOut + "Sicherheiten-Typ: " + ivSicherheitenTyp + StringKonverter.lineSeparator;
        lvOut = lvOut + "Sicherheitengeber: " + ivSicherheitengeber + StringKonverter.lineSeparator;
        lvOut = lvOut +  "Partnerfunktion: " + ivPartnerfunktion + StringKonverter.lineSeparator;
        lvOut = lvOut + "Gültig von: " + ivGueltigVon + StringKonverter.lineSeparator;
        lvOut = lvOut + "Gültig bis: " + ivGueltigBis + StringKonverter.lineSeparator;
        lvOut = lvOut + "Nominalwert: " + ivNominalwert + StringKonverter.lineSeparator;
        lvOut = lvOut + "Währung des Nominalwertes: " + ivNominalwertWaehrung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Anzusetzender Wert: " + ivAnzusetzenderWert + StringKonverter.lineSeparator;
        lvOut = lvOut + "Währung des anzusetzenden Wertes: " + ivAnzusetzenderWertWaehrung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Verfügungsbetrag: " + ivVerfuegungsbetrag + StringKonverter.lineSeparator;
        lvOut = lvOut + "Währung des Verfügungsbetrags: " + ivVerfuegungsbetragWaehrung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Anzahl Portions: " + ivAnzahlPortions + StringKonverter.lineSeparator;
        lvOut = lvOut + "Gesamtgrundschuld: " + ivGesamtgrundschuld + StringKonverter.lineSeparator;
        lvOut = lvOut + "Grundschuldbrief: " + ivGrundschuldbrief + StringKonverter.lineSeparator;
        
        return lvOut;
    }
}
