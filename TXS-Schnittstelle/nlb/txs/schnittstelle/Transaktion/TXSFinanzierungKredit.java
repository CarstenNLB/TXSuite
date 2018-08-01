/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;


/**
 * @author tepperc
 *
 */
public class TXSFinanzierungKredit implements TXSTransaktion
{
    /**
     * Finanzierungskey
     */
    private String ivKey;
    
    /**
     * Originator
     */
    private String ivOriginator;
    
    /**
     * Quellsystem
     */
    private String ivQuelle;
    
    /**
     * Konstruktor
     */
    public TXSFinanzierungKredit()
    {
        this.ivKey = new String();
        this.ivOriginator = new String();
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
     * @return the originator
     */
    public String getOriginator() {
        return this.ivOriginator;
    }

    /**
     * @param pvOriginator the originator to set
     */
    public void setOriginator(String pvOriginator) {
        this.ivOriginator = pvOriginator;
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
     * TXSFinanzgeschaeftStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        StringBuffer ivHelpXML = new StringBuffer();

        ivHelpXML.append("    <txsi:finkred key=\"" + ivKey + "\" org=\""
                                   + ivOriginator + "\" quelle=\"" + ivQuelle + "\">\n");
        return ivHelpXML;
    }
    
    /**
     * 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
    	return new StringBuffer();
    }
    
    /**
     * TXSFinanzgeschaeftEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("    </txsi:finkred>\n");
    }

}
