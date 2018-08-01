/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.Darlehen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQ;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenLoanIQBlock;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.SAPCMS.SAPCMS_Neu;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitPerson;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class LoanIQVerarbeitung 
{
    // Logger fuer LoanIQ
    private static Logger LOGGER_LOANIQ = Logger.getLogger("TXSLoanIQLogger");  
   
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Import-Verzeichnis der LoanIQ-Datei
     */
    private String ivImportVerzeichnis;
    
    /**
     * Export-Verzeichnis der TXS-Importdatei
     */
    private String ivExportVerzeichnis;
    
    /**
     * Dateiname der LoanIQ-Datei
     */
    private String ivLoanIQInputDatei;
    
    /**
     * Dateiname der TXS-Importdatei
     */
    private String ivLoanIQOutputDatei;
    
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
     * Import-Verzeichnis der Rueckmeldung
     */
    private String ivImportVerzeichnisRueckmeldung;
    
    /**
     * Dateiname der VerbuergtKonsortial-Datei
     */
    private String ivVerbuergtKonsortialDatei;
    
    /**
     * Dateiname der Ausplatzierungsmerkmal-Datei
     */
    private String ivAusplatzierungsmerkmalDatei;
    
    /**
     * FileOutputStream fuer CashflowQuellsystem-Datei
     */
    private FileOutputStream ivFosCashflowQuellsystem;
    
    /**
     * FileOutputStream fuer nicht relevante Ausplatzierungsmerkmale
     */
    private FileOutputStream ivFosAusplatzierungsmerkmal;
    
    /**
     * FileOutputStream fuer VerbuergtKonsortial-Datei
     */
    private FileOutputStream ivFosVerbuergtKonsortial;
    
    /**
     * KundeRequest-Datei
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
     * Ein DarlehenLoanIQBlock
     */
    private DarlehenLoanIQBlock ivDarlehenLoanIQBlock;
    
    /**
     * Zaehler fuer die Anzahl der Vorlaufsaetze
     */
    private int ivZaehlerVorlaufsatz;
    
    /**
     * Zaehler fuer die Anzahl der Brutto-Finanzgeschaefte
     */
    private int ivZaehlerFinanzgeschaefteBrutto;
    
    /**
     * Zaehler fuer die Anzahl der Netto-Finanzgeschaefte
     */
    private int ivZaehlerFinanzgeschaefteNetto;
    
    /**
     * Zaehler fuer die Anzahl der Fremd-Finanzgeschaefte
     */
    private int ivZaehlerFinanzgeschaefteFremd;
    
    /**
     * Ausgabedatei der TXS-Importdatei
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
     * Konstruktor fuer LoanIQ Verarbeitung
     * @param pvReader 
     */
    public LoanIQVerarbeitung(IniReader pvReader)
    {
        this.ivFinanzgeschaeft = new TXSFinanzgeschaeft();
        this.ivSliceInDaten = new TXSSliceInDaten();
        this.ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
        this.ivKondDaten = new TXSKonditionenDaten();
        this.ivKredkunde = new TXSKreditKunde();
        
        if (pvReader != null)
        {
            ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (ivInstitutsnummer.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
                        
            ivImportVerzeichnis = pvReader.getPropertyString("LoanIQ", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivExportVerzeichnis = pvReader.getPropertyString("LoanIQ", "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivLoanIQInputDatei =  pvReader.getPropertyString("LoanIQ", "LoanIQInput-Datei", "Fehler");
            if (ivLoanIQInputDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein LoanIQInput-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            ivLoanIQOutputDatei =  pvReader.getPropertyString("LoanIQ", "LoanIQOutput-Datei", "Fehler");
            if (ivLoanIQOutputDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein LoanIQOutput-Dateiname in der ini-Datei...");
                System.exit(1);
            }           
            
            ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("SAPCMS", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein SAPCMS Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            
            ivSapcmsDatei = pvReader.getPropertyString("SAPCMS", "SAPCMS-Datei", "Fehler");
            if (ivSapcmsDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein SAPCMS-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            ivCashflowQuellsystemDatei = pvReader.getPropertyString("LoanIQ", "Quellsystem-Datei", "Fehler");
            if (ivCashflowQuellsystemDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein Cashflow-Quellsystem-Dateiname in der ini-Datei...");
                System.exit(1);
            }

            ivKundeRequestDatei = pvReader.getPropertyString("LoanIQ", "KundeRequestDatei", "Fehler");
            if (ivKundeRequestDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein KundeRequest-Dateiname in der ini-Datei...");
                System.exit(1); 
            }

            ivImportVerzeichnisRueckmeldung = pvReader.getPropertyString("Rueckmeldung", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnisRueckmeldung.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein Import-Verzeichnis fuer die Rueckmeldung in der ini-Datei...");
                System.exit(1);
            }
            
            ivAusplatzierungsmerkmalDatei = pvReader.getPropertyString("Rueckmeldung", "AusplatzierungsmerkmalLoanIQ-Datei", "Fehler");
            if (ivAusplatzierungsmerkmalDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein Ausplatzierungsmerkmal-Dateiname in der ini-Datei...");
                System.exit(1); 
            }
            
            ivVerbuergtKonsortialDatei = pvReader.getPropertyString("Rueckmeldung", "VerbuergtKonsortialDatei", "Fehler");
            if (ivVerbuergtKonsortialDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein VerbuergtKonsortial-Dateiname in der ini-Datei...");
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
    	// Initialisierung der Zaehler
        ivZaehlerVorlaufsatz = 0;
        ivZaehlerFinanzgeschaefteBrutto = 0;
        ivZaehlerFinanzgeschaefteNetto = 0;
        ivZaehlerFinanzgeschaefteFremd = 0;
    	
        // VerbuergtKonsortial-Datei oeffnen (zum Schreiben)
        File lvFileVerbuergtKonsortial = new File(ivImportVerzeichnisRueckmeldung + "\\" + ivVerbuergtKonsortialDatei);
        try
        {
        	ivFosVerbuergtKonsortial = new FileOutputStream(lvFileVerbuergtKonsortial);
        }
        catch (Exception e)
        {
        	LOGGER_LOANIQ.error("Konnte VerbuergtKonsortial-Datei nicht oeffnen!");
        }
        
        // Cashflow-Quellsystem oeffnen (zum Schreiben)
        File lvFileCashflowQuellsystem = new File(ivExportVerzeichnis + "\\" + ivCashflowQuellsystemDatei);
        try
        {
            ivFosCashflowQuellsystem = new FileOutputStream(lvFileCashflowQuellsystem);
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ.error("Konnte CashflowQuellsystem-Datei nicht oeffnen!");
        }
        // Cashflow-Quellsystem oeffnen (zum Schreiben)
        
        // Ausplatzierungsmerkmal-Datei oeffnen (zum Schreiben)
        File lvFileAusplatzierungsmerkmal = new File(ivImportVerzeichnisRueckmeldung + "\\" + ivAusplatzierungsmerkmalDatei);
        try
        {
        	ivFosAusplatzierungsmerkmal = new FileOutputStream(lvFileAusplatzierungsmerkmal);
        }
        catch (Exception e)
        {
        	LOGGER_LOANIQ.error("Konnte Ausplatzierungsmerkmal-Datei nicht oeffnen!");
        }
        
        // SAP CMS-Daten einlesen
        ivSapcms = new SAPCMS_Neu(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, LOGGER_LOANIQ);
        
        // Darlehen XML-Datei im TXS-Format
        ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivLoanIQOutputDatei);
        ivOutputDarlehenXML.openXML();
        ivOutputDarlehenXML.printXMLStart();
        ivOutputDarlehenXML.printTXSImportDatenStart();
        
        // LoanIQ-Daten einlesen und verarbeiten
        readLoanIQ(ivImportVerzeichnis + "\\" + ivLoanIQInputDatei);

        ivOutputDarlehenXML.printTXSImportDatenEnde();
        ivOutputDarlehenXML.closeXML();
        
        // Statistik ausgeben
        LOGGER_LOANIQ.info(getStatistik());
        
        // Cashflow-Quellsystem schliessen
        try
        {
            ivFosCashflowQuellsystem.close();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ.error("Fehler beim Schliessen der CashflowQuellsystem-Datei");
        }
        // Cashflow-Quellsystem schliessen

        // VerbuergtKonsortial-Datei schliessen
        try
        {
            ivFosVerbuergtKonsortial.close();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ.error("Fehler beim Schliessen der VerbuergtKonsortial-Datei");
        }

        // Ausplatzierungsmerkmal-Datei schliessen
        try
        {
            ivFosAusplatzierungsmerkmal.close();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ.error("Fehler beim Schliessen der Ausplatzierungsmerkmal-Datei");
        }

        
        // KundeRequest-Datei schliessen
        try
        {
        	ivKundennummernOutput.close();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ.error("Fehler beim Schliessen der KundeRequest-Datei");        	
        }
    }
    
    /**
     * Einlesen und Verarbeiten der LoanIQ-Daten
     * @oaram pvDateiname
     */
    private void readLoanIQ(String pvDateiname)
    {
        String lvZeile = null;
        ivVorlaufsatz = new Vorlaufsatz();
        ivDarlehenLoanIQBlock = new DarlehenLoanIQBlock();
              
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File ivFileLoanIQ = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileLoanIQ);
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ.error("Konnte LoanIQ-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
        boolean lvStart = true;
        
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen LoanIQ-Datei
            {
                if (lvStart)
                {
                    ivVorlaufsatz.parseVorlaufsatz(lvZeile);
                    ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()));
                    ivZaehlerVorlaufsatz++;
                    lvStart = false;
                    
                    // KundeRequest-Datei oeffnen
                    ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_LOANIQ);
                    ivKundennummernOutput.open();
                    ivKundennummernOutput.printVorlaufsatz(ivInstitutsnummer, "Pfandbrief");
               }
                else
                {
                    //System.out.println("lvZeile: " + lvZeile);
                    if (!ivDarlehenLoanIQBlock.parseDarlehen(lvZeile, LOGGER_LOANIQ)) // Parsen der Felder
                    {        
                    	// Unterschiedliche Kontonummer -> Darlehen verarbeiten                    	
                    	verarbeiteDarlehen();
                        // Neuen LoanIQ-Block anlegen
                        ivDarlehenLoanIQBlock = new DarlehenLoanIQBlock();
                        // Zeile mit neuer Kontonummer muss noch verarbeitet werden
                        if (ivDarlehenLoanIQBlock.parseDarlehen(lvZeile, LOGGER_LOANIQ)) // Parsen der Felder
                        {
                        	if (lvZeile.charAt(56) == 'B') ivZaehlerFinanzgeschaefteBrutto++;
                        	if (lvZeile.charAt(56) == 'N') ivZaehlerFinanzgeschaefteNetto++;
                        	if (lvZeile.charAt(56) == 'F') ivZaehlerFinanzgeschaefteFremd++;
                        }
                    }
                    else
                    {
                    	if (lvZeile.charAt(56) == 'B') ivZaehlerFinanzgeschaefteBrutto++;
                    	if (lvZeile.charAt(56) == 'N') ivZaehlerFinanzgeschaefteNetto++;
                    	if (lvZeile.charAt(56) == 'F') ivZaehlerFinanzgeschaefteFremd++;
                    }
                }
            }
            // Letzten Kredit auch verarbeiten - CT 13.09.2017
            verarbeiteDarlehen();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
              
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ.error("Konnte LoanIQ-Datei nicht schliessen!");
        }           
    }

    /**
     * Liefert eine Statistik als String
     * @return 
     */
    private String getStatistik()
    {
        StringBuffer lvOut = new StringBuffer();
        lvOut.append(ivVorlaufsatz.toString());
        
        lvOut.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerFinanzgeschaefteBrutto + " Brutto-Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerFinanzgeschaefteNetto + " Netto-Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerFinanzgeschaefteFremd + " Fremd-Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlK + " mit Ausplatzierungsmerkmal Kx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlH + " mit Ausplatzierungsmerkmal Hx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlF + " mit Ausplatzierungsmerkmal Fx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlS + " mit Ausplatzierungsmerkmal Sx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlO + " mit Ausplatzierungsmerkmal Ox" + StringKonverter.lineSeparator);
        
        return lvOut.toString();
    }

    /**
     * Verarbeite den DarlehenLoanIQBlock
     */
    private void verarbeiteDarlehen()
    {  
          // LoanIQ Verarbeitung
    	if (ivDarlehenLoanIQBlock.existsDarlehenLoanIQNetto())
    	{
    		//LOGGER_LOANIQ.info("Einzelkonten;" + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getKontonummer() + ";" +
    		//		           ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getKundennummer() + ";" +
    		//		           ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getDeckungsschluessel() + ";" +
    		//		           ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal() + ";" +
    		//		           ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getSolldeckung() + ";" +
    		//		           ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getVerwaltendeOE() + ";" +
    		//		           ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getProduktschluessel() + ";;;" +
    		//		           ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getRestkapital() + ";" +
    		//		           ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getZusagedatum() + ";" +
    		//		           ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAuszahlungsdatum() + ";" +
    		//		           ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getLaufzeitende());
            
    		if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("A") || ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("C") ||
    			ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("D") || ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("E") ||	
    			ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("P") || ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("R") ||
    			ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("W") || ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("N"))
    		{
    			try
    			{                    		  
    				ivFosAusplatzierungsmerkmal.write((ivDarlehenLoanIQBlock.getKontonummer() + ";" + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal() + StringKonverter.lineSeparator).getBytes());
    			}
    			catch (Exception e)
    			{
    				LOGGER_LOANIQ.error("Fehler beim Schreiben in die Ausplatzierungsmerkmal-Datei");
    			}
    			LOGGER_LOANIQ.info("Kontonummer " + ivDarlehenLoanIQBlock.getKontonummer() + " - Nicht relevantes Ausplatzierungsmerkmal " + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal());        		  
    			return;
    		}
    		
    		if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().endsWith("0") && StringKonverter.convertString2Double(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getRestkapital()) == 0.0)
    		{
        		LOGGER_LOANIQ.info("Kein Import von " + ivDarlehenLoanIQBlock.getKontonummer() + ": Ausplatzierungsmerkmal " + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal() + " mit Restkapital == " + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getRestkapital()); 
        		return;
    		}
    		
    		// Buergennummer in die KundeRequest-Datei schreiben
    		if (!ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getBuergennummer().isEmpty())
    		{
    			ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getBuergennummer(), LOGGER_LOANIQ));   
    		}
    		// Kundennummer in die KundeRequest-Datei schreiben
    		if (!ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getKundennummer().isEmpty())
    		{
    			ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getKundennummer(), LOGGER_LOANIQ));
    		}
  
    		// TXS-Transaktionen mit Darlehensinformationen fuellen
    		if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("K") || ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("H") ||
    			ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("F") || ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("S") ||
    			ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("O"))
    		{
    			LOGGER_LOANIQ.info("Kontonummer " + ivDarlehenLoanIQBlock.getKontonummer() + " - Ausplatzierungsmerkmal: " + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal());
    			if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("K")) ivAnzahlK++;
    			if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("H")) ivAnzahlH++;
    			if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("F")) ivAnzahlF++;
    			if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("S")) ivAnzahlS++;
    			if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("O")) ivAnzahlO++;
                      
    			// Verbuergte Geschaefte und Konsortialgeschaefte in die VerbuergtKonsortial-Datei schreiben
    			if (StringKonverter.convertString2Double(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getBuergschaftprozent()) > 0.0 || ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getKennzeichenKonsortialkredit().equals("J"))
    			{
    				if (StringKonverter.convertString2Double(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getRestkapital()) == 0.0)
    				{
    					LOGGER_LOANIQ.info("Kontonummer " + ivDarlehenLoanIQBlock.getKontonummer() + " - Restkapital(Netto-Zeile): " + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getRestkapital());        		  
    				}
    				BigDecimal lvKonsortialFaktor = new BigDecimal("1.00");
    				BigDecimal lvRestkapitalNetto = StringKonverter.convertString2BigDecimal(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getRestkapital());
    				if (StringKonverter.convertString2Double(ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getRestkapital()) > 0.0 &&
    					lvRestkapitalNetto.doubleValue() > 0.0)
    				{
    					lvKonsortialFaktor = lvRestkapitalNetto.divide(StringKonverter.convertString2BigDecimal(ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getRestkapital()),  9, RoundingMode.HALF_UP);
    				}
    				else
    				{
    					LOGGER_LOANIQ.info("Kontonummer " + ivDarlehenLoanIQBlock.getKontonummer() + " - Restkapital(Netto-Zeile): " + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getRestkapital());
    					LOGGER_LOANIQ.info("Kontonummer " + ivDarlehenLoanIQBlock.getKontonummer() + " - Restkapital(Brutto-Zeile): " + ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getRestkapital());
    				}
                    	  
    				BigDecimal lvVerbuergtFaktor = StringKonverter.convertString2BigDecimal(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getBuergschaftprozent()).divide(new BigDecimal("100.00"),  9, RoundingMode.HALF_UP);
    				if (lvVerbuergtFaktor.doubleValue() == 0.0)
    				{
    					lvVerbuergtFaktor = new BigDecimal("1.00");
    				}
    				try
    				{                    		  
    					ivFosVerbuergtKonsortial.write((ivDarlehenLoanIQBlock.getKontonummer() + ";" + lvVerbuergtFaktor.toPlainString() + ";" + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getKennzeichenKonsortialkredit() + ";" + lvKonsortialFaktor.toPlainString() + ";" + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getRestkapital() + ";" + ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getRestkapital() + ";" + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getSolldeckung() + ";" + ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getSolldeckung() + StringKonverter.lineSeparator).getBytes());
    				}
    				catch (Exception e)
    				{
    					LOGGER_LOANIQ.error("Fehler beim Schreiben in die VerbuergtKonsortial-Datei");
    				}
    			}
                      
    			importDarlehen2Transaktion();
    		}
    		else
    		{
    			LOGGER_LOANIQ.info("Kontonummer " + ivDarlehenLoanIQBlock.getKontonummer() + " nicht verarbeitet - Ausplatzierungsmerkmal: " + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal());
    		}
    	}
    	else
    	{
    		LOGGER_LOANIQ.info("Keine Nettozeile: " + ivDarlehenLoanIQBlock.getKontonummer());
    	}
    }
      
    /**
     * Importiert die Darlehensinformationen in die TXS-Transaktionen
     */
    private void importDarlehen2Transaktion()
    {
          ivFinanzgeschaeft.initTXSFinanzgeschaeft();
          ivSliceInDaten.initTXSSliceInDaten();
          ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
          ivKondDaten.initTXSKonditionenDaten();
          ivKredkunde.initTXSKreditKunde();

          TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
          TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
          TXSSicherheitPerson lvShperson = new TXSSicherheitPerson(); 
          
          boolean lvOkayDarlehen = true;
          
          //DarlehenLoanIQ lvHelpDarlehenLoanIQ = null;
          
          if (lvOkayDarlehen)
          {
        	  lvOkayDarlehen = ivFinanzgeschaeft.importLoanIQ(ivDarlehenLoanIQBlock, ivVorlaufsatz, LOGGER_LOANIQ);
          }
             
          if (lvOkayDarlehen)
          {              
        	  lvOkayDarlehen = ivFinanzgeschaeftDaten.importLoanIQ(ivDarlehenLoanIQBlock, ivVorlaufsatz, LOGGER_LOANIQ);
          }
          
          if (lvOkayDarlehen)
          {
              lvOkayDarlehen = ivSliceInDaten.importLoanIQ(ivDarlehenLoanIQBlock, ivVorlaufsatz, LOGGER_LOANIQ);
          }
          
          if (lvOkayDarlehen)
          {
              lvOkayDarlehen = ivKondDaten.importLoanIQ(ivDarlehenLoanIQBlock, ivVorlaufsatz, LOGGER_LOANIQ);
          }
          
          if (lvOkayDarlehen)
          {
              lvOkayDarlehen = ivKredkunde.importLoanIQ(ivDarlehenLoanIQBlock, ivVorlaufsatz, LOGGER_LOANIQ);
          }
                   
           // Transaktionen in die Datei schreiben
          if (lvOkayDarlehen)
          {
              // Daten in CashflowQuellsystem-Datei schreiben 
              try
              {
                String lvBuergschaft = new String();  
                if (StringKonverter.convertString2Double(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getBuergschaftprozent()) > 0.0)
                {
                    lvBuergschaft = "J";  
                }
                else
                {
                    lvBuergschaft = "N";
                }
                ivFosCashflowQuellsystem.write((ivDarlehenLoanIQBlock.getKontonummer() + ";" + ivFinanzgeschaeft.getKey() + ";" + ivFinanzgeschaeft.getOriginator() + ";" + ivFinanzgeschaeft.getQuelle() + ";" + lvBuergschaft + ";" + ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getBuergschaftprozent() + ";" + StringKonverter.lineSeparator).getBytes());
              }
              catch (Exception e)
              {
                  LOGGER_LOANIQ.error("Fehler bei der Ausgabe in die CashflowQuellsystem-Datei");
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
          int lvKredittyp = ValueMapping.ermittleKredittyp(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal(), ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getBuergschaftprozent());
          // Sonder-/Uebergangsloesung MIDAS -> Ausplatzierungsmerkmal nicht vorhanden
          // Nicht verwendete Produktschluessel 450, 503, 505, 802, 805 und 811
          if (ivDarlehenLoanIQBlock.getQuellsystem().startsWith("MID"))
          {
              if (ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getProduktschluessel().equals("402") || ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getProduktschluessel().equals("404") || ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getProduktschluessel().equals("412"))
              {
                  if (!ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getBuergschaftprozent().isEmpty())
                  {
                      lvKredittyp = DarlehenLoanIQ.VERBUERGT_KOMMUNAL;
                  }
                  else
                  {
                    lvKredittyp = DarlehenLoanIQ.REIN_KOMMUNAL;
                  }
              }
              if (ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getProduktschluessel().equals("821") || ivDarlehenLoanIQBlock.getDarlehenLoanIQBrutto().getProduktschluessel().equals("827"))
              {
                  lvKredittyp = DarlehenLoanIQ.FLUGZEUGDARLEHEN;
              }
          }

          //System.out.println("Konto " + ivDarlehenLoanIQBlock.getKontonummer() + " lvKredittyp: " + lvKredittyp);
          
          if (lvOkayDarlehen)
          {
        	  // Wenn SAP CMS Daten geladen, dann verarbeiten
        	  if (ivSapcms != null)
        	  {
        		  if (lvKredittyp == DarlehenLoanIQ.HYPOTHEK_1A)//|| lvKredittyp == DarlehenLoanIQ.REIN_KOMMUNAL)
        		  {
        			  //ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitObjekte(ivDarlehen.getKontonummer(), "1", ivDarlehen.getBuergschaftprozent(), ivDarlehen.getDeckungsschluessel()));
        			  ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitHypotheken(ivDarlehenLoanIQBlock.getKontonummer(), new String(), "1", ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getBuergschaftprozent(), "ALIQPFBG"));
        		  }
        		  //// CT 19.02.2018 - Noch in der Testphase
        		  ////if (lvKredittyp == DarlehenLoanIQ.VERBUERGT_KOMMUNAL)
        		  ////{
        		  ////	  ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitBuergschaft(ivDarlehenLoanIQBlock.getKontonummer(), "ALIQPFBG", ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getRestkapital(), ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getBuergschaftprozent(), ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal()));
        		  ////}
        		  if (lvKredittyp == DarlehenLoanIQ.SCHIFFSDARLEHEN)
        		  {
        			  ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitSchiff(ivDarlehenLoanIQBlock.getKontonummer(), "ALIQSCHF"));
        		  }
        		  if (lvKredittyp == DarlehenLoanIQ.FLUGZEUGDARLEHEN)
        		  {
        			  ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitFlugzeug(ivDarlehenLoanIQBlock.getKontonummer(), "ALIQFLUG"));
        		  }
        	  }
          }            
          
          if (lvOkayDarlehen)
          {
        	  // Verbuergte Darlehen
        	  if (lvKredittyp == DarlehenLoanIQ.KOMMUNALVERBUERGTE_HYPOTHEK || lvKredittyp == DarlehenLoanIQ.VERBUERGT_KOMMUNAL)
        	  {
        		  if (lvKredsh.importDarlehen(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto(), ivVorlaufsatz.getInstitutsnummer(), LOGGER_LOANIQ))
        		  {
        			  // Transaktionen in die Datei schreiben
        			  ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionStart());
        			  ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionDaten());
                  
        			  // TXSSicherheitDaten
        			  lvShdaten.importDarlehen(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto());
        			  ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionStart());
        			  ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionDaten());
        			  ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionEnde());
                  
        			  // TXSSicherheitPerson
        			  lvShperson.setTXSSicherheitPerson(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getBuergennummer(), ivVorlaufsatz.getInstitutsnummer());
        			  ivOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionStart());
        			  ivOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionDaten());
        			  ivOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionEnde());
                  
        			  ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionEnde());
        		  }
        	  }
          }
          
          if (lvOkayDarlehen)
          {
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
          } 
          
          if (lvOkayDarlehen)
          {
          	// Bei fuer Deckung vorgesehenen Krediten eine Default-Finanzierung anlegen
          	// nur Schiffe und Flugzeuge
        	  if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().equals("S0") || ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().equals("F0"))
        	  {
          			// TXSFinanzgeschaeft setzen --> eigentlich Finanzierung!!!
          			TXSFinanzgeschaeft lvFinanzierung = new TXSFinanzgeschaeft();
          			lvFinanzierung.setKey(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getFacilityReferenz());
          			lvFinanzierung.setOriginator(ValueMapping.changeMandant(ivVorlaufsatz.getInstitutsnummer()));
          			lvFinanzierung.setQuelle("TXS");           
              
          			// TXSFinanzgeschaeftDaten setzen
          			TXSFinanzgeschaeftDaten lvFgdaten = new TXSFinanzgeschaeftDaten();
          			lvFgdaten.setAz(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getFacilityReferenz());
          			lvFgdaten.setAktivpassiv("1");
          			lvFgdaten.setKat("8");
          			if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("S"))
          			{
          				lvFgdaten.setTyp("70");
          			}
          			if (ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal().startsWith("F"))
          			{
          				lvFgdaten.setTyp("71");
          			}
              
          			// TXSKonditionenDaten setzen
          			TXSKonditionenDaten lvKond = new TXSKonditionenDaten();
          			lvKond.setKondkey(ivDarlehenLoanIQBlock.getDarlehenLoanIQNetto().getFacilityReferenz());
                     
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