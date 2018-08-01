/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.Kunde;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import nlb.txs.schnittstelle.Deckungspooling.OSPInstitut.OSPInstitutDaten;
import nlb.txs.schnittstelle.Deckungspooling.OSPInstitut.OSPInstitutKundenStatistik;
import nlb.txs.schnittstelle.Deckungspooling.OSPInstitut.OSPInstitutListe;
import nlb.txs.schnittstelle.Filtern.ListeObjekte;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.MappingDPP;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

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
public class OSPKundenVerarbeitung 
{
    /**
     * 
     */
    private org.jdom2.Document ivDocument;
    
    /**
     * Liste von OSPInstitute
     */
    private OSPInstitutListe ivListeOSPInstitute;
    
    /**
     * Liste der Quellsysteme
     */
    private ListeObjekte ivListeQuellsysteme;
    
    /**
     * Bestandsdatum
     */
    private String ivBestandsdatum;
    
    /**
     * SystemID
     */
    private String ivSystemID;
    
    /**
     * Creation-Timestamp
     */
    private String ivCreationTimestamp;
    
    /**
     * Ziel-Institut (004 -> BLB oder 009 -> NLB)
     */
    private String ivZielInstitut;
    
    /**
     * Konstruktor
     * @param pvListeOSPInstitute 
     * @param pvImportVerzeichnis 
     * @param pvExportVerzeichnis 
     * @param pvOSPKundenDatei 
     * @param pvTXSKundenDatei 
     * @param pvListeQuellsysteme 
     * @param pvLogger 
     */
    public OSPKundenVerarbeitung(OSPInstitutListe pvListeOSPInstitute, String pvImportVerzeichnis, String pvExportVerzeichnis,
                                 String pvOSPKundenDatei, String pvTXSKundenDatei, ListeObjekte pvListeQuellsysteme, Logger pvLogger)
    {
        pvLogger.info("Start DPP Kundenverarbeitung");
        this.ivBestandsdatum = new String();
        this.ivSystemID = new String();
        this.ivCreationTimestamp = new String();
        this.ivZielInstitut = new String();
        
        this.ivListeOSPInstitute = pvListeOSPInstitute;
        this.ivListeQuellsysteme = pvListeQuellsysteme;
       
        Runtime lvRuntime = Runtime.getRuntime();
        
        pvLogger.info("Used Memory vor dem Lesen:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));

        // Kundeninformationen aus OSPlus einlesen
        if (leseKunden(pvImportVerzeichnis + "\\" + pvOSPKundenDatei, pvLogger))
        {
           //leseKunden("D:\\NLB_TXX_20110418\\Deckungspooling\\DPP_Kunden_TXS_Vorschlag_20120718.xml");
        
          // Kundeninformationen verarbeiten
          if (!importOSPKunden(pvLogger))
          {
            pvLogger.error("Kunden: OSPInstitut nicht gefunden!");
          }
        }
       
        pvLogger.info("Used Memory nach dem Lesen:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
        
        // Kundeninformationen als TXS-Importdatei schreiben 
        schreibeKunden(pvExportVerzeichnis + "\\" + pvTXSKundenDatei, pvLogger);
        
        pvLogger.info("Used Memory nach dem Schreiben:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
        pvLogger.info("Ende DPP Kundenverarbeitung");
    }
    
    /**
     * Liest die Kundendaten aus OSPlus
     * @param pvFilename Dateiname der Kundendaten aus OSPlus
     * @param log
     */
    private boolean leseKunden(String pvFilename, Logger pvLogger)
    {
        // Zur Eingabe
        SAXBuilder ivBuilder = null;
        ivBuilder = new SAXBuilder();
        ivBuilder.setExpandEntities(true);
          
        try
        {
            ivDocument = (Document)ivBuilder.build(pvFilename);
        }
        catch(IOException io)
        {
             //System.out.println(io.getMessage());
             pvLogger.error(io.getMessage());
             return false;
        }
        catch(JDOMException jdomex)
        {
             //System.out.println(jdomex.getMessage());
             pvLogger.error(jdomex.getMessage());
             return false;
        }
        return true;
    }

    /**
     * Importiert die eingelesenen Kundendaten
     *
     */
    private boolean importOSPKunden(Logger pvLogger)
    {        
        Element lvRootNode = ivDocument.getRootElement();
  
        Element lvNodeHeader = (Element)lvRootNode.getChildren("Header").get(0);       

        this.ivBestandsdatum = lvNodeHeader.getChildText("Bestandsdatum");
        this.ivSystemID = lvNodeHeader.getChildText("SystemID");
        this.ivCreationTimestamp = lvNodeHeader.getChildText("CreationTimestamp");
            
        pvLogger.info("Bestandsdatum: " + ivBestandsdatum);
        pvLogger.info("SystemID: " + ivSystemID);
        pvLogger.info("CreationTimestamp: " + ivCreationTimestamp);            
        
        Element lvNodeStatistik = (Element)lvNodeHeader.getChildren("Statistik").get(0);
        List<Element> lvListInst = lvNodeStatistik.getChildren("OSPInstitut");
        pvLogger.info("Anzahl gefundener OSPInstitute - Statistik: " + lvListInst.size());

        String lvRegKz = new String();
        String lvOSPInst = new String();
        String lvDarKaKuNr = new String();

        OSPInstitutKundenStatistik lvKundenStatistik = new OSPInstitutKundenStatistik();;
        Element lvNodeOSPInstitutStatistik;
        for (int x = 0; x < lvListInst.size(); x++)
        {
            lvNodeOSPInstitutStatistik = (Element)lvListInst.get(x);  

            lvRegKz = lvNodeOSPInstitutStatistik.getAttributeValue("RegKz");
            lvOSPInst = lvNodeOSPInstitutStatistik.getAttributeValue("OSPInst");
            
            if (lvOSPInst.length() == 1) lvOSPInst = "00" + lvOSPInst;
            if (lvOSPInst.length() == 2) lvOSPInst = "0" + lvOSPInst;

            lvDarKaKuNr = lvNodeOSPInstitutStatistik.getAttributeValue("DarKaKuNr");
        
            pvLogger.info("Statistik - RegKz: " + lvRegKz);
            pvLogger.info("Statistik - OSPInst: " + lvOSPInst);
            pvLogger.info("Statistik - DarKaKuNr: " + lvDarKaKuNr);

            //OSPInstitutDaten datenInst;
            //if (listeOSPInstitute.contains(regKz, OSPInst))
            //{
            //    datenInst = listeOSPInstitute.getOSPInstitut(regKz, OSPInst);
            //}
            //else
            //{
            //    datenInst = new OSPInstitutDaten(regKz, OSPInst, DarKaKuNr);
            //    listeOSPInstitute.add(datenInst);
            //}
            //datenInst.setKundenBestandsdatum(bestandsdatum);

            //kundenStatistik = new OSPInstitutKundenStatistik();
            lvKundenStatistik.setAnzahlKunden(StringKonverter.convertString2Int(lvNodeOSPInstitutStatistik.getChildText("AnzKd")));
            lvKundenStatistik.setAnzahlKonten(StringKonverter.convertString2Int(lvNodeOSPInstitutStatistik.getChildText("AnzKto")));
        
            //datenInst.setKundenStatistik(kundenStatistik);
        }
        
        Element lvNodeResponse = (Element)lvRootNode.getChildren("Response").get(0);
        
        Element lvNodeZielInstitut = (Element)lvNodeResponse.getChildren("ZielInstitut").get(0);
         
        ivZielInstitut = lvNodeZielInstitut.getAttributeValue("nr");
        pvLogger.info("ZielInstitut: " + ivZielInstitut);
        
        Element lvNodeOSPInstitut;
        lvListInst = lvNodeZielInstitut.getChildren("OSPInstitut");
        pvLogger.info("Anzahl gefundener OSPInstitute: " + lvListInst.size());
        
        for (int i = 0; i < lvListInst.size(); i++)
        {
            lvNodeOSPInstitut = (Element)lvListInst.get(i);  

            lvRegKz = lvNodeOSPInstitut.getAttributeValue("RegKz");
            lvOSPInst = lvNodeOSPInstitut.getAttributeValue("OSPInst");
            
            if (lvOSPInst.length() == 1) lvOSPInst = "00" + lvOSPInst;
            if (lvOSPInst.length() == 2) lvOSPInst = "0" + lvOSPInst;
            System.out.println(lvRegKz + lvOSPInst);
            
            if (ivListeQuellsysteme.containsKey(lvRegKz + lvOSPInst))
            {
                // Die DarKaKuNr muss 10-stellig sein
                lvDarKaKuNr = MappingDPP.extendKundennummer(lvNodeOSPInstitut.getAttributeValue("DarKaKuNr"));
             
                OSPInstitutDaten lvDatenInst;
                if (ivListeOSPInstitute.contains(lvRegKz, lvOSPInst))
                {
                    lvDatenInst = ivListeOSPInstitute.getOSPInstitut(lvRegKz, lvOSPInst);
                }
                else
                {
                    lvDatenInst = new OSPInstitutDaten(lvRegKz, lvOSPInst, lvDarKaKuNr);
                    ivListeOSPInstitute.add(lvDatenInst);
                }
                lvDatenInst.setKundenBestandsdatum(ivBestandsdatum);
                // Die Statistik muss noch überarbeitet werden, da jetzt immer
                // die letzte eingelesene Statistik gewinnt und in jedes
                // Institut geschrieben wird.
                lvDatenInst.setKundenStatistik(lvKundenStatistik);

                //if (datenInst == null)
                //{
                //   System.out.println("OSPInstitut nicht gefunden!");
                //   return false;
                //}
                
                pvLogger.info("RegKz: " + lvRegKz);
                pvLogger.info("OSPInst: " + lvOSPInst);
                pvLogger.info("DarKaKuNr: " + lvDarKaKuNr);        
        
                List<Element> lvListKunde = lvNodeOSPInstitut.getChildren("Kunde");
                pvLogger.info("Anzahl gefundener Kunden: " + lvListKunde.size());

                Element lvNodeKunde;
                for (int j = 0; j < lvListKunde.size(); j++)
                {
                    OSPKunde lvKunde = new OSPKunde();  
                    lvNodeKunde = (Element)lvListKunde.get(j);  

                    System.out.println("Kundennummer: " + lvNodeKunde.getAttributeValue("nr"));
                    lvKunde.setKundennr(lvNodeKunde.getAttributeValue("nr"));
                    lvKunde.setVorname(lvNodeKunde.getChildText("Vorname"));
                    lvKunde.setNachname(lvNodeKunde.getChildText("Nachname"));
                    lvKunde.setErweiterung(lvNodeKunde.getChildText("Erweiterung"));
                    lvKunde.setKusyma(lvNodeKunde.getChildText("Kusyma"));
                    lvKunde.setRolle(lvNodeKunde.getChildText("Rolle"));
                    lvKunde.setBeruf(lvNodeKunde.getChildText("Beruf"));
                    List<Element> lvListKonten = lvNodeKunde.getChildren("Konto");
                    System.out.println("Anzahl gefundener Konten: " + lvListKonten.size());
                    LinkedList<String> lvListeKontonr = new LinkedList<String>();
                    for (int k = 0; k < lvListKonten.size(); k++)
                    {
                        lvListeKontonr.add(((Element)lvListKonten.get(k)).getText()); 
                        System.out.println("Kontonummer: " + ((Element)lvListKonten.get(k)).getText());
                    }
                    lvKunde.setListeKontonummern(lvListeKontonr);
            
                    Element lvNodeAdresse = (Element)lvNodeKunde.getChildren("Adresse").get(0);
            
                    lvKunde.setStr(lvNodeAdresse.getChildText("Strasse"));
                    lvKunde.setHausnr(lvNodeAdresse.getChildText("Hausnr"));
                    lvKunde.setPlz(lvNodeAdresse.getChildText("Plz"));
                    lvKunde.setOrt(lvNodeAdresse.getChildText("Ort"));
                    lvKunde.setLand(lvNodeAdresse.getChildText("Land"));
                    lvKunde.setGebiet(lvNodeAdresse.getChildText("Gebiet"));
            
                    lvDatenInst.addListeKunden(lvKunde);
                }
                System.out.println(lvDatenInst.toString());
            }
            else
            {
                pvLogger.error("Institut " + lvRegKz + lvOSPInst + " nicht gefunden...");
            }
        }
       
        return true; 
    }
    
    
    /**
     * 
     */
    private void schreibeKunden(String pvFilenameOut, Logger pvLogger)
    { 
        Element lvMyrootelement = new Element("importdaten");
        Namespace lvMyNamespace = Namespace.getNamespace("txsi", "http://agens.com/txsimport.xsd");
        lvMyrootelement.setNamespace(lvMyNamespace);
        Document lvMydocument = new Document(lvMyrootelement);
            
        // Header anhaengen
        Element lvMyelement2 = new Element("header");
    
        if (ivBestandsdatum.isEmpty())
        {
            Calendar lvCal = Calendar.getInstance();
            CalendarHelper lvCalHelp = new CalendarHelper();
            ivBestandsdatum = lvCalHelp.printDateAsNumber(lvCal);
        }
        lvMyelement2.setAttribute("valdate", DatumUtilities.changeDate(DatumUtilities.changeDatePoints(ivBestandsdatum)));
        lvMyrootelement.addContent(lvMyelement2);
        
        for (int x = 0; x < ivListeOSPInstitute.size(); x++)
        {
            OSPInstitutDaten lvInstitut = ivListeOSPInstitute.get(x);
            for (int y = 0; y < lvInstitut.getListeKunden().size(); y++)
            {
                OSPKunde lvKunde = lvInstitut.getListeKunden().get(y);
                
                // TXSPerson
                Element lvElement_pers = new Element("pers");
                lvElement_pers.setNamespace(lvMyNamespace);
               
                 // pers aufbauen
                 lvElement_pers.setAttribute("kdnr", lvInstitut.getRegKz() + lvInstitut.getOSPInst() + "_" + lvKunde.getKundennr());
                 lvElement_pers.setAttribute("org", lvInstitut.getRegKz() + lvInstitut.getOSPInst()); //ValueMapping.changeMandant(zielInstitut));
                 lvElement_pers.setAttribute("quelle", "DPP");
               
                 // TXSPersonDaten
                 Element lvElement_persdaten = new Element("persdaten");
                 lvElement_persdaten.setNamespace(lvMyNamespace);
         
                 // persdaten aufbauen
                 lvElement_persdaten.setAttribute("vname", lvKunde.getVorname());
                 lvElement_persdaten.setAttribute("nname", lvKunde.getNachname());              
                 lvElement_persdaten.setAttribute("str", lvKunde.getStr());
                 lvElement_persdaten.setAttribute("hausnr", lvKunde.getHausnr());
                 lvElement_persdaten.setAttribute("plz", lvKunde.getPlz());
                 lvElement_persdaten.setAttribute("ort", lvKunde.getOrt());
                 //element_persdaten.setAttribute("text", nodeAdresse.getChildText("Ergaenzung"));
                 
                 //if (nodeAdresse.getChildText("Land").equals("U1"))
                 //  element_persdaten.setAttribute("land", "GB");
                 //else    
                 lvElement_persdaten.setAttribute("land", MappingDPP.changeLaenderkennzahl(lvKunde.getLand()));
                 
                 // CT 22.12.2011 User-definierte Felder 
                 lvElement_persdaten.setAttribute("kusyma", lvKunde.getKusyma());
                 lvElement_persdaten.setAttribute("kugr", ValueMapping.changeKundengruppe(ermittleKundengruppe(lvKunde.getKusyma())));
                 
                 // CT 31.07.2012 - DPP liefert kein Gebiet, deshalb selbst ermitteln
                 if (MappingDPP.changeLaenderkennzahl(lvKunde.getLand()).equals("DE"))
                 {
                     String lvGebiet = MappingDPP.changeLaenderkennzahl(lvKunde.getLand()) + "_";
                     int lvRet_gebiet = ValueMapping.PLZ2Land(ivZielInstitut, lvKunde.getPlz());
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
                 
                 // pers anhaengen
                 lvMyrootelement.addContent(lvElement_pers);
               
                 // persdaten anhaengen
                 lvElement_pers.addContent(lvElement_persdaten);

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
          System.out.println("Konnte Kunde-XML Datei nicht oeffnen!");
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
    }
    
    /**
     * Ermittlung der Kundengruppe über die Kusyma 
     * @param pvKusyma
     * @return
     */
    private String ermittleKundengruppe(String pvKusyma)
    {
        /* Kundengruppe aus Kusyma */
        String lvKGruppe = new String();
    
        lvKGruppe = "H"; /* Default */
    
        if (pvKusyma.startsWith("0"))
        { /* Inländische Kreditinstitute */
            lvKGruppe = "G";
        } /* Inländische Kreditinstitute */
        if (pvKusyma.startsWith("1"))
        { /* Inländische öffentliche Haushalte */
            if (pvKusyma.equals("10000000"))
            { /* Bund */
              lvKGruppe = "B_1";
            }
            else
            { /* Nicht direkt Bund */
                if (pvKusyma.equals("11000000") ||
                    pvKusyma.equals("12000000"))
                { /* Bundesländer */
                    lvKGruppe = "C";
                } /* Bundesländer */
                else
                { /* keine Bundesländer */
                    if (pvKusyma.equals("13000000") ||
                        pvKusyma.equals("14000000"))
                    { /* Gemeinden */
                        lvKGruppe = "D";
                    } /* Gemeinden */
                    else
                    { /* keine Gemeinden */
                        if (pvKusyma.startsWith("15") ||
                            pvKusyma.startsWith("16") ||
                            pvKusyma.startsWith("17") ||
                            pvKusyma.startsWith("19"))
                        { /* Öfftl.Untern/Zweck. */
                            lvKGruppe = "E";
                        } /* Öfftl.Unternehmen/Zweckverbände */
                        else
                        { /* Keine öfftl.Unternehmen/Zweckverbände */
                            if (pvKusyma.equals("10000001") ||
                                pvKusyma.startsWith("18"))
                            { /* Andere durch Bund */
                                lvKGruppe = "B_2";
                            } /* Andere durch Bund abgesicherte */
                        } /* Keine öfftl.Unternehmen/Zweckverbände */
                    } /* keine Gemeinden */
                } /* keine Bundesländer */
            } /* Nicht direkt Bund */
        } /* Inländische öffentliche Haushalte */
    
        if (pvKusyma.startsWith("4"))
        { /* Inländische Unternehmen.... */
            if (pvKusyma.charAt(1) == '0' || pvKusyma.charAt(1) == '1' ||
                pvKusyma.charAt(1) == '2' || pvKusyma.charAt(1) == '3' ||
                pvKusyma.charAt(1) == '4' || pvKusyma.charAt(1) == '5')
            { /* öffentlich */
                lvKGruppe = "F";
            } /* mehrheitlich öffentlich */
        } /* Inländische Unternehmen.... */
        if (pvKusyma.startsWith("5"))
        { /* Ausländische KI */
            if (pvKusyma.charAt(2) == '0' || pvKusyma.charAt(2) == '1' ||
                pvKusyma.charAt(2) == '2' || pvKusyma.charAt(2) == '3')
            { /* int. Inst. */
                lvKGruppe = "A_2";
            } /* Internationale Institutionen */
        } /* Ausländische KI */
        if (pvKusyma.startsWith("6"))
        { /* Ausländische öffentliche Haushalte */
            if (pvKusyma.charAt(1) == '0')
            { /* Zentralregierung */
                lvKGruppe = "A_1";
            } /* Zentralregierung */
            else
            { /* Keine Zentralregierung */
                if (pvKusyma.charAt(1) == '2' || pvKusyma.charAt(1) == '4')
                { /*Länder/Sonst*/
                    lvKGruppe = "A_3";
                } /* Länder/Sonstige.. */
                else
                { /* Keine Länder .. */
                    if (pvKusyma.charAt(1) == '5' || pvKusyma.charAt(1) == '6')
                    { /* Andere ...*/
                        lvKGruppe = "A_6";
                    } /* Andere .......... */
                    else
                    { /* Keine Anderen */
                        if (pvKusyma.charAt(1) == '3')
                        { /* Gemeinden .....*/
                            lvKGruppe = "A_4";
                        } /* Gemeinden .......... */
                    } /* Keine Anderen */
                } /* Keine Länder .. */
            } /* Keine Zentralregierung */
        } /* Ausländische öffentliche Haushalte */
        
        return lvKGruppe;
    }
}
