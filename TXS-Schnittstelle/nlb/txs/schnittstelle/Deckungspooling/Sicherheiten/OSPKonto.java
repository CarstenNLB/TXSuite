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
public class OSPKonto 
{
    /**
     * OSPlus-Kontonummer
     */
    private String ivOspNr;
    
    /**
     * DarKa-Kontonummer
     */
    private String ivDarKaNr;
    
    /**
     * Zuweisungsbetrag
     */
    private String ivZuweisungsbetrag;
    
    /**
     * Deckungsstockbetrag
     */
    private String ivDeckungsstockbetrag;
    
    /**
     * Waehrung
     */
    private String ivWaehrung;
    
    /**
     * Konstruktor
     * @param pvOspNr 
     * @param pvDarKaNr 
     * @param pvBetrag 
     * @param pvDeckungsstockbetrag 
     * @param pvWaehrung 
     */
    public OSPKonto(String pvOspNr, String pvDarKaNr, String pvBetrag, String pvDeckungsstockbetrag, String pvWaehrung)
    {
        this.ivOspNr = pvOspNr;
        this.ivDarKaNr = pvDarKaNr;
        this.ivZuweisungsbetrag = pvBetrag;
        this.ivDeckungsstockbetrag = pvDeckungsstockbetrag;
        this.ivWaehrung = pvWaehrung;
    }

    /**
     * @return the ospNr
     */
    public String getOspNr() {
        return this.ivOspNr;
    }

    /**
     * @param pvOspNr the ospNr to set
     */
    public void setOspNr(String pvOspNr) {
        this.ivOspNr = pvOspNr;
    }

    /**
     * @return the darKaNr
     */
    public String getDarKaNr() {
        return this.ivDarKaNr;
    }

    /**
     * @param pvDarKaNr the darKaNr to set
     */
    public void setDarKaNr(String pvDarKaNr) {
        this.ivDarKaNr = pvDarKaNr;
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
    public void setZuweisungsbetrag(String pvZuweisungsbetrag) {
        this.ivZuweisungsbetrag = pvZuweisungsbetrag;
    }

    /**
     * @return the deckungsstockbetrag
     */
    public String getDeckungsstockbetrag() {
        return this.ivDeckungsstockbetrag;
    }

    /**
     * @param pvDeckungsstockbetrag the deckungsstockbetrag to set
     */
    public void setDeckungsstockbetrag(String pvDeckungsstockbetrag) {
        this.ivDeckungsstockbetrag = pvDeckungsstockbetrag;
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
     * @return 
     * 
     */
    public String toString()
    {
        String helpString;
        helpString = "Konto:\n";
        helpString = helpString + "OSPlus-Kontonummer: " + this.ivOspNr + "\n";
        helpString = helpString + "DarKa-Kontonummer: " + this.ivDarKaNr + "\n";
        helpString = helpString + "Zuweisungsbetrag: " + this.ivZuweisungsbetrag + "\n";
        helpString = helpString + "Deckunsstockbetrag: " + this.ivDeckungsstockbetrag + "\n";
        helpString = helpString + "Waehrung: " + this.ivWaehrung + "\n";
        
        return helpString;
    }
}
