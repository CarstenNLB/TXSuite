/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Sicherheiten.Sicherheiten2Register;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitPerson;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Utilities.ObjekteListe;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class DarlehenBlock
{
  /**
   * Vorlaufsatz
   */
  private Vorlaufsatz ivVorlaufsatz;

  /**
   * Referenz auf die Sicherheiten2Register (Sicherheiten2Pfandbrief oder Sicherheiten2RefiRegister)
   */
  private Sicherheiten2Register ivSicherheiten2Register;

  /**
   * Logger log4j
   */
  private Logger ivLogger;

  /**
     * Kontonummer des DarlehenBlock
     */
    private String ivKontonummer;
    
    /**
     * Quellsystem des DarlehenBlock
     */
    private String ivQuellsystem;
    
    /**
     * DarlehenBrutto
     */
    private Darlehen ivDarlehenBrutto;
    
    /**
     * DarlehenNetto
     */
    private Darlehen ivDarlehenNetto;
    
    /**
     * Liste der DarlehenFremd
     */
    private ArrayList<Darlehen> ivListeDarlehenFremd;

  /**
     * Konstruktor
     */
    public DarlehenBlock(Vorlaufsatz pvVorlaufsatz, Sicherheiten2Register pvSicherheiten2Register, Logger pvLogger)
    {
        this.ivVorlaufsatz = pvVorlaufsatz;
        this.ivSicherheiten2Register = pvSicherheiten2Register;
        this.ivLogger = pvLogger;
        this.ivKontonummer = "";
        this.ivQuellsystem = "";
        this.ivDarlehenBrutto = null;
        this.ivDarlehenNetto = null;
        this.ivListeDarlehenFremd = new ArrayList<Darlehen>();
    }

    /**
     * Liefert die Kontonummer
     * @return the kontonummer
     */
    public String getKontonummer() {
        return this.ivKontonummer;
    }

    /**
     * @param pvKontonummer the kontonummer to set
     */
    public void setKontonummer(String pvKontonummer) {
        this.ivKontonummer = pvKontonummer;
    }

    /**
     * Liefert das Quellsystem
     * @return the quellsystem
     */
    public String getQuellsystem() {
        return this.ivQuellsystem;
    }

    /**
     * @param pvQuellsystem the quellsystem to set
     */
    public void setQuellsystem(String pvQuellsystem) {
        this.ivQuellsystem = pvQuellsystem;
    }

    /**
     * @return the darlehenLoanIQBrutto
     */
    public Darlehen getDarlehenBrutto() {
        return this.ivDarlehenBrutto;
    }

    /**
     * @param pvDarlehenBrutto the darlehenLoanIQBrutto to set
     */
    public void setDarlehenBrutto(Darlehen pvDarlehenBrutto) {
        this.ivDarlehenBrutto = pvDarlehenBrutto;
    }

    /**
     * @return the darlehenLoanIQNetto
     */
    public Darlehen getDarlehenNetto() {
        return this.ivDarlehenNetto;
    }

    /**
     * @param pvDarlehenNetto the darlehenLoanIQNetto to set
     */
    public void setDarlehenNetto(Darlehen pvDarlehenNetto) {
        this.ivDarlehenNetto = pvDarlehenNetto;
    }

    /**
     * @return the listeDarlehenLoanIQFremd
     */
    public ArrayList<Darlehen> getListeDarlehenFremd() {
        return this.ivListeDarlehenFremd;
    }

    /**
     * @param pvListeDarlehenFremd the listeDarlehenFremd to set
     */
    public void setListeDarlehenFremd(ArrayList<Darlehen> pvListeDarlehenFremd) {
        this.ivListeDarlehenFremd = pvListeDarlehenFremd;
    }
    
    /**
     * Existiert DarlehenBrutto?
     * @return True -> Ja; False -> Nein
     */
    public boolean existsDarlehenBrutto()
    {
      if (ivDarlehenBrutto == null)
          return false;
      else
          return true;
    }
 
    /**
     * Existiert DarlehenNetto?
     * @return True -> Ja; False -> Nein
     */
    public boolean existsDarlehenNetto()
    {
      if (ivDarlehenNetto == null)
          return false;
      else
          return true;
    }

    /**
     * Ist die Liste DarlehenFremd leer?
     * @return True -> Liste leer; False -> Es gibt mindestens ein DarlehenLoanIQFremd
     */
    public boolean isListeDarlehenFremdEmpty()
    {
        return ivListeDarlehenFremd.isEmpty();
    }
    
    /**
     * Zerlegt eine Zeile nach Feldern und fuegt sie in den DarlehenBlock als B,N oder F-Zeile ein
     * @param pvZeile zu zerlegende Zeile
     * @param pvLogger log4j-Logger
     * @return
     */
   public boolean parseDarlehen(String pvZeile, Logger pvLogger)
   {                 
     Darlehen lvDarlehen = new Darlehen(pvLogger);
     String lvHelpKontonummer = "";
     if (pvZeile.startsWith("LOANIQ"))
     {
    	 lvHelpKontonummer = pvZeile.substring(7, 17);
     }
     if (pvZeile.startsWith("MID"))
     {
    	 //System.out.println("Index: " + pvZeile.substring(8).indexOf(";"));
    	 lvHelpKontonummer = pvZeile.substring(8, 8 + pvZeile.substring(8).indexOf(";"));
     }
     if (pvZeile.startsWith("IWHS"))
     {
       lvHelpKontonummer = pvZeile.substring(5, 15);
     }

     if (!ivKontonummer.isEmpty())
     {
       if (!lvHelpKontonummer.equals(ivKontonummer))
       {
         return false;
       }
     }
     
     if (!lvDarlehen.parseDarlehen(pvZeile)) // Parsen der Felder
     {
         pvLogger.error("Datenfehler in Zeile:\n");
         pvLogger.error(pvZeile);
         return false;
     }
     
     // Kontonummer im DarlehenBlock hinterlegen
     if (ivKontonummer.isEmpty())
     {
         ivKontonummer = lvDarlehen.getKontonummer();
     }
     // Quellsystem im DarlehenBlock hinterlegen
     if (ivQuellsystem.isEmpty())
     {
         ivQuellsystem = lvDarlehen.getQuellsystem();
     }
     if (lvDarlehen.getKennzeichenBruttoNettoFremd().equals("B"))
     {
         ivDarlehenBrutto = lvDarlehen;
     }
     if (lvDarlehen.getKennzeichenBruttoNettoFremd().equals("N"))
     {
         ivDarlehenNetto = lvDarlehen;
     }
     if (lvDarlehen.getKennzeichenBruttoNettoFremd().equals("F"))
     {
         ivListeDarlehenFremd.add(lvDarlehen);
     }  
     
     return true; 
   }
   
   /**
    * Verarbeite den DarlehenBlock fuer LoanIQ
    * @param pvFosVerbuergtKonsortial
    * @param pvFosCashflowQuellsystem
    * @param pvOutputDarlehenXML
    */
   public void verarbeiteDarlehenLoanIQ(FileOutputStream pvFosVerbuergtKonsortial, FileOutputStream pvFosCashflowQuellsystem, //SAPCMS_Neu pvSapcms, Vorlaufsatz pvVorlaufsatz,
                                        OutputDarlehenXML pvOutputDarlehenXML)//, Logger pvLogger)
   {  
     // LoanIQ Verarbeitung
   	 if (this.existsDarlehenNetto())
   	 {
   		if (this.getDarlehenNetto().getAusplatzierungsmerkmal().endsWith("0") && StringKonverter.convertString2Double(this.getDarlehenNetto().getRestkapital()) == 0.0)
   		{
       		ivLogger.info("Kein Import von " + this.getKontonummer() + ": Ausplatzierungsmerkmal " + this.getDarlehenNetto().getAusplatzierungsmerkmal() + " mit Restkapital == " + this.getDarlehenNetto().getRestkapital());
       		return;
   		}
   		 
   		// TXS-Transaktionen mit Darlehensinformationen fuellen
   		if (this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("K") || this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("H") ||
   			this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("F") || this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("S") ||
   			this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("O"))
   		{
   			ivLogger.info("Kontonummer " + this.getKontonummer() + " - Ausplatzierungsmerkmal: " + this.getDarlehenNetto().getAusplatzierungsmerkmal());
                     
   			// Verbuergte Geschaefte und Konsortialgeschaefte in die VerbuergtKonsortial-Datei schreiben
   			if (StringKonverter.convertString2Double(this.getDarlehenNetto().getBuergschaftprozent()) > 0.0 || this.getDarlehenNetto().getKennzeichenKonsortialkredit().equals("J"))
   			{
   				if (StringKonverter.convertString2Double(this.getDarlehenNetto().getRestkapital()) == 0.0)
   				{
   					ivLogger.info("Kontonummer " + this.getKontonummer() + " - Restkapital(Netto-Zeile): " + this.getDarlehenNetto().getRestkapital());
   				}
   				BigDecimal lvKonsortialFaktor = new BigDecimal("1.00");
   				BigDecimal lvRestkapitalNetto = StringKonverter.convertString2BigDecimal(this.getDarlehenNetto().getRestkapital());
   				if (this.getDarlehenBrutto() != null)
          {
   				  if (StringKonverter.convertString2Double(this.getDarlehenBrutto().getRestkapital()) > 0.0 &&
   					  lvRestkapitalNetto.doubleValue() > 0.0)
   				  {
   					  lvKonsortialFaktor = lvRestkapitalNetto.divide(StringKonverter.convertString2BigDecimal(this.getDarlehenBrutto().getRestkapital()),  9, RoundingMode.HALF_UP);
   				  }
   				  else
   				  {
   					  ivLogger.info("Kontonummer " + this.getKontonummer() + " - Restkapital(Netto-Zeile): " + this.getDarlehenNetto().getRestkapital());
   					  ivLogger.info("Kontonummer " + this.getKontonummer() + " - Restkapital(Brutto-Zeile): " + this.getDarlehenBrutto().getRestkapital());
   				  }
          }
                   	  
   				BigDecimal lvVerbuergtFaktor = StringKonverter.convertString2BigDecimal(this.getDarlehenNetto().getBuergschaftprozent()).divide(new BigDecimal("100.00"),  9, RoundingMode.HALF_UP);
   				if (lvVerbuergtFaktor.doubleValue() == 0.0)
   				{
   					lvVerbuergtFaktor = new BigDecimal("1.00");
   				}
   				try
   				{                    		  
   					pvFosVerbuergtKonsortial.write((this.getKontonummer() + ";" + lvVerbuergtFaktor.toPlainString() + ";" + this.getDarlehenNetto().getKennzeichenKonsortialkredit() + ";" + lvKonsortialFaktor.toPlainString() + ";" + this.getDarlehenNetto().getRestkapital() + ";" + this.getDarlehenBrutto().getRestkapital() + ";" + this.getDarlehenNetto().getSolldeckung() + ";" + this.getDarlehenBrutto().getSolldeckung() + StringKonverter.lineSeparator).getBytes());
   				}
   				catch (Exception e)
   				{
   					ivLogger.error("Fehler beim Schreiben in die VerbuergtKonsortial-Datei");
   				}
   			}
                     
   			importDarlehen2Transaktion(pvFosCashflowQuellsystem, pvOutputDarlehenXML, null);
   		}
   		else
   		{
   			ivLogger.info("Kontonummer " + this.getKontonummer() + " nicht verarbeitet - Ausplatzierungsmerkmal: " + this.getDarlehenNetto().getAusplatzierungsmerkmal());
   		}
   	}
   	else
   	{
   		ivLogger.info("Keine Nettozeile: " + this.getKontonummer());
   	}
   }

  /**
   * Verarbeite den DarlehenBlock fuer AZ6
   * @param pvFosCashflowQuellsystem
   * @param pvOutputDarlehenXML
   * @param pvMappingRueckmeldungListe
   */
  public void verarbeiteDarlehenAZ6(FileOutputStream pvFosCashflowQuellsystem, OutputDarlehenXML pvOutputDarlehenXML, ObjekteListe pvMappingRueckmeldungListe)
  {
    // AZ6 Verarbeitung
    if (this.existsDarlehenNetto())
    {
      if (this.getDarlehenNetto().getAusplatzierungsmerkmal().endsWith("0") && StringKonverter.convertString2Double(this.getDarlehenNetto().getRestkapital()) == 0.0)
      {
        ivLogger.info("Kein Import von " + this.getKontonummer() + ": Ausplatzierungsmerkmal " + this.getDarlehenNetto().getAusplatzierungsmerkmal() + " mit Restkapital == " + this.getDarlehenNetto().getRestkapital());
        return;
      }

      // TXS-Transaktionen mit Darlehensinformationen fuellen
      if (this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("K") || this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("H") ||
          this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("F") || this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("S") ||
          this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("O"))
      {
        ivLogger.info("Kontonummer " + this.getKontonummer() + " - Ausplatzierungsmerkmal: " + this.getDarlehenNetto().getAusplatzierungsmerkmal());

        // Verbuergte Geschaefte und Konsortialgeschaefte in die VerbuergtKonsortial-Datei schreiben
        if (StringKonverter.convertString2Double(this.getDarlehenNetto().getBuergschaftprozent()) > 0.0 || this.getDarlehenNetto().getKennzeichenKonsortialkredit().equals("J"))
        {
          if (StringKonverter.convertString2Double(this.getDarlehenNetto().getRestkapital()) == 0.0)
          {
            ivLogger.info("Kontonummer " + this.getKontonummer() + " - Restkapital(Netto-Zeile): " + this.getDarlehenNetto().getRestkapital());
          }
          BigDecimal lvKonsortialFaktor = new BigDecimal("1.00");
          BigDecimal lvRestkapitalNetto = StringKonverter.convertString2BigDecimal(this.getDarlehenNetto().getRestkapital());
          if (StringKonverter.convertString2Double(this.getDarlehenBrutto().getRestkapital()) > 0.0 &&
              lvRestkapitalNetto.doubleValue() > 0.0)
          {
            lvKonsortialFaktor = lvRestkapitalNetto.divide(StringKonverter.convertString2BigDecimal(this.getDarlehenBrutto().getRestkapital()),  9, RoundingMode.HALF_UP);
          }
          else
          {
            ivLogger.info("Kontonummer " + this.getKontonummer() + " - Restkapital(Netto-Zeile): " + this.getDarlehenNetto().getRestkapital());
            ivLogger.info("Kontonummer " + this.getKontonummer() + " - Restkapital(Brutto-Zeile): " + this.getDarlehenBrutto().getRestkapital());
          }

        }

        importDarlehen2Transaktion(pvFosCashflowQuellsystem, pvOutputDarlehenXML, pvMappingRueckmeldungListe);
      }
      else
      {
        ivLogger.info("Kontonummer " + this.getKontonummer() + " nicht verarbeitet - Ausplatzierungsmerkmal: " + this.getDarlehenNetto().getAusplatzierungsmerkmal());
      }
    }
    else
    {
      ivLogger.info("Keine Nettozeile: " + this.getKontonummer());
    }
  }

   /**
    * Importiert die Darlehensinformationen in die TXS-Transaktionen
    * @param pvFosCashflowQuellsystem
    * @param pvOutputDarlehenXML
    * @param pvMappingRueckmeldungListe
    */
   private void importDarlehen2Transaktion(FileOutputStream pvFosCashflowQuellsystem, OutputDarlehenXML pvOutputDarlehenXML, ObjekteListe pvMappingRueckmeldungListe)
   {
	   	    
	    // Transaktionen
	    //TXSFinanzgeschaeft ivFinanzgeschaeft;
	    //TXSSliceInDaten ivSliceInDaten;
	    //TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten;
	    //TXSKonditionenDaten ivKondDaten;
	    //TXSKreditKunde ivKredkunde;

        TXSFinanzgeschaeft lvFinanzgeschaeft = new TXSFinanzgeschaeft();
        TXSSliceInDaten lvSliceInDaten = new TXSSliceInDaten();
        TXSFinanzgeschaeftDaten lvFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
        TXSKonditionenDaten lvKondDaten = new TXSKonditionenDaten();
        TXSKreditKunde lvKredkunde = new TXSKreditKunde();

         //ivFinanzgeschaeft.initTXSFinanzgeschaeft();
         //ivSliceInDaten.initTXSSliceInDaten();
         //ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
         //ivKondDaten.initTXSKonditionenDaten();
         //ivKredkunde.initTXSKreditKunde();

         TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
         TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
         TXSSicherheitPerson lvShperson = new TXSSicherheitPerson(); 
         
         boolean lvOkayDarlehen = true;
         
         //Darlehen lvHelpDarlehenLoanIQ = null;
         
         //if (lvOkayDarlehen)
         //{
         lvOkayDarlehen = lvFinanzgeschaeft.importLoanIQ(this, ivVorlaufsatz, ivLogger);
         //}
            
         if (lvOkayDarlehen)
         {              
       	  lvOkayDarlehen = lvFinanzgeschaeftDaten.importLoanIQ(this, ivVorlaufsatz, ivLogger);
         }
         
         if (lvOkayDarlehen)
         {
             lvOkayDarlehen = lvSliceInDaten.importLoanIQ(this, ivVorlaufsatz, ivLogger);
         }
         
         if (lvOkayDarlehen)
         {
             lvOkayDarlehen = lvKondDaten.importLoanIQ(this, ivVorlaufsatz, ivLogger);
         }
         
         if (lvOkayDarlehen)
         {
             lvOkayDarlehen = lvKredkunde.importLoanIQ(this, ivVorlaufsatz, ivLogger);
         }
                  
          // Transaktionen in die Datei schreiben
         if (lvOkayDarlehen)
         {
             // Daten in CashflowQuellsystem-Datei schreiben 
             try
             {
               String lvBuergschaft = "";
               if (StringKonverter.convertString2Double(this.getDarlehenNetto().getBuergschaftprozent()) > 0.0)
               {
                   lvBuergschaft = "J";  
               }
               else
               {
                   lvBuergschaft = "N";
               }
               pvFosCashflowQuellsystem.write((this.getKontonummer() + ";" + lvFinanzgeschaeft.getKey() + ";" + lvFinanzgeschaeft.getOriginator() + ";" +
                                               lvFinanzgeschaeft.getQuelle() + ";" + lvBuergschaft + ";" + this.getDarlehenNetto().getBuergschaftprozent() + ";" +
               		                             lvKondDaten.getMantilg() + ";" + lvKondDaten.getManzins() + ";" + lvKondDaten.getFaellig() + ";;;;;" +
                                               this.getDarlehenNetto().getKennzeichenRollover() + ";" + StringKonverter.lineSeparator).getBytes());
             }
             catch (Exception e)
             {
                 ivLogger.error("Fehler bei der Ausgabe in die CashflowQuellsystem-Datei");
             }
             // Daten in CashflowQuellsystem-Datei schreiben
       	  
             pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionStart());
  
             pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionStart());
             pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionDaten());
             pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionEnde());
  
             pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionStart());
             pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionDaten());
             pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionEnde());

             pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionStart());
             pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionDaten());
             pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionEnde());
  
             pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionStart());
             pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionDaten());
             pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionEnde());
         }
         
         // Ermittle Kredittyp
         int lvKredittyp = ValueMapping.ermittleKredittyp(this.getDarlehenNetto().getAusplatzierungsmerkmal(), this.getDarlehenNetto().getBuergschaftprozent());
         // Sonder-/Uebergangsloesung MIDAS -> Ausplatzierungsmerkmal nicht vorhanden
         // Nicht verwendete Produktschluessel 450, 503, 505, 802, 805 und 811
         if (this.getQuellsystem().startsWith("MID"))
         {
             if (this.getDarlehenBrutto().getProduktschluessel().equals("402") || this.getDarlehenBrutto().getProduktschluessel().equals("404") || this.getDarlehenBrutto().getProduktschluessel().equals("412"))
             {
                 if (!this.getDarlehenBrutto().getBuergschaftprozent().isEmpty())
                 {
                     lvKredittyp = Darlehen.VERBUERGT_KOMMUNAL;
                 }
                 else
                 {
                   lvKredittyp = Darlehen.REIN_KOMMUNAL;
                 }
             }
             if (this.getDarlehenBrutto().getProduktschluessel().equals("821") || this.getDarlehenBrutto().getProduktschluessel().equals("827"))
             {
                 lvKredittyp = Darlehen.FLUGZEUGDARLEHEN;
             }
         }

         //System.out.println("Konto " + ivDarlehenLoanIQBlock.getKontonummer() + " lvKredittyp: " + lvKredittyp);
         
         if (lvOkayDarlehen)
         {
       	  // Wenn Sicherheiten-Daten geladen, dann verarbeiten
       	  if (ivSicherheiten2Register != null)
       	  {
       		  if (lvKredittyp == Darlehen.HYPOTHEK_1A)//|| lvKredittyp == Darlehen.REIN_KOMMUNAL)
       		  {
       			  //ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitObjekte(ivDarlehen.getKontonummer(), "1", ivDarlehen.getBuergschaftprozent(), ivDarlehen.getDeckungsschluessel()));
       			    pvOutputDarlehenXML.printTransaktion(ivSicherheiten2Register.importSicherheitHypotheken(this.getKontonummer(), "", "","1", this.getDarlehenNetto().getBuergschaftprozent(), lvFinanzgeschaeft.getQuelle(), ivVorlaufsatz.getInstitutsnummer(), pvMappingRueckmeldungListe));
       		  }
       		  //// CT 06.11.2019 - Noch in der Testphase
       		  ////if (lvKredittyp == Darlehen.VERBUERGT_KOMMUNAL)
       		  ////{
       		  ////	  pvOutputDarlehenXML.printTransaktion(ivSicherheiten2Register.importSicherheitBuergschaft(this.getKontonummer(), lvFinanzgeschaeft.getQuelle(), this.getDarlehenNetto().getRestkapital(), this.getDarlehenNetto().getBuergschaftprozent(), this.getDarlehenNetto().getAusplatzierungsmerkmal(), this.getDarlehenNetto().getNennbetrag(), this.getDarlehenNetto().getKundennummer(), this.getDarlehenNetto().getBuergennummer(), ivVorlaufsatz.getInstitutsnummer()));
       		  ////}
            //// CT 06.11.2019 - Noch in der Testphase
            if (lvKredittyp == Darlehen.SCHIFFSDARLEHEN)
       		  {
       			    pvOutputDarlehenXML.printTransaktion(ivSicherheiten2Register.importSicherheitSchiff(this.getKontonummer(), lvFinanzgeschaeft.getQuelle(), ivVorlaufsatz.getInstitutsnummer()));
       		  }
       		  if (lvKredittyp == Darlehen.FLUGZEUGDARLEHEN)
       		  {
       		  	  pvOutputDarlehenXML.printTransaktion(ivSicherheiten2Register.importSicherheitFlugzeug(this.getKontonummer(), lvFinanzgeschaeft.getQuelle(), ivVorlaufsatz.getInstitutsnummer()));
       		  }
       	  }
         }            
         
         if (lvOkayDarlehen)
         {
       	   // Verbuergte Darlehen
       	   if (lvKredittyp == Darlehen.KOMMUNALVERBUERGTE_HYPOTHEK || lvKredittyp == Darlehen.VERBUERGT_KOMMUNAL)
       	   {
       		   if (lvKredsh.importDarlehen(this.getDarlehenNetto(), ivVorlaufsatz.getInstitutsnummer(), ivLogger))
       		   {
       			  // Transaktionen in die Datei schreiben
       			  pvOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionStart());
       			  pvOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionDaten());
                 
       			  // TXSSicherheitDaten
       			  lvShdaten.importDarlehen(this.getDarlehenNetto(), ivLogger);
       			  pvOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionStart());
       			  pvOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionDaten());
       			  pvOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionEnde());
                 
       			  // TXSSicherheitPerson
       			  lvShperson.setTXSSicherheitPerson(this.getDarlehenNetto().getBuergennummer(), ivVorlaufsatz.getInstitutsnummer());
       			  pvOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionStart());
       			  pvOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionDaten());
       			  pvOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionEnde());
                 
       			  pvOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionEnde());
       		   }
       	   }
         }
         
         if (lvOkayDarlehen)
         {
             pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionEnde());
         } 
         
         if (lvOkayDarlehen)
         {
         	 // Bei fuer Deckung vorgesehenen Krediten eine Default-Finanzierung anlegen
         	 // nur Schiffe und Flugzeuge
       	   if (this.getDarlehenNetto().getAusplatzierungsmerkmal().equals("S0") || this.getDarlehenNetto().getAusplatzierungsmerkmal().equals("F0"))
       	   {
         			// TXSFinanzgeschaeft setzen --> eigentlich Finanzierung!!!
         			TXSFinanzgeschaeft lvFinanzierung = new TXSFinanzgeschaeft();
         			lvFinanzierung.setKey(this.getDarlehenNetto().getFacilityReferenz());
         			lvFinanzierung.setOriginator(ValueMapping.changeMandant(ivVorlaufsatz.getInstitutsnummer()));
         			lvFinanzierung.setQuelle("TXS");           
             
         			// TXSFinanzgeschaeftDaten setzen
         			TXSFinanzgeschaeftDaten lvFgdaten = new TXSFinanzgeschaeftDaten();
         			lvFgdaten.setAz(this.getDarlehenNetto().getFacilityReferenz());
         			lvFgdaten.setAktivpassiv("1");
         			lvFgdaten.setKat("8");
         			if (this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("S"))
         			{
         				lvFgdaten.setTyp("70");
         			}
         			if (this.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("F"))
         			{
         				lvFgdaten.setTyp("71");
         			}
             
         			// TXSKonditionenDaten setzen
         			TXSKonditionenDaten lvKond = new TXSKonditionenDaten();
         			lvKond.setKondkey(this.getDarlehenNetto().getFacilityReferenz());
                    
         			// TXSFinanzierungKredit
         			pvOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionStart());
         			pvOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionStart());
         			pvOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionDaten());
         			pvOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionEnde());            
         			pvOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionStart());
         			pvOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionDaten());
         			pvOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionEnde());
         			pvOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionEnde());                    
       	   }
         } 
   }
   

}
