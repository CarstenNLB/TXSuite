/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Kunde;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author tepperc
 *
 */
public class Kunde 
{
    /**
     * log4j-Logger
     */
    private static Logger LOGGER = Logger.getLogger("TXSKundeLogger"); 
    
    /**
     * Startroutine
     * @param argv 
     */
    public static void main(String[] argv) 
    {
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("Kunde <Verarbeitungsmodus> <ini-Datei>");
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
                String lvLoggingXML = lvReader.getPropertyString("Kunde_" + argv[argv.length - 2], "log4jconfig", "Fehler");
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
                                        
                String lvImportVerzeichnis = lvReader.getPropertyString("Kunde_" + argv[argv.length - 2], "ImportVerzeichnis", "Fehler");
                if (lvImportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein Import-Verzeichnis in der ini-Datei...");
                    System.exit(1);
                }

                String lvExportVerzeichnis = lvReader.getPropertyString("Kunde_" + argv[argv.length - 2], "ExportVerzeichnis", "Fehler");
                if (lvExportVerzeichnis.equals("Fehler"))
                {
                    LOGGER.error("Kein Export-Verzeichnis in der ini-Datei...");
                    System.exit(1);
                }

                String lvResponseDatei =  lvReader.getPropertyString("Kunde_" + argv[argv.length - 2], "ResponseDatei", "Fehler");
                if (lvResponseDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein Response-Dateiname in der ini-Datei...");
                    System.exit(1);
                }
  
                String lvKundeTXSDatei =  lvReader.getPropertyString("Kunde_" + argv[argv.length - 2], "KundeTXS-Datei", "Fehler");
                if (lvKundeTXSDatei.equals("Fehler"))
                {
                    LOGGER.error("Kein KundeTXS-Dateiname in der ini-Datei...");
                    System.exit(1);
                }                
                                  
                new Kunde(lvInstitut, lvImportVerzeichnis, lvExportVerzeichnis, lvResponseDatei, lvKundeTXSDatei);                
            }
        }
        System.exit(0);
    }
    
    /**
     * Konstruktor
     * @param pvInstitut 
     * @param pvImportVerzeichnis 
     * @param pvExportVerzeichnis 
     * @param pvResponseDatei 
     * @param pvKundeTXSDatei
     */
    public Kunde(String pvInstitut, String pvImportVerzeichnis, String pvExportVerzeichnis, String pvResponseDatei, String pvKundeTXSDatei)
    { 
    	
        String lvFilenameIn  = pvImportVerzeichnis + "\\" + pvResponseDatei;        
        String lvFilenameOut = pvImportVerzeichnis + "\\" + pvKundeTXSDatei;        

        Runtime lvRuntime = Runtime.getRuntime();
        
        LOGGER.info("Used Memory vor dem Lesen:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
        
        Document lvDocument = leseKunden(lvFilenameIn);

        LOGGER.info("Used Memory nach dem Lesen:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
        
        schreibeKunden(lvFilenameOut, lvDocument);

        LOGGER.info("Used Memory nach dem Schreiben:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
     }
    
    /**
     * Liest die Kundendaten von SPOT
     * @param pvFilename Dateiname der Kundendaten von SPOT
     */
    private Document leseKunden(String pvFilename)
    {
    	// Document
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
     * Schreibt die Kundendaten in die TXS XML-Datei
     * @param pvFilenameOut
     * @param pvDocument
     */
    private boolean schreibeKunden(String pvFilenameOut, Document pvDocument)
    {
        // Zur Ausgabe
        Element lvMyrootelement = new Element("importdaten");
        Namespace lvMyNamespace = Namespace.getNamespace("txsi", "http://agens.com/txsimport.xsd");
        lvMyrootelement.setNamespace(lvMyNamespace);
        Document lvMydocument = new Document(lvMyrootelement);
        
        Element lvRootNode = pvDocument.getRootElement();
 
        Element lvNodeHeader = (Element)lvRootNode.getChildren("Header").get(0);       

        LOGGER.info("Bestandsdatum: " + lvNodeHeader.getChildText("Bestandsdatum"));
        LOGGER.info("CorrelationID: " + lvNodeHeader.getChildText("CorrelationID"));
        LOGGER.info("SystemID: " + lvNodeHeader.getChildText("SystemID"));
        LOGGER.info("CreationTimestamp: " + lvNodeHeader.getChildText("CreationTimestamp"));            
        
        // Header anhaengen
        Element lvMyelement2 = new Element("header");

        lvMyelement2.setAttribute("valdate", DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvNodeHeader.getChildText("Bestandsdatum"))));
        lvMyrootelement.addContent(lvMyelement2);
        
        Element lvNodeResponse = (Element)lvRootNode.getChildren("Response").get(0);
        
        Element lvNodeInstitut = (Element)lvNodeResponse.getChildren("Institut").get(0);
         
        String lvInstitut = lvNodeInstitut.getAttributeValue("nr");

        LOGGER.info("Institut: " + lvInstitut);
        
        List<Element> lvListKunde = lvNodeInstitut.getChildren("Kunde");

        LOGGER.info("Anzahl gefundener Kunden: " + lvListKunde.size());

        Element lvNodeKunde;
        for (int i = 0; i < lvListKunde.size(); i++)
        {
          //StringBuffer lvHelpCSV = new StringBuffer();
        	 
         lvNodeKunde = (Element)lvListKunde.get(i);  
         if (lvNodeKunde.getAttributeValue("status").equals("R"))
         {                   
           // TXSPerson
           Element lvElement_pers = new Element("pers");
           lvElement_pers.setNamespace(lvMyNamespace);
          
            // pers aufbauen
            String lvKundennummer = lvNodeKunde.getAttributeValue("nr");
            lvElement_pers.setAttribute("kdnr", lvKundennummer);
            lvElement_pers.setAttribute("org", ValueMapping.changeMandant(lvInstitut));
            lvElement_pers.setAttribute("quelle", "KUNDE");
          
            // TXSPersonDaten
            Element lvElement_persdaten = new Element("persdaten");
            lvElement_persdaten.setNamespace(lvMyNamespace);
 
            // persdaten aufbauen
            String lvVorname = lvNodeKunde.getChildText("Vorname");
            lvElement_persdaten.setAttribute("vname", lvVorname);
            String lvNachname = lvNodeKunde.getChildText("Nachname");
            lvElement_persdaten.setAttribute("nname", lvNachname); // + nodeKunde.getChildText("Beruf"));
            //lvHelpCSV.append(lvNachname + ";");
        
            Element lvNodeAdresse = (Element)lvNodeKunde.getChildren("Adresse").get(0);
          
            lvElement_persdaten.setAttribute("str", lvNodeAdresse.getChildText("Strasse"));
            // CT 29.11.2011 - SPOT liefert eine Hausnummer
            lvElement_persdaten.setAttribute("hausnr", lvNodeAdresse.getChildText("Hausnr"));
            lvElement_persdaten.setAttribute("plz", lvNodeAdresse.getChildText("Plz"));
            lvElement_persdaten.setAttribute("ort", lvNodeAdresse.getChildText("Ort"));
            lvElement_persdaten.setAttribute("text", lvNodeAdresse.getChildText("Ergaenzung"));
            
            if (lvNodeAdresse.getChildText("Land").equals("U1"))
              lvElement_persdaten.setAttribute("land", "GB");
            else    
              lvElement_persdaten.setAttribute("land", lvNodeAdresse.getChildText("Land"));
            
            //lvHelpCSV.append(lvNodeAdresse.getChildText("Strasse") + ";" + lvNodeAdresse.getChildText("Hausnr") + ";" + lvNodeAdresse.getChildText("Plz") + ";" +  lvNodeAdresse.getChildText("Ort") + ";" + lvNodeAdresse.getChildText("Land"));
            
            //LOGGER.info("DeepSea;DeepSea;Kunde;" + lvKundennummer + ";" + lvHelpCSV.toString());
            
            // CT 22.12.2011 User-definierte Felder 
            lvElement_persdaten.setAttribute("kusyma", lvNodeKunde.getChildText("Kusyma_Neu"));
            lvElement_persdaten.setAttribute("kugr", ValueMapping.changeKundengruppe(ermittleKundengruppe(lvNodeKunde.getChildText("Kusyma"))));
                        
            // CT 30.11.2011 - SPOT liefert kein Gebiet, deshalb selbst ermitteln
            if (lvNodeAdresse.getChildText("Land").equals("DE"))
            {
                String lvGebiet = lvNodeAdresse.getChildText("Land") + "_";
                int lvRet_gebiet = ValueMapping.PLZ2Land(lvInstitut, lvNodeAdresse.getChildText("Plz"));
                if (lvRet_gebiet < 10)
                {
                    lvGebiet = lvGebiet + "0";
                }
                //if (ret_gebiet < 100)
                //{
                //  gebiet = gebiet + "0";
                //}
                lvGebiet = lvGebiet + lvRet_gebiet;
          
                lvElement_persdaten.setAttribute("gebiet", lvGebiet);
            }
            
            // CT 16.09.2014 User-definierte Rating Felder 
            Element lvNodeRating = (Element)lvNodeKunde.getChildren("Rating").get(0);
            if (!lvNodeRating.getChildText("rating_id").isEmpty())
            {
              lvElement_persdaten.setAttribute("rating_id", lvNodeRating.getChildText("rating_id"));
            }
            if (!lvNodeRating.getChildText("rating_master").isEmpty())
            {
              lvElement_persdaten.setAttribute("rating_master", lvNodeRating.getChildText("rating_master"));
            }      
            if (!lvNodeRating.getChildText("rating_datum").isEmpty())
            {
              lvElement_persdaten.setAttribute("rating_datum", lvNodeRating.getChildText("rating_datum").substring(8) + "." + lvNodeRating.getChildText("rating_datum").substring(5,7) + "." + lvNodeRating.getChildText("rating_datum").substring(0,4));
            }
            if (!lvNodeRating.getChildText("rating_tool_id").isEmpty())
            {
              lvElement_persdaten.setAttribute("rating_tool_id", lvNodeRating.getChildText("rating_tool_id"));
            }
            if (!lvNodeRating.getChildText("rating_tool").isEmpty())
            {  
              //String lvHelpRatingTool = String2XML.change2XML(lvNodeRating.getChildText("rating_tool")); 
              String lvHelpRatingTool = lvNodeRating.getChildText("rating_tool"); 
              if (lvHelpRatingTool.length() > 80)
              {
                //System.out.println("lvHelpRatingTool: " + lvHelpRatingTool);  
                lvHelpRatingTool = lvHelpRatingTool.substring(0,80);
                LOGGER.info("RatingTool gekuerzt: " + lvHelpRatingTool);  
              }
              lvElement_persdaten.setAttribute("rating_tool", lvHelpRatingTool);   
            }
            
            // pers anhaengen
            lvMyrootelement.addContent(lvElement_pers);
          
            // persdaten anhaengen
            lvElement_pers.addContent(lvElement_persdaten);
         }
         else
         {
             LOGGER.info("Kunde " + lvNodeKunde.getAttributeValue("nr") + " mit Status " + lvNodeKunde.getAttributeValue("status"));
         }
        }
        
        // Ausgabe
        FileOutputStream lvKundeOS = null;
        File lvFileKunde = new File(pvFilenameOut);
        try
        {
          lvKundeOS = new FileOutputStream(lvFileKunde);
        }
        catch (Exception e)
        {
          LOGGER.error("Konnte Kunde-XML Datei nicht oeffnen!");
        }    

        XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());
        
        try
        {
          lvOutputter.output(lvMydocument, lvKundeOS);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        return true;
    }

    /**
     * Ermittlung der Kundengruppe ueber die Kusyma 
     * @param pvKusyma
     * @return
     */
    private String ermittleKundengruppe(String pvKusyma)
    {
        // Kundengruppe aus Kusyma ermitteln 
        String lvKGruppe = new String();
    
        lvKGruppe = "H"; // Default
    
        if (pvKusyma.startsWith("0"))
        { // Inlaendische Kreditinstitute
            lvKGruppe = "G";
        }
        if (pvKusyma.startsWith("1"))
        { // Inlaendische Oeffentliche Haushalte
            if (pvKusyma.equals("10000000"))
            { // Bund
              lvKGruppe = "B_1";
            }
            else
            { // Nicht direkt Bund
                if (pvKusyma.equals("11000000") ||
                    pvKusyma.equals("12000000"))
                { // Bundeslaender
                    lvKGruppe = "C";
                }
                else
                { // keine Bundeslaender
                    if (pvKusyma.equals("13000000") ||
                        pvKusyma.equals("14000000"))
                    { // Gemeinden
                        lvKGruppe = "D";
                    }
                    else
                    { // keine Gemeinden 
                        if (pvKusyma.startsWith("15") ||
                            pvKusyma.startsWith("16") ||
                            pvKusyma.startsWith("17") ||
                            pvKusyma.startsWith("19"))
                        { // Oefftl.Untern/Zweck.
                            lvKGruppe = "E";
                        } 
                        else
                        { // Keine Oefftl.Unternehmen/Zweckverbaende 
                            if (pvKusyma.equals("10000001") ||
                                pvKusyma.startsWith("18"))
                            { // Andere durch Bund abgesicherte
                                lvKGruppe = "B_2";
                            }
                        } 
                    } 
                } 
            } 
        } 
    
        if (pvKusyma.startsWith("4"))
        { // Inlaendische Unternehmen...
            if (pvKusyma.charAt(1) == '0' || pvKusyma.charAt(1) == '1' ||
                pvKusyma.charAt(1) == '2' || pvKusyma.charAt(1) == '3' ||
                pvKusyma.charAt(1) == '4' || pvKusyma.charAt(1) == '5')
            { // mehrheitlich Oeffentlich
                lvKGruppe = "F";
            } 
        }
        if (pvKusyma.startsWith("5"))
        { // Auslaendische KI
            if (pvKusyma.charAt(1) == '0' || pvKusyma.charAt(1) == '1' ||
                pvKusyma.charAt(1) == '2' || pvKusyma.charAt(1) == '3')
            { // Internationale Institutionen
                lvKGruppe = "A_2";
            } 
        } 
        if (pvKusyma.startsWith("6"))
        { // Auslaendische Oeffentliche Haushalte
            if (pvKusyma.charAt(1) == '0')
            { // Zentralregierung 
                lvKGruppe = "A_1";
            } 
            else
            { // Keine Zentralregierung
                if (pvKusyma.charAt(1) == '2' || pvKusyma.charAt(1) == '4')
                { // Laender/Sonstige...
                    lvKGruppe = "A_3";
                }
                else
                { // Keine Laender...
                    if (pvKusyma.charAt(1) == '5' || pvKusyma.charAt(1) == '6')
                    { // Andere...
                        lvKGruppe = "A_6";
                    } 
                    else
                    { // Keine Anderen
                        if (pvKusyma.charAt(1) == '3')
                        { // Gemeinden .....
                            lvKGruppe = "A_4";
                        } 
                    } 
                }
            } 
        }
        
        return lvKGruppe;
    }
}
