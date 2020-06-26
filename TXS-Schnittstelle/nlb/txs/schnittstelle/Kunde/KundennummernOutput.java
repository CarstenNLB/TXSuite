/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Kunde;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

public class KundennummernOutput 
{
    /**
     * Name der Kundendatei
     */
    private String ivFilename;
    
    /**
     * FileOutputStream der Kundendatei
     */
    private FileOutputStream ivKdOS;
 
    /**
     * Loggerdatei
     */
    private Logger ivLogger;
    
    /**
     * Konstruktor
     * @param pvFilename Filename
     */
    public KundennummernOutput(String pvFilename, Logger pvLogger)
    {
        this.ivFilename = pvFilename;
        this.ivLogger = pvLogger;
    }
    
    /**
      * Oeffnen der Kundendatei
      */
    public void open()
    {
      File ivKdFile = new File(ivFilename);
      try
      {
        ivKdOS = new FileOutputStream(ivKdFile);
      }
      catch (Exception exp)
      {
        ivLogger.error("Konnte Kundendatei " + ivFilename + " nicht oeffnen!");
      }    
    }

    /**
      * Schliessen der Kundendatei
      */
    public void close()
    {
      try
      {
        ivKdOS.close();
      }
      catch (Exception exp)
      {
          ivLogger.error("Konnte Kundendatei " + ivFilename + " nicht schliessen!");    
      }
    }
    
    /**
     * Schreibt eine Kundennummer in die Datei
     * @param pvKundennummer Kundennummer
     */
    public void printKundennummer(String pvKundennummer)
    {
        try
        {
           ivKdOS.write((pvKundennummer + StringKonverter.lineSeparator).getBytes());
        }
        catch (Exception exp)
        {
          ivLogger.error("Fehler bei Ausgabe der Kundennummer " + pvKundennummer + " in die Kundendatei " + ivFilename);
        }
    }
    
    /**
     * Schreibt den Vorlaufsatz in die Datei
     * @param pvInstitut Institutsnummer
     * @param pvZielsystem Zielsystem
     */
    public void printVorlaufsatz(String pvInstitut, String pvZielsystem)
    {
        KundeRequestVorlaufsatz lvKundeRequestVorlaufsatz = new KundeRequestVorlaufsatz(pvInstitut, pvZielsystem);
        try
        {
          ivKdOS.write(lvKundeRequestVorlaufsatz.toString().getBytes());
        }
        catch (Exception exp)
        {
        	ivLogger.error("Fehler beim Schreiben des Vorlaufsatzes in die Kundendatei " + ivFilename);
        }

    }
    

}
