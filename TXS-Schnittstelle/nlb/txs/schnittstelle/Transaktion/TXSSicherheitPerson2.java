/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

/**
 * @author tepperc
 *
 */
public class TXSSicherheitPerson2 implements TXSTransaktion
{
    /**
     * Kundennummer
     */
    private String ivKdnr;
    
    /**
     * Originator
     */
    private String ivOrg;
    
    /**
     * Quellsystem
     */
    private String ivQuelle;
    
    /**
     * Rolle
     */
    private String ivRolle;

    /**
     * Konstruktor
     */
    public TXSSicherheitPerson2()
    {
        initTXSSicherheitPerson2();
    }
    
    /**
      *
      */
    public void initTXSSicherheitPerson2() 
    {
        this.ivKdnr = new String();
        this.ivOrg = new String();
        this.ivQuelle = new String();
        this.ivRolle = new String();
    }

    /**
     * @return the kdnr
     */
    public String getKdnr() {
        return this.ivKdnr;
    }

    /**
     * @param pvKdnr the kdnr to set
     */
    public void setKdnr(String pvKdnr) {
        this.ivKdnr = pvKdnr;
    }

    /**
     * @return the org
     */
    public String getOrg() {
        return this.ivOrg;
    }

    /**
     * @param pvOrg the org to set
     */
    public void setOrg(String pvOrg) {
        this.ivOrg = pvOrg;
    }

    /**
     * @return the quelle
     */
    public String getQuelle() {
        return this.ivQuelle;
    }

    /**
     * @param pvQuelle the quelle to set
     */
    public void setQuelle(String pvQuelle) {
        this.ivQuelle = pvQuelle;
    }

    /**
     * @return the rolle
     */
    public String getRolle() {
        return this.ivRolle;
    }

    /**
     * @param pvRolle the rolle to set
     */
    public void setRolle(String pvRolle) {
        this.ivRolle = pvRolle;
    }

    /**
     * TXSSicherheitPerson2Start in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("    <txsi:shperson2 ");
    }
    
    /**
     * TXSSicherheitPerson2 in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        lvHelpXML.append("kdnr=\"" + this.ivKdnr
                        + "\" org=\"" + this.ivOrg 
                        + "\" quelle=\"" + this.ivQuelle
                        + "\" rolle=\"" + this.ivRolle + "\">\n");
        return lvHelpXML;
    }
    
    /**
     * TXSSicherheitPerson2Ende in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("    </txsi:shperson2>\n");
    }

}
