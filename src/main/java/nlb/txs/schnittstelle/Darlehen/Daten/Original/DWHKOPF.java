/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Daten.Original;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class DWHKOPF 
{
    /**
     * Kundennummer
     */
    private String ivDwhkdnr;     
    
    /**
     * Objektnummer 
     */
    private String ivDwhonr;
    
    /**
     * Kontonummer
     */                             
    private String ivDwhknr;   
        
    /**
     * Gruppe
     */                               
    private String ivDwhtdb;
    
    /**
     * Erweiterte Art der Daten
     */
    private String ivDwheart;
    
    /**
     * Art der Daten
     */
    private String ivDwhart;
 
    /**
     * Typ der Daten
     */
    private String ivDwhtyp;
                                  
    /**
     * Reserve-UFeld1
     */
    private String ivDwhres10;
    
    /**
     * Reserve-Ufeld2
     */
    private String ivDwhres11;

    /**
     * Reserve-Ufeld3
     */
    private String ivDwhres12;
    
    /**
     * Konstruktor
     */
    public DWHKOPF() 
    {
        this.ivDwhkdnr = new String();
        this.ivDwhonr = new String();
        this.ivDwhknr = new String();
        this.ivDwhtdb = new String();
        this.ivDwheart = new String();
        this.ivDwhart = new String();
        this.ivDwhtyp = new String();
        this.ivDwhres10 = new String();
        this.ivDwhres11 = new String();
        this.ivDwhres12 = new String();
    }

    /**
     * @return the sDwhkdnr
     */
    public String getsDwhkdnr() {
        return this.ivDwhkdnr;
    }

    /**
     * @param pvSDwhkdnr the sDwhkdnr to set
     */
    public void setsDwhkdnr(String pvSDwhkdnr) {
        this.ivDwhkdnr = pvSDwhkdnr;
    }

    /**
     * @return the sDwhonr
     */
    public String getsDwhonr() {
        return this.ivDwhonr;
    }

    /**
     * @param pvSDwhonr the sDwhonr to set
     */
    public void setsDwhonr(String pvSDwhonr) {
        this.ivDwhonr = pvSDwhonr;
    }

    /**
     * @return the sDwhknr
     */
    public String getsDwhknr() {
        return this.ivDwhknr;
    }

    /**
     * @param pvSDwhknr the sDwhknr to set
     */
    public void setsDwhknr(String pvSDwhknr) {
        this.ivDwhknr = pvSDwhknr;
    }

    /**
     * @return the sDwhtdb
     */
    public String getsDwhtdb() {
        return this.ivDwhtdb;
    }

    /**
     * @param pvSDwhtdb the sDwhtdb to set
     */
    public void setsDwhtdb(String pvSDwhtdb) {
        this.ivDwhtdb = pvSDwhtdb;
    }

    /**
     * @return the sDwheart
     */
    public String getsDwheart() {
        return this.ivDwheart;
    }

    /**
     * @param pvSDwheart the sDwheart to set
     */
    public void setsDwheart(String pvSDwheart) {
        this.ivDwheart = pvSDwheart;
    }

    /**
     * @return the sDwhart
     */
    public String getsDwhart() {
        return this.ivDwhart;
    }

    /**
     * @param pvSDwhart the sDwhart to set
     */
    public void setsDwhart(String pvSDwhart) {
        this.ivDwhart = pvSDwhart;
    }

    /**
     * @return the sDwhtyp
     */
    public String getsDwhtyp() {
        return this.ivDwhtyp;
    }

    /**
     * @param pvSDwhtyp the sDwhtyp to set
     */
    public void setsDwhtyp(String pvSDwhtyp) {
        this.ivDwhtyp = pvSDwhtyp;
    }

    /**
     * @return the sDwhres10
     */
    public String getsDwhres10() {
        return this.ivDwhres10;
    }

    /**
     * @param pvSDwhres10 the sDwhres10 to set
     */
    public void setsDwhres10(String pvSDwhres10) {
        this.ivDwhres10 = pvSDwhres10;
    }

    /**
     * @return the sDwhres11
     */
    public String getsDwhres11() {
        return this.ivDwhres11;
    }

    /**
     * @param pvSDwhres11 the sDwhres11 to set
     */
    public void setsDwhres11(String pvSDwhres11) {
        this.ivDwhres11 = pvSDwhres11;
    }

    /**
     * @return the sDwhres12
     */
    public String getsDwhres12() {
        return this.ivDwhres12;
    }

    /**
     * @param pvSDwhres12 the sDwhres12 to set
     */
    public void setsDwhres12(String pvSDwhres12) {
        this.ivDwhres12 = pvSDwhres12;
    }

    /**
     * @return 
     * 
     */
    public String toString()
    {
        String lvHelpString;
        
        lvHelpString = "Segment - Kopf" + StringKonverter.lineSeparator;
        lvHelpString = lvHelpString + "Kundennummer: " + this.ivDwhkdnr + StringKonverter.lineSeparator; 
        lvHelpString = lvHelpString + "Objektnummer: " + this.ivDwhonr + StringKonverter.lineSeparator;
        lvHelpString = lvHelpString + "Kontonummer: " + this.ivDwhknr + StringKonverter.lineSeparator;   
        lvHelpString = lvHelpString + "Gruppe: " + this.ivDwhtdb + StringKonverter.lineSeparator;
        lvHelpString = lvHelpString + "Erweiterte Art der Daten: " + this.ivDwheart + StringKonverter.lineSeparator;
        lvHelpString = lvHelpString + "Art der Daten: " + this.ivDwhart + StringKonverter.lineSeparator;
        lvHelpString = lvHelpString + "Typ der Daten: " + this.ivDwhtyp + StringKonverter.lineSeparator;
        lvHelpString = lvHelpString + "Reserve-UFeld1: " + this.ivDwhres10 + StringKonverter.lineSeparator;
        lvHelpString = lvHelpString + "Reserve-Ufeld2: " + this.ivDwhres11 + StringKonverter.lineSeparator;
        lvHelpString = lvHelpString + "Reserve-Ufeld3: " + this.ivDwhres12 + StringKonverter.lineSeparator;
 
        return lvHelpString;
    }
}
