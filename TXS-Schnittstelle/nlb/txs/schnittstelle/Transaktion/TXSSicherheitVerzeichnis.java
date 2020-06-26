/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherheit;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPGrundbuch;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.ValueMapping;


/**
 * @author tepperc
 *
 */
public class TXSSicherheitVerzeichnis implements TXSTransaktion
{
    /**
     * 
     */
    private String ivVenr;
    
    /**
     * 
     */
    private String ivOrg;
    
    /**
     * 
     */
    private String ivQuelle;

    /**
     * Konstruktor
     */
    public TXSSicherheitVerzeichnis() 
    {
        initTXSSicherheitVerzeichnis();
    }

    /**
     * Initialisiert die Instanzvariablen mit leeren Strings
     */
    public void initTXSSicherheitVerzeichnis()
    {
        this.ivVenr = new String();
        this.ivOrg = new String();
        this.ivQuelle = new String();
    }

    /**
     * @return the venr
     */
    public String getVenr() {
        return this.ivVenr;
    }

    /**
     * @param pvVenr the venr to set
     */
    public void setVenr(String pvVenr) {
        this.ivVenr = pvVenr;
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
     * TXSSicherheitVerzeichnisStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
      return new StringBuffer("    <txsi:shve ");
    }
    
    /**
     * TXSSicherheitVerzeichnis in die XML-Datei schreiben
     * @return 

     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        lvHelpXML.append("venr=\"" +  String2XML.change2XML(String2XML.rtrim(this.ivVenr))
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
       return new StringBuffer("    </txsi:shve>\n");
    }
    
    /**
     * Importiert die Darlehensinformationen
     * @param pvModus 
     * @param pvDarlehen 
     * @param pvSicherheit 
     * @return 
     */
    public boolean importDarlehen(int pvModus, Darlehen pvDarlehen, Sicherheit pvSicherheit)
    {   
        this.ivVenr = pvSicherheit.getObjektnummer();
        this.ivOrg = ValueMapping.changeMandant(pvDarlehen.getInstitutsnummer());
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

        return true;
    }

    /**
     * @param pvZielDarlehen
     * @param pvGb 
     * @param pvInstitut 
     * @return 
     */
    public boolean importDarlehen(Darlehen pvZielDarlehen, OSPGrundbuch pvGb, String pvInstitut) 
    {
        this.ivVenr = pvInstitut + "_" + pvGb.getNr();
        this.ivOrg = ValueMapping.changeMandant(pvZielDarlehen.getInstitutsnummer());
        this.ivQuelle = "DPP";
        
        return true;

    }


}
