/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Wertpapier.Gattungsdaten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author tepperc
 *
 */
public class GattungsdatenRequest 
{
    // Logger fuer GattungsdatenRequest
    private static Logger LOGGER_GDR = Logger.getLogger("TXSGattungsdatenRequestLogger");  
    
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * Wertpapier-Bestandsdaten Importverzeichnis
     */
    private String ivWertpapierBestandsdatenImportVerzeichnis;
    
    /**
     * Exportverzeichnis
     */
    private String ivExportVerzeichnis;
    
    /**
     * Wertpapier-Bestandsdaten Inputdatei
     */
    private String ivWertpapierBestandsdatenInputDatei;
    
    /**
     * Buchungsdatum aus Datei 'dp.txt'
     */
    private String ivDaypointerFileout;
    
    /** 
     * Zaehlervariable
     */
    private int ivWertpapierBestandsdatenZaehler = 0;
    
    /**
     * Startroutine von GattungsdatenRequest
     * @param argv 
     */
    public static void main(String[] argv) 
    {
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("GattungsdatenRequest <ini-Datei>");
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
            
            new GattungsdatenRequest(lvReader);              
        }
        System.exit(0);
    }

    /**
     * Konstruktor
     * @param pvReader 
     */
    public GattungsdatenRequest(IniReader pvReader)
    {
        if (pvReader != null)
        {
            String lvLoggingXML = pvReader.getPropertyString("Gattungsdaten", "log4jconfig_GattungsdatenRequest", "Fehler");
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
                LOGGER_GDR.error("Keine [Konfiguration][Institut] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
            	LOGGER_GDR.info("Institutsnummer: " + ivInstitutsnummer);
            }
                        
            ivWertpapierBestandsdatenImportVerzeichnis = pvReader.getPropertyString("WertpapiereMAVIS", "ImportVerzeichnis", "Fehler");
            if (ivWertpapierBestandsdatenImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_GDR.error("Kein [WertpapiereMAVIS][ImportVerzeichnis] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
            	LOGGER_GDR.info("ImportVerzeichnis: " + ivWertpapierBestandsdatenImportVerzeichnis);
            }

            ivExportVerzeichnis = pvReader.getPropertyString("Gattungsdaten", "ExportVerzeichnis", "Fehler");
            if (ivExportVerzeichnis.equals("Fehler"))
            {
                LOGGER_GDR.error("Kein [Gattungsdaten][ExportVerzeichnis] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
            	LOGGER_GDR.info("ExportVerzeichnis: " + ivExportVerzeichnis);
            }

            ivWertpapierBestandsdatenInputDatei = pvReader.getPropertyString("WertpapiereMAVIS", "MAVISInput-Datei", "Fehler");
            if (ivWertpapierBestandsdatenInputDatei.equals("Fehler"))
            {
                LOGGER_GDR.error("Kein [WertpapiereMAVIS][MAVISInput-Datei] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
            	LOGGER_GDR.info("Input-Datei: " + ivWertpapierBestandsdatenInputDatei);
            }

            ivDaypointerFileout = pvReader.getPropertyString("Konfiguration", "DPFILE", "Fehler");
            if (ivDaypointerFileout.equals("Fehler"))
            {
                LOGGER_GDR.error("Kein [Konfiguration][DPFILE] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER_GDR.info("DPFILE: " + ivDaypointerFileout);                    
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
        // Zur Ausgabe
        Element lvMyrootelement = new Element("TXS");
        //Namespace lvMyNamespace = Namespace.getNamespace("txsi", "http://agens.com/txsimport.xsd");
        //lvMyrootelement.setNamespace(lvMyNamespace);
        Document lvMydocument = new Document(lvMyrootelement);
        
        // Header anhaengen
        Element lvMyElementHeader = new Element("Header");
        // Bestandsdatum
        Element lvMyElementBestandsdatum = new Element("Bestandsdatum");
        lvMyElementHeader.addContent(lvMyElementBestandsdatum);
        // CorrelationID
        Element lvMyElementCorrelationID = new Element("CorrelationID");
        lvMyElementCorrelationID.setText("4710");
        lvMyElementHeader.addContent(lvMyElementCorrelationID);
        // SystemID
        Element lvMyElementSystemID = new Element("SystemID");
        lvMyElementSystemID.setText("TXS_P");
        lvMyElementHeader.addContent(lvMyElementSystemID);
        // CreationTimestamp
        Element lvMyElementCreationTimestamp = new Element("CreationTimestamp");
        lvMyElementHeader.addContent(lvMyElementCreationTimestamp);
        
        lvMyrootelement.addContent(lvMyElementHeader);
       
        // Request anhaengen
        Element lvMyElementRequest = new Element("Request");
        lvMyrootelement.addContent(lvMyElementRequest);
        
        // Institut anhaengen
        Element lvMyElementInstitut = new Element("Institut");
        lvMyElementRequest.addContent(lvMyElementInstitut);
        
        // Liste mit den ISIN
        ListeISIN lvListeISIN = new ListeISIN();
        
    	// Einlesen des Buchungsdatum
        String lvBuchungsdatum = new String();
        
    	File ivFileDaypointer = new File(ivDaypointerFileout);
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
            LOGGER_GDR.info("Konnte Datei '" + ivDaypointerFileout + "' nicht oeffnen!");
          }
          try
          {
            lvBuchungsdatum = lvDaypointerBr.readLine();  
          }
          catch (Exception e)
          {
              LOGGER_GDR.error("Fehler beim Verarbeiten:" + lvBuchungsdatum);
              e.printStackTrace();
          }
          try
          {
            lvDaypointerBr.close();
            lvDaypointerFis.close();
          }
          catch (Exception e)
          {
              LOGGER_GDR.error("Konnte Datei '" + ivDaypointerFileout + "' nicht schliessen!");
          }
        }
        else
        {
            LOGGER_GDR.info("Keine Datei '" + ivDaypointerFileout + "' gefunden...");
        }
        LOGGER_GDR.info("Buchungsdatum: " + lvBuchungsdatum);

        lvMyElementBestandsdatum.setText(lvBuchungsdatum.replace("-", ""));
        lvMyElementCreationTimestamp.setText(DatumUtilities.createTimestamp(lvBuchungsdatum.replace("-", ""), "000000"));
        
        String lvInstitutsnummer = ivInstitutsnummer;
        if (ivInstitutsnummer.equals("NLB"))
        	lvInstitutsnummer = "009";
        if (ivInstitutsnummer.equals("BLB"))
        	lvInstitutsnummer = "004";
        
        lvMyElementInstitut.setAttribute("nr", lvInstitutsnummer);
        
        // Wertpapier-Bestandsdaten einlesen
        LOGGER_GDR.info("Einlesen: " + ivWertpapierBestandsdatenImportVerzeichnis + "\\" + ivWertpapierBestandsdatenInputDatei);	
        readBestandsdaten(ivWertpapierBestandsdatenImportVerzeichnis + "\\" + ivWertpapierBestandsdatenInputDatei, lvListeISIN, lvMyElementInstitut);
          
        // Ausgabe
        FileOutputStream lvGDOS = null;
        File lvFileGD = new File(ivExportVerzeichnis + "\\TXS2SPOT_GD_" + lvInstitutsnummer + "_" + lvBuchungsdatum.replace("-", "") + ".xml");
        try
        {
          lvGDOS = new FileOutputStream(lvFileGD);
        }
        catch (Exception e)
        {
          LOGGER_GDR.error("Konnte in GattungsdatenRequest-Datei nicht schreiben!");
        }    

        XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());

        try
        {
          Format lvFormat = lvOutputter.getFormat();
          lvFormat.setEncoding("ISO-8859-1");
          lvOutputter.output(lvMydocument, lvGDOS);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        LOGGER_GDR.info(getStatistik());
    }
         
    /**
     * Einlesen und Verarbeiten der Wertpapier-Bestandsdaten
     * @param pvDateiname
     * @param pvListeISIN
     * @param pvMyElementInstitut
     */
    private void readBestandsdaten(String pvDateiname, ListeISIN pvListeISIN, Element pvMyElementInstitut)
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
            LOGGER_GDR.error("Konnte Bestandsdaten-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen der Wertpapier-Bestandsdaten
            {
        		Bestandsdaten lvBestandsdaten = new Bestandsdaten();

             	if (lvZeile.startsWith("##"))
            	{
                    LOGGER_GDR.info("Zeile: " + lvZeile);
            	}
            	else
                {
                    if (lvBestandsdaten.parseBestandsdaten(lvZeile, LOGGER_GDR)) // Parsen der Felder
                    {     
                    	if (lvBestandsdaten.getAusplatzierungsmerkmal().startsWith("H") || lvBestandsdaten.getAusplatzierungsmerkmal().startsWith("K") ||
                    		lvBestandsdaten.getAusplatzierungsmerkmal().startsWith("S") || lvBestandsdaten.getAusplatzierungsmerkmal().startsWith("F"))
                    	{
                            if (!pvListeISIN.containsISIN(lvBestandsdaten.getProdukt()))
                            {        
                              Element lvMyElementISIN = new Element("ISIN");
                              lvMyElementISIN.setAttribute("nr", lvBestandsdaten.getProdukt());
                              pvMyElementInstitut.addContent(lvMyElementISIN);
                              pvListeISIN.addISIN(lvBestandsdaten.getProdukt());  
                              ivWertpapierBestandsdatenZaehler++;
                              LOGGER_GDR.info("Wertpapier-Bestandsdaten - Anforderung von Gattungsdaten: " + lvBestandsdaten.getProdukt());
                            }
                    	}
                    }
                }
            }
        }
        catch (Exception e)
        {
            LOGGER_GDR.error("Fehler beim Verarbeiten einer Zeile!");
            LOGGER_GDR.error("Zeile: " + lvZeile);
            e.printStackTrace();
        }
        
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            LOGGER_GDR.error("Konnte Bestandsdaten-Datei nicht schliessen!");
        }           
    }

    /**
     * Liefert eine Statistik
     * @return Statistik
     */
    private String getStatistik()
    {
        StringBuilder lvOut = new StringBuilder();
        
        lvOut.append("Anzahl WertpapierBestandsdaten: " + ivWertpapierBestandsdatenZaehler + StringKonverter.lineSeparator); 
        
        return lvOut.toString();
    }

}
