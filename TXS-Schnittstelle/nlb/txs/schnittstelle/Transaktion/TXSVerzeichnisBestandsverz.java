/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherheit;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.ValueMapping;


/**
 * @author tepperc
 *
 */
public class TXSVerzeichnisBestandsverz implements TXSTransaktion
{
    /**
     * 
     */
    private String ivBvnr;
    
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
    public TXSVerzeichnisBestandsverz() 
    {
        this.ivBvnr = new String();
        this.ivOrg = new String();
        this.ivQuelle = new String();
    }

    /**
     * @return the bvnr
     */
    public String getBvnr() {
        return this.ivBvnr;
    }

    /**
     * @param pvBvnr the bvnr to set
     */
    public void setBvnr(String pvBvnr) {
        this.ivBvnr = pvBvnr;
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
     * TXSVerzeichnisBestandsverzStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart() 
    {
      return new StringBuffer("      <txsi:vebv ");
    }

    /**
     * TXSVerzeichnisBestandsverz in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten() 
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        lvHelpXML.append("bvnr=\"" + String2XML.change2XML(String2XML.rtrim(this.ivBvnr))
                        + "\" org=\"" + this.ivOrg
                        + "\" quelle=\"" + this.ivQuelle 
                        + "\">\n");
        
        return lvHelpXML;
    }

    /**
     * TXSSicherheitVerzeichnisEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde() 
    {
      return new StringBuffer("      </txsi:vebv>\n");
    }

    /**
     * @param pvModus 
     * @param pvDarlehen 
     * @param pvSicherheit 
     * 
     */
    public void importDarlehen(int pvModus, Darlehen pvDarlehen, Sicherheit pvSicherheit) 
    {
        this.ivBvnr = pvSicherheit.getObjektnummer();
        this.ivOrg = ValueMapping.changeMandant(pvDarlehen.getInstitutsnummer());
        //this.ivQuelle = "DARLEHEN";        
        switch (pvModus)
        {
            case DarlehenVerarbeiten.DARKA:
                this.ivQuelle = "ADARLPFBG";
                break;
            case DarlehenVerarbeiten.FLUGZEUGE:
                this.ivQuelle = "ADARLFLUG";
                break;
            case DarlehenVerarbeiten.SCHIFFE:
                this.ivQuelle = "ADARLSCHF";
                break;
            case DarlehenVerarbeiten.OEPG:
                this.ivQuelle = "ADARLOEPG";
                //if (pvDarlehen.getInstitutsnummer().equals("004"))
                //{
                //    if (pvDarlehen.getProduktSchluessel().equals("01002") || pvDarlehen.getProduktSchluessel().equals("01003"))
                //    {
                //        this.ivQuelle = "AKFWOEPG";
                //    }
                //}
                break;
            case DarlehenVerarbeiten.ALT:
                this.ivQuelle = "ADARLPFBG";
                break;
            case DarlehenVerarbeiten.DPP:
                this.ivQuelle = "DPP";
                break;
            default:
                System.out.println("TXSFinanzgeschaeft: Unbekannter Modus");
        }        
    }
 
}
