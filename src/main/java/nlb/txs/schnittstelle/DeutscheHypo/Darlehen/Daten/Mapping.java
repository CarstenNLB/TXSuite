/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class Mapping {

  private String kontonummer;
  private String sicherheitenNummer;

  // Die folgenden 4 Variablen werden nicht benoetigt.
  private String DSI_SART;
  private String OBNR;
  private String KONTO_NR_2;
  private String DENR;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor - initialisert die Variablen mit leeren Strings
   * @param pvLogger log4j-Logger
   */
  public Mapping(Logger pvLogger)
  {
    ivLogger = pvLogger;

    kontonummer = new String();
    sicherheitenNummer = new String();
    DSI_SART = new String();
    OBNR = new String();
    KONTO_NR_2 = new String();
    DENR = new String();
  }

  /**
   * Einlesen der Mapping-Daten
   * @param pvDateiname Dateiname der Mapping-Daten
   * @param pvListeMapping Liste der Mapping-Daten
   * @param pvLogger log4j-Logger
   * @return Anzahl der eingelesenen Mapping-Daten
   */
  public static int readMapping(String pvDateiname, HashMap<String, Mapping> pvListeMapping, Logger pvLogger)
  {
    String lvZeile = null;
    int lvZaehlerMapping = 0;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDH = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDH);
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte Mapping-Datei nicht oeffnen!");
      return lvZaehlerMapping;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Kunden-Datei ein
      {
        Mapping lvMapping = new Mapping(pvLogger);
        if (lvMapping.parseMapping(lvZeile)) // Parsen der Felder
        {
          lvZaehlerMapping++;
          pvListeMapping.put(new String("" + lvZaehlerMapping), lvMapping);
        }
      }
    }
    catch (Exception e)
    {
      pvLogger.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      pvLogger.error("Konnte Mapping-Datei nicht schliessen!");
    }

    return lvZaehlerMapping;
  }


  /**
   * Zerlegt eine Mappingzeile in die einzelnen Felder/Werte
   * @param pvZeile
   * @return
   */
  private boolean parseMapping(String pvZeile)
  {
    String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
    int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
    int    lvZzStr=0;              // pointer fuer satzbereich

    // Steuerung/Iteration Eingabesatz
    for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
    {
      // wenn Semikolon erkannt
      if (pvZeile.charAt(lvZzStr) == ';')
      {
        this.setMapping(lvLfd, lvTemp.trim());

        lvLfd++;                  // naechste Feldnummer
        // loeschen Zwischenbuffer
        lvTemp = new String();
      }
      else
      {
        // uebernehmen Byte aus Eingabesatzposition
        lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
      }
    } // ende for

    // Letzte Feld auch noch setzen
    this.setMapping(lvLfd, lvTemp.trim());

    return true;
  }

  /**
   * Setzt einen Wert der Tabelle 'Mapping'
   * @param pvPos Position
   * @param pvWert Wert
   */
  private void setMapping(int pvPos, String pvWert)
  {
     switch (pvPos)
    {
      case 0:
        this.kontonummer = pvWert;
        break;
      case 1:
        this.sicherheitenNummer = pvWert;
        break;
      case 2:
        this.DSI_SART = pvWert;
        break;
      case 3:
        this.OBNR = pvWert;
        break;
      case 4:
        this.KONTO_NR_2 = pvWert;
        break;
      case 5:
        this.DENR = pvWert;
        break;
      default:
        ivLogger.error("Mapping: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
    }
  }

  /**
   * Liefert die Kontonummer
   * @return Kontonummer
   */
  public String getKontonummer() {
    return kontonummer;
  }

  /**
   * Setzt die Kontonummer
   * @param kontonummer Kontonummer
   */
  public void setKontonummer(String kontonummer) {
    this.kontonummer = kontonummer;
  }

  /**
   * Liefert die Sicherheitennummer
   * @return Sicherheitennummer
   */
  public String getSicherheitenNummer() {
    return sicherheitenNummer;
  }

  /**
   * Setzt die Sicherheitennummer
   * @param sicherheitenNummer Sicherheitennummer
   */
  public void setSicherheitenNummer(String sicherheitenNummer) {
    this.sicherheitenNummer = sicherheitenNummer;
  }

  /*
  public String getDSI_SART() {
    return DSI_SART;
  }

  public void setDSI_SART(String DSI_SART) {
    this.DSI_SART = DSI_SART;
  }

  public String getOBNR() {
    return OBNR;
  }

  public void setOBNR(String OBNR) {
    this.OBNR = OBNR;
  }

  public String getKONTO_NR_2() {
    return KONTO_NR_2;
  }

  public void setKONTO_NR_2(String KONTO_NR_2) {
    this.KONTO_NR_2 = KONTO_NR_2;
  }

  public String getDENR() {
    return DENR;
  }

  public void setDENR(String DENR) {
    this.DENR = DENR;
  } */
}
