/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.SAPCMS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetragListe;
import nlb.txs.schnittstelle.RefiRegister.RefiDeepSeaZusatz;
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
import nlb.txs.schnittstelle.SAPCMS.Entities.Triebwerk;
import nlb.txs.schnittstelle.SAPCMS.Entities.Vorlaufsatz;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author tepperc
 *
 */
public class SAPCMS_Neu 
{
    // Wird nur zu Testzwecken benoetigt!
    private static Logger LOGGER_SAPCMS = Logger.getLogger("TXSSAPCMSLogger");  
    
    // Status Vorlaufsatz
    private final String VORLAUFSATZ = "00";
    
    // Status Sicherheitenvereinbarung
    private final String SICHERHEITENVEREINBARUNG = "10";
    
    // Status Sicherungsumfang
    private final String SICHERUNGSUMFANG = "12";
    
    // Status Last
    private final String LAST = "14";
    
    // Status Grundbuchverweis
    private final String GRUNDBUCHVERWEIS = "15";
    
    // Status Geschaeftspartner
    private final String GESCHAEFTSPARTNER = "16";
    
    // Status Immobilie
    private final String IMMOBILIE = "20";
    
    // Status Flugzeug
    private final String FLUGZEUG = "21";
    
    // Status Triebwerk
    private final String TRIEBWERK = "22";
    
    // Status Schiff
    private final String SCHIFF = "23";
    
    // Status Beleihungssatz
    private final String BELEIHUNGSSATZ = "49";
    
    // Status Grundbuchblatt
    private final String GRUNDBUCHBLATT = "40";
    
    // Status Grundstueck
    private final String GRUNDSTUECK = "41";
    
    // Input-File SAPCMS
    private File ivFileSAPCMS;    
     
    // Jeweils eine Instanz wird benoetigt
    private Vorlaufsatz ivVorlaufsatz;
    private Sicherheitenvereinbarung ivSicherheitenvereinbarung;
    private Sicherungsumfang ivSicherungsumfang;
    private Last ivLast;
    private Grundbuchverweis ivGrundbuchverweis;
    private Geschaeftspartner ivGeschaeftspartner;
    private Immobilie ivImmobilie;
    private Flugzeug ivFlugzeug;
    private Triebwerk ivTriebwerk;
    private Schiff ivSchiff;
    private Beleihungssatz ivBeleihungssatz;
    private Grundbuchblatt ivGrundbuchblatt;
    private Grundstueck ivGrundstueck;
       
    // Liste (HashMap) der Entitaeten
    private HashMap<String, Sicherheitenvereinbarung> ivListeSicherheitenvereinbarung;
    private HashMap<String, LinkedList<Sicherungsumfang>> ivListeSicherungsumfang;
    private HashMap<String, Last> ivListeLast;
    private HashMap<String, Grundbuchverweis> ivListeGrundbuchverweis;
    private HashMap<String, Geschaeftspartner> ivListeGeschaeftspartner;
    private HashMap<String, Immobilie> ivListeImmobilie;
    private HashMap<String, Flugzeug> ivListeFlugzeug;
    private HashMap<String, Triebwerk> ivListeTriebwerk;
    private HashMap<String, Schiff> ivListeSchiff;
    private HashMap<String, Beleihungssatz> ivListeBeleihungssatz;
    private HashMap<String, Grundbuchblatt> ivListeGrundbuchblatt;
    private HashMap<String, Grundstueck> ivListeGrundstueck;
    
    // Zaehler der einzelnen Entitaeten
    private int ivZaehlerVorlaufsatz = 0;
    private int ivZaehlerSicherheitenvereinbarung = 0;
    private int ivZaehlerSicherungsumfang = 0;
    private int ivZaehlerLast = 0;
    private int ivZaehlerGrundbuchverweis = 0;
    private int ivZaehlerGeschaeftspartner = 0;
    private int ivZaehlerImmobilie = 0;
    private int ivZaehlerFlugzeug = 0;
    private int ivZaehlerTriebwerk = 0;
    private int ivZaehlerSchiff = 0;
    private int ivZaehlerBeleihungssatz = 0;
    private int ivZaehlerGrundbuchblatt = 0;
    private int ivZaehlerGrundstueck = 0;

    // Logger Darlehen
    private Logger ivLogger;
    
    // Liste Zuweisungsbetraege - DarKa
    private ObjektZuweisungsbetragListe ivOzwListe;
        
    // SAPCMS2Pfandbrief
    private SAPCMS2Pfandbrief ivSAPCMS2Pfandbrief;
    
    // SAPCMS2RefiRegister
    private SAPCMS2RefiRegister ivSAPCMS2RefiRegister;
    
    /**
     * Konstruktor
     * @param pvFilename 
     * @param pvLogger 
     */
    public SAPCMS_Neu(String pvFilename, Logger pvLogger) 
    {   
        this.ivLogger = pvLogger;
        // Initialisierung der Liste Zuweisungsbetraege
        ivOzwListe = new ObjektZuweisungsbetragListe();
        // Initialisierung der Listen Entitaeten
        ivListeSicherheitenvereinbarung = new HashMap<String, Sicherheitenvereinbarung>();
        ivListeSicherungsumfang = new HashMap<String, LinkedList<Sicherungsumfang>>();
        ivListeLast = new HashMap<String, Last>();
        ivListeGrundbuchverweis = new HashMap<String, Grundbuchverweis>();
        ivListeGeschaeftspartner = new HashMap<String, Geschaeftspartner>();
        ivListeImmobilie = new HashMap<String, Immobilie>();
        ivListeFlugzeug = new HashMap<String, Flugzeug>();
        ivListeTriebwerk = new HashMap<String, Triebwerk>();
        ivListeSchiff = new HashMap<String, Schiff>();
        ivListeBeleihungssatz = new HashMap<String, Beleihungssatz>();
        ivListeGrundbuchblatt = new HashMap<String, Grundbuchblatt>();
        ivListeGrundstueck = new HashMap<String, Grundstueck>();
        ivSAPCMS2Pfandbrief = new SAPCMS2Pfandbrief(this);
        ivSAPCMS2RefiRegister = new SAPCMS2RefiRegister(this);
        readSAPCMS(pvFilename);
    }

    /**
     * Liest die SAPCMS-Datei ein
     * @param pvDateiname
     */
    private void readSAPCMS(String pvDateiname)
    {
        ivLogger.info("Start - readSAPCMS");
        String lvZeile = null;
                      
        // Oeffnen der Dateien
        FileInputStream lvFis = null;
        ivFileSAPCMS = new File(pvDateiname);
        try
        {
            lvFis = new FileInputStream(ivFileSAPCMS);
        }
        catch (Exception e)
        {
            ivLogger.error("Konnte SAPCMS-Datei nicht oeffnen!");
            return;
        }
    
        BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
              
        try
        {
            while ((lvZeile = lvIn.readLine()) != null)  // Lesen SAPCMS-Datei
            {
              // Parsen der einzelnen Entitaeten  
              if (lvZeile.startsWith(VORLAUFSATZ))
              {
                ivVorlaufsatz = new Vorlaufsatz();
                ivVorlaufsatz.parseVorlaufsatz(lvZeile);
                ivZaehlerVorlaufsatz++;
              }
              if (lvZeile.startsWith(SICHERHEITENVEREINBARUNG))
              {
                ivSicherheitenvereinbarung = new Sicherheitenvereinbarung();
                ivSicherheitenvereinbarung.parseSicherheitenvereinbarung(lvZeile);
                ivListeSicherheitenvereinbarung.put(ivSicherheitenvereinbarung.getId(), ivSicherheitenvereinbarung);
                ivZaehlerSicherheitenvereinbarung++;
              }
              if (lvZeile.startsWith(SICHERUNGSUMFANG))
              {
                ivSicherungsumfang = new Sicherungsumfang();
                ivSicherungsumfang.parseSicherungsumfang(lvZeile, this);
                if (ivListeSicherungsumfang.containsKey(ivSicherungsumfang.getGeschaeftswertId()))
                {
                    LinkedList<Sicherungsumfang> helpListe = ivListeSicherungsumfang.get(ivSicherungsumfang.getGeschaeftswertId());
                    helpListe.add(ivSicherungsumfang);
                }
                else
                {
                  LinkedList<Sicherungsumfang> helpListe = new LinkedList<Sicherungsumfang>();
                  helpListe.add(ivSicherungsumfang);
                  ivListeSicherungsumfang.put(ivSicherungsumfang.getGeschaeftswertId(), helpListe);
                }
                ivZaehlerSicherungsumfang++;
              }
              if (lvZeile.startsWith(LAST))
              {
                ivLast = new Last();
                ivLast.parseLast(lvZeile);
                ivListeLast.put(ivLast.getId(), ivLast);
                ivZaehlerLast++;
              }
              if (lvZeile.startsWith(GRUNDBUCHVERWEIS))
              {
                ivGrundbuchverweis = new Grundbuchverweis();
                ivGrundbuchverweis.parseGrundbuchverweis(lvZeile);
                // Den Grundbuchverweis nicht beruecksichtigen, wenn das Loeschkennzeichen gesetzt ist.
                if (!ivGrundbuchverweis.getLoeschkennzeichen().equals("X"))
                {
                  ivListeGrundbuchverweis.put(ivGrundbuchverweis.getId(), ivGrundbuchverweis);
                }
                else
                {
                	ivLogger.info("Bei Grundbuchverweis " + ivGrundbuchverweis.getId() + " ist das Loeschkennzeichen gesetzt.");
                }
                ivZaehlerGrundbuchverweis++;
              }
              if (lvZeile.startsWith(GESCHAEFTSPARTNER))
              {
                ivGeschaeftspartner = new Geschaeftspartner();
                ivGeschaeftspartner.parseGeschaeftspartner(lvZeile);
                ivListeGeschaeftspartner.put(ivGeschaeftspartner.getId(), ivGeschaeftspartner);
                ivZaehlerGeschaeftspartner++;
              }
              if (lvZeile.startsWith(IMMOBILIE))
              {
                ivImmobilie = new Immobilie();
                ivImmobilie.parseImmobilie(lvZeile);
                ivListeImmobilie.put(ivImmobilie.getId(), ivImmobilie);
                ivZaehlerImmobilie++;
              }
              if (lvZeile.startsWith(FLUGZEUG))
              {
            	  ivFlugzeug = new Flugzeug();
            	  ivFlugzeug.parseFlugzeug(lvZeile);
            	  ivListeFlugzeug.put(ivFlugzeug.getId(), ivFlugzeug);
            	  ivZaehlerFlugzeug++;
              }
              if (lvZeile.startsWith(TRIEBWERK))
              {
            	  ivTriebwerk = new Triebwerk();
            	  ivTriebwerk.parseTriebwerk(lvZeile);
            	  ivListeTriebwerk.put(ivTriebwerk.getId(), ivTriebwerk);
            	  ivZaehlerTriebwerk++;
              }
              if (lvZeile.startsWith(SCHIFF))
              {
            	  ivSchiff = new Schiff();
            	  ivSchiff.parseSchiff(lvZeile);
            	  ivListeSchiff.put(ivSchiff.getId(), ivSchiff);
            	  ivZaehlerSchiff++;
              }
              if (lvZeile.startsWith(BELEIHUNGSSATZ))
              {
                ivBeleihungssatz = new Beleihungssatz();
                ivBeleihungssatz.parseBeleihungssatz(lvZeile);
                ivListeBeleihungssatz.put(ivBeleihungssatz.getId(), ivBeleihungssatz);
                ivZaehlerBeleihungssatz++;
              }
              if (lvZeile.startsWith(GRUNDBUCHBLATT))
              {
                ivGrundbuchblatt = new Grundbuchblatt();
                ivGrundbuchblatt.parseGrundbuchblatt(lvZeile);
                ivListeGrundbuchblatt.put(ivGrundbuchblatt.getId(), ivGrundbuchblatt);
                ivZaehlerGrundbuchblatt++;
              }
              if (lvZeile.startsWith(GRUNDSTUECK))
              {
                ivGrundstueck = new Grundstueck();
                ivGrundstueck.parseGrundstueck(lvZeile);
                ivListeGrundstueck.put(ivGrundstueck.getId(), ivGrundstueck);
                ivZaehlerGrundstueck++;
              }
            }
        }
        catch (Exception e)
        {
            ivLogger.error("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
        }
              
        try
        {
            lvIn.close();
        }
        catch (Exception e)
        {
            ivLogger.error("Konnte SAPCMS-Datei nicht schliessen!");
        } 
        
        ivLogger.info(getStatistik());
        //DeepSeaLogging lvDeepSeaLogging = new DeepSeaLogging(this, ivLogger);
     }

    /**
     * Liefert eine Statistik als String
     * @return String mit Statistik
     */
    private String getStatistik()
    {
        StringBuilder lvOut = new StringBuilder();
        lvOut.append(ivVorlaufsatz.toString());
        
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerSicherheitenvereinbarung + " Sicherheitenvereinbarung-Segmente gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerSicherungsumfang + " Sicherungsumfang-Segmente gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerLast + " Last-Segmente gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerGrundbuchverweis + " Grundbuchverweis-Segmente gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerGeschaeftspartner + " Geschaeftspartner-Segmente gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerImmobilie + " Immobilie-Segmente gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerFlugzeug + " Flugzeug-Segmente gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerTriebwerk + " Triebwerk-Segmente gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerSchiff + " Schiff-Segmente gelesen..." + StringKonverter.lineSeparator);      
        lvOut.append(ivZaehlerBeleihungssatz + " Beleihungssatz-Segmente gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerGrundbuchblatt + " Grundbuchblatt-Segmente gelesen..." + StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerGrundstueck + " Grundstueck-Segmente gelesen..." + StringKonverter.lineSeparator);
     
        lvOut.append("Anzahl Liste Sicherheitenvereinbarung: " + ivListeSicherheitenvereinbarung.size() + StringKonverter.lineSeparator);
        lvOut.append("Anzahl Liste Sicherungsumfang: " + ivListeSicherungsumfang.size() + StringKonverter.lineSeparator);
        lvOut.append("Anzahl Liste Last: " + ivListeLast.size() + StringKonverter.lineSeparator);
        lvOut.append("Anzahl Liste Grundbuchverweis: " + ivListeGrundbuchverweis.size() + StringKonverter.lineSeparator);
        lvOut.append("Anzahl Liste Geschaeftspartner: " + ivListeGeschaeftspartner.size() + StringKonverter.lineSeparator);
        lvOut.append("Anzahl Liste Immobilie: " + ivListeImmobilie.size() + StringKonverter.lineSeparator);
        lvOut.append("Anzahl Liste Flugzeug: " + ivListeFlugzeug.size() + StringKonverter.lineSeparator);
        lvOut.append("Anzahl Liste Triebwerk: " + ivListeTriebwerk.size() + StringKonverter.lineSeparator);
        lvOut.append("Anzahl Liste Schiff: " + ivListeSchiff.size() + StringKonverter.lineSeparator);        
        lvOut.append("Anzahl Liste Beleihungssatz: " + ivListeBeleihungssatz.size() + StringKonverter.lineSeparator);
        lvOut.append("Anzahl Liste Grundbuchblatt: " + ivListeGrundbuchblatt.size() + StringKonverter.lineSeparator);
        lvOut.append("Anzahl Liste Grundstueck: " + ivListeGrundstueck.size() + StringKonverter.lineSeparator);
        
        return lvOut.toString();
    }
    
    /**
     * Importiert die Sicherheiten/Hypotheken zu einer Kontonummer
     * @param pvKontonummer 
     * @param pvPassivkontonummer 
     * @param pvKredittyp 
     * @param pvBuergschaftprozent 
     * @param pvQuellsystem
     * @return
     */
    public StringBuffer importSicherheitHypotheken(String pvKontonummer, String pvPassivkontonummer, String pvKredittyp, String pvBuergschaftprozent, String pvQuellsystem) 
    {
        StringBuffer lvBuffer = new StringBuffer();
          
        Sicherungsumfang lvShum = null;
        
        ivLogger.info("Suche SAPCMS Sicherheit/Hypotheken zu Kontonummer " + pvKontonummer);
      
        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivListeSicherungsumfang.get(pvKontonummer);
        
        // Nur Sicherheiten verwenden, wenn ZW > 0 ist
        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
              lvShum = lvHelpListe.get(x);
              ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());

              if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0)
              {        
                  lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheit(lvShum, pvKontonummer, pvPassivkontonummer, new String(), pvKredittyp, pvBuergschaftprozent, pvQuellsystem));
              }
            } 
          }
        return lvBuffer;
    } 

    /**
     * Importiert die Sicherheiten/Buergschaften zu einer Kontonummer
     * @param pvKontonummer 
     * @param pvPassivkontonummer 
     * @param pvKredittyp 
     * @param pvBuergschaftprozent 
     * @param pvAusplatzierungsmerkmal
     * @return
     */
    public StringBuffer importSicherheitBuergschaft(String pvKontonummer, String pvQuellsystem, String pvRestkapital, String pvBuergschaftprozent, String pvAusplatzierungsmerkmal) 
    {
        StringBuffer lvBuffer = new StringBuffer();
          
        Sicherungsumfang lvShum = null;
        
        ivLogger.info("Suche SAPCMS Sicherheit/Buergschaft zu Kontonummer " + pvKontonummer);
      
        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivListeSicherungsumfang.get(pvKontonummer);
        
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
            	  ////if (lvShum.getDeckungsregisterRelevant().equals("01")) // Buergschaft relevant
            	  ////{
            		  lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitBuerge(lvShum, pvKontonummer, pvQuellsystem, pvRestkapital, pvBuergschaftprozent, pvAusplatzierungsmerkmal));

            	  ////}
              ////}
            } 
          }
        return lvBuffer;
    } 


    
    /**
     * Importiert die Sicherheiten fuer das RefiRegister zu einer Kontonummer
     * @param pvKontonummer 
     * @param pvPassivkontonummer 
     * @param pvKundennummer Kundennummer der Konsorte
     * @param pvKredittyp 
     * @param pvBuergschaftprozent
     * @param pvQuellsystem 
     * @param pvAusplatzierungsmerkmal
     * @return
     */
    public StringBuffer importSicherheitRefiRegister(String pvKontonummer, String pvPassivkontonummer, String pvKundennummer, String pvKredittyp, String pvBuergschaftprozent, String pvQuellsystem, String pvAusplatzierungsmerkmal) 
    {
        StringBuffer lvBuffer = new StringBuffer();
          
        Sicherungsumfang lvShum = null;
        
        ivLogger.info("RefiRegister - Suche SAPCMS SicherheitObjekt zu Kontonummer " + pvKontonummer);
      
        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivListeSicherungsumfang.get(pvKontonummer);
                
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
              lvBuffer.append(ivSAPCMS2RefiRegister.import2TXSKreditSicherheit(lvShum, pvKontonummer, pvPassivkontonummer, pvKundennummer, pvKredittyp, pvBuergschaftprozent, pvQuellsystem));
              ////lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitSchiff(lvShum, pvKontonummer, "ADARLREFI"));
            } 
          }
        return lvBuffer;
    }
    
    /**
     * Importiert die Sicherheiten fuer das RefiRegister DeepSea zu einer Kontonummer
     * @param pvKontonummer 
     * @param pvListeRefiDeepSea
     * @return
     */
    public StringBuffer importSicherheitRefiRegisterDeepSea(String pvKontonummer, HashMap<String, RefiDeepSeaZusatz> pvListeRefiDeepSea) 
    {
        StringBuffer lvBuffer = new StringBuffer();
          
        Sicherungsumfang lvShum = null;
        
        ivLogger.info("RefiRegisterDeepSea - Suche SAPCMS SicherheitObjekt zu Kontonummer " + pvKontonummer);
      
        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivListeSicherungsumfang.get(pvKontonummer);
        
        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
              lvShum = lvHelpListe.get(x);
              ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());

              lvBuffer.append(ivSAPCMS2RefiRegister.import2TXSKreditSicherheitSchiff(lvShum, pvKontonummer, "ADARLREFI", pvListeRefiDeepSea));
            } 
          }
        return lvBuffer;
    }
    
    /**
     * Importiert die Sicherheiten fuer MIDAS zu einer Kontonummer
     * @param pvKontonummer 
     * @param pvPassivkontonummer 
     * @param pvKredittyp 
     * @param pvRestkapital
     * @param pvBuergschaftprozent
     * @param pvAusplatzierungsmerkmal
     * @return
     */
    public StringBuffer importSicherheitMIDAS(String pvKontonummer, String pvPassivkontonummer, String pvKredittyp, String pvRestkapital, String pvBuergschaftprozent, String pvAusplatzierungsmerkmal)
    {
    	StringBuffer lvBuffer = new StringBuffer();
    	
        ivLogger.info("MIDAS - Suche SAPCMS SicherheitObjekt zu Kontonummer " + pvKontonummer);

        Sicherungsumfang lvShum = null;

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivListeSicherungsumfang.get(pvKontonummer);
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
              	  lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheit(lvShum, pvKontonummer, pvPassivkontonummer, new String(), pvKredittyp, pvBuergschaftprozent, "AMIDPFBG"));
              }
              if (pvAusplatzierungsmerkmal.startsWith("F"))
              {	
            	  lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitFlugzeug(lvShum, pvKontonummer, "AMIDFLUG"));
              }
              if (pvAusplatzierungsmerkmal.startsWith("K") && StringKonverter.convertString2Double(pvBuergschaftprozent) > 0.0)
              {
            	  if (lvShum.getDeckungsregisterRelevant().equals("01")) // Buergschaft relevant
            	  {
            		  lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitBuerge(lvShum, pvKontonummer, "AMIDPFBG", pvRestkapital, pvBuergschaftprozent, pvAusplatzierungsmerkmal));
            	  }
              }
            } 
        }
		return lvBuffer;	
    }
    
    /**
     * Importiert ein Schiff zu einer Kontonummer
     * @param pvKontonummer Kontonummer
     * @param pvQuellsystem Quellsystem
     * @return
     */
    public StringBuffer importSicherheitSchiff(String pvKontonummer, String pvQuellsystem)
    {
    	StringBuffer lvBuffer = new StringBuffer();

        ivLogger.info("Suche ein Schiff zur Kontonummer " + pvKontonummer);

        Sicherungsumfang lvShum = null;

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivListeSicherungsumfang.get(pvKontonummer);
        
        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
              lvShum = lvHelpListe.get(x);
              ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());

              if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0)
              {        
            	  lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitSchiff(lvShum, pvKontonummer, pvQuellsystem));
              }              
            } 
        }
        else
        {
        	ivLogger.info("Kein passendes Schiff in SAP CMS gefunden!");        	
        }
    	return lvBuffer;
    }
    
    /**
     * Importiert ein Flugzeug zu einer Kontonummer
     * @param pvKontonummer
     * @param pvQuellsystem
     * @return
     */
    public StringBuffer importSicherheitFlugzeug(String pvKontonummer, String pvQuellsystem)
    {
    	StringBuffer lvBuffer = new StringBuffer();

        ivLogger.info("Suche ein Flugzeug zur Kontonummer " + pvKontonummer);

        Sicherungsumfang lvShum = null;

        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivListeSicherungsumfang.get(pvKontonummer);
        
        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
              lvShum = lvHelpListe.get(x);
              ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());

              if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0)
              {        
            	  lvBuffer.append(ivSAPCMS2Pfandbrief.import2TXSKreditSicherheitFlugzeug(lvShum, pvKontonummer, pvQuellsystem));
              }
            } 
        }   
        else
        {
        	ivLogger.info("Kein passendes Flugzeug in SAP CMS gefunden!");
        }
    	return lvBuffer;
    } 
    
    /**
     * Existiert eine Sicherheit zur Kontonummer
     * @param pvKontonummer 
     * @return
     */
    public String getSicherheitId(String pvKontonummer) 
    {
        Sicherungsumfang lvShum = null;
        
        ivLogger.info("Suche SAPCMS SicherheitObjekt zu Kontonummer " + pvKontonummer);
      
        // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
        LinkedList<Sicherungsumfang> lvHelpListe = ivListeSicherungsumfang.get(pvKontonummer);
        
        // Nur Sicherheiten verwenden, wenn ZW > 0 ist
        if (lvHelpListe != null)
        {
            for (int x = 0; x < lvHelpListe.size(); x++)
            {
              lvShum = lvHelpListe.get(x);
              ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
              if (lvShum != null)
              {
                  ////if (StringKonverter.convertString2Double(lvShum.getZuweisungsbetrag()) > 0.0)
                  ////{
                    Sicherheitenvereinbarung lvSicherheitenvereinbarung = this.getListeSicherheitenvereinbarung().get(lvShum.getSicherheitenvereinbarungId());
                    return lvSicherheitenvereinbarung.getSicherheitenvereinbarungsId();
                  ////}
              }
            } 
        }
        return null;
    }

    
    /**
     * Liefert die Liste der Sicherheitenvereinbarungen
     * @return 
     */
    public HashMap<String, Sicherheitenvereinbarung> getListeSicherheitenvereinbarung()
    {
        return this.ivListeSicherheitenvereinbarung;
    }

    /**
     * Liefert die Liste der Sicherungsumfaenge
     * @return 
     */
    public HashMap<String, LinkedList<Sicherungsumfang>> getListeSicherungsumfang()
    {
        return this.ivListeSicherungsumfang;
    }
    
    /**
     * Liefert die Liste der Lasten
     * @return 
     */
    public HashMap<String, Last> getListeLast()
    {
        return this.ivListeLast;
    }

    /**
     * Liefert die Liste der Grundbuchverweise
     * @return 
     */
    public HashMap<String, Grundbuchverweis> getListeGrundbuchverweis()
    {
        return this.ivListeGrundbuchverweis;
    }
    
    /**
     * Liefert die Liste der Geschaeftspartner
     * @return 
     */
    public HashMap<String, Geschaeftspartner> getListeGeschaeftspartner()
    {
        return this.ivListeGeschaeftspartner;
    }

    /**
     * Liefert die Liste der Immobilien
     * @return 
     */
    public HashMap<String, Immobilie> getListeImmobilie()
    {
        return this.ivListeImmobilie;
    }
    
    /**
     * Liefert die Liste der Flugzeuge
     * @return
     */
    public HashMap<String, Flugzeug> getListeFlugzeug()
    {
    	return this.ivListeFlugzeug;
    }
    
    /**
     * Liefert die Liste der Triebwerke
     * @return
     */
    public HashMap<String, Triebwerk> getListeTriebwerk()
    {
    	return this.ivListeTriebwerk;
    }
    
    /**
     * Liefert die Liste der Schiffe
     * @return
     */
    public HashMap<String, Schiff> getListeSchiff()
    {
    	return this.ivListeSchiff;
    }

    /**
     * Liefert die Liste der Beleihungssaetze
     * @return 
     */
    public HashMap<String, Beleihungssatz> getListeBeleihungssatz()
    {
        return this.ivListeBeleihungssatz;
    }

    /**
     * Liefert die Liste der Grundbuchblaetter
     * @return 
     */
    public HashMap<String, Grundbuchblatt> getListeGrundbuchblatt()
    {
        return this.ivListeGrundbuchblatt;
    }

    /**
     * Liefert die Liste der Grundstuecke
     * @return 
     */
    public HashMap<String, Grundstueck> getListeGrundstueck()
    {
        return this.ivListeGrundstueck;
    }
    
    /**
     * Liefert den Vorlaufsatz
     * @return 
     */
    public Vorlaufsatz getVorlaufsatz()
    {
        return this.ivVorlaufsatz;
    }
    
    /**
     * Liefert die Liste der ObjektZuweisungsbetraege
     * @return 
     */
    public ObjektZuweisungsbetragListe getObjektZuweisungsbetragListe()
    {
        return this.ivOzwListe;
    }
    
    /**
     * @return the logger
     */
    public Logger getLogger() 
    {
        return this.ivLogger;
    }

    /**
     * Startroutine (main) SAPCMS_Neu
     * Wird nur zu Testzwecken benoetigt!
     * @param args
     */
    public static void main(String args[])
    { 
         if (!args[args.length - 1].endsWith(".ini"))
         {
            System.out.println("Starten:");
            System.out.println("SAPCMS <ini-Datei>");
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
        	
            String lvLoggingXML = lvReader.getPropertyString("SAPCMS", "log4jconfig", "Fehler");
            if (lvLoggingXML.equals("Fehler"))
            {
            	System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
            }
            else
            {
                DOMConfigurator.configure(lvLoggingXML);
            }     
            
            String lvImportVerzeichnisSAPCMS = lvReader.getPropertyString("SAPCMS", "ImportVerzeichnis", "Fehler");
            if (lvImportVerzeichnisSAPCMS.equals("Fehler"))
            {
                LOGGER_SAPCMS.error("Kein SAPCMS Import-Verzeichnis in der ini-Datei...");
                System.exit(1);
            }

            String lvSapcmsDatei = lvReader.getPropertyString("SAPCMS", "SAPCMS-Datei", "Fehler");
            if (lvSapcmsDatei.equals("Fehler"))
            {
                LOGGER_SAPCMS.error("Kein SAPCMS-Dateiname in der ini-Datei...");
                System.exit(1);
            }

            new SAPCMS_Neu(lvImportVerzeichnisSAPCMS + "\\" + lvSapcmsDatei, LOGGER_SAPCMS);
        }
        System.exit(0);
    }

}
