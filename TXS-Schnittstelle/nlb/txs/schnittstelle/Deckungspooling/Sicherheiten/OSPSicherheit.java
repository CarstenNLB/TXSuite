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
public class OSPSicherheit 
{
   /**
    * Sicherheiten-Nr
    */
    private String ivNr;
    
    /**
     * Liste der Vermoegensobjekte
     */
    private LinkedList<String> ivVermoegensobjekte;
    
    /**
     * Liste der Konten
     */
    private LinkedList<OSPKonto> ivKonten;
    
    /**
     * Rang
     */
    private String ivRang;
    
    /**
     * Nominalwert
     */
    private String ivNominalwert;
    
    /**
     * Waehrung
     */
    private String ivWaehrung;
    
    /**
     * Sicherheitenart
     */
    private String ivSicherheitenart;
    
    /**
     * Sicherheitenart Elementar
     */
    private String ivSicherheitenartElementar;
    
    /**
     * Sicherungsrechtart
     */
    private String ivSicherungsrechtart;
    
    /**
     * 
     */
    private String ivBelastungsforderungsbetrag;
    
    /**
     * 
     */
    private String ivVerfuegungsbetrag;
    
    /**
     * Konstruktor
     * @param pvNr 
     */
    public OSPSicherheit(String pvNr)
    {
        this.ivNr = pvNr;
        this.ivVermoegensobjekte = new LinkedList<String>();
        this.ivKonten = new LinkedList<OSPKonto>();
        this.ivRang = new String();
        this.ivNominalwert = new String();
        this.ivWaehrung = new String();
        this.ivSicherheitenart = new String();
        this.ivSicherheitenartElementar = new String();
        this.ivSicherungsrechtart = new String();
        this.ivBelastungsforderungsbetrag = new String();
        this.ivVerfuegungsbetrag = new String();
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
     * @return the vermoegensobjekte
     */
    public LinkedList<String> getVermoegensobjekte() {
        return this.ivVermoegensobjekte;
    }

    /**
     * @param pvVermoegensobjekte the vermoegensobjekte to set
     */
    public void setVermoegensobjekte(LinkedList<String> pvVermoegensobjekte) {
        this.ivVermoegensobjekte = pvVermoegensobjekte;
    }

    /**
     * @return the konten
     */
    public LinkedList<OSPKonto> getKonten() {
        return this.ivKonten;
    }

    /**
     * @param pvKonten the konten to set
     */
    public void setKonten(LinkedList<OSPKonto> pvKonten) {
        this.ivKonten = pvKonten;
    }

    /**
     * @return the rang
     */
    public String getRang() {
        return this.ivRang;
    }

    /**
     * @param pvRang the rang to set
     */
    public void setRang(String pvRang) {
        this.ivRang = pvRang;
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
     * 
     * @return die Sicherheitenart
     */
    public String getSicherheitenart() {
        return this.ivSicherheitenart;
    }

    /**
     * 
     * @param art die zu setzende Sicherheitenart
     */
    public void setSicherheitenart(String art) 
    {
        this.ivSicherheitenart = art;
    }

    /**
     * @return the sicherheitenartElementar
     */
    public String getSicherheitenartElementar() {
        return this.ivSicherheitenartElementar;
    }

    /**
     * @param pvSicherheitenartElementar the sicherheitenartElementar to set
     */
    public void setSicherheitenartElementar(String pvSicherheitenartElementar) {
        this.ivSicherheitenartElementar = pvSicherheitenartElementar;
    }

    /**
     * @return the sicherungsrechtart
     */
    public String getSicherungsrechtart() {
        return this.ivSicherungsrechtart;
    }

    /**
     * @param pvSicherungsrechtart the sicherungsrechtart to set
     */
    public void setSicherungsrechtart(String pvSicherungsrechtart) {
        this.ivSicherungsrechtart = pvSicherungsrechtart;
    }

    /**
     * @return the belastungsforderungsbetrag
     */
    public String getBelastungsforderungsbetrag() {
        return this.ivBelastungsforderungsbetrag;
    }

    /**
     * @param pvBelastungsforderungsbetrag the belastungsforderungsbetrag to set
     */
    public void setBelastungsforderungsbetrag(String pvBelastungsforderungsbetrag) {
        this.ivBelastungsforderungsbetrag = pvBelastungsforderungsbetrag;
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
        this.ivVerfuegungsbetrag = pvVerfuegungsbetrag;
    }

    /**
     * @return 
     * 
     */
    public String toString()
    {
        String helpString;
        helpString = "Sicherheit:\n";
        helpString = helpString + "Sicherheitennummer: " + ivNr + "\n";
        helpString = helpString + "Vermoegensobjekte:\n";
        for (int v = 0; v < ivVermoegensobjekte.size(); v++)
        {
           helpString = helpString + ivVermoegensobjekte.get(v) + "\n";
        }
        helpString = helpString + "Konten:\n";
        for (int k = 0; k < ivKonten.size(); k++)
        {
           helpString = helpString + ivKonten.get(k).toString() + "\n";
        }
        helpString = helpString + "Rang: " + this.ivRang + "\n";
        helpString = helpString + "Nominalwert: " + this.ivNominalwert + "\n";
        helpString = helpString + "Waehrung: " + this.ivWaehrung + "\n";
        helpString = helpString + "Sicherheitenart: " + this.ivSicherheitenart + "\n";
        helpString = helpString + "Sicherheitenart Elementar: " + this.ivSicherheitenartElementar + "\n";
        helpString = helpString + "Sicherungsrechtart: " + this.ivSicherungsrechtart + "\n";
        helpString = helpString + "Belastungsforderungsbetrag: " + this.ivBelastungsforderungsbetrag + "\n";
        helpString = helpString + "Verfuegungsbetrag: " + this.ivVerfuegungsbetrag + "\n";
        
        return helpString;
    }
}
