/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.Sicherheiten;

/**
 * @author tepperc
 *
 */
public class OSPGrundbuch 
{
    /**
     * Grundbuch-Nr
     */
    private String ivNr;
    
    /**
     * Grundbuchamt
     */
    private String ivAmt;
    
    /**
     * Grundbuchbezirk
     */
    private String ivBezirk;
    
    /**
     * Grundbuchband 
     */
    private String ivBand;
    
    /**
     * Grundbuchblatt
     */
    private String ivBlatt;
    
    /**
     * Grundbuchheft
     */
    private String ivHeft;
    
    /**
     * Grundbuchart
     */
    private String ivArt;

    /**
     * Abteilung des Grundbuches
     */
    private String ivAbteilung;
    
    /**
     * Laufende Nummer in der Abteilung
     */
    private String ivAbteilungNr;
    
    /**
     * Objekt-ID
     */
    private String ivObjID;
    
    /**
     * SirID
     */
    private String ivSirID;
    
    /**
     * @param pvNr 
     * 
     */
    public OSPGrundbuch(String pvNr) 
    {
        this.ivNr = pvNr;
        this.ivAmt = new String();
        this.ivBezirk = new String();
        this.ivBand = new String();
        this.ivBlatt = new String();
        this.ivHeft = new String();
        this.ivArt = new String();
        this.ivAbteilung = new String();
        this.ivAbteilungNr = new String();
        this.ivObjID = new String();
        this.ivSirID = new String();
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
     * @return the amt
     */
    public String getAmt() {
        return this.ivAmt;
    }

    /**
     * @param pvAmt the amt to set
     */
    public void setAmt(String pvAmt) {
        this.ivAmt = pvAmt;
    }

    /**
     * @return the bezirk
     */
    public String getBezirk() {
        return this.ivBezirk;
    }

    /**
     * @param pvBezirk the bezirk to set
     */
    public void setBezirk(String pvBezirk) {
        this.ivBezirk = pvBezirk;
    }

    /**
     * @return the band
     */
    public String getBand() {
        return this.ivBand;
    }

    /**
     * @param pvBand the band to set
     */
    public void setBand(String pvBand) {
        this.ivBand = pvBand;
    }

    /**
     * @return the blatt
     */
    public String getBlatt() {
        return this.ivBlatt;
    }

    /**
     * @param pvBlatt the blatt to set
     */
    public void setBlatt(String pvBlatt) {
        this.ivBlatt = pvBlatt;
    }

    /**
     * @return the heft
     */
    public String getHeft() {
        return this.ivHeft;
    }

    /**
     * @param pvHeft the heft to set
     */
    public void setHeft(String pvHeft) {
        this.ivHeft = pvHeft;
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
     * @return the abteilung
     */
    public String getAbteilung() {
        return this.ivAbteilung;
    }

    /**
     * @param pvAbteilung the abteilung to set
     */
    public void setAbteilung(String pvAbteilung) {
        this.ivAbteilung = pvAbteilung;
    }

    /**
     * @return the abteilungNr
     */
    public String getAbteilungNr() {
        return this.ivAbteilungNr;
    }

    /**
     * @param pvAbteilungNr the abteilungNr to set
     */
    public void setAbteilungNr(String pvAbteilungNr) {
        this.ivAbteilungNr = pvAbteilungNr;
    }

    /**
     * @return the objID
     */
    public String getObjID() {
        return this.ivObjID;
    }

    /**
     * @param pvObjID the objID to set
     */
    public void setObjID(String pvObjID) {
        this.ivObjID = pvObjID;
    }

    /**
     * @return the sirID
     */
    public String getSirID() {
        return this.ivSirID;
    }

    /**
     * @param pvSirID the sirID to set
     */
    public void setSirID(String pvSirID) {
        this.ivSirID = pvSirID;
    }

    /**
     * @return 
     * 
     */
    public String toString()
    {
        String helpString;
        helpString = "Grundbuch:\n";
        helpString = helpString + "Grundbuchnummer: " + this.ivNr + "\n";
        helpString = helpString + "Grundbuchamt: " + this.ivAmt + "\n";
        helpString = helpString + "Grundbuchbezirk: " + this.ivBezirk + "\n";
        helpString = helpString + "Grundbuchband: " + this.ivBand + "\n";
        helpString = helpString + "Grundbuchblatt: " + this.ivBlatt + "\n";
        helpString = helpString + "Grundbuchheft: " + this.ivHeft + "\n";
        helpString = helpString + "Grundbuchart: " + this.ivArt + "\n";
        helpString = helpString + "Abteilung: " + this.ivAbteilung + "\n";
        helpString = helpString + "AbteilungNr: " + this.ivAbteilungNr + "\n";
        helpString = helpString + "ObjektID: " + this.ivObjID + "\n";
        helpString = helpString + "SirID: " + this.ivSirID + "\n";
         
        return helpString;
    }
}
