/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten.LoanIQPassivDaten;
import nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten.LoanIQPassivListeSummenbetraege;
import nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten.LoanIQPassivSummenbetraege;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSWertpapierposition;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author tepperc
 *
 */
public class LoanIQPassivVerarbeitung 
{
    // Logger fuer LoanIQ
    private static Logger LOGGER_LOANIQ_PASSIV = Logger.getLogger("TXSLoanIQPassivLogger");  
   
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
     * Quellsystem-Datei fuer die FRISCO-Verarbeitung
     */
    private String ivCashflowQuellsystemDatei;
    
    /**
     * FileOutputStream für CashflowQuellsystem-Datei
     */
    private FileOutputStream ivFosCashflowQuellsystem;
    
    /**
     * KundeRequest-Datei
     */
    private String ivKundeRequestDatei;
    
    /**
     * 
     */
    //private KundeRequestXML ivReqXML;
    private KundennummernOutput ivKundennummernOutput;
    
    /**
     * Vorlaufsatz der LoanIQ-Datei
     */
    private Vorlaufsatz ivVorlaufsatz;
       
    /**
     * PassivDaten aus LoanIQ
     */
    private LoanIQPassivDaten ivPassivDaten;
    
    /**
     * Zaehler fuer die Anzahl der Vorlaufsaetze
     */
    private int ivZaehlerVorlaufsatz;
    
    /**
     * Zaehler fuer die Anzahl der Finanzgeschaefte
     */
    private int ivZaehlerFinanzgeschaefte;
    
    // Zaehlvariablen
    private int ivAnzahlK = 0;
    private int ivAnzahlH = 0;
    private int ivAnzahlF = 0;
    private int ivAnzahlS = 0;
    private int ivAnzahlO = 0;
    
    /**
     * 
     */
    private OutputDarlehenXML ivOutputDarlehenXML;
    
    // Transaktionen
    private TXSFinanzgeschaeft ivFinanzgeschaeft;
    //private TXSSliceInDaten ivSliceInDaten;
    private TXSWertpapierposition ivWpposdaten;
    private TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten;
    private TXSKonditionenDaten ivKondDaten;
    private TXSKreditKunde ivKredkunde;

    /**
     * Liste mit den Summenbetraegen
     */
    private LoanIQPassivListeSummenbetraege ivListeSummenbetraege; 
    
    /**
     * Konstruktor fuer Passivverarbeitung LoanIQ
     * @param pvReader 
     */
    public LoanIQPassivVerarbeitung(IniReader pvReader)
    {
        this.ivFinanzgeschaeft = new TXSFinanzgeschaeft();
        //this.ivSliceInDaten = new TXSSliceInDaten();
        this.ivWpposdaten = new TXSWertpapierposition();
        this.ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
        this.ivKondDaten = new TXSKonditionenDaten();
        this.ivKredkunde = new TXSKreditKunde();
        
        this.ivListeSummenbetraege = new LoanIQPassivListeSummenbetraege();
        
        if (pvReader != null)
        {
            String lvLoggingXML = pvReader.getPropertyString("LoanIQPassiv", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
              System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            {
                DOMConfigurator.configure(lvLoggingXML); 
            }            

            ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (ivInstitutsnummer.equals("Fehler"))
            {
                LOGGER_LOANIQ_PASSIV.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
                        
            ivImportVerzeichnis = pvReader.getPropertyString("LoanIQPassiv", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_LOANIQ_PASSIV.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivExportVerzeichnis = pvReader.getPropertyString("LoanIQPassiv", "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                LOGGER_LOANIQ_PASSIV.error("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivLoanIQInputDatei =  pvReader.getPropertyString("LoanIQPassiv", "LoanIQInput-Datei", "Fehler");
            if (ivLoanIQInputDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ_PASSIV.error("Kein LoanIQInput-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            ivLoanIQOutputDatei =  pvReader.getPropertyString("LoanIQPassiv", "LoanIQOutput-Datei", "Fehler");
            if (ivLoanIQOutputDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ_PASSIV.error("Kein LoanIQOutput-Dateiname in der ini-Datei...");
                System.exit(1);
            }           
                        
            ivCashflowQuellsystemDatei = pvReader.getPropertyString("LoanIQPassiv", "Quellsystem-Datei", "Fehler");
            if (ivCashflowQuellsystemDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ_PASSIV.error("Kein Cashflow-Quellsystem-Dateiname in der ini-Datei...");
                System.exit(1);
            }

            ivKundeRequestDatei = pvReader.getPropertyString("LoanIQPassiv", "KundeRequestDatei", "Fehler");
            if (ivKundeRequestDatei.equals("Fehler"))
            {
                LOGGER_LOANIQ_PASSIV.error("Kein KundeRequest-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            // Verarbeitung starten
            startVerarbeitung();
        }
    }

    /**
     * Verarbeitung starten
     */
    private void startVerarbeitung()
    {     
        LOGGER_LOANIQ_PASSIV.info("Institutsnummer: " + ivInstitutsnummer);
        LOGGER_LOANIQ_PASSIV.info("ImportVerzeichnis: " + ivImportVerzeichnis);
        LOGGER_LOANIQ_PASSIV.info("ExportVerzeichnis: " + ivExportVerzeichnis);
        LOGGER_LOANIQ_PASSIV.info("LoanIQInputDatei: " + ivLoanIQInputDatei);
        LOGGER_LOANIQ_PASSIV.info("LoanIQOutputDatei: " + ivLoanIQOutputDatei);
        LOGGER_LOANIQ_PASSIV.info("CashflowQuellsystemDatei: " + ivCashflowQuellsystemDatei);
        LOGGER_LOANIQ_PASSIV.info("KundeRequestDatei: " + ivKundeRequestDatei);
        
        Calendar lvCal = Calendar.getInstance();
        CalendarHelper lvCh = new CalendarHelper();

        LOGGER_LOANIQ_PASSIV.info("Start: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
                
        // Cashflow-Quellsystem
        File lvFileCashflowQuellsystem = new File(ivExportVerzeichnis + "\\" + ivCashflowQuellsystemDatei);
        try
        {
            ivFosCashflowQuellsystem = new FileOutputStream(lvFileCashflowQuellsystem);
        }
        catch (Exception e)
        {
        	LOGGER_LOANIQ_PASSIV.info("Konnte CashflowQuellsystem-Datei nicht oeffnen!");
        }
        // Cashflow-Quellsystem
                
        // Darlehen XML-Datei im TXS-Format
        ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivLoanIQOutputDatei);
        ivOutputDarlehenXML.openXML();
        ivOutputDarlehenXML.printXMLStart();
        ivOutputDarlehenXML.printTXSImportDatenStart();
        
        // Erste Durchlauf durch die Datei zum Zweck der Summenbildung von Nominalbetrag und Leistungsrate        
        readPassivDaten(true, ivImportVerzeichnis + "\\" + ivLoanIQInputDatei);

        // Zweite Durchlauf zur Verarbeitung der Datei
        readPassivDaten(false, ivImportVerzeichnis + "\\" + ivLoanIQInputDatei);

        ivOutputDarlehenXML.printTXSImportDatenEnde();
        ivOutputDarlehenXML.closeXML();
        
        lvCal = Calendar.getInstance();
        LOGGER_LOANIQ_PASSIV.info(printStatistik());
        
        // Cashflow-Quellsystem
        try
        {
            ivFosCashflowQuellsystem.close();
        }
        catch (Exception e)
        {
        	LOGGER_LOANIQ_PASSIV.info("Fehler beim Schliessen der CashflowQuellsystem-Datei");
        }
        // Cashflow-Quellsystem
        
        // KundeRequest-Datei schliessen
        try
        {
        	ivKundennummernOutput.close();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ_PASSIV.error("Fehler beim Schliessen der KundeRequest-Datei");        	
        }

        LOGGER_LOANIQ_PASSIV.info("Ende: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
    }
    
    /**
     * Berechnet die Summenbetraege von Nominalbetrag und Leistungsrate
     */
    private void berechneSummenbetraege()
    {
        if (ivPassivDaten.getAusplatzierungsmerkmal().startsWith("K") || ivPassivDaten.getAusplatzierungsmerkmal().startsWith("H") ||
            ivPassivDaten.getAusplatzierungsmerkmal().startsWith("F") || ivPassivDaten.getAusplatzierungsmerkmal().startsWith("S") || 
            ivPassivDaten.getAusplatzierungsmerkmal().startsWith("O"))
            {   
        		//System.out.println("Aktenzeichen: " + ivPassivDaten.getAktenzeichen());
        		if (ivListeSummenbetraege.containsKey(ivPassivDaten.getAktenzeichen()))
        		{
        			//System.out.println("Vorhanden... " + ivPassivDaten.getNennbetrag());
        			LoanIQPassivSummenbetraege ivSummenbetraege = ivListeSummenbetraege.get(ivPassivDaten.getAktenzeichen());
        			ivSummenbetraege.addNominalbetrag(StringKonverter.convertString2BigDecimal(ivPassivDaten.getNennbetrag()));
        			ivSummenbetraege.addLeistungsrate(StringKonverter.convertString2BigDecimal(ivPassivDaten.getLeistungsrate()));
        		}
        		else
        		{
        			//System.out.println("Neu... " + ivPassivDaten.getNennbetrag());
        			LoanIQPassivSummenbetraege ivSummenbetraege = new LoanIQPassivSummenbetraege();
        			ivSummenbetraege.addNominalbetrag(StringKonverter.convertString2BigDecimal(ivPassivDaten.getNennbetrag()));
        			ivSummenbetraege.addLeistungsrate(StringKonverter.convertString2BigDecimal(ivPassivDaten.getLeistungsrate()));
        			ivListeSummenbetraege.putSummenbetraege(ivPassivDaten.getAktenzeichen(), ivSummenbetraege);
        		}
            }
    }
    
    /**
     * Liest und verarbeitet die PassivDaten
     * @param pvDateiname
     * @param pvSummenbildung
     */
    private void readPassivDaten(boolean pvSummenbildung, String pvDateiname)
    {
        ivZaehlerVorlaufsatz = 0;
        ivZaehlerFinanzgeschaefte = 0;
        String lvZeile = null;
        ivVorlaufsatz = new Vorlaufsatz();
        ivPassivDaten= new LoanIQPassivDaten(LOGGER_LOANIQ_PASSIV);
              
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File ivFileLoanIQ = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileLoanIQ);
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ_PASSIV.error("Konnte LoanIQ-Datei nicht oeffnen!");
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
                    lvStart = false;
                    ivZaehlerVorlaufsatz++;

                    if (!pvSummenbildung)
                    {
                      ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()));
                    
                      // Kunde-Request XML-Datei oeffnen
                      //ivReqXML = new KundeRequestXML(ivExportVerzeichnis + "\\" + ivKundeRequestDatei + "_" + DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()).replace("-", "") + ".xml");
                      //ivReqXML.openXML();
                      // Kunde-Request XML-Start
                      //ivReqXML.printXMLStart();

                      //ivReqXML.printXMLHeader(DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()).replace("-", ""), DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()).replace("-", ""), "000000");
                      //ivReqXML.printXMLRequestStart(ivVorlaufsatz.getInstitutsnummer());
                      // KundeRequest-Datei oeffnen
                      ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_LOANIQ_PASSIV);
                      ivKundennummernOutput.open();
                      ivKundennummernOutput.printVorlaufsatz(ivInstitutsnummer, "Pfandbrief");

                    }
                }
                else
                {
                    ivZaehlerFinanzgeschaefte++;
                	// Spaeter wieder entfernen - Nur jede zweite Zeile wird verarbeitet - Fehler in der Anlieferung
                	//if (ivZaehlerFinanzgeschaefte % 2 == 1)
                	//{
                    if (!ivPassivDaten.parsePassivDaten(lvZeile)) // Parsen der Felder
                    {
                        LOGGER_LOANIQ_PASSIV.error("Datenfehler in Zeile:\n");
                        LOGGER_LOANIQ_PASSIV.error(lvZeile);
                    }
                    // Es werden zwei Zeilen fuer jedes Outstanding geliefert. Einzige Unterschied ist die
                    // Kundennummer, die einmal den Wert '0' hat. - CT 09.06.2016
                    //if (StringKonverter.convertString2Long(ivPassivDaten.getKundennummer()) > 0)
                    if (!pvSummenbildung)
                    {
                      verarbeitePassivDaten();
                    }
                    else
                    {
                      berechneSummenbetraege();	
                    }
                	//}
                }
            }
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ_PASSIV.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
    
              
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ_PASSIV.error("Konnte LoanIQ-Datei nicht schliessen!");
        }           
    }

    /**
     * Liefert eine Statistik als String
     * @return 
     */
    private String printStatistik()
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
     * Verarbeite die PassivDaten
     */
    private void verarbeitePassivDaten()
    {  
        // Kundennummer in die KundeRequest-Datei schreiben
        if (!ivPassivDaten.getKundennummer().isEmpty())
        {
            ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(ivPassivDaten.getKundennummer(), LOGGER_LOANIQ_PASSIV));   
        }
        
        // PassivDaten Verarbeitung
        // TXS-Transaktionen fuellen
        if (ivPassivDaten.getAusplatzierungsmerkmal().startsWith("K") || ivPassivDaten.getAusplatzierungsmerkmal().startsWith("H") ||
            ivPassivDaten.getAusplatzierungsmerkmal().startsWith("F") || ivPassivDaten.getAusplatzierungsmerkmal().startsWith("S") ||
            ivPassivDaten.getAusplatzierungsmerkmal().startsWith("O"))
        {
            if (ivPassivDaten.getAusplatzierungsmerkmal().startsWith("K")) ivAnzahlK++;
            if (ivPassivDaten.getAusplatzierungsmerkmal().startsWith("H")) ivAnzahlH++;
            if (ivPassivDaten.getAusplatzierungsmerkmal().startsWith("F")) ivAnzahlF++;
            if (ivPassivDaten.getAusplatzierungsmerkmal().startsWith("S")) ivAnzahlS++;
            if (ivPassivDaten.getAusplatzierungsmerkmal().startsWith("O")) ivAnzahlO++;
            
            importPassivDaten2Transaktion();
        }
        else
        {
            LOGGER_LOANIQ_PASSIV.info("Kontonummer " + ivPassivDaten.getKontonummer() + " nicht verarbeitet - Ausplatzierungsmerkmal: " + ivPassivDaten.getAusplatzierungsmerkmal() + " -> Kein Ausplatzierungsmerkmal");
        }
    }
      
    /**
     * Importiert die Darlehensinformationen in die TXS-Transaktionen
     */
    private void importPassivDaten2Transaktion()
    {
    	ivFinanzgeschaeft.initTXSFinanzgeschaeft();
        //ivSliceInDaten.initTXSSliceInDaten();
        ivWpposdaten.initTXSWertpapierposition();
        ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
        ivKondDaten.initTXSKonditionenDaten();
        ivKredkunde.initTXSKreditKunde();
          
        boolean lvOkayPassivDaten = true;
                         
        lvOkayPassivDaten = ivFinanzgeschaeft.importLoanIQPassiv(ivPassivDaten, ivVorlaufsatz.getInstitutsnummer());
        System.out.println("Key: " + ivFinanzgeschaeft.getKey());
        
        if (lvOkayPassivDaten)
        {
    	   // Daten in CashflowQuellsystem-Datei schreiben 
    	   try
    	   {
    	        System.out.println("Key: " + ivFinanzgeschaeft.getKey());
    		   ivFosCashflowQuellsystem.write((ivPassivDaten.getKontonummer() + ";" + ivFinanzgeschaeft.getKey() + ";" + ivFinanzgeschaeft.getOriginator() + ";" + ivFinanzgeschaeft.getQuelle() + ";N;;" + StringKonverter.lineSeparator).getBytes());
    	   }
    	   catch (Exception e)
    	   {
    		   LOGGER_LOANIQ_PASSIV.error("Fehler bei der Ausgabe in die CashflowQuellsystem-Datei");
    	   }
    	   // Daten in CashflowQuellsystem-Datei schreiben
              
    	   //System.out.println("Suche: " + ivPassivDaten.getAktenzeichen());
    	   String lvNennbetrag = ivPassivDaten.getNennbetrag();
    	   ivPassivDaten.setNennbetrag(ivListeSummenbetraege.getSummenbetraege(ivPassivDaten.getAktenzeichen()).getNominalbetrag().toString());
           lvOkayPassivDaten = ivFinanzgeschaeftDaten.importLoanIQPassiv(ivPassivDaten, ivVorlaufsatz.getInstitutsnummer());
           ivPassivDaten.setNennbetrag(lvNennbetrag);
        }
                  
        if (lvOkayPassivDaten)
        {
        	String lvLeistungsrate = ivPassivDaten.getLeistungsrate();
        	ivPassivDaten.setLeistungsrate(ivListeSummenbetraege.getSummenbetraege(ivPassivDaten.getAktenzeichen()).getLeistungsrate().toString());
        	lvOkayPassivDaten = ivKondDaten.importLoanIQPassiv(ivPassivDaten, LOGGER_LOANIQ_PASSIV);
        	ivPassivDaten.setLeistungsrate(lvLeistungsrate);
        }
          
        if (lvOkayPassivDaten)
        {
        	lvOkayPassivDaten = ivWpposdaten.importLoanIQPassiv(ivPassivDaten, ivVorlaufsatz.getInstitutsnummer());
        }
          
        if (lvOkayPassivDaten)
        {
        	lvOkayPassivDaten = ivKredkunde.importLoanIQPassiv(ivPassivDaten, ivVorlaufsatz.getInstitutsnummer());    
        }
          
        // Transaktionen in die Datei schreiben
        if (lvOkayPassivDaten)
        {
        	ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionStart());
   
        	ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionStart());
        	ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionDaten());
        	ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionEnde());
        	
        	ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
        	ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
        	ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());
   
        	ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionStart());
        	ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionDaten());
        	ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionEnde());
              
        	ivOutputDarlehenXML.printTransaktion(ivWpposdaten.printTXSTransaktionStart());
        	ivOutputDarlehenXML.printTransaktion(ivWpposdaten.printTXSTransaktionDaten());
        	ivOutputDarlehenXML.printTransaktion(ivWpposdaten.printTXSTransaktionEnde());

        	ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
        } 
    }   

}
