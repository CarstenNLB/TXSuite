/*******************************************************************************
 * Copyright (c) 2017 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;

import nlb.txs.schnittstelle.Utilities.IniReader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLMerger 
{
    /**
     * log4j-Logger
     */
    private static Logger LOGGER = Logger.getLogger("TXSXMLMergerLogger"); 
         
    /**
     * Startroutine XMLMerger
     * @param argv 
     */
    public static void main(String[] argv) 
    {
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("XMLMerger <ini-Datei>");
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
                String lvLoggingXML = lvReader.getPropertyString("XMLMerger", "log4jconfig", "Fehler");
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
                LOGGER.info("Institut: " + lvInstitut);
                
                String lvListeEingabedateien = lvReader.getPropertyString("XMLMerger", "ListeEingabedateien", "Fehler");
                if (lvListeEingabedateien.equals("Fehler"))
                {
                    LOGGER.error("Keine ListeEingabedateien in der ini-Datei...");
                    System.exit(1);
                }
                LOGGER.info("ListeEingabedateien: " + lvListeEingabedateien);

                String lvAusgabedatei = lvReader.getPropertyString("XMLMerger", "Ausgabedatei", "Fehler");
                if (lvAusgabedatei.equals("Fehler"))
                {
                    LOGGER.error("Keine Ausgabedatei in der ini-Datei...");
                    System.exit(1);
                }
                LOGGER.info("Ausgabedatei: " + lvAusgabedatei);
   
                new XMLMerger(lvListeEingabedateien, lvAusgabedatei);                
            }
        }
        System.exit(0);
    }
    
    /**
     * Konstruktor 
     * @param pvListeEingabedateien Liste der Eingabedateien
     * @param pvAusgabedatei Ausgabedatei
     */
    public XMLMerger(String pvListeEingabedateien, String pvAusgabedatei)
    {
    	// Einlesen der Liste der Eingabedateien 
        int lvZaehlerEingabedateien = 0;
        String lvEingabedatei = new String();
    	HashSet<String> lvListeEingabedateien = new HashSet<String>();
        
        File ivFileListeEingabedateien = new File(pvListeEingabedateien);

        if (ivFileListeEingabedateien != null && ivFileListeEingabedateien.exists())
        {
          FileInputStream lvListeEingabedateienFis = null;
          BufferedReader lvListeEingabedateienBr = null;
          try
          {
            lvListeEingabedateienFis = new FileInputStream(ivFileListeEingabedateien);
            lvListeEingabedateienBr = new BufferedReader(new InputStreamReader(lvListeEingabedateienFis));
          }
          catch (Exception e)
          {
            LOGGER.info("Konnte Datei '" + pvListeEingabedateien + "' nicht oeffnen!");
          }
          try
          {
            LOGGER.info("Folgende Eingabedateien werden verarbeitet:");  
            while ((lvEingabedatei = lvListeEingabedateienBr.readLine()) != null)  
            {
              if (!lvEingabedatei.trim().isEmpty())
              {
                lvListeEingabedateien.add(lvEingabedatei);
                LOGGER.info("Eingabedatei: " + lvEingabedatei);
                lvZaehlerEingabedateien++;
              }
            }
          }
          catch (Exception e)
          {
              LOGGER.error("Fehler beim Verarbeiten einer Zeile:" + lvEingabedatei);
              e.printStackTrace();
          }
          try
          {
            lvListeEingabedateienBr.close();
            lvListeEingabedateienFis.close();
          }
          catch (Exception e)
          {
              LOGGER.error("Konnte Datei '" + pvListeEingabedateien + "' nicht schliessen!");
          }
        }
        else
        {
            LOGGER.info("Keine Datei '" + pvListeEingabedateien + "' gefunden...");
        }
        LOGGER.info(lvZaehlerEingabedateien + " Eingabedateien enthalten...");

        // Dateien konkatenieren
        concat(lvListeEingabedateien, pvAusgabedatei);
    }
    
    /**
     * Liest die XML-Datei ein
     * @param pvFilename Dateiname der XML-Datei
     * @return
     */
    private Document readXML(String pvFilename)
    {
        // XML-Dokument
        Document lvDocument = null;
     
        // Zur Eingabe
        SAXBuilder lvBuilder = null;
        lvBuilder = new SAXBuilder();
        lvBuilder.setExpandEntities(true);
         
        try
        {
        	lvDocument = (Document)lvBuilder.build(pvFilename);
        }
        catch(IOException io)
        {
        	LOGGER.error(io.getMessage());
        }
        catch(JDOMException jdomex)
        {
        	LOGGER.error(jdomex.getMessage());
        }
        
        return lvDocument;
    }
    
    /**
     * Konkatenierung von XML-Importdateien
     * @param pvListeEingabedateien Liste der Eingabedateien
     * @param pvFilenameTarget Ausgabedatei
     */
    private void concat(HashSet<String> pvListeEingabedateien, String pvFilenameTarget)
    {
    	boolean lvFirst = true;
    	boolean lvValdate = false;
        Document lvDocumentSource = null;
        Document lvDocumentTarget = null;
		Element lvRootElementTarget = null;

        for (String lvEingabedatei:pvListeEingabedateien)
        {
        	// Source einlesen
        	lvDocumentSource = readXML(lvEingabedatei);
    	
        	if (lvFirst)
        	{
        		lvRootElementTarget = new Element("importdaten");
        		Namespace lvMyNamespace = Namespace.getNamespace("txsi", "http://agens.com/txsimport.xsd");
        		lvRootElementTarget.setNamespace(lvMyNamespace);
        		lvDocumentTarget = new Document(lvRootElementTarget);
        		lvFirst = false;
        	}
                	
        	// RootNode vom Eingangsdokument einlesen
        	Element lvRootNode = lvDocumentSource.getRootElement();

        	List<Element> lvList = lvRootNode.getChildren();
        	for (int i = 0; i < lvList.size(); i++)
        	{
        		Element lvNode = (Element)lvList.get(i); 
        		if (lvNode.getName().equals("header") && !lvValdate)
        		{
        		  lvRootElementTarget.addContent(lvNode.clone());
        		  lvValdate = true;
        		}
        		if (!lvNode.getName().equals("header"))
        		{
          		  lvRootElementTarget.addContent(lvNode.clone());        			
        		}
        	}
        }
                        
        // Ausgabe
        FileOutputStream lvXMLOS = null;
        File lvFileXML = new File(pvFilenameTarget);
        try
        {
        	lvXMLOS = new FileOutputStream(lvFileXML);
        }
        catch (Exception exp)
        {
        	LOGGER.error("Konnte XML-Ausgabedatei nicht oeffnen!");
        }    

        XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());
        
        try
        {
        	lvOutputter.output(lvDocumentTarget, lvXMLOS);
        }
        catch (IOException exp) 
        {
        	exp.printStackTrace();
        }
        
        try
        {
        	lvXMLOS.close();
        }
        catch (IOException exp)
        {
        	LOGGER.error("Konnte XML-Ausgabedatei nicht schliessen!");        	
        } 
    } 

}
