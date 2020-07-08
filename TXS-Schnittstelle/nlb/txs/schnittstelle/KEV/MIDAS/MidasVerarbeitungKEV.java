/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.KEV.MIDAS;

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
import nlb.txs.schnittstelle.Utilities.MappingMIDAS;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import org.apache.log4j.Logger;

public class MidasVerarbeitungKEV
{
  // Logger fuer KEV MIDAS
  private static Logger LOGGER_MIDAS_KEV = Logger.getLogger("MidasKEVLogger");

  /**
   * Import-Verzeichnis der MIDAS-Datei
   */
  private String ivImportVerzeichnis;

  /**
   * Export-Verzeichnis der TXS-Importdatei
   */
  private String ivExportVerzeichnis;

  /**
   * Dateiname der MidasKEV-Datei
   */
  private String ivMidasKEVInputDatei;

  /**
   * Dateiname der MidasKEV TXS-Importdatei
   */
  private String ivMidasKEVOutputDatei;

  /**
   * Import-Verzeichnis der Sicherheiten-Datei
   */
  //private String ivImportVerzeichnisSAPCMS;

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
   * Konstruktor fuer KEV Verarbeitung MIDAS
   * @param pvReader Verweis auf den IniReader
   */
  public MidasVerarbeitungKEV(IniReader pvReader)
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
      /*
      ivInstitutsnummer = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
      if (ivInstitutsnummer.equals("Fehler"))
      {
        LOGGER_MIDAS_KEV.error("Keine Institutsnummer in der ini-Datei...");
        System.exit(1);
      } */

      ivImportVerzeichnis = pvReader.getPropertyString("MidasKEV", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_MIDAS_KEV.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("MidasKEV", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_MIDAS_KEV.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }

      ivMidasKEVInputDatei =  pvReader.getPropertyString("MidasKEV", "MidasKEVInput-Datei", "Fehler");
      if (ivMidasKEVInputDatei.equals("Fehler"))
      {
        LOGGER_MIDAS_KEV.error("Kein MIDASInput-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      ivMidasKEVOutputDatei =  pvReader.getPropertyString("MidasKEV", "MidasKEVOutput-Datei", "Fehler");
      if (ivMidasKEVOutputDatei.equals("Fehler"))
      {
        LOGGER_MIDAS_KEV.error("Kein MIDASOutput-Dateiname in der ini-Datei...");
        System.exit(1);
      }
      /*
      ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("Sicherheiten", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
      {
        LOGGER_MIDAS_KEV.error("Kein Sicherheiten Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }
      */

      ivKundeRequestDatei = pvReader.getPropertyString("MidasKEV", "KundeRequestDatei", "Fehler");
      if (ivKundeRequestDatei.equals("Fehler"))
      {
        LOGGER_MIDAS_KEV.error("Kein KundeRequest-Dateiname in der ini-Datei...");
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

    LOGGER_MIDAS_KEV.info("Start: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);

    // Darlehen XML-Datei im TXS-Format
    ivOutputDarlehenXML = new OutputDarlehenXML(ivExportVerzeichnis + "\\" + ivMidasKEVOutputDatei, LOGGER_MIDAS_KEV);
    ivOutputDarlehenXML.openXML();
    ivOutputDarlehenXML.printXMLStart();
    ivOutputDarlehenXML.printTXSImportDatenStart();

    readMIDAS(ivImportVerzeichnis + "\\" + ivMidasKEVInputDatei);
    verarbeiteMIDAS();

    ivOutputDarlehenXML.printTXSImportDatenEnde();
    ivOutputDarlehenXML.closeXML();

    lvCal = Calendar.getInstance();
    LOGGER_MIDAS_KEV.info(this.getStatistik());

    // KundeRequest-Datei schliessen
    ivKundennummernOutput.close();

    LOGGER_MIDAS_KEV.info("Ende: " + lvCh.printDateAndTime(lvCal) + StringKonverter.lineSeparator);
  }

  /**
   * Liest die MIDAS-Datei/-Daten ein
   * @param pvDateiname Dateiname der MIDAS-Datei
   */
  private void readMIDAS(String pvDateiname)
  {
    ivZaehlerVorlaufsatz = 0;
    ivZaehlerFinanzgeschaefte = 0;
    String lvZeile = null;
    ivVorlaufsatz = new Vorlaufsatz(LOGGER_MIDAS_KEV);

    // Oeffnen der Dateien
    FileInputStream lvFis = null;
    File ivFileLoanIQ = new File(pvDateiname);
    try
    {
      lvFis = new FileInputStream(ivFileLoanIQ);
    }
    catch (Exception e)
    {
      LOGGER_MIDAS_KEV.error("Konnte MIDAS-Datei nicht oeffnen!");
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
          ivKundennummernOutput = new KundennummernOutput(ivExportVerzeichnis + "\\" + ivKundeRequestDatei, LOGGER_MIDAS_KEV);
          ivKundennummernOutput.open();
          ivKundennummernOutput.printVorlaufsatz(ivVorlaufsatz.getInstitutsnummer(), "Pfandbrief");
        }
        else
        {
          // Darlehen initialisieren
          Darlehen lvDarlehen = new Darlehen(LOGGER_MIDAS_KEV);
          if (lvDarlehen.parseDarlehen(lvZeile)) // Parsen der Felder
          {
            ivZaehlerFinanzgeschaefte++;
            ivListeGeschaefte.put(MappingMIDAS
                .ermittleMIDASKontonummer(lvDarlehen.getQuellsystem(), lvDarlehen.getKontonummer()),
                lvDarlehen);
          }
          else
          { 	// Fehlerhafte Zeile eingelesen, trotzdem Zaehler um eins erhoehen
            LOGGER_MIDAS_KEV.error("Fehlerhafte Zeile...");
            LOGGER_MIDAS_KEV.error(lvZeile);
            ivZaehlerFinanzgeschaefte++;
          }
        }
      }
    }
    catch (Exception e)
    {
      LOGGER_MIDAS_KEV.error("Fehler beim Verarbeiten einer Zeile!");
      e.printStackTrace();
    }

    try
    {
      lvIn.close();
    }
    catch (Exception e)
    {
      LOGGER_MIDAS_KEV.error("Konnte MIDAS-Datei nicht schliessen!");
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
   * Verarbeite die MIDAS-Geschaefte
   */
  private void verarbeiteMIDAS()
  {
    Collection<Darlehen> lvCollectionGeschaefte = ivListeGeschaefte.values();
    Iterator<Darlehen> lvIterGeschaefte = lvCollectionGeschaefte.iterator();

    while (lvIterGeschaefte.hasNext())
    {
      Darlehen lvDarlehen = lvIterGeschaefte.next();
      //LOGGER_MIDAS.info("Konto: " + lvDarlehen.getKontonummer());
      if (lvDarlehen.getKennzeichenBruttoNettoFremd().equals("N") &&
          (lvDarlehen.getAusplatzierungsmerkmal().startsWith("A0") || lvDarlehen.getAusplatzierungsmerkmal().startsWith("A1") ||
              lvDarlehen.getAusplatzierungsmerkmal().startsWith("A2")))
      {
        /*
        // Wenn Konsortial, dann Netto-Zeile verwenden - CT 02.07.2019
        if (lvDarlehen.getKennzeichenKonsortialkredit().equals("J"))
        {
          for (Darlehen lvHelpDarlehen :ivListeGeschaefte.values())
          {
            // Netto-Zeile ueber Gegenkontonummer ermitteln
            if (lvHelpDarlehen.getKennzeichenBruttoNettoFremd().equals("N") && lvHelpDarlehen.getGegenkontoPassiv().trim().equals(
                lvDarlehen.getKontonummer().trim()))
            {
              // Die Kontonummer aus der Brutto-Zeile muss verwendet werden
              //lvHelpDarlehen.setKontonummer(lvDarlehen.getKontonummer());
              lvDarlehen = lvHelpDarlehen; // Netto-Zeile verwenden
              break; // Gefunden -> Abbruch der Suche
            }
          }
        } */

        if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("A0")) ivAnzahlA0++;
        if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("A1")) ivAnzahlA1++;
        if (lvDarlehen.getAusplatzierungsmerkmal().startsWith("A2")) ivAnzahlA2++;

        try
        {
          // Buergennummer in die KundeRequest-Datei schreiben
          if (!lvDarlehen.getBuergennummer().isEmpty() && !lvDarlehen.getBuergennummer().equals("9999999999"))
          {
            if (!ivListeKundennummern.contains(
                MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_MIDAS_KEV)))
            {
              ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_MIDAS_KEV));
              ivListeKundennummern.add(MappingKunde.extendKundennummer(lvDarlehen.getBuergennummer(), LOGGER_MIDAS_KEV));
            }
          }
          // Kundennummer in die KundeRequest-Datei schreiben
          if (!lvDarlehen.getKundennummer().isEmpty() && !lvDarlehen.getKundennummer().equals("9999999999"))
          {
            if (!ivListeKundennummern.contains(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_MIDAS_KEV)))
            {
              ivKundennummernOutput.printKundennummer(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_MIDAS_KEV));
              ivListeKundennummern.add(MappingKunde.extendKundennummer(lvDarlehen.getKundennummer(), LOGGER_MIDAS_KEV));
            }
          }
        }
        catch (Exception exp)
        {
          LOGGER_MIDAS_KEV.error("Konnte Buergen- und Kundennummer nicht in KundeRequest-Datei schreiben...");
        }

        if (StringKonverter.convertString2Double(lvDarlehen.getRestkapital()) > 0.0)
        {
          importDarlehen2Transaktion(lvDarlehen);
        }
        else
        {
          LOGGER_MIDAS_KEV.info("Kontonummer " + lvDarlehen.getKontonummer() + " nicht verarbeitet - Ausplatzierungsmerkmal " + lvDarlehen
              .getAusplatzierungsmerkmal() + " und Restkapital " + lvDarlehen.getRestkapital());
        }
      }
    }

  }

  /**
   * Importiert die Darlehensinformationen in die TXS-Transaktionen
   * @param pvDarlehen
   */
  private void importDarlehen2Transaktion(Darlehen pvDarlehen)
  {
    ivFinanzgeschaeft.initTXSFinanzgeschaeft();
    ivSliceInDaten.initTXSSliceInDaten();
    ivFinanzgeschaeftDaten.initTXSFinanzgeschaeftDaten();
    ivKondDaten.initTXSKonditionenDaten();
    ivKredkunde.initTXSKreditKunde();

    boolean lvOkayDarlehen = ivFinanzgeschaeft.importMIDAS(pvDarlehen, ivVorlaufsatz);

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivFinanzgeschaeftDaten.importMIDAS(pvDarlehen, ivVorlaufsatz, LOGGER_MIDAS_KEV);
    }

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivSliceInDaten.importMIDAS(pvDarlehen, ivVorlaufsatz, LOGGER_MIDAS_KEV);
    }

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivKondDaten.importMIDAS(pvDarlehen, LOGGER_MIDAS_KEV);
    }

    if (lvOkayDarlehen)
    {
      lvOkayDarlehen = ivKredkunde.importMIDAS(pvDarlehen, ivVorlaufsatz);
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

    LOGGER_MIDAS_KEV.info("Konto " + pvDarlehen.getKontonummer() + " lvKredittyp: " + lvKredittyp);

    /*
    if (lvOkayDarlehen)
    {
      // Wenn SAP CMS Daten geladen, dann verarbeiten
      if (ivSapcms != null)
      {
        LOGGER_MIDAS.info("Sicherheiten;" +ivFinanzgeschaeft.getKey() + ";" + ivSapcms.getSicherheitId(ivFinanzgeschaeft.getKey()) + ";");
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
        ivOutputDarlehenXML.printTransaktion(ivSapcms.importSicherheitMIDAS(ivFinanzgeschaeft.getKey(), new String(), "A", pvDarlehen.getRestkapital(), pvDarlehen.getBuergschaftprozent(), pvDarlehen.getAusplatzierungsmerkmal()));
        ////}
      }
    } */

    if (lvOkayDarlehen)
    {
      ivOutputDarlehenXML.printTransaktion(ivFinanzgeschaeft.printTXSTransaktionEnde());
    }

    /*
    if (lvOkayDarlehen)
    {
      // Default-Finanzierung fuer Flugzeuge anlegen
      if (lvKredittyp == Darlehen.FLUGZEUGDARLEHEN)
      {
        // TXSFinanzgeschaeft setzen --> eigentlich Finanzierung!!!
        TXSFinanzgeschaeft lvFinanzierung = new TXSFinanzgeschaeft();
        lvFinanzierung.setKey(pvDarlehen.getKontonummer());
        lvFinanzierung.setOriginator(ValueMapping.changeMandant(ivVorlaufsatz.getInstitutsnummer()));
        lvFinanzierung.setQuelle("AMIDFLUG");

        // TXSFinanzgeschaeftDaten setzen
        TXSFinanzgeschaeftDaten lvFgdaten = new TXSFinanzgeschaeftDaten();
        lvFgdaten.setAz(pvDarlehen.getKontonummer());
        lvFgdaten.setAktivpassiv("1");
        lvFgdaten.setKat("8");
        lvFgdaten.setTyp("71");

        // TXSKonditionenDaten setzen
        TXSKonditionenDaten lvKond = new TXSKonditionenDaten();
        lvKond.setKondkey(pvDarlehen.getKontonummer());

        // TXSFinanzierungKredit
        ivOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvFgdaten.printTXSTransaktionEnde());
        ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionStart());
        ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionDaten());
        ivOutputDarlehenXML.printTransaktion(lvKond.printTXSTransaktionEnde());
        ivOutputDarlehenXML.printTransaktion(lvFinanzierung.printTXSTransaktionEnde());
      }
    } */
  }

}
