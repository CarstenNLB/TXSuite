package nlb.txs.schnittstelle.Sicherheiten;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetrag;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Beleihungssatz;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Flugzeug;
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
import org.apache.log4j.Logger;

public class Sicherheiten2Pfandbrief implements Sicherheiten2Register
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
	 */
	public Sicherheiten2Pfandbrief(SicherheitenDaten pvSicherheitenDaten, Logger pvLogger)
	{
		this.ivSicherheitenDaten = pvSicherheitenDaten;
		this.ivLogger = pvLogger;
	}

    /**
     * Importiert die Sicherheiten/Hypotheken zu einer Kontonummer
     * @param pvKontonummer Kontonummer
     * @param pvPassivkontonummer Passivkontonummer
     * @param pvKundennummer Kundennummer
     * @param pvKredittyp Kredittyp
     * @param pvBuergschaftprozent Buergschaftprozent
     * @param pvQuellsystem Quellsystem
     * @param pvInstitutsnummer Institutsnummer
     * @return
     */
    public StringBuffer importSicherheitHypotheken(String pvKontonummer, String pvPassivkontonummer, String pvKundennummer, String pvKredittyp, String pvBuergschaftprozent, String pvQuellsystem, String pvInstitutsnummer)
    {
        StringBuffer lvBuffer = new StringBuffer();

        Sicherungsumfang lvShum = null;

        ivLogger.info("Suche Sicherheit/Hypotheken zu Kontonummer " + pvKontonummer);

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvKontonummer);
        // Nur Sicherheiten verwenden, wenn ZW > 0 ist
        if (lvHelpListe != null)
        {
            //ivLogger.info("Size: " + lvHelpListe.size());
            //ivLogger.info(lvHelpListe.toString());

            for (int x = 0; x < lvHelpListe.size(); x++)
            {
                lvShum = lvHelpListe.get(x);
                ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());

                if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0)
                {
                    lvBuffer.append(this.import2TXSKreditSicherheit(lvShum, pvKontonummer, pvPassivkontonummer, pvKundennummer, pvKredittyp, pvBuergschaftprozent, pvQuellsystem, pvInstitutsnummer));
                }
            }
        }
        else
        {
            ivLogger.info("Keine passende Hypothek in den Sicherheiten-Daten gefunden!");
        }
        return lvBuffer;
    }

    /**
     * Importiert die Sicherheiten/Buergschaften zu einer Kontonummer
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
    public StringBuffer importSicherheitBuergschaft(String pvKontonummer, String pvQuellsystem, String pvRestkapital, String pvBuergschaftprozent, String pvAusplatzierungsmerkmal, String pvNominalbetrag, String pvKundennummer, String pvBuergennummer, String pvInstitutsnummer)
    {
        StringBuffer lvBuffer = new StringBuffer();

        Sicherungsumfang lvShum = null;

        ivLogger.info("Suche Sicherheit/Buergschaft zu Kontonummer " + pvKontonummer);

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvKontonummer);

        // Nur Sicherheiten verwenden, wenn ZW > 0 ist
        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
                lvShum = lvHelpListe.get(x);
                ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
                ivLogger.info("DeckungsregisterRelevant: " + lvShum.getDeckungsregisterRelevant());

                //if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0) // Spielt der Zuweisungsbetrag eine Rolle?
                ////if (StringKonverter.convertString2Double(pvBuergschaftprozent) > 0.0)
                ////{
                if (lvShum.getDeckungsregisterRelevant().equals("01")) // Buergschaft relevant
                {
                //this.getLogger().info("Start...");
                  lvBuffer.append(this.import2TXSKreditSicherheitBuerge(lvShum, pvKontonummer, pvQuellsystem, pvRestkapital, pvBuergschaftprozent, pvAusplatzierungsmerkmal, pvNominalbetrag, pvKundennummer, pvBuergennummer, pvInstitutsnummer));
                //this.getLogger().info("Ende...");
                }
                ////}
            }
        }
        else
        {
            ivLogger.info("Keine passende Buergschaft in den Sicherheiten-Daten gefunden!");
        }
        return lvBuffer;
    }


    /**
     * Importiert die Sicherheiten fuer MIDAS zu einer Kontonummer
     * @param pvKontonummer Kontonummer
     * @param pvPassivkontonummer Passivkontonummer
     * @param pvKredittyp Kredittyp
     * @param pvRestkapital Restkapital
     * @param pvBuergschaftprozent Buergschaftprozent
     * @param pvAusplatzierungsmerkmal Ausplatzierungsmerkmal
     * @param pvInstitutsnummer Institutsnummer
     * @return
     */
    /*
    public StringBuffer importSicherheitMIDAS(String pvKontonummer, String pvPassivkontonummer, String pvKredittyp, String pvRestkapital, String pvBuergschaftprozent, String pvAusplatzierungsmerkmal, String pvInstitutsnummer)
    {
        StringBuffer lvBuffer = new StringBuffer();

        ivLogger.info("MIDAS - Suche Sicherheiten SicherheitObjekt zu Kontonummer " + pvKontonummer);

        Sicherungsumfang lvShum = null;

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvKontonummer);
        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
                lvShum = lvHelpListe.get(x);
                ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
                ivLogger.info("DeckungsregisterRelevant: " + lvShum.getDeckungsregisterRelevant());

                // Erst einmal herausgenommen, da nur Flugzeugdarlehen ohne Buergen angeliefert werden - CT 27.04.2016
                if (pvAusplatzierungsmerkmal.startsWith("H"))
                {
                    lvBuffer.append(this.import2TXSKreditSicherheit(lvShum, pvKontonummer, pvPassivkontonummer, new String(), pvKredittyp, pvBuergschaftprozent, "AMIDPFBG", pvInstitutsnummer));
                }
                if (pvAusplatzierungsmerkmal.startsWith("F"))
                {
                    lvBuffer.append(this.import2TXSKreditSicherheitFlugzeug(lvShum, pvKontonummer, "AMIDFLUG", pvInstitutsnummer));
                }
                if (pvAusplatzierungsmerkmal.startsWith("K") && StringKonverter.convertString2Double(pvBuergschaftprozent) > 0.0)
                {
                    if (lvShum.getDeckungsregisterRelevant().equals("01")) // Buergschaft relevant
                    {
                        lvBuffer.append(this.import2TXSKreditSicherheitBuerge(lvShum, pvKontonummer, "AMIDPFBG", pvRestkapital, pvBuergschaftprozent, pvAusplatzierungsmerkmal, new String(), new String(), new String()));
                    }
                }
            }
        }
        return lvBuffer;
    } */

    /**
     * Importiert die Sicherheiten fuer KEV zu einer Kontonummer
     * @param pvKontonummer Kontonummer
     * @param pvPassivkontonummer Passivkontonummer
     * @param pvKredittyp Kredittyp
     * @param pvRestkapital Restkapital
     * @param pvBuergschaftprozent Buergschaftprozent
     * @param pvAusplatzierungsmerkmal Ausplatzierungsmerkmal
     * @param pvInstitutsnummer Institutsnummer
     * @return
     */
    /*
    public StringBuffer importSicherheitKEV(String pvKontonummer, String pvPassivkontonummer, String pvKredittyp, String pvRestkapital, String pvBuergschaftprozent, String pvAusplatzierungsmerkmal, String pvInstitutsnummer)
    {
        StringBuffer lvBuffer = new StringBuffer();

        ivLogger.info("KEV - Suche Sicherheiten SicherheitObjekt zu Kontonummer " + pvKontonummer);

        Sicherungsumfang lvShum = null;

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvKontonummer);
        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
                lvShum = lvHelpListe.get(x);
                ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
                ivLogger.info("DeckungsregisterRelevant: " + lvShum.getDeckungsregisterRelevant());

                // Erst einmal herausgenommen, da nur Flugzeugdarlehen ohne Buergen angeliefert werden - CT 27.04.2016
                //if (pvAusplatzierungsmerkmal.startsWith("H"))
                //{
                lvBuffer.append(this.import2TXSKreditSicherheit(lvShum, pvKontonummer, pvPassivkontonummer, new String(), pvKredittyp, pvBuergschaftprozent, "ALIQKEV", pvInstitutsnummer));
                //}
                //if (pvAusplatzierungsmerkmal.startsWith("F"))
                //{
                //lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitFlugzeug(lvShum, pvKontonummer, "ALIQKEV"));
                //}
                //if (pvAusplatzierungsmerkmal.startsWith("K") && StringKonverter.convertString2Double(pvBuergschaftprozent) > 0.0)
                //{
                //if (lvShum.getDeckungsregisterRelevant().equals("01")) // Buergschaft relevant
                //{
                //  lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitBuerge(lvShum, pvKontonummer, "ALIQKEV", pvRestkapital, pvBuergschaftprozent, pvAusplatzierungsmerkmal));
                //}
                //}
            }
        }
        return lvBuffer;
    } */


    /**
     * Importiert ein Schiff zu einer Kontonummer
     * @param pvKontonummer Kontonummer
     * @param pvQuellsystem Quellsystem
     * @param pvInstitutsnummer
     * @return
     */
    public StringBuffer importSicherheitSchiff(String pvKontonummer, String pvQuellsystem, String pvInstitutsnummer)
    {
        StringBuffer lvBuffer = new StringBuffer();

        ivLogger.info("Suche ein Schiff zur Kontonummer " + pvKontonummer);

        Sicherungsumfang lvShum = null;

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvKontonummer);

        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
                lvShum = lvHelpListe.get(x);
                ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());

                if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0)
                {
                    lvBuffer.append(this.import2TXSKreditSicherheitSchiff(lvShum, pvKontonummer, pvQuellsystem, pvInstitutsnummer));
                }
            }
        }
        else
        {
            ivLogger.info("Kein passendes Schiff in den Sicherheiten-Daten gefunden!");
        }
        return lvBuffer;
    }

    /**
     * Importiert ein Flugzeug zu einer Kontonummer
     * @param pvKontonummer Kontonummer
     * @param pvQuellsystem Quellsystem
     * @param pvInstitutsnummer Institutsnummer
     * @return
     */
    public StringBuffer importSicherheitFlugzeug(String pvKontonummer, String pvQuellsystem, String pvInstitutsnummer)
    {
        StringBuffer lvBuffer = new StringBuffer();

        ivLogger.info("Suche ein Flugzeug zur Kontonummer " + pvKontonummer);

        Sicherungsumfang lvShum = null;

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivSicherheitenDaten.getListeSicherungsumfang().get(pvKontonummer);

        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
                lvShum = lvHelpListe.get(x);
                ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());

                //ivLogger.info("Wert: " + StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()));
                if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0)
                {
                    lvBuffer.append(this.import2TXSKreditSicherheitFlugzeug(lvShum, pvKontonummer, pvQuellsystem, pvInstitutsnummer));
                }
            }
        }
        else
        {
            ivLogger.info("Kein passendes Flugzeug in den Sicherheiten-Daten gefunden!");
        }
        return lvBuffer;
    }


    /**
     * Importiert die Kredit-Sicherheit
     * @param pvShum Sicherungsumfang
     * @param pvKontonummer Kontonummer
     * @param pvPassivkontonummer Passivkontonummer
     * @param pvKundennummer Kundennummer
     * @param pvKredittyp Kredittyp
     * @param pvBuergschaftprozent Buergschaftprozent
     * @param pvQuellsystem Quellsystem
     * @param pvInstitutsnummer Institutsnummer
     * @return 
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
                 //lvKredsh.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                 lvKredsh.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
                 lvKredsh.setQuelle(pvQuellsystem);

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
                     lvHelpString.append(importGrundpfandrechtAuslandsimmo(pvShum, lvSicherheitenvereinbarung, lvImmobilie, lvLast, lvBuergeFakt, pvQuellsystem, pvInstitutsnummer));
                 }
             
                 // TXSSicherheitVerzeichnis
                 // Beleihungssatz suchen
                 //ivLogger.info("Size: " + ivSAPCMS.getListeBeleihungssatz().size());
                 HashMap<String, Grundstueck> lvHelpListeGrundstueck = new HashMap<String, Grundstueck>();
                 HashMap<String, Grundbuchblatt> lvHelpListeGrundbuchblatt = new HashMap<String, Grundbuchblatt>();
                 Beleihungssatz lvBeleihungssatz = null;
                 Collection<Beleihungssatz> lvCollectionBeleihungssatz = ivSicherheitenDaten.getListeBeleihungssatz().values();
                 Iterator<Beleihungssatz> lvIteratorBeleihungssatz = lvCollectionBeleihungssatz.iterator();
                 while (lvIteratorBeleihungssatz.hasNext())
                 {
                     Beleihungssatz lvHelpBeleihungssatz = lvIteratorBeleihungssatz.next();
                     if (pvKontonummer.equals("4250084070"))
                     {
                       //ivLogger.info("Beleihungssatz - ObjektteilId: " + lvHelpBeleihungssatz.getObjektteilId());
                       //ivLogger.info("Immobilie - ObjektteilId: " + lvImmobilie.getObjektteilId());
                     }
                     if (lvHelpBeleihungssatz.getObjektteilId().equals(lvImmobilie.getObjektteilId()))
                     {
                         lvBeleihungssatz = lvHelpBeleihungssatz;
                         //ivLogger.info("GrundstueckId: " + lvBeleihungssatz.getGrundstueckId());
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
                     //StringBuffer lvHelpStringGrundbuchblatt = new StringBuffer();
                     Grundbuchblatt lvGrundbuchblatt = lvIteratorGrundbuchblatt.next();
                     //System.out.println("Last: " + lvLast.getId() + " Grundbuchblatt: " + lvGrundbuchblatt.getId());

                     // 
                     lvCollectionGrundbuchverweis = ivSicherheitenDaten.getListeGrundbuchverweis().values();
                     lvIteratorGrundbuchverweis = lvCollectionGrundbuchverweis.iterator();
                     while (lvIteratorGrundbuchverweis.hasNext())
                     { 
                         Grundbuchverweis lvHelpGrundbuchverweis = lvIteratorGrundbuchverweis.next();
                         if (lvHelpGrundbuchverweis.getLastId().equals(lvLast.getId()) &&
                             lvHelpGrundbuchverweis.getGrundbuchblattId().equals(lvGrundbuchblatt.getId()))
                         {
                             ivLogger.info("Grundbuchverweis gefunden: Last " + lvLast.getId() + " Grundbuchblatt " + lvGrundbuchblatt.getId());
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
                         ivLogger.info(lvGrundbuchverweis.toString());
                    	 lvAnzahlGrundbuchblaetter++;
                     }
                 }
                 
                 // Doppelte Grundbuchblaetter (Dubletten) suchen und die Anzahl der Grundbuchblaetter anpassen
                 lvCollectionGrundbuchblatt = lvHelpListeGrundbuchblatt.values();
                 lvIteratorGrundbuchblatt = lvCollectionGrundbuchblatt.iterator();

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
                 ivLogger.info("Dublette;" + pvKontonummer + ";" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ";" + lvImmobilie.getObjektId() + ";" + " - Anzahl Dubletten: " + (lvAnzahlDubletten / 2));
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
                     Grundbuchverweis lvGrundbuchverweis = null;
                     lvCollectionGrundbuchverweis = ivSicherheitenDaten.getListeGrundbuchverweis().values();
                     lvIteratorGrundbuchverweis = lvCollectionGrundbuchverweis.iterator();
                     while (lvIteratorGrundbuchverweis.hasNext())
                     {
                         Grundbuchverweis lvHelpGrundbuchverweis = lvIteratorGrundbuchverweis.next();

                         //if (pvKontonummer.equals("4250084050"))
                         //{
                             //ivLogger.info(lvHelpGrundbuchverweis.toString());
                         //    if (lvGrundbuchblatt.getId().equals("44A42C64981300C70200000006DA083F") &&
                         //        lvHelpGrundbuchverweis.getGrundbuchblattId().equals("44A42C64981300C70200000006DA083F"))
                         //    {
                         //       ivLogger.info("Vergleich: " + lvHelpGrundbuchverweis.getLastId() + " == " + lvLast.getId() + " && "
                         //          + lvHelpGrundbuchverweis.getGrundbuchblattId() + " == " + lvGrundbuchblatt.getId());
                         //   }
                         // }

                           if (lvHelpGrundbuchverweis.getLastId().equals(lvLast.getId()) &&
                             lvHelpGrundbuchverweis.getGrundbuchblattId().equals(lvGrundbuchblatt.getId()))
                         {
                             //ivLogger.info("gefunden...");
                             lvGrundbuchverweis = lvHelpGrundbuchverweis;
                         }
                     }
                  
                     // Grundbuchverweis == null, dann Last-ID und Grundbuchblatt-ID protokollieren
                     if (lvGrundbuchverweis == null)
                     {
                    	 ivLogger.error("Fehlender Grundbuchverweis;" + pvKontonummer + ";" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                    	 ivLogger.error("Konnte keinen Grundbuchverweis fuer Last " + lvLast.getId() + " und Grundbuchblatt " + lvGrundbuchblatt.getId() + " ermitteln!");

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
                     if (pvInstitutsnummer.equals("004") || pvInstitutsnummer.equals("BLB"))
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
                     if (pvInstitutsnummer.equals("009") || pvInstitutsnummer.equals("NLB"))
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
                         ivLogger.info("Gesamtgrundschuld");
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
                                 ivLogger.info("ZuweisungsbetragPO-GBverweis;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvGrundbuchverweis.getLaufendeNrAbt3() + ";" + lvHelpBetrag.toString());
                     
                             }
                             else
                             {
                                 ivLogger.info("ZuweisungsbetragPO-Last;" + lvImmobilie.getObjektId() + lvGrundbuchblatt.getBand() + lvGrundbuchblatt.getBlatt() + lvLast.getRegisternummer() + ";" + lvHelpBetrag.toString());
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
                         ObjektZuweisungsbetrag lvOzw = ivSicherheitenDaten.getObjektZuweisungsbetragListe().getObjektZuweisungsbetrag(pvShum.getSicherheitenvereinbarungId());
                         if (lvOzw != null)
                         {
                             //ivLogger.info("Out: " + pvShum.getSicherheitenvereinbarungId() + " Betrag: " + lvOzw.getZuweisungsbetrag().toString());

                             lvVedaten.setBetrag(lvOzw.getZuweisungsbetrag().toString());
                         }
                     }
                                  
                     lvVedaten.setWhrg(pvShum.getZuweisungsbetragWaehrung());

                     lvHelpStringGrundbuchblatt.append(lvVedaten.printTXSTransaktionStart());
                     lvHelpStringGrundbuchblatt.append(lvVedaten.printTXSTransaktionDaten());
                     lvHelpStringGrundbuchblatt.append(lvVedaten.printTXSTransaktionEnde());
                     
                     // TXSVerzeichnisVBlatt
                     lvVevb = new TXSVerzeichnisVBlatt();
                     // BLB verwendet die ID des Grundbuchverweis
                     if (pvInstitutsnummer.equals("004") || pvInstitutsnummer.equals("BLB"))
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
                     if (pvInstitutsnummer.equals("009") || pvInstitutsnummer.equals("NLB"))
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


                     //lvVevb.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
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
                             //lvVebv.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                             lvVebv.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                     //lvVepo.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
                     lvVepo.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                         // Bei SPOT: lvPodaten.setEigtyp(ValueMapping.mapEigentumstypSPOT(lvImmobilie.getNutzung()));


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
                         lvPodaten.setBwertdat(DatumUtilities.changeDate(lvImmobilie.getFestsetzungsdatum()));
                         
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


                         ivLogger.info("AZ6;" + lvImmobilie.getDeckungskennzeichen() + ";" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ";" +
                                       lvImmobilie.getObjektId() + ";" + lvImmobilie.getNutzungsart() + ";" + lvImmobilie.getObjektartId() + ";");
                         lvPodaten.setOrt(lvImmobilie.getOrt());
                         lvPodaten.setPlz(lvImmobilie.getPostleitzahl());

                         String sSP_EigentumsTyp = new String();
                         ivLogger.info(lvImmobilie.getObjektartId() + " " + lvImmobilie.getGradBaufertigstellung() + " " + lvImmobilie.getNutzung() + " " + lvImmobilie.getNutzungsart() + " " + lvImmobilie.getProzentanteil());
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
                     //lvZugsh.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
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
     * Importiert Grundpfandrechte von Auslandsimmobilien
     * @param pvShum Sicherungsumfang
     * @param pvSicherheitenvereinbarung Sicherheitenvereinbarung
     * @param pvImmobilie Immobilie
     * @param pvLast Last
     * @param pvBuergeFakt BuergenFaktor
     * @param pvQuellsystem Quellsystem
     * @param pvInstitutsnummer Institutsnummer
     * @return
     */
    private String importGrundpfandrechtAuslandsimmo(Sicherungsumfang pvShum, Sicherheitenvereinbarung pvSicherheitenvereinbarung, Immobilie pvImmobilie, Last pvLast, BigDecimal pvBuergeFakt, String pvQuellsystem, String pvInstitutsnummer)
    {
        StringBuffer lvHelpString = new StringBuffer();
        ivLogger.info("Auslandsimmo...");
        TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
        // Pfandobjekt-Nr nehmen, da kein Grundbuch vorhanden!!!
        // CT 06.06.2012 - Wegen Eindeutigkeit die Sicherheiten-ID davor schreiben
        lvShve.setVenr(pvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + "_" + pvImmobilie.getObjektId());
        //lvShve.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
        lvShve.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));

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
        	  //ivLogger.info("Size: " + ivSAPCMS.getObjektZuweisungsbetragListe().size());
        	  //ivLogger.info("ID: " + pvShum.getSicherheitenvereinbarungId());
            //ObjektZuweisungsbetrag lvOzw = pvSAPCMS.getObjektZuweisungsbetragListe().getObjektZuweisungsbetrag(pvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
            ObjektZuweisungsbetrag lvOzw = ivSicherheitenDaten.getObjektZuweisungsbetragListe().getObjektZuweisungsbetrag(pvShum.getSicherheitenvereinbarungId());
            if (lvOzw != null)
            {
            	  //ivLogger.info("Zuweisungsbetrag: " + lvOzw.getZuweisungsbetrag() + " pvBuergeFakt: " + pvBuergeFakt);
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
        //lvVepo.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
        lvVepo.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
            lvPodaten.setBwertdat(DatumUtilities.changeDate(pvImmobilie.getFestsetzungsdatum()));
            
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
     * @param pvShum Sicherungsumfang
     * @param pvKontonummer Kontonummer
     * @param pvQuellsystem Quellsystem
     * @param pvInstitutsnummer Institutsnummer
     */
    public StringBuffer import2TXSKreditSicherheitFlugzeug(Sicherungsumfang pvShum, String pvKontonummer, String pvQuellsystem, String pvInstitutsnummer)
    {
        StringBuffer lvHelpString = new StringBuffer(); 
        ivLogger.info("Start - import2TXSKreditSicherheitFlugzeug");
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
            //if (pvKontonummer.equals("4250090240"))
            //{
            //    ivLogger.info("Sicherheitenvereinbarung-ID: " + lvSicherheitenvereinbarung.getId());
            //    ivLogger.info("Last - Sicherheitenvereinbarung-ID: " + lvHelpLast.getSicherheitenvereinbarungId());
            //}
            if (lvHelpLast.getSicherheitenvereinbarungId().equals(lvSicherheitenvereinbarung.getId()))
            {
                lvLast = lvHelpLast;
            }
             
             if (lvLast != null)
             {
                 // Flugzeug suchen
                 Flugzeug lvFlugzeug = null;
                 Collection<Flugzeug> lvCollectionFlugzeug = ivSicherheitenDaten.getListeFlugzeug().values();
                 Iterator<Flugzeug> lvIteratorFlugzeug = lvCollectionFlugzeug.iterator();
                 while (lvIteratorFlugzeug.hasNext())
                 {
                     Flugzeug lvHelpFlugzeug = lvIteratorFlugzeug.next();
                     if (lvHelpFlugzeug.getId().equals(lvLast.getImmobilieId()))
                     {
                         lvFlugzeug = lvHelpFlugzeug;
                     }
                 }
             
                 // Wenn Deckungskennzeichen nicht 'W' oder 'U' ist, dann leeren StringBuffer zurueckliefern
                 if (lvFlugzeug != null)
                 {
                     //System.out.println("Deckungskennzeichen: " + lvFlugzeug.getStatusDeckung());
                     if (!lvFlugzeug.getStatusDeckung().equals("W") && !lvFlugzeug.getStatusDeckung().equals("U"))
                     {   
                           continue;
                     }
                 }
                 
                 ivLogger.info("TXSKreditSicherheitFlugzeug - Start");
                 // TXSKreditSicherheit
                 lvKredsh = new TXSKreditSicherheit();
                 lvKredsh.setKey(lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                 lvKredsh.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                 lvShve.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                 lvVevb.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
	             lvVepo.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
     * @param pvShum
     * @param pvKontonummer
     * @param pvQuellsystem
     * @param pvInstitutsnummer
     */
    public StringBuffer import2TXSKreditSicherheitSchiff(Sicherungsumfang pvShum, String pvKontonummer, String pvQuellsystem, String pvInstitutsnummer)
    {
        StringBuffer lvHelpString = new StringBuffer(); 
        ivLogger.info("import2TXSKreditSicherheitSchiffe");
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
                 ivLogger.info(lvSchiff.toString());

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
       
                 lvKredsh.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                 if (pvInstitutsnummer.equals("004") || pvInstitutsnummer.equals("BLB"))
                 {
                	lvShdaten.setGepr(""); 
                 }
                 
                 lvShdaten.setNbetrag(lvSicherheitenvereinbarung.getNominalwert());

                 lvShdaten.setVbetrag(lvVerfBetrag.toString());
                 lvShdaten.setWhrg(lvSicherheitenvereinbarung.getVerfuegungsbetragWaehrung());
                 lvHelpCSV.append(lvSicherheitenvereinbarung.getNominalwert() + ";" + lvSicherheitenvereinbarung.getNominalwertWaehrung() + ";" + lvVerfBetrag.toString() + ";" + lvSicherheitenvereinbarung.getVerfuegungsbetragWaehrung() + ";");
       
                 lvShve = new TXSSicherheitVerzeichnis();
                 lvShve.setVenr(lvSchiff.getSchiffId() + "_" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId());
                 lvShve.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                 lvVevb.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                 lvVepo.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                 // CT 08.04.2020 - SAP CMS - SPOT lvPodaten.setSbrnr(lvSchiff.getSchiffstypen().substring(1));
                 lvPodaten.setSbrnr(lvSchiff.getSchiffstypen().substring(1));

                 // CT 08.04.2020 - SAP CMS - SPOT setzeSchiffsdaten(lvSchiff.getSchiffstypen().substring(1), lvPodaten);
                 setzeSchiffsdaten(lvSchiff.getSchiffstypen().substring(1), lvPodaten);

                 //lvPodaten.setAusldat(DatumUtilities.changeDate(lvSchiff.getAuslieferungsdatumSoll()));
                 lvPodaten.setAusldat(DatumUtilities.changeDate(lvSchiff.getAblieferungsdatum())); 
                 lvPodaten.setFertstdat(DatumUtilities.changeDate(lvSchiff.getAblieferungsdatum()));
                 lvHelpCSV.append(lvSchiff.getSchiffId() + ";" + lvSchiff.getSchiffsname() + ";" + lvSchiff.getIMONummer() + ";" + lvSchiff.getWaehrungBeleihungswertAktuell() + ";" + lvSchiff.getBeleihungswertAktuell() + ";");
                 lvHelpCSV.append(lvSchiff.getDatumBeleihungswertAktuell() + ";" + lvSchiff.getRegisterLand() + ";" + lvSchiff.getBeleihungswertIndeckungnahme() + " " + lvSchiff.getWaehrungBeleihungswertIndeckungnahme() + ";" + lvSchiff.getSchiffstypen().substring(1) + ";");
                 lvHelpCSV.append(lvPodaten.getOgrp() + ";" + lvPodaten.getNart() + ";" + lvPodaten.getErtragsf());
                 //ivLogger.info("DeepSea;DeepSea;Sicherheiten;" + pvKontonummer + ";" + lvSchiff.getSchiffId() + ";" + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ";" + lvHelpCSV.toString());
                
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
     * @param pvShum
     * @param pvKontonummer 
     * @param pvQuellsystem
     * @param pvRestkapital
     * @param pvBuergschaftprozent
     * @param pvAusplatzierungsmerkmal
     * @param pvInstitutsnummer
     */
    public StringBuffer import2TXSKreditSicherheitBuerge(Sicherungsumfang pvShum, String pvKontonummer, String pvQuellsystem, String pvRestkapital, String pvBuergschaftprozent, String pvAusplatzierungsmerkmal, String pvNominalbetrag, String pvKundennummer, String pvBuergennummer, String pvInstitutsnummer)
    {
        StringBuffer lvHelpString = new StringBuffer(); 
        ivLogger.info("import2TXSKreditSicherheitBuerge - " + pvKontonummer);

        Sicherheitenvereinbarung lvSicherheitenvereinbarung = ivSicherheitenDaten.getListeSicherheitenvereinbarung().get(pvShum.getSicherheitenvereinbarungId());
        ivLogger.info("Sicherheitenvereinbarung-ID: " + lvSicherheitenvereinbarung.getId() + " - " + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId() + ": " + lvSicherheitenvereinbarung.getSicherheitenvereinbarungsart());
        // Buergschaft
        if (lvSicherheitenvereinbarung.getSicherheitenvereinbarungsart().startsWith("03"))
        {
            // Geschaeftspartner suchen
            Geschaeftspartner lvGeschaeftspartner = null;
            Collection<Geschaeftspartner> lvCollectionGeschaeftspartner = ivSicherheitenDaten.getListeGeschaeftspartner().values();
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
                    ivLogger.info(lvGeschaeftspartner.toString());
                	//System.out.println("Kundennummer: " + lvGeschaeftspartner.getKundennummer());
                	//System.out.println("Funktion: " + lvGeschaeftspartner.getGeschaeftspartnerfunktion());
                	if (lvGeschaeftspartner.getGeschaeftspartnerfunktion().endsWith("50002"))
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
                		lvKredsh.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
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
                        lvShperson.setOrg(ValueMapping.changeMandant(pvInstitutsnummer));
                        lvShperson.setQuelle("KUNDE");
                        lvHelpString.append(lvShperson.printTXSTransaktionStart());
                        lvHelpString.append(lvShperson.printTXSTransaktionDaten());
                        lvHelpString.append(lvShperson.printTXSTransaktionEnde());
                       
                        lvHelpString.append(lvKredsh.printTXSTransaktionEnde());

                        // Logging der Buergschaften aus SAP CMS
                        StringBuffer lvHelpCSV = new StringBuffer();
                        lvHelpCSV.append("Buergschaft SAP CMS;" + pvKontonummer + ";" + lvKredsh.getKey() + ";" + lvGeschaeftspartner.getKundennummer() + ";" + lvGeschaeftspartner.getGeschaeftspartnerfunktion() + ";" + pvKundennummer + ";" + pvBuergennummer + ";" + lvKredsh.getQuelle() + ";" + lvKredsh.getZuwbetrag().replace(".", ",") + ";" + lvKredsh.getWhrg());
                        lvHelpCSV.append(";" + lvShdaten.getArt() + ";" + lvShdaten.getNbetrag().replace(".", ",") + ";" + lvShdaten.getVbetrag().replace(".", ",") + ";" + lvShdaten.getWhrg() + ";" + pvBuergschaftprozent.replace(".", ","));
                        lvHelpCSV.append(";" + lvSicherheitenvereinbarung.getVerbuergungssatz().replace(".", ","));
                        lvHelpCSV.append(";" + pvShum.getDeckungsregisterRelevant());
                        lvHelpCSV.append(";" + pvNominalbetrag.replace(".", ",") + ";" + pvRestkapital.replace(".", ","));
                        lvHelpCSV.append(";" + lvSicherheitenvereinbarung.getAusfallbuergschaftKennzeichen() + ";" + lvSicherheitenvereinbarung.getAusfallbuergschaft().replace(".", ",") + ";");
                        lvHelpCSV.append(StringKonverter.lineSeparator);

                        //ivLogger.info("Start...");
                        ivLogger.info(lvHelpCSV.toString());
                        //ivLogger.info("Ende...");
                	}
                }
            }
        }		
        
    	return lvHelpString;
    }

}
