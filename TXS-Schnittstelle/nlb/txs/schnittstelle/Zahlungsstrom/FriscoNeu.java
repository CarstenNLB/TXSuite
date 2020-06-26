/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Zahlungsstrom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Stefan Unnasch
 *
 * Diese Klasse verarbeitet aus FRISCO stammende XML-Dateien mit Zahlungsströmen und
 * überführt diese in das XML-Eingangsformat für TXS
 *
 *Beispiel für Eingang:
 *
 *<cashflow>
 *<vorlauf>
 *<inst>009</inst>
 *<bestdat>20130920</bestdat>
 *<erstelldat>20130921</erstelldat>
 *<zeit>04415360</zeit>
 *<version>01.00 </version>
 *<typ>RWX2023E</typ>
 *</vorlauf>
 *<konto>
 *<stamm  nr="1234567890" ap="A" anw="30" isin="" prod="1002" kntra="00080260" />
 *<elem dat="20131231" art="11" iso="EUR" betr="4714.11" />
 *<elem dat="20131231" art="21" iso="EUR" betr="614.48" />
 *...
 *...
 *...
 *<elem dat="20160630" art="11" iso="EUR" betr="4714.13" />
 *<elem dat="20160630" art="21" iso="EUR" betr="102.41" />
 *</konto>
 *<konto>
 *
 *Beispiel für Ausgang:
 *
 *<?xml version="1.0" encoding="UTF-8"?>
 *<txsi:importdaten xmlns:txsi="http://agens.com/txsimport.xsd">
 *  <header valdate="2013-09-24" />
 *  <txsi:fg org="29050000" quelle="DARLEHEN" key="1234567890">
 *    <txsi:cfdaten cfkey="1234567890_20130930" datum="2013-09-30" tbetrag="81865.48" zbetrag="12153.43" whrg="EUR" />
 *    <txsi:cfdaten cfkey="1234567890_20131031" datum="2013-10-31" tbetrag="82216.82" zbetrag="11802.09" whrg="EUR" />
 *  </txsi:fg>
 *</txsi:importdaten>
 *
 */

public class FriscoNeu extends DefaultHandler {

    private static Logger LOGGER = LogManager.getLogger("TXSFriscoLogger");
    
    private String temp_puffer ="";
  
    private String cv_stamm_nr = "";
    private String cv_stamm_ap = "";
    private String cv_stamm_anw = "";
    private String cv_stamm_isin = "";
    private String cv_stamm_prod = "";
    private double cv_proz = 0.00;
    private String cv_mantilg = "";
    private String cv_manzins = "";
    
    private long cv_mehrzeilig;
    
    private String cv_ABL17dat; 
    
    static long lvAW30Acnt=0, lvAW31Acnt=0, lvAW85Acnt=0;
    static long lvAW30Pcnt=0, lvAW31Pcnt=0, lvAW85Pcnt=0;
    
    //static long lvDPP_cnt = 0;
    static long lvOEPG_cnt = 0;
    static long lvPFBG_cnt = 0;
    //static long lvSchiff_cnt = 0;
    //static long lvFlug_cnt = 0;
    //static long lvMAVIS_cnt = 0;
    //static long lvDarlWP_cnt = 0;
    
    private static String lvMyDatum = "0000-00-00";
    private static String cvQuellen ="";
    
    /**
     * Liste für die Zahlungen pro Konto
    */
    static List<Element> lvAblauflist = new ArrayList<Element>();
        
    // Zur Ausgabe, der Aufbau ist der TXS Schnittstellenbeschreibung zu entnehmen  
    static Element lvMyrootelement = new Element("importdaten");
    static Namespace lvMyNamespace = Namespace.getNamespace("txsi", "http://agens.com/txsimport.xsd");
    static org.jdom2.Document lvMydocument = new Document(lvMyrootelement);
    static Element lvMyelement2 = new Element("header");
    
    static Element cvElement_fg;
    
    static int lvKontogefunden;
    static int lvKontogefunden_cnt = 0;
    static int lvKontonichtgefunden_cnt = 0;
    
    /**
     * Statistikvariablen
     */
    private static BigDecimal ivSummeAblaufart17ADARLPFBG = new BigDecimal("0.0");
    //private static BigDecimal ivSummeAblaufart17ADARLFLUG = new BigDecimal("0.0");
    //private static BigDecimal ivSummeAblaufart17ADARLSCHF = new BigDecimal("0.0");
    
    /**
    * nimmt Kontonummer oder ISIN und dazu das Quellsystem auf
    */
    static class icKeyQuelle
    {
        String ivKey;
        String ivOrg;
        String ivQuelle;
        double ivProz;
        String ivKontozustand;
        String ivManTilg;
        String ivManZins;
        String ivFaelligkeit;
    }
    
    /**
     * Liste aller Konten 
     */
    static HashMap<String, icKeyQuelle> cvListeKontenQuelle = new HashMap<String, icKeyQuelle>();
  
    /**
     * @param args args[0] .ini Datei, z.B. C:\Temp\txs_batch.ini
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     */
    public static void main(String[] args) 
    throws IOException, SAXException, ParserConfigurationException  
    {   
        String lvLog4jprop ="";
        
        String lvInstitut = "";
        
        String lvFileout ="";
              
        String lvEingabeverzeichnis   ="";
        String lvLogdateiname         ="";
        String lvMaske                ="";
        
        // alles für Liste DPP
        //String lvDPPDatei             ="";  
        //String lvDPPPfad              ="";  
        
        // alles für Liste DARLEHEN 
        String lvDarlDatei            ="";  
        String lvDarlPfad             ="";  
        
        // alles für Liste Flug 
        //String lvFlugDatei            ="";  
        //String lvFlugPfad             ="";  
        
        // alles für Liste Schiffe 
        //String lvSchiffDatei          ="";  
        //String lvSchiffPfad           ="";  
        
        // alles für Liste OEPG 
        String lvOEPGDatei            ="";  
        String lvOEPGPfad             ="";  
        
        // alles für Liste MAVIS 
        //String lvMAVISDatei            ="";  
        //String lvMAVISPfad             ="";  
        
        // alles für Liste Darlehen WP
        //String lvDarlWPDatei            ="";  
        
        FileInputStream     lvStreamlogein    = null;
        FileOutputStream    lvStreamlogaus    = null;
             
        // prüfen, ob die .ini Datei übergeben wurde
        if (!args[args.length - 1].endsWith(".ini"))
        {
            System.out.println("Fehler, Aufruf mit ini-Datei");
            System.out.println("FriscoNeu <ini-Datei>");
            System.exit(1);
        }
        
        // .ini lesen
        IniReader lvReader = null;
        try 
        {
            lvReader = new IniReader(args[args.length - 1]);
            
            //if (null == lvReader)
            //{
            //    System.out.println("Fehler beim Laden der ini-Datei: lvReader == null");
            //    System.exit(0);
            //}
        }
        catch (Exception exp)
        {
            System.out.println("Fehler beim Laden der ini-Datei: IniReader exception");
            System.exit(1);
        }
        
        // zuerst log4 mit property versorgen
        lvLog4jprop = lvReader.getPropertyString("FRISCO", "log4jconfig", "Fehler");
        if (lvLog4jprop.equals("Fehler"))
        {
            System.out.println("Kein [FRISCO][log4jconfig] in der ini-Datei, logging auf console");
            BasicConfigurator.configure();
            
        }
        else
        {
            System.out.println("log4jconfig=" + lvLog4jprop);  
            
            DOMConfigurator.configure(lvLog4jprop); 
        }
        
        LOGGER.info("log4j konfiguriert");
        
        LOGGER.info("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        
        // Institut aus .ini lesen
        lvInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
        
        if (lvInstitut.equals("Fehler"))
        {
            //System.out.println("Keine Institutsnummer in der ini-Datei...");
            LOGGER.error("Keine Institutsnummer in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("Institut=" + lvInstitut);                    
        }
        
        // Name der Ausgabedatei aus .ini lesen
        lvFileout = lvReader.getPropertyString("FRISCO", "FRISCOAUS", "Fehler");
        if (lvFileout.equals("Fehler"))
        {
            LOGGER.error("Kein [FRISCO][FRISCOAUS] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("FRISCOAUS=" + lvFileout);                    
        }
        
        // Name der Eingabeverzeichnisses aus .ini lesen
        lvEingabeverzeichnis = lvReader.getPropertyString("FRISCO", "FRISCODIR", "Fehler");
        if (lvEingabeverzeichnis.equals("Fehler"))
        {
            LOGGER.error("Kein [FRISCO][FRISCODIR] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("FRISCODIR=" + lvEingabeverzeichnis);                    
        }
        
        // Name des Protokolldatei aus .ini lesen
        lvLogdateiname = lvReader.getPropertyString("FRISCO", "FRISCOPRO", "Fehler");
        if (lvLogdateiname.equals("Fehler"))
        {
            LOGGER.error("Kein [FRISCO][FRISCOPRO] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("FRISCOPRO=" + lvLogdateiname);                    
        }
        
        // Maske für Eingangsdatei aus .ini lesen
        lvMaske = lvReader.getPropertyString("FRISCO", "FRISCOMSK", "Fehler");
        if (lvLogdateiname.equals("Fehler"))
        {
            LOGGER.error("Kein [FRISCO][FRISCOMSK] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("FRISCOMSK=" + lvMaske);                    
        }
                
        // Liste der zu verwendenden Quellsysteme lesen
        cvQuellen = lvReader.getPropertyString("FRISCO", "FRISCOQUELLEN", "Fehler");
        if (cvQuellen.equals("Fehler"))
        {
            LOGGER.error("Kein [FRISCO][FRISCOQUELLEN] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("FRISCOQUELLEN=" + cvQuellen);                    
        }
        
        LOGGER.info("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        /*
        // Pfad zur Deckungspoolingdatei aus .ini lesen
        lvDPPPfad = lvReader.getPropertyString("Deckungspooling", "ExportVerzeichnis", "Fehler");
        if (lvDPPPfad.equals("Fehler"))
        {
            LOGGER.error("Kein [Deckungspooling][ExportVerzeichnis] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("DPP PFAD=" + lvDPPPfad);                    
        }
        
        // Name der Deckungspoolingdatei aus .ini lesen
        lvDPPDatei = lvReader.getPropertyString("Deckungspooling", "Quellsystem-Datei", "Fehler");
        if (lvDPPDatei.equals("Fehler"))
        {
            LOGGER.error("Kein [Deckungspooling][Quellsystem-Datei] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("DPP DATEI=" + lvDPPDatei);                    
        }
        
        lvDPPDatei = lvDPPPfad.replace("\\", "/") + '/' + lvDPPDatei;
        LOGGER.info("DPP " + lvDPPDatei);
        
        // Deckungspooling Eingabe öffnen und in liste lesen
        try
        {
            FileReader lvDPPFileReader = new FileReader(lvDPPDatei);
            BufferedReader lvDPPBufferedReader = new BufferedReader(lvDPPFileReader);
            
            String lvDPPline ="";
            
            while ((lvDPPline = lvDPPBufferedReader.readLine()) != null)   
            {  
                icKeyQuelle lvDPPkq = new icKeyQuelle(); 
                
                // Alle Konten aus allen Dateien in eine Liste
                lvDPPkq.ivOrg = lvDPPline.substring(22,27);
                
                //lvDPPkq.ivKey = lvDPPkq.ivOrg + "_" + lvDPPline.substring(0,10);
                lvDPPkq.ivKey = lvDPPline.substring(0,10);
                
                lvDPPkq.ivQuelle = "DPP";
                lvDPPkq.ivKontozustand = new String();
                
                cvListeKontenQuelle.put(lvDPPkq.ivKey, lvDPPkq);
                lvDPP_cnt++;
            }  
            
            LOGGER.info("DPP Konten=" + lvDPP_cnt);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte DPP " + lvDPPDatei + " nicht öffnen");
        } */
        
        LOGGER.info("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        /*
        // Pfad zur Schiffsdatei aus .ini lesen
        lvSchiffPfad = lvReader.getPropertyString("Schiffe", "ExportVerzeichnis", "Fehler");
        if (lvSchiffPfad.equals("Fehler"))
        {
            LOGGER.error("Kein [Schiffe][ExportVerzeichnis] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("Schiffe PFAD=" + lvSchiffPfad);                    
        }
        
        // Name der Schiffsdatei aus .ini lesen
        lvSchiffDatei = lvReader.getPropertyString("Schiffe", "Quellsystem-Datei", "Fehler");
        if (lvSchiffDatei.equals("Fehler"))
        {
            LOGGER.error("Kein [Schiffe][Quellsystem-Datei] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("Schiffe DATEI=" + lvSchiffDatei);                    
        }
        
        lvSchiffDatei = lvSchiffPfad.replace("\\", "/") + '/' + lvSchiffDatei;
        LOGGER.info("Schiffe " + lvSchiffDatei);
        
        // Schiff Eingabe öffnen und in liste lesen
        try
        {
            FileReader lvSchiffFileReader = new FileReader(lvSchiffDatei);
            BufferedReader lvSchiffBufferedReader = new BufferedReader(lvSchiffFileReader);
            
            String lvSchiffline ="";
            
            while ((lvSchiffline = lvSchiffBufferedReader.readLine()) != null)   
            {  
                icKeyQuelle lvSchiffkq = new icKeyQuelle(); 
                
                lvSchiffkq.ivKey = lvSchiffline.substring(0,10);
                lvSchiffkq.ivOrg = lvSchiffline.substring(22,30);
                
                lvSchiffkq.ivQuelle = lvSchiffline.substring(31,40);
                //lvSchiffkq.ivQuelle = "ADARLSCHF";
                lvSchiffkq.ivKontozustand = lvSchiffline.substring(56, 57);
                
                cvListeKontenQuelle.put(lvSchiffkq.ivKey, lvSchiffkq);
                lvSchiff_cnt++;
            }  
            
            LOGGER.info("Schiff Konten=" + lvSchiff_cnt );
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Schiff " + lvSchiffDatei + " nicht öffnen");
        }    
        */
        LOGGER.info("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        /*
        // Pfad zur Flugzeugdatei aus .ini lesen
        lvFlugPfad = lvReader.getPropertyString("Flugzeuge", "ExportVerzeichnis", "Fehler");
        if (lvFlugPfad.equals("Fehler"))
        {
            LOGGER.error("Kein [Flugzeug][ExportVerzeichnis] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("Flugzeug PFAD=" + lvFlugPfad);                    
        }
        
        // Name der Flugzeugdatei aus .ini lesen
        lvFlugDatei = lvReader.getPropertyString("Flugzeuge", "Quellsystem-Datei", "Fehler");
        if (lvFlugDatei.equals("Fehler"))
        {
            LOGGER.error("Kein [Flugzeug][Quellsystem-Datei] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("Flugzeug DATEI=" + lvFlugDatei);                    
        }
        
        lvFlugDatei = lvFlugPfad.replace("\\", "/") + '/' + lvFlugDatei;
        LOGGER.info("Flugzeug " + lvFlugDatei);
        
        // Flugzeug Eingabe öffnen und in liste lesen
        try
        {
            FileReader lvFlugFileReader = new FileReader(lvFlugDatei);
            BufferedReader lvFlugBufferedReader = new BufferedReader(lvFlugFileReader);
            
            String lvFlugline ="";
            
            while ((lvFlugline = lvFlugBufferedReader.readLine()) != null)   
            {  
                icKeyQuelle lvFlugkq = new icKeyQuelle();
                
                lvFlugkq.ivKey = lvFlugline.substring(0,10);
                lvFlugkq.ivOrg = lvFlugline.substring(22,30);    
                
                lvFlugkq.ivQuelle = lvFlugline.substring(31,40);
                //lvFlugkq.ivQuelle = "ADARLFLUG";
                lvFlugkq.ivKontozustand = lvFlugline.substring(56, 57);
                
                cvListeKontenQuelle.put(lvFlugkq.ivKey, lvFlugkq);
                lvFlug_cnt++;
            }  
            
            LOGGER.info("Flug Konten=" + lvFlug_cnt );
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Flug " + lvFlugDatei + " nicht öffnen");
        }    
        */
        LOGGER.info("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        
        // Pfad zur OEPGdatei aus .ini lesen
        lvOEPGPfad = lvReader.getPropertyString("OEPG", "ExportVerzeichnis", "Fehler");
        if (lvOEPGPfad.equals("Fehler"))
        {
            LOGGER.error("Kein [OEPG][ExportVerzeichnis] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("OEPG PFAD=" + lvOEPGPfad);                    
        }
        
        // Name der OEPGdatei aus .ini lesen
        lvOEPGDatei = lvReader.getPropertyString("OEPG", "Quellsystem-Datei", "Fehler");
        if (lvOEPGDatei.equals("Fehler"))
        {
            LOGGER.error("Kein [OEPG][Quellsystem-Datei] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("OEPG DATEI=" + lvOEPGDatei);                    
        }
        
        lvOEPGDatei = lvOEPGPfad.replace("\\", "/") + '/' + lvOEPGDatei;
        LOGGER.info("OEPG " + lvOEPGDatei);
        
        // OEPG Eingabe öffnen und in liste lesen
        try
        {
            FileReader lvOEPGFileReader = new FileReader(lvOEPGDatei);
            BufferedReader lvOEPGBufferedReader = new BufferedReader(lvOEPGFileReader);
            
            String lvOEPGline ="";
            
            while ((lvOEPGline = lvOEPGBufferedReader.readLine()) != null)   
            {  
                // Alle Konten aus allen Dateien in eine Liste
                
                icKeyQuelle lvOEPGkq = new icKeyQuelle(); 
                
                lvOEPGkq.ivKey = lvOEPGline.substring(0,10);
                lvOEPGkq.ivOrg = lvOEPGline.substring(22,30);
                
                lvOEPGkq.ivQuelle = lvOEPGline.substring(31,40);
                //lvOEPGkq.ivQuelle = "ADARLOEPG";
                lvOEPGkq.ivKontozustand = lvOEPGline.substring(56, 57);
                lvOEPGkq.ivManTilg = lvOEPGline.substring(58, 59);
                //System.out.println("ManTilg: " + lvDarlkq.ivManTilg);
                lvOEPGkq.ivManZins = lvOEPGline.substring(60, 61);
                //System.out.println("ManZins: " + lvDarlkq.ivManZins);

                
                cvListeKontenQuelle.put(lvOEPGkq.ivKey, lvOEPGkq);
                lvOEPG_cnt++;
            }  
            
            LOGGER.info("OEPG Konten=" + lvOEPG_cnt );
            lvOEPGBufferedReader.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte OEPG " + lvOEPGDatei + " nicht öffnen");
        }    
            
        LOGGER.info("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        
        // lesen der Parameter aus der .ini-Datei ist hier abgeschlossen
        
        // Pfad zur Darlehensdatei aus .ini lesen
        lvDarlPfad = lvReader.getPropertyString("Darlehen", "ExportVerzeichnis", "Fehler");
        if (lvDarlPfad.equals("Fehler"))
        {
            LOGGER.error("Kein [Darlehen][ExportVerzeichnis] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("DARL PFAD=" + lvDarlPfad);                    
        }
        
        // Name der Darlehensdatei aus .ini lesen
        lvDarlDatei = lvReader.getPropertyString("Darlehen", "Quellsystem-Datei", "Fehler");
        if (lvDarlDatei.equals("Fehler"))
        {
            LOGGER.error("Kein [Deckungspooling][Quellsystem-Datei] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("DARL DATEI=" + lvDarlDatei);                    
        }
        
        lvDarlDatei = lvDarlPfad.replace("\\", "/") + '/' + lvDarlDatei;
        LOGGER.info("DARL " + lvDarlDatei);
            
        // lesen der Parameter aus der .ini-Datei ist hier abgeschlossen
        
        // Darlehens Eingabe öffnen und in liste lesen
        try
        {
            FileReader lvDarlFileReader = new FileReader(lvDarlDatei);
            BufferedReader lvDarlBufferedReader = new BufferedReader(lvDarlFileReader);
            
            String lvDarlline ="";
            
            while ((lvDarlline = lvDarlBufferedReader.readLine()) != null)   
            {  
                // Alle Konten aus allen Dateien in eine Liste
                
                icKeyQuelle lvDarlkq = new icKeyQuelle(); 
                
                lvDarlkq.ivKey = lvDarlline.substring(0,10);
                lvDarlkq.ivOrg = lvDarlline.substring(22,30);    
                
                if ( ("000.00000000".equals(lvDarlline.substring(43,55))) ||
                     ("100.00000000".equals(lvDarlline.substring(43,55)))   )
                {
                  lvDarlkq.ivProz = 0.00;
                }
                else
                {    
                  lvDarlkq.ivProz = Double.parseDouble(lvDarlline.substring(43,55));
                }
                 
                  lvDarlkq.ivQuelle = lvDarlline.substring(31,40);
               // lvDarlkq.ivQuelle = "ADARLPFBG";
               // lvDarlkq.ivQuelle = "DARLEHEN";
                  //System.out.println("Kontozustand: " + lvDarlline.substring(56, 57));
                  lvDarlkq.ivKontozustand = lvDarlline.substring(56, 57);
                  lvDarlkq.ivManTilg = lvDarlline.substring(58, 59);
                  //System.out.println("ManTilg: " + lvDarlkq.ivManTilg);
                  lvDarlkq.ivManZins = lvDarlline.substring(60, 61);
                  //System.out.println("ManZins: " + lvDarlkq.ivManZins);
                
                cvListeKontenQuelle.put(lvDarlkq.ivKey, lvDarlkq);
                lvPFBG_cnt++;
            }  
            
            LOGGER.info("DARL Konten=" + lvPFBG_cnt );
            lvDarlBufferedReader.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte DARL " + lvDarlDatei + " nicht öffnen");
        }    
        
        LOGGER.info("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        
        //////////////////////////////////////////////////////////////////////////////////////////////
        // Pfad zur MAVIS Quellsystemdatei aus .ini lesen
        //////////////////////////////////////////////////////////////////////////////////////////////
        /*
        lvMAVISPfad = lvReader.getPropertyString("MAVIS", "MAVISDIR", "Fehler");
        if (lvMAVISPfad.equals("Fehler"))
        {
            LOGGER.error("Kein [MAVIS][MAVISDIR] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("MAVIS PFAD=" + lvMAVISPfad);                    
        }
        
        // Name der MAVIS Quellsystemdatei aus .ini lesen
        lvMAVISDatei = lvReader.getPropertyString("MAVIS", "MAVQUELL", "Fehler");
        if (lvMAVISDatei.equals("Fehler"))
        {
            LOGGER.error("Kein [MAVIS][MAVQUELL] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("MAVIS DATEI=" + lvMAVISDatei);                    
        }
        
        lvMAVISDatei = lvMAVISPfad + lvMAVISDatei;
        
        // MAVIS Quellsystemdatei öffnen und in liste lesen
        try
        {
            FileReader lvMAVISFileReader = new FileReader(lvMAVISDatei);
            BufferedReader lvMAVISBufferedReader = new BufferedReader(lvMAVISFileReader);
            
            String lvMAVISline ="";
            
            while ((lvMAVISline = lvMAVISBufferedReader.readLine()) != null)   
            {  
                icKeyQuelle lvMAVISkq = new icKeyQuelle(); 
                
                // Alle Konten aus allen Dateien in eine Liste
                lvMAVISkq.ivKey = lvMAVISline.substring(0,12);
                lvMAVISkq.ivOrg = lvMAVISline.substring(13,21);
                lvMAVISkq.ivQuelle = lvMAVISline.substring(22,31);
                
                lvMAVISkq.ivKontozustand = new String();
                
                cvListeKontenQuelle.put(lvMAVISkq.ivKey, lvMAVISkq);
                lvMAVIS_cnt++;
            }  
            
            LOGGER.info("MAVIS Konten=" + lvMAVIS_cnt);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte MAVIS " + lvMAVISDatei + " nicht öffnen");
        }  */
        //////////////////////////////////////////////////////////////////////////////////////////////
        
        LOGGER.info("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        
        //////////////////////////////////////////////////////////////////////////////////////////////
        // Name der DarlWP Quellsystemdatei aus .ini lesen
        //////////////////////////////////////////////////////////////////////////////////////////////
        /*
        // Name der DarlWP Quellsystemdatei aus .ini lesen
        lvDarlWPDatei = lvReader.getPropertyString("DarlWP", "DARLWPQUELL", "Fehler");
        if (lvDarlWPDatei.equals("Fehler"))
        {
            LOGGER.error("Kein [DarlWP][DARLWPQUELL] in der ini-Datei...");
            System.exit(1);
        }
        else
        {
            LOGGER.info("DarlWP DATEI=" + lvDarlWPDatei);                    
        }
        
        // DarlWP Quellsystemdatei öffnen und in liste lesen
        try
        {
            FileReader lvDarlWPFileReader = new FileReader(lvDarlWPDatei);
            BufferedReader lvDarlWPBufferedReader = new BufferedReader(lvDarlWPFileReader);
            
            String lvDarlWPline ="";
            
            while ((lvDarlWPline = lvDarlWPBufferedReader.readLine()) != null)   
            {  
                icKeyQuelle lvDarlWPkq = new icKeyQuelle(); 
                
                // Alle Konten aus allen Dateien in eine Liste
                lvDarlWPkq.ivKey = lvDarlWPline.substring(0,10);
                lvDarlWPkq.ivOrg = lvDarlWPline.substring(11,19);
                lvDarlWPkq.ivQuelle = lvDarlWPline.substring(20,29);
                
                lvDarlWPkq.ivKontozustand = lvDarlWPline.substring(30, 31);
                
                cvListeKontenQuelle.put(lvDarlWPkq.ivKey, lvDarlWPkq);
                lvDarlWP_cnt++;
            }  
            
            LOGGER.info("DarlWP Konten=" + lvDarlWP_cnt);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte DarlWP " + lvDarlWPDatei + " nicht öffnen");
        } */
        //////////////////////////////////////////////////////////////////////////////////////////////
        
        LOGGER.info("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>");
        
        // Jetzt wird anhand der Dateimaske und der Protokolldatei bestimmt, welche Eingangsdatei im Verzeichnis die zu verarbeitende ist
        // Es soll die sein, die den höchsten daypointer hat und nicht in der Protokolldatei steht
        
        File lvEingabedir = new File(lvEingabeverzeichnis);
        File lvLogdatei = new File(lvEingabeverzeichnis+lvLogdateiname);
        
        String[] lvListing = lvEingabedir.list();
        
        String lvNeuedatei = lvMaske + ".D000000";
        
        for (int n = 0; n < lvListing.length; n++)
        {
       //   System.out.println("listing " + listing[n]); 
                      
          try
          {
            if (lvListing[n].substring(0,lvMaske.length()).equals(lvMaske))
            {
                    
              String lvNeuD = lvListing[n].substring(lvListing[n].lastIndexOf(".D")+2, lvListing[n].lastIndexOf(".D")+8);
              String lvAltD = lvNeuedatei.substring(lvNeuedatei.lastIndexOf(".D")+2,lvNeuedatei.lastIndexOf(".D")+8); 
                    
              if ( lvNeuD.compareTo(lvAltD) > 0)
              {
                lvNeuedatei = lvListing[n];
                int lvDatanfang = lvNeuedatei.lastIndexOf(".D")+2;
                lvMyDatum =  "20" + lvNeuedatei.substring(lvDatanfang, lvDatanfang+2)  +
                             "-" + lvNeuedatei.substring(lvDatanfang+2, lvDatanfang+4) +
                             "-" + lvNeuedatei.substring(lvDatanfang+4, lvDatanfang+6)  ;
                
              }
            }
          }
          catch( java.lang.StringIndexOutOfBoundsException e)
          {
              LOGGER.error("Dateiname kürzer als A1PBAT.NT.I009.LMTXS.KSXML");   
          }
        }
        
        LOGGER.info("Datei mit höchstem daypointer " + lvNeuedatei);
        
        // logdatei zur eingabe öffnen
        try
        {
          lvStreamlogein = new FileInputStream(lvLogdatei); 
        }
        catch (Exception e)
        {
          LOGGER.error("Konnte Protokolldatei " + lvEingabeverzeichnis + lvLogdateiname + " nicht öffnen");
        }
       
        int lvDateischoninlog = 0;
        
        InputStreamReader lvLogISR = new InputStreamReader(lvStreamlogein); 
        BufferedReader lvLogBR = new BufferedReader(lvLogISR); 
        
        String lvMylogline;
        try
        {
            while (null != (lvMylogline = lvLogBR.readLine()))
            {
                if (lvMylogline.equals(lvNeuedatei))
                {
                    lvDateischoninlog = 1; 
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Ärger mit dem bufferedreader");
        }
        
        if (1 == lvDateischoninlog)
        {
            LOGGER.error("bereits in Protokoll vorhanden, für erneute Verarbeitung hieraus löschen");
            System.exit(1);
        }
        
        try{lvLogBR.close();}catch(Exception e){};
        try{lvLogISR.close();}catch(Exception e){};
        try{lvStreamlogein.close();}catch(Exception e){};
                
        // Konfiguration usw ist abgeschlossen, jetzt kann die eigentliche Verarbeitung beginnen
        
        // Ausgabestrukturen vorbereiten
        lvMyrootelement.setNamespace(lvMyNamespace);
        lvMyelement2.setAttribute("valdate",lvMyDatum);
        lvMyrootelement.addContent(lvMyelement2);
        
        ////////////////////////////////////////////////////////////////////////////////////////////// 
        // In sp.parse() spielt sich die Hauptverarbeitung ab
        //////////////////////////////////////////////////////////////////////////////////////////////
        
        // Create a "parser factory" for creating SAX parsers
        SAXParserFactory spfac = SAXParserFactory.newInstance();

        // Now use the parser factory to create a SAXParser object
        SAXParser sp = spfac.newSAXParser();

        // Create an instance of this class; it defines all the handler methods
        FriscoNeu handler = new FriscoNeu();

        // Finally, tell the parser to parse the input and notify the handler
        sp.parse(lvEingabeverzeichnis + lvNeuedatei, handler);
        
        //////////////////////////////////////////////////////////////////////////////////////////////    
        // Hauptverarbeitung Ende
        //////////////////////////////////////////////////////////////////////////////////////////////
        
        // Jetzt kann die Ausgabe beginnen
        File lvTXSFile = new File(lvFileout);
        FileOutputStream lvTXSost = null;
        
        try
        {
          lvTXSost = new FileOutputStream(lvTXSFile);
        }
        catch (Exception e)
        {
          System.out.println("Konnte TXS-Ausgabedatei " + lvFileout +" nicht oeffnen!");
          LOGGER.error("Konnte TXS-Ausgabedatei " + lvFileout +" nicht oeffnen!");
        }    
        
        XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());
        
        try
        {
            lvOutputter.output(lvMydocument,lvTXSost);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        // Statistik ausgeben
        LOGGER.info("CFs AW30: aktiv: " + lvAW30Acnt + " passiv: " + lvAW30Pcnt);
        LOGGER.info("CFs AW31: aktiv: " + lvAW31Acnt + " passiv: " + lvAW31Pcnt); 
        LOGGER.info("CFs AW85: aktiv: " + lvAW85Acnt + " passiv: " + lvAW85Pcnt);
        
        LOGGER.info("CF Konten in Quellsystemdateien gefunden:" + lvKontogefunden_cnt);
        LOGGER.info("CF Konten nicht in Quellsystemdateien gefunden:" + lvKontonichtgefunden_cnt);
        
        LOGGER.info("ADARLPFBG - Summe Ablaufart 17: " + ivSummeAblaufart17ADARLPFBG);
        //LOGGER.info("ADARLFLUG - Summe Ablaufart 17: " + ivSummeAblaufart17ADARLFLUG);
        //LOGGER.info("ADARLSCHF - Summe Ablaufart 17: " + ivSummeAblaufart17ADARLSCHF);
        
        //handler.readList();
        
        // nach der Verarbeitung den Namen der verarbeiteten Dateien in die Protokolldatei schreiben
        try
        {
          lvStreamlogaus = new FileOutputStream(lvLogdatei, true); 
          
          lvStreamlogaus.write("-----------------------------------------".getBytes());
          lvStreamlogaus.write(System.getProperty("line.separator").getBytes());
          
          DateFormat lvDateFormatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM);
          Date lvJetzt = new Date();
          String lvDateOut = lvDateFormatter.format(lvJetzt);
               
          lvStreamlogaus.write(lvDateOut.getBytes());
          lvStreamlogaus.write(System.getProperty("line.separator").getBytes());
          
          lvStreamlogaus.write(lvNeuedatei.getBytes());
          lvStreamlogaus.write(System.getProperty("line.separator").getBytes());
          
          lvStreamlogaus.write("-----------------------------------------".getBytes());
          lvStreamlogaus.write(System.getProperty("line.separator").getBytes());
        }
        catch (Exception e)
        {
          LOGGER.error("Konnte logdatei " + lvEingabeverzeichnis + lvLogdateiname + " nicht öffnen");
        }
        
        LOGGER.info("Ende");
    }

    /**
     * wird vom handler beim Start eines XML Elements aufgerufen 
     * @param uri 
     * @param localName 
     * @param qName 
     * @param attributes 
     * @throws SAXException 
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes)
    throws SAXException 
    {
        temp_puffer = "";
        
        if (qName.equalsIgnoreCase("cashflow"))
        {
            LOGGER.info("Dateibeginn");
        }
        else if (qName.equalsIgnoreCase("konto"))
        {
            // Liste mit den Zahlungen zum letzten Konto leeren
            lvAblauflist.clear(); 
            
            // Letztes Datum zu Ablaufart 17 auf Grundstellung >alle setzen
            cv_ABL17dat = "99991231";
        }
        else if (qName.equalsIgnoreCase("stamm"))
        {
            // Spezialfall Konto kommt mehrfach (Konto ! -> NICHT isin) 
            if (cv_stamm_nr.equals(attributes.getValue("nr")))
            {
              cv_mehrzeilig++;
            }
            else
            {
              cv_mehrzeilig = 0;
            }
            
            // Schlüssel bis zum nächsten Konto merken
            cv_stamm_nr    = attributes.getValue("nr");
            cv_stamm_ap    = attributes.getValue("ap");
            cv_stamm_anw   = attributes.getValue("anw");
            cv_stamm_isin  = attributes.getValue("isin");
            cv_stamm_prod  =  attributes.getValue("prod");
            
            //cv_stamm_kntra = attributes.getValue("kntra");

            
            if ("".equals(cv_stamm_isin))
            {
                cv_stamm_isin = cv_stamm_nr;
            }
            
            // Element fg anlegen 
            cvElement_fg = new Element("fg");
            
            String lv_quelle = "nv";
            String lv_org    = "nv";
            String lv_key    = "nv";
            
            // Orginator zuweisen, anderen Orginator für DPP Konten
            // Weitehin ggf. Quellsystem für Aktiva 30/31 auf DPP
            
            lvKontogefunden = 0;
            cv_proz = 0.00;
            
            for (int k=0; k<cvListeKontenQuelle.size(); k++)
            {
             // Normalerweise Kontonummer  
              icKeyQuelle helpKeyQuelle = cvListeKontenQuelle.get(cv_stamm_nr);
              // aber MAVIS isin
              if (helpKeyQuelle == null)
              {
                  helpKeyQuelle = cvListeKontenQuelle.get(cv_stamm_isin);
              }
              //if ((cvListeKontenQuelle.get(k)).ivKey.equals(cv_stamm_nr)  ||  
              //    (cvListeKontenQuelle.get(k)).ivKey.equals(cv_stamm_isin)  ) 
              if (helpKeyQuelle != null)
              {
                //lv_quelle = cvListeKontenQuelle.get(k).ivQuelle;
                lv_quelle = helpKeyQuelle.ivQuelle;
                
                //lv_org = cvListeKontenQuelle.get(k).ivOrg;    
                lv_org = helpKeyQuelle.ivOrg;    
                 
                //cv_proz = cvListeKontenQuelle.get(k).ivProz;
                cv_proz = helpKeyQuelle.ivProz;
                cv_mantilg = helpKeyQuelle.ivManTilg;
                cv_manzins = helpKeyQuelle.ivManZins;
                
                lvKontogefunden = 1;
                lvKontogefunden_cnt++;
               
                break;
              }
            }
            
            if (0==lvKontogefunden)
            {
                lvKontonichtgefunden_cnt++;
              //  LOGGER.info("nicht gefunden  " + cv_stamm_nr + " " + cv_stamm_isin);
            }
           
            // Statistik und unterschiedliche Quellen und keys
            if ("30".equals(cv_stamm_anw))
            {                
                if ("A".equals(cv_stamm_ap))
                {
                    lv_key = cv_stamm_nr;
                    lvAW30Acnt++;
                }
                else if ("P".equals(cv_stamm_ap))
                {   
                    if (lv_org.equals("29050000")) // Spezialbehandlung BLB, siehe auch PassivDarl.java 
                    {
                      lv_key = "DE" + cv_stamm_nr;
                    }  
                    else
                    {    
                      lv_key = cv_stamm_isin;
                    }
                    
                    lvAW30Pcnt++;
                }    
            }
            else if ("31".equals(cv_stamm_anw))
            {
                if ("A".equals(cv_stamm_ap))
                {    
                    lv_key = cv_stamm_nr;
                    lvAW31Acnt++;
                }
                else if ("P".equals(cv_stamm_ap))
                {    
                    if (lv_org.equals("29050000")) // Spezialbehandlung BLB, siehe auch PassivDarl.java
                    {
                      lv_key = "DE" + cv_stamm_nr;
                    }  
                    else
                    {    
                      lv_key = cv_stamm_isin;
                    }
                    lvAW31Pcnt++;
                }    
            }
            else if ("85".equals(cv_stamm_anw))
            {
                if ("A".equals(cv_stamm_ap))
                {    
                    lv_key = cv_stamm_isin;
                    lvAW85Acnt++;
                }
                else if ("P".equals(cv_stamm_ap))
                {    
                    lv_key = cv_stamm_isin;
                    lvAW85Pcnt++;
                }
            }
            
            // alles anhängen
            cvElement_fg.setAttribute("org", lv_org);
            cvElement_fg.setAttribute("quelle", lv_quelle);
            cvElement_fg.setAttribute("key", lv_key);
            
        }
        else if (qName.equalsIgnoreCase("elem"))
        {
            // unbeachtet späterer Zusammenfassungen wird alles schon in ein element gepackt
            Element lvElement_cfdaten = new Element("cfdaten");
            
            lvElement_cfdaten.setAttribute("cfkey",
                    cvElement_fg.getAttributeValue("key") + "_" + attributes.getValue("dat"));
            
            lvElement_cfdaten.setAttribute("datum",DatumUtilities.FormatDatum(attributes.getValue("dat")));
            
            // Jetzt das Herausrechnen der Bürgschaftsanteile
            // Der Prozenntsatz findet sich in der CashflowQuellsystem Daeti
            // Herumrechnen mit String/Bigdecinmal ist etwas umständlich gelöst
            // cv_stamm_prod und cv_proz sind richtig gefüllt, weil pro Konto startElement() vor endElement
            // aufgerufen wird
            
            //System.out.println("xx " + cv_stamm_prod.substring(0,2) + cv_stamm_prod.substring(2,4));
            
            // bestimmte Produkte nicht rechnen (also nicht 02xx,07xx und 06xx aber 0603!)
            // cv_proz = 0 und = 100 ebenfalls nicht rechnen
            if ((("05".equals(cv_stamm_prod.substring(0,2))) ||
                 ("07".equals(cv_stamm_prod.substring(0,2))) ||
                 (("06".equals(cv_stamm_prod.substring(0,2)))) && !("03".equals(cv_stamm_prod.substring(2,4)))) ||
                 (cv_proz == 0.00)                                                                                ) 
            {    
              // Aus "betrag" wird dann nach der Zusammenfassung entweder "tbetrag" oder "zbetrag"
              lvElement_cfdaten.setAttribute("betrag", attributes.getValue("betr"));
            }
            else
            {    
              BigDecimal lvHelper1 = BigDecimal.ZERO;
              BigDecimal lvHelper2 = BigDecimal.ZERO;
              
              lvHelper1 = BigDecimal.valueOf(Double.parseDouble(attributes.getValue("betr")));
              
              lvHelper1 = lvHelper1.divide(BigDecimal.valueOf(100.00));
              
              lvHelper2 = BigDecimal.valueOf(cv_proz);

              lvHelper1 = lvHelper1.multiply(lvHelper2);
              
         //     if ("2308310050".equals(cv_stamm_nr))
         //     {
         //         System.out.println(attributes.getValue("dat") + " " + lvHelper1.toString());
         //     }
              
              //System.out.println("kto=" + cv_stamm_nr + " orig=" + Double.parseDouble(attributes.getValue("betr")) + " neu=" +lvHelper + " proz=" + cv_proz );
            
              // Aus "betrag" wird dann nach der Zusammenfassung entweder "tbetrag" oder "zbetrag"
              lvElement_cfdaten.setAttribute("betrag", lvHelper1.toString());
            }
            
            lvElement_cfdaten.setAttribute("whrg",attributes.getValue("iso"));
           
            // die Art wird später wieder gelöscht
            lvElement_cfdaten.setAttribute("art", attributes.getValue("art"));
            
            // Und an die Liste zum Konto hängen
            lvAblauflist.add((Element) lvElement_cfdaten);
            
            // Wenn es der (eine) Satz mit Ablaufart 17 (Kündigung nach §489) ist, dann das Datum merken
            if ("17".equals(attributes.getValue("art")))
            {
              cv_ABL17dat = attributes.getValue("dat");  
            }
        }
    }
  
    /**
     * wird vom handler beim Ende eines XML Elements aufgerufen
     * @param uri 
     * @param localName 
     * @param qName 
     * @throws SAXException 
     */
    public void endElement(String uri, String localName, String qName) 
    throws SAXException 
    {
        Element lvAblaufelem;
        Element lvAblaufelem2;
        
        String lvDatum1;
        String lvDatum2;
        String lvDatum3;
        
        BigDecimal lvTbetragbd = new BigDecimal(0.00);
        BigDecimal lvZbetragbd = new BigDecimal(0.00);
        BigDecimal lvBufferbd = new BigDecimal(0.00);
        
        if (qName.equalsIgnoreCase("cashflow"))
        {
            LOGGER.info("Dateiende erreicht");
        }
        else if (qName.equalsIgnoreCase("bestdat"))
        {
            LOGGER.info("bestdat=" + temp_puffer); 
            
            // hier kann das document mit dem header angelegt werden
        }
        else if (qName.equalsIgnoreCase("erstelldat"))
        {
            LOGGER.info("erstelldat=" + temp_puffer); 
            
            // hier kann das document mit dem header angelegt werden
        }
        else if (qName.equalsIgnoreCase("inst"))
        {
            LOGGER.info("inst=" + temp_puffer); 
        }
        else if (qName.equalsIgnoreCase("stamm"))
        {
            // System.out.println("hier");
        }
        else if (qName.equalsIgnoreCase("elem"))
        {
            // System.out.println("hier");
        }
        else if (qName.equalsIgnoreCase("konto"))
        {
            // Kontoende erreicht
            // Zunächst alle Elemente herauslöschen, die größer dem Datum einer eventuell
            // vorhandenen Ablaufart 17 sind
            // alle Produkte ausser 02xx !
        	
        	String lvManTilg = "0";
        	String lvManZins = "0";
 
        	if (!("02".equals(cv_stamm_prod.substring(0,2))))
            {
                if (!("99991231".equals(cv_ABL17dat)))
                {
                    for (int i=0; i<lvAblauflist.size(); i++)
                    { 
                       lvAblaufelem = (Element) lvAblauflist.get(i);
                      
                       if (lvAblaufelem.getAttributeValue("art").equals("17"))
                       { 
                           icKeyQuelle helpKeyQuelle = cvListeKontenQuelle.get(cv_stamm_nr);
                           if (helpKeyQuelle != null)
                           {
                    	     LOGGER.info(helpKeyQuelle.ivQuelle + "-Ablaufart 17: " + lvAblaufelem.getAttributeValue("betrag"));
                             if (helpKeyQuelle.ivQuelle.equals("ADARLPFBG"))
                             {
                    	       ivSummeAblaufart17ADARLPFBG = ivSummeAblaufart17ADARLPFBG.add(StringKonverter.convertString2BigDecimal(lvAblaufelem.getAttributeValue("betrag")));
                             }
                             //if (helpKeyQuelle.ivQuelle.equals("ADARLFLUG"))
                             //{
                    	     //  ivSummeAblaufart17ADARLFLUG = ivSummeAblaufart17ADARLFLUG.add(StringKonverter.convertString2BigDecimal(lvAblaufelem.getAttributeValue("betrag")));
                             //}
                             //if (helpKeyQuelle.ivQuelle.equals("ADARLSCHF"))
                             //{
                    	     //  ivSummeAblaufart17ADARLSCHF = ivSummeAblaufart17ADARLSCHF.add(StringKonverter.convertString2BigDecimal(lvAblaufelem.getAttributeValue("betrag")));
                             //}
                           }
                       }
                       lvDatum3 =  (lvAblaufelem.getAttributeValue("datum")).replace("-","");
                        
                       if (lvDatum3.compareTo(cv_ABL17dat)>0)
                       {
                           lvAblauflist.remove(i);
                           i--;
                       }
                    }    
                }
            }
            
            // Bei teilausgezahlten Krediten sollen keine Zinsen geliefert werden.
            // Teilausgezahlte Kredite haben einen Kontozustand == 3.            
            // Einmal über alle Ablaufelemente um Tilgung und zins zum selben Datum zusammenzufassen
            for (int j=0; j<lvAblauflist.size(); j++)
            {
                // Ablaufelement holen
                lvAblaufelem = (Element) lvAblauflist.get(j);

                if (lvAblaufelem.getAttributeValue("art").equals("21"))
                {
  
                    String lvKontozustand = new String();
                    // Kontozustand ermitteln
                    icKeyQuelle helpKeyQuelle = cvListeKontenQuelle.get(cv_stamm_nr);
                     if (helpKeyQuelle != null)
                    {
                         lvKontozustand = helpKeyQuelle.ivKontozustand;
                         //System.out.println(helpKeyQuelle.ivKey + " - lvKontozustand: " + lvKontozustand);
                    }

                    if (StringKonverter.convertString2Int(lvKontozustand) == 3)
                    {
                       // Element entfernen
                        lvAblauflist.remove(j);
                        // Der Rest der Liste rueckt nach unten, daher Zaehler wieder auf das naechste Element
                        j--;
                        LOGGER.info(lvAblaufelem.getAttributeValue("cfkey") + " - Kontozustand " + lvKontozustand + " - Keine Zinsen");  
                    }
                }                
            }
            
            // Einmal über alle Ablaufelemente um Tilgung und zins zum selben Datum zusammenzufassen
            for (int j=0; j<lvAblauflist.size(); j++)
            {
               // Ablaufelement holen
               lvAblaufelem = (Element) lvAblauflist.get(j);
               
               // Datum
               lvDatum1 = lvAblaufelem.getAttributeValue("datum");
               
               // Zins oder Tilgung
               lvTbetragbd = BigDecimal.ZERO;
               lvZbetragbd = BigDecimal.ZERO;
               
               if ( ("11".equals(lvAblaufelem.getAttributeValue("art"))) ||   // tilgung
                    ("12".equals(lvAblaufelem.getAttributeValue("art"))) ||   // außerplanmäßige Tilgung
                    ("13".equals(lvAblaufelem.getAttributeValue("art"))) ||   // RK bei Zinsanpassung
                    ("14".equals(lvAblaufelem.getAttributeValue("art"))) ||   // vorgemerkte Sondertilgung
                    ("15".equals(lvAblaufelem.getAttributeValue("art"))) ||   // Abgelaufene ZAP
                    ("17".equals(lvAblaufelem.getAttributeValue("art"))))     // RK zum Kündigungstermin */
               {
                 lvTbetragbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem.getAttributeValue("betrag")));
                 
                 // Keine Tilgungen für Passiva
                 if ("P".equals(cv_stamm_ap))
                 {
                     lvTbetragbd = BigDecimal.ZERO;
                 }
               }
               else if ("21".equals(lvAblaufelem.getAttributeValue("art"))) // zins
               {
                 lvZbetragbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem.getAttributeValue("betrag")));
 
                 // Passivzinsen drehen
                 if ("P".equals(cv_stamm_ap))
                 {
                     lvZbetragbd = lvZbetragbd.abs();
                 }  
               }
               else
               {
                 LOGGER.info(lvAblaufelem.getAttributeValue("cfkey") + " nicht verwendete Ablaufart " + lvAblaufelem.getAttributeValue("art"));  
               }
               
               // Gibt es ab hier weitere CFs zum selben Datum ?
               for (int k=j+1; k<lvAblauflist.size(); k++)
               {
                   lvAblaufelem2 = (Element) lvAblauflist.get(k); 
                   lvDatum2 = lvAblaufelem2.getAttributeValue("datum");
                   
                   String lvAblaufart2 = lvAblaufelem2.getAttributeValue("art");
                   
                   if (lvDatum1.equals(lvDatum2))
                   {
                       // Zins oder Tilgung aufaddieren
                       if ( ("11".equals(lvAblaufart2)) ||   // tilgung
                            ("12".equals(lvAblaufart2)) ||   // außerplanmäßige Tilgung
                            ("13".equals(lvAblaufart2)) ||   // RK bei Zinsanpassung
                            ("15".equals(lvAblaufart2)) ||   // Abgelaufene ZAP
                            ("17".equals(lvAblaufart2)))     // RK zum Kündigungstermin */
                       {
                          lvBufferbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem2.getAttributeValue("betrag")));
                          
                          // Keine Tilgungen für Passiva
                          if ("P".equals(cv_stamm_ap))
                          {
                              lvBufferbd = BigDecimal.ZERO;
                          }
                          
                          lvTbetragbd = lvTbetragbd.add(lvBufferbd);
                       }
                       else if ("21".equals(lvAblaufart2)) // zins
                       {
                         lvBufferbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem2.getAttributeValue("betrag")));
                         
                         // Passivzinsen drehen
                         if ("P".equals(cv_stamm_ap))
                         {
                             lvBufferbd = lvBufferbd.abs();
                         }
                         
                         lvZbetragbd = lvZbetragbd.add(lvBufferbd);
                       }
                       else
                       {
                         LOGGER.info(lvAblaufelem.getAttributeValue("cfkey") + " nicht verwendete Ablaufart " + lvAblaufart2);  
                       }
                       
                       // gefundenes element entfernen, damit es nicht nochmal gefunden wird
                       lvAblauflist.remove(k);
                       // der rest der Liste rückt nach unten, daher zähler wieder auf das nächste
                       k--;
                   }
               } // for über die Ablaufelemente gleichen Datums
               
               // alles gefunden, Ausgabe aufbauen
               
               Element lvElement_cfdaten_last = new Element("cfdaten");
               lvElement_cfdaten_last.setNamespace(lvMyNamespace);
                              
               String lvcfkey = "";
               lvcfkey = lvAblaufelem.getAttributeValue("cfkey");
               
               // der key passive WP aus Darlehen lautet auf key + konto, da es pro isin mehrere Konten geben kann
               // MAVIS auch
               if ("P".equals(cv_stamm_ap)
                   ||
                   ("85".equals(cv_stamm_anw) && "A".equals(cv_stamm_ap))
                   )
               {
                 lvcfkey+="_" + cv_stamm_nr;
               }    
             
               // es gibt Fälle (Konsortialgeschäfte), bei denen hostseitig das konto zweimal geliefert wird, weil 
               // nicht alle cfs in eine "Kontoklammer" passen
               // daher müssen sich die cashflowids zum selben konto unterscheiden
               if (0 != cv_mehrzeilig)
               {
                 lvcfkey+="_" + cv_mehrzeilig;
               } 
               
               lvElement_cfdaten_last.setAttribute("cfkey",lvcfkey);
                              
               lvElement_cfdaten_last.setAttribute("datum", lvAblaufelem.getAttributeValue("datum")); 
               
               lvElement_cfdaten_last.setAttribute("tbetrag",lvTbetragbd.toPlainString());  
               lvElement_cfdaten_last.setAttribute("zbetrag",lvZbetragbd.toPlainString());  
                     
               // Es wird ueberprueft, ob Tilgung oder Zinsen geliefert werden
               if (StringKonverter.convertString2Double(lvTbetragbd.toPlainString()) > 0.0)
               {
                	lvManTilg = "1";
               }

               if (StringKonverter.convertString2Double(lvZbetragbd.toPlainString()) > 0.0)
               {
                	lvManZins = "1";
               }

               lvElement_cfdaten_last.setAttribute("whrg", lvAblaufelem.getAttributeValue("whrg"));
               
               // letzte cfdaten anhängen
               cvElement_fg.addContent(lvElement_cfdaten_last);
            } // for über die Ablaufelemente
            
            // letztes Konto in Ausgabe einhängen, aber nur, wenn das Quellsystem stimmt
            if ((cvQuellen+';').contains(cvElement_fg.getAttributeValue("quelle")))
            {
               lvMyrootelement.addContent(cvElement_fg);
            //}
            
               // Stimmt 'mantilg' und 'manzins'
               //if (ivQuellsystemDatenListe.get(pvListeCashflow.get(0).getKontonummer()) != null)
               //{
               if (cv_mantilg != null)
               {
            	   if (!cv_mantilg.equals(lvManTilg))
            	   {
            		   LOGGER.info("Unterschiedliche Tilgungsschalter:" + cv_stamm_nr + ";" + cvElement_fg.getAttributeValue("key") + ";" + cvElement_fg.getAttributeValue("quelle")  + ";" + lvManTilg + ";" + cv_mantilg);
            	   }
               }
            
               if (cv_manzins != null)
               {
            	   if (!cv_manzins.equals(lvManZins))
            	   {
            		   LOGGER.info("Unterschiedliche Zinsschalter:" + cv_stamm_nr + ";" + cvElement_fg.getAttributeValue("key")  + ";" + cvElement_fg.getAttributeValue("quelle")  + ";" + lvManZins + ";" + cv_manzins);
            	   }
               }
            }
        }  
    }
    
    /**
     *  wird vom handler aufgerufen um den jeweils letzten Inhalt des Elementzs zu speichern
     *  das ist meiner Meinung nach etwas ungünstig gelöst...
     * @param buffer 
     * @param start 
     * @param length 
     */
    public void characters(char[] buffer, int start, int length) 
    {
        temp_puffer = new String(buffer, start, length);
    }
}
