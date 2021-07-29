/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Sicherheiten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import nlb.txs.schnittstelle.ObjektZuweisungsbetrag.ObjektZuweisungsbetragListe;
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
import nlb.txs.schnittstelle.Sicherheiten.Entities.Triebwerk;
import nlb.txs.schnittstelle.Sicherheiten.Entities.Vorlaufsatz;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

public class SicherheitenDaten {
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

  // Quelle SAPCMS
  public static final int SAPCMS = 1;

  // Quelle IWHS/VVS
  public static final int VVS = 2;

  // Input-File Sicherheiten
  //private File ivFileSAPCMS;

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

  // Liste Zuweisungsbetraege - DarKa
  private ObjektZuweisungsbetragListe ivOzwListe;

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

  /**
   * Referenz auf Sicherheiten2Pfandbrief
   */
  private Sicherheiten2Pfandbrief ivSicherheiten2Pfandbrief;

  /**
   * Referenz auf Sicherheiten2RefiRegister
   */
  private Sicherheiten2RefiRegister ivSicherheiten2RefiRegister;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Quelle der Daten (SAP CMS oder VVS)
   */
  private int ivQuelle;

  /**
   * Konstruktor
   * @param pvFilename
   * @param pvQuelle
   * @param pvLogger
   */
  public SicherheitenDaten(String pvFilename, int pvQuelle, Logger pvLogger)
  {
    this.ivLogger = pvLogger;
    this.ivQuelle = pvQuelle;

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
    //ivSAPCMS2Pfandbrief = new Sicherheiten2Pfandbrief(this);
    //ivSAPCMS2RefiRegister = new Sicherheiten2RefiRegister(this);
    readSicherheiten(pvFilename, pvQuelle);

    ivSicherheiten2Pfandbrief = new Sicherheiten2Pfandbrief(this, pvLogger);
    ivSicherheiten2RefiRegister = new Sicherheiten2RefiRegister(this, pvLogger);
  }

  /**
   * Liest die Sicherheiten-Datei ein
   * @param pvDateiname Name der Sicherheiten-Datei
   * @param pvQuelle Quelle der Sicherheiten (SAPCMS oder VVS)
   */
  private void readSicherheiten(String pvDateiname, int pvQuelle)
  {
    ivLogger.info("Start - readSicherheiten");
    ivLogger.info("Quelle: " + ((pvQuelle == 1)?"SAP CMS":"VVS"));
    String lvZeile = null;

    // Oeffnen der Datei
    FileInputStream lvFis = null;
    File ivFile = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFile);
    }
    catch (Exception e)
    {
      ivLogger.error("Konnte Sicherheiten-Datei '" + pvDateiname + "' nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      while ((lvZeile = lvIn.readLine()) != null)  // Lesen Sicherheiten-Datei
      {
        // Parsen der einzelnen Entitaeten
        //if (lvZeile.contains("4250177830")) {
        //  ivLogger.info(lvZeile);
        //  }
        if (lvZeile.startsWith(VORLAUFSATZ))
        {
          ivVorlaufsatz = new Vorlaufsatz(ivLogger);
          ivVorlaufsatz.parseVorlaufsatz(lvZeile, pvQuelle);
          ivZaehlerVorlaufsatz++;
        }
        if (lvZeile.startsWith(SICHERHEITENVEREINBARUNG))
        {
          ivSicherheitenvereinbarung = new Sicherheitenvereinbarung(ivLogger);
          ivSicherheitenvereinbarung.parseSicherheitenvereinbarung(lvZeile, pvQuelle);
          ivListeSicherheitenvereinbarung.put(ivSicherheitenvereinbarung.getId(), ivSicherheitenvereinbarung);
          ivZaehlerSicherheitenvereinbarung++;
          //if (!ivSicherheitenvereinbarung.getAusfallbuergschaftKennzeichen().isEmpty())
          //  System.out.println(ivSicherheitenvereinbarung.toString());
        }
        if (lvZeile.startsWith(SICHERUNGSUMFANG))
        {
          ivSicherungsumfang = new Sicherungsumfang(ivLogger);
          ivSicherungsumfang.parseSicherungsumfang(lvZeile, this.getObjektZuweisungsbetragListe(), pvQuelle);
          if (ivListeSicherungsumfang.containsKey(ivSicherungsumfang.getGeschaeftswertId()))
          {
            LinkedList<Sicherungsumfang> helpListe = ivListeSicherungsumfang.get(ivSicherungsumfang.getGeschaeftswertId());
            helpListe.add(ivSicherungsumfang);
          }
          else
          {
            LinkedList<Sicherungsumfang> helpListe = new LinkedList<Sicherungsumfang>();
            helpListe.add(ivSicherungsumfang);
            //ivLogger.info("GeschaeftswertId: " + ivSicherungsumfang.getGeschaeftswertId());
            ivListeSicherungsumfang.put(ivSicherungsumfang.getGeschaeftswertId(), helpListe);
           }
          ivZaehlerSicherungsumfang++;
        }
        if (lvZeile.startsWith(LAST))
        {
          ivLast = new Last(ivLogger);
          ivLast.parseLast(lvZeile, pvQuelle);
          if (pvQuelle == SicherheitenDaten.VVS)
          {
            if (ivLast.getId().contains("-"))
            { // Keine GUID -> einfuegen - CT 22.04.2021
              ivListeLast.put(ivLast.getId(), ivLast);
              //ivListeLast.put(new Integer(ivZaehlerLast).toString(), ivLast);
              ivZaehlerLast++;
            }
            else
            {
              ivLogger.info("GUID;" + ivListeSicherheitenvereinbarung.get(ivLast.getSicherheitenvereinbarungId()).getSicherheitenvereinbarungsId());
            }
          }
          else {
            ivListeLast.put(ivLast.getId(), ivLast);
            ivZaehlerLast++;
          }
         }
        if (lvZeile.startsWith(GRUNDBUCHVERWEIS))
        {
          ivGrundbuchverweis = new Grundbuchverweis(ivLogger);
          ivGrundbuchverweis.parseGrundbuchverweis(lvZeile, pvQuelle);
          // Den Grundbuchverweis nicht beruecksichtigen, wenn das Loeschkennzeichen gesetzt ist.
          if (!ivGrundbuchverweis.getLoeschkennzeichen().equals("X"))
          {
            //ivListeGrundbuchverweis.put(ivGrundbuchverweis.getId(), ivGrundbuchverweis);
            ivListeGrundbuchverweis.put(new Integer(ivZaehlerGrundbuchverweis).toString(), ivGrundbuchverweis);
          }
          else
          {
            ivLogger.info("Bei Grundbuchverweis " + ivGrundbuchverweis.getId() + " ist das Loeschkennzeichen gesetzt.");
          }
          ivZaehlerGrundbuchverweis++;
        }
        if (lvZeile.startsWith(GESCHAEFTSPARTNER))
        {
          ivGeschaeftspartner = new Geschaeftspartner(ivLogger);
          ivGeschaeftspartner.parseGeschaeftspartner(lvZeile, pvQuelle);
          //ivListeGeschaeftspartner.put(ivGeschaeftspartner.getId(), ivGeschaeftspartner);
          // CT 05.11.2019 - Wird benoetigt fuer SAP CMS ueber SPOT
          ivListeGeschaeftspartner.put(new Integer(ivZaehlerGeschaeftspartner).toString(), ivGeschaeftspartner);
          ivZaehlerGeschaeftspartner++;
        }
        if (lvZeile.startsWith(IMMOBILIE))
        {
          ivImmobilie = new Immobilie(ivLogger);
          ivImmobilie.parseImmobilie(lvZeile, pvQuelle);
          ivListeImmobilie.put(ivImmobilie.getId(), ivImmobilie);
          ivZaehlerImmobilie++;
        }
        if (lvZeile.startsWith(FLUGZEUG))
        {
          ivFlugzeug = new Flugzeug(ivLogger);
          ivFlugzeug.parseFlugzeug(lvZeile, pvQuelle);
          ivListeFlugzeug.put(ivFlugzeug.getId(), ivFlugzeug);
          ivZaehlerFlugzeug++;
        }
        if (lvZeile.startsWith(TRIEBWERK))
        {
          ivTriebwerk = new Triebwerk(ivLogger);
          ivTriebwerk.parseTriebwerk(lvZeile, pvQuelle);
          ivListeTriebwerk.put(ivTriebwerk.getId(), ivTriebwerk);
          ivZaehlerTriebwerk++;
        }
        if (lvZeile.startsWith(SCHIFF))
        {
          ivSchiff = new Schiff(ivLogger);
          ivSchiff.parseSchiff(lvZeile, pvQuelle);
          ivListeSchiff.put(ivSchiff.getId(), ivSchiff);
          ivZaehlerSchiff++;
        }
        if (lvZeile.startsWith(BELEIHUNGSSATZ))
        {
          ivBeleihungssatz = new Beleihungssatz(ivLogger);
          ivBeleihungssatz.parseBeleihungssatz(lvZeile, pvQuelle);
          ivListeBeleihungssatz.put(new Integer(ivZaehlerBeleihungssatz).toString(), ivBeleihungssatz);
          ivZaehlerBeleihungssatz++;
        }
        if (lvZeile.startsWith(GRUNDBUCHBLATT))
        {
          ivGrundbuchblatt = new Grundbuchblatt(ivLogger);
          ivGrundbuchblatt.parseGrundbuchblatt(lvZeile, pvQuelle);
          ivListeGrundbuchblatt.put(ivGrundbuchblatt.getId(), ivGrundbuchblatt);
          //ivListeGrundbuchblatt.put(new Integer(ivZaehlerGrundbuchblatt).toString(), ivGrundbuchblatt);
          ivZaehlerGrundbuchblatt++;
        }
        if (lvZeile.startsWith(GRUNDSTUECK))
        {
          ivGrundstueck = new Grundstueck(ivLogger);
          ivGrundstueck.parseGrundstueck(lvZeile, pvQuelle);
          //if (ivQuelle == VVS)
          //{
          //  ivListeGrundstueck.put(ivGrundstueck.getId() + ivGrundstueck.getLaufendeNummer(), ivGrundstueck);
          //}
          //else
          //{
            ivListeGrundstueck.put(ivGrundstueck.getId(), ivGrundstueck);
          //ivListeGrundstueck.put(new Integer(ivZaehlerGrundstueck).toString(), ivGrundstueck);

          //}
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
      ivLogger.error("Konnte Sicherheiten-Datei nicht schliessen!");
    }

    ivLogger.info(getStatistik());
  }

  /**
   * Liefert eine Statistik als String
   * @return String mit Statistik
   */
  private String getStatistik()
  {
    StringBuilder lvOut = new StringBuilder();
    if (ivVorlaufsatz != null)
    {
      lvOut.append(ivVorlaufsatz.toString());
    }

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
   * Liefert die Liste der Sicherheitenvereinbarungen
   * @return Liste der Sicherheitenvereinbarungen
   */
  public HashMap<String, Sicherheitenvereinbarung> getListeSicherheitenvereinbarung()
  {
    return this.ivListeSicherheitenvereinbarung;
  }

  /**
   * Liefert die Liste der Sicherungsumfaenge
   * @return Liste der Sicherungsumfaenge
   */
  public HashMap<String, LinkedList<Sicherungsumfang>> getListeSicherungsumfang()
  {
    return this.ivListeSicherungsumfang;
  }

  /**
   * Liefert die Liste der Lasten
   * @return Liste der Lasten
   */
  public HashMap<String, Last> getListeLast()
  {
    return this.ivListeLast;
  }

  /**
   * Liefert die Liste der Grundbuchverweise
   * @return Liste der Grundbuchverweise
   */
  public HashMap<String, Grundbuchverweis> getListeGrundbuchverweis()
  {
    return this.ivListeGrundbuchverweis;
  }

  /**
   * Liefert die Liste der Geschaeftspartner
   * @return Liste der Geschaeftspartner
   */
  public HashMap<String, Geschaeftspartner> getListeGeschaeftspartner()
  {
    return this.ivListeGeschaeftspartner;
  }

  /**
   * Liefert die Liste der Immobilien
   * @return Liste der Immobilien
   */
  public HashMap<String, Immobilie> getListeImmobilie()
  {
    return this.ivListeImmobilie;
  }

  /**
   * Liefert die Liste der Flugzeuge
   * @return Liste der Flugzeuge
   */
  public HashMap<String, Flugzeug> getListeFlugzeug()
  {
    return this.ivListeFlugzeug;
  }

  /**
   * Liefert die Liste der Triebwerke
   * @return Liste der Triebwerke
   */
  public HashMap<String, Triebwerk> getListeTriebwerk()
  {
    return this.ivListeTriebwerk;
  }

  /**
   * Liefert die Liste der Schiffe
   * @return Liste der Schiffe
   */
  public HashMap<String, Schiff> getListeSchiff()
  {
    return this.ivListeSchiff;
  }

  /**
   * Liefert die Liste der Beleihungssaetze
   * @return Liste der Beleihungswerte
   */
  public HashMap<String, Beleihungssatz> getListeBeleihungssatz()
  {
    return this.ivListeBeleihungssatz;
  }

  /**
   * Liefert die Liste der Grundbuchblaetter
   * @return Liste der Grundbuchblaetter
   */
  public HashMap<String, Grundbuchblatt> getListeGrundbuchblatt()
  {
    return this.ivListeGrundbuchblatt;
  }

  /**
   * Liefert die Liste der Grundstuecke
   * @return Liste der Grundstuecke
   */
  public HashMap<String, Grundstueck> getListeGrundstueck()
  {
    return this.ivListeGrundstueck;
  }

  /**
   * Liefert den Vorlaufsatz
   * @return Vorlaufsatz
   */
  public Vorlaufsatz getVorlaufsatz()
  {
    return this.ivVorlaufsatz;
  }

  /**
   * Liefert die Liste der ObjektZuweisungsbetraege
   * @return Liste der ObjektZuweisungsbetraege
   */
  public ObjektZuweisungsbetragListe getObjektZuweisungsbetragListe()
  {
    return this.ivOzwListe;
  }

  /**
   * Liefert die Referenz auf Sicherheiten2Pfandbrief
   * @return Referenz auf Sicherheiten2Pfandbrief
   */
  public Sicherheiten2Register getSicherheiten2Pfandbrief()
  {
    return this.ivSicherheiten2Pfandbrief;
  }

  /**
   * Liefert die Referenz auf Sicherheiten2RefiRegister
   * @return Referenz auf Sicherheiten2RefiRegister
   */
  public Sicherheiten2Register getSicherheiten2RefiRegister()
  {
    return this.ivSicherheiten2RefiRegister;
  }

  /**
   * Liefert die Quelle der Daten
   * @return 1 -> SAP CMS; 2 -> VVS
   */
  public int getQuelle()
  {
    return this.ivQuelle;
  }

  /**
   * Liefert den Buergschaftprozentsatz
   * @param pvKontonummer Kontonummer
   */
  public String getBuergschaftprozent(String pvKontonummer)
  {
    String lvBuergschaftprozent = "0.0";
    Sicherungsumfang lvShum = null;

    // Passende Liste der Sicherungsumfaenge zur Kontonummer ermitteln
    LinkedList<Sicherungsumfang> lvHelpListe = this.getListeSicherungsumfang().get(pvKontonummer);

    // Nur Sicherheiten verwenden, wenn ZW > 0 ist
    if (lvHelpListe != null)
    {
      for (int x = 0; x < lvHelpListe.size(); x++)
      {
        lvShum = lvHelpListe.get(x);
        ivLogger.info("SicherungsumfangId: " + lvShum.getId() + " Zuweisungsbetrag: " + lvShum.getZuweisungsbetrag());
        ivLogger.info("DeckungsregisterRelevant: " + lvShum.getDeckungsregisterRelevant());

        if (lvShum.getDeckungsregisterRelevant().equals("01")) // Buergschaft relevant
        {
          Sicherheitenvereinbarung lvSicherheitenvereinbarung = this.getListeSicherheitenvereinbarung().get(lvShum.getSicherheitenvereinbarungId());
          lvBuergschaftprozent = lvSicherheitenvereinbarung.getVerbuergungssatz();
          ivLogger.info("Verbuergungssatz: " + lvBuergschaftprozent);
        }
      }
    }
    else
    {
      ivLogger.info("Keine passende Buergschaft in den Sicherheiten-Daten gefunden!");
    }
    return lvBuergschaftprozent;
  }
}
