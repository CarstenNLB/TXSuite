/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.Utilities.String2XML;

/**
 * @author tepperc
 *
 */
public class TXSBestandsverzDaten implements TXSTransaktion
{
    /**
     * Flur
     */
    private String ivFlur;
    
    /**
     * Flurstueck
     */
    private String ivFlurst;
    
    /**
     * Gemarkung
     */
    private String ivGem;
    
    /**
     * Laufende Nr.
     */
    private String ivLfdnr;

    /**
     * Konstruktor
     */
    public TXSBestandsverzDaten() 
    {
       initTXSBestandsverzDaten();
    }

    /**
    * Initialisiert die Instanzvariablen mit leeren Strings
    */
    public void initTXSBestandsverzDaten()
    {
      this.ivFlur = new String();
      this.ivFlurst = new String();
      this.ivGem = new String();
      this.ivLfdnr = new String();
    }

    /**
     * Liefert den Flur
     * @return the flur
     */
    public String getFlur() {
        return this.ivFlur;
    }

    /**
     * Setzt den Flur
     * @param pvFlur the flur to set
     */
    public void setFlur(String pvFlur) {
        this.ivFlur = pvFlur;
    }

    /**
     * Liefert das Flurstueck
     * @return the flurst
     */
    public String getFlurst() {
        return this.ivFlurst;
    }

    /**
     * Setzt das Flurstueck
     * @param pvFlurst the flurst to set
     */
    public void setFlurst(String pvFlurst) {
        this.ivFlurst = pvFlurst;
    }

    /**
     * Liefert die Gemarkung
     * @return the gem
     */
    public String getGem() {
        return this.ivGem;
    }

    /**
     * Setzt die Gemarkung
     * @param pvGem the gem to set
     */
    public void setGem(String pvGem) {
        this.ivGem = pvGem;
    }

    /**
     * Liefert die laufende Nummer
     * @return the lfdnr
     */
    public String getLfdnr() {
        return this.ivLfdnr;
    }

    /**
     * Setzt die laufende Nummer
     * @param pvLfdnr the lfdnr to set
     */
    public void setLfdnr(String pvLfdnr) {
        this.ivLfdnr = pvLfdnr;
    }

    /**
     * TXSBestandsverzDatenStart in einen StringBuilder schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart() 
    {
        return new StringBuffer("        <txsi:bvdaten ");
    }

    /**
     * TXSBestandsverzDaten in einen StringBuilder schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten() 
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        //if (this.ivFlur.length() > 0)
        //{
          lvHelpXML.append("flur=\"" + String2XML.change2XML(this.ivFlur) + "\" ");  
        //}
        //if (this.ivFlurst.length() > 0)
        //{
          lvHelpXML.append("flurst=\"" + String2XML.change2XML(this.ivFlurst) + "\" ");
        //}       
        //if (this.ivGem.length() > 0)
        //{
          lvHelpXML.append("gem=\"" + String2XML.change2XML(this.ivGem) + "\" ");
        //// CT 04.05.2021 - lvHelpXML.append("gem=\"\" ");

        //}
        //if (this.ivLfdnr.length() > 0)
        //{
          lvHelpXML.append("lfdnr=\"" + this.ivLfdnr + "\" "); 
        //}       
        lvHelpXML.append(">");
        return lvHelpXML;
    }

    /**
     * TXSBestandsverzDatenEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde() 
    {
        return new StringBuffer("</txsi:bvdaten>\n");
    }

}
