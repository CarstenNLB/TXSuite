package nlb.txs.schnittstelle.Sicherheiten;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetrag;
import nlb.txs.schnittstelle.RefiRegister.RefiDeepSeaZusatz;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Beleihungssatz;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Geschaeftspartner;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Grundbuchblatt;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Grundbuchverweis;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Grundstueck;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Immobilie;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Last;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Schiff;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Sicherheitenvereinbarung;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Sicherungsumfang;
import nlb.txs.schnittstelle.Transaktion.TXSBestandsverzDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSPfandobjektDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitPerson2;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitVerzeichnis;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitZuSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisBestandsverz;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisPfandobjekt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisVBlatt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisblattDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import org.apache.log4j.Logger;

public class Sicherheiten2RefiRegister implements Sicherheiten2Register
{
    /**
     * Sicherheiten-Daten
     */
    private SicherheitenDaten ivSicherheitenDaten;

    /**
     * log4j-Logger
     */
    private Logger ivLogger;

    /**
	 * Konstruktor
	 * @param pvSicherheitenDaten
     * @param pvLogger
	 */
	public Sicherheiten2RefiRegister(SicherheitenDaten pvSicherheitenDaten, Logger pvLogger)
	{
		this.ivSicherheitenDaten = pvSicherheitenDaten;
		this.ivLogger = pvLogger;
	}

    /**
     * Importiert die Sicherheiten fuer das RefiRegister zu einer Kontonummer
     * @param pvKontonummer Kontonummer
     * @param pvPassivkontonummer Passivkontonummer
     * @param pvKundennummer Kundennummer der Konsorte
     * @param pvKredittyp Kredittyp
     * @param pvBuergschaftprozent Buergschaftprozent
     * @param pvQuellsystem Quellsystem
     * @param pvAusplatzierungsmerkmal Ausplatzierungsmerkmal
     * @return
     */ /*
    public StringBuffer importSicherheitRefiRegister(String pvKontonummer, String pvPassivkontonummer, String pvKundennummer, String pvKredittyp, String pvBuergschaftprozent, String pvQuellsystem, String pvAusplatzierungsmerkmal)
    {
        StringBuffer lvBuffer = new StringBuffer();

        Sicherungsumfang lvShum = null;

        ivLogger.info("RefiRegister - Suche Sicherheiten SicherheitObjekt zu Kontonummer " + pvKontonummer);

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvKontonummer);

        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
                lvShum = lvHelpListe.get(x);
                ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
                ivLogger.info("Buergschaft - DeckungsregisterRelevant: " + lvShum.getDeckungsregisterRelevant());

                ////if (lvShum.getDeckungsregisterRelevant().equals("01")) // Buergschaft relevant
                ////{
                ////  lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitBuerge(lvShum, pvKontonummer, pvQuellsystem, "0.01", pvBuergschaftprozent, pvAusplatzierungsmerkmal));
                ////}
                lvBuffer.append(this.import2TXSKreditSicherheit(lvShum, pvKontonummer, pvPassivkontonummer, pvKundennummer, pvKredittyp, pvBuergschaftprozent, pvQuellsystem));
                ////lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitSchiff(lvShum, pvKontonummer, "ADARLREFI"));
            }
        }
        return lvBuffer;
    } */

    /**
     * Importiert die Sicherheiten fuer das RefiRegister DeepSea zu einer Kontonummer
     * @param pvKontonummer Kontonummer
     * @param pvListeRefiDeepSea Liste RefiRegister fuer DeepSea
     * @return
     */
    /*
    public StringBuffer importSicherheitRefiRegisterDeepSea(String pvKontonummer, HashMap<String, RefiDeepSeaZusatz> pvListeRefiDeepSea)
    {
        StringBuffer lvBuffer = new StringBuffer();

        Sicherungsumfang lvShum = null;

        ivLogger.info("RefiRegisterDeepSea - Suche Sicherheiten SicherheitObjekt zu Kontonummer " + pvKontonummer);

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvKontonummer);

        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
                lvShum = lvHelpListe.get(x);
                ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());

                lvBuffer.append(this.import2TXSKreditSicherheitSchiff(lvShum, pvKontonummer, "ALIQREFI", pvListeRefiDeepSea));
            }
        }
        return lvBuffer;
    } */

    /**
     * Erstellt die TXS-Transaktionen fuer eine Sicherheit - Immobilie
     * @param pvShum
     * @param pvKontonummer 
     * @param pvPassivkontonummer 
     * @param pvKundennummer 
     * @param pvKredittyp 
     * @param pvBuergschaftprozent 
     * @param pvQuellsystem
     * @param pvInstitutsnummer
     * @return 
     * 
     */
    public StringBuffer import2TXSKreditSicherheit(Sicherungsumfang pvShum, String pvKontonummer, String pvPassivkontonummer, String pvKundennummer, String pvKredittyp, String pvBuergschaftprozent, String pvQuellsystem, String pvInstitutsnummer)
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
        
        Sicherheitenvereinbarung lvSicherheitenvereinbarung = ivSicherheitenDaten.getListeSicherheitenvereinbarung().get(pvShum.getSicherheitenvereinbarungId());
        
        BigDecimal lvBtrDivHd = new BigDecimal("0.01");
        BigDecimal lvBuergeFakt = new BigDecimal("1.0");
             
        //BigDecimal lvZuwBetrag = StringKonverter.convertString2BigDecimal(pvShum.getZuweisungsbetrag()); 
        BigDecimal lvVerfBetrag = StringKonverter.convertString2BigDecimal(lvSicherheitenvereinbarung.getVerfuegungsbetrag()); 
                                  
        BigDecimal lvVerfBetragBu = new BigDecimal("0.0");
        BigDecimal lvVerfBetragRe = new BigDecimal("0.0");
        //BigDecimal lvZuwBetragBu = new BigDecimal("0.0");
        //BigDecimal lvZuwBetragRe = new BigDecimal("0.0");
        //System.out.println("pvKredittyp: " + pvKredittyp);
        if (pvKredittyp.equals("1"))
        { // ohne Buerge .. alles 
            lvVerfBetragRe = lvVerfBetrag;
            //lvZuwBetragRe  = lvZuwBetrag;
        } // ohne Buerge .. alles 
        if (pvKredittyp.equals("2"))
        { // mit Buerge .. anteilig 
            if (StringKonverter.convertString2Double(pvBuergschaftprozent) == 0.0)
            { // nichts da
                lvVerfBetragRe = lvVerfBetrag;
                //lvZuwBetragRe  = lvZuwBetrag;
            } // nichts da 
            else
            { // etwas da
                lvBuergeFakt = lvBtrDivHd.multiply(StringKonverter.convertString2BigDecimal(pvBuergschaftprozent));
                 lvVerfBetragBu = lvVerfBetrag.multiply(lvBuergeFakt);
                 lvVerfBetragRe = lvVerfBetrag.subtract(lvVerfBetragBu);
                 //lvZuwBetragBu  = lvZuwBetrag.multiply(lvBuergeFakt);
                 //lvZuwBetragRe  = lvZuwBetrag.subtract(lvZuwBetragBu);
            } // etwas da 
        } // mit Buerge .. anteilig
                          
        // Last suchen
        Last lvLast = null;
        Collection<Last> lvCollectionLast = ivSicherheitenDaten.getListeLast().values();
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
                 Collection<Immobilie> lvCollectionImmobilie = ivSicherheitenDaten.getListeImmobilie().values();
                 Iterator<Immobilie> lvIteratorImmobilie = lvCollectionImmobilie.iterator();
                 while (lvIteratorImmobilie.hasNext())
                 {
                     Immobilie lvHelpImmobilie = lvIteratorImmobilie.next();
                     if (lvHelpImmobilie.getId().equals(lvLast.getImmobilieId()))
                     {
                         lvImmobilie = lvHelpImmobilie;
                     }
                 }
             
                 // Wenn Deckungskennzeichen nicht 'D', nicht 'F' oder nicht 'R' ist, dann leeren StringBuffer zurueckliefern
                 if (lvImmobilie != null)
                 {
                	   ivLogger.info("Immo-Deckungskennzeichen - " + lvImmobilie.getImmobilienobjektId() + ": " + lvImmobilie.getDeckungskennzeichen());
                     if (!lvImmobilie.getDeckungskennzeichen().equals("F") && !lvImmobilie.getDeckungskennzeichen().equals("D") && !lvImmobilie.getDeckungskennzeichen().equals("R"))
                     {   
                    	 continue;
                     }
                 }
                 else
                 {
                	 continue;
                 }
                 
                 ivLogger.info("TXSKreditSicherheit - Start");
             
                 lvSh = new TXSSicherheit();
                 lvSh.setKey(lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                 lvSh.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
                 lvSh.setQuelle(pvQuellsystem);
                  
                 // TXSSicherheitDaten
                 lvShdaten = new TXSSicherheitDaten();

                 lvShdaten.setArt(ValueMapping.mapSicherheitenArt(lvImmobilie.getLaenderschluessel(), lvSicherheitenvereinbarung.getSicherheitenvereinbarungsart(), lvSicherheitenvereinbarung.getGesamtgrundschuldKennzeichen()));
                 lvShdaten.setNbetrag(lvSicherheitenvereinbarung.getNominalwert());
                 
                 //System.out.println("lvVerfBetragRe: " + lvVerfBetragRe.toString());
                 //System.out.println("lvVerfBetragBu: " + lvVerfBetragBu.toString());
                 
                 if (pvKredittyp.equals("2"))
                 {
                     lvShdaten.setVbetrag(lvVerfBetragBu.toString());       
                 }
                 else
                 {
                     lvShdaten.setVbetrag(lvVerfBetragRe.toString());
                 }
                 lvShdaten.setWhrg(lvSicherheitenvereinbarung.getNominalwertWaehrung());
     
                 // TXSKreditSicherheit ausgeben
                 lvHelpString.append(lvSh.printTXSTransaktionStart());
                 lvHelpString.append(lvSh.printTXSTransaktionDaten());                     
                 lvHelpString.append(lvShdaten.printTXSTransaktionStart());
                 lvHelpString.append(lvShdaten.printTXSTransaktionDaten());
                 lvHelpString.append(lvShdaten.printTXSTransaktionEnde());
                    
                 Geschaeftspartner lvGeschaeftspartner = null;

                 TXSSicherheitPerson2 lvHelpPerson = new TXSSicherheitPerson2();
                 // Geschaeftspartner suchen
                 Collection<Geschaeftspartner> lvCollectionGeschaeftspartner = ivSicherheitenDaten.getListeGeschaeftspartner().values();
                 Iterator<Geschaeftspartner> lvIteratorGeschaeftspartner = lvCollectionGeschaeftspartner.iterator();
                 while (lvIteratorGeschaeftspartner.hasNext())
                 {
                	 Geschaeftspartner lvHelpGeschaeftspartner = lvIteratorGeschaeftspartner.next();
                	 if (lvHelpGeschaeftspartner.getSicherheitenvereinbarungId().equals(lvSicherheitenvereinbarung.getId()))
                	 {
                		 lvGeschaeftspartner = lvHelpGeschaeftspartner;
                	 }
                 }
                 lvHelpPerson.setKdnr(lvGeschaeftspartner.getKundennummer());
                 lvHelpPerson.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
                 lvHelpPerson.setQuelle("KUNDE");
                 lvHelpPerson.setRolle("2");
                 lvHelpString.append(lvHelpPerson.printTXSTransaktionStart());
                 lvHelpString.append(lvHelpPerson.printTXSTransaktionDaten());
                 lvHelpString.append(lvHelpPerson.printTXSTransaktionEnde());
                 
                 // Grundpfandr. Auslandsimmo -> Kein Grundbucheintrag
                 if (lvShdaten.getArt().equalsIgnoreCase("19"))
                 {
                     lvHelpString.append(importGrundpfandrechtAuslandsimmo(pvShum, lvSicherheitenvereinbarung, lvImmobilie, lvLast, lvBuergeFakt, pvQuellsystem));
                 }
             
                 // TXSSicherheitVerzeichnis
                 // Beleihungssatz suchen
                 HashMap<String, Grundstueck> lvHelpListeGrundstueck = new HashMap<String, Grundstueck>();
                 HashMap<String, Grundbuchblatt> lvHelpListeGrundbuchblatt = new HashMap<String, Grundbuchblatt>();
                 Beleihungssatz lvBeleihungssatz = null;
                 Collection<Beleihungssatz> lvCollectionBeleihungssatz = ivSicherheitenDaten.getListeBeleihungssatz().values();
                 Iterator<Beleihungssatz> lvIteratorBeleihungssatz = lvCollectionBeleihungssatz.iterator();
                 while (lvIteratorBeleihungssatz.hasNext())
                 {
                     Beleihungssatz lvHelpBeleihungssatz = lvIteratorBeleihungssatz.next();
                     if (lvHelpBeleihungssatz.getObjektteilId().equals(lvImmobilie.getObjektteilId()))
                     {
                         lvBeleihungssatz = lvHelpBeleihungssatz;
                         Grundstueck lvGrundstueck = ivSicherheitenDaten.getListeGrundstueck().get(lvBeleihungssatz.getGrundstueckId());
                         if (lvGrundstueck != null)
                         {
                           if (!lvHelpListeGrundstueck.containsKey(lvGrundstueck.getId()))
                           {
                             lvHelpListeGrundstueck.put(lvGrundstueck.getId(), lvGrundstueck);
                           }
                        
                           Grundbuchblatt lvGrundbuchblatt = ivSicherheitenDaten.getListeGrundbuchblatt().get(lvGrundstueck.getGrundbuchblattId());
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
                 Collection<Grundbuchverweis> lvCollectionGrundbuchverweis = ivSicherheitenDaten.getListeGrundbuchverweis().values();
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
                     Grundbuchblatt lvGrundbuchblatt = lvIteratorGrundbuchblatt.next();
                     lvCollectionGrundbuchverweis = ivSicherheitenDaten.getListeGrundbuchverweis().values();
                     lvIteratorGrundbuchverweis = lvCollectionGrundbuchverweis.iterator();
                     while (lvIteratorGrundbuchverweis.hasNext())
                     { 
                         Grundbuchverweis lvHelpGrundbuchverweis = lvIteratorGrundbuchverweis.next();
                         if (lvHelpGrundbuchverweis.getLastId().equals(lvLast.getId()) &&
                             lvHelpGrundbuchverweis.getGrundbuchblattId().equals(lvGrundbuchblatt.getId()))
                         {
                             lvAlleKeinenGrundbuchverweis = false;
                         }
                     }              
                 }
                 
                 // Bis jetzt verteilte Summe ZuweisungsbetragPO
                 BigDecimal lvSummeZuweisungsbetragPO = new BigDecimal("0.0");
                 // Anzahl erledigter Grundbuchblaetter
                 int lvAnzahlErledigterGrundbuchblaetter = 0;
                 int lvAnzahlGrundbuchblaetter = 0;
                 // CT 04.08.2016
                 lvCollectionGrundbuchblatt = lvHelpListeGrundbuchblatt.values();
                 lvIteratorGrundbuchblatt = lvCollectionGrundbuchblatt.iterator();

                 while (lvIteratorGrundbuchblatt.hasNext())
                 {
                     Grundbuchblatt lvGrundbuchblatt = lvIteratorGrundbuchblatt.next();
                     // Grundbuchblatt ueber Grundbuchverweis ermitteln
                     // Grundbuchverweis per Last-ID suchen
                     Grundbuchverweis lvGrundbuchverweis = null;
                     lvCollectionGrundbuchverweis = ivSicherheitenDaten.getListeGrundbuchverweis().values();
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
                 // CT 04.08.2016
                 
                 lvCollectionGrundbuchblatt = lvHelpListeGrundbuchblatt.values();
                 lvIteratorGrundbuchblatt = lvCollectionGrundbuchblatt.iterator();

                 while (lvIteratorGrundbuchblatt.hasNext())
                 {
                     StringBuffer lvHelpStringGrundbuchblatt = new StringBuffer();
                     Grundbuchblatt lvGrundbuchblatt = lvIteratorGrundbuchblatt.next();
                     ivLogger.info("Last: " + lvLast.getId() + " Grundbuchblatt: " + lvGrundbuchblatt.getId());
                     
                     // Grundbuchblatt ueber Grundbuchverweis ermitteln
                     // Grundbuchverweis per Last-ID suchen
                     Grundbuchverweis lvGrundbuchverweis = null;
                     lvCollectionGrundbuchverweis = ivSicherheitenDaten.getListeGrundbuchverweis().values();
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
                  
                     // Grundbuchverweis == null, dann Last-ID und Grundbuchblatt-ID protokollieren
                     if (lvGrundbuchverweis == null)
                     {
                         ivLogger.error("Konnte keinen Grundbuchverweis fuer Last " + lvLast.getId() + " und Grundbuchblatt " + lvGrundbuchblatt.getId() + " ermitteln!");
                         //System.out.println("lvAlleKeinenGrundbuchverweis: " + lvAlleKeinenGrundbuchverweis);
                         if (!lvAlleKeinenGrundbuchverweis)
                         {
                             continue;
                         }
                     }
 
                     lvShve = new TXSSicherheitVerzeichnis();
                     // BLB verwendet die ID des Grundbuchverweis
                     if (pvInstitutsnummer.equals("004"))
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
                     if (pvInstitutsnummer.equals("009"))
                     {
                         if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                         {
                             lvShve.setVenr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3());  
                        	 ////lvShve.setVenr(lvGrundbuchverweis.getId());
                         }
                         else
                         {
                             lvShve.setVenr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer());
                         }
                     }
                      
                     if (lvShve.getVenr().length() > 40)
                     {
                         lvShve.setVenr(lvShve.getVenr().substring(0, 39));
                     }
                   
                     if (pvInstitutsnummer.equals("009"))
                     {
                         if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                         {
                             ivLogger.info("TXSSicherheitVerzeichnis Venr;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3() + ";" + lvGrundbuchverweis.getId());
                         }

                     }                   
                     
                     lvShve.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                             {
                               lvHelpBetrag = (new BigDecimal(lvLast.getVerfuegungsbetrag())).subtract(lvSummeZuweisungsbetragPO); 
                             }
                             else
                             {
                               ivLogger.info("Last-Verfuegungsbetrag: " + lvLast.getVerfuegungsbetrag());
                               ivLogger.info("Anzahl Grundbuchblaetter: " + lvAnzahlGrundbuchblaetter);
                               lvHelpBetrag = (new BigDecimal(lvLast.getVerfuegungsbetrag())).divide(new BigDecimal(lvAnzahlGrundbuchblaetter), 2, RoundingMode.HALF_UP);
                             }
                            
                             if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                             {
                                 ivLogger.info("ZuweisungsbetragPO-GBverweis;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3() + ";" + lvHelpBetrag.toString());
                     
                             }
                             else
                             {
                                 ivLogger.info("ZuweisungsbetragPO-Last;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer() + ";" + lvHelpBetrag.toString());
                             }
                             lvVedaten.setBetrag(lvHelpBetrag.toString());
                             
                             lvSummeZuweisungsbetragPO = lvSummeZuweisungsbetragPO.add(lvHelpBetrag);
                         }
                         else
                         {
                           lvVedaten.setBetrag(lvLast.getVerfuegungsbetrag());
                         }
                     }
                     else
                     {
                         ObjektZuweisungsbetrag lvOzw = ivSicherheitenDaten.getObjektZuweisungsbetragListe().getObjektZuweisungsbetrag(pvShum.getSicherheitenvereinbarungId());
                         if (lvOzw != null)
                         {
                             lvVedaten.setBetrag(lvOzw.getZuweisungsbetrag().toString());
                         }
                     }
                                  
                     lvVedaten.setWhrg(pvShum.getZuweisungsbetragWaehrung());
                     
                     // Beim RefiRegister '0.01 EUR' liefern
                     lvVedaten.setBetrag("0.01");
                     lvVedaten.setWhrg("EUR");
                                      
                     lvHelpStringGrundbuchblatt.append(lvVedaten.printTXSTransaktionStart());
                     lvHelpStringGrundbuchblatt.append(lvVedaten.printTXSTransaktionDaten());
                     lvHelpStringGrundbuchblatt.append(lvVedaten.printTXSTransaktionEnde());
                     
                     // TXSVerzeichnisVBlatt
                     lvVevb = new TXSVerzeichnisVBlatt();
                     // BLB verwendet die ID des Grundbuchverweis
                     if (pvInstitutsnummer.equals("004"))
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
                     if (pvInstitutsnummer.equals("009"))
                     {
                         if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                         {
                             lvVevb.setVbnr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3());
                         }
                         else
                         {
                             lvVevb.setVbnr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer());
                         }
                     }

                     if (lvVevb.getVbnr().length() > 40)
                     {
                         lvVevb.setVbnr(lvVevb.getVbnr().substring(0, 39));
                     }
                     
                     if (pvInstitutsnummer.equals("009"))
                     {
                         if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                         {
                             ivLogger.info("TXSVerzeichnisVBlatt Vbnr;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3() + ";" + lvGrundbuchverweis.getId());
                         }
                     }


                     lvVevb.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                     lvVbdaten.setGbvon(lvVbdaten.getGbvon() + "; Amtsgericht: " + lvGrundbuchblatt.getAmtsgericht());
                     lvVbdaten.setGericht(lvGrundbuchblatt.getAmtsgericht());
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
                             // BLB verwendet die ID des Grundstuecks
                             if (pvInstitutsnummer.equals("004"))
                             {
                                 lvVebv.setBvnr(lvGrundstueck.getId());                                 
                             }
                             // NLB verwendet einen zusammengesetzten Schluessel
                             if (pvInstitutsnummer.equals("009"))
                             {
                                 if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                                 {
                                     lvVebv.setBvnr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3()
                                                    + lvGrundstueck.getLaufendeNummer());
                                 }
                                 else
                                 {
                                     lvVebv.setBvnr(lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer()
                                                    + lvGrundstueck.getLaufendeNummer());
                                 }
                                 
                             }     
                             
                             if (lvVebv.getBvnr().length() > 40)
                             {
                                 lvVebv.setBvnr(lvVebv.getBvnr().substring(0, 39));
                             }
                             
                             if (pvInstitutsnummer.equals("009"))
                             {
                                 if (lvVerweisVorhanden && lvGrundbuchverweis != null)
                                 {
                                     ivLogger.info("TXSVerzeichnisBestandsverz Bvnr;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3()
                                                                + lvGrundstueck.getLaufendeNummer() + ";" + lvGrundstueck.getId());

                                 }
                                 else
                                 {
                                     ivLogger.info("TXSVerzeichnisBestandsverz Bvnr;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer()
                                                                + lvGrundstueck.getLaufendeNummer() + ";" + lvGrundstueck.getId());
                                 }
                             }
                     
                             lvVebv.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
                             lvVebv.setQuelle(pvQuellsystem); 
                             lvHelpStringGrundbuchblatt.append(lvVebv.printTXSTransaktionStart());
                             lvHelpStringGrundbuchblatt.append(lvVebv.printTXSTransaktionDaten());
                             // TXSBestandsverzDaten
                             lvBvdaten = new TXSBestandsverzDaten();
                             lvBvdaten.setFlur(lvGrundstueck.getFlur());
                             if (lvBvdaten.getFlur().isEmpty())
                             {
                            	 lvBvdaten.setFlur("n.n.");   
                             }
                             lvBvdaten.setFlurst(lvGrundstueck.getFlurstueck());
                             lvBvdaten.setLfdnr(lvGrundstueck.getLaufendeNummer());
                             if (lvGrundbuchblatt.getBlatt().isEmpty() && !lvGrundstueck.getFlur().isEmpty())
                             {
                                 lvBvdaten.setGem(lvGrundstueck.getGemarkung());
                             }
                             
                             if (lvGrundstueck.getGemarkung().isEmpty())
                             {
                            	 lvBvdaten.setGem("n.n.");
                             }
                             else
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
                     lvVepo.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
                     lvVepo.setQuelle(pvQuellsystem);   
                     lvHelpStringGrundbuchblatt.append(lvVepo.printTXSTransaktionStart());
                     lvHelpStringGrundbuchblatt.append(lvVepo.printTXSTransaktionDaten());
                     // TXSPfandobjektDaten
                     lvPodaten = new TXSPfandobjektDaten();
                 
                     // Bestueckung von Pfandobjekten etc... 
                     if (lvImmobilie != null)
                     { // Es ist etwas da .... 
                         lvPodaten.setEigtyp(ValueMapping.mapEigentumstyp(lvImmobilie.getNutzung()));
                  
                         // Ertragsfaehigkeit - CT 09.07.2012
                         // Defaultmaessig '3' - voll ertragsfaehig
                         lvPodaten.setErtragsf("3");
                         // CT - 09.07.2012
                         if (!lvImmobilie.getRegion().isEmpty())
                         {
                             lvPodaten.setGebiet(lvImmobilie.getLaenderschluessel() + "_" + lvImmobilie.getRegion());
                         }
                
                         if (lvImmobilie.getLaenderschluessel().equals("DE"))
                         {
                        	 lvPodaten.setLand(lvImmobilie.getLaenderschluessel());
                         }
                         
                         if (lvImmobilie.getFertigstellungsdatum().length() > 4)
                             lvPodaten.setJahr(ValueMapping.changeBaujahr(lvImmobilie.getFertigstellungsdatum()));
                 
                         lvPodaten.setKat("1");
                         
                         lvPodaten.setSwert(lvImmobilie.getSachwert());
                         lvPodaten.setEwert(lvImmobilie.getErtragswert());
                   
                         lvPodaten.setBwert(lvImmobilie.getBeleihungswert());
                         ////lvPodaten.setBwertdat(DatumUtilities.changeDate(lvImmobilie.getFestsetzungsdatum()));
                         
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
                  
                         // CT 08.10.2015 - SAP CMS Auspraegungen verwenden
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
                         
                         // RefiRegister: Keine Adresse wenn entweder Flur oder Flurstueck gefuellt
                         if (lvBvdaten.getFlur().length() > 0 || lvBvdaten.getFlurst().length() > 0)
                         {
                        	 lvPodaten.setKadr("1");
                         }
                 
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
                     lvZugsh.setQuelle(pvQuellsystem);
                     lvZugsh.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
     * Erstellt die TXS-Transaktionen eines Grundpfandrechtes von Auslandsimmobilien
     * @param pvShum
     * @param pvSicherheitenvereinbarung
     * @param pvImmobilie
     * @param pvLast
     * @param pvBuergeFakt
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
        lvShve.setOrg(ValueMapping.changeMandant(ivSicherheitenDaten.getVorlaufsatz().getInstitut()));
        lvShve.setQuelle(pvQuellsystem);   
        lvHelpString.append(lvShve.printTXSTransaktionStart());
        lvHelpString.append(lvShve.printTXSTransaktionDaten());

        // TXSVerzeichnisDaten
        TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
        lvVedaten.setGbart("100");
        lvVedaten.setAsichr(pvImmobilie.getBemerkung());

        //System.out.println(pvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ": " + pvSicherheitenvereinbarung.getGesamtgrundschuldKennzeichen());
        if (pvSicherheitenvereinbarung.getGesamtgrundschuldKennzeichen().equals("X"))
        {
            lvVedaten.setBetrag(pvLast.getVerfuegungsbetrag()); //lvSicherheitenvereinbarung.getVerfuegungsbetrag());
            lvVedaten.setWhrg(pvLast.getVerfuegungsbetragWaehrung());
        }
        else
        {
        	//System.out.println("Size: " + ivSAPCMS.getObjektZuweisungsbetragListe().size());
        	//System.out.println("ID: " + pvShum.getSicherheitenvereinbarungId());
            ObjektZuweisungsbetrag lvOzw = ivSicherheitenDaten.getObjektZuweisungsbetragListe().getObjektZuweisungsbetrag(pvShum.getSicherheitenvereinbarungId());
            if (lvOzw != null)
            {
            	//System.out.println("Zuweisungsbetrag: " + lvOzw.getZuweisungsbetrag() + " pvBuergeFakt: " + pvBuergeFakt);
                lvVedaten.setBetrag((lvOzw.getZuweisungsbetrag().multiply(pvBuergeFakt)).toString());
                lvVedaten.setWhrg(pvShum.getZuweisungsbetragWaehrung());
           }
        }
        lvVedaten.setBetrag("0.01");
        lvVedaten.setWhrg("EUR");

        lvHelpString.append(lvVedaten.printTXSTransaktionStart());
        lvHelpString.append(lvVedaten.printTXSTransaktionDaten());
        lvHelpString.append(lvVedaten.printTXSTransaktionEnde());

        // TXSVerzeichnisPfandobjekt
        TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
        lvVepo.setObjnr(pvImmobilie.getObjektId());
        lvVepo.setOrg(ValueMapping.changeMandant(ivSicherheitenDaten.getVorlaufsatz().getInstitut()));
        lvVepo.setQuelle(pvQuellsystem);  
        lvHelpString.append(lvVepo.printTXSTransaktionStart());
        lvHelpString.append(lvVepo.printTXSTransaktionDaten());
        // TXSPfandobjektDaten
        TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();

        // Bestueckung von Pfandobjekten
        if (pvImmobilie != null)
        { // Es ist etwas da .... 
            //String lvStaat = new String();
            String lvEigentumsTyp = new String();

            //lvStaat = ValueMapping.mapLand(pvImmobilie.getLaenderschluessel());
            //if (lvStaat.isEmpty())
            //{ // Default 
            //    lvStaat = "100";
            //}

            // Ertragsfaehigkeit - CT 09.07.2012
            // Defaultmaessig '3' - voll ertragsfaehig
            String lvErtragsKz = "3";

            lvEigentumsTyp = ValueMapping.mapSP_Eigentumstyp(pvImmobilie.getObjektartId(), pvImmobilie.getGradBaufertigstellung(), pvImmobilie.getNutzung(), pvImmobilie.getNutzungsart(), pvImmobilie.getProzentanteil());

            lvPodaten.setEigtyp(ValueMapping.mapEigentumstyp(pvImmobilie.getNutzung()));

            lvPodaten.setErtragsf(lvErtragsKz);             

            // CT - 09.07.2012
            if (!pvImmobilie.getRegion().isEmpty())
            {
                lvPodaten.setGebiet(pvImmobilie.getLaenderschluessel() + "_" + pvImmobilie.getRegion());
            }

            if (pvImmobilie.getFertigstellungsdatum().length() > 4)
            {
                lvPodaten.setJahr(ValueMapping.changeBaujahr(pvImmobilie.getFertigstellungsdatum()));
            }
            lvPodaten.setKat("1");
            lvPodaten.setSwert(pvImmobilie.getSachwert());
            lvPodaten.setEwert(pvImmobilie.getErtragswert());
            lvPodaten.setBwert(pvImmobilie.getBeleihungswert());
            ////lvPodaten.setBwertdat(DatumUtilities.changeDate(pvImmobilie.getFestsetzungsdatum()));
            
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
     * Erstellt die TXS-Transaktionen fuer eine Sicherheit - Schiff
     * @param pvShum
     * @param pvKontonummer
     * @param pvQuellsystem
     * @param pvListeRefiDeepSeaZusatz
     */
    public StringBuffer import2TXSKreditSicherheitSchiff(Sicherungsumfang pvShum, String pvKontonummer, String pvQuellsystem, HashMap<String, RefiDeepSeaZusatz> pvListeRefiDeepSeaZusatz)
    {
        StringBuffer lvHelpString = new StringBuffer(); 
        ivLogger.info("RefiRegister: import2TXSKreditSicherheitSchiffe");
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
                 if (lvSchiff != null)
                 {
                     if (!lvSchiff.getStatusDeckung().equals("V") && !lvSchiff.getStatusDeckung().equals("S"))
                     {   
                    	   // Auskommentiert fuer DeepSea
                           //continue;
                     }
                 }
                 else
                 {
                	 ivLogger.error("Kein Schiff vorhanden;" + pvKontonummer + ";" + lvSicherheitenvereinbarung.getId());
                	 return new StringBuffer();
                 }
               
                 lvKredsh = new TXSKreditSicherheit();
                 lvKredsh.setKey(lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + "_" + pvKontonummer);
       
                 lvKredsh.setOrg(ValueMapping.changeMandant(ivSicherheitenDaten.getVorlaufsatz().getInstitut()));
                 lvKredsh.setQuelle(pvQuellsystem);
                 lvKredsh.setZuwbetrag(lvZuwBetrag.toString());
                 lvKredsh.setWhrg(pvShum.getZuweisungsbetragWaehrung());
                 // Defaultmaessig auf 'Ja' (1)
                 lvKredsh.setHauptsh("1");
                 lvKredsh.setZuwbetrag("0.01");
                 lvKredsh.setWhrg("EUR");
                 
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
                 lvShdaten.setWhrg(lvSicherheitenvereinbarung.getNominalwertWaehrung()); // CT 10.01.2019 reingenommen
                 //lvShdaten.setVbetrag(lvVerfBetrag.toString());
                 //lvShdaten.setWhrg(lvSicherheitenvereinbarung.getVerfuegungsbetragWaehrung());
                 lvShdaten.setVbetrag("0.01");
                 //lvShdaten.setWhrg("EUR"); // CT 10.01.2019 rausgenommen
                 
                 //System.out.println("Anzahl RefiDeepSeaZusatz: " + pvListeRefiDeepSeaZusatz.size());
                 System.out.println("get: " + pvKontonummer + "_" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId()); //lvSicherheitenId);
                 RefiDeepSeaZusatz lvRefiDeepSeaZusatz = pvListeRefiDeepSeaZusatz.get(pvKontonummer + "_" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                 if (lvRefiDeepSeaZusatz != null)
                 {
                 	lvShdaten.setRgrund(String2XML.change2XML(lvRefiDeepSeaZusatz.getRechtlicherGrund()));
                 	lvShdaten.setRgrunddat(DatumUtilities.changeDate(lvRefiDeepSeaZusatz.getRechtlicherGrundDatum()));
                 	lvShdaten.setDsepsichzerkl(DatumUtilities.changeDate(lvRefiDeepSeaZusatz.getRechtlicherGrundDatum()));                           
                 	lvShdaten.setOrigsichant("100.00");
                 	if (!lvRefiDeepSeaZusatz.getKonsortialanteil().trim().isEmpty())
                 	{
                 	  lvShdaten.setOrigsichant(lvRefiDeepSeaZusatz.getKonsortialanteil().replace(",", "."));
                 	}
                 	//lvShdaten.setReginfo(String2XML.change2XML(lvRefiDeepSeaZusatz.getBemerkung()));
                 }
       
                 lvShve = new TXSSicherheitVerzeichnis();
                 lvShve.setVenr(lvSchiff.getSchiffId() + "_" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId()+ "_" + pvKontonummer);
                 lvShve.setOrg(ValueMapping.changeMandant(ivSicherheitenDaten.getVorlaufsatz().getInstitut()));
                 lvShve.setQuelle(pvQuellsystem);
        
                 lvVedaten = new TXSVerzeichnisDaten();
                 lvVbdaten = new TXSVerzeichnisblattDaten();
                 lvVevb = new TXSVerzeichnisVBlatt();

                 // ZuweisungsbetragPO
                 //if (ivSAPCMS.getVorlaufsatz().getInstitut().equals("009"))
                 //{
                	 //lvVedaten.setBetrag(lvLast.getVerfuegungsbetrag());
                	 //lvVedaten.setWhrg(lvLast.getVerfuegungsbetragWaehrung());
                     lvVedaten.setBetrag("0.01");
                     lvVedaten.setWhrg("EUR");
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
                 }
                 else
                 {
                	 lvVedaten.setGbart("201");
                 }
                 if (lvLast.getRegisternummer().equals("N.R."))
                 {
                     lvVedaten.setNrabt(lvLast.getRegisternummer().toLowerCase());  
                 }
                 else
                 {
                   lvVedaten.setNrabt(lvLast.getRegisternummer());    
                 }
                 lvVedaten.setKat("2");
                 lvVedaten.setRang(lvLast.getRangfolge());

                 lvVevb.setVbnr(lvSchiff.getSchiffId() + "_" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + "_" + pvKontonummer);
                 lvVevb.setOrg(ValueMapping.changeMandant(ivSicherheitenDaten.getVorlaufsatz().getInstitut()));
                 lvVevb.setQuelle(pvQuellsystem);
                 
                 lvVbdaten.setKat("2");
                 lvVbdaten.setGbvon(lvSchiff.getRegisterort());
                 /* String lvRegisterland = lvSchiff.getRegisterLand();
                 //ivSAPCMS.getLogger().info("Registerort;" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ";" + lvSchiff.getSchiffId() + ";" + lvSchiff.getRegisterort() + ";" + lvSchiff.getRegisterLand());
                 if (lvSchiff.getRegisterLand().equals("DE"))
                 {
                   lvRegisterland = "Bundesrepublik Deutschland";
                 }
                 if (lvSchiff.getRegisterLand().equals("NL"))
                 {
                	 lvRegisterland = "Niederlande";
                 }
                 if (lvSchiff.getRegisterLand().equals("PA"))
                 {
                	 lvRegisterland = "Panama";
                 }
                 if (lvSchiff.getRegisterLand().equals("MT"))
                 {
                	 lvRegisterland = "Malta";
                 } */
                if (!lvSchiff.getRegisterblatt().isEmpty())
                 {
                	 ////if (lvSchiff.getRegisterLand().equals("DE"))
                	 ////{
                	 ////	 lvVbdaten.setBlatt("Blatt " + lvSchiff.getRegisterblatt());    
                	 ////}
                	 ////else
                	 ////{
                		 lvVbdaten.setBlatt(lvSchiff.getRegisterblatt());
                	 ////}
                 }
                 else
                 {
                	 lvVbdaten.setBlatt("n.r.");
                 }
                 
                 ////if (lvRefiDeepSeaZusatz != null)
                 ////{
                	 ////if (lvSchiff.getRegisterLand().equals("DE"))
                	 ////{
                	 ////	 lvVbdaten.setBlatt("Blatt " + lvRefiDeepSeaZusatz.getSchiffsregister());    
                	 ////}
                	 ////else
                	 ////{
                		 ////lvVbdaten.setBlatt(lvRefiDeepSeaZusatz.getSchiffsregister());
                	 ////}                	
                 ////}


                 lvVepo = new TXSVerzeichnisPfandobjekt();
                 lvVepo.setObjnr(lvSchiff.getSchiffId());
                 lvVepo.setOrg(ValueMapping.changeMandant(ivSicherheitenDaten.getVorlaufsatz().getInstitut()));
                 lvVepo.setQuelle(pvQuellsystem);
        
                 lvPodaten = new TXSPfandobjektDaten();
                 lvPodaten.setBez(String2XML.change2XML(lvSchiff.getSchiffsname()));
                 // CT 21.02.2017 Gefaellt mir nicht, aber wer fragt mich schon... :-)
                 //lvPodaten.setBez(lvPodaten.getBez() + ", " + lvSchiff.getIMONummer());
                 lvPodaten.setImo(lvSchiff.getIMONummer()); // Das richtige Feld auskommentiert.
                 ////lvPodaten.setImo(lvVbdaten.getBlatt());
                 // CT 21.02.2017 Gefaellt mir nicht, aber wer fragt mich schon... :-)                 
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
     *
     * @param pvKontonummer Kontonummer
     * @param pvPassivkontonummer Passivkontonummer
     * @param pvKundennummer Kundennummer
     * @param pvKredittyp Kredittyp
     * @param pvBuergschaftprozent Buergschaftprozent
     * @param pvQuellsystem Quellsystem
     * @param pvInstitutsnummer Institutsnummer
     * @return
     */
    @Override
    public StringBuffer importSicherheitHypotheken(String pvKontonummer, String pvPassivkontonummer, String pvKundennummer,
        String pvKredittyp, String pvBuergschaftprozent, String pvQuellsystem, String pvInstitutsnummer) {
        StringBuffer lvBuffer = new StringBuffer();

        Sicherungsumfang lvShum = null;

        ivLogger.info("RefiRegister - Suche Sicherheiten SicherheitObjekt zu Kontonummer " + pvKontonummer);

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvKontonummer);

        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
                lvShum = lvHelpListe.get(x);
                ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
                ////ivLogger.info("Buergschaft - DeckungsregisterRelevant: " + lvShum.getDeckungsregisterRelevant());

                ////if (lvShum.getDeckungsregisterRelevant().equals("01")) // Buergschaft relevant
                ////{
                ////  lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitBuerge(lvShum, pvKontonummer, pvQuellsystem, "0.01", pvBuergschaftprozent, pvAusplatzierungsmerkmal));
                ////}
                lvBuffer.append(this.import2TXSKreditSicherheit(lvShum, pvKontonummer, pvPassivkontonummer, pvKundennummer, pvKredittyp, pvBuergschaftprozent, pvQuellsystem, pvInstitutsnummer));
                ////lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitSchiff(lvShum, pvKontonummer, "ADARLREFI"));
            }
        }
        return lvBuffer;
    }

    /**
     * @param pvKontonummer Kontonummer
     * @param pvQuellsystem Quellsystem
     * @param pvRestkapital Restkapital
     * @param pvBuergschaftprozent Buergschaftprozent
     * @param pvAusplatzierungsmerkmal Ausplatzierungsmerkmal
     * @param pvNominalbetrag Nominalbetrag
     * @param pvKundennummer Kundennummer
     * @param pvBuergennummer Buergennummer
     * @param pvInstitutsnummer Institutsnummer
     * @return
     */
    @Override
    public StringBuffer importSicherheitBuergschaft(String pvKontonummer, String pvQuellsystem,
        String pvRestkapital, String pvBuergschaftprozent, String pvAusplatzierungsmerkmal,
        String pvNominalbetrag, String pvKundennummer, String pvBuergennummer, String pvInstitutsnummer) {
        return new StringBuffer();
    }

    /**
     *
     * @param pvKontonummer Kontonummer
     * @param pvQuellsystem Quellsystem
     * @param pvInstitut Institutsnummer (009/004 oder NLB/BLB)
     * @return
     */
    @Override
    public StringBuffer importSicherheitSchiff(String pvKontonummer, String pvQuellsystem,
        String pvInstitut) {
        return new StringBuffer();
    }

    /**
     *
     * @param pvKontonummer Kontonummer
     * @param pvQuellsystem Quellsystem
     * @param pvInstitut Institutsnummer (009/004 oder NLB/BLB)
     * @return
     */
    @Override
    public StringBuffer importSicherheitFlugzeug(String pvKontonummer, String pvQuellsystem,
        String pvInstitut) {
        return new StringBuffer();
    }
}
