/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.Sicherheiten;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Darlehen;
import nlb.txs.schnittstelle.Deckungspooling.OSPInstitut.OSPInstitutDaten;
import nlb.txs.schnittstelle.Deckungspooling.OSPInstitut.OSPInstitutListe;
import nlb.txs.schnittstelle.Deckungspooling.OSPInstitut.OSPInstitutSicherheitenStatistik;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSPfandobjektDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitVerzeichnis;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisPfandobjekt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisVBlatt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisblattDaten;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author tepperc
 *
 */
@Deprecated
public class OSPSicherheitenVerarbeitung 
{
    /**
     * 
     */
    private Document ivDocument;
    
    /**
     * Liste von OSPInstitute
     */
    private OSPInstitutListe ivListeOSPInstitute;
    
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
     * Logger
     */
    private Logger ivLogger;
    
    /**
     * Konstruktor
     * @param pvListeOSPInstitute 
     * @param pvImportVerzeichnis 
     * @param pvExportVerzeichnis 
     * @param pvOSPSicherheitenDatei
     * @param pvLogger 
     */
    public OSPSicherheitenVerarbeitung(OSPInstitutListe pvListeOSPInstitute, String pvImportVerzeichnis, String pvExportVerzeichnis,
                                 String pvOSPSicherheitenDatei, Logger pvLogger)
    {
        this.ivLogger = pvLogger;
        
        pvLogger.info("Start DPP Sicherheitenverarbeitung");
        this.ivListeOSPInstitute = pvListeOSPInstitute;
       
        Runtime lvRuntime = Runtime.getRuntime();
        
        pvLogger.info("Used Memory vor dem Lesen:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));

        // Kundeninformationen aus OSPlus einlesen
        if (leseSicherheiten(pvImportVerzeichnis + "\\" + pvOSPSicherheitenDatei, pvLogger))
        {
          //leseKunden("D:\\NLB_TXX_20110418\\Deckungspooling\\DPP_Kunden_TXS_Vorschlag_20120718.xml");
        
          // Kundeninformationen verarbeiten
          if (!importOSPSicherheiten(pvLogger))
          {
            pvLogger.error("Sicherheiten: OSPInstitut nicht gefunden!");
          }
        }

        pvLogger.info("Used Memory nach dem Lesen:"
                    + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
        
        pvLogger.info("Ende DPP Sicherheitenverarbeitung");
    }
 
    /**
     * @return the bestandsdatum
     */
    public String getBestandsdatum() {
        return this.ivBestandsdatum;
    }

    /**
     * @param pvBestandsdatum the bestandsdatum to set
     */
    public void setBestandsdatum(String pvBestandsdatum) {
        this.ivBestandsdatum = pvBestandsdatum;
    }

    /**
     * @return the systemID
     */
    public String getSystemID() {
        return this.ivSystemID;
    }

    /**
     * @param pvSystemID the systemID to set
     */
    public void setSystemID(String pvSystemID) {
        this.ivSystemID = pvSystemID;
    }

    /**
     * @return the creationTimestamp
     */
    public String getCreationTimestamp() {
        return this.ivCreationTimestamp;
    }

    /**
     * @param pvCreationTimestamp the creationTimestamp to set
     */
    public void setCreationTimestamp(String pvCreationTimestamp) {
        this.ivCreationTimestamp = pvCreationTimestamp;
    }

    /**
     * @return the zielInstitut
     */
    public String getZielInstitut() {
        return this.ivZielInstitut;
    }

    /**
     * @param pvZielInstitut the zielInstitut to set
     */
    public void setZielInstitut(String pvZielInstitut) {
        this.ivZielInstitut = pvZielInstitut;
    }

    /**
     * Liest die Sicherheitendaten aus OSPlus
     * @param pvFilename Dateiname der Sicherheitendaten aus OSPlus
     * @param log
     */
    private boolean leseSicherheiten(String pvFilename, Logger pvLogger)
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
     * Importiert die eingelesenen Sicherheitendaten
     *
     */
    private boolean importOSPSicherheiten(Logger pvLogger)
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

        Element lvNodeOSPInstitutStatistik;
        for (int x = 0; x < lvListInst.size(); x++)
        {
            lvNodeOSPInstitutStatistik = lvListInst.get(x);  

            lvRegKz = lvNodeOSPInstitutStatistik.getAttributeValue("RegKz");
            lvOSPInst = lvNodeOSPInstitutStatistik.getAttributeValue("OSPInst");
            if (lvOSPInst.length() == 1) lvOSPInst = "00" + lvOSPInst;
            if (lvOSPInst.length() == 2) lvOSPInst = "0" + lvOSPInst;
            
            lvDarKaKuNr = lvNodeOSPInstitutStatistik.getAttributeValue("DarKaKuNr");
        
            
            pvLogger.info("Statistik - RegKz: " + lvRegKz);
            pvLogger.info("Statistik - OSPInst: " + lvOSPInst);
            pvLogger.info("Statistik - DarKaKuNr: " + lvDarKaKuNr);

            OSPInstitutDaten datenInst = null;
            //System.out.println("Import - " + regKz + OSPInst);
            if (ivListeOSPInstitute.contains(lvRegKz, lvOSPInst))
            {
                datenInst = ivListeOSPInstitute.getOSPInstitut(lvRegKz, lvOSPInst);
            }
            //else
            //{
            //    datenInst = new OSPInstitutDaten(regKz, OSPInst, DarKaKuNr);
            //    listeOSPInstitute.add(datenInst);
            //}
            if (datenInst == null)
            {
               System.out.println("Import - OSPInstitut nicht gefunden!");
               return false;
            }

            datenInst.setSicherheitenBestandsdatum(ivBestandsdatum);

            OSPInstitutSicherheitenStatistik lvSicherheitenStatistik = new OSPInstitutSicherheitenStatistik();
            lvSicherheitenStatistik.setAnzahlSicherheiten(StringKonverter.convertString2Int(lvNodeOSPInstitutStatistik.getChildText("AnzSi")));
            lvSicherheitenStatistik.setAnzahlVermoegensobjekte(StringKonverter.convertString2Int(lvNodeOSPInstitutStatistik.getChildText("AnzVObj")));
            lvSicherheitenStatistik.setAnzahlGrundbuecher(StringKonverter.convertString2Int(lvNodeOSPInstitutStatistik.getChildText("AnzGB")));
            
            datenInst.setSicherheitenStatistik(lvSicherheitenStatistik);
        }
        
        Element lvNodeResponse = (Element)lvRootNode.getChildren("Response").get(0);
        
        Element lvNodeZielInstitut = (Element)lvNodeResponse.getChildren("ZielInstitut").get(0);
         
        this.ivZielInstitut = lvNodeZielInstitut.getAttributeValue("nr");
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
            
            lvDarKaKuNr = lvNodeOSPInstitut.getAttributeValue("DarKaKuNr");
        
            OSPInstitutDaten lvDatenInst;
            lvDatenInst = ivListeOSPInstitute.getOSPInstitut(lvRegKz, lvOSPInst);
            if (lvDatenInst == null)
            {
               System.out.println("OSPInstitut nicht gefunden!");
               return false;
            }
                
            pvLogger.info("RegKz: " + lvRegKz);
            pvLogger.info("OSPInst: " + lvOSPInst);
            pvLogger.info("DarKaKuNr: " + lvDarKaKuNr);        
        
            List<Element> listSicherheit = lvNodeOSPInstitut.getChildren("Sicherheit");
            pvLogger.info("Anzahl gefundener Sicherheiten: " + listSicherheit.size());

            Element lvNodeSicherheit;
            for (int j = 0; j < listSicherheit.size(); j++)
            {
                lvNodeSicherheit = listSicherheit.get(j);  
                OSPSicherheit lvSicherheit = new OSPSicherheit(lvNodeSicherheit.getAttributeValue("nr"));

                System.out.println("Sicherheitennummer: " + lvNodeSicherheit.getAttributeValue("nr"));
                // Daten der Sicherheit            
                lvSicherheit.setRang(lvNodeSicherheit.getChildText("Rang"));
                lvSicherheit.setNominalwert(lvNodeSicherheit.getChildText("Nominalwert"));
                lvSicherheit.setWaehrung(lvNodeSicherheit.getChildText("Waehrung"));
                lvSicherheit.setSicherheitenart(lvNodeSicherheit.getChildText("Sicherheitenart"));
                lvSicherheit.setSicherheitenartElementar(lvNodeSicherheit.getChildText("SicherheitenartElementar"));
                lvSicherheit.setSicherungsrechtart(lvNodeSicherheit.getChildText("Sicherungsrechtart"));
                lvSicherheit.setBelastungsforderungsbetrag(lvNodeSicherheit.getChildText("Belastungsforderungsbetrag"));
                lvSicherheit.setVerfuegungsbetrag(lvNodeSicherheit.getChildText("Verfuegungsbetrag"));
                
                // Vermoegensobjekt-Zuordnung
                Element lvNodeVermoegensobjekt = (Element)lvNodeSicherheit.getChildren("Vermoegensobjekt-Zuordnung").get(0);
                List<Element> lvListVermoegensobjekte = lvNodeVermoegensobjekt.getChildren("Vermoegensobjekt");
                System.out.println("Anzahl gefundener Vermoegensobjekte: " + lvListVermoegensobjekte.size());
                LinkedList<String> lvListeVermoegensobjekte = new LinkedList<String>();
                for (int v = 0; v < lvListVermoegensobjekte.size(); v++)
                {
                    lvListeVermoegensobjekte.add((lvListVermoegensobjekte.get(v)).getAttributeValue("nr")); 
                    System.out.println("Vermoegensobjektnummer: " + ((Element)lvListVermoegensobjekte.get(v)).getAttributeValue("nr"));
                }
                lvSicherheit.setVermoegensobjekte(lvListeVermoegensobjekte);
                 
                // Konto-Zuordung
                Element lvNodeKonten = (Element)lvNodeSicherheit.getChildren("Konto-Zuordnung").get(0); 
                List<Element> lvListKonten = lvNodeKonten.getChildren("Konto");
                System.out.println("Anzahl gefundener Konten: " + lvListKonten.size());
                LinkedList<OSPKonto> lvListeKonten = new LinkedList<OSPKonto>();
                Element lvNodeKonto;
                for (int k = 0; k < lvListKonten.size(); k++)
                {
                    lvNodeKonto = lvListKonten.get(k);
                    lvListeKonten.add(new OSPKonto(lvNodeKonto.getAttributeValue("OSPNR"), lvNodeKonto.getAttributeValue("DarKaNr"),
                                                 lvNodeKonto.getAttributeValue("Zuweisungsbetrag"), lvNodeKonto.getAttributeValue("Deckungsstockbetrag"), 
                                                 lvNodeKonto.getAttributeValue("Waehrung"))); 
                    System.out.println("OSPNr: " + lvNodeKonto.getAttributeValue("OSPNR") + " DarKaNr: " + lvNodeKonto.getAttributeValue("DarKaNr"));
                }
                lvSicherheit.setKonten(lvListeKonten);
                
                //sicherheit.setRang(nodeSicherheit.getChildText("Rang"));
                //sicherheit.setNominalwert(nodeSicherheit.getChildText("Nominalwert"));
                //sicherheit.setWaehrung(nodeSicherheit.getChildText("Waehrung"));
                //sicherheit.setArt(nodeSicherheit.getChildText("Sicherheitenart"));
                lvDatenInst.addListeSicherheiten(lvSicherheit);
            } 
           
            // Vermoegensobjekt
            List<Element> lvListVermoegensobjekt = lvNodeOSPInstitut.getChildren("Vermoegensobjekt");
            pvLogger.info("Anzahl gefundener Vermoegensobjekte: " + lvListVermoegensobjekt.size());

            Element lvNodeVermoegensobjekt;
            for (int l = 0; l < lvListVermoegensobjekt.size(); l++)
            {
                lvNodeVermoegensobjekt = lvListVermoegensobjekt.get(l);
                OSPVermoegensobjekt lvVermoegensobjekt = new OSPVermoegensobjekt(lvNodeVermoegensobjekt.getAttributeValue("nr"));
                //Grundbuch-Zuordnung
                Element lvNodeGBZuord = (Element)lvNodeVermoegensobjekt.getChildren("Grundbuch-Zuordnung").get(0); 
                List<Element> listGB = lvNodeGBZuord.getChildren("Grundbuch");
                System.out.println("Anzahl gefundener Grundbuecher: " + listGB.size());
                
                for (int k = 0; k < listGB.size(); k++)
                {
                    lvVermoegensobjekt.addGrundbuch((listGB.get(k)).getAttributeValue("nr"));
                    System.out.println("GB-Nr: " + (listGB.get(k)).getAttributeValue("nr"));
                }
                lvVermoegensobjekt.setVerwendungsart(lvNodeVermoegensobjekt.getChildText("Verwendungsart"));
                lvVermoegensobjekt.setBeleihungswert(lvNodeVermoegensobjekt.getChildText("Beleihungswert"));
                lvVermoegensobjekt.setNominalwert(lvNodeVermoegensobjekt.getChildText("Nominalwert"));
                lvVermoegensobjekt.setWaehrung(lvNodeVermoegensobjekt.getChildText("Waehrung"));
                lvVermoegensobjekt.setStrasse(lvNodeVermoegensobjekt.getChildText("Strasse"));
                lvVermoegensobjekt.setPlz(lvNodeVermoegensobjekt.getChildText("Plz"));
                lvVermoegensobjekt.setOrt(lvNodeVermoegensobjekt.getChildText("Ort"));
                lvVermoegensobjekt.setLand(lvNodeVermoegensobjekt.getChildText("Land"));
                lvVermoegensobjekt.setArt(lvNodeVermoegensobjekt.getChildText("Art"));
                lvVermoegensobjekt.setNutzungsart(lvNodeVermoegensobjekt.getChildText("Nutzungsart"));
                lvVermoegensobjekt.setBodenwert(lvNodeVermoegensobjekt.getChildText("Bodenwert"));
                lvVermoegensobjekt.setBauwert(lvNodeVermoegensobjekt.getChildText("Bauwert"));
                lvVermoegensobjekt.setErtragswert(lvNodeVermoegensobjekt.getChildText("Ertragswert"));
                lvVermoegensobjekt.setBaujahr(lvNodeVermoegensobjekt.getChildText("Baujahr"));
                lvDatenInst.addListeVermoegensobjekte(lvVermoegensobjekt);                    
            }
                
            // Grundbuch
            List<Element> lvListGrundbuecher = lvNodeOSPInstitut.getChildren("Grundbuch");
            pvLogger.info("Anzahl gefundener Grundb�cher: " + lvListGrundbuecher.size());
                
            Element lvNodeGrundbuch;
            for (int g = 0; g < lvListGrundbuecher.size(); g++)
            {
                lvNodeGrundbuch = lvListGrundbuecher.get(g);
                OSPGrundbuch lvGrundbuch = new OSPGrundbuch(lvNodeGrundbuch.getAttributeValue("nr"));
                lvGrundbuch.setAmt(lvNodeGrundbuch.getChildText("Grundbuchamt"));
                lvGrundbuch.setBezirk(lvNodeGrundbuch.getChildText("Grundbuchbezirk"));
                lvGrundbuch.setBand(lvNodeGrundbuch.getChildText("Band"));
                lvGrundbuch.setBlatt(lvNodeGrundbuch.getChildText("Blatt"));
                lvGrundbuch.setHeft(lvNodeGrundbuch.getChildText("Grundbuchheft"));
                lvGrundbuch.setArt(lvNodeGrundbuch.getChildText("Grundbuchart"));
                lvGrundbuch.setAbteilung(lvNodeGrundbuch.getChildText("Abteilung"));
                lvGrundbuch.setAbteilungNr(lvNodeGrundbuch.getChildText("AbteilungNr"));
                lvGrundbuch.setObjID(lvNodeGrundbuch.getChildText("ObjID"));
                lvGrundbuch.setSirID(lvNodeGrundbuch.getChildText("SirID"));
                lvDatenInst.addListeGrundbuecher(lvGrundbuch);
            }
                
            //logger.info(datenInst.toString());
        }
        
        return true;
    }

    /**
     * @param pvZielDarlehen 
     * @return
     */
    public StringBuffer importSicherheitObjekt2TXSKreditSicherheit(Darlehen pvZielDarlehen) 
    {
        StringBuffer lvHelpString = new StringBuffer();
        
        TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
        TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
        TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
        TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
        TXSVerzeichnisVBlatt lvVevb = new TXSVerzeichnisVBlatt();
        TXSVerzeichnisblattDaten lvVbdaten = new TXSVerzeichnisblattDaten();
        //TXSVerzeichnisBestandsverz vebv = new TXSVerzeichnisBestandsverz();
        //TXSBestandsverzDaten bvdaten = new TXSBestandsverzDaten();
        TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
        TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();
        //TXSSicherheitPerson shperson = new TXSSicherheitPerson();

        //System.out.println("CT - CT: " + pvZielDarlehen.getKundennummer());
        OSPInstitutDaten lvInstitut = ivListeOSPInstitute.getOSPInstitut(pvZielDarlehen.getKundennummer());
        if (lvInstitut != null)
        {
            System.out.println(lvInstitut.getRegKz() + lvInstitut.getOSPInst());
            System.out.println("Size - Sicherheiten: " + lvInstitut.getListeSicherheiten().size());
            for (int x = 0; x < lvInstitut.getListeSicherheiten().size(); x++)
            {
                OSPSicherheit lvSicherheit = lvInstitut.getListeSicherheiten().get(x);
                System.out.println(lvSicherheit.toString());
                for (int y = 0; y < lvSicherheit.getKonten().size(); y++)
                {
                    OSPKonto lvKonto = lvSicherheit.getKonten().get(y);
                    System.out.println(lvKonto.getDarKaNr() + " == " + pvZielDarlehen.getKontonummer());
                    if (lvKonto.getDarKaNr().equals(pvZielDarlehen.getKontonummer()))
                    {
                        OSPGrundbuch lvGb = null;
                        OSPVermoegensobjekt lvVermoegnsObj = null;
         
                        //ObjektZuweisungsbetrag ozw = ozwListe.getObjektZuweisungsbetrag(sicherheit.getObjektnummer());
                        //if (ozw != null)
                        //{
                        //  sicherheit.setVerfuegungsbetrag(ozw.getZuweisungsbetrag().toString());
                        //}
                    
                        // TXSKreditSicherheit
                        if (lvKredsh.importDarlehen(pvZielDarlehen, lvKonto, lvSicherheit, lvInstitut.getRegKz() + lvInstitut.getOSPInst(), ivLogger))
                        {
                            lvKredsh.setOrg(lvInstitut.getRegKz() + lvInstitut.getOSPInst());
                            
                            lvHelpString.append(lvKredsh.printTXSTransaktionStart());
                            lvHelpString.append(lvKredsh.printTXSTransaktionDaten());
                            
                            // TXSSicherheitDaten
                            lvShdaten.importDarlehen(pvZielDarlehen, lvSicherheit);
                            lvHelpString.append(lvShdaten.printTXSTransaktionStart());
                            lvHelpString.append(lvShdaten.printTXSTransaktionDaten());
                            lvHelpString.append(lvShdaten.printTXSTransaktionEnde());
                            
                            for (int z = 0; z < lvSicherheit.getVermoegensobjekte().size(); z++)
                            {
                                lvVermoegnsObj = lvInstitut.getVermoegensobjekt(lvSicherheit.getVermoegensobjekte().get(z));
                                if (lvVermoegnsObj != null)
                                {
                                    for (int i = 0; i < lvVermoegnsObj.getGrundbuecher().size(); i++)
                                    {
                                        lvGb = lvInstitut.getGrundbuch(lvVermoegnsObj.getGrundbuecher().get(i));
                                        if (lvGb.getSirID().equals(lvSicherheit.getNr()))
                                        {
                                          // TXSSicherheitVerzeichnis
                                          lvShve.importDarlehen(pvZielDarlehen, lvGb, lvInstitut.getRegKz() + lvInstitut.getOSPInst());
                                          lvShve.setOrg(lvInstitut.getRegKz() + lvInstitut.getOSPInst());
                                          lvHelpString.append(lvShve.printTXSTransaktionStart());
                                          lvHelpString.append(lvShve.printTXSTransaktionDaten());
                                        
                                          // TXSVerzeichnisDaten
                                          lvVedaten.importDarlehen(pvZielDarlehen, lvKonto, lvGb, lvSicherheit.getRang());
                                          lvHelpString.append(lvVedaten.printTXSTransaktionStart());
                                          lvHelpString.append(lvVedaten.printTXSTransaktionDaten());
                                          lvHelpString.append(lvVedaten.printTXSTransaktionEnde());
                                        
                                          // TXSVerzeichnisVBlatt 
                                          lvVevb.importDarlehen(pvZielDarlehen, lvGb, lvInstitut.getRegKz() + lvInstitut.getOSPInst());
                                          lvVevb.setOrg(lvInstitut.getRegKz() + lvInstitut.getOSPInst());
                                          lvHelpString.append(lvVevb.printTXSTransaktionStart());
                                          lvHelpString.append(lvVevb.printTXSTransaktionDaten());
                            
                                          // TXSVerzeichnisblattDaten
                                          lvVbdaten.importDarlehen(lvGb);
                                          lvHelpString.append(lvVbdaten.printTXSTransaktionStart());
                                          lvHelpString.append(lvVbdaten.printTXSTransaktionDaten());
                                          lvHelpString.append(lvVbdaten.printTXSTransaktionEnde());
                            
                                          lvHelpString.append(lvVevb.printTXSTransaktionEnde());
            
                                          // TXSVerzeichnisBestandsverz
                                          // CT 15.02.2012 - Entfernt
                                          //vebv.importDarlehen(zielDarlehen, sicherheit);
                                          //helpString.append(vebv.printTXSVerzeichnisBestandsverzStart());
                                          //helpString.append(vebv.printTXSVerzeichnisBestandsverz());
                            
                                          // TXSBestandsverzDaten
                                          // Aus Darlehen werden keine BestandsverzDaten geliefert - CT 10.01.2012
                                          //bvdaten.importDarlehen();
                                          //helpString = helpString + bvdaten.printTXSBestandsverzDatenStart();
                                          //helpString = helpString + bvdaten.printTXSBestandsverzDaten();
                                          //helpString = helpString + bvdaten.printTXSBestandsverzDatenEnde();
                            
                                          //helpString.append(vebv.printTXSVerzeichnisBestandsverzEnde());
                        
                                          // TXSVerzeichnisPfandobjekt
                                          lvVepo.importDarlehen(pvZielDarlehen, lvVermoegnsObj, lvInstitut.getRegKz() + lvInstitut.getOSPInst());
                                          lvVepo.setOrg(lvInstitut.getRegKz() + lvInstitut.getOSPInst());
                                          lvHelpString.append(lvVepo.printTXSTransaktionStart());
                                          lvHelpString.append(lvVepo.printTXSTransaktionDaten());
            
                                          // TXSPfandobjektDaten
                                          lvPodaten.importDarlehen(pvZielDarlehen, lvVermoegnsObj);
                                          lvHelpString.append(lvPodaten.printTXSTransaktionStart());
                                          lvHelpString.append(lvPodaten.printTXSTransaktionDaten());
                                          lvHelpString.append(lvPodaten.printTXSTransaktionEnde());
            
                                          lvHelpString.append(lvVepo.printTXSTransaktionEnde());

                                          lvHelpString.append(lvShve.printTXSTransaktionEnde());  
                                        }
                                    }
                                }
                            }
                           
                            lvHelpString.append(lvKredsh.printTXSTransaktionEnde());
                        }
                    }
                }
            }
        }
        return lvHelpString;
    }  
}
