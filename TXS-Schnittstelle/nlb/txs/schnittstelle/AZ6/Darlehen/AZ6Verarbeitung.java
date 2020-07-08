/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.AZ6.Darlehen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.DarlehenBlock;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Sicherheiten.Sicherheiten2Pfandbrief;
import nlb.txs.schnittstelle.Sicherheiten.SicherheitenDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

public class AZ6Verarbeitung
{
  // Logger fuer LoanIQ
  private static Logger LOGGER_AZ6 = Logger.getLogger("TXSAZ6Logger");

  /**
   * Institutsnummer
   */
  private String ivInstitutsnummer;

  /**
   * Import-Verzeichnis der LoanIQ-Datei
   */
  private String ivImportVerzeichnis;

  /**
   * Export-Verzeichnis der TXS-Importdatei
   */
  private String ivExportVerzeichnis;

  /**
   * Dateiname der AZ6-Datei
   */
  private String ivAZ6InputDatei;

  /**
   * Dateiname der TXS-Importdatei
   */
  private String ivAZ6OutputDatei;

  /**
   * Import-Verzeichnis der Sicherheiten-Datei aus SAP CMS
   */
  private String ivImportVerzeichnisSAPCMS;

  /**
   * Dateiname der Sicherheiten-Datei aus SAP CMS
   */
  private String ivSapcmsDatei;

  /**
   * Import-Verzeichnis der Sicherheiten-Datei aus VVS
   */
  private String ivImportVerzeichnisVVS;

  /**
   * Dateiname der Sicherheiten-Datei aus VVS
   */
  private String ivVVSDatei;

  /**
   * Sicherheiten-Daten
   */
  private SicherheitenDaten ivSicherheitenDaten;

  /**
   * Quellsystem-Datei fuer die FRISCO-Verarbeitung
   */
  private String ivCashflowQuellsystemDatei;

  /**
   * Import-Verzeichnis der Rueckmeldung
   */
  //private String ivImportVerzeichnisRueckmeldung;

  /**
   * KundeRequest-Datei
   */
  private String ivKundeRequestDatei;

  /**
   * FileOutputStream fuer CashflowQuellsystem-Datei
   */
  private FileOutputStream ivFosCashflowQuellsystem;

  /**
   * Ausgabedatei der Kundennummern
   */
  private KundennummernOutput ivKundennummernOutput;

  /**
   * Ein DarlehenBlock
   */
  private DarlehenBlock ivDarlehenBlock;

  /**
   * Vorlaufsatz der LoanIQ-Datei
   */
  private Vorlaufsatz ivVorlaufsatz;

  /**
   * Ausgabedatei der TXS-Importdaten
   */
  private OutputDarlehenXML ivOutputDarlehenXML;

  // Zaehlvariablen für die unterschiedlichen Ausplatzierungsmerkmale
  private int ivAnzahlK = 0;
  private int ivAnzahlH = 0;
  private int ivAnzahlF = 0;
  private int ivAnzahlS = 0;
  private int ivAnzahlO = 0;

  /**
   * Zaehler fuer die Anzahl der Vorlaufsaetze
   */
  private int ivZaehlerVorlaufsatz;

  /**
   * Zaehler fuer die Anzahl der Brutto-Finanzgeschaefte/-Zeilen
   */
  private int ivZaehlerFinanzgeschaefteBrutto;

  /**
   * Zaehler fuer die Anzahl der Netto-Finanzgeschaefte/-Zeilen
   */
  private int ivZaehlerFinanzgeschaefteNetto;

  /**
   * Zaehler fuer die Anzahl der Fremd-Finanzgeschaefte/-Zeilen
   */
  private int ivZaehlerFinanzgeschaefteFremd;

  public AZ6Verarbeitung(IniReader pvReader)
  {
    if (pvReader != null)
    {
      ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
      if (ivInstitutsnummer.equals("Fehler"))
      {
        LOGGER_AZ6.error("Keine Institutsnummer in der ini-Datei...");
        System.exit(1);
      }
      else
      {
         LOGGER_AZ6.info("Institutsnummer: " + ivInstitutsnummer);
      }

      ivImportVerzeichnis = pvReader.getPropertyString("AZ6", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_AZ6.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6.info("ImportVerzeichnis: " + ivImportVerzeichnis);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("AZ6", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_AZ6.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6.info("ExportVerzeichnis: " + ivExportVerzeichnis);
      }

      ivAZ6InputDatei =  pvReader.getPropertyString("AZ6", "AZ6Input-Datei", "Fehler");
      if (ivAZ6InputDatei.equals("Fehler"))
      {
        LOGGER_AZ6.error("Kein AZ6Input-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6.info("AZ6Input-Datei: " + ivAZ6InputDatei);
      }

      ivAZ6OutputDatei =  pvReader.getPropertyString("AZ6", "AZ6Output-Datei", "Fehler");
      if (ivAZ6OutputDatei.equals("Fehler"))
      {
        LOGGER_AZ6.error("Kein AZ6Output-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6.info("AZ6Output-Datei: " + ivAZ6OutputDatei);
      }

      ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("SAPCMS", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
      {
        LOGGER_AZ6.error("Kein SAPCMS Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6.info("SAPCMS ImportVerzeichnis: " + ivImportVerzeichnisSAPCMS);
      }

      ivSapcmsDatei = pvReader.getPropertyString("SAPCMS", "SAPCMS-Datei", "Fehler");
      if (ivSapcmsDatei.equals("Fehler"))
      {
        LOGGER_AZ6.error("Kein SAPCMS-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6.info("SAPCMS-Datei: " + ivSapcmsDatei);
      }

      ivImportVerzeichnisVVS = pvReader.getPropertyString("VVS", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnisVVS.equals("Fehler"))
      {
        LOGGER_AZ6.error("Kein VVS Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6.info("ImportVerzeichnis: " + ivImportVerzeichnisVVS);
      }

      ivVVSDatei = pvReader.getPropertyString("VVS", "VVS-Datei", "Fehler");
      if (ivVVSDatei.equals("Fehler"))
      {
        LOGGER_AZ6.error("Kein VVS-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6.info("VVS-Datei: " + ivVVSDatei);
      }

      ivCashflowQuellsystemDatei = pvReader.getPropertyString("AZ6", "Quellsystem-Datei", "Fehler");
      if (ivCashflowQuellsystemDatei.equals("Fehler"))
      {
        LOGGER_AZ6.error("Kein Cashflow-Quellsystem-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6.info("Cashflow Quellsystem-Datei: " + ivCashflowQuellsystemDatei);
      }

      ivKundeRequestDatei = pvReader.getPropertyString("AZ6", "KundeRequestDatei", "Fehler");
      if (ivKundeRequestDatei.equals("Fehler"))
      {
        LOGGER_AZ6.error("Kein KundeRequest-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6.info("KundeRequestDatei: " + ivKundeRequestDatei);
      }

      /*
      ivImportVerzeichnisRueckmeldung = pvReader.getPropertyString("Rueckmeldung", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnisRueckmeldung.equals("Fehler"))
      {
        LOGGER_LOANIQ.error("Kein Import-Verzeichnis fuer die Rueckmeldung in der ini-Datei...");
        System.exit(1);
      }

      ivAusplatzierungsmerkmalDatei = pvReader.getPropertyString("Rueckmeldung", "AusplatzierungsmerkmalLoanIQ-Datei", "Fehler");
      if (ivAusplatzierungsmerkmalDatei.equals("Fehler"))
      {
        LOGGER_LOANIQ.error("Kein Ausplatzierungsmerkmal-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      ivVerbuergtKonsortialDatei = pvReader.getPropertyString("Rueckmeldung", "VerbuergtKonsortialDatei", "Fehler");
      if (ivVerbuergtKonsortialDatei.equals("Fehler"))
      {
        LOGGER_LOANIQ.error("Kein VerbuergtKonsortial-Dateiname in der ini-Datei...");
        System.exit(1);
      } */

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
    ivZaehlerFinanzgeschaefteBrutto = 0;
    ivZaehlerFinanzgeschaefteNetto = 0;
    ivZaehlerFinanzgeschaefteFremd = 0;

  /*
  // VerbuergtKonsortial-Datei oeffnen (zum Schreiben)
  File lvFileVerbuergtKonsortial = new File(ivImportVerzeichnisRueckmeldung + "\\" + ivVerbuergtKonsortialDatei);
        try
  {
    ivFosVerbuergtKonsortial = new FileOutputStream(lvFileVerbuergtKonsortial);
  }
        catch (Exception e)
  {
    LOGGER_LOANIQ.error("Konnte VerbuergtKonsortial-Datei nicht oeffnen!");
  } */

  // Cashflow-Quellsystem oeffnen (zum Schreiben)
  File lvFileCashflowQuellsystem = new File(ivExportVerzeichnis + "\\" + ivCashflowQuellsystemDatei);
        try
  {
    ivFosCashflowQuellsystem = new FileOutputStream(lvFileCashflowQuellsystem);
  }
        catch (Exception e)
  {
    LOGGER_AZ6.error("Konnte CashflowQuellsystem-Datei nicht oeffnen!");
  }
  // Cashflow-Quellsystem oeffnen (zum Schreiben)

    /*
  // Ausplatzierungsmerkmal-Datei oeffnen (zum Schreiben)
  File lvFileAusplatzierungsmerkmal = new File(ivImportVerzeichnisRueckmeldung + "\\" + ivAusplatzierungsmerkmalDatei);
        try
  {
    ivFosAusplatzierungsmerkmal = new FileOutputStream(lvFileAusplatzierungsmerkmal);
  }
        catch (Exception e)
  {
    LOGGER_AZ6.error("Konnte Ausplatzierungsmerkmal-Datei nicht oeffnen!");
  } */

  // Zum Umschalten: SAP CMS oder VVS - CT 27.02.2020
  // SAP CMS-Daten einlesen
  //ivSicherheitenDaten = new SicherheitenDaten(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, SicherheitenDaten.SAPCMS, LOGGER_AZ6);

  // VVS-Daten einlesen
  ivSicherheitenDaten = new SicherheitenDaten(ivImportVerzeichnisVVS + "\\" + ivVVSDatei, SicherheitenDaten.VVS, LOGGER_AZ6);

  // Darlehen XML-Datei im TXS-Format
  ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivAZ6OutputDatei, LOGGER_AZ6);
  ivOutputDarlehenXML.openXML();
  ivOutputDarlehenXML.printXMLStart();
  ivOutputDarlehenXML.printTXSImportDatenStart();

  // AZ6-Daten einlesen und verarbeiten
    readAZ6(ivImportVerzeichnis + "\\" + ivAZ6InputDatei);

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    // Statistik ausgeben
    LOGGER_AZ6.info(getStatistik());

    // Cashflow-Quellsystem schliessen
    try
    {
      ivFosCashflowQuellsystem.close();
    }
    catch (Exception e)
    {
      LOGGER_AZ6.error("Fehler beim Schliessen der CashflowQuellsystem-Datei");
    }
    // Cashflow-Quellsystem schliessen

    /*
  // VerbuergtKonsortial-Datei schliessen
        try
  {
    ivFosVerbuergtKonsortial.close();
  }
        catch (Exception e)
  {
    LOGGER_LOANIQ.error("Fehler beim Schliessen der VerbuergtKonsortial-Datei");
  } */

    /*
  // Ausplatzierungsmerkmal-Datei schliessen
        try
  {
    ivFosAusplatzierungsmerkmal.close();
  }
        catch (Exception e)
  {
    LOGGER_AZ6.error("Fehler beim Schliessen der Ausplatzierungsmerkmal-Datei");
  }*/


  // KundeRequest-Datei schliessen
    try
    {
      ivKundennummernOutput.close();
    }
    catch (Exception e)
    {
      LOGGER_AZ6.error("Fehler beim Schliessen der KundeRequest-Datei");
    }
}

  /**
   * Einlesen und Verarbeiten der AZ6-Daten
   * @param pvDateiname
   */
  private void readAZ6(String pvDateiname)
  {
    String lvZeile = null;
    ivVorlaufsatz = new Vorlaufsatz(LOGGER_AZ6);
    ivDarlehenBlock = new DarlehenBlock(ivVorlaufsatz, new Sicherheiten2Pfandbrief(ivSicherheitenDaten, LOGGER_AZ6), LOGGER_AZ6);

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileLoanIQ = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileLoanIQ);
    }
    catch (Exception e)
    {
      LOGGER_AZ6.error("Konnte LoanIQ-Datei nicht oeffnen!");
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
          ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_AZ6);
          ivKundennummernOutput.open();
          ivKundennummernOutput.printVorlaufsatz(ivInstitutsnummer, "Pfandbrief");
        }
        else
        {
          //System.out.println("lvZeile: " + lvZeile);
          if (!ivDarlehenBlock.parseDarlehen(lvZeile, LOGGER_AZ6)) // Parsen der Felder
          {
            // Unterschiedliche Kontonummer -> Darlehen verarbeiten
            if (isAusplatzierungsmerkmalRelevant())
            {
              ivDarlehenBlock.verarbeiteDarlehenAZ6(ivFosCashflowQuellsystem, ivOutputDarlehenXML);
            }
            // Neuen LoanIQ-Block anlegen
            ivDarlehenBlock = new DarlehenBlock(ivVorlaufsatz, ivSicherheitenDaten.getSicherheiten2Pfandbrief(), LOGGER_AZ6);
            // Zeile mit neuer Kontonummer muss noch verarbeitet werden
            if (ivDarlehenBlock.parseDarlehen(lvZeile, LOGGER_AZ6)) // Parsen der Felder
            {
              if (lvZeile.contains(";B;")) ivZaehlerFinanzgeschaefteBrutto++;
              if (lvZeile.contains(";N;")) ivZaehlerFinanzgeschaefteNetto++;
              if (lvZeile.contains(";F;")) ivZaehlerFinanzgeschaefteFremd++;
            }
          }
          else
          {
            if (lvZeile.contains(";B;")) ivZaehlerFinanzgeschaefteBrutto++;
            if (lvZeile.contains(";N;")) ivZaehlerFinanzgeschaefteNetto++;
            if (lvZeile.contains(";F;")) ivZaehlerFinanzgeschaefteFremd++;
          }
        }
      }
      // Letzten Kredit auch verarbeiten - CT 13.09.2017
      if (isAusplatzierungsmerkmalRelevant())
      {
        /*
        ivDarlehenBlock.verarbeiteDarlehen(ivFosVerbuergtKonsortial, ivFosCashflowQuellsystem, ivOutputDarlehenXML);
        */
      }
    }
    catch (Exception e)
    {
      LOGGER_AZ6.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_AZ6.error("Konnte AZ6-Datei nicht schliessen!");
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
    lvOut.append(ivZaehlerFinanzgeschaefteBrutto + " Brutto-Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerFinanzgeschaefteNetto + " Netto-Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerFinanzgeschaefteFremd + " Fremd-Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlK + " mit Ausplatzierungsmerkmal Kx" + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlH + " mit Ausplatzierungsmerkmal Hx" + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlF + " mit Ausplatzierungsmerkmal Fx" + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlS + " mit Ausplatzierungsmerkmal Sx" + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlO + " mit Ausplatzierungsmerkmal Ox" + StringKonverter.lineSeparator);

    return lvOut.toString();
  }

  /**
   * Schreibt die nicht relevanten Ausplatzierungsmerkmale in eine Datei
   */
  private boolean isAusplatzierungsmerkmalRelevant()
  {
    boolean lvRelevant = false;
    if (ivDarlehenBlock.existsDarlehenNetto())
    {
      if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("A") ||
          ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("C") ||
          ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("D") ||
          ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("E") ||
          ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("P") ||
          ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("R") ||
          ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("W") ||
          ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("N"))
      {
        /*
        try
        {
          ivFosAusplatzierungsmerkmal.write((ivDarlehenBlock.getKontonummer() + ";" + ivDarlehenBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal() + StringKonverter.lineSeparator).getBytes());
        }
        catch (Exception e)
        {
          LOGGER_AZ6.error("Fehler beim Schreiben in die Ausplatzierungsmerkmal-Datei");
        }
         */
        LOGGER_AZ6.info("Kontonummer " + ivDarlehenBlock.getKontonummer() + " - Nicht relevantes Ausplatzierungsmerkmal " + ivDarlehenBlock
            .getDarlehenNetto().getAusplatzierungsmerkmal());
        lvRelevant = false;
      }
      else
      {
        if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("K") ||
            ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("H") ||
            ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("F") ||
            ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("S") ||
            ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("O"))
        {
          /*
          try
          {
            ivFosAusplatzierungsmerkmal.write((ivDarlehenBlock.getKontonummer() + ";" + ivDarlehenBlock.getDarlehenLoanIQNetto().getAusplatzierungsmerkmal() + StringKonverter.lineSeparator).getBytes());
          }
          catch (Exception e)
          {
            LOGGER_AZ6.error("Fehler beim Schreiben in die Ausplatzierungsmerkmal-Datei");
          }
           */
          lvRelevant = true;
          // Buergennummer in die KundeRequest-Datei schreiben
          if (!ivDarlehenBlock.getDarlehenNetto().getBuergennummer().isEmpty())
          {
            ivKundennummernOutput.printKundennummer(MappingKunde
                .extendKundennummer(ivDarlehenBlock.getDarlehenNetto().getBuergennummer(), LOGGER_AZ6));
          }
          // Kundennummer in die KundeRequest-Datei schreiben
          if (!ivDarlehenBlock.getDarlehenNetto().getKundennummer().isEmpty())
          {
            ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(
                ivDarlehenBlock.getDarlehenNetto().getKundennummer(), LOGGER_AZ6));
          }

          if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("K")) ivAnzahlK++;
          if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("H")) ivAnzahlH++;
          if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("F")) ivAnzahlF++;
          if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("S")) ivAnzahlS++;
          if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("O")) ivAnzahlO++;
        }
      }
    }
    return lvRelevant;
  }

}
