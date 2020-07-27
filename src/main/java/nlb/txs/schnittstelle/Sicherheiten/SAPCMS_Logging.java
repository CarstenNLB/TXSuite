package nlb.txs.schnittstelle.Sicherheiten;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import nlb.txs.schnittstelle.Sicherheiten.Entities.Last;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Schiff;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Sicherheitenvereinbarung;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Sicherungsumfang;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSPfandobjektDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitVerzeichnis;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisPfandobjekt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisVBlatt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisblattDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class SAPCMS_Logging 
{
    // Wird nur zu Testzwecken benoetigt!
    private static Logger LOGGER_SAPCMS = Logger.getLogger("TXSSAPCMSLogger");  

    /**
     * Sicherheiten-Daten
     */
    private SicherheitenDaten ivSicherheitenDaten;
    
   /**
     * Startroutine (main) SAPCMS_Logging
     * @param args
     */
    public static void main(String args[])
    { 
        if (!args[args.length - 1].endsWith(".ini"))
        {
           System.out.println("Starten:");
           System.out.println("Sicherheiten <ini-Datei>");
           System.exit(1);
        }
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
    	
        String lvLoggingXML = lvReader.getPropertyString("Sicherheiten", "log4jconfig", "Fehler");
        if (lvLoggingXML.equals("Fehler"))
        {
        	System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
        }
        else
        {
            DOMConfigurator.configure(lvLoggingXML);
        }     
        
        String lvImportVerzeichnisSAPCMS = lvReader.getPropertyString("Sicherheiten", "ImportVerzeichnis", "Fehler");
        if (lvImportVerzeichnisSAPCMS.equals("Fehler"))
        {
            LOGGER_SAPCMS.error("Kein Sicherheiten Import-Verzeichnis in der ini-Datei...");
            System.exit(1);
        }

        String lvSapcmsDatei = lvReader.getPropertyString("Sicherheiten", "Sicherheiten-Datei", "Fehler");
        if (lvSapcmsDatei.equals("Fehler"))
        {
            LOGGER_SAPCMS.error("Kein Sicherheiten-Dateiname in der ini-Datei...");
            System.exit(1);
        }

        new SAPCMS_Logging(lvImportVerzeichnisSAPCMS + "\\" + lvSapcmsDatei);
    }
    
    /**
     * Konstruktor
     * @param pvDateiname Name der SAP CMS-Datei
     */
    public SAPCMS_Logging(String pvDateiname)
    {
    	  ivSicherheitenDaten = new SicherheitenDaten(pvDateiname, SicherheitenDaten.SAPCMS, LOGGER_SAPCMS);
        Collection<LinkedList<Sicherungsumfang>> lvCollListe = ivSicherheitenDaten.getListeSicherungsumfang().values();
        Iterator<LinkedList<Sicherungsumfang>> lvIterListe = lvCollListe.iterator();
        while (lvIterListe.hasNext())
        {
        	
        	LinkedList<Sicherungsumfang> lvHelpListe = lvIterListe.next();
        	if (lvHelpListe != null)
        	{
        		for (int x = 0; x < lvHelpListe.size(); x++)
        		{
        			Sicherungsumfang lvShum = lvHelpListe.get(x);
        			LOGGER_SAPCMS.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());

        			loggeKreditSicherheitSchiff(lvShum); //, pvKontonummer, "ADARLREFI", pvListeRefiDeepSea));
        		} 
        	}
        }

        System.exit(0);
    }

    /**
     * Loggt eine KreditSicherheit Schiff
     * @param pvShum
     */
    private void loggeKreditSicherheitSchiff(Sicherungsumfang pvShum) //, String pvKontonummer, String pvQuellsystem)
    {
      try
      {
        //System.out.println("import2TXSKreditSicherheitSchiffe");
        TXSKreditSicherheit lvKredsh = null;
        TXSSicherheitDaten lvShdaten = null;
        TXSSicherheitVerzeichnis lvShve = null;
        TXSVerzeichnisDaten lvVedaten = null;
        TXSVerzeichnisVBlatt lvVevb = null;
        TXSVerzeichnisblattDaten lvVbdaten = null;
        TXSVerzeichnisPfandobjekt lvVepo = null;
        TXSPfandobjektDaten lvPodaten = null;
        
        Sicherheitenvereinbarung lvSicherheitenvereinbarung = ivSicherheitenDaten.getListeSicherheitenvereinbarung().get(pvShum.getSicherheitenvereinbarungId());
             
        BigDecimal lvZuwBetrag = StringKonverter.convertString2BigDecimal(pvShum.getZuweisungsbetrag()); 
        BigDecimal lvVerfBetrag = StringKonverter.convertString2BigDecimal(lvSicherheitenvereinbarung.getVerfuegungsbetrag()); 
                                  
         // Last suchen
        Last lvLast = null;
        Collection<Last> lvCollectionLast = ivSicherheitenDaten.getListeLast().values();
        Iterator<Last> lvIteratorLast = lvCollectionLast.iterator();
        while (lvIteratorLast.hasNext())
        {
        	StringBuffer lvHelpCSV = new StringBuffer();
            Last lvHelpLast = lvIteratorLast.next();
            lvLast = null;
            if (lvHelpLast.getSicherheitenvereinbarungId().equals(lvSicherheitenvereinbarung.getId()))
            {
                lvLast = lvHelpLast;
            }
             
             if (lvLast != null)
             {
                 // Schiff suchen
                 Schiff lvSchiff = null;
                 Collection<Schiff> lvCollectionSchiff = ivSicherheitenDaten.getListeSchiff().values();
                 Iterator<Schiff> lvIteratorSchiff = lvCollectionSchiff.iterator();
                 while (lvIteratorSchiff.hasNext())
                 {
                     Schiff lvHelpSchiff = lvIteratorSchiff.next();
                     if (lvHelpSchiff.getId().equals(lvLast.getImmobilieId()))
                     {
                         lvSchiff = lvHelpSchiff;
                     }
                 }
             
                 // Wenn Deckungskennzeichen nicht 'V' oder 'S' ist, dann leeren StringBuffer zurueckliefern
                 /* if (lvSchiff != null)
                 {
                     if (!lvSchiff.getStatusDeckung().equals("V") && !lvSchiff.getStatusDeckung().equals("S"))
                     {   
                           continue;
                     }
                 } */
               
                 lvKredsh = new TXSKreditSicherheit();
                 lvKredsh.setKey(lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
       
                 lvKredsh.setOrg(ValueMapping.changeMandant(ivSicherheitenDaten.getVorlaufsatz().getInstitut()));
                 //lvKredsh.setQuelle(pvQuellsystem);
                 lvKredsh.setZuwbetrag(lvZuwBetrag.toString());
                 lvKredsh.setWhrg(pvShum.getZuweisungsbetragWaehrung());
                 // Defaultmaessig auf 'Ja' (1)
                 lvKredsh.setHauptsh("1");
                 lvHelpCSV.append(lvZuwBetrag.toString() + ";" + pvShum.getZuweisungsbetragWaehrung() + ";" + pvShum.getWert().trim().replace(",", ".") + ";" + pvShum.getGroesse() + ";" + MappingKunde.extendKundennummer(pvShum.getGeschaeftspartnerId(), LOGGER_SAPCMS) + ";" + pvShum.getGeschaeftsparterfunktion() + ";");
                 
                 lvShdaten = new TXSSicherheitDaten();
                 // Schiffshypothek
                 lvShdaten.setArt("56");
                 lvShdaten.setBez(String2XML.change2XML(lvSchiff.getSchiffsname()));
                 // Defaultmaessig auf 'Ja' (1)
                 lvShdaten.setGepr("1");
                 if (ivSicherheitenDaten.getVorlaufsatz().getInstitut().equals("004"))
                 {
                	lvShdaten.setGepr(""); 
                 }
                 
                 lvShdaten.setNbetrag(lvSicherheitenvereinbarung.getNominalwert());
                 lvShdaten.setVbetrag(lvVerfBetrag.toString());
                 lvShdaten.setWhrg(lvSicherheitenvereinbarung.getVerfuegungsbetragWaehrung());
                 lvHelpCSV.append(lvSicherheitenvereinbarung.getNominalwert() + ";" + lvSicherheitenvereinbarung.getNominalwertWaehrung() + ";" + lvVerfBetrag.toString() + ";" + lvSicherheitenvereinbarung.getVerfuegungsbetragWaehrung() + ";");
       
                 lvShve = new TXSSicherheitVerzeichnis();
                 lvShve.setVenr(lvSchiff.getSchiffId() + "_" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                 lvShve.setOrg(ValueMapping.changeMandant(ivSicherheitenDaten.getVorlaufsatz().getInstitut()));
                 //lvShve.setQuelle(pvQuellsystem);
        
                 lvVedaten = new TXSVerzeichnisDaten();
                 lvVbdaten = new TXSVerzeichnisblattDaten();
                 lvVevb = new TXSVerzeichnisVBlatt();

                 // ZuweisungsbetragPO
                 //if (ivSAPCMS.getVorlaufsatz().getInstitut().equals("009"))
                 //{
                	 lvVedaten.setBetrag(lvLast.getVerfuegungsbetrag());
                	 lvVedaten.setWhrg(lvLast.getVerfuegungsbetragWaehrung());
                	 lvHelpCSV.append(lvLast.getVerfuegungsbetrag() + ";" + lvLast.getVerfuegungsbetragWaehrung() + ";");
                 //}
                 //else
                 //{
                	 // min {Konsortialanteil in Prozent * 60% * aktuelle Beleihungswert; Verfuegungsbetrag}
       
                	 //BigDecimal lvHelpVerf = new BigDecimal(lvSchiff.getVerfuegungsbetrag());
                	 //BigDecimal lvHelpBeleihAkt = new BigDecimal(lvSchiff.getBeleihungswertAkt());
                	 //System.out.println("Verfuegungsbetrag: " + lvSchiff.getVerfuegungsbetrag());
                	 //System.out.println("Beleihungswert: " + lvSchiff.getBeleihungswertAkt());
                	 //lvHelpKonsortialanteil = lvHelpVerf.divide(new BigDecimal(lvSchiff.getNennbetrag()), 9, RoundingMode.HALF_UP);
                	 //lvHelpBeleihAkt = lvHelpBeleihAkt.multiply(new BigDecimal("0.6")).multiply(lvHelpKonsortialanteil); // 60% Prozent * Konsortialanteil     
 
                	 //BigDecimal lvHelpVerfEUR = lvHelpVerf.divide(lvHelpWechselkursVerf, 9, RoundingMode.HALF_UP);	
                	 //BigDecimal lvHelpBeleihAktEUR = lvHelpBeleihAkt.divide(lvHelpWechselkursBeleih, 9, RoundingMode.HALF_UP);
        
                	 //System.out.println(lvHelpVerf.toString() + " --> EUR " + lvHelpVerfEUR.toString());
                	 //System.out.println(lvHelpBeleihAkt.toString() + " --> EUR " + lvHelpBeleihAktEUR.toString());
        
                	 //if (lvHelpVerfEUR.doubleValue() < lvHelpBeleihAktEUR.doubleValue()) 
                	 //{
                	 //    lvVedaten.setBetrag(lvHelpVerf.toString());
                	 //    lvVedaten.setWhrg(lvSchiff.getWaehrungSicherheit());
                	 //}
                	 //else
                	 //{
                	 //    lvVedaten.setBetrag(lvHelpBeleihAkt.toString());
                	 //    lvVedaten.setWhrg(lvSchiff.getWaehrungBeleihungswertAkt());
                	 //}
                 //}
                  
                 if (lvSchiff.getRegisterLand().equals("DE"))
                 {   
                	 lvVedaten.setAbt("3");
                	 lvVedaten.setGbart("200");
                	 lvHelpCSV.append("3;200;");
                 }
                 else
                 {
                	 lvVedaten.setGbart("201");
                	 lvHelpCSV.append(";201;");
                 }
                 if (lvLast.getRegisternummer().equals("N.R."))
                 {
                     lvVedaten.setNrabt(lvLast.getRegisternummer().toLowerCase());  
                     lvHelpCSV.append(lvLast.getRegisternummer().toLowerCase() + ";");
                 }
                 else
                 {
                   lvVedaten.setNrabt(lvLast.getRegisternummer());    
                   lvHelpCSV.append(lvLast.getRegisternummer() + ";");
                 }
                 lvVedaten.setKat("2");
                 lvVedaten.setRang(lvLast.getRangfolge());
                 lvHelpCSV.append(lvLast.getRangfolge() + ";");

                 lvVevb.setVbnr(lvSchiff.getSchiffId() + "_" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                 lvVevb.setOrg(ValueMapping.changeMandant(ivSicherheitenDaten.getVorlaufsatz().getInstitut()));
                 //lvVevb.setQuelle(pvQuellsystem);
                 
                 lvVbdaten.setKat("2");
                 lvVbdaten.setGbvon(lvSchiff.getRegisterort());
                 lvHelpCSV.append(lvSchiff.getRegisterort() + ";");
                 if (!lvSchiff.getRegisterblatt().isEmpty())
                 {
                	 if (lvSchiff.getRegisterLand().equals("DE"))
                	 {
                		 lvVbdaten.setBlatt("Blatt " + lvSchiff.getRegisterblatt());    
                		 lvHelpCSV.append("Blatt " + lvSchiff.getRegisterblatt() + ";");
                	 }
                	 else
                	 {
                		 lvVbdaten.setBlatt(lvSchiff.getRegisterblatt());
                		 lvHelpCSV.append(lvSchiff.getRegisterblatt() + ";");
                	 }
                 }
                 else
                 {
                	 lvVbdaten.setBlatt("n.r.");
                	 lvHelpCSV.append("n.r.;");
                 }

                 lvVepo = new TXSVerzeichnisPfandobjekt();
                 lvVepo.setObjnr(lvSchiff.getSchiffId());
                 lvVepo.setOrg(ValueMapping.changeMandant(ivSicherheitenDaten.getVorlaufsatz().getInstitut()));
                 //lvVepo.setQuelle(pvQuellsystem);
        
                 lvPodaten = new TXSPfandobjektDaten();
                 lvPodaten.setBez(String2XML.change2XML(lvSchiff.getSchiffsname()));
                 lvPodaten.setImo(lvSchiff.getIMONummer());
                 lvPodaten.setKat("2");
                 lvPodaten.setWhrg(lvSchiff.getWaehrungBeleihungswertAktuell());
                 lvPodaten.setBwert(lvSchiff.getBeleihungswertAktuell());
                 lvPodaten.setBwertdat(DatumUtilities.changeDate(lvSchiff.getDatumBeleihungswertAktuell()));
                 lvPodaten.setLand(lvSchiff.getRegisterLand());
      
                 // Konsortialanteil
                 //lvPodaten.setKonsortialanteil(this.getGroesse());
                 lvPodaten.setBlhwertinit(lvSchiff.getBeleihungswertIndeckungnahme() + " " + lvSchiff.getWaehrungBeleihungswertIndeckungnahme());
                 lvPodaten.setSbrnr(lvSchiff.getSchiffstypen().substring(1));
                 
                 setzeSchiffsdaten(lvSchiff.getSchiffstypen().substring(1), lvPodaten);
        
                 //lvPodaten.setAusldat(DatumUtilities.changeDate(lvSchiff.getAuslieferungsdatumSoll()));
                 lvPodaten.setAusldat(DatumUtilities.changeDate(lvSchiff.getAblieferungsdatum())); 
                 lvPodaten.setFertstdat(DatumUtilities.changeDate(lvSchiff.getAblieferungsdatum()));
                 lvHelpCSV.append(lvSchiff.getSchiffId() + ";" + lvSchiff.getSchiffsname() + ";" + lvSchiff.getIMONummer() + ";" + lvSchiff.getWaehrungBeleihungswertAktuell() + ";" + lvSchiff.getBeleihungswertAktuell() + ";");
                 lvHelpCSV.append(lvSchiff.getDatumBeleihungswertAktuell() + ";" + lvSchiff.getRegisterLand() + ";" + lvSchiff.getBeleihungswertIndeckungnahme() + " " + lvSchiff.getWaehrungBeleihungswertIndeckungnahme() + ";" + lvSchiff.getSchiffstypen().substring(1) + ";");
                 lvHelpCSV.append(lvPodaten.getOgrp() + ";" + lvPodaten.getNart() + ";" + lvPodaten.getErtragsf());
                 LOGGER_SAPCMS.info("BB;BB;Sicherheiten;" + pvShum.getGeschaeftswertId() + ";" + lvSchiff.getSchiffId() + ";" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ";" + lvHelpCSV.toString());
                
          }
        }
      }
      catch (Exception exp)
      {
    	  System.out.println("Auf zum n�chsten Datensatz...");
      }
    }
    
    /**
     * Setzt Schiffstyp, Objektgruppe/Schiffsart, Nutzungsart und Ertragsfaehigkeit
     * @param pvSchiffstyp
     * @param pvPodaten
     */
    private void setzeSchiffsdaten(String pvSchiffstyp, TXSPfandobjektDaten pvPodaten)
    { 
        // Schiffstyp
        pvPodaten.setStyp(pvSchiffstyp);
        
        // Objektgruppe - Schiffsart
        if (pvSchiffstyp.equals("50111") || pvSchiffstyp.equals("50121") ||
            pvSchiffstyp.equals("50122") || pvSchiffstyp.equals("50271") ||
            pvSchiffstyp.equals("50272") || pvSchiffstyp.equals("50273") ||
            pvSchiffstyp.equals("50274") || pvSchiffstyp.equals("50275") ||
            pvSchiffstyp.equals("50310") || pvSchiffstyp.equals("50320") ||
            pvSchiffstyp.equals("50410") || pvSchiffstyp.equals("50420") ||
            pvSchiffstyp.equals("50430"))
        {
            pvPodaten.setOgrp("102");
            
        }
        
        if (pvSchiffstyp.equals("50221") || pvSchiffstyp.equals("50222") ||
            pvSchiffstyp.equals("50223") || pvSchiffstyp.equals("50224") ||
            pvSchiffstyp.equals("50225") || pvSchiffstyp.equals("50226") ||
            pvSchiffstyp.equals("50227"))
        {
            pvPodaten.setOgrp("101");
        }
        
        if (pvSchiffstyp.equals("50231") || pvSchiffstyp.equals("50232") ||
            pvSchiffstyp.equals("50233") || pvSchiffstyp.equals("50234"))
        {
            pvPodaten.setOgrp("100");
        }
        
        if (pvSchiffstyp.equals("50255") || pvSchiffstyp.equals("50256") ||
            pvSchiffstyp.equals("50241") || pvSchiffstyp.equals("50242") ||
            pvSchiffstyp.equals("50243") || pvSchiffstyp.equals("50244") ||
            pvSchiffstyp.equals("50245") || pvSchiffstyp.equals("50251") ||
            pvSchiffstyp.equals("50252") || pvSchiffstyp.equals("50253") ||
            pvSchiffstyp.equals("50254"))
        {
            pvPodaten.setOgrp("103");
        }
        
        // Multipurpose
        if (pvSchiffstyp.equals("50261") || pvSchiffstyp.equals("50262") ||
            pvSchiffstyp.equals("50263") || pvSchiffstyp.equals("50264") ||
            pvSchiffstyp.equals("50265") || pvSchiffstyp.equals("50266") ||
            pvSchiffstyp.equals("50267") || pvSchiffstyp.equals("50268") ||
            pvSchiffstyp.equals("50269"))
        {
            pvPodaten.setOgrp("104");
        }
                    
        // Nutzungsart
        if (pvSchiffstyp.equals("50310") || pvSchiffstyp.equals("50320") ||
            pvSchiffstyp.equals("50410") || pvSchiffstyp.equals("50420") ||
            pvSchiffstyp.equals("50430"))
        {
            pvPodaten.setNart("9");
        }
        else
        {
            pvPodaten.setNart("10");
        }
        
        // Ertragsfaehigkeit
        pvPodaten.setErtragsf("SCHIFF");
    }

}
