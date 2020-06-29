/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Kunde;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.IniReader;
import nlb.txs.schnittstelle.Utilities.ValueMapping;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class KundeVerarbeitung
{

  /**
   * log4j-Logger
   */
  private static Logger LOGGER = Logger.getLogger("TXSKundeLogger");

  // Verarbeitungsmodus STANDARD, d.h. nicht KEV
  public static final int STANDARD = 0;

  // Verarbeitungsmodus KEV
  public static final int KEV = 1;

  /**
   * Modus -> STANDARD oder KEV
   */
  private int ivModus;

  /**
   * Konstruktor fuer Kunde Verarbeitung
   * @param pvModus STANDARD oder KEV
   * @param pvSystem Pfandbrief, RefiReg oder ...
   * @param pvReader Verweis auf den IniReader
   */
  public KundeVerarbeitung(int pvModus, String pvSystem, IniReader pvReader)
  {
    this.ivModus = pvModus;

    if (pvReader != null)
    {
      String lvInstitut = pvReader.getPropertyString("Konfiguration", "Institut", "Fehler");
      if (lvInstitut.equals("Fehler"))
      {
        LOGGER.error("Keine Institutsnummer in der ini-Datei...");
        System.exit(1);
      }

      String lvImportVerzeichnis = pvReader.getPropertyString("Kunde_" + pvSystem, "ImportVerzeichnis", "Fehler");
      if (lvImportVerzeichnis.equals("Fehler"))
      {
        LOGGER.error("Kein Import-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }

      String lvExportVerzeichnis = pvReader.getPropertyString("Kunde_" + pvSystem, "ExportVerzeichnis", "Fehler");
      if (lvExportVerzeichnis.equals("Fehler"))
      {
        LOGGER.error("Kein Export-Verzeichnis in der ini-Datei...");
        System.exit(1);
      }

      String lvResponseDatei =  pvReader.getPropertyString("Kunde_" + pvSystem, "ResponseDatei", "Fehler");
      if (lvResponseDatei.equals("Fehler"))
      {
        LOGGER.error("Kein Response-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      String lvKundeTXSDatei =  pvReader.getPropertyString("Kunde_" + pvSystem, "KundeTXS-Datei", "Fehler");
      if (lvKundeTXSDatei.equals("Fehler"))
      {
        LOGGER.error("Kein KundeTXS-Dateiname in der ini-Datei...");
        System.exit(1);
      }

      startVerarbeitung(lvInstitut, lvImportVerzeichnis, lvExportVerzeichnis, lvResponseDatei, lvKundeTXSDatei);
    }
  }

  /**
   * Startet die Kunde Verarbeitung
   * @param pvInstitut
   * @param pvImportVerzeichnis
   * @param pvExportVerzeichnis
   * @param pvResponseDatei
   * @param pvKundeTXSDatei
   */
  public void startVerarbeitung(String pvInstitut, String pvImportVerzeichnis, String pvExportVerzeichnis, String pvResponseDatei, String pvKundeTXSDatei)
  {

    String lvFilenameIn  = pvImportVerzeichnis + "\\" + pvResponseDatei;
    String lvFilenameOut = pvImportVerzeichnis + "\\" + pvKundeTXSDatei;

    Runtime lvRuntime = Runtime.getRuntime();

    LOGGER.info("Used Memory vor dem Lesen:"
        + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));

    Document lvDocument = leseKunden(lvFilenameIn);

    LOGGER.info("Used Memory nach dem Lesen:"
        + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));

    schreibeKunden(lvFilenameOut, lvDocument);

    LOGGER.info("Used Memory nach dem Schreiben:"
        + ((lvRuntime.totalMemory() - lvRuntime.freeMemory()) / 1024));
  }

  /**
   * Liest die Kundendaten von SPOT
   * @param pvFilename Dateiname der Kundendaten von SPOT
   */
  private Document leseKunden(String pvFilename)
  {
    // Document
    Document lvDocument = null;

    // Zur Eingabe
    SAXBuilder lvBuilder = null;
    lvBuilder = new SAXBuilder();
    lvBuilder.setExpandEntities(true);

    try
    {
      lvDocument = (Document)lvBuilder.build(pvFilename);
    }
    catch(IOException io)
    {
      LOGGER.error(io.getMessage());
    }
    catch(JDOMException jdomex)
    {
      LOGGER.error(jdomex.getMessage());
    }

    return lvDocument;
  }

  /**
   * Schreibt die Kundendaten in die TXS XML-Datei
   * @param pvFilenameOut
   * @param pvDocument
   */
  private boolean schreibeKunden(String pvFilenameOut, Document pvDocument)
  {
    // Zur Ausgabe
    Element lvMyrootelement = new Element("importdaten");
    Namespace lvMyNamespace = Namespace.getNamespace("txsi", "http://agens.com/txsimport.xsd");
    lvMyrootelement.setNamespace(lvMyNamespace);
    Document lvMydocument = new Document(lvMyrootelement);

    Element lvRootNode = pvDocument.getRootElement();

    Element lvNodeHeader = (Element)lvRootNode.getChildren("Header").get(0);

    LOGGER.info("Bestandsdatum: " + lvNodeHeader.getChildText("Bestandsdatum"));
    LOGGER.info("CorrelationID: " + lvNodeHeader.getChildText("CorrelationID"));
    LOGGER.info("SystemID: " + lvNodeHeader.getChildText("SystemID"));
    LOGGER.info("CreationTimestamp: " + lvNodeHeader.getChildText("CreationTimestamp"));

    // Header anhaengen
    Element lvMyelement2 = new Element("header");

    lvMyelement2.setAttribute("valdate", DatumUtilities.changeDate(DatumUtilities.changeDatePoints(lvNodeHeader.getChildText("Bestandsdatum"))));
    lvMyrootelement.addContent(lvMyelement2);

    Element lvNodeResponse = (Element)lvRootNode.getChildren("Response").get(0);

    Element lvNodeInstitut = (Element)lvNodeResponse.getChildren("Institut").get(0);

    String lvInstitut = lvNodeInstitut.getAttributeValue("nr");

    LOGGER.info("Institut: " + lvInstitut);

    List<Element> lvListKunde = lvNodeInstitut.getChildren("Kunde");

    LOGGER.info("Anzahl gefundener Kunden: " + lvListKunde.size());

    Element lvNodeKunde;
    for (int i = 0; i < lvListKunde.size(); i++)
    {
      //StringBuffer lvHelpCSV = new StringBuffer();

      lvNodeKunde = (Element)lvListKunde.get(i);
      if (lvNodeKunde.getAttributeValue("status").equals("R"))
      {
        // TXSPerson
        Element lvElement_pers = new Element("pers");
        lvElement_pers.setNamespace(lvMyNamespace);

        // pers aufbauen
        String lvKundennummer = lvNodeKunde.getAttributeValue("nr");
        lvElement_pers.setAttribute("kdnr", lvKundennummer);
        lvElement_pers.setAttribute("org", ValueMapping.changeMandant(lvInstitut));
        lvElement_pers.setAttribute("quelle", "KUNDE");

        // TXSPersonDaten
        Element lvElement_persdaten = new Element("persdaten");
        lvElement_persdaten.setNamespace(lvMyNamespace);

        // persdaten aufbauen
        String lvVorname = lvNodeKunde.getChildText("Vorname");
        lvElement_persdaten.setAttribute("vname", lvVorname);
        String lvNachname = lvNodeKunde.getChildText("Nachname");
        lvElement_persdaten.setAttribute("nname", lvNachname); // + nodeKunde.getChildText("Beruf"));
        //lvHelpCSV.append(lvNachname + ";");

        Element lvNodeAdresse = (Element)lvNodeKunde.getChildren("Adresse").get(0);

        lvElement_persdaten.setAttribute("str", lvNodeAdresse.getChildText("Strasse"));
        // CT 29.11.2011 - SPOT liefert eine Hausnummer
        lvElement_persdaten.setAttribute("hausnr", lvNodeAdresse.getChildText("Hausnr"));
        lvElement_persdaten.setAttribute("plz", lvNodeAdresse.getChildText("Plz"));
        lvElement_persdaten.setAttribute("ort", lvNodeAdresse.getChildText("Ort"));
        lvElement_persdaten.setAttribute("text", lvNodeAdresse.getChildText("Ergaenzung"));

        if (lvNodeAdresse.getChildText("Land").equals("U1"))
          lvElement_persdaten.setAttribute("land", "GB");
        else
          lvElement_persdaten.setAttribute("land", lvNodeAdresse.getChildText("Land"));

        //lvHelpCSV.append(lvNodeAdresse.getChildText("Strasse") + ";" + lvNodeAdresse.getChildText("Hausnr") + ";" + lvNodeAdresse.getChildText("Plz") + ";" +  lvNodeAdresse.getChildText("Ort") + ";" + lvNodeAdresse.getChildText("Land"));

        //LOGGER.info("DeepSea;DeepSea;Kunde;" + lvKundennummer + ";" + lvHelpCSV.toString());

        // CT 22.12.2011 User-definierte Felder
        lvElement_persdaten.setAttribute("kusyma", lvNodeKunde.getChildText("Kusyma_Neu"));
        if (ivModus == KundeVerarbeitung.STANDARD)
        {
          lvElement_persdaten.setAttribute("kugr", ValueMapping.changeKundengruppe(ermittleKundengruppe(lvNodeKunde.getChildText("Kusyma"))));
        }

        // CT 30.11.2011 - SPOT liefert kein Gebiet, deshalb selbst ermitteln
        if (lvNodeAdresse.getChildText("Land").equals("DE"))
        {
          String lvGebiet = lvNodeAdresse.getChildText("Land") + "_";
          int lvRet_gebiet = ValueMapping.PLZ2Land(lvInstitut, lvNodeAdresse.getChildText("Plz"));
          if (lvRet_gebiet < 10)
          {
            lvGebiet = lvGebiet + "0";
          }
          //if (ret_gebiet < 100)
          //{
          //  gebiet = gebiet + "0";
          //}
          lvGebiet = lvGebiet + lvRet_gebiet;

          lvElement_persdaten.setAttribute("gebiet", lvGebiet);
        }

        // CT 16.09.2014 User-definierte Rating Felder
        Element lvNodeRating = (Element)lvNodeKunde.getChildren("Rating").get(0);
        if (!lvNodeRating.getChildText("rating_id").isEmpty())
        {
          lvElement_persdaten.setAttribute("rating_id", lvNodeRating.getChildText("rating_id"));
        }
        if (!lvNodeRating.getChildText("rating_master").isEmpty())
        {
          lvElement_persdaten.setAttribute("rating_master", lvNodeRating.getChildText("rating_master"));
        }
        if (!lvNodeRating.getChildText("rating_datum").isEmpty())
        {
          lvElement_persdaten.setAttribute("rating_datum", lvNodeRating.getChildText("rating_datum").substring(8) + "." + lvNodeRating.getChildText("rating_datum").substring(5,7) + "." + lvNodeRating.getChildText("rating_datum").substring(0,4));
        }
        if (!lvNodeRating.getChildText("rating_tool_id").isEmpty())
        {
          lvElement_persdaten.setAttribute("rating_tool_id", lvNodeRating.getChildText("rating_tool_id"));
        }
        if (!lvNodeRating.getChildText("rating_tool").isEmpty())
        {
          //String lvHelpRatingTool = String2XML.change2XML(lvNodeRating.getChildText("rating_tool"));
          String lvHelpRatingTool = lvNodeRating.getChildText("rating_tool");
          if (lvHelpRatingTool.length() > 80)
          {
            //System.out.println("lvHelpRatingTool: " + lvHelpRatingTool);
            lvHelpRatingTool = lvHelpRatingTool.substring(0,80);
            LOGGER.info("RatingTool gekuerzt: " + lvHelpRatingTool);
          }
          lvElement_persdaten.setAttribute("rating_tool", lvHelpRatingTool);
        }

        // KEVSchuldnertyp
        if (ivModus == KundeVerarbeitung.KEV)
        {
          //lvElement_persdaten.setAttribute("kev_styp", ermittleKEVSchuldnertyp(lvNodeKunde.getChildText("Kusyma_Neu")));
          // TXS-Feld KEVSchuldnertyp
          lvElement_persdaten.setAttribute("kevschuldtyp", ermittleKEVSchuldnertyp(lvNodeKunde.getChildText("Kusyma_Neu")));
        }

        // KEV-Rating-Id
        if (ivModus == KundeVerarbeitung.KEV)
        {
          lvElement_persdaten.setAttribute("kev_rating_id", ermittleKEVRatingId(lvNodeKunde.getChildText("Kusyma_Neu"), lvNodeAdresse.getChildText("Land")));
        }

        // pers anhaengen
        lvMyrootelement.addContent(lvElement_pers);

        // persdaten anhaengen
        lvElement_pers.addContent(lvElement_persdaten);
      }
      else
      {
        LOGGER.info("Kunde " + lvNodeKunde.getAttributeValue("nr") + " mit Status " + lvNodeKunde.getAttributeValue("status"));
      }
    }

    // Ausgabe
    FileOutputStream lvKundeOS = null;
    File lvFileKunde = new File(pvFilenameOut);
    try
    {
      lvKundeOS = new FileOutputStream(lvFileKunde);
    }
    catch (Exception e)
    {
      LOGGER.error("Konnte Kunde-XML Datei nicht oeffnen!");
    }

    XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());

    try
    {
      lvOutputter.output(lvMydocument, lvKundeOS);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    return true;
  }

  /**
   * Ermittlung der KEV-Rating-Id ueber die Kusyma und das Land
   * @param pvKusyma Kusyma
   * @param pvLand Land
   * @return
   */
  private String ermittleKEVRatingId(String pvKusyma, String pvLand)
  {
    String lvKEVRatingId = "DER000000000099"; // Default-Wert

    if (ermittleKEVSchuldnertyp(pvKusyma).equals("Unternehmen")) // Wenn Unternehmen, dann...
    {
      if (pvLand.equals("DE")) // Deutschland
      {
        lvKEVRatingId = "DER000000000057";
      }

      if (pvLand.equals("AT")) // Oesterreich
      {
        lvKEVRatingId = "DER000000000073";
      }

      if (pvLand.equals("FR")) // Frankreich
      {
        lvKEVRatingId = "DER000000000065";
      }
    }

    return lvKEVRatingId;
  }

  /**
   * Ermittlung des KEVSchuldnertyps ueber die Kusyma
   * @param pvKusyma Kusyma
   * @return
   */
  private String ermittleKEVSchuldnertyp(String pvKusyma)
  {
    // 1 -> Unternehmen
    // 2 -> PSE1
    // 3 -> PSE2
    // 4 -> InternationaleOderSupranationaleInstitution

    String lvKEVSchuldnertyp = "Unternehmen"; // Default-Wert Unternehmen

    if (pvKusyma.charAt(0) == '1' && pvKusyma.charAt(5) == '0') // Zentralstaat
    {
      lvKEVSchuldnertyp = "PSE1";
    }

    if (pvKusyma.charAt(0) == '1' && (pvKusyma.charAt(5) == '1' || pvKusyma.charAt(5) == '2')) // Land
    {
      lvKEVSchuldnertyp = "PSE1";
    }

    if (pvKusyma.charAt(0) == '1' && (pvKusyma.charAt(5) == '4') || (pvKusyma.charAt(5) == '5' && pvKusyma.substring(13).equals("30")))
    {
      lvKEVSchuldnertyp = "PSE1";
    }

    return lvKEVSchuldnertyp;
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
