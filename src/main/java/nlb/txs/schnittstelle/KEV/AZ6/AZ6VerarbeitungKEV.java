/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.KEV.AZ6;

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

public class AZ6VerarbeitungKEV
{
  // Logger fuer KEV AZ6
  private static Logger LOGGER_AZ6_KEV = Logger.getLogger("AZ6KEVLogger");

  /**
   * Import-Verzeichnis der AZ6-Datei
   */
  private String ivImportVerzeichnis;

  /**
   * Export-Verzeichnis der TXS-Importdatei
   */
  private String ivExportVerzeichnis;

  /**
   * Dateiname der AZ6KEV-Datei
   */
  private String ivKEVInputDatei;

  /**
   * Dateiname der AZ6KEV TXS-Importdatei
   */
  private String ivKEVOutputDatei;

  /**
   * Import-Verzeichnis der Sicherheiten-Datei
   */
  private String ivImportVerzeichnisVVS;

  /**
   * Name der KundeRequest-Datei
   */
  private String ivKundeRequestDatei;

  /**
   * Ausgabedatei der Kundennummern
   */
  private KundennummernOutput ivKundennummernOutput;

  /**
   * Vorlaufsatz der AZ6-Datei
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
   * Konstruktor fuer KEV Verarbeitung AZ6
   * @param pvReader Verweis auf den IniReader
   */
  public AZ6VerarbeitungKEV(IniReader pvReader)
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

      ivImportVerzeichnis = pvReader.getPropertyString("AZ6KEV", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_AZ6_KEV.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6_KEV.info("ImportVerzeichnis: " + ivImportVerzeichnis);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("AZ6KEV", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_AZ6_KEV.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6_KEV.info("ExportVerzeichnis: " + ivExportVerzeichnis);
      }

      ivKEVInputDatei =  pvReader.getPropertyString("AZ6KEV", "KEVInput-Datei", "Fehler");
      if (ivKEVInputDatei.equals("Fehler"))
      {
        LOGGER_AZ6_KEV.error("Kein KEVInput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6_KEV.info("KEVInput-Datei: " + ivKEVInputDatei);
      }

      ivKEVOutputDatei =  pvReader.getPropertyString("AZ6KEV", "KEVOutput-Datei", "Fehler");
      if (ivKEVOutputDatei.equals("Fehler"))
      {
        LOGGER_AZ6_KEV.error("Kein KEVOutput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6_KEV.info("KEVOutput-Datei: " + ivKEVOutputDatei);
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

      ivKundeRequestDatei = pvReader.getPropertyString("AZ6KEV", "KundeRequestDatei", "Fehler");
      if (ivKundeRequestDatei.equals("Fehler"))
      {
        LOGGER_AZ6_KEV.error("Kein KundeRequest-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      else
      {
        LOGGER_AZ6_KEV.info("KundeRequestDatei: " + ivKundeRequestDatei);
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

    LOGGER_AZ6_KEV.info("Start: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);

    ////ivSapcms = new SAPCMS_Neu(ivImportVerzeichnisSAPCMS + "\\" + ivSapcmsDatei, LOGGER_LOANIQ_KEV);

    // Darlehen XML-Datei im TXS-Format
    //System.out.println(ivExportVerzeichnis + "\\" + ivKEVOutputDatei);
    ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivKEVOutputDatei, LOGGER_AZ6_KEV);
    ivOutputDarlehenXML.openXML();
    ivOutputDarlehenXML.printXMLStart();
    ivOutputDarlehenXML.printTXSImportDatenStart();

    readKEVAZ6(ivImportVerzeichnis + "\\" + ivKEVInputDatei);
    verarbeiteKEVAZ6();

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    lvCal = Calendar.getInstance();
    LOGGER_AZ6_KEV.info(this.getStatistik());

    // KundeRequest-Datei schliessen
    ivKundennummernOutput.close();

    LOGGER_AZ6_KEV.info("Ende: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
  }

  /**
   * Liest die AZ6-Datei/-Daten ein
   * @param pvDateiname Dateiname der AZ6-Datei
   */
  private void readKEVAZ6(String pvDateiname)
  {
    ivZaehlerVorlaufsatz = 0;
    ivZaehlerFinanzgeschaefte = 0;
    String lvZeile = null;
    ivVorlaufsatz = new Vorlaufsatz(LOGGER_AZ6_KEV);

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileLoanIQ = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileLoanIQ);
    }
    catch (Exception e)
    {
      LOGGER_AZ6_KEV.error("Konnte AZ6-Datei nicht oeffnen!");
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
          ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_AZ6_KEV);
          ivKundennummernOutput.open();
          ivKundennummernOutput.printVorlaufsatz(ivVorlaufsatz.getInstitutsnummer(), "KEV");
        }
        else
        {
          // Darlehen initialisieren
          Darlehen lvDarlehen = new Darlehen(LOGGER_AZ6_KEV);
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
            LOGGER_AZ6_KEV.error("Fehlerhafte Zeile...");
            LOGGER_AZ6_KEV.error(lvZeile);
            ivZaehlerFinanzgeschaefte++;
          }
        }
      }
    }
    catch (Exception e)
    {
      LOGGER_AZ6_KEV.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_AZ6_KEV.error("Konnte AZ6-Datei nicht schliessen!");
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
  private void verarbeiteKEVAZ6()
  {
    Collection<Darlehen> lvCollectionGeschaefte = ivListeGeschaefte.values();
    Iterator<Darlehen> lvIterGeschaefte = lvCollectionGeschaefte.iterator();

    while (lvIterGeschaefte.hasNext())
    {
      Darlehen lvDarlehen = lvIterGeschaefte.next();
      LOGGER_AZ6_KEV.info("Konto: " + lvDarlehen.getKontonummer() + " - KennzeichenNettoBruttoFremd: " + lvDarlehen
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
              if (!ivListeKundennummern.contains(
                  MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_AZ6_KEV)))
              {
                ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_AZ6_KEV));
                ivListeKundennummern.add(MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_AZ6_KEV));
              }
            }
            // Kundennummer in die KundeRequest-Datei schreiben
            if (!lvDarlehen.getKundennummer().isEmpty() && !lvDarlehen.getKundennummer().equals("9999999999"))
            {
              if (!ivListeKundennummern.contains(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_AZ6_KEV)))
              {
                ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_AZ6_KEV));
                ivListeKundennummern.add(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_AZ6_KEV));
              }
            }
          }
          catch (Exception exp)
          {
            LOGGER_AZ6_KEV.error("Konnte Buergen- und Kundennummer nicht in KundeRequest-Datei schreiben...");
          }

          if (StringKonverter.convertString2Double(lvDarlehen.getRestkapital()) > 0.0)
          {
            importDarlehen2Transaktion(lvDarlehen);
          }
          else
          {
            LOGGER_AZ6_KEV.info("Kontonummer " + lvDarlehen.getKontonummer() + " nicht verarbeitet - Ausplatzierungsmerkmal " +
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

    boolean lvOkayDarlehen = ivFinanzgeschaeft.importKEVAZ6(pvDarlehen, ivVorlaufsatz);

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivFinanzgeschaeftDaten.importKEVLoanIQ(pvDarlehen, ivVorlaufsatz, LOGGER_AZ6_KEV);
      ivFinanzgeschaeftDaten.setAnwendungsnummer("30"); // AZ6 -> Anwendungsnummer 30
    }

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivSliceInDaten.importKEVLoanIQ(pvDarlehen, ivVorlaufsatz, LOGGER_AZ6_KEV);
    }

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivKondDaten.importKEVLoanIQ(pvDarlehen, LOGGER_AZ6_KEV);
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
    int lvKredittyp = ValueMapping
        .ermittleKredittyp(pvDarlehen.getAusplatzierungsmerkmal(), pvDarlehen
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

    LOGGER_AZ6_KEV.info("Konto " + pvDarlehen.getKontonummer() + " lvKredittyp: " + lvKredittyp);

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
