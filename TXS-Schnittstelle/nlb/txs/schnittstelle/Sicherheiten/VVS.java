/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Sicherheiten;

import nlb.txs.schnittstelle.Utilities.IniReader;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class VVS
{
  // Wird nur zu Testzwecken benoetigt!
  private static Logger LOGGER_VVS = Logger.getLogger("TXSVVSLogger");

  /**
   * Sicherheiten-Daten aus VVS
   */
  private SicherheitenDaten ivSicherheitenDaten;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor
   * @param pvFilename Name der Datei mit den Sicherheiten-Daten aus VVS
   * @param pvLogger log4j-Logger
   */
  public VVS(String pvFilename, Logger pvLogger)
  {
    this.ivLogger = pvLogger;
    this.ivSicherheitenDaten = new SicherheitenDaten(pvFilename, SicherheitenDaten.VVS, pvLogger);
    // Initialisierung der Listen Entitaeten
    //ivListeSicherheitenvereinbarung = new HashMap<String, Sicherheitenvereinbarung>();
    //ivListeSicherungsumfang = new HashMap<String, LinkedList<Sicherungsumfang>>();
    //ivListeLast = new HashMap<String, Last>();
    //ivListeGrundbuchverweis = new HashMap<String, Grundbuchverweis>();
    //ivListeGeschaeftspartner = new HashMap<String, Geschaeftspartner>();
    //ivListeImmobilie = new HashMap<String, Immobilie>();
    //ivListeFlugzeug = new HashMap<String, Flugzeug>();
    //ivListeTriebwerk = new HashMap<String, Triebwerk>();
    //ivListeSchiff = new HashMap<String, Schiff>();
    //ivListeBeleihungssatz = new HashMap<String, Beleihungssatz>();
    //ivListeGrundbuchblatt = new HashMap<String, Grundbuchblatt>();
    //ivListeGrundstueck = new HashMap<String, Grundstueck>();
    //readVVS(pvFilename);
  }

  /**
   * Startroutine (main) VVS
   * Wird nur zu Testzwecken benoetigt!
   * @param args Uebergabeparameter
   */
  public static void main(String args[])
  {
    if (!args[args.length - 1].endsWith(".ini"))
    {
      System.out.println("Starten:");
      System.out.println("VVS <ini-Datei>");
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

      String lvLoggingXML = lvReader.getPropertyString("VVS", "log4jconfig", "Fehler");
      if (lvLoggingXML.equals("Fehler"))
      {
        System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
      }
      else
      {
        DOMConfigurator.configure(lvLoggingXML);
      }

      String lvImportVerzeichnisVVS = lvReader.getPropertyString("VVS", "ImportVerzeichnis", "Fehler");
      if (lvImportVerzeichnisVVS.equals("Fehler"))
      {
        LOGGER_VVS.error("Kein VVS Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_VVS.info("ImportVerzeichnis: " + lvImportVerzeichnisVVS);
      }

      String lvVVSDatei = lvReader.getPropertyString("VVS", "VVS-Datei", "Fehler");
      if (lvVVSDatei.equals("Fehler"))
      {
        LOGGER_VVS.error("Kein VVS-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_VVS.info("VVS-Datei: " + lvVVSDatei);
      }

      VVS lvVVS = new VVS(lvImportVerzeichnisVVS + "\\" + lvVVSDatei, LOGGER_VVS);
    }
    System.exit(0);
  }
}
