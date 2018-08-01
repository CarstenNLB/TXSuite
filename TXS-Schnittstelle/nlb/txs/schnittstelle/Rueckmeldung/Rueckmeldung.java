/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Rueckmeldung;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import nlb.txs.schnittstelle.MAVIS.RueckmeldeDatenMAVIS;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.ObjekteListe;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author tepperc
 *
 */
public class Rueckmeldung 
{
    /**
     * Wird zum Einlesen der TXS-Rueckmeldung per JDOM benoetigt
     */
    private Document ivDocument;
       
    /**
     * Liste der abgegangenen Kredite
     */
    private ObjekteListe ivListeAbgangKredite;
    
    /**
     * Liste der abgegangenen Sicherheiten
     */
    private ObjekteListe ivListeAbgangSicherheiten;
    
    /**
     * Liste der nicht-TXS Ausplatzierungsmerkmale DarKa
     */
    private ObjekteListe ivListeAusplatzierungsmerkmalDarKa;
    
    /**
     * Liste der nicht-TXS Ausplatzierungsmerkmale LoanIQ
     */
    private ObjekteListe ivListeAusplatzierungsmerkmalLoanIQ; 
    
    /**
     * Liste der LoanIQObjekte
     */
    private HashMap<String, LoanIQObjekt> ivListeLoanIQObjekte;
    
    /**
     * Liste der RueckmeldungDarKaObjekte
     */
    private HashSet<RueckmeldungDarKaObjekt> ivListeRueckmeldungDarKaObjekte;
    
    /**
     * Logger - Verwendung von log4j
     */
    private static Logger LOGGER = Logger.getLogger("TXSRueckmeldungLogger");
    
    /**
     * Startmethode
     * @param argv 
     */
    public static void main(String[] argv) 
    {
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("Rueckmeldung <ini-Datei>");
            System.exit(1);
        }
        else
        {
            IniReader lvReader = null;
            try 
            {
                lvReader = new IniReader(argv[argv.length - 1]);
            }
            catch (Exception exp)
            {
                System.out.println("Fehler beim Laden der ini-Datei...");
                System.exit(1);
            }
            if (lvReader != null)
            {                
                String lvLoggingXML = lvReader.getPropertyString("Rueckmeldung", "log4jconfig", "Fehler");
                if (lvLoggingXML.equals("Fehler"))
                {
                  System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
                }
                else
                {
                    DOMConfigurator.configure(lvLoggingXML); 
                }            
                
                String lvInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
                if (lvInstitut.equals("Fehler"))
                {
                	LOGGER.error("Kein [Konfiguration][Institut] in der ini-Datei...");
                	System.exit(1);
                }
                else
                {
                    LOGGER.info("Institut=" + lvInstitut);                    
                }
                
                String lvDaypointerFileout = lvReader.getPropertyString("Konfiguration", "DPFILE", "Fehler");
                if (lvDaypointerFileout.equals("Fehler"))
                {
                    LOGGER.error("Kein [Konfiguration][DPFILE] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("DPFILE=" + lvDaypointerFileout);                    
                }
                                        
                String lvImportVerzeichnis = lvReader.getPropertyString("Rueckmeldung", "ImportVerzeichnis", "Fehler");
                if (lvImportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][ImportVerzeichnis] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("ImportVerzeichnis=" + lvImportVerzeichnis);                    
                }

                String lvExportVerzeichnis = lvReader.getPropertyString("Rueckmeldung", "ExportVerzeichnis", "Fehler");
                if (lvExportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][ExportVerzeichnis] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("ExportVerzeichnis=" + lvExportVerzeichnis);                    
                }


                String lvRueckmeldungTXS =  lvReader.getPropertyString("Rueckmeldung", "RueckmeldungTXS", "Fehler");
                if (lvRueckmeldungTXS.equals("Fehler"))
                {
                	LOGGER.error("Kein [Rueckmeldung][RueckmeldungTXS] in der ini-Datei...");
                	System.exit(1);
                }
                else
                {
                    LOGGER.info("RueckmeldungTXS=" + lvRueckmeldungTXS);                    
                }
 
                String lvRueckmeldungDarlehen_NLB =  lvReader.getPropertyString("Rueckmeldung", "RueckmeldungDarlehen_NLB", "Fehler");
                if (lvRueckmeldungDarlehen_NLB.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][RueckmeldungDarlehen_NLB] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("RueckmeldungDarlehen_NLB=" + lvRueckmeldungDarlehen_NLB);                    
                }

                String lvRueckmeldungDarlehen_BLB =  lvReader.getPropertyString("Rueckmeldung", "RueckmeldungDarlehen_BLB", "Fehler");
                if (lvRueckmeldungDarlehen_BLB.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][RueckmeldungDarlehen_BLB] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("RueckmeldungDarlehen_BLB=" + lvRueckmeldungDarlehen_BLB);                    
                }

                
                String lvRueckmeldungLoanIQ =  lvReader.getPropertyString("Rueckmeldung", "RueckmeldungLoanIQ", "Fehler");
                if (lvRueckmeldungLoanIQ.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][RueckmeldungLoanIQ] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("RueckmeldungLoanIQ=" + lvRueckmeldungLoanIQ);                    
                }

                String lvRueckmeldungSAPCMS_NLB =  lvReader.getPropertyString("Rueckmeldung", "RueckmeldungSAPCMS_NLB", "Fehler");
                if (lvRueckmeldungSAPCMS_NLB.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][RueckmeldungSAPCMS_NLB] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("RueckmeldungSAPCMS_NLB=" + lvRueckmeldungSAPCMS_NLB);                    
                }

                String lvRueckmeldungSAPCMS_BLB =  lvReader.getPropertyString("Rueckmeldung", "RueckmeldungSAPCMS_BLB", "Fehler");
                if (lvRueckmeldungSAPCMS_BLB.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][RueckmeldungSAPCMS_BLB] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("RueckmeldungSAPCMS_BLB=" + lvRueckmeldungSAPCMS_BLB);                    
                }
                
                String lvRueckmeldungInMAVIS_NLB =  lvReader.getPropertyString("Rueckmeldung", "RueckmeldungInMAVIS_NLB", "Fehler");
                if (lvRueckmeldungInMAVIS_NLB.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][RueckmeldungInMAVIS_NLB] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("RueckmeldungInMAVIS_NLB=" + lvRueckmeldungInMAVIS_NLB);                    
                }

                String lvRueckmeldungInMAVIS_BLB =  lvReader.getPropertyString("Rueckmeldung", "RueckmeldungInMAVIS_BLB", "Fehler");
                if (lvRueckmeldungInMAVIS_BLB.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][RueckmeldungInMAVIS_BLB] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("RueckmeldungInMAVIS_BLB=" + lvRueckmeldungInMAVIS_BLB);                    
                }
                
                String lvRueckmeldungOutMAVIS_NLB =  lvReader.getPropertyString("Rueckmeldung", "RueckmeldungOutMAVIS_NLB", "Fehler");
                if (lvRueckmeldungOutMAVIS_NLB.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][RueckmeldungOutMAVIS_NLB] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("RueckmeldungOutMAVIS_NLB=" + lvRueckmeldungOutMAVIS_NLB);                    
                }
                
                String lvRueckmeldungOutMAVIS_BLB =  lvReader.getPropertyString("Rueckmeldung", "RueckmeldungOutMAVIS_BLB", "Fehler");
                if (lvRueckmeldungOutMAVIS_BLB.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][RueckmeldungOutMAVIS_BLB] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("RueckmeldungOutMAVIS_BLB=" + lvRueckmeldungOutMAVIS_BLB);                    
                }
                
                // wird fuer LoanIQ benoetigt
                String lvVerbuergtKonsortial = lvReader.getPropertyString("Rueckmeldung", "VerbuergtKonsortialDatei", "Fehler");
                if (lvVerbuergtKonsortial.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][VerbuergtKonsortialDatei] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("VerbuergtKonsortialDatei=" + lvVerbuergtKonsortial);                    
                }

                // AusplatzierungsmerkmalDarKa-Datei enthaelt die nicht relevanten Ausplatzierungsmerkmale aus DarKa
                String lvAusplatzierungsmerkmalDarKaDatei = lvReader.getPropertyString("Rueckmeldung", "AusplatzierungsmerkmalDarKa-Datei", "Fehler");
                if (lvAusplatzierungsmerkmalDarKaDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][AusplatzierungsmerkmalDarKa-Datei] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("AusplatzierungsmerkmalDarKa-Datei=" + lvAusplatzierungsmerkmalDarKaDatei);                    
                }
                
                // AusplatzierungsmerkmalLoanIQ-Datei enthaelt die nicht relevanten Ausplatzierungsmerkmale aus LoanIQ
                String lvAusplatzierungsmerkmalLoanIQDatei = lvReader.getPropertyString("Rueckmeldung", "AusplatzierungsmerkmalLoanIQ-Datei", "Fehler");
                if (lvAusplatzierungsmerkmalLoanIQDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][AusplatzierungsmerkmalLoanIQ-Datei] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("AusplatzierungsmerkmalLoanIQ-Datei=" + lvAusplatzierungsmerkmalLoanIQDatei);                    
                }

                // Abgangsliste-Kredite
                String lvAbgangslisteKredite = lvReader.getPropertyString("Rueckmeldung", "Abgangsliste-Kredite", "Fehler");
                if (lvAbgangslisteKredite.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][Abgangsliste-Kredite] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("Abgangsliste-Kredite=" + lvAbgangslisteKredite);                    
                }
                
                // Abgangsliste-Sicherheiten
                String lvAbgangslisteSicherheiten = lvReader.getPropertyString("Rueckmeldung", "Abgangsliste-Sicherheiten", "Fehler");
                if (lvAbgangslisteSicherheiten.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][Abgangsliste-Sicherheiten] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("Abgangsliste-Sicherheiten=" + lvAbgangslisteSicherheiten);                    
                }

                // Bagatellgrenze
                String lvBagatellgrenze = lvReader.getPropertyString("Rueckmeldung", "Bagatellgrenze", "Fehler");
                if (lvBagatellgrenze.equals("Fehler"))
                {
                    LOGGER.error("Kein [Rueckmeldung][Bagatellgrenze] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("Bagatellgrenze=" + lvBagatellgrenze);                    
                }

                new Rueckmeldung(lvInstitut, lvImportVerzeichnis, lvExportVerzeichnis, lvRueckmeldungTXS, lvRueckmeldungDarlehen_NLB, lvRueckmeldungDarlehen_BLB,
                		         lvRueckmeldungLoanIQ, lvRueckmeldungSAPCMS_NLB, lvRueckmeldungSAPCMS_BLB, lvRueckmeldungInMAVIS_NLB, lvRueckmeldungInMAVIS_BLB,
                		         lvRueckmeldungOutMAVIS_NLB, lvRueckmeldungOutMAVIS_BLB, lvVerbuergtKonsortial, lvAbgangslisteKredite, lvAbgangslisteSicherheiten,
                		         lvAusplatzierungsmerkmalDarKaDatei, lvAusplatzierungsmerkmalLoanIQDatei, lvBagatellgrenze, lvDaypointerFileout);
                
             }
        }
        System.exit(0);
    }
    
    /**
     * Konstruktor
     * @param pvInstitut 
     * @param pvImportVerzeichnis 
     * @param pvExportVerzeichnis 
     * @param pvRueckmeldungTXS
     * @param pvRueckmeldungDarlehen_NLB
     * @param pvRueckmeldungDarlehen_BLB 
     * @param pvRueckmeldungLoanIQ 
     * @param pvRueckmeldungSAPCMS_NLB
     * @param pvRueckmeldungSAPCMS_BLB
     * @param pvRueckmeldungInMAVIS_NLB
     * @param pvRueckmeldungInMAVIS_BLB
     * @param pvRueckmeldungOutMAVIS_NLB
     * @param pvRueckmeldungOutMAVIS_BLB
     * @param pvVerbuergtKonsortial
     * @param pvAbgangslisteKredite
     * @param pvAbgangslisteSicherheiten
     * @param pvAusplatzierungsmerkmalDarKaDatei
     * @param pvAusplatzierungsmerkmalLoanIQDatei
     * @param pvBagatellgrenze
     * @param pvDaypointerFileout
     */
    public Rueckmeldung(String pvInstitut, String pvImportVerzeichnis, String pvExportVerzeichnis, String pvRueckmeldungTXS, 
                        String pvRueckmeldungDarlehen_NLB, String pvRueckmeldungDarlehen_BLB, String pvRueckmeldungLoanIQ, 
                        String pvRueckmeldungSAPCMS_NLB, String pvRueckmeldungSAPCMS_BLB, String pvRueckmeldungInMAVIS_NLB, 
                        String pvRueckmeldungInMAVIS_BLB, String pvRueckmeldungOutMAVIS_NLB, String pvRueckmeldungOutMAVIS_BLB,
                        String pvVerbuergtKonsortial, String pvAbgangslisteKredite, String pvAbgangslisteSicherheiten,
                        String pvAusplatzierungsmerkmalDarKaDatei, String pvAusplatzierungsmerkmalLoanIQDatei, String pvBagatellgrenze, String pvDaypointerFileout)
    {
        LOGGER.info("Start der Rueckmeldung");

        LOGGER.info("Abgangsliste-Sicherheiten:");
        ivListeAbgangSicherheiten = new ObjekteListe(pvImportVerzeichnis + "\\" + pvAbgangslisteSicherheiten, LOGGER);
        ivListeAbgangSicherheiten.leseObjekteListe();
        
        LOGGER.info("Abgangsliste-Kredite:");
        ivListeAbgangKredite = new ObjekteListe(pvImportVerzeichnis + "\\" + pvAbgangslisteKredite, LOGGER);
        ivListeAbgangKredite.leseObjekteListe();

        // Liste der nicht-TXS Ausplatzierungsmerkmale aus DarKa einlesen
        LOGGER.info("Ausplatzierungsmerkmal DarKa:");
        ivListeAusplatzierungsmerkmalDarKa = new ObjekteListe(pvImportVerzeichnis + "\\" + pvAusplatzierungsmerkmalDarKaDatei, LOGGER);
        ivListeAusplatzierungsmerkmalDarKa.leseObjekteListe();

        // Liste der nicht-TXS Ausplatzierungsmerkmale aus LoanIQ einlesen - CT 04.09.2017
        LOGGER.info("Ausplatzierungsmerkmal LoanIQ:");
        ivListeAusplatzierungsmerkmalLoanIQ = new ObjekteListe(pvImportVerzeichnis + "\\" + pvAusplatzierungsmerkmalLoanIQDatei, LOGGER);
        ivListeAusplatzierungsmerkmalLoanIQ.leseObjekteListe();

        // Liste der RueckmeldungDarKaObjekte einlesen - CT 13.12.2017
        //ivListeRueckmeldungDarKaObjekte = new HashSet<RueckmeldungDarKaObjekt>();
        //leseRueckmeldungDarKaObjekte("C:\\CT\\Rueckmeldung\\PfandBG_Rueckmeldung_DarKa_2017-12-13_114745.txt");
        
        String lvZeile = null;
        int lvAnzahlLoanIQObjekte = 0;   
        ivListeLoanIQObjekte = new HashMap<String, LoanIQObjekt>();
        
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File lvVerbuergtKonsortial = new File(pvImportVerzeichnis + "\\" + pvVerbuergtKonsortial);
        try
        {
        	lvFis = new FileInputStream(lvVerbuergtKonsortial);
        }
        catch (Exception e)
        {
        	LOGGER.error("Konnte VerbuergtKonsortial-Datei nicht oeffnen!");
        	return;
        }
        
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
            
        LoanIQObjekt lvLoanIQObjekt = null;
        try
        {
        	while ((lvZeile = lvIn.readLine()) != null)  // Lesen LoanIQ-Datei
        	{
        		lvLoanIQObjekt = new LoanIQObjekt();
        		if (!lvLoanIQObjekt.parseLoanIQObjekt(lvZeile)) // Parsen der Felder
        		{    
        			LOGGER.error("Fehler beim Einlesen einer Zeile aus der VerbuergtKonsortial-Datei");
        			LOGGER.error("Zeile: " + lvZeile);
        		}
        		ivListeLoanIQObjekte.put(lvLoanIQObjekt.getKontonummer(), lvLoanIQObjekt);
        		lvAnzahlLoanIQObjekte++;
            }
        }
        catch (Exception e)
        {
        	LOGGER.error("Fehler beim Verarbeiten einer Zeile!");
        	e.printStackTrace();
        }
                 
        try
        {
        	lvIn.close();
        }
        catch (Exception e)
        {
        	LOGGER.error("Konnte VerbuergtKonsortial-Datei nicht schliessen!");
        } 
        LOGGER.info(lvAnzahlLoanIQObjekte + " LoanIQObjekte eingelesen...");
        
        
        // Geschaefte aus LoanIQ nur einmal mit (N,99) zurueckmelden
        //ObjekteListe lvKontonummernListe = new ObjekteListe(pvImportVerzeichnis + "\\RueckmeldungKontonummernLoanIQ.txt", LOGGER);
        //lvKontonummernListe.leseObjekteListe();
        
        String lvFilenameIn  = pvImportVerzeichnis + "\\" + pvRueckmeldungTXS;      
        String lvFilenameOutDarlehen_NLB = pvExportVerzeichnis + "\\" + pvRueckmeldungDarlehen_NLB;        
        String lvFilenameOutDarlehen_BLB = pvExportVerzeichnis + "\\" + pvRueckmeldungDarlehen_BLB;        
        String lvFilenameOutLoanIQ = pvExportVerzeichnis + "\\" + pvRueckmeldungLoanIQ;
        String lvFilenameOutSAPCMS_NLB = pvExportVerzeichnis + "\\" + pvRueckmeldungSAPCMS_NLB;
        String lvFilenameOutSAPCMS_BLB = pvExportVerzeichnis + "\\" + pvRueckmeldungSAPCMS_BLB;
        String lvFilenameInMAVIS_NLB = pvRueckmeldungInMAVIS_NLB;
        String lvFilenameInMAVIS_BLB = pvRueckmeldungInMAVIS_BLB;
        String lvFilenameOutMAVIS_NLB = pvExportVerzeichnis + "\\" + pvRueckmeldungOutMAVIS_NLB;
        String lvFilenameOutMAVIS_BLB = pvExportVerzeichnis + "\\" + pvRueckmeldungOutMAVIS_BLB;
        		
        // Buchungsdatum einlesen aus DPFILE
        String lvBuchungsdatum = leseBuchungsdatum(pvDaypointerFileout);
        
        Runtime lvRuntime = Runtime.getRuntime();
        
        LOGGER.info("Used Memory vor dem Lesen:"
                + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
        
        boolean lvOkay = leseRueckmeldedaten(lvFilenameIn);

        LOGGER.info("Used Memory nach dem Lesen:"
                + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));

        if (lvOkay)
        {
          // Rueckmeldung DarKa  
          schreibeRueckmeldungDarlehen(lvFilenameOutDarlehen_NLB, lvFilenameOutDarlehen_BLB);
          
          // Rueckmeldung LoanIQ - CT 17.09.2014
          schreibeRueckmeldungLoanIQ(lvFilenameOutLoanIQ, pvBagatellgrenze);
                 
          // Rueckmeldung SAP CMS
          // Neue Variante - CT 10.08.2012
          if (hasShPo())
          {
        	  LOGGER.info("Attribute 'shpo' existiert...");
        	  schreibeRueckmeldungSAPCMS(lvFilenameOutSAPCMS_NLB, lvFilenameOutSAPCMS_BLB);
          }
          else
          {
        	  LOGGER.info("Attribute 'shpo' fehlt --> Keine Rueckmeldung an SAP CMS");
          }
          
          // Rueckmeldung MAVIS
          schreibeRueckmeldungMAVIS(lvFilenameOutMAVIS_NLB, lvFilenameOutMAVIS_BLB, lvFilenameInMAVIS_NLB, lvFilenameInMAVIS_BLB, lvBuchungsdatum);
        }
        
        // CT 10.08.2012 - Workaround
        // Liste der abgegangenen Sicherheiten rausschreiben 
        LOGGER.info("Abgangsliste-Sicherheiten:");
        ivListeAbgangSicherheiten.schreibeObjekteListe();
        
        // Liste der abgegangenen Kredite rausschreiben
        LOGGER.info("Abgangsliste-Kredite:");
        ivListeAbgangKredite.schreibeObjekteListe();
                
        // Liste der abgegangenen Darlehen rausschreiben - CT 30.01.2014        
        //lvKontonummernListe.schreibeObjekteListe();
        
        LOGGER.info("Used Memory nach dem Schreiben:"
                + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
        
        LOGGER.info("Ende der Rueckmeldung");
    }
    
    /**
     * Einlesen des Buchungsdatum aus dem DPFILE
     * @param pvDaypointerFileout
     */
    private String leseBuchungsdatum(String pvDaypointerFileout)
    {
    	// Einlesen des Buchungsdatum
        String lvBuchungsdatum = new String();
    	File ivFileDaypointer = new File(pvDaypointerFileout);
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
            LOGGER.info("Konnte Datei '" + pvDaypointerFileout + "' nicht oeffnen!");
          }
          try
          {
            lvBuchungsdatum = lvDaypointerBr.readLine();  
          }
          catch (Exception e)
          {
              LOGGER.error("Fehler beim Verarbeiten:" + lvBuchungsdatum);
              e.printStackTrace();
          }
          try
          {
            lvDaypointerBr.close();
            lvDaypointerFis.close();
          }
          catch (Exception e)
          {
              LOGGER.error("Konnte Datei '" + pvDaypointerFileout + "' nicht schliessen!");
          }
        }
        else
        {
            LOGGER.info("Keine Datei '" + pvDaypointerFileout + "' gefunden...");
        }
        LOGGER.info("Buchungsdatum: " + lvBuchungsdatum);

        return lvBuchungsdatum;
    }
    
    /**
     * Liest die von TXS gelieferten Rueckmeldedaten ein
     * @param pvFilename Dateiname der von TXS gelieferten Rueckmeldedaten
     */
    private boolean leseRueckmeldedaten(String pvFilename)
    {
        // Zur Eingabe
        SAXBuilder lvBuilder = null;
        lvBuilder = new SAXBuilder();
        lvBuilder.setExpandEntities(true);
          
        try
        {
            ivDocument = (Document)lvBuilder.build(pvFilename);
        }
        catch (IOException io)
        {
            LOGGER.error(io.getMessage());
            return false;
        }
        catch (JDOMException jdomex)
        {
            LOGGER.error(jdomex.getMessage());
            return false;
         }
          
       return true;
    }
  
    
    /**
     * Schreibt die Rueckmeldedatei fuer Darlehen
     * @param pvFilenameOut_NLB
     * @param pvFilenameOut_BLB
     */
    private void schreibeRueckmeldungDarlehen(String pvFilenameOut_NLB, String pvFilenameOut_BLB)
    {     
        // Liste der Darlehen
        HashMap<String, DarlehenObjekt> lvListeDarlehen = new HashMap<String, DarlehenObjekt>();

        OutputRueckmeldung lvOutRueck_NLB = new OutputRueckmeldung(pvFilenameOut_NLB);
        OutputRueckmeldung lvOutRueck_BLB = new OutputRueckmeldung(pvFilenameOut_BLB);
        lvOutRueck_NLB.open();
        lvOutRueck_BLB.open();
        String lvRueckmeldeZeile;
        
        Element lvRootNode = ivDocument.getRootElement();
        
        List<Element> lvListSlice = lvRootNode.getChildren();
        LOGGER.info("Anzahl Datensaetze: " + lvListSlice.size());
        
        int lvZaehler_NLB = 0;
        int lvZaehler_BLB = 0;
        DarlehenObjekt lvDarlehenObjekt;
        
        for (int x = 0; x < lvListSlice.size(); x++)
        {
            Element lvNodeSlice = (Element)lvListSlice.get(x);  
            // Deckungsstatus ermitteln
            // Ueber Ausplatzierungsmerkmal...
            String lvDeckungsstatus = ermittleDeckungsstatus(lvNodeSlice.getAttributeValue("ausplmerkmal"), lvNodeSlice.getAttributeValue("bis"), lvNodeSlice.getAttributeValue("quelle"));
            
            // Ueber Assigned und Blacklist... - alte Variante 
            //String lvDeckungsstatus = ermittleDeckungsstatus(lvNodeSlice.getAttributeValue("ass"), lvNodeSlice.getAttributeValue("black"));
            
            // Repo-Flag
            //String lvRepoFlag = lvNodeSlice.getAttributeValue("repo");
            
            // Nliqui-Kennzeichen
            //String lvNliqui = lvNodeSlice.getAttributeValue("nliqui");
            
            // Wenn Repo-Flag -> 1 und Nliqui-Kennzeichen -> true ist, dann muss das Deckungskennzeichen '5' oder '6' sein
            // In dem Fall keine Rueckmeldung an Darlehen - CT 15.11.2013
            // Hier muesste man auch das passende Deckungskennzeichen zurueckmelden - Noch einbauen - CT 22.05.2014
            //if (lvRepoFlag.equals("1") && lvNliqui.equals("true"))
            //{
            //    LOGGER.info("Repo-Flag -> 1 und Nliqui -> true: " + lvNodeSlice.getAttributeValue("key"));
            //}
            //else
            //{
            if (lvDeckungsstatus.equals("S") || lvDeckungsstatus.equals("U") ||
                lvDeckungsstatus.equals("D") || lvDeckungsstatus.equals("N") ||
                lvDeckungsstatus.equals("A") || lvDeckungsstatus.equals("0") ||
                lvDeckungsstatus.equals("1") || lvDeckungsstatus.equals("2") ||
                lvDeckungsstatus.equals("3") || lvDeckungsstatus.equals("4") ||
                lvDeckungsstatus.equals("5") || lvDeckungsstatus.equals("6") ||
                lvDeckungsstatus.equals("7") || lvDeckungsstatus.equals("8") ||
                lvDeckungsstatus.equals("9"))
                {
                   	if (lvNodeSlice.getAttributeValue("quelle").startsWith("ADARL") ||
                		lvNodeSlice.getAttributeValue("quelle").startsWith("ADAWP") ||
                        lvNodeSlice.getAttributeValue("quelle").startsWith("PDARL"))
                    {
                        String lvHelpKey = lvNodeSlice.getAttributeValue("key");
                        if (lvNodeSlice.getAttributeValue("key").contains("_"))
                        {
                            lvHelpKey = lvNodeSlice.getAttributeValue("key").substring(lvNodeSlice.getAttributeValue("key").indexOf("_") + 1);
                        }

                        // nicht-TXS Ausplatzierungsmerkmal darf nicht zurueckgemeldet werden
                        if (!ivListeAusplatzierungsmerkmalDarKa.contains(lvHelpKey))
                        {
                          if (lvListeDarlehen.containsKey(lvHelpKey))
                          {
                            lvDarlehenObjekt = lvListeDarlehen.get(lvHelpKey);
                            if (!lvDeckungsstatus.equals("N"))
                            {
                                if (!lvDarlehenObjekt.getDeckungsstatus().equals(lvDeckungsstatus))
                                {       
                                    lvDarlehenObjekt.setAusplatzierungsmerkmal(lvNodeSlice.getAttributeValue("ausplmerkmal"));
                                    lvDarlehenObjekt.setDeckungsstatus(lvDeckungsstatus);
                                    // Quellsystem muss auch geaendert werden
                                    lvDarlehenObjekt.setQuellsystem(lvNodeSlice.getAttributeValue("quelle"));
                                }
                            }
                          }
                          else
                          {
                              if (lvHelpKey.length() == 10)
                              {
                                  lvDarlehenObjekt = new DarlehenObjekt(lvNodeSlice.getAttributeValue("ausplmerkmal"), lvDeckungsstatus, lvNodeSlice.getAttributeValue("org"), lvNodeSlice.getAttributeValue("quelle"), lvNodeSlice.getAttributeValue("bis"));
                                  lvListeDarlehen.put(lvHelpKey, lvDarlehenObjekt);
                              }
                          }
                        }
                    }
                }
        }

        /* for (RueckmeldungDarKaObjekt lvRueckmeldungDarKaObjekt:ivListeRueckmeldungDarKaObjekte)
        {       	
        	// Deckungsstatus ueber Ausplatzierungsmerkmal ermitteln
            String lvDeckungsstatus = ermittleDeckungsstatus(lvRueckmeldungDarKaObjekt.getAusplatzierungsmerkmal(), lvRueckmeldungDarKaObjekt.getBetragBis(), lvRueckmeldungDarKaObjekt.getQuelle());
            
            // Ueber Assigned und Blacklist... - alte Variante 
            //String lvDeckungsstatus = ermittleDeckungsstatus(lvNodeSlice.getAttributeValue("ass"), lvNodeSlice.getAttributeValue("black"));
            
            // Repo-Flag
            //String lvRepoFlag = lvNodeSlice.getAttributeValue("repo");
            
            // Nliqui-Kennzeichen
            //String lvNliqui = lvNodeSlice.getAttributeValue("nliqui");
            
            // Wenn Repo-Flag -> 1 und Nliqui-Kennzeichen -> true ist, dann muss das Deckungskennzeichen '5' oder '6' sein
            // In dem Fall keine Rueckmeldung an Darlehen - CT 15.11.2013
            // Hier muesste man auch das passende Deckungskennzeichen zurueckmelden - Noch einbauen - CT 22.05.2014
            //if (lvRepoFlag.equals("1") && lvNliqui.equals("true"))
            //{
            //    LOGGER.info("Repo-Flag -> 1 und Nliqui -> true: " + lvNodeSlice.getAttributeValue("key"));
            //}
            //else
            //{
            if (lvDeckungsstatus.equals("S") || lvDeckungsstatus.equals("U") ||
                lvDeckungsstatus.equals("D") || lvDeckungsstatus.equals("N") ||
                lvDeckungsstatus.equals("A") || lvDeckungsstatus.equals("0") ||
                lvDeckungsstatus.equals("1") || lvDeckungsstatus.equals("2") ||
                lvDeckungsstatus.equals("3") || lvDeckungsstatus.equals("4") ||
                lvDeckungsstatus.equals("5") || lvDeckungsstatus.equals("6") ||
                lvDeckungsstatus.equals("7") || lvDeckungsstatus.equals("8") ||
                lvDeckungsstatus.equals("9"))
                {
                   	if (lvRueckmeldungDarKaObjekt.getQuelle().startsWith("ADARL") ||
                   		lvRueckmeldungDarKaObjekt.getQuelle().startsWith("ADAWP") ||
                   		lvRueckmeldungDarKaObjekt.getQuelle().startsWith("PDARL"))
                    {
                        String lvHelpKey = lvRueckmeldungDarKaObjekt.getAktenzeichen();

                        // nicht-TXS Ausplatzierungsmerkmal darf nicht zurueckgemeldet werden
                        if (!ivListeAusplatzierungsmerkmalDarKa.contains(lvHelpKey))
                        {
                          if (lvListeDarlehen.containsKey(lvHelpKey))
                          {
                            lvDarlehenObjekt = lvListeDarlehen.get(lvHelpKey);
                            if (!lvDeckungsstatus.equals("N"))
                            {
                                if (!lvDarlehenObjekt.getDeckungsstatus().equals(lvDeckungsstatus))
                                {       
                                    lvDarlehenObjekt.setAusplatzierungsmerkmal(lvRueckmeldungDarKaObjekt.getAusplatzierungsmerkmal());
                                    lvDarlehenObjekt.setDeckungsstatus(lvDeckungsstatus);
                                    // Quellsystem muss auch geaendert werden
                                    lvDarlehenObjekt.setQuellsystem(lvRueckmeldungDarKaObjekt.getQuelle());
                                }
                            }
                          }
                          else
                          {
                              if (lvHelpKey.length() == 10)
                              {
                                  lvDarlehenObjekt = new DarlehenObjekt(lvRueckmeldungDarKaObjekt.getAusplatzierungsmerkmal(), lvDeckungsstatus, lvRueckmeldungDarKaObjekt.getOriginator(), lvRueckmeldungDarKaObjekt.getQuelle(), lvRueckmeldungDarKaObjekt.getBetragBis());
                                  lvListeDarlehen.put(lvHelpKey, lvDarlehenObjekt);
                              }
                          }
                        }
                    }
                }
        } */
        
        Iterator<Entry<String, DarlehenObjekt>> iterListeDarlehen = lvListeDarlehen.entrySet().iterator();
        while (iterListeDarlehen.hasNext())
        {
            Entry<String, DarlehenObjekt> lvHelpEntry = (Entry<String, DarlehenObjekt>)iterListeDarlehen.next();
            DarlehenObjekt lvHelpDarlehenObjekt = lvHelpEntry.getValue(); 
            
            // Ausplatzierungsmerkmal != 99 und in der Liste der abgegangen Kredite, dann aus der Liste loeschen
            if (!lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().equals("99") && ivListeAbgangKredite.contains(lvHelpEntry.getKey()))
            {
            	ivListeAbgangKredite.remove(lvHelpEntry.getKey());
            }
           
            if (!ivListeAbgangKredite.contains(lvHelpEntry.getKey()))
            {
            	lvRueckmeldeZeile = "STX0502" + lvHelpEntry.getKey() + ";";
            
            	if (lvHelpDarlehenObjekt.getOriginator().equals("25050000"))
            	{
            		lvRueckmeldeZeile = lvRueckmeldeZeile + "009;";
            	}
            	if (lvHelpDarlehenObjekt.getOriginator().equals("29050000"))
            	{
            		lvRueckmeldeZeile = lvRueckmeldeZeile + "004;";                        
            	}
            
            	// Das Deckungskennzeichen fuer OEPG ist immer 'A'
            	if ((lvHelpDarlehenObjekt.getQuellsystem().equals("ADARLOEPG") || lvHelpDarlehenObjekt.getQuellsystem().equals("PDARLOEPG")) 
            		 && lvHelpDarlehenObjekt.getDeckungsstatus().equals("D"))
            	{
            		lvHelpDarlehenObjekt.setDeckungsstatus("A");
            	}
            
            	if ((lvHelpDarlehenObjekt.getQuellsystem().equals("ADARLSCHF") || lvHelpDarlehenObjekt.getQuellsystem().equals("PDARLSCHF")) 
                     && lvHelpDarlehenObjekt.getDeckungsstatus().equals("D"))
            	{
            		lvHelpDarlehenObjekt.setDeckungsstatus("S");
            	}
            
            	if ((lvHelpDarlehenObjekt.getQuellsystem().equals("ADARLFLUG") || lvHelpDarlehenObjekt.getQuellsystem().equals("PDARLFLUG")) 
                     && lvHelpDarlehenObjekt.getDeckungsstatus().equals("D"))
            	{
            		lvHelpDarlehenObjekt.setDeckungsstatus("U");
            	}
            
            	lvRueckmeldeZeile = lvRueckmeldeZeile + lvHelpDarlehenObjekt.getDeckungsstatus() + ";";
        
            	String lvAusplatzierungsmerkmal = lvHelpDarlehenObjekt.getAusplatzierungsmerkmal();
            
            	lvRueckmeldeZeile = lvRueckmeldeZeile + lvAusplatzierungsmerkmal + ";" + StringKonverter.lineSeparator;  
            	// nur BLB: OEPG nicht zurueckmelden
            	if (lvHelpDarlehenObjekt.getOriginator().equals("29050000"))
            	{ 
            		lvOutRueck_BLB.printRueckmeldezeile(lvRueckmeldeZeile);    
                    lvZaehler_BLB++;
            	}
            	else
            	{ 
            		lvOutRueck_NLB.printRueckmeldezeile(lvRueckmeldeZeile);
            		lvZaehler_NLB++;
            	}
            	
            	// Wenn das Ausplatzierungsmerkmal '99' ist, dann in die Liste der abgegangenen Kredite eintragen
                if (lvAusplatzierungsmerkmal.equals("99"))
                {
                  ivListeAbgangKredite.add(lvHelpEntry.getKey());
                }
            }           
        }
        
        lvOutRueck_NLB.close();
        lvOutRueck_BLB.close();
        LOGGER.info("Anzahl Rueckmeldungen an Darlehen_NLB: " + lvZaehler_NLB);
        LOGGER.info("Anzahl Rueckmeldungen an Darlehen_BLB: " + lvZaehler_BLB);
    }
  
    /**
     * Schreibt die Rueckmeldedatei fuer SAPCMS
     * @param pvFilenameOut_NLB
     * @param pvFilenameOut_BLB
     */
   private void schreibeRueckmeldungSAPCMS(String pvFilenameOut_NLB, String pvFilenameOut_BLB)
    {        
        OutputRueckmeldung lvOutRueck_NLB = new OutputRueckmeldung(pvFilenameOut_NLB);
        lvOutRueck_NLB.open();
        OutputRueckmeldung lvOutRueck_BLB = new OutputRueckmeldung(pvFilenameOut_BLB);
        lvOutRueck_BLB.open();
        
        Element lvRootNode = ivDocument.getRootElement();
        
        List<Element> lvListSlice = lvRootNode.getChildren();

        RueckmeldeTripelListe lvTripelListe = ermittleRueckmeldeTripel(lvListSlice);
                        
        // Vorlaufsatz schreiben
        // Bsp.: 00;009;TXS_PROD;EDRADMIN;24.05.2012;20120524144510;9377;
        String lvRueckmeldeZeile = "00;";
        lvRueckmeldeZeile = lvRueckmeldeZeile + "009;";
         
        lvRueckmeldeZeile = lvRueckmeldeZeile + "TXS_PROD;"; // Konstant --> 'TXS_PROD'
        lvRueckmeldeZeile = lvRueckmeldeZeile + System.getProperty("user.name") + ";";
        DateFormat lvDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date lvDate = new Date();
        lvRueckmeldeZeile = lvRueckmeldeZeile + lvDateFormat.format(lvDate) + ";";
        lvDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        lvRueckmeldeZeile = lvRueckmeldeZeile + lvDateFormat.format(lvDate) + ";";
        lvRueckmeldeZeile = lvRueckmeldeZeile + lvTripelListe.size() + ";" + StringKonverter.lineSeparator;
        // NLB-Vorlaufsatz
        lvOutRueck_NLB.printRueckmeldezeile(lvRueckmeldeZeile);
        // BLB-Vorlaufsatz
        lvOutRueck_BLB.printRueckmeldezeile(lvRueckmeldeZeile.replace("009", "004"));
       
        int lvZaehler_NLB = 0;
        int lvZaehler_BLB = 0;
        // Datensaetze schreiben
        // 50;1234567890;D;10100001434;N;10720000391;N;
        Enumeration<RueckmeldeTripel> enumTripel = lvTripelListe.elements();
        while (enumTripel.hasMoreElements())
        {
            RueckmeldeTripel lvTripel = enumTripel.nextElement();  
            
        //for (int x = 0; x < lvTripelListe.size(); x++)
        //{
        //    RueckmeldeTripel lvTripel = lvTripelListe..get(x);  
            //if (ivListeObjekte == null || ivListeObjekte.getObjekt(lvTripel.getObjektID()) == null)
            //{
            //if ((lvTripel.getSicherheitenStatus().equals("D") || lvTripel.getSicherheitenStatus().equals("N")))// && 
                    //(lvNodeSlice.getAttributeValue("quelle").equals("ADARLPFBG") || lvNodeSlice.getAttributeValue("quelle").equals("ADARLOEPG") || 
                    // lvNodeSlice.getAttributeValue("quelle").equals("ADARLSCHF") || lvNodeSlice.getAttributeValue("quelle").equals("ADARLFLUG")))
            //{
            LOGGER.info("SAP CMS-Tripel" + ";" + lvTripel.getGeschaeftswertID() + ";" + lvTripel.getGeschaeftswertStatus() + ";" + 
                                                 lvTripel.getSicherheitenID() + ";" + lvTripel.getSicherheitenStatus() + ";" +
                                                 lvTripel.getObjektID() + ";" + lvTripel.getObjektStatus());
            lvRueckmeldeZeile = "50;";
            lvRueckmeldeZeile = lvRueckmeldeZeile + lvTripel.getGeschaeftswertID() + ";";
            lvRueckmeldeZeile = lvRueckmeldeZeile + lvTripel.getGeschaeftswertStatus() + ";";
            lvRueckmeldeZeile = lvRueckmeldeZeile + lvTripel.getSicherheitenID() + ";";
            lvRueckmeldeZeile = lvRueckmeldeZeile + lvTripel.getSicherheitenStatus() + ";";
            lvRueckmeldeZeile = lvRueckmeldeZeile + lvTripel.getObjektID() + ";";
            lvRueckmeldeZeile = lvRueckmeldeZeile + lvTripel.getObjektStatus() + ";" + StringKonverter.lineSeparator;
            if (lvTripel.getOriginator().equals("29050000"))
            {
            	lvOutRueck_BLB.printRueckmeldezeile(lvRueckmeldeZeile);  
            	lvZaehler_BLB++;
            }
            else
            {
            	lvOutRueck_NLB.printRueckmeldezeile(lvRueckmeldeZeile);  
            	lvZaehler_NLB++;                	
            }
          // }
        }

        lvOutRueck_NLB.close();
        lvOutRueck_BLB.close();
        LOGGER.info("Anzahl Rueckmeldungen an SAPCMS_NLB: " + lvZaehler_NLB);
        LOGGER.info("Anzahl Rueckmeldungen an SAPCMS_BLB: " + lvZaehler_BLB);
    } 
    
    /**
     * "Alte" Rueckmelde-Methode fuer SAPCMS
     * @param pvFilenameOut
     */
    /* private void schreibeRueckmeldungSAPCMS(String pvFilenameOut)
    {            
        OutputRueckmeldung lvOutRueck = new OutputRueckmeldung(pvFilenameOut);
        lvOutRueck.open();
        String lvRueckmeldeZeile = new String();
        String lvInstitutsNr = new String();
        
        Element lvRootNode = ivDocument.getRootElement();
        
        List<Element> lvListSlice = lvRootNode.getChildren();

        SicherungsobjektListe lvObjListe = new SicherungsobjektListe();
        int lvZaehler = 0;
        
        for (int x = 0; x < lvListSlice.size(); x++)
        {
            Element lvNodeSlice = (Element)lvListSlice.get(x); 

            // CT 24.02.2014 - Wird spaeter benoetigt...
            String lvDeckungsstatus = ermittleDeckungsstatus(lvNodeSlice.getAttributeValue("ausplmerkmal"), lvNodeSlice.getAttributeValue("bis"), lvNodeSlice.getAttributeValue("quelle"));
            //String lvDeckungsstatus = ermittleDeckungsstatus(lvNodeSlice.getAttributeValue("ass"), lvNodeSlice.getAttributeValue("black"));
            
            if ((lvDeckungsstatus.equals("D") || lvDeckungsstatus.equals("N")) && 
                (lvNodeSlice.getAttributeValue("quelle").equals("ADARLPFBG") || lvNodeSlice.getAttributeValue("quelle").equals("ADARLOEPG") || 
                 lvNodeSlice.getAttributeValue("quelle").equals("ADARLSCHF") || lvNodeSlice.getAttributeValue("quelle").equals("ADARLFLUG")))
            {        
                if (lvNodeSlice.getAttributeValue("org").equals("25050000"))
                {
                    lvInstitutsNr = "0001";
                }  
                         
                String lvHelpString = new String();
                
                if (!lvNodeSlice.getAttributeValue("shpo").isEmpty())
                {
                    if (lvNodeSlice.getAttributeValue("shpo").contains(";"))
                    {
                        //System.out.println(lvNodeSlice.getAttributeValue("shpo"));
                        for (int y = 0; y < lvNodeSlice.getAttributeValue("shpo").length(); y++)
                        {
                            if (lvNodeSlice.getAttributeValue("shpo").charAt(y) == ';')
                            {
                                if (!lvHelpString.contains(lvNodeSlice.getAttributeValue("key")))
                                {
                                  //System.out.println("lvHelpString: " + lvHelpString);
                                  lvObjListe.putSHPO(lvHelpString, lvDeckungsstatus);
                                }
                                lvHelpString = new String();
                            }
                            else
                            {
                                lvHelpString = lvHelpString + lvNodeSlice.getAttributeValue("shpo").charAt(y);
                            }      
                        }
                        
                        if (!lvHelpString.contains(lvNodeSlice.getAttributeValue("key")))
                        {
                          //System.out.println("lvHelpString: " + lvHelpString);
                          lvObjListe.putSHPO(lvHelpString, lvDeckungsstatus);
                        }
                    }
                    else
                    {
                        if (!lvNodeSlice.getAttributeValue("shpo").contains(lvNodeSlice.getAttributeValue("key")))
                        {
                          //System.out.println(lvNodeSlice.getAttributeValue("shpo"));
                          lvObjListe.putSHPO(lvNodeSlice.getAttributeValue("shpo"), lvDeckungsstatus);
                        }
                    }
                }
                
            }
        }
         
        Collection<Sicherungsobjekt> lvObjekte = lvObjListe.values();
        Iterator<Sicherungsobjekt> iterObjekte = lvObjekte.iterator();
        while (iterObjekte.hasNext())
        {
            Sicherungsobjekt lvObjekt = iterObjekte.next();
            for (int y = 0; y < lvObjekt.getShKeys().size(); y++)
            {
                String lvShkey = lvObjekt.getShKeys().get(y);
                //System.out.println("CT - lvShkey: " + lvShkey);
                if (!lvShkey.startsWith("SH_DARL") && !lvShkey.contains("_"))
                {
                    //shkey = shkey.substring(0, shkey.indexOf("_"));
                    // Workaround - CT 10.08.2012
                    if (lvObjekt.getDeckungsstatus().equals("D"))
                    {
                        ivListeAbgangObjekte.remove(lvObjekt.getsObjKey());
                    }
                    //System.out.println("sObjKey: " + lvObjekt.getsObjKey());
                    // Workaround - CT 10.08.2012
                    //if (ivListeObjekte == null || ivListeObjekte.getObjekt(lvObjekt.getsObjKey()) == null)
                    //{
                        if (ivListeAbgangObjekte.getObjekt(lvObjekt.getsObjKey()) == null)
                        {
                            if (!lvObjekt.getDeckungsstatus().equals("L"))
                            {
                              lvRueckmeldeZeile = lvInstitutsNr + ";" + lvShkey + ";20;" 
                              + lvObjekt.getsObjKey() + ";" + lvObjekt.getDeckungsstatus() + ";" + StringKonverter.lineSeparator;
                              lvOutRueck.printRueckmeldezeile(lvRueckmeldeZeile);
                              lvZaehler++;
                              // Workaround - CT 10.08.2012
                              if (lvObjekt.getDeckungsstatus().equals("N"))
                              {
                                if (!ivListeAbgangObjekte.containsKey(lvObjekt.getsObjKey()))
                                {
                                    ivListeAbgangObjekte.addObjekt(lvObjekt.getsObjKey());
                                }
                              }
                              // Workaround - CT 10.08.2012
                            }
                        }
                    //}
                }
            }                    
        }         
        
        lvOutRueck.close();       
        LOGGER.info("Anzahl Rueckmeldungen an SAPCMS: " + lvZaehler);
    } */
        
    /**
     * Schreibt die Rueckmeldedatei fuer MAVIS
     * @param pvFilenameOut_NLB
     * @param pvFilenameOut_BLB
     * @param pvFilenameIn_NLB
     * @param pvFilenameIn_BLB
     * @param pvBuchungsdatum
     */
    private void schreibeRueckmeldungMAVIS(String pvFilenameOut_NLB, String pvFilenameOut_BLB, String pvFilenameIn_NLB, String pvFilenameIn_BLB, String pvBuchungsdatum)
    {
        // Liste der Rueckmeldeobjekte
        HashMap<String, DarlehenObjekt> lvListeRueckmeldeobjekte = new HashMap<String, DarlehenObjekt>();
    	
    	int lvZaehlerMAVIS_NLB = 0;
    	int lvZaehlerMAVIS_BLB = 0;
        DarlehenObjekt lvDarlehenObjekt = null;
    	
    	// RueckmeldungMAVIS_NLB einlesen
        File lvRueckMAVIS_NLB = new File(pvFilenameIn_NLB);
        HashMap<String, RueckmeldeDatenMAVIS> lvListeMAVIS_NLB = new HashMap<String, RueckmeldeDatenMAVIS>();
        if (lvRueckMAVIS_NLB != null && lvRueckMAVIS_NLB.exists())
        {
          FileInputStream lvRueckMAVISIs = null;
          BufferedReader lvRueckMAVISIn = null;
          try
          {
            lvRueckMAVISIs = new FileInputStream(lvRueckMAVIS_NLB);
            lvRueckMAVISIn = new BufferedReader(new InputStreamReader(lvRueckMAVISIs));
          }
          catch (Exception e)
          {
            LOGGER.info("Konnte Datei '" + pvFilenameIn_NLB + "' nicht oeffnen!");
          }
          LOGGER.info("ISIN in RueckmeldungInMAVIS_NLB:");
          String lvRueckMAVISZeile = new String();
          try
          {
            while ((lvRueckMAVISZeile = lvRueckMAVISIn.readLine()) != null)  // Einlesen einer Zeile
            {
              if (lvRueckMAVISZeile != null)
              {
            	RueckmeldeDatenMAVIS lvRueckmeldeDatenMAVIS = new RueckmeldeDatenMAVIS();
            	lvRueckmeldeDatenMAVIS.parseRueckmeldeDaten(lvRueckMAVISZeile, LOGGER);
                lvListeMAVIS_NLB.put(lvRueckMAVISZeile.substring(30,42), lvRueckmeldeDatenMAVIS);
                LOGGER.info(lvRueckMAVISZeile.substring(30,42));
                lvZaehlerMAVIS_NLB++;
              }
            }
          }
          catch (Exception e)
          {
              LOGGER.error("Fehler beim Verarbeiten einer Zeile:" + lvRueckMAVISZeile);
              e.printStackTrace();
          }
          try
          {
            lvRueckMAVISIn.close();
            lvRueckMAVISIs.close();
          }
          catch (Exception e)
          {
              LOGGER.error("Konnte Datei '" + pvFilenameIn_NLB + "' nicht schliessen!");
          }
        }
        else
        {
            LOGGER.info("Keine Datei '" + pvFilenameIn_NLB + "' gefunden...");
        }
        HashMap<String, RueckmeldeDatenMAVIS> lvListeMAVIS_NLB_Clone = (HashMap<String, RueckmeldeDatenMAVIS>) lvListeMAVIS_NLB.clone();
        LOGGER.info("Anzahl ISIN in RueckmeldungInMAVIS_NLB: " + lvZaehlerMAVIS_NLB);
    	
    	// RueckmeldungMAVIS_BLB einlesen
        File lvRueckMAVIS_BLB = new File(pvFilenameIn_BLB);
        HashMap<String, RueckmeldeDatenMAVIS> lvListeMAVIS_BLB = new HashMap<String, RueckmeldeDatenMAVIS>();
        if (lvRueckMAVIS_BLB != null && lvRueckMAVIS_BLB.exists())
        {
          FileInputStream lvRueckMAVISIs = null;
          BufferedReader lvRueckMAVISIn = null;
          try
          {
            lvRueckMAVISIs = new FileInputStream(lvRueckMAVIS_BLB);
            lvRueckMAVISIn = new BufferedReader(new InputStreamReader(lvRueckMAVISIs));
          }
          catch (Exception e)
          {
            LOGGER.info("Konnte Datei '" + pvFilenameIn_BLB + "' nicht oeffnen!");
          }
          LOGGER.info("ISIN in RueckmeldungInMAVIS_BLB:");
          String lvRueckMAVISZeile = new String();
          try
          {
            while ((lvRueckMAVISZeile = lvRueckMAVISIn.readLine()) != null)  // Einlesen einer Zeile
            {
              if (lvRueckMAVISZeile != null)
              {
              	RueckmeldeDatenMAVIS lvRueckmeldeDatenMAVIS = new RueckmeldeDatenMAVIS();
              	lvRueckmeldeDatenMAVIS.parseRueckmeldeDaten(lvRueckMAVISZeile, LOGGER);
                lvListeMAVIS_BLB.put(lvRueckMAVISZeile.substring(30,42), lvRueckmeldeDatenMAVIS);
                LOGGER.info(lvRueckMAVISZeile.substring(30,42));
                lvZaehlerMAVIS_BLB++;
              }
            }
          }
          catch (Exception e)
          {
              LOGGER.error("Fehler beim Verarbeiten einer Zeile:" + lvRueckMAVISZeile);
              e.printStackTrace();
          }
          try
          {
            lvRueckMAVISIn.close();
            lvRueckMAVISIs.close();
          }
          catch (Exception e)
          {
              LOGGER.error("Konnte Datei '" + pvFilenameIn_BLB + "' nicht schliessen!");
          }
        }
        else
        {
            LOGGER.info("Keine Datei '" + pvFilenameIn_BLB + "' gefunden...");
        }
        HashMap<String, RueckmeldeDatenMAVIS> lvListeMAVIS_BLB_Clone = (HashMap<String, RueckmeldeDatenMAVIS>) lvListeMAVIS_BLB.clone();
        LOGGER.info("Anzahl ISIN in RueckmeldungInMAVIS_BLB: " + lvZaehlerMAVIS_BLB);
        
        lvZaehlerMAVIS_NLB = 0;
        lvZaehlerMAVIS_BLB = 0;
        
 	   	Calendar lvCal = Calendar.getInstance();
 	   	CalendarHelper lvCh = new CalendarHelper();

    	OutputRueckmeldung lvOutRueck_NLB = new OutputRueckmeldung(pvFilenameOut_NLB + "_" + lvCh.printDateAsNumber(lvCal) + ".csv");
    	OutputRueckmeldung lvOutRueck_BLB = new OutputRueckmeldung(pvFilenameOut_BLB + "_" + lvCh.printDateAsNumber(lvCal) + ".csv");
    	lvOutRueck_NLB.open();
    	lvOutRueck_BLB.open();
    	
        Element lvRootNode = ivDocument.getRootElement();
        
        List<Element> lvListSlice = lvRootNode.getChildren();       
        
        // Alle Slices durchgehen und Liste der Rueckmeldeobjekte fuellen
        for (int x = 0; x < lvListSlice.size(); x++)
        {
            Element lvNodeSlice = (Element)lvListSlice.get(x);  
            if (lvNodeSlice.getAttributeValue("quelle").contains("MAVI"))
            {
                String lvDeckungsstatus = ermittleDeckungsstatus(lvNodeSlice.getAttributeValue("ausplmerkmal"), lvNodeSlice.getAttributeValue("bis"), lvNodeSlice.getAttributeValue("quelle"));

                String lvHelpKey = lvNodeSlice.getAttributeValue("key");
                if (lvNodeSlice.getAttributeValue("key").contains("_")) // Eventuell vorhandener Unterstrichteil am Ende entfernen
                {
                    lvHelpKey = lvNodeSlice.getAttributeValue("key").substring(0, lvNodeSlice.getAttributeValue("key").indexOf("_"));
                }
                
                if (lvListeRueckmeldeobjekte.containsKey(lvHelpKey)) // Key schon vorhanden...
                {
                  lvDarlehenObjekt = lvListeRueckmeldeobjekte.get(lvHelpKey);
                  if (lvDeckungsstatus.equals("D") || lvDeckungsstatus.equals("S") || lvDeckungsstatus.equals("U") ||
                      lvDeckungsstatus.equals("1") || lvDeckungsstatus.equals("2") || lvDeckungsstatus.equals("3") ||
                      lvDeckungsstatus.equals("4") || lvDeckungsstatus.equals("5") || lvDeckungsstatus.equals("6") ||
                      lvDeckungsstatus.equals("7") || lvDeckungsstatus.equals("8")) // 'D', 'U', 'S', '1' bis '8' immer setzen
                  {
                	  lvDarlehenObjekt.setAusplatzierungsmerkmal(lvNodeSlice.getAttributeValue("ausplmerkmal"));
                	  lvDarlehenObjekt.setDeckungsstatus(lvDeckungsstatus);
                	  // Quellsystem muss auch geaendert werden
                	  lvDarlehenObjekt.setQuellsystem(lvNodeSlice.getAttributeValue("quelle"));
                	  lvDarlehenObjekt.setOriginator(lvNodeSlice.getAttributeValue("org"));
                  }
                  else
                  {
                	  if (lvDeckungsstatus.equals("N") && (!lvDarlehenObjekt.getDeckungsstatus().equals("D") && !lvDarlehenObjekt.getDeckungsstatus().equals("S") && !lvDarlehenObjekt.getDeckungsstatus().equals("U") &&
                			                               !lvDarlehenObjekt.getDeckungsstatus().equals("1") && !lvDarlehenObjekt.getDeckungsstatus().equals("2") && !lvDarlehenObjekt.getDeckungsstatus().equals("3") &&
                			                               !lvDarlehenObjekt.getDeckungsstatus().equals("4") && !lvDarlehenObjekt.getDeckungsstatus().equals("5") && !lvDarlehenObjekt.getDeckungsstatus().equals("6") &&
                			                               !lvDarlehenObjekt.getDeckungsstatus().equals("7") && !lvDarlehenObjekt.getDeckungsstatus().equals("8"))) //'N' nur setzen, wenn nicht 'D', 'S', 'U', '1' bis '8' eingetragen ist
                	  {
                		  // Wenn Feld '99Zurueckgemeldet' gesetzt, dann keine Rueckmeldung
                          RueckmeldeDatenMAVIS lvRueckmeldeDatenMAVIS;
                          if (lvDarlehenObjekt.getOriginator().equals("29050000"))
                          {
                       	    lvRueckmeldeDatenMAVIS = lvListeMAVIS_BLB.get(lvHelpKey);
                          }
                          else
                          {
                       	    lvRueckmeldeDatenMAVIS = lvListeMAVIS_NLB.get(lvHelpKey);
                          }
                          if (lvRueckmeldeDatenMAVIS != null)
                          {
                        	  if (!lvRueckmeldeDatenMAVIS.get99Zurueckgemeldet().equals("J"))
                        	  {
                        		  // Wenn Anlieferungsdatum nicht gleich dem heutigen Datum ist und '99Zurueckgemeldet' == N, dann einmal (N,99) zurueckmelden und Feld '99Zurueckgemeldet' auf 'J' setzen.
                        		  //if (!pvBuchungsdatum.equals(lvRueckmeldeDatenMAVIS.getAnlieferungsdatum()) && 
                        		  if (lvRueckmeldeDatenMAVIS.get99Zurueckgemeldet().equals("N"))
                        		  {
                        			  lvDarlehenObjekt.setAusplatzierungsmerkmal(lvNodeSlice.getAttributeValue("ausplmerkmal"));
                        			  lvDarlehenObjekt.setDeckungsstatus(lvDeckungsstatus);
                        			  // Quellsystem muss auch geaendert werden
                        			  lvDarlehenObjekt.setQuellsystem(lvNodeSlice.getAttributeValue("quelle")); 
                        			  lvDarlehenObjekt.setOriginator(lvNodeSlice.getAttributeValue("org"));
                        			  lvRueckmeldeDatenMAVIS.set99Zurueckgemeldet("J");
                        		  }
                        	  }
                          }
                          else
                          {
                        	  LOGGER.info("Wurde nicht angeliefert: " + lvHelpKey);
                          }
                	  }
                  }
                }
                else // Key nicht vorhanden, dann ein neues Rueckmeldeobjekt anlegen.
                {
            		lvDarlehenObjekt = new DarlehenObjekt(lvNodeSlice.getAttributeValue("ausplmerkmal"), lvDeckungsstatus, lvNodeSlice.getAttributeValue("org"), lvNodeSlice.getAttributeValue("quelle"), lvNodeSlice.getAttributeValue("bis"));
                	if (lvDeckungsstatus.equals("N"))
                	{
              		  // Wenn Feld '99Zurueckgemeldet' gesetzt, dann keine Rueckmeldung
                        RueckmeldeDatenMAVIS lvRueckmeldeDatenMAVIS;
                        if (lvDarlehenObjekt.getOriginator().equals("29050000"))
                        {
                     	   lvRueckmeldeDatenMAVIS = lvListeMAVIS_BLB.get(lvHelpKey);
                        }
                        else
                        {
                     	   lvRueckmeldeDatenMAVIS = lvListeMAVIS_NLB.get(lvHelpKey);
                        }
                        if (lvRueckmeldeDatenMAVIS != null)
                        {
                          if (!lvRueckmeldeDatenMAVIS.get99Zurueckgemeldet().equals("J"))
                          {
                        	// System.out.println(pvBuchungsdatum + " == " + lvRueckmeldeDatenMAVIS.getAnlieferungsdatum());
                        	// System.out.println("99Zurueckgemeldet: " + lvRueckmeldeDatenMAVIS.get99Zurueckgemeldet());
                            // Wenn Anlieferungsdatum nicht gleich dem heutigen Datum ist und '99Zurueckgemeldet' == N, dann einmal (N,99) zurueckmelden und Feld '99Zurueckgemeldet' auf 'J' setzen.
                        	//if (!pvBuchungsdatum.equals(lvRueckmeldeDatenMAVIS.getAnlieferungsdatum()) && 
                        	if (lvRueckmeldeDatenMAVIS.get99Zurueckgemeldet().equals("N"))
                        	{
                        		lvListeRueckmeldeobjekte.put(lvHelpKey, lvDarlehenObjekt);
                        		lvRueckmeldeDatenMAVIS.set99Zurueckgemeldet("J");
                        	}
                          }
                        }
                        else
                        {
                      	  LOGGER.info("Wurde nicht angeliefert: " + lvHelpKey);
                        }
                	}
                	else
                	{
                		lvListeRueckmeldeobjekte.put(lvHelpKey, lvDarlehenObjekt);
                		//System.out.println(lvHelpKey + ":" + lvDeckungsstatus + " gesetzt...");
                	}
                }
                
            }
        }
        
        // Rueckmeldezeilen in Datei schreiben
        Set<String> lvSetKeys = lvListeRueckmeldeobjekte.keySet();
        Iterator<String> lvIterKeys = lvSetKeys.iterator();
        while (lvIterKeys.hasNext())
        {       
           String lvHelpKey = lvIterKeys.next();         
           DarlehenObjekt lvHelpDarlehenObjekt = lvListeRueckmeldeobjekte.get(lvHelpKey);        	
           RueckmeldeDatenMAVIS lvRueckmeldeDatenMAVIS;
           if (lvHelpDarlehenObjekt.getOriginator().equals("29050000"))
           {
        	   lvRueckmeldeDatenMAVIS = lvListeMAVIS_BLB.get(lvHelpKey);
           	   if (lvRueckmeldeDatenMAVIS != null)
        	   {
        		   lvListeMAVIS_BLB_Clone.remove(lvHelpKey);
        	   }
           }
           else
           {
        	   lvRueckmeldeDatenMAVIS = lvListeMAVIS_NLB.get(lvHelpKey);
        	   if (lvRueckmeldeDatenMAVIS != null)
        	   {
        		   lvListeMAVIS_NLB_Clone.remove(lvHelpKey);
        	   }
           }
           if (lvRueckmeldeDatenMAVIS != null) // Nur rausschreiben, wenn es den ersten Teil gibt.
           {
        	   StringBuffer lvBufferZeile = new StringBuffer();
        	   String lvHelpZeile = lvRueckmeldeDatenMAVIS.toString();
        	   lvHelpZeile = lvHelpZeile.substring(0, lvHelpZeile.lastIndexOf(";")); // Anlieferungsdatum entfernen
        	   lvHelpZeile = lvHelpZeile.substring(0, lvHelpZeile.lastIndexOf(";")); // 99Zurueckgemeldet entfernen
        	   lvBufferZeile.append(lvHelpZeile);
        	   lvBufferZeile.append(";");
        	   lvBufferZeile.append(lvHelpDarlehenObjekt.getAusplatzierungsmerkmal());
        	   lvBufferZeile.append(";");
        	   lvBufferZeile.append(ermittleDeckungsstatus(lvHelpDarlehenObjekt.getAusplatzierungsmerkmal(), lvHelpDarlehenObjekt.getSolldeckung(), lvHelpDarlehenObjekt.getQuellsystem()));
        	   lvBufferZeile.append(";");
        	   lvBufferZeile.append(lvCh.printDateWithMinus(lvCal));	
        	   lvBufferZeile.append(StringKonverter.lineSeparator);
        	   if (lvHelpDarlehenObjekt.getOriginator().equals("29050000"))
        	   {
        		   if (!lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().endsWith("0"))  // Ausplatzierungsmerkmal mit am Ende 0 nicht zurueckmelden
        		   {
        		     lvOutRueck_BLB.printRueckmeldezeile(lvBufferZeile.toString());	
        		     lvZaehlerMAVIS_BLB++;
        		   }
        	   }
        	   else
        	   {
        		   if (!lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().endsWith("0")) // Ausplatzierungsmerkmal mit am Ende 0 nicht zurueckmelden
        		   {
        		     lvOutRueck_NLB.printRueckmeldezeile(lvBufferZeile.toString());	
        		     lvZaehlerMAVIS_NLB++;
        		   }
        	   }
           }
           else
           {
        	   LOGGER.info("Keinen ersten Teil fuer " + lvHelpKey + " gefunden!");
           }
        }
    	
        lvOutRueck_NLB.close();
        lvOutRueck_BLB.close();
        
        LOGGER.info("Nicht zurueckgemeldet - NLB:");
        for (RueckmeldeDatenMAVIS lvRueckmeldeDaten:lvListeMAVIS_NLB_Clone.values())
        {
        	LOGGER.info(lvRueckmeldeDaten.toString());
        }

        LOGGER.info("Nicht zurueckgemeldet - BLB:");
        for (RueckmeldeDatenMAVIS lvRueckmeldeDaten:lvListeMAVIS_BLB_Clone.values())
        {
        	LOGGER.info(lvRueckmeldeDaten.toString());
        }
        
        // Fuer die Ausgabe der Rueckmeldedaten
        File lvRueckFile = new File(pvFilenameIn_NLB);
        FileOutputStream lvRueckOut = null;
        
        try
        {
        	lvRueckOut = new FileOutputStream(lvRueckFile);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte RueckmeldeDaten-Datei " + lvRueckFile +" nicht oeffnen!");
            return;      	
        }
    	
        // Schreibe RueckmeldeDaten in die Datei
        for (RueckmeldeDatenMAVIS lvRueckmeldeDaten:lvListeMAVIS_NLB.values())
        {
        	try
        	{
        		lvRueckOut.write((lvRueckmeldeDaten.toString() + StringKonverter.lineSeparator).getBytes());
        	}
        	catch (Exception e)
        	{
        		LOGGER.error("Fehler beim Rausschreiben der RueckmeldeDaten");
        	}
        }

        try
        {
        	lvRueckOut.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte RueckmeldeDaten-Datei " + lvRueckFile +" nicht schliessen!");
        }    

        lvRueckFile = new File(pvFilenameIn_BLB);
       
        try
        {
        	lvRueckOut = new FileOutputStream(lvRueckFile);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte RueckmeldeDaten-Datei " + lvRueckFile +" nicht oeffnen!");
            return;      	
        }
    	
        // Schreibe RueckmeldeDaten in die Datei
        for (RueckmeldeDatenMAVIS lvRueckmeldeDaten:lvListeMAVIS_BLB.values())
        {
        	try
        	{
        		lvRueckOut.write((lvRueckmeldeDaten.toString() + StringKonverter.lineSeparator).getBytes());
        	}
        	catch (Exception e)
        	{
        		LOGGER.error("Fehler beim Rausschreiben der RueckmeldeDaten");
        	}
        }

        try
        {
        	lvRueckOut.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte RueckmeldeDaten-Datei " + lvRueckFile +" nicht schliessen!");
        }    

        LOGGER.info("Anzahl Rueckmeldungen an MAVIS_NLB: " + lvZaehlerMAVIS_NLB);
        LOGGER.info("Anzahl Rueckmeldungen an MAVIS_BLB: " + lvZaehlerMAVIS_BLB);
    }
    
    /**
     * Schreibt die Rueckmeldedatei fuer LoanIQ
     * @param pvFilenameOut
     * @param pvBagatellgrenze
     */
    private void schreibeRueckmeldungLoanIQ(String pvFilenameOut, String pvBagatellgrenze)
    {
        int lvZaehlerLoanIQ = 0;
        
        // Liste der Darlehen
        HashMap<String, DarlehenObjekt> lvListeDarlehen = new HashMap<String, DarlehenObjekt>();

        OutputRueckmeldung lvOutRueck = new OutputRueckmeldung(pvFilenameOut);
        lvOutRueck.open();
        
        Element lvRootNode = ivDocument.getRootElement();
        
        List<Element> lvListSlice = lvRootNode.getChildren();
        LOGGER.info("Anzahl Datensaetze: " + lvListSlice.size());
        
        DarlehenObjekt lvDarlehenObjekt;
        
        for (int x = 0; x < lvListSlice.size(); x++)
        {
            Element lvNodeSlice = (Element)lvListSlice.get(x);  
            // Deckungsstatus ermitteln
            // Ueber Ausplatzierungsmerkmal...
            String lvDeckungsstatus = ermittleDeckungsstatus(lvNodeSlice.getAttributeValue("ausplmerkmal"), lvNodeSlice.getAttributeValue("bis"), lvNodeSlice.getAttributeValue("quelle"));
            // Ueber Assigned und Blacklist... - alte Variante 
            //String lvDeckungsstatus = ermittleDeckungsstatus(lvNodeSlice.getAttributeValue("ass"), lvNodeSlice.getAttributeValue("black"));
            
            // Repo-Flag
            //String lvRepoFlag = lvNodeSlice.getAttributeValue("repo");
            
            // Nliqui-Kennzeichen
            //String lvNliqui = lvNodeSlice.getAttributeValue("nliqui");
            
            // Wenn Repo-Flag -> 1 und Nliqui-Kennzeichen -> true ist, dann muss das Deckungskennzeichen '5' oder '6' sein
            // In dem Fall keine Rueckmeldung an Darlehen - CT 15.11.2013
            // Hier muesste man auch das passende Deckungskennzeichen zurueckmelden - Noch einbauen - CT 22.05.2014
            //if (lvRepoFlag.equals("1") && lvNliqui.equals("true"))
            //{
            //    LOGGER.info("Repo-Flag -> 1 und Nliqui -> true: " + lvNodeSlice.getAttributeValue("key"));
            //}
            //else
            //{
            // CT 16.09.2014 - Erst einmal raus, da ansonsten die Rueckmeldedatei leer ist
            //if (lvDeckungsstatus.equals("S") || lvDeckungsstatus.equals("U") ||
            //    lvDeckungsstatus.equals("D") || lvDeckungsstatus.equals("N") ||
            //    lvDeckungsstatus.equals("A") || lvDeckungsstatus.equals("0") ||
            //    lvDeckungsstatus.equals("1") || lvDeckungsstatus.equals("2") ||
            //    lvDeckungsstatus.equals("3") || lvDeckungsstatus.equals("4") ||
            //    lvDeckungsstatus.equals("5") || lvDeckungsstatus.equals("6") ||
            //    lvDeckungsstatus.equals("7") || lvDeckungsstatus.equals("8") ||
            //    lvDeckungsstatus.equals("9"))
            //    {
                    if (lvNodeSlice.getAttributeValue("quelle").startsWith("ALIQ"))
                    {
                        // nicht-TXS Ausplatzierungsmerkmal darf nicht zurueckgemeldet werden
                        if (!ivListeAusplatzierungsmerkmalLoanIQ.contains(lvNodeSlice.getAttributeValue("key")))
                        {
                          if (lvListeDarlehen.containsKey(lvNodeSlice.getAttributeValue("key")))
                          {
                            lvDarlehenObjekt = lvListeDarlehen.get(lvNodeSlice.getAttributeValue("key"));
                            if (!lvDeckungsstatus.equals("N"))
                            {
                                if (!lvDarlehenObjekt.getDeckungsstatus().equals(lvDeckungsstatus))
                                {       
                                    lvDarlehenObjekt.setAusplatzierungsmerkmal(lvNodeSlice.getAttributeValue("ausplmerkmal"));
                                    lvDarlehenObjekt.setDeckungsstatus(lvDeckungsstatus);
                                    // Quellsystem und Solldeckung muss auch geaendert werden
                                    lvDarlehenObjekt.setQuellsystem(lvNodeSlice.getAttributeValue("quelle"));
                                    lvDarlehenObjekt.setSolldeckung(lvNodeSlice.getAttributeValue("bis"));
                                }
                            }
                          }
                          else
                          {
                            lvDarlehenObjekt = new DarlehenObjekt(lvNodeSlice.getAttributeValue("ausplmerkmal"), lvDeckungsstatus, lvNodeSlice.getAttributeValue("org"), lvNodeSlice.getAttributeValue("quelle"), lvNodeSlice.getAttributeValue("bis"));
                            lvListeDarlehen.put(lvNodeSlice.getAttributeValue("key"), lvDarlehenObjekt);
                          }
                        }
                    }
                //}
            //}
        }
        
        lvOutRueck.printRueckmeldezeile("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + StringKonverter.lineSeparator);
        lvOutRueck.printRueckmeldezeile("<TXSuite_Outstandings>" + StringKonverter.lineSeparator);
        
        Iterator<Entry<String, DarlehenObjekt>> iterListeDarlehen = lvListeDarlehen.entrySet().iterator();
        while (iterListeDarlehen.hasNext())
        {           
            Entry<String, DarlehenObjekt> lvHelpEntry = (Entry<String, DarlehenObjekt>)iterListeDarlehen.next();
            DarlehenObjekt lvHelpDarlehenObjekt = (DarlehenObjekt)lvHelpEntry.getValue(); 
                        
            // Ausplatzierungsmerkmal != 99 und in der Liste der abgegangen Kredite, dann aus der Liste loeschen
            if (!lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().equals("99") && ivListeAbgangKredite.contains(lvHelpEntry.getKey()))
            {
            	ivListeAbgangKredite.remove(lvHelpEntry.getKey());
            }
 
            // Das Deckungskennzeichen fuer OEPG ist immer 'A'
            if (lvHelpDarlehenObjekt.getQuellsystem().equals("ALIQOEPG") &&
                lvHelpDarlehenObjekt.getDeckungsstatus().equals("D"))
            {
                lvHelpDarlehenObjekt.setDeckungsstatus("A");
            }
            if (lvHelpDarlehenObjekt.getQuellsystem().equals("ALIQSCHF") &&
                lvHelpDarlehenObjekt.getDeckungsstatus().equals("D"))
            {
                lvHelpDarlehenObjekt.setDeckungsstatus("S");
            }
            if (lvHelpDarlehenObjekt.getQuellsystem().equals("ALIQFLUG") &&
                lvHelpDarlehenObjekt.getDeckungsstatus().equals("D"))
            {
                lvHelpDarlehenObjekt.setDeckungsstatus("U");
            }
            
            if (!ivListeAbgangKredite.contains(lvHelpEntry.getKey()))
            {

            	if (!lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().isEmpty() && !lvHelpDarlehenObjekt.getDeckungsstatus().isEmpty())
            	{
            		lvOutRueck.printRueckmeldezeile("  <Outstanding>" + StringKonverter.lineSeparator);
            	
            		lvOutRueck.printRueckmeldezeile("    <Alias>" + lvHelpEntry.getKey() + "</Alias>" + StringKonverter.lineSeparator);
            		lvOutRueck.printRueckmeldezeile("    <Ausplatzierungskennzeichen>" + lvHelpDarlehenObjekt.getAusplatzierungsmerkmal() + "</Ausplatzierungskennzeichen>" + StringKonverter.lineSeparator);
            		lvOutRueck.printRueckmeldezeile("    <Deckungskennzeichen>" + lvHelpDarlehenObjekt.getDeckungsstatus() + "</Deckungskennzeichen>" + StringKonverter.lineSeparator);
            
            		if (ivListeLoanIQObjekte.containsKey(lvHelpEntry.getKey()))
            		{
            			LoanIQObjekt lvHelpLoanIQObjekt = ivListeLoanIQObjekte.get(lvHelpEntry.getKey());
            			BigDecimal lvBetragBis = StringKonverter.convertString2BigDecimal(lvHelpDarlehenObjekt.getSolldeckung());
            			BigDecimal lvVerbuergtFaktor = new BigDecimal("1.0");
            			// Bei Ausplatzierungsmerkmal 'H0', 'H1', 'S0', 'S1', 'F0' und 'F1' darf der Buergschaftprozentsatz nicht beruecksichtigt werden.
            			if (!(lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().equals("H0") || lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().equals("H1") ||
            				  lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().equals("S0") || lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().equals("S1") ||
            			      lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().equals("F0") || lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().equals("F1")))
            			{
            				lvVerbuergtFaktor = StringKonverter.convertString2BigDecimal(lvHelpLoanIQObjekt.getBuergschaftprozent());
            			}
            			BigDecimal lvKonsortialFaktor = StringKonverter.convertString2BigDecimal(lvHelpLoanIQObjekt.getKonsortialanteil());
            			BigDecimal lvFaktorKomplett = lvVerbuergtFaktor.multiply(lvKonsortialFaktor);
                
            			LOGGER.info("Kontonummer: " + lvHelpEntry.getKey());
            			if (lvFaktorKomplett.doubleValue() == 1.0)
            			{
            				LOGGER.info("Solldeckung - LoanIQ: " + lvBetragBis.toPlainString());                	
            			}
            			else
            			{
            				lvBetragBis = lvBetragBis.divide(lvFaktorKomplett, 9, RoundingMode.HALF_UP);
            				LOGGER.info("Solldeckung - LoanIQ (gerechnet): " + lvBetragBis.toPlainString());
            			}
            			BigDecimal lvSolldeckungBrutto = StringKonverter.convertString2BigDecimal(lvHelpLoanIQObjekt.getSolldeckungBrutto());
            			BigDecimal lvDifferenz = (lvSolldeckungBrutto.subtract(lvBetragBis)).abs();
            			BigDecimal lvBagatellgrenze = StringKonverter.convertString2BigDecimal(pvBagatellgrenze);
            			LOGGER.info("VerbuergtFaktor: " + lvVerbuergtFaktor.toString());
            			LOGGER.info("KonsortialFaktor: " + lvKonsortialFaktor.toString());
            			LOGGER.info("Faktor - Gesamt: " + lvFaktorKomplett);
            			LOGGER.info("SolldeckungBrutto: " + lvSolldeckungBrutto.toPlainString());
            			LOGGER.info("Solldeckung gerechnet: " + lvBetragBis.toPlainString());
            			LOGGER.info("Differenz: " + lvDifferenz.toPlainString());
            			LOGGER.info("Bagatellgrenze: " + lvBagatellgrenze.toPlainString());
            			if (lvBagatellgrenze.doubleValue() > lvDifferenz.doubleValue())
            			{
            				lvOutRueck.printRueckmeldezeile("    <Solldeckung>" + lvHelpLoanIQObjekt.getSolldeckungBrutto() + "</Solldeckung>" + StringKonverter.lineSeparator);               	            				
            			}	
            			else
            			{
            				lvOutRueck.printRueckmeldezeile("    <Solldeckung>" + lvBetragBis.toPlainString() + "</Solldeckung>" + StringKonverter.lineSeparator);               	
            			}
            		}
            		else
            		{
            			lvOutRueck.printRueckmeldezeile("    <Solldeckung>" + lvHelpDarlehenObjekt.getSolldeckung() + "</Solldeckung>" + StringKonverter.lineSeparator);
            		}
            
            		lvOutRueck.printRueckmeldezeile("  </Outstanding>" + StringKonverter.lineSeparator);

            		lvZaehlerLoanIQ++;
            	
            		// Wenn das Ausplatzierungsmerkmal '99' ist, dann in die Liste der abgegangenen Kredite eintragen
            		if (lvHelpDarlehenObjekt.getAusplatzierungsmerkmal().equals("99"))
            		{
            			ivListeAbgangKredite.add(lvHelpEntry.getKey());
            		}
            	}
            	else
            	{
        			LOGGER.info("Kontonummer: " + lvHelpEntry.getKey() + " - Deckungskennzeichen oder Ausplatzierungsmerkmal leer");
        			LOGGER.info("Ausplatzierungsmerkmal: " + lvHelpDarlehenObjekt.getAusplatzierungsmerkmal());
        			LOGGER.info("Deckungskennzeichen: " + lvHelpDarlehenObjekt.getDeckungsstatus());
            	}
            }
            else
            {
            	LOGGER.info("Keine Rueckmeldung, da (99,N) nur einmal zurueckgemeldet wird: " + lvHelpEntry.getKey());
            }
        }
        
        lvOutRueck.printRueckmeldezeile("</TXSuite_Outstandings>" + StringKonverter.lineSeparator);
        lvOutRueck.close();
        
        LOGGER.info("Anzahl Rueckmeldungen an LoanIQ: " + lvZaehlerLoanIQ);
    }
    
    /**
     * 
     * @param pvListSlice
     * @return
     */
    private RueckmeldeTripelListe ermittleRueckmeldeTripel(List<Element> pvListSlice)
    {
        RueckmeldeTripelListe lvRueckmeldeTripelListe = new RueckmeldeTripelListe();
        
        for (int x = 0; x < pvListSlice.size(); x++)
        {
            Element lvNodeSlice = (Element)pvListSlice.get(x);  

            // CT 24.02.2014 - Wird spaeter benoetigt...
            String lvDeckungsstatus = ermittleDeckungsstatusSAPCMS(lvNodeSlice.getAttributeValue("ausplmerkmal"));
                           
            String lvHelpShpo = lvNodeSlice.getAttributeValue("shpo");

            if (!lvHelpShpo.isEmpty())
              {
                //System.out.println("shpo: " + lvHelpShpo);
                String lvShKey = new String();
                String lvShStatus = new String();
                String lvObjKey = new String();
                String lvHelpString = new String();
                for (int y = 0; y < lvHelpShpo.length(); y++)
                {
                    if (lvHelpShpo.charAt(y) == ',' || lvHelpShpo.charAt(y) == ';')
                    {
                        if (lvHelpShpo.charAt(y) == ',')
                        {
                            if (lvShKey.isEmpty())
                            {
                                lvShKey = lvHelpString;
                            }
                            else if (lvShStatus.isEmpty())
                            {
                                lvShStatus = lvHelpString;
                            }
                            else if (lvObjKey.isEmpty())
                            {
                                lvObjKey = lvHelpString;
                            }
                            else
                            {
                              if (!lvShKey.startsWith("SH_DARL") && !lvShKey.contains("_"))
                              {
                                if (!lvDeckungsstatus.equals("F") && !lvShStatus.equals("F") && !lvHelpString.equals("F") && 
                                    !lvShStatus.equals("L") && !lvHelpString.equals("L"))
                                {
                                  // Flugzeuge
                                  if (lvDeckungsstatus.equals("U"))
                                  {
                                	  if (lvShStatus.equals("D")) lvShStatus = "U";
                                	  if (lvHelpString.equals("D")) lvHelpString = "U";
                                  }
                                  // Schiffe
                                  if (lvDeckungsstatus.equals("S"))
                                  {
                                	  if (lvShStatus.equals("D")) lvShStatus = "S";
                                	  if (lvHelpString.equals("D")) lvHelpString = "S";
                                  }                                  
                                  RueckmeldeTripel lvRueckmeldeTripel = new RueckmeldeTripel(lvNodeSlice.getAttributeValue("org"), lvNodeSlice.getAttributeValue("key"), lvShKey, lvObjKey, lvDeckungsstatus, lvShStatus, lvHelpString);
                                  if (!lvRueckmeldeTripelListe.contains(lvRueckmeldeTripel))
                                  {
                                    lvRueckmeldeTripelListe.put(lvRueckmeldeTripel.getGeschaeftswertID() + lvRueckmeldeTripel.getSicherheitenID() + lvRueckmeldeTripel.getObjektID(), lvRueckmeldeTripel);
                                  }
                                }
                              }
                              else
                              {
                                  LOGGER.info("DarKa-Sicherheit: " + lvShKey);
                              }
                              lvObjKey = new String();
                            }
                            lvHelpString = new String(); 
                        }
                        if (lvHelpShpo.charAt(y) == ';')
                        {
                            if (!lvShKey.startsWith("SH_DARL") && !lvShKey.contains("_"))
                            {  
                                if (!lvDeckungsstatus.equals("F") && !lvShStatus.equals("F") && !lvHelpString.equals("F") &&
                                    !lvShStatus.equals("L") && !lvHelpString.equals("L"))
                                {
                                	// Flugzeuge
                                    if (lvDeckungsstatus.equals("U"))
                                    {
                                  	  if (lvShStatus.equals("D")) lvShStatus = "U";
                                  	  if (lvHelpString.equals("D")) lvHelpString = "U";
                                    }
                                    // Schiffe
                                    if (lvDeckungsstatus.equals("S"))
                                    {
                                  	  if (lvShStatus.equals("D")) lvShStatus = "S";
                                  	  if (lvHelpString.equals("D")) lvHelpString = "S";
                                    }                                  
                                    RueckmeldeTripel lvRueckmeldeTripel = new RueckmeldeTripel(lvNodeSlice.getAttributeValue("org"), lvNodeSlice.getAttributeValue("key"), lvShKey, lvObjKey, lvDeckungsstatus, lvShStatus, lvHelpString);
                                    if (!lvRueckmeldeTripelListe.contains(lvRueckmeldeTripel))
                                    {
                                    	lvRueckmeldeTripelListe.put(lvRueckmeldeTripel.getGeschaeftswertID() + lvRueckmeldeTripel.getSicherheitenID() + lvRueckmeldeTripel.getObjektID(), lvRueckmeldeTripel);
                                    }
                                }
                            }
                            else
                            {
                                LOGGER.info("DarKa-Sicherheit: " + lvShKey);
                            }
                            lvShKey = new String();
                            lvShStatus = new String();
                            lvObjKey = new String();
                            lvHelpString = new String();
                        }
                    }
                    else
                    {
                        lvHelpString = lvHelpString + lvHelpShpo.charAt(y);
                    }
                }
                
                // Letzte Paar Obj-ID, Deck-Status haben kein ',' oder ';'
                if (!lvShKey.startsWith("SH_DARL") && !lvShKey.contains("_"))
                {
                    if (!lvDeckungsstatus.equals("F") && !lvShStatus.equals("F") && !lvHelpString.equals("F") &&
                        !lvShStatus.equals("L") && !lvHelpString.equals("L"))     
                    {
                        // Flugzeuge
                        if (lvDeckungsstatus.equals("U"))
                        {
                      	  if (lvShStatus.equals("D")) lvShStatus = "U";
                      	  if (lvHelpString.equals("D")) lvHelpString = "U";
                        }
                        // Schiffe
                        if (lvDeckungsstatus.equals("S"))
                        {
                      	  if (lvShStatus.equals("D")) lvShStatus = "S";
                      	  if (lvHelpString.equals("D")) lvHelpString = "S";
                        }                                  
                        RueckmeldeTripel rTripel = new RueckmeldeTripel(lvNodeSlice.getAttributeValue("org"), lvNodeSlice.getAttributeValue("key"), lvShKey, lvObjKey, lvDeckungsstatus, lvShStatus, lvHelpString);
                        if (!lvRueckmeldeTripelListe.contains(rTripel))
                        {
                            lvRueckmeldeTripelListe.put(rTripel.getGeschaeftswertID() + rTripel.getSicherheitenID() + rTripel.getObjektID(), rTripel);
                        }
                    }
                }
                else
                {
                    LOGGER.info("DarKa-Sicherheit: " + lvShKey);
                }                
              }
            else
            {
            	LOGGER.info("Konto " + lvNodeSlice.getAttributeValue("key") + "- shpo ist leer...");
            }
        }
        
        LOGGER.info("Anzahl Rueckmelde-Tripel: " + lvRueckmeldeTripelListe.size());
        
        return lvRueckmeldeTripelListe;
    } 
    
    /**
     * Ermittelt den Deckungsstatus anhand der Felder 'assigned' und 'blacklist'
     * @param pvAss
     * @param pvBlack
     * @return
     */
    /*
    private String ermittleDeckungsstatus(String pvAss, String pvBlack)
    {
        String lvStatus = "undefined";
        
        // assigned = 1 and blacklist = 0 --> 'in Deckung'
        // assigned = 0 and blacklist = 1 --> 'abgegangen'
        // assigned = 0 and blacklist = 0 --> 'fuer Deckung vorgesehen'
        // sonst 'undefined'

        if (pvAss.equals("1") && pvBlack.equals("0"))
        {
            lvStatus = "D";
        }
        if (pvAss.equals("0") && pvBlack.equals("1"))
        {
            lvStatus = "N";
        }
        if (pvAss.equals("0") && pvBlack.equals("0"))
        {
            lvStatus = "F";
        }

        return lvStatus;
    } */
    
    /**
     * Ermittelt den Deckungsstatus anhand des Ausplatzierungsmerkmals
     * @param pvAusplatzierungsmerkmal
     * @param pvBis
     * @param pvQuellsystem
     * @return
     */
    private String ermittleDeckungsstatus(String pvAusplatzierungsmerkmal, String pvBis, String pvQuellsystem)
    {
        String lvStatus = new String();
        
        // Ausplatzierungsmerkmal -> Deckungskennzeichen
        // O0 -> A
        // O1 -> A
        // O2 -> A
        // O3 -> A
        // O4 -> A
        // S0 -> V
        // S1 -> S
        // S2 -> 3
        // S3 -> S
        // S4 -> 7
        // F0 -> W
        // F1 -> U
        // F2 -> 4
        // F3 -> U
        // F4 -> 8
        // H0 -> F
        // H1 -> D
        // H2 -> 2
        // H3 -> D
        // H4 -> 6
        // K0 -> F
        // K1 -> D
        // K2 -> 1
        // K3 -> D
        // K4 -> 5
        // 99 -> N

        // Altbestand 
        // O0, O1 und O3 auf 'A'
        // O2 auf '0'
        // O4 auf '9'
        if (pvAusplatzierungsmerkmal.startsWith("O"))
        {
            if (pvAusplatzierungsmerkmal.equals("O0") || pvAusplatzierungsmerkmal.equals("O1") || pvAusplatzierungsmerkmal.equals("O3"))
            {
              lvStatus = "A";
            }
            if (pvAusplatzierungsmerkmal.equals("O2"))
            {
              lvStatus = "0";
            }
            if (pvAusplatzierungsmerkmal.equals("O4"))
            {
              lvStatus = "9";
            }
        }
        
        // Schiffsbestand
        if (pvAusplatzierungsmerkmal.equals("S0"))
        {
            lvStatus = "V";
        }
        if (pvAusplatzierungsmerkmal.equals("S1") || pvAusplatzierungsmerkmal.equals("S3"))
        {
            lvStatus = "S";
        }
        if (pvAusplatzierungsmerkmal.equals("S2"))
        {
            lvStatus = "3";
        }
        if (pvAusplatzierungsmerkmal.equals("S4"))
        {
            lvStatus = "7";
        }
        
        // Flugzeugbestand
        if (pvAusplatzierungsmerkmal.equals("F0"))
        {
            lvStatus = "W";
        }
        if (pvAusplatzierungsmerkmal.equals("F1") || pvAusplatzierungsmerkmal.equals("F3"))
        {
            lvStatus = "U";
        }
        if (pvAusplatzierungsmerkmal.equals("F2"))
        {
            lvStatus = "4";
        }
        if (pvAusplatzierungsmerkmal.equals("F4"))
        {
            lvStatus = "8";
        }
       
        // Hypothekenbestand
        if (pvAusplatzierungsmerkmal.equals("H0"))
        {
            lvStatus = "F";
        }
        if (pvAusplatzierungsmerkmal.equals("H1") || pvAusplatzierungsmerkmal.equals("H3"))
        {
            lvStatus = "D";
        }
        if (pvAusplatzierungsmerkmal.equals("H2"))
        {
            lvStatus = "2";
        }
        if (pvAusplatzierungsmerkmal.equals("H4"))
        {
            lvStatus = "6";
        }
        
        // Kommunalbestand
        if (pvAusplatzierungsmerkmal.equals("K0"))
        {
            lvStatus = "F";
        }
        if (pvAusplatzierungsmerkmal.equals("K1") || pvAusplatzierungsmerkmal.equals("K3"))
        {
            lvStatus = "D";
        }
        if (pvAusplatzierungsmerkmal.equals("K2"))
        {
            lvStatus = "1";
        }
        if (pvAusplatzierungsmerkmal.equals("K4"))
        {
            lvStatus = "5";
        }

        // Abgegangen/Ausplatzierungsmerkmal '99'
        if (pvAusplatzierungsmerkmal.endsWith("99"))
        {
            if (pvQuellsystem.startsWith("PDARL") || pvQuellsystem.contains("MAVI") || pvQuellsystem.startsWith("ADAWP") || pvQuellsystem.contains("LIQ"))
            {
                lvStatus = "N";
            }
            else
            {
              // Abgegangen wird nur zurueckgemeldet, wenn die Solldeckung/Bis-Betrag > 0.0 ist
              if (StringKonverter.convertString2Double(pvBis) > 0.0)
              {
                lvStatus = "N";
              }
            }
        }

        return lvStatus;
    } 

    /**
     * Ermittelt den Deckungsstatus fuer SAPCMS anhand des Ausplatzierungsmerkmals
     * @param pvAusplatzierungsmerkmal
     * @param pvBis
     * @param pvQuellsystem
     * @return
     */
    private String ermittleDeckungsstatusSAPCMS(String pvAusplatzierungsmerkmal)
    {
        String lvStatus = new String();
        
        // Ausplatzierungsmerkmal -> Deckungskennzeichen
        // O0 -> A
        // O1 -> A
        // O2 -> A
        // O3 -> A
        // O4 -> A
        // S0 -> V
        // S1 -> S
        // S2 -> 3
        // S3 -> S
        // S4 -> 7
        // F0 -> W
        // F1 -> U
        // F2 -> 4
        // F3 -> U
        // F4 -> 8
        // H0 -> F
        // H1 -> D
        // H2 -> 2
        // H3 -> D
        // H4 -> 6
        // K0 -> F
        // K1 -> D
        // K2 -> 1
        // K3 -> D
        // K4 -> 5
        // 99 -> N

        // Altbestand 
        // O1, O3 und O4 auf 'A'
        // O2 auf '0'
        // O0 wird nicht zurueckgemeldet
        if (pvAusplatzierungsmerkmal.startsWith("O"))
        {
            if (pvAusplatzierungsmerkmal.equals("O1") || pvAusplatzierungsmerkmal.equals("O3"))
            {
              lvStatus = "A";
            }
            if (pvAusplatzierungsmerkmal.equals("O2"))
            {
              lvStatus = "0";
            }
            if (pvAusplatzierungsmerkmal.equals("O4"))
            {
              lvStatus = "9";
            }
        }
        
        // Schiffsbestand
        if (pvAusplatzierungsmerkmal.equals("S0"))
        {
            lvStatus = "V";
        }
        if (pvAusplatzierungsmerkmal.equals("S1") || pvAusplatzierungsmerkmal.equals("S3"))
        {
            lvStatus = "S";
        }
        if (pvAusplatzierungsmerkmal.equals("S2"))
        {
            lvStatus = "3";
        }
        if (pvAusplatzierungsmerkmal.equals("S4"))
        {
            lvStatus = "7";
        }
        
        // Flugzeugbestand
        if (pvAusplatzierungsmerkmal.equals("F0"))
        {
            lvStatus = "W";
        }
        if (pvAusplatzierungsmerkmal.equals("F1") || pvAusplatzierungsmerkmal.equals("F3"))
        {
            lvStatus = "U";
        }
        if (pvAusplatzierungsmerkmal.equals("F2"))
        {
            lvStatus = "4";
        }
        if (pvAusplatzierungsmerkmal.equals("F4"))
        {
            lvStatus = "8";
        }
       
        // Hypothekenbestand
        if (pvAusplatzierungsmerkmal.equals("H0"))
        {
            lvStatus = "F";
        }
        if (pvAusplatzierungsmerkmal.equals("H1") || pvAusplatzierungsmerkmal.equals("H3"))
        {
            lvStatus = "D";
        }
        if (pvAusplatzierungsmerkmal.equals("H2"))
        {
            lvStatus = "2";
        }
        if (pvAusplatzierungsmerkmal.equals("H4"))
        {
            lvStatus = "6";
        }
        
        // Kommunalbestand
        if (pvAusplatzierungsmerkmal.equals("K0"))
        {
            lvStatus = "F";
        }
        if (pvAusplatzierungsmerkmal.equals("K1") || pvAusplatzierungsmerkmal.equals("K3"))
        {
            lvStatus = "D";
        }
        if (pvAusplatzierungsmerkmal.equals("K2"))
        {
            lvStatus = "1";
        }
        if (pvAusplatzierungsmerkmal.equals("K4"))
        {
            lvStatus = "5";
        }

        // Abgegangen/Ausplatzierungsmerkmal '99'
        if (pvAusplatzierungsmerkmal.endsWith("99"))
        {
            lvStatus = "N";
        }

        return lvStatus;
    }   
    
	/**
     * Liest die RueckmeldungDarKaObjekte ein
     * @param pvDateiname
     */
    private void leseRueckmeldungDarKaObjekte(String pvDateiname)
    {
        LOGGER.info("Start - leseRueckmeldungDarKaObjekte");
        String lvZeile = null;
        int lvZeilenNummer = 0;          
        
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File lvFileRueckmeldungDarKaObjekte = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(lvFileRueckmeldungDarKaObjekte);
        }
        catch (Exception e)
        {
            LOGGER.info("Konnte RueckmeldungDarKaObjekte-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen RueckmeldungDarKaObjekte-Datei
            {
            	lvZeilenNummer++;
            	RueckmeldungDarKaObjekt lvRueckmeldungDarKaObjekt = new RueckmeldungDarKaObjekt();
            	lvRueckmeldungDarKaObjekt.parseRueckmeldungDarKaObjekt(lvZeile);
            	ivListeRueckmeldungDarKaObjekte.add(lvRueckmeldungDarKaObjekt);
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Fehler beim Verarbeiten einer Zeile!");
            LOGGER.error("Zeile: " + lvZeile);
            e.printStackTrace();
        }
              
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte RueckmeldungDarKaObjekte-Datei nicht schliessen!");
        } 
        LOGGER.info("Anzahl eingelesener Zeilen: " + lvZeilenNummer);
        LOGGER.info("Anzahl eingelesener RueckmeldungDarKaObjekte: " + ivListeRueckmeldungDarKaObjekte.size());
     }

    /**
     * Prueft, ob es ein Attribute 'shpo' gibt.
     * @return true -> Attribute 'shpo' existiert, ansonsten false
     */
    private boolean hasShPo()
    {
        Element lvRootNode = ivDocument.getRootElement();
        
        List<Element> lvListSlice = lvRootNode.getChildren();
        Element lvNodeSlice = (Element)lvListSlice.get(0);  
        
        Attribute lvAttributeShpo = lvNodeSlice.getAttribute("shpo");
        
        if (lvAttributeShpo == null)
        {
        	return false;
        }
        else
        {
        	return true;
        }
    }
}
