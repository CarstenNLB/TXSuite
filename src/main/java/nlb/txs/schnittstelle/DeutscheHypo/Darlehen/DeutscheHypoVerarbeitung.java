/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.DeutscheHypo.Darlehen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten.Deckung;
import nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten.Finanzgeschaeft;
import nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten.Grundbuch;
import nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten.Konditionen;
import nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten.Kunde;
import nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten.Mapping;
import nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten.Pfandobjekt;
import nlb.txs.schnittstelle.DeutscheHypo.Darlehen.Daten.Sicherheiten;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Sicherheiten.Sicherheiten2Pfandbrief;
import nlb.txs.schnittstelle.Sicherheiten.SicherheitenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSBestandsverzDaten;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSPersonenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSPfandobjektDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitPerson;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitVerzeichnis;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisBestandsverz;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisDaten;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisPfandobjekt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisVBlatt;
import nlb.txs.schnittstelle.Transaktion.TXSVerzeichnisblattDaten;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingDH;
import nlb.txs.schnittstelle.Utilities.String2XML;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import org.apache.log4j.Logger;

public class DeutscheHypoVerarbeitung
{
  // Logger fuer DeutscheHypo-Verarbeitung
  private static Logger LOGGER_DH = Logger.getLogger("TXSDHLogger");

  /**
   * Institutsnummer
   */
  private String ivInstitutsnummer;

  /**
   * Buchungsdatum
   */
  private String ivBuchungsdatum;

  /**
   * Import-Verzeichnis der AZ6-Datei
   */
  private String ivImportVerzeichnis;

  /**
   * Export-Verzeichnis der TXS-Importdatei
   */
  private String ivExportVerzeichnis;

  /**
   * Dateiname der Deckung-Datei
   */
  private String ivDeckungDatei;

  /**
   * Dateiname der Finanzgeschaefte-Datei
   */
  private String ivFinanzgeschaefteDatei;

  /**
   * Dateiname der FinanzgeschaefteKD-Datei
   */
  private String ivFinanzgeschaefteKDDatei;

  /**
   * Dateiname der Grundbuecher-Datei
   */
  private String ivGrundbuchDatei;

  /**
   * Dateiname der Konditionen-Datei
   */
  private String ivKonditionenDatei;

  /**
   * Dateiname der Kunden-Datei
   */
  private String ivKundenDatei;

  /**
   * Dateiname der Mapping-Datei
   */
  private String ivMappingDatei;

  /**
   * Dateiname der Pfandobjekt-Datei
   */
  private String ivPfandobjektDatei;

  /**
   * Dateiname der Sicherheiten-Datei
   */
  private String ivSicherheitenDatei;

  /**
   * Dateiname der TXS-Importdatei
   */
  private String ivDHOutputDatei;

  /**
   * Import-Verzeichnis der Sicherheiten-Datei
   */
  private String ivImportVerzeichnisSAPCMS;

  /**
   * Dateiname der Sicherheiten-Datei
   */
  private String ivSapcmsDatei;

  /**
   * Sicherheiten-Daten
   */
  private SicherheitenDaten ivSicherheitenDaten;

  /**
   * Dateiname der CashflowQuellsystem-Datei
   */
  private String ivCashflowQuellsystemDatei;

  /**
   * FileOutputStream fuer CashflowQuellsystem-Datei
   */
  private FileOutputStream ivFosCashflowQuellsystem;

  /**
   * Ausgabedatei der TXS-Importdaten
   */
  private OutputDarlehenXML ivOutputDarlehenXML;

  // Listen der einzelnen Eingabedateien/-tabellen
  private HashMap<String, Deckung> ivListeDeckung;
  private HashMap<String, Finanzgeschaeft> ivListeFinanzgeschaefte;
  private HashMap<String, Finanzgeschaeft> ivListeFinanzgeschaefteKD;
  private HashMap<String, Grundbuch> ivListeGrundbuch;
  private HashMap<String, Konditionen> ivListeKonditionen;
  private HashMap<String, Kunde> ivListeKunden;
  private HashMap<String, Mapping> ivListeMapping;
  private HashMap<String, Pfandobjekt> ivListePfandobjekt;
  private HashMap<String, Sicherheiten> ivListeSicherheiten;

  // Zaehlervariablen
  private int ivZaehlerDeckung = 0;
  private int ivZaehlerFinanzgeschaefte = 0;
  private int ivZaehlerFinanzgeschaefteKD = 0;
  private int ivZaehlerGrundbuch = 0;
  private int ivZaehlerKonditionen = 0;
  private int ivZaehlerKunde = 0;
  private int ivZaehlerMapping = 0;
  private int ivZaehlerPfandobjekt = 0;
  private int ivZaehlerSicherheiten = 0;

  /**
   * Konstruktor
   * @param pvReader
   */
  public DeutscheHypoVerarbeitung(IniReader pvReader)
  {
    if (pvReader != null)
    {
      ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
      if (ivInstitutsnummer.equals("Fehler"))
      {
        LOGGER_DH.error("Keine Institutsnummer in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("Institutsnummer: " + ivInstitutsnummer);
      }

      ivImportVerzeichnis = pvReader.getPropertyString("DH", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("ImportVerzeichnis: " + ivImportVerzeichnis);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("DH", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("ExportVerzeichnis: " + ivExportVerzeichnis);
      }

      ivDeckungDatei =  pvReader.getPropertyString("DH", "Deckung-Datei", "Fehler");
      if (ivDeckungDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Deckung-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("Deckung-Datei: " + ivDeckungDatei);
      }

      ivFinanzgeschaefteDatei =  pvReader.getPropertyString("DH", "Finanzgeschaefte-Datei", "Fehler");
      if (ivFinanzgeschaefteDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Finanzgeschaefte-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("Finanzgeschaefte-Datei: " + ivFinanzgeschaefteDatei);
      }

      ivFinanzgeschaefteKDDatei =  pvReader.getPropertyString("DH", "FinanzgeschaefteKD-Datei", "Fehler");
      if (ivFinanzgeschaefteKDDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein FinanzgeschaefteKD-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("FinanzgeschaefteKD-Datei: " + ivFinanzgeschaefteKDDatei);
      }

      ivGrundbuchDatei =  pvReader.getPropertyString("DH", "Grundbuch-Datei", "Fehler");
      if (ivGrundbuchDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Grundbuch-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("Grundbuch-Datei: " + ivGrundbuchDatei);
      }

      ivKonditionenDatei =  pvReader.getPropertyString("DH", "Konditionen-Datei", "Fehler");
      if (ivKonditionenDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Konditionen-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("Konditionen-Datei: " + ivKonditionenDatei);
      }

      ivKundenDatei =  pvReader.getPropertyString("DH", "Kunden-Datei", "Fehler");
      if (ivKundenDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Kunden-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("Kunden-Datei: " + ivKundenDatei);
      }

      ivMappingDatei =  pvReader.getPropertyString("DH", "Mapping-Datei", "Fehler");
      if (ivMappingDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Mapping-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("Mapping-Datei: " + ivMappingDatei);
      }

      ivPfandobjektDatei =  pvReader.getPropertyString("DH", "Pfandobjekt-Datei", "Fehler");
      if (ivPfandobjektDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Pfandobjekt-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("Pfandobjekt-Datei: " + ivPfandobjektDatei);
      }

      ivSicherheitenDatei =  pvReader.getPropertyString("DH", "Sicherheiten-Datei", "Fehler");
      if (ivSicherheitenDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Sicherheiten-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("Sicherheiten-Datei: " + ivSicherheitenDatei);
      }

      ivDHOutputDatei =  pvReader.getPropertyString("DH", "DHOutput-Datei", "Fehler");
      if (ivDHOutputDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein DHOutput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("DHOutput-Datei: " + ivDHOutputDatei);
      }

      ivCashflowQuellsystemDatei = pvReader.getPropertyString("DH", "Quellsystem-Datei", "Fehler");
      if (ivCashflowQuellsystemDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein Cashflow-Quellsystem-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("Cashflow Quelsystem-Datei: " + ivCashflowQuellsystemDatei);
      }

      ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("SAPCMS", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
      {
        LOGGER_DH.error("Kein SAPCMS Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("SAPCMS ImportVerzeichnis: " + ivImportVerzeichnisSAPCMS);
      }

      ivSapcmsDatei = pvReader.getPropertyString("SAPCMS", "SAPCMS-Datei", "Fehler");
      if (ivSapcmsDatei.equals("Fehler"))
      {
        LOGGER_DH.error("Kein SAPCMS-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("SAPCMS-Datei: " + ivSapcmsDatei);
      }

      String lvDaypointerFileout = pvReader.getPropertyString("Konfiguration", "DPFILE", "Fehler");
      if (lvDaypointerFileout.equals("Fehler"))
      {
        LOGGER_DH.error("Kein [Konfiguration][DPFILE] in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_DH.info("DPFILE=" + lvDaypointerFileout);
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
          LOGGER_DH.info("Konnte Datei '" + lvDaypointerFileout + "' nicht oeffnen!");
        }
        try
        {
          ivBuchungsdatum = lvDaypointerBr.readLine();
        }
        catch (Exception e)
        {
          LOGGER_DH.error("Fehler beim Verarbeiten:" + ivBuchungsdatum);
          e.printStackTrace();
        }
        try
        {
          lvDaypointerBr.close();
          lvDaypointerFis.close();
        }
        catch (Exception e)
        {
          LOGGER_DH.error("Konnte Datei '" + lvDaypointerFileout + "' nicht schliessen!");
        }
      }
      else
      {
        LOGGER_DH.info("Keine Datei '" + lvDaypointerFileout + "' gefunden...");
      }
      LOGGER_DH.info("Buchungsdatum: " + ivBuchungsdatum);

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
    // Cashflow-Quellsystem oeffnen (zum Schreiben)
    File lvFileCashflowQuellsystem = new File(ivExportVerzeichnis + "\\" + ivCashflowQuellsystemDatei);
    try
    {
      ivFosCashflowQuellsystem = new FileOutputStream(lvFileCashflowQuellsystem);
    }
    catch (Exception e)
    {
      LOGGER_DH.error("Konnte CashflowQuellsystem-Datei nicht oeffnen!");
    }
    // Cashflow-Quellsystem oeffnen (zum Schreiben)

    ivListeDeckung = new HashMap<String, Deckung>();
    ivListeFinanzgeschaefte = new HashMap<String, Finanzgeschaeft>();
    ivListeFinanzgeschaefteKD = new HashMap<String, Finanzgeschaeft>();
    ivListeGrundbuch = new HashMap<String, Grundbuch>();
    ivListeKonditionen = new HashMap<String, Konditionen>();
    ivListeKunden = new HashMap<String, Kunde>();
    ivListeMapping = new HashMap<String, Mapping>();
    ivListePfandobjekt = new HashMap<String, Pfandobjekt>();
    ivListeSicherheiten = new HashMap<String, Sicherheiten>();

    // SAP CMS-Daten einlesen
    ivSicherheitenDaten = new SicherheitenDaten(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, SicherheitenDaten.SAPCMS, LOGGER_DH);

    // Darlehen XML-Datei im TXS-Format
    ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivDHOutputDatei, LOGGER_DH);
    ivOutputDarlehenXML.openXML();
    ivOutputDarlehenXML.printXMLStart();
    ivOutputDarlehenXML.printTXSImportDatenStart();
    ivOutputDarlehenXML.printTXSHeader(ivBuchungsdatum);

    // Deckungsdaten einlesen
    ivZaehlerDeckung = Deckung.readDeckung(ivImportVerzeichnis + "\\" + ivDeckungDatei, ivListeDeckung, LOGGER_DH);
    // Finanzgeschaefte einlesen
    ivZaehlerFinanzgeschaefte = Finanzgeschaeft.readFinanzgeschaefte(ivImportVerzeichnis + "\\" + ivFinanzgeschaefteDatei, ivListeFinanzgeschaefte, LOGGER_DH);
    // FinanzgeschaefteKD einlesen
    ivZaehlerFinanzgeschaefteKD = Finanzgeschaeft.readFinanzgeschaefte(ivImportVerzeichnis + "\\" + ivFinanzgeschaefteKDDatei,  ivListeFinanzgeschaefteKD, LOGGER_DH);
    // Grundbuecher einlesen
    ivZaehlerGrundbuch = Grundbuch.readGrundbuch(ivImportVerzeichnis + "\\" + ivGrundbuchDatei, ivListeGrundbuch, LOGGER_DH);
    // Konditionen einlesen
    ivZaehlerKonditionen = Konditionen.readKonditionen(ivImportVerzeichnis + "\\" + ivKonditionenDatei, ivListeKonditionen, LOGGER_DH);
    // Kunden einlesen
    ivZaehlerKunde = Kunde.readKunden(ivImportVerzeichnis + "\\" + ivKundenDatei, ivListeKunden, LOGGER_DH);
    // Mapping einlesen
    ivZaehlerMapping = Mapping.readMapping(ivImportVerzeichnis + "\\" + ivMappingDatei, ivListeMapping, LOGGER_DH);
    // Pfandobjekt einlesen
    ivZaehlerPfandobjekt = Pfandobjekt.readPfandobjekt(ivImportVerzeichnis + "\\" + ivPfandobjektDatei, ivListePfandobjekt, LOGGER_DH);
    // Sicherheiten einlesen
    ivZaehlerSicherheiten = Sicherheiten.readSicherheiten(ivImportVerzeichnis + "\\" + ivSicherheitenDatei, ivListeSicherheiten, LOGGER_DH);

    importDarlehen2Transaktion(ivOutputDarlehenXML);
    importDarlehenKD2Transaktion(ivOutputDarlehenXML);

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    // Cashflow-Quellsystem schliessen
    try
    {
      ivFosCashflowQuellsystem.close();
    }
    catch (Exception e)
    {
      LOGGER_DH.error("Fehler beim Schliessen der CashflowQuellsystem-Datei");
    }
    // Cashflow-Quellsystem schliessen

    // Statistik ausgeben
    LOGGER_DH.info(getStatistik());
  }

  /**
   * Importiert die DH-Informationen in die TXS-Transaktionen
   * @param pvOutputDarlehenXML
   */
  private void importDarlehen2Transaktion(OutputDarlehenXML pvOutputDarlehenXML)
  {

    // Transaktionen
    TXSFinanzgeschaeft lvFinanzgeschaeft = new TXSFinanzgeschaeft();
    TXSSliceInDaten lvSliceInDaten = new TXSSliceInDaten();
    TXSFinanzgeschaeftDaten lvFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
    TXSKonditionenDaten lvKondDaten = new TXSKonditionenDaten();
    TXSKreditKunde lvKredkunde = new TXSKreditKunde();
    TXSPersonenDaten lvPersdaten = new TXSPersonenDaten();

    TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
    TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
    TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
    TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
    TXSVerzeichnisVBlatt lvVevb = new TXSVerzeichnisVBlatt();
    TXSVerzeichnisblattDaten lvVbdaten = new TXSVerzeichnisblattDaten();
    TXSVerzeichnisBestandsverz lvVebv = new TXSVerzeichnisBestandsverz();
    TXSBestandsverzDaten lvBvdaten = new TXSBestandsverzDaten();
    TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
    TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();

    for (Finanzgeschaeft lvFinanzgeschaeftDH: ivListeFinanzgeschaefte.values())
    {
      LOGGER_DH.info("Verarbeite Kontonummer: " + lvFinanzgeschaeftDH.getKontonummer());

      lvFinanzgeschaeft = new TXSFinanzgeschaeft();
      lvSliceInDaten = new TXSSliceInDaten();
      lvFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
      lvKondDaten = new TXSKonditionenDaten();
      lvKredkunde = new TXSKreditKunde();
      lvPersdaten = new TXSPersonenDaten();

      lvKredsh = new TXSKreditSicherheit();
      lvShdaten = new TXSSicherheitDaten();
      lvShve = new TXSSicherheitVerzeichnis();
      lvVedaten = new TXSVerzeichnisDaten();
      lvVevb = new TXSVerzeichnisVBlatt();
      lvVbdaten = new TXSVerzeichnisblattDaten();
      lvVebv = new TXSVerzeichnisBestandsverz();
      lvBvdaten = new TXSBestandsverzDaten();
      lvVepo = new TXSVerzeichnisPfandobjekt();
      lvPodaten = new TXSPfandobjektDaten();

      // TXSFinanzgeschaeft
      lvFinanzgeschaeft.setKey(lvFinanzgeschaeftDH.getKontonummer());
      lvFinanzgeschaeft.setQuelle(lvFinanzgeschaeftDH.getQuelle());
      //lvFinanzgeschaeft.setOriginator(lvFinanzgeschaeftDH.getOrg());
      lvFinanzgeschaeft.setOriginator("25010600");

      // TXSFinanzgeschaeftDaten
      if (lvFinanzgeschaeftDH.getAktivpassiv().equals("A")) {
        lvFinanzgeschaeftDaten.setAktivpassiv("1");
      }
      lvFinanzgeschaeftDaten.setAusstat(lvFinanzgeschaeftDH.getAusstat());
      if (lvFinanzgeschaeftDH.getAuszdat().length() == 8) {
        lvFinanzgeschaeftDaten.setAuszdat(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvFinanzgeschaeftDH.getAuszdat())));
      }
      lvFinanzgeschaeftDaten.setAverpfl(lvFinanzgeschaeftDH.getAverpfl());
      lvFinanzgeschaeftDaten.setAz(lvFinanzgeschaeftDH.getKontonummer());
      lvFinanzgeschaeftDaten.setKat("1");
      lvFinanzgeschaeftDaten.setPfbrfrel("3");
      lvFinanzgeschaeftDaten.setNbetrag(lvFinanzgeschaeftDH.getNbetrag().replace(",", "."));
      lvFinanzgeschaeftDaten.setWhrg(lvFinanzgeschaeftDH.getWhrg());
      lvFinanzgeschaeftDaten.setVwhrg(lvFinanzgeschaeftDH.getVwhrg());
      lvFinanzgeschaeftDaten.setAbetrag(lvFinanzgeschaeftDH.getAbetrag().replace(",", "."));
      lvFinanzgeschaeftDaten.setRkapi(lvFinanzgeschaeftDH.getRkapi().replace(",", "."));
      if (lvFinanzgeschaeftDH.getRoll().equals("N")) {
        lvFinanzgeschaeftDaten.setRoll("0");
      }
      lvFinanzgeschaeftDaten.setTyp(lvFinanzgeschaeftDH.getTyp());
      if (lvFinanzgeschaeftDH.getZusdat().length() == 8) {
        lvFinanzgeschaeftDaten.setZusdat(DatumUtilities
            .changeDate(DatumUtilities.changeDatePoints(lvFinanzgeschaeftDH.getZusdat())));
      }
      if (lvFinanzgeschaeftDH.getKonskredit().equals("N"))
      {
        lvFinanzgeschaeftDaten.setKonskredit("0");
      }
      else {
        lvFinanzgeschaeftDaten.setKonskredit("1");
        lvFinanzgeschaeftDaten.setKonsfuehrer(lvFinanzgeschaeftDH.getKonsfuehrer());
      }

      // TXSKonditionen
      Konditionen lvKonditionenDH = ivListeKonditionen.get(lvFinanzgeschaeftDH.getKontonummer());
      if (lvKonditionenDH != null) {
        lvKondDaten.setKondkey("1");
        lvKondDaten.setAtkonv("1");
        lvKondDaten.setAtkonvtag("0");
        //lvKondDaten.setAtkonvmod(lvKonditionenDH.get);
        lvKondDaten.setBankkal("DE");
        lvKondDaten.setBernom(lvKonditionenDH.getBernom().replace(",", "."));
        lvKondDaten.setCap(lvKonditionenDH.getCap());
        if (lvKonditionenDH.getDatltztanp().length() == 8) {
          lvKondDaten.setDatltztanp(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getDatltztanp())));
        }

        lvKondDaten.setEnddat(DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getVfaellig())));
        lvKondDaten.setLrate(lvKonditionenDH.getLrate().replace(",", "."));
        if (lvKonditionenDH.getFaellig().length() == 8) {
          lvKondDaten.setFaellig(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getFaellig())));
        }
        lvKondDaten.setFixtage("0");
        lvKondDaten.setFixkalart("1");
        lvKondDaten.setFixkonv("1");
        lvKondDaten.setKalkonv(lvKonditionenDH.getKalkonv());
        lvKondDaten.setKalfix("DE");
        lvKondDaten.setMonendkonv("1");
        lvKondDaten.setManzins("1");
        lvKondDaten.setMantilg("1");
        lvKondDaten.setNomzins(lvKonditionenDH.getNomzins().replace(",", "."));
        lvKondDaten.setRefzins(MappingDH.changeRefzins(lvKonditionenDH.getRefzins(), LOGGER_DH));
        if (lvKonditionenDH.getTilgbeg().length() == 8) {
          lvKondDaten.setTilgbeg(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getTilgbeg())));
        }
        if (lvKonditionenDH.getTilgdat().length() == 8) {
          lvKondDaten.setTilgdat(lvKondDaten.getTilgbeg());
        }
        lvKondDaten.setTilgryth(lvKonditionenDH.getTilgryth());
        lvKondDaten.setTilgsatz(lvKonditionenDH.getTilgsatz().replace(",", "."));
        lvKondDaten.setTilgver("0");
        lvKondDaten.setVfaellig("0");
        lvKondDaten.setZahltyp(lvKonditionenDH.getZahltyp());
        lvKondDaten.setZinsryth(lvKonditionenDH.getZinsryth());
        lvKondDaten.setZinstyp(lvKonditionenDH.getZinstyp());
        if (lvKonditionenDH.getZinsbeg().length() == 8) {
          lvKondDaten.setZinsbeg(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getZinsbeg())));
        }
        if (lvKonditionenDH.getZinsdat().length() == 8) {
          lvKondDaten.setZinsdat(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getZinsdat())));
        }
        //lvKondDaten.setZinszahlart(lvKonditionenDH);
        lvKondDaten.setWhrg(lvKonditionenDH.getWhrg());
      }

      // TXSSlice
      Deckung lvDeckungDH = ivListeDeckung.get(lvFinanzgeschaeftDH.getKontonummer());
      if (lvDeckungDH != null) {
        lvSliceInDaten.setDecktyp(lvDeckungDH.getDecktyp());
        //lvSliceInDaten.setBis(lvDeckungDH.getBis().replace(",", "."));
        lvSliceInDaten.setKey(lvDeckungDH.getKey());
        lvSliceInDaten.setVon("0.0");
        lvSliceInDaten.setPool("Hypothekenpfandbrief");
        lvSliceInDaten.setTx("Hypothekenpfandbrief");
        lvSliceInDaten.setPrj("NLB-PfandBG");
        lvSliceInDaten.setTilgmod("1");
        lvSliceInDaten.setNbetrag(lvDeckungDH.getNbetrag().replace(",", "."));
        lvSliceInDaten.setNliqui("0");
        lvSliceInDaten.setWhrg(lvDeckungDH.getWhrg());
        if (lvDeckungDH.getAktivpassiv().equals("A")) {
          lvSliceInDaten.setAktivpassiv("1");
        }
      }

      // TXSKreditKunde
      Kunde lvKundeDH = ivListeKunden.get(lvFinanzgeschaeftDH.getKundennummer());
      if (lvKundeDH != null) {
        lvKredkunde.setKdnr(lvKundeDH.getKundennummer());
        lvKredkunde.setOrg("25010600");
        lvKredkunde.setQuelle("KUNDE");
        lvKredkunde.setRolle("0");

        lvPersdaten.setNname(String2XML.change2XML(lvKundeDH.getNname()));
        lvPersdaten.setLand(lvKundeDH.getLand());
        lvPersdaten.setStr(String2XML.change2XML(lvKundeDH.getStr()));
        if (String2XML.change2XML(lvKundeDH.getHausnummer()).length() > 10)
        {
          lvPersdaten.setHausnr(String2XML.change2XML(lvKundeDH.getHausnummer()).substring(0, 9));
        }
        else {
          lvPersdaten.setHausnr(String2XML.change2XML(lvKundeDH.getHausnummer()));
        }
        lvPersdaten.setPlz(lvKundeDH.getPlz());
        lvPersdaten.setOrt(String2XML.change2XML(lvKundeDH.getOrt()));
        lvPersdaten.setKusyma(lvKundeDH.getKusyma());
        lvPersdaten.setKugr(String2XML.change2XML(ValueMapping.changeKundengruppe(ermittleKundengruppe(lvKundeDH.getKusyma()))));
        lvPersdaten.setRatingID(String2XML.change2XML(lvKundeDH.getRating_id()));
        if (String2XML.change2XML(lvKundeDH.getRating_id()).length() > 10) {
          lvPersdaten.setRatingID(String2XML.change2XML(lvKundeDH.getRating_id()).substring(0, 9));
        }
        lvPersdaten.setRatingMaster(String2XML.change2XML(lvKundeDH.getRating_master()));
        if (String2XML.change2XML(lvKundeDH.getRating_master()).length() > 40) {
          lvPersdaten.setRatingMaster(String2XML.change2XML(lvKundeDH.getRating_master()).substring(0, 39));
        }
        if (lvKundeDH.getRating_datum().length() == 8) {
          lvPersdaten.setRatingDatum(
              DatumUtilities.changeDatePoints(lvKundeDH.getRating_datum()));
        }
        lvPersdaten.setRatingToolID(String2XML.change2XML(lvKundeDH.getRating_tool_id()));
        lvPersdaten.setRatingTool(String2XML.change2XML(lvKundeDH.getRating_tool()));

        // CT 10.03.2021 - DH liefert kein Gebiet, deshalb selbst ermitteln
        if (lvPersdaten.getLand().equals("DE"))
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

          lvPersdaten.setGebiet(lvGebiet);
        }
      }

      // Daten in CashflowQuellsystem-Datei schreiben
      try
      {
        ivFosCashflowQuellsystem.write((lvFinanzgeschaeftDH.getKontonummer() + ";" + lvFinanzgeschaeft.getKey() + ";" + lvFinanzgeschaeft.getOriginator() + ";" +
            lvFinanzgeschaeft.getQuelle() + ";;;;;;;;;;;" + StringKonverter.lineSeparator).getBytes());
      }
      catch (Exception e)
      {
        LOGGER_DH.error("Fehler bei der Ausgabe in die CashflowQuellsystem-Datei");
      }

      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionStart());

      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionEnde());

      // Sonderfall: Fuer diese drei Geschaefte die Sicherheiten aus SAP CMS nehmen
      if (lvFinanzgeschaeftDH.getKontonummer().equals("122412215") || lvFinanzgeschaeftDH.getKontonummer().equals("122412318") ||
          lvFinanzgeschaeftDH.getKontonummer().equals("122412411"))
      {
        LOGGER_DH.info("CMS Sicherheiten: " + lvFinanzgeschaeftDH.getKontonummer());
        Sicherheiten2Pfandbrief lvSicherheiten2Pfandbrief = new Sicherheiten2Pfandbrief(ivSicherheitenDaten, LOGGER_DH);
        pvOutputDarlehenXML.printTransaktion(lvSicherheiten2Pfandbrief.importSicherheitHypotheken("7100010234", lvFinanzgeschaeftDH.getKontonummer(), "80101699", "1", new String(), "ADHYPFBG", "009", null));
      }
      else {

        if (lvFinanzgeschaeftDH.getTyp().equals("1")) {
          for (Mapping lvMappingDH : ivListeMapping.values()) {

            if (lvMappingDH.getKontonummer().equals(lvFinanzgeschaeftDH.getKontonummer())) {

              Sicherheiten lvSicherheitDH = ivListeSicherheiten
                  .get(lvMappingDH.getSicherheitenNummer());
              if (lvSicherheitDH != null) {
                //TXSKreditSicherheit
                lvKredsh.setWhrg(lvFinanzgeschaeftDaten.getWhrg());
                lvKredsh.setOrg(lvFinanzgeschaeft.getOriginator());
                lvKredsh.setZuwbetrag(lvDeckungDH.getBis().replace(",", "."));
                lvKredsh.setKey(lvSicherheitDH.getSicherheitenNummer());
                lvKredsh.setQuelle(lvFinanzgeschaeft.getQuelle());

                // TXSSicherheitenDaten
                lvShdaten.setArt(lvSicherheitDH.getArt());
                lvShdaten.setVbetrag(lvSicherheitDH.getVbetrag());
                lvShdaten.setWhrg(lvSicherheitDH.getWhrg());
                lvShdaten.setNbetrag(lvSicherheitDH.getNbetrag());
                lvShdaten.setNbetrag(lvShdaten.getNbetrag());
                lvShdaten.setPbsatz(lvSicherheitDH.getPbsatz());
                // Sicherheit geprueft auf 'Ja' (1)
                lvShdaten.setGepr("1");

                // Transaktionen in die Datei schreiben
                // TXSKreditSicherheit
                pvOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionStart());
                pvOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionDaten());

                // TXSSicherheitDaten
                pvOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionStart());
                pvOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionDaten());
                pvOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionEnde());

                for (Grundbuch lvGrundbuchDH : ivListeGrundbuch.values()) {

                  if (lvGrundbuchDH.getSicherheitenNummer()
                      .equals(lvSicherheitDH.getSicherheitenNummer())) {

                    if (lvSicherheitDH.getArt().equals("19")) {
                      LOGGER_DH.info(
                          "Auslandsimmo - Sicherheitenart 19: " + lvFinanzgeschaeft.getKey() + " - "
                              + lvKredsh.getKey());
                      importGrundpfandrechtAuslandsimmo(pvOutputDarlehenXML, lvSicherheitDH,
                          lvGrundbuchDH, lvFinanzgeschaeftDH,
                          lvFinanzgeschaeft.getOriginator(), lvFinanzgeschaeft.getQuelle());
                    } else {
                      String lvHelpLfdnr = lvGrundbuchDH.getLfdnr();
                      if (lvGrundbuchDH.getLfdnr().contains("_")) {
                        lvHelpLfdnr = lvGrundbuchDH.getLfdnr()
                            .substring(0, lvGrundbuchDH.getLfdnr().indexOf("_"));
                      }

                      lvShve.setVenr(lvGrundbuchDH.getSicherheitenNummer() + "_" + lvGrundbuchDH
                          .getPfandobjektNummer() + "_" + lvHelpLfdnr);
                      lvShve.setOrg(lvFinanzgeschaeft.getOriginator());
                      lvShve.setQuelle(lvFinanzgeschaeft.getQuelle());

                      int lvAnzahl = 0;
                      HashMap<String, BigDecimal> lvTeilVbetrag = new HashMap<String, BigDecimal>();
                      for (Grundbuch lvHelpGrundbuchDH : ivListeGrundbuch.values()) {
                        if (lvHelpGrundbuchDH.getPfandobjektNummer()
                            .equals(lvGrundbuchDH.getPfandobjektNummer()) &&
                            lvHelpGrundbuchDH.getSicherheitenNummer()
                                .equals(lvGrundbuchDH.getSicherheitenNummer())) {
                          String lvHelpNr = lvHelpGrundbuchDH.getLfdnr();
                          if (lvHelpGrundbuchDH.getLfdnr().contains("_")) {
                            lvHelpNr = lvHelpGrundbuchDH.getLfdnr()
                                .substring(0, lvHelpGrundbuchDH.getLfdnr().indexOf("_"));
                          }

                          if (lvTeilVbetrag.containsKey(
                              lvHelpGrundbuchDH.getSicherheitenNummer() + "_" + lvHelpGrundbuchDH
                                  .getPfandobjektNummer() + "_" + lvHelpNr)) {
                            BigDecimal lvHelpVbetrag = lvTeilVbetrag.get(
                                lvHelpGrundbuchDH.getSicherheitenNummer() + "_" + lvHelpGrundbuchDH
                                    .getPfandobjektNummer() + "_" + lvHelpNr);
                            lvHelpVbetrag = lvHelpVbetrag.add(StringKonverter
                                .convertString2BigDecimal(lvHelpGrundbuchDH.getVbetrag()));
                            lvTeilVbetrag.remove(
                                lvHelpGrundbuchDH.getSicherheitenNummer() + "_" + lvHelpGrundbuchDH
                                    .getPfandobjektNummer() + "_" + lvHelpNr);
                            lvTeilVbetrag.put(
                                lvHelpGrundbuchDH.getSicherheitenNummer() + "_" + lvHelpGrundbuchDH
                                    .getPfandobjektNummer() + "_" + lvHelpNr, lvHelpVbetrag);
                          } else {
                            lvTeilVbetrag.put(
                                lvHelpGrundbuchDH.getSicherheitenNummer() + "_" + lvHelpGrundbuchDH
                                    .getPfandobjektNummer() + "_" + lvHelpNr, StringKonverter
                                    .convertString2BigDecimal(lvHelpGrundbuchDH.getVbetrag()));
                          }
                          lvAnzahl++;
                        }
                      }

                      if (lvAnzahl > 1) {
                        lvVedaten.setBetrag(lvTeilVbetrag.get(
                            lvGrundbuchDH.getSicherheitenNummer() + "_" + lvGrundbuchDH
                                .getPfandobjektNummer() + "_" + lvHelpLfdnr).toPlainString());
                        lvVedaten.setWhrg(lvSicherheitDH.getWhrg());
                      } else {
                        lvVedaten.setBetrag(lvGrundbuchDH.getVbetrag());
                        lvVedaten.setWhrg(lvGrundbuchDH.getGrundpfandrechtWaehrung());
                      }

                      lvVedaten.setGbart("1");
                      if (lvGrundbuchDH.getAbt().equals("III")) {
                        lvVedaten.setAbt("3");
                      }
                      // Erst einmal den Rang genommen - Wird noch geprueft - CT 15.03.2021
                      lvVedaten.setNrabt(lvGrundbuchDH.getRang());
                      lvVedaten.setKat("1");

                      lvVevb.setVbnr(lvGrundbuchDH.getSicherheitenNummer() + "_" + lvGrundbuchDH
                          .getPfandobjektNummer() + "_" + lvHelpLfdnr);
                      lvVevb.setOrg(lvFinanzgeschaeft.getOriginator());
                      lvVevb.setQuelle(lvFinanzgeschaeft.getQuelle());

                      lvVbdaten.setBlatt(lvGrundbuchDH.getBlatt());
                      lvVbdaten.setGbvon(lvGrundbuchDH.getGbvon());
                      lvVbdaten.setKat("1");
                      lvVbdaten.setBand(lvGrundbuchDH.getBand());
                      lvVbdaten.setGericht(lvGrundbuchDH.getGericht());

                      lvVebv.setBvnr(lvGrundbuchDH.getSicherheitenNummer() + "_" + lvGrundbuchDH
                          .getPfandobjektNummer() + "_" + lvGrundbuchDH.getLfdnr());
                      lvVebv.setOrg(lvFinanzgeschaeft.getOriginator());
                      lvVebv.setQuelle(lvFinanzgeschaeft.getQuelle());

                      //lvBvdaten.setGem(lvGrundbuchDH.getGem());
                      lvBvdaten.setFlur(lvGrundbuchDH.getFlur());
                      lvBvdaten.setFlurst(lvGrundbuchDH.getFlurst());
                      lvBvdaten.setLfdnr(lvGrundbuchDH.getLfdnr());

                      Pfandobjekt lvPfandobjektDH = ivListePfandobjekt
                          .get(lvGrundbuchDH.getPfandobjektNummer());
                      if (lvPfandobjektDH != null) {
                        lvVepo.setObjnr(lvPfandobjektDH.getPfandobjektNummer());
                        lvVepo.setOrg(lvFinanzgeschaeft.getOriginator());
                        lvVepo.setQuelle(lvFinanzgeschaeft.getQuelle());

                        lvPodaten.setEigtyp("1");
                        if (lvPfandobjektDH.getEigennutzung().equals("0")) {
                          lvPodaten.setEigtyp("2");
                        }

                        // Ertragsfaehigkeit setzen - CT 19.07.2021
                        // 11 -> voll ertragsfaehig (TXS -> 3)
                        // 12 -> Baugeld (unfertige und noch nicht ertragsfaehige Neubauten) (TXS -> 1)
                        // 13 -> Bauplatz (TXS -> 2)
                        // 14 -> Kein Mapping, das Feld bleibt leer.
                        if (lvFinanzgeschaeftDH.getDeckungsschluessel().equals("11"))
                        {
                          lvPodaten.setErtragsf("3");
                        }

                        if (lvFinanzgeschaeftDH.getDeckungsschluessel().equals("12"))
                        {
                          lvPodaten.setErtragsf("1");
                        }

                        if (lvFinanzgeschaeftDH.getDeckungsschluessel().equals("13"))
                        {
                          lvPodaten.setErtragsf("2");
                        }

                       // CT 10.03.2021 - DH liefert kein Gebiet, deshalb selbst ermitteln
                        if (lvPfandobjektDH.getLand().equals("DE")) {
                          String lvGebiet = "DE_";
                          int lvRet_gebiet = ValueMapping.PLZ2Land("009", lvPfandobjektDH.getPlz());
                          if (lvRet_gebiet < 10) {
                            lvGebiet = lvGebiet + "0";
                          }
                          //if (ret_gebiet < 100)
                          //{
                          //  gebiet = gebiet + "0";
                          //}
                          lvGebiet = lvGebiet + lvRet_gebiet;

                          lvPodaten.setGebiet(lvGebiet);
                        } else {
                          lvPodaten.setGebiet(
                              MappingDH.changeGebiet(lvPfandobjektDH.getGebiet(), LOGGER_DH));
                        }

                        lvPodaten.setJahr(lvPfandobjektDH.getJahr());
                        lvPodaten.setKat("1");
                        lvPodaten.setLand(lvPfandobjektDH.getLand());
                        lvPodaten.setSwert(lvPfandobjektDH.getSwert());
                        lvPodaten.setEwert(lvPfandobjektDH.getEwert());
                        lvPodaten.setBwert(lvPfandobjektDH.getBwert());
                        if (lvPfandobjektDH.getBwertdat().length() == 8) {
                          lvPodaten.setBwertdat(DatumUtilities.changeDate(
                              DatumUtilities.changeDatePoints(lvPfandobjektDH.getBwertdat())));
                        }
                        // Nutzungsart
                        lvPodaten.setNart(
                            MappingDH
                                .changeNutzungsart(lvPfandobjektDH.getNutzungsart(), LOGGER_DH));

                        // Objektgruppe
                        lvPodaten.setOgrp(MappingDH
                            .changeObjektgruppe(lvPfandobjektDH.getWirtschaftlich(), LOGGER_DH));
                        if (lvFinanzgeschaeftDH.getDeckungsschluessel().equals("12"))
                        {
                          lvPodaten.setOgrp("100099");
                        }

                        if (lvFinanzgeschaeftDH.getDeckungsschluessel().equals("13"))
                        {
                          lvPodaten.setOgrp("100022");
                        }

                        lvPodaten.setOrt(lvPfandobjektDH.getOrt());
                        lvPodaten.setPlz(lvPfandobjektDH.getPlz());
                        lvPodaten.setStr(lvPfandobjektDH.getStrhn());
                        lvPodaten.setZusatz(lvPfandobjektDH.getZusatz());
                        lvPodaten.setWhrg(lvPfandobjektDH.getWhrg());
                      }

                      if (lvPfandobjektDH != null &&
                          ((!lvPfandobjektDH.getLand().equals("DE")) && (
                              lvVbdaten.getBlatt().isEmpty() || lvVbdaten.getGbvon().isEmpty()))) {
                        LOGGER_DH.info(
                            "Auslandsimmo: " + lvFinanzgeschaeft.getKey() + " - " + lvKredsh
                                .getKey());
                        importGrundpfandrechtAuslandsimmo(pvOutputDarlehenXML, lvSicherheitDH,
                            lvGrundbuchDH, lvFinanzgeschaeftDH,
                            lvFinanzgeschaeft.getOriginator(), lvFinanzgeschaeft.getQuelle());
                      } else {
                        pvOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionStart());
                        pvOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionDaten());

                        pvOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionStart());
                        pvOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionDaten());
                        pvOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionEnde());

                        pvOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionStart());
                        pvOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionDaten());

                        pvOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionStart());
                        pvOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionDaten());
                        pvOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionEnde());

                        pvOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionEnde());

                        pvOutputDarlehenXML.printTransaktion(lvVebv.printTXSTransaktionStart());
                        pvOutputDarlehenXML.printTransaktion(lvVebv.printTXSTransaktionDaten());

                        pvOutputDarlehenXML.printTransaktion(lvBvdaten.printTXSTransaktionStart());
                        pvOutputDarlehenXML.printTransaktion(lvBvdaten.printTXSTransaktionDaten());
                        pvOutputDarlehenXML.printTransaktion(lvBvdaten.printTXSTransaktionEnde());

                        pvOutputDarlehenXML.printTransaktion(lvVebv.printTXSTransaktionEnde());

                        pvOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionStart());
                        pvOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionDaten());
                        pvOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionStart());
                        pvOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionDaten());
                        pvOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionEnde());

                        pvOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionEnde());

                        pvOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionEnde());
                      }
                    }
                  }
                }

                pvOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionEnde());
              }
            }
          }
        }
      }

      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionEnde());
    }
  }

  /**
   * Importiert die DH-Informationen in die TXS-Transaktionen
   * @param pvOutputDarlehenXML
   */
  private void importDarlehenKD2Transaktion(OutputDarlehenXML pvOutputDarlehenXML) {

    // Transaktionen
    TXSFinanzgeschaeft lvFinanzgeschaeft = new TXSFinanzgeschaeft();
    TXSSliceInDaten lvSliceInDaten = new TXSSliceInDaten();
    TXSFinanzgeschaeftDaten lvFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
    TXSKonditionenDaten lvKondDaten = new TXSKonditionenDaten();
    TXSKreditKunde lvKredkunde = new TXSKreditKunde();
    TXSPersonenDaten lvPersdaten = new TXSPersonenDaten();

    TXSKreditSicherheit lvKredsh = new TXSKreditSicherheit();
    TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
    TXSSicherheitPerson lvShperson = new TXSSicherheitPerson();

    for (Finanzgeschaeft lvFinanzgeschaeftDH : ivListeFinanzgeschaefteKD.values()) {
      LOGGER_DH.info("Verarbeite Kontonummer: " + lvFinanzgeschaeftDH.getKontonummer());

      lvFinanzgeschaeft = new TXSFinanzgeschaeft();
      lvSliceInDaten = new TXSSliceInDaten();
      lvFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
      lvKondDaten = new TXSKonditionenDaten();
      lvKredkunde = new TXSKreditKunde();
      lvPersdaten = new TXSPersonenDaten();

      lvKredsh = new TXSKreditSicherheit();
      lvShdaten = new TXSSicherheitDaten();
      lvShperson = new TXSSicherheitPerson();

      // TXSFinanzgeschaeft
      lvFinanzgeschaeft.setKey(lvFinanzgeschaeftDH.getKontonummer());
      lvFinanzgeschaeft.setQuelle(lvFinanzgeschaeftDH.getQuelle());
      //lvFinanzgeschaeft.setOriginator(lvFinanzgeschaeftDH.getOrg());
      lvFinanzgeschaeft.setOriginator("25010600");

      // TXSFinanzgeschaeftDaten
      if (lvFinanzgeschaeftDH.getAktivpassiv().equals("A")) {
        lvFinanzgeschaeftDaten.setAktivpassiv("1");
      }
      lvFinanzgeschaeftDaten.setAusstat(lvFinanzgeschaeftDH.getAusstat());
      if (lvFinanzgeschaeftDH.getAuszdat().length() == 8) {
        lvFinanzgeschaeftDaten.setAuszdat(DatumUtilities
            .changeDate(DatumUtilities.changeDatePoints(lvFinanzgeschaeftDH.getAuszdat())));
      }
      lvFinanzgeschaeftDaten.setAverpfl(lvFinanzgeschaeftDH.getAverpfl());
      lvFinanzgeschaeftDaten.setAz(lvFinanzgeschaeftDH.getKontonummer());
      lvFinanzgeschaeftDaten.setKat("1");
      lvFinanzgeschaeftDaten.setPfbrfrel("3");
      lvFinanzgeschaeftDaten.setNbetrag(lvFinanzgeschaeftDH.getNbetrag().replace(",", "."));
      lvFinanzgeschaeftDaten.setWhrg(lvFinanzgeschaeftDH.getWhrg());
      lvFinanzgeschaeftDaten.setVwhrg(lvFinanzgeschaeftDH.getVwhrg());
      lvFinanzgeschaeftDaten.setAbetrag(lvFinanzgeschaeftDH.getAbetrag().replace(",", "."));
      lvFinanzgeschaeftDaten.setRkapi(lvFinanzgeschaeftDH.getRkapi().replace(",", "."));
      if (lvFinanzgeschaeftDH.getRoll().equals("N")) {
        lvFinanzgeschaeftDaten.setRoll("0");
      }
      lvFinanzgeschaeftDaten.setTyp(lvFinanzgeschaeftDH.getTyp());
      if (lvFinanzgeschaeftDH.getZusdat().length() == 8) {
        lvFinanzgeschaeftDaten.setZusdat(DatumUtilities
            .changeDate(DatumUtilities.changeDatePoints(lvFinanzgeschaeftDH.getZusdat())));
      }
      if (lvFinanzgeschaeftDH.getKonskredit().equals("N")) {
        lvFinanzgeschaeftDaten.setKonskredit("0");
      } else {
        lvFinanzgeschaeftDaten.setKonskredit("1");
        lvFinanzgeschaeftDaten.setKonsfuehrer(lvFinanzgeschaeftDH.getKonsfuehrer());
      }

      // TXSKonditionen
      Konditionen lvKonditionenDH = ivListeKonditionen.get(lvFinanzgeschaeftDH.getKontonummer());
      if (lvKonditionenDH != null) {
        lvKondDaten.setKondkey("1");
        lvKondDaten.setAtkonv("1");
        lvKondDaten.setAtkonvtag("0");
        //lvKondDaten.setAtkonvmod(lvKonditionenDH.get);
        lvKondDaten.setBankkal("DE");
        lvKondDaten.setBernom(lvKonditionenDH.getBernom().replace(",", "."));
        lvKondDaten.setCap(lvKonditionenDH.getCap());
        if (lvKonditionenDH.getDatltztanp().length() == 8) {
          lvKondDaten.setDatltztanp(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getDatltztanp())));
        }
        //lvKondDaten.setDatltzttilg(lvKonditionenDH);
        if (lvKonditionenDH.getTilgbeg().length() == 8) {
          lvKondDaten.setEnddat(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getTilgbeg())));
        }
        lvKondDaten.setLrate(lvKonditionenDH.getLrate().replace(",", "."));
        if (lvKonditionenDH.getFaellig().length() == 8) {
          lvKondDaten.setFaellig(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getFaellig())));
        }
        lvKondDaten.setFixtage("0");
        lvKondDaten.setFixkalart("1");
        lvKondDaten.setFixkonv("1");
        lvKondDaten.setKalkonv(lvKonditionenDH.getKalkonv());
        lvKondDaten.setKalfix("DE");
        lvKondDaten.setMonendkonv("1");
        lvKondDaten.setManzins("1");
        lvKondDaten.setMantilg("1");
        lvKondDaten.setNomzins(lvKonditionenDH.getNomzins().replace(",", "."));
        lvKondDaten.setRefzins(MappingDH.changeRefzins(lvKonditionenDH.getRefzins(), LOGGER_DH));
        if (lvKonditionenDH.getTilgbeg().length() == 8) {
          lvKondDaten.setTilgbeg(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getTilgbeg())));
        }
        lvKondDaten.setTilgdat(lvKondDaten.getTilgbeg());
        lvKondDaten.setTilgryth(lvKonditionenDH.getTilgryth());
        lvKondDaten.setTilgsatz(lvKonditionenDH.getTilgsatz().replace(",", "."));
        lvKondDaten.setTilgver("0");
        lvKondDaten.setVfaellig("0");
        lvKondDaten.setZahltyp(lvKonditionenDH.getZahltyp());
        lvKondDaten.setZinsryth(lvKonditionenDH.getZinsryth());
        lvKondDaten.setZinstyp(lvKonditionenDH.getZinstyp());
        if (lvKonditionenDH.getZinsbeg().length() == 8) {
          lvKondDaten.setZinsbeg(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getZinsbeg())));
        }
        if (lvKonditionenDH.getZinsdat().length() == 8) {
          lvKondDaten.setZinsdat(DatumUtilities
              .changeDate(DatumUtilities.changeDatePoints(lvKonditionenDH.getZinsdat())));
        }
        //lvKondDaten.setZinszahlart(lvKonditionenDH);
        lvKondDaten.setWhrg(lvKonditionenDH.getWhrg());
      }

      // TXSSlice
      Deckung lvDeckungDH = ivListeDeckung.get(lvFinanzgeschaeftDH.getKontonummer());
      if (lvDeckungDH != null) {
        lvSliceInDaten.setDecktyp(lvDeckungDH.getDecktyp());
        //lvSliceInDaten.setBis(lvDeckungDH.getBis().replace(",", "."));
        lvSliceInDaten.setKey(lvDeckungDH.getKey());
        lvSliceInDaten.setVon("0.0");
        lvSliceInDaten.setPool("&#214;ffentlicher Pfandbrief");
        lvSliceInDaten.setTx("&#214;ffentlicher Pfandbrief");
        lvSliceInDaten.setPrj("NLB-PfandBG");
        lvSliceInDaten.setTilgmod("1");
        lvSliceInDaten.setNbetrag(lvDeckungDH.getNbetrag().replace(",", "."));
        lvSliceInDaten.setNliqui("0");
        lvSliceInDaten.setWhrg(lvDeckungDH.getWhrg());
        if (lvDeckungDH.getAktivpassiv().equals("A")) {
          lvSliceInDaten.setAktivpassiv("1");
        }
      }

      // TXSKreditKunde
      Kunde lvKundeDH = ivListeKunden.get(lvFinanzgeschaeftDH.getKundennummer());
      if (lvKundeDH != null) {
        lvKredkunde.setKdnr(lvKundeDH.getKundennummer());
        lvKredkunde.setOrg("25010600");
        lvKredkunde.setQuelle("KUNDE");
        lvKredkunde.setRolle("0");

        lvPersdaten.setNname(String2XML.change2XML(lvKundeDH.getNname()));
        lvPersdaten.setLand(lvKundeDH.getLand());
        lvPersdaten.setStr(String2XML.change2XML(lvKundeDH.getStr()));
        if (String2XML.change2XML(lvKundeDH.getHausnummer()).length() > 10) {
          lvPersdaten.setHausnr(String2XML.change2XML(lvKundeDH.getHausnummer()).substring(0, 9));
        } else {
          lvPersdaten.setHausnr(String2XML.change2XML(lvKundeDH.getHausnummer()));
        }
        lvPersdaten.setPlz(lvKundeDH.getPlz());
        lvPersdaten.setOrt(String2XML.change2XML(lvKundeDH.getOrt()));
        lvPersdaten.setKusyma(lvKundeDH.getKusyma());
        lvPersdaten.setKugr(String2XML.change2XML(
            ValueMapping.changeKundengruppe(ermittleKundengruppe(lvKundeDH.getKusyma()))));
        lvPersdaten.setRatingID(String2XML.change2XML(lvKundeDH.getRating_id()));
        if (String2XML.change2XML(lvKundeDH.getRating_id()).length() > 10) {
          lvPersdaten.setRatingID(String2XML.change2XML(lvKundeDH.getRating_id()).substring(0, 9));
        }
        lvPersdaten.setRatingMaster(String2XML.change2XML(lvKundeDH.getRating_master()));
        if (String2XML.change2XML(lvKundeDH.getRating_master()).length() > 40) {
          lvPersdaten.setRatingMaster(
              String2XML.change2XML(lvKundeDH.getRating_master()).substring(0, 39));
        }
        if (lvKundeDH.getRating_datum().length() == 8) {
          lvPersdaten.setRatingDatum(
              DatumUtilities.changeDatePoints(lvKundeDH.getRating_datum()));
        }
        lvPersdaten.setRatingToolID(String2XML.change2XML(lvKundeDH.getRating_tool_id()));
        lvPersdaten.setRatingTool(String2XML.change2XML(lvKundeDH.getRating_tool()));

        // CT 10.03.2021 - DH liefert kein Gebiet, deshalb selbst ermitteln
        if (lvPersdaten.getLand().equals("DE")) {
          String lvGebiet = "DE_";
          int lvRet_gebiet = ValueMapping.PLZ2Land("009", lvKundeDH.getPlz());
          if (lvRet_gebiet < 10) {
            lvGebiet = lvGebiet + "0";
          }
          //if (ret_gebiet < 100)
          //{
          //  gebiet = gebiet + "0";
          //}
          lvGebiet = lvGebiet + lvRet_gebiet;

          lvPersdaten.setGebiet(lvGebiet);
        }
     }

      // Daten in CashflowQuellsystem-Datei schreiben
      try
      {
          ivFosCashflowQuellsystem.write((lvFinanzgeschaeftDH.getKontonummer() + ";" + lvFinanzgeschaeft.getKey() + ";" + lvFinanzgeschaeft.getOriginator() + ";" +
            lvFinanzgeschaeft.getQuelle() + ";;;;;;;;;;;" + StringKonverter.lineSeparator).getBytes());
      }
      catch (Exception e)
      {
        LOGGER_DH.error("Fehler bei der Ausgabe in die CashflowQuellsystem-Datei");
      }

      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionStart());

      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeftDaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvSliceInDaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvKondDaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvKredkunde.printTXSTransaktionEnde());

      if (lvFinanzgeschaeftDH.getTyp().equals("3")) {
        for (Mapping lvMappingDH : ivListeMapping.values()) {

          if (lvMappingDH.getKontonummer().equals(lvFinanzgeschaeftDH.getKontonummer())) {

            Sicherheiten lvSicherheitDH = ivListeSicherheiten
                .get(lvMappingDH.getSicherheitenNummer());
            if (lvSicherheitDH != null && lvSicherheitDH.getArt().equals("5")) {
              //TXSKreditSicherheit
              //lvKredsh.setWhrg(lvSicherheitDH.getWhrg());
              lvKredsh.setWhrg(lvFinanzgeschaeftDaten.getWhrg());
              lvKredsh.setOrg(lvFinanzgeschaeft.getOriginator());
              lvKredsh.setZuwbetrag(lvFinanzgeschaeftDaten.getRkapi());
              lvKredsh.setKey(lvSicherheitDH.getSicherheitenNummer());
              lvKredsh.setQuelle(lvFinanzgeschaeft.getQuelle());

              // TXSSicherheitenDaten
              lvShdaten.setArt("51");
              lvShdaten.setVbetrag(lvSicherheitDH.getVbetrag());
              lvShdaten.setWhrg(lvSicherheitDH.getWhrg());
              lvShdaten.setNbetrag(lvSicherheitDH.getVbetrag());
              lvShdaten.setPbsatz("0.0");
              // Sicherheit geprueft auf 'Ja' (1)
              lvShdaten.setGepr("1");

              // TXSKreditKunde
              lvKundeDH = ivListeKunden.get(lvSicherheitDH.getKundennummer());
              if (lvKundeDH != null) {

                // TXSSicherheitPerson
                lvShperson.setKdnr(lvKundeDH.getKundennummer());
                lvShperson.setOrg("25010600");
                lvShperson.setQuelle("KUNDE");

                lvPersdaten.initTXSPersonenDaten();
                lvPersdaten.setNname(String2XML.change2XML(lvKundeDH.getNname()));
                lvPersdaten.setLand(lvKundeDH.getLand());
                lvPersdaten.setStr(String2XML.change2XML(lvKundeDH.getStr()));
                if (String2XML.change2XML(lvKundeDH.getHausnummer()).length() > 10)
                {
                  lvPersdaten.setHausnr(String2XML.change2XML(lvKundeDH.getHausnummer()).substring(0, 9));
                }
                else {
                  lvPersdaten.setHausnr(String2XML.change2XML(lvKundeDH.getHausnummer()));
                }
                lvPersdaten.setPlz(lvKundeDH.getPlz());
                lvPersdaten.setOrt(String2XML.change2XML(lvKundeDH.getOrt()));
                lvPersdaten.setKusyma(lvKundeDH.getKusyma());
                lvPersdaten.setKugr(String2XML.change2XML(ValueMapping.changeKundengruppe(ermittleKundengruppe(lvKundeDH.getKusyma()))));
                lvPersdaten.setRatingID(String2XML.change2XML(lvKundeDH.getRating_id()));
                if (String2XML.change2XML(lvKundeDH.getRating_id()).length() > 10) {
                  lvPersdaten.setRatingID(String2XML.change2XML(lvKundeDH.getRating_id()).substring(0, 9));
                }
                lvPersdaten.setRatingMaster(String2XML.change2XML(lvKundeDH.getRating_master()));
                if (String2XML.change2XML(lvKundeDH.getRating_master()).length() > 40) {
                  lvPersdaten.setRatingMaster(String2XML.change2XML(lvKundeDH.getRating_master()).substring(0, 39));
                }
                if (lvKundeDH.getRating_datum().length() == 8) {
                  lvPersdaten
                      .setRatingDatum(DatumUtilities.changeDatePoints(lvKundeDH.getRating_datum()));
                }
                lvPersdaten.setRatingToolID(String2XML.change2XML(lvKundeDH.getRating_tool_id()));
                lvPersdaten.setRatingTool(String2XML.change2XML(lvKundeDH.getRating_tool()));

                // CT 10.03.2021 - DH liefert kein Gebiet, deshalb selbst ermitteln
                if (lvPersdaten.getLand().equals("DE"))
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

                  lvPersdaten.setGebiet(lvGebiet);
                }
              }

              // Transaktionen in die Datei schreiben
              // TXSKreditSicherheit
              pvOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionStart());
              pvOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionDaten());

              // TXSSicherheitDaten
              pvOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionStart());
              pvOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionDaten());
              pvOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionEnde());

              // TXSSicherheitPerson
              pvOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionStart());
              pvOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionDaten());
              //TXSPersonenDaten
              pvOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionStart());
              pvOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionDaten());
              pvOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionEnde());
              pvOutputDarlehenXML.printTransaktion(lvShperson.printTXSTransaktionEnde());

              pvOutputDarlehenXML.printTransaktion(lvKredsh.printTXSTransaktionEnde());
            }
          }
        }
      }
      pvOutputDarlehenXML.printTransaktion(lvFinanzgeschaeft.printTXSTransaktionEnde());
    }
  }

  /**
   * Importiert Grundpfandrechte von Auslandsimmobilien
   * @param pvOutputDarlehenXML
   * @param pvGrundbuchDH
   * @param pvOriginator
   * @param pvQuelle
   */
  private void importGrundpfandrechtAuslandsimmo(OutputDarlehenXML pvOutputDarlehenXML, Sicherheiten pvSicherheitDH, Grundbuch pvGrundbuchDH, Finanzgeschaeft pvFinanzgeschaeftDH, String pvOriginator, String pvQuelle)
  {
    //LOGGER_DH.info("Auslandsimmo...");
    TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
    // Pfandobjekt-Nr nehmen, da kein Grundbuch vorhanden!!!
    // CT 06.06.2012 - Wegen Eindeutigkeit die Sicherheiten-ID davor schreiben
    String lvHelpLfdnr = pvGrundbuchDH.getLfdnr();
    if (pvGrundbuchDH.getLfdnr().contains("_"))
    {
      lvHelpLfdnr = pvGrundbuchDH.getLfdnr().substring(0, pvGrundbuchDH.getLfdnr().indexOf("_"));
    }

    lvShve.setVenr(pvGrundbuchDH.getSicherheitenNummer() + "_" + pvGrundbuchDH.getPfandobjektNummer() + "_" + lvHelpLfdnr);
     lvShve.setOrg(pvOriginator);

    lvShve.setQuelle(pvQuelle);
    pvOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionStart());
    pvOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionDaten());

    // TXSVerzeichnisDaten
    TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
    //vedaten.setAbt("3");
    lvVedaten.setGbart("100");

    int lvAnzahl = 0;
    HashMap<String, BigDecimal> lvTeilVbetrag = new HashMap<String, BigDecimal>();
    for (Grundbuch lvHelpGrundbuchDH : ivListeGrundbuch.values()) {
      if (lvHelpGrundbuchDH.getPfandobjektNummer().equals(pvGrundbuchDH.getPfandobjektNummer()) &&
          lvHelpGrundbuchDH.getSicherheitenNummer().equals(pvGrundbuchDH.getSicherheitenNummer()))
      {
        String lvHelpNr = lvHelpGrundbuchDH.getLfdnr();
        if (lvHelpGrundbuchDH.getLfdnr().contains("_"))
        {
          lvHelpNr = lvHelpGrundbuchDH.getLfdnr().substring(0, lvHelpGrundbuchDH.getLfdnr().indexOf("_"));
        }

        if (lvTeilVbetrag.containsKey(lvHelpGrundbuchDH.getSicherheitenNummer() + "_" + lvHelpGrundbuchDH.getPfandobjektNummer() + "_" + lvHelpNr))
        {
          BigDecimal lvHelpVbetrag = lvTeilVbetrag.get(lvHelpGrundbuchDH.getSicherheitenNummer() + "_" + lvHelpGrundbuchDH.getPfandobjektNummer() + "_" + lvHelpNr);
          lvHelpVbetrag = lvHelpVbetrag.add(StringKonverter.convertString2BigDecimal(lvHelpGrundbuchDH.getVbetrag()));
          lvTeilVbetrag.remove(lvHelpGrundbuchDH.getSicherheitenNummer() + "_" + lvHelpGrundbuchDH.getPfandobjektNummer() + "_" + lvHelpNr);
          lvTeilVbetrag.put(lvHelpGrundbuchDH.getSicherheitenNummer() + "_" + lvHelpGrundbuchDH.getPfandobjektNummer() + "_" + lvHelpNr, lvHelpVbetrag);
        }
        else
        {
          lvTeilVbetrag.put(lvHelpGrundbuchDH.getSicherheitenNummer() + "_" + lvHelpGrundbuchDH.getPfandobjektNummer() + "_" + lvHelpNr, StringKonverter.convertString2BigDecimal(lvHelpGrundbuchDH.getVbetrag()));
        }
        lvAnzahl++;
      }
    }

    if (lvAnzahl > 1)
    {
      lvVedaten.setBetrag(lvTeilVbetrag.get(pvGrundbuchDH.getSicherheitenNummer() + "_" + pvGrundbuchDH.getPfandobjektNummer() + "_" + lvHelpLfdnr).toPlainString());
      lvVedaten.setWhrg(pvSicherheitDH.getWhrg());
    }
    else {
      lvVedaten.setBetrag(pvGrundbuchDH.getVbetrag());
      lvVedaten.setWhrg(pvGrundbuchDH.getGrundpfandrechtWaehrung());
    }

    lvVedaten.setAsichr(".");

    pvOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionStart());
    pvOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionDaten());
    pvOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionEnde());

    Pfandobjekt lvPfandobjektDH = ivListePfandobjekt.get(pvGrundbuchDH.getPfandobjektNummer());
    if (lvPfandobjektDH != null) {

      // TXSVerzeichnisPfandobjekt
      TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
      lvVepo.setObjnr(lvPfandobjektDH.getPfandobjektNummer());
      //lvVepo.setOrg(ValueMapping.changeMandant(ivSAPCMS.getVorlaufsatz().getInstitut()));
      lvVepo.setOrg(pvOriginator);
      lvVepo.setQuelle(pvQuelle);
      pvOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionDaten());
      // TXSPfandobjektDaten
      TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();

      lvPodaten.setEigtyp("1");
      if (lvPfandobjektDH.getEigennutzung().equals("0")) {
        lvPodaten.setEigtyp("2");
      }

      // Ertragsfaehigkeit setzen - CT 19.07.2021
      // 11 -> voll ertragsfaehig (TXS -> 3)
      // 12 -> Baugeld (unfertige und noch nicht ertragsfaehige Neubauten) (TXS -> 1)
      // 13 -> Bauplatz (TXS -> 2)
      // 14 -> Kein Mapping, das Feld bleibt leer.
      if (pvFinanzgeschaeftDH.getDeckungsschluessel().equals("11"))
      {
        lvPodaten.setErtragsf("3");
      }

      if (pvFinanzgeschaeftDH.getDeckungsschluessel().equals("12"))
      {
        lvPodaten.setErtragsf("1");
      }

      if (pvFinanzgeschaeftDH.getDeckungsschluessel().equals("13"))
      {
        lvPodaten.setErtragsf("2");
      }

      lvPodaten.setJahr(lvPfandobjektDH.getJahr());
      lvPodaten.setKat("1");
      lvPodaten.setLand(lvPfandobjektDH.getLand());
      lvPodaten.setGebiet(MappingDH.changeGebiet(lvPfandobjektDH.getGebiet(), LOGGER_DH));
      lvPodaten.setSwert(lvPfandobjektDH.getSwert());
      lvPodaten.setEwert(lvPfandobjektDH.getEwert());
      lvPodaten.setBwert(lvPfandobjektDH.getBwert());
      if (lvPfandobjektDH.getBwertdat().length() == 8) {
        lvPodaten.setBwertdat(DatumUtilities
            .changeDate(DatumUtilities.changeDatePoints(lvPfandobjektDH.getBwertdat())));
      }
      // Nutzungsart
      lvPodaten.setNart(MappingDH.changeNutzungsart(lvPfandobjektDH.getNutzungsart(), LOGGER_DH));

      // Objektgruppe
      lvPodaten.setOgrp(MappingDH.changeObjektgruppe(lvPfandobjektDH.getWirtschaftlich(), LOGGER_DH));
      if (pvFinanzgeschaeftDH.getDeckungsschluessel().equals("12"))
      {
        lvPodaten.setOgrp("100099");
      }

      if (pvFinanzgeschaeftDH.getDeckungsschluessel().equals("13"))
      {
        lvPodaten.setOgrp("100022");
      }

      lvPodaten.setOrt(lvPfandobjektDH.getOrt());
      lvPodaten.setPlz(lvPfandobjektDH.getPlz());
      lvPodaten.setStr(lvPfandobjektDH.getStrhn());
      lvPodaten.setWhrg(lvPfandobjektDH.getWhrg());

      pvOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionStart());
      pvOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionDaten());
      pvOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionEnde());

      pvOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionEnde());
    }

    pvOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionEnde());
  }

  /**
   * Liefert eine Statistik als String
   * @return
   */
  private String getStatistik()
  {
    StringBuffer lvOut = new StringBuffer();

    lvOut.append(ivZaehlerDeckung + " Deckungsdaten eingelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerFinanzgeschaefte + " Finanzgeschaefte eingelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerFinanzgeschaefteKD + " FinanzgeschaefteKD eingelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerGrundbuch + " Grundbuecher eingelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerKonditionen + " Konditionen eingelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerKunde + " Kunden eingelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerMapping + " Mappings eingelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerPfandobjekt + " Pfandobjekte eingelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerSicherheiten + " Sicherheiten eingelesen..." + StringKonverter.lineSeparator);

    return lvOut.toString();
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
