/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Wertpapier.Gattungsdaten;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nlb.txs.schnittstelle.Utilities.IniReader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author tepperc
 *
 */
public class Gattungsdaten 
{    
    /**
     * Liste der Gattungen
     */
    private HashMap<String, Gattung> ivListeGattungen;
    
    /**
     * Bestandsdatum
     */
    private String ivBestandsdatum;
    
    /**
     * CorrelationID
     */
    private String ivCorrelationID;

    /**
     * SystemID
     */
    private String ivSystemID;
    
    /**
     * CreationTimestamp
     */
    private String ivCreationTimestamp;

    /**
     * Institutsnummer
     */
    private String ivInstitut;
    
    /**
     * 
     */
    private Document ivDocument;
    
    /**
     * Logger - Verwendung von log4j
     */
    private static Logger LOGGER_Gattungsdaten = Logger.getLogger("TXSGattungsdatenLogger");

    /**
     * Startroutine
     * @param args Uebergabeparameter
     */
    public static void main(String[] args) 
    {        
        if (!args[args.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("Gattungsdaten <ini-Datei>");
            System.exit(1);
        }
        else
        {                                  
            IniReader lvReader = null;
            try 
            {
                lvReader = new IniReader(args[args.length - 1]);
            }
            catch (Exception exp)
            {
                System.out.println("Fehler beim Laden der ini-Datei...");
                System.exit(1);
            }
            
            new Gattungsdaten(lvReader, null);              
        }
        System.exit(0);

    }
    
    /**
     * Konstruktor
     * @param pvReader
     * @param pvLogger log4j-Logger
     */
    public Gattungsdaten(IniReader pvReader, Logger pvLogger)
    { 
        if (pvReader != null)
        {
        	if (pvLogger == null)
        	{
               String lvLoggingXML = pvReader.getPropertyString("Gattungsdaten", "log4jconfig", "Fehler");
               if (lvLoggingXML.equals("Fehler"))
               {
                 System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
               }
               else
               {
                  DOMConfigurator.configure(lvLoggingXML); 
               }
        	}
        	else
        	{
        		LOGGER_Gattungsdaten = pvLogger;
        	}

            String lvInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (lvInstitutsnummer.equals("Fehler"))
            {
                LOGGER_Gattungsdaten.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
                        
            String lvImportVerzeichnis = pvReader.getPropertyString("Gattungsdaten", "ImportVerzeichnis", "Fehler");
            if (lvImportVerzeichnis.equals("Fehler"))
            {
                LOGGER_Gattungsdaten.error("Kein Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            String lvResponseDatei = pvReader.getPropertyString("Gattungsdaten", "ResponseDatei", "Fehler");
            if (lvResponseDatei.equals("Fehler"))
            {
                LOGGER_Gattungsdaten.error("Kein Response-Dateiname in der ini-Datei...");
                System.exit(1);
            }

            Runtime lvRuntime = Runtime.getRuntime();
        
            LOGGER_Gattungsdaten.info("Used Memory vor dem Lesen:"
                                      + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
        
            leseGattungsdaten(lvImportVerzeichnis + "\\" + lvResponseDatei);

            LOGGER_Gattungsdaten.info("Used Memory nach dem Lesen:"
                                      + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024)); 
        
            verarbeiteGattungsdaten();
        }
     }
    
    /**
     * Liest die Gattungsdaten
     * @param pvFilename Dateiname der Gattungsdaten
     */
    private void leseGattungsdaten(String pvFilename)
    {
    	// Status des Datensatzes
    	// R - Gattung gefunden, Daten vollstaendig
    	// X - Gattung gefunden, Daten unvollstaendig
    	// U - Gattung nicht gefunden
    	// D - Gattung geloescht (technisch)
    	// I - Gattung inaktiv
    	
    	// REQUEST
    	// blank - Default
    	// X - Gattung nicht gefunden, in Requestliste eingetragen
    	// Wird eine angeforderte Gattung nicht gefunden, muss sie in die Tabelle L3_S_INSTRUMENT_REQUEST hinterlegt werden.
    	// Sie wird dann ggf. am Folgetag angeliefert.
    	
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
             System.out.println(io.getMessage());
        }
        catch(JDOMException jdomex)
        {
             System.out.println(jdomex.getMessage());
        }
    }
    
    /**
     * Schreibt die Gattungsdaten
     */
    private void verarbeiteGattungsdaten()
    {
        // Liste der Gattungen initialisieren
        ivListeGattungen = new HashMap<String, Gattung>();
        
        Element lvRootNode = ivDocument.getRootElement();
        //System.out.println(rootNode.toString());
 
        Element lvNodeHeader = (Element)lvRootNode.getChildren("Header").get(0);       

        ivBestandsdatum = lvNodeHeader.getChildText("Bestandsdatum");
        ivCorrelationID = lvNodeHeader.getChildText("CorrelationID");
        ivSystemID = lvNodeHeader.getChildText("SystemID");
        ivCreationTimestamp = lvNodeHeader.getChildText("CreationTimestamp");
        
        LOGGER_Gattungsdaten.info("Bestandsdatum: " + ivBestandsdatum);
        LOGGER_Gattungsdaten.info("CorrelationID: " + ivCorrelationID);
        LOGGER_Gattungsdaten.info("SystemID: " + ivSystemID);
        LOGGER_Gattungsdaten.info("CreationTimestamp: " + ivCreationTimestamp);            
                
        Element lvNodeResponse = (Element)lvRootNode.getChildren("Response").get(0);
        
        Element lvNodeInstitut = (Element)lvNodeResponse.getChildren("Institut").get(0);
         
        ivInstitut = lvNodeInstitut.getAttributeValue("nr");

        LOGGER_Gattungsdaten.info("Institut: " + ivInstitut);
        
        List<Element> lvListGattungsdaten = lvNodeInstitut.getChildren("Gattung");

        LOGGER_Gattungsdaten.info("Anzahl gefundener Gattungsdaten: " + lvListGattungsdaten.size());

        Element lvNodeGattung;
        for (int i = 0; i < lvListGattungsdaten.size(); i++)
        {
          lvNodeGattung = (Element)lvListGattungsdaten.get(i);
          String lvISIN = lvNodeGattung.getAttributeValue("ISIN");
          String lvStatus = lvNodeGattung.getAttributeValue("STATUS");
          String lvRequest = lvNodeGattung.getAttributeValue("REQUEST");
          
          LOGGER_Gattungsdaten.info("Gattungsdaten fuer ISIN: " + lvISIN + " mit Status: " + lvStatus + " und Request: " + lvRequest);
          Gattung lvGattung = new Gattung();
          lvGattung.setGD160(lvNodeGattung.getChildText("GD160"));
          lvGattung.setGD161(lvNodeGattung.getChildText("GD161"));
          lvGattung.setGD214(lvNodeGattung.getChildText("GD214"));
          lvGattung.setGD226(lvNodeGattung.getChildText("GD226"));
          lvGattung.setGD230(lvNodeGattung.getChildText("GD230"));
          lvGattung.setGD240(lvNodeGattung.getChildText("GD240"));
          lvGattung.setGD245(lvNodeGattung.getChildText("GD245"));
          lvGattung.setGD260(lvNodeGattung.getChildText("GD260"));
          lvGattung.setGD270(lvNodeGattung.getChildText("GD270"));
          lvGattung.setGD280A(lvNodeGattung.getChildText("GD280A"));
          lvGattung.setGD290A(lvNodeGattung.getChildText("GD290A"));
          lvGattung.setGD311A(lvNodeGattung.getChildText("GD311A"));
          lvGattung.setGD312(lvNodeGattung.getChildText("GD312"));
          lvGattung.setGD321(lvNodeGattung.getChildText("GD321"));
          lvGattung.setGD322(lvNodeGattung.getChildText("GD322"));
          lvGattung.setGD481A(lvNodeGattung.getChildText("GD481A"));
          lvGattung.setGD483(lvNodeGattung.getChildText("GD483"));
          lvGattung.setGD572(lvNodeGattung.getChildText("GD572"));
          lvGattung.setGD630A(lvNodeGattung.getChildText("GD630A"));
          lvGattung.setGD630B(lvNodeGattung.getChildText("GD630B"));
          lvGattung.setGD660(lvNodeGattung.getChildText("GD660"));
          lvGattung.setGD669(lvNodeGattung.getChildText("GD669"));
          lvGattung.setGD776I(lvNodeGattung.getChildText("GD776I"));
          lvGattung.setGD801A(lvNodeGattung.getChildText("GD801A"));
          lvGattung.setGD803E(lvNodeGattung.getChildText("GD803E"));
          lvGattung.setGD804E(lvNodeGattung.getChildText("GD804E"));
          lvGattung.setGD805(lvNodeGattung.getChildText("GD805"));
          lvGattung.setGD808(lvNodeGattung.getChildText("GD808"));
          lvGattung.setGD808A(lvNodeGattung.getChildText("GD808A"));
          lvGattung.setGD808B(lvNodeGattung.getChildText("GD808B"));
          lvGattung.setGD808C(lvNodeGattung.getChildText("GD808C"));
          lvGattung.setGD809(lvNodeGattung.getChildText("GD809"));
          lvGattung.setGD809C(lvNodeGattung.getChildText("GD809C"));
          lvGattung.setGD811(lvNodeGattung.getChildText("GD811"));
          lvGattung.setGD815B(lvNodeGattung.getChildText("GD815B"));
          lvGattung.setGD821B(lvNodeGattung.getChildText("GD821B"));
          lvGattung.setGD841(lvNodeGattung.getChildText("GD841"));
          lvGattung.setGD842(lvNodeGattung.getChildText("GD842"));
          lvGattung.setGD861A(lvNodeGattung.getChildText("GD861A"));
          lvGattung.setGD900(lvNodeGattung.getChildText("GD900"));
          lvGattung.setGD910(lvNodeGattung.getChildText("GD910"));
          lvGattung.setGD921B(lvNodeGattung.getChildText("GD921B"));
          lvGattung.setGD943(lvNodeGattung.getChildText("GD943"));
          lvGattung.setGD952(lvNodeGattung.getChildText("GD952"));
          lvGattung.setGD970G(lvNodeGattung.getChildText("GD970G"));  
          lvGattung.setGD323(lvNodeGattung.getChildText("GD323"));
          lvGattung.setGD776(lvNodeGattung.getChildText("GD776"));
          lvGattung.setGD776B(lvNodeGattung.getChildText("GD776B"));
          lvGattung.setGD776K(lvNodeGattung.getChildText("GD776K"));
          lvGattung.setGD942(lvNodeGattung.getChildText("GD942"));
          lvGattung.setGD970i(lvNodeGattung.getChildText("GD970i"));
          lvGattung.setRefIndex(lvNodeGattung.getChildText("REF_INDEX"));
          lvGattung.setRefIndexCurrency(lvNodeGattung.getChildText("REF_INDEX_CURR"));
          lvGattung.setRefIndexTerm(lvNodeGattung.getChildText("REF_INDEX_TERM"));
          lvGattung.setProduktTyp(lvNodeGattung.getChildText("PRODUKT_TYP"));
          lvGattung.setBoersennotiert(lvNodeGattung.getChildText("BOERSENNOTIERT"));
          
          // Adresse
          Element lvNodeAdresse = (Element)lvNodeGattung.getChildren("Adresse").get(0);
          Adresse lvAdresse = new Adresse(lvNodeAdresse.getChildText("KUNDE_NR"), lvNodeAdresse.getChildText("NAME"), lvNodeAdresse.getChildText("STRASSE"),
                                          lvNodeAdresse.getChildText("PLZ"), lvNodeAdresse.getChildText("ORT"));
          lvGattung.setAdresse(lvAdresse);
          
          // Kuendigungstermine
          Element lvNodeKuendigung = (Element)lvNodeGattung.getChildren("Kuendigung").get(0);
          List<Element> lvListTermine = lvNodeKuendigung.getChildren("Termin");

          LOGGER_Gattungsdaten.info("Anzahl gefundener Termine: " + lvListTermine.size());

          ArrayList<KuendigungTermin> lvKuendigungstermine = new ArrayList<KuendigungTermin>();
          Element lvNodeTermin;
          for (int j = 0; j < lvListTermine.size(); j++)
          {
            lvNodeTermin = (Element)lvListTermine.get(j);
            KuendigungTermin lvKuendigungTermin = new KuendigungTermin(lvNodeTermin.getChildText("KUEND_KURS"), lvNodeTermin.getChildText("KUEND_PER"));
            lvKuendigungstermine.add(lvKuendigungTermin);
          }
          lvGattung.setKuendigungstermine(lvKuendigungstermine);
          
          ivListeGattungen.put(lvISIN, lvGattung);
        }
    }
    
    /**
     * Liefert die Gattung zu einer Wertpapier ISIN
     * @param pvISIN 
     * @return Die gefundene Gattung zur ISIN
     */
    public Gattung getGattung(String pvISIN)
    {
        return ivListeGattungen.get(pvISIN);
    }

	/**
	 * Liefert das Bestandsdatum
	 * @return the ivBestandsdatum
	 */
	public String getBestandsdatum() 
	{
		return ivBestandsdatum;
	}

	/**
	 * Setzt das Bestandsdatum
	 * @param pvBestandsdatum the ivBestandsdatum to set
	 */
	public void setBestandsdatum(String pvBestandsdatum) 
	{
		this.ivBestandsdatum = pvBestandsdatum;
	}

	/**
	 * Liefert die CorrelationID
	 * @return the ivCorrelationID
	 */
	public String getCorrelationID() 
	{
		return ivCorrelationID;
	}

	/**
	 * Setzt die CorrelationID
	 * @param pvCorrelationID the ivCorrelationID to set
	 */
	public void setCorrelationID(String pvCorrelationID) 
	{
		this.ivCorrelationID = pvCorrelationID;
	}

	/**
	 * Liefert die SystemID
	 * @return the ivSystemID
	 */
	public String getSystemID() 
	{
		return ivSystemID;
	}

	/**
	 * Setzt die SystemID
	 * @param pvSystemID the ivSystemID to set
	 */
	public void setSystemID(String pvSystemID) 
	{
		this.ivSystemID = pvSystemID;
	}

	/**
	 * Liefert den CreationTimestamp
	 * @return the ivCreationTimestamp
	 */
	public String getCreationTimestamp() 
	{
		return ivCreationTimestamp;
	}

	/**
	 * Setzt den CreationTimestamp
	 * @param pvCreationTimestamp the ivCreationTimestamp to set
	 */
	public void setCreationTimestamp(String pvCreationTimestamp) 
	{
		this.ivCreationTimestamp = pvCreationTimestamp;
	}

	/**
	 * Liefert das Institut
	 * @return the ivInstitut
	 */
	public String getInstitut() 
	{
		return ivInstitut;
	}

	/**
	 * Setzt das Institut
	 * @param pvInstitut the ivInstitut to set
	 */
	public void setInstitut(String pvInstitut) 
	{
		this.ivInstitut = pvInstitut;
	}
}
