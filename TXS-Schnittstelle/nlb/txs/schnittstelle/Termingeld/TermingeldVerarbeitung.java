/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Termingeld;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Termingeld.Daten.Termingeld;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

public class TermingeldVerarbeitung
{
  // Logger fuer Termingelder
  private static Logger LOGGER_TERMINGELD = Logger.getLogger("TXSTermingeldLogger");

  /**
   * Institutsnummer
   */
  private String ivInstitutsnummer;

  /**
   * Import-Verzeichnis der Termingeld-Datei
   */
  private String ivImportVerzeichnis;

  /**
   * Export-Verzeichnis der TXS-Importdatei
   */
  private String ivExportVerzeichnis;

  /**
   * Dateiname der Termingeld-Datei
   */
  private String ivTermingeldInputDatei;

  /**
   * Dateiname der TXS-Importdatei
   */
  private String ivTermingeldOutputDatei;

  /**
   * Ausgabedatei der TXS-Importdaten
   */
  private OutputDarlehenXML ivOutputDarlehenXML;

  /**
   * KundeRequest-Datei
   */
  private String ivKundeRequestDatei;

  /**
   * Ausgabedatei der Kundennummern
   */
  private KundennummernOutput ivKundennummernOutput;

  /**
   * Vorlaufsatz
   */
  private Vorlaufsatz ivVorlaufsatz;

  /**
   * Termingeld
   */
  private Termingeld ivTermingeld;

  /**
   * Zaehler fuer die Anzahl der Vorlaufsaetze
   */
  private int ivZaehlerVorlaufsatz;

  /**
   * Zaehler fuer die Anzahl der Termingelder
   */
  private int ivZaehlerTermingelder;

  /**
   * Konstruktor fuer Termingelder Verarbeitung
   * @param pvReader
   */
  public TermingeldVerarbeitung(IniReader pvReader)
  {

    if (pvReader != null)
    {
      ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
      if (ivInstitutsnummer.equals("Fehler"))
      {
        LOGGER_TERMINGELD.error("Keine Institutsnummer in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_TERMINGELD.info("Institutsnummer: " + ivInstitutsnummer);
      }

      ivImportVerzeichnis = pvReader.getPropertyString("Termingeld", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_TERMINGELD.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_TERMINGELD.info("ImportVerzeichnis: " + ivImportVerzeichnis);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("Termingeld", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_TERMINGELD.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_TERMINGELD.info("ExportVerzeichnis: " + ivExportVerzeichnis);
      }

      ivTermingeldInputDatei =  pvReader.getPropertyString("Termingeld", "TermingeldInput-Datei", "Fehler");
      if (ivTermingeldInputDatei.equals("Fehler"))
      {
        LOGGER_TERMINGELD.error("Kein TermingeldInput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_TERMINGELD.info("TermingeldInput-Datei: " + ivTermingeldInputDatei);
      }

      ivTermingeldOutputDatei =  pvReader.getPropertyString("Termingeld", "TermingeldOutput-Datei", "Fehler");
      if (ivTermingeldOutputDatei.equals("Fehler"))
      {
        LOGGER_TERMINGELD.error("Kein TermingeldOutput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_TERMINGELD.info("TermingeldOutput-Datei: " + ivTermingeldOutputDatei);
      }

      ivKundeRequestDatei = pvReader.getPropertyString("Termingeld", "KundeRequestDatei", "Fehler");
      if (ivKundeRequestDatei.equals("Fehler"))
      {
        LOGGER_TERMINGELD.error("Kein KundeRequest-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_TERMINGELD.info("KundeRequestDatei: " + ivKundeRequestDatei);
      }

      // Verarbeitung starten
      startVerarbeitung();
    }
    System.exit(0);
  }

  /**
   * Start der Verarbeitung
   */
  private void startVerarbeitung()
  {
    // Initialisierung der Zaehler
    ivZaehlerVorlaufsatz = 0;
    ivZaehlerTermingelder = 0;

     // Darlehen XML-Datei im TXS-Format
    ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivTermingeldOutputDatei, LOGGER_TERMINGELD);
    ivOutputDarlehenXML.openXML();
    ivOutputDarlehenXML.printXMLStart();
    ivOutputDarlehenXML.printTXSImportDatenStart();

    // Termingelder-Daten einlesen und verarbeiten
    readTermingelder(ivImportVerzeichnis + "\\" + ivTermingeldInputDatei);

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    // Statistik ausgeben
    LOGGER_TERMINGELD.info(getStatistik());

    // KundeRequest-Datei schliessen
    try
    {
      ivKundennummernOutput.close();
    }
    catch (Exception e)
    {
      LOGGER_TERMINGELD.error("Fehler beim Schliessen der KundeRequest-Datei");
    }
  }

  /**
   * Einlesen und Verarbeiten der Termingelder-Daten
   * @oaram pvDateiname
   */
  private void readTermingelder(String pvDateiname)
  {
    String lvZeile = null;
    ivVorlaufsatz = new Vorlaufsatz(LOGGER_TERMINGELD);
    ivTermingeld = new Termingeld(LOGGER_TERMINGELD);

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File lvFileTermingeld = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(lvFileTermingeld);
    }
    catch (Exception e)
    {
      LOGGER_TERMINGELD.error("Konnte Termingelder-Datei nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
    boolean lvStart = true;

    try
    {
      while ((lvZeile = lvIn.readLine()) != null)  // Lesen LoanIQ-Datei
      {
        if (lvStart)
        {
          ivVorlaufsatz.parseVorlaufsatz(lvZeile);
          ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()));
          ivZaehlerVorlaufsatz++;
          lvStart = false;

          // KundeRequest-Datei oeffnen
          ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_TERMINGELD);
          ivKundennummernOutput.open();
          ivKundennummernOutput.printVorlaufsatz(ivInstitutsnummer, "Pfandbrief");
        }
        else
        {
          //System.out.println("lvZeile: " + lvZeile);
          if (ivTermingeld.parseTermingeld(lvZeile)) // Parsen der Felder
          {
            if (isAusplatzierungsmerkmalRelevant())
            {
              ivTermingeld.verarbeiteTermingeld(ivOutputDarlehenXML, ivVorlaufsatz);
            }
            ivZaehlerTermingelder++;
          }
        }
      }
    }
    catch (Exception e)
    {
      LOGGER_TERMINGELD.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_TERMINGELD.error("Konnte Termingelder-Datei nicht schliessen!");
    }
  }

  /**
   * Liefert eine Statistik als String
   * @return
   */
  private String getStatistik()
  {
    StringBuffer lvOut = new StringBuffer();

    lvOut.append(ivVorlaufsatz.toString());

    lvOut.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerTermingelder + " Termingelder gelesen..." + StringKonverter.lineSeparator);

    return lvOut.toString();
  }

  /**
   * Pruefung ob das Ausplatzierungsmerkmal relevant ist
   * @return true -> relevant; false -> nicht relevant
   */
  private boolean isAusplatzierungsmerkmalRelevant()
  {
    // CT 09.06.2020 - Erst einmal alles nehmen
    boolean lvRelevant = true;

    return lvRelevant;
  }

}
