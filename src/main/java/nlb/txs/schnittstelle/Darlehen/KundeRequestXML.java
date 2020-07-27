/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.io.File;
import java.io.FileOutputStream;
import nlb.txs.schnittstelle.Utilities.DatumUtilities;
import nlb.txs.schnittstelle.Utilities.StringKonverter;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class KundeRequestXML 
{
    /**
     * Name der Datei
     */
    private String ivFilename;

    /**
     * FileOutputStream der XML-KundeRequest
     */
    private FileOutputStream ivXmlOS;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
     * Konstruktor
     * @param pvFilename 
     */
    public KundeRequestXML(String pvFilename, Logger pvLogger)
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
        ivLogger.error("Konnte Kunde-Request XML-Datei nicht oeffnen!");
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
          ivLogger.error("Konnte Kunde-Request XML-Datei nicht schliessen!");
      }
    }

    /**
     * Ausgabe des XML-Starts
     */
    public void printXMLStart()
    {   
      // Start der XML-Datei schreiben
      try
      {
        ivXmlOS.write((new String("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>" + StringKonverter.lineSeparator)).getBytes());
        ivXmlOS.write((new String("<TXS>" + StringKonverter.lineSeparator)).getBytes());
      }
      catch (Exception e)
      {
        ivLogger.error("Start: Fehler bei Ausgabe in Kunde-Request XML-Datei");
      }
    }
    
    /**
     * Ausgabe des XML-Ende
     */
    public void printXMLEnde()
    {   
      // Ende der XML-Datei schreiben
      try
      {
        ivXmlOS.write((new String("</TXS>" + StringKonverter.lineSeparator)).getBytes());
      }
      catch (Exception e)
      {
        ivLogger.error("Ende: Fehler bei Ausgabe in Kunde-Request XML-Datei");
      }
    }
 
    /**
     * Ausgabe des XML-Headers
     * @param pvBuchungsdatum Buchungsdatum
     * @param pvDatum Erstellungsdatum
     * @param pvUhrzeit Erstellungsuhrzeit
     */
    public void printXMLHeader(String pvBuchungsdatum, String pvDatum, String pvUhrzeit)
    {   
      // Header der XML-Datei schreiben
      try
      {
        ivXmlOS.write((new String("  <Header>" + StringKonverter.lineSeparator)).getBytes());
        ivXmlOS.write((new String("    <Bestandsdatum>" + pvBuchungsdatum + "</Bestandsdatum>" + StringKonverter.lineSeparator)).getBytes());
        ivXmlOS.write((new String("    <CorrelationID>4710</CorrelationID>" + StringKonverter.lineSeparator)).getBytes());
        ivXmlOS.write((new String("    <SystemID>TXS_P</SystemID>" + StringKonverter.lineSeparator)).getBytes());
        ivXmlOS.write((new String("    <CreationTimestamp>" + DatumUtilities.createTimestamp(pvDatum, pvUhrzeit) + "</CreationTimestamp>" + StringKonverter.lineSeparator)).getBytes());
        ivXmlOS.write((new String("  </Header>" + StringKonverter.lineSeparator)).getBytes());
      }
      catch (Exception e)
      {
        ivLogger.error("XML-Header: Fehler bei Ausgabe in Kunde-Request XML-Datei");
      }
    }

    /**
     * Schreibt den XML-RequestStart in die Datei
     * @param pvInstitut Institutsnummer
     */
    public void printXMLRequestStart(String pvInstitut)
    {
        // RequestStart der XML-Datei schreiben
        try
        {
          ivXmlOS.write((new String("  <Request>" + StringKonverter.lineSeparator)).getBytes());
          ivXmlOS.write((new String("    <Institut nr=\"" + pvInstitut + "\">" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("XML-RequestStart: Fehler bei Ausgabe in Kunde-Request XML-Datei");
        }
       
    }

    /**
     * Schreibt das XML-RequestEnde in die Datei
     */
    public void printXMLRequestEnde()
    {
        // Header der XML-Datei schreiben
        try
        {
           ivXmlOS.write((new String("    </Institut>" + StringKonverter.lineSeparator)).getBytes());
           ivXmlOS.write((new String("  </Request>" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("XML-RequestEnde: Fehler bei Ausgabe in Kunde-Request XML-Datei");
        }       
    }
  
    /**
     * Schreibt die Kunde-Request Datei
     * @param pvKundennummer Kundennummer
     */
    public void printKundeRequest(String pvKundennummer)
    {
        // Header der XML-Datei schreiben
        try
        {
           ivXmlOS.write((new String("      <Kunde nr=\"" + pvKundennummer + "\" />" + StringKonverter.lineSeparator)).getBytes());
        }
        catch (Exception e)
        {
          ivLogger.error("KundeRequest: Fehler bei Ausgabe in Kunde-Request XML-Datei");
        }
    }

}
