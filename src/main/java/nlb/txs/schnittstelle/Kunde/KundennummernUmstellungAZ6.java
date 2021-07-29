/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Kunde;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class KundennummernUmstellungAZ6
{
  /**
   * log4j-Logger
   */
  private static Logger LOGGER = Logger.getLogger("TXSKundennummernUmstellungAZ6Logger");

  /**
   * Liste mit KundennummernUmstellungDaten
   */
  private HashMap<String, KundennummernUmstellungAZ6Daten> ivListeKundennummernUmstellungAZ6Daten;

  /**
   * Konstruktor
   * @param pvInstitut
   * @param pvImportVerzeichnis
   * @param pvExportVerzeichnis
   * @param pvKundennummernUmstellungDatei
   * @param pvInputDatei
   * @param pvOutputDatei
   */
  public KundennummernUmstellungAZ6(String pvInstitut, String pvImportVerzeichnis, String pvExportVerzeichnis, String pvKundennummernUmstellungDatei, String pvInputDatei, String pvOutputDatei)
  {
    // Initialisierung der Liste fuer die KundennummernUmstellungAZ6Daten
    ivListeKundennummernUmstellungAZ6Daten = new HashMap<String, KundennummernUmstellungAZ6Daten>();

    // Einlesen der KundennummernUmstellungDaten
    leseKundennummernUmstellungDaten(pvImportVerzeichnis + "\\" + pvKundennummernUmstellungDatei);

    // Statistik doppelter Kundennummern
    Collection<KundennummernUmstellungAZ6Daten> lvCollectionKundennummernUmstellungAZ6Daten = ivListeKundennummernUmstellungAZ6Daten.values();
    Iterator<KundennummernUmstellungAZ6Daten> lvIteratorKundennummernUmstellungAZ6Daten = lvCollectionKundennummernUmstellungAZ6Daten.iterator();
    while (lvIteratorKundennummernUmstellungAZ6Daten.hasNext())
    {
      KundennummernUmstellungAZ6Daten lvKundennummernUmstellungAZ6Daten = lvIteratorKundennummernUmstellungAZ6Daten.next();
      LOGGER.info(lvKundennummernUmstellungAZ6Daten.toString());
    }

    // Umstellung der Kundennummern
    umstellenKundennummern(pvImportVerzeichnis + "\\" + pvInputDatei, pvExportVerzeichnis + "\\" + pvOutputDatei);
  }

  /**
   * Einlesen der KundennummernUmstellungDaten
   * @param pvFilename
   */
  private void leseKundennummernUmstellungDaten(String pvFilename)
  {
    int lvZaehlerKundennummern = 0;
    File lvKundennummernUmstellung = new File(pvFilename);
    if (lvKundennummernUmstellung != null && lvKundennummernUmstellung.exists())
    {
      FileInputStream lvKundennummernUmstellungIs = null;
      BufferedReader lvKundennummernUmstellungIn = null;
      try
      {
        lvKundennummernUmstellungIs = new FileInputStream(lvKundennummernUmstellung);
        lvKundennummernUmstellungIn = new BufferedReader(new InputStreamReader(lvKundennummernUmstellungIs));
      }
      catch (Exception e)
      {
        LOGGER.error("Konnte Datei '" + lvKundennummernUmstellung.getAbsolutePath() + "' nicht oeffnen!");
      }
      String lvZeile = new String();
      boolean lvFirst = true;
      try
      {
        while ((lvZeile = lvKundennummernUmstellungIn.readLine()) != null)  // Einlesen KundennummernUmstellungDatei
        {
          if (lvFirst)
          {
            lvFirst = false;
          }
          else
          {
            if (lvZeile != null)
            {
              if (!lvZeile.startsWith("**** END OF FILE ****"))
              {
                KundennummernUmstellungAZ6Daten lvKundennummernUmstellungDaten = new KundennummernUmstellungAZ6Daten();
                lvKundennummernUmstellungDaten.parseDaten(lvZeile);
                ivListeKundennummernUmstellungAZ6Daten.put(lvKundennummernUmstellungDaten.getKdNrBLB(), lvKundennummernUmstellungDaten);
                LOGGER.info("Kundennummern-Umstellung: " + lvZeile);
                lvZaehlerKundennummern++;
              }
            }
          }
        }
      }
      catch (Exception e)
      {
        LOGGER.error("Fehler beim Verarbeiten einer Zeile:" + lvZeile);
        e.printStackTrace();
      }
      try
      {
        lvKundennummernUmstellungIn.close();
        lvKundennummernUmstellungIs.close();
      }
      catch (Exception e)
      {
        LOGGER.error("Konnte Datei '" + lvKundennummernUmstellung.getAbsolutePath() + "' nicht schliessen!");
      }
    }
    else
    {
      LOGGER.info("Keine Datei '" + lvKundennummernUmstellung.getAbsolutePath() + "' gefunden...");
    }
    LOGGER.info("Anzahl Kundennummern-Umstellungen: " + lvZaehlerKundennummern);
  }

  /**
   * Umstellen der Kundennummern
   * @param pvInputFilename
   * @param pvOutputFilename
   */
  private void umstellenKundennummern(String pvInputFilename, String pvOutputFilename)
  {
    LOGGER.info("Kundennummern umstellen...");
    LOGGER.info("Input-Datei: " + pvInputFilename);
    LOGGER.info("Output-Datei: " + pvOutputFilename);

    File lvInputDatei = new File(pvInputFilename);
    File lvOutputDatei = new File(pvOutputFilename);

    FileInputStream lvFis = null;
    FileOutputStream lvFos = null;

    boolean lvOkay = true;
    try
    {
      lvFis = new FileInputStream(lvInputDatei);
    }
    catch (Exception e)
    {
      LOGGER.error("Konnte Eingabe-Datei '" + pvInputFilename + "' nicht oeffnen!");
      lvOkay = false;
    }

    try
    {
      lvFos = new FileOutputStream(lvOutputDatei);
    }
    catch (Exception e)
    {
      LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputFilename + "' nicht oeffnen!");
      lvOkay = false;
    }

    if (!lvOkay)
    {
      return;
    }

    BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
    String lvZeile = new String();
    StringBuilder lvZeileNeu = new StringBuilder();

    int lvZeileCounter = 0;
    try
    {
      while ((lvZeile = lvIn.readLine()) != null)  // Einlesen der Input-Datei
      {
        StringBuilder lvTemp = new StringBuilder();  // Arbeitsbereich/Zwischenspeicher Feld
        int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
        int    lvZzStr=0;              // pointer fuer satzbereich

        // Steuerung/Iteration Eingabesatz
        for (lvZzStr=0; lvZzStr < lvZeile.length(); lvZzStr++)
        {
          // wenn Semikolon erkannt
          if (lvZeile.charAt(lvZzStr) == ';')
          {
            // Buergennummer
            //if (lvLfd == 33)
            //{
            //  if (ivListeKundennummernUmstellungAZ6Daten.containsKey(lvTemp.toString()))
            //  {
            //    KundennummernUmstellungAZ6Daten lvKundennummernUmstellungAZ6Daten = ivListeKundennummernUmstellungAZ6Daten.get(lvTemp.toString());
            //    LOGGER.info("Buergennummer von " + lvTemp.toString() + " auf " + lvKundennummernUmstellungAZ6Daten.getKdNrNLB() + " umgestellt.");
            //    lvTemp = new StringBuilder(lvKundennummernUmstellungAZ6Daten.getKdNrNLB());
            //  }
            //}

             // Kundennummer
            if (lvLfd == 61)
            {
              if (ivListeKundennummernUmstellungAZ6Daten.containsKey(lvTemp.toString()))
              {
                KundennummernUmstellungAZ6Daten lvKundennummernUmstellungAZ6Daten = ivListeKundennummernUmstellungAZ6Daten.get(lvTemp.toString());
                LOGGER.info("Kundennummer von " + lvTemp.toString() + " auf " + lvKundennummernUmstellungAZ6Daten.getKdNrNLB() + " umgestellt.");
                lvTemp = new StringBuilder(lvKundennummernUmstellungAZ6Daten.getKdNrNLB());
              }
            }

            lvZeileNeu.append(lvTemp + ";");
            // loeschen Zwischenbuffer
            lvTemp = new StringBuilder();
            lvLfd++;                  // naechste Feldnummer
          }
          else
          {
            // uebernehmen Byte aus Eingabesatzposition
            lvTemp.append(lvZeile.charAt(lvZzStr));
          }
        } // ende for

        lvFos.write((lvZeileNeu + "E" + StringKonverter.lineSeparator).getBytes());
        lvZeileNeu = new StringBuilder();
        lvZeileCounter++;
      }
    }
    catch (Exception exp)
    {
      LOGGER.error("Verarbeitungsfehler in Zeile " + lvZeileCounter);
      LOGGER.error(lvZeile);
      exp.printStackTrace();
    }

    try
    {
      lvIn.close();
      lvFis.close();
    }
    catch (Exception exp)
    {
      LOGGER.error("Konnte Eingabe-Datei '" + pvInputFilename + "' nicht schliessen!");
    }

    try
    {
      lvFos.close();
    }
    catch (Exception exp)
    {
      LOGGER.error("Konnte Ausgabe-Datei '" + pvOutputFilename + "' nicht schliessen!");
    }
  }

  /**
   * Startroutine
   * @param argv
   */
  public static void main(String[] argv)
  {
    if (!argv[argv.length - 1].endsWith(".ini"))
    {
      System.out.println("Starten:");
      System.out.println("KundennummernUmstellung <ini-Datei>");
      System.exit(1);
    }
    else
    {
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

      if (lvReader != null)
      {
        String lvLoggingXML = lvReader.getPropertyString("KundennummernUmstellungAZ6", "log4jconfig", "Fehler");
        if (lvLoggingXML.equals("Fehler"))
        {
          System.out.println("Keine log4j-Konfiguration in der ini-Datei...");
        }
        else
        {
          DOMConfigurator.configure(lvLoggingXML);
        }

        String lvInstitut = lvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
        if (lvInstitut.equals("Fehler"))
        {
          LOGGER.error("Keine Institutsnummer in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("Institutsnummer: " + lvInstitut);
        }

        String lvImportVerzeichnis = lvReader.getPropertyString("KundennummernUmstellungAZ6", "ImportVerzeichnis", "Fehler");
        if (lvImportVerzeichnis.equals("Fehler"))
        {
          LOGGER.error("Kein Import-Verzeichnis in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("ImportVerzeichnis: " + lvImportVerzeichnis);
        }

        String lvExportVerzeichnis = lvReader.getPropertyString("KundennummernUmstellungAZ6", "ExportVerzeichnis", "Fehler");
        if (lvExportVerzeichnis.equals("Fehler"))
        {
          LOGGER.error("Kein Export-Verzeichnis in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("ExportVerzeichnis: " + lvExportVerzeichnis);
        }

        String lvKundennummernUmstellungDatei =  lvReader.getPropertyString("KundennummernUmstellungAZ6", "KundennummernUmstellungDatei", "Fehler");
        if (lvKundennummernUmstellungDatei.equals("Fehler"))
        {
          LOGGER.error("Kein KundennummernUmstellungAZ6-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("KundennummernUmstellungAZ6-Dateiname: " + lvKundennummernUmstellungDatei);
        }

        String lvInputDatei =  lvReader.getPropertyString("KundennummernUmstellungAZ6", "InputDatei", "Fehler");
        if (lvInputDatei.equals("Fehler"))
        {
          LOGGER.error("Kein Input-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("InputDatei: " + lvInputDatei);
        }

        String lvOutputDatei =  lvReader.getPropertyString("KundennummernUmstellungAZ6", "OutputDatei", "Fehler");
        if (lvOutputDatei.equals("Fehler"))
        {
          LOGGER.error("Kein Output-Dateiname in der ini-Datei...");
          System.exit(1);
        }
        else
        {
          LOGGER.info("OutputDatei: " + lvOutputDatei);
        }

        new KundennummernUmstellungAZ6(lvInstitut, lvImportVerzeichnis, lvExportVerzeichnis, lvKundennummernUmstellungDatei, lvInputDatei, lvOutputDatei);
      }
    }

    System.exit(0);
  }

  /**
   * Interne Klasse fuer die Daten der KundennummernUmstellungAZ6
   */
  private class KundennummernUmstellungAZ6Daten
  {
    /**
     * Kundennummer bei der Bremer Landesbank (BLB)
     */
    private String ivKdNrBLB;

    /**
     * Kundennummer bei der Bundesbank
     */
    private String ivKdNrBundesbank;

    /**
     * Kundennummer bei der NORD/LB (NLB)
     */
    private String ivKdNrNLB;

    /**
     * Konstruktor - Initialisiert die Felder mit leeren Strings
     */
    public KundennummernUmstellungAZ6Daten()
    {
      this.ivKdNrBLB = new String();
      this.ivKdNrBundesbank = new String();
      this.ivKdNrNLB = new String();
    }

    /**
     * @return the ivKdNrBLB
     */
    public String getKdNrBLB()
    {
      return ivKdNrBLB;
    }

    /**
     * @param pvKdNrBLB the ivKdNrBLB to set
     */
    public void setKdNrBLB(String pvKdNrBLB)
    {
      this.ivKdNrBLB = pvKdNrBLB;
    }

    /**
     * @return the ivKdNrBundesbank
     */
    public String getKdNrBundesbank()
    {
      return ivKdNrBundesbank;
    }

    /**
     * @param pvKdNrBundesbank the ivKdNrBundesbank to set
     */
    public void setKdNrBundesbank(String pvKdNrBundesbank)
    {
      this.ivKdNrBundesbank = pvKdNrBundesbank;
    }

    /**
     * @return the ivKdNrNLB
     */
    public String getKdNrNLB()
    {
      return ivKdNrNLB;
    }

    /**
     * @param pvKdNrNLB the ivKdNrNLB to set
     */
    public void setKdNrNLB(String pvKdNrNLB)
    {
      this.ivKdNrNLB = pvKdNrNLB;
    }

    /**
     * Zerlegt den String in einzelne Felder
     * @pvZeile Den zu zerlegenden String
     */
    public void parseDaten(String pvZeile)
    {
      this.setKdNrBLB(pvZeile.substring(0, pvZeile.indexOf(";")));
      pvZeile = pvZeile.substring(pvZeile.indexOf(";") + 1);
      this.setKdNrBundesbank(pvZeile.substring(0, pvZeile.indexOf(";")));
      this.setKdNrNLB(pvZeile.substring(pvZeile.indexOf(";") + 1));
    }

    /**
     * Schreibt die Daten in einen String
     * @return Daten in einem String
     */
    public String toString()
    {
      StringBuilder lvHelpStringBuilder = new StringBuilder();

      lvHelpStringBuilder.append("KdNrBLB: " + this.getKdNrBLB());
      lvHelpStringBuilder.append(" - KdNrBundesbank: " + this.getKdNrBundesbank());
      lvHelpStringBuilder.append(" - KdNrNLB: " + this.getKdNrNLB());

      return lvHelpStringBuilder.toString();
    }
  }

}
