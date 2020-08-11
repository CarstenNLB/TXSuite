/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package de.nordlbit.prost.tools.KEVXMLMerger;

import de.nordlbit.prost.Utilities.IniReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class KEVXMLMerger {
  /**
   * log4j-Logger
   */
  private static Logger LOGGER = Logger.getLogger("KEVXMLMergerLogger");

  /**
   * Startroutine KEVXMLMerger
   * @param argv
   */
  public static void main(String[] argv)
  {
    if (!argv[argv.length - 1].endsWith(".ini"))
    {
      System.out.println("Starten:");
      System.out.println("KEVXMLMerger <ini-Datei>");
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
        String lvLoggingXML = lvReader.getPropertyString("KEVXMLMerger", "log4jconfig", "Fehler");
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
        LOGGER.info("Institut: " + lvInstitut);

        String lvEingabedatei1 = lvReader.getPropertyString("KEVXMLMerger", "Eingabedatei1", "Fehler");
        if (lvEingabedatei1.equals("Fehler"))
        {
          LOGGER.error("Keine Eingabedatei1 in der ini-Datei...");
          System.exit(1);
        }
        LOGGER.info("Eingabedatei1: " + lvEingabedatei1);

        String lvEingabedatei2 = lvReader.getPropertyString("KEVXMLMerger", "Eingabedatei2", "Fehler");
        if (lvEingabedatei2.equals("Fehler"))
        {
          LOGGER.error("Keine Eingabedatei2 in der ini-Datei...");
          System.exit(1);
        }
        LOGGER.info("Eingabedatei2: " + lvEingabedatei2);


        String lvAusgabedatei = lvReader.getPropertyString("KEVXMLMerger", "Ausgabedatei", "Fehler");
        if (lvAusgabedatei.equals("Fehler"))
        {
          LOGGER.error("Keine Ausgabedatei in der ini-Datei...");
          System.exit(1);
        }
        LOGGER.info("Ausgabedatei: " + lvAusgabedatei);

        new KEVXMLMerger(lvEingabedatei1, lvEingabedatei2, lvAusgabedatei);
      }
    }
    System.exit(0);
  }

  /**
   * Konstruktor
   * @param pvEingabedatei1 Eingabedatei1
   * @param pvEingabedatei2 Eingabedatei2
   * @param pvAusgabedatei Ausgabedatei
   */
  public KEVXMLMerger(String pvEingabedatei1, String pvEingabedatei2, String pvAusgabedatei)
  {
    // Dateien konkatenieren
    concat(pvEingabedatei1, pvEingabedatei2, pvAusgabedatei);
  }

  /**
   * Liest die XML-Datei ein
   * @param pvFilename Dateiname der XML-Datei
   * @return
   */
  private Document readXML(String pvFilename)
  {
    // XML-Dokument
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
   * Konkatenierung von XML-Importdateien
   * @param pvFilenameEingabedatei1 Eingabedatei1
   * @param pvFilenameEingabedatei2 Eingabedatei2
   * @param pvFilenameTarget Ausgabedatei
   */
  private void concatXML(String pvFilenameEingabedatei1, String pvFilenameEingabedatei2, String pvFilenameTarget)
  {
    boolean lvFirst = true;
    Document lvDocumentSource = null;
    Document lvDocumentTarget = null;
    Element lvRootElementTarget = null;

    lvRootElementTarget = new Element("Informationsfile");
    //Namespace lvMyNamespace = Namespace.getNamespace("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    //lvRootElementTarget.setNamespace(lvMyNamespace);

    lvRootElementTarget.setAttribute("xsi:schemaLocation", "http://www.bundesbank.de/maccs_1_0 MACCsInformationsfile_1_2.xsd");
    lvRootElementTarget.setAttribute("xmlns", "http://www.bundesbank.de/maccs_1_0");
    lvRootElementTarget.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

    lvDocumentTarget = new Document(lvRootElementTarget);

    // Source einlesen
    lvDocumentSource = readXML(pvFilenameEingabedatei1);

    // RootNode vom Eingangsdokument einlesen
    Element lvRootNode = lvDocumentSource.getRootElement();

    List<Element> lvList = lvRootNode.getChildren();
    for (int i = 0; i < lvList.size(); i++)
    {
        Element lvNode = (Element)lvList.get(i);
        if (lvNode.getName().equals("Dateiidentifikation") && lvFirst)
        {
          lvRootElementTarget.addContent(lvNode.clone());
          lvFirst = false;
        }
        //if (lvNode.getName().equals(""))
        //{
        //  lvRootElementTarget.addContent(lvNode.clone());
        //}
    }

    // Ausgabe
    FileOutputStream lvXMLOS = null;
    File lvFileXML = new File(pvFilenameTarget);
    try
    {
      lvXMLOS = new FileOutputStream(lvFileXML);
    }
    catch (Exception exp)
    {
      LOGGER.error("Konnte XML-Ausgabedatei nicht oeffnen!");
    }

    XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());

    try
    {
      lvOutputter.output(lvDocumentTarget, lvXMLOS);
    }
    catch (IOException exp)
    {
      exp.printStackTrace();
    }

    try
    {
      lvXMLOS.close();
    }
    catch (IOException exp)
    {
      LOGGER.error("Konnte XML-Ausgabedatei nicht schliessen!");
    }
  }

  /**
   * Konkatenierung von XML-Importdateien
   * @param pvFilenameEingabedatei1 Eingabedatei1
   * @param pvFilenameEingabedatei2 Eingabedatei2
   * @param pvFilenameTarget Ausgabedatei
   */
  private void concat(String pvFilenameEingabedatei1, String pvFilenameEingabedatei2, String pvFilenameTarget)
  {
     System.out.println("Source1: " + pvFilenameEingabedatei1);
     System.out.println("Source2: " + pvFilenameEingabedatei2);
     System.out.println("Target: " + pvFilenameTarget);

     String lvZeile;

     // FileOutputStream fuer Target
     FileOutputStream ivFosTarget = null;

     File lvFileTarget = new File(pvFilenameTarget);
     try
     {
       ivFosTarget = new FileOutputStream(lvFileTarget);
     }
     catch (Exception e)
     {
       System.out.println("Konnte Target nicht oeffnen!");
     }

     // Oeffnen Source1
     FileInputStream lvFileInputStream = null;
     File ivFileSource1 = new File(pvFilenameEingabedatei1);
     try
     {
       lvFileInputStream = new FileInputStream(ivFileSource1);
     }
     catch (Exception e)
     {
       System.out.println("Konnte Source1 nicht oeffnen!");
       System.exit(1);
     }

     BufferedReader lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));

     try
     {
       while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Einlesen
       {
         if (!lvZeile.contains("</SchuldnerListe>") && !lvZeile.contains("</Informationsfile>"))
         {
           ivFosTarget.write((lvZeile + StringKonverter.lineSeparator).getBytes());
         }
       }

     }
     catch (Exception exp)
     {
       System.out.println("Fehler beim Einfuegen von Source1");
     }

     try
     {
       lvFileInputStream.close();
     }
     catch (Exception e)
     {
       System.out.println("Fehler beim Schliessen Source1");
     }

     // Oeffnen Source2
     File ivFileSource2 = new File(pvFilenameEingabedatei2);
     try
     {
       lvFileInputStream = new FileInputStream(ivFileSource2);
     }
     catch (Exception e)
     {
       System.out.println("Konnte Source2 nicht oeffnen!");
       System.exit(1);
     }

     lvBufferedReaderIn = new BufferedReader(new InputStreamReader(lvFileInputStream));

     try
     {
       for (int i = 0; i < 7; i++)
       {
         lvZeile = lvBufferedReaderIn.readLine();
       }
       while ((lvZeile = lvBufferedReaderIn.readLine()) != null)  // Einlesen
       {
         ivFosTarget.write((lvZeile + StringKonverter.lineSeparator).getBytes());
       }
     }
     catch (Exception exp)
     {
       System.out.println("Fehler beim Einfuegen von Source2");
     }

     try
     {
       lvFileInputStream.close();
     }
     catch (Exception e)
     {
       System.out.println("Fehler beim Schliessen Source2");
     }

     try
     {
       ivFosTarget.close();
     }
     catch (Exception e)
    {
       System.out.println("Fehler beim Schliessen vom Target");
    }
  }

}
