/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.io.File;
import java.io.FileOutputStream;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherungsobjekt;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.DWHVOR;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class SicherungsobjekteXML 
{
    /**
     * Name der XML-Ausgabedatei
     */
    private String ivFilename;

    /**
     * FileOutputStream fuer die XML-Ausgabedatei
     */
    private FileOutputStream ivXmlOS;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
     * Konstruktor
     * @param pvFilename Name der XML-Ausgabedatei
     * @param pvLogger log4-Logger
     */
    public SicherungsobjekteXML(String pvFilename, Logger pvLogger)
    {
        this.ivFilename = pvFilename;
        this.ivLogger = pvLogger;
    }
    
    /**
      * Oeffnen der XML-Datei
      */
    public void openXML()
    {
      File ivXmlFile = new File(ivFilename);
      try
      {
        ivXmlOS = new FileOutputStream(ivXmlFile);
      }
      catch (Exception e)
      {
        ivLogger.error("Konnte die Sicherungsobjekte XML-Datei nicht oeffnen!");
      }    
    }

    /**
      * Schliessen der XML-Datei
      */
    public void closeXML()
    {
      try
      {
        ivXmlOS.close();
      }
      catch (Exception e)
      {
          ivLogger.error("Konnte die Sicherungsobjekte XML-Datei nicht schliessen!");
      }
    }
     
    /**
     * Start der XML-Ausgabedatei schreiben
     * @param pvVorlaufsatz Vorlaufsatz
     * 
     */
    public void printXMLStart(DWHVOR pvVorlaufsatz)
    {
        // Start der XML-Datei schreiben
        try
        {
            ivXmlOS.write((new String("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>" + StringKonverter.lineSeparator)).getBytes());
            ivXmlOS.write((new String("<Institut nr=\"" + pvVorlaufsatz.getsDwvinst()+ "\">" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Start: Fehler bei Ausgabe in die Sicherungsobjekte XML-Datei");
        }
       
    }

    /**
     * Ende der XML-Ausgabedatei schreiben
     */
    public void printXMLEnde()
    {
      // Ende der XML-Datei schreiben
        try
        {
           ivXmlOS.write((new String("</Institut>" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Ende: Fehler bei Ausgabe in die Sicherungsobjekte XML-Datei");
        }       
    }

    
    /**
     * Schreibt das Sicherungsobjekt in die XML-Ausgabedatei
     * @param pvSicherungsObj 
     */
    public void printSicherungsobjekte(Sicherungsobjekt pvSicherungsObj)
    {
        try
        {
           ivXmlOS.write(("  <Sicherungsobjekt nr=\"" + pvSicherungsObj.getObjektnummer() + "\">" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Darlehenssystem>" + pvSicherungsObj.getHerkunftDarlehen() + "</Darlehenssystem>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Sicherheitensystem>" + pvSicherungsObj.getHerkunftSicherheit() + "</Sicherheitensystem>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Datenherkunft>" + pvSicherungsObj.getHerkunftDaten() + "</Datenherkunft>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Objektart>" + pvSicherungsObj.getObjektart() + "</Objektart>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Postleitzahl>" + pvSicherungsObj.getPostleitzahl() + "</Postleitzahl>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Ort>" + pvSicherungsObj.getOrt() + "</Ort>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Strasse>" + pvSicherungsObj.getStrasse() + "</Strasse>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Beleihungsgebiet>" + pvSicherungsObj.getBeleihungsgebiet() + "</Beleihungsgebiet>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kundennummer>" + pvSicherungsObj.getKundennummer() + "</Kundennummer>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kontonummer>" + pvSicherungsObj.getKontonummer() + "</Kontonummer>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Grundbuch>" + pvSicherungsObj.getGrundbuch() + "</Grundbuch>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Band>" + pvSicherungsObj.getBand() + "</Band>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Blatt>" + pvSicherungsObj.getBlatt() + "</Blatt>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <LaufendeNr>" + pvSicherungsObj.getLaufendeNummer() + "</LaufendeNr>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Verwendungszweck>" + pvSicherungsObj.getVerwendungszweck() + "</Verwendungszweck>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Zusatzsicherheit>" + pvSicherungsObj.getZusatzsicherheit() + "</Zusatzsicherheit>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <WKZ-Beleihungswert>" + pvSicherungsObj.getBeleihungswertWKZ() + "</WKZ-Beleihungswert>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Baujahr>" + pvSicherungsObj.getBaujahr() + "</Baujahr>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Beleihungswert>" + pvSicherungsObj.getBeleihungswert() + "</Beleihungswert>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Sachwert>" + pvSicherungsObj.getSachwert() + "</Sachwert>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Ertragswert>" + pvSicherungsObj.getErtragswert() + "</Ertragswert>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Grundschuld>" + pvSicherungsObj.getGrundschuld() + "</Grundschuld>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("  </Sicherungsobjekt>" + StringKonverter.lineSeparator).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Fehler bei Ausgabe in die Sicherungsobjekte XML-Datei");
        }
    }
}
