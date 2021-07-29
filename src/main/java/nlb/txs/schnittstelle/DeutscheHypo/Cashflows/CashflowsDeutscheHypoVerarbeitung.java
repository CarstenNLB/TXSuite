/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.DeutscheHypo.Cashflows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import nlb.txs.schnittstelle.DeutscheHypo.Cashflows.Daten.Cashflow;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.QuellsystemDaten.QuellsystemDaten;
import nlb.txs.schnittstelle.QuellsystemDaten.QuellsystemListe;
import nlb.txs.schnittstelle.Transaktion.TXSCashflowDaten;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

public class CashflowsDeutscheHypoVerarbeitung
{
  // Logger fuer Cashflows
  private static Logger LOGGER_CASHFLOWS_DH = Logger.getLogger("TXSCashflowsDHLogger");

  /**
   * Institutsnummer
   */
  private String ivInstitutsnummer;

  /**
   * Import-Verzeichnis der Cashflows-Datei
   */
  private String ivImportVerzeichnis;

  /**
   * Export-Verzeichnis der TXS-Importdatei
   */
  private String ivExportVerzeichnis;

  /**
   * Dateiname der Cashflows-Datei
   */
  private String ivCashflowsInputDatei;

  /**
   * Dateiname der TXS-Importdatei
   */
  private String ivCashflowsOutputDatei;

  /**
   * Dateiname der Finanzgeschaefte Quellsystem-Datei
   */
  private String ivQuellsystemDateiFG;

  /**
   * Dateiname der Wertpapiere Quellsystem-Datei
   */
  private String ivQuellsystemDateiWP;

  /**
   * Liste von Kontonummern mit Quellsystem
   */
  private QuellsystemListe ivQuellsystemDatenListe;

  /**
   * Ein Cashflow
   */
  private Cashflow ivCashflow;

  /**
   * Zaehler fuer die Anzahl der Finanzgeschaefte
   */
  private int ivZaehlerCashflows;

  /**
   * Zaehler fuer die Anzahl der Finanzgeschaefte, fuer die Cashflows geschrieben wurden
   */
  private int ivZaehlerWriteFinanzgeschaefte;

  /**
   * Importdatei TXS
   */
  private OutputDarlehenXML ivOutputDarlehenXML;

  /**
   * Konstruktor fuer Verarbeitung Cashflows
   * @param pvReader
   */
  public CashflowsDeutscheHypoVerarbeitung(IniReader pvReader)
  {
    LOGGER_CASHFLOWS_DH = Logger.getLogger("TXSCashflowsDHLogger");

    if (pvReader != null)
    {
      ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
      if (ivInstitutsnummer.equals("Fehler"))
      {
        LOGGER_CASHFLOWS_DH.error("Keine Institutsnummer in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_CASHFLOWS_DH.info("Institut: " + ivInstitutsnummer);
      }

      ivImportVerzeichnis = pvReader.getPropertyString("CashflowsDH", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_CASHFLOWS_DH.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_CASHFLOWS_DH.info("ImportVerzeichnis: " + ivImportVerzeichnis);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("CashflowsDH", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_CASHFLOWS_DH.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_CASHFLOWS_DH.info("ExportVerzeichnis: " + ivExportVerzeichnis);
      }

      ivCashflowsInputDatei =  pvReader.getPropertyString("CashflowsDH", "CashflowsInput-Datei", "Fehler");
      if (ivCashflowsInputDatei.equals("Fehler"))
      {
        LOGGER_CASHFLOWS_DH.error("Kein CashflowsInput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_CASHFLOWS_DH.info("CashflowsInput-Datei: " + ivCashflowsInputDatei);
      }

      ivCashflowsOutputDatei =  pvReader.getPropertyString("CashflowsDH", "CashflowsOutput-Datei", "Fehler");
      if (ivCashflowsOutputDatei.equals("Fehler"))
      {
        LOGGER_CASHFLOWS_DH.error("Kein CashflowsOutput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_CASHFLOWS_DH.info("CashflowsOutput-Datei: " + ivCashflowsOutputDatei);
      }

      ivQuellsystemDateiFG =  pvReader.getPropertyString("DH", "Quellsystem-Datei", "Fehler");
      if (ivQuellsystemDateiFG.equals("Fehler"))
      {
        LOGGER_CASHFLOWS_DH.error("Kein Quellsystem-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_CASHFLOWS_DH.info("Quellsystem-Dateiname - FG: " + ivQuellsystemDateiFG);
      }

      ivQuellsystemDateiWP =  pvReader.getPropertyString("WertpapiereDH", "Quellsystem-Datei", "Fehler");
      if (ivQuellsystemDateiWP.equals("Fehler"))
      {
        LOGGER_CASHFLOWS_DH.error("Kein Quellsystem-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_CASHFLOWS_DH.info("Quellsystem-Dateiname - WP: " + ivQuellsystemDateiWP);
      }

      // Verarbeitung starten
      startVerarbeitung();
    }
  }

  /**
   * Verarbeitungsstart
   */
  private void startVerarbeitung()
  {
    int lvZaehlerQuellsysteme = 0;

    // Einlesen der QuellsystemDatenFG
    File lvQuellsystemDatenFile = new File(ivImportVerzeichnis + "\\" + ivQuellsystemDateiFG);
    ivQuellsystemDatenListe = new QuellsystemListe();
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
        LOGGER_CASHFLOWS_DH.info("Konnte Quellsystem-Datei " + ivImportVerzeichnis + "\\" + ivQuellsystemDateiFG + " nicht oeffnen!");
      }
      String lvQuellsystemDatenZeile = new String();
      try
      {
        while ((lvQuellsystemDatenZeile = lvQuellsystemDatenIn.readLine()) != null)  // Lese Quellsystem-Datei
        {
          if (lvQuellsystemDatenZeile != null)
          {
            ivQuellsystemDatenListe.parseQuellsystemDaten(lvQuellsystemDatenZeile, LOGGER_CASHFLOWS_DH);
            lvZaehlerQuellsysteme++;
          }
        }
      }
      catch (Exception e)
      {
        LOGGER_CASHFLOWS_DH.error("Fehler beim Verarbeiten einer Zeile:" + lvQuellsystemDatenZeile);
        e.printStackTrace();
      }
      try
      {
        lvQuellsystemDatenIn.close();
        lvQuellsystemDatenIs.close();
      }
      catch (Exception e)
      {
        LOGGER_CASHFLOWS_DH.error("Konnte Quellsystem-Datei " + ivImportVerzeichnis + "\\" + ivQuellsystemDateiFG + " nicht schliessen!");
      }
    }
    else
    {
      LOGGER_CASHFLOWS_DH.info("Keine Quellsystem-Datei " + ivImportVerzeichnis + "\\" + ivQuellsystemDateiFG + " gefunden...");
    }

    // Einlesen der QuellsystemDatenWP
    lvQuellsystemDatenFile = new File(ivImportVerzeichnis + "\\" + ivQuellsystemDateiWP);
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
        LOGGER_CASHFLOWS_DH.info("Konnte Quellsystem-Datei " + ivImportVerzeichnis + "\\" + ivQuellsystemDateiWP + " nicht oeffnen!");
      }
      String lvQuellsystemDatenZeile = new String();
      try
      {
        while ((lvQuellsystemDatenZeile = lvQuellsystemDatenIn.readLine()) != null)  // Lese Quellsystem-Datei
        {
          if (lvQuellsystemDatenZeile != null)
          {
            ivQuellsystemDatenListe.parseQuellsystemDaten(lvQuellsystemDatenZeile, LOGGER_CASHFLOWS_DH);
            lvZaehlerQuellsysteme++;
          }
        }
      }
      catch (Exception e)
      {
        LOGGER_CASHFLOWS_DH.error("Fehler beim Verarbeiten einer Zeile:" + lvQuellsystemDatenZeile);
        e.printStackTrace();
      }
      try
      {
        lvQuellsystemDatenIn.close();
        lvQuellsystemDatenIs.close();
      }
      catch (Exception e)
      {
        LOGGER_CASHFLOWS_DH.error("Konnte Quellsystem-Datei " + ivImportVerzeichnis + "\\" + ivQuellsystemDateiWP + " nicht schliessen!");
      }
    }
    else
    {
      LOGGER_CASHFLOWS_DH.info("Keine Quellsystem-Datei " + ivImportVerzeichnis + "\\" + ivQuellsystemDateiWP + " gefunden...");
    }

    LOGGER_CASHFLOWS_DH.info("Anzahl QuellsystemDaten: " + lvZaehlerQuellsysteme);

    // Darlehen XML-Datei im TXS-Format
    ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivCashflowsOutputDatei, LOGGER_CASHFLOWS_DH);
    ivOutputDarlehenXML.openXML();
    ivOutputDarlehenXML.printXMLStart();
    ivOutputDarlehenXML.printTXSImportDatenStart();

    readCashflows(ivImportVerzeichnis + "\\" + ivCashflowsInputDatei);

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    LOGGER_CASHFLOWS_DH.info(printStatistik());
  }

  /**
   * Liest die Cashflows
   * @param pvDateiname Name der Cashflow-Datei
   */
  private void readCashflows(String pvDateiname)
  {
    ArrayList<Cashflow> lvListeCashflow = new ArrayList<Cashflow>();
    String lvAktuelleKontonummer = new String();

    ivZaehlerCashflows = 0;
    ivZaehlerWriteFinanzgeschaefte = 0;
    String lvZeile = null;
    ivCashflow = new Cashflow();

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileLoanIQ = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileLoanIQ);
    }
    catch (Exception e)
    {
      LOGGER_CASHFLOWS_DH.error("Konnte CashflowDH-Datei nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      // Erste Zeile ueberlesen
      lvIn.readLine();

      while ((lvZeile = lvIn.readLine()) != null)  // Lese Cashflow-Datei
      {
          ivCashflow = new Cashflow();
          if (!ivCashflow.parseCashflow(lvZeile)) // Parsen der Felder
          {
            LOGGER_CASHFLOWS_DH.error("Datenfehler!!!\n");
          }
          if (lvAktuelleKontonummer.equals(ivCashflow.getKontonummer()))
          {
            lvListeCashflow.add(ivCashflow);
          }
          else
          {
            if (!lvAktuelleKontonummer.isEmpty())
            {
              LOGGER_CASHFLOWS_DH.info("Anzahl Cashflows - Kontonummer " + lvAktuelleKontonummer +": " + lvListeCashflow.size());
              Collections.sort(lvListeCashflow);
              verarbeiteCashflows(lvListeCashflow);
            }
            lvListeCashflow = new ArrayList<Cashflow>();
            lvListeCashflow.add(ivCashflow);
            lvAktuelleKontonummer = ivCashflow.getKontonummer();
          }

          ivZaehlerCashflows++;
        }
    }
    catch (Exception e)
    {
      LOGGER_CASHFLOWS_DH.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }
    if (!lvAktuelleKontonummer.isEmpty())
    {
      Collections.sort(lvListeCashflow);
      verarbeiteCashflows(lvListeCashflow);
      LOGGER_CASHFLOWS_DH.info("Anzahl Cashflows - Kontonummer " + lvAktuelleKontonummer +": " + lvListeCashflow.size());
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_CASHFLOWS_DH.error("Konnte CashflowDH-Datei nicht schliessen!");
    }
  }

  /**
   * Verarbeite Cashflows
   * @param pvListeCashflow Liste der zu verarbeitenden Cashflows
   */
  private void verarbeiteCashflows(ArrayList<Cashflow> pvListeCashflow)
  {

    QuellsystemDaten lvQuellsystemDaten = null;

    if (pvListeCashflow.size() == 0) {
      LOGGER_CASHFLOWS_DH.info("Liste der Cashflows leer...");
      return;
    }

    if (ivQuellsystemDatenListe.contains(pvListeCashflow.get(0).getKontonummer()))
    {
      lvQuellsystemDaten = ivQuellsystemDatenListe.getQuellsystemDaten(pvListeCashflow.get(0).getKontonummer());
    }
    else
    {
      LOGGER_CASHFLOWS_DH.info("Keine QuellsystemDaten gefunden: " + pvListeCashflow.get(0).getKontonummer());
    }


    TXSFinanzgeschaeft lvFg = new TXSFinanzgeschaeft();
    if (lvQuellsystemDaten != null)
    {
       lvFg.setKey(lvQuellsystemDaten.getZielkontonummer());
    }
    else
    {
       lvFg.setKey(pvListeCashflow.get(0).getKontonummer());
    }

    lvFg.setOriginator("25010600");
    if (lvQuellsystemDaten != null)
    {
      if (!lvQuellsystemDaten.getQuellsystem().isEmpty())
      {
        lvFg.setQuelle(lvQuellsystemDaten.getQuellsystem());
      }
      else
      {
        LOGGER_CASHFLOWS_DH.info("Kein Quellsystem fuer Kontonummer " + pvListeCashflow.get(0).getKontonummer() + " vorhanden!");
        return;
      }
    }
    else
    {
      LOGGER_CASHFLOWS_DH.info("Konnte keine QuellsystemDaten fuer Kontonummer " + pvListeCashflow.get(0).getKontonummer() + " ermitteln!");
      return;
    }

    ivOutputDarlehenXML.printTransaktion(lvFg.printTXSTransaktionStart());

    TXSCashflowDaten lvCfdaten = null;
    for (int i = 0; i < pvListeCashflow.size();i++)
    {
      LOGGER_CASHFLOWS_DH.info("Cashflow: " + pvListeCashflow.get(i).getKontonummer() + " - " + pvListeCashflow.get(i).getCashflowType() + " - " + pvListeCashflow.get(i).getDatum() + " - " + pvListeCashflow.get(i).getBetrag());

      lvCfdaten = new TXSCashflowDaten();
      String lvBuchungsdatum = pvListeCashflow.get(i).getDatum();
      if (lvQuellsystemDaten != null)
      {
        if (lvQuellsystemDaten.getQuellsystem().startsWith("P"))
        {
          lvCfdaten.setCfkey(lvQuellsystemDaten.getZielkontonummer() + "_" + lvQuellsystemDaten.getUrsprungskontonummer() + "_" + lvBuchungsdatum);
        }
        else
        {
          lvCfdaten.setCfkey(lvQuellsystemDaten.getZielkontonummer() + "_" + lvBuchungsdatum);
        }
      }
      else
      {
        lvCfdaten.setCfkey(pvListeCashflow.get(i).getKontonummer() + "_" + lvBuchungsdatum);
      }

      lvCfdaten.setDatum(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(pvListeCashflow.get(i).getDatum())));

      if (pvListeCashflow.get(i).getCashflowType().equals("Z"))
      {
        lvCfdaten.setZbetrag(pvListeCashflow.get(i).getBetrag());
      }

      if (pvListeCashflow.get(i).getCashflowType().equals("T"))
      {
        lvCfdaten.setTbetrag(pvListeCashflow.get(i).getBetrag());
      }

      lvCfdaten.setWhrg(pvListeCashflow.get(i).getWaehrung());

      while (i < (pvListeCashflow.size() - 1) && pvListeCashflow.get(i).getDatum().equals(pvListeCashflow.get(i + 1).getDatum()))
      {
        i++;
        BigDecimal lvHelpTbetrag = StringKonverter.convertString2BigDecimal(lvCfdaten.getTbetrag());
        BigDecimal lvHelpZbetrag = StringKonverter.convertString2BigDecimal(lvCfdaten.getZbetrag());
        BigDecimal lvHelpWert = StringKonverter.convertString2BigDecimal(pvListeCashflow.get(i).getBetrag());
        if (pvListeCashflow.get(i).getCashflowType().equals("T"))
        {
          lvHelpTbetrag = lvHelpTbetrag.add(lvHelpWert);
          lvCfdaten.setTbetrag(lvHelpTbetrag.toString());
        }

        if (pvListeCashflow.get(i).getCashflowType().equals("Z"))
        {
          lvHelpZbetrag = lvHelpZbetrag.add(lvHelpWert);
          lvCfdaten.setZbetrag(lvHelpZbetrag.toString());
        }
      }

      //LOGGER_CASHFLOWS_DH.info(lvCfdaten.getCfkey() + " - " + lvCfdaten.getTbetrag() + " - " + lvCfdaten.getZbetrag());

      if (StringKonverter.convertString2Double(lvCfdaten.getTbetrag()) > 0.0 || StringKonverter.convertString2Double(lvCfdaten.getZbetrag()) > 0.0)
      {
        ivOutputDarlehenXML.printTransaktion(lvCfdaten.printTXSCashflowDaten());
      }
    }

    ivOutputDarlehenXML.printTransaktion(lvFg.printTXSTransaktionEnde());

    ivZaehlerWriteFinanzgeschaefte++;
  }

    /**
     * Liefert eine Statistik als String
     * @return
     */
  private String printStatistik()
  {
    StringBuilder lvOut = new StringBuilder();
    lvOut.append(ivZaehlerCashflows + " Cashflows gelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerWriteFinanzgeschaefte + " Finanzgeschaefte mit Cashflows geschrieben..." + StringKonverter.lineSeparator);

    return lvOut.toString();
  }

}
