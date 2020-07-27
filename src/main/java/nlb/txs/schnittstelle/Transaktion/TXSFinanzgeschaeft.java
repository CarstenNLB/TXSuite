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
import nlb.txs.schnittstelle.Utilities.MappingMIDAS;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;
import org.apache.log4j.Logger;

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
     * Initialisiert die Instanzvariablen mit leeren Strings
     */
    public void initTXSFinanzgeschaeft() 
    {
        this.ivKey = new String();
        this.ivOriginator = new String();
        this.ivQuelle = new String();
    }

    /**
     * Liefert die Kontonummer (sprich den Key)
     * @return the key
     */
    public String getKey() {
        return this.ivKey;
    }

    /**
     * Setzt die Kontonummer
     * @param pvKey the key to set
     */
    public void setKey(String pvKey) {
        this.ivKey = pvKey;
    }

    /**
     * Liefert den Originator
     * @return the originator
     */
    public String getOriginator() {
        return this.ivOriginator;
    }

    /**
     * Setzt den Originator
     * @param pvOriginator the originator to set
     */
    public void setOriginator(String pvOriginator) {
        this.ivOriginator = pvOriginator;
    }

    /**
     * Liefert das Quellsystem
     * @return the quelle
     */
    public String getQuelle() {
        return this.ivQuelle;
    }

    /**
     * Setzt das Quellsystem
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
    public boolean importDarlehen(int pvModus, nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen pvDarlehen)
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
            case DarlehenVerarbeiten.KEV:
                this.ivQuelle = "ADARLKEV";
                break;
          default:
                System.out.println("TXSFinanzgeschaeft: Unbekannter Modus");
        }        
        return true;
    }

  /**
     * Importiert die Darlehensinformationen aus LoanIQ
     * @param pvDarlehenBlock
     * @param pvVorlaufsatz 
     * @return 
     */
    public boolean importLoanIQ(DarlehenBlock pvDarlehenBlock, Vorlaufsatz pvVorlaufsatz, Logger pvLogger)
    {
    	Darlehen lvHelpDarlehen = pvDarlehenBlock.getDarlehenNetto();
   	
        // LoanIQ-Daten(Key und Quelle) setzen
    	this.ivKey = lvHelpDarlehen.getKontonummer();
        // Definition des Kredittyps
        int lvHelpKredittyp = ValueMapping.ermittleKredittyp(lvHelpDarlehen.getAusplatzierungsmerkmal(), lvHelpDarlehen.getBuergschaftprozent());
          
        //if (lvHelpKredittyp.equals("undefiniert"))
        //{
        //	pvLogger.error("Darlehen " + lvHelpDarlehen.getKontonummer() + ": Kredittyp undefiniert...");
        //	return false;
        //}

      StringBuilder lvHelpQuellsystem = new StringBuilder();
      lvHelpQuellsystem.append("A");
      if (pvDarlehenBlock.getQuellsystem().equals("IWHS"))
      {
        lvHelpQuellsystem.append("AZ6");
      }
    if (pvDarlehenBlock.getQuellsystem().equals("LOANIQ"))
    {
      lvHelpQuellsystem.append("LIQ");
    }
    	switch (lvHelpKredittyp)
    	{
            case Darlehen.HYPOTHEK_1A:
            case Darlehen.KOMMUNALVERBUERGTE_HYPOTHEK:
            case Darlehen.REIN_KOMMUNAL:
            case Darlehen.VERBUERGT_KOMMUNAL:
                if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("O"))
                {
                  lvHelpQuellsystem.append("OEPG");
                }
                else
                {
                  lvHelpQuellsystem.append("PFBG");
                }
                break;
            case Darlehen.SCHIFFSDARLEHEN:
                lvHelpQuellsystem.append("SCHF");
                break;
            case Darlehen.FLUGZEUGDARLEHEN:
              lvHelpQuellsystem.append("FLUG");
                break;
            case Darlehen.BANKKREDIT:
            	if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("O"))
            	{
                lvHelpQuellsystem.append("OEPG");
            	}
            	if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("K") || lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("H"))
            	{
                lvHelpQuellsystem.append("PFBG");
            	}
            	if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("F"))
            	{
                lvHelpQuellsystem.append("FLUG");
            	}
            	if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("S"))
            	{
                lvHelpQuellsystem.append("SCHF");
            	}
            	break;	            	
            case Darlehen.SONSTIGE_SCHULDVERSCHREIBUNG:
            	if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("O"))
            	{
                lvHelpQuellsystem.append("WPOEPG");
            	}
            	if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("K") || lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("H"))
            	{
                lvHelpQuellsystem.append("WPPFBG");
            	}
            	if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("F"))
            	{
                lvHelpQuellsystem.append("WPFLUG");
            	}
            	if (lvHelpDarlehen.getAusplatzierungsmerkmal().startsWith("S"))
            	{
                lvHelpQuellsystem.append("WPSCHF");
            	}
            	break;
            default:
                System.out.println("TXSFinanzgeschaeft - Quellsystem: Unbekannter Kredittyp " + lvHelpKredittyp);       
    	}

      this.ivQuelle = lvHelpQuellsystem.toString();
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
     * @param pvDarlehen
     * @param pvVorlaufsatz 
     * @return 
     */
    public boolean importMIDAS(Darlehen pvDarlehen, Vorlaufsatz pvVorlaufsatz)
    {
        // MIDAS-Daten(Key und Quelle) setzen
        this.ivKey = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen.getKontonummer());

    	if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("H") || pvDarlehen.getAusplatzierungsmerkmal().startsWith("K"))
    	{
    		this.ivQuelle = "AMIDPFBG";
        }
    	if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("F"))
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


  /**
   * Importiert die Wertpapier-Informationen fuer KEV
   * @param pvBestandsdaten
   * @param pvInstitutsnummer
   * @return
   */
  public boolean importKEVWertpapier(Bestandsdaten pvBestandsdaten, String pvInstitutsnummer)
  {
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

    if (pvBestandsdaten.getSystem().equals("MIDASAKTIV"))
    {
      lvQuelle.append("MIDKEV");
    }
    if (pvBestandsdaten.getSystem().equals("MAVISAKTIV"))
    {
      lvQuelle.append("MAVIKEV");
    }
    this.ivQuelle = lvQuelle.toString();

    // Originator per Institutsnummer setzen
    this.ivOriginator = ValueMapping.changeMandant(pvInstitutsnummer);

    return true;
  }

  /**
   * Importiert die Darlehensinformationen aus LoanIQ fuer KEV
   * @param pvDarlehen
   * @param pvVorlaufsatz
   * @return
   */
  public boolean importKEVLoanIQ(Darlehen pvDarlehen, Vorlaufsatz pvVorlaufsatz)
  {
    // LoanIQ-Daten(Key und Quelle) setzen
    this.ivKey = pvDarlehen.getKontonummer();
    this.ivQuelle = "ALIQKEV";

    // Originator per Institutsnummer setzen
    this.ivOriginator = ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer());

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
    // Key = Kontonummer
    this.ivKey = pvTermingeld.getKontonummer();

    // Defaultmaessig
    this.ivQuelle = "AGELDPFBG";
    // Wenn das Ausplatzierungsmerkmal 'O2' oder 'O4', dann OEPG
    if (pvTermingeld.getAusplatzierungsmerkmal().equals("O2") || pvTermingeld.getAusplatzierungsmerkmal().equals("O4"))
    {
      this.ivQuelle = "AGELDOEPG";
    }

    // Originator per Institutsnummer setzen
    this.ivOriginator = ValueMapping.changeMandant(pvVorlaufsatz.getInstitutsnummer());

    return true;
  }

}
