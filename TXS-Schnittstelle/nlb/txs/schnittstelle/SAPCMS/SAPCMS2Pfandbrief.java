package nlb.txs.schnittstelle.SAPCMS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetrag;
import nlb.txs.schnittstelle.SAPCMS.Entities.Beleihungssatz;
import nlb.txs.schnittstelle.SAPCMS.Entities.Flugzeug;
import nlb.txs.schnittstelle.SAPCMS.Entities.Geschaeftspartner;
import nlb.txs.schnittstelle.SAPCMS.Entities.Grundbuchblatt;
import nlb.txs.schnittstelle.SAPCMS.Entities.Grundbuchverweis;
import nlb.txs.schnittstelle.SAPCMS.Entities.Grundstueck;
import nlb.txs.schnittstelle.SAPCMS.Entities.Immobilie;
import nlb.txs.schnittstelle.SAPCMS.Entities.Last;
import nlb.txs.schnittstelle.SAPCMS.Entities.Schiff;
import nlb.txs.schnittstelle.SAPCMS.Entities.Sicherheitenvereinbarung;
import nlb.txs.schnittstelle.SAPCMS.Entities.Sicherungsumfang;
import nlb.txs.schnittstelle.Transaktion.TXSBestandsverzDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSPfandobjektDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitPerson;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitVerzeichnis;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitZuSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSTriebwerkDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisBestandsverz;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisPfandobjekt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisVBlatt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisblattDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;

public class SAPCMS2Pfandbrief 
{
	/**
	 * Ueber diese Variable kann auf die SAPCMS-Daten zugegriffen werden
	 */
	private SAPCMS_Neu ivSAPCMS;
	
	/**
	 * log4j-Logger
	 */
	private Logger ivLogger;
	
	/**
	 * Konstruktor
	 */
	public SAPCMS2Pfandbrief(SAPCMS_Neu pvSAPCMS)
	{
		this.ivSAPCMS = pvSAPCMS;
		this.ivLogger = pvSAPCMS.getLogger();
	}
	
    /**
     * Importiert die Kredit-Sicherheit
     * @param pvShum
     * @param pvKontonummer 
     * @param pvPassivkontonummer 
     * @param pvKundennummer 
     * @param pvKredittyp 
     * @param pvBuergschaftprozent 
     * @param pvQuellsystem
     * @return 
     */
    public StringBuffer import2TXSKreditSicherheit(Sicherungsumfang pvShum, String pvKontonummer, String pvPassivkontonummer, String pvKundennummer, String pvKredittyp, String pvBuergschaftprozent, String pvQuellsystem) 
    {
        StringBuffer lvHelpString = new StringBuffer(); 
        ivLogger.info("import2TXSKreditSicherheit");
        TXSKreditSicherheit lvKredsh = null;
        TXSSicherheit lvSh = null;
        TXSSicherheitDaten lvShdaten = null;
        TXSSicherheitVerzeichnis lvShve = null;
        TXSVerzeichnisDaten lvVedaten = null;
        TXSVerzeichnisVBlatt lvVevb = null;
        TXSVerzeichnisblattDaten lvVbdaten = null;
        TXSVerzeichnisBestandsverz lvVebv = null;
        TXSBestandsverzDaten lvBvdaten = null;
        TXSVerzeichnisPfandobjekt lvVepo = null;
        TXSPfandobjektDaten lvPodaten = null;
        
        Sicherheitenvereinbarung lvSicherheitenvereinbarung = ivSAPCMS.getListeSicherheitenvereinbarung().get(pvShum.getSicherheitenvereinbarungId());
        
        BigDecimal lvBtrDivHd = new BigDecimal("0.01");
        BigDecimal lvBuergeFakt = new BigDecimal("1.0");
             
        BigDecimal lvZuwBetrag = StringKonverter.convertString2BigDecimal(pvShum.getZuweisungsbetrag()); 
        BigDecimal lvVerfBetrag = StringKonverter.convertString2BigDecimal(lvSicherheitenvereinbarung.getVerfuegungsbetrag()); 
                                  
        BigDecimal lvVerfBetragBu = new BigDecimal("0.0");
        BigDecimal lvVerfBetragRe = new BigDecimal("0.0");
        BigDecimal lvZuwBetragBu = new BigDecimal("0.0");
        BigDecimal lvZuwBetragRe = new BigDecimal("0.0");
        if (pvKredittyp.equals("1"))
        { // ohne Buerge .. alles 
            lvVerfBetragRe = lvVerfBetrag;
            lvZuwBetragRe  = lvZuwBetrag;
        } // ohne Buerge .. alles 
        if (pvKredittyp.equals("2"))
        { // mit Buerge .. anteilig 
            if (StringKonverter.convertString2Double(pvBuergschaftprozent) == 0.0)
            { // nichts da
                lvVerfBetragRe = lvVerfBetrag;
                lvZuwBetragRe  = lvZuwBetrag;
            } // nichts da 
            else
            { // etwas da
                lvBuergeFakt = lvBtrDivHd.multiply(StringKonverter.convertString2BigDecimal(pvBuergschaftprozent));
                 lvVerfBetragBu = lvVerfBetrag.multiply(lvBuergeFakt);
                 lvVerfBetragRe = lvVerfBetrag.subtract(lvVerfBetragBu);
                 lvZuwBetragBu  = lvZuwBetrag.multiply(lvBuergeFakt);
                 lvZuwBetragRe  = lvZuwBetrag.subtract(lvZuwBetragBu);
            } // etwas da 
        } // mit Buerge .. anteilig
                          
        // Last suchen
        Last lvLast = null;
        Collection<Last> lvCollectionLast = ivSAPCMS.getListeLast().values();
        Iterator<Last> lvIteratorLast = lvCollectionLast.iterator();
        while (lvIteratorLast.hasNext())
        { 
            Last lvHelpLast = lvIteratorLast.next();
            lvLast = null;
            if (lvHelpLast.getSicherheitenvereinbarungId().equals(lvSicherheitenvereinbarung.getId()))
            {
                lvLast = lvHelpLast;
            }
             
             if (lvLast != null)
             {
                 // Immobilie suchen
                 Immobilie lvImmobilie = null;
                 Collection<Immobilie> lvCollectionImmobilie = ivSAPCMS.getListeImmobilie().values();
                 Iterator<Immobilie> lvIteratorImmobilie = lvCollectionImmobilie.iterator();
                 while (lvIteratorImmobilie.hasNext())
                 {
                     Immobilie lvHelpImmobilie = lvIteratorImmobilie.next();
                     if (lvHelpImmobilie.getId().equals(lvLast.getImmobilieId()))
                     {
                         lvImmobilie = lvHelpImmobilie;
                     }
                 }
             
                 // Pfandbrief: Wenn Deckungskennzeichen nicht 'D' oder 'F' ist, dann leeren StringBuffer zurueckliefern
                 if (lvImmobilie != null)
                 {
                	 //System.out.println("Immo-Deckungskennzeichen - " + lvImmobilie.getImmobilienobjektId() + ": " + lvImmobilie.getDeckungskennzeichen());  
                	 if (!lvImmobilie.getDeckungskennzeichen().equals("F") && !lvImmobilie.getDeckungskennzeichen().equals("D"))
                     {   
                		 ivLogger.info("Deckungskennzeichen an der Immobilie ungleich 'F' oder 'D': " + lvImmobilie.getDeckungskennzeichen());
                		 continue;
                     }
                 }
                 else
                 {
                	 ivLogger.info("Keine Immobilie vorhanden!");
                	 continue;
                 }
                 
                 ivLogger.info("TXSKreditSicherheit - Start");
             
                 // TXSKreditSicherheit
                 lvKredsh = new TXSKreditSicherheit();
                 lvKredsh.setKey(lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                 lvKredsh.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                 lvKredsh.setQuelle(pvQuellsystem);
                 //System.out.println("Kredittyp: " + pvKredittyp);
                 //System.out.println("lvZuwBetragBu: " + lvZuwBetragBu.toString());
                 //System.out.println("lvZuwBetragRe: " + lvZuwBetragRe.toString());
                 if (pvKredittyp.equals("2"))
                 {
                	 lvKredsh.setZuwbetrag(lvZuwBetragBu.toString());             
                 }
                 else
                 {
                	 lvKredsh.setZuwbetrag(lvZuwBetragRe.toString());            
                 }
                 lvKredsh.setWhrg(pvShum.getZuweisungsbetragWaehrung());
                 
                 // TXSSicherheitDaten
                 lvShdaten = new TXSSicherheitDaten();

                 lvShdaten.setArt(ValueMapping.mapSicherheitenArt(lvImmobilie.getLaenderschluessel(), lvSicherheitenvereinbarung.getSicherheitenvereinbarungsart(), lvSicherheitenvereinbarung.getGesamtgrundschuldKennzeichen()));
                 lvShdaten.setNbetrag(lvSicherheitenvereinbarung.getNominalwert());
                 // Test BLB - Defaultmaessig auf 'Ja' (1) - CT 04.03.2015
                 // Spaeter wieder rausnehmen
                 //lvShdaten.setGepr("1");
                 
                 if (pvKredittyp.equals("2"))
                 {
                     lvShdaten.setVbetrag(lvVerfBetragBu.toString());       
                 }
                 else
                 {
                     lvShdaten.setVbetrag(lvVerfBetragRe.toString());
                 }
                 lvShdaten.setWhrg(lvSicherheitenvereinbarung.getNominalwertWaehrung());

                 lvHelpString.append(lvKredsh.printTXSTransaktionStart());
                 lvHelpString.append(lvKredsh.printTXSTransaktionDaten());
                
                 lvHelpString.append(lvShdaten.printTXSTransaktionStart());
                 lvHelpString.append(lvShdaten.printTXSTransaktionDaten());
                 lvHelpString.append(lvShdaten.printTXSTransaktionEnde());
                                     
                 // Grundpfandr. Auslandsimmo -> Kein Grundbucheintrag
                 if (lvShdaten.getArt().equalsIgnoreCase("19"))
                 {
                     lvHelpString.append(importGrundpfandrechtAuslandsimmo(pvShum, lvSicherheitenvereinbarung, lvImmobilie, lvLast, lvBuergeFakt, pvQuellsystem));
                 }
             
                 // TXSSicherheitVerzeichnis
                 // Beleihungssatz suchen
                 //System.out.println("Size: " + pvSAPCMS.getListeBeleihungssatz().size());
                 HashMap<String, Grundstueck> lvHelpListeGrundstueck = new HashMap<String, Grundstueck>();
                 HashMap<String, Grundbuchblatt> lvHelpListeGrundbuchblatt = new HashMap<String, Grundbuchblatt>();
                 Beleihungssatz lvBeleihungssatz = null;
                 Collection<Beleihungssatz> lvCollectionBeleihungssatz = ivSAPCMS.getListeBeleihungssatz().values();
                 Iterator<Beleihungssatz> lvIteratorBeleihungssatz = lvCollectionBeleihungssatz.iterator();
                 while (lvIteratorBeleihungssatz.hasNext())
                 {
                     Beleihungssatz lvHelpBeleihungssatz = lvIteratorBeleihungssatz.next();
                     if (lvHelpBeleihungssatz.getObjektteilId().equals(lvImmobilie.getObjektteilId()))
                     {
                         lvBeleihungssatz = lvHelpBeleihungssatz;
                         Grundstueck lvGrundstueck = ivSAPCMS.getListeGrundstueck().get(lvBeleihungssatz.getGrundstueckId());
                         if (lvGrundstueck != null)
                         {
                           if (!lvHelpListeGrundstueck.containsKey(lvGrundstueck.getId()))
                           {
                             lvHelpListeGrundstueck.put(lvGrundstueck.getId(), lvGrundstueck);
                           }
                        
                           Grundbuchblatt lvGrundbuchblatt = ivSAPCMS.getListeGrundbuchblatt().get(lvGrundstueck.getGrundbuchblattId());
                           if (lvGrundbuchblatt != null)
                           {
                             if (!lvHelpListeGrundbuchblatt.containsKey(lvGrundbuchblatt.getId()))
                             {
                               lvHelpListeGrundbuchblatt.put(lvGrundbuchblatt.getId(), lvGrundbuchblatt);
                             }
                           }
                         }
                     }
                 }            
                
                 // Ueberpruefung, ob Last-Referenz in der Liste der Grundbuchverweise
                 boolean lvVerweisVorhanden = false;
                 Collection<Grundbuchverweis> lvCollectionGrundbuchverweis = ivSAPCMS.getListeGrundbuchverweis().values();
                 Iterator<Grundbuchverweis> lvIteratorGrundbuchverweis = lvCollectionGrundbuchverweis.iterator();
                 while (lvIteratorGrundbuchverweis.hasNext())
                 {
                     Grundbuchverweis lvHelpGrundbuchverweis = lvIteratorGrundbuchverweis.next();
                     if (lvHelpGrundbuchverweis.getLastId().equals(lvLast.getId()))
                     {
                         lvVerweisVorhanden = true;
                     }
                 }

                 Collection<Grundbuchblatt> lvCollectionGrundbuchblatt = lvHelpListeGrundbuchblatt.values();
                 Iterator<Grundbuchblatt> lvIteratorGrundbuchblatt = lvCollectionGrundbuchblatt.iterator();
                 boolean lvAlleKeinenGrundbuchverweis = true;
                 while (lvIteratorGrundbuchblatt.hasNext())
                 {
                     //StringBuffer lvHelpStringGrundbuchblatt = new StringBuffer();
                     Grundbuchblatt lvGrundbuchblatt = lvIteratorGrundbuchblatt.next();
                     //System.out.println("Last: " + lvLast.getId() + " Grundbuchblatt: " + lvGrundbuchblatt.getId());

                     // 
                     lvCollectionGrundbuchverweis = ivSAPCMS.getListeGrundbuchverweis().values();
                     lvIteratorGrundbuchverweis = lvCollectionGrundbuchverweis.iterator();
                     while (lvIteratorGrundbuchverweis.hasNext())
                     { 
                         Grundbuchverweis lvHelpGrundbuchverweis = lvIteratorGrundbuchverweis.next();
                         if (lvHelpGrundbuchverweis.getLastId().equals(lvLast.getId()) &&
                             lvHelpGrundbuchverweis.getGrundbuchblattId().equals(lvGrundbuchblatt.getId()))
                         {
                             //System.out.println("Grundbuchverweis gefunden: Last " + lvLast.getId() + " Grundbuchblatt " + lvGrundbuchblatt.getId());
                             lvAlleKeinenGrundbuchverweis = false;
                         }
                     }              
                 }
                 //System.out.println("lvAlleKeinenGrundbuchverweis: " + lvAlleKeinenGrundbuchverweis);
                 
                 // Bis jetzt verteilte Summe ZuweisungsbetragPO
                 BigDecimal lvSummeZuweisungsbetragPO = new BigDecimal("0.0");
                 // Anzahl erledigter Grundbuchblaetter
                 int lvAnzahlErledigterGrundbuchblaetter = 0;
                 int lvAnzahlGrundbuchblaetter = 0;
                 // CT 04.08.2016
                 ArrayList<Grundbuchblatt> lvHelpListeGrundbuchblattVergleich = new ArrayList<Grundbuchblatt>();
                 lvCollectionGrundbuchblatt = lvHelpListeGrundbuchblatt.values();
                 lvIteratorGrundbuchblatt = lvCollectionGrundbuchblatt.iterator();

                 while (lvIteratorGrundbuchblatt.hasNext())
                 {
                     Grundbuchblatt lvGrundbuchblatt = lvIteratorGrundbuchblatt.next();
                     lvHelpListeGrundbuchblattVergleich.add(lvGrundbuchblatt);
                     //System.out.println("Grundbuchblatt: " + lvGrundbuchblatt.toString());
                     // Grundbuchblatt ueber Grundbuchverweis ermitteln
                     // Grundbuchverweis per Last-ID suchen
                     Grundbuchverweis lvGrundbuchverweis = null;
                     lvCollectionGrundbuchverweis = ivSAPCMS.getListeGrundbuchverweis().values();
                     lvIteratorGrundbuchverweis = lvCollectionGrundbuchverweis.iterator();
                     while (lvIteratorGrundbuchverweis.hasNext())
                     {
                         Grundbuchverweis lvHelpGrundbuchverweis = lvIteratorGrundbuchverweis.next();
                         if (lvHelpGrundbuchverweis.getLastId().equals(lvLast.getId()) &&
                             lvHelpGrundbuchverweis.getGrundbuchblattId().equals(lvGrundbuchblatt.getId()))
                         {
                             lvGrundbuchverweis = lvHelpGrundbuchverweis;
                         }
                     }
                  
                     // Grundbuchverweis != null, dann eine gueltige Kombination Grundbuchblatt <-> Grundbuchverweis gefunden
                     if (lvGrundbuchverweis != null)
                     {
                    	 lvAnzahlGrundbuchblaetter++;
                     }
                 }
                 
                 // Doppelte Grundbuchblaetter (Dubletten) suchen und die Anzahl der Grundbuchblaetter anpassen
                 lvCollectionGrundbuchblatt = lvHelpListeGrundbuchblatt.values();
                 lvIteratorGrundbuchblatt = lvCollectionGrundbuchblatt.iterator();
                 //System.out.println("lvAnzahlGrundbuchblaetter: " + lvAnzahlGrundbuchblaetter);
                 int lvAnzahlDubletten = 0;
                 
                 while (lvIteratorGrundbuchblatt.hasNext())
                 {
                     Grundbuchblatt lvGrundbuchblatt = lvIteratorGrundbuchblatt.next();
                     for (Grundbuchblatt lvGrundbuchblattVergleich:lvHelpListeGrundbuchblattVergleich)
                     {
                       if (!lvGrundbuchblatt.getId().equals(lvGrundbuchblattVergleich.getId()))
                    	 {
                    		 if (lvGrundbuchblatt.equals(lvGrundbuchblattVergleich))
                    		 {
                    			 lvAnzahlDubletten++;
                    			 ivLogger.info("Grundbuchblatt Dublette: " + lvGrundbuchblatt.getId() + "-" + lvGrundbuchblattVergleich.getId());
                    		 }
                    	 }
                     }
                 }
                 ivLogger.info("Sicherheit " + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + " - Anzahl Dubletten: " + (lvAnzahlDubletten / 2));
                 lvAnzahlGrundbuchblaetter -= (lvAnzahlDubletten / 2);
                 ivLogger.info("Sicherheit " + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + " - Anzahl Grundbuchblaetter: " + lvAnzahlGrundbuchblaetter);
                 // CT 04.08.2016
                 
                 lvCollectionGrundbuchblatt = lvHelpListeGrundbuchblatt.values();
                 lvIteratorGrundbuchblatt = lvCollectionGrundbuchblatt.iterator();

                 while (lvIteratorGrundbuchblatt.hasNext())
                 {
                     StringBuffer lvHelpStringGrundbuchblatt = new StringBuffer();
                     Grundbuchblatt lvGrundbuchblatt = lvIteratorGrundbuchblatt.next();
                     //System.out.println("Last: " + lvLast.getId() + " Grundbuchblatt: " + lvGrundbuchblatt.getId());

                     // 
                     //lvCollectionGrundbuchverweis = pvSAPCMS.getListeGrundbuchverweis().values();
                     //lvIteratorGrundbuchverweis = lvCollectionGrundbuchverweis.iterator();
                     //while (lvIteratorGrundbuchverweis.hasNext())
                     //{
                         //Grundbuchverweis lvHelpGrundbuchverweis = lvIteratorGrundbuchverweis.next();
                         //if (lvHelpGrundbuchverweis.getLastId().equals(lvLast.getId()) &&
                         //    lvHelpGrundbuchverweis.getGrundbuchblattId().equals(lvGrundbuchblatt.getId()))
                         //{
                         //    System.out.println("Grundbuchverweis gefunden: Last " + lvLast.getId() + " Grundbuchblatt " + lvGrundbuchblatt.getId());
                         //    lvAlleKeinenGrundbuchverweis = false;
                         //}
                     //}              
                     
                     // Grundbuchblatt ueber Grundbuchverweis ermitteln
                     // Grundbuchverweis per Last-ID suchen
                     //System.out.println("Last: " + lvLast.toString());
                     //System.out.println("Grundbuchblatt: " + lvGrundbuchblatt.toString());
                     Grundbuchverweis lvGrundbuchverweis = null;
                     lvCollectionGrundbuchverweis = ivSAPCMS.getListeGrundbuchverweis().values();
                     lvIteratorGrundbuchverweis = lvCollectionGrundbuchverweis.iterator();
                     while (lvIteratorGrundbuchverweis.hasNext())
                     {
                         Grundbuchverweis lvHelpGrundbuchverweis = lvIteratorGrundbuchverweis.next();
                         //System.out.println("Grundbuchverweis: " + lvHelpGrundbuchverweis.toString());
                         if (lvHelpGrundbuchverweis.getLastId().equals(lvLast.getId()) &&
                             lvHelpGrundbuchverweis.getGrundbuchblattId().equals(lvGrundbuchblatt.getId()))
                         {
                             lvGrundbuchverweis = lvHelpGrundbuchverweis;
                         }
                     }
                  
                     // Grundbuchverweis == null, dann Last-ID und Grundbuchblatt-ID protokollieren
                     if (lvGrundbuchverweis == null)
                     {
                    	 ivLogger.error("Fehlender Grundbuchverweis;" + pvKontonummer + ";" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                         ivLogger.error("Konnte keinen Grundbuchverweis fuer Last " + lvLast.getId() + " und Grundbuchblatt " + lvGrundbuchblatt.getId() + " ermitteln!");
                         //System.out.println("lvAlleKeinenGrundbuchverweis: " + lvAlleKeinenGrundbuchverweis);
                         if (!lvAlleKeinenGrundbuchverweis)
                         {
                             continue;
                         }
                     }
                     else
                     {
                    	 ivLogger.info("Grundbuchverweis: " + lvGrundbuchverweis.toString());
                     }
 
                     lvShve = new TXSSicherheitVerzeichnis();
                     // BLB verwendet die ID des Grundbuchverweis
                     if (ivSAPCMS.getVorlaufsatz().getInstitut().equals("004"))
                     {
                         if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                         {                         
                           lvShve.setVenr(lvGrundbuchverweis.getId());
                         }
                         else
                         {
                           lvShve.setVenr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer());                             
                         }
                     }
                     // NLB verwendet einen zusammengesetzten Schluessel
                     if (ivSAPCMS.getVorlaufsatz().getInstitut().equals("009"))
                     {
                         if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                         {
                             ////lvShve.setVenr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3());  
                        	 lvShve.setVenr(lvGrundbuchverweis.getId());
                         }
                         else
                         {
                        	 //continue; - auch eine Moeglichkeit - CT 06.07.2017
                        	 return new StringBuffer();
                         }
                         ////else
                         ////{
                         ////    lvShve.setVenr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer());
                         ////}
                     }
                      
                     if (lvShve.getVenr().length() > 40)
                     {
                         lvShve.setVenr(lvShve.getVenr().substring(0, 39));
                     }
                   
                     //if (ivSAPCMS.getVorlaufsatz().getInstitut().equals("009"))
                     //{
                     //    if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                     //    {
                     //        ivLogger.info("TXSSicherheitVerzeichnis Venr;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3() + ";" + lvGrundbuchverweis.getId());                     
                     //    }
                     //    else
                     //    {
                     //   	 if (lvGrundbuchverweis != null)
                     //   	 {
                     //   		 ivLogger.info("TXSSicherheitVerzeichnis Venr;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer() + ";" + lvGrundbuchverweis.getId());
                     //   	 }
                     //    }
                     //}                   
                     
                     lvShve.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                     lvShve.setQuelle(pvQuellsystem);
                     lvHelpStringGrundbuchblatt.append(lvShve.printTXSTransaktionStart());
                     lvHelpStringGrundbuchblatt.append(lvShve.printTXSTransaktionDaten());
                     // TXSVerzeichnisDaten
                     lvVedaten = new TXSVerzeichnisDaten();
                     lvVedaten.setAbt("3");
                     lvVedaten.setGbart("1"); 
                     lvVedaten.setKat("1");
                     // Nr. Abteilung 3 - CT 16.03.2015
                     if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                     {
                         lvVedaten.setNrabt(lvGrundbuchverweis.getLaufendeNrAbt3());
                         ivLogger.info("NrAbt3" + ";" + pvKontonummer + ";" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ";"  + lvLast.getRegisternummer() + ";" + lvGrundbuchverweis.getLaufendeNrAbt3());
                     }
                     else
                     {
                         lvVedaten.setNrabt(lvLast.getRegisternummer());                         
                     }
                     
                     if (lvSicherheitenvereinbarung.getGesamtgrundschuldKennzeichen().equals("X"))
                     {
                         //System.out.println("Last - Verfuegungsbetrag: " + lvLast.getVerfuegungsbetrag());
                         //System.out.println("Anzahl - Grundbuchblaetter: " + lvCollectionGrundbuchblatt.size());
                         //for (Grundbuchblatt helpGblatt:lvCollectionGrundbuchblatt)
                         //{
                         //	 System.out.println(helpGblatt.getBand() + " " + helpGblatt.getBlatt() + " " + helpGblatt.getGrundbuchVon() + " " + helpGblatt.getId());
                         //}
                         if (lvCollectionGrundbuchblatt.size() > 1)
                         {
                             lvAnzahlErledigterGrundbuchblaetter++;
                             BigDecimal lvHelpBetrag = new BigDecimal("0.0");
                             if (lvAnzahlGrundbuchblaetter == 0)
                             {
                            	lvAnzahlGrundbuchblaetter = lvCollectionGrundbuchblatt.size();
                            	ivLogger.info("Keine passenden Grundbuchverweise gepflegt: " + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                             }
                             if (lvAnzahlErledigterGrundbuchblaetter == lvAnzahlGrundbuchblaetter)
                             //if (lvAnzahlErledigterGrundbuchblaetter == lvCollectionGrundbuchblatt.size())
                             {
                               lvHelpBetrag = (new BigDecimal(lvLast.getVerfuegungsbetrag())).subtract(lvSummeZuweisungsbetragPO); 
                             }
                             else
                             {
                               lvHelpBetrag = (new BigDecimal(lvLast.getVerfuegungsbetrag())).divide(new BigDecimal(lvAnzahlGrundbuchblaetter), 2, RoundingMode.HALF_UP);	 
                               //lvHelpBetrag = (new BigDecimal(lvLast.getVerfuegungsbetrag())).divide(new BigDecimal(lvCollectionGrundbuchblatt.size()), 2, RoundingMode.HALF_UP); 
                             }
                            
                             if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                             {
                                 ivLogger.info("ZuweisungsbetragPO;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3() + ";" + lvHelpBetrag.toString());                     
                     
                             }
                             else
                             {
                                 ivLogger.info("ZuweisungsbetragPO;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer() + ";" + lvHelpBetrag.toString());                     
                             }
                             lvVedaten.setBetrag(lvHelpBetrag.toString());
                             ////// CT 08.12.2015 Spaeter wieder entfernen - Ausgebaut CT 15.12.2015
                             ////lvVedaten.setBetrag(lvLast.getVerfuegungsbetrag());
                             
                             lvSummeZuweisungsbetragPO = lvSummeZuweisungsbetragPO.add(lvHelpBetrag);
                         }
                         else
                         {
                           lvVedaten.setBetrag(lvLast.getVerfuegungsbetrag());
                         }
                     }
                     else
                     {
                         ObjektZuweisungsbetrag lvOzw = ivSAPCMS.getObjektZuweisungsbetragListe().getObjektZuweisungsbetrag(pvShum.getSicherheitenvereinbarungId());
                         if (lvOzw != null)
                         {
                             lvVedaten.setBetrag(lvOzw.getZuweisungsbetrag().toString());
                         }
                     }
                                  
                     lvVedaten.setWhrg(pvShum.getZuweisungsbetragWaehrung());
                                          
                     //if (this.getZuweisungsbetragWaehrung().isEmpty())
                     //{
                     //    System.out.println("ZuweisungbetragWaehrung leer - " + this.getId() + " " + this.getZuweisungsbetrag() + this.getZuweisungsbetragWaehrung());
                     //}
                 
                     lvHelpStringGrundbuchblatt.append(lvVedaten.printTXSTransaktionStart());
                     lvHelpStringGrundbuchblatt.append(lvVedaten.printTXSTransaktionDaten());
                     lvHelpStringGrundbuchblatt.append(lvVedaten.printTXSTransaktionEnde());
                     
                     // TXSVerzeichnisVBlatt
                     lvVevb = new TXSVerzeichnisVBlatt();
                     // BLB verwendet die ID des Grundbuchverweis
                     if (ivSAPCMS.getVorlaufsatz().getInstitut().equals("004"))
                     {
                         if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                         {    
                           lvVevb.setVbnr(lvGrundbuchverweis.getId());    
                         }
                         else
                         {
                             lvVevb.setVbnr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer());                             
                         }
                     }
                     // NLB verwendet einen zusammengesetzten Schluessel
                     if (ivSAPCMS.getVorlaufsatz().getInstitut().equals("009"))
                     {
                         if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                         {
                         ////    lvVevb.setVbnr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3());
                        	 lvVevb.setVbnr(lvGrundbuchverweis.getId());
                         }
                         ////else
                         ////{
                         ////    lvVevb.setVbnr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer());
                         ////}
                     }

                     if (lvVevb.getVbnr().length() > 40)
                     {
                         lvVevb.setVbnr(lvVevb.getVbnr().substring(0, 39));
                     }
                     
                     //if (ivSAPCMS.getVorlaufsatz().getInstitut().equals("009"))
                     //{
                     //    if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                     //    {
                     //        ivLogger.info("TXSVerzeichnisVBlatt Vbnr;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3() + ";" + lvGrundbuchverweis.getId());                            
                     //    }
                     //    else
                     //    {
                     //   	 if (lvGrundbuchverweis != null)
                     //   	 {
                     //   		 ivLogger.info("TXSVerzeichnisVBlatt Vbnr;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer() + ";" + lvGrundbuchverweis.getId());
                     //   	 }
                     //    }
                     //}


                     lvVevb.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                     lvVevb.setQuelle(pvQuellsystem);
                     
                     lvHelpStringGrundbuchblatt.append(lvVevb.printTXSTransaktionStart());
                     lvHelpStringGrundbuchblatt.append(lvVevb.printTXSTransaktionDaten());
                 
                     // TXSVerzeichnisblattDaten
                     lvVbdaten = new TXSVerzeichnisblattDaten();
                     lvVbdaten.setBand(lvGrundbuchblatt.getBand());
                     if (lvGrundbuchblatt.getBlatt().isEmpty())
                     {
                         lvVbdaten.setBlatt("0000");
                     }
                     else
                     {
                         lvVbdaten.setBlatt(lvGrundbuchblatt.getBlatt());
                     }
                     lvVbdaten.setGbvon(lvGrundbuchblatt.getGrundbuchVon());
                     if (lvGrundbuchblatt.getGrundbuchVon().isEmpty() && !lvGrundbuchblatt.getAmtsgericht().isEmpty())
                     {
                         lvVbdaten.setGbvon(lvGrundbuchblatt.getAmtsgericht());
                     }
                     lvVbdaten.setKat("1");
                     lvHelpStringGrundbuchblatt.append(lvVbdaten.printTXSTransaktionStart());
                     lvHelpStringGrundbuchblatt.append(lvVbdaten.printTXSTransaktionDaten());
                     lvHelpStringGrundbuchblatt.append(lvVbdaten.printTXSTransaktionEnde());
                 
                     lvHelpStringGrundbuchblatt.append(lvVevb.printTXSTransaktionEnde());
                 
                     // TXSVerzeichnisBestandsverz                 
                     Collection<Grundstueck> lvCollectionGrundstueck = lvHelpListeGrundstueck.values();
                     Iterator<Grundstueck> lvIteratorGrundstueck = lvCollectionGrundstueck.iterator();
                     while (lvIteratorGrundstueck.hasNext())
                     {
                         Grundstueck lvGrundstueck = lvIteratorGrundstueck.next();

                         if (lvGrundstueck.getGrundbuchblattId().equals(lvGrundbuchblatt.getId()))
                         {
                             lvVebv = new TXSVerzeichnisBestandsverz();
                             lvVebv.setBvnr(lvGrundstueck.getId());                                 
                             
                             if (lvVebv.getBvnr().length() > 40)
                             {
                                 lvVebv.setBvnr(lvVebv.getBvnr().substring(0, 39));
                             }
                             
                             //if (ivSAPCMS.getVorlaufsatz().getInstitut().equals("009"))
                             //{
                             //    if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                             //    {
                             //        ivLogger.info("TXSVerzeichnisBestandsverz Bvnr;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3()
                             //                                   + lvGrundstueck.getLaufendeNummer() + ";" + lvGrundstueck.getId());
                             //
                             //    }
                             //    else
                             //    {
                             //        ivLogger.info("TXSVerzeichnisBestandsverz Bvnr;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer()
                             //                                   + lvGrundstueck.getLaufendeNummer() + ";" + lvGrundstueck.getId());
                             //    }
                             //}
                     
                             // alte: vebv.setBvnr(gbe.getGrundbuchID());
                             lvVebv.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                             lvVebv.setQuelle(pvQuellsystem);
                             lvHelpStringGrundbuchblatt.append(lvVebv.printTXSTransaktionStart());
                             lvHelpStringGrundbuchblatt.append(lvVebv.printTXSTransaktionDaten());
                             // TXSBestandsverzDaten
                             lvBvdaten = new TXSBestandsverzDaten();
                             lvBvdaten.setFlur(lvGrundstueck.getFlur());
                             lvBvdaten.setFlurst(lvGrundstueck.getFlurstueck());
                             lvBvdaten.setLfdnr(lvGrundstueck.getLaufendeNummer());
                             if (lvGrundbuchblatt.getBlatt().isEmpty() && !lvGrundstueck.getFlur().isEmpty())
                             {
                                 lvBvdaten.setGem(lvGrundstueck.getGemarkung());
                             }
                                              
                             lvHelpStringGrundbuchblatt.append(lvBvdaten.printTXSTransaktionStart());
                             lvHelpStringGrundbuchblatt.append(lvBvdaten.printTXSTransaktionDaten());
                             lvHelpStringGrundbuchblatt.append(lvBvdaten.printTXSTransaktionEnde());
                 
                             lvHelpStringGrundbuchblatt.append(lvVebv.printTXSTransaktionEnde());
                         }
                     }
                 
                     // TXSVerzeichnisPfandobjekt
                     lvVepo = new TXSVerzeichnisPfandobjekt();
                     lvVepo.setObjnr(lvImmobilie.getObjektId());
                     lvVepo.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                     lvVepo.setQuelle(pvQuellsystem);
                     lvHelpStringGrundbuchblatt.append(lvVepo.printTXSTransaktionStart());
                     lvHelpStringGrundbuchblatt.append(lvVepo.printTXSTransaktionDaten());
                     // TXSPfandobjektDaten
                     lvPodaten = new TXSPfandobjektDaten();
                 
                     // Bestueckung von Pfandobjekten etc... 
                     if (lvImmobilie != null)
                     { // Es ist etwas da .... 
                         // Eigentumstyp aktuell gar nicht zu mappen !! 
                         //String lvPfGruppe = new String();
                         //String lvPfArt = new String();

                         //lvPfGruppe = ValueMapping.mapPfandObjektGruppe(lvImmobilie.getNutzungsart(), lvImmobilie.getProzentanteil());
                         //lvPfArt = ValueMapping.mapNutzungsart(lvImmobilie.getObjektartId(), lvImmobilie.getProzentanteil(), lvImmobilie.getGradBaufertigstellung());

                        //String lvErtragsKz = "3";
                         // Bauplatz
                         //if (lvPfArt.startsWith("4"))
                         //{
                         //    lvErtragsKz = "2";
                         //}
                         // Baugeld
                         //if (lvPfArt.startsWith("7"))
                         //{
                         //    lvErtragsKz = "1";
                         //}
                               
                         // CT 26.03.2012 - Altes Mapping 
                         //podaten.setEigtyp(ValueMapping.changeEigentumstyp(sEigentumsTyp));
                         lvPodaten.setEigtyp(ValueMapping.mapEigentumstyp(lvImmobilie.getNutzung()));
                  
                         // Ertragsfaehigkeit - CT 09.07.2012
                         // Defaultmaessig '3' - voll ertragsfaehig
                         lvPodaten.setErtragsf("3");
                         //alte Version: podaten.setGebiet(ValueMapping.changeGebiet(sLand));
                         // CT - 09.07.2012
                         if (!lvImmobilie.getRegion().isEmpty())
                         {
                             lvPodaten.setGebiet(lvImmobilie.getLaenderschluessel() + "_" + lvImmobilie.getRegion());
                         }
                
                         if (lvImmobilie.getFertigstellungsdatum().length() > 4)
                             lvPodaten.setJahr(ValueMapping.changeBaujahr(lvImmobilie.getFertigstellungsdatum()));
                 
                         lvPodaten.setKat("1");
                         //alte Version: podaten.setLand(ValueMapping.changeLand(sStaat));
                         lvPodaten.setLand(lvImmobilie.getLaenderschluessel());
                         
                         lvPodaten.setSwert(lvImmobilie.getSachwert());
                         lvPodaten.setEwert(lvImmobilie.getErtragswert());
                   
                         lvPodaten.setBwert(lvImmobilie.getBeleihungswert());
                         
                         // Nutzungsart
                         String lvNutzungsart;
                         if (StringKonverter.convertString2Double(lvImmobilie.getProzentanteil()) <= 33.0)
                         {
                             lvNutzungsart = "1";
                         }
                         else
                         {
                             lvNutzungsart = "2";
                         }
                         lvPodaten.setNart(lvNutzungsart);
                 
                         // CT 07.12.2015 - Mapping Objektgruppe erst einmal ausgebaut
                         //lvPodaten.setOgrp(ValueMapping.mapPfandObjektGruppe(lvImmobilie.getNutzungsart(), lvImmobilie.getProzentanteil()));
 
                         // CT 08.10.2015 - SAP CMS Auspraegungen verwenden
                         // CT 07.12.2015 - Mapping Objektgruppe erst einmal ausgebaut
                         //if (pvSAPCMS.getVorlaufsatz().getInstitut().equals("009"))
                         //{   
                         
                           String lvHelpObjektgruppe = lvImmobilie.getNutzungsart();
                           if (lvHelpObjektgruppe.equals("100008"))
                           {
                               lvHelpObjektgruppe = "100007";
                           }
                           if (lvHelpObjektgruppe.equals("100020"))
                           {
                               lvHelpObjektgruppe = "100019";
                           }
                           if (lvHelpObjektgruppe.equals("100023"))
                           {
                               lvHelpObjektgruppe = "100022";
                           }
                           lvPodaten.setOgrp(lvHelpObjektgruppe);
                         //}                       
                
                         lvPodaten.setOrt(lvImmobilie.getOrt());
                         lvPodaten.setPlz(lvImmobilie.getPostleitzahl());

                         String sSP_EigentumsTyp = new String();
                         sSP_EigentumsTyp = ValueMapping.mapSP_Eigentumstyp(lvImmobilie.getObjektartId(), lvImmobilie.getGradBaufertigstellung(), lvImmobilie.getNutzung(), lvImmobilie.getNutzungsart(), lvImmobilie.getProzentanteil());
                         lvPodaten.setSpeigtyp(sSP_EigentumsTyp);
                 
                         lvPodaten.setStr(lvImmobilie.getStrasse());
                         // Hausnummer in ein eigenes Feld - CT 25.11.2011
                         lvPodaten.setHausnr(lvImmobilie.getHausnummer());
                         lvPodaten.setZusatz(lvImmobilie.getErgaenzungHausnummer());
                         lvPodaten.setWhrg(lvImmobilie.getBeleihungswertWaehrung());
                                          
                         lvHelpStringGrundbuchblatt.append(lvPodaten.printTXSTransaktionStart());
                         lvHelpStringGrundbuchblatt.append(lvPodaten.printTXSTransaktionDaten());
                         lvHelpStringGrundbuchblatt.append(lvPodaten.printTXSTransaktionEnde());
                 
                         lvHelpStringGrundbuchblatt.append(lvVepo.printTXSTransaktionEnde());
                 
                         lvHelpStringGrundbuchblatt.append(lvShve.printTXSTransaktionEnde());
                     }
                  
                     if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                     {
                         lvHelpString.append(lvHelpStringGrundbuchblatt);
                     }
                     else
                     {
                         lvHelpString.append(lvHelpStringGrundbuchblatt);
                     }
                   
                 }
                 if (lvKredsh != null)
                 {
                     lvHelpString.append(lvKredsh.printTXSTransaktionEnde());
                 }
 
                 if (lvSh != null)
                 {
                     TXSSicherheitZuSicherheit lvZugsh = new TXSSicherheitZuSicherheit();
                     lvZugsh.setKey(lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + "_" + pvKundennummer + "_" + pvPassivkontonummer + "_ABTR");
                     lvZugsh.setQuelle("ADARLREFI");
                     lvZugsh.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                     lvZugsh.setArt("1");
                     lvZugsh.setRang("1");
                     
                     lvHelpString.append(lvZugsh.printTXSTransaktionStart());
                     lvHelpString.append(lvZugsh.printTXSTransaktionDaten());
                     lvHelpString.append(lvZugsh.printTXSTransaktionEnde());            

                     lvHelpString.append(lvSh.printTXSTransaktionEnde());
                 }
             }             
        }

        return lvHelpString; 
    }  
    
    /**
     * Importiert Grundpfandrechte von Auslandsimmobilien
     * @param pvShum
     * @param pvSicherheitenvereinbarung
     * @param pvImmobilie
     * @param pvLast
     * @param pvBuergeFakt
     * @param pvSAPCMS
     * @param pvQuellsystem
     * @return
     */
    private String importGrundpfandrechtAuslandsimmo(Sicherungsumfang pvShum, Sicherheitenvereinbarung pvSicherheitenvereinbarung, Immobilie pvImmobilie, Last pvLast, BigDecimal pvBuergeFakt, String pvQuellsystem)
    {
        StringBuffer lvHelpString = new StringBuffer();
        System.out.println("Auslandsimmo...");
        TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
        // Pfandobjekt-Nr nehmen, da kein Grundbuch vorhanden!!!
        // CT 06.06.2012 - Wegen Eindeutigkeit die Sicherheiten-ID davor schreiben
        lvShve.setVenr(pvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + "_" + pvImmobilie.getObjektId());
        lvShve.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
        lvShve.setQuelle(pvQuellsystem);
        lvHelpString.append(lvShve.printTXSTransaktionStart());
        lvHelpString.append(lvShve.printTXSTransaktionDaten());

        // TXSVerzeichnisDaten
        TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
        //vedaten.setAbt("3");
        lvVedaten.setGbart("100");
        lvVedaten.setAsichr(pvImmobilie.getBemerkung());
        //vedaten.setKat("1");
        //vedaten.setNrabt(gbe.getLfdNr());
        //vedaten.setBetrag(dZuwBetragRe.toString());
        // CT 28.11.2011 - Umgestellt auf Verfuegungsbertrag
        //if (darlehen.getKredittyp().equals("B"))
        //{
        //    vedaten.setBetrag(dVerfBetragBu.toString());       
        //}
        //else
        //{
        //    vedaten.setBetrag(dVerfBetragRe.toString());
        //}
        ivLogger.info(pvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ": " + pvSicherheitenvereinbarung.getGesamtgrundschuldKennzeichen());
        if (pvSicherheitenvereinbarung.getGesamtgrundschuldKennzeichen().equals("X"))
        {
            lvVedaten.setBetrag(pvLast.getVerfuegungsbetrag()); //lvSicherheitenvereinbarung.getVerfuegungsbetrag());
            lvVedaten.setWhrg(pvLast.getVerfuegungsbetragWaehrung());
        }
        else
        {
        	System.out.println("Size: " + ivSAPCMS.getObjektZuweisungsbetragListe().size());
        	System.out.println("ID: " + pvShum.getSicherheitenvereinbarungId());
            //ObjektZuweisungsbetrag lvOzw = pvSAPCMS.getObjektZuweisungsbetragListe().getObjektZuweisungsbetrag(pvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
            ObjektZuweisungsbetrag lvOzw = ivSAPCMS.getObjektZuweisungsbetragListe().getObjektZuweisungsbetrag(pvShum.getSicherheitenvereinbarungId());
            if (lvOzw != null)
            {
            	System.out.println("Zuweisungsbetrag: " + lvOzw.getZuweisungsbetrag() + " pvBuergeFakt: " + pvBuergeFakt);
                lvVedaten.setBetrag((lvOzw.getZuweisungsbetrag().multiply(pvBuergeFakt)).toString());
                lvVedaten.setWhrg(pvShum.getZuweisungsbetragWaehrung());
           }
        }
        //lvVedaten.setWhrg(pvSicherheitenvereinbarung.getVerfuegungsbetragWaehrung());              

        lvHelpString.append(lvVedaten.printTXSTransaktionStart());
        lvHelpString.append(lvVedaten.printTXSTransaktionDaten());
        lvHelpString.append(lvVedaten.printTXSTransaktionEnde());

        // TXSVerzeichnisPfandobjekt
        TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
        lvVepo.setObjnr(pvImmobilie.getObjektId());
        lvVepo.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
        lvVepo.setQuelle(pvQuellsystem);
        lvHelpString.append(lvVepo.printTXSTransaktionStart());
        lvHelpString.append(lvVepo.printTXSTransaktionDaten());
        // TXSPfandobjektDaten
        TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();

        // Bestueckung von Pfandobjekten
        if (pvImmobilie != null)
        { // Es ist etwas da .... 
            //String lvLand = new String();
            String lvStaat = new String();
            String lvEigentumsTyp = new String();

            lvStaat = ValueMapping.mapLand(pvImmobilie.getLaenderschluessel());
            if (lvStaat.isEmpty())
            { // Default 
                lvStaat = "100";
            }
            //System.out.println("sStaat: " + sStaat);
            // Sonderfaelle LandID umsetzen..bspw. Deutschland 
            //lvLand = ValueMapping.mapGebiete(lvStaat, pvImmobilie.getRegion());
            // Pfandobjektgruppe / Pfandobjektnutzungsart / Eigentumstyp 
            //System.out.println("sLand: " + sLand);

            // Eigentumstyp aktuell gar nicht zu mappen !! 
            //String lvPfGruppe = new String();
            //String lvPfArt = new String();

            //lvPfGruppe = ValueMapping.mapPfandObjektGruppe(pvImmobilie.getNutzungsart(), pvImmobilie.getProzentanteil());
            //lvPfArt = ValueMapping.mapNutzungsart(pvImmobilie.getObjektartId(), pvImmobilie.getProzentanteil(), pvImmobilie.getGradBaufertigstellung());

            // Ertragsfaehigkeit - CT 09.07.2012
            // Defaultmaessig '3' - voll ertragsfaehig
            String lvErtragsKz = "3";
            // Bauplatz
            //if (lvPfArt.startsWith("4"))
            //{
            //    lvErtragsKz = "2";
            //}
            // Baugeld
            //if (lvPfArt.startsWith("7"))
            //{
            //    lvErtragsKz = "1";
            //}

            lvEigentumsTyp = ValueMapping.mapSP_Eigentumstyp(pvImmobilie.getObjektartId(), pvImmobilie.getGradBaufertigstellung(), pvImmobilie.getNutzung(), pvImmobilie.getNutzungsart(), pvImmobilie.getProzentanteil());

            // CT 26.03.2012 - Altes Mapping 
            //podaten.setEigtyp(ValueMapping.changeEigentumstyp(sEigentumsTyp));
            lvPodaten.setEigtyp(ValueMapping.mapEigentumstyp(pvImmobilie.getNutzung()));

            lvPodaten.setErtragsf(lvErtragsKz);             

            //alte Version: podaten.setGebiet(ValueMapping.changeGebiet(sLand));
            // CT - 09.07.2012
            if (!pvImmobilie.getRegion().isEmpty())
            {
                lvPodaten.setGebiet(pvImmobilie.getLaenderschluessel() + "_" + pvImmobilie.getRegion());
            }

            if (pvImmobilie.getFertigstellungsdatum().length() > 4)
                lvPodaten.setJahr(ValueMapping.changeBaujahr(pvImmobilie.getFertigstellungsdatum()));
            lvPodaten.setKat("1");
            lvPodaten.setLand(ValueMapping.changeLand(lvStaat));
            lvPodaten.setSwert(pvImmobilie.getSachwert());
            lvPodaten.setEwert(pvImmobilie.getErtragswert());
            lvPodaten.setBwert(pvImmobilie.getBeleihungswert());
            
            // Nutzungsart
            String lvNutzungsart;
            if (StringKonverter.convertString2Double(pvImmobilie.getProzentanteil()) <= 33.0)
            {
                lvNutzungsart = "1";
            }
            else
            {
                lvNutzungsart = "2";
            }
            lvPodaten.setNart(lvNutzungsart);

            // CT 07.12.2015 - Mapping Objektgruppe erst einmal ausgebaut
            //lvPodaten.setOgrp(ValueMapping.mapPfandObjektGruppe(pvImmobilie.getNutzungsart(), pvImmobilie.getProzentanteil()));
            
            // CT 08.10.2015 - SAP CMS Auspraegungen verwenden
            String lvHelpObjektgruppe = pvImmobilie.getNutzungsart();
            if (lvHelpObjektgruppe.equals("100008"))
            {
            	lvHelpObjektgruppe = "100007";
            }
            if (lvHelpObjektgruppe.equals("100020"))
            {
            	lvHelpObjektgruppe = "100019";
            }
            if (lvHelpObjektgruppe.equals("100023"))
            {
                lvHelpObjektgruppe = "100022";
            }
            lvPodaten.setOgrp(lvHelpObjektgruppe);

            lvPodaten.setOrt(pvImmobilie.getOrt());
            lvPodaten.setPlz(pvImmobilie.getPostleitzahl());
            lvPodaten.setSpeigtyp(lvEigentumsTyp);
            lvPodaten.setStr(pvImmobilie.getStrasse());
            lvPodaten.setHausnr(pvImmobilie.getHausnummer());
            lvPodaten.setZusatz(pvImmobilie.getErgaenzungHausnummer());
            lvPodaten.setWhrg(pvImmobilie.getBeleihungswertWaehrung());
        }

        lvHelpString.append(lvPodaten.printTXSTransaktionStart());
        lvHelpString.append(lvPodaten.printTXSTransaktionDaten());
        lvHelpString.append(lvPodaten.printTXSTransaktionEnde());

        lvHelpString.append(lvVepo.printTXSTransaktionEnde());
        lvHelpString.append(lvShve.printTXSTransaktionEnde());
        
        return lvHelpString.toString();
    }
     
    /**
     * Importiert eine KreditSicherheit Flugzeuge
     * @param pvSAPCMS
     * @param pvKontonummer
     * @param pvQuellsystem
     */
    public StringBuffer import2TXSKreditSicherheitFlugzeug(Sicherungsumfang pvShum, String pvKontonummer, String pvQuellsystem)
    {
        StringBuffer lvHelpString = new StringBuffer(); 
        System.out.println("import2TXSKreditSicherheitFlugzeug");
        TXSKreditSicherheit lvKredsh = null;
        TXSSicherheitDaten lvShdaten = null;
        TXSSicherheitVerzeichnis lvShve = null;
        TXSVerzeichnisDaten lvVedaten = null;
        TXSVerzeichnisVBlatt lvVevb = null;
        TXSVerzeichnisblattDaten lvVbdaten = null;
        TXSVerzeichnisPfandobjekt lvVepo = null;
        TXSPfandobjektDaten lvPodaten = null;
        
        Sicherheitenvereinbarung lvSicherheitenvereinbarung = ivSAPCMS.getListeSicherheitenvereinbarung().get(pvShum.getSicherheitenvereinbarungId());
                     
        BigDecimal lvZuwBetrag = StringKonverter.convertString2BigDecimal(pvShum.getZuweisungsbetrag()); 
        BigDecimal lvVerfBetrag = StringKonverter.convertString2BigDecimal(lvSicherheitenvereinbarung.getVerfuegungsbetrag()); 
                                  
        // Last suchen
        Last lvLast = null;
        Collection<Last> lvCollectionLast = ivSAPCMS.getListeLast().values();
        Iterator<Last> lvIteratorLast = lvCollectionLast.iterator();
        while (lvIteratorLast.hasNext())
        {
            Last lvHelpLast = lvIteratorLast.next();
            lvLast = null;
            if (lvHelpLast.getSicherheitenvereinbarungId().equals(lvSicherheitenvereinbarung.getId()))
            {
                lvLast = lvHelpLast;
            }
             
             if (lvLast != null)
             {
                 // Flugzeug suchen
                 Flugzeug lvFlugzeug = null;
                 Collection<Flugzeug> lvCollectionFlugzeug = ivSAPCMS.getListeFlugzeug().values();
                 Iterator<Flugzeug> lvIteratorFlugzeug = lvCollectionFlugzeug.iterator();
                 while (lvIteratorFlugzeug.hasNext())
                 {
                     Flugzeug lvHelpFlugzeug = lvIteratorFlugzeug.next();
                     if (lvHelpFlugzeug.getId().equals(lvLast.getImmobilieId()))
                     {
                         lvFlugzeug = lvHelpFlugzeug;
                     }
                 }
             
                 //System.out.println("lvFlugzeug: " + lvFlugzeug);
                 // Wenn Deckungskennzeichen nicht 'W' oder 'U' ist, dann leeren StringBuffer zurueckliefern
                 if (lvFlugzeug != null)
                 {
                     //System.out.println("Deckungskennzeichen: " + lvFlugzeug.getStatusDeckung());
                     if (!lvFlugzeug.getStatusDeckung().equals("W") && !lvFlugzeug.getStatusDeckung().equals("U"))
                     {   
                           continue;
                     }
                 }
                 
                 System.out.println("TXSKreditSicherheitFlugzeug - Start");
                 // TXSKreditSicherheit
                 lvKredsh = new TXSKreditSicherheit();
                 lvKredsh.setKey(lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                 lvKredsh.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                 lvKredsh.setQuelle(pvQuellsystem);
                 lvKredsh.setZuwbetrag(lvZuwBetrag.toString());
                 lvKredsh.setWhrg(pvShum.getZuweisungsbetragWaehrung());
        
                 // TXSSicherheitDaten
                 lvShdaten = new TXSSicherheitDaten();
                 lvShdaten.setArt("57");
                 // Defaultmaessig auf 'Ja' (1)
                 lvShdaten.setGepr("1");
                 lvShdaten.setNbetrag(lvSicherheitenvereinbarung.getNominalwert());
        
                 lvShdaten.setVbetrag(lvVerfBetrag.toString());
                 lvShdaten.setWhrg(lvSicherheitenvereinbarung.getVerfuegungsbetragWaehrung());
        
                 lvShve = new TXSSicherheitVerzeichnis();
                 lvShve.setVenr(lvFlugzeug.getIdentifikationsnummer() + "_" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                 lvShve.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                 lvShve.setQuelle(pvQuellsystem);
                        
                 lvVedaten = new TXSVerzeichnisDaten();
                 lvVevb = new TXSVerzeichnisVBlatt();
                 lvVbdaten = new TXSVerzeichnisblattDaten();
                 if (lvFlugzeug.getRegisterland().equals("DE"))
                 {
                	 lvVedaten.setAbt("3");
                	 lvVedaten.setGbart("300");
                 }
                 else
                 {
                	 lvVedaten.setGbart("301");
                 }
                 if (lvLast.getRegisternummer().equals("N.R."))
                 {
              	   lvVedaten.setNrabt(lvLast.getRegisternummer().toLowerCase());                	 
                 }
                 else
                 {
            	   lvVedaten.setNrabt(lvLast.getRegisternummer());
                 }
                 lvVedaten.setRang(lvLast.getRangfolge());
                 lvVedaten.setKat("3");

                 lvVedaten.setBetrag(lvLast.getVerfuegungsbetrag());
                 lvVedaten.setWhrg(lvLast.getVerfuegungsbetragWaehrung());              
         
                 lvVevb.setVbnr(lvFlugzeug.getIdentifikationsnummer() + "_" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId()); 
                 lvVevb.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                 lvVevb.setQuelle(pvQuellsystem);
        
                 lvVbdaten.setKat("3");
	             lvVbdaten.setGbvon(lvFlugzeug.getRegisterort());
	  
	             if (lvFlugzeug.getRegisterblatt().isEmpty())
	             {
	            	lvVbdaten.setBlatt("n.r."); 
	             }
	             else
	             {
	            	 if (lvFlugzeug.getRegisterland().equals("DE"))
	            	 {
	            		 lvVbdaten.setBlatt("Blatt " + lvFlugzeug.getRegisterblatt());	            		 
	            	 }
	            	 else
	            	 {
	            		 lvVbdaten.setBlatt(lvFlugzeug.getRegisterblatt());
	            	 }
	             }
	             
	             lvVepo = new TXSVerzeichnisPfandobjekt();
	             lvVepo.setObjnr(lvFlugzeug.getIdentifikationsnummer());
	             lvVepo.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
	             lvVepo.setQuelle(pvQuellsystem);
        
	             lvPodaten = new TXSPfandobjektDaten();
	             lvPodaten.setKat("3");
	             // Konsortialanteil
	             //lvPodaten.setKonsortialanteil(this.getGroesse());
	        
	             lvPodaten.setLand(lvFlugzeug.getLandRegistrierung());
	             
	             lvPodaten.setBrnr(lvFlugzeug.getBranchennummer());
	             lvPodaten.setWhrg(lvFlugzeug.getWaehrungBeleihungswertAktuell());
	             lvPodaten.setBwert(lvFlugzeug.getBeleihungswertAktuell());
	             lvPodaten.setBwertdat(DatumUtilities.changeDate(lvFlugzeug.getDatumBeleihungswertAktuell()));
	             lvPodaten.setMuster(lvFlugzeug.getFlugzeugtyp());
	             lvPodaten.setVar(lvFlugzeug.getFlugzeugvariante());
	             lvPodaten.setSn(lvFlugzeug.getMSN());
	             lvPodaten.setBez(lvFlugzeug.getOrdnungsnummer());
	             
	             setzeFlugzeugdaten(lvFlugzeug.getBranchennummer(), lvPodaten);
	             lvPodaten.setBlhwertinit(lvFlugzeug.getBeleihungswertInitial() + " " + lvFlugzeug.getWaehrungBeleihungswertInitial());

	             lvPodaten.setAusldat(DatumUtilities.changeDate(lvFlugzeug.getHerstellungsdatum()));
	             lvPodaten.setFertstdat(DatumUtilities.changeDate(lvFlugzeug.getHerstellungsdatum()));
	             // FinanzObjektNr kann nicht angeliefert werden. Information nicht vorhanden.
	             //lvPodaten.setFinobjnr(lvFlugzeug.getFinanzObjektNr());  
                               
	             // TXSKreditSicherheit
	             lvHelpString.append(lvKredsh.printTXSTransaktionStart());
	             lvHelpString.append(lvKredsh.printTXSTransaktionDaten());
   
	             // TXSSicherheitDaten
	             lvHelpString.append(lvShdaten.printTXSTransaktionStart());
	             lvHelpString.append(lvShdaten.printTXSTransaktionDaten());
	             lvHelpString.append(lvShdaten.printTXSTransaktionEnde());
   
	             // TXSSicherheitVerzeichnis
	             lvHelpString.append(lvShve.printTXSTransaktionStart());
	             lvHelpString.append(lvShve.printTXSTransaktionDaten());

	             // TXSVerzeichnisDaten
	             lvHelpString.append(lvVedaten.printTXSTransaktionStart());
	             lvHelpString.append(lvVedaten.printTXSTransaktionDaten());
	             lvHelpString.append(lvVedaten.printTXSTransaktionEnde());

	             // TXSVerzeichnisBlatt 
	             lvHelpString.append(lvVevb.printTXSTransaktionStart());
	             lvHelpString.append(lvVevb.printTXSTransaktionDaten());

	             // TXSVerzeichnisblattDaten
	             lvHelpString.append(lvVbdaten.printTXSTransaktionStart());
	             lvHelpString.append(lvVbdaten.printTXSTransaktionDaten());
	             lvHelpString.append(lvVbdaten.printTXSTransaktionEnde());
        
	             lvHelpString.append(lvVevb.printTXSTransaktionEnde());
        
	             // TXSPfandobjekt
	             lvHelpString.append(lvVepo.printTXSTransaktionStart());
	             lvHelpString.append(lvVepo.printTXSTransaktionDaten());
   
	             // TXSPfandobjektDaten
	             lvHelpString.append(lvPodaten.printTXSTransaktionStart());
	             lvHelpString.append(lvPodaten.printTXSTransaktionDaten());
	             lvHelpString.append(lvPodaten.printTXSTransaktionEnde());
   
	             // TXSTriebwerkDaten
	             TXSTriebwerkDaten lvTwdaten = null;

	             // Triebwerk1
	             if (!lvFlugzeug.getTriebwerk1().isEmpty())
	             {
	               lvTwdaten = new TXSTriebwerkDaten();
	               lvTwdaten.setBstatus("1");
	               lvTwdaten.setTwkey(lvFlugzeug.getTriebwerk1());
	               lvTwdaten.setSn(lvFlugzeug.getTriebwerk1());
	               lvTwdaten.setTyp(lvFlugzeug.getTriebwerkstyp());
	               lvTwdaten.setVariante(lvFlugzeug.getTriebwerkstyp() + "_" + lvFlugzeug.getTriebwerksvariante());
    
	               lvHelpString.append(lvTwdaten.printTXSTransaktionStart());
	               lvHelpString.append(lvTwdaten.printTXSTransaktionDaten());
	               lvHelpString.append(lvTwdaten.printTXSTransaktionEnde());
	             }

	             // Triebwerk2
	             if (!lvFlugzeug.getTriebwerk2().isEmpty())
	             {
	               lvTwdaten = new TXSTriebwerkDaten();
	               lvTwdaten.setBstatus("1");
	               lvTwdaten.setTwkey(lvFlugzeug.getTriebwerk2());
	               lvTwdaten.setSn(lvFlugzeug.getTriebwerk2());
	               lvTwdaten.setTyp(lvFlugzeug.getTriebwerkstyp());
	               lvTwdaten.setVariante(lvFlugzeug.getTriebwerkstyp() + "_" + lvFlugzeug.getTriebwerksvariante());
    
	               lvHelpString.append(lvTwdaten.printTXSTransaktionStart());
	               lvHelpString.append(lvTwdaten.printTXSTransaktionDaten());
	               lvHelpString.append(lvTwdaten.printTXSTransaktionEnde());
	             }

	             // Triebwerk3
	             if (!lvFlugzeug.getTriebwerk3().isEmpty())
	             {
	               lvTwdaten = new TXSTriebwerkDaten();
	               lvTwdaten.setBstatus("1");
	               lvTwdaten.setTwkey(lvFlugzeug.getTriebwerk3());
	               lvTwdaten.setSn(lvFlugzeug.getTriebwerk3());
	               lvTwdaten.setTyp(lvFlugzeug.getTriebwerkstyp());
	               lvTwdaten.setVariante(lvFlugzeug.getTriebwerkstyp() + "_" + lvFlugzeug.getTriebwerksvariante());
    
	               lvHelpString.append(lvTwdaten.printTXSTransaktionStart());
	               lvHelpString.append(lvTwdaten.printTXSTransaktionDaten());
	               lvHelpString.append(lvTwdaten.printTXSTransaktionEnde());
	             }

	             // Triebwerk4
	             if (!lvFlugzeug.getTriebwerk4().isEmpty())
	             {
	               lvTwdaten = new TXSTriebwerkDaten();
	               lvTwdaten.setBstatus("1");
	               lvTwdaten.setTwkey(lvFlugzeug.getTriebwerk4());
	               lvTwdaten.setSn(lvFlugzeug.getTriebwerk4());
	               lvTwdaten.setTyp(lvFlugzeug.getTriebwerkstyp());
	               lvTwdaten.setVariante(lvFlugzeug.getTriebwerkstyp() + "_" + lvFlugzeug.getTriebwerksvariante());
    
	               lvHelpString.append(lvTwdaten.printTXSTransaktionStart());
	               lvHelpString.append(lvTwdaten.printTXSTransaktionDaten());
	               lvHelpString.append(lvTwdaten.printTXSTransaktionEnde());
	             }

	             lvHelpString.append(lvVepo.printTXSTransaktionEnde());
   
	             lvHelpString.append(lvShve.printTXSTransaktionEnde());
   
	             lvHelpString.append(lvKredsh.printTXSTransaktionEnde());     
             }
        }
	        
        return lvHelpString;
    }
    
    /**
     * Setzt Flugzeugtyp, Objektgruppe/Flugzeugart, Nutzungsart und Ertragsfaehigkeit
     * @param pvFlugzeugtyp
     * @param pvPodaten
     */
    private void setzeFlugzeugdaten(String pvFlugzeugtyp, TXSPfandobjektDaten pvPodaten)
    {        
    	//System.out.println("pvFlugzeugtyp: " + pvFlugzeugtyp);
        // Flugzeugtyp, Objektgruppe/Flugzeugart, Distanz
        if (pvFlugzeugtyp.equals("5111"))
        {
            pvPodaten.setFtyp("12");
            pvPodaten.setOgrp("204");
            pvPodaten.setDist("2");
        }
        if (pvFlugzeugtyp.equals("51121"))
        {
            pvPodaten.setFtyp("9");
            pvPodaten.setOgrp("203");
            pvPodaten.setDist("2");
        }
        if (pvFlugzeugtyp.equals("51122"))
        {
            pvPodaten.setFtyp("10");
            pvPodaten.setOgrp("203");
            pvPodaten.setDist("2");
        }
        if (pvFlugzeugtyp.equals("51123"))
        {
            pvPodaten.setFtyp("11");
            pvPodaten.setOgrp("203");
            pvPodaten.setDist("2");
        }
        if (pvFlugzeugtyp.equals("51131"))
        {
            pvPodaten.setFtyp("4");
            pvPodaten.setOgrp("202");
            pvPodaten.setDist("2");
        }
        if (pvFlugzeugtyp.equals("51132"))
        {
            pvPodaten.setFtyp("5");
            pvPodaten.setOgrp("202");
            pvPodaten.setDist("3");
        }
        if (pvFlugzeugtyp.equals("51133"))
        {
            pvPodaten.setFtyp("6");
            pvPodaten.setOgrp("202");
            pvPodaten.setDist("2");
        }
        if (pvFlugzeugtyp.equals("51134"))
        {
            pvPodaten.setFtyp("7");
            pvPodaten.setOgrp("202");
            pvPodaten.setDist("3");
        }
        if (pvFlugzeugtyp.equals("51135"))
        {
            pvPodaten.setFtyp("8");
            pvPodaten.setOgrp("202");
            pvPodaten.setDist("3");
        }
        if (pvFlugzeugtyp.equals("51141") || pvFlugzeugtyp.equals("51142"))
        {
            pvPodaten.setFtyp("15");
            pvPodaten.setOgrp("206");
            if (pvFlugzeugtyp.equals("51141"))
                pvPodaten.setDist("3");
            else
                pvPodaten.setDist("4");      
        }
        if (pvFlugzeugtyp.equals("51143") || pvFlugzeugtyp.equals("51144"))
        {
            pvPodaten.setFtyp("16");
            pvPodaten.setOgrp("206");
            if (pvFlugzeugtyp.equals("51143"))
                pvPodaten.setDist("3");
            else
                pvPodaten.setDist("4");
        }
        if (pvFlugzeugtyp.equals("51151"))
        {
            pvPodaten.setFtyp("13");
            pvPodaten.setOgrp("205");
            pvPodaten.setDist("4");
        }
        if (pvFlugzeugtyp.equals("51152"))
        {
            pvPodaten.setFtyp("14");
            pvPodaten.setOgrp("205");
            pvPodaten.setDist("4");
        }
        if (pvFlugzeugtyp.equals("5116"))
        {
            pvPodaten.setFtyp("1");
            pvPodaten.setOgrp("200");
            pvPodaten.setDist("1");
        }
        if (pvFlugzeugtyp.equals("51211") || pvFlugzeugtyp.equals("51212"))
        {
            pvPodaten.setFtyp("2");
            pvPodaten.setOgrp("201");
            if (pvFlugzeugtyp.equals("51211"))
                pvPodaten.setDist("3");
            else
                pvPodaten.setDist("4");
        }
        if (pvFlugzeugtyp.equals("51213") || pvFlugzeugtyp.equals("51214"))
        {
            pvPodaten.setFtyp("3");
            pvPodaten.setOgrp("201");
            if (pvFlugzeugtyp.equals("51213"))
                pvPodaten.setDist("3");
            else
                pvPodaten.setDist("4");
        }
        
        // Nutzungsart
        if (pvFlugzeugtyp.equals("51211") || pvFlugzeugtyp.equals("51212") ||
            pvFlugzeugtyp.equals("51213") || pvFlugzeugtyp.equals("51214"))
        {
            // Guetertransport
            pvPodaten.setNart("12");
        }
        else
        {
            // Personentransport
            pvPodaten.setNart("11");
        }
        
        // Betriebsstatus
        // Defaultmaessig 'In Betrieb' -> 1
        pvPodaten.setBstatus("1");
        
        // Ertragsfaehigkeit
        // unbekannt...  
    }
             
    /**
     * Importiert eine KreditSicherheit Schiff
     * @param pvSAPCMS
     * @param pvKontonummer
     * @param pvQuellsystem
     */
    public StringBuffer import2TXSKreditSicherheitSchiff(Sicherungsumfang pvShum, String pvKontonummer, String pvQuellsystem)
    {
        StringBuffer lvHelpString = new StringBuffer(); 
        System.out.println("import2TXSKreditSicherheitSchiffe");
        TXSKreditSicherheit lvKredsh = null;
        TXSSicherheitDaten lvShdaten = null;
        TXSSicherheitVerzeichnis lvShve = null;
        TXSVerzeichnisDaten lvVedaten = null;
        TXSVerzeichnisVBlatt lvVevb = null;
        TXSVerzeichnisblattDaten lvVbdaten = null;
        TXSVerzeichnisPfandobjekt lvVepo = null;
        TXSPfandobjektDaten lvPodaten = null;
        
        Sicherheitenvereinbarung lvSicherheitenvereinbarung = ivSAPCMS.getListeSicherheitenvereinbarung().get(pvShum.getSicherheitenvereinbarungId());
             
        BigDecimal lvZuwBetrag = StringKonverter.convertString2BigDecimal(pvShum.getZuweisungsbetrag()); 
        BigDecimal lvVerfBetrag = StringKonverter.convertString2BigDecimal(lvSicherheitenvereinbarung.getVerfuegungsbetrag()); 
                                  
         // Last suchen
        Last lvLast = null;
        Collection<Last> lvCollectionLast = ivSAPCMS.getListeLast().values();
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
                 Collection<Schiff> lvCollectionSchiff = ivSAPCMS.getListeSchiff().values();
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
                 if (lvSchiff != null)
                 {
                     if (!lvSchiff.getStatusDeckung().equals("V") && !lvSchiff.getStatusDeckung().equals("S"))
                     {   
                           continue;
                     }
                 }
               
                 lvKredsh = new TXSKreditSicherheit();
                 lvKredsh.setKey(lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
       
                 lvKredsh.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                 lvKredsh.setQuelle(pvQuellsystem);
                 lvKredsh.setZuwbetrag(lvZuwBetrag.toString());
                 lvKredsh.setWhrg(pvShum.getZuweisungsbetragWaehrung());
                 // Defaultmaessig auf 'Ja' (1)
                 lvKredsh.setHauptsh("1");
                 lvHelpCSV.append(lvZuwBetrag.toString() + ";" + pvShum.getZuweisungsbetragWaehrung() + ";" + pvShum.getWert().trim().replace(",", ".") + ";" + pvShum.getGroesse() + ";" + MappingKunde.extendKundennummer(pvShum.getGeschaeftspartnerId(), ivLogger) + ";" + pvShum.getGeschaeftsparterfunktion() + ";");
                 
                 lvShdaten = new TXSSicherheitDaten();
                 // Schiffshypothek
                 lvShdaten.setArt("56");
                 lvShdaten.setBez(String2XML.change2XML(lvSchiff.getSchiffsname()));
                 // Defaultmaessig auf 'Ja' (1)
                 lvShdaten.setGepr("1");
                 if (ivSAPCMS.getVorlaufsatz().getInstitut().equals("004"))
                 {
                	lvShdaten.setGepr(""); 
                 }
                 
                 lvShdaten.setNbetrag(lvSicherheitenvereinbarung.getNominalwert());
                 lvShdaten.setVbetrag(lvVerfBetrag.toString());
                 lvShdaten.setWhrg(lvSicherheitenvereinbarung.getVerfuegungsbetragWaehrung());
                 lvHelpCSV.append(lvSicherheitenvereinbarung.getNominalwert() + ";" + lvSicherheitenvereinbarung.getNominalwertWaehrung() + ";" + lvVerfBetrag.toString() + ";" + lvSicherheitenvereinbarung.getVerfuegungsbetragWaehrung() + ";");
       
                 lvShve = new TXSSicherheitVerzeichnis();
                 lvShve.setVenr(lvSchiff.getSchiffId() + "_" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                 lvShve.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                 lvShve.setQuelle(pvQuellsystem);
        
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
                 lvVevb.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                 lvVevb.setQuelle(pvQuellsystem);
                 
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
                 lvVepo.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                 lvVepo.setQuelle(pvQuellsystem);
        
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
                 ivLogger.info("DeepSea;DeepSea;SAPCMS;" + pvKontonummer + ";" + lvSchiff.getSchiffId() + ";" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ";" + lvHelpCSV.toString());
                
                 // TXSKreditSicherheit
                 lvHelpString.append(lvKredsh.printTXSTransaktionStart());
                 lvHelpString.append(lvKredsh.printTXSTransaktionDaten());
   
                 // TXSSicherheitDaten
                 lvHelpString.append(lvShdaten.printTXSTransaktionStart());
                 lvHelpString.append(lvShdaten.printTXSTransaktionDaten());
                 lvHelpString.append(lvShdaten.printTXSTransaktionEnde());
   
                 // TXSSicherheitVerzeichnis
                 lvHelpString.append(lvShve.printTXSTransaktionStart());
                 lvHelpString.append(lvShve.printTXSTransaktionDaten());

                 // TXSVerzeichnisDaten
                 lvHelpString.append(lvVedaten.printTXSTransaktionStart());
                 lvHelpString.append(lvVedaten.printTXSTransaktionDaten());
                 lvHelpString.append(lvVedaten.printTXSTransaktionEnde());

                 // TXSVerzeichnisBlatt 
                 lvHelpString.append(lvVevb.printTXSTransaktionStart());
                 lvHelpString.append(lvVevb.printTXSTransaktionDaten());

                 // TXSVerzeichnisblattDaten
                 lvHelpString.append(lvVbdaten.printTXSTransaktionStart());
                 lvHelpString.append(lvVbdaten.printTXSTransaktionDaten());
                 lvHelpString.append(lvVbdaten.printTXSTransaktionEnde());
   
                 lvHelpString.append(lvVevb.printTXSTransaktionEnde());
        
                 // TXSVerzeichnisPfandobjekt
                 lvHelpString.append(lvVepo.printTXSTransaktionStart());
                 lvHelpString.append(lvVepo.printTXSTransaktionDaten());
   
                 // TXSPfandobjektDaten
                 lvHelpString.append(lvPodaten.printTXSTransaktionStart());
                 lvHelpString.append(lvPodaten.printTXSTransaktionDaten());
                 lvHelpString.append(lvPodaten.printTXSTransaktionEnde());
   
                 lvHelpString.append(lvVepo.printTXSTransaktionEnde());
   
                 lvHelpString.append(lvShve.printTXSTransaktionEnde());
   
                 lvHelpString.append(lvKredsh.printTXSTransaktionEnde());      	
             }
        }
    	return lvHelpString;
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
    
    /**
     * Importiert eine KreditSicherheit Buerge
     * @param pvSAPCMS 
     * @param pvKontonummer 
     * @param pvQuellsystem
     * @param pvRestkapital
     * @param pvBuergschaftprozent
     * @param pvAusplatzierungsmerkmal
     */
    public StringBuffer import2TXSKreditSicherheitBuerge(Sicherungsumfang pvShum, String pvKontonummer, String pvQuellsystem, String pvRestkapital, String pvBuergschaftprozent, String pvAusplatzierungsmerkmal)
    {
        StringBuffer lvHelpString = new StringBuffer(); 
        ivLogger.info("import2TXSKreditSicherheitBuerge - " + pvKontonummer);

        Sicherheitenvereinbarung lvSicherheitenvereinbarung = ivSAPCMS.getListeSicherheitenvereinbarung().get(pvShum.getSicherheitenvereinbarungId());
        System.out.println("Sicherheitenvereinbarung-ID: " + lvSicherheitenvereinbarung.getId() + " - " + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ": " + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsart());
        // Buergschaft
        if (lvSicherheitenvereinbarung.getSicherheitenvereinbarungsart().startsWith("03"))
        {
            // Geschaeftspartner suchen
            Geschaeftspartner lvGeschaeftspartner = null;
            Collection<Geschaeftspartner> lvCollectionGeschaeftspartner = ivSAPCMS.getListeGeschaeftspartner().values();
            Iterator<Geschaeftspartner> lvIteratorGeschaeftspartner = lvCollectionGeschaeftspartner.iterator();
            while (lvIteratorGeschaeftspartner.hasNext())
            {
                Geschaeftspartner lvHelpGeschaeftspartner = lvIteratorGeschaeftspartner.next();
                lvGeschaeftspartner = null;
                if (lvHelpGeschaeftspartner.getSicherheitenvereinbarungId().equals(lvSicherheitenvereinbarung.getId()))
                {
                    lvGeschaeftspartner = lvHelpGeschaeftspartner;
                }
                if (lvGeschaeftspartner != null)
                {
                	//System.out.println("Kundennummer: " + lvGeschaeftspartner.getKundennummer());
                	//System.out.println("Funktion: " + lvGeschaeftspartner.getGeschaeftspartnerfunktion());
                	if (lvGeschaeftspartner.getGeschaeftspartnerfunktion().equals("050002"))
                	{
                        BigDecimal lvHelpFaktor = new BigDecimal("100.0");
                        BigDecimal lvHelpRestkapital = StringKonverter.convertString2BigDecimal(pvRestkapital);
                        //System.out.println("Buergschaftprozent: " + pvBuergschaftprozent);
                        BigDecimal lvHelpBuergschaftprozent = (StringKonverter.convertString2BigDecimal(pvBuergschaftprozent)).divide(lvHelpFaktor, 9, RoundingMode.HALF_UP);
                        //System.out.println("Zuweisungsbetrag errechnet: " + (lvHelpRestkapital.multiply(lvHelpBuergschaftprozent)).toString());
                		//ivLogger.info("MIDAS;MIDAS;" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ";" +  lvGeschaeftspartner.getKundennummer() + ";" + pvKontonummer + ";");
                        // TXSKreditSicherheit
                		TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
                		lvKredsh.setKey(lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                		lvKredsh.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                		lvKredsh.setQuelle(pvQuellsystem);
                		if (pvQuellsystem.contains("MID"))
                		{
                			if (pvAusplatzierungsmerkmal.endsWith("0"))
                			{
                				if (lvHelpRestkapital.doubleValue() > 0.0)
                				{
                					lvKredsh.setZuwbetrag((lvHelpRestkapital.multiply(lvHelpBuergschaftprozent)).toString());
                				}
                				else
                				{
                					lvKredsh.setZuwbetrag(lvHelpRestkapital.toString());
                				}
                        		lvKredsh.setWhrg(lvSicherheitenvereinbarung.getNominalwertWaehrung());
                			}
                		}
                        lvHelpString.append(lvKredsh.printTXSTransaktionStart());
                        lvHelpString.append(lvKredsh.printTXSTransaktionDaten());
     
                        // TXSSicherheitDaten
                        TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
                        lvShdaten.setArt("51");
                        lvShdaten.setPbsatz("0.0");
                        //lvShdaten.setNbetrag(lvSicherheitenvereinbarung.getNominalwert());
                        //lvShdaten.setWhrg(lvSicherheitenvereinbarung.getNominalwertWaehrung());
                        // Umstellung auf Wunsch von Kharashevich - CT 29.11.2017
                        lvShdaten.setNbetrag(lvSicherheitenvereinbarung.getAnzusetzenderWert());
                        lvShdaten.setWhrg(lvSicherheitenvereinbarung.getAnzusetzenderWertWaehrung());
                        
                        lvShdaten.setProbsatz(pvBuergschaftprozent);

                        // Verfuegungsbetrag soll gleich Nennbetrag sein - CT 30.11.2017
                        lvShdaten.setVbetrag(lvShdaten.getNbetrag());
                        //lvShdaten.setVbetrag(lvSicherheitenvereinbarung.getVerfuegungsbetrag()); 
                        //lvShdaten.setWhrg(lvSicherheitenvereinbarung.getVerfuegungsbetragWaehrung());

                        lvHelpString.append(lvShdaten.printTXSTransaktionStart());
                        lvHelpString.append(lvShdaten.printTXSTransaktionDaten());
                        lvHelpString.append(lvShdaten.printTXSTransaktionEnde());
                       
                        // TXSSicherheitPerson
                        TXSSicherheitPerson lvShperson = new TXSSicherheitPerson();
                        lvShperson.setKdnr(lvGeschaeftspartner.getKundennummer());
                        lvShperson.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                        lvShperson.setQuelle("KUNDE");
                        lvHelpString.append(lvShperson.printTXSTransaktionStart());
                        lvHelpString.append(lvShperson.printTXSTransaktionDaten());
                        lvHelpString.append(lvShperson.printTXSTransaktionEnde());
                       
                        lvHelpString.append(lvKredsh.printTXSTransaktionEnde());

                        // Logging der Buergschaften aus SAP CMS
                        StringBuffer lvHelpCSV = new StringBuffer();
                        lvHelpCSV.append("Buergschaft SAP CMS;" + pvKontonummer + ";" + lvKredsh.getKey() + ";" + lvKredsh.getQuelle() + ";" + lvKredsh.getZuwbetrag().replace(".", ",") + ";" + lvKredsh.getWhrg());
                        lvHelpCSV.append(";" + lvShdaten.getArt() + ";" + lvShdaten.getNbetrag().replace(".", ",") + ";" + lvShdaten.getVbetrag().replace(".", ",") + ";" + lvShdaten.getWhrg() + ";" + lvShdaten.getProbsatz().replace(".", ","));
                        lvHelpCSV.append(";" + lvShperson.getKdnr());
                        lvHelpCSV.append(";" + pvShum.getDeckungsregisterRelevant() + ";");
                        lvHelpCSV.append(StringKonverter.lineSeparator);
                        
                        ivLogger.info(lvHelpCSV.toString());

                	}
                }
            }
        }		
        
    	return lvHelpString;
    }

}
