/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.AZ6.RefiRegister;

import nlb.txs.schnittstelle.Sicherheiten.SicherheitenDaten;
import nlb.txs.schnittstelle.Utilities.IniReader;
import org.apache.log4j.Logger;

public class AZ62RefiRegisterVerarbeitung {

  // Logger fuer LoanIQ
  private static Logger LOGGER_AZ62RefiRegister = Logger.getLogger("TXSAZ62RefiRegisterLogger");

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

      ivImportVerzeichnis = pvReader.getPropertyString("AZ6", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnis.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }

      ivExportVerzeichnis = pvReader.getPropertyString("AZ62RefiRegister", "ExportVerzeichnis", "Fehler");
      if (ivExportVerzeichnis.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }

      ivAZ6InputDatei =  pvReader.getPropertyString("AZ6", "AZ6Input-Datei", "Fehler");
      if (ivAZ6InputDatei.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein AZ6Input-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      ivAZ62RefiRegisterOutputDatei =  pvReader.getPropertyString("AZ62RefiRegister", "AZ62RefiRegisterOutput-Datei", "Fehler");
      if (ivAZ62RefiRegisterOutputDatei.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein AZ62RefiRegisterOutput-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      ivImportVerzeichnisSAPCMS = pvReader.getPropertyString("Sicherheiten", "ImportVerzeichnis", "Fehler");
      if (ivImportVerzeichnisSAPCMS.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Sicherheiten Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }

      ivSapcmsDatei = pvReader.getPropertyString("Sicherheiten", "Sicherheiten-Datei", "Fehler");
      if (ivSapcmsDatei.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Sicherheiten-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      /* ivFilterDatei = pvReader.getPropertyString("LoanIQ2RefiRegister", "Filter-Datei", "Fehler");
      if (ivFilterDatei.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Filter-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      ivFilterDateiBB = pvReader.getPropertyString("LoanIQ2RefiRegister", "Filter-Datei-BB", "Fehler");
      if (ivFilterDateiBB.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein Filter-Dateiname fuer BB in der ini-Datei...");
        System.exit(1);
      }

      ivKundeRequestDatei = pvReader.getPropertyString("AZ62RefiRegister", "KundeRequestDatei", "Fehler");
      if (ivKundeRequestDatei.equals("Fehler"))
      {
        LOGGER_AZ62RefiRegister.error("Kein KundeRequest-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      ivListeKontonummern = new HashSet<String>();
      // Dummy-Kontonummer damit die Liste nicht leer wird
      ivListeKontonummern.add("99999999999");

      ivListeKontonummernBB = new HashSet<String>();
      // Dummy-Kontonummer damit die Liste nicht leer wird
      ivListeKontonummernBB.add("99999999999");

      ivListeRefiDeepSeaZusatz = new HashMap<String, RefiDeepSeaZusatz>();

      // RefiDeepSeaZusatz einlesen - CT 26.03.2019
      ivListeRefiDeepSeaZusatz = readRefiDeepSeaZusatz(ivExportVerzeichnis + "\\BB_20190401.csv");

      // Liste der Kontonummern einlesen
      readListeKontonummern(ivListeKontonummern, ivExportVerzeichnis + "\\" + ivFilterDatei);

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

  }
}