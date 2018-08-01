/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.MIDAS.Darlehen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQ;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.SAPCMS.SAPCMS_Neu;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.MappingMIDAS;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class MIDASVerarbeitung 
{
    // Logger fuer MIDAS
    private static Logger LOGGER_MIDAS = Logger.getLogger("TXSMIDASLogger");  
   
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Import-Verzeichnis der MIDAS-Datei
     */
    private String ivImportVerzeichnis;
    
    /**
     * Export-Verzeichnis der TXS-Importdatei
     */
    private String ivExportVerzeichnis;
    
    /**
     * Dateiname der MIDAS-Datei
     */
    private String ivMIDASInputDatei;
    
    /**
     * Dateiname der TXS-Importdatei
     */
    private String ivMIDASOutputDatei;
    
    /**
     * Import-Verzeichnis der SAPCMS-Datei
     */
    private String ivImportVerzeichnisSAPCMS;
    
    /**
     * Dateiname der SAPCMS-Datei
     */
    private String ivSapcmsDatei;

    /**
     * Sicherheiten aus SAPCMS
     */
    private SAPCMS_Neu ivSapcms;
    
    /**
     * Quellsystem-Datei fuer die FRISCO-Verarbeitung
     */
    private String ivCashflowQuellsystemDatei;
    
    /**
     * FileOutputStream fuer CashflowQuellsystem-Datei
     */
    private FileOutputStream ivFosCashflowQuellsystem;
    
    /**
     * Name der KundeRequest-Datei
     */
    private String ivKundeRequestDatei;
        
    /**
     * Ausgabedatei der Kundennummern
     */
    private KundennummernOutput ivKundennummernOutput;
    
    /**
     * Vorlaufsatz der LoanIQ-Datei
     */
    private Vorlaufsatz ivVorlaufsatz;
           
    /**
     * Zaehler fuer die Anzahl der Vorlaufsaetze
     */
    private int ivZaehlerVorlaufsatz;
    
    /**
     * Zaehler fuer die Anzahl der Finanzgeschaefte
     */
    private int ivZaehlerFinanzgeschaefte;
    
    /**
     * Liste der Kundennummern 
     */
    private HashSet<String> ivListeKundennummern;
    
    /**
     * Liste der MIDAS-Geschaefte
     */
    private HashMap<String, DarlehenLoanIQ> ivListeGeschaefte;
    
    /**
     * TXS-Importdatei
     */
    private OutputDarlehenXML ivOutputDarlehenXML;
    
    // Transaktionen
    private TXSFinanzgeschaeft ivFinanzgeschaeft;
    private TXSSliceInDaten ivSliceInDaten;
    private TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten;
    private TXSKonditionenDaten ivKondDaten;
    private TXSKreditKunde ivKredkunde;
    
    // Zaehlvariablen
    private int ivAnzahlK = 0;
    private int ivAnzahlH = 0;
    private int ivAnzahlF = 0;
    private int ivAnzahlS = 0;
    private int ivAnzahlO = 0;
    
    /**
     * Konstruktor fuer Verarbeitung MIDAS
     * @param pvReader 
     */
    public MIDASVerarbeitung(IniReader pvReader)
    {
        this.ivFinanzgeschaeft = new TXSFinanzgeschaeft();
        this.ivSliceInDaten = new TXSSliceInDaten();
        this.ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
        this.ivKondDaten = new TXSKonditionenDaten();
        this.ivKredkunde = new TXSKreditKunde();
        
        ivListeKundennummern = new HashSet<String>();
        ivListeGeschaefte = new HashMap<String, DarlehenLoanIQ>();
        
        if (pvReader != null)
        {

            ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (ivInstitutsnummer.equals("Fehler"))
            {
                LOGGER_MIDAS.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
                        
            ivImportVerzeichnis = pvReader.getPropertyString("MIDAS", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_MIDAS.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivExportVerzeichnis = pvReader.getPropertyString("MIDAS", "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                LOGGER_MIDAS.error("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivMIDASInputDatei =  pvReader.getPropertyString("MIDAS", "MIDASInput-Datei", "Fehler");
            if (ivMIDASInputDatei.equals("Fehler"))
            {
                LOGGER_MIDAS.error("Kein MIDASInput-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            ivMIDASOutputDatei =  pvReader.getPropertyString("MIDAS", "MIDASOutput-Datei", "Fehler");
            if (ivMIDASOutputDatei.equals("Fehler"))
            {
                LOGGER_MIDAS.error("Kein MIDASOutput-Dateiname in der ini-Datei...");
                System.exit(1);
            }           
            
            ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("SAPCMS", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
            {
                LOGGER_MIDAS.error("Kein SAPCMS Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            
            ivSapcmsDatei = pvReader.getPropertyString("SAPCMS", "SAPCMS-Datei", "Fehler");
            if (ivSapcmsDatei.equals("Fehler"))
            {
                LOGGER_MIDAS.error("Kein SAPCMS-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            ivCashflowQuellsystemDatei = pvReader.getPropertyString("MIDAS", "Quellsystem-Datei", "Fehler");
            if (ivCashflowQuellsystemDatei.equals("Fehler"))
            {
                LOGGER_MIDAS.error("Kein Cashflow-Quellsystem-Dateiname in der ini-Datei...");
                System.exit(1);
            }

            ivKundeRequestDatei = pvReader.getPropertyString("MIDAS", "KundeRequestDatei", "Fehler");
            if (ivKundeRequestDatei.equals("Fehler"))
            {
                LOGGER_MIDAS.error("Kein KundeRequest-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            // Verarbeitung starten
            startVerarbeitung();
        }
    }

    /**
     * Start der Verarbeitung
     */
    private void startVerarbeitung()
    {       
        Calendar lvCal = Calendar.getInstance();
        CalendarHelper lvCh = new CalendarHelper();

        LOGGER_MIDAS.info("Start: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
                
        // Cashflow-Quellsystem
        File lvFileCashflowQuellsystem = new File(ivExportVerzeichnis + "\\" + ivCashflowQuellsystemDatei);
        try
        {
            ivFosCashflowQuellsystem = new FileOutputStream(lvFileCashflowQuellsystem);
        }
        catch (Exception e)
        {
            LOGGER_MIDAS.error("Konnte CashflowQuellsystem-Datei nicht oeffnen!");
        }
        // Cashflow-Quellsystem
                
        ivSapcms = new SAPCMS_Neu(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, LOGGER_MIDAS);
        
        // Darlehen XML-Datei im TXS-Format
        ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivMIDASOutputDatei);
        ivOutputDarlehenXML.openXML();
        ivOutputDarlehenXML.printXMLStart();
        ivOutputDarlehenXML.printTXSImportDatenStart();
        
        readMIDAS(ivImportVerzeichnis + "\\" + ivMIDASInputDatei);
        verarbeiteMIDAS();

        ivOutputDarlehenXML.printTXSImportDatenEnde();
        ivOutputDarlehenXML.closeXML();
        
        lvCal = Calendar.getInstance();
        LOGGER_MIDAS.info(this.getStatistik());
        
        // Cashflow-Quellsystem
        try
        {
            ivFosCashflowQuellsystem.close();
        }
        catch (Exception e)
        {
            LOGGER_MIDAS.error("Fehler beim Schliessen der CashflowQuellsystem-Datei");
        }
        // Cashflow-Quellsystem
        
        // KundeRequest-Datei schliessen
        ivKundennummernOutput.close();               
        
        LOGGER_MIDAS.info("Ende: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
    }
    
    /**
     * Liest die MIDAS-Datei/-Daten ein
     * @param pvDateiname Dateiname der MIDAS-Datei
     */
    private void readMIDAS(String pvDateiname)
    {
        ivZaehlerVorlaufsatz = 0;
        ivZaehlerFinanzgeschaefte = 0;
        String lvZeile = null;
        ivVorlaufsatz = new Vorlaufsatz();
              
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File ivFileLoanIQ = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileLoanIQ);
        }
        catch (Exception e)
        {
            LOGGER_MIDAS.error("Konnte MIDAS-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
        boolean lvStart = true;
        
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen MIDAS-Datei
            {
                if (lvStart)
                {
                    ivVorlaufsatz.parseVorlaufsatz(lvZeile);
                    ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()));
                    ivZaehlerVorlaufsatz++;
                    lvStart = false;     
                    
                    // KundeRequest-Datei oeffnen
                    ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_MIDAS);
                    ivKundennummernOutput.open();
                    ivKundennummernOutput.printVorlaufsatz(ivVorlaufsatz.getInstitutsnummer(), "Pfandbrief");
               }
               else
               {
            	    // DarlehenLoanIQ initialisieren
            	    DarlehenLoanIQ lvDarlehenLoanIQ = new DarlehenLoanIQ(LOGGER_MIDAS);
                    if (lvDarlehenLoanIQ.parseDarlehen(lvZeile)) // Parsen der Felder
                    {       
                        ivZaehlerFinanzgeschaefte++;
                        ivListeGeschaefte.put(MappingMIDAS.ermittleMIDASKontonummer(lvDarlehenLoanIQ.getQuellsystem(), lvDarlehenLoanIQ.getKontonummer()), lvDarlehenLoanIQ);
                    }
                    else
                    { 	// Fehlerhafte Zeile eingelesen, trotzdem Zaehler um eins erhoehen
                    	LOGGER_MIDAS.error("Fehlerhafte Zeile...");
                    	LOGGER_MIDAS.error(lvZeile);
                        ivZaehlerFinanzgeschaefte++;
                    }
               }
            }
        }
        catch (Exception e)
        {
            LOGGER_MIDAS.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
         
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_MIDAS.error("Konnte MIDAS-Datei nicht schliessen!");
        }           
    }

    /**
     * Liefert eine Statistik als String
     * @return String mit Statistik
     */
    private String getStatistik()
    {
        StringBuilder lvOut = new StringBuilder();
        lvOut.append(ivVorlaufsatz.toString());        
        lvOut.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerFinanzgeschaefte + " Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlK + " mit Ausplatzierungsmerkmal Kx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlH + " mit Ausplatzierungsmerkmal Hx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlF + " mit Ausplatzierungsmerkmal Fx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlS + " mit Ausplatzierungsmerkmal Sx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlO + " mit Ausplatzierungsmerkmal Ox" + StringKonverter.lineSeparator);
        return lvOut.toString();
    }
     
    /**
     * Verarbeite die MIDAS-Geschaefte
     */
    private void verarbeiteMIDAS()
    {  
    	Collection<DarlehenLoanIQ> lvCollectionGeschaefte = ivListeGeschaefte.values();
    	Iterator<DarlehenLoanIQ> lvIterGeschaefte = lvCollectionGeschaefte.iterator();
    	
    	while (lvIterGeschaefte.hasNext())
    	{
    		DarlehenLoanIQ lvDarlehenLoanIQ = lvIterGeschaefte.next();
    		//LOGGER_MIDAS.info("Konto: " + lvDarlehenLoanIQ.getKontonummer());
            if (lvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("F") || lvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("H") ||
                lvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K"))
            	{
            		if (lvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("K")) ivAnzahlK++;
            		if (lvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("H")) ivAnzahlH++;
            		if (lvDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("F")) ivAnzahlF++;
            		//if (ivDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("S")) ivAnzahlS++;
            		//if (ivDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("O")) ivAnzahlO++;

            		try
            		{
            			// Buergennummer in die KundeRequest-Datei schreiben
            			if (!lvDarlehenLoanIQ.getBuergennummer().isEmpty() && !lvDarlehenLoanIQ.getBuergennummer().equals("9999999999"))
            			{
            				if (!ivListeKundennummern.contains(MappingKunde.extendKundennummer(lvDarlehenLoanIQ.getBuergennummer(), LOGGER_MIDAS)))
            				{
            					ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehenLoanIQ.getBuergennummer(), LOGGER_MIDAS));   
            					ivListeKundennummern.add(MappingKunde.extendKundennummer(lvDarlehenLoanIQ.getBuergennummer(), LOGGER_MIDAS));
            				}    			  
            			}
            			// Kundennummer in die KundeRequest-Datei schreiben
            			if (!lvDarlehenLoanIQ.getKundennummer().isEmpty() && !lvDarlehenLoanIQ.getKundennummer().equals("9999999999"))
            			{
            				if (!ivListeKundennummern.contains(MappingKunde.extendKundennummer(lvDarlehenLoanIQ.getKundennummer(), LOGGER_MIDAS)))
            				{
            					ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehenLoanIQ.getKundennummer(), LOGGER_MIDAS));
            					ivListeKundennummern.add(MappingKunde.extendKundennummer(lvDarlehenLoanIQ.getKundennummer(), LOGGER_MIDAS));
            				}
            			}
            		}
            		catch (Exception exp)
            		{
            			LOGGER_MIDAS.error("Konnte Buergen- und Kundennummer nicht in KundeRequest-Datei schreiben...");
            		}

                    if (StringKonverter.convertString2Double(lvDarlehenLoanIQ.getRestkapital()) > 0.0)
                    {
                        importDarlehen2Transaktion(lvDarlehenLoanIQ);
                    }
                    else
                    {
                        LOGGER_MIDAS.info("Kontonummer " + lvDarlehenLoanIQ.getKontonummer() + " nicht verarbeitet - Deckungskennzeichen " + lvDarlehenLoanIQ.getDeckungsschluessel() + " und Restkapital " + lvDarlehenLoanIQ.getRestkapital());
                    }
                }
    	}
    	
    }
    
    /**
     * Importiert die Darlehensinformationen in die TXS-Transaktionen
     * @param pvDarlehenLoanIQ
     */
    private void importDarlehen2Transaktion(DarlehenLoanIQ pvDarlehenLoanIQ)
    {
          ivFinanzgeschaeft.initTXSFinanzgeschaeft();
          ivSliceInDaten.initTXSSliceInDaten();
          ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
          ivKondDaten.initTXSKonditionenDaten();
          ivKredkunde.initTXSKreditKunde();
          
          //boolean lvOkayDarlehen = true;
          
          //if (lvOkayDarlehen)
          //{
          boolean lvOkayDarlehen = ivFinanzgeschaeft.importMIDAS(pvDarlehenLoanIQ, ivVorlaufsatz);
          //}
             
          if (lvOkayDarlehen)
          {           
              lvOkayDarlehen = ivFinanzgeschaeftDaten.importMIDAS(pvDarlehenLoanIQ, ivVorlaufsatz, LOGGER_MIDAS);
          }
          
          if (lvOkayDarlehen)
          {
              lvOkayDarlehen = ivSliceInDaten.importMIDAS(pvDarlehenLoanIQ, ivVorlaufsatz, LOGGER_MIDAS);
          }
          
          if (lvOkayDarlehen)
          {
              lvOkayDarlehen = ivKondDaten.importMIDAS(pvDarlehenLoanIQ, LOGGER_MIDAS);
          }
          
          if (lvOkayDarlehen)
          {
              lvOkayDarlehen = ivKredkunde.importMIDAS(pvDarlehenLoanIQ, ivVorlaufsatz);
          }
                   
           // Transaktionen in die Datei schreiben
          if (lvOkayDarlehen)
          {
              // Daten in CashflowQuellsystem-Datei schreiben
              try
              {
                String lvBuergschaft = new String();  
                if (StringKonverter.convertString2Double(pvDarlehenLoanIQ.getBuergschaftprozent()) > 0.0)
                {
                    lvBuergschaft = "J";  
                }
                else
                {
                    lvBuergschaft = "N";
                }
                ivFosCashflowQuellsystem.write((pvDarlehenLoanIQ.getKontonummer() + ";" + ivFinanzgeschaeft.getKey() + ";" + ivFinanzgeschaeft.getOriginator() + ";" + ivFinanzgeschaeft.getQuelle() + ";" + lvBuergschaft + ";" + pvDarlehenLoanIQ.getBuergschaftprozent() + ";" + StringKonverter.lineSeparator).getBytes());
              }
              catch (Exception e)
              {
                  LOGGER_MIDAS.error("Fehler bei der Ausgabe in die CashflowQuellsystem-Datei");
              }
              // Daten in CashflowQuellsystem-Datei schreiben
        	  
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionStart());
   
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionStart());
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionDaten());
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionEnde());
   
              ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionStart());
              ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionDaten());
              ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionEnde());

              ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
              ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
              ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());
   
              ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionStart());
              ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionDaten());
              ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionEnde());
          }
          
          // Ermittle Kredittyp
          int lvKredittyp = ValueMapping.ermittleKredittyp(pvDarlehenLoanIQ.getAusplatzierungsmerkmal(), pvDarlehenLoanIQ.getBuergschaftprozent()); //pvDarlehenLoanIQ.ermittleKredittyp();
          // Sonder-/Uebergangsloesung MIDAS -> Ausplatzierungsmerkmal nicht vorhanden
          // Nicht verwendete Produktschluessel 450, 503, 505, 802, 805 und 811
          if (pvDarlehenLoanIQ.getProduktschluessel().equals("402") || pvDarlehenLoanIQ.getProduktschluessel().equals("404") || pvDarlehenLoanIQ.getProduktschluessel().equals("412"))
          {
        	  if (!pvDarlehenLoanIQ.getBuergschaftprozent().isEmpty())
        	  {
        		  lvKredittyp = DarlehenLoanIQ.VERBUERGT_KOMMUNAL;
        	  }
        	  else
        	  {
        		  lvKredittyp = DarlehenLoanIQ.REIN_KOMMUNAL;
              }
          }
          if (pvDarlehenLoanIQ.getProduktschluessel().equals("821") || pvDarlehenLoanIQ.getProduktschluessel().equals("827"))
          {
        	  lvKredittyp = DarlehenLoanIQ.FLUGZEUGDARLEHEN;
          }

          LOGGER_MIDAS.info("Konto " + pvDarlehenLoanIQ.getKontonummer() + " lvKredittyp: " + lvKredittyp);
          
          if (lvOkayDarlehen)
          {
        	  // Wenn SAP CMS Daten geladen, dann verarbeiten
        	  if (ivSapcms != null)
        	  {
        		  LOGGER_MIDAS.info("SAPCMS;" +ivFinanzgeschaeft.getKey() + ";" + ivSapcms.getSicherheitId(ivFinanzgeschaeft.getKey()) + ";");
            ////if (lvKredittyp == DarlehenLoanIQ.HYPOTHEK_1A)//|| lvKredittyp == DarlehenLoanIQ.REIN_KOMMUNAL)
            ////  {
                  // Es wird protokolliert, ob SAP CMS vorliegen
                  //if (!ivSapcms.existsSicherheitObjekt(ivDarlehen.getKontonummer()))
                  //{
                  //   LOGGER_LOANIQ.info("Kein SAP CMS;" + ivDarlehen.getKontonummer());
                  //}
                  //else
                  //{
                  //    LOGGER_LOANIQ.info("SAP CMS vorhanden;" + ivDarlehen.getKontonummer());
                  //}
                  //String helpKredittyp = new String();
                  //if (lvKredittyp == DarlehenLoanIQ.HYPOTHEK_1A)
                  //{
                  //    helpKredittyp = "A";
                  //}
                  //if (lvKredittyp == DarlehenLoanIQ)
                  //{
                  //    helpKredittyp = "A";
                  //}                  
                  //ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitObjekte(ivDarlehen.getKontonummer(), "A", ivDarlehen.getBuergschaftprozent(), ivDarlehen.getDeckungsschluessel()));
                  ////ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitObjekte(ivDarlehenLoanIQBlock.getKontonummer(), new String(), "A", lvHelpDarlehenLoanIQ.getBuergschaftprozent()));
        		  ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitMIDAS(ivFinanzgeschaeft.getKey(), new String(), "A", pvDarlehenLoanIQ.getRestkapital(), pvDarlehenLoanIQ.getBuergschaftprozent(), pvDarlehenLoanIQ.getAusplatzierungsmerkmal()));            	
              ////}
        	  }
          }           
          
          if (lvOkayDarlehen)
          {
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
          } 
          
          if (lvOkayDarlehen)
          {
            // Default-Finanzierung fuer Flugzeuge anlegen
            if (lvKredittyp == DarlehenLoanIQ.FLUGZEUGDARLEHEN)
            {
                  // TXSFinanzgeschaeft setzen --> eigentlich Finanzierung!!!
                  TXSFinanzgeschaeft lvFinanzierung = new TXSFinanzgeschaeft();
                  lvFinanzierung.setKey(pvDarlehenLoanIQ.getKontonummer());
                  lvFinanzierung.setOriginator(ValueMapping.changeMandant(ivVorlaufsatz.getInstitutsnummer()));
                  lvFinanzierung.setQuelle("AMIDFLUG");           
              
                  // TXSFinanzgeschaeftDaten setzen
                  TXSFinanzgeschaeftDaten lvFgdaten = new TXSFinanzgeschaeftDaten();
                  lvFgdaten.setAz(pvDarlehenLoanIQ.getKontonummer());
                  lvFgdaten.setAktivpassiv("1");
                  lvFgdaten.setKat("8");
                  lvFgdaten.setTyp("71");
              
                  // TXSKonditionenDaten setzen
                  TXSKonditionenDaten lvKond = new TXSKonditionenDaten();
                  lvKond.setKondkey(pvDarlehenLoanIQ.getKontonummer());
                     
                  // TXSFinanzierungKredit
                  ivOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionStart());
                  ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionStart());
                  ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionDaten());
                  ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionEnde());            
                  ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionStart());
                  ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionDaten());
                  ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionEnde());
                  ivOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionEnde());                    
            }
          }
    }

}
