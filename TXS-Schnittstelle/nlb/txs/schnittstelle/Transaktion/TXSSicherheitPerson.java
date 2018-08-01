/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.Utilities.ValueMapping;


/**
 * @author tepperc
 *
 */
public class TXSSicherheitPerson implements TXSTransaktion
{
    /**
     * 
     */
    private String ivKdnr;
    
    /**
     * 
     */
    private String ivOrg;
    
    /**
     * 
     */
    private String ivQuelle;

    /**
     *
     */
    public TXSSicherheitPerson() 
    {
        this.ivKdnr = new String();
        this.ivOrg = new String();
        this.ivQuelle = new String();
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
     * TXSSicherheitPerson in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
      return new StringBuffer("      <txsi:shperson ");
    }
   
    /**
     * TXSSicherheitPerson in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        lvHelpXML.append("kdnr=\"" + this.ivKdnr
                        + "\" org=\"" + this.ivOrg 
                        + "\" quelle=\"" + this.ivQuelle
                        + "\">\n");
        return lvHelpXML;
    }
    
    /**
     * TXSSicherheitPersonEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("      </txsi:shperson>\n");
    }

    /**
     * @param pvBuergennummer 
     * @param pvInstitutsnummer 
     * 
     */
    public void setTXSSicherheitPerson(String pvBuergennummer, String pvInstitutsnummer) 
    {
        this.ivKdnr = pvBuergennummer;
        this.ivOrg = ValueMapping.changeMandant(pvInstitutsnummer);
        this.ivQuelle = "KUNDE";
    }

}
