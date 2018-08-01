/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

import org.apache.log4j.Logger;

import nlb.txs.schnittstelle.Darlehen.DarlehenVerarbeiten;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQ;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQBlock;
import nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten.LoanIQPassivDaten;
import nlb.txs.schnittstelle.Utilities.MappingMIDAS;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;

/**
 * @author tepperc
 *
 */
public class TXSFinanzgeschaeft implements TXSTransaktion
{
    /**
     * Kontonummer
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
    public TXSFinanzgeschaeft()
    {
        initTXSFinanzgeschaeft();
    }
    
    /**
     * 
     */
    public void initTXSFinanzgeschaeft() 
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

        ivHelpXML.append("  <txsi:fg key=\"" + ivKey + "\" org=\""
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
        return new StringBuffer("  </txsi:fg>\n");
    }

    /**
     * Importiert die Darlehensinformationen aus DarKa
     * @param pvModus 
     * @param pvDarlehen 
     * @return 
     */
    public boolean importDarlehen(int pvModus, Darlehen pvDarlehen)
    {
        this.ivKey = pvDarlehen.getKontonummer();
        this.ivOriginator = ValueMapping.changeMandant(pvDarlehen.getInstitutsnummer());
        switch (pvModus)
        {
            case DarlehenVerarbeiten.DARKA:
                // Umschluesselung zum Wertpapier(Aktiv) anhand des Ausplatzierungsmerkmal - CT 09.02.2017
            	// K3 -> ADARLPFBG
            	if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2") || pvDarlehen.getAusplatzierungsmerkmal().endsWith("4") || pvDarlehen.getAusplatzierungsmerkmal().equals("H3"))
            	{	
            		this.ivQuelle = "ADAWPPFBG";  
                    // Sonderregel BLB - CT 19.12.2016
                    // Ausplatzierungsmerkmal H3 und Produktschluessel 0101, 0103 oder 0106 dann "ADARLPFBG"
                    if (pvDarlehen.getInstitutsnummer().equals("004"))
                    {
                    	if (pvDarlehen.getAusplatzierungsmerkmal().equals("H3")) // Wenn H3, dann... 
                    	{   // Wenn Produktschluessel 0101, 0103 oder 0106 dann "ADARLPFBG"
                    		if (pvDarlehen.getProduktSchluessel().equals("00101") || pvDarlehen.getProduktSchluessel().equals("00103") || pvDarlehen.getProduktSchluessel().equals("00106"))
                    		{
                    			this.ivQuelle = "ADARLPFBG";
                    		}
                    	}
                    }
                }
                else
                {
                	this.ivQuelle = "ADARLPFBG";
                }
                break;
            case DarlehenVerarbeiten.FLUGZEUGE:
                // Umschluesselung zum Wertpapier(Aktiv) anhand des Ausplatzierungsmerkmal - CT 09.02.2017
            	if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2") || pvDarlehen.getAusplatzierungsmerkmal().endsWith("3") || pvDarlehen.getAusplatzierungsmerkmal().endsWith("4"))
            	{	
                    this.ivQuelle = "ADAWPFLUG";
                }
                else
                {
                	this.ivQuelle = "ADARLFLUG";
                }
                break;
            case DarlehenVerarbeiten.SCHIFFE:
                // Umschluesselung zum Wertpapier(Aktiv) anhand des Ausplatzierungsmerkmal - CT 09.02.2017
            	if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2") || pvDarlehen.getAusplatzierungsmerkmal().endsWith("3") || pvDarlehen.getAusplatzierungsmerkmal().endsWith("4"))
                {
                    this.ivQuelle = "ADAWPSCHF";
                    // Sonderregel BLB - CT 19.12.2016
                    // Ausplatzierungsmerkmal S3 und Produktschluessel 0101, 0103 oder 0106 dann "ADARLSCHF"
                    if (pvDarlehen.getInstitutsnummer().equals("004"))
                    {
                    	if (pvDarlehen.getAusplatzierungsmerkmal().equals("S3")) // Wenn S3, dann... 
                    	{   // Wenn Produktschluessel 0101, 0103 oder 0106 dann "ADARLSCHF"
                    		if (pvDarlehen.getProduktSchluessel().equals("00101") || pvDarlehen.getProduktSchluessel().equals("00103") || pvDarlehen.getProduktSchluessel().equals("00106"))
                    		{
                    			this.ivQuelle = "ADARLSCHF";
                    		}
                    	}
                    }
                }
                else
                {
                    this.ivQuelle = "ADARLSCHF";
                }
                break;
            case DarlehenVerarbeiten.OEPG:
                // Umschluesselung zum Wertpapier(Aktiv) anhand des Ausplatzierungsmerkmal - CT 09.02.2017
            	// Nur O2 und O4 relevant...
            	if (pvDarlehen.getAusplatzierungsmerkmal().endsWith("2") || pvDarlehen.getAusplatzierungsmerkmal().endsWith("4"))
                {
                    this.ivQuelle = "ADAWPOEPG";
                }
                else
                {
                    this.ivQuelle = "ADARLOEPG";
                }
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
     * Importiert die Darlehensinformationen aus LoanIQ
     * @param pvDarlehenLoanIQBlock 
     * @param pvVorlaufsatz 
     * @return 
     */
    public boolean importLoanIQ(DarlehenLoanIQBlock pvDarlehenLoanIQBlock, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {
    	DarlehenLoanIQ lvHelpDarlehenLoanIQ = pvDarlehenLoanIQBlock.getDarlehenLoanIQNetto();
   	
        // LoanIQ-Daten(Key und Quelle) setzen
    	this.ivKey = lvHelpDarlehenLoanIQ.getKontonummer();
        // Definition des Kredittyps
        int lvHelpKredittyp = ValueMapping.ermittleKredittyp(lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal(), lvHelpDarlehenLoanIQ.getBuergschaftprozent());
          
        //if (lvHelpKredittyp.equals("undefiniert"))
        //{
        //	pvLogger.error("Darlehen " + lvHelpDarlehenLoanIQ.getKontonummer() + ": Kredittyp undefiniert...");
        //	return false;
        //}

    	//int lvHelpKredittyp = lvHelpDarlehenLoanIQ.ermittleKredittyp();
    	//System.out.println("Kredittyp: " + lvHelpKredittyp);
          
    	switch (lvHelpKredittyp)
    	{
            case DarlehenLoanIQ.HYPOTHEK_1A:
            case DarlehenLoanIQ.KOMMUNALVERBUERGTE_HYPOTHEK:
            case DarlehenLoanIQ.REIN_KOMMUNAL:
            case DarlehenLoanIQ.VERBUERGT_KOMMUNAL:
                if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("O"))
                {
                  this.ivQuelle = "ALIQOEPG";   
                }
                else
                {	
                  this.ivQuelle = "ALIQPFBG";
                }
                break;
            case DarlehenLoanIQ.SCHIFFSDARLEHEN:
                  this.ivQuelle = "ALIQSCHF";
                break;
            case DarlehenLoanIQ.FLUGZEUGDARLEHEN:
                 this.ivQuelle = "ALIQFLUG";
                break;
            case DarlehenLoanIQ.BANKKREDIT:
            	if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("O"))
            	{
            		this.ivQuelle = "ALIQOEPG";
            	}
            	if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K") || lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("H"))
            	{
            		this.ivQuelle = "ALIQPFBG";
            	}
            	if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("F"))
            	{
            		this.ivQuelle = "ALIQFLUG";
            	}
            	if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("S"))
            	{
            		this.ivQuelle = "ALIQSCHF";
            	}
            	break;	            	
            case DarlehenLoanIQ.SONSTIGE_SCHULDVERSCHREIBUNG:
            	if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("O"))
            	{
            		this.ivQuelle = "ALIQWPOEPG";
            	}
            	if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K") || lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("H"))
            	{
            		this.ivQuelle = "ALIQWPPFBG";
            	}
            	if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("F"))
            	{
            		this.ivQuelle = "ALIQWPFLUG";
            	}
            	if (lvHelpDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("S"))
            	{
            		this.ivQuelle = "ALIQWPSCHF";
            	}
            	break;
            default:
                System.out.println("TXSFinanzgeschaeft - Quellsystem: Unbekannter Kredittyp " + lvHelpKredittyp);       
    	}
        
        // Originator per Institutsnummer setzen
        this.ivOriginator = ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer());
        
        return true;
    }
    
    /**
     * Importiert die Passiv-Informationen aus LoanIQ
     * @param pvPassivDaten
     * @param pvInstitutsnummer
     * @return
     */
    public boolean importLoanIQPassiv(LoanIQPassivDaten pvPassivDaten, String pvInstitutsnummer)
    {
        // TXSFinanzgeschaeft
        this.setKey(pvPassivDaten.getAktenzeichen());
        // BLB: DE + "Kontonummer"
        if (pvInstitutsnummer.equals("004"))
        {
     	   this.setKey("DE" + pvPassivDaten.getKontonummer());
        }

        this.setOriginator(ValueMapping.changeMandant(pvInstitutsnummer));
        if (pvPassivDaten.getAusplatzierungsmerkmal().startsWith("H") || pvPassivDaten.getAusplatzierungsmerkmal().startsWith("K"))
        {
            this.setQuelle("PLIQPFBG");
        }
        if (pvPassivDaten.getAusplatzierungsmerkmal().startsWith("F"))
        {
            this.setQuelle("PLIQFLUG");           
        }
        if (pvPassivDaten.getAusplatzierungsmerkmal().startsWith("S"))
        {
            this.setQuelle("PLIQSCHF");           
        }
        if (pvPassivDaten.getAusplatzierungsmerkmal().startsWith("O"))
        {
        	this.setQuelle("PLIQOEPG");
        }
        
        return true;
    }
    
    /**
     * Importiert die Darlehensinformationen aus MIDAS
     * @param pvDarlehenLoanIQ
     * @param pvVorlaufsatz 
     * @return 
     */
    public boolean importMIDAS(DarlehenLoanIQ pvDarlehenLoanIQ, Vorlaufsatz pvVorlaufsatz)
    {
        // MIDAS-Daten(Key und Quelle) setzen
    	this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehenLoanIQ.getQuellsystem(), pvDarlehenLoanIQ.getKontonummer());
    	if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("H") || pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K"))
    	{
    		this.ivQuelle = "AMIDPFBG";
        }
    	if (pvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("F"))
    	{
    		this.ivQuelle = "AMIDFLUG";
        }
        
        // Originator per Institutsnummer setzen
        this.ivOriginator = ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer());
        
        return true;
    }

    /**
     * Importiert die Wertpapier-Informationen
     * @param pvBestandsdaten
     * @param pvInstitutsnummer
     * @return 
     */
    public boolean importWertpapier(Bestandsdaten pvBestandsdaten, String pvInstitutsnummer)
    {    	
        // MAVIS-Daten(Key und Quelle) setzen
    	this.ivKey = pvBestandsdaten.getProdukt();
    	StringBuilder lvQuelle = new StringBuilder();
    	if (pvBestandsdaten.getAktivPassiv().equals("1"))
    	{
    		lvQuelle.append("A");
    	}
    	if (pvBestandsdaten.getAktivPassiv().equals("2"))
    	{
    		lvQuelle.append("P");
    	}
    	
    	if (pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("H") || pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("K"))
    	{
    		lvQuelle.append("MAVIPFBG");
        }
    	if (pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("F"))
    	{
    		lvQuelle.append("MAVIFLUG");
        }
    	if (pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("S"))
    	{
    		lvQuelle.append("MAVISCHF");
        }
    	if (pvBestandsdaten.getAusplatzierungsmerkmal().startsWith("O"))
    	{
    		lvQuelle.append("MAVIOEPG");
        }
        this.ivQuelle = lvQuelle.toString();
        
        // Originator per Institutsnummer setzen
        this.ivOriginator = ValueMapping.changeMandant(pvInstitutsnummer);
        
        return true;
    }   

}
