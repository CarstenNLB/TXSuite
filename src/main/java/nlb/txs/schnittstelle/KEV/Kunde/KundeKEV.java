/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.KEV.Kunde;

import nlb.txs.schnittstelle.Kunde.KundeVerarbeitung;
import nlb.txs.schnittstelle.Utilities.IniReader;
import org.apache.log4j.xml.DOMConfigurator;

public class KundeKEV
{
  /**
   * Startroutine
   *
   * @param argv
   */
  public static void main(String[] argv) {
    if (!argv[argv.length - 1].endsWith(".ini")) {
      System.out.println("Starten:");
      System.out.println("Kunde <Verarbeitungsmodus> <ini-Datei>");
      System.exit(1);
    } else {
      IniReader lvReader = null;
      try
      {
        lvReader = new IniReader(argv[argv.length - 1]);
      }
      catch (Exception exp)
      {
        System.out.println("Fehler beim Laden der ini-Datei...");
        System.exit(1);
      }

      String lvLoggingXML = lvReader.getPropertyString("Kunde_" + argv[argv.length - 2], "log4jconfig", "Fehler");
      if (lvLoggingXML.equals("Fehler"))
      {
        System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
      }
      else
      {
        DOMConfigurator.configure(lvLoggingXML);
      }

      new KundeVerarbeitung(KundeVerarbeitung.KEV, argv[argv.length - 2], lvReader);

    }
    System.exit(0);
  }

}
