/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;


/**
 * @author tepperc
 *
 */
public class TXSCashflowDaten 
{
    /**
     * 
     */
    private String ivBewart;
    
    /**
     * 
     */
    private String ivCfkey;
    
    /**
     * 
     */
    private String ivDatum;
    
    /**
     * 
     */
    private String ivNomdeck;
    
    /**
     * 
     */
    private String ivRhyt;
    
    /**
     * 
     */
    private String ivTbetrag;
    
    /**
     * 
     */
    private String ivTsatz;
    
    /**
     * 
     */
    private String ivWhrg;
    
    /**
     * 
     */
    private String ivZbetrag;
    
    /**
     * 
     */
    private String ivZsatz;

    /**
     * Konstruktor
     */
    public TXSCashflowDaten() 
    {
        this.ivBewart = new String();
        this.ivCfkey = new String();
        this.ivDatum = new String();
        this.ivNomdeck = new String();
        this.ivRhyt = new String();
        this.ivTbetrag = new String();
        this.ivTsatz = new String();
        this.ivWhrg = new String();
        this.ivZbetrag = new String();
        this.ivZsatz = new String();
    }

    /**
     * @return the bewart
     */
    public String getBewart() {
        return this.ivBewart;
    }

    /**
     * @param pvBewart the bewart to set
     */
    public void setBewart(String pvBewart) {
        this.ivBewart = pvBewart;
    }

    /**
     * @return the cfkey
     */
    public String getCfkey() {
        return this.ivCfkey;
    }

    /**
     * @param pvCfkey the cfkey to set
     */
    public void setCfkey(String pvCfkey) {
        this.ivCfkey = pvCfkey;
    }

    /**
     * @return the datum
     */
    public String getDatum() {
        return this.ivDatum;
    }

    /**
     * @param pvDatum the datum to set
     */
    public void setDatum(String pvDatum) {
        this.ivDatum = pvDatum;
    }

    /**
     * @return the nomdeck
     */
    public String getNomdeck() {
        return this.ivNomdeck;
    }

    /**
     * @param pvNomdeck the nomdeck to set
     */
    public void setNomdeck(String pvNomdeck) {
        this.ivNomdeck = pvNomdeck;
    }

    /**
     * @return the rhyt
     */
    public String getRhyt() {
        return this.ivRhyt;
    }

    /**
     * @param pvRhyt the rhyt to set
     */
    public void setRhyt(String pvRhyt) {
        this.ivRhyt = pvRhyt;
    }

    /**
     * @return the tbetrag
     */
    public String getTbetrag() {
        return this.ivTbetrag;
    }

    /**
     * @param pvTbetrag the tbetrag to set
     */
    public void setTbetrag(String pvTbetrag) {
        this.ivTbetrag = pvTbetrag;
    }

    /**
     * @return the tsatz
     */
    public String getTsatz() {
        return this.ivTsatz;
    }

    /**
     * @param pvTsatz the tsatz to set
     */
    public void setTsatz(String pvTsatz) {
        this.ivTsatz = pvTsatz;
    }

    /**
     * @return the whrg
     */
    public String getWhrg() {
        return this.ivWhrg;
    }

    /**
     * @param pvWhrg the whrg to set
     */
    public void setWhrg(String pvWhrg) {
        this.ivWhrg = pvWhrg;
    }

    /**
     * @return the zbetrag
     */
    public String getZbetrag() {
        return this.ivZbetrag;
    }

    /**
     * @param pvZbetrag the zbetrag to set
     */
    public void setZbetrag(String pvZbetrag) {
        this.ivZbetrag = pvZbetrag;
    }

    /**
     * @return the zsatz
     */
    public String getZsatz() {
        return this.ivZsatz;
    }

    /**
     * @param pvZsatz the zsatz to set
     */
    public void setZsatz(String pvZsatz) {
        this.ivZsatz = pvZsatz;
    }

    /**
     * TXSCashflowDaten in die XML-Datei schreiben
     * @return 
     */
    public String printTXSCashflowDaten()
    {
        // Ein CashFlow-Satz    
        String lvXmlString = new String();

        // Start des Satzes
        lvXmlString = lvXmlString + "    <txsi:cfdaten ";

        lvXmlString = lvXmlString + "cfkey=\"" + this.ivCfkey;
         
        lvXmlString = lvXmlString + "\" datum=\"" + this.ivDatum;
        
        if (!this.ivTsatz.isEmpty())
        {
          lvXmlString = lvXmlString + "\" tsatz=\"" + this.ivTsatz;
        }
        
        if (this.ivTbetrag.isEmpty())
        {
          lvXmlString = lvXmlString + "\" tbetrag=\"0";            
        }
        else
        {
          lvXmlString = lvXmlString + "\" tbetrag=\"" + this.ivTbetrag;
        }
        
        if (!this.ivZsatz.isEmpty())
        {
          lvXmlString = lvXmlString + "\" zsatz=\"" + this.ivZsatz;
          
        }
        
        if (this.ivZbetrag.isEmpty())
        {
            lvXmlString = lvXmlString + "\" zbetrag=\"0";            
        }
        else
        {
          lvXmlString = lvXmlString + "\" zbetrag=\"" + this.ivZbetrag;
        }       
      
        lvXmlString = lvXmlString + "\" whrg=\"" + this.ivWhrg;
                            
        // Abschluss des Satzes
        lvXmlString = lvXmlString + "\"/>\n";

        return lvXmlString;
    }

}
