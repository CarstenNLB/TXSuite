/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.OutputXML;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.log4j.Logger;

/**
 * Diese Klasse schreibt die XML-Daten/-Zeilen in die Ausgabedatei
 * @author tepperc
 */
public class OutputDarlehenXML 
{
  /**
   * Name der Ausgabedatei
   */
  private String ivFilename;

  /**
   * FileOutputStream in die Ausgabedatei
   */
  private FileOutputStream ivXmlFos;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor
   * @param pvFilename Name der Ausgabedatei
   * @param pvLogger log4j-Logger
   */
    public OutputDarlehenXML(String pvFilename, Logger pvLogger)
    {
      this.ivLogger = pvLogger;
      this.ivFilename = pvFilename;
    }
    
    /**
      * Oeffnen der XML-Datei
      */
    public void openXML()
    {
      File ivXmlFile = new File(ivFilename);
      try
      {
        ivXmlFos = new FileOutputStream(ivXmlFile);
      }
      catch (Exception e)
      {
        ivLogger.error("Konnte XML-Datei nicht oeffnen!");
      }    
    }

    /**
      * Schliessen der XML-Datei
      */
    public void closeXML()
    {
      try
      {
        ivXmlFos.close();
      }
      catch (Exception e)
      {
          ivLogger.error("Konnte XML-Datei nicht schliessen!");
      }
    }

    /**
     * Ausgabe des XML-Kopfs in die XML-Datei
     */
    public void printXMLStart()
    {   
      // Start der XML-Datei schreiben
      try
      {
        ivXmlFos.write(("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n").getBytes());
      }
      catch (Exception e)
      {
        ivLogger.error("Start: Fehler bei Ausgabe in XML-Datei");
      }
    }
    
    /**
     * Schreibt den TXSHeader in die XML-Datei
     * @param pvValdate Datum
     */
    public void printTXSHeader(String pvValdate) 
    {
        try
        {
          ivXmlFos.write(("  <txsi:header valdate=\"" + pvValdate + "\"/>\n").getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Header: Fehler bei Ausgabe in XML-Datei");
        }
    }

    /**
     * Schreibt den Start der TXSImportDaten in die XML-Datei
     */
    public void printTXSImportDatenStart()
    {
        try
        {
          ivXmlFos.write(("<txsi:importdaten xmlns:txsi=\"http://agens.com/txsimport.xsd\">\n").getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("ImportDaten: Fehler bei Ausgabe in XML-Datei");
        }
    }

    /**
     * Schreibt das Ende der TXSImportDaten in die XML-Datei
     */
    public void printTXSImportDatenEnde()
    {
        try
        {
          ivXmlFos.write(("</txsi:importdaten>").getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Ende: Fehler bei Ausgabe in XML-Datei");
        }
    }
    
    /**
     * Schreibt eine Transaktion in die XML-Datei
     * @param pvText Ausgabetext der Transaktion
     */
    public void printTransaktion(String pvText)
    {
        // Transaktion in die XML-Datei schreiben
        try
        {
           ivXmlFos.write(pvText.getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Fehler bei Ausgabe einer Transaktion in die XML-Datei");
        }
    }
    
    /**
     * Schreibt eine Transaktion in die XML-Datei
     * @param pvText Ausgabetext der Transaktion
     */
    public void printTransaktion(StringBuffer pvText)
    {
        // Transaktion in die XML-Datei schreiben
        try
        {
           ivXmlFos.write(pvText.toString().getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("Fehler bei Ausgabe einer Transaktion in die XML-Datei");
        }        
    }

}
