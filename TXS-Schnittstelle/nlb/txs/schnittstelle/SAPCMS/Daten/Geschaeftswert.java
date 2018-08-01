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
public class Geschaeftswert 
{
   /**
    * Sicherheiten-ID
    */
    private String ivSicherheitenID;
    
    /**
     * Geschäftswert-ID
     */
    private String ivGeschaeftswertID;
    
    /**
     * Teil-ID
     */
    private String ivTeilID;
    
    /**
     * Kreditsystem
     */
    private String ivKreditsystem;
    
    /**
     * Kreditnehmer
     */
    private String ivKreditnehmer;
    
    /**
     * Partnerfunktion
     */
    private String ivPartnerfunktion;
    
    /**
     * Zuweisungsbetrag
     */
    private String ivZuweisungsbetrag;
    
    /**
     * Währung des Zuweisungsbetrag
     */
    private String ivZuweisungsbetragWaehrung;
    
    /**
     * Einschränkung für Teil
     */
    private String ivEinschraenkungTeil;
    
    /**
     * Teilwert %
     */
    private String ivTeilwertProzent;
    
    /**
     * Teilwert
     */
    private String ivTeilwert;
    
    /**
     * Währung des Teilwertes
     */
    private String ivTeilwertWaehrung;

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
     * @return the geschaeftswertID
     */
    public String getGeschaeftswertID() {
        return this.ivGeschaeftswertID;
    }

    /**
     * @param pvGeschaeftswertID the geschaeftswertID to set
     */
    public void setGeschaeftswertID(String pvGeschaeftswertID) {
        this.ivGeschaeftswertID = pvGeschaeftswertID;
    }

    /**
     * @return the teilID
     */
    public String getTeilID() {
        return this.ivTeilID;
    }

    /**
     * @param pvTeilID the teilID to set
     */
    public void setTeilID(String pvTeilID) {
        this.ivTeilID = pvTeilID;
    }

    /**
     * @return the kreditsystem
     */
    public String getKreditsystem() {
        return this.ivKreditsystem;
    }

    /**
     * @param pvKreditsystem the kreditsystem to set
     */
    public void setKreditsystem(String pvKreditsystem) {
        this.ivKreditsystem = pvKreditsystem;
    }

    /**
     * @return the kreditnehmer
     */
    public String getKreditnehmer() {
        return this.ivKreditnehmer;
    }

    /**
     * @param pvKreditnehmer the kreditnehmer to set
     */
    public void setKreditnehmer(String pvKreditnehmer) {
        this.ivKreditnehmer = pvKreditnehmer;
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
     * @return the zuweisungsbetrag
     */
    public String getZuweisungsbetrag() {
        return this.ivZuweisungsbetrag;
    }

    /**
     * @param pvZuweisungsbetrag the zuweisungsbetrag to set
     */
    public void setZuweisungsbetrag(String pvZuweisungsbetrag) 
    {
        this.ivZuweisungsbetrag = StringKonverter.convertWertfeldSAPCMS(pvZuweisungsbetrag, 2);
    }

    /**
     * @return the zuweisungsbetragWaehrung
     */
    public String getZuweisungsbetragWaehrung() {
        return this.ivZuweisungsbetragWaehrung;
    }

    /**
     * @param pvZuweisungsbetragWaehrung the zuweisungsbetragWaehrung to set
     */
    public void setZuweisungsbetragWaehrung(String pvZuweisungsbetragWaehrung) {
        this.ivZuweisungsbetragWaehrung = pvZuweisungsbetragWaehrung;
    }

    /**
     * @return the einschraenkungTeil
     */
    public String getEinschraenkungTeil() {
        return this.ivEinschraenkungTeil;
    }

    /**
     * @param pvEinschraenkungTeil the einschraenkungTeil to set
     */
    public void setEinschraenkungTeil(String pvEinschraenkungTeil) {
        this.ivEinschraenkungTeil = pvEinschraenkungTeil;
    }

    /**
     * @return the teilwertProzent
     */
    public String getTeilwertProzent() {
        return this.ivTeilwertProzent;
    }

    /**
     * @param pvTeilwertProzent the teilwertProzent to set
     */
    public void setTeilwertProzent(String pvTeilwertProzent) 
    {
        this.ivTeilwertProzent = StringKonverter.convertWertfeldSAPCMS(pvTeilwertProzent, 6);
    }

    /**
     * @return the teilwert
     */
    public String getTeilwert() 
    {
        return this.ivTeilwert;
    }

    /**
     * @param pvTeilwert the teilwert to set
     */
    public void setTeilwert(String pvTeilwert) 
    {
        this.ivTeilwert = StringKonverter.convertWertfeldSAPCMS(pvTeilwert, 2);
    }

    /**
     * @return the teilwertWaehrung
     */
    public String getTeilwertWaehrung() {
        return this.ivTeilwertWaehrung;
    }

    /**
     * @param pvTeilwertWaehrung the teilwertWaehrung to set
     */
    public void setTeilwertWaehrung(String pvTeilwertWaehrung) {
        this.ivTeilwertWaehrung = pvTeilwertWaehrung;
    } 
    
    /**
     * Schreibt den Inhalt in einen String
     * @return 
     */
    public String toString()
    {
        String lvOut = new String();
        lvOut = "Geschaeftswert:" + StringKonverter.lineSeparator;
        lvOut = lvOut + "Sicherheiten-ID: " + ivSicherheitenID + StringKonverter.lineSeparator;
        lvOut = lvOut + "Geschäftswert-ID: " + ivGeschaeftswertID + StringKonverter.lineSeparator;
        lvOut = lvOut + "Teil-ID: " + ivTeilID + StringKonverter.lineSeparator;
        lvOut = lvOut + "Kreditsystem: " + ivKreditsystem + StringKonverter.lineSeparator;
        lvOut = lvOut + "Kreditnehmer: " + ivKreditnehmer + StringKonverter.lineSeparator;
        lvOut = lvOut + "Partnerfunktion: " + ivPartnerfunktion + StringKonverter.lineSeparator;
        lvOut = lvOut + "Zuweisungsbetrag: " + ivZuweisungsbetrag + StringKonverter.lineSeparator;
        lvOut = lvOut + "Währung des Zuweisungsbetrag: " + ivZuweisungsbetragWaehrung + StringKonverter.lineSeparator;
        lvOut = lvOut + "Einschränkung für Teil: " + ivEinschraenkungTeil + StringKonverter.lineSeparator;
        lvOut = lvOut + "Teilwert %: " + ivTeilwertProzent + StringKonverter.lineSeparator;
        lvOut = lvOut + "Teilwert: " + ivTeilwert + StringKonverter.lineSeparator;
        lvOut = lvOut + "Währung des Teilwerts: " + ivTeilwertWaehrung + StringKonverter.lineSeparator;

        return lvOut;
    }
}
