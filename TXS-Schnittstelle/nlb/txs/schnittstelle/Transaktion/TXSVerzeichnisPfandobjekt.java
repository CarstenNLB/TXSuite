/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherungsobjekt;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPVermoegensobjekt;
import nlb.txs.schnittstelle.Utilities.ValueMapping;



/**
 * @author tepperc
 *
 */
public class TXSVerzeichnisPfandobjekt implements TXSTransaktion
{
    /**
     * Eindeutige Nummer des Pfandobjekts.
     * z.B.: 1234567890
     * Pflicht
     */
    private String ivObjnr;
    
    /**
     * Liefernde Bank
     * Pflicht
     */
    private String ivOrg;
    
    /**
     * Bestandsführendes System des Pfandobjekts.
     * z.B. SAP
     * Pflicht
     */
    private String ivQuelle;
    
    /**
     * Konstruktor
     */
    public TXSVerzeichnisPfandobjekt()
    {
        ivObjnr = new String();
        ivOrg = new String();
        ivQuelle = new String();
    }

    /**
     * @return the objnr
     */
    public String getObjnr() {
        return this.ivObjnr;
    }

    /**
     * @param pvObjnr the objnr to set
     */
    public void setObjnr(String pvObjnr) {
        this.ivObjnr = pvObjnr;
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
     * TXSVerzeichnisPfandobjektStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("      <txsi:vepo ");
    }
    
    /**
     * TXSVerzeichnisPfandobjekt in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        lvHelpXML.append("objnr=\"" + this.ivObjnr + "\" ");
        lvHelpXML.append("org=\"" + this.ivOrg + "\" ");
        lvHelpXML.append("quelle=\"" + this.ivQuelle + "\">\n");
        
        return lvHelpXML;
    }
    
    /**
     * TXSVerzeichnisPfandobjekt in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("      </txsi:vepo>\n");
    }

    /**
     * Importiert die Darlehensinformationen
     * @param pvModus 
     * @param pvDarlehen 
     * @param pvObj 
     * @return 
     */
    public void importDarlehen(int pvModus, Darlehen pvDarlehen, Sicherungsobjekt pvObj)
    {
        this.ivObjnr = pvObj.getObjektnummer();
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
    }

    /**
     * @param pvZielDarlehen
     * @param pvVObj
     * @param pvInstitut 
     */
    public void importDarlehen(Darlehen pvZielDarlehen, OSPVermoegensobjekt pvVObj, String pvInstitut) 
    {
        this.ivObjnr = pvInstitut + "_" + pvVObj.getNr();
        this.ivOrg = ValueMapping.changeMandant(pvZielDarlehen.getInstitutsnummer());
        this.ivQuelle = "DPP";       
    }

}
