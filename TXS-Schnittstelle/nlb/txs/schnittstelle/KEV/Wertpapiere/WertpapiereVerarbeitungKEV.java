/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.KEV.Wertpapiere;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.MAVIS.RueckmeldeDatenMAVIS;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSGlobalurkunde;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Transaktion.TXSWertpapierposition;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Wertpapier.Bestand.Bestandsdaten;
import nlb.txs.schnittstelle.Wertpapier.Bestand.DepotSummierung;
import nlb.txs.schnittstelle.Wertpapier.Gattungsdaten.Gattung;
import nlb.txs.schnittstelle.Wertpapier.Gattungsdaten.Gattungsdaten;
import org.apache.log4j.Logger;

public class WertpapiereVerarbeitungKEV
{
  // Logger fuer WertpapiereKEV
  private static Logger LOGGER_WERTPAPIERE_KEV = Logger.getLogger("WertpapiereKEVLogger");

  /**
   * Institutsnummer
   */
  private String ivInstitutsnummer;

  /**
   * Liste mit ISIN, die eingespielt werden sollen
   */
  private HashSet<String> ivListeKeys;

  /**
   * Buchungsdatum
   */
  private String ivBuchungsdatum;

  /**
   * Import-Verzeichnis der Wertpapiere-Datei
   */
  private String ivImportVerzeichnis;

  /**
   * Export-Verzeichnis der TXS-Importdatei
   */
  private String ivExportVerzeichnis;

  /**
   * Dateiname der WertpapiereInput-Datei
   */
  private String ivWertpapiereInputDatei;

  /**
   * Dateiname der TXS-Importdatei
   */
  private String ivWertpapiereOutputDatei;

  /**
   * Name der KundeRequest-Datei
   */
  private String ivKundeRequestDatei;

  /**
   * Dateiname der Liste der ISIN
   */
  private String ivFilterDatei;

  /**
   * Ausgabedatei der Kundennummern
   */
  private KundennummernOutput ivKundennummernOutput;

  /**
   * Zaehler fuer die Anzahl der Zeilen in der Bestandsdaten-Datei
   */
  private int ivZaehlerBestandsdaten;

  /**
   * Zaehler mit der Anzahl verarbeiteter Bestandsdaten
   */
  private int ivZaehlerBestandsdatenVerarbeitet;

  // Zaehlvariablen
  private int ivAnzahlA0 = 0;
  private int ivAnzahlA1 = 0;
  private int ivAnzahlA2 = 0;

  /**
   * TXS-Importdatei
   */
  private OutputDarlehenXML ivOutputDarlehenXML;

  // Transaktionen
  private TXSFinanzgeschaeft ivFinanzgeschaeft;
  private TXSSliceInDaten ivSliceInDaten;
  private TXSWertpapierposition ivWertpapierposition;
  private TXSGlobalurkunde ivGlobalurkunde;
  private TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten;
  private TXSKonditionenDaten ivKondDaten;
  private TXSKreditKunde ivKredkunde;

  /**
   * Bestandsdaten
   */
  private Bestandsdaten ivBestandsdaten;

  /**
   * Gattung zu Bestandsdaten
   */
  private Gattung ivGattung;

  /**
   * Gattungsdaten
   */
  private Gattungsdaten ivGattungsdaten;

  /**
   * Liste der RueckmeldeDaten
   */
  private HashMap<String, RueckmeldeDatenMAVIS> ivListeRueckmeldeDaten;

  /**
   * Summierung bei mehreren Depots
   */
  private DepotSummierung ivDepotSummierung;

  /**
   * Konstruktor
   * @param pvReader
   */
  public WertpapiereVerarbeitungKEV(IniReader pvReader)
  {
    ivZaehlerBestandsdaten = 0;
    ivZaehlerBestandsdatenVerarbeitet = 0;

    ivFinanzgeschaeft = new TXSFinanzgeschaeft();
    ivSliceInDaten = new TXSSliceInDaten();
    ivWertpapierposition = new TXSWertpapierposition();
    ivGlobalurkunde = new TXSGlobalurkunde();
    ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
    ivKondDaten = new TXSKonditionenDaten();
    ivKredkunde = new TXSKreditKunde();

    if (pvReader != null)
    {

      ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
      if (ivInstitutsnummer.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_KEV.error("Keine Institutsnummer in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_KEV.info("Institutsnummer: " + ivInstitutsnummer);
      }

      ivImportVerzeichnis = pvReader.getPropertyString("WertpapiereKEV", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_KEV.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_KEV.info("ImportVerzeichnis: " + ivImportVerzeichnis);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("WertpapiereKEV", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_KEV.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_KEV.info("ExportVerzeichnis: " + ivExportVerzeichnis);
      }

      ivWertpapiereInputDatei =  pvReader.getPropertyString("WertpapiereKEV", "WertpapiereKEVInput-Datei", "Fehler");
      if (ivWertpapiereInputDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_KEV.error("Kein WertpapiereKEVInput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_KEV.info("WertpapiereKEVInput-Datei: " + ivWertpapiereInputDatei);
      }

      ivWertpapiereOutputDatei =  pvReader.getPropertyString("WertpapiereKEV", "WertpapiereKEVOutput-Datei", "Fehler");
      if (ivWertpapiereOutputDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_KEV.error("Kein WertpapiereKEVOutput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_KEV.info("WertpapiereKEVOutput-Datei: " + ivWertpapiereOutputDatei);
      }

      ivKundeRequestDatei = pvReader.getPropertyString("WertpapiereKEV", "KundeRequestDatei", "Fehler");
      if (ivKundeRequestDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_KEV.error("Kein KundeRequest-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_KEV.info("KundeRequestDatei: " + ivKundeRequestDatei);
      }

      ivFilterDatei = pvReader.getPropertyString("WertpapiereKEV", "Filter-Datei", "Fehler");
      if (ivFilterDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_KEV.error("Kein Filter-Dateiname in der ini-Datei...");
        System.exit(1);
      }


      String lvDaypointerFileout = pvReader.getPropertyString("Konfiguration", "DPFILE", "Fehler");
      if (lvDaypointerFileout.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_KEV.error("Kein [Konfiguration][DPFILE] in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_KEV.info("DPFILE=" + lvDaypointerFileout);
      }

      // Einlesen des Buchungsdatum
      File ivFileDaypointer = new File(lvDaypointerFileout);
      if (ivFileDaypointer != null && ivFileDaypointer.exists())
      {
        FileInputStream lvDaypointerFis = null;
        BufferedReader lvDaypointerBr = null;
        try
        {
          lvDaypointerFis = new FileInputStream(ivFileDaypointer);
          lvDaypointerBr = new BufferedReader(new InputStreamReader(lvDaypointerFis));
        }
        catch (Exception e)
        {
          LOGGER_WERTPAPIERE_KEV.info("Konnte Datei '" + lvDaypointerFileout + "' nicht oeffnen!");
        }
        try
        {
          ivBuchungsdatum = lvDaypointerBr.readLine();
        }
        catch (Exception e)
        {
          LOGGER_WERTPAPIERE_KEV.error("Fehler beim Verarbeiten:" + ivBuchungsdatum);
          e.printStackTrace();
        }
        try
        {
          lvDaypointerBr.close();
          lvDaypointerFis.close();
        }
        catch (Exception e)
        {
          LOGGER_WERTPAPIERE_KEV.error("Konnte Datei '" + lvDaypointerFileout + "' nicht schliessen!");
        }
      }
      else
      {
        LOGGER_WERTPAPIERE_KEV.info("Keine Datei '" + lvDaypointerFileout + "' gefunden...");
      }
      LOGGER_WERTPAPIERE_KEV.info("Buchungsdatum: " + ivBuchungsdatum);

      ivListeKeys = new HashSet<String>();

      // Liste der Kontonummern einlesen
      readListeKontonummern(ivListeKeys, ivExportVerzeichnis + "\\" + ivFilterDatei);

      // Gattungsdaten einlesen
      ivGattungsdaten =  new Gattungsdaten(pvReader, LOGGER_WERTPAPIERE_KEV);

      // DepotSummierungDaten initialisieren
      ivDepotSummierung = new DepotSummierung(LOGGER_WERTPAPIERE_KEV);

      // Start der Verarbeitung
      startVerarbeitung();

      // KundeRequest-Datei schliessen
      ivKundennummernOutput.close();
    }
  }

  /**
   * Startet die Verarbeitung
   */
  private void startVerarbeitung()
  {
    // KundeRequest-Datei oeffnen
    ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_WERTPAPIERE_KEV);
    ivKundennummernOutput.open();
    ivKundennummernOutput.printVorlaufsatz(ivInstitutsnummer, "KEV");

     // XML-Datei im TXS-Format
    ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivWertpapiereOutputDatei, LOGGER_WERTPAPIERE_KEV);
    ivOutputDarlehenXML.openXML();
    ivOutputDarlehenXML.printXMLStart();
    ivOutputDarlehenXML.printTXSImportDatenStart();
    ivOutputDarlehenXML.printTXSHeader(ivBuchungsdatum);

    // MAVIS-Bestandsdaten einlesen und verarbeiten
    readBestandsdaten(ivImportVerzeichnis + "\\" + ivWertpapiereInputDatei);

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    LOGGER_WERTPAPIERE_KEV.info(this.getStatistik());

    // KundeRequest-Datei schliessen
    ivKundennummernOutput.close();
  }

  /**
   * Einlesen und Verarbeiten der Wertpapier-Bestandsdaten
   */
  private void readBestandsdaten(String pvDateiname)
  {
    String lvZeile = null;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileLoanIQ = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileLoanIQ);
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_KEV.error("Konnte Bestandsdaten-Datei nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      while ((lvZeile = lvIn.readLine()) != null)  // Lesen der Wertpapier-Bestandsdaten
      {
        ivBestandsdaten = new Bestandsdaten();

        if (lvZeile.startsWith("##"))
        {
          LOGGER_WERTPAPIERE_KEV.info("Zeile: " + lvZeile);
        }
        else
        {
          if (ivBestandsdaten.parseBestandsdaten(lvZeile, LOGGER_WERTPAPIERE_KEV)) // Parsen der Felder
          {
            ivZaehlerBestandsdaten++;
            //if (!ivBestandsdaten.getAusplatzierungsmerkmal().isEmpty())
            //{
              verarbeiteBestandsdaten();
            //}
          }
        }
      }
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_KEV.error("Fehler beim Verarbeiten einer Zeile!");
      LOGGER_WERTPAPIERE_KEV.error("Zeile: " + lvZeile);
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_KEV.error("Konnte Bestandsdaten-Datei nicht schliessen!");
    }
  }

  /**
   * Verarbeite Bestandsdaten
   */
  private void verarbeiteBestandsdaten()
  {
    // CT 20.08.2018 - Sonderverarbeitung bis Aufsummierung von zwei Depots realisiert
    //if (ivBestandsdaten.getProdukt().equals("DE000LFA1602"))
    //{
    //  ivBestandsdaten.setNominalbetrag("55000000.0000");
    //}

    ivGattung = ivGattungsdaten.getGattung(ivBestandsdaten.getProdukt());
    if (ivGattung == null)
    {
      LOGGER_WERTPAPIERE_KEV.error("Keine Gattung fuer ISIN " + ivBestandsdaten.getProdukt() + " gefunden.");
      return;
    }
    else
    {
      LOGGER_WERTPAPIERE_KEV.info("Gattung fuer ISIN " + ivBestandsdaten.getProdukt() + " vorhanden.");
      ivZaehlerBestandsdatenVerarbeitet++;
    }

    LOGGER_WERTPAPIERE_KEV.info("Ausplatzierungsmerkmal: " + ivBestandsdaten.getAusplatzierungsmerkmal());
    LOGGER_WERTPAPIERE_KEV.info("DepotNr: " + ivBestandsdaten.getDepotNr());
    if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("A0") || ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("A1") ||
        ivBestandsdaten.getDepotNr().startsWith("SS") || // Workaround CT 29.10.2019
        ivListeKeys.contains(ivBestandsdaten.getProdukt())) // Workaround CT 19.03.2020 - MAVIS Anlieferung
    {
      ivKundennummernOutput.printKundennummer(
          MappingKunde.extendKundennummer(ivBestandsdaten.getKundennummer(), LOGGER_WERTPAPIERE_KEV));

      ivDepotSummierung.addDepotSummierungDaten(ivBestandsdaten.getProdukt(), ivBestandsdaten.getDepotNr(), ivBestandsdaten.getNominalbetrag());
      importWertpapier2Transaktion();

      if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("A0")) ivAnzahlA0++;
      if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("A1")) ivAnzahlA1++;
      if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("A2")) ivAnzahlA2++;
    }
  }

  /**
   * Importiert die Wertpapier-Informationen in die TXS-Transaktionen
   */
  private void importWertpapier2Transaktion()
  {
    ivFinanzgeschaeft.initTXSFinanzgeschaeft();
    ivSliceInDaten.initTXSSliceInDaten();
    ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
    ivKondDaten.initTXSKonditionenDaten();
    ivKredkunde.initTXSKreditKunde();

    boolean lvOkayWertpapier = true;

    if (lvOkayWertpapier)
    {
      lvOkayWertpapier = ivFinanzgeschaeft.importKEVWertpapier(ivBestandsdaten, ivInstitutsnummer);
    }

    if (lvOkayWertpapier)
    {
      lvOkayWertpapier = ivFinanzgeschaeftDaten.importKEVWertpapier(ivBestandsdaten, ivGattung, LOGGER_WERTPAPIERE_KEV);
    }

    if (lvOkayWertpapier)
    {
      lvOkayWertpapier = ivKondDaten.importKEVWertpapier(ivBestandsdaten, ivGattung, LOGGER_WERTPAPIERE_KEV);
    }

    if (lvOkayWertpapier)
    {
      if (ivBestandsdaten.getAktivPassiv().equals("1")) // Aktiv - Slice
      {
        lvOkayWertpapier = ivSliceInDaten.importKEVWertpapier(ivBestandsdaten, ivInstitutsnummer, LOGGER_WERTPAPIERE_KEV);
      }
      if (ivBestandsdaten.getAktivPassiv().equals("2")) // Passiv - Wertpapierposition + Globalurkunde
      {
        lvOkayWertpapier = ivWertpapierposition.importKEVWertpapier(ivBestandsdaten);
        if (lvOkayWertpapier)
        {
          lvOkayWertpapier = ivGlobalurkunde.importKEVWertpapier(ivBestandsdaten, ivGattung.getGD630A(), ivGattung.getGD660());
        }
      }
    }


    if (lvOkayWertpapier)
    {
      if (ivBestandsdaten.getAktivPassiv().equals("1")) // Nur Aktiv
      {
        lvOkayWertpapier = ivKredkunde.importKEVWertpapier(ivBestandsdaten, ivInstitutsnummer);
      }
    }

    // Transaktionen in die Datei schreiben
    if (lvOkayWertpapier)
    {
      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionStart());

      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionStart());
      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionDaten());
      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionEnde());

      ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
      ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
      ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());

      if (ivBestandsdaten.getAktivPassiv().equals("1")) // Nur Aktiv
      {
        ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionEnde());
      }

      if (ivBestandsdaten.getAktivPassiv().equals("2")) // Nur Passiv
      {
        // Wertpapierposition
        ivOutputDarlehenXML.printTransaktion(ivWertpapierposition.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(ivWertpapierposition.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(ivWertpapierposition.printTXSTransaktionEnde());

        // Globalurkunde
        ivOutputDarlehenXML.printTransaktion(ivGlobalurkunde.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(ivGlobalurkunde.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(ivGlobalurkunde.printTXSTransaktionEnde());
      }

      if (ivBestandsdaten.getAktivPassiv().equals("1")) // Nur Aktiv
      {
        ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionEnde());
      }

      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
    }
  }

  /**
   * Liefert eine Statistik
   * @return Statistik
   */
  private String getStatistik()
  {
    StringBuilder lvOut = new StringBuilder();

    lvOut.append("Anzahl gelesener Zeilen aus der Bestandsdaten-Datei: " + ivZaehlerBestandsdaten + StringKonverter.lineSeparator);
    lvOut.append("Anzahl verarbeiteter Bestandsdaten: " + ivZaehlerBestandsdatenVerarbeitet + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlA0 + " mit Ausplatzierungsmerkmal A0" + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlA1 + " mit Ausplatzierungsmerkmal A1" + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlA2 + " mit Ausplatzierungsmerkmal A2" + StringKonverter.lineSeparator);
    lvOut.append("Depotsummierungen:" + StringKonverter.lineSeparator);
    lvOut.append(ivDepotSummierung.toString());

    return lvOut.toString();
  }

  /**
   * Liest die Liste der ISIN ein
   * @param pvListeKontonummern Liste der ISIN
   * @param pvDateiname Dateiname der ISIN-liste
   */
  private void readListeKontonummern(HashSet<String> pvListeKontonummern, String pvDateiname) {
    LOGGER_WERTPAPIERE_KEV.info("Start - readListeKontonummern");
    LOGGER_WERTPAPIERE_KEV.info("Datei: " + pvDateiname);
    String lvZeile = null;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File lvFileListeKontonummern = new File(pvDateiname);
    try {
      lvFis = new FileInputStream(lvFileListeKontonummern);
    } catch (Exception e) {
      LOGGER_WERTPAPIERE_KEV.error(
          "Konnte die Kontonummernliste-Datei '" + pvDateiname + "' nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try {
      while ((lvZeile = lvIn.readLine()) != null) // Lesen der ListeKontonummern-Datei
      {
        LOGGER_WERTPAPIERE_KEV.info("Verarbeite Kontonummer: " + lvZeile);
        pvListeKontonummern.add(lvZeile);
      }
    } catch (Exception e) {
      LOGGER_WERTPAPIERE_KEV.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    LOGGER_WERTPAPIERE_KEV.info(
        "Anzahl von Kontonummern in der Liste: " + pvListeKontonummern.size());

    try {
      lvIn.close();
    } catch (Exception e) {
      LOGGER_WERTPAPIERE_KEV.error(
          "Konnte die Kontonummernliste-Datei '" + pvDateiname + "' nicht schliessen!");
    }
    }
  }
