/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.DeutscheHypo.Wertpapiere;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten.Konditionen;
import nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten.Kunde;
import nlb.txs.schnittstelle.DeutscheHypo.Wertpapiere.Bestandsdaten.Bestandsdaten;
import nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten.LoanIQPassivListeSummenbetraege;
import nlb.txs.schnittstelle.LoanIQ.DarlehenPassiv.Daten.LoanIQPassivSummenbetraege;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSGlobalurkunde;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSPersonenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Transaktion.TXSWertpapierposition;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingWertpapiere;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import nlb.txs.schnittstelle.Utilities.WPSuffix;
import nlb.txs.schnittstelle.Wertpapier.Gattungsdaten.Gattung;
import nlb.txs.schnittstelle.Wertpapier.Gattungsdaten.Gattungsdaten;
import org.apache.log4j.Logger;

public class WertpapiereVerarbeitungDH
{
  // Logger fuer Wertpapiere DH
  private static Logger LOGGER_WERTPAPIERE_DH = Logger.getLogger("TXSWertpapiereDHLogger");

  /**
   * Institutsnummer
   */
  private String ivInstitutsnummer;

  /**
   * Buchungsdatum
   */
  private String ivBuchungsdatum;

  /**
   * Import-Verzeichnis der MAVIS-Datei
   */
  private String ivImportVerzeichnis;

  /**
   * Export-Verzeichnis der TXS-Importdatei
   */
  private String ivExportVerzeichnis;

  /**
   * Dateiname der WertpapiereAktiv-Datei
   */
  private String ivWertpapiereAktivInputDatei;

  /**
   * Dateiname der WertpapierePassiv-Datei
   */
  private String ivWertpapierePassivInputDatei;

  /**
   * Dateiname der aktiven Wertpapiere SSD
   */
  private String ivWertpapiereSSDInputDatei;

  /**
   * Dateiname der passiven Wertpapiere NaPa
   */
  private String ivWertpapiereNaPaInputDatei;

  /**
   * Dateiname der TXS-Importdatei
   */
  private String ivWertpapiereDHOutputDatei;

  /**
   * Dateiname der Kunden-Datei
   */
  private String ivKundenDatei;

  /**
   * Dateiname der Konditionen-Datei
   */
  private String ivKonditionenDatei;

  /**
   * Dateiname der CashflowQuellsystem-Datei
   */
  private String ivCashflowQuellsystemDatei;

  /**
   * FileOutputStream fuer CashflowQuellsystem-Datei
   */
  private FileOutputStream ivFosCashflowQuellsystem;

  // Zaehlervariablen
  private int ivZaehlerKunde = 0;
  private int ivZaehlerKonditionen = 0;

  /**
   * Zaehler fuer die Anzahl der Zeilen in der Bestandsdaten-Datei
   */
  private int ivZaehlerBestandsdaten;

  /**
   * Zaehler mit der Anzahl verarbeiteter Bestandsdaten
   */
  private int ivZaehlerBestandsdatenVerarbeitet;

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
  private TXSPersonenDaten ivPersdaten;

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

  // Listen mit den Kunden- und Konditionendaten
  private HashMap<String, Kunde> ivListeKunden;
  private HashMap<String, Konditionen> ivListeKonditionen;

  /**
   * Liste mit den Summenbetraegen
   */
  private LoanIQPassivListeSummenbetraege ivListeSummenbetraege;

  /**
   * Konstruktor
   * @param pvReader
   */
  public WertpapiereVerarbeitungDH(IniReader pvReader)
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
    ivPersdaten = new TXSPersonenDaten();

    this.ivListeSummenbetraege = new LoanIQPassivListeSummenbetraege();

    if (pvReader != null)
    {

      ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
      if (ivInstitutsnummer.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Keine Institutsnummer in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("Institutsnummer: " + ivInstitutsnummer);
      }

      ivImportVerzeichnis = pvReader.getPropertyString("WertpapiereDH", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("ImportVerzeichnis: " + ivImportVerzeichnis);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("WertpapiereDH", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("ExportVerzeichnis: " + ivExportVerzeichnis);
      }

      ivWertpapiereAktivInputDatei =  pvReader.getPropertyString("WertpapiereDH", "WertpapiereAktivInput-Datei", "Fehler");
      if (ivWertpapiereAktivInputDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein WertpapiereAktivInput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("WertpapiereAktivInput-Datei: " + ivWertpapiereAktivInputDatei);
      }

      ivWertpapierePassivInputDatei =  pvReader.getPropertyString("WertpapiereDH", "WertpapierePassivInput-Datei", "Fehler");
      if (ivWertpapierePassivInputDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein WertpapierePassivInput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("WertpapierePassivInput-Datei: " + ivWertpapierePassivInputDatei);
      }

      ivWertpapiereSSDInputDatei = pvReader.getPropertyString("WertpapiereDH", "WertpapiereSSDInput-Datei", "Fehler");
      if (ivWertpapiereSSDInputDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein WertpapiereSSDInput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("WertpapiereSSDInput-Datei: " + ivWertpapiereSSDInputDatei);
      }

      ivWertpapiereNaPaInputDatei = pvReader.getPropertyString("WertpapiereDH", "WertpapiereNaPaInput-Datei", "Fehler");
      if (ivWertpapiereNaPaInputDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein WertpapiereNaPaInput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("WertpapiereNaPaInput-Datei: " + ivWertpapiereNaPaInputDatei);
      }

      ivWertpapiereDHOutputDatei =  pvReader.getPropertyString("WertpapiereDH", "WertpapiereOutput-Datei", "Fehler");
      if (ivWertpapiereDHOutputDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein WertpapiereOutput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("WertpapiereOutput-Datei: " + ivWertpapiereDHOutputDatei);
      }

      ivKonditionenDatei =  pvReader.getPropertyString("DH", "Konditionen-Datei", "Fehler");
      if (ivKonditionenDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein Konditionen-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("Konditionen-Datei: " + ivKonditionenDatei);
      }

      ivKundenDatei =  pvReader.getPropertyString("DH", "Kunden-Datei", "Fehler");
      if (ivKundenDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein Kunden-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("Kunden-Datei: " + ivKundenDatei);
      }

      ivCashflowQuellsystemDatei = pvReader.getPropertyString("WertpapiereDH", "Quellsystem-Datei", "Fehler");
      if (ivCashflowQuellsystemDatei.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein Cashflow-Quellsystem-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("Cashflow Quelsystem-Datei: " + ivCashflowQuellsystemDatei);
      }

      String lvDaypointerFileout = pvReader.getPropertyString("Konfiguration", "DPFILE", "Fehler");
      if (lvDaypointerFileout.equals("Fehler"))
      {
        LOGGER_WERTPAPIERE_DH.error("Kein [Konfiguration][DPFILE] in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("DPFILE=" + lvDaypointerFileout);
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
          LOGGER_WERTPAPIERE_DH.info("Konnte Datei '" + lvDaypointerFileout + "' nicht oeffnen!");
        }
        try
        {
          ivBuchungsdatum = lvDaypointerBr.readLine();
        }
        catch (Exception e)
        {
          LOGGER_WERTPAPIERE_DH.error("Fehler beim Verarbeiten:" + ivBuchungsdatum);
          e.printStackTrace();
        }
        try
        {
          lvDaypointerBr.close();
          lvDaypointerFis.close();
        }
        catch (Exception e)
        {
          LOGGER_WERTPAPIERE_DH.error("Konnte Datei '" + lvDaypointerFileout + "' nicht schliessen!");
        }
      }
      else
      {
        LOGGER_WERTPAPIERE_DH.info("Keine Datei '" + lvDaypointerFileout + "' gefunden...");
      }
      LOGGER_WERTPAPIERE_DH.info("Buchungsdatum: " + ivBuchungsdatum);

      // Gattungsdaten einlesen
      ivGattungsdaten =  new Gattungsdaten(pvReader, LOGGER_WERTPAPIERE_DH);

      // Start der Verarbeitung
      startVerarbeitung();
    }
  }

  /**
   *
   */
  public void startVerarbeitung()
  {
    // Cashflow-Quellsystem oeffnen (zum Schreiben)
    File lvFileCashflowQuellsystem = new File(ivExportVerzeichnis + "\\" + ivCashflowQuellsystemDatei);
    try
    {
      ivFosCashflowQuellsystem = new FileOutputStream(lvFileCashflowQuellsystem);
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Konnte CashflowQuellsystem-Datei nicht oeffnen!");
    }
    // Cashflow-Quellsystem oeffnen (zum Schreiben)

    ivListeKunden = new HashMap<String, Kunde>();
    ivListeKonditionen = new HashMap<String, Konditionen>();

    // XML-Datei im TXS-Format
    ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivWertpapiereDHOutputDatei, LOGGER_WERTPAPIERE_DH);
    ivOutputDarlehenXML.openXML();
    ivOutputDarlehenXML.printXMLStart();
    ivOutputDarlehenXML.printTXSImportDatenStart();
    ivOutputDarlehenXML.printTXSHeader(ivBuchungsdatum);

    // Kunden einlesen
    readKunden(ivImportVerzeichnis + "\\" + ivKundenDatei);

    // Konditionen einlesen
    readKonditionen(ivImportVerzeichnis + "\\" + ivKonditionenDatei);

    // Wertpapiere_Aktiv einlesen und verarbeiten
    readBestandsdaten(ivImportVerzeichnis + "\\" + ivWertpapiereAktivInputDatei, "Aktiv", false);

    // Wertpapiere_Passiv einlesen und verarbeiten
    readBestandsdaten(ivImportVerzeichnis + "\\" + ivWertpapierePassivInputDatei, "Aktiv", false);

    // Wertpapiere_NaPa einlesen fuer die Summenbildung
    readBestandsdaten(ivImportVerzeichnis + "\\" + ivWertpapiereNaPaInputDatei, "SSD", true);

    // Wertpapiere_NaPa einlesen und verarbeiten
    readBestandsdaten(ivImportVerzeichnis + "\\" + ivWertpapiereNaPaInputDatei, "SSD", false);

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    // Cashflow-Quellsystem schliessen
    try
    {
      ivFosCashflowQuellsystem.close();
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Fehler beim Schliessen der CashflowQuellsystem-Datei");
    }
    // Cashflow-Quellsystem schliessen

    LOGGER_WERTPAPIERE_DH.info(this.getStatistik());

  }

  /**
   * Liefert eine Statistik als String
   * @return
   */
  private String getStatistik()
  {
    StringBuilder lvOut = new StringBuilder();

    lvOut.append("Anzahl gelesener Zeilen aus der Bestandsdaten-Datei: " + ivZaehlerBestandsdaten + StringKonverter.lineSeparator);
    lvOut.append("Anzahl verarbeiteter Bestandsdaten: " + ivZaehlerBestandsdatenVerarbeitet + StringKonverter.lineSeparator);
    lvOut.append("Anzahl Kundendaten: " + ivZaehlerKunde + StringKonverter.lineSeparator);
    lvOut.append("Anzahl Konditionen: " + ivZaehlerKonditionen + StringKonverter.lineSeparator);

    return lvOut.toString();
  }

  /**
   * Einlesen der Kunden-Daten
   * @param pvDateiname
   */
  private void readKunden(String pvDateiname)
  {
    String lvZeile = null;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDH = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDH);
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Konnte Kunden-Datei nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Deckung-Datei ein
      {
        Kunde lvKunde = new Kunde(LOGGER_WERTPAPIERE_DH);
        if (lvKunde.parseKunde(lvZeile)) // Parsen der Felder
        {
          ivZaehlerKunde++;
          ivListeKunden.put(lvKunde.getKundennummer(), lvKunde);
        }
      }
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Konnte Kunden-Datei nicht schliessen!");
    }
  }

  /**
   * Einlesen der Konditionen-Daten
   * @param pvDateiname
   */
  private void readKonditionen(String pvDateiname)
  {
    String lvZeile = null;

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileDH = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileDH);
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Konnte Konditionen-Datei nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      lvIn.readLine(); // Erste Zeile (Ueberschriften) ueberlesen
      while ((lvZeile = lvIn.readLine()) != null)  // Lese Zeile der Konditionen-Datei ein
      {
        Konditionen lvKondition = new Konditionen(LOGGER_WERTPAPIERE_DH);
        if (lvKondition.parseKonditionen(lvZeile)) // Parsen der Felder
        {
          ivZaehlerKonditionen++;
          ivListeKonditionen.put(lvKondition.getKontonummer(), lvKondition);
        }
      }
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Konnte Konditionen-Datei nicht schliessen!");
    }
  }

  /**
   * Einlesen und Verarbeiten der Wertpapier-Bestandsdaten
   */
  private void readBestandsdaten(String pvDateiname, String pvModus, boolean pvSummenbildung)
  {
    LOGGER_WERTPAPIERE_DH.info(pvDateiname + " - " + pvModus + " - " + pvSummenbildung);
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
      LOGGER_WERTPAPIERE_DH.error("Konnte Bestandsdaten-Datei nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));

    try
    {
      // Erste Zeile ueberlesen
      lvIn.readLine();

      while ((lvZeile = lvIn.readLine()) != null)  // Lesen der Wertpapier-Bestandsdaten
      {
        ivBestandsdaten = new Bestandsdaten();

          if (ivBestandsdaten.parseBestandsdaten(lvZeile, LOGGER_WERTPAPIERE_DH)) // Parsen der Felder
          {
            ivZaehlerBestandsdaten++;
            if (pvModus.equals("Aktiv")) {
              verarbeiteBestandsdaten();
            }
            if (pvModus.equals("SSD"))
            {
              if (pvSummenbildung)
              {
                berechneSummenbetraege();
              }
              else {
                verarbeiteBestandsdatenSSD();
              }
            }
          }
      }
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Fehler beim Verarbeiten einer Zeile!");
      LOGGER_WERTPAPIERE_DH.error("Zeile: " + lvZeile);
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Konnte Bestandsdaten-Datei nicht schliessen!");
    }
  }

  /**
   * Berechnet die Summenbetraege von Nominalbetrag und Leistungsrate
   */
  private void berechneSummenbetraege()
  {
    if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("K") || ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("H"))
    {
      if (ivListeSummenbetraege.containsKey(ivBestandsdaten.getISIN()))
      {
        LoanIQPassivSummenbetraege lvSummenbetraege = ivListeSummenbetraege.get(ivBestandsdaten.getISIN());
        lvSummenbetraege.addNominalbetrag(StringKonverter.convertString2BigDecimal(ivBestandsdaten.getNominalbetrag()));
        Konditionen lvKonditionen = ivListeKonditionen.get(ivBestandsdaten.getProdukt());
        if (lvKonditionen != null) {
          lvSummenbetraege
              .addLeistungsrate(StringKonverter.convertString2BigDecimal(lvKonditionen.getLrate()));
        }
      }
      else
      {
        LoanIQPassivSummenbetraege lvSummenbetraege = new LoanIQPassivSummenbetraege();
        lvSummenbetraege.addNominalbetrag(StringKonverter.convertString2BigDecimal(ivBestandsdaten.getNominalbetrag()));
        Konditionen lvKonditionen = ivListeKonditionen.get(ivBestandsdaten.getProdukt());
        if (lvKonditionen != null) {
          lvSummenbetraege
              .addLeistungsrate(StringKonverter.convertString2BigDecimal(lvKonditionen.getLrate()));
        }
        ivListeSummenbetraege.putSummenbetraege(ivBestandsdaten.getISIN(), lvSummenbetraege);
      }
    }
  }

  /**
   * Verarbeite Bestandsdaten
   */
  private void verarbeiteBestandsdaten()
  {
    ivGattung = ivGattungsdaten.getGattung(ivBestandsdaten.getISIN());
    if (ivGattung == null)
    {
      LOGGER_WERTPAPIERE_DH.error("Keine Gattung fuer ISIN " + ivBestandsdaten.getISIN() + " gefunden.");
      return;
    }
    else
    {
      LOGGER_WERTPAPIERE_DH.info("Gattung fuer ISIN " + ivBestandsdaten.getISIN() + " vorhanden.");
      ivZaehlerBestandsdatenVerarbeitet++;
    }

    importWertpapier2Transaktion();

  }

  /**
   * Verarbeite Bestandsdaten
   */
  private void verarbeiteBestandsdatenSSD() {
    importWertpapierSSD2Transaktion();
  }

  /**
   * Importiert die Wertpapier-Informationen in die TXS-Transaktionen
   */
  private void importWertpapier2Transaktion()
  {
    LOGGER_WERTPAPIERE_DH.info("ImportWertpapier: " + ivBestandsdaten.getISIN());

    ivFinanzgeschaeft.initTXSFinanzgeschaeft();
    ivSliceInDaten.initTXSSliceInDaten();
    ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
    ivKondDaten.initTXSKonditionenDaten();
    ivKredkunde.initTXSKreditKunde();
    ivPersdaten.initTXSPersonenDaten();

    boolean lvOkayWertpapier = true;

    if (lvOkayWertpapier)
    {
      // Key und Quelle setzen
      ivFinanzgeschaeft.setKey(ivBestandsdaten.getISIN());
      if (ivBestandsdaten.getAktivPassiv().equals("A")) {
        ivFinanzgeschaeft.setQuelle("ADHYWPPFBG");
      }
      if (ivBestandsdaten.getAktivPassiv().equals("P"))
      {
        ivFinanzgeschaeft.setQuelle("PDHYPFBG");
      }

      // Originator per Institutsnummer setzen
      ivFinanzgeschaeft.setOriginator("25010600");

    }

    if (lvOkayWertpapier)
    {
      if (ivBestandsdaten.getAktivPassiv().equals("A")) {
        ivFinanzgeschaeftDaten.setAktivpassiv("1");
      }
      if (ivBestandsdaten.getAktivPassiv().equals("P")) {
        ivFinanzgeschaeftDaten.setAktivpassiv("2");
      }

      //ivFinanzgeschaeftDaten.setAz(ivBestandsdaten.getProdukt());
      ivFinanzgeschaeftDaten.setAz(ivBestandsdaten.getISIN());
      ivFinanzgeschaeftDaten.setIsin(ivBestandsdaten.getISIN());
      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      {
        //this.ivDepotnummer = pvBestandsdaten.getDepotNr();
      }
      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Aktiv - Konstant '2'
      {
        ivFinanzgeschaeftDaten.setKat("2");
      }
      if (ivBestandsdaten.getAktivPassiv().equals("P")) // Passiv - Konstant '7'
      {
        ivFinanzgeschaeftDaten.setKat("7");
      }
      //ivFinanzgeschaeftDaten.setVwhrg(ivGattung.getGD630B());
      //ivFinanzgeschaeftDaten.setWhrg(ivGattung.getGD630B());
      ivFinanzgeschaeftDaten.setVwhrg(ivBestandsdaten.getNominalbetragWaehrung());
      ivFinanzgeschaeftDaten.setWhrg(ivBestandsdaten.getNominalbetragWaehrung());
      ivFinanzgeschaeftDaten.setNbetrag(ivBestandsdaten.getNominalbetrag());

      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      {
        ivFinanzgeschaeftDaten.setRkapi(ivBestandsdaten.getNominalbetrag());
      }

      // Ausplatzierungsmerkmal wird verwendet - CT 16.06.2016
      // Aktive Wertpapiere aus der Liquiditaetsreserve oder der sichernden Ueberdeckung erhalten repo -> 'Ja'
      // CT 06.08.2015
      if (ivBestandsdaten.getEZBfaehig().equals("Yes"))
      {
        ivFinanzgeschaeftDaten.setRepo("1");
      }
      else {
        ivFinanzgeschaeftDaten.setRepo("0"); //Default
      }
      if (("K2".equals(ivBestandsdaten.getAusplatzierungsmerkmal())) ||  // sichernde Ueberdeckung KO
          ("H2".equals(ivBestandsdaten.getAusplatzierungsmerkmal())) ||  // sichernde Ueberdeckung Hypo
          ("K4".equals(ivBestandsdaten.getAusplatzierungsmerkmal())) ||  // Liquiditaetsreserve KO
          ("H4".equals(ivBestandsdaten.getAusplatzierungsmerkmal())))    // Liquiditaetsreserve HPF
      {
        ivFinanzgeschaeftDaten.setRepo("1");
      }

      ivFinanzgeschaeftDaten.setBsform(ivBestandsdaten.getWertpapierBezeichnung());
      ivFinanzgeschaeftDaten.setAbetrag(ivBestandsdaten.getNominalbetrag());

      ivFinanzgeschaeftDaten.setUrtilgsatz(ivGattung.getGD861A());
      ivFinanzgeschaeftDaten.setKuendr(ivGattung.getGD943());
      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Aktiv - Konstant '21'
      {
        ivFinanzgeschaeftDaten.setTyp("21");
      }

      if (ivBestandsdaten.getAktivPassiv().equals("P")) // Passiv - WP-Art
      {
        ivFinanzgeschaeftDaten.setTyp(ivBestandsdaten.getWertpapierart());
      }

      ivFinanzgeschaeftDaten.setTkurs("100.00");
      //if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      //{
      //	ivFinanzgeschaeftDaten.setTkurs(ivGattung.getGD669());
      //}

      ivFinanzgeschaeftDaten.setGd160(ivGattung.getGD160());
      ivFinanzgeschaeftDaten.setGd260(String2XML.change2XML(ivGattung.getGD260()));
      ivFinanzgeschaeftDaten.setGd776(ivGattung.getGD776());
      ivFinanzgeschaeftDaten.setGd776B(ivGattung.getGD776B());
      //this.ivGibnot = pvGattung.getBoersennotiert();

    }

    if (lvOkayWertpapier)
    {
      ivKondDaten.setKondkey("1");
      ivKondDaten.setEkurs(ivGattung.getGD669());
      if (!ivGattung.getGD660().isEmpty())
      {
        ivKondDaten.setEdat(ivGattung.getGD660());
      }
      else
      {
        ivKondDaten.setEdat(ivGattung.getGD322());
      }

      //if (ivBestandsdaten.getAktivPassiv().equals("A")) // nur Aktiv
      //{
        ivKondDaten.setEnddat(ivGattung.getGD910());
      //}

      ivKondDaten.setAtkonv("1"); // Konstant 'Danach'(1)
      ivKondDaten.setAtkonvmod("0"); // Erst einmal Konstant '0' -> Problem MAVIS-Produkttyp
      ivKondDaten.setAtkonvtag("0"); // Konstant '0'
      ivKondDaten.setBankkal("DE");  // Konstant 'DE'
      ivKondDaten.setSpread(ivGattung.getGD808C());
      ivKondDaten.setCap(ivGattung.getGD804E());
      if (ivBestandsdaten.getAktivPassiv().equals("P")) // nur Passiv
      {
        ivKondDaten.setDatltztfix(ivGattung.getGD815B());
      }

      ivKondDaten.setFloor(ivGattung.getGD803E());

      ivKondDaten.setFaellig(ivGattung.getGD910());

      ivKondDaten.setFixkalart("1"); // Konstant 'Banktage'(1)
      ivKondDaten.setFixkonv(MappingWertpapiere.ermittleFixingKonvention(ivGattung.getGD809C()));
      if (ivBestandsdaten.getAktivPassiv().equals("P")) // nur Passiv
      {
        ivKondDaten.setFixrhyth(MappingWertpapiere.ermittleZinsrythmus(ivGattung.getGD311A(), ivGattung.getGD811())); // Auch hier Zinsrythmus verwenden
      }
      ivKondDaten.setFixtage(ivGattung.getGD809());
      ivKondDaten.setFixtagedir("2"); // Konstant 'Davor'(2)
      ivKondDaten.setFixtagemod("0"); // Konstant '0'
      ivKondDaten.setKalfix("DE"); // Konstant 'DE'
      ivKondDaten.setKalkonv(MappingWertpapiere.ermittleKalenderkonvention(ivGattung.getGD821B()));

      if (ivBestandsdaten.getAktivPassiv().equals("A"))
      {
        if (ivGattung.getGD821B().equals("09") || ivGattung.getGD821B().equals("10"))
        { // Ungerade(2)
          ivKondDaten.setKupbas("2");
          ivKondDaten.setKupbasodd("2");
        }
        else // Gerade(1)
        {
          ivKondDaten.setKupbas("1");
          ivKondDaten.setKupbasodd("1");
        }
      }
      if (ivBestandsdaten.getAktivPassiv().equals("P"))
      {
        // Anpassungen an TXS 4.03 wegen geaenderter Barwertberechnungen dort an variablen Papieren
        if ("010202".equals(ivBestandsdaten.getWertpapierart()))
        {
          ivKondDaten.setKupbas("0");
          ivKondDaten.setKupbasodd("0");
          //this.ivDatltztfix = pvGattung.getGD815B(); // Hier noetig...
          //lvEl_konddaten.setAttribute("fixrhyth",lvNodeSA01.getChildText("Zinszahlungstyp").trim());
        }
        else
        {
          if (ivGattung.getGD821B().equals("09") || ivGattung.getGD821B().equals("10"))
          { // Ungerade(2)
            ivKondDaten.setKupbas("2");
            ivKondDaten.setKupbasodd("2");
          }
          else // Gerade(1)
          {
            ivKondDaten.setKupbas("1");
            ivKondDaten.setKupbasodd("1");
          }
        }
      }
      if (ivGattung.getGD900().isEmpty())
      {
        ivKondDaten.setLrate(ivBestandsdaten.getNominalbetrag());
      }
      else
      {
        ivKondDaten.setLrate(ivGattung.getGD921B());
      }
      ivKondDaten.setMonendkonv("1"); // Konstant 'gleich'(1)
      ivKondDaten.setMantilg("1"); // Cashflows werden angeliefert - Aenderung zur alten Schnittstelle
      ivKondDaten.setManzins("1"); // Cashflows werden angeliefert - Aenderung zur alten Schnittstelle
      ivKondDaten.setNomzins(ivGattung.getGD801A());
      ivKondDaten.setRefzins(MappingWertpapiere.ermittleReferenzzins(ivGattung.getGD808(), ivGattung.getGD808A(), ivGattung.getGD808B()));
      // CT 27.05.2019 - Duplizierung des Referenzzins in 'ivRefzinstxt'
      ivKondDaten.setRefzinstxt(ivKondDaten.getRefzins());

      //if (pvBestandsdaten.getAktivPassiv().equals("2")) // nur Passiv
      //{
      ivKondDaten.setRkurs(ivGattung.getGD861A());
      //}
      if (ivBestandsdaten.getAktivPassiv().equals("P")) // nur Passiv
      {
        ivKondDaten.setTilgabw("0"); // Konstant '0'
      }
      if (ivBestandsdaten.getAktivPassiv().equals("P")) // nur Passiv
      {
        ivKondDaten.setTilgbeg(ivGattung.getGD910());
      }
      ivKondDaten.setTilgdat(ivGattung.getGD910());
      ivKondDaten.setTilgperkonv("1"); // Konstant 'anchor'(1)

      // Tilgungsrythmus defaultmaessig auf 'bullet' (0) setzen - CT 11.08.2015
      //if (pvBestandsdaten.getAktivPassiv().equals("2"))
      //{
      ivKondDaten.setTilgryth("0");
      //}
    	/* else
    	{
      		// GD842     	TXS-Wert
    		// 1            12
    		// 3            6
    		// 4            3
    		// A            1
    		// <> blank     13

    		// Defaultwert '0'
    		this.ivTilgryth = "0";
    		if (!(pvGattung.getGD842().equals("1") || pvGattung.getGD842().equals("3") || pvGattung.getGD842().equals("4") || pvGattung.getGD842().equals("A")))
    		{
    			this.ivTilgryth = "13";
    		}
    		else
    		{
    		  if (pvGattung.getGD842().equals("1"))
    		  {
    			this.ivTilgryth = "12";
    		  }
    		  if (pvGattung.getGD842().equals("3"))
    		  {
    			this.ivTilgryth = "6";
    		  }
    		  if (pvGattung.getGD842().equals("4"))
    		  {
    			this.ivTilgryth = "3";
    		  }
    		  if (pvGattung.getGD842().equals("A"))
    		  {
    			this.ivTilgryth = "1";
    		  }
    		}
    	} */
      ////this.ivTilgsatz = "0";
      ////if (pvGattung.getGD841().equals("3") || pvGattung.getGD841().equals("4") || pvGattung.getGD841().equals("E") || pvGattung.getGD841().equals("B"))
      ////{
      ////	this.ivTilgsatz = "100";
      ////}
      ivKondDaten.setTilgsatz(ivGattung.getGD861A());

      ivKondDaten.setZahltyp(MappingWertpapiere.ermittleZahlungstyp(ivGattung.getGD841()));
      if (ivKondDaten.getZahltyp().equals("3"))
      {
        ivKondDaten.setTilgver("0");
      }
      ivKondDaten.setZinsabw(MappingWertpapiere.ermittleZinsabweichung(ivGattung.getGD321()));
      ivKondDaten.setZinsbeg(ivGattung.getGD322());
      ivKondDaten.setZinsdat(ivGattung.getGD290A());
      ivKondDaten.setZinsenddat(ivGattung.getGD910());
      ivKondDaten.setZinsperkonv("1"); // Konstant 'anchor'(1)
      ivKondDaten.setZinsryth(MappingWertpapiere.ermittleZinsrythmus(ivGattung.getGD311A(), ivGattung.getGD811()));
      ivKondDaten.setZinstyp(MappingWertpapiere.ermittleZinstyp(ivGattung.getGD805()));
      ivKondDaten.setZinszahlart(MappingWertpapiere.ermittleZinszahlart(ivGattung.getGD805(), ivGattung.getGD312()));
      ivKondDaten.setZinsfak("1"); // Konstant '1'
      ivKondDaten.setVzinsdat("0"); // Konstant '0'
      ivKondDaten.setVztilgdat("0"); // Konstant '0'
      ivKondDaten.setWhrg(ivBestandsdaten.getNominalbetragWaehrung());
    }

    if (lvOkayWertpapier)
    {

      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Aktiv - Slice
      {
        LOGGER_WERTPAPIERE_DH.info("Wertpapier " + ivBestandsdaten.getISIN() + " - Ausplatzierungsmerkmal " + ivBestandsdaten.getAusplatzierungsmerkmal());

        if (!(ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("H") || ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("K")))
        {
          LOGGER_WERTPAPIERE_DH.error("Wertpapier " + ivBestandsdaten.getISIN() + " - Ungueltiges Ausplatzierungsmerkmal " + ivBestandsdaten.getAusplatzierungsmerkmal());
        }
        else
        {
        // 18.11.2016 - Slice-Typ ueber das Ausplatzierungsmerkmal ermitteln
        // Hypothekenpfandbrief
        if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("H"))
        {
          ivSliceInDaten.setKey(ivBestandsdaten.getProdukt() + WPSuffix
              .getSuffix(ivBestandsdaten.getAusplatzierungsmerkmal(), LOGGER_WERTPAPIERE_DH));
          ivSliceInDaten.setTx("Hypothekenpfandbrief");
          ivSliceInDaten.setPool("Hypothekenpfandbrief");
          ivSliceInDaten.setPrj("NLB-PfandBG");
         }

        // Oeffentlicher Pfandbrief
        if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("K"))
        {
          ivSliceInDaten.setKey(ivBestandsdaten.getProdukt() + WPSuffix.getSuffix(ivBestandsdaten.getAusplatzierungsmerkmal(), LOGGER_WERTPAPIERE_DH));
          ivSliceInDaten.setTx("&#214;ffentlicher Pfandbrief");
          ivSliceInDaten.setPool("&#214;ffentlicher Pfandbrief");
          ivSliceInDaten.setPrj("NLB-PfandBG");
        }

          ivSliceInDaten.setAktivpassiv("1"); // Slice immer Aktiv

        // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
        // 1 -> Ordentl. gattungsklassische Deckung
        // 2 -> Sichernde Ueberdeckung
        // 3 -> Weitere Deckung

          ivSliceInDaten.setDecktyp("1");
        if (ivBestandsdaten.getAusplatzierungsmerkmal().endsWith("2") || ivBestandsdaten.getAusplatzierungsmerkmal().equals("O4"))
        {
          ivSliceInDaten.setDecktyp("2");
        }
        else if (!ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("O") &&
            (ivBestandsdaten.getAusplatzierungsmerkmal().endsWith("3") || ivBestandsdaten.getAusplatzierungsmerkmal().endsWith("4")))
        {
          ivSliceInDaten.setDecktyp("3");
        }

          ivSliceInDaten.setVon("0.0");
          ivSliceInDaten.setBis(ivBestandsdaten.getNominalbetrag());

        if (StringKonverter.convertString2Double(ivSliceInDaten.getBis()) > 0.0)
        {
          ivSliceInDaten.setNbetrag(ivSliceInDaten.getBis());
        }
        else
        {
          ivSliceInDaten.setNbetrag("0.0");
        }

        // CT 20.12.2011 - NurLiq-Kennzeichen setzen
        // Defaultmaessig erst einmal auf "0" (false) setzen
          ivSliceInDaten.setNliqui("0");
        if (ivBestandsdaten.getAusplatzierungsmerkmal().endsWith("4"))
        {
          if (!ivBestandsdaten.getAusplatzierungsmerkmal().equals("O4")) // OEPG - 'O4' nicht
          {
            ivSliceInDaten.setNliqui("1");
          }
        }

          ivSliceInDaten.setWhrg(ivBestandsdaten.getNominalbetragWaehrung());
      }
      }

      if (ivBestandsdaten.getAktivPassiv().equals("P")) // Passiv - Wertpapierposition + Globalurkunde
      {
        ivWertpapierposition.setWpposkey(ivBestandsdaten.getProdukt() + "_0");
        ivWertpapierposition.setLfdnr(ivBestandsdaten.getProdukt());
        ivWertpapierposition.setWhrg(ivBestandsdaten.getNominalbetragWaehrung());
        ivWertpapierposition.setNbetrag(ivBestandsdaten.getNominalbetrag());
        ivWertpapierposition.setRkapi(ivBestandsdaten.getNominalbetrag());

        ivGlobalurkunde.setGukey(ivBestandsdaten.getProdukt() + "_1");

        String lvmyregtyp = ivBestandsdaten.getWertpapierart().substring(3,4);

        ivGlobalurkunde.setRegtyp(lvmyregtyp);

        //this.ivBiszu = pvBestandsdaten.getNominalbetrag();
        ivGlobalurkunde.setBiszu(ivGattung.getGD630A());

        ivGlobalurkunde.setValuta(ivBestandsdaten.getNominalbetrag());
        ivGlobalurkunde.setWhrg(ivBestandsdaten.getNominalbetragWaehrung());
        ivGlobalurkunde.setNr("1");
        ivGlobalurkunde.setBiszudat(ivGattung.getGD660());
      }

    }

    if (lvOkayWertpapier)
    {
      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      {
        Kunde lvKundeDH = ivListeKunden.get(ivBestandsdaten.getKundennummer());
        if (lvKundeDH != null) {
          ivKredkunde.setKdnr(lvKundeDH.getKundennummer());
          ivKredkunde.setOrg("25010600");
          ivKredkunde.setQuelle("KUNDE");
          ivKredkunde.setRolle("0");

          ivPersdaten.setNname(String2XML.change2XML(lvKundeDH.getNname()));
          ivPersdaten.setLand(lvKundeDH.getLand());
          ivPersdaten.setStr(String2XML.change2XML(lvKundeDH.getStr()));
          if (String2XML.change2XML(lvKundeDH.getHausnummer()).length() > 10)
          {
            ivPersdaten.setHausnr(String2XML.change2XML(lvKundeDH.getHausnummer()).substring(0, 9));
          }
          else {
            ivPersdaten.setHausnr(String2XML.change2XML(lvKundeDH.getHausnummer()));
          }
          ivPersdaten.setPlz(lvKundeDH.getPlz());
          ivPersdaten.setOrt(String2XML.change2XML(lvKundeDH.getOrt()));
          ivPersdaten.setKusyma(lvKundeDH.getKusyma());
          ivPersdaten.setKugr(String2XML.change2XML(ValueMapping.changeKundengruppe(ermittleKundengruppe(lvKundeDH.getKusyma()))));
          ivPersdaten.setRatingID(String2XML.change2XML(lvKundeDH.getRating_id()));
          if (String2XML.change2XML(lvKundeDH.getRating_id()).length() > 10) {
            ivPersdaten.setRatingID(String2XML.change2XML(lvKundeDH.getRating_id()).substring(0, 9));
          }
          ivPersdaten.setRatingMaster(String2XML.change2XML(lvKundeDH.getRating_master()));
          if (String2XML.change2XML(lvKundeDH.getRating_master()).length() > 40) {
            ivPersdaten.setRatingMaster(String2XML.change2XML(lvKundeDH.getRating_master()).substring(0, 39));
          }
          ivPersdaten.setRatingDatum(DatumUtilities.changeDatePoints(String2XML.change2XML(lvKundeDH.getRating_datum())));
          ivPersdaten.setRatingToolID(String2XML.change2XML(lvKundeDH.getRating_tool_id()));
          ivPersdaten.setRatingTool(String2XML.change2XML(lvKundeDH.getRating_tool()));

          // CT 10.03.2021 - DH liefert kein Gebiet, deshalb selbst ermitteln
          if (ivPersdaten.getLand().equals("DE"))
          {
            String lvGebiet = "DE_";
            int lvRet_gebiet = ValueMapping.PLZ2Land("009", lvKundeDH.getPlz());
            if (lvRet_gebiet < 10)
            {
              lvGebiet = lvGebiet + "0";
            }
            //if (ret_gebiet < 100)
            //{
            //  gebiet = gebiet + "0";
            //}
            lvGebiet = lvGebiet + lvRet_gebiet;

            ivPersdaten.setGebiet(lvGebiet);
          }

          //lvPersdaten.setGebiet(lvKundeDH.getGebiet());
        }
      }
    }

    // Daten in CashflowQuellsystem-Datei schreiben
    try
    {
       ivFosCashflowQuellsystem.write((ivBestandsdaten.getProdukt() + ";" + ivFinanzgeschaeft.getKey() + ";" + ivFinanzgeschaeft.getOriginator() + ";" +
          ivFinanzgeschaeft.getQuelle() + ";;;;;;;;;;;" + StringKonverter.lineSeparator).getBytes());
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Fehler bei der Ausgabe in die CashflowQuellsystem-Datei");
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

      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      {
        ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionEnde());
      }

      if (ivBestandsdaten.getAktivPassiv().equals("P")) // Nur Passiv
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

      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      {
        if (!ivKredkunde.getKdnr().isEmpty()) {
          ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionDaten());

          ivOutputDarlehenXML.printTransaktion(ivPersdaten.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(ivPersdaten.printTXSTransaktionDaten());
          ivOutputDarlehenXML.printTransaktion(ivPersdaten.printTXSTransaktionEnde());

          ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionEnde());
        }
      }

      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
    }
  }

  /**
   * Importiert die WertpapierSSD-Informationen in die TXS-Transaktionen
   */
  private void importWertpapierSSD2Transaktion()
  {
    LOGGER_WERTPAPIERE_DH.info("ImportWertpapier - SSD: " + ivBestandsdaten.getProdukt());

    ivFinanzgeschaeft.initTXSFinanzgeschaeft();
    ivSliceInDaten.initTXSSliceInDaten();
    ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
    ivKondDaten.initTXSKonditionenDaten();
    ivKredkunde.initTXSKreditKunde();
    ivPersdaten.initTXSPersonenDaten();
    ivWertpapierposition.initTXSWertpapierposition();
    ivGlobalurkunde.initTXSGlobalurkunde();

    boolean lvOkayWertpapier = true;

    if (lvOkayWertpapier)
    {
      // Key und Quelle setzen
      ivFinanzgeschaeft.setKey(ivBestandsdaten.getISIN());
      if (ivBestandsdaten.getAktivPassiv().equals("A")) {
        ivFinanzgeschaeft.setQuelle("ADHYWPPFBG");
      }

      if (ivBestandsdaten.getAktivPassiv().equals("P")) {
        ivFinanzgeschaeft.setQuelle("PDHYPFBG");
      }

      // Originator per Institutsnummer setzen
      ivFinanzgeschaeft.setOriginator("25010600");

    }

    if (lvOkayWertpapier)
    {
      if (ivBestandsdaten.getAktivPassiv().equals("A")) {
        ivFinanzgeschaeftDaten.setAktivpassiv("1");
      }
      if (ivBestandsdaten.getAktivPassiv().equals("P")) {
        ivFinanzgeschaeftDaten.setAktivpassiv("2");
      }

      //ivFinanzgeschaeftDaten.setAz(ivBestandsdaten.getProdukt());
      ivFinanzgeschaeftDaten.setAz(ivBestandsdaten.getISIN());
      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      {
        //this.ivDepotnummer = pvBestandsdaten.getDepotNr();
      }
      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Aktiv - Konstant '2'
      {
        ivFinanzgeschaeftDaten.setKat("2");
      }
      if (ivBestandsdaten.getAktivPassiv().equals("P")) // Passiv - Konstant '7'
      {
        ivFinanzgeschaeftDaten.setKat("7");
      }
      ivFinanzgeschaeftDaten.setVwhrg(ivBestandsdaten.getNominalbetragWaehrung());
      ivFinanzgeschaeftDaten.setWhrg(ivBestandsdaten.getNominalbetragWaehrung());
      //ivFinanzgeschaeftDaten.setNbetrag(ivBestandsdaten.getNominalbetrag());
      ivFinanzgeschaeftDaten.setNbetrag(ivListeSummenbetraege.getSummenbetraege(ivBestandsdaten.getISIN()).getNominalbetrag().toString());
      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      {
        ivFinanzgeschaeftDaten.setRkapi(ivBestandsdaten.getNominalbetrag());
      }

      // Ausplatzierungsmerkmal wird verwendet - CT 16.06.2016
      // Aktive Wertpapiere aus der Liquiditaetsreserve oder der sichernden Ueberdeckung erhalten repo -> 'Ja'
      // CT 06.08.2015
      if (ivBestandsdaten.getEZBfaehig().equals("Yes"))
      {
        ivFinanzgeschaeftDaten.setRepo("1");
      }
      else {
        ivFinanzgeschaeftDaten.setRepo("0"); //Default
      }
      if (("K2".equals(ivBestandsdaten.getAusplatzierungsmerkmal())) ||  // sichernde Ueberdeckung KO
          ("H2".equals(ivBestandsdaten.getAusplatzierungsmerkmal())) ||  // sichernde Ueberdeckung Hypo
          ("K4".equals(ivBestandsdaten.getAusplatzierungsmerkmal())) ||  // Liquiditaetsreserve KO
          ("H4".equals(ivBestandsdaten.getAusplatzierungsmerkmal())))    // Liquiditaetsreserve HPF
      {
        ivFinanzgeschaeftDaten.setRepo("1");
      }

      ivFinanzgeschaeftDaten.setBsform(ivBestandsdaten.getWertpapierBezeichnung());
      ivFinanzgeschaeftDaten.setAbetrag(ivBestandsdaten.getNominalbetrag());

      //ivFinanzgeschaeftDaten.setUrtilgsatz(ivGattung.getGD861A());
      //ivFinanzgeschaeftDaten.setKuendr(ivGattung.getGD943());
      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Aktiv - Konstant '21'
      {
        ivFinanzgeschaeftDaten.setTyp("21");
      }

      if (ivBestandsdaten.getAktivPassiv().equals("P")) // Passiv - WP-Art
      {
        ivFinanzgeschaeftDaten.setTyp(ivBestandsdaten.getWertpapierart());
      }

     }

    Konditionen lvKonditionen = ivListeKonditionen.get(ivBestandsdaten.getProdukt());
    if (lvKonditionen != null)
    {
      ivKondDaten.setKondkey("1");
      if (!lvKonditionen.getTilgbeg().isEmpty()) {
        ivKondDaten.setEnddat(
            DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvKonditionen.getTilgbeg())));
      }
      else
      {
        ivKondDaten.setEnddat(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvKonditionen.getFaellig())));
      }
      ivKondDaten.setAtkonv("1"); // Konstant 'Danach'(1)
      ivKondDaten.setAtkonvmod("0"); // Erst einmal Konstant '0' -> Problem MAVIS-Produkttyp
      ivKondDaten.setAtkonvtag("0"); // Konstant '0'
      ivKondDaten.setBankkal("DE");  // Konstant 'DE'
      ivKondDaten.setBernom(lvKonditionen.getBernom().replace("," , "."));
      //ivKondDaten.setSpread(ivGattung.getGD808C());
      //ivKondDaten.setCap(ivGattung.getGD804E());
      if (ivBestandsdaten.getAktivPassiv().equals("P")) // nur Passiv
      {
        if (!lvKonditionen.getDatltztanp().equals("0"))
        {
          ivKondDaten.setDatltztfix(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvKonditionen.getDatltztanp())));
        }
      }

      //ivKondDaten.setFloor(ivGattung.getGD803E());

      ivKondDaten.setFaellig(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvKonditionen.getFaellig())));

      ivKondDaten.setFixkalart("1"); // Konstant 'Banktage'(1)
      ivKondDaten.setFixtagedir("2"); // Konstant 'Davor'(2)
      ivKondDaten.setFixtagemod("0"); // Konstant '0'
      ivKondDaten.setKalfix("DE"); // Konstant 'DE'
      ivKondDaten.setKalkonv(lvKonditionen.getKalkonv());

      ivKondDaten.setLrate(ivListeSummenbetraege.getSummenbetraege(ivBestandsdaten.getISIN()).getLeistungsrate().toString());
      ivKondDaten.setMonendkonv("1"); // Konstant 'gleich'(1)
      ivKondDaten.setMantilg("1"); // Cashflows werden angeliefert - Aenderung zur alten Schnittstelle
      ivKondDaten.setManzins("1"); // Cashflows werden angeliefert - Aenderung zur alten Schnittstelle
      ivKondDaten.setNomzins(lvKonditionen.getNomzins().replace("," , "."));
      ivKondDaten.setTilgbeg(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvKonditionen.getTilgbeg())));

      //ivKondDaten.setTilgdat(ivGattung.getGD910());
      ivKondDaten.setTilgperkonv("1"); // Konstant 'anchor'(1)

      // Tilgungsrythmus defaultmaessig auf 'bullet' (0) setzen - CT 11.08.2015
      //if (pvBestandsdaten.getAktivPassiv().equals("2"))
      //{
      ivKondDaten.setTilgryth("0");
      //}
    	/* else
    	{
      		// GD842     	TXS-Wert
    		// 1            12
    		// 3            6
    		// 4            3
    		// A            1
    		// <> blank     13

    		// Defaultwert '0'
    		this.ivTilgryth = "0";
    		if (!(pvGattung.getGD842().equals("1") || pvGattung.getGD842().equals("3") || pvGattung.getGD842().equals("4") || pvGattung.getGD842().equals("A")))
    		{
    			this.ivTilgryth = "13";
    		}
    		else
    		{
    		  if (pvGattung.getGD842().equals("1"))
    		  {
    			this.ivTilgryth = "12";
    		  }
    		  if (pvGattung.getGD842().equals("3"))
    		  {
    			this.ivTilgryth = "6";
    		  }
    		  if (pvGattung.getGD842().equals("4"))
    		  {
    			this.ivTilgryth = "3";
    		  }
    		  if (pvGattung.getGD842().equals("A"))
    		  {
    			this.ivTilgryth = "1";
    		  }
    		}
    	} */
      ////this.ivTilgsatz = "0";
      ////if (pvGattung.getGD841().equals("3") || pvGattung.getGD841().equals("4") || pvGattung.getGD841().equals("E") || pvGattung.getGD841().equals("B"))
      ////{
      ////	this.ivTilgsatz = "100";
      ////}
      ivKondDaten.setTilgsatz(lvKonditionen.getTilgsatz().replace(",", "."));

      ivKondDaten.setZahltyp(lvKonditionen.getZahltyp());
      //ivKondDaten.setZinsabw(MappingWertpapiere.ermittleZinsabweichung(ivGattung.getGD321()));
      ivKondDaten.setZinsbeg(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvKonditionen.getZinsbeg())));
      ivKondDaten.setZinsdat(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvKonditionen.getZinsdat())));
      //ivKondDaten.setZinsenddat(ivGattung.getGD910());
      ivKondDaten.setZinsperkonv("1"); // Konstant 'anchor'(1)
      ivKondDaten.setZinsryth(lvKonditionen.getZinsryth());
      ivKondDaten.setZinstyp(lvKonditionen.getZinstyp());
      //ivKondDaten.setZinszahlart(MappingWertpapiere.ermittleZinszahlart(ivGattung.getGD805(), ivGattung.getGD312()));
      ivKondDaten.setZinsfak("1"); // Konstant '1'
      ivKondDaten.setVzinsdat("0"); // Konstant '0'
      ivKondDaten.setVztilgdat("0"); // Konstant '0'
      ivKondDaten.setWhrg(lvKonditionen.getWhrg());
    }

    if (lvOkayWertpapier)
    {

      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Aktiv - Slice
      {
        LOGGER_WERTPAPIERE_DH.info("Wertpapier " + ivBestandsdaten.getProdukt() + " - Ausplatzierungsmerkmal " + ivBestandsdaten.getAusplatzierungsmerkmal());

        if (!(ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("H") || ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("K")))
        {
          LOGGER_WERTPAPIERE_DH.error("Wertpapier " + ivBestandsdaten.getISIN() + " - Ungueltiges Ausplatzierungsmerkmal " + ivBestandsdaten.getAusplatzierungsmerkmal());
        }
        else
        {
          // 18.11.2016 - Slice-Typ ueber das Ausplatzierungsmerkmal ermitteln
          // Hypothekenpfandbrief
          if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("H"))
          {
            ivSliceInDaten.setKey(ivBestandsdaten.getProdukt() + WPSuffix
                .getSuffix(ivBestandsdaten.getAusplatzierungsmerkmal(), LOGGER_WERTPAPIERE_DH));
            ivSliceInDaten.setTx("Hypothekenpfandbrief");
            ivSliceInDaten.setPool("Hypothekenpfandbrief");
            ivSliceInDaten.setPrj("NLB-PfandBG");
          }

          // Oeffentlicher Pfandbrief
          if (ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("K"))
          {
            ivSliceInDaten.setKey(ivBestandsdaten.getProdukt() + WPSuffix.getSuffix(ivBestandsdaten.getAusplatzierungsmerkmal(), LOGGER_WERTPAPIERE_DH));
            ivSliceInDaten.setTx("&#214;ffentlicher Pfandbrief");
            ivSliceInDaten.setPool("&#214;ffentlicher Pfandbrief");
            ivSliceInDaten.setPrj("NLB-PfandBG");
          }

          ivSliceInDaten.setAktivpassiv("1"); // Slice immer Aktiv

          // Erst einmal Deckungstyp auf "1" setzen - CT 13.01.2012
          // 1 -> Ordentl. gattungsklassische Deckung
          // 2 -> Sichernde Ueberdeckung
          // 3 -> Weitere Deckung

          ivSliceInDaten.setDecktyp("1");
          if (ivBestandsdaten.getAusplatzierungsmerkmal().endsWith("2") || ivBestandsdaten.getAusplatzierungsmerkmal().equals("O4"))
          {
            ivSliceInDaten.setDecktyp("2");
          }
          else if (!ivBestandsdaten.getAusplatzierungsmerkmal().startsWith("O") &&
              (ivBestandsdaten.getAusplatzierungsmerkmal().endsWith("3") || ivBestandsdaten.getAusplatzierungsmerkmal().endsWith("4")))
          {
            ivSliceInDaten.setDecktyp("3");
          }

          ivSliceInDaten.setVon("0.0");
          ivSliceInDaten.setBis(ivBestandsdaten.getNominalbetrag());

          if (StringKonverter.convertString2Double(ivSliceInDaten.getBis()) > 0.0)
          {
            ivSliceInDaten.setNbetrag(ivSliceInDaten.getBis());
          }
          else
          {
            ivSliceInDaten.setNbetrag("0.0");
          }

          // CT 20.12.2011 - NurLiq-Kennzeichen setzen
          // Defaultmaessig erst einmal auf "0" (false) setzen
          ivSliceInDaten.setNliqui("0");
          if (ivBestandsdaten.getAusplatzierungsmerkmal().endsWith("4"))
          {
            if (!ivBestandsdaten.getAusplatzierungsmerkmal().equals("O4")) // OEPG - 'O4' nicht
            {
              ivSliceInDaten.setNliqui("1");
            }
          }

          ivSliceInDaten.setWhrg(ivBestandsdaten.getNominalbetragWaehrung());
        }
      }

      if (ivBestandsdaten.getAktivPassiv().equals("P")) // Passiv - Wertpapierposition
      {
        ivWertpapierposition.setWpposkey(ivBestandsdaten.getISIN() + "_" + ivBestandsdaten.getProdukt());
        ivWertpapierposition.setLfdnr(ivBestandsdaten.getProdukt());
        ivWertpapierposition.setWhrg(ivBestandsdaten.getNominalbetragWaehrung());
        ivWertpapierposition.setNbetrag(ivBestandsdaten.getNominalbetrag());
        ivWertpapierposition.setRkapi(ivBestandsdaten.getNominalbetrag());
       }
    }

    if (lvOkayWertpapier)
    {
      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      {
        Kunde lvKundeDH = ivListeKunden.get(ivBestandsdaten.getKundennummer());
        if (lvKundeDH != null) {
          ivKredkunde.setKdnr(lvKundeDH.getKundennummer());
          ivKredkunde.setOrg("25010600");
          ivKredkunde.setQuelle("KUNDE");
          ivKredkunde.setRolle("0");

          ivPersdaten.setNname(String2XML.change2XML(lvKundeDH.getNname()));
          ivPersdaten.setLand(lvKundeDH.getLand());
          ivPersdaten.setStr(String2XML.change2XML(lvKundeDH.getStr()));
          if (String2XML.change2XML(lvKundeDH.getHausnummer()).length() > 10)
          {
            ivPersdaten.setHausnr(String2XML.change2XML(lvKundeDH.getHausnummer()).substring(0, 9));
          }
          else {
            ivPersdaten.setHausnr(String2XML.change2XML(lvKundeDH.getHausnummer()));
          }
          ivPersdaten.setPlz(lvKundeDH.getPlz());
          ivPersdaten.setOrt(String2XML.change2XML(lvKundeDH.getOrt()));
          ivPersdaten.setKusyma(lvKundeDH.getKusyma());
          ivPersdaten.setKugr(String2XML.change2XML(ValueMapping.changeKundengruppe(ermittleKundengruppe(lvKundeDH.getKusyma()))));
          ivPersdaten.setRatingID(String2XML.change2XML(lvKundeDH.getRating_id()));
          if (String2XML.change2XML(lvKundeDH.getRating_id()).length() > 10) {
            ivPersdaten.setRatingID(String2XML.change2XML(lvKundeDH.getRating_id()).substring(0, 9));
          }
          ivPersdaten.setRatingMaster(String2XML.change2XML(lvKundeDH.getRating_master()));
          if (String2XML.change2XML(lvKundeDH.getRating_master()).length() > 40) {
            ivPersdaten.setRatingMaster(String2XML.change2XML(lvKundeDH.getRating_master()).substring(0, 39));
          }
          ivPersdaten.setRatingDatum(DatumUtilities.changeDatePoints(String2XML.change2XML(lvKundeDH.getRating_datum())));
          ivPersdaten.setRatingToolID(String2XML.change2XML(lvKundeDH.getRating_tool_id()));
          ivPersdaten.setRatingTool(String2XML.change2XML(lvKundeDH.getRating_tool()));

          // CT 10.03.2021 - DH liefert kein Gebiet, deshalb selbst ermitteln
          if (ivPersdaten.getLand().equals("DE"))
          {
            String lvGebiet = "DE_";
            int lvRet_gebiet = ValueMapping.PLZ2Land("009", lvKundeDH.getPlz());
            if (lvRet_gebiet < 10)
            {
              lvGebiet = lvGebiet + "0";
            }
            //if (ret_gebiet < 100)
            //{
            //  gebiet = gebiet + "0";
            //}
            lvGebiet = lvGebiet + lvRet_gebiet;

            ivPersdaten.setGebiet(lvGebiet);
          }

        }
      }
    }

    // Daten in CashflowQuellsystem-Datei schreiben
    try
    {
      ivFosCashflowQuellsystem.write((ivBestandsdaten.getKontonummer() + ";" + ivFinanzgeschaeft.getKey() + ";" + ivFinanzgeschaeft.getOriginator() + ";" +
          ivFinanzgeschaeft.getQuelle() + ";;;;;;;;;;;" + StringKonverter.lineSeparator).getBytes());
    }
    catch (Exception e)
    {
      LOGGER_WERTPAPIERE_DH.error("Fehler bei der Ausgabe in die CashflowQuellsystem-Datei");
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

      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      {
        ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionEnde());
      }

      if (ivBestandsdaten.getAktivPassiv().equals("P")) // Nur Passiv
      {
        // Wertpapierposition
        ivOutputDarlehenXML.printTransaktion(ivWertpapierposition.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(ivWertpapierposition.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(ivWertpapierposition.printTXSTransaktionEnde());
      }

      if (ivBestandsdaten.getAktivPassiv().equals("A")) // Nur Aktiv
      {
        if (!ivKredkunde.getKdnr().isEmpty()) {
          ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionDaten());

          ivOutputDarlehenXML.printTransaktion(ivPersdaten.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(ivPersdaten.printTXSTransaktionDaten());
          ivOutputDarlehenXML.printTransaktion(ivPersdaten.printTXSTransaktionEnde());

          ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionEnde());
        }
      }

      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
    }
  }

  /**
   * Ermittlung der Kundengruppe ueber die Kusyma
   * @param pvKusyma Kusyma
   * @return
   */
  private String ermittleKundengruppe(String pvKusyma)
  {
    // Kundengruppe aus Kusyma ermitteln
    String lvKGruppe = new String();

    lvKGruppe = "H"; // Default

    if (pvKusyma.startsWith("0"))
    { // Inlaendische Kreditinstitute
      lvKGruppe = "G";
    }
    if (pvKusyma.startsWith("1"))
    { // Inlaendische Oeffentliche Haushalte
      if (pvKusyma.equals("10000000"))
      { // Bund
        lvKGruppe = "B_1";
      }
      else
      { // Nicht direkt Bund
        if (pvKusyma.equals("11000000") ||
            pvKusyma.equals("12000000"))
        { // Bundeslaender
          lvKGruppe = "C";
        }
        else
        { // keine Bundeslaender
          if (pvKusyma.equals("13000000") ||
              pvKusyma.equals("14000000"))
          { // Gemeinden
            lvKGruppe = "D";
          }
          else
          { // keine Gemeinden
            if (pvKusyma.startsWith("15") ||
                pvKusyma.startsWith("16") ||
                pvKusyma.startsWith("17") ||
                pvKusyma.startsWith("19"))
            { // Oefftl.Untern/Zweck.
              lvKGruppe = "E";
            }
            else
            { // Keine Oefftl.Unternehmen/Zweckverbaende
              if (pvKusyma.equals("10000001") ||
                  pvKusyma.startsWith("18"))
              { // Andere durch Bund abgesicherte
                lvKGruppe = "B_2";
              }
            }
          }
        }
      }
    }

    if (pvKusyma.startsWith("4"))
    { // Inlaendische Unternehmen...
      if (pvKusyma.charAt(1) == '0' || pvKusyma.charAt(1) == '1' ||
          pvKusyma.charAt(1) == '2' || pvKusyma.charAt(1) == '3' ||
          pvKusyma.charAt(1) == '4' || pvKusyma.charAt(1) == '5')
      { // mehrheitlich Oeffentlich
        lvKGruppe = "F";
      }
    }
    if (pvKusyma.startsWith("5"))
    { // Auslaendische KI
      if (pvKusyma.charAt(1) == '0' || pvKusyma.charAt(1) == '1' ||
          pvKusyma.charAt(1) == '2' || pvKusyma.charAt(1) == '3')
      { // Internationale Institutionen
        lvKGruppe = "A_2";
      }
    }
    if (pvKusyma.startsWith("6"))
    { // Auslaendische Oeffentliche Haushalte
      if (pvKusyma.charAt(1) == '0')
      { // Zentralregierung
        lvKGruppe = "A_1";
      }
      else
      { // Keine Zentralregierung
        if (pvKusyma.charAt(1) == '2' || pvKusyma.charAt(1) == '4')
        { // Laender/Sonstige...
          lvKGruppe = "A_3";
        }
        else
        { // Keine Laender...
          if (pvKusyma.charAt(1) == '5' || pvKusyma.charAt(1) == '6')
          { // Andere...
            lvKGruppe = "A_6";
          }
          else
          { // Keine Anderen
            if (pvKusyma.charAt(1) == '3')
            { // Gemeinden .....
              lvKGruppe = "A_4";
            }
          }
        }
      }
    }

    return lvKGruppe;
  }
}
