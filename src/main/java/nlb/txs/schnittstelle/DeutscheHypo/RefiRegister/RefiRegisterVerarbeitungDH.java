/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.DeutscheHypo.RefiRegister;

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
import nlb.txs.schnittstelle.Transaktion.TXSBestandsverzDaten;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSKreditSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSPersonenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSPfandobjektDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheit;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitDaten;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitPerson2;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitVerzeichnis;
import nlb.txs.schnittstelle.Transaktion.TXSSicherheitZuSicherheit;
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

public class RefiRegisterVerarbeitungDH {

  // Logger fuer RefiRegisterDH
  private static Logger LOGGER_RefiRegisterDH = Logger.getLogger("RefiRegisterDHLogger");

  /**
   * Institutsnummer
   */
  private String ivInstitutsnummer;

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
   * Ausgabedatei der TXS-Importdaten
   */
  private OutputDarlehenXML ivOutputDarlehenXML;

  // Listen mit den einzelnen Eingabedateien/-tabellen
  private HashMap<String, Deckung> ivListeDeckung;
  private HashMap<String, Finanzgeschaeft> ivListeFinanzgeschaefte;
  private HashMap<String, Grundbuch> ivListeGrundbuch;
  private HashMap<String, Konditionen> ivListeKonditionen;
  private HashMap<String, Kunde> ivListeKunden;
  private HashMap<String, Mapping> ivListeMapping;
  private HashMap<String, Pfandobjekt> ivListePfandobjekt;
  private HashMap<String, Sicherheiten> ivListeSicherheiten;

  // Zaehlervariablen
  private int ivZaehlerDeckung = 0;
  private int ivZaehlerFinanzgeschaefte = 0;
  private int ivZaehlerGrundbuch = 0;
  private int ivZaehlerKonditionen = 0;
  private int ivZaehlerKunde = 0;
  private int ivZaehlerMapping = 0;
  private int ivZaehlerPfandobjekt = 0;
  private int ivZaehlerSicherheiten = 0;

  /**
   * Konstruktor fuer LoanIQ2RefiRegister Verarbeitung
   */
  public RefiRegisterVerarbeitungDH(IniReader pvReader)
  {
    if (pvReader != null)
    {
        ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
        if (ivInstitutsnummer.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Keine Institutsnummer in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("Institutsnummer: " + ivInstitutsnummer);
        }

        ivImportVerzeichnis = pvReader.getPropertyString("RefiRegisterDH", "ImportVerzeichnis", "Fehler");
        if (ivImportVerzeichnis.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein Import-Verzeichnis in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("ImportVerzeichnis: " + ivImportVerzeichnis);
        }

        ivExportVerzeichnis = pvReader.getPropertyString("RefiRegisterDH", "ExportVerzeichnis", "Fehler");
        if (ivExportVerzeichnis.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein Export-Verzeichnis in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("ExportVerzeichnis: " + ivExportVerzeichnis);
        }

        ivDeckungDatei =  pvReader.getPropertyString("RefiRegisterDH", "Deckung-Datei", "Fehler");
        if (ivDeckungDatei.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein Deckung-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("Deckung-Datei: " + ivDeckungDatei);
        }

        ivFinanzgeschaefteDatei =  pvReader.getPropertyString("RefiRegisterDH", "Finanzgeschaefte-Datei", "Fehler");
        if (ivFinanzgeschaefteDatei.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein Finanzgeschaefte-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("Finanzgeschaefte-Datei: " + ivFinanzgeschaefteDatei);
        }

        ivGrundbuchDatei =  pvReader.getPropertyString("RefiRegisterDH", "Grundbuch-Datei", "Fehler");
        if (ivGrundbuchDatei.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein Grundbuch-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("Grundbuch-Datei: " + ivGrundbuchDatei);
        }

        ivKonditionenDatei =  pvReader.getPropertyString("RefiRegisterDH", "Konditionen-Datei", "Fehler");
        if (ivKonditionenDatei.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein Konditionen-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("Konditionen-Datei: " + ivKonditionenDatei);
        }

        ivKundenDatei =  pvReader.getPropertyString("RefiRegisterDH", "Kunden-Datei", "Fehler");
        if (ivKundenDatei.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein Kunden-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("Kunden-Datei: " + ivKundenDatei);
        }

        ivMappingDatei =  pvReader.getPropertyString("RefiRegisterDH", "Mapping-Datei", "Fehler");
        if (ivMappingDatei.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein Mapping-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("Mapping-Datei: " + ivMappingDatei);
        }

        ivPfandobjektDatei =  pvReader.getPropertyString("RefiRegisterDH", "Pfandobjekt-Datei", "Fehler");
        if (ivPfandobjektDatei.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein Pfandobjekt-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("Pfandobjekt-Datei: " + ivPfandobjektDatei);
        }

        ivSicherheitenDatei =  pvReader.getPropertyString("RefiRegisterDH", "Sicherheiten-Datei", "Fehler");
        if (ivSicherheitenDatei.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein Sicherheiten-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("Sicherheiten-Datei: " + ivSicherheitenDatei);
        }

        ivDHOutputDatei =  pvReader.getPropertyString("RefiRegisterDH", "DHOutput-Datei", "Fehler");
        if (ivDHOutputDatei.equals("Fehler"))
        {
          LOGGER_RefiRegisterDH.error("Kein DHOutput-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER_RefiRegisterDH.info("DHOutput-Datei: " + ivDHOutputDatei);
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
    // Listen initialisieren
    ivListeDeckung = new HashMap<String, Deckung>();
    ivListeFinanzgeschaefte = new HashMap<String, Finanzgeschaeft>();
    ivListeGrundbuch = new HashMap<String, Grundbuch>();
    ivListeKonditionen = new HashMap<String, Konditionen>();
    ivListeKunden = new HashMap<String, Kunde>();
    ivListeMapping = new HashMap<String, Mapping>();
    ivListePfandobjekt = new HashMap<String, Pfandobjekt>();
    ivListeSicherheiten = new HashMap<String, Sicherheiten>();

    // Darlehen XML-Datei im TXS-Format
    ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivDHOutputDatei, LOGGER_RefiRegisterDH);
    ivOutputDarlehenXML.openXML();
    ivOutputDarlehenXML.printXMLStart();
    ivOutputDarlehenXML.printTXSImportDatenStart();

     // Finanzgeschaefte einlesen
    ivZaehlerFinanzgeschaefte = Finanzgeschaeft.readFinanzgeschaefte(ivImportVerzeichnis + "\\" + ivFinanzgeschaefteDatei, ivListeFinanzgeschaefte, LOGGER_RefiRegisterDH);
    // Grundbuecher einlesen
    ivZaehlerGrundbuch = Grundbuch.readGrundbuch(ivImportVerzeichnis + "\\" + ivGrundbuchDatei, ivListeGrundbuch, LOGGER_RefiRegisterDH);
    // Kunden einlesen
    ivZaehlerKunde = Kunde.readKunden(ivImportVerzeichnis + "\\" + ivKundenDatei, ivListeKunden, LOGGER_RefiRegisterDH);
    // Mapping einlesen
    ivZaehlerMapping = Mapping.readMapping(ivImportVerzeichnis + "\\" + ivMappingDatei, ivListeMapping, LOGGER_RefiRegisterDH);
    // Pfandobjekt einlesen
    ivZaehlerPfandobjekt = Pfandobjekt.readPfandobjekt(ivImportVerzeichnis + "\\" + ivPfandobjektDatei, ivListePfandobjekt, LOGGER_RefiRegisterDH);
    // Sicherheiten einlesen
    ivZaehlerSicherheiten = Sicherheiten.readSicherheiten(ivImportVerzeichnis + "\\" + ivSicherheitenDatei, ivListeSicherheiten, LOGGER_RefiRegisterDH);

    // Verarbeitung der Finanzgeschaefte
    for (Finanzgeschaeft lvFinanzgeschaeftDH: ivListeFinanzgeschaefte.values()) {
      // Zur Filterung von einzelnen Geschaeften - CT 20.06.2021
      if (lvFinanzgeschaeftDH.getKontonummer().startsWith("122893")) // || lvFinanzgeschaeftDH.getKontonummer().startsWith("122627") ||
          //lvFinanzgeschaeftDH.getKontonummer().startsWith("122239") || lvFinanzgeschaeftDH.getKontonummer().startsWith("122773") ||
          //lvFinanzgeschaeftDH.getKontonummer().startsWith("112757") || lvFinanzgeschaeftDH.getKontonummer().startsWith("122751")) // ||
          //lvFinanzgeschaeftDH.getKontonummer().startsWith("122600") || lvFinanzgeschaeftDH.getKontonummer().startsWith("119795"))
      {
        LOGGER_RefiRegisterDH.info("Verarbeite Kontonummer: " + lvFinanzgeschaeftDH.getKontonummer());
        verarbeiteKreditDeutscheHypo(lvFinanzgeschaeftDH);
      }
    }

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    // Statistik ausgeben
    LOGGER_RefiRegisterDH.info(getStatistik());

  }

  /**
   * Verarbeitet ein Finanzgeschaeft DeutscheHypo
   * @param pvFinanzgeschaeftDH Finanzgeschaeft DeutscheHypo
   */
  private void verarbeiteKreditDeutscheHypo(Finanzgeschaeft pvFinanzgeschaeftDH)
  {
    TXSFinanzgeschaeft ivFinanzgeschaeft = new TXSFinanzgeschaeft();
    TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
    TXSKonditionenDaten ivKondDaten = new TXSKonditionenDaten();
    TXSKreditKunde ivKredKunde = new TXSKreditKunde();
    TXSPersonenDaten lvPersdaten = new TXSPersonenDaten();

    TXSSicherheitDaten lvShdaten = new TXSSicherheitDaten();
    TXSSicherheitVerzeichnis lvShve = new TXSSicherheitVerzeichnis();
    TXSVerzeichnisDaten lvVedaten = new TXSVerzeichnisDaten();
    TXSVerzeichnisVBlatt lvVevb = new TXSVerzeichnisVBlatt();
    TXSVerzeichnisblattDaten lvVbdaten = new TXSVerzeichnisblattDaten();
    TXSVerzeichnisBestandsverz lvVebv = new TXSVerzeichnisBestandsverz();
    TXSBestandsverzDaten lvBvdaten = new TXSBestandsverzDaten();

    TXSVerzeichnisPfandobjekt lvVepo = new TXSVerzeichnisPfandobjekt();
    TXSPfandobjektDaten lvPodaten = new TXSPfandobjektDaten();

    // TXSFinanzgeschaeft
    ivFinanzgeschaeft.setKey(pvFinanzgeschaeftDH.getKontonummer());
    ivFinanzgeschaeft.setOriginator("25010600");
    ivFinanzgeschaeft.setQuelle("ADHYREFI");

    // TXSFinanzgeschaeftDaten
    ivFinanzgeschaeftDaten.setAbetrag("0.01");
    ivFinanzgeschaeftDaten.setRkapi("0.01");
    ivFinanzgeschaeftDaten.setNbetrag("0.01");
    ivFinanzgeschaeftDaten.setAusstat("2");
    ivFinanzgeschaeftDaten.setAktivpassiv("1");
    ivFinanzgeschaeftDaten.setAz(pvFinanzgeschaeftDH.getKontonummer());
    ivFinanzgeschaeftDaten.setWhrg(pvFinanzgeschaeftDH.getWhrg());
    ivFinanzgeschaeftDaten.setVwhrg(pvFinanzgeschaeftDH.getWhrg());
    ivFinanzgeschaeftDaten.setTyp("1");
    ivFinanzgeschaeftDaten.setKat("1");
    if (pvFinanzgeschaeftDH.getZusdat().length() == 8) {
      ivFinanzgeschaeftDaten.setZusdat(DatumUtilities
          .changeDate(DatumUtilities.changeDatePoints(pvFinanzgeschaeftDH.getZusdat())));
    }
    // TXSKondDaten
    ivKondDaten.setKondkey("1");
    ivKondDaten.setFaellig("2099-12-31");


    // TXSKredKunde
    Kunde lvKundeDH = ivListeKunden.get(pvFinanzgeschaeftDH.getKundennummer());
    if (lvKundeDH != null) {
      ivKredKunde.setKdnr(lvKundeDH.getKundennummer());
      ivKredKunde.setQuelle("KUNDE");
      ivKredKunde.setOrg("25010600");
      ivKredKunde.setRolle("1");

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
            DatumUtilities.changeDatePoints(String2XML.change2XML(lvKundeDH.getRating_datum())));
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
    ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionStart());

    ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionStart());
    ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionDaten());
    ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionEnde());

    ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
    ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
    ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());

    ivOutputDarlehenXML.printTransaktion(ivKredKunde.printTXSTransaktionStart());
    ivOutputDarlehenXML.printTransaktion(ivKredKunde.printTXSTransaktionDaten());
    ivOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionStart());
    ivOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionDaten());
    ivOutputDarlehenXML.printTransaktion(lvPersdaten.printTXSTransaktionEnde());
    ivOutputDarlehenXML.printTransaktion(ivKredKunde.printTXSTransaktionEnde());

    for (Mapping lvMappingDH : ivListeMapping.values()) {
      if (lvMappingDH.getKontonummer().equals(pvFinanzgeschaeftDH.getKontonummer())) {
        Sicherheiten lvSicherheitDH = ivListeSicherheiten.get(lvMappingDH.getSicherheitenNummer());
        if (lvSicherheitDH != null) {
          // TXSKreditSicherheit
          TXSKreditSicherheit lvKredSh = new TXSKreditSicherheit();
          lvKredSh.setKey(lvSicherheitDH.getSicherheitenNummer() + "_ABTR");
          //lvKredSh.setQuelle(ivFinanzgeschaeft.getQuelle());
          lvKredSh.setQuelle("ADHYREFI");
          lvKredSh.setOrg(ivFinanzgeschaeft.getOriginator());
          lvKredSh.setHauptsh("0");
          lvKredSh.setZuwbetrag("0.01");
          lvKredSh.setWhrg(ivFinanzgeschaeftDaten.getWhrg());

          // TXSSicherheitDaten
          TXSSicherheitDaten lvShdatenAbtr = new TXSSicherheitDaten();
          lvShdatenAbtr.setArt("55");

          // TXSSicherheitPerson2
          TXSSicherheitPerson2 lvShperson2 = new TXSSicherheitPerson2();
          lvShperson2.setKdnr("0000001115");
          lvShperson2.setOrg(ValueMapping.changeMandant("009"));
          lvShperson2.setQuelle("KUNDE");
          lvShperson2.setRolle("2");

          ivOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionDaten());
          ivOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionDaten());
          ivOutputDarlehenXML.printTransaktion(lvShdatenAbtr.printTXSTransaktionEnde());
          ivOutputDarlehenXML.printTransaktion(lvShperson2.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(lvShperson2.printTXSTransaktionDaten());
          ivOutputDarlehenXML.printTransaktion(lvShperson2.printTXSTransaktionEnde());
          ivOutputDarlehenXML.printTransaktion(lvKredSh.printTXSTransaktionEnde());

        }
      }
    }

    for (Mapping lvMappingDH : ivListeMapping.values()) {
      if (lvMappingDH.getKontonummer().equals(pvFinanzgeschaeftDH.getKontonummer())) {
        Sicherheiten lvSicherheitDH = ivListeSicherheiten.get(lvMappingDH.getSicherheitenNummer());
        if (lvSicherheitDH != null) {
          // Originalsicherheit
          TXSSicherheit lvSh = new TXSSicherheit();
          lvSh.setKey(lvSicherheitDH.getSicherheitenNummer());
          lvSh.setOrg("25010600");
          lvSh.setQuelle("ADHYREFI");

          // TXSSicherheitDaten
          lvShdaten = new TXSSicherheitDaten();
          lvShdaten.setArt("1" + lvSicherheitDH.getArt());
          lvShdaten.setNbetrag(lvSicherheitDH.getNbetrag());
          lvShdaten.setVbetrag(lvSicherheitDH.getVbetrag());
          lvShdaten.setWhrg(lvSicherheitDH.getWhrg());
          lvShdaten.setOrigsichant("100");
          lvShdaten.setGepr("1");

          // TXSKreditSicherheit ausgeben
          ivOutputDarlehenXML.printTransaktion(lvSh.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(lvSh.printTXSTransaktionDaten());
          ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionDaten());
          ivOutputDarlehenXML.printTransaktion(lvShdaten.printTXSTransaktionEnde());

          for (Grundbuch lvGrundbuchDH : ivListeGrundbuch.values()) {

            if (lvGrundbuchDH.getSicherheitenNummer()
                .equals(lvSicherheitDH.getSicherheitenNummer())) {

              String lvHelpLfdnr = lvGrundbuchDH.getLfdnr();
                if (lvGrundbuchDH.getLfdnr().contains("_"))
                {
                  lvHelpLfdnr = lvGrundbuchDH.getLfdnr().substring(0, lvGrundbuchDH.getLfdnr().indexOf("_"));
                }

                lvShve.setVenr(lvGrundbuchDH.getSicherheitenNummer() + "_" + lvGrundbuchDH
                    .getPfandobjektNummer() + "_" + lvHelpLfdnr);
                lvShve.setOrg(ivFinanzgeschaeft.getOriginator());
                //lvShve.setQuelle(ivFinanzgeschaeft.getQuelle());
                lvShve.setQuelle("ADHYREFI");

                int lvAnzahl = 0;
                for (Grundbuch lvHelpGrundbuchDH : ivListeGrundbuch.values()) {
                  if (lvHelpGrundbuchDH.getPfandobjektNummer().equals(lvGrundbuchDH.getPfandobjektNummer()) &&
                      lvHelpGrundbuchDH.getSicherheitenNummer().equals(lvGrundbuchDH.getSicherheitenNummer()))
                  {
                    lvAnzahl++;
                  }
                }

                if (lvAnzahl > 1)
                {
                  lvVedaten.setBetrag(lvSicherheitDH.getVbetrag());
                  lvVedaten.setWhrg(lvSicherheitDH.getWhrg());
                }
                else {
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
                lvVevb.setOrg(ivFinanzgeschaeft.getOriginator());
                //lvVevb.setQuelle(ivFinanzgeschaeft.getQuelle());
                lvVevb.setQuelle("ADHYREFI");

                lvVbdaten.setBlatt(lvGrundbuchDH.getBlatt());
                lvVbdaten.setGbvon(lvGrundbuchDH.getGbvon() + "; Amtsgericht: " + lvGrundbuchDH.getGericht());
                lvVbdaten.setKat("1");
                lvVbdaten.setBand(lvGrundbuchDH.getBand());
                lvVbdaten.setGericht(lvGrundbuchDH.getGericht());

                lvVebv.setBvnr(lvGrundbuchDH.getSicherheitenNummer() + "_" + lvGrundbuchDH
                    .getPfandobjektNummer() + "_" + lvGrundbuchDH.getLfdnr());
                lvVebv.setOrg(ivFinanzgeschaeft.getOriginator());
                //lvVebv.setQuelle(ivFinanzgeschaeft.getQuelle());
                lvVebv.setQuelle("ADHYREFI");

                lvBvdaten.setGem(lvGrundbuchDH.getGem());
                lvBvdaten.setFlur(lvGrundbuchDH.getFlur());
                lvBvdaten.setFlurst(lvGrundbuchDH.getFlurst());
                lvBvdaten.setLfdnr(lvGrundbuchDH.getLfdnr());

                Pfandobjekt lvPfandobjektDH = ivListePfandobjekt
                    .get(lvGrundbuchDH.getPfandobjektNummer());
                if (lvPfandobjektDH != null) {
                  lvVepo.setObjnr(lvPfandobjektDH.getPfandobjektNummer());
                  lvVepo.setOrg(ivFinanzgeschaeft.getOriginator());
                  //lvVepo.setQuelle(ivFinanzgeschaeft.getQuelle());
                  lvVepo.setQuelle("ADHYREFI");

                  lvPodaten.setEigtyp("1");
                  if (lvPfandobjektDH.getEigennutzung().equals("0")) {
                    lvPodaten.setEigtyp("2");
                  }
                  lvPodaten.setErtragsf("3");
                  if (lvPfandobjektDH.getFertigstellung().equals("N")) {
                    lvPodaten.setErtragsf("2"); // Bauplatz
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
                  }
                  else
                  {
                    lvPodaten.setGebiet(
                        MappingDH.changeGebiet(lvPfandobjektDH.getGebiet(), LOGGER_RefiRegisterDH));
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
                      MappingDH.changeNutzungsart(lvPfandobjektDH.getNutzungsart(), LOGGER_RefiRegisterDH));

                  // Objektgruppe
                  lvPodaten.setOgrp(MappingDH
                      .changeObjektgruppe(lvPfandobjektDH.getWirtschaftlich(), LOGGER_RefiRegisterDH));

                  lvPodaten.setOrt(lvPfandobjektDH.getOrt());
                  lvPodaten.setPlz(lvPfandobjektDH.getPlz());
                  lvPodaten.setStr(lvPfandobjektDH.getStrhn());
                  lvPodaten.setZusatz(lvPfandobjektDH.getZusatz());
                  lvPodaten.setWhrg(lvPfandobjektDH.getWhrg());
                }

                ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionStart());
                  ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionDaten());

                  ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionStart());
                  ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionDaten());
                  ivOutputDarlehenXML.printTransaktion(lvVedaten.printTXSTransaktionEnde());

                  ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionStart());
                  ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionDaten());

                  ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionStart());
                  ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionDaten());
                  ivOutputDarlehenXML.printTransaktion(lvVbdaten.printTXSTransaktionEnde());

                  ivOutputDarlehenXML.printTransaktion(lvVevb.printTXSTransaktionEnde());

                  ivOutputDarlehenXML.printTransaktion(lvVebv.printTXSTransaktionStart());
                  ivOutputDarlehenXML.printTransaktion(lvVebv.printTXSTransaktionDaten());

                  ivOutputDarlehenXML.printTransaktion(lvBvdaten.printTXSTransaktionStart());
                  ivOutputDarlehenXML.printTransaktion(lvBvdaten.printTXSTransaktionDaten());
                  ivOutputDarlehenXML.printTransaktion(lvBvdaten.printTXSTransaktionEnde());

                  ivOutputDarlehenXML.printTransaktion(lvVebv.printTXSTransaktionEnde());

                  if (lvPfandobjektDH != null) {
                    ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionStart());
                    ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionDaten());
                    ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionStart());
                    ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionDaten());
                    ivOutputDarlehenXML.printTransaktion(lvPodaten.printTXSTransaktionEnde());

                    ivOutputDarlehenXML.printTransaktion(lvVepo.printTXSTransaktionEnde());
                  }
                  ivOutputDarlehenXML.printTransaktion(lvShve.printTXSTransaktionEnde());
            }
          }

          TXSSicherheitZuSicherheit lvZugsh = new TXSSicherheitZuSicherheit();
          lvZugsh.setKey(lvSicherheitDH.getSicherheitenNummer() + "_ABTR");
          lvZugsh.setQuelle("ADHYREFI");
          lvZugsh.setOrg("25010600");
          lvZugsh.setArt("1");
          lvZugsh.setRang("1");

          ivOutputDarlehenXML.printTransaktion(lvZugsh.printTXSTransaktionStart());
          ivOutputDarlehenXML.printTransaktion(lvZugsh.printTXSTransaktionDaten());
          ivOutputDarlehenXML.printTransaktion(lvZugsh.printTXSTransaktionEnde());

          ivOutputDarlehenXML.printTransaktion(lvSh.printTXSTransaktionEnde());
        }
      }
    }
    ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
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
