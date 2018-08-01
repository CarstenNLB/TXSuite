/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Zahlungsstrom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
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
 * @author Stefan Unnasch
 *
 * Dieses Programm verarbeitet XML-Zahlungströme aus der Anwendung FRSICO (via hostprogramm CEDRCF01),
 * fasst diese ggf. zusammen und gibt eine 
 * XML-Datei im XML-Format für die Anwendung TXS aus 
 *
 * Als Werkzeug zur XML-Verarbeitung wird jdom benutzt
 *
 */
public class Frisco {

    private static Logger LOGGER = LogManager.getLogger("TXSFriscoLogger");
    
    /**
     * @param args .ini Datei, z.B. C:\Temp\txs_batch.ini
     * 
     * hierin muss enthalten sein
     * 
     *  [Konfiguration]
     *  Institut=BLB
     * 
     *  [FRISCO]
     *  FRISCOAUS=C:/temp/Frisco_TXS.xml
     *  FRISCODIR=C:/temp/
     *  FRISCOLOG=friscolog.txt
     *  FRISCOMSK=A1PBAT.NT.I00X.LMTXS.CFXML
     */
    public static void main(String[] args) 
    {    
        String lvFileout ="";
        String lvDaypointerFileout ="";
        
      
        String lvEingabeverzeichnis   ="";
        String lvLogdateiname         ="";
        String lvMaske                ="";
        
        String lvLog4jprop ="";
        
        FileInputStream     lvStreamlogein    = null;
        FileOutputStream    lvStreamlogaus    = null;
        
        String lvMyDatum = "0000-00-00";
        
        String lvInstitut = "";
        
        Date lvstartdate = new Date();
        // display time and date using toString()
        System.out.println("Start : " + lvstartdate);
        
        if (!args[args.length - 1].endsWith(".ini"))
        {
            System.out.println("Fehler, Aufruf mit ini-Datei");
            System.out.println("Zahlungstrom <ini-Datei>");
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
            if (lvReader != null)
            {
                
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
                   // PropertyConfigurator.configure(log4jprop);
                    
                    DOMConfigurator.configure(lvLog4jprop); 
                }
                
                LOGGER.info("log4j konfiguriert");
               
                lvInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
                
                if (lvInstitut.equals("Fehler"))
                {
                    System.out.println("Keine Institutsnummer in der ini-Datei...");
                    LOGGER.error("Keine Institutsnummer in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("Institut=" + lvInstitut);                    
                }
                
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
                
                lvLogdateiname = lvReader.getPropertyString("FRISCO", "FRISCOLOG", "Fehler");
                if (lvLogdateiname.equals("Fehler"))
                {
                    LOGGER.error("Kein [FRISCO][FRISCOLOG] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("FRISCOLOG=" + lvLogdateiname);                    
                }
                
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
                
                lvDaypointerFileout = lvReader.getPropertyString("FRISCO", "FRISCODPFILE", "Fehler");
                if (lvDaypointerFileout.equals("Fehler"))
                {
                    LOGGER.error("Kein [FRISCO][FRISCODPFILE] in der ini-Datei...");
                    System.exit(1);
                }
                else
                {
                    LOGGER.info("FRISCODPFILE=" + lvDaypointerFileout);                    
                }
                
            }
        }
        
        File lvTXSFile = new File(lvFileout);
        FileOutputStream lvTXSost = null;
        
        FileOutputStream lvDaypointerost = null;
        
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
          //    System.out.println("------hit");
                    
              String lvNeuD = lvListing[n].substring(lvListing[n].lastIndexOf(".D")+2, lvListing[n].lastIndexOf(".D")+8);
              String lvAltD = lvNeuedatei.substring(lvNeuedatei.lastIndexOf(".D")+2,lvNeuedatei.lastIndexOf(".D")+8); 
                    
          //    System.out.println("xxx " + neuD + " " + altD);
                    
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
            //  System.out.println("Dateiname kürzer als A1PBAT.NT.I009.LMTXS.KSXML");   
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
          LOGGER.error("Konnte logdatei " + lvEingabeverzeichnis + lvLogdateiname + " nicht öffnen");
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
            LOGGER.error("bereits in log");
            System.exit(0);
        }
        
        try{lvLogBR.close();}catch(Exception e){};
        try{lvLogISR.close();}catch(Exception e){};
        try{lvStreamlogein.close();}catch(Exception e){};
        
        // daypointer zur externen Weiterverarbeitung in Datei schreiben
        File lvDaypointerFile = new File(lvDaypointerFileout);
        
        try
        {
            lvDaypointerost = new FileOutputStream(lvDaypointerFile);
        }
        catch (Exception e)
        {
            LOGGER.error("Konnte Daypointerdatei " + lvDaypointerFileout +" nicht schreiben!");
        }    
        
        try
        {
           lvDaypointerost.write(lvMyDatum.getBytes());
        }
        catch (Exception e)
        {
          LOGGER.error("Konnte Daypointerdatei " + lvDaypointerFileout +" nicht schreiben!");
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
        
        Element lvMyelement2 = new Element("header");
        lvMyelement2.setAttribute("valdate",lvMyDatum);
        lvMyrootelement.addContent(lvMyelement2);
        
        LOGGER.info("start ");
        
        int lvMb = 1024*1024;

        //Getting the runtime reference from system
        Runtime lvRuntime = Runtime.getRuntime();

        System.out.println("##### Heap utilization statistics [MB] #####");

        //Print used memory
        System.out.println("Used Memory:"
            + (lvRuntime.totalMemory() - lvRuntime.freeMemory()) / lvMb);

        //Print free memory
        System.out.println("Free Memory:"
            + lvRuntime.freeMemory() / lvMb);

        //Print total available memory
        System.out.println("Total Memory:" + lvRuntime.totalMemory() / lvMb);

        //Print Maximum available memory
        System.out.println("Max Memory:" + lvRuntime.maxMemory() / lvMb);
        
        try
        {
          Document lvDocument = (Document) lvBuilder.build(lvEingabeverzeichnis+lvNeuedatei);
          
          System.out.println("Used Memory nach lesen:"
                  + (lvRuntime.totalMemory() - lvRuntime.freeMemory()) / lvMb);
          
          Element lvRootNode = lvDocument.getRootElement();
          
          List<Element> lvVorlauf = lvRootNode.getChildren("vorlauf");
          Element lvVorlaufelem = lvVorlauf.get(0);
          
          String lvBLZ;
          String lvKey = "0000000000";
          
          System.out.println("Verarbeite Institut " + lvVorlaufelem.getChildText("inst"));
          
          if ("009".equals(lvVorlaufelem.getChildText("inst")))
          {
            lvBLZ="25050000";               
          }
          else if ("004".equals(lvVorlaufelem.getChildText("inst")))
          {
            lvBLZ="29050000";               
          } 
          else
          {
            lvBLZ="29050000";               
          }
              
          List<Element> lvKontolist = new ArrayList<Element>();
          lvKontolist = lvRootNode.getChildren("konto");
          
          List<Element> lvKontolistP = new ArrayList<Element>();
          
          LOGGER.info("Anzahl der Konten = " + lvKontolist.size() );
          
          List<Element> lvStammlist = new ArrayList<Element>();
          List<Element> lvStammlist2 = new ArrayList<Element>();
          List<Element> lvStammlist3 = new ArrayList<Element>();
          
          
          List<Element> lvAblauflist = new ArrayList<Element>();
          
          
          Element lvStammelem;
          Element lvStammelem2;
          Element lvStammelem3;
          
          
          Element lvAblaufelem; 
          Element lvAblaufelem2;
   //       Element ablaufelem3;
          
          Element lvNode;
          Element lvNode2;
          Element lvNode3;
          
          String lvDatum1 = "00000000";
          String lvDatum2 = "00000000";
          
          //String isin1 = "000000000000";
          //String isin2 = "000000000000";
          
          BigDecimal lvTbetragbd = new BigDecimal(0.00);
          BigDecimal lvZbetragbd = new BigDecimal(0.00);
          BigDecimal lvBufferbd = new BigDecimal(0.00);
          
          // Schleife über das document - erst alle aktiven Papiere
          for (int i=0; i< lvKontolist.size(); i++)
          {
              lvNode = (Element) lvKontolist.get(i);
              
              lvStammlist = lvNode.getChildren("stamm");
                         
              lvStammelem = (Element) lvStammlist.get(0);
           
          //    System.out.println(i + "*** Konto= " + stammelem.getAttributeValue("nr") + " " + stammelem.getAttributeValue("ap"));
              
              // passive in neue Liste zur Verarbeitung unten
              if ("P".equals(lvStammelem.getAttributeValue("ap")))
              {    
                 Element lvNodeP = (Element) lvKontolist.get(i);
                  
                 lvKontolistP.add(lvNodeP);
              }
              else if ("A".equals(lvStammelem.getAttributeValue("ap")))
              {
              
              // Aktive    
              if ( (i%128) == 0)
              {
                System.out.println(i + " Aktivkonto=" +  lvStammelem.getAttributeValue("nr"));
                
                System.out.println("Used Memory aktuell:"
                        + (lvRuntime.totalMemory() - lvRuntime.freeMemory()) / lvMb);
              }
                            
              // TXSFinanzgeschaeft
              Element lvElement_fg = new Element("fg");
              lvElement_fg.setNamespace(lvMyNamespace);
           
              // fg (hier) aufbauen
              lvElement_fg.setAttribute("org", lvBLZ);
                            
              if ("30".equals(lvStammelem.getAttributeValue("anw")))
              {
                lvElement_fg.setAttribute("quelle","DARLEHEN");     // Darlehen      
                
                lvKey =  lvStammelem.getAttributeValue("nr");
              }
              else if ("31".equals(lvStammelem.getAttributeValue("anw")))
              {
                lvElement_fg.setAttribute("quelle","DARLEHEN");     // Fremdwährungsdarlehen  
                
                lvKey = lvStammelem.getAttributeValue("nr");
              }
              else if ("98".equals(lvStammelem.getAttributeValue("anw")))
              {
                lvElement_fg.setAttribute("quelle","DARLEHEN");     // "ausgeblendete"  
                
                lvKey = lvStammelem.getAttributeValue("nr");
              }
                  
              lvElement_fg.setAttribute("key", lvKey);
           
              // fg anhängen
              lvMyrootelement.addContent(lvElement_fg);
          
          //    List<Element> ablauflist = new ArrayList();
          //    ablauflist.clear();
              lvAblauflist = lvNode.getChildren("elem");
              
              lvDatum1 = "00000000";
              lvDatum2 = "00000000";
              
              lvTbetragbd = BigDecimal.ZERO;
              lvZbetragbd = BigDecimal.ZERO;
              
              int merker = 0;
              
              // jetzt Test auf ein auf zwei hostzeilen gesplittetes Konto, nächstes Element holen
              // beim letzten Satz keinen weiteren lesen...
              if (i<lvKontolist.size()-1)
              {
                  lvNode3 = (Element) lvKontolist.get(i+1);
              
                  lvStammlist3 = lvNode3.getChildren("stamm");
                         
                  lvStammelem3 = (Element) lvStammlist3.get(0);
              
                // Treffer ?
                if (lvStammelem.getAttributeValue("nr").equals(lvStammelem3.getAttributeValue("nr")))
                {
                  
                  System.out.println(i + "zweizeiliges Konto= " + lvStammelem.getAttributeValue("nr") );   
                  
                  List<Element> lvAblauflistzweizeiler = new ArrayList<Element>();
                  
                  lvAblauflistzweizeiler = lvNode3.getChildren("elem");
                  
                  int lvListsizezweizeiler = lvAblauflistzweizeiler.size();
                  
                  for (int p=0; p<lvListsizezweizeiler; p++)
                  {
                      Element lvMyelem = (Element) lvAblauflistzweizeiler.get(0); // detach sorgt für das vorrücken
                     
                      lvMyelem.detach();
                       
                      lvAblauflist.add((Element) lvMyelem);
                      
                  //    System.out.println("add " + p + " " + myelem.getAttributeValue("dat"));
                  }    
                 // Treffer: großen Kontenzähler einen vorrücken
                    i++;
                  
                 // merker = 1;
                 }
              }
              
              // Einmal über alle Ablaufelemente
              for (int j=0; j<lvAblauflist.size(); j++)
              {
                 // Ablaufelement holen
                 lvAblaufelem = (Element) lvAblauflist.get(j);
                 
                 // Datum
                 lvDatum1 = lvAblaufelem.getAttributeValue("dat");
                 
                 // Zins oder Tilgung
                 lvTbetragbd = BigDecimal.ZERO;
                 lvZbetragbd = BigDecimal.ZERO;
                 
                 if ( ("11".equals(lvAblaufelem.getAttributeValue("art"))) ||   // tilgung
                      ("12".equals(lvAblaufelem.getAttributeValue("art"))) ||   // außerplanmäßige Tilgung
                      ("13".equals(lvAblaufelem.getAttributeValue("art"))) ||   // RK bei Zinsanpassung
                      ("15".equals(lvAblaufelem.getAttributeValue("art"))) ||   // Abgelaufene ZAP
                      ("17".equals(lvAblaufelem.getAttributeValue("art")))    ) // RK zum Kündigungstermin
                 {
                   lvTbetragbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem.getAttributeValue("betr")));
                 }
                 else if ("21".equals(lvAblaufelem.getAttributeValue("art"))) // zins
                 {
                   lvZbetragbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem.getAttributeValue("betr")));
                 }
                 else
                 {
                   System.out.println("nicht verwendete Ablaufart " + lvAblaufelem.getAttributeValue("art"));  
                 }
                 
             //    System.out.println("j=" + j + " " + Datum1 + " " + ablaufelem.getAttributeValue("art") + " " + tbetragbd + " " + zbetragbd);
                 
                 // Gibt es ab hier weitere CFs zum selben Datum ?
                 for (int k=j+1; k<lvAblauflist.size(); k++)
                 {
                     lvAblaufelem2 = (Element) lvAblauflist.get(k); 
                     lvDatum2 = lvAblaufelem2.getAttributeValue("dat");
                     
                     String lvAblaufart2 = lvAblaufelem2.getAttributeValue("art");
                     
                     if (lvDatum1.equals(lvDatum2))
                     {
                         // Zins oder Tilgung aufaddieren
                         if ( ("11".equals(lvAblaufart2)) ||   // tilgung
                              ("12".equals(lvAblaufart2)) ||   // außerplanmäßige Tilgung
                              ("13".equals(lvAblaufart2)) ||   // RK bei Zinsanpassung
                              ("15".equals(lvAblaufart2)) ||   // Abgelaufene ZAP
                              ("17".equals(lvAblaufart2))    ) // RK zum Kündigungstermin
                         {
                             
                             if (1==merker)
                             {
                                 System.out.println("datum=" + lvDatum2);
                                 System.out.println("decimal= " + lvTbetragbd);
                                 System.out.println("string neu = " + lvAblaufelem2.getAttributeValue("betr"));
                             } 
                             
                            lvBufferbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem2.getAttributeValue("betr")));
                            lvTbetragbd = lvTbetragbd.add(lvBufferbd);
                            
                            if (1==merker)
                            {
                                System.out.println("decimal nach add= " + lvTbetragbd);
                             
                            } 
                         }
                         else if ("21".equals(lvAblaufart2)) // zins
                         {
                           lvBufferbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem2.getAttributeValue("betr")));  
                       //    System.out.println("pufferbd=" + pufferbd);
                           
                           lvZbetragbd = lvZbetragbd.add(lvBufferbd);
                           
                       //    System.out.println("zbetragbd=" + zbetragbd);
                       //    System.out.println("x="+ ablaufelem2.getAttributeValue("betr"));
                         
                         }
                         else
                         {
                           System.out.println("nicht verwendete Ablaufart " + lvAblaufart2);  
                         }
                         
                      //   System.out.println("k=" + k + " " + Datum1 + " " + ablaufart2 + " " + tbetragbd + " " + zbetragbd);
                         
                         // gefundenes element entfernen, damit es nicht nochmal gefunden wird
                         lvAblauflist.remove(k);
                         // der rest der Liste rückt nach unten, daher zähler wieder auf das nächste
                         k--;
                     }
                 } // for über die Ablaufelemente gleichen Datums
                 
                 // alles gefunden, Ausgabe aufbauen
                 Element lvElement_cfdaten = new Element("cfdaten");
                 lvElement_cfdaten.setNamespace(lvMyNamespace);
                     
                 Element lvElement_cfdaten_last = new Element("cfdaten");
                 lvElement_cfdaten_last.setNamespace(lvMyNamespace);
                         
                 lvElement_cfdaten_last.setAttribute("cfkey", lvKey + '_' + lvDatum1);
                         
                 lvElement_cfdaten_last.setAttribute("datum",DatumUtilities.FormatDatum(lvDatum1));
                 
                 lvElement_cfdaten_last.setAttribute("tbetrag",lvTbetragbd.toString());  
                 lvElement_cfdaten_last.setAttribute("zbetrag",lvZbetragbd.toString());  
               
                                                  
                 lvElement_cfdaten_last.setAttribute("whrg",lvAblaufelem.getAttributeValue("iso"));
                         // letzte cfdaten anhängen
                 lvElement_fg.addContent(lvElement_cfdaten_last);
              } // for über die Ablaufelemente
              
              lvStammlist.clear();
              
              lvAblauflist.clear();
              
            } // if aktive
          } // for über die aktiven
            
          lvKontolist.clear();
          
          LOGGER.info("Anzahl der passiven Konten = " + lvKontolistP.size() );
          
          System.out.println("Used Memory aktuell:"
                  + (lvRuntime.totalMemory() - lvRuntime.freeMemory()) / lvMb);
       
          // Schleife über die kleinere Liste mit den passiven
          for (int m=0; m< lvKontolistP.size(); m++)
          {
              lvNode = (Element) lvKontolistP.get(m);
              
              lvStammlist = lvNode.getChildren("stamm");
                         
              lvStammelem = (Element) lvStammlist.get(0);
           
              System.out.println(m + "*** Konto= " + lvStammelem.getAttributeValue("nr") + " " + lvStammelem.getAttributeValue("ap"));
           
              /*
              if ( (m%64) == 0)
              {
                System.out.println(m + " Passivkonto=" +  stammelem.getAttributeValue("nr"));
                
                System.out.println("Used Memory aktuell:"
                        + (runtime.totalMemory() - runtime.freeMemory()) / mb);
              }
              */
              
              // TXSFinanzgeschaeft
              Element lvElement_fg = new Element("fg");
              lvElement_fg.setNamespace(lvMyNamespace);
           
              // fg (hier) aufbauen
              lvElement_fg.setAttribute("org", lvBLZ);
       
              // Andere Behandlung für BLB, dort ist die ISIN nie gefüllt und
              // wir aus DE plus Kontonummer erzeugt
              lvKey = lvStammelem.getAttributeValue("isin");
              
              // SU 20130812 weitere Spezialbehandlung BLB, die Namenspapiere werden mit ISIN geliefert
              // aber *zunächst* so nicht an TXS weitergegeben, daher auf leer
              if (lvInstitut.equals("BLB"))
              {        
                  lvKey ="";    
              }
              
              if ("".equals(lvKey)) 
              {
                  lvKey = "DE"+ lvStammelem.getAttributeValue("nr");
              }
              
              if ("30".equals(lvStammelem.getAttributeValue("anw")))
              {
                lvElement_fg.setAttribute("quelle","DARLEHENWP");     // Darlehen      
              }
              else if ("31".equals(lvStammelem.getAttributeValue("anw")))
              {
                lvElement_fg.setAttribute("quelle","DARLEHENWP");     // Fremdwährungsdarlehen  
              }
              else if ("98".equals(lvStammelem.getAttributeValue("anw")))
              {
                lvElement_fg.setAttribute("quelle","DARLEHEN");     // "ausgeblendete"  
                
                lvKey = lvStammelem.getAttributeValue("nr");
              }
                  
              lvElement_fg.setAttribute("key", lvKey);
           
              // fg anhängen
              lvMyrootelement.addContent(lvElement_fg);
              
              // Ablaufliste holen
              lvAblauflist = lvNode.getChildren("elem");
              
              String lvIsin1 = "000000000000";
              String lvIsin2 = "000000000000";
              
              lvIsin1 = lvStammelem.getAttributeValue("isin");
              
              // SU 20130812 weitere Spezialbehandlung BLB, die Namenspapiere werden mit ISIN geliefert
              // aber *zunächst* so nicht an TXS weitergegeben, daher auf leer
              if (lvInstitut.equals("BLB"))
              {        
                  lvIsin1 ="";    
              }
              
              for (int n=m+1; n< lvKontolistP.size(); n++)
              {
                lvNode2 = (Element) lvKontolistP.get(n);  
                
                lvStammlist2 = lvNode2.getChildren("stamm");
                
                lvStammelem2 = (Element) lvStammlist2.get(0);
                  
                lvIsin2 = lvStammelem2.getAttributeValue("isin");
                
                // SU 20130812 weitere Spezialbehandlung BLB, die Namenspapiere werden mit ISIN geliefert
                // aber *zunächst* so nicht an TXS weitergegeben, daher auf leer
                if (lvInstitut.equals("BLB"))
                {        
                    lvIsin2 ="";    
                }
                
                
                if (lvIsin2.equals(lvIsin1) && !lvIsin2.equals(""))
                {
                  System.out.println(lvIsin1 + " " + lvStammelem.getAttributeValue("nr") + " " + lvStammelem2.getAttributeValue("nr"));                      
                 
                  // anhängen !!
                  List<Element> lvAblauflistplus = new ArrayList<Element>();
                  
                  lvAblauflistplus = lvNode2.getChildren("elem");
                  
                  int lvListsize = lvAblauflistplus.size();
                  
                  for (int p=0; p<lvListsize; p++)
                  {
                      Element lvMyelem = (Element) lvAblauflistplus.get(0); // detach sorgt für das vorrücken
                     
                      lvMyelem.detach();
                       
                      lvAblauflist.add((Element) lvMyelem);
                      
                  //    System.out.println("add " + p + " " + myelem.getAttributeValue("dat"));
                  }    
                  
                  // aus der Liste
                  lvKontolistP.remove(n);
                  // zähler nachrücken
                  n--;
                }
              }
              
              lvDatum1 = "00000000";
              lvDatum2 = "00000000";
              
              // Einmal über alle Ablaufelemente
              for (int j=0; j<lvAblauflist.size(); j++)
              {
                 // Ablaufelement holen
                 lvAblaufelem = (Element) lvAblauflist.get(j);
                 
                 // Datum
                 lvDatum1 = lvAblaufelem.getAttributeValue("dat");
                 
                 lvTbetragbd = BigDecimal.ZERO;
                 lvZbetragbd = BigDecimal.ZERO;
                 
                 if ( ("11".equals(lvAblaufelem.getAttributeValue("art"))) ||   // tilgung
                      ("12".equals(lvAblaufelem.getAttributeValue("art"))) ||   // außerplanmäßige Tilgung
                      ("13".equals(lvAblaufelem.getAttributeValue("art"))) ||   // RK bei Zinsanpassung
                      ("15".equals(lvAblaufelem.getAttributeValue("art"))) ||   // Abgelaufene ZAP
                      ("17".equals(lvAblaufelem.getAttributeValue("art")))    ) // RK zum Kündigungstermin
                 {
                   lvTbetragbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem.getAttributeValue("betr")));
                 }
                 else if ("21".equals(lvAblaufelem.getAttributeValue("art"))) // zins
                 {
                   lvZbetragbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem.getAttributeValue("betr")));
                 }
                 else
                 {
                   System.out.println("nicht verwendete Ablaufart " + lvAblaufelem.getAttributeValue("art"));  
                 }
                 
                 // Gibt es ab hier weitere CFs zum selben Datum ?
                 for (int k=j+1; k<lvAblauflist.size(); k++)
                 {
                     lvAblaufelem2 = (Element) lvAblauflist.get(k); 
                     lvDatum2 = lvAblaufelem2.getAttributeValue("dat");
                     
                     if (lvDatum1.equals(lvDatum2))
                     {
                         // Zins oder Tilgung aufaddieren
                         if ( ("11".equals(lvAblaufelem2.getAttributeValue("art"))) ||   // tilgung
                              ("12".equals(lvAblaufelem2.getAttributeValue("art"))) ||   // außerplanmäßige Tilgung
                              ("13".equals(lvAblaufelem2.getAttributeValue("art"))) ||   // RK bei Zinsanpassung
                              ("15".equals(lvAblaufelem2.getAttributeValue("art"))) ||   // Abgelaufene ZAP
                              ("17".equals(lvAblaufelem2.getAttributeValue("art")))    ) // RK zum Kündigungstermin
                         {
                            lvBufferbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem2.getAttributeValue("betr")));  
                            lvTbetragbd = lvTbetragbd.add(lvBufferbd);  
                         
                         }
                         else if ("21".equals(lvAblaufelem2.getAttributeValue("art"))) // zins
                         {
                           lvBufferbd = BigDecimal.valueOf(Double.parseDouble(lvAblaufelem2.getAttributeValue("betr")));  
                           lvZbetragbd = lvZbetragbd.add(lvBufferbd);  
                         }
                         else
                         {
                           System.out.println("nicht verwendete Ablaufart " + lvAblaufelem2.getAttributeValue("art"));  
                         }
                         
                         // gefundenes element entfernen, damit es nicht nochmal gefunden wird
                         lvAblauflist.remove(k);
                         // zähler nachrücken
                         k--;
                     }
                 } // for über die Ablaufelemente gleichen Datums
                 
                 // alles gefunden, Ausgabe aufbauen
                 Element lvElement_cfdaten = new Element("cfdaten");
                 lvElement_cfdaten.setNamespace(lvMyNamespace);
                     
                 Element lvElement_cfdaten_last = new Element("cfdaten");
                 lvElement_cfdaten_last.setNamespace(lvMyNamespace);
                         
                 lvElement_cfdaten_last.setAttribute("cfkey", lvKey + '_' + lvDatum1);
                 
               //  element_cfdaten_last.setAttribute("cfkey", ablaufelem.getAttributeValue("nr")+ '_' + Datum1);
                 
                 lvElement_cfdaten_last.setAttribute("datum",DatumUtilities.FormatDatum(lvDatum1));
                 
              //   element_cfdaten_last.setAttribute("tbetrag",Double.toString(tbetrag));  
              //   element_cfdaten_last.setAttribute("zbetrag",Double.toString(zbetrag));
                 
                 // passive CFs auch positiv
                 lvTbetragbd = lvTbetragbd.abs();
                 lvZbetragbd = lvZbetragbd.abs();
                 
                 // KEINE TILGUNGEN FÜR PASSIVE 20121219   
               //element_cfdaten_last.setAttribute("tbetrag",tbetragbd.toString()); 
                 lvElement_cfdaten_last.setAttribute("tbetrag","0");
                 
                 lvElement_cfdaten_last.setAttribute("zbetrag",lvZbetragbd.toString());  
                                                  
                 lvElement_cfdaten_last.setAttribute("whrg",lvAblaufelem.getAttributeValue("iso"));
                 // letzte cfdaten anhängen
                 lvElement_fg.addContent(lvElement_cfdaten_last);
              } // for über die Ablaufelemente
              
              lvStammlist.clear();
              
              lvAblauflist.clear();
              
          } // for über die passiven
          
          lvKontolistP.clear();
        }  
        catch(IOException io)
        {
          System.out.println(io.getMessage());
        }
        catch(JDOMException jdomex)
        {
          System.out.println("JDOMException");  
          System.out.println(jdomex.getMessage());
          
          LOGGER.error(jdomex.getMessage());
          
          // hier bleibt nichts anders als der exit
          System.exit(0);
        }
        
        System.out.println("Used Memory aktuell:"
                + (lvRuntime.totalMemory() - lvRuntime.freeMemory()) / lvMb);
        
        System.out.println("vor ausgabe");
        
        XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());
        
        System.out.println("format ok");
              
        try
        {
          lvTXSost = new FileOutputStream(lvTXSFile);
        }
        catch (Exception e)
        {
          System.out.println("Konnte TXS-Ausgabedatei " + lvFileout +" nicht oeffnen!");
          LOGGER.error("Konnte TXS-Ausgabedatei " + lvFileout +" nicht oeffnen!");
        }    
        
        try
        {
         //   outputter.output(lvMydocument, System.out);
            lvOutputter.output(lvMydocument,lvTXSost);
            System.out.println("ausgabe ok");
            
        }
        catch (IOException e) {e.printStackTrace();}
        
        // verarbeitete Datei ins logfile schreiben
        // gemeint ist nicht das log4j logfile, sondern die Verarbeitungshistorie
        // dieser Art:
        
        //-----------------------------------------
        //Montag, 10. Dezember 2012 15:28:57
        //A1PBAT.NT.I009.LMTXS.CFXML.D121126
        //-----------------------------------------
        //-----------------------------------------
        //Montag, 4. Februar 2013 14:25:28
        //A1PBAT.NT.I009.LMTXS.CFXML.D121124
        //-----------------------------------------
        //-----------------------------------------
        //Montag, 4. Februar 2013 16:25:27
        //A1PBAT.NT.I009.LMTXS.CFXML.D121128
        //-----------------------------------------

        
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
           
           //Getting the runtime reference from system
           lvRuntime = Runtime.getRuntime();

           System.out.println("##### Heap utilization statistics [MB] #####");

           //Print used memory
           System.out.println("Used Memory:"
               + (lvRuntime.totalMemory() - lvRuntime.freeMemory()) / lvMb);

           //Print free memory
           System.out.println("Free Memory:"
               + lvRuntime.freeMemory() / lvMb);

           //Print total available memory
           System.out.println("Total Memory:" + lvRuntime.totalMemory() / lvMb);

           //Print Maximum available memory
           System.out.println("Max Memory:" + lvRuntime.maxMemory() / lvMb);
     
           Date endedate = new Date();
           // display time and date using toString()
           LOGGER.info("Ende : " + endedate);
    }
}
