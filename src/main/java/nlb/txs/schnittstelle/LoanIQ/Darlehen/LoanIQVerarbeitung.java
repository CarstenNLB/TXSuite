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
import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenBlock;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Sicherheiten.Sicherheiten2Pfandbrief;
import nlb.txs.schnittstelle.Sicherheiten.SicherheitenDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
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
     * Import-Verzeichnis der Sicherheiten-Datei
     */
    private String ivImportVerzeichnisSAPCMS;
    
    /**
     * Dateiname der Sicherheiten-Datei
     */
    private String ivSapcmsDatei;

    /**
     * Sicherheiten-Daten
     */
    private SicherheitenDaten ivSicherheitenDaten;
    
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
     * Ein DarlehenBlock
     */
    private DarlehenBlock ivDarlehenBlock;
    
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
     * Ausgabedatei der TXS-Importdaten
     */
    private OutputDarlehenXML ivOutputDarlehenXML;

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
        
        if (pvReader != null)
        {
            ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (ivInstitutsnummer.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_LOANIQ.info("Institutsnummer: " + ivInstitutsnummer);
            }
                        
            ivImportVerzeichnis = pvReader.getPropertyString("LoanIQ", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_LOANIQ.info("ImportVerzeichnis: " + ivImportVerzeichnis);
            }

            ivExportVerzeichnis = pvReader.getPropertyString("LoanIQ", "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_LOANIQ.info("ExportVerzeichnis: " + ivExportVerzeichnis);
            }

            ivLoanIQInputDatei =  pvReader.getPropertyString("LoanIQ", "LoanIQInput-Datei", "Fehler");
            if (ivLoanIQInputDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein LoanIQInput-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_LOANIQ.info("LoanIQInput-Datei: " + ivLoanIQInputDatei);
            }

            ivLoanIQOutputDatei =  pvReader.getPropertyString("LoanIQ", "LoanIQOutput-Datei", "Fehler");
            if (ivLoanIQOutputDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein LoanIQOutput-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_LOANIQ.info("LoanIQOutput-Datei: " + ivLoanIQOutputDatei);
            }

            ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("SAPCMS", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein SAPCMS Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_LOANIQ.info("SAPCMS ImportVerzeichnis: " + ivImportVerzeichnisSAPCMS);
            }

            ivSapcmsDatei = pvReader.getPropertyString("SAPCMS", "SAPCMS-Datei", "Fehler");
            if (ivSapcmsDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein SAPCMS-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_LOANIQ.info("SAPCMS-Datei: " + ivSapcmsDatei);
            }

            ivCashflowQuellsystemDatei = pvReader.getPropertyString("LoanIQ", "Quellsystem-Datei", "Fehler");
            if (ivCashflowQuellsystemDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein Cashflow-Quellsystem-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_LOANIQ.info("Cashflow Quelsystem-Datei: " + ivCashflowQuellsystemDatei);
            }

            ivKundeRequestDatei = pvReader.getPropertyString("LoanIQ", "KundeRequestDatei", "Fehler");
            if (ivKundeRequestDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein KundeRequest-Dateiname in der ini-Datei...");
                System.exit(1); 
            }
            else
            {
                LOGGER_LOANIQ.info("KundeRequestDatei: " + ivKundeRequestDatei);
            }

            ivImportVerzeichnisRueckmeldung = pvReader.getPropertyString("Rueckmeldung", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnisRueckmeldung.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein Import-Verzeichnis fuer die Rueckmeldung in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_LOANIQ.info("Rueckmeldung ImportVerzeichnis: " + ivImportVerzeichnisRueckmeldung);
            }

            ivAusplatzierungsmerkmalDatei = pvReader.getPropertyString("Rueckmeldung", "AusplatzierungsmerkmalLoanIQ-Datei", "Fehler");
            if (ivAusplatzierungsmerkmalDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein Ausplatzierungsmerkmal-Dateiname in der ini-Datei...");
                System.exit(1); 
            }
            else
            {
                LOGGER_LOANIQ.info("AusplatzierungsmerkmalLoanIQ-Datei: " + ivAusplatzierungsmerkmalDatei);
            }

            ivVerbuergtKonsortialDatei = pvReader.getPropertyString("Rueckmeldung", "VerbuergtKonsortialDatei", "Fehler");
            if (ivVerbuergtKonsortialDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ.error("Kein VerbuergtKonsortial-Dateiname in der ini-Datei...");
                System.exit(1); 
            }
            else
            {
                LOGGER_LOANIQ.info("VerbuergtKonsortialDatei: " + ivVerbuergtKonsortialDatei);
            }

            // Verarbeitung starten
            startVerarbeitung();
        }
        System.exit(0);
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
        ivSicherheitenDaten = new SicherheitenDaten(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, SicherheitenDaten.SAPCMS, LOGGER_LOANIQ);
        
        // Darlehen XML-Datei im TXS-Format
        ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivLoanIQOutputDatei, LOGGER_LOANIQ);
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
        ivVorlaufsatz = new Vorlaufsatz(LOGGER_LOANIQ);
        ivDarlehenBlock = new DarlehenBlock(ivVorlaufsatz, ivSicherheitenDaten.getSicherheiten2Pfandbrief(), LOGGER_LOANIQ);
              
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
                    if (!ivDarlehenBlock.parseDarlehen(lvZeile, LOGGER_LOANIQ)) // Parsen der Felder
                    {        
                    	// Unterschiedliche Kontonummer -> Darlehen verarbeiten 
                    	if (isAusplatzierungsmerkmalRelevant())
                    	{
                    		ivDarlehenBlock
                            .verarbeiteDarlehenLoanIQ(ivFosVerbuergtKonsortial, ivFosCashflowQuellsystem, ivOutputDarlehenXML);
                    	}
                        // Neuen LoanIQ-Block anlegen
                        ivDarlehenBlock = new DarlehenBlock(ivVorlaufsatz, new Sicherheiten2Pfandbrief(ivSicherheitenDaten, LOGGER_LOANIQ), LOGGER_LOANIQ);
                        // Zeile mit neuer Kontonummer muss noch verarbeitet werden
                        if (ivDarlehenBlock.parseDarlehen(lvZeile, LOGGER_LOANIQ)) // Parsen der Felder
                        {
                            if (lvZeile.contains(";B;")) ivZaehlerFinanzgeschaefteBrutto++;
                            if (lvZeile.contains(";N;")) ivZaehlerFinanzgeschaefteNetto++;
                            if (lvZeile.contains(";F;")) ivZaehlerFinanzgeschaefteFremd++;
                        }
                    }
                    else
                    {
                        if (lvZeile.contains(";B;")) ivZaehlerFinanzgeschaefteBrutto++;
                        if (lvZeile.contains(";N;")) ivZaehlerFinanzgeschaefteNetto++;
                        if (lvZeile.contains(";F;")) ivZaehlerFinanzgeschaefteFremd++;
                    }
                }
            }
            // Letzten Kredit auch verarbeiten - CT 13.09.2017
            if (isAusplatzierungsmerkmalRelevant())
            {
            	ivDarlehenBlock
                  .verarbeiteDarlehenLoanIQ(ivFosVerbuergtKonsortial, ivFosCashflowQuellsystem, ivOutputDarlehenXML);
            }
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
     * Pruefung ob das Ausplatzierungsmerkmal relevant ist und schreibt die nicht relevanten Ausplatzierungsmerkmale in eine Datei
     * @return true -> relevant; false -> nicht relevant
     */
    private boolean isAusplatzierungsmerkmalRelevant()
    {
    	 boolean lvRelevant = false;
      	 if (ivDarlehenBlock.existsDarlehenNetto())
       	 {                
       		if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("A") ||
              ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("C") ||
       			  ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("D") ||
              ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("E") ||
       			  ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("P") ||
              ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("R") ||
       			  ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("W") ||
              ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("N"))
       		{
       			try
       			{                    		  
       				ivFosAusplatzierungsmerkmal.write((ivDarlehenBlock.getKontonummer() + ";" +
                  ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal() + StringKonverter.lineSeparator).getBytes());
       			}
       			catch (Exception e)
       			{
       				LOGGER_LOANIQ.error("Fehler beim Schreiben in die Ausplatzierungsmerkmal-Datei");
       			}
       			LOGGER_LOANIQ.info("Kontonummer " + ivDarlehenBlock.getKontonummer() + " - Nicht relevantes Ausplatzierungsmerkmal " +
                ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal());
       			lvRelevant = false;
       		}
       		else
       		{
       	   		if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("K") ||
                  ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("H") ||
       	   			  ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("F") ||
                  ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("S") ||
       	   		    ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("O"))
       	   		{
           			try
           			{                    		  
           				ivFosAusplatzierungsmerkmal.write((ivDarlehenBlock.getKontonummer() + ";" +
                      ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal() + StringKonverter.lineSeparator).getBytes());
           			}
           			catch (Exception e)
           			{
           				LOGGER_LOANIQ.error("Fehler beim Schreiben in die Ausplatzierungsmerkmal-Datei");
           			}

       	   			lvRelevant = true;
       	   			// Buergennummer in die KundeRequest-Datei schreiben
       	   			if (!ivDarlehenBlock.getDarlehenNetto().getBuergennummer().isEmpty())
       	   			{
       	   				ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(
                      ivDarlehenBlock.getDarlehenNetto().getBuergennummer(), LOGGER_LOANIQ));
       	   			}
       	   			// Kundennummer in die KundeRequest-Datei schreiben
       	   			if (!ivDarlehenBlock.getDarlehenNetto().getKundennummer().isEmpty())
       	   			{
       	   				ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(
                      ivDarlehenBlock.getDarlehenNetto().getKundennummer(), LOGGER_LOANIQ));
       	   			}
       	   			
       	   			if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("K")) ivAnzahlK++;
       	   			if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("H")) ivAnzahlH++;
       	   			if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("F")) ivAnzahlF++;
       	   			if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("S")) ivAnzahlS++;
       	   			if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("O")) ivAnzahlO++;
       	   		}
       		}
       	 }
      	 return lvRelevant;
    }
}