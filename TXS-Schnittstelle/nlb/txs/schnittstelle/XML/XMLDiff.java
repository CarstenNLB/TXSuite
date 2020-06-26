package nlb.txs.schnittstelle.XML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

import nlb.txs.schnittstelle.Utilities.IniReader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLDiff 
{
	
    // Datenbankinfos - DNLBTXTE
    private final String svUsername = "txsrefi";
    private final String svPassword = "gruenNLB1";
    private final String svDatenbank = "DNLBTXTE";
    private final String svIP = "2.109.0.109";
    private final String svPort = "1521";

    /**
     * log4j-Logger
     */
    private static Logger LOGGER = Logger.getLogger("TXSXMLDiffLogger"); 
         
    private Connection ivConnection;

    /**
     * Startroutine XMLDiff
     * @param argv 
     */
    public static void main(String[] argv) 
    {
        if (!argv[argv.length - 1].endsWith(".ini"))
        {
            System.out.println("Starten:");
            System.out.println("XMLDiff <ini-Datei>");
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
                String lvLoggingXML = lvReader.getPropertyString("XMLDiff", "log4jconfig", "Fehler");
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
                
                String lvDatei1 = lvReader.getPropertyString("XMLDiff", "Datei1", "Fehler");
                if (lvDatei1.equals("Fehler"))
                {
                    LOGGER.error("Keine Datei1 in der ini-Datei...");
                    System.exit(1);
                }
                LOGGER.info("Datei1: " + lvDatei1);

                String lvDatei2 = lvReader.getPropertyString("XMLDiff", "Datei2", "Fehler");
                if (lvDatei2.equals("Fehler"))
                {
                    LOGGER.error("Keine Datei2 in der ini-Datei...");
                    System.exit(1);
                }
                LOGGER.info("Datei2: " + lvDatei2);
   
                new XMLDiff(lvDatei1, lvDatei2);                
            }
        }
        System.exit(0);
    }
    
    /**
     * Konstruktor 
     * @param pvDatei1
     * @param pvDatei2
     */
    public XMLDiff(String pvDatei1, String pvDatei2)
    {
    	////openConnection();
    	
        // Dateien vergleichen
        diff3(pvDatei1, pvDatei2);
        
        ////closeConnection();
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
     * Vergleichen von XML-Importdateien
     * @param pvDatei1
     * @param pvDatei2
     */
    private void diff2(String pvDatei1, String pvDatei2)
    {
    	HashMap<String, String> lvListeDatei1 = new HashMap<String, String>();
    	HashMap<String, String> lvListeDatei2 = new HashMap<String, String>();
        Document lvDocumentSource = null;
        
        // Datei1 einlesen
        lvDocumentSource = readXML(pvDatei1);
                	
        // RootNode vom Eingangsdokument einlesen
        Element lvRootNodeDatei = lvDocumentSource.getRootElement();

        writeAttributes2List(lvListeDatei1, new String(), lvRootNodeDatei);
        
        /*
        // Datei2 einlesen
        lvDocumentSource = readXML(pvDatei2);
                	
        // RootNode vom Eingangsdokument einlesen
        lvRootNodeDatei = lvDocumentSource.getRootElement();

        writeAttributes2List(lvListeDatei2, new String(), lvRootNodeDatei);

        // Vergleichen der beiden Listen
        for (String lvKey:lvListeDatei1.keySet())
        {
        	if (!lvListeDatei1.get(lvKey).equals(lvListeDatei2.get(lvKey)))
        	{
				LOGGER.info(lvKey + ": " + lvListeDatei1.get(lvKey)  + " != " + lvListeDatei2.get(lvKey));
        	}
        } */
    }
    
    /**
     * Vergleichen von XML-Importdateien
     * @param pvDatei1
     * @param pvDatei2
     */
    private void diff3(String pvDatei1, String pvDatei2)
    {
        LOGGER.info("Start - diff3");
        
        int lvAnzahlZeilen1 = 0;
        int lvAnzahlZeilen2 = 0;
        
        String lvZeile1 = null;
        String lvZeile2 = null;
        String lvKontonummer1 = null;
        String lvKontonummer2 = null;
                      
        // Oeffnen der Dateien
        FileInputStream lvFis1 = null;
        File lvFile1 = new File(pvDatei1);
        try
        {
            lvFis1 = new FileInputStream(lvFile1);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Datei '" + pvDatei1 + "' nicht oeffnen!");
            return;
        }

        FileInputStream lvFis2 = null;
        File lvFile2 = new File(pvDatei2);
        try
        {
            lvFis2 = new FileInputStream(lvFile2);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Datei '" + pvDatei2 + "' nicht oeffnen!");
            try
            {
            	lvFis1.close();
            }
            catch (Exception exp)
            {
            	LOGGER.error("Konnte die Datei '" + pvDatei1 + "' nicht schliessen!");
            }     
            return;
        }
        
        BufferedReader lvIn1 = new BufferedReader(new InputStreamReader(lvFis1));
        BufferedReader lvIn2 = new BufferedReader(new InputStreamReader(lvFis2));
        
        try
        {
            while ((lvZeile1 = lvIn1.readLine()) != null && (lvZeile2 = lvIn2.readLine()) != null)   // Lesen der Dateien
            {
            	lvAnzahlZeilen1++;                  		
            	lvAnzahlZeilen2++;
            	
            	// Kontonummer aus der fg-Zeile holen
            	if (lvZeile1.contains("<txsi:fg "))
            	{
            		lvKontonummer1 = lvZeile1.substring(lvZeile1.indexOf("key=") + 5, lvZeile1.indexOf("key=") + 15);
            	}
            	// Kontonummer aus der fg-Zeile holen
            	if (lvZeile2.contains("<txsi:fg "))
            	{
            		lvKontonummer2 = lvZeile2.substring(lvZeile2.indexOf("key=") + 5, lvZeile2.indexOf("key=") + 15);
            	}
            	
            	if (lvKontonummer1 != null && lvKontonummer2 != null && lvKontonummer1.equals(lvKontonummer2))
            	{
            		if (!lvZeile1.equals(lvZeile2))
            		{
            			LOGGER.info("Zeilen unterschiedlich: ");
            			LOGGER.info("Kontonummer: " + lvKontonummer1 + " - Zeile1: " + lvZeile1);
            			LOGGER.info("Kontonummer: " + lvKontonummer2 + " - Zeile2: " + lvZeile2);
            			searchDiff(lvZeile1, lvZeile2);
            		}
            	}
            	else
            	{
            		LOGGER.info("Kontonummer unterschiedlich: ");
        			LOGGER.info("Kontonummer: " + lvKontonummer1);
        			LOGGER.info("Kontonummer: " + lvKontonummer2);
            	}
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
                
        try
        {
            lvIn1.close();
            lvFis1.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte die Datei '" + pvDatei1 + "' nicht schliessen!");
        }     

        try
        {
            lvIn2.close();
            lvFis2.close();
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte die Datei '" + pvDatei2 + "' nicht schliessen!");
        }     
        
        LOGGER.info("Anzahl Zeilen in der Datei '" + pvDatei1 + "': " + lvAnzahlZeilen1);
        LOGGER.info("Anzahl Zeilen in der Datei '" + pvDatei2 + "': " + lvAnzahlZeilen2);

    }

    
    /**
     * Durchsucht die beiden Zeilen nach Unterscheidungen auf Feldebene
     * @param pvZeile1
     * @param pvZeile2
     */
    private void searchDiff(String pvZeile1, String pvZeile2)
    {
    	pvZeile1 = pvZeile1.substring(pvZeile1.indexOf("<"));
    	pvZeile2 = pvZeile2.substring(pvZeile2.indexOf("<"));
    	//System.out.println(pvZeile1);
    	//System.out.println(pvZeile1.indexOf(":"));
    	//System.out.println(pvZeile1.indexOf(" "));
    	String lvTransaktion = pvZeile1.substring(pvZeile1.indexOf(":") + 1, pvZeile1.indexOf(" "));
    	LOGGER.info("Transaktion: " + lvTransaktion);

    	
    	pvZeile1 = pvZeile1.substring(pvZeile1.indexOf(" ") + 1);
    	pvZeile2 = pvZeile2.substring(pvZeile2.indexOf(" ") + 1);
    	 
    	String lvFeld1 = new String();
    	String lvFeld2 = new String();

    	/*for (int i = 0; i < pvZeile1.length(); i++)
    	{
    		if (pvZeile1.charAt(i) == ' ')
    		{
    			if (!lvFeld1.equals(lvFeld2))
    			{
    			  LOGGER.info(lvFeld1);
    			  LOGGER.info(lvFeld2);
    			}
    			lvFeld1 = new String();
    			lvFeld2 = new String();
    		}
    		else
    		{
    			lvFeld1 = lvFeld1 + pvZeile1.charAt(i);
    			lvFeld2 = lvFeld2 + pvZeile2.charAt(i);
    		}
    		
    	} */
    	HashMap<String, String> lvListeFelder1 = new HashMap<String, String>();
    	HashMap<String, String> lvListeFelder2 = new HashMap<String, String>();
    	
    	while (pvZeile1.contains(" "))
    	{
    		lvFeld1 = pvZeile1.substring(0, pvZeile1.indexOf(" "));
    		while (!lvFeld1.endsWith("\""))
    		{
    			pvZeile1 = pvZeile1.substring(pvZeile1.indexOf(" ") + 1);
    			lvFeld1 = lvFeld1 + pvZeile1.substring(0, pvZeile1.indexOf(" "));
    		}
    		//LOGGER.info(lvFeld1);
    		//System.out.println(lvFeld1.substring(0, lvFeld1.indexOf("=")));
    		//System.out.println(lvFeld1.substring(lvFeld1.indexOf("=") + 2, lvFeld1.length() - 2));
    		lvListeFelder1.put(lvFeld1.substring(0, lvFeld1.indexOf("=")), lvFeld1.substring(lvFeld1.indexOf("=") + 2, lvFeld1.length() - 1));
    		pvZeile1 = pvZeile1.substring(pvZeile1.indexOf(" ") + 1);
    	}
    	
       	while (pvZeile2.contains(" "))
    	{
    		lvFeld2 = pvZeile2.substring(0, pvZeile2.indexOf(" "));
    		while (!lvFeld2.endsWith("\""))
    		{
    			pvZeile2 = pvZeile2.substring(pvZeile2.indexOf(" ") + 1);
    			lvFeld2 = lvFeld2 + pvZeile2.substring(0, pvZeile2.indexOf(" "));
    		}

    		//LOGGER.info(lvFeld2);
    		lvListeFelder2.put(lvFeld2.substring(0, lvFeld2.indexOf("=")), lvFeld2.substring(lvFeld2.indexOf("=") + 2, lvFeld2.length() - 1));
    		pvZeile2 = pvZeile2.substring(pvZeile2.indexOf(" ") + 1);
    	}
       	
       	
       	//Set<String> lvSet1 = lvListeFelder1.keySet();
       	//Iterator<String> lvIter1 = lvSet1.iterator();
       	HashMap <String, String> lvHelpListe = (HashMap<String,String>)lvListeFelder1.clone();
       	//while (lvIter1.hasNext())
       	for (String lvKey:lvHelpListe.keySet())
       	{
       		//String lvKey = lvIter1.next();
       	   if (lvListeFelder1.get(lvKey).equals(lvListeFelder2.get(lvKey)))
       	   {
       		   lvListeFelder1.remove(lvKey);
       		   lvListeFelder2.remove(lvKey);
       	   }
       	}
    	
       	LOGGER.info("Felder aus der ersten Datei:");
       	for (String lvKey:lvListeFelder1.keySet())
       	{
       		LOGGER.info(lvKey + "=" + lvListeFelder1.get(lvKey));
       	}
 
       	LOGGER.info("Felder aus der zweiten Datei:");
       	for (String lvKey:lvListeFelder2.keySet())
       	{
       		LOGGER.info(lvKey + "=" + lvListeFelder2.get(lvKey));
       	}
 
       	//System.out.println(lvListeFelder1.size());
       	//System.out.println(lvListeFelder2.size());
    	
    	
    }
    
    /**
     * Schreibt die Attribute in die Liste
     * @param pvListe
     * @param pvKontonummer
     * @param pvElement
     */
    private void writeAttributes2List(HashMap<String, String> pvListe, String pvKontonummer, Element pvElement)
    {
    	if (pvElement.getName().equals("fg"))
    	{
    		pvKontonummer = pvElement.getAttributeValue("key");
    	}
    	if (pvElement.getName().equals("pers"))
    	{
    		pvKontonummer = pvElement.getAttributeValue("kdnr");
    	}
    	if (pvElement.getName().equals("cfdaten"))
    	{
    		pvKontonummer = pvElement.getAttributeValue("cfkey");
    	}
    	//Attribute in Liste schreiben
    	for (Attribute lvAttribute:pvElement.getAttributes())
    	{
    		//pvListe.put(pvKontonummer + pvElement.getName() + lvAttribute.getName(), lvAttribute.getValue());
    		insert(pvKontonummer, pvElement.getName(), lvAttribute.getName(), lvAttribute.getValue());
    	}
    	//Rekursiver Aufruf fuer die Kinder
    	for (Element pvChild:pvElement.getChildren())
    	{
    		writeAttributes2List(pvListe, pvKontonummer, pvChild);
    	}
    }
    
    /**
     * Oeffnet eine Datenbankverbindung 
     */
    public void openConnection()
    {
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String lvUrl = "jdbc:oracle:thin:@//" + svIP + ":" + svPort + "/" + svDatenbank;
            ivConnection = DriverManager.getConnection(lvUrl, svUsername, svPassword);
        }
        catch (Exception exp)
        {
            LOGGER.error("Konnte keine Datenbankverbindung zu " + svIP + ":" + svPort + "/" + svDatenbank + " herstellen.");
        }
    }
    
    /**
     * Schliesst eine Datenbankverbindung
     */
    public void closeConnection()
    {
        try
        {
          ivConnection.close();
        }
        catch (Exception exp)
        {
            LOGGER.error("Konnte Datenverbindung nicht schliessen.");
        }
    }
    
    /** 
     * Einfuegen eines Datensatzes
     */
    public void insert(String pvKey, String pvTransaktion, String pvAttribute, String pvValue)
    {
    	try
    	{      
    		PreparedStatement stmt = ivConnection.prepareStatement("INSERT INTO txsrefi.monitoring VALUES (?,?,?,?)");
    		stmt.setString(1, pvKey);
    		stmt.setString(2, pvTransaktion);
    		stmt.setString(3, pvAttribute);
    		stmt.setString(4, pvValue);
            int lvResult = stmt.executeUpdate();
    		stmt.close();
    	}
    	catch (Exception exp)
    	{
    		LOGGER.error("Konnte Monitoring-Datensatz nicht einfuegen.");
    	}  
    }

    /**
     * Vergleichen von XML-Importdateien
     * @param pvDatei1
     * @param pvDatei2
     */
    private void diff(String pvDatei1, String pvDatei2)
    {
    	HashMap<String, Element> lvListeFg = new HashMap<String, Element>();
        Document lvDocumentSource1 = null;
        
        // Datei1 einlesen
        lvDocumentSource1 = readXML(pvDatei1);
                	
        // RootNode vom Eingangsdokument einlesen
        Element lvRootNodeDatei1 = lvDocumentSource1.getRootElement();

        List<Element> lvListDatei1 = lvRootNodeDatei1.getChildren();
        for (int i = 0; i < lvListDatei1.size(); i++)
        {
        	Element lvNode = (Element)lvListDatei1.get(i); 
        	if (lvNode.getName().equals("fg"))
        	{
              lvListeFg.put(lvNode.getAttributeValue("key"), lvNode);
        	}
        }
        
        LOGGER.info("Anzahl der Finanzgeschaefte in Datei 1: " + lvListeFg.size());
                               
        // Datei2 einlesen
        Document lvDocumentSource2 = null;
        
        // Datei1 einlesen
        lvDocumentSource2 = readXML(pvDatei2);
                	
        // RootNode vom Eingangsdokument einlesen
        Element lvRootNodeDatei2 = lvDocumentSource2.getRootElement();
       
        List<Element> lvList = lvRootNodeDatei2.getChildren();
        int lvAnzahlFg = 0;
        for (int i = 0; i < lvList.size(); i++)
        {
        	Element lvNode = (Element)lvList.get(i); 
        	if (lvNode.getName().equals("fg"))
        	{
        		lvAnzahlFg++;
        		Element lvNodeDatei1 = lvListeFg.get(lvNode.getAttribute("key").getValue());
        		if (lvNodeDatei1 == null)
        		{
        			LOGGER.info("Finanzgeschaeft nicht in Datei1: " + lvNode.getAttributeValue("key"));
        		}
        		else
        		{
        			compareFinanzgeschaefte(lvNodeDatei1, lvNode);
        			lvListeFg.remove(lvNode.getAttribute("key").getValue());
        		}
        	}
        }
        
        LOGGER.info("Anzahl der Finanzgeschaefte in Datei 2: " + lvAnzahlFg);  
        for (Element lvElement:lvListeFg.values())
        {
        	LOGGER.info("Finanzgeschaeft nicht in Datei2: " + lvElement.getAttributeValue("key"));
        }
   } 
    
   /**
    * Vergleicht zwei Finanzgeschaefte(Elemente) ohne die Kreditsicherheit
    * @param pvElement1
    * @param pvElement2
    */
    private void compareFinanzgeschaefte(Element pvElement1, Element pvElement2)
    {
    	//LOGGER.info("Vergleiche Konto " + pvElement1.getAttributeValue("key"));
    	//LOGGER.info(pvElement1.toString());
    	//LOGGER.info(pvElement2.toString());
    	StringBuilder lvHeader = new StringBuilder();
    	StringBuilder lvDatei1 = new StringBuilder();
    	StringBuilder lvDatei2 = new StringBuilder();
		  lvHeader.append("Diff;Key;");
		  lvDatei1.append("Diff;" + pvElement1.getAttributeValue("key") + ";");
		  lvDatei2.append("Diff;" + pvElement2.getAttributeValue("key") + ";");
        
    	for (Element lvChild1:pvElement1.getChildren())
    	{
    		Element lvChild2 = null;
    	   	for (Element lvHelpChild:pvElement2.getChildren())
        	{
        		if (lvHelpChild.getName().equals(lvChild1.getName()))
        		{
        			lvChild2 = lvHelpChild;
        		}
        	}

    		if (lvChild2 == null)
    		{
    			LOGGER.info(lvChild1.getName() + " in beiden Dateien nicht vorhanden");
    		}
    		else
    		{
    			if (!lvChild1.getName().equals("kredsh"))
    			{
    				
    				for (Attribute lvAttribute1:lvChild1.getAttributes())
    				{
    					Attribute lvAttribute2 = lvChild2.getAttribute(lvAttribute1.getName());
    					if (lvAttribute2 == null)
    					{
    						LOGGER.info(lvAttribute1.getName() + " in beiden Dateien nicht vorhanden");
    					}
    					else
    					{
    						if (!lvAttribute1.getValue().equals(lvAttribute2.getValue()))
    						{
    							BigDecimal lvHelpWert1 = new BigDecimal("0.0");
    							BigDecimal lvHelpWert2 = new BigDecimal("0.0");
    							try
    							{
    								lvHelpWert1 = new BigDecimal(lvAttribute1.getValue());
    								lvHelpWert2 = new BigDecimal(lvAttribute2.getValue());
    							}
    							catch (NumberFormatException exp)
    							{
    								LOGGER.info("Konto " + pvElement1.getAttributeValue("key") + " - Transaktion " + lvChild1.getName() + " - Attribute " + lvAttribute1.getName() + ": " + lvAttribute1.getValue() + " != " + lvAttribute2.getValue());    
    								lvHeader.append(lvAttribute1.getName() + ";");
    								lvDatei1.append(lvAttribute1.getValue() + ";");
    								lvDatei2.append(lvAttribute2.getValue() + ";");
    							}

    							if (lvHelpWert1.doubleValue() != lvHelpWert2.doubleValue())
    							{
    							  LOGGER.info("Konto " + pvElement1.getAttributeValue("key") + " - Transaktion " + lvChild1.getName() + " - Attribute " + lvAttribute1.getName() + ": " + lvAttribute1.getValue() + " != " + lvAttribute2.getValue());
    							  lvHeader.append(lvAttribute1.getName() + ";");
    							  lvDatei1.append(lvAttribute1.getValue() + ";");
    							  lvDatei2.append(lvAttribute2.getValue() + ";");
    							}
    						}
    					}
    				}
    			}
    			else
    			{
    				// Kreditsicherheit vergleichen -> eigene Methode
    				compareSicherheiten(lvChild1, lvChild2);
    			}
    		}
    	}
		  LOGGER.info(lvHeader.toString());
		  LOGGER.info(lvDatei1.toString());
		  LOGGER.info(lvDatei2.toString());
    }
 
    /**
     * Vergleicht zwei Sicherheiten(Elemente)
     * @param pvElement1
     * @param pvElement2
     */
     private void compareSicherheiten(Element pvElement1, Element pvElement2)
     {
         Element lvChild1 = pvElement1.getChildren().get(0);
         Element lvChild2 = pvElement2.getChildren().get(0);

         LOGGER.info("Under construction: Sicherheiten vergleichen");
         LOGGER.info("Sicherheit1: " + lvChild1.toString());
         LOGGER.info("Sicherheit2: " + lvChild2.toString());

     }

    
}
