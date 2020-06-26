package nlb.txs.schnittstelle.LoanIQ.RefiRegister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.Darlehen;
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

public class LoanIQ2RefiRegisterVerarbeitung 
{
    // Logger fuer LoanIQ
    private static Logger LOGGER_LOANIQ2RefiRegister = Logger.getLogger("TXSLoanIQ2RefiRegisterLogger");  
   
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
    private String ivLoanIQ2RefiRegisterOutputDatei;
    
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
     * Dateiname der Liste der Kontonummern
     */
    private String ivFilterDatei;

    /**
     * Dateiname der Liste der Kontonummern fuer BB
     */
    //private String ivFilterDateiBB;

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
     * Liste der zu verarbeitenden Kontonummern
     */
    private HashSet<String> ivListeKontonummern;
    
    /**
     * Liste der zu verarbeiteneden Kontonummern fuer BB
     */
    //private HashSet<String> ivListeKontonummernBB;
    
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
	    
    // Zaehlvariablen
    //private int ivAnzahlAF = 0;
    //private int ivAnzahlB = 0;
    private int ivAnzahlK = 0;
    private int ivAnzahlH = 0;
    private int ivAnzahlF = 0;
    private int ivAnzahlS = 0;
    private int ivAnzahlO = 0;
	
    /**
     * Liste der RefiDeepSea-Zusaetze
     */
    //private HashMap<String, RefiDeepSeaZusatz> ivListeRefiDeepSeaZusatz;
    
    /**
     * RefiRegister Aktiv
     */
    //private RefiRegisterAktiv ivRefiRegisterAktiv;
    
    /**
     * RefiRegister Passiv
     */
    private RefiRegisterPassiv ivRefiRegisterPassiv;
    
    /**
     * Konstruktor fuer LoanIQ2RefiRegister Verarbeitung
     * @param pvReader 
     */
    public LoanIQ2RefiRegisterVerarbeitung(IniReader pvReader)
    {        
        if (pvReader != null)
        {
            ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (ivInstitutsnummer.equals("Fehler"))
            {
            	LOGGER_LOANIQ2RefiRegister.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
                        
            ivImportVerzeichnis = pvReader.getPropertyString("LoanIQ", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
            	LOGGER_LOANIQ2RefiRegister.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivExportVerzeichnis = pvReader.getPropertyString("LoanIQ2RefiRegister", "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
            	LOGGER_LOANIQ2RefiRegister.error("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivLoanIQInputDatei =  pvReader.getPropertyString("LoanIQ", "LoanIQInput-Datei", "Fehler");
            if (ivLoanIQInputDatei.equals("Fehler"))
            {
            	LOGGER_LOANIQ2RefiRegister.error("Kein LoanIQInput-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            ivLoanIQ2RefiRegisterOutputDatei =  pvReader.getPropertyString("LoanIQ2RefiRegister", "LoanIQ2RefiRegisterOutput-Datei", "Fehler");
            if (ivLoanIQ2RefiRegisterOutputDatei.equals("Fehler"))
            {
            	LOGGER_LOANIQ2RefiRegister.error("Kein LoanIQ2RefiRegisterOutput-Dateiname in der ini-Datei...");
                System.exit(1);
            }           
            
            ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("SAPCMS", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
            {
            	LOGGER_LOANIQ2RefiRegister.error("Kein SAPCMS Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            
            ivSapcmsDatei = pvReader.getPropertyString("SAPCMS", "SAPCMS-Datei", "Fehler");
            if (ivSapcmsDatei.equals("Fehler"))
            {
            	LOGGER_LOANIQ2RefiRegister.error("Kein SAPCMS-Dateiname in der ini-Datei...");
                System.exit(1);
            }

            ivFilterDatei = pvReader.getPropertyString("LoanIQ2RefiRegister", "Filter-Datei", "Fehler");
            if (ivFilterDatei.equals("Fehler"))
            {
            	LOGGER_LOANIQ2RefiRegister.error("Kein Filter-Dateiname in der ini-Datei...");
            	System.exit(1);
            }

            //ivFilterDateiBB = pvReader.getPropertyString("LoanIQ2RefiRegister", "Filter-Datei-BB", "Fehler");
            //if (ivFilterDateiBB.equals("Fehler"))
            //{
            //	LOGGER_LOANIQ2RefiRegister.error("Kein Filter-Dateiname fuer BB in der ini-Datei...");
            //	System.exit(1);
            //}

            
            ivKundeRequestDatei = pvReader.getPropertyString("LoanIQ2RefiRegister", "KundeRequestDatei", "Fehler");
            if (ivKundeRequestDatei.equals("Fehler"))
            {
            	LOGGER_LOANIQ2RefiRegister.error("Kein KundeRequest-Dateiname in der ini-Datei...");
                System.exit(1); 
            }
            
            ivListeKontonummern = new HashSet<String>();
            // Dummy-Kontonummer damit die Liste nicht leer wird
            ivListeKontonummern.add("99999999999");
            
            //ivListeKontonummernBB = new HashSet<String>();
            // Dummy-Kontonummer damit die Liste nicht leer wird
            //ivListeKontonummernBB.add("99999999999");

            //ivListeRefiDeepSeaZusatz = new HashMap<String, RefiDeepSeaZusatz>();
            
            // RefiDeepSeaZusatz einlesen - CT 26.03.2019
            //ivListeRefiDeepSeaZusatz = readRefiDeepSeaZusatz(ivExportVerzeichnis + "\\BB_20190401.csv");
            
            // Liste der Kontonummern einlesen
            readListeKontonummern(ivListeKontonummern, ivExportVerzeichnis + "\\" + ivFilterDatei);
            
            // Liste der Kontonummern fuer BB einlesen
            //readListeKontonummern(ivListeKontonummernBB, ivExportVerzeichnis + "\\" + ivFilterDateiBB);
            
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
    	                
        // Sicherheiten-Daten einlesen
        ivSicherheitenDaten = new SicherheitenDaten(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, SicherheitenDaten.SAPCMS, LOGGER_LOANIQ2RefiRegister);
        
        // Darlehen XML-Datei im TXS-Format
        ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivLoanIQ2RefiRegisterOutputDatei, LOGGER_LOANIQ2RefiRegister);
        ivOutputDarlehenXML.openXML();
        ivOutputDarlehenXML.printXMLStart();
        ivOutputDarlehenXML.printTXSImportDatenStart();
        
        // RefiRegister-Passiv
        ivRefiRegisterPassiv = new RefiRegisterPassiv(ivSicherheitenDaten, ivOutputDarlehenXML, LOGGER_LOANIQ2RefiRegister);
        //ivRefiRegisterAktiv = new RefiRegisterAktiv(ivSicherheitenDaten, ivOutputDarlehenXML, LOGGER_LOANIQ2RefiRegister);
        
        // LoanIQ-Daten einlesen und verarbeiten
        readLoanIQ(ivImportVerzeichnis + "\\" + ivLoanIQInputDatei);

        ivOutputDarlehenXML.printTXSImportDatenEnde();
        ivOutputDarlehenXML.closeXML();
        
        // Statistik ausgeben
        LOGGER_LOANIQ2RefiRegister.info(getStatistik());
                
        // KundeRequest-Datei schliessen
        try
        {
        	ivKundennummernOutput.close();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ2RefiRegister.error("Fehler beim Schliessen der KundeRequest-Datei");        	
        }
    }
    
    /**
     * Einlesen und Verarbeiten der LoanIQ-Daten
     * @param pvDateiname
     */
    private void readLoanIQ(String pvDateiname)
    {
        String lvZeile = null;
        ivVorlaufsatz = new Vorlaufsatz(LOGGER_LOANIQ2RefiRegister);
        //TODO ivSicherheitenDaten.getSicherheiten2Pfandbrief() -> Sicherheiten2RefiRegister?
        ivDarlehenBlock = new DarlehenBlock(ivVorlaufsatz, ivSicherheitenDaten.getSicherheiten2RefiRegister(), LOGGER_LOANIQ2RefiRegister);
              
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File ivFileLoanIQ = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileLoanIQ);
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ2RefiRegister.error("Konnte LoanIQ-Datei nicht oeffnen!");
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
                    ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_LOANIQ2RefiRegister);
                    ivKundennummernOutput.open();
                    ivKundennummernOutput.printVorlaufsatz(ivInstitutsnummer, "RefiReg");
               }
                else
                {
                    //System.out.println("lvZeile: " + lvZeile);
                    if (!ivDarlehenBlock.parseDarlehen(lvZeile, LOGGER_LOANIQ2RefiRegister)) // Parsen der Felder
                    {        
                    	// Unterschiedliche Kontonummer -> Darlehen verarbeiten    
                    	if (ivListeKontonummern.contains(ivDarlehenBlock.getKontonummer()))
                    	{
                    		verarbeiteDarlehenPassiv();
                    	}

                        // Neuen LoanIQ-Block anlegen
                        ivDarlehenBlock = new DarlehenBlock(ivVorlaufsatz, new Sicherheiten2Pfandbrief(ivSicherheitenDaten, LOGGER_LOANIQ2RefiRegister), LOGGER_LOANIQ2RefiRegister);
                        // Zeile mit neuer Kontonummer muss noch verarbeitet werden
                        if (ivDarlehenBlock.parseDarlehen(lvZeile, LOGGER_LOANIQ2RefiRegister)) // Einlesen der Felder
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
        	if (ivListeKontonummern.contains(ivDarlehenBlock.getKontonummer()))
        	{
        		verarbeiteDarlehenPassiv();
        	}
         }
        catch (Exception e)
        {
            LOGGER_LOANIQ2RefiRegister.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
              
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ2RefiRegister.error("Konnte LoanIQ-Datei nicht schliessen!");
        }           
    }
    
    /**
     * Liest die RefiZusatz-Datei ein
     * @param pvDateiname
     */
    /*
    private HashMap<String, RefiDeepSeaZusatz> readRefiDeepSeaZusatz(String pvDateiname)
    {
    	LOGGER_LOANIQ2RefiRegister.info("Start - readRefiDeepSeaZusatz");
        String lvZeile = null;
        int lvZaehlerRefiDeepSeaZusatz = 0;
        HashMap<String, RefiDeepSeaZusatz> lvListeRefiDeepSeaZusatz = new HashMap<String, RefiDeepSeaZusatz>();
                      
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File lvFileRefiDeepSeaZusatz = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(lvFileRefiDeepSeaZusatz);
        }
        catch (Exception e)
        {
        	LOGGER_LOANIQ2RefiRegister.error("Konnte RefiDeepSeaZusatz-Datei nicht oeffnen!");
            return null;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
             
        RefiDeepSeaZusatz lvRefiDeepSeaZusatz = null;
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen RefiZusatz-Datei
            {
                lvRefiDeepSeaZusatz = new RefiDeepSeaZusatz();
                lvRefiDeepSeaZusatz.parseRefiDeepSeaZusatz(lvZeile, lvZaehlerRefiDeepSeaZusatz);
                lvListeRefiDeepSeaZusatz.put(lvRefiDeepSeaZusatz.getKontonummer() + "_" + lvRefiDeepSeaZusatz.getSicherheitenID(), lvRefiDeepSeaZusatz);
                lvZaehlerRefiDeepSeaZusatz++;
            }
        }
        catch (Exception e)
        {
        	LOGGER_LOANIQ2RefiRegister.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
        
        LOGGER_LOANIQ2RefiRegister.info("Anzahl ivListeRefiDeepSeaZusatz: " + lvListeRefiDeepSeaZusatz.size());
        LOGGER_LOANIQ2RefiRegister.info("Anzahl gelesener Zeilen in der RefiDeepSeaZusatz-Datei: " + lvZaehlerRefiDeepSeaZusatz);
        
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
        	LOGGER_LOANIQ2RefiRegister.error("Konnte RefiZusatz-Datei nicht schliessen!");
        }   
        
        return lvListeRefiDeepSeaZusatz;
     } */
    
    /**
     * Liest die Liste der Kontonummern ein
     * @param pvListeKontonummern Liste der Kontonummern
     * @param pvDateiname Dateiname der Kontonummernliste
     */
    private void readListeKontonummern(HashSet<String> pvListeKontonummern, String pvDateiname)
    {
        LOGGER_LOANIQ2RefiRegister.info("Start - readListeKontonummern");
        LOGGER_LOANIQ2RefiRegister.info("Datei: " + pvDateiname);
        String lvZeile = null;
                      
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File lvFileListeKontonummern = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(lvFileListeKontonummern);
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ2RefiRegister.error("Konnte die Kontonummernliste-Datei '" + pvDateiname +"' nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
             
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen der ListeKontonummern-Datei
            {
                LOGGER_LOANIQ2RefiRegister.info("Verarbeite Kontonummer: " + lvZeile);
                pvListeKontonummern.add(lvZeile);
            }
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ2RefiRegister.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
        
        LOGGER_LOANIQ2RefiRegister.info("Anzahl von Kontonummern in der Liste: " + pvListeKontonummern.size());
        
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_LOANIQ2RefiRegister.error("Konnte die Kontonummernliste-Datei '" + pvDateiname + "' nicht schliessen!");
        }     
     }

    /**
     * Liefert Statistik Informationen als String
     * @return String mit Statistik Informationen
     */
    private String getStatistik()
    {
        StringBuffer lvOut = new StringBuffer();
        lvOut.append(ivVorlaufsatz.toString());
        
        lvOut.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerFinanzgeschaefteBrutto + " Brutto-Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerFinanzgeschaefteNetto + " Netto-Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerFinanzgeschaefteFremd + " Fremd-Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
        //if (ivListeRefiDeepSeaZusatz != null)
        //{
        //   lvOut.append(ivListeRefiDeepSeaZusatz.size() + " RefiDeepSeaZusatz gelesen..." + StringKonverter.lineSeparator);
        //}
        lvOut.append(ivAnzahlK + " mit Ausplatzierungsmerkmal Kx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlH + " mit Ausplatzierungsmerkmal Hx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlF + " mit Ausplatzierungsmerkmal Fx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlS + " mit Ausplatzierungsmerkmal Sx" + StringKonverter.lineSeparator);
        lvOut.append(ivAnzahlO + " mit Ausplatzierungsmerkmal Ox" + StringKonverter.lineSeparator);
        
        return lvOut.toString();
    }

    /**
     * Verarbeite den DarlehenBlock in "passiver" Weise
     */
    private void verarbeiteDarlehenPassiv()
    {  
        // LoanIQ2RefiRegister Verarbeitung
    	////if (ivListeKontonummern.contains(ivDarlehenBlock.getKontonummer()))
    	////{
    		LOGGER_LOANIQ2RefiRegister.info("Verarbeite Kontonummer: " + ivDarlehenBlock.getKontonummer());
    		
			if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("K")) ivAnzahlK++;
			if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("H")) ivAnzahlH++;
			if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("F")) ivAnzahlF++;
			if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("S")) ivAnzahlS++;
			if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("O")) ivAnzahlO++;

    		if (ivDarlehenBlock.getListeDarlehenFremd().size() > 0)
    		{
    			for (Darlehen lvDarlehen: ivDarlehenBlock.getListeDarlehenFremd())
    			{
    				// Konsortialnummer in die KundeRequest-Datei schreiben
    				if (!lvDarlehen.getKonsortialfuehrer().isEmpty())
    				{
    					ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehen.getKonsortialfuehrer(), LOGGER_LOANIQ2RefiRegister));   
    				}
    				// Kundennummer in die KundeRequest-Datei schreiben
    				if (!lvDarlehen.getKundennummer().isEmpty())
    				{
    					ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_LOANIQ2RefiRegister));
    				}
  
    				// TXS-Transaktionen mit Darlehensinformationen fuellen
    				ivRefiRegisterPassiv.importDarlehen2Transaktion(lvDarlehen, ivVorlaufsatz); //, ivListeRefiDeepSeaZusatz);
    			}

    		}
    		else
    		{
    			LOGGER_LOANIQ2RefiRegister.info("Keine Fremdanteile...");
    			return;
    		}
    	////}
    }
}
