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
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.Darlehen;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Sicherheiten.SAPCMS_Neu;
import nlb.txs.schnittstelle.Sicherheiten.Sicherheiten2Pfandbrief;
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
     * Import-Verzeichnis der Sicherheiten-Datei
     */
    private String ivImportVerzeichnisSAPCMS;
    
    /**
     * Dateiname der Sicherheiten-Datei
     */
    private String ivSapcmsDatei;

    /**
     * SAP CMS Sicherheiten
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
    private HashMap<String, Darlehen> ivListeGeschaefte;
    
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
    //private int ivAnzahlF = 0;
    //private int ivAnzahlS = 0;
    //private int ivAnzahlO = 0;
    private int ivRestkapitalNull = 0;
    
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
        ivListeGeschaefte = new HashMap<String, Darlehen>();
        
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
            
            System.exit(ivRestkapitalNull);
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
        ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivMIDASOutputDatei, LOGGER_MIDAS);
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
        ivVorlaufsatz = new Vorlaufsatz(LOGGER_MIDAS);

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
            	    // Darlehen initialisieren
            	    Darlehen lvDarlehen = new Darlehen(LOGGER_MIDAS);
                    if (lvDarlehen.parseDarlehen(lvZeile)) // Parsen der Felder
                    {       
                        ivZaehlerFinanzgeschaefte++;
                        if (lvDarlehen.getKennzeichenBruttoNettoFremd().equals("N"))
                        {
                          ivListeGeschaefte.put(MappingMIDAS.ermittleMIDASKontonummer(lvDarlehen.getQuellsystem(), lvDarlehen
                            .getKontonummer()), lvDarlehen);
                        }
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
        //lvOut.append(ivAnzahlF + " mit Ausplatzierungsmerkmal Fx" + StringKonverter.lineSeparator);
        //lvOut.append(ivAnzahlS + " mit Ausplatzierungsmerkmal Sx" + StringKonverter.lineSeparator);
        //lvOut.append(ivAnzahlO + " mit Ausplatzierungsmerkmal Ox" + StringKonverter.lineSeparator);
        lvOut.append(ivRestkapitalNull + " mit Restkapital 0.0 und TXS-relevantem Ausplatzierungsmerkmal" + StringKonverter.lineSeparator);
        return lvOut.toString();
    }
     
    /**
     * Verarbeite die MIDAS-Geschaefte
     */
    private void verarbeiteMIDAS()
    {  
    	Collection<Darlehen> lvCollectionGeschaefte = ivListeGeschaefte.values();
    	Iterator<Darlehen> lvIterGeschaefte = lvCollectionGeschaefte.iterator();
    	
    	while (lvIterGeschaefte.hasNext())
    	{
    		Darlehen lvDarlehen = lvIterGeschaefte.next();
    		//LOGGER_MIDAS.info("Konto: " + lvDarlehen.getKontonummer());
            if (lvDarlehen.getKennzeichenBruttoNettoFremd().equals("N") &&
                (lvDarlehen.getAusplatzierungsmerkmal().startsWith("H") || lvDarlehen.getAusplatzierungsmerkmal().startsWith("K")))
            	{
            	/*
            	  // Wenn Konsortial, dann Netto-Zeile verwenden - CT 02.07.2019
            	  if (lvDarlehen.getKennzeichenKonsortialkredit().equals("J"))
                {
                  for (Darlehen lvHelpDarlehen :ivListeGeschaefte.values())
                  {
                    // Netto-Zeile ueber Gegenkontonummer ermitteln
                    if (lvHelpDarlehen.getKennzeichenBruttoNettoFremd().equals("N") && lvHelpDarlehen
                        .getGegenkontoPassiv().trim().equals(lvDarlehen.getKontonummer().trim()))
                    {
                      // Die Kontonummer aus der Brutto-Zeile muss verwendet werden
                      //lvHelpDarlehen.setKontonummer(lvDarlehen.getKontonummer());
                      lvDarlehen = lvHelpDarlehen; // Netto-Zeile verwenden
                      break; // Gefunden -> Abbruch der Suche
                    }
                  }
                }
        */
            		if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("K")) ivAnzahlK++;
            		if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("H")) ivAnzahlH++;
            		//if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("F")) ivAnzahlF++;
            		//if (ivDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("S")) ivAnzahlS++;
            		//if (ivDarlehenLoanIQ.getAusplatzierungsmerkmal().startsWith("O")) ivAnzahlO++;

            		try
            		{
            			// Buergennummer in die KundeRequest-Datei schreiben
            			if (!lvDarlehen.getBuergennummer().isEmpty() && !lvDarlehen.getBuergennummer().equals("9999999999"))
            			{
            				if (!ivListeKundennummern.contains(MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_MIDAS)))
            				{
            					ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(
                          lvDarlehen.getBuergennummer(), LOGGER_MIDAS));
            					ivListeKundennummern.add(MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_MIDAS));
            				}    			  
            			}
            			// Kundennummer in die KundeRequest-Datei schreiben
            			if (!lvDarlehen.getKundennummer().isEmpty() && !lvDarlehen.getKundennummer().equals("9999999999"))
            			{
            				if (!ivListeKundennummern.contains(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_MIDAS)))
            				{
            					ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(
                          lvDarlehen.getKundennummer(), LOGGER_MIDAS));
            					ivListeKundennummern.add(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_MIDAS));
            				}
            			}
            		}
            		catch (Exception exp)
            		{
            			LOGGER_MIDAS.error("Konnte Buergen- und Kundennummer nicht in KundeRequest-Datei schreiben...");
            		}

                    if (StringKonverter.convertString2Double(lvDarlehen.getRestkapital()) > 0.0)
                    {
                        importDarlehen2Transaktion(lvDarlehen);
                    }
                    else
                    {
                        LOGGER_MIDAS.info("Kontonummer " + lvDarlehen.getKontonummer() + " nicht verarbeitet - Ausplatzierungsmerkmal " + lvDarlehen
                            .getAusplatzierungsmerkmal() + " und Restkapital " + lvDarlehen.getRestkapital());
                    }
                }
    	}
    	
    }
    
    /**
     * Importiert die Darlehensinformationen in die TXS-Transaktionen
     * @param pvDarlehen
     */
    private void importDarlehen2Transaktion(Darlehen pvDarlehen)
    {
          ivFinanzgeschaeft.initTXSFinanzgeschaeft();
          ivSliceInDaten.initTXSSliceInDaten();
          ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
          ivKondDaten.initTXSKonditionenDaten();
          ivKredkunde.initTXSKreditKunde();
          
          boolean lvOkayDarlehen = ivFinanzgeschaeft.importMIDAS(pvDarlehen, ivVorlaufsatz);

          if (lvOkayDarlehen)
          {           
              lvOkayDarlehen = ivFinanzgeschaeftDaten.importMIDAS(pvDarlehen, ivVorlaufsatz, LOGGER_MIDAS);
          }
          
          if (lvOkayDarlehen)
          {
              lvOkayDarlehen = ivSliceInDaten.importMIDAS(pvDarlehen, ivVorlaufsatz, LOGGER_MIDAS);
          }
          
          if (lvOkayDarlehen)
          {
              lvOkayDarlehen = ivKondDaten.importMIDAS(pvDarlehen, LOGGER_MIDAS);
          }
          
          if (lvOkayDarlehen)
          {
              lvOkayDarlehen = ivKredkunde.importMIDAS(pvDarlehen, ivVorlaufsatz);
          }
                   
           // Transaktionen in die Datei schreiben
          if (lvOkayDarlehen)
          {
              // Daten in CashflowQuellsystem-Datei schreiben
              try
              {
                String lvBuergschaft = new String();  
                if (StringKonverter.convertString2Double(pvDarlehen.getBuergschaftprozent()) > 0.0)
                {
                    lvBuergschaft = "J";  
                }
                else
                {
                    lvBuergschaft = "N";
                }
                ivFosCashflowQuellsystem.write((MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen
                    .getKontonummer()) + ";" +
                                                ivFinanzgeschaeft.getKey() + ";" + ivFinanzgeschaeft.getOriginator() + ";" +
                                                ivFinanzgeschaeft.getQuelle() + ";" + lvBuergschaft + ";" + pvDarlehen
                    .getBuergschaftprozent() + ";" +
                		                            ivKondDaten.getMantilg() + ";" + ivKondDaten.getManzins() + ";" + ivKondDaten.getFaellig() + StringKonverter.lineSeparator).getBytes());
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
          int lvKredittyp = ValueMapping.ermittleKredittyp(pvDarlehen.getAusplatzierungsmerkmal(), pvDarlehen.getBuergschaftprozent());
          // Sonder-/Uebergangsloesung MIDAS -> Ausplatzierungsmerkmal nicht vorhanden
          // Nicht verwendete Produktschluessel 450, 503, 505, 802, 805 und 811
          if (pvDarlehen.getProduktschluessel().equals("402") || pvDarlehen.getProduktschluessel().equals("404") || pvDarlehen
              .getProduktschluessel().equals("412"))
          {
        	  if (!pvDarlehen.getBuergschaftprozent().isEmpty())
        	  {
        		  lvKredittyp = Darlehen.VERBUERGT_KOMMUNAL;
        	  }
        	  else
        	  {
        		  lvKredittyp = Darlehen.REIN_KOMMUNAL;
              }
          }
          if (pvDarlehen.getProduktschluessel().equals("821") || pvDarlehen.getProduktschluessel().equals("827"))
          {
        	  lvKredittyp = Darlehen.FLUGZEUGDARLEHEN;
          }

          LOGGER_MIDAS.info("Konto " + pvDarlehen.getKontonummer() + " lvKredittyp: " + lvKredittyp);

          if (lvOkayDarlehen)
          {
        	  // Wenn SAP CMS Daten geladen, dann verarbeiten
        	  if (ivSapcms != null)
        	  {
              LOGGER_MIDAS.info("MIDAS - Suche SicherheitObjekt zu Kontonummer " + pvDarlehen.getKontonummer());

              StringBuffer lvBuffer = new StringBuffer();
              if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("H"))
              {
                lvBuffer.append(((Sicherheiten2Pfandbrief)ivSapcms.getSicherheitenDaten().getSicherheiten2Pfandbrief()).importSicherheitHypotheken(MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen
                    .getKontonummer()), pvDarlehen.getGegenkontoPassiv(), "", "A", pvDarlehen.getBuergschaftprozent(), "AMIDPFBG", ivVorlaufsatz.getInstitutsnummer()));
              }
              // Herausgenommen, da die Flugzeugdarlehen nicht mehr benoetigt werden - CT 04.03.2020
              //if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("F"))
              //{
              //  lvBuffer.append(((Sicherheiten2Pfandbrief)ivSapcms.getSicherheitenDaten().getSicherheiten2Pfandbrief()).importSicherheitFlugzeug(MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen
              //      .getKontonummer()), "AMIDFLUG", ivVorlaufsatz.getInstitutsnummer()));
              //}
              if (pvDarlehen.getAusplatzierungsmerkmal().startsWith("K") && StringKonverter.convertString2Double(pvDarlehen.getBuergschaftprozent()) > 0.0)
              {
                String lvKontonummer = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen.getKontonummer());
                //if (pvDarlehen.getKennzeichenKonsortialkredit().equals("J"))
                //{
                //  lvKontonummer = MappingMIDAS.ermittleMIDASKontonummer(pvDarlehen.getQuellsystem(), pvDarlehen.getGegenkontoPassiv());
                //}
                lvBuffer.append(((Sicherheiten2Pfandbrief)ivSapcms.getSicherheitenDaten().getSicherheiten2Pfandbrief()).importSicherheitBuergschaft(lvKontonummer, "AMIDPFBG", pvDarlehen.getRestkapital(), pvDarlehen.getBuergschaftprozent(), pvDarlehen.getAusplatzierungsmerkmal(), pvDarlehen.getNennbetrag(), pvDarlehen.getKundennummer(), pvDarlehen.getBuergennummer()));
              }

              ivOutputDarlehenXML.printTransaktion(lvBuffer);
              ////ivOutputDarlehenXML.printTransaktion(((Sicherheiten2Pfandbrief)ivSapcms.getSicherheitenDaten().getSicherheiten2Pfandbrief()).importSicherheitMIDAS(ivFinanzgeschaeft.getKey(), new String(), "A", pvDarlehen
              ////    .getRestkapital(), pvDarlehen.getBuergschaftprozent(), pvDarlehen.getAusplatzierungsmerkmal(), ivVorlaufsatz.getInstitutsnummer()));
              ////}
        	  }
          }           
          
          if (lvOkayDarlehen)
          {
              ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
          }

        // Herausgenommen, da die Flugzeugdarlehen nicht mehr benoetigt werden - CT 04.03.2020
          /*
          if (lvOkayDarlehen)
          {
            // Default-Finanzierung fuer Flugzeuge anlegen
            if (lvKredittyp == Darlehen.FLUGZEUGDARLEHEN)
            {
                  // TXSFinanzgeschaeft setzen --> eigentlich Finanzierung!!!
                  TXSFinanzgeschaeft lvFinanzierung = new TXSFinanzgeschaeft();
                  lvFinanzierung.setKey(pvDarlehen.getKontonummer());
                  lvFinanzierung.setOriginator(ValueMapping.changeMandant(ivVorlaufsatz.getInstitutsnummer()));
                  lvFinanzierung.setQuelle("AMIDFLUG");           
              
                  // TXSFinanzgeschaeftDaten setzen
                  TXSFinanzgeschaeftDaten lvFgdaten = new TXSFinanzgeschaeftDaten();
                  lvFgdaten.setAz(pvDarlehen.getKontonummer());
                  lvFgdaten.setAktivpassiv("1");
                  lvFgdaten.setKat("8");
                  lvFgdaten.setTyp("71");
              
                  // TXSKonditionenDaten setzen
                  TXSKonditionenDaten lvKond = new TXSKonditionenDaten();
                  lvKond.setKondkey(pvDarlehen.getKontonummer());
                     
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
          } */
    }

}
