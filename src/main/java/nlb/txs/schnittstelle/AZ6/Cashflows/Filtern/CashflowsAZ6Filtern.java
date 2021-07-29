/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.AZ6.Cashflows.Filtern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class CashflowsAZ6Filtern
{
  /**
   * log4j-Logger
   */
  private static Logger LOGGER = Logger.getLogger("TXSCashflowsAZ6FilternLogger");

  /**
   * Liste der (relevanten) Kontonummern
   */
  private HashSet<String> ivListeKontonummern;

  /**
   * Startroutine
   * @param argv Aufrufparameter
   */
  public static void main(String argv[])
  {
    if (!argv[argv.length - 1].endsWith(".ini"))
    {
      System.out.println("Starten:");
      System.out.println("CashflowsAZ6Filtern <ini-Datei>");
      System.exit(1);
    }
    else
    {
      IniReader lvReader = null;
      try
      {
        lvReader = new IniReader(argv[argv.length - 1]);
      }
      catch (Exception exp)
      {
        System.out.println("Fehler beim Laden der ini-Datei...");
        System.exit(1);
      }
      if (lvReader != null)
      {
        String lvLoggingXML = lvReader.getPropertyString("CashflowsAZ6Filtern", "log4jconfig", "Fehler");
        if (lvLoggingXML.equals("Fehler"))
        {
          System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
        }
        else
        {
          DOMConfigurator.configure(lvLoggingXML);
        }

        String lvInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
        if (lvInstitut.equals("Fehler"))
        {
          LOGGER.error("Keine Institutsnummer in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("Institut: " + lvInstitut);
        }

        String lvImportVerzeichnis = lvReader.getPropertyString("CashflowsAZ6Filtern", "ImportVerzeichnis", "Fehler");
        if (lvImportVerzeichnis.equals("Fehler"))
        {
          LOGGER.error("Kein Import-Verzeichnis in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("ImportVerzeichnis: " + lvImportVerzeichnis);
        }

        String lvExportVerzeichnis = lvReader.getPropertyString("CashflowsAZ6Filtern", "ExportVerzeichnis", "Fehler");
        if (lvExportVerzeichnis.equals("Fehler"))
        {
          LOGGER.error("Kein Export-Verzeichnis in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("ExportVerzeichnis: " + lvExportVerzeichnis);
        }

        String lvCashflowsInput =  lvReader.getPropertyString("CashflowsAZ6Filtern", "CashflowsInput-Datei", "Fehler");
        if (lvCashflowsInput.equals("Fehler"))
        {
          LOGGER.error("Kein CashflowsInput-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("CashflowsInput-Dateiname: " + lvCashflowsInput);
        }

        String lvCashflowsOutput = lvReader.getPropertyString("CashflowsAZ6Filtern", "CashflowsOutput-Datei", "Fehler");
        if (lvCashflowsOutput.equals("Fehler"))
        {
          LOGGER.error("Kein CashflowsOutput-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("CashflowsOutput-Datei: " + lvCashflowsOutput);
        }

        String lvQuellsystemDatei =  lvReader.getPropertyString("AZ6", "Quellsystem-Datei", "Fehler");
        if (lvQuellsystemDatei.equals("Fehler"))
        {
          LOGGER.error("Kein Quellsystem-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("Quellsystem-Datei: " + lvQuellsystemDatei);
        }

        new CashflowsAZ6Filtern(lvInstitut, lvImportVerzeichnis, lvExportVerzeichnis, lvCashflowsInput, lvCashflowsOutput, lvQuellsystemDatei);
      }
    }
    System.exit(0);
  }

  /**
   * Konstruktor
   * @param pvInstitut Institut
   * @param pvImportVerzeichnis ImportVerzeichnis der Eingabedatei der Cashflows
   * @param pvExportVerzeichnis ExportVerzeichnis der Ausgabedatei der Cashflows
   * @param pvCashflowsInput Eingabedatei der Cashflows
   * @param pvCashflowsOutput Ausgabedatei der Cashflows
   * @param pvQuellsystemDatei Datei mit Kontonummern fuer die die Cashflows benoetigt werden.
   */
  public CashflowsAZ6Filtern(String pvInstitut, String pvImportVerzeichnis, String pvExportVerzeichnis, String pvCashflowsInput, String pvCashflowsOutput, String pvQuellsystemDatei)
  {
    int lvZaehlerQuellsysteme = 0;
    ivListeKontonummern = new HashSet<String>();

    // Einlesen der AZ6QuellsystemDaten
    File lvQuellsystemDatenFile = new File(pvImportVerzeichnis + "\\" + pvQuellsystemDatei);
    if (lvQuellsystemDatenFile != null && lvQuellsystemDatenFile.exists())
    {
      FileInputStream lvQuellsystemDatenIs = null;
      BufferedReader lvQuellsystemDatenIn = null;
      try
      {
        lvQuellsystemDatenIs = new FileInputStream(lvQuellsystemDatenFile);
        lvQuellsystemDatenIn = new BufferedReader(new InputStreamReader(lvQuellsystemDatenIs));
      }
      catch (Exception e)
      {
        LOGGER.info("Konnte Quellsystem-Datei " + pvImportVerzeichnis + "\\" + pvQuellsystemDatei + " nicht oeffnen!");
      }
      String lvQuellsystemDatenZeile = new String();
      try
      {
        while ((lvQuellsystemDatenZeile = lvQuellsystemDatenIn.readLine()) != null)  // Lese Quellsystem-Datei
        {
          if (lvQuellsystemDatenZeile != null)
          {
            //LOGGER.info("Kontonummer: " + lvQuellsystemDatenZeile.substring(0, 10));
            ivListeKontonummern.add(lvQuellsystemDatenZeile.substring(0, 10));
            lvZaehlerQuellsysteme++;
          }
        }
      }
      catch (Exception e)
      {
        LOGGER.error("Fehler beim Verarbeiten einer Zeile:" + lvQuellsystemDatenZeile);
        e.printStackTrace();
      }
      try
      {
        lvQuellsystemDatenIn.close();
        lvQuellsystemDatenIs.close();
      }
      catch (Exception e)
      {
        LOGGER.error("Konnte Quellsystem-Datei " + pvImportVerzeichnis + "\\" + pvQuellsystemDatei + " nicht schliessen!");
      }
    }
    else
    {
      LOGGER.info("Keine Quellsystem-Datei " + pvImportVerzeichnis + "\\" + pvQuellsystemDatei + " gefunden...");
    }

    LOGGER.info("Anzahl Kontonummern in der Quellsystem-Datei: " + lvZaehlerQuellsysteme);

    // Filterung der Cashflows starten
    filterCashflows(pvImportVerzeichnis + "\\" + pvCashflowsInput, pvExportVerzeichnis + "\\" + pvCashflowsOutput);
  }

  /**
   * Filtert die Cashflows
   * Nur Netto-Zeilen sind relevant und die Kontonummer muss in der Liste stehen.
   * @param pvDateiInput Eingabedatei der Cashflows
   * @param pvDateiOutput Ausgabedatei der Cashflows
   */
  private void filterCashflows(String pvDateiInput, String pvDateiOutput)
  {
    int lvZaehlerVorlaufsatz = 0;
    int lvZaehlerCashflows = 0;
    int lvZaehlerWriteCashflows = 0;
    String lvZeile = null;

    FileOutputStream lvFos = null;
    File lvFileCashflowsOutput = new File(pvDateiOutput);
    try
    {
      lvFos = new FileOutputStream(lvFileCashflowsOutput);
    }
    catch (Exception e)
    {
      LOGGER.info("Konnte OutputDatei '" + lvFileCashflowsOutput.getAbsolutePath() + "' nicht oeffnen!");
    }

    // Oeffnen der CashflowInput-Datei
    FileInputStream lvFis = null;
    File lvFileCashflowsInput = new File(pvDateiInput);
    try
    {
      lvFis = new FileInputStream(lvFileCashflowsInput);
    }
    catch (Exception e)
    {
      LOGGER.error("Konnte InputDatei '" + lvFileCashflowsInput.getAbsolutePath() + "' nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
    boolean lvStart = true;

    try
    {
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Cashflow-Datei
      {
        if (lvStart)
        {
          lvFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
          lvZaehlerVorlaufsatz++;
          lvStart = false;

        }
        else
        {
           // Nur Netto-Zeilen sind relevant und die Kontonummer muss in der Liste stehen
          if (lvZeile.contains(";N;") && ivListeKontonummern.contains(lvZeile.substring(5, 15)))
          {
            lvFos.write((lvZeile + StringKonverter.lineSeparator).getBytes());
            lvZaehlerWriteCashflows++;
          }

          lvZaehlerCashflows++;
        }
      }
    }
    catch (Exception e)
    {
      LOGGER.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
      lvFis.close();
    }
    catch (Exception e)
    {
      LOGGER.error("Konnte CashflowAZ6-InputDatei nicht schliessen!");
    }

    try
    {
      lvFos.close();
    }
    catch (Exception e)
    {
      LOGGER.error("Konnte CashflowAZ6-OutputDatei nicht schliessen!");
    }

    LOGGER.info("Anzahl Vorlaufsaetze: " + lvZaehlerVorlaufsatz);
    LOGGER.info("Anzahl gelesener Cashflow Zeilen: " + lvZaehlerCashflows);
    LOGGER.info("Anzahl geschriebener Cashflow Zeilen: " + lvZaehlerWriteCashflows);

  }

}
