/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.RefiRegister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import nlb.txs.schnittstelle.Darlehen.DarlehenXML;
import nlb.txs.schnittstelle.Darlehen.Daten.DarlehenKomplett;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherheit;
import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherungsobjekt;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.BAUFI;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.DWHVOR;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.INF;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KON;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KONTS;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KTOZB;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KTR;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.KTS;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.OBJ;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.REC;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.UMS;
import nlb.txs.schnittstelle.Darlehen.LeseVorlaufsatz;
import nlb.txs.schnittstelle.Darlehen.SicherheitenListe;
import nlb.txs.schnittstelle.Darlehen.SicherheitenXML;
import nlb.txs.schnittstelle.Darlehen.SicherungsobjekteListe;
import nlb.txs.schnittstelle.Darlehen.SicherungsobjekteXML;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Sicherheiten.SicherheitenDaten;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * 
 * @author tepperc
 */
public class RefiRegisterVerarbeitung 
{
    // Verarbeitungsmodus RefiRegister
    public static final int REFI_REG = 0;
    
    // Verarbeitungsmodus LettreDeGage
    //public static final int LETTRE_DE_GAGE = 1;
    
    // Verarbeitungsmodus DeepSea
    //public static final int DEEP_SEA = 2;
	
    // Logger fuer RefiRegister
    private static Logger LOGGER_REFI_REGISTER = Logger.getLogger("TXSRefiRegisterLogger"); 

    // Status BAUFI
    private final int BAUFI = 1;
    
    // Status INF
    private final int INF = 2;
    
    // Status KON
    private final int KON = 3;
    
    // Status KONTS
    private final int KONTS = 4;

    // Status KTOZB
    private final int KTOZB = 5;

    // Status KTR
    private final int KTR = 6;

    // Status KTS
    private final int KTS = 7;

    // Status OBJ
    private final int OBJ = 8;

    // Status REC
    private final int REC = 9;

    // Status UMS
    private final int UMS = 10;
    
    // Status undefiniert
    private final int UNDEFINIERT = 11;

    /**
     * DarKa Status 
     */
    private int ivStatus;
        
    /**
     * Verarbeitungsmodus
     * (REFI_REG, LETTRE_DE_GAGE oder DEEP_SEA)
     */
    private int ivVerarbeitungsmodus;
    
    // Ini-Parameter
    private String ivInstitutsnummer;
    private String ivImportVerzeichnis;
    private String ivExportVerzeichnis;
    private String ivDarlehenImportDatei;
    private String ivDarlehenTXSDatei;
    private String ivDarlehenDatei;
    private String ivSicherheitenDatei;
    private String ivSicherungsobjekteDatei;
    private String ivImportVerzeichnisSAPCMS;
    private String ivFilterDatei;
    private String ivSapcmsDatei;
    
    /**
     * RefiRegister Passiv
     */
    private RefiRegisterPassiv ivRefiRegisterPassiv;
        
    /**
     * RefiRegister Aktiv
     */
    //private RefiRegisterAktiv ivRefiRegisterAktiv;
    
    /**
     * Kundennummer
     */
    private String ivKundennummer;     
    
    /**
     * Objektnummer 
     */
    private String ivObjektnummer;
    
    /**
     * Kontonummer
     */                             
    private String ivKontonummer;   
    private String ivAlteKontonummer;

    private File ivFileDarlehen;    
    
    private DWHVOR ivVorlaufsatz;
    private DarlehenKomplett ivOriginalDarlehen;
    private Darlehen ivZielDarlehen;
    private BAUFI ivBaufi;
    private INF ivInf;
    private KON ivKon;
    private KONTS ivKonts;
    private KTOZB ivKtozb;
    private KTR ivKtr;
    private KTS ivKts;
    private OBJ ivObj;
    private REC ivRec;
    private UMS ivUms;
    
    // Zaehlervariablen
    private int ivZaehlerVorlaufsatz = 0;
    private int ivZaehlerBAUFI = 0;
    private int ivZaehlerINF = 0;
    private int ivZaehlerKON = 0;
    private int ivZaehlerKONTS = 0;
    private int ivZaehlerKTOZB = 0;
    private int ivZaehlerKTR = 0;
    private int ivZaehlerKTS = 0;
    private int ivZaehlerOBJ = 0;
    private int ivZaehlerREC = 0;
    private int ivZaehlerUMS = 0;
    private int ivZaehlerRefiRegisterFilterElemente = 0;
    private int ivZaehlerRefiDeepSeaZusatz = 0;   
    
    private DarlehenXML ivDarlehenXML;
    private OutputDarlehenXML ivOutputDarlehenXML;
    
    // Sicherheiten aus Darlehen
    private SicherheitenXML ivSicherheitenXML;
    private SicherheitenListe ivListeSicherheiten;
    
    // Sicherungsobjekte aus Darlehen
    private SicherungsobjekteXML ivSicherungsObjXML;
    private SicherungsobjekteListe ivListeSicherungsobjekte;
    
    // Sicherheiten-Daten
    private SicherheitenDaten ivSicherheitenDaten;
               
    /**
     * 
     */
    private HashMap<String, RefiRegisterFilterElement> ivListeRefiRegisterFilterElemente;
    
    /**
     * Liste der Refi-Zusaetze
     */
    private HashMap<String, RefiZusatz> ivListeRefiZusatz;
    
    /**
     * Liste der RefiDeepSea-Zusaetze
     */
    //private HashMap<String, RefiDeepSeaZusatz> ivListeRefiDeepSeaZusatz;
    
    /** 
     * Liste der Kontonummern die verarbeitet werden
     */
    private HashSet<String> ivListeKontonummern;
    
    /**
     * Konstruktor fuer RefiRegister Verarbeitung
     * @param pvReader 
     * @param pvVerarbeitungsmodus
     */
    public RefiRegisterVerarbeitung(IniReader pvReader, int pvVerarbeitungsmodus)
    {
    	String lvModus = "RefiRegister"; // Default RefiRegister
    	//if (pvVerarbeitungsmodus == REFI_REG)
    	//{
    	//	lvModus = "RefiRegister";
    	//}
    	//if (pvVerarbeitungsmodus == DEEP_SEA)
    	//{
    	//	lvModus = "RefiRegister_DeepSea";
    	//}
    	//if (pvVerarbeitungsmodus == LETTRE_DE_GAGE)
    	//{
    	//	lvModus = "RefiRegister_LettreDeGage";
    	//}
    	
        if (pvReader != null)
        {
            ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (ivInstitutsnummer.equals("Fehler"))
            {
                LOGGER_REFI_REGISTER.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("Institut: " + ivInstitutsnummer);
            }
                        
            ivImportVerzeichnis = pvReader.getPropertyString(lvModus, "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_REFI_REGISTER.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("ImportVerzeichnis: " + ivImportVerzeichnis);
            }

            ivExportVerzeichnis = pvReader.getPropertyString(lvModus, "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                LOGGER_REFI_REGISTER.error("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("ExportVerzeichnis: " + ivExportVerzeichnis);
            }

            ivDarlehenImportDatei =  pvReader.getPropertyString(lvModus, "DarlehenImport-Datei", "Fehler");
            if (ivDarlehenImportDatei.equals("Fehler"))
            {
                LOGGER_REFI_REGISTER.error("Kein DarlehenImport-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("DarlehenImport-Datei: " + ivDarlehenImportDatei);
            }

            ivDarlehenTXSDatei =  pvReader.getPropertyString(lvModus, "DarlehenTXS-Datei", "Fehler");
            if (ivDarlehenTXSDatei.equals("Fehler"))
            {
                LOGGER_REFI_REGISTER.error("Kein DarlehenTXS-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("DarlehenTXS-Datei: " + ivDarlehenTXSDatei);
            }

            ivDarlehenDatei = pvReader.getPropertyString(lvModus, "Darlehen-Datei", "Fehler");
            if (ivDarlehenDatei.equals("Fehler"))
            {
                LOGGER_REFI_REGISTER.error("Kein Darlehen-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("Darlehen-Datei: " + ivDarlehenDatei);
            }

            ivSicherheitenDatei = pvReader.getPropertyString(lvModus, "Sicherheiten-Datei", "Fehler");
            if (ivSicherheitenDatei.equals("Fehler"))
            {
                LOGGER_REFI_REGISTER.error("Kein Sicherheiten-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("SicherheitenDatei: " + ivSicherheitenDatei);
            }

            ivSicherungsobjekteDatei = pvReader.getPropertyString(lvModus, "Sicherungsobjekte-Datei", "Fehler");
            if (ivSicherungsobjekteDatei.equals("Fehler"))
            {
                LOGGER_REFI_REGISTER.error("Kein Sicherungsobjekte-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("Sicherungsobjekte-Datei: " + ivSicherungsobjekteDatei);
            }

            ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("Sicherheiten", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
            {
                LOGGER_REFI_REGISTER.error("Kein Sicherheiten Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("Sicherheiten ImportVerzeichnis: " + ivImportVerzeichnisSAPCMS);
            }

            ivFilterDatei = pvReader.getPropertyString(lvModus, "Filter-Datei", "Fehler");
            if (ivFilterDatei.equals("Fehler"))
            {
            	LOGGER_REFI_REGISTER.error("Kein Filter-Dateiname in der ini-Datei...");
            	System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("Filter-Datei: " + ivFilterDatei);
            }

            ivSapcmsDatei = pvReader.getPropertyString("Sicherheiten", "Sicherheiten-Datei", "Fehler");
            if (ivSapcmsDatei.equals("Fehler"))
            {
                LOGGER_REFI_REGISTER.error("Kein Sicherheiten-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_REFI_REGISTER.info("Sicherheiten-Datei: " + ivSapcmsDatei);
            }

            this.ivVerarbeitungsmodus = pvVerarbeitungsmodus;
            this.ivRefiRegisterPassiv = new RefiRegisterPassiv(ivExportVerzeichnis, LOGGER_REFI_REGISTER);
            //this.ivRefiRegisterAktiv = new RefiRegisterAktiv(LOGGER_REFI_REGISTER);
        
            ivListeRefiRegisterFilterElemente = new HashMap<String, RefiRegisterFilterElement>();
            ivListeRefiZusatz = new HashMap<String, RefiZusatz>();
            //ivListeRefiDeepSeaZusatz = new HashMap<String, RefiDeepSeaZusatz>();
            ivListeKontonummern = new HashSet<String>();
            // Dummy-Kontonummer damit die Liste nicht leer wird
            ivListeKontonummern.add("99999999999");
            
            // RefiRegisterFilterElemente einlesen
            readRefiRegisterFilterElemente(ivExportVerzeichnis + "\\Konsortialgeschaefte.txt");
            
            // RefiZusatz einlesen - CT 18.02.2016 herausgenommen - Nur bei der initialen Lieferung notwendig
            //ivListeRefiZusatz = readRefiZusatz(ivExportVerzeichnis + "\\RefiListe_Pfandobjekt.csv");
            
            // RefiDeepSeaZusatz einlesen - CT 10.02.2017
            //if (pvVerarbeitungsmodus == DEEP_SEA)
            //{
            //  readRefiDeepSeaZusatz("C:\\DeepSea\\DeepSea-Abzug_20170210.csv");
            //}
            
            // Liste der Kontonummern einlesen
            readListeKontonummern(ivExportVerzeichnis + "\\" + ivFilterDatei);
            
            // Verarbeitung starten
            startVerarbeitung();
        }
    }
    
    /**
     * Liest die RefiRegisterFilterElemente-Datei ein
     * @param pvDateiname
     */
    private void readRefiRegisterFilterElemente(String pvDateiname)
    {
        LOGGER_REFI_REGISTER.info("Start - readRefiRegisterFilterElemente");
        String lvZeile = null;
                      
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File lvFileRefiRegisterFilterElemente = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(lvFileRefiRegisterFilterElemente);
        }
        catch (Exception e)
        {
            LOGGER_REFI_REGISTER.error("Konnte RefiRegisterFilterElemente-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
             
        RefiRegisterFilterElement lvRefiRegisterFilterElement = null;
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen RefiRegisterFilterElemente-Datei
            {
                lvRefiRegisterFilterElement = new RefiRegisterFilterElement();
                lvRefiRegisterFilterElement.parseRefiRegisterFilterElement(lvZeile);
                ivListeRefiRegisterFilterElemente.put(lvRefiRegisterFilterElement.getKontonummer(), lvRefiRegisterFilterElement);
                ivZaehlerRefiRegisterFilterElemente++;
            }
        }
        catch (Exception e)
        {
            LOGGER_REFI_REGISTER.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
              
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_REFI_REGISTER.error("Konnte RefiRegisterFilterElemente-Datei nicht schliessen!");
        }     
     }

    /**
     * Liest die RefiZusatz-Datei ein
     * @param pvDateiname
     */
    private HashMap<String, RefiZusatz> readRefiZusatz(String pvDateiname)
    {
        LOGGER_REFI_REGISTER.info("Start - readRefiZusatz");
        String lvZeile = null;
        int lvZaehlerRefiZusatz = 0;
        HashMap<String, RefiZusatz> lvListeRefiZusatz = new HashMap<String, RefiZusatz>();
                      
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File lvFileRefiZusatz = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(lvFileRefiZusatz);
        }
        catch (Exception e)
        {
        	LOGGER_REFI_REGISTER.error("Konnte RefiZusatz-Datei nicht oeffnen!");
            return null;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
             
        RefiZusatz lvRefiZusatz = null;
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen RefiZusatz-Datei
            {
                lvRefiZusatz = new RefiZusatz();
                lvRefiZusatz.parseRefiZusatz(lvZeile, lvZaehlerRefiZusatz);
                lvListeRefiZusatz.put(lvRefiZusatz.getPassivkontonummer() + lvRefiZusatz.getSicherheitenID(), lvRefiZusatz);
                lvZaehlerRefiZusatz++;
            }
        }
        catch (Exception e)
        {
        	LOGGER_REFI_REGISTER.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
        
        LOGGER_REFI_REGISTER.info("Anzahl ivListeRefiZusatz: " + lvListeRefiZusatz.size());
        LOGGER_REFI_REGISTER.info("Anzahl gelesener Zeilen in der RefiZusatz-Datei: " + lvZaehlerRefiZusatz);
        
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
        	LOGGER_REFI_REGISTER.error("Konnte RefiZusatz-Datei nicht schliessen!");
        }   
        
        return lvListeRefiZusatz;
     }
    
    /**
     * Liest die RefiDeepSeaZusatz-Datei ein
     * @param pvDateiname
     */
    private void readRefiDeepSeaZusatz(String pvDateiname)
    {
        LOGGER_REFI_REGISTER.info("Start - readRefiDeepSeaZusatz");
        String lvZeile = null;
                      
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File lvFileRefiZusatz = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(lvFileRefiZusatz);
        }
        catch (Exception e)
        {
            LOGGER_REFI_REGISTER.error("Konnte RefiDeepSeaZusatz-Datei nicht oeffnen!");
            LOGGER_REFI_REGISTER.error("Datei: " + pvDateiname);
            return; 
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
             
        /* RefiDeepSeaZusatz lvRefiDeepSeaZusatz = null;
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen RefiZusatz-Datei
            {
                lvRefiDeepSeaZusatz = new RefiDeepSeaZusatz();
                lvRefiDeepSeaZusatz.parseRefiDeepSeaZusatz(lvZeile, ivZaehlerRefiDeepSeaZusatz);
                //System.out.println("Hinzugefuegt: " + lvRefiDeepSeaZusatz.getKontonummer() + lvRefiDeepSeaZusatz.getSicherheitenID());
                ivListeRefiDeepSeaZusatz.put(lvRefiDeepSeaZusatz.getKontonummer() + lvRefiDeepSeaZusatz.getSicherheitenID(), lvRefiDeepSeaZusatz);
                ivListeRefiDeepSeaZusatz.put(lvRefiDeepSeaZusatz.getKontonummer(), lvRefiDeepSeaZusatz);
                ivZaehlerRefiDeepSeaZusatz++;
            }
        }
        catch (Exception e)
        {
            LOGGER_REFI_REGISTER.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
        
        LOGGER_REFI_REGISTER.info("Anzahl ivListeRefiDeepSeaZusatz: " + ivListeRefiDeepSeaZusatz.size());
        */
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_REFI_REGISTER.error("Konnte RefiDeepSeaZusatz-Datei nicht schliessen!");
        }     
     }    
 
    /**
     * Liest die Liste der Kontonummern ein
     * @param pvDateiname
     */
    private void readListeKontonummern(String pvDateiname)
    {
        LOGGER_REFI_REGISTER.info("Start - readListeKontonummern");
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
            LOGGER_REFI_REGISTER.error("Konnte die ListeKontonummern-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
             
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen der ListeKontonummern-Datei
            {
                LOGGER_REFI_REGISTER.info("Verarbeite Kontonummer: " + lvZeile);
                ivListeKontonummern.add(lvZeile);
            }
        }
        catch (Exception e)
        {
            LOGGER_REFI_REGISTER.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
        
        LOGGER_REFI_REGISTER.info("Anzahl ivListeKontonummern: " + ivListeKontonummern.size());
        
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_REFI_REGISTER.error("Konnte die ListeKontonummern-Datei nicht schliessen!");
        }     
     }
    
    /**
     * Startet die Verarbeitung
     */
    private void startVerarbeitung()
    {        
        Calendar lvCal = Calendar.getInstance();
        CalendarHelper lvCh = new CalendarHelper();

        LOGGER_REFI_REGISTER.info("Start: " + lvCh.printDateAndTime(lvCal));
                          
        // Sicherheiten-Datei einlesen
        ivSicherheitenDaten = new SicherheitenDaten(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, SicherheitenDaten.SAPCMS, LOGGER_REFI_REGISTER);
                
        // Darlehen XML-Datei
        ivDarlehenXML = new DarlehenXML(ivExportVerzeichnis + "\\" + ivDarlehenDatei, LOGGER_REFI_REGISTER);
        ivDarlehenXML.openXML();
        
        // Darlehen XML-Datei im TXS-Format
        ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivDarlehenTXSDatei, LOGGER_REFI_REGISTER);
        ivOutputDarlehenXML.openXML();
        ivOutputDarlehenXML.printXMLStart();
                
        // Sicherheiten XML-Datei 
        ivSicherheitenXML = new SicherheitenXML(ivExportVerzeichnis + "\\" + ivSicherheitenDatei, LOGGER_REFI_REGISTER);
        ivSicherheitenXML.openXML();
        ivListeSicherheiten = new SicherheitenListe();
        
        // Sicherungsobjekte XML-Datei
        ivSicherungsObjXML = new SicherungsobjekteXML(ivExportVerzeichnis + "\\" + ivSicherungsobjekteDatei, LOGGER_REFI_REGISTER);
        ivSicherungsObjXML.openXML();
        ivListeSicherungsobjekte = new SicherungsobjekteListe();
        
        // Darlehen einlesen und verarbeiten
        readDarlehen(ivImportVerzeichnis + "\\" + ivDarlehenImportDatei);
  
        ivSicherheitenXML.printXMLEnde();
        ivSicherheitenXML.closeXML();
        
        ivSicherungsObjXML.printXMLEnde();
        ivSicherungsObjXML.closeXML();
        
        ivDarlehenXML.printXMLEnde();
        ivDarlehenXML.closeXML();
        
        ivOutputDarlehenXML.printTXSImportDatenEnde();
        ivOutputDarlehenXML.closeXML();
                 
        LOGGER_REFI_REGISTER.info("Nicht gefundene Kontonummern:" + StringKonverter.lineSeparator);
        for (String lvKontonummer:ivListeKontonummern)
        {
        	LOGGER_REFI_REGISTER.info(lvKontonummer + StringKonverter.lineSeparator);
        }
        
        LOGGER_REFI_REGISTER.info(getStatistik());
        
        lvCal = Calendar.getInstance();
        LOGGER_REFI_REGISTER.info("Ende: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
    }

    /**
     * Liest die Darlehen ein
     * @param pvDateiname
     */
    private void readDarlehen(String pvDateiname)
    {
        String lvZeile = null;
        
        ivOriginalDarlehen = new DarlehenKomplett(LOGGER_REFI_REGISTER);
        
        // Oeffnen der Dateien
        FileInputStream lvFileInputStream = null;
        ivFileDarlehen = new File(pvDateiname);
        try
        {
            lvFileInputStream = new FileInputStream(ivFileDarlehen);
        }
        catch (Exception e)
        {
            LOGGER_REFI_REGISTER.error("Konnte Darlehen-Datei nicht oeffnen!");
            return;
        }

        BufferedReader lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));
        boolean lvStart = true;
          
        try
        {
            while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Lesen Darlehen-Datei
            {
              if (lvStart)
              {
                  LeseVorlaufsatz lvLeseVorlaufsatz = new LeseVorlaufsatz(); 
                  ivVorlaufsatz = lvLeseVorlaufsatz.parseVorlaufsatz(lvZeile);
                  ivZaehlerVorlaufsatz++;
                  ivOriginalDarlehen.setAnzahlDatum(ivVorlaufsatz.getAnzahlDatum());
                  ivOriginalDarlehen.setBuchungsdatum(ivVorlaufsatz.getsDwvbdat());
                  ivOriginalDarlehen.setInstitutsnummer(ivVorlaufsatz.getsDwvinst());
                  ivSicherheitenXML.printXMLStart(ivVorlaufsatz);
                  ivSicherungsObjXML.printXMLStart(ivVorlaufsatz);
                  ivDarlehenXML.printXMLStart(ivVorlaufsatz);
                  ivOutputDarlehenXML.printTXSImportDatenStart();
                  ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(ivVorlaufsatz.getsDwvbdat())));
                  lvStart = false;
              }
              else
              {
                  if (!parseDarlehen(lvZeile)) // Parsen der Felder
                  {
                      LOGGER_REFI_REGISTER.error("Datenfehler: " + lvZeile);
                  }

                  if (!ivKontonummer.equals("0000000000"))
                  {
                      einfuegenSegment();
                      ivAlteKontonummer = ivKontonummer;
                  }
              }
            }
          }
          catch (Exception e)
          {
            LOGGER_REFI_REGISTER.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
          }

          LOGGER_REFI_REGISTER.info("Konto: " + ivKts.getKopf().getsDwhknr());
          // Das letzte Darlehen muss noch verarbeitet werden
          if (ivKtr != null)
          {
            
            // Pruefe, ob das Datum im KON, KONTS und KTOZB korrekt ist
            ivOriginalDarlehen.pruefeDatum();
            ////if (ivKts.getsDkom().equals("21") || ivKts.getsDkom().equals("22") || ivKts.getsDkom().equals("23"))
            ////{
                if (ivListeKontonummern.isEmpty() || ivListeKontonummern.contains(ivKts.getKopf().getsDwhknr()) || 
                    ivKtr.getAusplatzierungsmerkmal().equals("B1") ||  // DeepSea
                    ivKtr.getAusplatzierungsmerkmal().startsWith("G")) // LettreDeGage
                {
                  LOGGER_REFI_REGISTER.info("Kontonummer " + ivKts.getKopf().getsDwhknr() + " in der Liste enthalten...");
                  ivListeKontonummern.remove(ivKts.getKopf().getsDwhknr());
                  //if (ivVerarbeitungsmodus == DEEP_SEA || ivVerarbeitungsmodus == LETTRE_DE_GAGE)
                  //{
                	//  verarbeiteAktiv();
                  //}
                  if (ivVerarbeitungsmodus == REFI_REG)
                  {
                	  verarbeitePassiv();
                  }
                  LOGGER_REFI_REGISTER.info("Refi;Refi;" + ivKts.getKopf().getsDwhknr() + ";" + ivKtr.getsDbue());
                }
            ////}
          }
          else
          {
        	  LOGGER_REFI_REGISTER.info("KTR == null");
          }
          
          try
          {
            lvBufferedReaderIn.close();
          }
          catch (Exception e)
          {
            LOGGER_REFI_REGISTER.error("Konnte Darlehen-Datei nicht schliessen!");
          }
             
        }
 
    /**
     * Verarbeitet das Passiv-Darlehen
     */
    private void verarbeitePassiv()
    {         
    	LOGGER_REFI_REGISTER.info("Start verarbeitePassiv...");

         // OBJ-Segment vorhanden?
        if (ivOriginalDarlehen.getOBJ() != null)
        {
            // Sicherheiten rausschreiben
            Sicherheit sicherheit = new Sicherheit();
            if (sicherheit.extractSicherheit(ivOriginalDarlehen))
            {
                if (!ivListeSicherheiten.contains(sicherheit.getObjektnummer()))
                {
                    ivListeSicherheiten.put(sicherheit.getObjektnummer(), sicherheit);
                    ivSicherheitenXML.printSicherheit(sicherheit);
                }
            }
         
            // Sicherungsobjekte rausschreiben
            Sicherungsobjekt sObj = new Sicherungsobjekt();
            if (sObj.extractSicherungsobjekt(ivOriginalDarlehen, LOGGER_REFI_REGISTER))
            {
                if (!ivListeSicherungsobjekte.contains(sObj.getObjektnummer()))
                {
                    ivListeSicherungsobjekte.put(sObj.getObjektnummer(), sObj);
                    ivSicherungsObjXML.printSicherungsobjekte(sObj);
                }
            }
        }     
        
       ivZielDarlehen = new Darlehen(LOGGER_REFI_REGISTER, new String()); // Umsatz19-Datei wird nicht benoetigt -> new String()
       ivZielDarlehen.extractDarlehen(ivOriginalDarlehen);
       
       //System.out.println("Kontotyp: " + ivZielDarlehen.getKontotyp());
       //System.out.println("Kontozustand: " + ivZielDarlehen.getKontozustand());
       if (StringKonverter.convertString2Int(ivZielDarlehen.getKontotyp()) == 4)
       {  
           int lvHelpInt = StringKonverter.convertString2Int(ivZielDarlehen.getKontozustand());
           
           if (lvHelpInt < 8)
           { 
              ivDarlehenXML.printDarlehen(ivZielDarlehen);
              try
              {
                  ivRefiRegisterPassiv.importDarlehen2Transaktion(ivZielDarlehen, ivOutputDarlehenXML, ivSicherheitenDaten, ivListeRefiRegisterFilterElemente);//, ivListeRefiZusatz);
              }
              catch (Exception exp)
              {
                  LOGGER_REFI_REGISTER.error("Verarbeitungsfehler - Konto " + ivZielDarlehen.getKontonummer() + StringKonverter.lineSeparator);
                  LOGGER_REFI_REGISTER.error(exp.getMessage());
                  exp.printStackTrace();
              }
           }
           else
           {
               LOGGER_REFI_REGISTER.info("-->Konto " + ivZielDarlehen.getKontonummer() + "("
                       + ivZielDarlehen.getHerkunftDarlehen() + ";" + ivZielDarlehen.getHerkunftDaten()
                       + ";" + ivZielDarlehen.getKundennummer() + ";" + ivZielDarlehen.getObjektnummer()
                       + ")KtoZustand-" + ivZielDarlehen.getKontozustand() + "-darf nicht");
           }
        }      
    }
 
    /**
     * Verarbeitet das Aktiv-Darlehen
     */
    private void verarbeiteAktiv()
    {       
    	LOGGER_REFI_REGISTER.info("Start verarbeiteAktiv...");
         // OBJ-Segment vorhanden?
        if (ivOriginalDarlehen.getOBJ() != null)
        {
            // Sicherheiten rausschreiben
            Sicherheit sicherheit = new Sicherheit();
            if (sicherheit.extractSicherheit(ivOriginalDarlehen))
            {
                if (!ivListeSicherheiten.contains(sicherheit.getObjektnummer()))
                {
                    ivListeSicherheiten.put(sicherheit.getObjektnummer(), sicherheit);
                    ivSicherheitenXML.printSicherheit(sicherheit);
                }
            }
         
            // Sicherungsobjekte rausschreiben
            Sicherungsobjekt sObj = new Sicherungsobjekt();
            if (sObj.extractSicherungsobjekt(ivOriginalDarlehen, LOGGER_REFI_REGISTER))
            {
                if (!ivListeSicherungsobjekte.contains(sObj.getObjektnummer()))
                {
                    ivListeSicherungsobjekte.put(sObj.getObjektnummer(), sObj);
                    ivSicherungsObjXML.printSicherungsobjekte(sObj);
                }
            }
        }     
        
       ivZielDarlehen = new Darlehen(LOGGER_REFI_REGISTER, new String()); // Umsatz19-Datei wird nicht benoetigt -> new String()
       ivZielDarlehen.extractDarlehen(ivOriginalDarlehen);
       
       //System.out.println("Kontotyp: " + ivZielDarlehen.getKontotyp());
       //System.out.println("Kontozustand: " + ivZielDarlehen.getKontozustand());
       /* if (ivVerarbeitungsmodus == DEEP_SEA)
       {
           int lvHelpInt = StringKonverter.convertString2Int(ivZielDarlehen.getKontozustand());

    	   if (lvHelpInt < 8)
           { 
    		   ivDarlehenXML.printDarlehen(ivZielDarlehen);
    		   try
    		   {
    			   ivRefiRegisterAktiv.importDarlehen2TransaktionDeepSea(ivZielDarlehen, ivOutputDarlehenXML, ivSicherheitenDaten, ivListeRefiDeepSeaZusatz, ivVerarbeitungsmodus);
    		   }
    		   catch (Exception exp)
    		   {
    			   LOGGER_REFI_REGISTER.error("Verarbeitungsfehler - Konto " + ivZielDarlehen.getKontonummer() + StringKonverter.lineSeparator);
    			   LOGGER_REFI_REGISTER.error(exp.getMessage());
    			   exp.printStackTrace();
    		   }
           }
           else
           {
               LOGGER_REFI_REGISTER.info("-->Konto " + ivZielDarlehen.getKontonummer() + "("
                                         + ivZielDarlehen.getHerkunftDarlehen() + ";" + ivZielDarlehen.getHerkunftDaten()
                                         + ";" + ivZielDarlehen.getKundennummer() + ";" + ivZielDarlehen.getObjektnummer()
                                         + ")KtoZustand-" + ivZielDarlehen.getKontozustand() + "-darf nicht");
           }
       }
       if (ivVerarbeitungsmodus == LETTRE_DE_GAGE)
       {
           //if (StringKonverter.convertString2Int(ivZielDarlehen.getKontotyp()) == 4)
           //{  
           //    int lvHelpInt = StringKonverter.convertString2Int(ivZielDarlehen.getKontozustand());
               
           //    if (lvHelpInt < 8)
           //    { 
                  ivDarlehenXML.printDarlehen(ivZielDarlehen);
                  try
                  {
                      ivRefiRegisterAktiv.importDarlehen2TransaktionLettreDeGage(ivZielDarlehen, ivOutputDarlehenXML, ivSicherheitenDaten, ivVerarbeitungsmodus);
                  }
                  catch (Exception exp)
                  {
                      LOGGER_REFI_REGISTER.error("Verarbeitungsfehler - Konto " + ivZielDarlehen.getKontonummer() + StringKonverter.lineSeparator);
                      LOGGER_REFI_REGISTER.error(exp.getMessage());
                      exp.printStackTrace();
                  }
               //}
               //else
               //{
               //    LOGGER_REFI_REGISTER.info("-->Konto " + ivZielDarlehen.getKontonummer() + "("
               //            + ivZielDarlehen.getHerkunftDarlehen() + ";" + ivZielDarlehen.getHerkunftDaten()
               //            + ";" + ivZielDarlehen.getKundennummer() + ";" + ivZielDarlehen.getObjektnummer()
               //            + ")KtoZustand-" + ivZielDarlehen.getKontozustand() + "-darf nicht");
               //}
            //}    	   
       } */
    }

    /**
     * Liefert die Statistik als Zeichenkette
     * @return 
     */
    private String getStatistik()
    {
      StringBuilder lvStringBuilder = new StringBuilder();
      lvStringBuilder.append(ivVorlaufsatz.toString());
      
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerOBJ + " OBJ-Segmente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerBAUFI + " BAUFI-Segmente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerKTS + " KTS-Segmente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerKTR + " KTR-Segmente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerINF + " INF-Segmente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerKON + " KON-Segmente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerKONTS + " KONTS-Segmente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerKTOZB + " KTOZB-Segmente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerREC + " REC-Segmente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerUMS + " UMS-Segmente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(StringKonverter.lineSeparator);
      lvStringBuilder.append(ivZaehlerRefiRegisterFilterElemente + " RefiRegisterFilterElemente gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);     
      lvStringBuilder.append(ivListeRefiZusatz.size() + " RefiZusatz gelesen...");
      lvStringBuilder.append(StringKonverter.lineSeparator);
      //if (ivVerarbeitungsmodus == DEEP_SEA)
      //{
      //  lvStringBuilder.append(ivZaehlerRefiDeepSeaZusatz + " RefiDeepSeaZusatz gelesen...");
      //}
      lvStringBuilder.append(StringKonverter.lineSeparator);     
      
      return lvStringBuilder.toString();
    }

  /**
   * Zerlegt eine Zeile in die unterschiedlichen Daten/Felder
   * @param pvZeile
   * @return
   */
   private boolean parseDarlehen(String pvZeile)
   {             
      String stemp = new String(); // arbeitsbereich/zwischenspeicher feld
      int lvLfd = 0; // lfd feldnr, pruefsumme je satzart

      ivStatus = UNDEFINIERT;
      
      if (pvZeile.contains(";KTR     ;"))
      {
          pvZeile = removeSemikolons(pvZeile);
      }
      
      // steuerung/iteration eingabesatz
      for (int lvZzStr = 0; lvZzStr < pvZeile.length(); lvZzStr++)
      {

        // wenn semikolon erkannt
          if (pvZeile.charAt(lvZzStr) == ';')
        {
              if (lvLfd < 4)
            {
                  if (lvLfd == 0)
                {
                    ivKundennummer = stemp;
                }
                  if (lvLfd == 1)
                {
                    ivObjektnummer = stemp;
                }
                  if (lvLfd == 2)
                {
                    ivKontonummer = stemp;
                }
                
                  if (lvLfd == 3)
                {                	  
                    if (ivAlteKontonummer != null)
                    {
                      if (!ivKontonummer.equals(ivAlteKontonummer))
                      {
                        if (!ivKontonummer.equals("0000000000"))
                        {
                            //LOGGER_REFI_REGISTER.info("Konto: " + ivAlteKontonummer);

                          if (ivKtr != null)
                          {
                              // Pruefe, ob das Datum in den KON, KONTS und KTOZB korrekt ist
                              ivOriginalDarlehen.pruefeDatum();

                              ////if (ivKts.getsDkom().equals("21") || ivKts.getsDkom().equals("22") || ivKts.getsDkom().equals("23"))
                              ////{
                                  if (ivListeKontonummern.isEmpty() || ivListeKontonummern.contains(ivAlteKontonummer) ||
                                      ivKtr.getAusplatzierungsmerkmal().equals("B1") ||  // DeepSea
                                      ivKtr.getAusplatzierungsmerkmal().startsWith("G")) // LettreDeGage
                                  {
                                	 LOGGER_REFI_REGISTER.info("Kontonummer " + ivAlteKontonummer + " in der Liste enthalten...");
                                     ivListeKontonummern.remove(ivAlteKontonummer);
                                     //if (ivVerarbeitungsmodus == DEEP_SEA || ivVerarbeitungsmodus == LETTRE_DE_GAGE)
                                     //{
                                     //	 verarbeiteAktiv();
                                     //}
                                     if (ivVerarbeitungsmodus == REFI_REG)
                                     {
                                    	 verarbeitePassiv();
                                     }
                                     LOGGER_REFI_REGISTER.info("Refi;Refi;" + ivAlteKontonummer + ";" + ivKtr.getsDbue());
                                  }
                              ////}                           
                          }
                          else
                          {
                        	  LOGGER_REFI_REGISTER.info("KTR == null");
                          }
                          ivOriginalDarlehen.clearLists();
                          ivInf = null;
                          ivKon = null;
                          ivKonts = null;
                          ivKtozb = null;
                          ivKtr = null;
                          ivKts = null;
                          ivRec = null;
                          ivUms = null;
                        }
                      }
                    }
                  erzeugeSegment(stemp.trim());
                }
            }
            else
            {
                  setzeWert(ivStatus, lvLfd, stemp);
            }

              lvLfd++; // naechste feldnummer
            // loeschen zwischenbuffer
            stemp = new String();
        }
        else
        {
            // uebernehmen byte aus eingabesatzposition
              stemp = stemp + pvZeile.charAt(lvZzStr);
        }
    } // ende for  
    
    // Letzten Wert der Zeile/Segment auch noch setzen
    if (!stemp.isEmpty())
    {
          setzeWert(ivStatus, lvLfd, stemp);
    }

    return true;
   }


  /**
    * Erzeugen eines Segments
    * @param pvStemp
    */
   private void erzeugeSegment(String pvStemp)
   {
      if (pvStemp.equals("BAUFI"))
       {
           ivStatus = BAUFI;
          ivBaufi = new BAUFI();
          ivBaufi.getKopf().setsDwhkdnr(ivKundennummer);
          ivBaufi.getKopf().setsDwhonr(ivObjektnummer);
          ivBaufi.getKopf().setsDwhknr(ivKontonummer);
          ivZaehlerBAUFI++;
       }
      if (pvStemp.equals("INF"))
       {
           ivStatus = INF;
          ivInf = new INF();
          ivInf.getKopf().setsDwhkdnr(ivKundennummer);
          ivInf.getKopf().setsDwhonr(ivObjektnummer);
          ivInf.getKopf().setsDwhknr(ivKontonummer);
          ivZaehlerINF++;
       }
      if (pvStemp.equals("KON"))
       {
           ivStatus = KON;
          ivKon = new KON();
          ivKon.getKopf().setsDwhkdnr(ivKundennummer);
          ivKon.getKopf().setsDwhonr(ivObjektnummer);
          ivKon.getKopf().setsDwhknr(ivKontonummer);
          ivZaehlerKON++;
       }
      if (pvStemp.equals("KONTS"))
       {
           ivStatus = KONTS;
          ivKonts = new KONTS();
          ivKonts.getKopf().setsDwhkdnr(ivKundennummer);
          ivKonts.getKopf().setsDwhonr(ivObjektnummer);
          ivKonts.getKopf().setsDwhknr(ivKontonummer);
          ivZaehlerKONTS++;
       }
      if (pvStemp.equals("KTOZB"))
       {
           ivStatus = KTOZB;
          ivKtozb = new KTOZB();
          ivKtozb.getKopf().setsDwhkdnr(ivKundennummer);
          ivKtozb.getKopf().setsDwhonr(ivObjektnummer);
          ivKtozb.getKopf().setsDwhknr(ivKontonummer);
          ivZaehlerKTOZB++;
       }
      if (pvStemp.equals("KTR"))
       {
           ivStatus = KTR;
          ivKtr = new KTR();
          ivKtr.getKopf().setsDwhkdnr(ivKundennummer);
          ivKtr.getKopf().setsDwhonr(ivObjektnummer);
          ivKtr.getKopf().setsDwhknr(ivKontonummer);
          ivZaehlerKTR++;
       }
      if (pvStemp.equals("KTS"))
       {
           ivStatus = KTS;
          ivKts = new KTS();
          ivKts.getKopf().setsDwhkdnr(ivKundennummer);
          ivKts.getKopf().setsDwhonr(ivObjektnummer);
          ivKts.getKopf().setsDwhknr(ivKontonummer);
          ivZaehlerKTS++;
       }
      if (pvStemp.equals("OBJ"))
       {
           ivStatus = OBJ;
          ivObj = new OBJ();
          ivObj.getKopf().setsDwhkdnr(ivKundennummer);
          ivObj.getKopf().setsDwhonr(ivObjektnummer);
          ivObj.getKopf().setsDwhknr(ivKontonummer);
          ivZaehlerOBJ++;
       }
      if (pvStemp.equals("DWHNREC"))
       {
           ivStatus = REC;
          ivRec = new REC();
          ivRec.getKopf().setsDwhkdnr(ivKundennummer);
          ivRec.getKopf().setsDwhonr(ivObjektnummer);
          ivRec.getKopf().setsDwhknr(ivKontonummer);
          ivZaehlerREC++;
       }
      if (pvStemp.equals("UMS"))
       {
           ivStatus = UMS;
          ivUms = new UMS();
          ivUms.getKopf().setsDwhkdnr(ivKundennummer);
          ivUms.getKopf().setsDwhonr(ivObjektnummer);
          ivUms.getKopf().setsDwhknr(ivKontonummer);
          ivZaehlerUMS++;
       }
   }
   
   /**
    * Fuegt ein Segment dem Darlehen hinzu
    */
   private void einfuegenSegment()
   {
       switch (ivStatus)
       {
           case BAUFI:
              ivOriginalDarlehen.setBAUFI(ivBaufi);
               break;
           case INF:
              ivOriginalDarlehen.addINF(ivInf);
               break;
           case KON:
              ivOriginalDarlehen.addKON(ivKon);
               break;
           case KONTS:
              ivOriginalDarlehen.addKONTS(ivKonts);
               break;
           case KTOZB:
              ivOriginalDarlehen.addKTOZB(ivKtozb);
               break;
           case KTR:
              ivOriginalDarlehen.setKTR(ivKtr);
               break;
           case KTS:
              ivOriginalDarlehen.setKTS(ivKts);
              if (ivBaufi != null)
               {
                  if (ivOriginalDarlehen.getKTS().getKopf().getsDwhkdnr().equals(ivBaufi.getKopf().getsDwhkdnr())
                          && ivOriginalDarlehen.getKTS().getKopf().getsDwhonr().equals(ivBaufi.getKopf().getsDwhonr()))
                       {
                      ivOriginalDarlehen.setBAUFI(ivBaufi);
                       }
               }
              if (ivObj != null)
               {
                  if (ivOriginalDarlehen.getKTS().getKopf().getsDwhkdnr().equals(ivObj.getKopf().getsDwhkdnr())
                          && ivOriginalDarlehen.getKTS().getKopf().getsDwhonr().equals(ivObj.getKopf().getsDwhonr()))
                   {
                      ivOriginalDarlehen.setOBJ(ivObj);
                   }
               }
               break;
           case OBJ:
              ivOriginalDarlehen.setOBJ(ivObj);
               break;
           case REC:
              ivOriginalDarlehen.setREC(ivRec);
               break;
           case UMS:
              ivOriginalDarlehen.setUMS(ivUms);
               break;                         
            default:
               LOGGER_REFI_REGISTER.error("einfuegenSegment - Unbekannte Satzart: " + ivStatus);
       }  
   }
   
   /**
    * Setzen eines Wertes in ein Segment
    * @param pvStatus
    * @param pvLfd
    * @param pvStemp
    */
  private void setzeWert(int pvStatus, int pvLfd, String pvStemp)
   {
      switch (pvStatus)
       {
           case BAUFI:
              ivBaufi.setBAUFI(pvLfd, pvStemp);
               break;
           case INF:
              ivInf.setINF(pvLfd, pvStemp);
               // CT 08.06.2012 - Wenn OFLEI, dann Kontonummer ins Logfile schreiben
              if (pvLfd == 4)
               {
                  //if (ivInf.getKopf().getsDwhtdb().equals("OFLEI"))
                 //{
                      //if (ivLog != null)
                   //{
                          //LOGGER_DARKA.info("OFLEI - Kontonummer: " + ivInf.getKopf().getsDwhknr());
                   //}
                 //}
               }
               break;
           case KON:
              ivKon.setKON(pvLfd, pvStemp);
               break;
           case KONTS:
              ivKonts.setKONTS(pvLfd, pvStemp);
               break;
           case KTOZB:
              ivKtozb.setKTOZB(pvLfd, pvStemp);
               break;
           case KTR:
              ivKtr.setKTR(pvLfd, pvStemp);
               break;
           case KTS:
              ivKts.setKTS(pvLfd, pvStemp);
               break;
           case OBJ:
              ivObj.setOBJ(pvLfd, pvStemp);
               break;
           case REC:
              ivRec.setREC(pvLfd, pvStemp);
               break;
           case UMS:
              ivUms.setUMS(pvLfd, pvStemp);
               break;                         
            default:
              LOGGER_REFI_REGISTER.error("setzeWert - Unbekannte Satzart: " + pvStatus);
       }   
   }
   
   
   /**
    * Entfernt die Semikolons in der Zeichenkette
    * @param pvText
    * @return
    */
   private String removeSemikolons(String pvText)
   {
       String lvHelpString = pvText.substring(0,73);
       lvHelpString = lvHelpString + pvText.substring(73,97).replace(";", ",") + ";";
       
       lvHelpString = lvHelpString + pvText.substring(98,122).replace(";", ",") + ";";
       
       lvHelpString = lvHelpString + pvText.substring(123);
              
       return lvHelpString;
   }

}
