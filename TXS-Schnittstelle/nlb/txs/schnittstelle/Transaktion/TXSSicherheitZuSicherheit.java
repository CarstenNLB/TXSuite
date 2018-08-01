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
public class TXSSicherheitZuSicherheit implements TXSTransaktion
{
   /**
    * Beziehungsart 
    */
    private String ivArt;
    
    /**
     * Sicherheitenschluessel
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
     * Rang
     */
    private String ivRang;

    /**
     * @param pvArt
     * @param pvKey
     * @param pvOrg
     * @param pvQuelle
     * @param pvRang
     */
    public TXSSicherheitZuSicherheit() 
    {
        super();
        this.ivArt = new String();
        this.ivKey = new String();
        this.ivOrg = new String();
        this.ivQuelle = new String();
        this.ivRang = new String();
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
     * TXSSicherheitZuSicherheitStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("    <txsi:zugsh ");
    }
    
    /**
     * TXSSicherheitZuSicherheit in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        lvHelpXML.append("key=\"" + this.ivKey
                        + "\" org=\"" + this.ivOrg 
                        + "\" quelle=\"" + this.ivQuelle + "\"");
        if (ivArt.length() > 0)
        {
          lvHelpXML.append(" art=\"" + this.ivArt + "\"");
        }
        if (ivRang.length() > 0)
        {
          lvHelpXML.append(" rang=\"" + this.ivRang + "\"");
        }
        
        lvHelpXML.append(">\n");
        return lvHelpXML;
    }
    
    /**
     * TXSSicherheitZuSicherheitEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("    </txsi:zugsh>\n");
    }
    
}
