/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Rueckmeldung;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.log4j.Logger;

/**
 * Diese Klasse schreibt eine Rueckmeldedatei.
 * @author tepperc
 */
public class OutputRueckmeldung 
{

  /**
   * Name der Datei
   */
  private String ivFilename;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * FileOutputStream der Rueckmeldedatei
   */
  private FileOutputStream ivFos;
    
    /**
     * Konstruktor
     * @param pvFilename Name der Datei
     * @param pvLogger log4j-Logger
     */
    public OutputRueckmeldung(String pvFilename, Logger pvLogger)
    {
        this.ivFilename = pvFilename;
        this.ivLogger = pvLogger;
    }
    
    /**
      * Oeffnen der Rueckmelde-Datei
      */
    public void open()
    {
      File ivFile = new File(ivFilename);
      try
      {
        ivFos = new FileOutputStream(ivFile);
      }
      catch (Exception e)
      {
        ivLogger.error("Konnte Rueckmelde-Datei nicht oeffnen!");
      }    
    }

    /**
      * Schliessen der Rueckmelde-Datei
      */
    public void close()
    {
      try
      {
        ivFos.close();
      }
      catch (Exception e)
      {
          ivLogger.error("Konnte Rueckmelde-Datei nicht schliessen!");
      }
    }
    
    /**
     * Schreibt eine Rueckmeldezeile in die Datei
     * @param pvZeile Rueckmeldezeile
     */
    public void printRueckmeldezeile(String pvZeile)
    {
        try
        {
           ivFos.write(pvZeile.getBytes());
        }
        catch (Exception e)
        {
           ivLogger.error("Fehler bei Ausgabe der Rueckmeldezeile in die Datei");
        }
    }
}
