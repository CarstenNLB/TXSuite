/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.DarlehenWP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import nlb.txs.schnittstelle.Filtern.ListeObjekte;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Wertpapier.PassivDarl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
@Deprecated
public class DarlehenWPVerarbeitung 
{
    private static Logger LOGGER = LogManager.getLogger("TXSDarlWPPFBGLogger");
 
    /**
     * 
     */
    private Document ivDocument;
    
    /**
     * Liste der Objekte, die einen Umsatz19 hatten
     */
    private ListeObjekte ivListeUmsatz19Objekte;

    /**
     * 
     */
    private boolean ivKorrekturBelieferung;
    
    /**
     * 
     */
    private boolean ivRestsaldoNull;
    
    /**
     * Buchungsdatum
     */
    //private long ivBuchungsdatum;
    
    /**
     * Institutsnummer
     */
    private String ivInstitutsnummer;
    
    /**
     * @param pvReader 
     * @param pvQuellsystem 
     */
    public DarlehenWPVerarbeitung(IniReader pvReader, String pvQuellsystem)
    {
        
        String lvFilein = "";
        String lvDirin = "";
        
        String lvFileout = "";
        String lvFileoutErgaenzung = "";
        
        String lvFileout_darlwpquell = "";
        String lvFilein_darlwpUmsatz19 = "";
        
        String lvInieintragRegister = "";
        
        // Entsprechenden Logger auswaehlen
        if (pvQuellsystem.equals("PDARLPFBG"))
        {
            LOGGER = Logger.getLogger("TXSDarlWPPFBGLogger"); 
        }
        if (pvQuellsystem.equals("PDARLFLUG"))
        {
            LOGGER = Logger.getLogger("TXSDarlWPFLUGLogger");
        }
        if (pvQuellsystem.equals("PDARLSCHF"))
        {
            LOGGER = Logger.getLogger("TXSDarlWPSCHFLogger");
        }
        if (pvQuellsystem.equals("PDARLOEPG"))
        {
            LOGGER = Logger.getLogger("TXSDarlWPOEPGLogger");
        }
        
        if (pvReader != null)
        {                
            LOGGER.info("erstes Argument Quellsystem=" + pvQuellsystem);
                
            if ("PDARLPFBG".equals(pvQuellsystem))
            {
                lvInieintragRegister="Darlehen"; 
                lvFileoutErgaenzung = "_";
            }
            else if ("PDARLOEPG".equals(pvQuellsystem)) 
            {
                lvInieintragRegister="OEPG";   
                lvFileoutErgaenzung = "_OEPG_";
            }
            else if ("PDARLFLUG".equals(pvQuellsystem))
            {
                lvInieintragRegister="Flugzeuge";
                lvFileoutErgaenzung = "_Flugzeuge_";
            }
            else if ("PDARLSCHF".equals(pvQuellsystem))   
            {
                lvInieintragRegister="Schiffe";
                lvFileoutErgaenzung = "_Schiffe_";
            }
            else
            {
                LOGGER.error("Fehler: unbekanntes Quellsystem, erlaubt sind PDARLPFBG,PDARLOEPG,PDARLFLUG,PDARLSCHF ");
                System.exit(0);
            }
                
            String lvInstitut = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
            if (lvInstitut.equals("Fehler"))
            {
                System.out.println("Keine Institutsnummer in der ini-Datei...");
                LOGGER.error("Keine Institutsnummer in der ini-Datei...");
                System.exit(0);
            }
            else
            {
                LOGGER.info("Institut=" + lvInstitut);                    
            }
                
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            // Eingangsdatei nach Register bestimmen
            ///////////////////////////////////////////////////////////////////////////////////////////////////
                
            lvDirin = pvReader.getPropertyString(lvInieintragRegister,"ExportVerzeichnis", "Fehler");
            if (lvDirin.equals("Fehler"))
            {
                LOGGER.error("Kein [DARLWP][DARLWPXMLEIN] in der ini-Datei...");
                System.exit(0);
            }
            else
            {
                LOGGER.info("Eingabeverzeichnis=" + lvDirin);                    
            }
                
            lvFilein = pvReader.getPropertyString(lvInieintragRegister, "DarlehenWP-Datei", "Fehler");
            if (lvFilein.equals("Fehler"))
            {
                LOGGER.error("Kein " + lvInieintragRegister + " DarlehenWP-Datei in der ini-Datei...");
                System.exit(0);
            }
            else
            {
                LOGGER.info("Eingabedatei=" + lvFilein);                    
            }
            ///////////////////////////////////////////////////////////////////////////////////////////////////
                
            lvFilein = lvDirin  + "\\" + lvFilein; 
                
                
            lvFileout = pvReader.getPropertyString("DARLWP", "DARLWPXMLAUS", "Fehler");
            if (lvFileout.equals("Fehler"))
            {
                LOGGER.error("Kein [DARLWP][DARLWPXMLAUS] in der ini-Datei...");
                System.exit(0);
            }
            else
            {
                // Schiffe, OEPG_Flugzeuge einh�ngen
                lvFileout = lvFileout.replace("DarlWP_", "DarlWP" + lvFileoutErgaenzung);
                    
                LOGGER.info("DARLWPXMLAUS=" + lvFileout);   
            }
                
            // Ausgabe Quellsystem
            lvFileout_darlwpquell = pvReader.getPropertyString("DARLWP", "DARLWPQUELL", "Fehler");
            if (lvFileout_darlwpquell.equals("Fehler"))
            {
                LOGGER.error("Kein [DARLWP][DARLWPQUELL] in der ini-Datei...");
                System.exit(0);
            }
            else
            {
                LOGGER.info("DARLWPQUELL=" + lvFileout_darlwpquell);                    
            }

            // Ausgabe Umsatz12
            //lvFileout_darlwpUmsatz12 = pvReader.getPropertyString("DARLWP", "DARLWPUMSATZ12", "Fehler");
            //if (lvFileout_darlwpUmsatz12.equals("Fehler"))
            //{
            //    LOGGER.error("Kein [DARLWP][DARLWPUMSATZ12] in der ini-Datei...");
            //    System.exit(0);
            //}
            //else
            //{
            //    LOGGER.info("DARLWPUMSATZ12=" + lvFileout_darlwpUmsatz12);                    
            //}

            // Ausgabe Umsatz19
            lvFilein_darlwpUmsatz19 = pvReader.getPropertyString("DARLWP", "DARLWPUMSATZ19", "Fehler");
            if (lvFilein_darlwpUmsatz19.equals("Fehler"))
            {
                LOGGER.error("Kein [DARLWP][DARLWPUMSATZ19] in der ini-Datei...");
                System.exit(0);
            }
            else
            {
                LOGGER.info("DARLWPUMSATZ19=" + lvFilein_darlwpUmsatz19);                    
            }
        }
        
        LOGGER.info(".ini ok");

        startVerarbeitung(lvFileout, lvFileout_darlwpquell, lvFilein, pvQuellsystem, lvFilein_darlwpUmsatz19);
    }
        
    /**
     * @param pvFileout 
     * @param pvFileout_darlwpquell 
     * @param pvFilein
     * @param pvQuellsystem
     * @param pvFileUmsatz19
     */
    private void startVerarbeitung(String pvFileout, String pvFileout_darlwpquell, String pvFilein, String pvQuellsystem, String pvFileUmsatz19)
    {              
        // CT - 08.07.2014    
        int lvZaehlerUmsatz19 = 0;
        File lvFilterUmsatz19 = new File(pvFileUmsatz19);
        ivListeUmsatz19Objekte = new ListeObjekte();
        if (lvFilterUmsatz19 != null && lvFilterUmsatz19.exists())
        {
          FileInputStream lvFilterUmsatz19Is = null;
          BufferedReader lvfilterUmsatz19In = null;
          try
          {
            lvFilterUmsatz19Is = new FileInputStream(lvFilterUmsatz19);
            lvfilterUmsatz19In = new BufferedReader(new InputStreamReader(lvFilterUmsatz19Is));
          }
          catch (Exception e)
          {
            LOGGER.info("Konnte Filterdatei " + pvFileUmsatz19 + " nicht oeffnen!");
          }
          String lvFilterUmsatz19Zeile = new String();
          try
          {
            LOGGER.info("Folgende Objekte werden gefiltert:");  
            while ((lvFilterUmsatz19Zeile = lvfilterUmsatz19In.readLine()) != null)  // Lesen Umsatz19-Datei
            {
              if (lvFilterUmsatz19Zeile != null)
              {
                ivListeUmsatz19Objekte.addObjekt(lvFilterUmsatz19Zeile);
                LOGGER.info("Objekt: " + lvFilterUmsatz19Zeile);
                lvZaehlerUmsatz19++;
              }
            }
          }
          catch (Exception e)
          {
              LOGGER.error("Fehler beim Verarbeiten einer Zeile:" + lvFilterUmsatz19Zeile);
              e.printStackTrace();
          }
          try
          {
            lvfilterUmsatz19In.close();
            lvFilterUmsatz19Is.close();
          }
          catch (Exception e)
          {
              System.out.println("Konnte Datei nicht schliessen!");
          }
        }
        else
        {
            LOGGER.info("Keine Filterdatei 'Umsatz19Filtern.txt' gefunden...");
        }
        LOGGER.info("Anzahl Objektnummern - Umsatz19-Filterndatei: " + lvZaehlerUmsatz19);
        // CT - Workaround               
        
        // Zur Eingabe
        //SAXBuilder lvBuilder = null;
        //lvBuilder = new SAXBuilder();
        //lvBuilder.setExpandEntities(true);
        leseDarlehenWP(pvFilein);        
        
        schreibeDarlehenWP(pvFileout, pvFileout_darlwpquell, pvQuellsystem, pvFileUmsatz19);
        
        // CT 08.07.2014
        // Liste der Objekte mit Umsatz19 rausschreiben 
        /* lvFilterUmsatz19 = new File(pvFileUmsatz19);
        FileOutputStream lvFilterUmsatz19Os = null;

        try
        {
          lvFilterUmsatz19Os = new FileOutputStream(lvFilterUmsatz19);
        }
        catch (Exception e)
        {
          System.out.println("Konnte " + pvFileUmsatz19 + " nicht oeffnen!");
          System.out.println("File: " + lvFilterUmsatz19.getAbsolutePath());
        }    

        String lvObjekt;
        Collection<String> lvObjekte = ivListeUmsatz19Objekte.keySet();
        Iterator<String> lvIterObjekte = lvObjekte.iterator();
        while (lvIterObjekte.hasNext())
        {
            lvObjekt = lvIterObjekte.next();
            try
            {
               //System.out.println("lvObjekt: " + lvObjekt);
               lvFilterUmsatz19Os.write((lvObjekt + StringKonverter.lineSeparator).getBytes());
               //lvFilterAbgangOs.write((lvObjekt.substring(0,11) + StringKonverter.lineSeparator).getBytes());
            }
            catch (Exception e)
            {
              System.out.println("Start: Fehler bei Ausgabe in die Datei");
            }
        }
        try
        {
          lvFilterUmsatz19Os.close();
        }
        catch (Exception e)
        {
            System.out.println("Konnte " + pvFileUmsatz19 + " nicht schliessen!");
        }   
        // CT 08.07.2014
     */
    }
    
    /**
     * @param pvFilename
     * @param pvFilenameQuellsysteme
     * @param pvQuellsystem
     * @param pvUmsatz19
     */
    private void schreibeDarlehenWP(String pvFilename, String pvFilenameQuellsysteme, String pvQuellsystem, String pvFileUmsatz19)
    {
        File lvTXSFile = new File(pvFilename);
        
        FileOutputStream lvTSXost = null;
        
        try
        {
          lvTSXost = new FileOutputStream(lvTXSFile);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte TXS-Ausgabedatei " + pvFilename + " nicht oeffnen!");
            return;
        }    
        
        // Fuer die Ausgabe der Quellsystemdatei 
        File lvQuelle = new File(pvFilenameQuellsysteme);
        FileOutputStream lvQuelleost = null;
        
        try
        {
            lvQuelleost = new FileOutputStream(lvQuelle, true);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Cashflow_Quellsystemdatei " + lvQuelle.getName() +" nicht oeffnen!");
            System.exit(0);
        }

        // Zur Ausgabe
        Element lvMyrootelement = new Element("importdaten");
        Namespace lvMyNamespace = Namespace.getNamespace("txsi", "http://agens.com/txsimport.xsd");
        lvMyrootelement.setNamespace(lvMyNamespace);
        Document lvMydocument = new Document(lvMyrootelement);
        
        BigDecimal lvNbetrag = new BigDecimal(0.00);
        
        //try{
            //Document lvDocument = (Document) lvBuilder.build(pvFilein);
        Element lvRootNode = ivDocument.getRootElement();
       
        // header anhaengen
        Element lvMyelement2 = new Element("header");
        lvMyelement2.setAttribute("valdate", lvRootNode.getAttributeValue("valdate"));
        //ivBuchungsdatum = StringKonverter.convertString2Long(lvRootNode.getAttributeValue("valdate").replace("-", ""));
        ivInstitutsnummer = lvRootNode.getAttributeValue("nr");
        lvMyrootelement.addContent(lvMyelement2);
           
            
            // Institutsnummer
            // System.out.println(rootNode.getAttributeValue("nr"));
            
        String lvTxsprojekt = "nicht definiert";
        String lvBLZ = "nicht definiert";
        String lvKey = "nicht definiert";
            
        // landesbankspezifische Werte setzen
        if (lvRootNode.getAttributeValue("nr").equals("009"))
        {
            lvTxsprojekt = "NLB-PfandBG";
            lvBLZ = "25050000";
        }
        else if (lvRootNode.getAttributeValue("nr").equals("004"))
        {
            lvTxsprojekt = "BLB-PfandBG";
            lvBLZ = "29050000";
        }
            
        PassivDarl lvTxspassiv = new PassivDarl(lvTxsprojekt);
            
        List<Element> lvList = lvRootNode.getChildren("DarlehenWP");
           
        // Schleife �ber das document
        for (int i=0; i< lvList.size(); i++)
        {
            Element lvNode = (Element) lvList.get(i);

            LOGGER.info("DARLWP;" + lvNode.getChildText("ISIN") + ";" + lvNode.getChildText("Kontonummer") + ";" + lvNode.getChildText("Kontozustand") + ";" + lvNode.getChildText("Restkapital") + ";" + lvNode.getChildText("ZinsanpassungDatum") + ";" + lvNode.getChildText("KennzeichenRollover") + ";" + lvNode.getChildText("VertragBis" ) + ";" + lvNode.getChildText("NaechsteZinsfaelligkeit"));
            if (pruefeAusgabe(lvNode))
            {
              //System.out.println(node.getAttributeValue("nr"));
              //System.out.println(node.getChildText("ISIN"));
              
              //int lvLiefern = 1; 
              
              /*
              // spezielle Aussteuerung f�r Bremen KFW (noch zu KOMMENTIEREN!!!!)
              if (  ((lvTxsprojekt.startsWith("BLB")))        && 
                   (   
                 //   ("0000001022".equals(lvNode.getChildText("Kundennummer")) ||   
                    ("0000001041".equals(lvNode.getChildText("Kundennummer"))   )
                   )                                                            )
              {
                  lvLiefern = 0;
              }
              */
                   
              //if (1 == lvLiefern)    
              //{
                  
              // TXSFinanzgeschaeft
              Element lvElement_fg = new Element("fg");
              lvElement_fg.setNamespace(lvMyNamespace);
           
              // Sonderbehandlung Bremen, dort ist in Darlehen die ISIN nicht gef�llt
              // bei �nderungen hier Zahlungsstrom.frisco.java mit �ndern
              
              lvKey = lvNode.getChildText("ISIN").trim();
              
              // NUR BREMEN
              // CT - 08.08.2013 Korrektur der fehlerhaften Anlieferung der ISIN
              if (lvTxsprojekt.startsWith("BLB"))
              {
                if  ("05201".equals(lvNode.getChildText("ProduktSchluessel")))
                {
                  lvKey = lvNode.getChildText("Kontonummer");    
                }        
                else
                {  
                lvKey = ""; // Workaround - Immer leer setzen
                }
              }
              //OBIGES NUR BREMEN
              
              if ("".equals(lvKey))
              {
                  lvKey = "DE"+lvNode.getChildText("Kontonummer");
              }
           
              // fg (hier) aufbauen
              lvElement_fg.setAttribute("key", lvKey);
              lvElement_fg.setAttribute("org", lvBLZ);
              lvElement_fg.setAttribute("quelle", pvQuellsystem);
              
              // SU 20140424
              // ACHTUNG ! Sonderl�sung zum Aussteuern von passiven rollover-Gesch�ften 
              // ACHTUNG ! Dieses if taucht zweimal auf -Suche nach "SU 20140424"
              // Diese werden nicht in die Quellsystemdatei geschrieben, damit die Zahlungsstromverarbeitung
              // die entsprechenden Konten �berliest
              // �ber die Sch�nheit der if-Abfrage kann man streiten :/
              // zum Ausbau das klammernde if entfernen
              // Stand 20140424: NLB: 20 Konten
              //                 BLB:  6 Konten
              
              String lvAktuelleKontonummer =  lvNode.getChildText("Kontonummer");
              String lvISIN = lvNode.getChildText("ISIN");
              
              // NLB - Rollover Sonderloesung - 26.01.2015
              if (ivInstitutsnummer.equals("009"))
              {
                  if (!lvNode.getChildText("ZinsanpassungDatum").equals("00000000"))
                  {
                    long lvZinsanpassung = StringKonverter.convertString2Long(lvNode.getChildText("ZinsanpassungDatum"));
                    long lvTilgungsbeginn = StringKonverter.convertString2Long(lvNode.getChildText("Tilgungsbeginn"));
                    if (lvTilgungsbeginn - lvZinsanpassung > 1)                       
                      //lvNode.getChildText("KennzeichenRollover").equals("F") || lvNode.getChildText("KennzeichenRollover").equals("V") ||
                      //lvISIN.equals("XFNB00NJM384") || lvISIN.equals("XFNB00NJB270") || lvISIN.equals("XFNB00NJB478") ||
                      //lvISIN.equals("XFNB00N48694") || lvISIN.equals("XFNB00NJF172") || lvISIN.equals("XFNB00N48793") || lvISIN.equals("XFNB00NJB288"))
                    {  
                      LOGGER.info("Konto " +  lvAktuelleKontonummer + " wird nicht in die Quellsystemdatei geschrieben");                             
                      LOGGER.info("Zinsanpassungsdatum: " + lvNode.getChildText("ZinsanpassungDatum") + " Tilgungsbeginn: " + lvNode.getChildText("Tilgungsbeginn"));
                    }
                    else
                    {
                      // Hier CashflowQuellsystem_DarlWP schreiben
                      try
                      {
                        System.out.println("Cashflowdatei geschrieben: " + lvNode.getChildText("Kontonummer"));
                        lvQuelleost.write((lvNode.getChildText("Kontonummer") + ";" + lvBLZ + ";"+ pvQuellsystem + ";" + lvNode.getChildText("Kontozustand") + ";").getBytes() );
                        lvQuelleost.write(System.getProperty("line.separator").getBytes());
                      }
                      catch (IOException exp)
                      {
                         LOGGER.error("Konnte " + lvNode.getChildText("Kontonummer") + " nicht in die Quellsystemdatei schreiben.");
                      }
                    }              
                  }
                  else // hier die normale Verarbeitung    
                  { 
                    // Hier CashflowQuellsystem_DarlWP schreiben
                      try
                      {
                        System.out.println("Cashflowdatei geschrieben: " + lvNode.getChildText("Kontonummer"));
                        lvQuelleost.write((lvNode.getChildText("Kontonummer") + ";" + lvBLZ + ";"+ pvQuellsystem + ";" + lvNode.getChildText("Kontozustand") + ";").getBytes() );
                        lvQuelleost.write(System.getProperty("line.separator").getBytes());
                      }
                      catch (IOException exp)
                      {
                        LOGGER.error("Konnte " + lvNode.getChildText("Kontonummer") + " nicht in die Quellsystemdatei schreiben.");
                      }
                  }
              }

              // BLB - Rollover Sonderloesung - 26.01.2015
              if (ivInstitutsnummer.equals("004"))
              {
                if (((lvAktuelleKontonummer.equals("6801110083"))) || ((lvAktuelleKontonummer.equals("6801240086"))) ||
                    ((lvAktuelleKontonummer.equals("6801240122"))) || ((lvAktuelleKontonummer.equals("6802880095"))) ||
                    ((lvAktuelleKontonummer.equals("6917070682"))) || ((lvAktuelleKontonummer.equals("6917070698"))))
                {
                  LOGGER.info("Konto " +  lvAktuelleKontonummer + " wird wegen rollover Problem nicht in die Quellsystemdatei geschrieben"); 
                }
                else // hier die normale Verarbeitung    
                { 
                  // Hier CashflowQuellsystem_DarlWP schreiben
                  try
                  {
                    lvQuelleost.write((lvNode.getChildText("Kontonummer") + ";" + lvBLZ + ";"+ pvQuellsystem + ";" + lvNode.getChildText("Kontozustand") + ";").getBytes() );
                    lvQuelleost.write(System.getProperty("line.separator").getBytes());
                  }
                  catch (IOException exp)
                  {
                    LOGGER.error("Konnte " + lvNode.getChildText("Kontonummer") + " nicht in die Quellsystemdatei schreiben.");
                  }
                }
              }
              //LOGGER.info(lvNode.getChildText("Kontonummer"));
              
              // fg anh�ngen
              lvMyrootelement.addContent(lvElement_fg);
              
              // wpposdaten aufbauen
              Element lvElement_wpposdaten = lvTxspassiv.wpposdaten_passivdarl(lvNode, ivKorrekturBelieferung, ivRestsaldoNull);
              lvElement_wpposdaten.setNamespace(lvMyNamespace);
              ////CT 29.07.2016 -> auch NLB testet Tilgungsbginn als abweichende Faelligkeit
              // BLB moechte als abweichende Faelligkeit immer den Tilgungsbeginn - CT 19.07.2016
              ////if (ivInstitutsnummer.equals("004"))
              ////{
                  if (!("F".equals(lvNode.getChildText("KennzeichenRollover")) || "V".equals(lvNode.getChildText("KennzeichenRollover"))))  
                  {
            	    lvElement_wpposdaten.removeAttribute("abwfaell");
            	    lvElement_wpposdaten.setAttribute("abwfaell", DatumUtilities.FormatDatum(lvNode.getChildText("Tilgungsbeginn"))); 
                  }
              ////}
              
              // wpposdaten anhaengen
              lvElement_fg.addContent(lvElement_wpposdaten);
              
              // kredkunde aufbauen
              Element lvElement_kredkunde = lvTxspassiv.kredkunde_passivdarl(lvNode,"KUNDE");
              lvElement_kredkunde.setNamespace(lvMyNamespace);
              lvElement_kredkunde.setAttribute("org",lvBLZ);
                                          
              // kredkunde anhaengen
              lvElement_fg.addContent(lvElement_kredkunde);
              
              lvNbetrag = BigDecimal.valueOf(Double.parseDouble(lvNode.getChildText("UrsprungsKapital")));
              
              // eine ISIN kann an mehreren Konten (mehrere Kunden) hinterlegt (verkauft) sein
              for (int j=i+1; j< lvList.size(); j++)
              {
                Element lvNode2 = (Element) lvList.get(j);
    
                if (lvNode2.getChildText("ISIN").equals(lvKey))
                {
                  if (pruefeAusgabe(lvNode2))
                  {
                    LOGGER.info("ISIN mehrfach " + lvKey + " " + lvNode.getChildText("Kontonummer") + " " + lvNode2.getChildText("Kontonummer"));                    
          
                    // SU 20140424
                    // ACHTUNG ! Sonderl�sung zum Aussteuern von passiven rollover-Gesch�ften 
                    // ACHTUNG ! Dieses if taucht zweimal auf -Suche nach "SU 20140424"
                    // Diese werden nicht in die Quellsystemdatei geschrieben, damit die Zahlungsstromverarbeitung
                    // die entsprechenden Konten �berliest
                    // �ber die Sch�nheit der if-Abfrage kann man streiten :/
                    // zum Ausbau das klammernde if entfernen
                    // Stand 20140424: NLB: 20 Konten
                    //                 BLB:  6 Konten
                    
                    String lvAktuelleKontonummer2;  
                    lvAktuelleKontonummer2 =  lvNode2.getChildText("Kontonummer");
                    // NLB - Rollover Sonderloesung - 26.01.2015
                    if (ivInstitutsnummer.equals("009"))
                    {
                        if (lvNode2.getChildText("KennzeichenRollover").equals("F") || lvNode2.getChildText("KennzeichenRollover").equals("V") ||
                            lvISIN.equals("XFNB00NJM384") || lvISIN.equals("XFNB00NJB270") || lvISIN.equals("XFNB00NJB478") ||
                            lvISIN.equals("XFNB00N48694") || lvISIN.equals("XFNB00NJF172") || lvISIN.equals("XFNB00N48793") || lvISIN.equals("XFNB00NJB288"))
                        {
                            LOGGER.info("Konto " +  lvAktuelleKontonummer2 + " wird wegen rollover Problem nicht in die Quellsystemdatei geschrieben");                             
                        }
                        else // hier die normale Verarbeitung    
                        { 
                          // Hier CashflowQuellsystem_DarlWP schreiben
                          try
                          {
                            lvQuelleost.write((lvNode2.getChildText("Kontonummer") + ";" + lvBLZ + ";"+ pvQuellsystem + ";" + lvNode2.getChildText("Kontozustand") + ";").getBytes() );
                            lvQuelleost.write(System.getProperty("line.separator").getBytes());
                          }
                          catch (IOException exp)
                          {
                              LOGGER.error("Konnte " + lvNode2.getChildText("Kontonummer") + " nicht in die Quellsystemdatei schreiben.");
                          }
                        }                           
                    }
                    // BLB - Rollover Sonderloesung - 26.01.2015
                    if (ivInstitutsnummer.equals("004"))
                    {
                      if (((lvAktuelleKontonummer2.equals("6801110083"))) || ((lvAktuelleKontonummer2.equals("6801240086"))) ||
                          ((lvAktuelleKontonummer2.equals("6801240122"))) || ((lvAktuelleKontonummer2.equals("6802880095"))) ||
                          ((lvAktuelleKontonummer2.equals("6917070682"))) || ((lvAktuelleKontonummer2.equals("6917070698"))))
                      {
                        LOGGER.info("Konto " +  lvAktuelleKontonummer2 + " wird wegen rollover Problem nicht in die Quellsystemdatei geschrieben"); 
                      }
                      else // hier die normale Verarbeitung    
                      { 
                        // Hier CashflowQuellsystem_DarlWP schreiben
                        try
                        {
                          lvQuelleost.write((lvNode2.getChildText("Kontonummer") + ";" + lvBLZ + ";"+ pvQuellsystem + ";" + lvNode2.getChildText("Kontozustand") + ";").getBytes() );
                          lvQuelleost.write(System.getProperty("line.separator").getBytes());
                        }
                        catch (IOException exp)
                        {
                          LOGGER.error("Konnte " + lvNode2.getChildText("Kontonummer") + " nicht in die Quellsystemdatei schreiben.");
                        }
                      }
                    }
                    
                    lvNbetrag = lvNbetrag.add(BigDecimal.valueOf(Double.parseDouble(lvNode2.getChildText("UrsprungsKapital"))));
              
                 // wpposdaten aufbauen
                    Element lvElement_wpposdaten2 = lvTxspassiv.wpposdaten_passivdarl(lvNode2, ivKorrekturBelieferung, ivRestsaldoNull);
                    lvElement_wpposdaten2.setNamespace(lvMyNamespace);
                    
                    // wpposdaten anhaengen
                    lvElement_fg.addContent(lvElement_wpposdaten2);
                    
                 // kredkunde aufbauen
                    Element lvElement_kredkunde2 = lvTxspassiv.kredkunde_passivdarl(lvNode2, "KUNDE" );
                    lvElement_kredkunde2.setNamespace(lvMyNamespace);
                    lvElement_kredkunde2.setAttribute("org",lvBLZ);
                                                
                    // kredkunde anhaengen
                    lvElement_fg.addContent(lvElement_kredkunde2);
                  }
                  // aus der Liste
                  lvList.remove(j);
                  // z�hler nachruecken
                  j--;
                }
              }
              
              // System.out.println("---- gesamter nbetrag an " + lvKey +  "=" + lvNbetrag ); 
              
              // fgdaten aufbauen, Nennbetrag ist Summe der Einzelgeschaefte (Konten)
              Element lvElement_fgdaten = lvTxspassiv.fgdaten_passivdarl(lvNode, lvNbetrag.toString(), pvQuellsystem);
              lvElement_fgdaten.setNamespace(lvMyNamespace);
              
              // fgdaten anhaengen
              lvElement_fg.addContent(lvElement_fgdaten);
              
              // konddaten
              Element lvElement_konddaten = lvTxspassiv.konddaten_passivdarl(lvNode, ivKorrekturBelieferung);
              // Notloesung BLB 13.07.2015
              // Aktualisiert CT 23.12.2016
              if (ivInstitutsnummer.equals("004"))
              {
                  if (lvAktuelleKontonummer.equals("6910840275") || lvAktuelleKontonummer.equals("6913070284") ||
                      lvAktuelleKontonummer.equals("6917530308"))
                  {
                      lvElement_konddaten.removeAttribute("zinsdat");
                      lvElement_konddaten.setAttribute("zinsdat", "2016-01-04"); 
                  }
                  if (lvAktuelleKontonummer.equals("6801110099") || lvAktuelleKontonummer.equals("6805160028") ||
                	  lvAktuelleKontonummer.equals("6805250019") || lvAktuelleKontonummer.equals("6918770142"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-01-09"); 
                  }
                  if (lvAktuelleKontonummer.equals("6917530317"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-01-10"); 
                  }
                  if (lvAktuelleKontonummer.equals("6805230110"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-01-12"); 
                  }
                  if (lvAktuelleKontonummer.equals("6918770158"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-01-25"); 
                  }
                  if (lvAktuelleKontonummer.equals("6916200116"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-02-06"); 
                  }
                  if (lvAktuelleKontonummer.equals("6805230126"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-03-13"); 
                  }
                  if (lvAktuelleKontonummer.equals("6801170065"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-03-18"); 
                  }
                  if (lvAktuelleKontonummer.equals("6915740084"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-04-10"); 
                  }
                  if (lvAktuelleKontonummer.equals("6802140407"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-06-20"); 
                  }
                  if (lvAktuelleKontonummer.equals("6913070307"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-07-03"); 
                  }
                  if (lvAktuelleKontonummer.equals("6802140416"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-07-10"); 
                  }
                  if (lvAktuelleKontonummer.equals("6916150231"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-08-08"); 
                  }
                  if (lvAktuelleKontonummer.equals("6927120140"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2016-11-21"); 
                  }
                  // Dezember - alte Faelle
                  if (lvAktuelleKontonummer.equals("6910840269") || lvAktuelleKontonummer.equals("6916200107"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2015-12-06"); 
                  }
                  if (lvAktuelleKontonummer.equals("6912030114"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2015-12-23"); 
                  }
                  if (lvAktuelleKontonummer.equals("6808350018"))
                  {
                	  lvElement_konddaten.removeAttribute("zinsdat");
                	  lvElement_konddaten.setAttribute("zinsdat", "2015-12-27"); 
                  }
              }
              
              lvElement_konddaten.setNamespace(lvMyNamespace);
                  
              //konddaten anhaengen
              lvElement_fg.addContent(lvElement_konddaten);
              }
             } // for
            
           //}catch(IOException io){
           //    LOGGER.error(io.getMessage());
           //}catch(JDOMException jdomex){
           //    LOGGER.error(jdomex.getMessage());
           //}
           
           XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());
           
           try{
               //lvOutputter.output(lvMydocument, System.out);
               lvOutputter.output(lvMydocument,lvTSXost);
           }
           catch (IOException e) {e.printStackTrace();}

           try
           {
             lvQuelleost.close();
           }
           catch (Exception exp)
           {
             System.out.println("Konnte " + lvQuelleost.toString() + " nicht schliessen");   
           }
           
           LOGGER.info("ende");
    }
    
    /**
     * Es wird geprueft, ob das DarlehenWP in die TXS-Ausgabedatei geschrieben werden soll
     * @param pvNode 
     * @return
     */
    private boolean pruefeAusgabe(Element pvNode)
    {
        ivKorrekturBelieferung = false;
        ivRestsaldoNull = false;
                
        // Keine Anlieferung, wenn in der Umsatz19-Liste enthalten
        ////if (ivListeUmsatz19Objekte.containsKey(pvNode.getChildText("ISIN") + "_" + pvNode.getChildText("Kontonummer")))
        ////{
        ////  return false;
        ////}
        /*
        // Tilgungsschluessel entweder aus dem KTR- oder KON-Segment nehmen 
        String lvTilgungsschluessel = pvNode.getChildText("TilgungsschluesselKTR");
        if (pvNode.getChildText("TilgungsschluesselKTR").equals(" "))
        {
            lvTilgungsschluessel = pvNode.getChildText("TilgungsschluesselKON");
        }
        
        // Kontozustand muss '8' sein
        if (pvNode.getChildText("Kontozustand").equals("8"))
        {
            // Tilgungsschluessel muss '3' sein
            if (lvTilgungsschluessel.equals("3"))
            {
                LOGGER.info("Tilgungsschluessel: " + lvTilgungsschluessel + " - " + pvNode.getChildText("ISIN") + "_" + pvNode.getChildText("Kontonummer"));
                
                long lvTilgungstermin = StringKonverter.convertString2Long(pvNode.getChildText("Tilgungstermin"));
                // Tilgungstermin < Buchungsdatum
                if (lvTilgungstermin > ivBuchungsdatum)
                {
                   // Restsaldo nicht beliefern und Faelligkeit = Tilgungstermin 
                   ivKorrekturBelieferung = true;  
                }
                else
                {
                    // Restsaldo = 0 -> Korrektur der Belieferung
                    ivRestsaldoNull = true;              
                }
            }
        }
        */
        return true;
    }

    /**
     * Liest die DarlehenWP-Daten ein
     * @param pvFilename Dateiname der DarlehenWP-Datei
     */
    private void leseDarlehenWP(String pvFilename)
    {
        // Zur Eingabe
        SAXBuilder lvBuilder = new SAXBuilder();
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


}
