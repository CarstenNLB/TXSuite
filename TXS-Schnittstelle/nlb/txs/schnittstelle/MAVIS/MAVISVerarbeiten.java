package nlb.txs.schnittstelle.MAVIS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Wertpapier.Aktiv;
import nlb.txs.schnittstelle.Wertpapier.Passiv;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

@Deprecated
public class MAVISVerarbeiten 
{
	/**
	 * log4j-Logger
	 */
	private static Logger LOGGER = LogManager.getLogger("TXSMAVISLogger");	
	
    private String ivMyBLZ;
    private String ivMyProjekt;
    private String ivMyDatum = "0000-00-00";

    //private String ivExportMAVIS;
    private String ivRueckmeldungMAVIS;

    private String ivFileout;  
    private String ivFileout_mavquell;
    
    private String ivEingabeverzeichnis;
    private String ivLogdateiname;
    private String ivMaske;
    
    /**
     * Liste der RueckmeldeDaten 
     */
    private HashMap<String, RueckmeldeDatenMAVIS> ivListeRueckmeldeDaten;

    /**
     * Konstruktor fuer Verarbeitung MAVIS     
     * @param pvReader 
     */
    public MAVISVerarbeiten(IniReader pvReader)
    {
        if (pvReader != null)
        {
        	String lvInstitut = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (lvInstitut.equals("Fehler"))
            {
                LOGGER.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER.info("Institut=" + lvInstitut);   
                
                if (lvInstitut.equals("NLB"))
                {
                    ivMyBLZ = "25050000";         // BLZ der NordLB
                    ivMyProjekt = "NLB-PfandBG";
                }
                else if (lvInstitut.equals("BLB"))
                {
                    ivMyBLZ = "29050000";         // BLZ der Bremer Landesbank
                    ivMyProjekt = "BLB-PfandBG";
                }   
                else
                {
                    LOGGER.error("nicht zu verabeitendes Institut");
                    System.exit(1);
                }
            }
            
            ivFileout = pvReader.getPropertyString("MAVIS", "MAVXMLAUS", "Fehler");
            if (ivFileout.equals("Fehler"))
            {
                LOGGER.error("Kein [MAVIS][MAVXMLAUS] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER.info("MAVXMLAUS=" + ivFileout);                    
            }
                            
            ivEingabeverzeichnis = pvReader.getPropertyString("MAVIS", "MAVISDIR", "Fehler");
            if (ivEingabeverzeichnis.equals("Fehler"))
            {
                LOGGER.error("Kein [MAVIS][MAVISDIR] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER.info("MAVISDIR=" + ivEingabeverzeichnis);                    
            }
            
            ivLogdateiname = pvReader.getPropertyString("MAVIS", "MAVISLOG", "Fehler");
            if (ivLogdateiname.equals("Fehler"))
            {
                LOGGER.error("Kein [MAVIS][MAVISLOG] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER.info("MAVISLOG=" + ivLogdateiname);                    
            }
            
            ivMaske = pvReader.getPropertyString("MAVIS", "MAVISMSK", "Fehler");
            if (ivMaske.equals("Fehler"))
            {
                LOGGER.error("Kein [MAVIS][MAVISMSK] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER.info("MAVISMSK=" + ivMaske);                    
            }
            
            ivFileout_mavquell = pvReader.getPropertyString("MAVIS", "MAVQUELL", "Fehler");
            if (ivFileout_mavquell.equals("Fehler"))
            {
                LOGGER.error("Kein [MAVIS][MAVQUELL] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER.info("MAVQUELL=" + ivFileout_mavquell);                    
            }
            
            //ivExportMAVIS = pvReader.getPropertyString("Rueckmeldung", "ImportVerzeichnis", "Fehler");
            //if (ivExportMAVIS.equals("Fehler"))
            //{
            //    LOGGER.error("Kein [Rueckmeldung][ImportVerzeichnis] in der ini-Datei...");
            //    System.exit(1);
            //}
            //else
            //{
            //    LOGGER.info("ExportMAVIS=" + ivExportMAVIS);                    
            //}

            ivRueckmeldungMAVIS = pvReader.getPropertyString("Rueckmeldung", "RueckmeldungInMAVIS_" + lvInstitut, "Fehler");
            if (ivRueckmeldungMAVIS.equals("Fehler"))
            {
                LOGGER.error("Kein [Rueckmeldung][RueckmeldungInMAVIS_" + lvInstitut + "] in der ini-Datei...");
                System.exit(1);
            }
            else
            {
                LOGGER.info("RueckmeldungInMAVIS_NLB=" + ivRueckmeldungMAVIS);                    
            }
        }
        
        // Initialisierung der Liste der RueckmeldeDaten
        ivListeRueckmeldeDaten = new HashMap<String, RueckmeldeDatenMAVIS>();
        // Liest die RueckmeldeDaten aus einer Datei ein
        leseRueckmeldeDaten(ivRueckmeldungMAVIS);
        // Start der Verarbeitung
        startVerarbeitung();
        // Schreibt die RueckmeldeDaten in eine Datei
        schreibeRueckmeldeDaten(ivRueckmeldungMAVIS);
    }
    
    /**
     *
     */
    private void startVerarbeitung() 
    {          
        FileInputStream  lvStreamlogein = null;
        FileOutputStream lvStreamlogaus = null;
                
        // Fuer die Ausgabe der Importdatei
        File lvTXSFile = new File(ivFileout);
        FileOutputStream  lvTXSost = null;
        
        // F�r die Ausgabe der Quellsystemdatei        
        File lvQuelle = new File(ivEingabeverzeichnis + ivFileout_mavquell);
        FileOutputStream lvQuelleost = null;
        
        try
        {
            lvQuelleost = new FileOutputStream(lvQuelle);
        }
        catch (Exception e)
        {
            LOGGER.error("Cashflow_Quellsystemdatei " + lvQuelle +" nicht oeffnen!");
            System.exit(1);
        }
                
        File lvEingabedir = new File(ivEingabeverzeichnis);
        File lvLogdatei = new File(ivEingabeverzeichnis + ivLogdateiname);
        
        String[] lvListing = lvEingabedir.list();
        
        String lvNeuedatei = ivMaske + ".D000000";
        
        for (int n = 0; n < lvListing.length; n++)
        {
                      
          try
          {
            if (lvListing[n].substring(0,ivMaske.length()).equals(ivMaske))
            {                    
              String lvNeuD = lvListing[n].substring(lvListing[n].lastIndexOf(".D")+2, lvListing[n].lastIndexOf(".D")+8);
              String lvAltD = lvNeuedatei.substring(lvNeuedatei.lastIndexOf(".D")+2,lvNeuedatei.lastIndexOf(".D")+8); 
 
              if ( lvNeuD.compareTo(lvAltD) > 0)
              {
                lvNeuedatei = lvListing[n];
                int lvDatanfang = lvNeuedatei.lastIndexOf(".D")+2;
                ivMyDatum =  "20" + lvNeuedatei.substring(lvDatanfang, lvDatanfang+2)  +
                           "-" + lvNeuedatei.substring(lvDatanfang+2, lvDatanfang+4) +
                           "-" + lvNeuedatei.substring(lvDatanfang+4, lvDatanfang+6)  ;
                
              }
            }
          }
          catch( java.lang.StringIndexOutOfBoundsException e)
          {
            LOGGER.error("Dateiname kuerzer als A1PBAT.NT.I009.LMTXS.KSXML"); 
          }
        }
        
        LOGGER.info("Datei mit hoechstem daypointer " + lvNeuedatei);
        
        // logdatei zur eingabe �ffnen
        try
        {
          lvStreamlogein = new FileInputStream(lvLogdatei); 
        }
        catch (Exception e)
        {
          LOGGER.error("Konnte logdatei " + ivEingabeverzeichnis + ivLogdateiname + " nicht oeffnen");
          System.exit(1);
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
            LOGGER.error("Aerger mit dem bufferedreader");
        }
        
        if (1 == lvDateischoninlog)
        {
            LOGGER.error("bereits in log");
            System.exit(1);
        }
        
        try
        {
        	lvLogBR.close();
        }
        catch(Exception e)
        {
          LOGGER.error("Konnte 'LogBR' nicht schliessen...");	
        }
        
        try
        {
        	lvLogISR.close();
        }
        catch(Exception e)
        {
        	LOGGER.error("Konnte 'LogSR' nicht schliesen...");
        }
        
        try
        {
        	lvStreamlogein.close();
        }
        catch(Exception e)
        {
        	LOGGER.error("Konnte 'Streamlogein' nicht schliessen...");
        }
                
        // Ausgabe Importdatei oeffnen
        try
        {
          lvTXSost = new FileOutputStream(lvTXSFile);
        }
        catch (Exception e)
        {
          LOGGER.error("Konnte TXS-Ausgabedatei " + ivFileout +" nicht oeffnen!");
          System.exit(1);
        }    
        
        // Zur Eingabe
        SAXBuilder lvBuilder = null;
        lvBuilder = new SAXBuilder();
        lvBuilder.setExpandEntities(true);
        
        // Zur Ausgabe
        Element lvMyrootelement = new Element("importdaten");
        Namespace lvMyNamespace = Namespace.getNamespace("txsi", "http://agens.com/txsimport.xsd");
        lvMyrootelement.setNamespace(lvMyNamespace);
        Document lvMydocument = new Document(lvMyrootelement);
        
        // aktiv passiv
        Aktiv lvTxsaktiv = new Aktiv(ivMyProjekt);
      
        Passiv lvTxspassiv = new Passiv();
               
        // header anhaengen
        Element lvMyelement2 = new Element("header");
        lvMyelement2.setAttribute("valdate",ivMyDatum);
        lvMyrootelement.addContent(lvMyelement2);
        
        try
        {
            Document lvDocument = (Document) lvBuilder.build(ivEingabeverzeichnis + lvNeuedatei);
            
            Element lvRootNode = lvDocument.getRootElement();
            List<Element> lvList = lvRootNode.getChildren("SATZ");
          
            List<Element> lvListKEY;
            Element lvNodeKEY;
            
            BigDecimal lvNbetragbd = new BigDecimal(0.00);
            BigDecimal lvAbetragbd = new BigDecimal(0.00);
            
            // Schleife ueber das document
            for (int i=0; i< lvList.size(); i++)
            {
              Element lvNode = (Element) lvList.get(i);
  
              
              lvListKEY = lvNode.getChildren("MavisKey");
              lvNodeKEY = (Element) lvListKEY.get(0);

              // RueckmeldeDaten aktualisieren oder neuen Datensatz anhaengen
              if (ivListeRueckmeldeDaten.containsKey(lvNodeKEY.getChildText("Produkt"))) // Aktualisieren
              {
            	  RueckmeldeDatenMAVIS lvRueckmeldeDaten = ivListeRueckmeldeDaten.get(lvNodeKEY.getChildText("Produkt"));
                  lvRueckmeldeDaten.setAnlieferungsdatum(ivMyDatum);
              }
              else // Neuer Datensatz anhaengen
              {
            	  RueckmeldeDatenMAVIS lvRueckmeldeDaten = new RueckmeldeDatenMAVIS();
            	  lvRueckmeldeDaten.setZeitMM(lvNodeKEY.getChildText("ZeitMM"));
            	  lvRueckmeldeDaten.setBilanzArt(lvNodeKEY.getChildText("BilanzArt"));
            	  lvRueckmeldeDaten.setInstitut(lvNodeKEY.getChildText("Institut"));
            	  lvRueckmeldeDaten.setFinanzKonto(lvNodeKEY.getChildText("FinanzKonto"));
            	  lvRueckmeldeDaten.setAktPas(lvNodeKEY.getChildText("AktPas"));
            	  lvRueckmeldeDaten.setBestandstyp(lvNodeKEY.getChildText("Bestandstyp"));
            	  lvRueckmeldeDaten.setEbene(lvNodeKEY.getChildText("Ebene"));
            	  lvRueckmeldeDaten.setProdukt(lvNodeKEY.getChildText("Produkt"));
            	  lvRueckmeldeDaten.setKZSoma(lvNodeKEY.getChildText("KZSoma"));
            	  lvRueckmeldeDaten.setDatumSoma(lvNodeKEY.getChildText("DatumSoma"));
            	  lvRueckmeldeDaten.setDepotNR(lvNodeKEY.getChildText("DepotNR"));
            	  lvRueckmeldeDaten.set99Zurueckgemeldet("N");
            	  lvRueckmeldeDaten.setAnlieferungsdatum(ivMyDatum);
            	  ivListeRueckmeldeDaten.put(lvNodeKEY.getChildText("Produkt"), lvRueckmeldeDaten);
              }
            	  
              // wenig konsistent, aber wegen der erweiterten Anlieferung aus MAVIS
              // wird der Deckungsschluessel hier schon benoetigt
              List<Element> lvListSA01_DS = lvNode.getChildren("SATZSA01");
              Element lvNodeSA01_DS = (Element) lvListSA01_DS.get(0);
              
              // Umstellung vom Deckungsschluessel auf das Ausplatzierungsmerkmal - CT 16.06.2016
              // alles ausser '99' und leer
              if (!("99".equals(lvNodeSA01_DS.getChildText("AusplazMM")) || lvNodeSA01_DS.getChildText("AusplazMM").isEmpty()))              
              {
            	  // Ausgabe
              
              // TXSFinanzgeschaeft
              Element lvElement_fg = new Element("fg");
              lvElement_fg.setNamespace(lvMyNamespace);
              
              // TXSFinanzgeschaeftDaten
              Element lvElement_fgdaten = new Element("fgdaten");
              lvElement_fgdaten.setNamespace(lvMyNamespace);
            
              // TXSKonditionenDaten
              Element lvElement_konddaten = new Element("konddaten");
              lvElement_konddaten.setNamespace(lvMyNamespace);

              // TXSWertpapierposition
              Element lvElement_wpposdaten = new Element("wpposdaten");
              lvElement_wpposdaten.setNamespace(lvMyNamespace);
              
              // TXSGlobalurkunde
              Element lvElement_gudaten = new Element("gudaten");
              lvElement_gudaten.setNamespace(lvMyNamespace);

              // TXSSliceInDaten
              Element lvElement_slicedaten = new Element("slicedaten");
              lvElement_slicedaten.setNamespace(lvMyNamespace);
              Element lvElement_slicedaten2 = new Element("slicedaten");
              lvElement_slicedaten2.setNamespace(lvMyNamespace);
              
              // TXSKundendaten
              Element lvElement_kredkunde = new Element("kredkunde");
              lvElement_kredkunde.setNamespace(lvMyNamespace);
              
              String lvTxsaktpastext ="?MAVI???";
              String lvMavisaktpas=lvNodeKEY.getChildText("AktPas");
              
              // Bestimmung des Quellsystems
              if (lvNodeSA01_DS.getChildText("AusplazMM").startsWith("S"))
              {
                  lvTxsaktpastext ="MAVISCHF";   
              }
              else if (lvNodeSA01_DS.getChildText("AusplazMM").startsWith("F"))
              {
                  lvTxsaktpastext ="MAVIFLUG";
              }
              else if (lvNodeSA01_DS.getChildText("AusplazMM").startsWith("O")) 
              {
                  lvTxsaktpastext ="MAVIOEPG";
              }
              else if (lvNodeSA01_DS.getChildText("AusplazMM").startsWith("K") ||
                       lvNodeSA01_DS.getChildText("AusplazMM").startsWith("H"))
              {
                  lvTxsaktpastext ="MAVIPFBG";
              }
              else
              {
                  lvTxsaktpastext ="MAVI????";
              } 
              
              // aktiv/passiv 
              // ist von mavis zu txs gedreht
              if (lvMavisaktpas.equals("1"))
              {
                  lvTxsaktpastext = "P" + lvTxsaktpastext;
                  
                  lvElement_fgdaten = lvTxspassiv.fgdaten_passiv(lvNode, lvNodeKEY);
                  lvElement_fgdaten.setNamespace(lvMyNamespace);
                  
                  lvElement_konddaten = lvTxspassiv.konddaten_passiv(lvNode, lvNodeKEY, ivMyBLZ);
                  lvElement_konddaten.setNamespace(lvMyNamespace);
                  
                  lvElement_wpposdaten = lvTxspassiv.wpposdaten_passiv(lvNode, lvNodeKEY);
                  lvElement_wpposdaten.setNamespace(lvMyNamespace);
                  
                  lvElement_gudaten = lvTxspassiv.gudaten_passiv(lvNode, lvNodeKEY);
                  lvElement_gudaten.setNamespace(lvMyNamespace);
                  
                  // fg aufbauen
                  lvElement_fg.setAttribute("key", lvNodeKEY.getChildText("Produkt"));
                  lvElement_fg.setAttribute("org", ivMyBLZ);
                  lvElement_fg.setAttribute("quelle",lvTxsaktpastext);
                  
                  lvQuelleost.write((lvNodeKEY.getChildText("Produkt") + ";" + ivMyBLZ + ";"+ lvTxsaktpastext + ";").getBytes() );
                  lvQuelleost.write(System.getProperty("line.separator").getBytes());
             
                  // fg anhaengen
                  lvMyrootelement.addContent(lvElement_fg);
                  
                  // fgdaten anhaengen
                  lvElement_fg.addContent(lvElement_fgdaten);
                  
                  // konddaten anhaengen
                  lvElement_fg.addContent(lvElement_konddaten);
                  
                  // Wertpapierpositionsdaten anhaengen
                  lvElement_fg.addContent(lvElement_wpposdaten);
                  
                  // Globalurkunde anh�ngen
                  lvElement_fg.addContent(lvElement_gudaten);
                  
              }
              else if (lvMavisaktpas.equals("2"))
              {
                  lvTxsaktpastext = "A" + lvTxsaktpastext;
                  
                  //String lvSetzetkurs = "ja";
                  boolean lvSetzetkurs = true;
                  //String lvZweiterslice ="nein";
                  boolean lvZweiterslice = false;
                  
                  List<Element> lvListSA03 = lvNode.getChildren("SATZSA03");
                  Element lvNodeSA03 = (Element) lvListSA03.get(0);
                  
                  lvNbetragbd = BigDecimal.valueOf(Double.parseDouble(lvNodeSA03.getChildText("Nominalbetrag").replace(",", ".")));
                  lvAbetragbd = BigDecimal.valueOf(Double.parseDouble(lvNodeSA03.getChildText("Betrag").replace(",", ".")));        
                  
                  for (int j=i+1; j< lvList.size(); j++)
                  {
                    Element lvNode2 = (Element) lvList.get(j);
        
                    List<Element> lvListKEY2 = lvNode2.getChildren("MavisKey");
                    Element lvNodeKEY2 = (Element) lvListKEY2.get(0);
                    
                    List<Element> lvListSA032 = lvNode2.getChildren("SATZSA03");
                    Element lvNodeSA032 = (Element) lvListSA032.get(0);
                    
                    List<Element> lvListSA01DS2 = lvNode2.getChildren("SATZSA01");
                    Element lvNodeSA01_DS2 = (Element) lvListSA01DS2.get(0);
                                        
                    // Umstellung vom Deckungsschluessel auf das Ausplatzierungsmerkmal - CT 16.06.2016
                    if (!("99".equals(lvNodeSA01_DS2.getChildText("AusplazMM")) || lvNodeSA01_DS.getChildText("AusplazMM").isEmpty()))
                    {
                    
                    	if (lvNodeKEY.getChildText("Produkt").equals(lvNodeKEY2.getChildText("Produkt")))
                    	{
                    		LOGGER.info("doppelt=" + lvNodeKEY2.getChildText("Produkt"));   
                      
                    		lvNbetragbd = lvNbetragbd.add(BigDecimal.valueOf(Double.parseDouble(lvNodeSA032.getChildText("Nominalbetrag").replace(",", "."))));
                    		lvAbetragbd = lvAbetragbd.add(BigDecimal.valueOf(Double.parseDouble(lvNodeSA032.getChildText("Betrag").replace(",", "."))));        
                      
                    		//lvSetzetkurs = "nein";
                    		lvSetzetkurs = false;
                    		//lvZweiterslice = "ja";
                    		lvZweiterslice = true;
                    		
                    		//slicedaten 2
                    		lvElement_slicedaten2 = lvTxsaktiv.slicedaten_aktiv(lvNode2,lvNodeKEY);
                    		lvElement_slicedaten2.setNamespace(lvMyNamespace);
                      
                    		// aus der Liste
                    		lvList.remove(j);
                    		// zaehler nachruecken
                    		j--;
                    	}
                    }
                  }  
                  
                  //slicedaten
                  lvElement_slicedaten = lvTxsaktiv.slicedaten_aktiv(lvNode,lvNodeKEY);
                  lvElement_slicedaten.setNamespace(lvMyNamespace);
                  
               // fgdaten
                  lvElement_fgdaten = lvTxsaktiv.fgdaten_aktiv(lvNode, lvNodeKEY, lvNbetragbd.toString(), lvAbetragbd.toString(), lvSetzetkurs);
                  lvElement_fgdaten.setNamespace(lvMyNamespace);
                  
                  //konddaten
                  lvElement_konddaten = lvTxsaktiv.konddaten_aktiv(lvNode,lvNodeKEY, ivMyBLZ);
                  lvElement_konddaten.setNamespace(lvMyNamespace);
                  
                  //kredkunde
                  lvElement_kredkunde = lvTxsaktiv.kredkunde_aktiv(lvNode,lvNodeKEY, ivMyBLZ, lvTxsaktpastext );
                  lvElement_kredkunde.setNamespace(lvMyNamespace);
                  
                  // fg (hier) aufbauen
                  lvElement_fg.setAttribute("key",lvNodeKEY.getChildText("Produkt"));
                  lvElement_fg.setAttribute("org", ivMyBLZ);
                  lvElement_fg.setAttribute("quelle",lvTxsaktpastext);
                  
                  List<Element> lvListSA01 = lvNode.getChildren("SATZSA01");
                  Element lvNodeSA01 = (Element)lvListSA01.get(0);
                  
                  if (!lvNodeSA01.getChildText("Zinstyp").equals("variabel"))
                  {
                    lvQuelleost.write((lvNodeKEY.getChildText("Produkt") + ";" + ivMyBLZ + ";"+ lvTxsaktpastext + ";").getBytes() );
                    lvQuelleost.write(System.getProperty("line.separator").getBytes());
                  }
                  else
                  {
                    LOGGER.info("Zinstyp variabel --> " + lvNodeKEY.getChildText("Produkt"));   
                  }
                  
                  // fg anhaengen
                  lvMyrootelement.addContent(lvElement_fg);
                  
                  // fgdaten anhaengen
                  lvElement_fg.addContent(lvElement_fgdaten);
                  
                  // slicedaten anhaengen
                  lvElement_fg.addContent(lvElement_slicedaten);
                  
                  //if ("ja".equals(lvZweiterslice))
                  if (lvZweiterslice)
                  {
                    lvElement_fg.addContent(lvElement_slicedaten2); 
                  }
                      
                  // konddaten anhaengen
                  lvElement_fg.addContent(lvElement_konddaten);
                  
                  // kredkunde anhaengen, ggf. separat ?
                  lvElement_fg.addContent(lvElement_kredkunde);
              }    
              }
            }
  
          }
        catch(IOException io)
        {
             LOGGER.info(io.getMessage());
        }
        catch(JDOMException jdomex)
        {
             LOGGER.info(jdomex.getMessage());
        }
    
      XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());
           
      try
      {
        lvOutputter.output(lvMydocument, lvTXSost);
      }
      catch (IOException e) 
      {
    	  e.printStackTrace();
      }
 
      // verarbeitete Datei ins logfile schreiben
      try
      {
        lvStreamlogaus = new FileOutputStream(lvLogdatei, true); 
        
        lvStreamlogaus.write("-----------------------------------------".getBytes());
        lvStreamlogaus.write(System.getProperty("line.separator").getBytes());
        
        DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM);
        Date lvJetzt = new Date();
        String dateOut = dateFormatter.format(lvJetzt);
             
        lvStreamlogaus.write(dateOut.getBytes());
        lvStreamlogaus.write(System.getProperty("line.separator").getBytes());
        
        lvStreamlogaus.write(lvNeuedatei.getBytes());
        lvStreamlogaus.write(System.getProperty("line.separator").getBytes());
        
        lvStreamlogaus.write("-----------------------------------------".getBytes());
        lvStreamlogaus.write(System.getProperty("line.separator").getBytes());
      }
      catch (Exception e)
      {
        LOGGER.error("Konnte logdatei " + ivEingabeverzeichnis + ivLogdateiname + " nicht oeffnen");
      }
      
      LOGGER.info("Verarbeitungsende");
    }
    
    /**
     * Einlesen der RueckmeldeDaten aus einer Datei
     * @param pvDateiname
     */
    private void leseRueckmeldeDaten(String pvDateiname)
    {
        String lvZeile = null;
        int lvZaehlerRueckmeldeDaten = 0;      
        
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        File ivFileRueckmeldeDaten = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileRueckmeldeDaten);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte RueckmeldeDaten-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
        
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen der RueckmeldeDaten
            {
        		  RueckmeldeDatenMAVIS lvRueckmeldeDaten = new RueckmeldeDatenMAVIS();

              lvRueckmeldeDaten.parseRueckmeldeDaten(lvZeile, LOGGER); // Parsen der Felder

        		  //if (lvRueckmeldeDaten.parseRueckmeldeDaten(lvZeile, LOGGER)) // Parsen der Felder
        		  //{
              lvZaehlerRueckmeldeDaten++;
              ivListeRueckmeldeDaten.put(lvRueckmeldeDaten.getProdukt(), lvRueckmeldeDaten);
             // }
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
            LOGGER.error("Konnte RueckmeldeDaten-Datei nicht schliessen!");
        }    
        
        LOGGER.info("Anzahl eingelesener RueckmeldeDaten: " + lvZaehlerRueckmeldeDaten);
    }
    
    /**
     * Schreiben der RueckmeldeDaten in eine Datei
     * @param pvDateiname
     */
    private void schreibeRueckmeldeDaten(String pvDateiname)
    {
        // Fuer die Ausgabe der Rueckmeldedaten
        File lvRueckFile = new File(pvDateiname);
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
        for (RueckmeldeDatenMAVIS lvRueckmeldeDaten:ivListeRueckmeldeDaten.values())
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
    }

 }

