/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.AZ6.RefiRegister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.Darlehen;
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

public class AZ62RefiRegisterVerarbeitung {

  // Logger fuer LoanIQ
  private static Logger LOGGER_AZ62RefiRegister = Logger.getLogger("TXSAZ62RefiRegisterLogger");

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

  /**
   * Ein DarlehenBlock
   */
  private DarlehenBlock ivDarlehenBlock;

  /**
   * Ausgabedatei der Kundennummern
   */
  private KundennummernOutput ivKundennummernOutput;

  /**
   * KundeRequest-Datei
   */
  private String ivKundeRequestDatei;

  /**
   * Vorlaufsatz der LoanIQ-Datei
   */
  private Vorlaufsatz ivVorlaufsatz;

  /**
   * Ausgabedatei der TXS-Importdaten
   */
  private OutputDarlehenXML ivOutputDarlehenXML;

  // Zaehlvariablen
  //private int ivAnzahlAF = 0;
  //private int ivAnzahlB = 0;
  private int ivAnzahlK = 0;
  private int ivAnzahlH = 0;
  private int ivAnzahlF = 0;
  private int ivAnzahlS = 0;
  private int ivAnzahlO = 0;
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
  private String ivAZ62RefiRegisterOutputDatei;

  /**
   * Import-Verzeichnis der Sicherheiten-Datei
   */
  private String ivImportVerzeichnisVVS;

  /**
   * Dateiname der Sicherheiten-Datei
   */
  private String ivVVSDatei;


  /**
   * Import-Verzeichnis der Sicherheiten-Datei
   */
  //private String ivImportVerzeichnisSAPCMS;

  /**
   * Dateiname der Sicherheiten-Datei
   */
  //private String ivSapcmsDatei;

  /**
   * Dateiname der Liste der Kontonummern
   */
  private String ivFilterDatei;

  /**
   * Sicherheiten-Daten
   */
  private SicherheitenDaten ivSicherheitenDaten;

  /**
   * Liste der zu verarbeitenden Kontonummern
   */
  private HashSet<String> ivListeKontonummern;

  /**
   * RefiRegister AZ6 Passiv
   */
  private RefiRegisterAZ6Passiv ivRefiRegisterAZ6Passiv;

  /**
   * Konstruktor fuer AZ62RefiRegister Verarbeitung
   * @param pvReader
   */
  public AZ62RefiRegisterVerarbeitung(IniReader pvReader)
  {
    if (pvReader != null)
    {
      ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
      if (ivInstitutsnummer.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Keine Institutsnummer in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ62RefiRegister.info("Institutsnummer: " + ivInstitutsnummer);
      }

      ivImportVerzeichnis = pvReader.getPropertyString("AZ6", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ62RefiRegister.info("Import-Verzeichnis: " + ivImportVerzeichnis);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("AZ62RefiRegister", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ62RefiRegister.info("Export-Verzeichnis: " + ivExportVerzeichnis);
      }

      ivAZ6InputDatei =  pvReader.getPropertyString("AZ6", "AZ6Input-Datei", "Fehler");
      if (ivAZ6InputDatei.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein AZ6Input-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ62RefiRegister.info("AZ6Input-Datei: " + ivAZ6InputDatei);
      }

      ivAZ62RefiRegisterOutputDatei =  pvReader.getPropertyString("AZ62RefiRegister", "AZ62RefiRegisterOutput-Datei", "Fehler");
      if (ivAZ62RefiRegisterOutputDatei.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein AZ62RefiRegisterOutput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ62RefiRegister.info("AZ62RefiRegisterOutput-Datei: " + ivAZ62RefiRegisterOutputDatei);
      }

      ivImportVerzeichnisVVS = pvReader.getPropertyString("VVS", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnisVVS.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Sicherheiten Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ62RefiRegister.info("VVS-ImportVerzeichnis: " + ivImportVerzeichnisVVS);
      }

      ivVVSDatei = pvReader.getPropertyString("VVS", "VVS-Datei", "Fehler");
      if (ivVVSDatei.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Sicherheiten-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ62RefiRegister.info("VVS-Datei: " + ivVVSDatei);

      }

      ivFilterDatei = pvReader.getPropertyString("AZ62RefiRegister", "Filter-Datei", "Fehler");
      if (ivFilterDatei.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Filter-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ62RefiRegister.info("Filter-Datei: " + ivFilterDatei);
      }

      /*
      ivFilterDateiBB = pvReader.getPropertyString("AZ62RefiRegister", "Filter-Datei-BB", "Fehler");
      if (ivFilterDateiBB.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Filter-Dateiname fuer BB in der ini-Datei...");
        System.exit(1);
      }
      */
      ivKundeRequestDatei = pvReader.getPropertyString("AZ62RefiRegister", "KundeRequestDatei", "Fehler");
      if (ivKundeRequestDatei.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein KundeRequest-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ62RefiRegister.info("KundeRequestDatei: " + ivKundeRequestDatei);
      }

      ivListeKontonummern = new HashSet<String>();
      // Dummy-Kontonummer damit die Liste nicht leer wird
      ivListeKontonummern.add("99999999999");

      /*
      ivListeKontonummernBB = new HashSet<String>();
      // Dummy-Kontonummer damit die Liste nicht leer wird
      ivListeKontonummernBB.add("99999999999");

      ivListeRefiDeepSeaZusatz = new HashMap<String, RefiDeepSeaZusatz>();

      // RefiDeepSeaZusatz einlesen - CT 26.03.2019
      ivListeRefiDeepSeaZusatz = readRefiDeepSeaZusatz(ivExportVerzeichnis + "\\BB_20190401.csv");
      */
      // Liste der Kontonummern einlesen
      readListeKontonummern(ivListeKontonummern, ivExportVerzeichnis + "\\" + ivFilterDatei);

      /*
      // Liste der Kontonummern fuer BB einlesen
      readListeKontonummern(ivListeKontonummernBB, ivExportVerzeichnis + "\\" + ivFilterDateiBB);
      */
      // Verarbeitung starten
      startVerarbeitung();
    }
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

    // Zum Umschalten: SAP CMS oder VVS - CT 27.02.2020
    // SAP CMS-Daten einlesen
    //ivSicherheitenDaten = new SicherheitenDaten(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, SicherheitenDaten.SAPCMS, LOGGER_AZ6);

    // VVS-Daten einlesen
    ivSicherheitenDaten = new SicherheitenDaten(ivImportVerzeichnisVVS + "\\" + ivVVSDatei, SicherheitenDaten.VVS, LOGGER_AZ62RefiRegister);

    // Darlehen XML-Datei im TXS-Format
    ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivAZ62RefiRegisterOutputDatei, LOGGER_AZ62RefiRegister);
    ivOutputDarlehenXML.openXML();
    ivOutputDarlehenXML.printXMLStart();
    ivOutputDarlehenXML.printTXSImportDatenStart();

    // RefiRegister-Passiv
    ivRefiRegisterAZ6Passiv = new RefiRegisterAZ6Passiv(ivSicherheitenDaten, ivOutputDarlehenXML, LOGGER_AZ62RefiRegister);

    // AZ6-Daten einlesen und verarbeiten
    readAZ6(ivImportVerzeichnis + "\\" + ivAZ6InputDatei);

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    // Statistik ausgeben
    LOGGER_AZ62RefiRegister.info(getStatistik());

    // KundeRequest-Datei schliessen
    try
    {
      ivKundennummernOutput.close();
    }
    catch (Exception e)
    {
      LOGGER_AZ62RefiRegister.error("Fehler beim Schliessen der KundeRequest-Datei");
    }

    // MappingRueckmeldung schreiben
    //ivMappingRueckmeldungListe.schreibeObjekteListe();
  }

  /**
   * Liest die Liste der Kontonummern ein
   * @param pvListeKontonummern Liste der Kontonummern
   * @param pvDateiname Dateiname der Kontonummernliste
   */
  private void readListeKontonummern(HashSet<String> pvListeKontonummern, String pvDateiname)
  {
    LOGGER_AZ62RefiRegister.info("Start - readListeKontonummern");
    LOGGER_AZ62RefiRegister.info("Datei: " + pvDateiname);
    String lvZeile = null;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File lvFileListeKontonummern = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(lvFileListeKontonummern);
    }
    catch (Exception e)
    {
      LOGGER_AZ62RefiRegister.error("Konnte die Kontonummernliste-Datei '" + pvDateiname +"' nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      while ((lvZeile = lvIn.readLine()) != null)  // Lesen der ListeKontonummern-Datei
      {
        LOGGER_AZ62RefiRegister.info("Verarbeite Kontonummer: " + lvZeile);
        pvListeKontonummern.add(lvZeile);
      }
    }
    catch (Exception e)
    {
      LOGGER_AZ62RefiRegister.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    LOGGER_AZ62RefiRegister.info("Anzahl von Kontonummern in der Liste: " + (pvListeKontonummern.size() - 1));

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_AZ62RefiRegister.error("Konnte die Kontonummernliste-Datei '" + pvDateiname + "' nicht schliessen!");
    }
  }

  /**
   * Einlesen und Verarbeiten der AZ6-Daten
   * @param pvDateiname
   */
  private void readAZ6(String pvDateiname)
  {
    String lvZeile = null;
    ivVorlaufsatz = new Vorlaufsatz(LOGGER_AZ62RefiRegister);
    ivDarlehenBlock = new DarlehenBlock(ivVorlaufsatz, new Sicherheiten2Pfandbrief(ivSicherheitenDaten, LOGGER_AZ62RefiRegister), LOGGER_AZ62RefiRegister);

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileLoanIQ = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileLoanIQ);
    }
    catch (Exception e)
    {
      LOGGER_AZ62RefiRegister.error("Konnte AZ6-Datei nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
    boolean lvStart = true;

    try
    {
      while ((lvZeile = lvIn.readLine()) != null)  // Lesen AZ6-Datei
      {
        if (lvStart)
        {
          ivVorlaufsatz.parseVorlaufsatz(lvZeile);
          ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()));
          ivZaehlerVorlaufsatz++;
          lvStart = false;

          // KundeRequest-Datei oeffnen
          ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_AZ62RefiRegister);
          ivKundennummernOutput.open();
          ivKundennummernOutput.printVorlaufsatz(ivInstitutsnummer, "RefiReg");
        }
        else
        {
          //System.out.println("lvZeile: " + lvZeile);
          if (!ivDarlehenBlock.parseDarlehen(lvZeile, LOGGER_AZ62RefiRegister)) // Parsen der Felder
          {
            // Unterschiedliche Kontonummer -> Darlehen verarbeiten
            if (ivListeKontonummern.contains(ivDarlehenBlock.getKontonummer()))
            {
              LOGGER_AZ62RefiRegister.info("Treffer: " + ivDarlehenBlock.getKontonummer());
              verarbeiteDarlehenAZ6Passiv();
            }
            else
            {
              LOGGER_AZ62RefiRegister.info("Kein Treffer: " + ivDarlehenBlock.getKontonummer());
            }
            // Neuen Darlehen-Block anlegen
            ivDarlehenBlock = new DarlehenBlock(ivVorlaufsatz, ivSicherheitenDaten.getSicherheiten2RefiRegister(), LOGGER_AZ62RefiRegister);
            // Zeile mit neuer Kontonummer muss noch verarbeitet werden
            if (ivDarlehenBlock.parseDarlehen(lvZeile, LOGGER_AZ62RefiRegister)) // Parsen der Felder
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
      //if (isAusplatzierungsmerkmalRelevant())
      //{
        /*
        ivDarlehenBlock.verarbeiteDarlehen(ivFosVerbuergtKonsortial, ivFosCashflowQuellsystem, ivOutputDarlehenXML);
        */
      //}
    }
    catch (Exception e)
    {
      LOGGER_AZ62RefiRegister.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_AZ62RefiRegister.error("Konnte AZ6-Datei nicht schliessen!");
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
   * Verarbeite den DarlehenBlock in "passiver" Weise
   */
  private void verarbeiteDarlehenAZ6Passiv()
  {
    LOGGER_AZ62RefiRegister.info("Verarbeite Kontonummer: " + ivDarlehenBlock.getKontonummer());

    if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("K")) ivAnzahlK++;
    if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("H")) ivAnzahlH++;
    if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("F")) ivAnzahlF++;
    if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("S")) ivAnzahlS++;
    if (ivDarlehenBlock.getDarlehenNetto().getAusplatzierungsmerkmal().startsWith("O")) ivAnzahlO++;

    if (ivDarlehenBlock.getListeDarlehenFremd().size() > 0)
    {
      for (Darlehen lvDarlehen: ivDarlehenBlock.getListeDarlehenFremd())
      {
        // Konsortialnummer in die KundeRequest-Datei schreiben
        //if (!lvDarlehen.getKonsortialfuehrer().isEmpty())
        //{
        //  ivKundennummernOutput.printKundennummer(MappingKunde
        //      .extendKundennummer(lvDarlehen.getKonsortialfuehrer(), LOGGER_AZ62RefiRegister));
        //}
        // Kundennummer in die KundeRequest-Datei schreiben
        if (!lvDarlehen.getKundennummer().isEmpty())
        {
          ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_AZ62RefiRegister));
        }
        // Buergennummer in die KundeRequest-Datei schreiben
        if (!ivDarlehenBlock.getDarlehenNetto().getKundennummer().isEmpty())
        {
          ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(ivDarlehenBlock.getDarlehenNetto().getKundennummer(), LOGGER_AZ62RefiRegister));
        }

        // TXS-Transaktionen mit Darlehensinformationen fuellen
        ivRefiRegisterAZ6Passiv.importDarlehen2Transaktion(lvDarlehen, ivVorlaufsatz, ivDarlehenBlock.getDarlehenNetto().getKundennummer()); //, ivListeRefiDeepSeaZusatz);
      }

    }
    else
    {
      LOGGER_AZ62RefiRegister.info("Keine Fremdanteile...");
      return;
    }
  }


}