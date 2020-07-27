/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Kunde.KundeResponseSplitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import nlb.txs.schnittstelle.Kunde.KundeRequestVorlaufsatz;
import nlb.txs.schnittstelle.Utilities.IniReader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class KundeResponseSplitter 
{
    /**
     * log4j-Logger
     */
    private static Logger LOGGER = Logger.getLogger("TXSKundeResponseSplitterLogger"); 
    
    /**
     * Dokument mit von SPOT angelieferten Kunden 
     */
    private Document ivDocument;

    /**
     * Pfandbrief - Liste der Kundennummern
     */
    private HashSet<String> ivListeKundennrPfandbrief;

    /**
     * RefiReg - Liste der Kundennummern
     */
    private HashSet<String> ivListeKundennrRefiReg;

    /**
     * KEV - Liste der Kundennummern
     */
    private HashSet<String> ivListeKundennrKEV;

  /**
     * Startroutine von KundeResponseSplitter
     * @param argv Aufrufparameter
     */
    public static void main(String[] argv) 
    {
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("KundeResponseSplitter <ini-Datei>");
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
                String lvLoggingXML = lvReader.getPropertyString("KundeResponseSplitter", "log4jconfig", "Fehler");
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
                    LOGGER.error("Keine Institutsnummer in der ini-Datei...");
                    System.exit(1);
                }
                                                        
                String lvListeKundeInDatei = lvReader.getPropertyString("KundeRequestMerger", "ListeKundeInDatei", "Fehler");
                if (lvListeKundeInDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein ListeKundeIn-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvExportVerzeichnisKundeResponseSplitter = lvReader.getPropertyString("KundeResponseSplitter", "ExportVerzeichnis", "Fehler");
                if (lvExportVerzeichnisKundeResponseSplitter.equals("Fehler"))
                {
                    LOGGER.error("Kein Export-Verzeichnis KundeResponseSplitter in der ini-Datei...");
                    System.exit(1);
                }

                String lvKundeResponsePfandbrief = lvReader.getPropertyString("KundeResponseSplitter", "KundeResponsePfandbrief", "Fehler");
                if (lvKundeResponsePfandbrief.equals("Fehler"))
                {
                    LOGGER.error("Kein KundeResponsePfandbrief-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvKundeResponseRefiReg = lvReader.getPropertyString("KundeResponseSplitter", "KundeResponseRefiReg", "Fehler");
                if (lvKundeResponseRefiReg.equals("Fehler"))
                {
                    LOGGER.error("Kein KundeResponseRefiReg-Dateiname in der ini-Datei...");
                    System.exit(1);
                }

                String lvKundeResponseKEV = lvReader.getPropertyString("KundeResponseSplitter", "KundeResponseKEV", "Fehler");
                if (lvKundeResponseKEV.equals("Fehler"))
                {
                  LOGGER.error("Kein KundeResponseKEV-Dateiname in der ini-Datei...");
                  System.exit(1);
                }

                String lvImportVerzeichnis = lvReader.getPropertyString("KundeResponseSplitter", "ImportVerzeichnis", "Fehler");
                if (lvImportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein Import-Verzeichnis in der ini-Datei...");
                    System.exit(1);
                }
                
                String lvResponseDatei =  lvReader.getPropertyString("KundeResponseSplitter", "ResponseDatei", "Fehler");
                if (lvResponseDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein Response-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
  
                new KundeResponseSplitter(lvInstitut, lvListeKundeInDatei, lvImportVerzeichnis, lvResponseDatei, lvExportVerzeichnisKundeResponseSplitter,
                	                      lvKundeResponsePfandbrief, lvKundeResponseRefiReg, lvKundeResponseKEV);
            }
        }
        System.exit(0);
    }
    
    /**
     * Konstruktor
     * @param pvInstitut Institut
     * @param pvListeKundeInDatei Datei mit der Liste der Kunden-Dateien
     * @param pvImportVerzeichnis Import-Verzeichnis
     * @param pvResponseDatei Response-Datei
     * @param pvExportVerzeichnisKundeResponseSplitter Exportverzeichnis der KundeResponse-Dateien
     * @param pvKundeResponsePfandbrief KundeResponse-Datei fuer das TXS Pfandbrief
     * @param pvKundeResponseRefiReg KundeResponse-Datei fuer TXS RefiRegister
     * @param pvKundeResponseKEV KundeResponse-Datei fuer TXS KEV
     */
    public KundeResponseSplitter(String pvInstitut, String pvListeKundeInDatei, String pvImportVerzeichnis, String pvResponseDatei, String pvExportVerzeichnisKundeResponseSplitter,
                                 String pvKundeResponsePfandbrief, String pvKundeResponseRefiReg, String pvKundeResponseKEV)
    { 
        ivListeKundennrPfandbrief = new HashSet<String>();
        ivListeKundennrRefiReg = new HashSet<String>();
        ivListeKundennrKEV = new HashSet<String>();
    	
        String lvFilenameIn  = pvImportVerzeichnis + "\\" + pvResponseDatei;   
        String lvFilenameOutPfandbrief = pvExportVerzeichnisKundeResponseSplitter + "\\" + pvKundeResponsePfandbrief;
        String lvFilenameOutRefiReg = pvExportVerzeichnisKundeResponseSplitter + "\\" + pvKundeResponseRefiReg;
        String lvFilenameOutKEV =  pvExportVerzeichnisKundeResponseSplitter + "\\" + pvKundeResponseKEV;

      Runtime lvRuntime = Runtime.getRuntime();
        
        LOGGER.info("Used Memory vor dem Lesen:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
        
        LOGGER.info("Response - Eingabedatei:");
        LOGGER.info(lvFilenameIn);
        LOGGER.info("Response - Ausgabedateien:");
        LOGGER.info(lvFilenameOutPfandbrief);
        LOGGER.info(lvFilenameOutRefiReg);
        LOGGER.info(lvFilenameOutKEV);
                
        leseKundendateien(pvListeKundeInDatei);
        leseKunden(lvFilenameIn);

        LOGGER.info("Used Memory nach dem Lesen:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
        
        zerlegeKunden(lvFilenameOutPfandbrief, lvFilenameOutRefiReg, lvFilenameOutKEV);
        
        LOGGER.info("Used Memory nach dem Schreiben:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
     }
    
    /**
     * Einlesen der Liste der Kundendateien
     * @param pvFilenameListeKundeIn
     */
    private void leseKundendateien(String pvFilenameListeKundeIn)
    {
    	HashSet<String> lvListeKundeIn = new HashSet<String>();

    	// Einlesen der Liste der Kundendateien
        int lvZaehlerKundeIn = 0;
        String lvKundeIn = new String();
        
        File ivFileListeKundeIn = new File(pvFilenameListeKundeIn);

        if (ivFileListeKundeIn != null && ivFileListeKundeIn.exists())
        {
          FileInputStream lvListeKundeInFis = null;
          BufferedReader lvListeKundeInBr = null;
          try
          {
            lvListeKundeInFis = new FileInputStream(ivFileListeKundeIn);
            lvListeKundeInBr = new BufferedReader(new InputStreamReader(lvListeKundeInFis));
          }
          catch (Exception e)
          {
            LOGGER.info("Konnte Datei '" + pvFilenameListeKundeIn + "' nicht oeffnen!");
          }
          try
          {
            LOGGER.info("Folgende KundeIn-Dateien werden verarbeitet:");  
            while ((lvKundeIn = lvListeKundeInBr.readLine()) != null)  
            {
              if (lvKundeIn != null)
              {
                lvListeKundeIn.add(lvKundeIn);
                LOGGER.info("KundeIn-Datei: " + lvKundeIn);
                lvZaehlerKundeIn++;
              }
            }
          }
          catch (Exception e)
          {
              LOGGER.error("Fehler beim Verarbeiten einer Zeile:" + lvKundeIn);
              e.printStackTrace();
          }
          try
          {
            lvListeKundeInBr.close();
            lvListeKundeInFis.close();
          }
          catch (Exception e)
          {
              LOGGER.error("Konnte Datei '" + pvFilenameListeKundeIn + "' nicht schliessen!");
          }
        }
        else
        {
            LOGGER.info("Keine Datei '" + pvFilenameListeKundeIn + "' gefunden...");
        }
        LOGGER.info(lvZaehlerKundeIn + " KundeIn-Dateien enthalten...");
        
        int lvZaehlerKundennummernPfandbrief = 0;
        int lvZaehlerKundennummernRefiReg = 0;
        int lvZaehlerKundennummernKEV = 0;

        // Kundennummern einlesen und zuordnen
        Iterator<String> lvIterKundeIn = lvListeKundeIn.iterator();
        while (lvIterKundeIn.hasNext())
        {
        	lvKundeIn = lvIterKundeIn.next();
            File ivFileKundeIn = new File(lvKundeIn);

            if (ivFileKundeIn != null && ivFileKundeIn.exists())
            {
              FileInputStream lvKundeInFis = null;
              BufferedReader lvKundeInBr = null;
              try
              {
                lvKundeInFis = new FileInputStream(ivFileKundeIn);
                lvKundeInBr = new BufferedReader(new InputStreamReader(lvKundeInFis));
              }
              catch (Exception exp)
              {
                LOGGER.error("Konnte Datei '" + lvKundeIn + "' nicht oeffnen!");
              }
              
              boolean first = true;
              String lvZeile = new String();
              KundeRequestVorlaufsatz lvKundeRequestVorlaufsatz = new KundeRequestVorlaufsatz(LOGGER);
              try
              {
            	  while ((lvZeile = lvKundeInBr.readLine()) != null)  
            	  {
                	if (first)
                	{
                		lvKundeRequestVorlaufsatz.parseVorlaufsatz(lvZeile);
                		first = false;
                	}
                	else
                	{
                		if (lvKundeRequestVorlaufsatz.getZielsystem().equals("Pfandbrief"))
                		{
                			ivListeKundennrPfandbrief.add(lvZeile);
                			lvZaehlerKundennummernPfandbrief++;
                		}
                		if (lvKundeRequestVorlaufsatz.getZielsystem().equals("RefiReg"))
                		{
                			ivListeKundennrRefiReg.add(lvZeile);
                			lvZaehlerKundennummernRefiReg++;
                		}
                		if (lvKundeRequestVorlaufsatz.getZielsystem().equals("KEV"))
                    {
                      ivListeKundennrKEV.add(lvZeile);
                      lvZaehlerKundennummernKEV++;
                    }
                	}
            	  }
              }
              catch (Exception exp)
              {
                  LOGGER.error("Fehler beim Verarbeiten von:" + lvZeile);
                  exp.printStackTrace();
              }
              try
              {
                lvKundeInBr.close();
                lvKundeInFis.close();
              }
              catch (Exception exp)
              {
                  LOGGER.error("Konnte Datei '" + lvKundeIn + "' nicht schliessen!");
              }
            }
            else
            {
                LOGGER.info("Keine Datei '" + lvKundeIn + "' gefunden...");
            }
        }
        LOGGER.info(lvZaehlerKundennummernPfandbrief + " Kundennummern Pfandbrief enthalten...");
        LOGGER.info(lvZaehlerKundennummernRefiReg + " Kundennummern RefiReg enthalten...");
        LOGGER.info(lvZaehlerKundennummernKEV + " Kundennummern KEV enthalten...");
    }
    
    /**
     * Liest die Kundendaten von SPOT
     * @param pvFilename Dateiname der Kundendaten von SPOT
     */
    private void leseKunden(String pvFilename)
    {
        // Zur Eingabe
        SAXBuilder lvBuilder = null;
        lvBuilder = new SAXBuilder();
        lvBuilder.setExpandEntities(true);
         
        try
        {
            ivDocument = (Document)lvBuilder.build(pvFilename);
        }
        catch(IOException io)
        {
             LOGGER.error(io.getMessage());
        }
        catch(JDOMException jdomex)
        {
             LOGGER.error(jdomex.getMessage());
        }
    }
    
    /**
     * Zerlegt die eine Responsedatei in drei (Pfandbrief, RefiReg und KEV)
     * @param pvFilenamePfandbrief Filename TXS Pfandbrief
     * @param pvFilenameRefiReg Filename TXS RefiRegister
     * @param pvFilenameKEV Filename TXS KEV
     */
    private void zerlegeKunden(String pvFilenamePfandbrief, String pvFilenameRefiReg, String pvFilenameKEV)
    {
        // Zur Ausgabe
    	Element lvNodeTXS = new Element("TXS");
        Element lvRootElementPfandbrief = lvNodeTXS.clone();
        Element lvRootElementRefiReg = lvNodeTXS.clone();
        Element lvRootElementKEV = lvNodeTXS.clone();
        Document lvDocumentPfandbrief = new Document(lvRootElementPfandbrief);
        Document lvDocumentRefiReg = new Document(lvRootElementRefiReg);
        Document lvDocumentKEV = new Document(lvRootElementKEV);

        // RootNode vom Eingangsdokument einlesen
        Element lvRootNode = ivDocument.getRootElement();
        Element lvNodeHeader = (Element)lvRootNode.getChildren("Header").get(0);       
        
        LOGGER.info("Bestandsdatum: " + lvNodeHeader.getChildText("Bestandsdatum"));
        LOGGER.info("CorrelationID: " + lvNodeHeader.getChildText("CorrelationID"));
        LOGGER.info("SystemID: " + lvNodeHeader.getChildText("SystemID"));
        LOGGER.info("CreationTimestamp: " + lvNodeHeader.getChildText("CreationTimestamp"));            
        
        // Header anhaengen
        lvRootElementPfandbrief.addContent(lvNodeHeader.clone());
        lvRootElementRefiReg.addContent(lvNodeHeader.clone());
        lvRootElementKEV.addContent(lvNodeHeader.clone());
        
        Element lvNodeResponse = (Element)lvRootNode.getChildren("Response").get(0);       
        Element lvNodeInstitut = (Element)lvNodeResponse.getChildren("Institut").get(0);
         
        Element lvNodeResponsePfandbrief = new Element("Response");
        Element lvNodeResponseRefiReg = new Element("Response");
        Element lvNodeResponseKEV = new Element("Response");
        
        Element lvNodeInstitutPfandbrief = new Element("Institut");
        lvNodeInstitutPfandbrief.setAttribute("nr", lvNodeInstitut.getAttributeValue("nr"));
        Element lvNodeInstitutRefiReg = new Element("Institut");
        lvNodeInstitutRefiReg.setAttribute("nr", lvNodeInstitut.getAttributeValue("nr"));
        Element lvNodeInstitutKEV = new Element("Institut");
        lvNodeInstitutKEV.setAttribute("nr", lvNodeInstitut.getAttributeValue("nr"));

        lvNodeResponsePfandbrief.addContent(lvNodeInstitutPfandbrief);
        lvNodeResponseRefiReg.addContent(lvNodeInstitutRefiReg);
        lvNodeResponseKEV.addContent(lvNodeInstitutKEV);

        lvRootElementPfandbrief.addContent(lvNodeResponsePfandbrief);
        lvRootElementRefiReg.addContent(lvNodeResponseRefiReg);
        lvRootElementKEV.addContent(lvNodeResponseKEV);

        List<Element> lvListKunde = lvNodeInstitut.getChildren("Kunde");

        LOGGER.info("Anzahl angelieferter Kunden: " + lvListKunde.size());

        int lvAnzahlTrefferPfandbrief = 0;
        int lvAnzahlTrefferRefiReg = 0;
        int lvAnzahlTrefferKEV = 0;
        
        Element lvNodeKunde;
        for (int i = 0; i < lvListKunde.size(); i++)
        {
        	lvNodeKunde = (Element)lvListKunde.get(i);  
        	if (ivListeKundennrPfandbrief.contains(lvNodeKunde.getAttributeValue("nr")))
        	{
        		lvNodeInstitutPfandbrief.addContent(lvNodeKunde.clone());
        		LOGGER.info("Pfandbrief - Kundennummer " + lvNodeKunde.getAttributeValue("nr") + " gefunden");
        		lvAnzahlTrefferPfandbrief++;
        	}
        	if (ivListeKundennrRefiReg.contains(lvNodeKunde.getAttributeValue("nr")))
        	{
        		lvNodeInstitutRefiReg.addContent(lvNodeKunde.clone());
        		LOGGER.info("RefiReg - Kundennummer " + lvNodeKunde.getAttributeValue("nr") + " gefunden");
        		lvAnzahlTrefferRefiReg++;
        	}
           if (ivListeKundennrKEV.contains(lvNodeKunde.getAttributeValue("nr")))
          {
            lvNodeInstitutKEV.addContent(lvNodeKunde.clone());
            LOGGER.info("KEV - Kundennummer " + lvNodeKunde.getAttributeValue("nr") + " gefunden");
            lvAnzahlTrefferKEV++;
          }
        }
        
        LOGGER.info("Anzahl gefundener Kundennummern Pfandbrief: " + lvAnzahlTrefferPfandbrief);
        LOGGER.info("Anzahl gefundener Kundennummern RefiReg: " + lvAnzahlTrefferRefiReg);
        LOGGER.info("Anzahl gefundener Kundennummern KEV: " + lvAnzahlTrefferKEV);

        // Ausgabe Pfandbrief
        FileOutputStream lvKundeOS = null;
        File lvFileKunde = new File(pvFilenamePfandbrief);
        try
        {
          lvKundeOS = new FileOutputStream(lvFileKunde);
        }
        catch (Exception exp)
        {
          LOGGER.error("Konnte KundePfandbrief XML-Datei nicht oeffnen!");
        }    

        XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());
        
        try
        {
          lvOutputter.output(lvDocumentPfandbrief, lvKundeOS);
        }
        catch (IOException exp) 
        {
            exp.printStackTrace();
        }
        
        try
        {
          lvKundeOS.close();
        }
        catch (IOException exp)
        {
            LOGGER.error("Konnte KundePfandbrief XML-Datei nicht schliessen!");        	
        }
        
        // Ausgabe RefiReg
        lvFileKunde = new File(pvFilenameRefiReg);
        try
        {
          lvKundeOS = new FileOutputStream(lvFileKunde);
        }
        catch (Exception exp)
        {
          LOGGER.error("Konnte KundeRefiReg XML-Datei nicht oeffnen!");
        }    

        lvOutputter = new XMLOutputter(Format.getPrettyFormat());
        
        try
        {
          lvOutputter.output(lvDocumentRefiReg, lvKundeOS);
        }
        catch (IOException exp) 
        {
            exp.printStackTrace();
        }
        try
        {
          lvKundeOS.close();
        }
        catch (IOException exp)
        {
            LOGGER.error("Konnte KundeRefiReg XML-Datei nicht schliessen!");        	
        }

        // Ausgabe KEV
        lvFileKunde = new File(pvFilenameKEV);
        try
        {
          lvKundeOS = new FileOutputStream(lvFileKunde);
        }
        catch (Exception exp)
        {
          LOGGER.error("Konnte KundeKEV XML-Datei nicht oeffnen!");
        }

        lvOutputter = new XMLOutputter(Format.getPrettyFormat());

        try
        {
          lvOutputter.output(lvDocumentKEV, lvKundeOS);
        }
        catch (IOException exp)
        {
          exp.printStackTrace();
        }
        try
       {
          lvKundeOS.close();
       }
       catch (IOException exp)
       {
          LOGGER.error("Konnte KundeKEV XML-Datei nicht schliessen!");
       }
    }

}
