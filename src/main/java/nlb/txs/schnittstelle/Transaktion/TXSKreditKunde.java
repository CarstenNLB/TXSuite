/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.Darlehen;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenBlock;
import nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten.LoanIQPassivDaten;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.Termingelder.Daten.Termingeld;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;
import org.apache.log4j.Logger;


/**
 * @author tepperc
 *
 */
public class TXSKreditKunde implements TXSTransaktion
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
    private String ivRolle;

    /**
     * Konstruktor
     */
    public TXSKreditKunde()
    {
        initTXSKreditKunde();
    }
    
    /**
      *
      */
    public void initTXSKreditKunde() 
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
     * TXSKreditKunde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("    <txsi:kredkunde ");
    }
    
    /**
     * TXSKreditKunde in die XML-Datei schreiben
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
     * TXSKreditKundeEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("    </txsi:kredkunde>\n");
    }

    /**
     * Importiert den Kreditnehmer aus DarKa
     * @param pvModus 
     * @param pvDarlehen
     * @return
     */
    public boolean importDarlehen(int pvModus, nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen pvDarlehen)
    {
        this.ivKdnr = pvDarlehen.getKundennummer();
        this.ivOrg = ValueMapping.changeMandant(pvDarlehen.getInstitutsnummer());
        switch (pvModus)
        {
           case DarlehenVerarbeiten.DARKA:
           case DarlehenVerarbeiten.FLUGZEUGE:
           case DarlehenVerarbeiten.SCHIFFE:
           case DarlehenVerarbeiten.OEPG:
            case DarlehenVerarbeiten.KEV:
           //case DarlehenVerarbeiten.ALT:
               this.ivQuelle = "KUNDE";
               break;
           case DarlehenVerarbeiten.DPP:
               this.ivQuelle = "DPP";
           default:
               System.out.println("TXSKreditKunde: Unbekannter Modus");
        }

        // CT 07.12.2015 Umstellung des Kreditnehmers auf Hauptkreditnehmer
        this.ivRolle = "0";
        
        return true;
    }

    /**
     * Importiert den Kreditnehmer aus LoanIQ
     * @param pvDarlehenBlock
     * @param pvVorlaufsatz 
     * @return
     */
    public boolean importLoanIQ(DarlehenBlock pvDarlehenBlock, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {
    	Darlehen lvHelpDarlehen = pvDarlehenBlock.getDarlehenNetto();
    	
        this.ivKdnr = MappingKunde.extendKundennummer(lvHelpDarlehen.getKundennummer(), pvLogger);
        this.ivOrg = ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer());
        this.ivQuelle = "KUNDE";
        this.ivRolle = "0";
               
        return true;
    }
    
    /**
     * Importiert die PassivDaten aus LoanIQ
     * @param pvPassivDaten
     * @param pvInstitutsnummer
     */
    public boolean importLoanIQPassiv(LoanIQPassivDaten pvPassivDaten, String pvInstitutsnummer)
    {
      String lvKdnrhelper = "0000000000" + pvPassivDaten.getKundennummer();
      lvKdnrhelper = lvKdnrhelper.substring(lvKdnrhelper.length()-10, lvKdnrhelper.length());
      this.setKdnr(lvKdnrhelper);
      this.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
      this.setQuelle("KUNDE");
      this.setRolle("90");
      
      return true;
    }     

    
    /**
     * Importiert den Kreditnehmer aus MIDAS
     * @param pvDarlehen
     * @param pvVorlaufsatz 
     * @return
     */
    public boolean importMIDAS(Darlehen pvDarlehen, Vorlaufsatz pvVorlaufsatz)
    {
        this.ivKdnr = MappingKunde.extendKundennummer(pvDarlehen.getKundennummer(), null);
        this.ivOrg = ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer());
        this.ivQuelle = "KUNDE";
        this.ivRolle = "0";
                
        return true;
    }

    /**
     * Importiert die Wertpapier-Informationen
     * @return 
     */
    public boolean importWertpapier(Bestandsdaten pvBestandsdaten, String pvInstitutsnummer)
    {
    	this.ivKdnr = pvBestandsdaten.getKundennummer();
        this.ivOrg = ValueMapping.changeMandant(pvInstitutsnummer);
    	this.ivQuelle = "KUNDE";
    	this.ivRolle = "0";
    	
    	return true;
    }

    /**
     * Importiert die Wertpapier-Informationen fuer KEV
     * @return
     */
    public boolean importKEVWertpapier(Bestandsdaten pvBestandsdaten, String pvInstitutsnummer)
    {
        this.ivKdnr = pvBestandsdaten.getKundennummer();
        this.ivOrg = ValueMapping.changeMandant(pvInstitutsnummer);
        this.ivQuelle = "KUNDE";
        this.ivRolle = "0";

        return true;
    }


    /**
     * Importiert den Kreditnehmer aus LoanIQ fuer KEV
     * @param pvDarlehen
     * @param pvVorlaufsatz
     * @return
     */
    public boolean importKEVLoanIQ(Darlehen pvDarlehen, Vorlaufsatz pvVorlaufsatz)
    {
        this.ivKdnr = MappingKunde.extendKundennummer(pvDarlehen.getKundennummer(), null);
        this.ivOrg = ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer());
        this.ivQuelle = "KUNDE";
        this.ivRolle = "0";

        return true;
    }

    /**
     * Importiert die Termingeldinformationen
     * @param pvTermingeld Termingeld
     * @param pvVorlaufsatz Vorlaufsatz
     * @param pvLogger log4j-Logger
     * @return
     */
    public boolean importTermingeld(Termingeld pvTermingeld, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {
        this.ivKdnr = pvTermingeld.getGeschaeftspartnernummer();
        this.ivOrg = ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer());
        this.ivQuelle = "KUNDE";
        this.ivRolle = "0";

        return true;
    }

}
