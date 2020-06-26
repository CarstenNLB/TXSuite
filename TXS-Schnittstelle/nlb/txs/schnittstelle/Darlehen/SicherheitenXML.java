/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.io.File;
import java.io.FileOutputStream;

import nlb.txs.schnittstelle.Darlehen.Daten.Extrakt.Sicherheit;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.DWHVOR;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class SicherheitenXML 
{
    /**
     * Name der XML-Ausgabedatei
     */
    private String ivFilename;

    /**
     * FileOutputStream der XML-Ausgabedatei
     */
    private FileOutputStream ivXmlOS;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor
   * @param pvFilename Name der XML-Ausgabedatei
   * @param pvLogger log4j-Logger
   */
    public SicherheitenXML(String pvFilename, Logger pvLogger)
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
        ivLogger.error("Konnte die Sicherheiten XML-Datei nicht oeffnen!");
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
          ivLogger.error("Konnte die Sicherheiten XML-Datei nicht schliessen!");
      }
    }
     
    /**
     * @param pvVorlaufsatz Vorlaufsatz
     * 
     */
    public void printXMLStart(DWHVOR pvVorlaufsatz)
    {
        // RequestStart der XML-Datei schreiben
        try
        {
            ivXmlOS.write((new String("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>" + StringKonverter.lineSeparator)).getBytes());
            ivXmlOS.write((new String("<Institut nr=\"" + pvVorlaufsatz.getsDwvinst()+ "\">" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Start: Fehler bei Ausgabe in die Sicherheiten XML-Datei");
        }
       
    }

    /**
     * 
     */
    public void printXMLEnde()
    {
        try
        {
           ivXmlOS.write((new String("</Institut>" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Ende: Fehler bei Ausgabe in die Sicherheiten XML-Datei");
        }       
    }

    
    /**
     * Schreibt die Sicherheit in die XML-Datei
     * @param pvSicherheit
     */
    public void printSicherheit(Sicherheit pvSicherheit)
    {
        try
        {
           ivXmlOS.write(("  <Sicherheit nr=\"" + pvSicherheit.getObjektnummer() + "\">" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Darlehenssystem>" + pvSicherheit.getHerkunftDarlehen() + "</Darlehenssystem>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Sicherheitensystem>" + pvSicherheit.getHerkunftSicherheit() + "</Sicherheitensystem>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Datenherkunft>" + pvSicherheit.getHerkunftDaten() + "</Datenherkunft>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kundennummer>" + pvSicherheit.getKundennummer() + "</Kundennummer>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Kontonummer>" + pvSicherheit.getKontonummer() + "</Kontonummer>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Sicherheitenschluessel>" + pvSicherheit.getSicherheitenschluessel() + "</Sicherheitenschluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Infotyp>" + pvSicherheit.getInfotyp() + "</Infotyp>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Buchungsschluessel>" + pvSicherheit.getBuchungsschluessel() + "</Buchungsschluessel>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Zuweisungsbetrag>" + pvSicherheit.getZuweisungsbetrag() + "</Zuweisungsbetrag>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Grundschuld>" + pvSicherheit.getGrundschuld() + "</Grundschuld>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Nominalwert>" + pvSicherheit.getNominalwert() + "</Nominalwert>" + StringKonverter.lineSeparator).getBytes());
           ivXmlOS.write(("    <Verfuegungsbetrag>" + pvSicherheit.getVerfuegungsbetrag() + "</Verfuegungsbetrag>" + StringKonverter.lineSeparator).getBytes());
           //xmlOS.write((new String("      <FremdeRechte>" + sicherheit.getFremdeRechte() + "</FremdeRechte>\n")).getBytes());
           //xmlOS.write((new String("      <EigeneRechte>" + sicherheit.getEigeneRechte() + "</EigeneRechte>\n")).getBytes());
           ivXmlOS.write(("  </Sicherheit>" + StringKonverter.lineSeparator).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Fehler bei Ausgabe in die Sicherheiten XML-Datei");
        }
    }


}
