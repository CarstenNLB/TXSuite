/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.KEV.LoanIQ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import nlb.txs.schnittstelle.Kunde.KundennummernOutput;
import nlb.txs.schnittstelle.LoanIQ.Darlehen.Daten.Darlehen;
import nlb.txs.schnittstelle.LoanIQ.Vorlaufsatz;
import nlb.txs.schnittstelle.OutputXML.OutputDarlehenXML;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeft;
import nlb.txs.schnittstelle.Transaktion.TXSFinanzgeschaeftDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKonditionenDaten;
import nlb.txs.schnittstelle.Transaktion.TXSKreditKunde;
import nlb.txs.schnittstelle.Transaktion.TXSSliceInDaten;
import nlb.txs.schnittstelle.Utilities.CalendarHelper;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.MappingKunde;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import org.apache.log4j.Logger;

public class LoanIQVerarbeitungKEV
{
  // Logger fuer KEV LoanIQ
  private static Logger LOGGER_LOANIQ_KEV = Logger.getLogger("LoanIQKEVLogger");

  ///**
  // * Institutsnummer
  // */
  //private String ivInstitutsnummer;

  /**
   * Import-Verzeichnis der MIDAS-Datei
   */
  private String ivImportVerzeichnis;

  /**
   * Export-Verzeichnis der TXS-Importdatei
   */
  private String ivExportVerzeichnis;

  /**
   * Dateiname der KEV-Datei
   */
  private String ivKEVInputDatei;

  /**
   * Dateiname der TXS-Importdatei
   */
  private String ivKEVOutputDatei;

  /**
   * Import-Verzeichnis der Sicherheiten-Datei
   */
  private String ivImportVerzeichnisSAPCMS;

  /**
   * Dateiname der Sicherheiten-Datei
   */
  ////private String ivSapcmsDatei;

  /**
   * Sicherheiten aus Sicherheiten
   */
  ////private SAPCMS_Neu ivSapcms;

  /**
   * Name der KundeRequest-Datei
   */
  private String ivKundeRequestDatei;

  /**
   * Ausgabedatei der Kundennummern
   */
  private KundennummernOutput ivKundennummernOutput;

  /**
   * Vorlaufsatz der LoanIQ-Datei
   */
  private Vorlaufsatz ivVorlaufsatz;

  /**
   * Zaehler fuer die Anzahl der Vorlaufsaetze
   */
  private int ivZaehlerVorlaufsatz;

  /**
   * Zaehler fuer die Anzahl der Finanzgeschaefte
   */
  private int ivZaehlerFinanzgeschaefte;

  /**
   * Liste der Kundennummern
   */
  private HashSet<String> ivListeKundennummern;

  /**
   * Liste der KEV-Geschaefte
   */
  private HashMap<String, Darlehen> ivListeGeschaefte;

  /**
   * TXS-Importdatei
   */
  private OutputDarlehenXML ivOutputDarlehenXML;

  // Transaktionen
  private TXSFinanzgeschaeft ivFinanzgeschaeft;
  private TXSSliceInDaten ivSliceInDaten;
  private TXSFinanzgeschaeftDaten ivFinanzgeschaeftDaten;
  private TXSKonditionenDaten ivKondDaten;
  private TXSKreditKunde ivKredkunde;

  // Zaehlvariablen
  private int ivAnzahlA0 = 0;
  private int ivAnzahlA1 = 0;
  private int ivAnzahlA2 = 0;
  private int ivRestkapitalNull = 0;

  /**
   * Konstruktor fuer KEV Verarbeitung LoanIQ
   * @param pvReader Verweis auf den IniReader
   */
  public LoanIQVerarbeitungKEV(IniReader pvReader)
  {
    this.ivFinanzgeschaeft = new TXSFinanzgeschaeft();
    this.ivSliceInDaten = new TXSSliceInDaten();
    this.ivFinanzgeschaeftDaten = new TXSFinanzgeschaeftDaten();
    this.ivKondDaten = new TXSKonditionenDaten();
    this.ivKredkunde = new TXSKreditKunde();

    ivListeKundennummern = new HashSet<String>();
    ivListeGeschaefte = new HashMap<String, Darlehen>();

    if (pvReader != null)
    {

      //ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
      //if (ivInstitutsnummer.equals("Fehler"))
      //{
      //  LOGGER_KEV_LOANIQ.error("Keine Institutsnummer in der ini-Datei...");
      //  System.exit(1);
      //}

      ivImportVerzeichnis = pvReader.getPropertyString("LoanIQKEV", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_LOANIQ_KEV.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("LoanIQKEV", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_LOANIQ_KEV.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }

      ivKEVInputDatei =  pvReader.getPropertyString("LoanIQKEV", "KEVInput-Datei", "Fehler");
      if (ivKEVInputDatei.equals("Fehler"))
      {
        LOGGER_LOANIQ_KEV.error("Kein KEVInput-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      ivKEVOutputDatei =  pvReader.getPropertyString("LoanIQKEV", "KEVOutput-Datei", "Fehler");
      if (ivKEVOutputDatei.equals("Fehler"))
      {
        LOGGER_LOANIQ_KEV.error("Kein KEVOutput-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      /*
      ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("Sicherheiten", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
      {
        LOGGER_LOANIQ_KEV.error("Kein Sicherheiten Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      } */

      ////ivSapcmsDatei = pvReader.getPropertyString("Sicherheiten", "Sicherheiten-Datei", "Fehler");
      ////if (ivSapcmsDatei.equals("Fehler"))
      ////{
      ////  LOGGER_LOANIQ_KEV.error("Kein Sicherheiten-Dateiname in der ini-Datei...");
      ////  System.exit(1);
      ////}

      ivKundeRequestDatei = pvReader.getPropertyString("LoanIQKEV", "KundeRequestDatei", "Fehler");
      if (ivKundeRequestDatei.equals("Fehler"))
      {
        LOGGER_LOANIQ_KEV.error("Kein KundeRequest-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      // Verarbeitung starten
      startVerarbeitung();

      System.exit(ivRestkapitalNull);
    }
  }

  /**
   * Start der Verarbeitung
   */
  private void startVerarbeitung()
  {
    Calendar lvCal = Calendar.getInstance();
    CalendarHelper lvCh = new CalendarHelper();

    LOGGER_LOANIQ_KEV.info("Start: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);

    ////ivSapcms = new SAPCMS_Neu(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, LOGGER_LOANIQ_KEV);

    // Darlehen XML-Datei im TXS-Format
    System.out.println(ivExportVerzeichnis + "\\" + ivKEVOutputDatei);
    ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivKEVOutputDatei, LOGGER_LOANIQ_KEV);
    ivOutputDarlehenXML.openXML();
    ivOutputDarlehenXML.printXMLStart();
    ivOutputDarlehenXML.printTXSImportDatenStart();

    readKEVLoanIQ(ivImportVerzeichnis + "\\" + ivKEVInputDatei);
    verarbeiteKEVLoanIQ();

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    lvCal = Calendar.getInstance();
    LOGGER_LOANIQ_KEV.info(this.getStatistik());

    // KundeRequest-Datei schliessen
    ivKundennummernOutput.close();

    LOGGER_LOANIQ_KEV.info("Ende: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
  }

  /**
   * Liest die LoanIQKEV-Datei/-Daten ein
   * @param pvDateiname Dateiname der LoanIQKEV-Datei
   */
  private void readKEVLoanIQ(String pvDateiname)
  {
    ivZaehlerVorlaufsatz = 0;
    ivZaehlerFinanzgeschaefte = 0;
    String lvZeile = null;
    ivVorlaufsatz = new Vorlaufsatz(LOGGER_LOANIQ_KEV);

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileLoanIQ = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileLoanIQ);
    }
    catch (Exception e)
    {
      LOGGER_LOANIQ_KEV.error("Konnte LoanIQKEV-Datei nicht oeffnen!");
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
    boolean lvStart = true;

    try
    {
      while ((lvZeile = lvIn.readLine()) != null)  // Lesen MIDAS-Datei
      {
        if (lvStart)
        {
          ivVorlaufsatz.parseVorlaufsatz(lvZeile);
          ivOutputDarlehenXML.printTXSHeader(DatumUtilities.changeDate(ivVorlaufsatz.getBuchungsdatum()));
          ivZaehlerVorlaufsatz++;
          lvStart = false;

          // KundeRequest-Datei oeffnen
          ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_LOANIQ_KEV);
          ivKundennummernOutput.open();
          ivKundennummernOutput.printVorlaufsatz(ivVorlaufsatz.getInstitutsnummer(), "KEV");
        }
        else
        {
          // Darlehen initialisieren
          Darlehen lvDarlehen = new Darlehen(LOGGER_LOANIQ_KEV);
          if (lvDarlehen.parseDarlehen(lvZeile)) // Parsen der Felder
          {
            ivZaehlerFinanzgeschaefte++;
            if (lvDarlehen.getKennzeichenBruttoNettoFremd().equals("N"))
            {
              ivListeGeschaefte.put(lvDarlehen.getKontonummer(), lvDarlehen);
            }
          }
          else
          { 	// Fehlerhafte Zeile eingelesen, trotzdem Zaehler um eins erhoehen
            LOGGER_LOANIQ_KEV.error("Fehlerhafte Zeile...");
            LOGGER_LOANIQ_KEV.error(lvZeile);
            ivZaehlerFinanzgeschaefte++;
          }
        }
      }
    }
    catch (Exception e)
    {
      LOGGER_LOANIQ_KEV.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_LOANIQ_KEV.error("Konnte LoanIQKEV-Datei nicht schliessen!");
    }
  }

  /**
   * Liefert eine Statistik als String
   * @return String mit Statistik
   */
  private String getStatistik()
  {
    StringBuilder lvOut = new StringBuilder();
    lvOut.append(ivVorlaufsatz.toString());
    lvOut.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivZaehlerFinanzgeschaefte + " Finanzgeschaefte gelesen..." + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlA0 + " mit Ausplatzierungsmerkmal A0" + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlA1 + " mit Ausplatzierungsmerkmal A1" + StringKonverter.lineSeparator);
    lvOut.append(ivAnzahlA2 + " mit Ausplatzierungsmerkmal A2" + StringKonverter.lineSeparator);
    lvOut.append(ivRestkapitalNull + " mit Restkapital 0.0 und TXS-relevantem Ausplatzierungsmerkmal" + StringKonverter.lineSeparator);
    return lvOut.toString();
  }

  /**
   * Verarbeite die KEV-Geschaefte
   */
  private void verarbeiteKEVLoanIQ()
  {
    Collection<Darlehen> lvCollectionGeschaefte = ivListeGeschaefte.values();
    Iterator<Darlehen> lvIterGeschaefte = lvCollectionGeschaefte.iterator();

    while (lvIterGeschaefte.hasNext())
    {
      Darlehen lvDarlehen = lvIterGeschaefte.next();
      LOGGER_LOANIQ_KEV.info("Konto: " + lvDarlehen.getKontonummer() + " - KennzeichenNettoBruttoFremd: " + lvDarlehen
          .getKennzeichenBruttoNettoFremd());
      if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("A") && !lvDarlehen.getAusplatzierungsmerkmal().equals("AF"))
      {
        if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("A0")) ivAnzahlA0++;
        if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("A1")) ivAnzahlA1++;
        if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("A2")) ivAnzahlA2++;

        if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("A0") || lvDarlehen.getAusplatzierungsmerkmal().startsWith("A1"))
        {
          try
          {
            // Buergennummer in die KundeRequest-Datei schreiben
            if (!lvDarlehen.getBuergennummer().isEmpty() && !lvDarlehen.getBuergennummer().equals("9999999999"))
            {
              if (!ivListeKundennummern.contains(MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_LOANIQ_KEV)))
              {
                ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_LOANIQ_KEV));
                ivListeKundennummern.add(MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_LOANIQ_KEV));
              }
            }
            // Kundennummer in die KundeRequest-Datei schreiben
            if (!lvDarlehen.getKundennummer().isEmpty() && !lvDarlehen.getKundennummer().equals("9999999999"))
            {
              if (!ivListeKundennummern.contains(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_LOANIQ_KEV)))
              {
                ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_LOANIQ_KEV));
                ivListeKundennummern.add(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_LOANIQ_KEV));
              }
            }
          }
          catch (Exception exp)
          {
            LOGGER_LOANIQ_KEV.error("Konnte Buergen- und Kundennummer nicht in KundeRequest-Datei schreiben...");
          }

          if (StringKonverter.convertString2Double(lvDarlehen.getRestkapital()) > 0.0)
          {
            importDarlehen2Transaktion(lvDarlehen);
          }
          else
          {
            LOGGER_LOANIQ_KEV.info("Kontonummer " + lvDarlehen.getKontonummer() + " nicht verarbeitet - Ausplatzierungsmerkmal " +
                                   lvDarlehen.getAusplatzierungsmerkmal() + " und Restkapital " + lvDarlehen.getRestkapital());
          }
        }
      }
    }
  }

  /**
   * Importiert die Darlehensinformationen in die TXS-Transaktionen
   * @param pvDarlehen Darlehen aus LoanIQ
   */
  private void importDarlehen2Transaktion(Darlehen pvDarlehen)
  {
    ivFinanzgeschaeft.initTXSFinanzgeschaeft();
    ivSliceInDaten.initTXSSliceInDaten();
    ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
    ivKondDaten.initTXSKonditionenDaten();
    ivKredkunde.initTXSKreditKunde();

    boolean lvOkayDarlehen = ivFinanzgeschaeft.importKEVLoanIQ(pvDarlehen, ivVorlaufsatz);

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivFinanzgeschaeftDaten.importKEVLoanIQ(pvDarlehen, ivVorlaufsatz, LOGGER_LOANIQ_KEV);
    }

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivSliceInDaten.importKEVLoanIQ(pvDarlehen, ivVorlaufsatz, LOGGER_LOANIQ_KEV);
    }

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivKondDaten.importKEVLoanIQ(pvDarlehen, LOGGER_LOANIQ_KEV);
    }

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivKredkunde.importKEVLoanIQ(pvDarlehen, ivVorlaufsatz);
    }

    // Transaktionen in die Datei schreiben
    if (lvOkayDarlehen)
    {
      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionStart());

      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionStart());
      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionDaten());
      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeftDaten.printTXSTransaktionEnde());

      ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionStart());
      ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionDaten());
      ivOutputDarlehenXML.printTransaktion(ivSliceInDaten.printTXSTransaktionEnde());

      ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionStart());
      ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionDaten());
      ivOutputDarlehenXML.printTransaktion(ivKondDaten.printTXSTransaktionEnde());

      ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionStart());
      ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionDaten());
      ivOutputDarlehenXML.printTransaktion(ivKredkunde.printTXSTransaktionEnde());
    }

    // Ermittle Kredittyp
    int lvKredittyp = ValueMapping.ermittleKredittyp(pvDarlehen.getAusplatzierungsmerkmal(), pvDarlehen
        .getBuergschaftprozent()); //pvDarlehen.ermittleKredittyp();
    // Sonder-/Uebergangsloesung MIDAS -> Ausplatzierungsmerkmal nicht vorhanden
    // Nicht verwendete Produktschluessel 450, 503, 505, 802, 805 und 811
    if (pvDarlehen.getProduktschluessel().equals("402") || pvDarlehen.getProduktschluessel().equals("404") || pvDarlehen
        .getProduktschluessel().equals("412"))
    {
      if (!pvDarlehen.getBuergschaftprozent().isEmpty())
      {
        lvKredittyp = Darlehen.VERBUERGT_KOMMUNAL;
      }
      else
      {
        lvKredittyp = Darlehen.REIN_KOMMUNAL;
      }
    }
    if (pvDarlehen.getProduktschluessel().equals("821") || pvDarlehen.getProduktschluessel().equals("827"))
    {
      lvKredittyp = Darlehen.FLUGZEUGDARLEHEN;
    }

    LOGGER_LOANIQ_KEV.info("Konto " + pvDarlehen.getKontonummer() + " lvKredittyp: " + lvKredittyp);

    ////if (lvOkayDarlehen)
    ////{
      // Wenn SAP CMS Daten geladen, dann verarbeiten
    ////  if (ivSapcms != null)
    ////  {
    ////    LOGGER_LOANIQ_KEV.info("Sicherheiten;" +ivFinanzgeschaeft.getKey() + ";" + ivSapcms.getSicherheitId(ivFinanzgeschaeft.getKey()) + ";");
        ////if (lvKredittyp == Darlehen.HYPOTHEK_1A)//|| lvKredittyp == Darlehen.REIN_KOMMUNAL)
        ////  {
        // Es wird protokolliert, ob SAP CMS vorliegen
        //if (!ivSapcms.existsSicherheitObjekt(ivDarlehen.getKontonummer()))
        //{
        //   LOGGER_LOANIQ.info("Kein SAP CMS;" + ivDarlehen.getKontonummer());
        //}
        //else
        //{
        //    LOGGER_LOANIQ.info("SAP CMS vorhanden;" + ivDarlehen.getKontonummer());
        //}
        //String helpKredittyp = new String();
        //if (lvKredittyp == Darlehen.HYPOTHEK_1A)
        //{
        //    helpKredittyp = "A";
        //}
        //if (lvKredittyp == Darlehen)
        //{
        //    helpKredittyp = "A";
        //}
        //ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitObjekte(ivDarlehen.getKontonummer(), "A", ivDarlehen.getBuergschaftprozent(), ivDarlehen.getDeckungsschluessel()));
        ////ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitObjekte(ivDarlehenLoanIQBlock.getKontonummer(), new String(), "A", lvHelpDarlehenLoanIQ.getBuergschaftprozent()));
        ////ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitKEV(ivFinanzgeschaeft.getKey(), new String(), "A", pvDarlehen.getRestkapital(), pvDarlehen.getBuergschaftprozent(), pvDarlehen.getAusplatzierungsmerkmal()));
        ////}
      ////}
    ////}

    if (lvOkayDarlehen)
    {
      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
    }
  }
}