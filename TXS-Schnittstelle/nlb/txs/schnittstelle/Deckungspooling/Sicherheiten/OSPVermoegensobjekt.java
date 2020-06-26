/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.Sicherheiten;

import java.util.LinkedList;

/**
 * @author tepperc
 *
 */
@Deprecated
public class OSPVermoegensobjekt 
{
    /**
     * Vermoegensobjekt-Nr
     */
    private String ivNr;
    
    /**
     * Liste der Grundb�cher
     */
    private LinkedList<String> ivGrundbuecher;
    
    /**
     * Verwendungsart
     */
    private String ivVerwendungsart;
    
    /**
     * Beleihungswert
     */
    private String ivBeleihungswert;
    
    /**
     * Nominalwert
     */
    private String ivNominalwert;
    
    /**
     * Waehrung
     */
    private String ivWaehrung;
    
    /**
     * Strasse
     */
    private String ivStrasse;
    
    /**
     * Postleitzahl
     */
    private String ivPlz;
    
    /**
     * Ort
     */
    private String ivOrt;
    
    /**
     * Land
     */
    private String ivLand;
    
    /**
     * Art
     */
    private String ivArt;
    
    /**
     * Nutzungsart
     */
    private String ivNutzungsart;
    
    /**
     * Bodenwert
     */
    private String ivBodenwert;
    
    /**
     * Bauwert
     */
    private String ivBauwert;
    
    /**
     * Ertragswert
     */
    private String ivErtragswert;
    
    /**
     * Baujahr
     */
    private String ivBaujahr;
    
    /**
     * Konstruktor
     * @param pvNr 
     */
    public OSPVermoegensobjekt(String pvNr)
    {
        this.ivNr = pvNr;
        this.ivGrundbuecher = new LinkedList<String>();
        this.ivVerwendungsart = new String();
        this.ivBeleihungswert = new String();
        this.ivNominalwert = new String();
        this.ivWaehrung = new String();
        this.ivStrasse = new String(); 
        this.ivPlz = new String(); 
        this.ivOrt = new String(); 
        this.ivLand = new String(); 
        this.ivArt = new String(); 
        this.ivNutzungsart = new String(); 
        this.ivBodenwert = new String(); 
        this.ivBauwert = new String();
        this.ivErtragswert = new String();
        this.ivBaujahr = new String();
    }

    /**
     * @return the nr
     */
    public String getNr() {
        return this.ivNr;
    }

    /**
     * @param pvNr the nr to set
     */
    public void setNr(String pvNr) {
        this.ivNr = pvNr;
    }

    /**
     * @return the grundbuecher
     */
    public LinkedList<String> getGrundbuecher() {
        return this.ivGrundbuecher;
    }

    /**
     * @param pvGrundbuecher the grundbuecher to set
     */
    public void setGrundbuecher(LinkedList<String> pvGrundbuecher) {
        this.ivGrundbuecher = pvGrundbuecher;
    }
    
    /**
     * @param pvNr 
     * 
     */
    public void addGrundbuch(String pvNr)
    {
        this.ivGrundbuecher.add(pvNr);
    }

    /**
     * @return the verwendungsart
     */
    public String getVerwendungsart() {
        return this.ivVerwendungsart;
    }

    /**
     * @param pvVerwendungsart the verwendungsart to set
     */
    public void setVerwendungsart(String pvVerwendungsart) {
        this.ivVerwendungsart = pvVerwendungsart;
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
     * @return the waehrung
     */
    public String getWaehrung() {
        return this.ivWaehrung;
    }

    /**
     * @param pvWaehrung the waehrung to set
     */
    public void setWaehrung(String pvWaehrung) {
        this.ivWaehrung = pvWaehrung;
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
     * @return the plz
     */
    public String getPlz() {
        return this.ivPlz;
    }

    /**
     * @param pvPlz the plz to set
     */
    public void setPlz(String pvPlz) {
        this.ivPlz = pvPlz;
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
     * @return the art
     */
    public String getArt() {
        return this.ivArt;
    }

    /**
     * @param pvArt the art to set
     */
    public void setArt(String pvArt) {
        this.ivArt = pvArt;
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
     * @return the bodenwert
     */
    public String getBodenwert() {
        return this.ivBodenwert;
    }

    /**
     * @param pvBodenwert the bodenwert to set
     */
    public void setBodenwert(String pvBodenwert) {
        this.ivBodenwert = pvBodenwert;
    }

    /**
     * @return the bauwert
     */
    public String getBauwert() {
        return this.ivBauwert;
    }

    /**
     * @param pvBauwert the bauwert to set
     */
    public void setBauwert(String pvBauwert) {
        this.ivBauwert = pvBauwert;
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
     * @return the baujahr
     */
    public String getBaujahr() {
        return this.ivBaujahr;
    }

    /**
     * @param pvBaujahr the baujahr to set
     */
    public void setBaujahr(String pvBaujahr) {
        this.ivBaujahr = pvBaujahr;
    }

    /**
     * @return 
     * 
     */
    public String toString()
    {
        String helpString;
        helpString = "Vermoegensobjekt:\n";
        helpString = helpString + "Objektnummer: " + ivNr + "\n";
        helpString = helpString + "Grundbuecher:\n";
        for (int x = 0; x < ivGrundbuecher.size(); x++)
        {
           helpString = helpString + ivGrundbuecher.get(x) + "\n";
        }
        helpString = helpString + "Verwendungsart: " + this.ivVerwendungsart+ "\n";
        helpString = helpString + "Beleihungswert: " + this.ivBeleihungswert + "\n";
        helpString = helpString + "Nominmalwert: " + this.ivNominalwert + "\n";
        helpString = helpString + "Waehrung: " + this.ivWaehrung + "\n";
        helpString = helpString + "Strasse: " + this.ivStrasse + "\n";
        helpString = helpString + "Postleitzahl: " + this.ivPlz + "\n";
        helpString = helpString + "Ort: " + this.ivOrt + "\n";
        helpString = helpString + "Land: " + this.ivLand + "\n";
        helpString = helpString + "Art: " + this.ivArt + "\n";
        helpString = helpString + "Nutzungsart: " + this.ivNutzungsart + "\n";
        helpString = helpString + "Bodenwert: " + this.ivBodenwert + "\n";
        helpString = helpString + "Bauwert: " + this.ivBauwert + "\n";
        helpString = helpString + "Ertragswert: " + this.ivErtragswert + "\n";
        helpString = helpString + "Baujahr: " + this.ivBaujahr + "\n";
        
        return helpString;
    }
}
