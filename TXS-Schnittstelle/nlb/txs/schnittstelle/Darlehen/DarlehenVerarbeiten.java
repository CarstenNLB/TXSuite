/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Calendar;

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
import nlb.txs.schnittstelle.Darlehen.Finanzierung.ListeObjekte;
import nlb.txs.schnittstelle.Darlehen.Finanzierung.ObjektElement;
import nlb.txs.schnittstelle.Deckungspooling.DeckungspoolingVerarbeitung;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPSicherheitenVerarbeitung;
import nlb.txs.schnittstelle.LoanIQ.ANNADatei.ANNADatei;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.InputObjektZuweisungsbetrag;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetrag;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetragListe;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Sicherheiten.SAPCMS_Neu;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzierungKredit;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSPfandobjektDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitPerson;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitVerzeichnis;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisPfandobjekt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisVBlatt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisblattDaten;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class DarlehenVerarbeiten 
{   
    // Logger fuer DarKa
    private static Logger LOGGER_DARKA = Logger.getLogger("TXSDarKaLogger"); 
    
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
     * 
     */
    private int ivStatus;
    
    // Verarbeitungsmodus TXX-Schnittstelle
    public static final int ALT = 0;
    
    // Verarbeitungsmodus DarKa
    public static final int DARKA = 1;
    
    // Verarbeitungsmodus Deckungspooling (DPP)
    public static final int DPP = 2;
    
    // Verarbeitungsmodus Schiffe
    public static final int SCHIFFE = 3;
    
    // Verarbeitungsmodus Flugzeuge
    public static final int FLUGZEUGE = 4;
    
    // Verarbeitungsmodus OEPG
    public static final int OEPG = 5;
    
    // Verarbeitungsmodus KEV-Extractor
    public static final int KEV_EXTRACTOR = 6;

    // Verarbeitungsmodus KEV
    public static final int KEV = 7;

    /**
     * Verarbeitungsmodus
     */
    private int ivModus;
   
    // Ini-Parameter
    private String ivInstitutsnummer;
    private String ivImportVerzeichnis;
    private String ivExportVerzeichnis;
    private String ivDarlehenImportDatei;
    private String ivDarlehenTXSDatei;
    private String ivDarlehenDatei;
    private String ivDarlehenWPDatei;
    private String ivSicherheitenDatei;
    private String ivSicherungsobjekteDatei;
    private String ivObjektZWDatei;
    private String ivImportVerzeichnisSAPCMS;
    private String ivSapcmsDatei;
    private String ivANNADatei;
    private String ivCashflowQuellsystemDatei;
    
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

    private String ivFilenameUmsatz19;
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
   
    //private OutputKontonummern outputKto;
    // CT - fuer Debugzwecke
    //private KontoListe ktoListe;
    
    private DarlehenXML ivDarlehenXML;
    private OutputDarlehenXML ivOutputDarlehenXML;
    private DarlehenWertpapierXML ivDarlehenWertpapierXML;
    
    // Sicherheiten aus Darlehen
    private SicherheitenXML ivSicherheitenXML;
    private SicherheitenListe ivListeSicherheiten;
    
    // Sicherungsobjekte aus Darlehen
    private SicherungsobjekteXML ivSicherungsObjXML;
    private SicherungsobjekteListe ivListeSicherungsobjekte;
    
    // Sicherheiten aus SAP CMS
    private SAPCMS_Neu ivSapcms;
        
    // ANNA-Datei aus LoanIQ
    private ANNADatei ivANNA;
    
    // Zuweisungsbetrag der Objekte aus Darlehen --> aufsummiert
    private InputObjektZuweisungsbetrag ivObjektZuweisungsbetrag;
    
    // ObjektZuweisungsbetragListe
    private ObjektZuweisungsbetragListe ivObjektZuweisungsListe;
    
    // Deckungspooling Sicherheiten und Kunden
    private DeckungspoolingVerarbeitung ivDpp;
    
    // FileOutputStream fuer CashflowQuellsystem-Datei
    private FileOutputStream ivFosCashflowQuellsystem;
    
    // FileOutputStream fuer KEV-Extractor
    private FileOutputStream ivFosKEVExtractor;
    
    // Logging
    //private Logging ivLog;
    //private boolean ivLogging;
   
    // Transaktionen
    private TXSFinanzgeschaeft ivFinanzgeschaeft;
    private TXSSliceInDaten ivSliceInDaten;
    private TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten;
    private TXSKonditionenDaten ivKondDaten;
    private TXSKreditKunde ivKredkunde;

    /**
     * Liste Objektnummern bei den Finanzierungen
     */
    private ListeObjekte ivListeObjekte;
    
    /**
     * Konstruktor fuer Verarbeitung DarKa
     * @param pvModus 
     * @param pvReader 
     */
    public DarlehenVerarbeiten(int pvModus, IniReader pvReader)
    {
        this.ivModus = pvModus;
        
        // Entsprechenden Logger auswaehlen
        if (pvModus == DarlehenVerarbeiten.DARKA || pvModus == DarlehenVerarbeiten.KEV_EXTRACTOR)
        {
            LOGGER_DARKA = Logger.getLogger("TXSDarKaLogger"); 
        }
        if (pvModus == DarlehenVerarbeiten.FLUGZEUGE)
        {
            LOGGER_DARKA = Logger.getLogger("TXSDarKaFlugzeugeLogger");
        }
        if (pvModus == DarlehenVerarbeiten.SCHIFFE)
        {
            LOGGER_DARKA = Logger.getLogger("TXSDarKaSchiffeLogger");
        }
        if (pvModus == DarlehenVerarbeiten.OEPG)
        {
            LOGGER_DARKA = Logger.getLogger("TXSDarKaOEPGLogger");
        }
        if (pvModus == DarlehenVerarbeiten.KEV)
        {
            LOGGER_DARKA = Logger.getLogger("DarKaKEVLogger");
        }
        
        this.ivFinanzgeschaeft = new TXSFinanzgeschaeft();
        this.ivSliceInDaten = new TXSSliceInDaten();
        this.ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
        this.ivKondDaten = new TXSKonditionenDaten();
        this.ivKredkunde = new TXSKreditKunde();
        
        if (pvReader != null)
        {
            //String lvLoggingXML = pvReader.getPropertyString("log4j", "log4jconfig", "Fehler");
            //if (lvLoggingXML.equals("Fehler"))
            //{
            //  System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            //}
            //else
            //{
            //    DOMConfigurator.configure(lvLoggingXML); 
            //}            

            ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (ivInstitutsnummer.equals("Fehler"))
            {
                LOGGER_DARKA.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }

            if (pvModus != DarlehenVerarbeiten.KEV)
            {
              ivFilenameUmsatz19 = pvReader.getPropertyString("DARLWP", "DARLWPUMSATZ19", "Fehler");
              if (ivFilenameUmsatz19.equals("Fehler"))
              {
                LOGGER_DARKA.error("Kein [DARLWP][DARLWPUMSATZ19] in der ini-Datei...");
                System.exit(1);
              }
              else
              {
                LOGGER_DARKA.info("DARLWPUMSATZ19=" + ivFilenameUmsatz19);
              }
            }

            // Defaultmaessig 'Darlehen'
            String bereich = "Darlehen";
            switch (pvModus)
            {
                case DarlehenVerarbeiten.DARKA:
                    bereich = "Darlehen";
                    break;
                case DarlehenVerarbeiten.FLUGZEUGE:
                    bereich = "Flugzeuge";
                    break;
                case DarlehenVerarbeiten.SCHIFFE:
                    bereich = "Schiffe";
                    break;
                case DarlehenVerarbeiten.OEPG:
                    bereich = "OEPG";
                    break;
                case DarlehenVerarbeiten.DPP:
                    bereich = "Deckungspooling";
                    break;
                case DarlehenVerarbeiten.KEV:
                    bereich = "DarKaKEV";
                    break;
                default:
                    System.out.println("DarlehenVerarbeiten - Unbekannter Modus");
                    System.out.println("Defaultmodus: " + bereich);
            }   
            
            ivImportVerzeichnis = pvReader.getPropertyString(bereich, "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_DARKA.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivExportVerzeichnis = pvReader.getPropertyString(bereich, "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                LOGGER_DARKA.error("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            ivDarlehenImportDatei =  pvReader.getPropertyString(bereich, "DarlehenImport-Datei", "Fehler");
            if (ivDarlehenImportDatei.equals("Fehler"))
            {
                LOGGER_DARKA.error("Kein DarlehenImport-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            ivDarlehenTXSDatei =  pvReader.getPropertyString(bereich, "DarlehenTXS-Datei", "Fehler");
            if (ivDarlehenTXSDatei.equals("Fehler"))
            {
                LOGGER_DARKA.error("Kein DarlehenTXS-Dateiname in der ini-Datei...");
                System.exit(1);
            }
           
            ivDarlehenDatei = pvReader.getPropertyString(bereich, "Darlehen-Datei", "Fehler");
            if (ivDarlehenDatei.equals("Fehler"))
            {
                LOGGER_DARKA.error("Kein Darlehen-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            if (pvModus != DarlehenVerarbeiten.DPP)
            {
              ivDarlehenWPDatei = pvReader.getPropertyString(bereich, "DarlehenWP-Datei", "Fehler");
              if (ivDarlehenWPDatei.equals("Fehler"))
              {
                LOGGER_DARKA.error("Kein DarlehenWP-Dateiname in der ini-Datei...");
                System.exit(1);
              }
            }

            if (pvModus == DarlehenVerarbeiten.DARKA)
            {
              ivSicherheitenDatei = pvReader.getPropertyString(bereich, "Sicherheiten-Datei", "Fehler");
              if (ivSicherheitenDatei.equals("Fehler"))
              {
                  LOGGER_DARKA.error("Kein Sicherheiten-Dateiname in der ini-Datei...");
                  System.exit(1);
              }

              ivSicherungsobjekteDatei = pvReader.getPropertyString(bereich, "Sicherungsobjekte-Datei", "Fehler");
              if (ivSicherungsobjekteDatei.equals("Fehler"))
              {
                  LOGGER_DARKA.error("Kein Sicherungsobjekte-Dateiname in der ini-Datei...");
                  System.exit(1);
              }
            }

            if (pvModus != DarlehenVerarbeiten.KEV)
            {
              ivObjektZWDatei = pvReader.getPropertyString(bereich, "ObjektZW-Datei", "Fehler");
              if (ivObjektZWDatei.equals("Fehler"))
              {
                LOGGER_DARKA.error("Kein ObjektZW-Dateiname in der ini-Datei...");
                System.exit(1);
              }
            }
            
            if (pvModus == DarlehenVerarbeiten.DARKA || pvModus == DarlehenVerarbeiten.SCHIFFE || pvModus == DarlehenVerarbeiten.FLUGZEUGE)
            {
              ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("SAPCMS", "ImportVerzeichnis", "Fehler");
              if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
              {
                  LOGGER_DARKA.error("Kein Sicherheiten Import-Verzeichnis in der ini-Datei...");
                  System.exit(1);
              }

              ivSapcmsDatei = pvReader.getPropertyString("SAPCMS", "SAPCMS-Datei", "Fehler");
              if (ivSapcmsDatei.equals("Fehler"))
              {
                  LOGGER_DARKA.error("Kein Sicherheiten-Dateiname in der ini-Datei...");
                  System.exit(1);
              }
              
              ivANNADatei = pvReader.getPropertyString(bereich, "ANNA-Datei", "Fehler");
              if (ivANNADatei.equals("Fehler"))
              {
                  LOGGER_DARKA.error("Kein ANNA-Dateiname in der ini-Datei...");
                  System.exit(1);
              }              
            }
            
            if (pvModus == DarlehenVerarbeiten.OEPG)
            {
                ivANNADatei = pvReader.getPropertyString(bereich, "ANNA-Datei", "Fehler");
                if (ivANNADatei.equals("Fehler"))
                {
                    LOGGER_DARKA.error("Kein ANNA-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
            }
            
            ivCashflowQuellsystemDatei = pvReader.getPropertyString(bereich, "Quellsystem-Datei", "Fehler");
            if (ivCashflowQuellsystemDatei.equals("Fehler"))
            {
                LOGGER_DARKA.error("Kein Cashflow-Quellsystem-Dateiname in der ini-Datei...");
                System.exit(1);
            }
            
            if (pvModus == DarlehenVerarbeiten.DPP)
            {
                String lvOSPKundenDatei =  pvReader.getPropertyString("Deckungspooling", "OSPKunden-Datei", "Fehler");
                if (lvOSPKundenDatei.equals("Fehler"))
                {
                    LOGGER_DARKA.error("Kein OSPKunden-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
                
                String lvTXSKundenDatei =  pvReader.getPropertyString("Deckungspooling", "TXSDPPKunden-Datei", "Fehler");
                if (lvTXSKundenDatei.equals("Fehler"))
                {
                    LOGGER_DARKA.error("Kein TXSKunden-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
                     
                String lvOSPSicherheitenDatei =  pvReader.getPropertyString("Deckungspooling", "OSPSicherheiten-Datei", "Fehler");
                if (lvOSPSicherheitenDatei.equals("Fehler"))
                {
                    LOGGER_DARKA.error("Kein OSPSicherheiten-Dateiname in der ini-Datei...");
                    System.exit(1);
                } 
                
                String lvQuellsystemeDatei =  pvReader.getPropertyString("Deckungspooling", "Quellsysteme-Datei", "Fehler");
                if (lvOSPSicherheitenDatei.equals("Fehler"))
                {
                    LOGGER_DARKA.error("Kein Quellsysteme-Dateiname in der ini-Datei...");
                    System.exit(1);
                }        
                
              // Deckungspoolingdaten verarbeiten
              ivDpp = new DeckungspoolingVerarbeitung(ivInstitutsnummer, ivImportVerzeichnis, ivExportVerzeichnis, lvOSPKundenDatei, lvTXSKundenDatei, lvOSPSicherheitenDatei, lvQuellsystemeDatei, LOGGER_DARKA);
            }
            
            // Verarbeitung starten
            startVerarbeitung();
        }

    }
    
    /**
     * Konstruktor alte Schnittstelle
     * @param logging 
     * @param log 
     * @param reader 
     */
    /* public DarlehenVerarbeiten(boolean logging, Logging log, IniReader reader)
    {     
        // Logging Datei setzen
        this.ivLog = log;
        this.ivLogging = logging;

        this.ivModus = DARKA;
        
        this.ivFinanzgeschaeft = new TXSFinanzgeschaeft();
        this.ivSliceInDaten = new TXSSliceInDaten();
        this.ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
        this.ivKondDaten = new TXSKonditionenDaten();
        this.ivKredkunde = new TXSKreditKunde();
                
        // Einlesen der ini-Datei
        if (reader != null)
        {
            //String institut = reader.getPropertyString("Konfiguration", "Institut", "Fehler");
            //if (institut.equals("Fehler"))
            //{
            //    System.out.println("Keine Institutsnummer in der ini-Datei...");
            //    System.exit(0);
            //}
                                    
            ivImportVerzeichnis = reader.getPropertyString("Darlehen", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnis.equals("Fehler"))
            {
                System.out.println("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(0);
            }

            ivExportVerzeichnis = reader.getPropertyString("Darlehen", "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                System.out.println("Kein Export-Verzeichnis in der ini-Datei...");
                System.exit(0);
            }

            ivDarlehenImportDatei =  reader.getPropertyString("Darlehen", "DarlehenImport-Datei", "Fehler");
            if (ivDarlehenImportDatei.equals("Fehler"))
            {
                System.out.println("Kein DarlehenImport-Dateiname in der ini-Datei...");
                System.exit(0);
            }
            
            ivDarlehenTXSDatei =  reader.getPropertyString("Darlehen", "DarlehenTXS-Datei", "Fehler");
            if (ivDarlehenTXSDatei.equals("Fehler"))
            {
                System.out.println("Kein DarlehenTXS-Dateiname in der ini-Datei...");
                System.exit(0);
            }
           
            ivDarlehenDatei = reader.getPropertyString("Darlehen", "Darlehen-Datei", "Fehler");
            if (ivDarlehenDatei.equals("Fehler"))
            {
                System.out.println("Kein Darlehen-Dateiname in der ini-Datei...");
                System.exit(0);
            }
            
            ivDarlehenWPDatei = reader.getPropertyString("Darlehen", "DarlehenWP-Datei", "Fehler");
            if (ivDarlehenWPDatei.equals("Fehler"))
            {
                System.out.println("Kein DarlehenWP-Dateiname in der ini-Datei...");
                System.exit(0);
            }

            ivSicherheitenDatei = reader.getPropertyString("Darlehen", "Sicherheiten-Datei", "Fehler");
            if (ivSicherheitenDatei.equals("Fehler"))
            {
                System.out.println("Kein Sicherheiten-Dateiname in der ini-Datei...");
                System.exit(0);
            }

            ivSicherungsobjekteDatei = reader.getPropertyString("Darlehen", "Sicherungsobjekte-Datei", "Fehler");
            if (ivSicherungsobjekteDatei.equals("Fehler"))
            {
                System.out.println("Kein Sicherungsobjekte-Dateiname in der ini-Datei...");
                System.exit(0);
            }

            ivObjektZWDatei = reader.getPropertyString("Darlehen", "ObjektZW-Datei", "Fehler");
            if (ivObjektZWDatei.equals("Fehler"))
            {
                System.out.println("Kein ObjektZW-Dateiname in der ini-Datei...");
                System.exit(0);
            }
            
            ivImportVerzeichnisSAPCMS = reader.getPropertyString("Sicherheiten", "ImportVerzeichnis", "Fehler");
            if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
            {
                System.out.println("Kein Sicherheiten Import-Verzeichnis in der ini-Datei...");
                System.exit(0);
            }

            ivSapcmsDatei = reader.getPropertyString("Sicherheiten", "Sicherheiten-Datei", "Fehler");
            if (ivSapcmsDatei.equals("Fehler"))
            {
                System.out.println("Kein Sicherheiten-Dateiname in der ini-Datei...");
                System.exit(0);
            }

            startVerarbeitung();
        }
    } */
    
    /**
     * 
     */
    private void startVerarbeitung()
    {        
        Calendar lvCal = Calendar.getInstance();
        CalendarHelper lvCh = new CalendarHelper();

        LOGGER_DARKA.info("Start: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
                
        // Eine eventuell vorhandene Darlehen Filterdatei einlesen
        /* int zaehlerKontonummern = 0;
        File filter = new File(ivImportVerzeichnis + "\\" + ivFilterDatei);
        if (filter.exists())
        {
          ivKtoListe = new KontoListe();
          FileInputStream filterIS = null;
          try
          {
            filterIS = new FileInputStream(filter);
          }
          catch (Exception e)
          {
            LOGGER_DARKA.error("Konnte Filterdatei nicht oeffnen!");
          }
          BufferedReader filterIn = new BufferedReader(new InputStreamReader(filterIS));
          String ivKto = new String();
          try
          {
            while ((ivKto = filterIn.readLine()) != null)  // Lesen der Filterdatei
            {
              if (ivKto != null)
              {
                ivKtoListe.add(ivKto);
                LOGGER_DARKA.info("Kontonummer: " + ivKto);
                zaehlerKontonummern++;
              }
            }
          }
          catch (Exception e)
          {
              LOGGER_DARKA.error("Fehler beim Verarbeiten der Kontonummer:" + ivKto);
              e.printStackTrace();
          }
        }
        LOGGER_DARKA.info("Anzahl Kontonummern: " + zaehlerKontonummern); */
        
        // CT 14.02.2012 - Id Ausgabe
        // Auskommentieren
        //FileOutputStream lvFosIDs = null;
        //File lvFileIDs = new File(ivExportVerzeichnis + "\\IDs_Change.txt");
        //try
        //{
        //    lvFosIDs = new FileOutputStream(lvFileIDs);
        //}
        //catch (Exception e)
        //{
        //  System.out.println("Konnte IDs-Datei nicht oeffnen!");
        //}
        // CT 14.02.2012

        // CT 17.07.2018 - KEV-Extractor
        File lvFileKEV = new File(ivExportVerzeichnis + "\\KEV-Extractor.csv");
        try
        {
            ivFosKEVExtractor = new FileOutputStream(lvFileKEV);
            ivFosKEVExtractor.write(("InstNr;KtoNr;DZR;ZBBG;DLZ;DHZI;DNZI;" + StringKonverter.lineSeparator).getBytes());
        }
        catch (Exception e)
        {
        	LOGGER_DARKA.info("Konnte KEVExtractor-Datei nicht oeffnen!");
        }
        // CT 17.07.2018
        
        // CT 06.05.2013 - Cashflow-Quellsystem
        File lvFileCashflowQuellsystem = new File(ivExportVerzeichnis + "\\" + ivCashflowQuellsystemDatei);
        try
        {
            ivFosCashflowQuellsystem = new FileOutputStream(lvFileCashflowQuellsystem);
        }
        catch (Exception e)
        {
            System.out.println("Konnte CashflowQuellsystem-Datei nicht oeffnen!");
        }
        // CT 06.05.2013
        
        // Sicherheiten-Datei einlesen, wenn Verarbeitung DarKa
        ////if (ivModus == DarlehenVerarbeiten.DARKA)
        ////{
        ////  ivSapcms = new Sicherheiten(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, LOGGER_DARKA);
        ////}

        // Sicherheiten-Datei einlesen, wenn Verarbeitung DarKa, Schiffe, Flugzeuge oder KEV
        if (ivModus == DarlehenVerarbeiten.DARKA || ivModus == DarlehenVerarbeiten.SCHIFFE || ivModus == DarlehenVerarbeiten.FLUGZEUGE)
        {
          ivSapcms = new SAPCMS_Neu(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, LOGGER_DARKA);
        }

        // ANNA-Datei einlesen, wenn Verarbeitung DarKa, Schiffe, Flugzeuge oder KEV
        if (ivModus == DarlehenVerarbeiten.DARKA || ivModus == DarlehenVerarbeiten.SCHIFFE || ivModus == DarlehenVerarbeiten.FLUGZEUGE || ivModus == DarlehenVerarbeiten.OEPG)
        {
          ivANNA = new ANNADatei(ivImportVerzeichnis + "\\" + ivANNADatei, LOGGER_DARKA);
        }

        // ObjektZuweisungsbetrag-Datei einlesen
        if (ivModus == DarlehenVerarbeiten.DARKA || ivModus == DarlehenVerarbeiten.SCHIFFE || ivModus == DarlehenVerarbeiten.FLUGZEUGE)
        {
          ivObjektZuweisungsbetrag = new InputObjektZuweisungsbetrag(ivImportVerzeichnis + "\\" + ivObjektZWDatei);
          ivObjektZuweisungsbetrag.open();
          ivObjektZuweisungsListe = ivObjektZuweisungsbetrag.readObjektZuweisungsbetrag();
          ivObjektZuweisungsbetrag.close();
        }
        
        // Darlehen XML-Datei
        ivDarlehenXML = new DarlehenXML(ivExportVerzeichnis + "\\" + ivDarlehenDatei, LOGGER_DARKA);
        ivDarlehenXML.openXML();
        
        // Darlehen XML-Datei im TXS-Format
        ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivDarlehenTXSDatei, LOGGER_DARKA);
        ivOutputDarlehenXML.openXML();
        ivOutputDarlehenXML.printXMLStart();
        
        // DarlehenWP XML-Datei
        if (ivModus != DarlehenVerarbeiten.DPP)
        {
            ivDarlehenWertpapierXML = new DarlehenWertpapierXML(ivExportVerzeichnis + "\\" + ivDarlehenWPDatei);
            ivDarlehenWertpapierXML.openXML();
        }
        
        if (ivModus == DarlehenVerarbeiten.DARKA)
        {
            // Sicherheiten XML-Datei 
            ivSicherheitenXML = new SicherheitenXML(ivExportVerzeichnis + "\\" + ivSicherheitenDatei, LOGGER_DARKA);
            ivSicherheitenXML.openXML();
            ivListeSicherheiten = new SicherheitenListe();
        
            // Sicherungsobjekte XML-Datei
            ivSicherungsObjXML = new SicherungsobjekteXML(ivExportVerzeichnis + "\\" + ivSicherungsobjekteDatei, LOGGER_DARKA);
            ivSicherungsObjXML.openXML();
            ivListeSicherungsobjekte = new SicherungsobjekteListe();
        }
        
        // Darlehen einlesen und verarbeiten
        readDarlehen(ivImportVerzeichnis + "\\" + ivDarlehenImportDatei);
  
        // Alle Files wieder schliessen ...
        if (ivModus == DarlehenVerarbeiten.DARKA)
        {
            ivSicherheitenXML.printXMLEnde();
            ivSicherheitenXML.closeXML();
        
            ivSicherungsObjXML.printXMLEnde();
            ivSicherungsObjXML.closeXML();
        }
        
        ivDarlehenXML.printXMLEnde();
        ivDarlehenXML.closeXML();
        
        ivOutputDarlehenXML.printTXSImportDatenEnde();
        ivOutputDarlehenXML.closeXML();
        
        if (ivModus != DarlehenVerarbeiten.DPP)
        {
          ivDarlehenWertpapierXML.printXMLEnde();
          ivDarlehenWertpapierXML.closeXML();
        }
        
        // CT 14.02.2012
        //try 
        //{
        //  lvFosIDs.close();
        //}
        //catch (Exception e)
        //{
        //    System.out.println("Fehler beim Schliessen von fosIDs");
        //}
        // CT 14.02.2012

        // CT 17.07.2017
        try 
        {
        	ivFosKEVExtractor.close();
        }
        catch (Exception e)
        {
            LOGGER_DARKA.info("Fehler beim Schliessen der KEVExtractor-Datei");
        }
        // CT 17.07.2018

        
        // CT 06.05.2013
        try
        {
            ivFosCashflowQuellsystem.close();
        }
        catch (Exception e)
        {
            System.out.println("Fehler beim Schliessen der CashflowQuellsystem-Datei");
        }
        // CT 06.05.2013
        LOGGER_DARKA.info(printStatistik());
        
        lvCal = Calendar.getInstance();
        LOGGER_DARKA.info("Ende: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
    }
        
    /**
     * 
     */
    private void readDarlehen(String pvDateiname)
    {
        String lvZeile = null;
        
        ivOriginalDarlehen = new DarlehenKomplett(LOGGER_DARKA);
        
        // Oeffnen der Dateien
        FileInputStream lvFileInputStream = null;
        ivFileDarlehen = new File(pvDateiname);
        try
        {
            lvFileInputStream = new FileInputStream(ivFileDarlehen);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Darlehen-Datei nicht oeffnen!");
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
                  if (ivModus == DarlehenVerarbeiten.DARKA)
                  {
                      ivSicherheitenXML.printXMLStart(ivVorlaufsatz);
                      ivSicherungsObjXML.printXMLStart(ivVorlaufsatz);
                  }
                  ivDarlehenXML.printXMLStart(ivVorlaufsatz);
                  ivOutputDarlehenXML.printTXSImportDatenStart();
                  ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(ivVorlaufsatz.getsDwvbdat())));
                  if (ivModus != DarlehenVerarbeiten.DPP)
                  {
                    ivDarlehenWertpapierXML.printXMLStart(ivVorlaufsatz);
                  }
                  leseListeFinanzierungObjekte();
                  lvStart = false;
              }
              else
              {
                  if (!parseDarlehen(lvZeile)) // Parsen der Felder
                  {
                      System.out.println("Datenfehler!!!\n");
                  }
                  //System.out.println(s);
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
            System.out.println("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
          }

          // Das letzte Darlehen muss noch verarbeitet werden
        if (ivKtr != null)
          {
            //System.out.println("Vor Pruefung...");
            // Pruefe, ob das Datum im KON, KONTS und KTOZB korrekt ist
            ivOriginalDarlehen.pruefeDatum();
            //System.out.println("Nach Pruefung...");
            
            // CT 10.05.2017 - Einfach nur LettreDeGage-Schrott
            // LettreDeGage einfach verarbeiten
            //if (ivKtr.getAusplatzierungsmerkmal().startsWith("G"))
            //{
            //	verarbeiteDarlehen();
            //}
            
            if (ivModus == DarlehenVerarbeiten.DARKA || ivModus == DarlehenVerarbeiten.DPP)
            {
                //System.out.println("Kontonummer: " + ivOriginalDarlehen.getKTS().getKopf().getsDwhknr()); 
                //System.out.println("existsDummySegment: " + ivOriginalDarlehen.existsDummySegment());
                //System.out.println("existsPflichtsegmente: " + ivOriginalDarlehen.existsPflichtsegmente());

              if (!ivOriginalDarlehen.existsDummySegment() && ivOriginalDarlehen.existsPflichtsegmente() &&
                //    && (ivKtr.getsDd().equals("F") || ivKtr.getsDd().equals("D") || ivKtr.getsDd().equals("5") || ivKtr.getsDd().equals("6") ||
                //        ivKtr.getsDd().equals("1") || ivKtr.getsDd().equals("2")))
             	 (ivKtr.getAusplatzierungsmerkmal().startsWith("K") || ivKtr.getAusplatzierungsmerkmal().startsWith("H")))

              {
                  verarbeiteDarlehen();
              }
            }
            if (ivModus == DarlehenVerarbeiten.FLUGZEUGE)
            {
                //System.out.println("Flugzeuge...");
                //System.out.println("Kontonummer: " + ivOriginalDarlehen.getKTS().getKopf().getsDwhknr()); 
                //System.out.println("existsDummySegment: " + ivOriginalDarlehen.existsDummySegment());
                //System.out.println("existsPflichtsegmente: " + ivOriginalDarlehen.existsPflichtsegmente());
                if (!ivOriginalDarlehen.existsDummySegment() && ivOriginalDarlehen.existsPflichtsegmente() && 
                  //      (ivKtr.getsDd().equals("U") || ivKtr.getsDd().equals("W") || ivKtr.getsDd().equals("4") || ivKtr.getsDd().equals("8")))
                	ivKtr.getAusplatzierungsmerkmal().startsWith("F")) 
                {
                    verarbeiteDarlehen();
                }                                    
            }
            if (ivModus == DarlehenVerarbeiten.SCHIFFE)
            {
                //System.out.println("Schiffe...");
                //System.out.println("Kontonummer: " + ivOriginalDarlehen.getKTS().getKopf().getsDwhknr()); 
                //System.out.println("existsDummySegment: " + ivOriginalDarlehen.existsDummySegment());
                //System.out.println("existsPflichtsegmente: " + ivOriginalDarlehen.existsPflichtsegmente());
                //System.out.println("Deckungskennzeichen: " + ivKtr.getsDd());
                if (!ivOriginalDarlehen.existsDummySegment() && ivOriginalDarlehen.existsPflichtsegmente() && 
                 //   (ivKtr.getsDd().equals("S") || ivKtr.getsDd().equals("V") || ivKtr.getsDd().equals("3") || ivKtr.getsDd().equals("7")))
                	ivKtr.getAusplatzierungsmerkmal().startsWith("S"))
                {
                    verarbeiteDarlehen();
                }                                                                       
            }
            if (ivModus == DarlehenVerarbeiten.OEPG)
            {
                if (!ivOriginalDarlehen.existsDummySegment() && ivOriginalDarlehen.existsPflichtsegmente() && 
                 //   (ivKtr.getsDd().equals("A") || ivKtr.getsDd().equals("9")))
                	ivKtr.getAusplatzierungsmerkmal().startsWith("O"))
                {
                    verarbeiteDarlehen();
                }
            }
            if (ivModus == DarlehenVerarbeiten.KEV)
            {
                  if (!ivOriginalDarlehen.existsDummySegment() && ivOriginalDarlehen.existsPflichtsegmente() &&
                      //(ivKtr.getsDd().equals("A") || ivKtr.getsDd().equals("9")))
                      ivKtr.getAusplatzierungsmerkmal().startsWith("A"))
                  {
                      verarbeiteDarlehen();
                  }
              }
              if (ivModus == DarlehenVerarbeiten.KEV_EXTRACTOR)
            {
            	if (ivKtr.getAusplatzierungsmerkmal().equals("A0") || ivKtr.getAusplatzierungsmerkmal().equals("A1"))
            	{
            		// InstNr;KtoNr;DZR;ZBBG;DLZ;DHZI;DNZI;
            		LOGGER_DARKA.info(ivVorlaufsatz.getsDwvinst() + ";" + ivOriginalDarlehen.getKTR().getKopf().getsDwhknr() + ";" + ivOriginalDarlehen.getKTS().getsDzr() + ";" +
            		                  ivOriginalDarlehen.getKTOZB().getsZbbg() + ";" + ivOriginalDarlehen.getKTR().getsDlz() + ";" + ivOriginalDarlehen.getKTR().getsDhzi() + ";" +
            				          ivOriginalDarlehen.getKTR().getsDnzi() + ";");
            		try
            		{
            			ivFosKEVExtractor.write((ivVorlaufsatz.getsDwvinst() + ";" + ivOriginalDarlehen.getKTR().getKopf().getsDwhknr() + ";" + ivOriginalDarlehen.getKTS().getsDzr() + ";" +
            					                ivOriginalDarlehen.getKTOZB().getsZbbg() + ";" + ivOriginalDarlehen.getKTR().getsDlz() + ";" + ivOriginalDarlehen.getKTR().getsDhzi() + ";" +
  				                                ivOriginalDarlehen.getKTR().getsDnzi() + ";" + StringKonverter.lineSeparator).getBytes());
            		}
            		catch (Exception exp)
            		{
            		   LOGGER_DARKA.error("Fehler beim Schreiben in die KEVExtractor-Datei");	
            		}
            	}
            }
          }
          
          try
          {
            lvBufferedReaderIn.close();
          }
          catch (Exception e)
          {
            System.out.println("Konnte Darlehen-Datei nicht schliessen!");
          }
             
        //if (ivLogging)
        //  {
        //LOGGER_DARKA.info(printStatistik());
        //  }
        }
    
    /**
     * 
     */
    private void leseListeFinanzierungObjekte()
    {
        // CT 11.03.2014 - Finanzierung - Liste der Objektnummern einlesen - Start
        ivListeObjekte = new ListeObjekte();

        File lvFileListeObjektNr = new File(ivImportVerzeichnis + "\\Objektnummern_" + (DatumUtilities.changeDate(DatumUtilities.changeDatePoints(ivVorlaufsatz.getsDwvbdat()))).replace("-", "_") + ".csv");
        if (lvFileListeObjektNr.exists())
        {                        
            FileInputStream lvFileIS = null;
            try
            {
                lvFileIS = new FileInputStream(lvFileListeObjektNr);
            }
            catch (Exception e)
            {
                System.out.println("Konnte File mit der Liste der Objektnummer bei den Finanzierungen nicht oeffnen!");
            }
            BufferedReader lvReaderIn = new BufferedReader(new InputStreamReader(lvFileIS));
            boolean lvFirst = true;
            String s = new String();
            try
            {
                while ((s = lvReaderIn.readLine()) != null)  // Lesen der Datei
                {
                    if (s != null)
                    {
                        if (lvFirst)
                        {
                            lvFirst = false;
                        }
                        else
                        {
                          System.out.println(s);
                        
                          ObjektElement lvObjektElement = new ObjektElement();

                          System.out.println("Finanzierung Externer Key: " + s.substring(0, s.indexOf(";")));
                          lvObjektElement.setFinanzierungExternerKey(s.substring(0, s.indexOf(";")));
                          s = s.substring(s.indexOf(";") + 1);
                          System.out.println("Objekt-/Facility-Nr: " + s.substring(0, s.indexOf(";")));
                          lvObjektElement.setObjektNr(s.substring(0, s.indexOf(";")));
                          s = s.substring(s.indexOf(";") + 1);
                          System.out.println("Quellsystem: " + s);
                          lvObjektElement.setQuellsystem(s);
                        
                          ivListeObjekte.addObjekt(lvObjektElement);
                        }
                    }
                }
            }
            catch (Exception e)
            {
              System.out.println("Fehler beim Verarbeiten einer Zeile:" + s);
              e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Konnte Datei " + lvFileListeObjektNr.getName() + " nicht finden...");
        }
        
        // CT 11.03.2014 - Finanzierung - Liste der Objektnummern einlesen - Ende

    }
    
      /**
       * @return 
       */
      public String printStatistik()
      {
        StringBuffer lvStringBuffer = new StringBuffer();
        lvStringBuffer.append(ivVorlaufsatz.toString());
        
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerOBJ + " OBJ-Segmente gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerBAUFI + " BAUFI-Segmente gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerKTS + " KTS-Segmente gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerKTR + " KTR-Segmente gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerINF + " INF-Segmente gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerKON + " KON-Segmente gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerKONTS + " KONTS-Segmente gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerKTOZB + " KTOZB-Segmente gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerREC + " REC-Segmente gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        lvStringBuffer.append(ivZaehlerUMS + " UMS-Segmente gelesen...");
        lvStringBuffer.append(StringKonverter.lineSeparator);
        
        return lvStringBuffer.toString();
      }

    /**
     * @param pvZeile 
     * @return 
     *       
     */
     private boolean parseDarlehen(String pvZeile)
     {             
        String stemp = new String(); // arbeitsbereich/zwischenspeicher feld
        int lvLfd = 0; // lfd feldnr, pruefsumme je satzart
        int lvZzStr = 0; // pointer fuer satzbereich
        
        ivStatus = UNDEFINIERT;
        
        //System.out.println("Zeile: " + pvZeile);
        
        //LOGGER_DARKA.info("parseDarlehen...");
        // steuerung/iteration eingabesatz
        for (lvZzStr = 0; lvZzStr < pvZeile.length(); lvZzStr++)
        {  
          // wenn semikolon erkannt
            if (pvZeile.charAt(lvZzStr) == ';')
          {
                //System.out.println("stemp: " + stemp);
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
                        //System.out.println("Kontonummerwechsel: von " + ivAlteKontonummer + " nach " + ivKontonummer);

                      if (ivAlteKontonummer != null)
                      {
                        if (!ivKontonummer.equals(ivAlteKontonummer))
                        {
                          if (!ivKontonummer.equals("0000000000"))
                          {
                             if (ivKtr != null)
                            {
                            	// Einzelkontenauflistung - CT 07.06.2018 
                                //LOGGER_DARKA.info("Einzelkonten;" + ivOriginalDarlehen.getKTS().getKopf().getsDwhknr() + ";" + 
                                //                  ivOriginalDarlehen.getKTR().getKopf().getsDwhkdnr() + ";" + 
                                //		          ivOriginalDarlehen.getKTR().getsDd() + ";" +
                         		//                  ivOriginalDarlehen.getKTR().getAusplatzierungsmerkmal() + ";" +
                                //		          (ivOriginalDarlehen.getINFS() != null?ivOriginalDarlehen.getINFS().getsBdmb():"") + ";" +
                                //		          //ivOriginalDarlehen.getINFS().getsBdmb()+ ";" + 
                         		//                  ivOriginalDarlehen.getKTS().getsDvs() + ";" +
                         		//                  ivOriginalDarlehen.getKTS().getsDpd() + ";;" + 
                         		//                  ivOriginalDarlehen.getKTS().getsDkz() + ";" + 
                         		//                  ivOriginalDarlehen.getKTS().getsDrk() + ";;" +
                         		//                  //ivOriginalDarlehen.getZusagedatum() + ";" + 
                         		//                  ivOriginalDarlehen.getKTS().getsDdv() + ";" + 
                         		//                  ivOriginalDarlehen.getKTR().getsDdvb());
                            	 
                                // Pruefe, ob das Datum in den KON, KONTS und KTOZB korrekt ist
                                ivOriginalDarlehen.pruefeDatum();
                              
                                // CT 10.05.2017 - Einfach nur LettreDeGage-Schrott
                                // LettreDeGage einfach verarbeiten
                                //if (ivKtr.getAusplatzierungsmerkmal().startsWith("G"))
                                //{
                                //	verarbeiteDarlehen();
                                //}
                                
                                if (ivModus == DarlehenVerarbeiten.DARKA || ivModus == DarlehenVerarbeiten.DPP)
                                {
                                  if (!ivOriginalDarlehen.existsDummySegment() && ivOriginalDarlehen.existsPflichtsegmente() && 
                                        //  (ivKtr.getsDd().equals("F") || ivKtr.getsDd().equals("D") || ivKtr.getsDd().equals("5") || ivKtr.getsDd().equals("6") || 
                                        //   ivKtr.getsDd().equals("1") || ivKtr.getsDd().equals("2")))
                                	 (ivKtr.getAusplatzierungsmerkmal().startsWith("K") || ivKtr.getAusplatzierungsmerkmal().startsWith("H")))
                                  {
                                       verarbeiteDarlehen();
                                  }
                                }
                                if (ivModus == DarlehenVerarbeiten.FLUGZEUGE)
                                {
                                    //System.out.println("Flugzeuge...");
                                    if (!ivOriginalDarlehen.existsDummySegment() && ivOriginalDarlehen.existsPflichtsegmente() && 
                                        //    (ivKtr.getsDd().equals("U") || ivKtr.getsDd().equals("W") || ivKtr.getsDd().equals("4") || ivKtr.getsDd().equals("8")))
                                    	ivKtr.getAusplatzierungsmerkmal().startsWith("F"))
                                    	{
                                     		verarbeiteDarlehen();
                                    	}                                    
                                }
                                if (ivModus == DarlehenVerarbeiten.SCHIFFE)
                                {
                                    //System.out.println("Schiffe...");
                                    //System.out.println("Kontonummer: " + ivOriginalDarlehen.getKTS().getKopf().getsDwhknr()); 
                                    //System.out.println("existsDummySegment: " + ivOriginalDarlehen.existsDummySegment());
                                    //System.out.println("existsPflichtsegmente: " + ivOriginalDarlehen.existsPflichtsegmente());
                                    //System.out.println("Deckungskennzeichen: " + ivKtr.getsDd());
                                    if (!ivOriginalDarlehen.existsDummySegment() && ivOriginalDarlehen.existsPflichtsegmente() && 
                                        //    (ivKtr.getsDd().equals("S") || ivKtr.getsDd().equals("V") || ivKtr.getsDd().equals("3") || ivKtr.getsDd().equals("7")))
                                    	ivKtr.getAusplatzierungsmerkmal().startsWith("S"))	
                                    {
                                        verarbeiteDarlehen();
                                    }                                                                       
                                }
                                if (ivModus == DarlehenVerarbeiten.OEPG)
                                {
                                    if (!ivOriginalDarlehen.existsDummySegment() && ivOriginalDarlehen.existsPflichtsegmente() && 
                                        //(ivKtr.getsDd().equals("A") || ivKtr.getsDd().equals("9")))
                                        ivKtr.getAusplatzierungsmerkmal().startsWith("O"))
                                    {
                                         verarbeiteDarlehen();
                                    }
                                }
                                if (ivModus == DarlehenVerarbeiten.KEV)
                                {
                                    if (!ivOriginalDarlehen.existsDummySegment() && ivOriginalDarlehen.existsPflichtsegmente() &&
                                        //(ivKtr.getsDd().equals("A") || ivKtr.getsDd().equals("9")))
                                        ivKtr.getAusplatzierungsmerkmal().startsWith("A0") || ivKtr.getAusplatzierungsmerkmal().startsWith("A1"))
                                    {
                                        verarbeiteDarlehen();
                                    }
                                }
                                if (ivModus == DarlehenVerarbeiten.KEV_EXTRACTOR)
                                {
                                	if (ivKtr.getAusplatzierungsmerkmal().equals("A0") || ivKtr.getAusplatzierungsmerkmal().equals("A1"))
                                	{
                                		// InstNr;KtoNr;DZR;ZBBG;DLZ;DHZI;DNZI;
                                		LOGGER_DARKA.info(ivVorlaufsatz.getsDwvinst() + ";" + ivOriginalDarlehen.getKTR().getKopf().getsDwhknr() + ";" + ivOriginalDarlehen.getKTS().getsDzr() + ";" +
                                		                  ivOriginalDarlehen.getKTOZB().getsZbbg() + ";" + ivOriginalDarlehen.getKTR().getsDlz() + ";" + ivOriginalDarlehen.getKTR().getsDhzi() + ";" +
                                				          ivOriginalDarlehen.getKTR().getsDnzi() + ";");
                                		try
                                		{
                                			ivFosKEVExtractor.write((ivVorlaufsatz.getsDwvinst() + ";" + ivOriginalDarlehen.getKTR().getKopf().getsDwhknr() + ";" + ivOriginalDarlehen.getKTS().getsDzr() + ";" +
                                					                ivOriginalDarlehen.getKTOZB().getsZbbg() + ";" + ivOriginalDarlehen.getKTR().getsDlz() + ";" + ivOriginalDarlehen.getKTR().getsDhzi() + ";" +
                      				                                ivOriginalDarlehen.getKTR().getsDnzi() + ";" + StringKonverter.lineSeparator).getBytes());
                                		}
                                		catch (Exception exp)
                                		{
                                			LOGGER_DARKA.error("Fehler beim Schreiben in die KEVExtractor-Datei");	
                                		}

                                	}
                                }
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
      * Verarbeite das Darlehen
      */
     private void verarbeiteDarlehen()
     {           
         LOGGER_DARKA.info("verarbeiteDarlehen...");
             	 
         //LOGGER_DARKA.info("Ueberpruefe " + ivOriginalDarlehen.getKTS().getKopf().getsDwhknr());
         // Gibt es die Kontonummer in den ANNA-Daten
         if (ivModus != DarlehenVerarbeiten.KEV && ivANNA.existsKontonummerQuellsystem(ivOriginalDarlehen.getKTS().getKopf().getsDwhknr()))
         {
         	LOGGER_DARKA.info("-->Konto " + ivOriginalDarlehen.getKTS().getKopf().getsDwhknr() + " existiert in der ANNA-Datei --> Keine Verarbeitung");
         }         
         else
         {
  
          // OBJ-Segment vorhanden?
         if (ivModus == DarlehenVerarbeiten.DARKA)
         {
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
                 if (sObj.extractSicherungsobjekt(ivOriginalDarlehen, LOGGER_DARKA))
                 {
                     if (!ivListeSicherungsobjekte.contains(sObj.getObjektnummer()))
                     {
                         ivListeSicherungsobjekte.put(sObj.getObjektnummer(), sObj);
                         ivSicherungsObjXML.printSicherungsobjekte(sObj);
                     }
                 }
             }
         }       
         
        ivZielDarlehen = new Darlehen(LOGGER_DARKA, ivFilenameUmsatz19);
        ivZielDarlehen.extractDarlehen(ivOriginalDarlehen);
        if (ivModus != DarlehenVerarbeiten.DPP)
        {
            // Sonderbehandlung BLB OEPG - KfW (Weiterreichungs-) Kredite an DarlehenWP liefern
            //if (ivModus == DarlehenVerarbeiten.OEPG && ivZielDarlehen.getInstitutsnummer().equals("004") && ivZielDarlehen.getProduktSchluessel().equals("05201"))
            //{
            //    ivZielDarlehen.extractWPBesonderheiten(ivOriginalDarlehen);
            //    if (ivZielDarlehen.darlehenPruefen())
            //    {                   
            //        ivDarlehenWertpapierXML.printDarlehen(ivZielDarlehen);
            //    }                
            //}
            //else
            //{
                if ((StringKonverter.convertString2Int(ivZielDarlehen.getKontotyp()) == 2 ||
                     StringKonverter.convertString2Int(ivZielDarlehen.getKontotyp()) == 4) &&
                 // Aktive Namenspfandbriefe auch an Passivverarbeitung weiterleiten
                 // Erkennung ueber Deckungsschluessel
                 //ivZielDarlehen.getDeckungsschluessel().equals("3") || ivZielDarlehen.getDeckungsschluessel().equals("4") ||
                 //ivZielDarlehen.getDeckungsschluessel().equals("7") || ivZielDarlehen.getDeckungsschluessel().equals("8")) &&
                 !ivZielDarlehen.getProduktSchluessel().equals("05201")) // KfW (Weiterreichungs-) Kredite nicht liefern
                {
                    ivZielDarlehen.extractWPBesonderheiten(ivOriginalDarlehen);
                    if (ivZielDarlehen.darlehenPruefen())
                    {                   
                        ivDarlehenWertpapierXML.printDarlehen(ivZielDarlehen);
                    }
                }
            //}
        }
        
        //LOGGER_DARKA.info("Einzelkonten;" + ivZielDarlehen.getKontonummer() + ";" + ivZielDarlehen.getKundennummer() + ";" + ivZielDarlehen.getDeckungsschluessel() + ";" +
		    //ivZielDarlehen.getAusplatzierungsmerkmal() + ";" + ivZielDarlehen.getSolldeckung() + ";" + ivZielDarlehen.getVerwaltendeOE() + ";" +
		    //        ivZielDarlehen.getProduktSchluessel() + ";" + ivZielDarlehen.getKontozustand() + ";" + ivZielDarlehen.getRestkapital() + ";" +
		    //        ivZielDarlehen.getZusageDatum() + ";" + ivZielDarlehen.getVollvalutierungsdatum() + ";" + ivZielDarlehen.getVertragBis());
        
        //System.out.println("Kontotyp: " + ivZielDarlehen.getKontotyp());
        if (StringKonverter.convertString2Int(ivZielDarlehen.getKontotyp()) == 1 ||
            StringKonverter.convertString2Int(ivZielDarlehen.getKontotyp()) == 3)
        {  
            if (ivZielDarlehen.darlehenPruefen())
            {
                // CT 13.03.2014 - Rausgenommen - Test abgeschlossen...
                // Nur zu Testzwecken BLB - Solldeckung = Restkapital
                //if (ivZielDarlehen.getInstitutsnummer().equals("004") && ivModus == DarlehenVerarbeiten.SCHIFFE)
                //{
                //  ivZielDarlehen.setSolldeckung(ivZielDarlehen.getRestkapital());
                //  LOGGER_DARKA.info("Kredit: " + ivZielDarlehen.getKontonummer() + " - Solldeckung auf Restkapital gesetzt...");
                //}
                // CT 13.02.2014 - Wieder entfernen
                ivDarlehenXML.printDarlehen(ivZielDarlehen);
               try
               {
                 importDarlehen2Transaktion();
               }
               catch (Exception exp)
               {
                   LOGGER_DARKA.error("Verarbeitungsfehler - Konto " + ivZielDarlehen.getKontonummer() + StringKonverter.lineSeparator);
                   LOGGER_DARKA.error(exp.getMessage());
                   exp.printStackTrace();
               }
             }
         }
         }
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
                 System.out.println("einfuegenSegment - Unbekannte Satzart: " + ivStatus);
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
                System.out.println("setzeWert - Unbekannte Satzart: " + pvStatus);
         }   
     }
     
     /**
      * Importiert die Darlehensinformationen in die TXS-Transaktionen
      */
    public void importDarlehen2Transaktion()
     {
    	LOGGER_DARKA.info("Start importDarlehen2Transaktion...");
        ivFinanzgeschaeft.initTXSFinanzgeschaeft();
        ivSliceInDaten.initTXSSliceInDaten();
        ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
        ivKondDaten.initTXSKonditionenDaten();
        ivKredkunde.initTXSKreditKunde();

        TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
        TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
        TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
        TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
        TXSVerzeichnisVBlatt lvVevb = new TXSVerzeichnisVBlatt();
        TXSVerzeichnisblattDaten lvVbdaten = new TXSVerzeichnisblattDaten();
        TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
        TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();
        TXSSicherheitPerson lvShperson = new TXSSicherheitPerson();
         
        boolean lvSolldeckungOEPG = true;
        // OEPG nur dann liefern, wenn die Solldeckung > 0
        // Die anderen Kredite durchlassen, deshalb defaultmaessig 'true'
        if (ivModus == DarlehenVerarbeiten.OEPG)
        {
            LOGGER_DARKA.info("OEPG;" + ivZielDarlehen.getKontonummer() + ";" + ivZielDarlehen.getSolldeckung() + ";");
        	System.out.println("Konto: " + ivZielDarlehen.getKontonummer() + " - Deckungskennzeichen: " + ivZielDarlehen.getDeckungsschluessel() + " Ausplatzierungsmerkmal: " + ivZielDarlehen.getAusplatzierungsmerkmal());

            /* Solldeckung *********************************** */
            BigDecimal lvSollDeckung = new BigDecimal("0.0");
            //System.out.println("Solldeckung: " + darlehen.getSolldeckung());
            lvSollDeckung = StringKonverter.convertString2BigDecimal(ivZielDarlehen.getSolldeckung());
            if (lvSollDeckung.doubleValue() <= 0.0)
            {
                lvSolldeckungOEPG = false;
            }
        }
        
        if (lvSolldeckungOEPG)
        {
            boolean lvOkayDarlehen = true;
            
            String lvInstKennung = new String();
            String lvKundenNr = new String();

            if (ivModus == DarlehenVerarbeiten.DPP)
            {
                // Originator und Institutsnummer ueber die Kontonummer ermitteln
                //System.out.println("CT - Kontonummer: " + ivZielDarlehen.getKontonummer());
                if (ivDpp.getInstitutskennung(ivZielDarlehen.getKontonummer()).isEmpty())
                {
                  System.out.println("Kein Institut fuer Kontonummer " + ivZielDarlehen.getKontonummer() + " gefunden.");
                  lvOkayDarlehen = false;
                }
                else
                {
                    lvInstKennung = ivDpp.getInstitutskennung(ivZielDarlehen.getKontonummer());
                }
                if (ivDpp.getKundennummer(ivZielDarlehen.getKontonummer()).isEmpty())
                {
                  System.out.println("Keine Kundennummer fuer Kontonummer " + ivZielDarlehen.getKontonummer() + " gefunden.");
                  lvOkayDarlehen = false;
                }
                else
                {
                    lvKundenNr = ivDpp.getKundennummer(ivZielDarlehen.getKontonummer());
                }    
            }
            
            if (lvOkayDarlehen)
            {
              lvOkayDarlehen = ivFinanzgeschaeft.importDarlehen(ivModus, ivZielDarlehen);
            }
            
            //if (!ivSapcms.existsSicherheitObjekt(ivZielDarlehen.getKontonummer()))
            //{
            if (lvOkayDarlehen)
            {
                if (ivModus == DarlehenVerarbeiten.DPP)
                {
                    // Originator fuer DPP umsetzen
                    //ivFinanzgeschaeft.setKey(ivFinanzgeschaeft.getKey());
                    ivFinanzgeschaeft.setOriginator(lvInstKennung);
                }
      
                lvOkayDarlehen = ivFinanzgeschaeftDaten.importDarlehen(ivModus, ivZielDarlehen, LOGGER_DARKA);
            }
            if (lvOkayDarlehen)
            {
            	lvOkayDarlehen = ivSliceInDaten.importDarlehen(ivModus, ivZielDarlehen, LOGGER_DARKA);
            }
            if (lvOkayDarlehen)
            {
            	if (ivModus == DarlehenVerarbeiten.DPP)
            	{
                
            		// Transaktion und Pool fuer DPP je nach Produktschluessel umsetzen
            		if (ivZielDarlehen.getProduktSchluessel().equals("00117"))
            		{
            			//ivSliceInDaten.setKey(lvInstKennung + "_" + ivZielDarlehen.getKontonummer() + "P");
            			ivSliceInDaten.setTx("Hypothekenpfandbrief");
            			ivSliceInDaten.setPool("Hypothekenpfandbrief");
            		}
            		ivSliceInDaten.setBis(ivFinanzgeschaeftDaten.getNbetrag());

            	}
            	lvOkayDarlehen = ivKondDaten.importDarlehen(ivModus, ivZielDarlehen, LOGGER_DARKA);
            
            	if (ivModus == DarlehenVerarbeiten.DPP)
            	{
            		// TXS muss selber rechnen, da keine Cashflows angeliefert werden
            		ivKondDaten.setMantilg("0");
            		ivKondDaten.setManzins("0");
              
            		// Nominalzins auf '0.0' setzen
            		ivKondDaten.setNomzins("0.0");
              
            		// Zinsrythmus auf 'Sonderfall' setzen
            		ivKondDaten.setZinsryth("13");   
              
            		// Datum naechste Zinsanpassung auf Faelligkeit setzen
            		ivKondDaten.setDnz(DatumUtilities.changeDatePoints(ivKondDaten.getFaellig().replace("-", "")));
            	}
            }
            if (lvOkayDarlehen)
            {
            	lvOkayDarlehen = ivKredkunde.importDarlehen(ivModus, ivZielDarlehen);
            }
        
            if (lvOkayDarlehen)
            {
            	if (ivModus == DarlehenVerarbeiten.DPP)
            	{
            		// Key und Originator fuer DPP umsetzen
            		ivKredkunde.setKdnr(lvInstKennung + "_" + lvKundenNr);
            		ivKredkunde.setOrg(lvInstKennung);
            	}
            }
       
            // Transaktionen in die Datei schreiben
            if (lvOkayDarlehen)
            {
            	// CT 06.05.2013 - Daten in CashflowQuellsystem-Datei schreiben 
            	try
            	{
            		String lvBuergschaft = new String();  
            		if (ivZielDarlehen.getBuergschaftSchluessel().equals("000"))
            		{
            			lvBuergschaft = "N";  
            		}
            		else
            		{
            			lvBuergschaft = "J";
            		}
            		ivFosCashflowQuellsystem.write((ivZielDarlehen.getKontonummer() + ";" + ivFinanzgeschaeft.getKey() + ";" + ivFinanzgeschaeft.getOriginator() + ";" + ivFinanzgeschaeft.getQuelle() + ";" + 
            		                                lvBuergschaft + ";" + ivZielDarlehen.getBuergschaftProzent() + ";" + ivZielDarlehen.getKontozustand() + ";" + 
            				                        ivKondDaten.getMantilg() + ";" + ivKondDaten.getManzins() + ";" + ivKondDaten.getFaellig() + StringKonverter.lineSeparator).getBytes());
            	}
            	catch (Exception e)
            	{
            		System.out.println("Fehler bei der Ausgabe in die CashflowQuellsystem-Datei");
            	}
            	// CT 06.05.2013
        	
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
        
        // Deckungspooling Sicherheiten verarbeiten
        if (ivModus == DarlehenVerarbeiten.DPP)
        {
            // Sicherheiten aus OSPlus verarbeiten
            OSPSicherheitenVerarbeitung lvShVerarbeitung = ivDpp.getSicherheitenVerarbeitung();
            if (lvShVerarbeitung != null)
            {
                //System.out.println("shVerarbeitung");
                ivOutputDarlehenXML.printTransaktion(lvShVerarbeitung.importSicherheitObjekt2TXSKreditSicherheit(ivZielDarlehen));
            }
                             
        }
        
        // Wenn Verarbeitung DarKa und SAP CMS Daten geladen, dann verarbeiten
        if (ivModus == DarlehenVerarbeiten.DARKA && ivSapcms != null && !ivFinanzgeschaeft.getQuelle().startsWith("ADAWP"))
         {
            if (ivZielDarlehen.getKredittyp().equals("1"))
            {
                ////ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitObjekte(ivZielDarlehen.getKontonummer(), ivZielDarlehen.getKredittyp(), ivZielDarlehen.getBuergschaftProzent(), ivZielDarlehen.getDeckungsschluessel()));
                try
                {
            	  ivOutputDarlehenXML.printTransaktion(ivSapcms.getSicherheitenDaten().getSicherheiten2Pfandbrief().importSicherheitHypotheken(ivZielDarlehen.getKontonummer(), new String(), "", ivZielDarlehen.getKredittyp(), ivZielDarlehen.getBuergschaftProzent(), "ADARLPFBG", ivInstitutsnummer));

                }
            	catch (Exception exp)
            	{
                  LOGGER_DARKA.error("Verarbeitungsfehler Sicherheit Pfandbrief - Konto " + ivZielDarlehen.getKontonummer() + StringKonverter.lineSeparator);
                  LOGGER_DARKA.error(exp.getMessage());
                  exp.printStackTrace();
            	}
            }
            //// CT 07.11.2019 - Noch in der Testphase
            ////if (ivZielDarlehen.getKredittyp().equals("2") || ivZielDarlehen.getKredittyp().equals("4"))
            ////{
            ////    try
            ////    {
            ////    	  ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitBuergschaft(ivZielDarlehen.getKontonummer(), "ADARLPFBG", ivZielDarlehen.getRestkapital(), ivZielDarlehen.getBuergschaftProzent(), ivZielDarlehen.getAusplatzierungsmerkmal(), ivZielDarlehen.getUrsprungsKapital(), ivZielDarlehen.getKundennummer(), ivZielDarlehen.getBuergennummer()));
            ////    }
            ////  	catch (Exception exp)
            ////    {
            ////      LOGGER_DARKA.error("Verarbeitungsfehler Sicherheit Buergschaft - Konto " + ivZielDarlehen.getKontonummer() + StringKonverter.lineSeparator);
            ////      LOGGER_DARKA.error(exp.getMessage());
            ////      exp.printStackTrace();
            ////    }
            ////}
            //// CT 07.11.2019 - Noch in der Testphase
         }
         
        if (ivModus == DarlehenVerarbeiten.DARKA || ivModus == DarlehenVerarbeiten.OEPG)
         {
            // Verbuergte Darlehen
            if (ivZielDarlehen.getKredittyp().equals("2") || ivZielDarlehen.getKredittyp().equals("4"))
             {
            	// CT 14.03.2016 - Herausgenommen
            	//LOGGER_DARKA.info("Buerge in Sicherheiten: " + ivZielDarlehen.getKontonummer() + ";" + ivSapcms.getSicherheitId(ivZielDarlehen.getKontonummer()));
                //ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitBuergschaft(ivZielDarlehen.getKontonummer(), "ADARLPFBG", ivZielDarlehen.getRestkapital(), ivZielDarlehen.getBuergschaftProzent()));
            	// CT 14.03.2016 - Herausgenommen
            	if (lvKredsh.importDarlehen(ivModus, ivZielDarlehen, LOGGER_DARKA))
                {
                    // Transaktionen in die Datei schreiben
                    ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionStart());
                    ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionDaten());
 
                    // TXSSicherheitDaten
                    lvShdaten.importDarlehen(ivZielDarlehen);
                    ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionStart());
                    ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionDaten());
                    ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionEnde());
                   
                    // TXSSicherheitPerson
                    lvShperson.setTXSSicherheitPerson(ivZielDarlehen.getBuergennummer(), ivZielDarlehen.getInstitutsnummer());
                    ivOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionStart());
                    ivOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionDaten());
                    ivOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionEnde());
                   
                    ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionEnde());                    
                }
             }
             else
             {
                // Hypothekendarlehen 
                if (ivZielDarlehen.getKredittyp().equals("1") && !ivFinanzgeschaeft.getQuelle().startsWith("ADAWP") && !(ivModus == DarlehenVerarbeiten.OEPG))
                {
                    Sicherheit lvSicherheit = ivListeSicherheiten.getSicherheit(ivZielDarlehen.getObjektnummer()); //+ "_" + zielDarlehen.getKontonummer());
                    Sicherungsobjekt lvSicherungsObj = ivListeSicherungsobjekte.getSicherungsobjekt(ivZielDarlehen.getObjektnummer());

                    ObjektZuweisungsbetrag lvObjektZuweisungsbetrag = ivObjektZuweisungsListe.getObjektZuweisungsbetrag(lvSicherheit.getObjektnummer());
                    if (lvObjektZuweisungsbetrag != null)
                    {
                        lvSicherheit.setVerfuegungsbetrag(lvObjektZuweisungsbetrag.getZuweisungsbetrag().toString());
                    }
             
                    // TXSKreditSicherheit
                    if (lvKredsh.importDarlehen(ivModus, ivZielDarlehen, lvSicherheit, LOGGER_DARKA))
                    {
                        // Direkt in die Datei zu schreiben  
                        ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionStart());
                        ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionDaten());
               
                        // TXSSicherheitDaten
                        lvShdaten.importDarlehen(ivZielDarlehen, lvSicherheit, lvSicherungsObj);
                        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionStart());
                        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionDaten());
                        ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionEnde());
               
                        // TXSSicherheitVerzeichnis
                        lvShve.importDarlehen(ivModus, ivZielDarlehen, lvSicherheit);
                        ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionStart());
                        ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionDaten());

                        // TXSVerzeichnisDaten
                        lvVedaten.importDarlehen(ivZielDarlehen, lvSicherheit, lvSicherungsObj);
                        ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionStart());
                        ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionDaten());
                        ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionEnde());

                        // TXSVerzeichnisBlatt 
                        lvVevb.importDarlehen(ivModus, ivZielDarlehen, lvSicherheit);
                        ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionStart());
                        ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionDaten());
 
                        // TXSVerzeichnisblattDaten
                        lvVbdaten.importDarlehen(lvSicherungsObj);
                        ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionStart());
                        ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionDaten());
                        ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionEnde());
               
                        ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionEnde());
                           
                        // TXSVerzeichnisPfandobjekt
                        lvVepo.importDarlehen(ivModus, ivZielDarlehen, lvSicherungsObj);
                        ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionStart());
                        ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionDaten());
               
                        // TXSPfandobjektDaten
                        lvPodaten.importDarlehen(ivZielDarlehen, lvSicherungsObj);
                        ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionStart());
                        ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionDaten());
                        ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionEnde());
               
                        ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionEnde());
               
                        ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionEnde());
               
                        ivOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionEnde());
                 
                        /* if (logging)
                           {
                             log.writeln("SH_DARL;" + zielDarlehen.getKontonummer() + ";" + zielDarlehen.getKundennummer() + ";" + zielDarlehen.getDeckungsschluessel() + ";" 
                                 + zielDarlehen.getBewilligendeOE() + ";" + zielDarlehen.getVerwaltendeOE() + ";" + zielDarlehen.getProduktverantwortlicheOE() + ";" 
                                 + zielDarlehen.getKundenbetreuendeOE() + ";" + zielDarlehen.getProduktSchluessel() + ";" + zielDarlehen.getZuweisungsbetrag() + ";"
                                 + zielDarlehen.getRestkapital() + ";" + zielDarlehen.getUrsprungsKapital() + ";" + zielDarlehen.getBuergschaftSchluessel() + ";" 
                                 + zielDarlehen.getVertragBis() + ";");
                           } 
                         */
                    }
                }
             }
         }
 
        if (lvOkayDarlehen)
        {
        	// Wenn Verarbeitung Schiffe und SAP CMS Daten geladen, dann Schiff anhaengen
        	if (ivModus == DarlehenVerarbeiten.SCHIFFE && ivSapcms != null && !ivFinanzgeschaeft.getQuelle().startsWith("ADAWP"))
        	{
        		try
        		{
        			ivOutputDarlehenXML.printTransaktion(ivSapcms.getSicherheitenDaten().getSicherheiten2Pfandbrief().importSicherheitSchiff(ivZielDarlehen.getKontonummer(), "ADARLSCHF", ivInstitutsnummer));
        		}
        		catch (Exception exp)
        		{
        			LOGGER_DARKA.error("Verarbeitungsfehler Sicherheit Schiff - Konto " + ivZielDarlehen.getKontonummer() + StringKonverter.lineSeparator);
        			LOGGER_DARKA.error(exp.getMessage());
        			exp.printStackTrace();
        		}
        	}
        }
        
        if (lvOkayDarlehen)
        {
        	// Wenn Verarbeitung Flugzeug und SAP CMS Daten geladen, dann Flugzeug anhaengen
        	if (ivModus == DarlehenVerarbeiten.FLUGZEUGE && ivSapcms != null && !ivFinanzgeschaeft.getQuelle().startsWith("ADAWP"))
        	{
        		try
        		{
        			ivOutputDarlehenXML.printTransaktion(ivSapcms.getSicherheitenDaten().getSicherheiten2Pfandbrief().importSicherheitFlugzeug(ivZielDarlehen.getKontonummer(), "ADARLFLUG", ivInstitutsnummer));
        		}
        		catch (Exception exp)
        		{
        			LOGGER_DARKA.error("Verarbeitungsfehler Sicherheit Flugzeug - Konto " + ivZielDarlehen.getKontonummer() + StringKonverter.lineSeparator);
        			LOGGER_DARKA.error(exp.getMessage());
        			exp.printStackTrace();        		
        		}
        	}
        }
 
        if (lvOkayDarlehen)
         {
            ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
         }
        
        if (lvOkayDarlehen)
        {
        	// Finanzierung hinzufuegen, wenn Objektnummer in der Liste ist
        	//System.out.println("Start Finanzierung...");
        	//System.out.println("Objektnummer: " + ivZielDarlehen.getObjektnummer());
        	ObjektElement lvHelpObjektElement = ivListeObjekte.getObjekt(ivZielDarlehen.getObjektnummer());
        	if (lvHelpObjektElement != null)
        	{
        		//System.out.println("vorhanden...");
        		if (lvHelpObjektElement.getQuellsystem().equals("DarKa"))
        		{
        			// TXSFinanzgeschaeft setzen --> eigentlich Finanzierung!!!
        			TXSFinanzgeschaeft lvFinanzierung = new TXSFinanzgeschaeft();
        			lvFinanzierung.setKey(lvHelpObjektElement.getFinanzierungExternerKey());
        			lvFinanzierung.setOriginator(ValueMapping.changeMandant(ivZielDarlehen.getInstitutsnummer()));
        			lvFinanzierung.setQuelle("TXS");           

        			// TXSFinanzierungKredit setzen
        			TXSFinanzierungKredit lvFinkred = new TXSFinanzierungKredit();
        			lvFinkred.setKey(ivZielDarlehen.getKontonummer());
        			lvFinkred.setOriginator(ValueMapping.changeMandant(ivZielDarlehen.getInstitutsnummer()));
 
        			switch (ivModus)
        			{
                    case DarlehenVerarbeiten.DARKA:
                        lvFinkred.setQuelle("ADARLPFBG");
                       break;
                    case DarlehenVerarbeiten.FLUGZEUGE:
                        lvFinkred.setQuelle("ADARLFLUG");
                       break;
                    case DarlehenVerarbeiten.SCHIFFE:
                        lvFinkred.setQuelle("ADARLSCHF");
                       break;
                    case DarlehenVerarbeiten.OEPG:
                        lvFinkred.setQuelle("ADARLOEPG");
                       break;
                    case DarlehenVerarbeiten.ALT:
                        lvFinkred.setQuelle("ADARLPFBG");
                        break;
                    case DarlehenVerarbeiten.DPP:
                        lvFinkred.setQuelle("DPP");
                        break;
                    default:
                        System.out.println("TXSFinanzierungKredit: Unbekannter Modus");
        			}        
                
        			// TXSFinanzierungKredit
        			ivOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionStart());
        			ivOutputDarlehenXML.printTransaktion(lvFinkred.printTXSTransaktionStart());
        			ivOutputDarlehenXML.printTransaktion(lvFinkred.printTXSTransaktionEnde());
        			ivOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionEnde());
        		}
        	}
        }
        
        if (lvOkayDarlehen)
        {
        	// Bei fuer Deckung vorgesehenen Krediten eine Default-Finanzierung anlegen
        	// nur Schiffe und Flugzeuge
        	if (ivModus == DarlehenVerarbeiten.SCHIFFE || ivModus == DarlehenVerarbeiten.FLUGZEUGE)
        	{
        		if (ivZielDarlehen.getAusplatzierungsmerkmal().equals("S0") || ivZielDarlehen.getAusplatzierungsmerkmal().equals("F0"))
        		{
        			// TXSFinanzgeschaeft setzen --> eigentlich Finanzierung!!!
        			TXSFinanzgeschaeft lvFinanzierung = new TXSFinanzgeschaeft();
        			lvFinanzierung.setKey(ivZielDarlehen.getObjektnummer());
        			lvFinanzierung.setOriginator(ValueMapping.changeMandant(ivZielDarlehen.getInstitutsnummer()));
        			lvFinanzierung.setQuelle("TXS");           
            
        			// TXSFinanzgeschaeftDaten setzen
        			TXSFinanzgeschaeftDaten lvFgdaten = new TXSFinanzgeschaeftDaten();
        			lvFgdaten.setAz(ivZielDarlehen.getObjektnummer());
        			lvFgdaten.setAktivpassiv("1");
        			lvFgdaten.setKat("8");
        			if (ivModus == DarlehenVerarbeiten.SCHIFFE)
        			{
        				lvFgdaten.setTyp("70");
        			}
        			if (ivModus == DarlehenVerarbeiten.FLUGZEUGE)
        			{
        				lvFgdaten.setTyp("71");
        			}
            
        			// TXSKonditionenDaten setzen
        			TXSKonditionenDaten lvKond = new TXSKonditionenDaten();
        			lvKond.setKondkey(ivZielDarlehen.getObjektnummer());
                   
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
     }
}
