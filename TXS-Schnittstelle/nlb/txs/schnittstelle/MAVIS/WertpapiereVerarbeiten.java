/*******************************************************************************
 * Copyright (c) 2016 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/
package nlb.txs.schnittstelle.MAVIS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSGlobalurkunde;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Transaktion.TXSWertpapierposition;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;
import nlb.txs.schnittstelle.Wertpapier.Bestand.DepotSummierung;
import nlb.txs.schnittstelle.Wertpapier.Gattungsdaten.Gattung;
import nlb.txs.schnittstelle.Wertpapier.Gattungsdaten.Gattungsdaten;
import org.apache.log4j.Logger;

/**
 * In dieser Klasse werden die Bestands- und Gattungsdaten aus MAVIS eingelesen und verarbeitet.
 * @author Carsten Tepper
 *
 */
public class WertpapiereVerarbeiten 
{
    // Logger fuer LoanIQ
    private static Logger LOGGER_MAVIS = Logger.getLogger("TXSMAVISLogger");  
   
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Buchungsdatum
     */
    private String ivBuchungsdatum;
    
    /**
     * Import-Verzeichnis der MAVIS-Datei
     */
    private String ivImportVerzeichnis;
    
    /**
     * Export-Verzeichnis der TXS-Importdatei
     */
    private String ivExportVerzeichnis;
    
    /**
     * Dateiname der MAVIS-Datei
     */
    private String ivMAVISInputDatei;
    
    /**
     * Dateiname der TXS-Importdatei
     */
    private String ivMAVISOutputDatei;
    
    /**
     * Quellsystem-Datei fuer die FRISCO-Verarbeitung
     */
    private String ivCashflowQuellsystemDatei;

    /**
     * Name der Rueckmeldedaten-Datei
     */
    private String ivRueckmeldungMAVIS;

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
     * Zaehler fuer die Anzahl der Zeilen in der Bestandsdaten-Datei
     */
    private int ivZaehlerBestandsdaten;
       
    /**
     * Zaehler mit der Anzahl verarbeiteter Bestandsdaten
     */
    private int ivZaehlerBestandsdatenVerarbeitet;
    
    // Zaehlvariablen
    private int ivAnzahlK = 0;
    private int ivAnzahlH = 0;
    private int ivAnzahlF = 0;
    private int ivAnzahlS = 0;
    private int ivAnzahlO = 0;

    /**
     * TXS-Importdatei
     */
    private OutputDarlehenXML ivOutputDarlehenXML;
    
    // Transaktionen
    private TXSFinanzgeschaeft ivFinanzgeschaeft;
    private TXSSliceInDaten ivSliceInDaten;
    private TXSWertpapierposition ivWertpapierposition;
    private TXSGlobalurkunde ivGlobalurkunde;
    private TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten;
    private TXSKonditionenDaten ivKondDaten;
    private TXSKreditKunde ivKredkunde;
	
    /**
     * Bestandsdaten
     */
    private Bestandsdaten ivBestandsdaten;
    
    /**
     * Gattung zu Bestandsdaten
     */
    private Gattung ivGattung;
    
    /**
	   * Gattungsdaten
	   */
	  private Gattungsdaten ivGattungsdaten;

    /**
     * Liste der RueckmeldeDaten
     */
    private HashMap<String, RueckmeldeDatenMAVIS> ivListeRueckmeldeDaten;

    /**
     * Summierung bei mehreren Depots
     */
    private DepotSummierung ivDepotSummierung;

    /**
	 * Konstruktor
	 * @param pvReader
	 */
	public WertpapiereVerarbeiten(IniReader pvReader)
	{        
		ivZaehlerBestandsdaten = 0;
		ivZaehlerBestandsdatenVerarbeitet = 0;
		
        ivFinanzgeschaeft = new TXSFinanzgeschaeft();
        ivSliceInDaten = new TXSSliceInDaten();
        ivWertpapierposition = new TXSWertpapierposition();
        ivGlobalurkunde = new TXSGlobalurkunde();
        ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
        ivKondDaten = new TXSKonditionenDaten();
        ivKredkunde = new TXSKreditKunde();

		if (pvReader != null)
        {

            ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (ivInstitutsnummer.equals("Fehler"))
            {
                LOGGER_MAVIS.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
            else
            {
            	LOGGER_MAVIS.info("Institutsnummer: " + ivInstitutsnummer);
            }
                        
            ivImportVerzeichnis = pvReader.getPropertyString("WertpapiereMAVIS", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_MAVIS.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            else
            {
            	LOGGER_MAVIS.info("ImportVerzeichnis: " + ivImportVerzeichnis);
            }

            ivExportVerzeichnis = pvReader.getPropertyString("WertpapiereMAVIS", "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                LOGGER_MAVIS.error("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            else
            {
            	LOGGER_MAVIS.info("ExportVerzeichnis: " + ivExportVerzeichnis);
            }

            ivMAVISInputDatei =  pvReader.getPropertyString("WertpapiereMAVIS", "MAVISInput-Datei", "Fehler");
            if (ivMAVISInputDatei.equals("Fehler"))
            {
                LOGGER_MAVIS.error("Kein MAVISInput-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
            	LOGGER_MAVIS.info("MAVISInput-Datei: " + ivMAVISInputDatei);
            }
            
            ivMAVISOutputDatei =  pvReader.getPropertyString("WertpapiereMAVIS", "MAVISOutput-Datei", "Fehler");
            if (ivMAVISOutputDatei.equals("Fehler"))
            {
                LOGGER_MAVIS.error("Kein MAVISOutput-Dateiname in der ini-Datei...");
                System.exit(1);
            }   
            else
            {
            	LOGGER_MAVIS.info("MAVISOutput-Datei: " + ivMAVISOutputDatei);
            }
                        
            ivCashflowQuellsystemDatei = pvReader.getPropertyString("WertpapiereMAVIS", "Quellsystem-Datei", "Fehler");
            if (ivCashflowQuellsystemDatei.equals("Fehler"))
            {
                LOGGER_MAVIS.error("Kein Cashflow-Quellsystem-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
            	LOGGER_MAVIS.info("Cashflow Quellsystem-Datei: " + ivCashflowQuellsystemDatei);
            }

            ivKundeRequestDatei = pvReader.getPropertyString("WertpapiereMAVIS", "KundeRequestDatei", "Fehler");
            if (ivKundeRequestDatei.equals("Fehler"))
            {
                LOGGER_MAVIS.error("Kein KundeRequest-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
            	LOGGER_MAVIS.info("KundeRequestDatei: " + ivKundeRequestDatei);
            }
            
            String lvDaypointerFileout = pvReader.getPropertyString("Konfiguration", "DPFILE", "Fehler");
            if (lvDaypointerFileout.equals("Fehler"))
            {
                LOGGER_MAVIS.error("Kein [Konfiguration][DPFILE] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_MAVIS.info("DPFILE=" + lvDaypointerFileout);                    
            }

        	// Einlesen des Buchungsdatum
            File ivFileDaypointer = new File(lvDaypointerFileout);
            if (ivFileDaypointer != null && ivFileDaypointer.exists())
            {
              FileInputStream lvDaypointerFis = null;
              BufferedReader lvDaypointerBr = null;
              try
              {
                lvDaypointerFis = new FileInputStream(ivFileDaypointer);
                lvDaypointerBr = new BufferedReader(new InputStreamReader(lvDaypointerFis));
              }
              catch (Exception e)
              {
                LOGGER_MAVIS.info("Konnte Datei '" + lvDaypointerFileout + "' nicht oeffnen!");
              }
              try
              {
                ivBuchungsdatum = lvDaypointerBr.readLine();  
              }
              catch (Exception e)
              {
                  LOGGER_MAVIS.error("Fehler beim Verarbeiten:" + ivBuchungsdatum);
                  e.printStackTrace();
              }
              try
              {
                lvDaypointerBr.close();
                lvDaypointerFis.close();
              }
              catch (Exception e)
              {
                  LOGGER_MAVIS.error("Konnte Datei '" + lvDaypointerFileout + "' nicht schliessen!");
              }
            }
            else
            {
                LOGGER_MAVIS.info("Keine Datei '" + lvDaypointerFileout + "' gefunden...");
            }
            LOGGER_MAVIS.info("Buchungsdatum: " + ivBuchungsdatum);

              
            // Gattungsdaten einlesen
        		ivGattungsdaten =  new Gattungsdaten(pvReader, LOGGER_MAVIS);

        		// Initialisierung der Liste der RueckmeldeDaten
            //ivListeRueckmeldeDaten = new HashMap<String, RueckmeldeDatenMAVIS>();
            // Liest die RueckmeldeDaten aus einer Datei ein
            //leseRueckmeldeDaten(ivRueckmeldungMAVIS);

            // DepotSummierungDaten initialisieren
            ivDepotSummierung = new DepotSummierung(LOGGER_MAVIS);

            // Start der Verarbeitung
            startVerarbeitung();

            // Schreibt die RueckmeldeDaten in eine Datei
            //schreibeRueckmeldeDaten(ivRueckmeldungMAVIS);

            // KundeRequest-Datei schliessen
            ivKundennummernOutput.close();               
       }
    }

    /**
     * Startet die Verarbeitung
     */
    private void startVerarbeitung()
    {     
        // KundeRequest-Datei oeffnen
        ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_MAVIS);
        ivKundennummernOutput.open();
        ivKundennummernOutput.printVorlaufsatz(ivInstitutsnummer, "Pfandbrief");

        // Cashflow-Quellsystem oeffnen (zum Schreiben)
        File lvFileCashflowQuellsystem = new File(ivExportVerzeichnis + "\\" + ivCashflowQuellsystemDatei);
        try
        {
            ivFosCashflowQuellsystem = new FileOutputStream(lvFileCashflowQuellsystem);
        }
        catch (Exception e)
        {
            LOGGER_MAVIS.error("Konnte CashflowQuellsystem-Datei nicht oeffnen!");
        }
        // Cashflow-Quellsystem oeffnen (zum Schreiben)
  
        // XML-Datei im TXS-Format
        ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivMAVISOutputDatei, LOGGER_MAVIS);
        ivOutputDarlehenXML.openXML();
        ivOutputDarlehenXML.printXMLStart();
        ivOutputDarlehenXML.printTXSImportDatenStart();
        ivOutputDarlehenXML.printTXSHeader(ivBuchungsdatum);
    	
        // MAVIS-Bestandsdaten einlesen und verarbeiten
        readBestandsdaten(ivImportVerzeichnis + "\\" + ivMAVISInputDatei);
        
        ivOutputDarlehenXML.printTXSImportDatenEnde();
        ivOutputDarlehenXML.closeXML();

        LOGGER_MAVIS.info(this.getStatistik());
        
        // Cashflow-Quellsystem schliessen
        try
        {
            ivFosCashflowQuellsystem.close();
        }
        catch (Exception e)
        {
            LOGGER_MAVIS.error("Fehler beim Schliessen der CashflowQuellsystem-Datei");
        }
        // Cashflow-Quellsystem schliessen

       // KundeRequest-Datei schliessen
        ivKundennummernOutput.close();               
    }

    /**
     * Einlesen der RueckmeldeDaten aus einer Datei
     * @param pvDateiname
     */
    private void leseRueckmeldeDaten(String pvDateiname)
    {
        String lvZeile = null;
        int lvZaehlerRueckmeldeDaten = 0;

        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File ivFileRueckmeldeDaten = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileRueckmeldeDaten);
        }
        catch (Exception e)
        {
            LOGGER_MAVIS.error("Konnte RueckmeldeDaten-Datei nicht oeffnen!");
            return;
        }

        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen der RueckmeldeDaten
            {
                RueckmeldeDatenMAVIS lvRueckmeldeDaten = new RueckmeldeDatenMAVIS();

                lvRueckmeldeDaten.parseRueckmeldeDaten(lvZeile, LOGGER_MAVIS); // Parsen der Felder

                //if (lvRueckmeldeDaten.parseRueckmeldeDaten(lvZeile, LOGGER_MAVIS)) // Parsen der Felder
                //{
                lvZaehlerRueckmeldeDaten++;
                ivListeRueckmeldeDaten.put(lvRueckmeldeDaten.getProdukt(), lvRueckmeldeDaten);
                //}
            }
        }
        catch (Exception e)
        {
            LOGGER_MAVIS.error("Fehler beim Verarbeiten einer Zeile!");
            LOGGER_MAVIS.error("Zeile: " + lvZeile);
            e.printStackTrace();
        }

        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_MAVIS.error("Konnte RueckmeldeDaten-Datei nicht schliessen!");
        }

        LOGGER_MAVIS.info("Anzahl eingelesener RueckmeldeDaten: " + lvZaehlerRueckmeldeDaten);
    }

    /**
     * Schreiben der RueckmeldeDaten in eine Datei
     * @param pvDateiname
     */
    private void schreibeRueckmeldeDaten(String pvDateiname)
    {
        // Fuer die Ausgabe der Rueckmeldedaten
        File lvRueckFile = new File(pvDateiname);
        FileOutputStream lvRueckOut = null;

        try
        {
            lvRueckOut = new FileOutputStream(lvRueckFile);
        }
        catch (Exception e)
        {
            LOGGER_MAVIS.error("Konnte RueckmeldeDaten-Datei " + lvRueckFile +" nicht oeffnen!");
            return;
        }

        // Schreibe RueckmeldeDaten in die Datei
        for (RueckmeldeDatenMAVIS lvRueckmeldeDaten:ivListeRueckmeldeDaten.values())
        {
            try
            {
                lvRueckOut.write((lvRueckmeldeDaten.toString() + StringKonverter.lineSeparator).getBytes());
            }
            catch (Exception e)
            {
                LOGGER_MAVIS.error("Fehler beim Rausschreiben der RueckmeldeDaten");
            }
        }

        try
        {
            lvRueckOut.close();
        }
        catch (Exception e)
        {
            LOGGER_MAVIS.error("Konnte RueckmeldeDaten-Datei " + lvRueckFile +" nicht schliessen!");
        }
    }

    /**
     * Einlesen und Verarbeiten der Wertpapier-Bestandsdaten
     */
    private void readBestandsdaten(String pvDateiname)
    {
        String lvZeile = null;
              
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File ivFileLoanIQ = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileLoanIQ);
        }
        catch (Exception e)
        {
            LOGGER_MAVIS.error("Konnte Bestandsdaten-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
        
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen der Wertpapier-Bestandsdaten
            {
        		ivBestandsdaten = new Bestandsdaten();

             	if (lvZeile.startsWith("##"))
            	{
                    LOGGER_MAVIS.info("Zeile: " + lvZeile);
            	}
            	else
                {
                    if (ivBestandsdaten.parseBestandsdaten(lvZeile, LOGGER_MAVIS)) // Parsen der Felder
                    {     
                    	ivZaehlerBestandsdaten++;
                    	if (!ivBestandsdaten.getAusplatzierungsmerkmal().isEmpty())
                    	{
                    		verarbeiteBestandsdaten();
                    	}
                    }
                }
            }
        }
        catch (Exception e)
        {
            LOGGER_MAVIS.error("Fehler beim Verarbeiten einer Zeile!");
            LOGGER_MAVIS.error("Zeile: " + lvZeile);
            e.printStackTrace();
        }
        
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_MAVIS.error("Konnte Bestandsdaten-Datei nicht schliessen!");
        }           
    }

    /**
     * Verarbeite Bestandsdaten
     */
    private void verarbeiteBestandsdaten()
    {
      // CT 20.08.2018 - Sonderverarbeitung bis Aufsummierung von zwei Depots realisiert
      if (ivBestandsdaten.getProdukt().equals("DE000LFA1602"))
      {
          ivBestandsdaten.setNominalbetrag("55000000.0000");
      }

    	ivGattung = ivGattungsdaten.getGattung(ivBestandsdaten.getProdukt());
    	if (ivGattung == null)
    	{
    		LOGGER_MAVIS.error("Keine Gattung fuer ISIN " + ivBestandsdaten.getProdukt() + " gefunden.");
    		return;
    	}
    	else
    	{
    		LOGGER_MAVIS.info("Gattung fuer ISIN " + ivBestandsdaten.getProdukt() + " vorhanden.");
    		ivZaehlerBestandsdatenVerarbeitet++;
    	}

        if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("K") || ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("H") ||
            ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("F")	|| ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("S") ||
            ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("O"))
        {
           ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(ivBestandsdaten.getKundennummer(), LOGGER_MAVIS));

           ivDepotSummierung.addDepotSummierungDaten(ivBestandsdaten.getProdukt(), ivBestandsdaten.getDepotNr(), ivBestandsdaten.getNominalbetrag());
           importWertpapier2Transaktion();

           if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("K")) ivAnzahlK++;
           if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("H")) ivAnzahlH++;
           if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("F")) ivAnzahlF++;
           if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("S")) ivAnzahlS++;
           if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("O")) ivAnzahlO++;
        }
    }
    
    /**
     * Importiert die Wertpapier-Informationen in die TXS-Transaktionen
     */
    private void importWertpapier2Transaktion()
    {
    	ivFinanzgeschaeft.initTXSFinanzgeschaeft();
    	ivSliceInDaten.initTXSSliceInDaten();
    	ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
    	ivKondDaten.initTXSKonditionenDaten();
    	ivKredkunde.initTXSKreditKunde();
                    
    	boolean lvOkayWertpapier = true;
                    
    	if (lvOkayWertpapier)
    	{
    		lvOkayWertpapier = ivFinanzgeschaeft.importWertpapier(ivBestandsdaten, ivInstitutsnummer);
    	}
             
    	if (lvOkayWertpapier)
    	{
    		// Daten in CashflowQuellsystem-Datei schreiben
    		try
    		{
    			String lvBuergschaft = new String();  
                //if (StringKonverter.convertString2Double(lvHelpDarlehenLoanIQ.getBuergschaftprozent()) > 0.0)
                //{
                //    lvBuergschaft = "J";  
                //}
                //else
                //{
                //    lvBuergschaft = "N";
                //}
                ivFosCashflowQuellsystem.write((ivBestandsdaten.getProdukt() + "_" + ivBestandsdaten.getDepotNr()  + ";" + ivFinanzgeschaeft.getKey() + ";" + ivFinanzgeschaeft.getOriginator() + ";" + ivFinanzgeschaeft.getQuelle() + ";" + lvBuergschaft + ";" + "0.0" + ";" + StringKonverter.lineSeparator).getBytes());
              }
              catch (Exception e)
              {
                  LOGGER_MAVIS.error("Fehler bei der Ausgabe in die CashflowQuellsystem-Datei");
              }
              // Daten in CashflowQuellsystem-Datei schreiben
              
              lvOkayWertpapier = ivFinanzgeschaeftDaten.importWertpapier(ivBestandsdaten, ivGattung, LOGGER_MAVIS);
          }
    	
    	if (lvOkayWertpapier)
        {
            lvOkayWertpapier = ivKondDaten.importWertpapier(ivBestandsdaten, ivGattung, LOGGER_MAVIS);
        }
  
          if (lvOkayWertpapier)
          {
        	  if (ivBestandsdaten.getAktivPassiv().equals("1")) // Aktiv - Slice
        	  {
                lvOkayWertpapier = ivSliceInDaten.importWertpapier(ivBestandsdaten, ivInstitutsnummer, LOGGER_MAVIS);
        	  }
        	  if (ivBestandsdaten.getAktivPassiv().equals("2")) // Passiv - Wertpapierposition + Globalurkunde
        	  {
        		  lvOkayWertpapier = ivWertpapierposition.importWertpapier(ivBestandsdaten);
        		  if (lvOkayWertpapier)
        		  {
        		    lvOkayWertpapier = ivGlobalurkunde.importWertpapier(ivBestandsdaten, ivGattung.getGD630A(), ivGattung.getGD660());
        		  }
        	  }
          }
          
         
          if (lvOkayWertpapier)
          {
        	  if (ivBestandsdaten.getAktivPassiv().equals("1")) // Nur Aktiv
        	  {
                lvOkayWertpapier = ivKredkunde.importWertpapier(ivBestandsdaten, ivInstitutsnummer);
        	  }
          }
                   
           // Transaktionen in die Datei schreiben
          if (lvOkayWertpapier)
          {
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionStart());
   
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionStart());
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionDaten());
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionEnde());
   
              ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
              ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
              ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());
              
              if (ivBestandsdaten.getAktivPassiv().equals("1")) // Nur Aktiv
              {
                ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionStart());
                ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionDaten());
                ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionEnde());
              }
                           
              if (ivBestandsdaten.getAktivPassiv().equals("2")) // Nur Passiv
              {
            	  // Wertpapierposition
            	  ivOutputDarlehenXML.printTransaktion(ivWertpapierposition.printTXSTransaktionStart());
            	  ivOutputDarlehenXML.printTransaktion(ivWertpapierposition.printTXSTransaktionDaten());
            	  ivOutputDarlehenXML.printTransaktion(ivWertpapierposition.printTXSTransaktionEnde());

            	  // Globalurkunde
            	  ivOutputDarlehenXML.printTransaktion(ivGlobalurkunde.printTXSTransaktionStart());
            	  ivOutputDarlehenXML.printTransaktion(ivGlobalurkunde.printTXSTransaktionDaten());
            	  ivOutputDarlehenXML.printTransaktion(ivGlobalurkunde.printTXSTransaktionEnde());
              }
   
              if (ivBestandsdaten.getAktivPassiv().equals("1")) // Nur Aktiv
              {
                ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionStart());
                ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionDaten());
                ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionEnde());
              }
              
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
          }       
    }
    
    /**
     * Liefert eine Statistik
     * @return Statistik
     */
    private String getStatistik()
    {
        StringBuilder lvOut = new StringBuilder();
        
        lvOut.append("Anzahl gelesener Zeilen aus der Bestandsdaten-Datei: " + ivZaehlerBestandsdaten + StringKonverter.lineSeparator);
        lvOut.append("Anzahl verarbeiteter Bestandsdaten: " + ivZaehlerBestandsdatenVerarbeitet + StringKonverter.lineSeparator); 
        lvOut.append(ivAnzahlK + " mit Ausplatzierungsmerkmal Kx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlH + " mit Ausplatzierungsmerkmal Hx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlF + " mit Ausplatzierungsmerkmal Fx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlS + " mit Ausplatzierungsmerkmal Sx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlO + " mit Ausplatzierungsmerkmal Ox" + StringKonverter.lineSeparator);
        lvOut.append("Depotsummierungen:" + StringKonverter.lineSeparator);
        lvOut.append(ivDepotSummierung.toString());

        return lvOut.toString();
    }

}
