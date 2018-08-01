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
public class TXSSicherheit implements TXSTransaktion 
{
    /**
     * Key
     */
    private String ivKey;
    
    /**
     * Originator
     */
    private String ivOrg;
    
    /**
     * Quellsystem
     */
    private String ivQuelle;

    /**
     * Konstruktor - Initialisiert die Variablen mit leeren Strings
     */
    public TXSSicherheit() 
    {
        this.ivKey = new String();
        this.ivOrg = new String();
        this.ivQuelle = new String();
    }

    /**
     * @return the key
     */
    public String getKey() {
        return this.ivKey;
    }

    /**
     * @param pvKey the key to set
     */
    public void setKey(String pvKey) {
        this.ivKey = pvKey;
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
     * TXSSicherheitStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("    <txsi:sh ");
    }
    
    /**
     * TXSSicherheit in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        lvHelpXML.append("key=\"" + this.ivKey
                        + "\" org=\"" + this.ivOrg 
                        + "\" quelle=\"" + this.ivQuelle + "\"");
        lvHelpXML.append(">\n");
        return lvHelpXML;
    }
    
    /**
     * TXSSicherheitEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("    </txsi:sh>\n");
    }

}
