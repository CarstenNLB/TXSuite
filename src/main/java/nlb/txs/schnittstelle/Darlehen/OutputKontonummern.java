/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import java.io.File;
import java.io.FileOutputStream;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class OutputKontonummern 
{
    /**
     * Name der Datei
     */
    private String ivFilename;

    /**
     * FileOutputStream fuer die Kontonummern
     */
    private FileOutputStream ivKtoOS;
 
    /**
     * Konstruktor
     * @param pvFilename Name der Datei
     */
    public OutputKontonummern(String pvFilename)
    {
        this.ivFilename = pvFilename;
    }
    
    /**
      * Oeffnen der Datei
      */
    public void open()
    {
      File ivKtoFile = new File(ivFilename);
      try
      {
        ivKtoOS = new FileOutputStream(ivKtoFile);
      }
      catch (Exception e)
      {
        System.out.println("Konnte Kontonummern Datei nicht oeffnen!");
      }    
    }

    /**
      * Schliessen der Datei
      */
    public void close()
    {
      try
      {
        ivKtoOS.close();
      }
      catch (Exception e)
      {
          System.out.println("Konnte Kontonummern Datei nicht schliessen!");       
      }
    }
    
    /**
     * Schreibt die Kontonummer in die Datei
     * @param pvKontonummer Kontonummern
     */
    public void printKontonummer(String pvKontonummer)
    {
        try
        {
           ivKtoOS.write((pvKontonummer + StringKonverter.lineSeparator).getBytes());
        }
        catch (Exception e)
        {
          System.out.println("Fehler bei Ausgabe der Kontonummer in die Datei '" + ivFilename + "'");
        }
    }

}
